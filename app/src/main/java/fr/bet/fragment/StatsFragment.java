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
import com.google.android.material.button.MaterialButton;
import java.util.List;
import java.util.stream.Collectors;
import fr.bet.App;
import fr.bet.R;
import fr.bet.tools.Utils;
import fr.bet.unibet_football_data.beans.Competition;
import fr.bet.unibet_football_data.services.StatistiqueService;

public class StatsFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {

    private NestedScrollView nestedScrollView;
    private AppCompatSpinner spinnerHomeTeam;
    private AppCompatSpinner spinnerAwayTeam;
    private AppCompatSpinner spinnerCompetition;
    private String homeTeamItemSelected;
    private Integer homeTeamPositionSelected = 0;
    private TextView awayTextView;
    private MaterialButton analytic_stat_button;
    private List<Competition> allCompetitions;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        awayTextView = view.findViewById(R.id.awayteam);
        analytic_stat_button = view.findViewById(R.id.analyticStatsButton);
        spinnerCompetition = view.findViewById(R.id.spinnerCompetition);
        // Chargement des données
        String jsonLeagueCode = Utils.getJsonFromAssets(App.getContext(), "LeagueCode.json");
        allCompetitions = StatistiqueService.getAllCompetition(jsonLeagueCode);
        Log.i("datacouk", "League codes : " + allCompetitions.size());
        String jsonData = Utils.getJsonFromAssets(App.getContext(), "FL1/2020/FL1.json");
        // Liste des équipes
        List<String> listTeam = StatistiqueService.getAllTeams(jsonData);
        ArrayAdapter<String> dataAdapter = getDataAdapter(listTeam);
        spinnerHomeTeam = view.findViewById(R.id.spinnerHomeTeam);
        spinnerHomeTeam.setAdapter(dataAdapter);
        spinnerHomeTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos,long id)
            {
                if(pos == 0) {
                    awayTextView.setVisibility(View.GONE);
                    analytic_stat_button.setVisibility(View.GONE);
                    spinnerAwayTeam.setVisibility(View.GONE);
                    spinnerAwayTeam.setAdapter(null);
                    homeTeamPositionSelected = 0;
                }
                else if(pos != homeTeamPositionSelected ) {
                    homeTeamPositionSelected = pos;
                    homeTeamItemSelected= (String) parent.getItemAtPosition(homeTeamPositionSelected);
                    if(!"".equals(homeTeamItemSelected)) {
                        awayTextView.setVisibility(View.VISIBLE);
                        analytic_stat_button.setVisibility(View.VISIBLE);
                        List<String> listTeamAway = listTeam.stream().filter(team -> !team.equals(homeTeamItemSelected)).collect(Collectors.toList());
                        ArrayAdapter<String> dataAdapterAway = getDataAdapter(listTeamAway);
                        spinnerAwayTeam.setAdapter(dataAdapterAway);
                        spinnerAwayTeam.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> view)
            {
                Log.i("datacouk", "Home Team no selected : ");
            }
        });
        spinnerAwayTeam = view.findViewById(R.id.spinnerAwayTeam);
        spinnerAwayTeam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos,long id)
            {
                List<String> listTeamHome = listTeam;
                spinnerHomeTeam.setAdapter(getDataAdapter(listTeamHome));
                spinnerHomeTeam.setSelection(homeTeamPositionSelected);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0)
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
    public void onClick(View v) {}
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
