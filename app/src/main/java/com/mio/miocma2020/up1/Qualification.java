package com.mio.miocma2020.up1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mio.miocma2020.APICollector;
import com.mio.miocma2020.APIFlot;
import com.mio.miocma2020.R;
import com.mio.miocma2020.StaffHelper;
import com.mio.miocma2020.transaction.APIInsertTransaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Qualification extends AppCompatActivity {
    private Spinner spFlot;
    private List<Map<String, Object>> collectors = new ArrayList<>();
    private String collector_spinner_selected="";
    private String collector_spinner_selectedWeight="";
    private Button button,buttonSubmit;
    private TextView weightOrigin;
    private StaffHelper staffHelper;
    private TextView staffname,staffno;
    private EditText weight1,weight2,weight3r,noterejected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qualification);
        spFlot = findViewById(R.id.spinnerflot);
        button = findViewById(R.id.buttonPrint);
        weightOrigin = findViewById(R.id.weightIDtext);
        staffHelper = new StaffHelper(Qualification.this);
        staffname = findViewById(R.id.staffnameID);
        staffno = findViewById(R.id.staffnoID);
        staffname.setText(staffHelper.getStaffName().toString());
        staffno.setText(staffHelper.getStaffNO().toString());
        weight1 = findViewById(R.id.weightc1);
        weight2 = findViewById(R.id.weightc2);
        weight3r = findViewById(R.id.weightc3);
        noterejected = findViewById(R.id.rejectReasonID);
        buttonSubmit = findViewById(R.id.buttonSubmitID);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertQualification();
            }
        });
        getResponseCollector();
    }
    private void getResponseCollector(){
        //Toast.makeText(getApplicationContext(),"GET RESPONSESSSSSSSSSSSSsss",Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIFlot.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        APIFlot api = retrofit.create(APIFlot.class);
        Call<String> call = api.getString();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("Responsestring", response.body().toString());
                //Toast.makeText()
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());
                        String jsonresponse = response.body().toString();
                        loadCollectorToArray(jsonresponse);
                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
    //menyimpan data ke arrayRegion
    private void loadCollectorToArray(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {
                JSONArray dataArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    Map<String, Object> item = new HashMap<>();
                    item.put("flot", dataobj.getString("FLOT"));
                    item.put("weight", dataobj.getString("ProdWeight"));
                    collectors.add(item);
                }
                SimpleAdapter arrayAdapter = new SimpleAdapter(Qualification.this, collectors,
                        android.R.layout.simple_list_item_1,
                        new String[]{"flot"}, new int[]{android.R.id.text1});
                spFlot.setAdapter(arrayAdapter);
                spFlot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        // memunculkan toast + value Spinner yang dipilih (diambil dari adapter)
                        Toast.makeText(getApplicationContext(), "Selected "+ collectors.get(i).get("weight").toString(),Toast.LENGTH_SHORT).show();
                        collector_spinner_selected = collectors.get(i).get("flot").toString();
                        collector_spinner_selectedWeight = collectors.get(i).get("weight").toString();
                        weightOrigin.setText(collector_spinner_selectedWeight);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void InsertQualification() {
        String flotcode =collector_spinner_selected;
        Double weightc1 = Double.parseDouble(weight1.getText().toString());
        Double weightc2 = Double.parseDouble(weight2.getText().toString());
        Double weightc3r = Double.parseDouble(weight3r.getText().toString());
        int processingno =0 ;
        String staffno=staffHelper.getStaffNO();
        String note = noterejected.getText().toString();

        //Toast.makeText(getApplicationContext(),code,Toast.LENGTH_LONG).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIQualifications.REGIURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        APIQualifications api = retrofit.create(APIQualifications.class);

        Call<String> call = api.InsertTransaction(flotcode,weightc1,weightc2,weightc3r,processingno,staffno,note);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //Toast.makeText(getApplicationContext(),"RESPONSE BODY "+response.body(),Toast.LENGTH_LONG).show();
                //Toast.makeText()
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        String jsonresponse = response.body().toString();
                        Toast.makeText(getApplicationContext(),"DATA INSERTED",Toast.LENGTH_LONG).show();
                        Qualification.this.finish();
                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");
                        Toast.makeText(getApplicationContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(getApplicationContext(), "this is an actual network failure"+t.getMessage(), Toast.LENGTH_SHORT).show();
                    // logging probably not necessary
                }
                else {
                    Toast.makeText(getApplicationContext(), "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                    // todo log to some central bug tracking service
                }
            }
        });
    }
}
