package fr.bet.unibet_football_data.beans;

public class Match {

    private String Div; // Nom de la division
    private String Date; // Date du match
    private String Time; // Heure du match
    private String HomeTeam; // Equipe Home
    private String AwayTeam; // Equipe Away
    private Integer FTHG; // Nombre de buts équipe home
    private Integer FTAG; // Nombre de buts équipe away
    private Integer HS; //   Nombre de tirs au but home
    private Integer AS; //   Nombre de tirs au but away
    private Integer HST; // Nombre de tirs cadrés home
    private Integer AST; // Nombre de tirs cadrés away
    private Integer HY; // Nombre de cartons jaune dans le match home
    private Integer AY; // Nombre de cartons jaune dans le match away
    private Integer HR; // Nombre de cartons rouge dans le match home
    private Integer AR; // Nombre de cartons rouge dans le match away


    public String getDiv() {
        return Div;
    }

    public void setDiv(String div) {
        Div = div;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getHomeTeam() {
        return HomeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        HomeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return AwayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        AwayTeam = awayTeam;
    }

    public Integer getFTHG() {
        return FTHG;
    }

    public void setFTHG(Integer FTHG) {
        this.FTHG = FTHG;
    }

    public Integer getFTAG() {
        return FTAG;
    }

    public void setFTAG(Integer FTAG) {
        this.FTAG = FTAG;
    }

    public Integer getHS() {
        return HS;
    }

    public void setHS(Integer HS) {
        this.HS = HS;
    }

    public Integer getAS() {
        return AS;
    }

    public void setAS(Integer AS) {
        this.AS = AS;
    }

    public Integer getHST() {
        return HST;
    }

    public void setHST(Integer HST) {
        this.HST = HST;
    }

    public Integer getAST() {
        return AST;
    }

    public void setAST(Integer AST) {
        this.AST = AST;
    }

    public Integer getHY() {
        return HY;
    }

    public void setHY(Integer HY) {
        this.HY = HY;
    }

    public Integer getAY() {
        return AY;
    }

    public void setAY(Integer AY) {
        this.AY = AY;
    }

    public Integer getHR() {
        return HR;
    }

    public void setHR(Integer HR) {
        this.HR = HR;
    }

    public Integer getAR() {
        return AR;
    }

    public void setAR(Integer AR) {
        this.AR = AR;
    }

    @Override
    public String toString() {
        return "Match{" +
                "Nom de la division ='" + Div + '\'' +
                ", Date du match ='" + Date + '\'' +
                ", Heure du match ='" + Time + '\'' +
                ", Equipe Home ='" + HomeTeam + '\'' +
                ", Equipe Away ='" + AwayTeam + '\'' +
                ", Nombre de buts équipe home =" + FTHG +
                ", Nombre de buts équipe away =" + FTAG +
                ", Nombre de tirs au but home =" + HS +
                ", Nombre de tirs au but away =" + AS +
                ", Nombre de tirs cadrés home =" + HST +
                ", Nombre de tirs cadrés away =" + AST +
                ", Nombre de cartons jaune dans le match home =" + HY +
                ", Nombre de cartons jaune dans le match away =" + AY +
                ", Nombre de cartons rouge dans le match home =" + HR +
                ", Nombre de cartons rouge dans le match away =" + AR +
                '}';
    }
}
