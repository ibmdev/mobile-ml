package fr.bet.unibet_football_data.services;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.bet.unibet_football_data.beans.Competition;
import fr.bet.unibet_football_data.beans.Match;
import fr.bet.unibet_football_data.beans.Team;

public class StatistiqueService {

    public static List<Competition> getAllCompetition(final String jsonData) {
        Gson gson = new Gson();
        Type listCompetitionType = new TypeToken<List<Competition>>() { }.getType();
        List<Competition> competitions = gson.fromJson(jsonData, listCompetitionType);
        return competitions;
    }

    public static List<String> getAllCompetitionAsString(final List<Competition> competitions) {
        List<String> competitionAsName = competitions.stream()
                .map(c -> c.getLeague())
                .collect(Collectors.toList());
        competitionAsName.add(0,"");
        return competitionAsName;
    }
    public static List<Match> getAllMatchs(final String jsonData) {
        Gson gson = new Gson();
        Type listMatchType = new TypeToken<List<Match>>() { }.getType();
        List<Match> matchs = gson.fromJson(jsonData, listMatchType);
        return matchs;
    }
    public static List<String> getAllTeams(final String jsonData) {
        List<Match> matchs = getAllMatchs(jsonData);
        List<String> teamsName = matchs.stream()
                .flatMap(match -> Stream.of(match.getHomeTeam(), match.getAwayTeam()))
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        teamsName.add(0,"");
        return teamsName;
    }
    // Recherche des matchs d'une équipe
    public static List<Match> getMatchesByTeamName(final String teamName, final List<Match> matchs) {
        List<Match> matches = matchs.stream()
                .filter(m -> teamName.equals(m.getHomeTeam()) || teamName.equals(m.getAwayTeam()))
                .collect(Collectors.toList());
        return matches;
    }
    // Recherche des statistiques d'une équipe
    public static Team fillStatsForTeam(final String teamName, final List<Match> allMatchs) {
        List<Match> matchesTeam = getMatchesByTeamName(teamName, allMatchs);
        Team team = new Team(teamName);
        matchesTeam.stream().forEach((match) -> {
            if(teamName.equals(match.getHomeTeam())) {
                team.getRankHome().setPlayed(team.getRankHome().getPlayed() + 1);
                team.getRankHome().setGFTotal(team.getRankHome().getGFTotal() + match.getFTHG());
                team.getRankHome().setGCTotal(team.getRankHome().getGCTotal() + match.getFTAG());
                team.getRankHome().setCYTotal(team.getRankHome().getCYTotal() + match.getHY());
                team.getRankHome().setCRTotal(team.getRankHome().getCRTotal() + match.getHR());
                if(match.getFTHG() > match.getFTAG()) {
                    team.getRankHome().setPoints(team.getRankHome().getPoints() + 3);
                } else if(match.getFTHG() == match.getFTAG()) {
                    team.getRankHome().setPoints(team.getRankHome().getPoints() + 1);
                }

            } else if(teamName.equals(match.getAwayTeam())) {
                team.getRankAway().setPlayed(team.getRankAway().getPlayed() + 1);
                team.getRankAway().setGFTotal(team.getRankAway().getGFTotal() + match.getFTAG());
                team.getRankAway().setGCTotal(team.getRankAway().getGCTotal() + match.getFTHG());
                team.getRankAway().setCYTotal(team.getRankAway().getCYTotal() + match.getAY());
                team.getRankAway().setCRTotal(team.getRankAway().getCRTotal() + match.getAR());
                if(match.getFTAG() > match.getFTHG()) {
                    team.getRankAway().setPoints(team.getRankAway().getPoints() + 3);
                } else if(match.getFTHG() == match.getFTAG()) {
                    team.getRankAway().setPoints(team.getRankAway().getPoints() + 1);
                }
            }
        });
        // Global
        team.getRankGlobal().setPoints(team.getRankHome().getPoints() + team.getRankAway().getPoints());
        team.getRankGlobal().setPlayed(matchesTeam.size());
        team.getRankGlobal().setGFTotal(team.getRankHome().getGFTotal() + team.getRankAway().getGFTotal());
        team.getRankGlobal().setGCTotal(team.getRankHome().getGCTotal() + team.getRankAway().getGCTotal());
        team.getRankGlobal().setCYTotal(team.getRankHome().getCYTotal() + team.getRankAway().getCYTotal());
        team.getRankGlobal().setCRTotal(team.getRankHome().getCRTotal() + team.getRankAway().getCRTotal());

        return team;
    }

    // Utilitaires de tri des données

    /* Tri par nombre de points */
    public static List<Team> sortByPoints(final List<Team> data) {
        return  data.stream()
        .sorted(Comparator.comparing(Team-> Team.getRankGlobal().getPoints()))
        .collect(Collectors.toList());
    }

    /* Tri par nombre de points */
    public static List<Team> sortByGoalsFor(final List<Team> data) {
        return  data.stream()
                .sorted(Comparator.comparing(Team-> Team.getRankGlobal().getGFTotal()))
                .collect(Collectors.toList());
    }
}
