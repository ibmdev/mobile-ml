package fr.bet.unibet_football_data.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import fr.bet.unibet_football_data.beans.Match;
import fr.bet.unibet_football_data.beans.Team;

public class StatistiqueService {

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
        team.setPlayed(matchesTeam.size());
        matchesTeam.stream().forEach((match) -> {
            if(teamName.equals(match.getHomeTeam())) {
                team.setGFTotal(team.getGFTotal() + match.getFTHG());
                team.setGCTotal(team.getGCTotal() + match.getFTAG());
                team.setCYTotal(team.getCYTotal() + match.getHY());
                team.setCRTotal(team.getCRTotal() + match.getHR());
                if(match.getFTHG() > match.getFTAG()) {
                    team.setPoints(team.getPoints() + 3);
                } else if(match.getFTHG() == match.getFTAG()) {
                    team.setPoints(team.getPoints() + 1);
                }

            } else if(teamName.equals(match.getAwayTeam())) {
                team.setGFTotal(team.getGFTotal() + match.getFTAG());
                team.setGCTotal(team.getGCTotal() + match.getFTHG());
                team.setCYTotal(team.getCYTotal() + match.getAY());
                team.setCRTotal(team.getCRTotal() + match.getAR());
                if(match.getFTAG() > match.getFTHG()) {
                    team.setPoints(team.getPoints() + 3);
                } else if(match.getFTHG() == match.getFTAG()) {
                    team.setPoints(team.getPoints() + 1);
                }
            }
        });
        return team;
    }

    // Utilitaires de tri des données

    /* Tri par nombre de points */
    public static List<Team> sortByPoints(final List<Team> data) {
        return  data.stream()
        .sorted(Comparator.comparing(Team::getPoints))
        .collect(Collectors.toList());
    }

    /* Tri par nombre de points */
    public static List<Team> sortByGoalsFor(final List<Team> data) {
        return  data.stream()
                .sorted(Comparator.comparing(Team::getGFTotal))
                .collect(Collectors.toList());
    }
}
