package fi.kivibot.gameinfoback;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import fi.kivibot.gameinfoback.api.old.Platform;
import fi.kivibot.gameinfoback.api.old.SoloLeagueEntry;
import fi.kivibot.gameinfoback.api.old.structures.ChampionStats;
import fi.kivibot.gameinfoback.api.old.structures.LeagueEntry;
import fi.kivibot.gameinfoback.api.old.structures.MiniSeries;
import fi.kivibot.gameinfoback.api.old.structures.PlayerStatsSummary;
import fi.kivibot.gameinfoback.api.old.structures.RankedStats;
import fi.kivibot.gameinfoback.api.old.structures.RunePage;
import fi.kivibot.gameinfoback.api.old.structures.RunePages;
import fi.kivibot.gameinfoback.api.old.structures.RuneSlot;
import fi.kivibot.gameinfoback.api.old.structures.Summoner;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicklas
 */
public class MariaDBHandler implements DatabaseHandler {

    private final ComboPooledDataSource ds;

    public MariaDBHandler() throws SQLException, PropertyVetoException {
        ds = new ComboPooledDataSource();
        ds.setDriverClass("org.mariadb.jdbc.Driver");
        ds.setJdbcUrl("jdbc:mariadb://127.0.0.1/gameinfo");
        ds.setUser("root");
        ds.setPassword("qwerty");
    }

