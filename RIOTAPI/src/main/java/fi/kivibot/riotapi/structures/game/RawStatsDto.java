package fi.kivibot.riotapi.structures.game;

import java.util.Objects;

public class RawStatsDto {

    private Integer assists;
    private Integer barracksKilled;
    private Integer championsKilled;
    private Integer combatPlayerScore;
    private Integer consumablesPurchased;
    private Integer damageDealtPlayer;
    private Integer doubleKills;
    private Integer firstBlood;
    private Integer gold;
    private Integer goldEarned;
    private Integer goldSpent;
    private Integer item0;
    private Integer item1;
    private Integer item2;
    private Integer item3;
    private Integer item4;
    private Integer item5;
    private Integer item6;
    private Integer itemsPurchased;
    private Integer killingSprees;
    private Integer largestCriticalStrike;
    private Integer largestKillingSpree;
    private Integer largestMultiKill;
    private Integer legendaryItemsCreated;
    private Integer level;
    private Integer magicDamageDealtPlayer;
    private Integer magicDamageDealtToChampions;
    private Integer magicDamageTaken;
    private Integer minionsDenied;
    private Integer minionsKilled;
    private Integer neutralMinionsKilled;
    private Integer neutralMinionsKilledEnemyJungle;
    private Integer neutralMinionsKilledYourJungle;
    private Boolean nexusKilled;
    private Integer nodeCapture;
    private Integer nodeCaptureAssist;
    private Integer nodeNeutralize;
    private Integer nodeNeutralizeAssist;
    private Integer numDeaths;
    private Integer numItemsBought;
    private Integer objectivePlayerScore;
    private Integer pentaKills;
    private Integer physicalDamageDealtPlayer;
    private Integer physicalDamageDealtToChampions;
    private Integer physicalDamageTaken;
    private Integer playerPosition;
    private Integer playerRole;
    private Integer quadraKills;
    private Integer sightWardsBought;
    private Integer spell1Cast;
    private Integer spell2Cast;
    private Integer spell3Cast;
    private Integer spell4Cast;
    private Integer summonSpell1Cast;
    private Integer summonSpell2Cast;
    private Integer superMonsterKilled;
    private Integer team;
    private Integer teamObjective;
    private Integer timePlayed;
    private Integer totalDamageDealt;
    private Integer totalDamageDealtToChampions;
    private Integer totalDamageTaken;
    private Integer totalHeal;
    private Integer totalPlayerScore;
    private Integer totalScoreRank;
    private Integer totalTimeCrowdControlDealt;
    private Integer totalUnitsHealed;
    private Integer tripleKills;
    private Integer trueDamageDealtPlayer;
    private Integer trueDamageDealtToChampions;
    private Integer trueDamageTaken;
    private Integer turretsKilled;
    private Integer unrealKills;
    private Integer victoryPointTotal;
    private Integer visionWardsBought;
    private Integer wardKilled;
    private Integer wardPlaced;
    private Boolean win;

