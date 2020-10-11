package fr.bet.unibet_football_data.beans;

public class Team {

    public Team(String name) {
        this.name = name;
    }

    private String name;
    private Integer played = 0; // Nombre de matchs joués
    private Integer points = 0; // Total de points
    private Integer GFTotal = 0; // Total de buts Pour
    private Integer GCTotal = 0; // Total de buts Contre
    private Integer CYTotal = 0; // Total de cartons jaune
    private Integer CRTotal = 0; // Total de cartons rouge

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPlayed() {
        return played;
    }

    public void setPlayed(Integer played) {
        this.played = played;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getGFTotal() {
        return GFTotal;
    }

    public void setGFTotal(Integer GFTotal) {
        this.GFTotal = GFTotal;
    }

    public Integer getGCTotal() {
        return GCTotal;
    }

    public void setGCTotal(Integer GCTotal) {
        this.GCTotal = GCTotal;
    }

    public Integer getCYTotal() {
        return CYTotal;
    }

    public void setCYTotal(Integer CYTotal) {
        this.CYTotal = CYTotal;
    }

    public Integer getCRTotal() {
        return CRTotal;
    }

    public void setCRTotal(Integer CRTotal) {
        this.CRTotal = CRTotal;
    }
    @Override
    public String toString() {
        return name + " : [" +
                "Matchs joués = " + played +
                ", Nombre de points = " + points +
                ", Buts Pour = " + GFTotal +
                ", Buts Contre = " + GCTotal +
                ", Cartons jaune = " + CYTotal +
                ", Cartons rouge = " + CRTotal +
                ']';
    }
}
