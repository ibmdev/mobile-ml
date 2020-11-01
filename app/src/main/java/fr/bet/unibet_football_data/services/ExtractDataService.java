package fr.bet.unibet_football_data.services;

import android.util.Log;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import fr.bet.App;
import fr.bet.tools.Utils;
import fr.bet.unibet_football_data.beans.Match;
import fr.bet.unibet_football_data.model.ExtractMatchRequest;
import fr.bet.unibet_football_data.model.ExtractMatchResponse;

public class ExtractDataService {

    final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static List<String> getListFilesTonAnalyze(final String competition, final String dateDebut, final String dateFin) {
        LocalDate dateTimeDebut = null;
        LocalDate dateTimeFin = null;
        List<String> listFiles = new ArrayList<>();
        if(dateDebut != null && !dateDebut.isEmpty()) {
            dateTimeDebut = LocalDate.parse(dateDebut, formatter);
        }
        if(dateFin != null && !dateFin.isEmpty()) {
            dateTimeFin = LocalDate.parse(dateFin, formatter);
        }
        if(dateTimeDebut == null && dateTimeFin == null) {
            listFiles.add(String.valueOf(LocalDateTime.now().getYear()));
        }
        else if(dateTimeDebut !=null && dateTimeFin == null) {
            listFiles.add(String.valueOf(dateTimeDebut.getYear()));
        }
        else if(dateTimeDebut ==null && dateTimeFin != null) {
            listFiles.add(String.valueOf(dateTimeFin.getYear()));

        }
        else {
            long diffYear = ChronoUnit.YEARS.between(dateTimeDebut, dateTimeFin);
            int yearDebut = dateTimeDebut.getYear();
            int yearFin = dateTimeFin.getYear();
            listFiles.add(String.valueOf(dateTimeDebut.getYear()));
            listFiles.addAll(IntStream.iterate(1, i -> i + 1)
                    .limit(diffYear)
                    .mapToObj(i -> String.valueOf(yearDebut + i))
                    .collect(Collectors.toList()));
            if(!listFiles.contains(String.valueOf(yearFin))) {
                listFiles.add(String.valueOf(yearFin));
            }
        }

        listFiles = listFiles.stream().map(year -> competition + "/"+ year + "/"+competition+".json").collect(Collectors.toList());
        return listFiles;
    }
    // Extraction des matchs à analyser
    public static ExtractMatchResponse extractMatches(final ExtractMatchRequest request) {
        ExtractMatchResponse matchResponse = new ExtractMatchResponse();
        List<Match> allMatchs = new ArrayList<>();
        request.getPaths().stream().forEach(path -> {
            String jsonData = Utils.getJsonFromAssets(App.getContext(), path);
            if(jsonData != null) {
                allMatchs.addAll(StatistiqueService.getAllMatchs(jsonData));
            }
        });
        Log.i("datacouk","Nombre de matchs extraits : " + allMatchs.size());
        // Extraction des matchs de la Home Team
        matchResponse.setHomeTeamMatches(allMatchs.stream()
                .filter(team -> team.getHomeTeam().equals(request.getHomeTeam()) || team.getAwayTeam().equals(request.getHomeTeam()))
                .collect(Collectors.toList()));
        // Extraction des matchs de la Away Team
        matchResponse.setAwayTeamMatches(allMatchs.stream()
                .filter(team -> team.getHomeTeam().equals(request.getAwayTeam()) || team.getAwayTeam().equals(request.getAwayTeam()))
                .collect(Collectors.toList()));

        if(request.getDateDebut() != null && !request.getDateDebut().isEmpty() ) {
            matchResponse.setHomeTeamMatches(matchResponse.getHomeTeamMatches()
                    .stream()
                    .filter(m -> Utils.convertStringToLocalDate(m.getDate()).isAfter(Utils.convertStringToLocalDate(request.getDateDebut()))).collect(Collectors.toList()));

            matchResponse.setAwayTeamMatches(matchResponse.getAwayTeamMatches()
                    .stream()
                    .filter(m -> Utils.convertStringToLocalDate(m.getDate()).isAfter(Utils.convertStringToLocalDate(request.getDateDebut()))).collect(Collectors.toList()));
        }
        if(request.getDateFin() != null && !request.getDateFin().isEmpty() ) {
            matchResponse.setHomeTeamMatches(matchResponse.getHomeTeamMatches()
                    .stream()
                    .filter(m -> Utils.convertStringToLocalDate(m.getDate()).isBefore(Utils.convertStringToLocalDate(request.getDateFin()))).collect(Collectors.toList()));

            matchResponse.setAwayTeamMatches(matchResponse.getAwayTeamMatches()
                    .stream()
                    .filter(m -> Utils.convertStringToLocalDate(m.getDate()).isBefore(Utils.convertStringToLocalDate(request.getDateFin()))).collect(Collectors.toList()));
        }

        Log.i("datacouk","Nombre de matchs Home Team : " + matchResponse.getHomeTeamMatches().size());
        Log.i("datacouk","Nombre de matchs Away Team : " + matchResponse.getAwayTeamMatches().size());
        return matchResponse;
    }
}
