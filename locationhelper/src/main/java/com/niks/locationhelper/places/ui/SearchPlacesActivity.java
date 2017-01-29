package com.niks.locationhelper.places.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.niks.baseutils.BaseAppCompatActivity;
import com.niks.locationhelper.R;
import com.niks.locationhelper.places.model.PlacePrediction;
import com.niks.locationhelper.places.network.PlacesAPI;
import com.niks.net.callback.OperationCallback;

import java.util.ArrayList;

public class SearchPlacesActivity extends BaseAppCompatActivity {
    private ListView listView;
    private EditText et_search;
    private SearchPlacesAdapter searchPlacesAdapter;
    private TextView tv_reason;
    private ImageView iv_search_places,iv_back;
    private String api_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_places_fragment);
        initializeIds();
        Intent intent = getIntent();
        handleIntent(intent);
    }

    private void initializeIds() {
        iv_back=(ImageView) findViewById(R.id.search_places_back_iv);
        iv_back.setColorFilter(Color.parseColor("#717171"));
        iv_search_places=(ImageView) findViewById(R.id.search_places_iv);
        iv_search_places.setColorFilter(Color.parseColor("#717171"));
        tv_reason = (TextView) findViewById(R.id.search_places_empty_tv);
        listView = (ListView) findViewById(R.id.search_places_lv);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (searchPlacesAdapter != null && searchPlacesAdapter.list != null && searchPlacesAdapter.list.size() > 0) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("success", searchPlacesAdapter.list.get(position));
                    setResult(RESULT_OK, returnIntent);
                    /*SearchPlacesActivity.this.*/
                    finish();
                }
            }
        });
        et_search = (EditText) findViewById(R.id.search_places_et);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence cs, int start, int before, int count) {
                String searched_string = cs.toString();
                if (!TextUtils.isEmpty(searched_string)) {
                    PlacesAPI placesAPI = new PlacesAPI(SearchPlacesActivity.this);
                    placesAPI.setPlace_name(searched_string);
                    placesAPI.setPlace_type(PlacePrediction.PlaceTypes.GEOCODE);
                    placesAPI.getPlacesList(SearchPlacesActivity.this, new OperationCallback() {
                        @Override
                        public void onSuccess(Object response, Object o1) {
                            if (response instanceof ArrayList) {
                                ArrayList<PlacePrediction> places_list = (ArrayList<PlacePrediction>) response;
                                if (places_list.size()>0) {
                                    searchPlacesAdapter = new SearchPlacesAdapter(places_list);
                                    listView.setAdapter(searchPlacesAdapter);
                                    listView.setVisibility(View.VISIBLE);
                                    tv_reason.setVisibility(View.GONE);
                                }else{
                                    tv_reason.setText(R.string.msg_no_location_found);
                                    listView.setVisibility(View.GONE);
                                    tv_reason.setVisibility(View.VISIBLE);
                                }
                            } else {
                                tv_reason.setText(getString(R.string.places_api_failed));
                                listView.setVisibility(View.GONE);
                                tv_reason.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onFailed(Object response, Object o1) {
                            if (response instanceof Integer) {
                                //No internet connection
                                if ((int) response == 1001) {
                                    tv_reason.setText(o1.toString());
                                    listView.setVisibility(View.GONE);
                                    tv_reason.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }, api_key);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void handleIntent(Intent intent) {
        if (intent != null) {
            if (intent.hasExtra("title")) {
                String title = intent.getStringExtra("title");
                setTitle(title);
            }
            if (intent.hasExtra("api_key")) {
                api_key=intent.getStringExtra("api_key");
            }else{
                Intent returnIntent = new Intent();
                returnIntent.putExtra("failure", getString(R.string.msg_provide_server_key));
                setResult(RESULT_OK, returnIntent);
                finish();
            }

            if(intent.hasExtra("dark_actionbar_color")){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(Color.parseColor(intent.getStringExtra("dark_actionbar_color")));
                }
            }
        }
    }

    public class SearchPlacesAdapter extends BaseAdapter {

        public ArrayList<PlacePrediction> list;

        private SearchPlacesAdapter(ArrayList<PlacePrediction> list) {
            super();
            this.list = list;
        }

        public int getCount() {
            return list.size();
        }

        public Object getItem(int position) {

            return null;
        }

        public long getItemId(int position) {

            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        private class ViewHolder {
            TextView tv_place_name;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            LayoutInflater inflater = SearchPlacesActivity.this.getLayoutInflater();

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.search_places_item, null);
                holder = new ViewHolder();
                holder.tv_place_name = (TextView) convertView.findViewById(android.R.id.text1);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tv_place_name.setText(list.get(position).getDescription());

            return convertView;
        }

    }
}
