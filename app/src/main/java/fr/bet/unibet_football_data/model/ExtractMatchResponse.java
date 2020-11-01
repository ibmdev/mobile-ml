package fr.bet.unibet_football_data.model;

import java.util.List;

import fr.bet.unibet_football_data.beans.Match;

public class ExtractMatchResponse {

    List<Match> homeTeamMatches;
    List<Match> awayTeamMatches;

    public List<Match> getHomeTeamMatches() {
        return homeTeamMatches;
    }

    public void setHomeTeamMatches(List<Match> homeTeamMatches) {
        this.homeTeamMatches = homeTeamMatches;
    }

    public List<Match> getAwayTeamMatches() {
        return awayTeamMatches;
    }

    public void setAwayTeamMatches(List<Match> awayTeamMatches) {
        this.awayTeamMatches = awayTeamMatches;
    }
}
