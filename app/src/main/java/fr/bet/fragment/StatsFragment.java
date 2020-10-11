package fr.bet.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import fr.bet.App;
import fr.bet.R;
import fr.bet.tools.Utils;

public class StatsFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {

    private NestedScrollView nestedScrollView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Chargement des données
        String jsonData = Utils.getJsonFromAssets(App.getContext(), "F1/2020/F1.json");
        Log.i("datacouk", "jsonData : " + jsonData);
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
}