    public RawStatsDto(Integer assists, Integer barracksKilled, Integer championsKilled, Integer combatPlayerScore, Integer consumablesPurchased, Integer damageDealtPlayer, Integer doubleKills, Integer firstBlood, Integer gold, Integer goldEarned, Integer goldSpent, Integer item0, Integer item1, Integer item2, Integer item3, Integer item4, Integer item5, Integer item6, Integer itemsPurchased, Integer killingSprees, Integer largestCriticalStrike, Integer largestKillingSpree, Integer largestMultiKill, Integer legendaryItemsCreated, Integer level, Integer magicDamageDealtPlayer, Integer magicDamageDealtToChampions, Integer magicDamageTaken, Integer minionsDenied, Integer minionsKilled, Integer neutralMinionsKilled, Integer neutralMinionsKilledEnemyJungle, Integer neutralMinionsKilledYourJungle, Boolean nexusKilled, Integer nodeCapture, Integer nodeCaptureAssist, Integer nodeNeutralize, Integer nodeNeutralizeAssist, Integer numDeaths, Integer numItemsBought, Integer objectivePlayerScore, Integer pentaKills, Integer physicalDamageDealtPlayer, Integer physicalDamageDealtToChampions, Integer physicalDamageTaken, Integer playerPosition, Integer playerRole, Integer quadraKills, Integer sightWardsBought, Integer spell1Cast, Integer spell2Cast, Integer spell3Cast, Integer spell4Cast, Integer summonSpell1Cast, Integer summonSpell2Cast, Integer superMonsterKilled, Integer team, Integer teamObjective, Integer timePlayed, Integer totalDamageDealt, Integer totalDamageDealtToChampions, Integer totalDamageTaken, Integer totalHeal, Integer totalPlayerScore, Integer totalScoreRank, Integer totalTimeCrowdControlDealt, Integer totalUnitsHealed, Integer tripleKills, Integer trueDamageDealtPlayer, Integer trueDamageDealtToChampions, Integer trueDamageTaken, Integer turretsKilled, Integer unrealKills, Integer victoryPointTotal, Integer visionWardsBought, Integer wardKilled, Integer wardPlaced, Boolean win) {
        this.assists = assists;
        this.barracksKilled = barracksKilled;
        this.championsKilled = championsKilled;
        this.combatPlayerScore = combatPlayerScore;
        this.consumablesPurchased = consumablesPurchased;
        this.damageDealtPlayer = damageDealtPlayer;
        this.doubleKills = doubleKills;
        this.firstBlood = firstBlood;
        this.gold = gold;
        this.goldEarned = goldEarned;
        this.goldSpent = goldSpent;
        this.item0 = item0;
        this.item1 = item1;
        this.item2 = item2;
        this.item3 = item3;
        this.item4 = item4;
        this.item5 = item5;
        this.item6 = item6;
        this.itemsPurchased = itemsPurchased;
        this.killingSprees = killingSprees;
        this.largestCriticalStrike = largestCriticalStrike;
        this.largestKillingSpree = largestKillingSpree;
        this.largestMultiKill = largestMultiKill;
        this.legendaryItemsCreated = legendaryItemsCreated;
        this.level = level;
        this.magicDamageDealtPlayer = magicDamageDealtPlayer;
        this.magicDamageDealtToChampions = magicDamageDealtToChampions;
        this.magicDamageTaken = magicDamageTaken;
        this.minionsDenied = minionsDenied;
        this.minionsKilled = minionsKilled;
        this.neutralMinionsKilled = neutralMinionsKilled;
        this.neutralMinionsKilledEnemyJungle = neutralMinionsKilledEnemyJungle;
        this.neutralMinionsKilledYourJungle = neutralMinionsKilledYourJungle;
        this.nexusKilled = nexusKilled;
        this.nodeCapture = nodeCapture;
        this.nodeCaptureAssist = nodeCaptureAssist;
        this.nodeNeutralize = nodeNeutralize;
        this.nodeNeutralizeAssist = nodeNeutralizeAssist;
        this.numDeaths = numDeaths;
        this.numItemsBought = numItemsBought;
        this.objectivePlayerScore = objectivePlayerScore;
        this.pentaKills = pentaKills;
        this.physicalDamageDealtPlayer = physicalDamageDealtPlayer;
        this.physicalDamageDealtToChampions = physicalDamageDealtToChampions;
        this.physicalDamageTaken = physicalDamageTaken;
        this.playerPosition = playerPosition;
        this.playerRole = playerRole;
        this.quadraKills = quadraKills;
        this.sightWardsBought = sightWardsBought;
        this.spell1Cast = spell1Cast;
        this.spell2Cast = spell2Cast;
        this.spell3Cast = spell3Cast;
        this.spell4Cast = spell4Cast;
        this.summonSpell1Cast = summonSpell1Cast;
        this.summonSpell2Cast = summonSpell2Cast;
        this.superMonsterKilled = superMonsterKilled;
        this.team = team;
        this.teamObjective = teamObjective;
        this.timePlayed = timePlayed;
        this.totalDamageDealt = totalDamageDealt;
        this.totalDamageDealtToChampions = totalDamageDealtToChampions;
        this.totalDamageTaken = totalDamageTaken;
        this.totalHeal = totalHeal;
        this.totalPlayerScore = totalPlayerScore;
        this.totalScoreRank = totalScoreRank;
        this.totalTimeCrowdControlDealt = totalTimeCrowdControlDealt;
        this.totalUnitsHealed = totalUnitsHealed;
        this.tripleKills = tripleKills;
        this.trueDamageDealtPlayer = trueDamageDealtPlayer;
        this.trueDamageDealtToChampions = trueDamageDealtToChampions;
        this.trueDamageTaken = trueDamageTaken;
        this.turretsKilled = turretsKilled;
        this.unrealKills = unrealKills;
        this.victoryPointTotal = victoryPointTotal;
        this.visionWardsBought = visionWardsBought;
        this.wardKilled = wardKilled;
        this.wardPlaced = wardPlaced;
        this.win = win;
    }

