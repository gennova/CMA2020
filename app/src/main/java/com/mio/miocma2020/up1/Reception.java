package com.mio.miocma2020.up1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mio.miocma2020.APICollector;
import com.mio.miocma2020.R;
import com.mio.miocma2020.StaffHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Reception extends AppCompatActivity {
    private Spinner spCollector;
    private List<Map<String, Object>> collectors = new ArrayList<>();
    private String collector_spinner_selected="";
    private Button button;
    private StaffHelper staffHelper;
    private TextView staffname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reception);
        spCollector = findViewById(R.id.spinnerCollector);
        button = findViewById(R.id.buttonPrint);
        staffHelper = new StaffHelper(Reception.this);
        staffname = findViewById(R.id.staffnameID);
        staffname.setText(staffHelper.getStaffName().toString());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openURL = new Intent(android.content.Intent.ACTION_VIEW);
                openURL.setData(Uri.parse("http://192.168.99.168/include/examples/example_001.php"));
                startActivity(openURL);
            }
        });
        getResponseCollector();
    }

    private void getResponseCollector(){
        //Toast.makeText(getApplicationContext(),"GET RESPONSESSSSSSSSSSSSsss",Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APICollector.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        APICollector api = retrofit.create(APICollector.class);
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
                    item.put("collectorid", dataobj.getString("CNo"));
                    item.put("collectorname", dataobj.getString("CName"));
                    collectors.add(item);
                }
                SimpleAdapter arrayAdapter = new SimpleAdapter(Reception.this, collectors,
                        android.R.layout.simple_list_item_2,
                        new String[]{"collectorid","collectorname"}, new int[]{android.R.id.text1,android.R.id.text2});
                spCollector.setAdapter(arrayAdapter);
                spCollector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        // memunculkan toast + value Spinner yang dipilih (diambil dari adapter)
                        Toast.makeText(getApplicationContext(), "Selected "+ collectors.get(i).get("collectorid").toString(),Toast.LENGTH_SHORT).show();
                        collector_spinner_selected = collectors.get(i).get("collectorid").toString();
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
}
