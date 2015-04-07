var imagePath = realRoot + "img"

function showError(strong, info) {
    $("#alerts").append('<div class="alert alert-danger alert-dismissible fade in" id="alert0" role="alert">'
            + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
            + '<strong>' + strong + '</strong> ' + info + '</div>');
}

function setTimeCounter(start) {
    setInterval(function () {
        time = new Date().getTime() - start;
        ms = time % 1000;
        s = ((time - ms) / 1000) % 60;
        m = (time - time % 60000) / 60000;
        if (s < 10) {
            s = "0" + s;
        }
        $("#gameTime").text(m + ":" + s);
    }, 300);
}

var lastRowT100 = false, lastRowT200 = false;
function addSummoner(s) {
    var rowClass;
    if (s.team === 100) {
        if (!lastRowT100) {
            lastRowT100 = true;
            rowClass = "cr2";
        } else {
            lastRowT100 = false;
            rowClass = "cr1";
        }
    } else {
        if (!lastRowT200) {
            lastRowT200 = true;
            rowClass = "cr2";
        } else {
            lastRowT200 = false;
            rowClass = "cr1";
        }
    }
    if (s.hilight) {
        s.rowClass = "cr3";
    }
    var row = $("<div></div>").loadTemplate($("#summonerRowTemplate"), {
        summonerName: s.summonerName,
        championIcon: imagePath + "/champion/" + s.championImage,
        summonerSpell1: imagePath + "/spell/" + s.spell1Image,
        summonerSpell2: imagePath + "/spell/" + s.spell2Image,
        rankElementId: s.summonerId + "_rank",
        winRateElementId: s.summonerId + "_winRate",
        kdaElementId: s.summonerId + "_kda",
        championWinRateElementId: s.summonerId + "_champWinRate",
        runesElementId: s.summonerId + "_runes",
        masteriesElementId: s.summonerId + "_masteries",
        offenseCount: s.masteries.offense,
        defenseCount: s.masteries.defense,
        utilityCount: s.masteries.utility
    }).addClass("row").addClass(rowClass);
    row.find("#" + s.summonerId + "_masteries").popover({
        content: "<img src='" + realRoot + "img/masteryback.png'>"
                + '<img class="" src="' + "" + 'img/spell/SummonerHeal.png" style="width: 48px; position: absolute; left: 25px; top: 15px;">'
                + '<img class="" src="img/spell/SummonerHeal.png" style="width: 48px; position: absolute; left: 85px; top: 88px;">'
                + '<img class="" src="img/spell/SummonerHeal.png" style="width: 48px; position: absolute; left: 145px; top: 157px;">'
                + '<img class="" src="img/spell/SummonerHeal.png" style="width: 48px; position: absolute; left: 205px; top: 229px;">'
                + '<img class="" src="img/spell/SummonerHeal.png" style="width: 48px; position: absolute; left: 25px; top: 300px;">'
                + '<img class="" src="img/spell/SummonerHeal.png" style="width: 48px; position: absolute; left: 85px; top: 372px;">'
                + '<h3 style="position: absolute; left: 25px; top: 420px; color: #fff;">Offense: 21</h3>',
        html: true,
        placement: "left",
        trigger: "click"
    });
    $("#t" + s.team + "_summoners").append(row);
}

function addBan(team, champ) {
    $("#t" + team + "_bans").append('<img src="' + imagePath + '/champion/' + champ + '">');
}