    public Integer getAssists() {
        return assists;
    }

    public Integer getBarracksKilled() {
        return barracksKilled;
    }

    public Integer getChampionsKilled() {
        return championsKilled;
    }

    public Integer getCombatPlayerScore() {
        return combatPlayerScore;
    }

    public Integer getConsumablesPurchased() {
        return consumablesPurchased;
    }

    public Integer getDamageDealtPlayer() {
        return damageDealtPlayer;
    }

    public Integer getDoubleKills() {
        return doubleKills;
    }

    public Integer getFirstBlood() {
        return firstBlood;
    }

    public Integer getGold() {
        return gold;
    }

    public Integer getGoldEarned() {
        return goldEarned;
    }

    public Integer getGoldSpent() {
        return goldSpent;
    }

    public Integer getItem0() {
        return item0;
    }

    public Integer getItem1() {
        return item1;
    }

    public Integer getItem2() {
        return item2;
    }

    public Integer getItem3() {
        return item3;
    }

    public Integer getItem4() {
        return item4;
    }

    public Integer getItem5() {
        return item5;
    }

    public Integer getItem6() {
        return item6;
    }

    public Integer getItemsPurchased() {
        return itemsPurchased;
    }

    public Integer getKillingSprees() {
        return killingSprees;
    }

    public Integer getLargestCriticalStrike() {
        return largestCriticalStrike;
    }

    public Integer getLargestKillingSpree() {
        return largestKillingSpree;
    }

    public Integer getLargestMultiKill() {
        return largestMultiKill;
    }

