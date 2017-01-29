package com.niks.locationhelperdemo;

import android.os.Bundle;

import com.niks.baseutils.BaseAppCompatActivity;
import com.niks.locationhelper.direction.network.DirectionAPI;
import com.niks.locationhelper.places.model.PlacePrediction;
import com.niks.locationhelper.places.network.PlacesAPI;
import com.niks.net.callback.OperationCallback;

/**
 * Created by niks
 */
public class MainActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        DirectionAPI directionAPI = new DirectionAPI(this);
        directionAPI.setOrigin_latitude(18.5417946);
        directionAPI.setOrigin_longitude(73.8011542000000);

        directionAPI.setDestination_latitude(18.527388);
        directionAPI.setDestination_longitude(73.8429711);
        directionAPI.getDirectionList(this,new OperationCallback() {
            @Override
            public void onSuccess(Object response, Object o1) {
                response.getClass();
            }

            @Override
            public void onFailed(Object response, Object o1) {
                if(response instanceof Integer){
                    if((int)response == 1001){
                        myToast(o1.toString());
                    }
                }
            }
        });

        PlacesAPI placesAPI = new PlacesAPI(this);
        placesAPI.setPlace_name("Pune");
        placesAPI.setPlace_type(PlacePrediction.PlaceTypes.GEOCODE);
        placesAPI.getPlacesList(this,new OperationCallback() {
            @Override
            public void onSuccess(Object response, Object o1) {
                response.getClass();
            }

            @Override
            public void onFailed(Object response, Object o1) {
                if(response instanceof Integer){
                    if((int)response == 1001){
                        myToast(o1.toString());
                    }
                }
            }
        }, "AIzaSyBbw1LVaA7_x6g-ncqwCWgzw4ceJQfgyx0");
    }
}
