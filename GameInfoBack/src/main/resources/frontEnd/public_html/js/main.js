var search;

$(document).ready(function () {
    function handleData(data) {
        setInfo(data.bigIcon, data.bigName, data.gameInfo);
        setTimeCounter(data.startTime);
        data.participant.forEach(function (p) {
            addSummoner(p.team, p.name, p.championImage, p.spell1Image, p.spell2Image,
                    p.s5, p.wins, p.losses,
                    p.masteries.oc, p.masteries.dc, p.masteries.uc,
                    p.ranked.kda, p.ranked.averageKills, p.ranked.averageDeaths,
                    p.ranked.averageAssists, p.ranked.championWinRatio, p.ranked.championWins,
                    p.ranked.championLosses, p.runePageName, p.runePageNameFull, p.hilight,
                    p.runeStats);
        });
        data.bans.forEach(function (p) {
            addBan(p.team, p.champion);
        });
        $("#logo1").removeClass("logo1");
        $("#teams").show();
    }
    lastRowT100 = false;
    lastRowT200 = false;
    function addSummoner(team, name, champIcon, spell1, spell2, s5, wins, losses, oc, dc, uc, kda, ak, ad, aa, cwr, cw, cl, rpname, rpnamef, hilight, runeStats) {
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

        row = $("<div class='row " + rowClass + "'></div>").loadTemplate($("#summonerRowTemplate"), {
            summonerName: name,
            championIcon: realRoot + "img/champion/" + champIcon,
            summonerSpell1: realRoot + "img/spell/" + spell1,
            summonerSpell2: realRoot + "img/spell/" + spell2,
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
    }
    function addBan(team, champ) {
        $("#t" + team + "_bans").append('<img src="' + realRoot + 'img/champion/' + champ + '">');
    }
    function setInfo(icon, name, gameinfo) {
        $("#bigIcon").attr("src", realRoot + "img/profileicon/" + icon);
        $("#bigName").text(name);
        $("#gameinfo").text(gameinfo);
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
        }, 500);
    }
    function searchSummoner(name) {
        localStorage.searchValue = name;
        $("#summonerInput").val(localStorage.searchValue);
        $("#searchButton").attr("disabled", "disabled");
        $("#searchButtonImage").toggleClass("spinner");
        $("#searchButtonImage").toggleClass("glyphicon-refresh");
        $("#searchButtonText").text("Searching")
        searching = true;
        $.ajax(realRoot + name + "/curgame/").done(function (data) {
            console.log(data);
            handleData(data);
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