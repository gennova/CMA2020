package com.mio.miocma2020.transaction;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mio.miocma2020.CollectorHelper;
import com.mio.miocma2020.PublicURL;
import com.mio.miocma2020.R;
import com.mio.miocma2020.Transactions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AddTransaction extends AppCompatActivity {
    private static final String JSON_URL = PublicURL.getAPIURL()+"farmer.php";
    private CollectorHelper collectorHelper;
    private TextView tvname,tvhobby;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    EditText dateReceive,textPopUpProduct,filterproduct,farmerTextPopUP,filterfarmer,pweight;
    // Listview Adapter
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapterfarmer;
    List<Map<String, Object>> list_products = new ArrayList<>();
    List<Map<String, Object>> list_farmers = new ArrayList<>();
    private SharedPreferences prefs_insertdata,product_filter_selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        prefs_insertdata = getApplicationContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        product_filter_selected = getApplicationContext().getSharedPreferences("selected",Context.MODE_PRIVATE);
        collectorHelper = new CollectorHelper(this);
        tvhobby = findViewById(R.id.tvhobby);
        tvname = findViewById(R.id.tvname);
        tvname.setText(collectorHelper.getName());
        tvhobby.setText("Collector No"+collectorHelper.getNO());
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        dateReceive = findViewById(R.id.textDateReceivedID);
        textPopUpProduct = findViewById(R.id.textProductID);
        farmerTextPopUP = findViewById(R.id.farmerNoID);
        pweight = findViewById(R.id.textWeightID);
        Button savetrans = findViewById(R.id.buttonInsert);
        savetrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertTransaction();
                Intent intent = new Intent(AddTransaction.this, Transactions.class);
                startActivity(intent);
                AddTransaction.this.finish();
            }
        });
        farmerTextPopUP.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    postNewComment();
                    //Toast.makeText(getApplicationContext(),collectorHelper.getNO(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        dateReceive.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    list_products = new ArrayList<>();//set empty
                    showDateDialog();
                }
            }
        });
        textPopUpProduct.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    getResponseProduct(AddTransaction.this);
                }
            }
        });
    }

    private void showDateDialog(){

        /*
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /*
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /*
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /*
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                dateReceive.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }
    public void showDialog(Activity activity){
        //getResponseProduct();
        ListView lv;
        final Dialog dialog = new Dialog(activity);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_list_product);
        //setContentView(R.layout.dialog_listview);
        // Listview Data
        final String products[] = {"Dell Inspiron", "HTC One X", "HTC Wildfire S", "HTC Sense", "HTC Sensation XE",
                "iPhone 4S", "Samsung Galaxy Note 800",
                "Samsung Galaxy S3", "MacBook Air", "Mac Mini", "MacBook Pro"};

        lv = (ListView) dialog.findViewById(R.id.list_view_product);
        filterproduct = (EditText) dialog.findViewById(R.id.inputSearchProduct);

        // Adding items to listview
        adapter = new ArrayAdapter<String>(this, R.layout.list_item_product, R.id.product_name, products);
        lv.setAdapter(adapter);
        filterproduct.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                AddTransaction.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),parent.getItemAtPosition(position).toString()+list_products.size(),Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    private void getResponseProduct(Activity activity){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIProduct.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        APIProduct api = retrofit.create(APIProduct.class);
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
                        loadProductToArray(jsonresponse,AddTransaction.this);
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
    //menyimpan data ke array Product
    private void loadProductToArray(String response,Activity activity){
        //Toast.makeText(getApplicationContext(),"INI DI LOAD PRODUCT",Toast.LENGTH_SHORT).show();
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equals("true")) {
                JSONArray dataArray = jsonObject.getJSONArray("data");
                list_products = new ArrayList<>();
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    Map<String, Object> item = new HashMap<>();
                    item.put("ProdID", dataobj.getString("ProdID"));
                    item.put("ProdNameEN", dataobj.getString("ProdNameEN"));
                    list_products.add(item);
                }
            }
            //getResponseProduct();
            ListView lv;
            final Dialog dialog = new Dialog(activity);
            // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_list_product);
            //setContentView(R.layout.dialog_listview);
            // Listview Data
            final String products[] = new String[list_products.size()];
            for (int i=0;i<list_products.size();i++){
                products[i] = list_products.get(i).get("ProdID").toString()+" - "+list_products.get(i).get("ProdNameEN").toString();
            }
            lv = (ListView) dialog.findViewById(R.id.list_view_product);
            filterproduct = dialog.findViewById(R.id.inputSearchProduct);

            // Adding items to listview
            adapter = new ArrayAdapter<String>(this, R.layout.list_item_product, R.id.product_name, products);
            lv.setAdapter(adapter);
            filterproduct.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                    // When user changed the Text
                    AddTransaction.this.adapter.getFilter().filter(cs);
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    // TODO Auto-generated method stub
                }
            });
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(getApplicationContext(),parent.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();
                    String code = parent.getItemAtPosition(position).toString();
                    String code_trim = code.substring(0,2);
                    textPopUpProduct.setText(code_trim); //original
                    //textPopUpProduct.setText(String.valueOf());
                    SharedPreferences.Editor editor = prefs_insertdata.edit();
                    editor.putString("product_id", code_trim);
                    editor.commit();
                    editor.apply();
                    dialog.dismiss();
                }
            });
            dialog.show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //post data by code collector to view collector's farmers, send params collno
    public void postNewComment(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST,JSON_URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray playerArray = obj.getJSONArray("data");
                    for (int i = 0; i < playerArray.length(); i++) {
                        JSONObject farmerObject = playerArray.getJSONObject(i);
                        Map<String, Object> item = new HashMap<>();
                        item.put("FNo", farmerObject.getString("FNo"));
                        item.put("FName", farmerObject.getString("FName"));
                        item.put("FCert", farmerObject.getString("FCert"));
                        list_farmers.add(item);
                    }
                    //Toast.makeText(getApplicationContext(),"Lihat data"+list_farmers.size(),Toast.LENGTH_LONG).show();
                    ListView lv;
                    final Dialog dialog = new Dialog(AddTransaction.this);
                    // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.dialog_list_farmer);
                    //setContentView(R.layout.dialog_listview);
                    // Listview Data
                    final String products[] = new String[list_farmers.size()];
                    for (int i=0;i<list_farmers.size();i++){
                        products[i] = list_farmers.get(i).get("FNo").toString()+" - "+list_farmers.get(i).get("FName").toString();
                    }
                    filterfarmer = (EditText) dialog.findViewById(R.id.inputSearchFarmer);
                    lv = (ListView) dialog.findViewById(R.id.list_view_farmer);
                    // Adding items to listview
                    adapter = new ArrayAdapter<String>(AddTransaction.this, R.layout.list_item_farmers, R.id.farmer_name, products);
                    lv.setAdapter(adapter);
                    filterfarmer.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                            // When user changed the Text
                            AddTransaction.this.adapter.getFilter().filter(cs);
                        }

                        @Override
                        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                                      int arg3) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void afterTextChanged(Editable arg0) {
                            // TODO Auto-generated method stub
                        }
                    });
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String code = parent.getItemAtPosition(position).toString();
                            String code_trim = code.substring(0,9);
                            farmerTextPopUP.setText(code_trim);
                            SharedPreferences.Editor editor = prefs_insertdata.edit();
                            editor.putString("farmer_code", code_trim);
                            editor.commit();
                            editor.apply();
                            //Toast.makeText(getApplicationContext(),list_farmers.get(position).get("FNo").toString(),Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
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

    private void InsertTransaction() {
        //Toast.makeText(getApplicationContext(),"Register started",Toast.LENGTH_LONG).show();
        String lastUniqueNumber = String.valueOf(generateUniqueNumber());
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        String farmercertificate = null;
        String cno = collectorHelper.getNO();
        String fno = farmerTextPopUP.getText().toString();
        for (int i=0;i<list_farmers.size();i++){
            String code = list_farmers.get(i).get("FNo").toString();
            if(code.equalsIgnoreCase(fno)){
                Log.i("KODE FARMERS LIHATLAH ", code+" "+fno);
                farmercertificate = list_farmers.get(i).get("FCert").toString();
            }
        }
        String prodid = textPopUpProduct.getText().toString();
        double product_weight = Double.parseDouble(pweight.getText().toString());
        String rdate = dateReceive.getText().toString();
        String flot =farmercertificate+cno+fno+prodid+rdate.replace("-","");
        String flot2 =flot+"*"+lastUniqueNumber.toString();
        String qrcode =flot2+".pdf";
        //Toast.makeText(getApplicationContext(),code,Toast.LENGTH_LONG).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIInsertTransaction.REGIURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        APIInsertTransaction api = retrofit.create(APIInsertTransaction.class);

        Call<String> call = api.InsertTransaction(cno,fno,prodid,product_weight,rdate,flot2,qrcode);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //Toast.makeText(getApplicationContext(),"RESPONSE BODY "+response.body(),Toast.LENGTH_LONG).show();
                //Toast.makeText()
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        String jsonresponse = response.body().toString();
                        Toast.makeText(getApplicationContext(),"Data Inserted Successfully",Toast.LENGTH_LONG).show();
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
    public int generateUniqueNumber() {
        Random r = new Random( System.currentTimeMillis() );
        return 100 + r.nextInt(999);
    }
}
