package fi.kivibot.summonerbackend;

import com.google.common.collect.ImmutableMap;
import fi.kivibot.riotapi.constant.Region;
import fi.kivibot.riotapi.internal.SummonerAPI;
import fi.kivibot.riotapi.structures.summoner.SummonerDto;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 *
 * @author Nicklas
 */
public class UpdateSummoners implements Callable<Map<Long, DBSummoner>> {

    private final long UPDATE_THRESHOLD = 5000; //5s

    private final SummonerAPI summonerAPI;
    private final Cache<Long, DBSummoner> summonerCache;
    private final DBHandler dbh;
    private final Region region;
    private final Collection<Long> ids;

    public UpdateSummoners(SummonerAPI summonerAPI, Cache<Long, DBSummoner> summonerCache, DBHandler dbh, Region region, Collection<Long> ids) {
        this.summonerAPI = summonerAPI;
        this.summonerCache = summonerCache;
        this.dbh = dbh;
        this.region = region;
        this.ids = ids.stream().collect(Collectors.toList());
    }

    @Override
    public Map<Long, DBSummoner> call() throws Exception {
        Map<Long, DBSummoner> sums = summonerCache.getAll(ids);
        List<Long> update = new ArrayList<>();
        Map<Long, DBSummoner> output = new HashMap<>();
        for (DBSummoner sum : sums.values()) {
            if (sum.getLastUpdated() < System.currentTimeMillis() - UPDATE_THRESHOLD) {
                update.add(sum.getId());
            } else {
                output.put(sum.getId(), sum);
            }
            ids.remove(sum.getId());
        }
        update.addAll(ids);
        if (!update.isEmpty()) {
            Map<String, SummonerDto> updated = summonerAPI.getSummonerById(region, update);
            Map<Long, DBSummoner> updated2 = new HashMap<>();
            Map<Long, DBSummoner> insert = new HashMap<>();
            for (SummonerDto sum : updated.values()) {
                DBSummoner dbsum = new DBSummoner(System.currentTimeMillis(), System.currentTimeMillis(), sum);
                summonerCache.put(dbsum.getId(), dbsum);
                if (ids.contains(sum.getId())) {
                    insert.put(dbsum.getId(), dbsum);
                } else {
                    updated2.put(dbsum.getId(), dbsum);
                }
            }
            try {
                if (!insert.isEmpty() || !updated.isEmpty()) {
                    dbh.transaction((tdbh) -> {
                        if (!insert.isEmpty()) {
                            tdbh.insertSummoners(region, insert.values());
                        }
                        if (!updated2.isEmpty()) {
                            tdbh.updateSummoners(region, updated2.values());
                        }
                    });
                }
            } catch (IOException | SQLException e) {
                //TODO: handle
                System.out.println(e);
            }

            output.putAll(updated2);
            output.putAll(insert);
        }
        return output;
    }

}
