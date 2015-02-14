var search;

$(document).ready(function () {
    function handleData(data) {
        setInfo(data.bigIcon, data.bigName, data.gameInfo);
        data.participant.forEach(function (p) {
            addSummoner(p.team, p.name, p.championImage, p.spell1Image, p.spell2Image,
                    p.s5, p.masteries.oc, p.masteries.dc, p.masteries.uc,
                    p.ranked.kda, p.ranked.averageKills, p.ranked.averageDeaths,
                    p.ranked.averageAssists, p.ranked.championWinRatio, p.ranked.championWins,
                    p.ranked.championLosses, p.runePageName, p.runePageNameFull, p.hilight);
        });
        data.bans.forEach(function (p) {
            addBan(p.team, p.champion);
        });
        $("#teams").show();
    }
    lastRowT100 = false;
    lastRowT200 = false;
    function addSummoner(team, name, champIcon, spell1, spell2, s5, oc, dc, uc, kda, ak, ad, aa, cwr, cw, cl, rpname, rpnamef, hilight) {
        s = $("#template .row_summoner").clone();
        if (team === 100) {
            if (!lastRowT100) {
                s.addClass("cr2");
                lastRowT100 = true;
            } else {
                s.addClass("cr1");
                lastRowT100 = false;
            }
        } else {
            if (!lastRowT200) {
                s.addClass("cr2");
                lastRowT200 = true;
            } else {
                s.addClass("cr1");
                lastRowT200 = false;
            }
        }
        if (hilight) {
            s.addClass("cr3");
        }
        s.find(".row_name").text(name);
        s.find(".champ_icon").attr("src", realRoot + "img/champion/" + champIcon);
        s.find(".spell1").attr("src", realRoot + "img/spell/" + spell1);
        s.find(".spell2").attr("src", realRoot + "img/spell/" + spell2);
        s.find(".row_s5").text(s5);
        s.find(".moc").text(oc);
        s.find(".mdc").text(dc);
        s.find(".muc").text(uc);
        s.find(".kda").text(kda);
        s.find(".kda_k").text(ak);
        s.find(".kda_d").text(ad);
        s.find(".kda_a").text(aa);
        s.find(".champWins .rate").text(cwr);
        s.find(".champWins .wins").text(cw);
        s.find(".champWins .losses").text(cl);
        s.find(".runes").text(rpname).attr("title", rpnamef).tooltip();
        $("#t" + team + "_summoners").append(s);
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
    function cleanUp() {
        $("#t100_summoners .row_summoner").remove();
        $("#t200_summoners .row_summoner").remove();
        $("#t100_bans img").remove();
        $("#t200_bans img").remove();
        lastRowT200 = false;
        lastRowT100 = false;
    }
    function searchSummoner(name) {
        localStorage.searchValue = name;
        $("#summonerInput").val(localStorage.searchValue);
        $("#searchButton").attr("disabled", "disabled");
        $("#searchButtonImage").toggleClass("spinner");
        $("#searchButtonImage").toggleClass("glyphicon-refresh");
        $("#searchButtonText").text("Searching")
        searching = true;
        $.ajax("/" + name + "/curgame/").done(function (data) {
            console.log(data);
            $("#searchButton").addClass("btn-success");
            setTimeout(function () {
                $("#searchButton").removeClass("btn-success");
            }, 2000);
            cleanUp();
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
                $("#searchButtonText").text("Searching. Please be patient!");
            }
        }, 6000);
    }
    search = searchSummoner;
    //searchSummoner("tuk1");
    if (localStorage.searchValue !== undefined) {
        $("#summonerInput").val(localStorage.searchValue);
    }
    //setInfo("548.png", "AlleyCaesar", "Summoner's Rift, Solo Ranked 5v5, EUNE");
    //addSummoner(1, "KiviBot", "Azir.png","SummonerFlash.png", "SummonerHeal.png", "S4", 21, 0, 9);
});
console.log(realRoot)