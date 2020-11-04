package fr.bet.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import com.github.mikephil.charting.charts.BarChart;
import com.google.android.material.button.MaterialButton;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import fr.bet.App;
import fr.bet.R;
import fr.bet.components.material_design.DatePickerComponent;
import fr.bet.tools.material_design.ComponentBuilder;
import fr.bet.tools.Utils;
import fr.bet.tools.mp_android_chart.BarchartComponentBuilder;
import fr.bet.unibet_football_data.beans.Competition;
import fr.bet.unibet_football_data.beans.Team;
import fr.bet.unibet_football_data.model.ExtractMatchRequest;
import fr.bet.unibet_football_data.model.ExtractMatchResponse;
import fr.bet.unibet_football_data.services.ExtractDataService;
import fr.bet.unibet_football_data.services.StatistiqueService;

public class StatsFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {

    private NestedScrollView nestedScrollView;
    private AppCompatSpinner spinnerHomeTeam;
    private AppCompatSpinner spinnerAwayTeam;
    private AppCompatSpinner spinnerCompetition;
    private String homeTeamItemSelected;
    private String awayTeamItemSelected;
    private Integer homeTeamPositionSelected = 0;
    private TextView awayTextView;
    private TextView homeTextView;
    private MaterialButton analytic_stat_button;
    private List<Competition> allCompetitions;
    // Compétition sélectionnée
    Optional<Competition> competition;
    // Date de début
    private MaterialButton date_debut_button;
    private DatePickerComponent datePickerDebut;
    // Date de fin
    private MaterialButton date_fin_button;
    private DatePickerComponent datePickerFin;

    // Liste des matchs à extraire et à analyser
    private ExtractMatchResponse matchesToAnalyze;

    // BarChart Goals for Home Team
    private BarChart chartGFHomeTeam;

    // BarChart Goals for Away Team
    private BarChart chartGFAwayTeam;



