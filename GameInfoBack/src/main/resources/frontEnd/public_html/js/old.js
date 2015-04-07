var search;

$(document).ready(function () {
    var lastRowT100 = false;
    var lastRowT200 = false;
    var server = "";
    function addSummonerOld(team, name, champIcon, spell1, spell2, s5, wins, losses, oc, dc, uc, kda, ak, ad, aa, cwr, cw, cl, rpname, rpnamef, hilight, runeStats) {
        var rowClass;
        if (team === 100) {
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
        if (hilight) {
            rowClass = "cr3";
        }
        var rsstr = "<br>";
        runeStats.forEach(function (rs) {
            rsstr += "<br>" + rs.name + "<br>" + rs.value;
        });
        var row = $("<div class='row " + rowClass + "'></div>").loadTemplate($("#summonerRowTemplate"), {
            summonerName: name,
            championIcon: realRoot + "static/img/champion/" + champIcon,
            summonerSpell1: realRoot + "static/img/spell/" + spell1,
            summonerSpell2: realRoot + "static/img/spell/" + spell2,
            tier: s5.split(" ")[0].toLowerCase(),
            seasonRank: s5,
            soloRankedWinRate: (Math.round(wins / Math.max(wins + losses, 1) * 1000) / 10) + "%",
            soloRankedWins: wins,
            soloRankedLosses: losses,
            championKDA: kda,
            championKills: ak,
            championDeaths: ad,
            championAssists: aa,
            championWinRate: cwr,
            championWins: cw,
            championLosses: cl,
            runePageName: rpname,
            runePageInfo: rpname + rsstr,
            offenseCount: oc,
            defenseCount: dc,
            utilityCount: uc
        });
        row.find(".runes").tooltip();
        $("#t" + team + "_summoners").append(row);
        var more = $("<div class='collapse' data-toggle='collapse'>"
                + "O____O"
                + "</div>");
        $("#t" + team + "_summoners").append(more);
        row.click(function () {
            more.collapse("toggle");
        });
        row.find(".masteries").popover({
            content: "<img src='" + realRoot + "static/img/masteryback.png'>",
            html: true,
            placement: "left",
            trigger: "hover"
        });
    }
    function addBan(team, champ) {
        $("#t" + team + "_bans").append('<img src="' + realRoot + 'static/img/champion/' + champ + '">');
    }
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
    function addSummonerBasic(p) {
        var rowClass;
        if (p.team === 100) {
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
        if (p.hilight) {
            p.rowClass = "cr3";
        }
        var row = $("<div class='row " + rowClass + "'></div>").loadTemplate($("#summonerRowTemplate"), {
            summonerName: p.summonerName,
            championIcon: realRoot + "static/img/champion/" + p.championImage,
            summonerSpell1: realRoot + "static/img/spell/" + p.spell1Image,
            summonerSpell2: realRoot + "static/img/spell/" + p.spell2Image,
            rankElementId: p.summonerId + "_rank",
            winRateElementId: p.summonerId + "_winRate",
            kdaElementId: p.summonerId + "_kda",
            championWinRateElementId: p.summonerId + "_champWinRate",
            runesElementId: p.summonerId + "_runes",
            masteriesElementId: p.summonerId + "_masteries",
            offenseCount: p.masteries.offense,
            defenseCount: p.masteries.defense,
            utilityCount: p.masteries.utility
        });
        row.find("#" + p.summonerId + "_masteries").popover({
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
        $("#t" + p.team + "_summoners").append(row);
    }
    function handleCurrentGameData(data) {
        console.log(data);
        $("#bigIcon").attr("src", realRoot + "static/" + "img/profileicon/" + data.bigIcon);
        $("#bigName").text(data.bigName);
        $("#gameinfo").text(data.infoLine);
        setTimeCounter(data.startTime);
        data.participants.forEach(function (p) {
            addSummonerBasic(p);
        });
        data.bannedChampions.forEach(function (p) {
            addBan(p.team, p.champion);
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
    function searchSummoner(server_, name) {
        server = server_;
        localStorage.searchValue = name;
        $("#summonerInput").val(localStorage.searchValue);
        $("#searchButton").attr("disabled", "disabled");
        $("#searchButtonImage").toggleClass("spinner");
        $("#searchButtonImage").toggleClass("glyphicon-refresh");
        $("#searchButtonText").text("Searching")
        searching = true;
        $.ajax(realRoot + server + "/" + name + "/current").done(function (data) {
            handleCurrentGameData(data);
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
    search = searchSummoner;
    if (localStorage.searchValue !== undefined) {
        $("#summonerInput").val(localStorage.searchValue);
    }
});
console.log(realRoot)