    @Override
    public void updateSummoner(Platform p, Summoner s) {
        try (Connection conn = ds.getConnection()) {
            System.out.println("us");
            try (PreparedStatement ps = conn.prepareStatement("insert into summoner values (?,?,?,?,?,?, default) ON DUPLICATE KEY UPDATE name=values(name), profileIconId=values(profileIconId), revisionDate=values(revisionDate), summonerLevel=values(summonerLevel);")) {
                ps.setInt(1, (int) s.getId());
                if (s.getId() < 0) {
                    System.out.println("SUMMONER ID <0 DETECTED!");
                }
                ps.setString(2, s.getName());
                ps.setInt(3, s.getProfileIconId());
                ps.setLong(4, s.getRevisionDate());
                ps.setInt(5, (int) s.getSummonerLevel());
                ps.setString(6, p.getRegion());
                ps.execute();
            } catch (SQLException ex) {
                Logger.getLogger(MariaDBHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MariaDBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updatePlayerStatsSummary(Platform p, PlayerStatsSummary pss, Summoner s) {
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("insert into playerstatssummary values (?,?,?,?,?,?) ON DUPLICATE KEY UPDATE losses=values(losses), wins=values(wins), modifyDate=values(modifyDate);")) {
                ps.setInt(1, pss.getLosses());
                ps.setInt(2, pss.getWins());
                ps.setLong(3, pss.getModifyDate());
                ps.setString(4, pss.getPlayerStatSummaryType());
                ps.setInt(5, (int) s.getId());
                ps.setString(6, p.getRegion());
                ps.execute();
            } catch (SQLException ex) {
                Logger.getLogger(MariaDBHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MariaDBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateRankedStats(Platform p, RankedStats rs, Summoner s) {
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("insert into rankedstats values(?,?,?) ON DUPLICATE KEY UPDATE modifyDate=values(modifyDate);")) {
                ps.setLong(1, rs.getModifyDate());
                ps.setInt(2, (int) s.getId());
                ps.setString(3, p.getRegion());
                ps.execute();
            } catch (SQLException ex) {
                Logger.getLogger(MariaDBHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            try (PreparedStatement ps = conn.prepareStatement("insert into championstats values (?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE totalSessionsPlayed=values(totalSessionsPlayed), totalSessionsWon=values(totalSessionsWon), totalSessionsLost=values(totalSessionsLost), totalChampionKills=values(totalChampionKills), totalDeathsPerSession=values(totalDeathsPerSession), totalAssists=values(totalAssists);")) {
                for (ChampionStats cs : rs.getChampions()) {
                    ps.setInt(1, (int) s.getId());
                    ps.setString(2, p.getRegion());
                    ps.setInt(3, cs.getId());
                    ps.setInt(4, cs.getTotalSessionsPlayed());
                    ps.setInt(5, cs.getTotalSessionsWon());
                    ps.setInt(6, cs.getTotalSessionsLost());
                    ps.setInt(7, cs.getTotalChampionKills());
                    ps.setInt(8, cs.getTotalDeathsPerSession());
                    ps.setInt(9, cs.getTotalAssists());
                    ps.addBatch();
                }
                ps.executeBatch();
            } catch (SQLException ex) {
                Logger.getLogger(MariaDBHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MariaDBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Summoner> getUpdated(Platform p, List<Long> summoners) {
        try (Connection conn = ds.getConnection()) {
            List<Summoner> out = new ArrayList<>();
            if (summoners.isEmpty()) {
                return out;
            }
            StringBuilder sb = new StringBuilder("select summonerId, name, profileIconId, revisionDate, summonerLevel from summoner where summonerId in (");
            for (int i = 0; i < summoners.size() - 1; i++) {
                sb.append("?" + ", ");
            }
            sb.append("?) and current_timestamp()-updated < 1200;");
            try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
                for (int i = 0; i < summoners.size(); i++) {
                    ps.setInt(i + 1, (int) (long) summoners.get(i));
                }
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    out.add(new Summoner(rs.getInt("summonerId"), rs.getString("name"), rs.getInt("profileIconId"), rs.getLong("revisionDate"), rs.getInt("summonerLevel")));
                }
            } catch (SQLException ex) {
                Logger.getLogger(MariaDBHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(out);
            return out;
        } catch (SQLException ex) {
            Logger.getLogger(MariaDBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Summoner> getSummoners(Platform p, List<Long> ids) {
        try (Connection conn = ds.getConnection()) {
            List<Summoner> out = new ArrayList<>();
            if (ids.isEmpty()) {
                return out;
            }
            StringBuilder sb = new StringBuilder("select * from summoner where summonerId in (");
            for (int i = 0; i < ids.size() - 1; i++) {
                sb.append("?" + ", ");
            }
            sb.append("?);");
            try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
                for (int i = 0; i < ids.size(); i++) {
                    ps.setInt(i + 1, (int) (long) ids.get(i));
                }
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    out.add(new Summoner(rs.getInt("summonerId"), rs.getString("name"), rs.getInt("profileIconId"), rs.getLong("revisionDate"), rs.getInt("summonerLevel")));
                }
            } catch (SQLException ex) {
                Logger.getLogger(MariaDBHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(out);
            return out;
        } catch (SQLException ex) {
            Logger.getLogger(MariaDBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void updateRunePages(Platform p, RunePages rps, Summoner s) {
        try (Connection conn = ds.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement("insert into runepage values(?,?,?,?,?) ON DUPLICATE KEY UPDATE name=values(name);")) {
                for (RunePage rp : rps.getRunePages()) {
                    ps.setInt(1, (int) s.getId());
                    ps.setString(2, p.getRegion());
                    ps.setInt(3, (int) rp.getId());
                    ps.setString(4, rp.getName());
                    ps.setBoolean(5, rp.isCurrent());
                    ps.addBatch();
                }
                ps.executeBatch();
            } catch (SQLException ex) {
                Logger.getLogger(MariaDBHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            try (PreparedStatement ps = conn.prepareStatement("insert into runeslot values(?,?,?,?) ON DUPLICATE KEY UPDATE runeId=values(runeId);")) {               
                for (RunePage rp : rps.getRunePages()) {
                    for (RuneSlot rs : rp.getSlots()) {
                        ps.setString(1, p.getRegion());
                        ps.setInt(2, (int) rp.getId());
                        ps.setInt(3, rs.getRuneId());
                        ps.setInt(4, rs.getRuneSlotId());
                        ps.addBatch();
                    }
                }
                ps.executeBatch();
            } catch (SQLException ex) {
                Logger.getLogger(MariaDBHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(MariaDBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public RunePages getRunePages(Platform p, long sid) {
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("select id, name, current from runepage where runepage.summonerId=? and runepage.region=?;")) {
                ps.setInt(1, (int) sid);
                ps.setString(2, p.getRegion());
                ResultSet rs = ps.executeQuery();
                Set<RunePage> rps = new HashSet<>();
                while (rs.next()) {
                    try (PreparedStatement ps2 = conn.prepareStatement("select runeId, runeSlotId from runeslot where runeslot.pageId=? and runeslot.region=?;")) {
                        ps2.setInt(1, rs.getInt("id"));
                        ps2.setString(2, p.getRegion());
                        ResultSet rs2 = ps2.executeQuery();
                        Set<RuneSlot> rss = new HashSet<>();
                        while (rs2.next()) {
                            rss.add(new RuneSlot(rs2.getInt("runeId"), rs2.getInt("runeSlotId")));
                        }
                        rps.add(new RunePage(rs.getBoolean("current"), rs.getInt("id"), rs.getString("name"), rss));
                    }
                }
                return new RunePages(rps, sid);
            } catch (SQLException ex) {
                Logger.getLogger(MariaDBHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MariaDBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public RunePage getActiveRunepage(Platform p, long sid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PlayerStatsSummary getPlayerStatsSummary(Platform p, long sid, String type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PlayerStatsSummary> getAllPlayerStatsSummaries(Platform p, long sid) {
        try (Connection conn = ds.getConnection()) {
            List<PlayerStatsSummary> pssl = new ArrayList<>();
            try (PreparedStatement ps = conn.prepareStatement("select losses, wins, modifyDate, playerStatSummaryType from playerstatssummary where summonerId=? and region=?;")) {
                ps.setInt(1, (int) sid);
                ps.setString(2, p.getRegion());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    pssl.add(new PlayerStatsSummary(rs.getInt("losses"), rs.getLong("modifyDate"), rs.getString("playerStatSummaryType"), rs.getInt("wins")));
                }
            } catch (SQLException ex) {
                Logger.getLogger(MariaDBHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            return pssl;
        } catch (SQLException ex) {
            Logger.getLogger(MariaDBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ChampionStats getChampionStats(Platform p, long sid, long cid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateSoloLeagueEntry(Platform p, String tier, LeagueEntry solo, Summoner s) {
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("insert into sololeagueentry values(?,?,?,?,?,?,?,?,?,?,?,?) "
                    + "ON DUPLICATE KEY UPDATE tier=values(tier), division=values(division), freshBlood=values(freshBlood),"
                    + "hotStreak=values(hotStreak), inactive=values(inactive), veteran=values(veteran), leaguePoints=values(leaguePoints), "
                    + "losses=values(losses), wins=values(wins), miniSeries=values(miniSeries);")) {
                ps.setString(1, tier);
                ps.setString(2, solo.getDivision());
                ps.setBoolean(3, solo.isFreshBlood());
                ps.setBoolean(4, solo.isHotStreak());
                ps.setBoolean(5, solo.isInactive());
                ps.setBoolean(6, solo.isVeteran());
                ps.setInt(7, solo.getLeaguePoints());
                ps.setInt(8, solo.getLosses());
                ps.setInt(9, solo.getWins());
                ps.setInt(10, (int) s.getId());
                ps.setString(11, p.getRegion());
                ps.setString(12, solo.getMiniSeries() != null ? solo.getMiniSeries().getProgress() : null);
                ps.execute();
            } catch (SQLException ex) {
                Logger.getLogger(MariaDBHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MariaDBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public RankedStats getRankedStats(Platform p, long id) {
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("select modifyDate, summonerId from rankedstats where rankedstats.summonerId=? and rankedstats.region=?;")) {
                ps.setInt(1, (int) id);
                ps.setString(2, p.getRegion());
                ResultSet rs = ps.executeQuery();
                List<ChampionStats> csl = new ArrayList<>();
                if (rs.next()) {
                    try (PreparedStatement ps2 = conn.prepareStatement("select championId, totalSessionsPlayed, totalSessionsWon, totalSessionsLost,"
                            + "totalChampionKills, totalDeathsPerSession, totalAssists from championstats where championstats.summonerId=? and championstats.region=?;")) {
                        ps2.setInt(1, (int) id);
                        ps2.setString(2, p.getRegion());
                        ResultSet rs2 = ps2.executeQuery();
                        while (rs2.next()) {
                            csl.add(new ChampionStats(rs2.getInt("championId"), rs2.getInt("totalSessionsPlayed"), rs2.getInt("totalSessionsWon"),
                                    rs2.getInt("totalSessionsLost"), rs2.getInt("totalChampionKills"), rs2.getInt("totalDeathsPerSession"),
                                    rs2.getInt("totalAssists")));
                        }
                    }
                    return new RankedStats(csl, rs.getLong("modifyDate"), id);
                }
            } catch (SQLException ex) {
                Logger.getLogger(MariaDBHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MariaDBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public SoloLeagueEntry getSoloLeagueEntry(Platform p, long id) {
        try (Connection conn = ds.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("select tier, division, freshBlood, hotStreak, inactive, veteran, leaguePoints, losses, summoner.summonerId, name, wins, miniSeries from sololeagueentry "
                    + "join summoner on summoner.summonerId = sololeagueentry.summonerId and summoner.region = sololeagueentry.region "
                    + "where sololeagueentry.summonerId=? and sololeagueentry.region=?")) {
                ps.setInt(1, (int) id);
                ps.setString(2, p.getRegion());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String msp = rs.getString("miniSeries");
                    MiniSeries ms = null;
                    if (msp != null) {
                        int w = 0, l = 0;
                        for (char c : msp.toCharArray()) {
                            if (c == 'W') {
                                w++;
                            } else if (c == 'L') {
                                l++;
                            }
                        }
                        ms = new MiniSeries(l, msp, msp.length(), w);
                    }
                    return new SoloLeagueEntry(rs.getString("tier"), rs.getString("division"),
                            rs.getBoolean("freshBlood"), rs.getBoolean("hotStreak"),
                            rs.getBoolean("inactive"), rs.getBoolean("veteran"),
                            rs.getInt("leaguePoints"), rs.getInt("losses"),
                            ms, rs.getInt("summoner.summonerId") + "", rs.getString("name"), rs.getInt("wins"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(MariaDBHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MariaDBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
