package blackriders.dummyproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

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
    List<Karrigar> karrigarList;
    City city;
    State state;
    Context context = MainActivity.this;
    String stateUrl = "http://demo3.startdesigns.com/KaarigarDhundoAdmin/api_state.php",
            cityUrl = "http://demo3.startdesigns.com/KaarigarDhundoAdmin/api_cities.php?stateID=",
            searchUrl = "http://demo3.startdesigns.com/KaarigarDhundoAdmin/api_kaarigar_search.php?id=",
            TAG = "HOME";

    ProgressDialog dialog;

    String stateID, stateName, cityID, cityName, countryID, latitude, longitude, searchtext, k_id, k_name, k_fname,
            k_email, k_number, k_city, k_address, k_password, k_image, k_status, k_experience, k_specialization,
            main_cat_id, sub_cat_id, child_cat_id, k_lat, k_long, k_created_at;

    ImageView img_search;

    ListView list_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spin_country = (Spinner) findViewById(R.id.spin_country);
        spin_state = (Spinner) findViewById(R.id.spin_state);
        spin_city = (Spinner) findViewById(R.id.spin_city);

        edit_search = (EditText) findViewById(R.id.edit_search);

        img_search = (ImageView) findViewById(R.id.img_search);

        list_data = (ListView) findViewById(R.id.list_data);

        countryList = new ArrayList<>();
        cityList = new ArrayList<>();
        stateList = new ArrayList<>();
        karrigarList = new ArrayList<>();

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
                closeKeyboard();
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
                closeKeyboard();
                if (position != 0) {
                    String stateId = stateList.get(position).getStateID();
                    getCityList(stateId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchtext = edit_search.getText().toString().trim();
                closeKeyboard();
                if (!searchtext.equalsIgnoreCase("")) {
                    karrigarList.clear();
                    getListOfKaarigar(searchtext);
                } else {
                    Toast.makeText(context, "Enter something to search...!", Toast.LENGTH_SHORT).show();
                }
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


    public void getListOfKaarigar(String searchtext) {

        String tag_string_req = "req_get_city";

        dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait...");
        dialog.show();


        StringRequest strReq = new StringRequest(Request.Method.POST,
                searchUrl.concat(searchtext), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d(TAG, "Response Get List : " + response);

                dialog.dismiss();
                try {

                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        k_id = object.getString("k_id");
                        k_name = object.getString("k_name");
                        k_fname = object.getString("k_fname");
                        k_email = object.getString("k_email");
                        k_number = object.getString("k_number");
                        k_city = object.getString("k_city");
                        k_address = object.getString("k_address");
                        k_password = object.getString("k_password");
                        k_image = object.getString("k_image");
                        k_status = object.getString("k_status");
                        k_experience = object.getString("k_experience");
                        k_specialization = object.getString("k_specialization");
                        main_cat_id = object.getString("main_cat_id");
                        sub_cat_id = object.getString("sub_cat_id");
                        child_cat_id = object.getString("child_cat_id");
                        k_lat = object.getString("k_lat");
                        k_long = object.getString("k_long");
                        k_created_at = object.getString("k_created_at");

                        Karrigar karrigar = new Karrigar(k_id, k_name, k_fname, k_email, k_number, k_city, k_address,
                                k_password, k_image, k_status, k_experience, k_specialization, main_cat_id, sub_cat_id,
                                child_cat_id, k_lat, k_long, k_created_at);
                        karrigarList.add(karrigar);
                    }

                    list_data.setAdapter(new KarrigarAdapter(context, karrigarList));

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

    public void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