    public Integer getLegendaryItemsCreated() {
        return legendaryItemsCreated;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getMagicDamageDealtPlayer() {
        return magicDamageDealtPlayer;
    }

    public Integer getMagicDamageDealtToChampions() {
        return magicDamageDealtToChampions;
    }

    public Integer getMagicDamageTaken() {
        return magicDamageTaken;
    }

    public Integer getMinionsDenied() {
        return minionsDenied;
    }

    public Integer getMinionsKilled() {
        return minionsKilled;
    }

    public Integer getNeutralMinionsKilled() {
        return neutralMinionsKilled;
    }

    public Integer getNeutralMinionsKilledEnemyJungle() {
        return neutralMinionsKilledEnemyJungle;
    }

    public Integer getNeutralMinionsKilledYourJungle() {
        return neutralMinionsKilledYourJungle;
    }

    public Boolean getNexusKilled() {
        return nexusKilled;
    }

    public Integer getNodeCapture() {
        return nodeCapture;
    }

    public Integer getNodeCaptureAssist() {
        return nodeCaptureAssist;
    }

    public Integer getNodeNeutralize() {
        return nodeNeutralize;
    }

    public Integer getNodeNeutralizeAssist() {
        return nodeNeutralizeAssist;
    }

    public Integer getNumDeaths() {
        return numDeaths;
    }

    public Integer getNumItemsBought() {
        return numItemsBought;
    }

    public Integer getObjectivePlayerScore() {
        return objectivePlayerScore;
    }

    public Integer getPentaKills() {
        return pentaKills;
    }

    public Integer getPhysicalDamageDealtPlayer() {
        return physicalDamageDealtPlayer;
    }

    public Integer getPhysicalDamageDealtToChampions() {
        return physicalDamageDealtToChampions;
    }

    public Integer getPhysicalDamageTaken() {
        return physicalDamageTaken;
    }

    public Integer getPlayerPosition() {
        return playerPosition;
    }

    public Integer getPlayerRole() {
        return playerRole;
    }

    public Integer getQuadraKills() {
        return quadraKills;
    }

    public Integer getSightWardsBought() {
        return sightWardsBought;
    }

    public Integer getSpell1Cast() {
        return spell1Cast;
    }

    public Integer getSpell2Cast() {
        return spell2Cast;
    }

    public Integer getSpell3Cast() {
        return spell3Cast;
    }

    public Integer getSpell4Cast() {
        return spell4Cast;
    }

    public Integer getSummonSpell1Cast() {
        return summonSpell1Cast;
    }

    public Integer getSummonSpell2Cast() {
        return summonSpell2Cast;
    }

    public Integer getSuperMonsterKilled() {
        return superMonsterKilled;
    }

    public Integer getTeam() {
        return team;
    }

    public Integer getTeamObjective() {
        return teamObjective;
    }

    public Integer getTimePlayed() {
        return timePlayed;
    }

    public Integer getTotalDamageDealt() {
        return totalDamageDealt;
    }

    public Integer getTotalDamageDealtToChampions() {
        return totalDamageDealtToChampions;
    }

    public Integer getTotalDamageTaken() {
        return totalDamageTaken;
    }

    public Integer getTotalHeal() {
        return totalHeal;
    }

    public Integer getTotalPlayerScore() {
        return totalPlayerScore;
    }

    public Integer getTotalScoreRank() {
        return totalScoreRank;
    }

    public Integer getTotalTimeCrowdControlDealt() {
        return totalTimeCrowdControlDealt;
    }

    public Integer getTotalUnitsHealed() {
        return totalUnitsHealed;
    }

    public Integer getTripleKills() {
        return tripleKills;
    }

    public Integer getTrueDamageDealtPlayer() {
        return trueDamageDealtPlayer;
    }

    public Integer getTrueDamageDealtToChampions() {
        return trueDamageDealtToChampions;
    }

    public Integer getTrueDamageTaken() {
        return trueDamageTaken;
    }

    public Integer getTurretsKilled() {
        return turretsKilled;
    }

    public Integer getUnrealKills() {
        return unrealKills;
    }

    public Integer getVictoryPointTotal() {
        return victoryPointTotal;
    }

    public Integer getVisionWardsBought() {
        return visionWardsBought;
    }

    public Integer getWardKilled() {
        return wardKilled;
    }

    public Integer getWardPlaced() {
        return wardPlaced;
    }

    public Boolean getWin() {
        return win;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.assists);
        hash = 17 * hash + Objects.hashCode(this.barracksKilled);
        hash = 17 * hash + Objects.hashCode(this.championsKilled);
        hash = 17 * hash + Objects.hashCode(this.combatPlayerScore);
        hash = 17 * hash + Objects.hashCode(this.consumablesPurchased);
        hash = 17 * hash + Objects.hashCode(this.damageDealtPlayer);
        hash = 17 * hash + Objects.hashCode(this.doubleKills);
        hash = 17 * hash + Objects.hashCode(this.firstBlood);
        hash = 17 * hash + Objects.hashCode(this.gold);
        hash = 17 * hash + Objects.hashCode(this.goldEarned);
        hash = 17 * hash + Objects.hashCode(this.goldSpent);
        hash = 17 * hash + Objects.hashCode(this.item0);
        hash = 17 * hash + Objects.hashCode(this.item1);
        hash = 17 * hash + Objects.hashCode(this.item2);
        hash = 17 * hash + Objects.hashCode(this.item3);
        hash = 17 * hash + Objects.hashCode(this.item4);
        hash = 17 * hash + Objects.hashCode(this.item5);
        hash = 17 * hash + Objects.hashCode(this.item6);
        hash = 17 * hash + Objects.hashCode(this.itemsPurchased);
        hash = 17 * hash + Objects.hashCode(this.killingSprees);
        hash = 17 * hash + Objects.hashCode(this.largestCriticalStrike);
        hash = 17 * hash + Objects.hashCode(this.largestKillingSpree);
        hash = 17 * hash + Objects.hashCode(this.largestMultiKill);
        hash = 17 * hash + Objects.hashCode(this.legendaryItemsCreated);
        hash = 17 * hash + Objects.hashCode(this.level);
        hash = 17 * hash + Objects.hashCode(this.magicDamageDealtPlayer);
        hash = 17 * hash + Objects.hashCode(this.magicDamageDealtToChampions);
        hash = 17 * hash + Objects.hashCode(this.magicDamageTaken);
        hash = 17 * hash + Objects.hashCode(this.minionsDenied);
        hash = 17 * hash + Objects.hashCode(this.minionsKilled);
        hash = 17 * hash + Objects.hashCode(this.neutralMinionsKilled);
        hash = 17 * hash + Objects.hashCode(this.neutralMinionsKilledEnemyJungle);
        hash = 17 * hash + Objects.hashCode(this.neutralMinionsKilledYourJungle);
        hash = 17 * hash + Objects.hashCode(this.nexusKilled);
        hash = 17 * hash + Objects.hashCode(this.nodeCapture);
        hash = 17 * hash + Objects.hashCode(this.nodeCaptureAssist);
        hash = 17 * hash + Objects.hashCode(this.nodeNeutralize);
        hash = 17 * hash + Objects.hashCode(this.nodeNeutralizeAssist);
        hash = 17 * hash + Objects.hashCode(this.numDeaths);
        hash = 17 * hash + Objects.hashCode(this.numItemsBought);
        hash = 17 * hash + Objects.hashCode(this.objectivePlayerScore);
        hash = 17 * hash + Objects.hashCode(this.pentaKills);
        hash = 17 * hash + Objects.hashCode(this.physicalDamageDealtPlayer);
        hash = 17 * hash + Objects.hashCode(this.physicalDamageDealtToChampions);
        hash = 17 * hash + Objects.hashCode(this.physicalDamageTaken);
        hash = 17 * hash + Objects.hashCode(this.playerPosition);
        hash = 17 * hash + Objects.hashCode(this.playerRole);
        hash = 17 * hash + Objects.hashCode(this.quadraKills);
        hash = 17 * hash + Objects.hashCode(this.sightWardsBought);
        hash = 17 * hash + Objects.hashCode(this.spell1Cast);
        hash = 17 * hash + Objects.hashCode(this.spell2Cast);
        hash = 17 * hash + Objects.hashCode(this.spell3Cast);
        hash = 17 * hash + Objects.hashCode(this.spell4Cast);
        hash = 17 * hash + Objects.hashCode(this.summonSpell1Cast);
        hash = 17 * hash + Objects.hashCode(this.summonSpell2Cast);
        hash = 17 * hash + Objects.hashCode(this.superMonsterKilled);
        hash = 17 * hash + Objects.hashCode(this.team);
        hash = 17 * hash + Objects.hashCode(this.teamObjective);
        hash = 17 * hash + Objects.hashCode(this.timePlayed);
        hash = 17 * hash + Objects.hashCode(this.totalDamageDealt);
        hash = 17 * hash + Objects.hashCode(this.totalDamageDealtToChampions);
        hash = 17 * hash + Objects.hashCode(this.totalDamageTaken);
        hash = 17 * hash + Objects.hashCode(this.totalHeal);
        hash = 17 * hash + Objects.hashCode(this.totalPlayerScore);
        hash = 17 * hash + Objects.hashCode(this.totalScoreRank);
        hash = 17 * hash + Objects.hashCode(this.totalTimeCrowdControlDealt);
        hash = 17 * hash + Objects.hashCode(this.totalUnitsHealed);
        hash = 17 * hash + Objects.hashCode(this.tripleKills);
        hash = 17 * hash + Objects.hashCode(this.trueDamageDealtPlayer);
        hash = 17 * hash + Objects.hashCode(this.trueDamageDealtToChampions);
        hash = 17 * hash + Objects.hashCode(this.trueDamageTaken);
        hash = 17 * hash + Objects.hashCode(this.turretsKilled);
        hash = 17 * hash + Objects.hashCode(this.unrealKills);
        hash = 17 * hash + Objects.hashCode(this.victoryPointTotal);
        hash = 17 * hash + Objects.hashCode(this.visionWardsBought);
        hash = 17 * hash + Objects.hashCode(this.wardKilled);
        hash = 17 * hash + Objects.hashCode(this.wardPlaced);
        hash = 17 * hash + Objects.hashCode(this.win);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RawStatsDto other = (RawStatsDto) obj;
        if (!Objects.equals(this.assists, other.assists)) {
            return false;
        }
        if (!Objects.equals(this.barracksKilled, other.barracksKilled)) {
            return false;
        }
        if (!Objects.equals(this.championsKilled, other.championsKilled)) {
            return false;
        }
        if (!Objects.equals(this.combatPlayerScore, other.combatPlayerScore)) {
            return false;
        }
        if (!Objects.equals(this.consumablesPurchased, other.consumablesPurchased)) {
            return false;
        }
        if (!Objects.equals(this.damageDealtPlayer, other.damageDealtPlayer)) {
            return false;
        }
        if (!Objects.equals(this.doubleKills, other.doubleKills)) {
            return false;
        }
        if (!Objects.equals(this.firstBlood, other.firstBlood)) {
            return false;
        }
        if (!Objects.equals(this.gold, other.gold)) {
            return false;
        }
        if (!Objects.equals(this.goldEarned, other.goldEarned)) {
            return false;
        }
        if (!Objects.equals(this.goldSpent, other.goldSpent)) {
            return false;
        }
        if (!Objects.equals(this.item0, other.item0)) {
            return false;
        }
        if (!Objects.equals(this.item1, other.item1)) {
            return false;
        }
        if (!Objects.equals(this.item2, other.item2)) {
            return false;
        }
        if (!Objects.equals(this.item3, other.item3)) {
            return false;
        }
        if (!Objects.equals(this.item4, other.item4)) {
            return false;
        }
        if (!Objects.equals(this.item5, other.item5)) {
            return false;
        }
        if (!Objects.equals(this.item6, other.item6)) {
            return false;
        }
        if (!Objects.equals(this.itemsPurchased, other.itemsPurchased)) {
            return false;
        }
        if (!Objects.equals(this.killingSprees, other.killingSprees)) {
            return false;
        }
        if (!Objects.equals(this.largestCriticalStrike, other.largestCriticalStrike)) {
            return false;
        }
        if (!Objects.equals(this.largestKillingSpree, other.largestKillingSpree)) {
            return false;
        }
        if (!Objects.equals(this.largestMultiKill, other.largestMultiKill)) {
            return false;
        }
        if (!Objects.equals(this.legendaryItemsCreated, other.legendaryItemsCreated)) {
            return false;
        }
        if (!Objects.equals(this.level, other.level)) {
            return false;
        }
        if (!Objects.equals(this.magicDamageDealtPlayer, other.magicDamageDealtPlayer)) {
            return false;
        }
        if (!Objects.equals(this.magicDamageDealtToChampions, other.magicDamageDealtToChampions)) {
            return false;
        }
        if (!Objects.equals(this.magicDamageTaken, other.magicDamageTaken)) {
            return false;
        }
        if (!Objects.equals(this.minionsDenied, other.minionsDenied)) {
            return false;
        }
        if (!Objects.equals(this.minionsKilled, other.minionsKilled)) {
            return false;
        }
        if (!Objects.equals(this.neutralMinionsKilled, other.neutralMinionsKilled)) {
            return false;
        }
        if (!Objects.equals(this.neutralMinionsKilledEnemyJungle, other.neutralMinionsKilledEnemyJungle)) {
            return false;
        }
        if (!Objects.equals(this.neutralMinionsKilledYourJungle, other.neutralMinionsKilledYourJungle)) {
            return false;
        }
        if (!Objects.equals(this.nexusKilled, other.nexusKilled)) {
            return false;
        }
        if (!Objects.equals(this.nodeCapture, other.nodeCapture)) {
            return false;
        }
        if (!Objects.equals(this.nodeCaptureAssist, other.nodeCaptureAssist)) {
            return false;
        }
        if (!Objects.equals(this.nodeNeutralize, other.nodeNeutralize)) {
            return false;
        }
        if (!Objects.equals(this.nodeNeutralizeAssist, other.nodeNeutralizeAssist)) {
            return false;
        }
        if (!Objects.equals(this.numDeaths, other.numDeaths)) {
            return false;
        }
        if (!Objects.equals(this.numItemsBought, other.numItemsBought)) {
            return false;
        }
        if (!Objects.equals(this.objectivePlayerScore, other.objectivePlayerScore)) {
            return false;
        }
        if (!Objects.equals(this.pentaKills, other.pentaKills)) {
            return false;
        }
        if (!Objects.equals(this.physicalDamageDealtPlayer, other.physicalDamageDealtPlayer)) {
            return false;
        }
        if (!Objects.equals(this.physicalDamageDealtToChampions, other.physicalDamageDealtToChampions)) {
            return false;
        }
        if (!Objects.equals(this.physicalDamageTaken, other.physicalDamageTaken)) {
            return false;
        }
        if (!Objects.equals(this.playerPosition, other.playerPosition)) {
            return false;
        }
        if (!Objects.equals(this.playerRole, other.playerRole)) {
            return false;
        }
        if (!Objects.equals(this.quadraKills, other.quadraKills)) {
            return false;
        }
        if (!Objects.equals(this.sightWardsBought, other.sightWardsBought)) {
            return false;
        }
        if (!Objects.equals(this.spell1Cast, other.spell1Cast)) {
            return false;
        }
        if (!Objects.equals(this.spell2Cast, other.spell2Cast)) {
            return false;
        }
        if (!Objects.equals(this.spell3Cast, other.spell3Cast)) {
            return false;
        }
        if (!Objects.equals(this.spell4Cast, other.spell4Cast)) {
            return false;
        }
        if (!Objects.equals(this.summonSpell1Cast, other.summonSpell1Cast)) {
            return false;
        }
        if (!Objects.equals(this.summonSpell2Cast, other.summonSpell2Cast)) {
            return false;
        }
        if (!Objects.equals(this.superMonsterKilled, other.superMonsterKilled)) {
            return false;
        }
        if (!Objects.equals(this.team, other.team)) {
            return false;
        }
        if (!Objects.equals(this.teamObjective, other.teamObjective)) {
            return false;
        }
        if (!Objects.equals(this.timePlayed, other.timePlayed)) {
            return false;
        }
        if (!Objects.equals(this.totalDamageDealt, other.totalDamageDealt)) {
            return false;
        }
        if (!Objects.equals(this.totalDamageDealtToChampions, other.totalDamageDealtToChampions)) {
            return false;
        }
        if (!Objects.equals(this.totalDamageTaken, other.totalDamageTaken)) {
            return false;
        }
        if (!Objects.equals(this.totalHeal, other.totalHeal)) {
            return false;
        }
        if (!Objects.equals(this.totalPlayerScore, other.totalPlayerScore)) {
            return false;
        }
        if (!Objects.equals(this.totalScoreRank, other.totalScoreRank)) {
            return false;
        }
        if (!Objects.equals(this.totalTimeCrowdControlDealt, other.totalTimeCrowdControlDealt)) {
            return false;
        }
        if (!Objects.equals(this.totalUnitsHealed, other.totalUnitsHealed)) {
            return false;
        }
        if (!Objects.equals(this.tripleKills, other.tripleKills)) {
            return false;
        }
        if (!Objects.equals(this.trueDamageDealtPlayer, other.trueDamageDealtPlayer)) {
            return false;
        }
        if (!Objects.equals(this.trueDamageDealtToChampions, other.trueDamageDealtToChampions)) {
            return false;
        }
        if (!Objects.equals(this.trueDamageTaken, other.trueDamageTaken)) {
            return false;
        }
        if (!Objects.equals(this.turretsKilled, other.turretsKilled)) {
            return false;
        }
        if (!Objects.equals(this.unrealKills, other.unrealKills)) {
            return false;
        }
        if (!Objects.equals(this.victoryPointTotal, other.victoryPointTotal)) {
            return false;
        }
        if (!Objects.equals(this.visionWardsBought, other.visionWardsBought)) {
            return false;
        }
        if (!Objects.equals(this.wardKilled, other.wardKilled)) {
            return false;
        }
        if (!Objects.equals(this.wardPlaced, other.wardPlaced)) {
            return false;
        }
        if (!Objects.equals(this.win, other.win)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RawStatsDto{" + "assists=" + assists + ", barracksKilled=" + barracksKilled + ", championsKilled=" + championsKilled + ", combatPlayerScore=" + combatPlayerScore + ", consumablesPurchased=" + consumablesPurchased + ", damageDealtPlayer=" + damageDealtPlayer + ", doubleKills=" + doubleKills + ", firstBlood=" + firstBlood + ", gold=" + gold + ", goldEarned=" + goldEarned + ", goldSpent=" + goldSpent + ", item0=" + item0 + ", item1=" + item1 + ", item2=" + item2 + ", item3=" + item3 + ", item4=" + item4 + ", item5=" + item5 + ", item6=" + item6 + ", itemsPurchased=" + itemsPurchased + ", killingSprees=" + killingSprees + ", largestCriticalStrike=" + largestCriticalStrike + ", largestKillingSpree=" + largestKillingSpree + ", largestMultiKill=" + largestMultiKill + ", legendaryItemsCreated=" + legendaryItemsCreated + ", level=" + level + ", magicDamageDealtPlayer=" + magicDamageDealtPlayer + ", magicDamageDealtToChampions=" + magicDamageDealtToChampions + ", magicDamageTaken=" + magicDamageTaken + ", minionsDenied=" + minionsDenied + ", minionsKilled=" + minionsKilled + ", neutralMinionsKilled=" + neutralMinionsKilled + ", neutralMinionsKilledEnemyJungle=" + neutralMinionsKilledEnemyJungle + ", neutralMinionsKilledYourJungle=" + neutralMinionsKilledYourJungle + ", nexusKilled=" + nexusKilled + ", nodeCapture=" + nodeCapture + ", nodeCaptureAssist=" + nodeCaptureAssist + ", nodeNeutralize=" + nodeNeutralize + ", nodeNeutralizeAssist=" + nodeNeutralizeAssist + ", numDeaths=" + numDeaths + ", numItemsBought=" + numItemsBought + ", objectivePlayerScore=" + objectivePlayerScore + ", pentaKills=" + pentaKills + ", physicalDamageDealtPlayer=" + physicalDamageDealtPlayer + ", physicalDamageDealtToChampions=" + physicalDamageDealtToChampions + ", physicalDamageTaken=" + physicalDamageTaken + ", playerPosition=" + playerPosition + ", playerRole=" + playerRole + ", quadraKills=" + quadraKills + ", sightWardsBought=" + sightWardsBought + ", spell1Cast=" + spell1Cast + ", spell2Cast=" + spell2Cast + ", spell3Cast=" + spell3Cast + ", spell4Cast=" + spell4Cast + ", summonSpell1Cast=" + summonSpell1Cast + ", summonSpell2Cast=" + summonSpell2Cast + ", superMonsterKilled=" + superMonsterKilled + ", team=" + team + ", teamObjective=" + teamObjective + ", timePlayed=" + timePlayed + ", totalDamageDealt=" + totalDamageDealt + ", totalDamageDealtToChampions=" + totalDamageDealtToChampions + ", totalDamageTaken=" + totalDamageTaken + ", totalHeal=" + totalHeal + ", totalPlayerScore=" + totalPlayerScore + ", totalScoreRank=" + totalScoreRank + ", totalTimeCrowdControlDealt=" + totalTimeCrowdControlDealt + ", totalUnitsHealed=" + totalUnitsHealed + ", tripleKills=" + tripleKills + ", trueDamageDealtPlayer=" + trueDamageDealtPlayer + ", trueDamageDealtToChampions=" + trueDamageDealtToChampions + ", trueDamageTaken=" + trueDamageTaken + ", turretsKilled=" + turretsKilled + ", unrealKills=" + unrealKills + ", victoryPointTotal=" + victoryPointTotal + ", visionWardsBought=" + visionWardsBought + ", wardKilled=" + wardKilled + ", wardPlaced=" + wardPlaced + ", win=" + win + '}';
    }

}