function handleCurrentGame(server, summoner, data) {
    console.log(data);
    $("#bigIcon").attr("src", imagePath + "/profileicon/" + data.bigIcon);
    $("#bigName").text(data.bigName);
    $("#gameinfo").text(data.infoLine);
    setTimeCounter(data.startTime);
    data.participants.forEach(function (p) {
        addSummoner(p);
    });
    data.bannedChampions.forEach(function (b) {
        addBan(b.team, b.champion);
    });
    $("#logo1").removeClass("logo1");
    $("#teams").show();

    $.ajax(realRoot + server + "/" + data.gameId + "/ranks").done(function (pd) {
        console.log(pd);
        pd.participants.forEach(function (p) {
            var tier = "";
            var rank = "";
            if (p.tier !== undefined) {
                tier = p.tier.toLowerCase();
                rank = p.tier + " " + p.division + "<br>";
                if (p.series === undefined) {
                    rank += p.lp + " LP";
                } else {
                    rank += "Series: " + p.series.replace(/W/g, "<span class='green1'>o</span>")
                            .replace(/L/g, "<span class='red1'>o</span>")
                            .replace(/N/g, "<span class='grey2'>o</span>");
                }
            } else {
                rank = "LEVEL " + p.level;
            }
            $("#" + p.summonerId + "_rank").loadTemplate($("#summonerRankTemplate"), {
                tier: tier,
                seasonRank: rank
            });
            var rsstr = "<br>";
            p.runeStats.forEach(function (rs) {
                rsstr += "<br>" + rs.name + "<br>" + rs.value;
            });
            $("#" + p.summonerId + "_runes").loadTemplate($("#summonerRunesTemplate"), {
                runePageName: p.runePageName,
                runePageInfo: p.runePageNameFull + rsstr,
            }).find(".runes").tooltip();
            $.ajax(realRoot + server + "/" + data.gameId + "/" + p.summonerId + "/stats").done(function (sd) {
                console.log(sd);
                $("#" + p.summonerId + "_winRate").loadTemplate($("#summonerWinRateTemplate"), {
                    soloRankedWinRate: Math.round(sd.wins / Math.max(sd.wins + sd.losses, 1) * 1000) / 10 + "%",
                    soloRankedWins: sd.wins,
                    soloRankedLosses: sd.losses
                });
                $("#" + p.summonerId + "_kda").loadTemplate($("#summonerKDATemplate"), {
                    championKDA: sd.ranked.kda,
                    championKills: sd.ranked.averageKills,
                    championDeaths: sd.ranked.averageDeaths,
                    championAssists: sd.ranked.averageAssists
                });
                $("#" + p.summonerId + "_champWinRate").loadTemplate($("#summonerChampWinRateTemplate"), {
                    championWinRate: sd.ranked.championWinRatio,
                    championWins: sd.ranked.championWins,
                    championLosses: sd.ranked.championLosses
                });
            }).fail(function (data) {
                console.log(data);
                $("#searchButton").addClass("btn-danger");
                setTimeout(function () {
                    $("#searchButton").removeClass("btn-danger");
                }, 2000);
                showError(data.status, data.responseText);
            });
        });
    }).fail(function (data) {
        console.log(data);
        $("#searchButton").addClass("btn-danger");
        setTimeout(function () {
            $("#searchButton").removeClass("btn-danger");
        }, 2000);
        showError(data.status, data.responseText);
    });
}

function search(server, summoner) {
    sessionStorage.searchValue = summoner;
    sessionStorage.serverValue = server;
    $("#summonerInput").val(sessionStorage.searchValue);
    $("#serverSelect").val(sessionStorage.serverValue);
    $("#searchButton").attr("disabled", "disabled");
    $("#searchButtonImage").toggleClass("spinner");
    $("#searchButtonImage").toggleClass("glyphicon-refresh");
    $("#searchButtonText").text("Searching")

    $.ajax(realRoot + server + "/" + summoner + "/current").done(function (data) {
        handleCurrentGame(server, summoner, data);
    }).fail(function (data) {
        console.log(data)
        $("#searchButton").addClass("btn-danger");
        setTimeout(function () {
            $("#searchButton").removeClass("btn-danger");
        }, 2000);
        showError(data.status, data.responseText);
    }).complete(function () {
        searching = false;
        $("#searchButton").removeAttr("disabled");
        $("#searchButtonImage").toggleClass("spinner");
        $("#searchButtonImage").toggleClass("glyphicon-refresh");
        $("#searchButtonText").text("Search");
    });
    setTimeout(function () {
        if (searching) {
            $("#searchButtonText").text("Tässä menee aikaa...");
        }
    }, 6000);

}


$(document).ready(function () {
    if (sessionStorage.searchValue !== undefined) {
        $("#summonerInput").val(sessionStorage.searchValue);
    }
    if (sessionStorage.serverValue !== undefined) {
        $("#summonerInput").val(sessionStorage.searchValue);
    }
});
