package com.mio.miocma2020.up1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.mio.miocma2020.R;
import com.mio.miocma2020.StaffHelper;

public class ProcessingOne extends AppCompatActivity {
    private StaffHelper staffHelper;
    private ImageView receptionButton;
    private ImageView qualification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing_one);
        receptionButton = findViewById(R.id.receptionID);
        qualification = findViewById(R.id.qualificationIDImage);
        receptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Reception.class);
                startActivity(intent);
            }
        });
        qualification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Qualification.class);
                startActivity(intent);
            }
        });
        staffHelper = new StaffHelper(this);
        TextView loginName;
        loginName = findViewById(R.id.textNameLogin);
        loginName.setText(staffHelper.getStaffName());
    }
}
