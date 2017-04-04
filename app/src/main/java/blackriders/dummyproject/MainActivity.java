package blackriders.dummyproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sanwal Singh on 4/4/17.
 */


public class MainActivity extends AppCompatActivity {


    EditText edit_search;
    Spinner spin_country, spin_state, spin_city;

    List<String> countryList;
    List<City> cityList;
    List<State> stateList;
    City city;
    State state;
    Context context = MainActivity.this;
    String stateUrl = "http://demo3.startdesigns.com/KaarigarDhundoAdmin/api_state.php",
            cityUrl = "http://demo3.startdesigns.com/KaarigarDhundoAdmin/api_cities.php?stateID=", TAG = "HOME";

    ProgressDialog dialog;

    String stateID, stateName, cityID, cityName, countryID, latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spin_country = (Spinner) findViewById(R.id.spin_country);
        spin_state = (Spinner) findViewById(R.id.spin_state);
        spin_city = (Spinner) findViewById(R.id.spin_city);

        edit_search = (EditText) findViewById(R.id.edit_search);

        countryList = new ArrayList<>();
        cityList = new ArrayList<>();
        stateList = new ArrayList<>();

        countryList.add(0, "Select Country");
        countryList.add(1, "India");

        city = new City();
        city.setCityName("Select city");
        cityList.add(0, city);

        state = new State();
        state.setStateName("Select state");
        stateList.add(0, state);

        spin_country.setAdapter(new StringAdapter(getApplicationContext(), countryList));

        spin_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    getStateList();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spin_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    String stateId = stateList.get(position).getStateID();
                    getCityList(stateId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    public void getStateList() {

        String tag_string_req = "req_get_state";

        dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait...");
        dialog.show();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                stateUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d(TAG, "Response State : " + response);

                dialog.dismiss();
                try {
                    stateList.clear();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("json_state");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        stateID = object.getString("stateID");
                        stateName = object.getString("stateName");
                        State state = new State(stateID, stateName);
                        stateList.add(state);
                    }

                    spin_state.setAdapter(new StateAdapter(getApplicationContext(), stateList));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Log.e(TAG, "Error Profile Lock: " + error.getMessage());
            }
        });
        strReq.setRetryPolicy(new RetryPolicy() {

            @Override
            public void retry(VolleyError arg0) throws VolleyError {
            }

            @Override
            public int getCurrentTimeout() {
                return 0;
            }

            @Override
            public int getCurrentRetryCount() {
                return 0;
            }
        });
        strReq.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void getCityList(String stateId) {

        String tag_string_req = "req_get_city";

        dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait...");
        dialog.show();


        StringRequest strReq = new StringRequest(Request.Method.POST,
                cityUrl.concat(stateId), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d(TAG, "Response City : " + response);

                dialog.dismiss();
                try {
                    cityList.clear();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("json_city");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        cityID = object.getString("cityID");
                        cityName = object.getString("cityName");
                        stateID = object.getString("stateID");
                        countryID = object.getString("countryID");
                        latitude = object.getString("latitude");
                        longitude = object.getString("longitude");
                        City city = new City(cityID, cityName, stateID, countryID, latitude, longitude);
                        cityList.add(city);
                    }

                    spin_city.setAdapter(new CityAdapter(getApplicationContext(), cityList));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Log.e(TAG, "Error Profile Lock: " + error.getMessage());
            }
        });
        strReq.setRetryPolicy(new RetryPolicy() {

            @Override
            public void retry(VolleyError arg0) throws VolleyError {
            }

            @Override
            public int getCurrentTimeout() {
                return 0;
            }

            @Override
            public int getCurrentRetryCount() {
                return 0;
            }
        });
        strReq.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}
