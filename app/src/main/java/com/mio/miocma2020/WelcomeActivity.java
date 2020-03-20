package com.mio.miocma2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class WelcomeActivity extends AppCompatActivity {
    //faster debug Go to Setting (ctrl+alt+s) -> Build, Execution, Deployment -> Compiler set to offline
    private Spinner spCertificate,spGender,spregion;
    private String certificate_spinner_selected="";
    private String gender_spinner_selected="";
    private String region_spinner_selected="";
    private String[] arrayCertificate;
    private String[] gender = {
            "MALE",
            "FEMALE"
    };
    private List<Map<String, Object>> region = new ArrayList<>();
    private TextView tvname,tvhobby;
    private EditText codeid,nameid,phonid,dobid,emailid,nikid,addressid;
    private TextView btnlogout,editbutton;
    private Button buttonfarmer,buttonupdate,buttonTransaction;
    private CollectorHelper collectorHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getResponse(); //tampilkan list region
        getResponseCertificate(); //tampilkan list certificate
        collectorHelper = new CollectorHelper(this);
        tvhobby = (TextView) findViewById(R.id.tvhobby);
        tvname = (TextView) findViewById(R.id.tvname);
        codeid = findViewById(R.id.codeid);
        nameid = findViewById(R.id.nameid);
        dobid = findViewById(R.id.dobid);
        phonid = findViewById(R.id.phoneid);
        emailid = findViewById(R.id.emailid);
        nikid = findViewById(R.id.nikid);
        addressid = findViewById(R.id.addressid);
        btnlogout = (TextView) findViewById(R.id.btn);
        editbutton = findViewById(R.id.editbutton);
        buttonfarmer = findViewById(R.id.farmerbutton);
        buttonupdate = findViewById(R.id.butonsave);
        buttonTransaction = findViewById(R.id.buttonTransaction);
        tvname.setText(collectorHelper.getName());
        tvhobby.setText("Collector No"+collectorHelper.getNO());
        codeid.setText(collectorHelper.getNO());
        nameid.setText(collectorHelper.getName());
        dobid.setText(collectorHelper.getDOB());
        phonid.setText(collectorHelper.getCMPhone());
        emailid.setText(collectorHelper.getEmail());
        nikid.setText(collectorHelper.getNik());
        addressid.setText(collectorHelper.getAddress());
        spCertificate = (Spinner) findViewById(R.id.spinner2);
        spGender = findViewById(R.id.spinner3);
        spregion = findViewById(R.id.spinner4);
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, gender);
        spGender.setAdapter(adapter2);

        /*
        // inisialiasi Array Adapter dengan memasukkan string array di atas
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayCertificate);
        // mengeset Array Adapter tersebut ke Spinner
        spCertificate.setAdapter(adapter);

        // mengeset listener untuk mengetahui saat item dipilih
        spCertificate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // memunculkan toast + value Spinner yang dipilih (diambil dari adapter)
                //Toast.makeText(getApplicationContext(), "Selected "+ adapter.getItem(i), Toast.LENGTH_SHORT).show();
                certificate_spinner_selected = adapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        */

        // inisialiasi Array Adapter Gender dengan memasukkan string array di atas
        final ArrayAdapter<String> adaptergender = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, gender);
        // mengeset Array Adapter tersebut ke Spinner
        spGender.setAdapter(adaptergender);
        // mengeset listener untuk mengetahui saat item dipilih
        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // memunculkan toast + value Spinner yang dipilih (diambil dari adapter)
                //Toast.makeText(getApplicationContext(), "Selected "+ adapter.getItem(i), Toast.LENGTH_SHORT).show();
                gender_spinner_selected = adaptergender.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        /*
        // inisialiasi Array Adapter dengan memasukkan string array di atas
        final ArrayAdapter<String> adapterRegion = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, region);
        // mengeset Array Adapter tersebut ke Spinner
        spregion.setAdapter(adapter);
        // mengeset listener untuk mengetahui saat item dipilih
        spregion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // memunculkan toast + value Spinner yang dipilih (diambil dari adapter)
                //Toast.makeText(getApplicationContext(), "Selected "+ adapter.getItem(i), Toast.LENGTH_SHORT).show();
                region_spinner_selected = adapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

         */
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectorHelper.putIsLogin(false);
                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                WelcomeActivity.this.finish();
            }
        });

        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //codeid.setEnabled(true);
                nameid.setEnabled(true);
                dobid.setEnabled(true);
                phonid.setEnabled(true);
                emailid.setEnabled(true);
                nikid.setEnabled(true);
                addressid.setEnabled(true);
            }
        });
        buttonupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerMe();
                codeid.setEnabled(false);
                nameid.setEnabled(false);
                dobid.setEnabled(false);
                phonid.setEnabled(false);
                emailid.setEnabled(false);
                nikid.setEnabled(false);
                addressid.setEnabled(false);
            }
        });
        buttonfarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this,Farmers.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_out, R.anim.slide_in_bottom);
                //WelcomeActivity.this.finish();
            }
        });
        buttonTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this,Transactions.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.slide_in_bottom);
                //WelcomeActivity.this.finish();
            }
        });
    }

    private void getResponse(){
        //Toast.makeText(getApplicationContext(),"GET RESPONSESSSSSSSSSSSSsss",Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiRegion.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        ApiRegion api = retrofit.create(ApiRegion.class);
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
                        loadRegionToArray(jsonresponse);
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

    private void getResponseCertificate(){
        //Toast.makeText(getApplicationContext(),"GET RESPONSESSSSSSSSSSSS CERTIFICATE",Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiCertificate.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        ApiCertificate api = retrofit.create(ApiCertificate.class);
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
                        loadToArrayCertificate(jsonresponse);
                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");
                        //Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    //menyimpan data ke arrayRegion
    private void loadRegionToArray(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {
                JSONArray dataArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    Map<String, Object> item = new HashMap<>();
                    item.put("Region_ID", dataobj.getString("Region_ID"));
                    item.put("Region_Name", dataobj.getString("Region_Name"));
                    region.add(item);
                }
                SimpleAdapter arrayAdapter = new SimpleAdapter(WelcomeActivity.this, region,
                        android.R.layout.simple_spinner_item,
                        new String[]{"Region_Name"}, new int[]{android.R.id.text1});
                spregion.setAdapter(arrayAdapter);
                spregion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        // memunculkan toast + value Spinner yang dipilih (diambil dari adapter)
                        //Toast.makeText(getApplicationContext(), "Selected "+ adapter3.getItem(i), Toast.LENGTH_SHORT).show();
                        region_spinner_selected = region.get(i).get("Region_ID").toString();
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

    private void loadToArrayCertificate(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {
                JSONArray dataArray = jsonObject.getJSONArray("data");
                arrayCertificate = new String[dataArray.length()]; // buat array sejumlah data
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    arrayCertificate[i] = dataobj.getString("Cert_ID");
                }
                final ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, arrayCertificate);
                spCertificate.setAdapter(adapter3);
                spCertificate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        // memunculkan toast + value Spinner yang dipilih (diambil dari adapter)
                        //Toast.makeText(getApplicationContext(), "Selected "+ adapter3.getItem(i), Toast.LENGTH_SHORT).show();
                        certificate_spinner_selected = adapter3.getItem(i);
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

    private void registerMe() {
        //Toast.makeText(getApplicationContext(),"Register started",Toast.LENGTH_LONG).show();
        String code = codeid.getText().toString();
        String name = nameid.getText().toString();
        int cert = Integer.parseInt(certificate_spinner_selected);
        String gender = gender_spinner_selected;
        String dob = dobid.getText().toString();
        String phone = phonid.getText().toString();
        String email = emailid.getText().toString();
        int region = Integer.parseInt(region_spinner_selected);
        String nik = nikid.getText().toString();
        String address= addressid.getText().toString();
        //Toast.makeText(getApplicationContext(),code,Toast.LENGTH_LONG).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUpdateCollector.REGIURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        APIUpdateCollector api = retrofit.create(APIUpdateCollector.class);

        Call<String> call = api.getUserRegi(code,name,cert,gender,dob,phone,email,region,nik,address);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //Toast.makeText(getApplicationContext(),"RESPONSE BODY "+response.body(),Toast.LENGTH_LONG).show();
                //Toast.makeText()
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        String jsonresponse = response.body().toString();
                        Toast.makeText(getApplicationContext(),"Data Update Successfully",Toast.LENGTH_LONG).show();
                        try {
                            parseRegData(jsonresponse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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
        collectorHelper.putName(name);
        collectorHelper.putNO(code);
        collectorHelper.putDOB(dob);
        collectorHelper.putPhone(phone);
        collectorHelper.putEmail(email);
        collectorHelper.putCERT(String.valueOf(cert));
        collectorHelper.putRegionID(String.valueOf(region));
        collectorHelper.putAddress(address);
        //collectorHelper.putFoto();
        collectorHelper.putNik(nik);
        tvname.setText(collectorHelper.getName());
    }
    private void parseRegData(String response) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        if (jsonObject.optString("status").equals("true")){
            saveInfo(response);
        }else {
            Toast.makeText(WelcomeActivity.this, jsonObject.getString("Something error occured"), Toast.LENGTH_SHORT).show();
        }
    }
    private void saveInfo(String response){
        collectorHelper.putIsLogin(true);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {
                JSONArray dataArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    collectorHelper.putName(dataobj.getString("CName"));
                    collectorHelper.putNO(dataobj.getString("CNo"));
                    collectorHelper.putDOB(dataobj.getString("CDOB"));
                    collectorHelper.putPhone(dataobj.getString("CMPhone"));
                    collectorHelper.putEmail(dataobj.getString("CEmail"));
                    collectorHelper.putCERT(dataobj.getString("CCERT"));
                    collectorHelper.putRegionID(dataobj.getString("CRegion_ID"));
                    collectorHelper.putAddress(dataobj.getString("CAddress"));
                    collectorHelper.putFoto(dataobj.getString("CPhoto"));
                    collectorHelper.putNik(dataobj.getString("CNIK"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
