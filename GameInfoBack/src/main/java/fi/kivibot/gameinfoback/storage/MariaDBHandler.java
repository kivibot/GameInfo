package fi.kivibot.gameinfoback.storage;

import fi.kivibot.gameinfoback.storage.DatabaseException;
import fi.kivibot.gameinfoback.storage.DatabaseHandler;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import fi.kivibot.gameinfoback.DeprecatedMariaDBHandler;
import fi.kivibot.gameinfoback.api.Platform;
import fi.kivibot.gameinfoback.api.struct.league.League;
import fi.kivibot.gameinfoback.api.struct.stats.AggregatedStats;
import fi.kivibot.gameinfoback.api.struct.stats.ChampionStats;
import fi.kivibot.gameinfoback.api.struct.stats.RankedStats;
import fi.kivibot.gameinfoback.api.struct.summoner.Summoner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicklas
 */
public class MariaDBHandler implements DatabaseHandler {

    private final Connection conn;

    public MariaDBHandler(ComboPooledDataSource cpds) throws SQLException {
        conn = cpds.getConnection();
        conn.setAutoCommit(false);
    }

    @Override
    public void commit() throws DatabaseException {
        try {
            conn.commit();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public void updateSummoners(Platform p, Collection<Summoner> sums) throws DatabaseException {
        System.out.println("updating: " + p + " " + sums);
        if (sums.isEmpty()) {
            return;
        }
        try (PreparedStatement ps = conn.prepareStatement("insert into summoner(region, summonerId, name, profileIconId, revisionDate, summonerLevel, updated, rsu, leu) values (?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE name=values(name), profileIconId=values(profileIconId), revisionDate=values(revisionDate), summonerLevel=values(summonerLevel), updated=values(updated), rsu=values(rsu), leu=values(leu);")) {
            for (Summoner s : sums) {
                ps.setString(1, p.getRegion());
                ps.setInt(2, (int) s.getId());
                if (s.getId() < 0) {
                    System.out.println("SUMMONER ID <0 DETECTED!");
                }
                ps.setString(3, s.getName());
                ps.setInt(4, s.getProfileIconId());
                ps.setTimestamp(5, new Timestamp(s.getRevisionDate()));
                ps.setInt(6, (int) s.getSummonerLevel());
                ps.setTimestamp(7, new Timestamp(s.getLastUpdated()));
                ps.setTimestamp(8, new Timestamp(s.getRsu()));
                ps.setTimestamp(9, new Timestamp(s.getLeu()));
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public Map<Long, Summoner> getSummoners(Platform p, Collection<Long> ids) throws DatabaseException {
        if (ids.isEmpty()) {
            return new HashMap<>();
        }
        System.out.println(ids.size());
        StringBuilder idps = new StringBuilder();
        for (int i = 0; i < ids.size() - 1; i++) {
            idps.append("?,");
        }
        idps.append("?");
        Map<Long, Summoner> map = new HashMap<>();
        try (PreparedStatement ps = conn.prepareStatement("select summonerId, name, profileIconId, revisionDate, summonerLevel, updated, rsu, leu from summoner where region=? and summonerId in (" + idps + ")")) {
            ps.setString(1, p.getRegion());
            int i = 2;
            Iterator<Long> it = ids.iterator();
            while (it.hasNext()) {
                ps.setInt(i, it.next().intValue());
                i++;
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("?");
                Summoner s = new Summoner();
                s.setId(rs.getInt(1));
                s.setName(rs.getString(2));
                s.setProfileIconId(rs.getInt(3));
                s.setRevisionDate(rs.getTimestamp(4).getTime());
                s.setSummonerLevel(rs.getInt(5));
                s.setLastUpdated(rs.getTimestamp(6).getTime());
                s.setRsu(rs.getTimestamp(7).getTime());
                s.setLeu(rs.getTimestamp(8).getTime());
                map.put(s.getId(), s);
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
        System.out.println(map);
        return map;
    }

    @Override
    public void updateLeagues(Platform p, Collection<League> leagues) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() throws DatabaseException {
        try {
            conn.rollback();
            conn.close();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public void updateRankedStats(Platform p, Collection<RankedStats> rankedStats) throws DatabaseException {
        if (rankedStats.isEmpty()) {
            return;
        }
        try (PreparedStatement ps = conn.prepareStatement("insert into rankedstats(region, summonerId, modifyDate) values (?,?,?) ON DUPLICATE KEY UPDATE modifyDate=values(modifyDate)");
                PreparedStatement ps2 = conn.prepareStatement("replace into championstats(region, summonerId, championId, totalSessionsPlayed, totalSessionsWon, totalSessionsLost, totalChampionKills, totalDeathsPerSession, totalAssists) values (?,?,?,?,?,?,?,?,?)")) {
            for (RankedStats s : rankedStats) {
                ps.setString(1, p.getRegion());
                ps.setInt(2, (int) s.getSummonerId());
                ps.setLong(3, s.getModifyDate());
                ps.addBatch();

                for (ChampionStats cs : s.getChampions()) {
                    int i=1;
                    ps2.setString(i++, p.getRegion());
                    ps2.setInt(i++, (int) s.getSummonerId());
                    ps2.setInt(i++, cs.getId());
                    ps2.setInt(i++, cs.getStats().getTotalSessionsPlayed());
                    ps2.setInt(i++, cs.getStats().getTotalSessionsWon());
                    ps2.setInt(i++, cs.getStats().getTotalSessionsLost());
                    ps2.setInt(i++, cs.getStats().getTotalChampionKills());
                    ps2.setInt(i++, cs.getStats().getTotalDeathsPerSession());
                    ps2.setInt(i++, cs.getStats().getTotalAssists());
                    ps2.addBatch();
                }

            }
            ps.executeBatch();
            ps2.executeBatch();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    @Override
    public Map<Long, RankedStats> getRankedStats(Platform p, Collection<Long> ids) throws DatabaseException {
        if(ids.isEmpty()){
            return new HashMap<>();
        }
        System.out.println(ids.size());
        StringBuilder idps = new StringBuilder();
        for (int i = 0; i < ids.size() - 1; i++) {
            idps.append("?,");
        }
        idps.append("?");
        Map<Long, RankedStats> map = new HashMap<>();
        try (PreparedStatement ps = conn.prepareStatement("select summonerId, modifyDate from rankedstats where region=? and summonerId in (" + idps + ")")) {
            ps.setString(1, p.getRegion());
            int i = 2;
            Iterator<Long> it = ids.iterator();
            while (it.hasNext()) {
                ps.setInt(i++, it.next().intValue());
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RankedStats rso = new RankedStats();
                rso.setSummonerId(rs.getInt(1));
                rso.setModifyDate(rs.getLong(2));
                try (PreparedStatement ps2 = conn.prepareStatement("select championId, totalSessionsPlayed, totalSessionsWon, totalSessionsLost, totalChampionKills, totalDeathsPerSession, totalAssists from championstats where region=? and summonerId=?")) {
                    ps2.setString(1, p.getRegion());
                    ps2.setInt(2, (int) rso.getSummonerId());
                    ResultSet rs2 = ps2.executeQuery();
                    List<ChampionStats> csl = new ArrayList<>();
                    while(rs2.next()){
                        ChampionStats cs = new ChampionStats();
                        cs.setId(rs2.getInt(1));
                        AggregatedStats as = new AggregatedStats();
                        as.setTotalSessionsPlayed(rs2.getInt(2));
                        as.setTotalSessionsWon(rs2.getInt(3));
                        as.setTotalSessionsLost(rs2.getInt(4));
                        as.setTotalChampionKills(rs2.getInt(5));
                        as.setTotalDeathsPerSession(rs2.getInt(6));
                        as.setTotalAssists(rs2.getInt(7));
                        cs.setStats(as);
                        csl.add(cs);
                    }
                    if(!csl.isEmpty()){
                        rso.setChampions(csl);
                    }
                }
                map.put(rso.getSummonerId(), rso);
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
        return map;
    }

    @Override
    public Map<Long, List<DBLeagueEntry>> getLeagueEntries(Platform p, Collection<Long> ids) throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