    public void selectTeams(final String competition, final String year) {
        String competitionYear = competition + "/"+ year + "/"+competition+".json";
        String jsonData = Utils.getJsonFromAssets(App.getContext(), competitionYear);
        Log.i("datacouk", "jsonData: "+ jsonData);
        // Liste des équipes
        List<String> listTeam = StatistiqueService.getAllTeams(jsonData);
        ArrayAdapter<String> dataAdapter = getDataAdapter(listTeam);
        spinnerHomeTeam.setAdapter(dataAdapter);
        spinnerHomeTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos,long id)
            {
                if(pos == 0) {
                    awayTextView.setVisibility(View.GONE);
                    analytic_stat_button.setVisibility(View.GONE);
                    date_debut_button.setVisibility(View.GONE);
                    date_fin_button.setVisibility(View.GONE);
                    spinnerAwayTeam.setVisibility(View.GONE);
                    spinnerAwayTeam.setAdapter(null);
                    homeTeamPositionSelected = 0;
                    BarchartComponentBuilder.cleanBarChart(chartGFHomeTeam);
                    BarchartComponentBuilder.cleanBarChart(chartGFAwayTeam);
                }
                else if(pos != homeTeamPositionSelected ) {
                    homeTeamPositionSelected = pos;
                    homeTeamItemSelected= (String) parent.getItemAtPosition(homeTeamPositionSelected);
                    if(!"".equals(homeTeamItemSelected)) {
                        awayTextView.setVisibility(View.VISIBLE);
                        analytic_stat_button.setVisibility(View.VISIBLE);
                        date_debut_button.setVisibility(View.VISIBLE);
                        date_fin_button.setVisibility(View.VISIBLE);
                        List<String> listTeamAway = listTeam.stream().filter(team -> !team.equals(homeTeamItemSelected)).collect(Collectors.toList());
                        ArrayAdapter<String> dataAdapterAway = getDataAdapter(listTeamAway);
                        spinnerAwayTeam.setAdapter(dataAdapterAway);
                        spinnerAwayTeam.setVisibility(View.VISIBLE);
                        BarchartComponentBuilder.cleanBarChart(chartGFHomeTeam);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> view)
            {
                Log.i("datacouk", "Home Team no selected : ");
            }
        });
        spinnerAwayTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos,long id)
            {
                awayTeamItemSelected= (String) parent.getItemAtPosition(pos);
                List<String> listTeamHome = listTeam;
                spinnerHomeTeam.setAdapter(getDataAdapter(listTeamHome));
                spinnerHomeTeam.setSelection(homeTeamPositionSelected);
                BarchartComponentBuilder.cleanBarChart(chartGFAwayTeam);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {}
        });
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        awayTextView = view.findViewById(R.id.awayteam);
        homeTextView = view.findViewById(R.id.hometeam);
        spinnerHomeTeam = view.findViewById(R.id.spinnerHomeTeam);
        spinnerAwayTeam = view.findViewById(R.id.spinnerAwayTeam);
        analytic_stat_button = view.findViewById(R.id.analyticStatsButton);
        analytic_stat_button.setOnClickListener(this);
        spinnerCompetition = view.findViewById(R.id.spinnerCompetition);
        // Date de début
        date_debut_button = view.findViewById(R.id.date_debut);
        date_debut_button.setOnClickListener(this);
        datePickerDebut = new DatePickerComponent();
        datePickerDebut.setDatePicker(ComponentBuilder.createDatePicker(getContext(), datePickerDebut, date_debut_button));
        // Date de fin
        date_fin_button = view.findViewById(R.id.date_fin);
        date_fin_button.setOnClickListener(this);
        datePickerFin = new DatePickerComponent();
        datePickerFin.setDatePicker(ComponentBuilder.createDatePicker(getContext(), datePickerFin, date_fin_button));

        // BarChart
        chartGFHomeTeam = view.findViewById(R.id.chartHomeGF);
        chartGFAwayTeam = view.findViewById(R.id.chartAwayGF);

        String jsonLeagueCode = Utils.getJsonFromAssets(App.getContext(), "LeagueCode.json");
        allCompetitions = StatistiqueService.getAllCompetition(jsonLeagueCode);
        ArrayAdapter<String> dataAdapter = getDataAdapter(StatistiqueService.getAllCompetitionAsString(allCompetitions));
        spinnerCompetition.setAdapter(dataAdapter);
        spinnerCompetition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View viewAdapter, int pos,long id)
            {
                if(pos == 0) {
                    homeTextView.setVisibility(View.GONE);
                    spinnerHomeTeam.setVisibility(View.GONE);
                    spinnerHomeTeam.setAdapter(null);
                    awayTextView.setVisibility(View.GONE);
                    spinnerAwayTeam.setVisibility(View.GONE);
                    spinnerAwayTeam.setAdapter(null);
                    analytic_stat_button.setVisibility(View.GONE);
                    date_debut_button.setVisibility(View.GONE);
                    date_fin_button.setVisibility(View.GONE);
                    BarchartComponentBuilder.cleanBarChart(chartGFHomeTeam);
                    BarchartComponentBuilder.cleanBarChart(chartGFAwayTeam);

                } else {
                    String league = (String) parent.getItemAtPosition(pos);
                    homeTextView.setVisibility(View.VISIBLE);
                    spinnerHomeTeam.setVisibility(View.VISIBLE);
                    competition =  allCompetitions.stream().filter(c -> league!=null &&  league.equals(c.getLeague())).findFirst();
                    selectTeams(competition.get().getCode(), String.valueOf(LocalDate.now().getYear()));

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> av)
            {}
        });
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        nestedScrollView = (NestedScrollView) inflater.inflate(R.layout.fragment_stats, container, false);
        return nestedScrollView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.analyticStatsButton:
                // Extraction des paths des fichiers à analyser
                List<String> pathFiles =  ExtractDataService.getListFilesTonAnalyze(competition.get().getCode(), datePickerDebut.getDateAsString(), datePickerFin.getDateAsString());
                ExtractMatchRequest matchRequest = new ExtractMatchRequest();
                matchRequest.setPaths(pathFiles);
                matchRequest.setDateDebut(datePickerDebut.getDateAsString());
                matchRequest.setDateFin(datePickerFin.getDateAsString());
                matchRequest.setHomeTeam(homeTeamItemSelected);
                matchRequest.setAwayTeam(awayTeamItemSelected);
                matchesToAnalyze = ExtractDataService.extractMatches(matchRequest);
                matchesToAnalyze.getHomeTeamMatches().stream().forEach(m -> Log.i("datacouk", "Home Matches" + m.toString()));
                matchesToAnalyze.getAwayTeamMatches().stream().forEach(m -> Log.i("datacouk", "Away Matches" + m.toString()));
                BarchartComponentBuilder.createBarChartGoalsFor(chartGFHomeTeam, matchesToAnalyze.getHomeTeamMatches(), homeTeamItemSelected);
                BarchartComponentBuilder.createBarChartGoalsFor(chartGFAwayTeam, matchesToAnalyze.getAwayTeamMatches(), awayTeamItemSelected);
                // Statistiques complete de la home team
                Team homeTeam = StatistiqueService.fillStatsForTeam(homeTeamItemSelected, matchesToAnalyze.getHomeTeamMatches());
                Log.i("datacouk","Team Home Classement : " + homeTeam.getName() + " > " + homeTeam.toString());

                break;
            case R.id.date_debut:
                datePickerDebut.getDatePicker().show();
                break;
            case R.id.date_fin:
                datePickerFin.getDatePicker().show();
                break;
        }
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    private ArrayAdapter getDataAdapter(final List<String> data) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return dataAdapter;
    }
}
