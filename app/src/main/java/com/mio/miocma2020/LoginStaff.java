package com.mio.miocma2020;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mio.miocma2020.up1.ProcessingOne;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginStaff extends AppCompatActivity {
    private EditText etUname, etPass;
    private Button btnlogin;
    private StaffHelper staffHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_staff);
        staffHelper = new StaffHelper(this);
        staffHelper = new StaffHelper(this);
        if(staffHelper.getIsLogin()==true){
            Intent intent = new Intent(LoginStaff.this, ProcessingOne.class);
            startActivity(intent);
            LoginStaff.this.finish();
        }
        etUname = (EditText) findViewById(R.id.etusername);
        etPass = (EditText) findViewById(R.id.etpassword);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loginUser();
                LoginStaff.AsyncTaskRunner runner = new LoginStaff.AsyncTaskRunner();
                runner.execute();
            }
        });

    }
    private void loginUser() {
        final String username = etUname.getText().toString().trim();
        final String password = etPass.getText().toString().trim();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APILoginStaff.LOGINURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        APILoginStaff api = retrofit.create(APILoginStaff.class);
        Call<String> call = api.getUserLogin(username,password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //Log.i("Responsestring", response.body().toString());
                //Toast.makeText(getApplicationContext(),response.body().toString(),Toast.LENGTH_LONG).show();
                //Toast.makeText()

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        //      Log.i("onSuccess", response.body().toString());
                        String jsonresponse = response.body().toString();
                        parseLoginData(jsonresponse);

                    } else {
                        //    Log.i("onEmptyResponse", "Returned empty response");
                        Toast.makeText(getApplicationContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Something error responses "+t.getMessage(),Toast.LENGTH_LONG).show();
                //progressDialog.dismiss();
            }
        });

    }
    //menyimpan data ke sharepref dan buka intent baru
    private void parseLoginData(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {
                saveInfo(response);
                Toast.makeText(LoginStaff.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginStaff.this,ProcessingOne.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                this.finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void saveInfo(String response){
        staffHelper.putIsLogin(true);
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {
                JSONArray dataArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    staffHelper.putStaffNO(dataobj.getString("SNo"));
                    staffHelper.putStaffName(dataobj.getString("SName"));
                    staffHelper.putStaffNIK(dataobj.getString("SNIK"));
                    staffHelper.putStaffPosition(dataobj.getString("SPosition"));
                    staffHelper.putStaffAddress(dataobj.getString("SAddress"));
                    staffHelper.putStaffGender(dataobj.getString("SGender"));
                    staffHelper.putStaffPhone(dataobj.getString("SMPhone"));
                    staffHelper.putStaffEmail(dataobj.getString("SEmail"));
                    staffHelper.putStaffPhoto(dataobj.getString("SPhoto"));
                    staffHelper.putStaffRole(dataobj.getString("Role"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        ProgressDialog progressDialog;
        private String resp;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
                //int time = Integer.parseInt(params[0])*1000;
                //resp = "Slept for " + 15 + " seconds";
                for(int i = 0;i<3;i++){
                    Log.i("BACK",String.valueOf(i));
                    Thread.sleep(1000);
                }
                loginUser();
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            progressDialog.dismiss();
            //Toast.makeText(getApplicationContext(),"Finish",Toast.LENGTH_SHORT).show();
        }


        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(LoginStaff.this);
            progressDialog.setMessage("Loading. Please wait...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }


        @Override
        protected void onProgressUpdate(String... text) {

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LoginStaff.this,MainActivity.class);
        startActivity(intent);
        LoginStaff.this.finish();
    }
}
