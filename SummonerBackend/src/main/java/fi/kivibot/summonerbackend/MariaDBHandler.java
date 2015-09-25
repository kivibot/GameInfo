package fi.kivibot.summonerbackend;

import fi.kivibot.riotapi.constant.Region;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Nicklas
 */
public class MariaDBHandler implements DBHandler {

    private final Connection connection;

    public MariaDBHandler(Connection connection) {
        this.connection = connection;
        connectionPool = null;
    }

    private final ConnectionPool connectionPool;

    public MariaDBHandler(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
        connection = null;
    }

    private Connection getConnection() {
        if (connection != null) {
            return connection;
        }
        return connectionPool.getConnection();
    }

    @Override
    public Map<Long, DBSummoner> getSummoners(Region region, Collection<Long> ids) throws IOException, SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("select id, name, profileIconId, revisionDate, summonerLevel, exists from summoner where region=? and id=?");
            for (long id : ids) {
                ps.setString(1, region.getName());
                ps.setInt(2, (int) id);
                ps.addBatch();
            }
            ResultSet rs = ps.executeQuery();
            Map<Long, DBSummoner> map = new HashMap<>();
            while (rs.next()) {
                long id = rs.getInt("id");
                map.put(id, new DBSummoner(0, 0, rs.getBoolean("exists"), id,
                        rs.getString("name"), rs.getInt("profileIconId"),
                        rs.getLong("revisionDate"), rs.getInt("summonerLevel")));
            }
            return map;
        }
    }

    @Override
    public void insertSummoners(Region region, Collection<DBSummoner> summoners) throws IOException, SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("insert into summoner (region, id, name, profileIconId, revisionDate, summonerLevel, exists_) values (?, ?, ?, ?, ?, ?, ?)");
            for (DBSummoner summoner : summoners) {
                int i=0;
                ps.setString(++i, region.getName());
                ps.setInt(++i, (int) summoner.getId());
                ps.setString(++i, summoner.getName());
                ps.setInt(++i, summoner.getProfileIconId());
                ps.setLong(++i, summoner.getRevisionDate());
                ps.setInt(++i, (int) summoner.getSummonerLevel());
                ps.setBoolean(++i, summoner.exists());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    @Override
    public void updateSummoners(Region region, Collection<DBSummoner> summoners) throws IOException, SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("update summoner set exists_=?,name=?,profileIconId=?,revisionDate=?,summonerLevel=? where region=? and id=? ");
            for (DBSummoner summoner : summoners) {
                int i=0;
                ps.setBoolean(++i, summoner.exists());
                ps.setString(++i, summoner.getName());
                ps.setInt(++i, summoner.getProfileIconId());
                ps.setLong(++i, summoner.getRevisionDate());
                ps.setInt(++i, (int) summoner.getSummonerLevel());
                ps.setString(++i, region.getName());
                ps.setInt(++i, (int) summoner.getId());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    @Override
    public Map<String, DBSummoner> getSummonersByName(Region region, Collection<String> names) throws IOException, SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("select id, name, profileIconId, revisionDate, summonerLevel, exists from summoner where region=? and name=?");
            for (String name : names) {
                ps.setString(1, region.getName());
                ps.setString(2, name);
                ps.addBatch();
            }
            ResultSet rs = ps.executeQuery();
            Map<String, DBSummoner> map = new HashMap<>();
            while (rs.next()) {
                String name = rs.getString("name");
                map.put(name, new DBSummoner(0, 0, rs.getBoolean("exists"), rs.getInt("id"),
                        name, rs.getInt("profileIconId"),
                        rs.getLong("revisionDate"), rs.getInt("summonerLevel")));
            }
            return map;
        }
    }

    @Override
    public void transaction(TransactionHandler transaction) throws IOException, SQLException {
        Connection conn = getConnection();
        try {
            if (connection == null) {
                conn.setAutoCommit(false);
                transaction.onTransaction(new MariaDBHandler(connection));
                conn.commit();
            } else {
                transaction.onTransaction(this);
            }
        } catch (Exception e) { //Must be java.lang.Exception
            if (connection == null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (connection == null) {
                conn.close();
            }
        }
    }

}
