package fr.bet.unibet_football_data.beans;

public class Team {

    public Team(String name) {
        this.name = name;
        setRankGlobal(new Ranking());
        setRankHome(new Ranking());
        setRankAway(new Ranking());
    }

    private String name;
    private Ranking rankHome;
    private Ranking rankAway;
    private Ranking rankGlobal;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ranking getRankHome() {
        return rankHome;
    }

    public void setRankHome(Ranking rankHome) {
        this.rankHome = rankHome;
    }

    public Ranking getRankAway() {
        return rankAway;
    }

    public void setRankAway(Ranking rankAway) {
        this.rankAway = rankAway;
    }

    public Ranking getRankGlobal() {
        return rankGlobal;
    }

    public void setRankGlobal(Ranking rankGlobal) {
        this.rankGlobal = rankGlobal;
    }

    @Override
    public String toString() {
        return "Equipe {" +
                "Nom de l'équipe ='" + name + '\'' +
                ", Classement Home =" + rankHome.toString() +
                ", Classement Away =" + rankAway.toString() +
                ", Classement global =" + rankGlobal.toString() +
                '}';
    }
}
