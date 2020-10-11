package fr.bet.activity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import fr.bet.R;

public class DonateActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        initView();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar_donate);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

}
