package com.mio.miocma2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Farmers extends AppCompatActivity {
    private static final String TAG = "Farmers";
    private static final String JSON_URL = PublicURL.getAPIURL()+"farmer.php";
    ListView listView;
    private List<FarmerItem> farmerItems;
    private CollectorHelper collectorHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmers);
        collectorHelper = new CollectorHelper(this);
        farmerItems = new ArrayList<>();
        //loadPlayer(); // use this for GET method
        postNewComment(); // use this for POST Method
        setContentView(R.layout.activity_farmers);
        listView =  findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FarmerItem obj = (FarmerItem) parent.getAdapter().getItem(position);
                //Toast.makeText(getApplicationContext(),"Selected "+obj.fname,Toast.LENGTH_LONG).show();
                Log.i(TAG, "SELECTED : "+obj.fname);
            }
        });
    }

    private void loadPlayer() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray playerArray = obj.getJSONArray("data");
                            //Toast.makeText(getApplicationContext(),"Lihat data"+playerArray.length(),Toast.LENGTH_LONG).show();
                            for (int i = 0; i < playerArray.length(); i++) {
                                JSONObject playerObject = playerArray.getJSONObject(i);
                                FarmerItem playerItem = new FarmerItem(playerObject.getString("FCode"),
                                        playerObject.getString("FName"),
                                        playerObject.getString("FAddress"),
                                        playerObject.getString("FGender"),
                                        playerObject.getString("FMPhone"),
                                        playerObject.getString("FPhoto"));
                                farmerItems.add(playerItem);
                            }
                            ListViewAdapter adapter = new ListViewAdapter(farmerItems, getApplicationContext());
                            listView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    //post data by code collector to view collector's farmers, send params collno
    public void postNewComment(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST,JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray playerArray = obj.getJSONArray("data");
                    //Toast.makeText(getApplicationContext(),"Lihat data"+playerArray.length(),Toast.LENGTH_LONG).show();
                    for (int i = 0; i < playerArray.length(); i++) {
                        JSONObject playerObject = playerArray.getJSONObject(i);
                        FarmerItem playerItem = new FarmerItem(playerObject.getString("FCode"),
                                playerObject.getString("FName"),
                                playerObject.getString("FAddress"),
                                playerObject.getString("FGender"),
                                playerObject.getString("FMPhone"),
                                playerObject.getString("FPhoto"));
                        farmerItems.add(playerItem);
                    }
                    ListViewAdapter adapter = new ListViewAdapter(farmerItems, getApplicationContext());
                    listView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("collno",collectorHelper.getNO());
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }
    public interface PostCommentResponseListener {
        public void requestStarted();
        public void requestCompleted();
        public void requestEndedWithError(VolleyError error);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(getApplicationContext(),"Back pressed",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Farmers.this,WelcomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        Farmers.this.finish();
    }
}
