package com.example.tadhkirapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class PrayerTimesActivity extends AppCompatActivity {

    private static final Object TAG = "tag";
    String url;

    String tag_json_obj = "json_obj_req";
    ProgressDialog pDialog;
    TextView mSubuhTv, mZohorTv, mAsarTv, mMaghribTv, mIsyakTv, mLocationTv, mDateTv;
    EditText mSearchLocation;
    Button mSearchBtn;
    ImageButton previousbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prayer_times);

        getSupportActionBar().hide();


        mSubuhTv    =findViewById(R.id.subuhTv);
        mZohorTv    =findViewById(R.id.zohorTv);
        mAsarTv     =findViewById(R.id.asarTv);
        mMaghribTv  =findViewById(R.id.maghribTv);
        mIsyakTv    =findViewById(R.id.isyakTv);
        mLocationTv =findViewById(R.id.locationTv);
        mDateTv     =findViewById(R.id.dateTv);

        mSearchLocation     =findViewById(R.id.searchLocation);
        mSearchBtn     =findViewById(R.id.searchBtn);

        mSearchBtn.setOnClickListener (new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String mLocation = mSearchLocation.getText().toString().trim();

                if (mLocation.isEmpty()){
                    Toast.makeText(PrayerTimesActivity.this, "Please enter location", Toast.LENGTH_SHORT).show();
                }
                else{
                    url = "YOUR_MUSLIMSALAT_API_KEY_HERE";
                    searchLocation();
                }
            }
        });


        previousbtn = findViewById(R.id.back_button);

        previousbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });





    }

    private void searchLocation() {

        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String country = response.get ("country").toString();
                            String state = response.get ("state").toString();
                            String city = response.get ("city").toString();
                            String location = country +", "+ state +", "+ city;

                            String date = response.getJSONArray("items").getJSONObject(0).get("date_for").toString();


                            String mSubuh = response.getJSONArray("items").getJSONObject(0).get("fajr").toString();
                            String mZohor = response.getJSONArray("items").getJSONObject(0).get("dhuhr").toString();
                            String mAsar = response.getJSONArray("items").getJSONObject(0).get("asr").toString();
                            String mMaghrib = response.getJSONArray("items").getJSONObject(0).get("maghrib").toString();
                            String mIsyak = response.getJSONArray("items").getJSONObject(0).get("isha").toString();

                            mSubuhTv.setText (mSubuh);
                            mZohorTv.setText (mZohor);
                            mAsarTv.setText (mAsar);
                            mMaghribTv.setText (mMaghrib);
                            mIsyakTv.setText (mIsyak);

                            mLocationTv.setText (location);
                            mDateTv.setText (date);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d((String) TAG, "Error: " + error.getMessage());
                Toast.makeText(PrayerTimesActivity.this, "Error", Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                pDialog.hide();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }


}