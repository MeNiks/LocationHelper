package com.niks.locationhelperdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.niks.baseutils.BaseAppCompatActivity;
import com.niks.locationhelper.direction.network.DirectionAPI;
import com.niks.locationhelper.places.model.PlacePrediction;
import com.niks.locationhelper.places.network.PlacesAPI;
import com.niks.locationhelper.places.ui.SearchPlacesActivity;
import com.niks.net.callback.OperationCallback;

/**
 * Created by niks
 */
public class MainActivity extends BaseAppCompatActivity {

    private static final String API_KEY ="PROVIDE_API_KEY_HERE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

    }

    public void clickDirectionApi(View view) {
        DirectionAPI directionAPI = new DirectionAPI(this);
        directionAPI.setOrigin_latitude(18.5417946);
        directionAPI.setOrigin_longitude(73.8011542000000);

        directionAPI.setDestination_latitude(18.527388);
        directionAPI.setDestination_longitude(73.8429711);
        directionAPI.getDirectionList(this, new OperationCallback() {
            @Override
            public void onSuccess(Object response, Object o1) {
                response.getClass();
            }

            @Override
            public void onFailed(Object response, Object o1) {
                if (response instanceof Integer) {
                    //No internet connection
                    if ((int) response == 1001) {
                        myToast(o1.toString());
                    }
                }
            }
        });
    }

    public void clickPlacesApi(View view) {
        PlacesAPI placesAPI = new PlacesAPI(this);
        placesAPI.setPlace_name("Pune");
        placesAPI.setPlace_type(PlacePrediction.PlaceTypes.GEOCODE);
        placesAPI.getPlacesList(this, new OperationCallback() {
            @Override
            public void onSuccess(Object response, Object o1) {
                response.getClass();
            }

            @Override
            public void onFailed(Object response, Object o1) {
                if (response instanceof Integer) {
                    //No internet connection
                    if ((int) response == 1001) {
                        myToast(o1.toString());
                    }
                }
            }
        }, API_KEY);
    }
    public void searchPlaces(View view) {
        Intent intent = new Intent(MainActivity.this, SearchPlacesActivity.class);
        intent.putExtra("title","Search Location");
        intent.putExtra("api_key",API_KEY);
        startActivityForResult(intent, 401);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         if(requestCode == 401){
             if (data!=null) {
                 Bundle bundle = data.getExtras();
                 if(bundle!=null){
                     if(bundle.containsKey("success")){
                         PlacePrediction search_result= (PlacePrediction) bundle.getSerializable("success");
                         myToast(search_result.getDescription());
                     }
                 }
             }
         }
    }



}
