package com.mio.miocma2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceControl;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.mio.miocma2020.transaction.AddTransaction;
import com.mio.miocma2020.transaction.BarcodeTrans;
import com.mio.miocma2020.transaction.ListTransactionAdapter;
import com.mio.miocma2020.transaction.Transaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Transactions extends AppCompatActivity {
    private static final String TAG = "Transaction";
    private static final String JSON_URL = PublicURL.getAPIURL()+"transaction.php";
    ListView listViewTrans;
    private TextView tvname,tvhobby,btnlogout;
    private CollectorHelper collectorHelper;
    private List<Transaction> transaction_items;
    Button delete,view_qr,buttonback;
    TextView buttonAdd;
    CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        collectorHelper = new CollectorHelper(this);
        tvhobby = (TextView) findViewById(R.id.tvhobby);
        tvname = (TextView) findViewById(R.id.tvname);
        buttonback = findViewById(R.id.farmerbutton);
        btnlogout = findViewById(R.id.btnlogout);
        tvname.setText(collectorHelper.getName());
        tvhobby.setText("Collector No"+collectorHelper.getNO());
        transaction_items = new ArrayList<>();
        //loadPlayer(); // use this for GET method
        postNewComment(); // use this for POST Method
        listViewTrans =  findViewById(R.id.listViewfarmer);
        buttonAdd = findViewById(R.id.addID);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Transactions.this,AddTransaction.class);
                startActivity(intent);
            }
        });
        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Transactions.this,WelcomeActivity.class);
                startActivity(intent);
                Transactions.this.finish();
            }
        });
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectorHelper.putIsLogin(false);
                Intent intent = new Intent(Transactions.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Transactions.this.finish();
            }
        });
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
                        JSONObject object = playerArray.getJSONObject(i);
                        Transaction transaction = new Transaction(object.getString("TransID"),
                                object.getString("CNo"),object.getString("FNo"),object.getString("ProdID"),object.getString("ProdWeight"),object.getString("RDate"),object.getString("FLOT"));
                        transaction_items.add(transaction);
                    }
                    ListTransactionAdapter adapter = new ListTransactionAdapter(transaction_items,getApplicationContext());
                    listViewTrans.setAdapter(adapter);
                    listViewTrans.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            final Transaction obj = (Transaction) parent.getAdapter().getItem(position);
                            //Toast.makeText(getApplicationContext(),"Selected "+obj.getFlot(),Toast.LENGTH_LONG).show();
                            final Dialog dialog = new Dialog(Transactions.this);
                            // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.dialog_confirm);
                            delete = dialog.findViewById(R.id.button33);
                            view_qr = dialog.findViewById(R.id.buttonqr);
                            TextView closed = dialog.findViewById(R.id.textView3close);
                            closed.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    Snackbar.make(findViewById(android.R.id.content), "Data delete successfully", Snackbar.LENGTH_LONG).show();
                                }
                            });
                            view_qr.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(getBaseContext(), BarcodeTrans.class);
                                    intent.putExtra("FLOTCODE", obj.getFlot());
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.zoom_out, R.anim.slide_in_bottom);
                                }
                            });
                            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT)); //remove white corner
                            dialog.show();
                            overridePendingTransition(R.anim.zoom_out, R.anim.slide_in_bottom);
                            //Log.i(TAG, "SELECTED : "+obj.fname);
                            //Intent intent = new Intent(getBaseContext(), BarcodeTrans.class);
                            //intent.putExtra("FLOTCODE", obj.getFlot());
                            //startActivity(intent);
                            /* // displaying snackbar
                            Snackbar.make(findViewById(android.R.id.content), "ddddd", Snackbar.LENGTH_LONG).show();
                             */

                        }

                    });
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
        Transactions.this.finish();
        super.onBackPressed();
    }
}
