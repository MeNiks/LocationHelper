package com.niks.locationhelper.places.network;

import android.content.Context;
import android.text.TextUtils;

import com.niks.baseutils.JsonValuesUtils;
import com.niks.locationhelper.R;
import com.niks.locationhelper.places.model.PlacePrediction;
import com.niks.net.callback.OperationCallback;
import com.niks.net.model.ResultModel;
import com.niks.net.network.HttpURLConnectionRequester;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by niks
 */

public class PlacesAPI {
    private HttpURLConnectionRequester httpURLConnectionRequester;
    private static final String PLACES_API_URL = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=";
    private String place_name, place_type;

    public PlacesAPI(Context context) {
        httpURLConnectionRequester = HttpURLConnectionRequester.getInstance(context);
    }

    /*
    * Provide place name to search
    * */
    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    /*
    * Reference Doc : https://developers.google.com/places/web-service/autocomplete#place_types
    * */
    public void setPlace_type(String place_type) {
        this.place_type = place_type;
    }

    /*
    * Server Api key
    * */
    public void getPlacesList(final Context context,final OperationCallback operationCallback, String api_key) {
        if (TextUtils.isEmpty(place_name)) {
            operationCallback.onFailed(context.getString(R.string.msg_provide_place_name), null);
            return;
        }

        if (TextUtils.isEmpty(place_type)) {
            operationCallback.onFailed(context.getString(R.string.msg_provide_places_type), null);
            return;
        }

        if (TextUtils.isEmpty(api_key)) {
            operationCallback.onFailed(context.getString(R.string.msg_provide_server_key), null);
            return;
        }
//        ArrayList<PlacePrediction> test = getPlacesListModels(sample_json);
//        operationCallback.onSuccess(test, null);
//        return;
        String url = PLACES_API_URL + place_name + "&types=" + place_type + "&language=en&key=" + api_key;
        httpURLConnectionRequester.sendHttpURLConnectionRequest("GET", url, null, null, "", new OperationCallback() {
            @Override
            public void onSuccess(Object parameter1, Object parameter2) {
                try {
                    ResultModel resultModel = (ResultModel) parameter1;
                    ArrayList<PlacePrediction> test = getPlacesListModels(resultModel.getResponse());
                    operationCallback.onSuccess(test, null);
                } catch (Exception e) {
                    operationCallback.onFailed(context.getString(R.string.places_api_failed), null);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(Object parameter1, Object parameter2) {
                try {
//                    ResultModel resultModel = (ResultModel) parameter1;
                    operationCallback.onFailed(parameter1, parameter2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private ArrayList<PlacePrediction> getPlacesListModels(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            if (!jsonObject.isNull("predictions")) {

                ArrayList<PlacePrediction> placePredictionArrayList = new ArrayList<>();
                JSONArray predictionsJsonArray = jsonObject.getJSONArray("predictions");
                for (int i = 0; i < predictionsJsonArray.length(); i++) {

                    JSONObject predictionJsonObject = predictionsJsonArray.getJSONObject(i);
                    PlacePrediction placePrediction = new PlacePrediction();
                    placePrediction.setId(JsonValuesUtils.getString(predictionJsonObject, "id"));
                    placePrediction.setPlace_id(JsonValuesUtils.getString(predictionJsonObject, "place_id"));
                    placePrediction.setDescription(JsonValuesUtils.getString(predictionJsonObject, "description"));
                    placePrediction.setReference(JsonValuesUtils.getString(predictionJsonObject, "reference"));
                    placePredictionArrayList.add(placePrediction);
                }
                return placePredictionArrayList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    Sample json
//    String sample_json = "{\"predictions\":[{\"description\":\"Pune, Maharashtra, India\",\"id\":\"b53b6db33aaf3aa06dc661e9b2631735f3d484f1\",\"matched_substrings\":[{\"length\":4,\"offset\":0}],\"place_id\":\"ChIJARFGZy6_wjsRQ-Oenb9DjYI\",\"reference\":\"CjQwAAAAP8z0bc4JcdaH_0KjMQomBN1SqHmCTudbTBhulhoLTZH65zMJIdiBB3T54BVi2T1VEhAlvn2xgb9WDCJF71mHzEUNGhQ8YhGtsz-f5Seo1na5r29jrTIG2A\",\"structured_formatting\":{\"main_text\":\"Pune\",\"main_text_matched_substrings\":[{\"length\":4,\"offset\":0}],\"secondary_text\":\"Maharashtra, India\"},\"terms\":[{\"offset\":0,\"value\":\"Pune\"},{\"offset\":6,\"value\":\"Maharashtra\"},{\"offset\":19,\"value\":\"India\"}],\"types\":[\"locality\",\"political\",\"geocode\"]},{\"description\":\"Pune Junction, Pune, Maharashtra, India\",\"id\":\"d94c594672688548e1cbef8a20db1e074ea4fff2\",\"matched_substrings\":[{\"length\":4,\"offset\":0}],\"place_id\":\"ChIJSXAo8VjAwjsR8XBJRuCZo0c\",\"reference\":\"CkQ1AAAAQ8t8ZVuHthXZWmHAXXYFNm_psWXH0QZZaPSQBZLYARrAzmLT-aUJSbi8mPmPAKpU613S_RF_zEvwn0YUXkpkMRIQ6wStWJoM6aPClgHSNbV7XxoUNAFd67sMyDpdfAO7TzOIK_Ve6Wk\",\"structured_formatting\":{\"main_text\":\"Pune Junction\",\"main_text_matched_substrings\":[{\"length\":4,\"offset\":0}],\"secondary_text\":\"Pune, Maharashtra, India\"},\"terms\":[{\"offset\":0,\"value\":\"Pune Junction\"},{\"offset\":15,\"value\":\"Pune\"},{\"offset\":21,\"value\":\"Maharashtra\"},{\"offset\":34,\"value\":\"India\"}],\"types\":[\"transit_station\",\"point_of_interest\",\"establishment\",\"geocode\"]},{\"description\":\"Pune Nagar Road, Yerawada, Pune, Maharashtra, India\",\"id\":\"380f7c4045de76aabf8ed2dfe88568afe9e87427\",\"matched_substrings\":[{\"length\":4,\"offset\":0}],\"place_id\":\"EjNQdW5lIE5hZ2FyIFJvYWQsIFllcmF3YWRhLCBQdW5lLCBNYWhhcmFzaHRyYSwgSW5kaWE\",\"reference\":\"CkQ3AAAAlT9mUFM8y1MDUJRsXn7I5wFpyFWy3jIeYsKQkUNQ7fMMYv9QZZ9v1kvhTRQ8hXnoZYUFJlBLjl1CHPQNkU3urhIQNsR86hFoxxmMudIQklncahoU8trBzNOVl7Itw1nHUPkOaCOia_8\",\"structured_formatting\":{\"main_text\":\"Pune Nagar Road\",\"main_text_matched_substrings\":[{\"length\":4,\"offset\":0}],\"secondary_text\":\"Yerawada, Pune, Maharashtra, India\"},\"terms\":[{\"offset\":0,\"value\":\"Pune Nagar Road\"},{\"offset\":17,\"value\":\"Yerawada\"},{\"offset\":27,\"value\":\"Pune\"},{\"offset\":33,\"value\":\"Maharashtra\"},{\"offset\":46,\"value\":\"India\"}],\"types\":[\"route\",\"geocode\"]},{\"description\":\"Pune University, Pune, Maharashtra, India\",\"id\":\"7e420e4785c299761332675801f3205b19a17ef9\",\"matched_substrings\":[{\"length\":4,\"offset\":0}],\"place_id\":\"ChIJbWuyKEK_wjsRwQU-K8S16fI\",\"reference\":\"ClRBAAAA2--oquSdEhoWeQyDuFOW_GQ4xMi5x-HBw-hONx02TFMUr4r20WXjZzlYEyPuX15V6uXhSYR25_2v7i1vucowOZHZ0KAxllls2sm-4My1RM4SEJ9UgfX2FmKrH8vYB0U9aYsaFK1Hqm1om4pcRSFINFIgLhFndjo3\",\"structured_formatting\":{\"main_text\":\"Pune University\",\"main_text_matched_substrings\":[{\"length\":4,\"offset\":0}],\"secondary_text\":\"Pune, Maharashtra, India\"},\"terms\":[{\"offset\":0,\"value\":\"Pune University\"},{\"offset\":17,\"value\":\"Pune\"},{\"offset\":23,\"value\":\"Maharashtra\"},{\"offset\":36,\"value\":\"India\"}],\"types\":[\"sublocality_level_2\",\"sublocality\",\"political\",\"geocode\"]},{\"description\":\"Pune Railway Station, Pune, Maharashtra, India\",\"id\":\"4314bc1c39f89c75e04a6c3aa381dd3db4790837\",\"matched_substrings\":[{\"length\":4,\"offset\":0}],\"place_id\":\"ChIJsUSiFlnAwjsRgirMjA9NnkI\",\"reference\":\"CkQ8AAAAgrKcY0nWUIqJnRDSuokmXNYohFjv2WxAg1izilO9EOgGIPkEYNCbhymANRotciCxgecKr1i-Hgx3vCexfb8AKhIQFGcuVis9KI29PsePhPXULRoUWQOw9gOnMj5tvtxURnV5KZ347aM\",\"structured_formatting\":{\"main_text\":\"Pune Railway Station\",\"main_text_matched_substrings\":[{\"length\":4,\"offset\":0}],\"secondary_text\":\"Pune, Maharashtra, India\"},\"terms\":[{\"offset\":0,\"value\":\"Pune Railway Station\"},{\"offset\":22,\"value\":\"Pune\"},{\"offset\":28,\"value\":\"Maharashtra\"},{\"offset\":41,\"value\":\"India\"}],\"types\":[\"transit_station\",\"point_of_interest\",\"establishment\",\"geocode\"]}],\"status\":\"OK\"}";
}