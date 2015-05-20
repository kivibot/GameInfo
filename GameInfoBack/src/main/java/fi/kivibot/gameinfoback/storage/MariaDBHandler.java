package fi.kivibot.gameinfoback.storage;

import fi.kivibot.gameinfoback.storage.DatabaseException;
import fi.kivibot.gameinfoback.storage.DatabaseHandler;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import fi.kivibot.gameinfoback.DeprecatedMariaDBHandler;
import fi.kivibot.gameinfoback.api.Platform;
import fi.kivibot.gameinfoback.api.struct.league.League;
import fi.kivibot.gameinfoback.api.struct.summoner.Summoner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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
        try (PreparedStatement ps = conn.prepareStatement("insert into summoner(region, summonerId, name, profileIconId, revisionDate, summonerLevel, updated) values (?,?,?,?,?,?, default) ON DUPLICATE KEY UPDATE name=values(name), profileIconId=values(profileIconId), revisionDate=values(revisionDate), summonerLevel=values(summonerLevel), updated=values(updated);")) {
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
        try (PreparedStatement ps = conn.prepareStatement("select summonerId, name, profileIconId, revisionDate, summonerLevel, updated from summoner where region=? and summonerId in (" + idps + ")")) {
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

}
