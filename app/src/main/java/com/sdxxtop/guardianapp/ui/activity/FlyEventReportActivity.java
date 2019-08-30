package com.sdxxtop.guardianapp.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.sdxxtop.guardianapp.R;

import androidx.appcompat.app.AppCompatActivity;

public class FlyEventReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fly_event_report);

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(FlyEventReportActivity.this,FlyDataListActivity.class));
            }
        });
    }
}
