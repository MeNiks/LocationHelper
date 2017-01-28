package com.niks.locationhelper.direction.network;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;

import com.niks.baseutils.JsonValuesUtils;
import com.niks.baseutils.model.KeyValueModel;
import com.niks.locationhelper.direction.model.DirectionLeg;
import com.niks.locationhelper.direction.model.DirectionRoute;
import com.niks.locationhelper.direction.model.DirectionStep;
import com.niks.net.callback.OperationCallback;
import com.niks.net.model.ResultModel;
import com.niks.net.network.HttpURLConnectionRequester;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DirectionAPI {
    private HttpURLConnectionRequester httpURLConnectionRequester;
    private String DIRECTION_API_URL = "http://maps.googleapis.com/maps/api/directions/json?origin=";
    private double origin_latitude = -1, origin_longitude = -1, destination_latitude = -1, destination_longitude = -1;

    public DirectionAPI(Context context) {
        httpURLConnectionRequester = HttpURLConnectionRequester.getInstance(context);
    }

    public void setOrigin_latitude(double origin_latitude) {
        this.origin_latitude = origin_latitude;
    }

    public void setOrigin_longitude(double origin_longitude) {
        this.origin_longitude = origin_longitude;
    }

    public void setDestination_latitude(double destination_latitude) {
        this.destination_latitude = destination_latitude;
    }

    public void setDestination_longitude(double destination_longitude) {
        this.destination_longitude = destination_longitude;
    }

    public void getDirectionList(final OperationCallback operationCallback) {
        if (origin_latitude == -1) {
            operationCallback.onFailed("Set origin latitude", null);
            return;
        }
        if (origin_longitude == -1) {
            operationCallback.onFailed("Set origin longitude", null);
            return;
        }
        if (destination_latitude == -1) {
            operationCallback.onFailed("Set destination latitude", null);
            return;
        }

        if (destination_longitude == -1) {
            operationCallback.onFailed("Set destination longitude", null);
            return;
        }
        new CallDirectionAPI(operationCallback).execute();


    }

    private ArrayList<DirectionRoute> getDirectionListModels(String route_json) {
        try {
            ArrayList<DirectionRoute> directionRouteArrayList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(route_json);
            if (!jsonObject.isNull("routes")) {
                JSONArray routesJsonArray = jsonObject.getJSONArray("routes");
                for (int i = 0; i < routesJsonArray.length(); i++) {

                    DirectionRoute directionRoute = new DirectionRoute();
                    JSONObject routeJsonObject = routesJsonArray.getJSONObject(i);
                    if (!routeJsonObject.isNull("legs")) {

                        ArrayList<DirectionLeg> directionLegArrayList = new ArrayList<>();
                        JSONArray legsJsonArray = routeJsonObject.getJSONArray("legs");
                        for (int j = 0; j < legsJsonArray.length(); j++) {

                            DirectionLeg directionLeg = new DirectionLeg();
                            JSONObject legJsonObject = legsJsonArray.getJSONObject(j);
                            if (!legJsonObject.isNull("distance")) {

                                JSONObject distanceJsonObject = legJsonObject.getJSONObject("distance");
                                {
                                    KeyValueModel textkeyValueModel = new KeyValueModel();
                                    textkeyValueModel.setKey("text");
                                    textkeyValueModel.setValue(JsonValuesUtils.getString(distanceJsonObject, "text"));
                                    directionLeg.setDistance_text(textkeyValueModel);
                                }

                                {
                                    KeyValueModel valuekeyValueModel = new KeyValueModel();
                                    valuekeyValueModel.setKey("value");
                                    valuekeyValueModel.setValue(JsonValuesUtils.getString(distanceJsonObject, "value"));
                                    directionLeg.setDistance_value(valuekeyValueModel);
                                }
                            }

                            if (!legJsonObject.isNull("duration")) {

                                JSONObject durationJsonObject = legJsonObject.getJSONObject("duration");
                                {
                                    KeyValueModel textkeyValueModel = new KeyValueModel();
                                    textkeyValueModel.setKey("text");
                                    textkeyValueModel.setValue(JsonValuesUtils.getString(durationJsonObject, "text"));
                                    directionLeg.setDuration_text(textkeyValueModel);
                                }

                                {
                                    KeyValueModel valuekeyValueModel = new KeyValueModel();
                                    valuekeyValueModel.setKey("value");
                                    valuekeyValueModel.setValue(JsonValuesUtils.getString(durationJsonObject, "value"));
                                    directionLeg.setDuration_value(valuekeyValueModel);
                                }
                            }

                            if (!legJsonObject.isNull("end_address")) {
                                directionLeg.setEnd_address(JsonValuesUtils.getString(legJsonObject, "end_address"));
                            }

                            if (!legJsonObject.isNull("end_location")) {

                                JSONObject end_locationJsonObject = legJsonObject.getJSONObject("end_location");
                                Location location = new Location("network");
                                location.setLatitude(JsonValuesUtils.getDouble(end_locationJsonObject, "lat"));
                                location.setLongitude(JsonValuesUtils.getDouble(end_locationJsonObject, "lng"));
                                directionLeg.setEnd_location(location);
                            }

                            if (!legJsonObject.isNull("start_address")) {
                                directionLeg.setStart_address(JsonValuesUtils.getString(legJsonObject, "start_address"));
                            }

                            if (!legJsonObject.isNull("start_location")) {
                                JSONObject start_locationJsonObject = legJsonObject.getJSONObject("start_location");
                                Location location = new Location("network");
                                location.setLatitude(JsonValuesUtils.getDouble(start_locationJsonObject, "lat"));
                                location.setLongitude(JsonValuesUtils.getDouble(start_locationJsonObject, "lng"));
                                directionLeg.setStart_location(location);
                            }

                            if (!legJsonObject.isNull("steps")) {
                                ArrayList<DirectionStep> directionStepArrayList = new ArrayList<>();
                                JSONArray stepsJsonArray = legJsonObject.getJSONArray("steps");
                                for (int k = 0; k < stepsJsonArray.length(); k++) {

                                    JSONObject stepJsonObject = stepsJsonArray.getJSONObject(k);
                                    DirectionStep directionStep = new DirectionStep();
                                    if (!stepJsonObject.isNull("distance")) {

                                        JSONObject distanceJsonObject = stepJsonObject.getJSONObject("distance");
                                        {
                                            KeyValueModel textkeyValueModel = new KeyValueModel();
                                            textkeyValueModel.setKey("text");
                                            textkeyValueModel.setValue(JsonValuesUtils.getString(distanceJsonObject, "text"));
                                            directionStep.setDistance_text(textkeyValueModel);
                                        }

                                        {
                                            KeyValueModel valuekeyValueModel = new KeyValueModel();
                                            valuekeyValueModel.setKey("value");
                                            valuekeyValueModel.setValue(JsonValuesUtils.getString(distanceJsonObject, "value"));
                                            directionStep.setDistance_value(valuekeyValueModel);
                                        }
                                    }

                                    if (!stepJsonObject.isNull("duration")) {

                                        JSONObject durationJsonObject = stepJsonObject.getJSONObject("duration");
                                        {
                                            KeyValueModel textkeyValueModel = new KeyValueModel();
                                            textkeyValueModel.setKey("text");
                                            textkeyValueModel.setValue(JsonValuesUtils.getString(durationJsonObject, "text"));
                                            directionStep.setDuration_text(textkeyValueModel);
                                        }

                                        {
                                            KeyValueModel valuekeyValueModel = new KeyValueModel();
                                            valuekeyValueModel.setKey("value");
                                            valuekeyValueModel.setValue(JsonValuesUtils.getString(durationJsonObject, "value"));
                                            directionStep.setDuration_value(valuekeyValueModel);
                                        }
                                    }

                                    if (!stepJsonObject.isNull("end_location")) {

                                        JSONObject end_locationJsonObject = stepJsonObject.getJSONObject("end_location");
                                        Location location = new Location("network");
                                        location.setLatitude(JsonValuesUtils.getDouble(end_locationJsonObject, "lat"));
                                        location.setLongitude(JsonValuesUtils.getDouble(end_locationJsonObject, "lng"));
                                        directionStep.setEnd_location(location);
                                    }

                                    if (!stepJsonObject.isNull("html_instructions")) {
                                        directionStep.setHtml_instructions(JsonValuesUtils.getString(stepJsonObject, "html_instructions"));
                                    }

                                    if (!stepJsonObject.isNull("polyline")) {
                                        JSONObject polylineJsonObject = stepJsonObject.getJSONObject("polyline");
                                        KeyValueModel textkeyValueModel = new KeyValueModel();
                                        textkeyValueModel.setKey("points");
                                        textkeyValueModel.setValue(JsonValuesUtils.getString(polylineJsonObject, "points"));
                                        directionStep.setPolyline(textkeyValueModel);
                                    }

                                    if (!stepJsonObject.isNull("start_location")) {

                                        JSONObject end_locationJsonObject = stepJsonObject.getJSONObject("start_location");
                                        Location location = new Location("network");
                                        location.setLatitude(JsonValuesUtils.getDouble(end_locationJsonObject, "lat"));
                                        location.setLongitude(JsonValuesUtils.getDouble(end_locationJsonObject, "lng"));
                                        directionStep.setStart_location(location);
                                    }

                                    if (!stepJsonObject.isNull("travel_mode")) {
                                        directionStep.setTravel_mode(JsonValuesUtils.getString(stepJsonObject, "travel_mode"));
                                    }
                                    directionStepArrayList.add(directionStep);
                                }
                                directionLeg.setDirectionStepArrayList(directionStepArrayList);

                            }
                            directionLegArrayList.add(directionLeg);
                        }
                        directionRoute.setDirectionLegArrayList(directionLegArrayList);
                    }
                    directionRouteArrayList.add(directionRoute);
                }
            }
            return directionRouteArrayList;
        } catch (Exception e) {
            return null;
        }
    }

//    String route_json = "{\"routes\":[{\"bounds\":{\"northeast\":{\"lat\":18.5423758,\"lng\":73.8429711},\"southwest\":{\"lat\":18.5269352,\"lng\":73.80115420000001}},\"copyrights\":\"Map data ©2015 Google\",\"legs\":[{\"distance\":{\"text\":\"6.3 km\",\"value\":6298},\"duration\":{\"text\":\"14 mins\",\"value\":861},\"end_address\":\"1180/4, Motilal Eknath Path, Revenue Colony, Shivajinagar, Pune, Maharashtra 411005, India\",\"end_location\":{\"lat\":18.527388,\"lng\":73.8429711},\"start_address\":\"Nimhan Mala Road, Ward No. 8, Pashan Gaon, Pashan, Pune, Maharashtra 411008, India\",\"start_location\":{\"lat\":18.5417946,\"lng\":73.80115420000001},\"steps\":[{\"distance\":{\"text\":\"0.1 km\",\"value\":122},\"duration\":{\"text\":\"1 min\",\"value\":19},\"end_location\":{\"lat\":18.5419393,\"lng\":73.80225759999999},\"html_instructions\":\"Head <b>east</b> on <b>Nimhan Mala Rd</b>\",\"polyline\":{\"points\":\"emdpBehmaMB}@AwA?MCKEMEKEIII\"},\"start_location\":{\"lat\":18.5417946,\"lng\":73.80115420000001},\"travel_mode\":\"DRIVING\"},{\"distance\":{\"text\":\"0.6 km\",\"value\":600},\"duration\":{\"text\":\"2 mins\",\"value\":93},\"end_location\":{\"lat\":18.5378061,\"lng\":73.8052142},\"html_instructions\":\"Turn <b>right</b>\",\"maneuver\":\"turn-right\",\"polyline\":{\"points\":\"cndpBcomaMPWFCDEJCNEHAH?x@FpBPF@`@FlBuBz@aAxBgC|AiBnCwC@A\"},\"start_location\":{\"lat\":18.5419393,\"lng\":73.80225759999999},\"travel_mode\":\"DRIVING\"},{\"distance\":{\"text\":\"2.5 km\",\"value\":2518},\"duration\":{\"text\":\"5 mins\",\"value\":298},\"end_location\":{\"lat\":18.5422543,\"lng\":73.82846599999999},\"html_instructions\":\"Turn <b>left</b> onto <b>Pashan Rd</b><div style=\\\"font-size:0.9em\\\">Pass by PES Modern English Medium High School (on the left in 1.1&nbsp;km)</div>\",\"maneuver\":\"turn-left\",\"polyline\":{\"points\":\"itcpBqanaMoAkE[kAUqAM_AIc@q@cEk@yDAGs@}Fg@sDGs@W}HEaBOoFAMa@wJI}@Eq@?Sc@aKKm@Om@K_@gAyDWaAKa@Mw@w@yFY{AUgAIo@CWCa@@sA?e@Cu@CKI}@W}B?WAOCgAAa@Ce@AWQ}@Qw@]}@\"},\"start_location\":{\"lat\":18.5378061,\"lng\":73.8052142},\"travel_mode\":\"DRIVING\"},{\"distance\":{\"text\":\"0.5 km\",\"value\":518},\"duration\":{\"text\":\"1 min\",\"value\":52},\"end_location\":{\"lat\":18.5397254,\"lng\":73.8324149},\"html_instructions\":\"Turn <b>right</b> onto <b>Ganeshkhind Rd</b><div style=\\\"font-size:0.9em\\\">Pass by Bank of Maharashtra ATM (on the left)</div>\",\"maneuver\":\"turn-right\",\"polyline\":{\"points\":\"apdpB}rraMYWPUZe@`@u@lB}Cl@cAb@s@LUNU`BmC`A}AXe@d@u@\"},\"start_location\":{\"lat\":18.5422543,\"lng\":73.82846599999999},\"travel_mode\":\"DRIVING\"},{\"distance\":{\"text\":\"0.9 km\",\"value\":918},\"duration\":{\"text\":\"2 mins\",\"value\":91},\"end_location\":{\"lat\":18.5346307,\"lng\":73.839242},\"html_instructions\":\"Slight <b>left</b> to stay on <b>Ganeshkhind Rd</b><div style=\\\"font-size:0.9em\\\">Pass by Ganesh Mandir (on the left in 750&nbsp;m)</div>\",\"maneuver\":\"turn-slight-left\",\"polyline\":{\"points\":\"i`dpBqksaMNg@Vi@b@u@r@gAf@o@l@_AHMzAoBn@{@bDkEXO|@sA|AwBjAeBPUr@eAHOLQ^q@LU|AgC\"},\"start_location\":{\"lat\":18.5397254,\"lng\":73.8324149},\"travel_mode\":\"DRIVING\"},{\"distance\":{\"text\":\"0.6 km\",\"value\":588},\"duration\":{\"text\":\"2 mins\",\"value\":98},\"end_location\":{\"lat\":18.5299997,\"lng\":73.8374091},\"html_instructions\":\"Turn <b>right</b> onto <b>Hare Krishna Mandir Rd</b>\",\"maneuver\":\"turn-right\",\"polyline\":{\"points\":\"m`cpBgvtaMRLpDjC|BdBb@`@n@n@h@Zz@\\\\b@LTBd@Az@Ch@Cp@GfD]\"},\"start_location\":{\"lat\":18.5346307,\"lng\":73.839242},\"travel_mode\":\"DRIVING\"},{\"distance\":{\"text\":\"0.1 km\",\"value\":122},\"duration\":{\"text\":\"1 min\",\"value\":22},\"end_location\":{\"lat\":18.5299122,\"lng\":73.8362607},\"html_instructions\":\"Turn <b>right</b> at <b>BM Thorat Chowk</b> onto <b>Lakaki Rd/Nargis Dutt Rd</b>\",\"maneuver\":\"turn-right\",\"polyline\":{\"points\":\"ocbpByjtaMF\\\\DfA@f@@vA\"},\"start_location\":{\"lat\":18.5299997,\"lng\":73.8374091},\"travel_mode\":\"DRIVING\"},{\"distance\":{\"text\":\"0.7 km\",\"value\":739},\"duration\":{\"text\":\"2 mins\",\"value\":105},\"end_location\":{\"lat\":18.526963,\"lng\":73.8417774},\"html_instructions\":\"Turn <b>left</b> at <b>Deep Bungalow Chowk</b> onto <b>Sahasrabuddhe Rd</b>\",\"maneuver\":\"turn-left\",\"polyline\":{\"points\":\"}bbpBsctaMZ]FKHKr@u@fBoAvA{AJOr@_BFKDI@Ij@eCBMDS?I@IAgAA}@?_@Be@@q@FuC@g@?A@EDGDEDCDAHCTCPCFAT?l@E\"},\"start_location\":{\"lat\":18.5299122,\"lng\":73.8362607},\"travel_mode\":\"DRIVING\"},{\"distance\":{\"text\":\"45 m\",\"value\":45},\"duration\":{\"text\":\"1 min\",\"value\":19},\"end_location\":{\"lat\":18.5269352,\"lng\":73.84220010000001},\"html_instructions\":\"Turn <b>left</b> onto <b>Chaturshringi Rd</b>\",\"maneuver\":\"turn-left\",\"polyline\":{\"points\":\"opapBcfuaM?q@@O@Q\"},\"start_location\":{\"lat\":18.526963,\"lng\":73.8417774},\"travel_mode\":\"DRIVING\"},{\"distance\":{\"text\":\"81 m\",\"value\":81},\"duration\":{\"text\":\"1 min\",\"value\":15},\"end_location\":{\"lat\":18.5275763,\"lng\":73.84257029999999},\"html_instructions\":\"Turn <b>left</b> at <b>Dnyaneshwar Paduka Chowk</b> onto <b>FC Road</b>\",\"maneuver\":\"turn-left\",\"polyline\":{\"points\":\"kpapBwhuaMuAq@IE_@Q\"},\"start_location\":{\"lat\":18.5269352,\"lng\":73.84220010000001},\"travel_mode\":\"DRIVING\"},{\"distance\":{\"text\":\"47 m\",\"value\":47},\"duration\":{\"text\":\"1 min\",\"value\":49},\"end_location\":{\"lat\":18.527388,\"lng\":73.8429711},\"html_instructions\":\"Turn <b>right</b><div style=\\\"font-size:0.9em\\\">Destination will be on the left</div>\",\"maneuver\":\"turn-right\",\"polyline\":{\"points\":\"ktapBakuaMd@mA?A\"},\"start_location\":{\"lat\":18.5275763,\"lng\":73.84257029999999},\"travel_mode\":\"DRIVING\"}],\"via_waypoint\":[]}],\"overview_polyline\":{\"points\":\"emdpBehmaM@uCCYKYOSX[PIXGbAFxBR`@FlBuBtDiElFaG@AoAkE[kAUqAWcB}A}Ju@eGo@gFo@}Sq@{Nc@aKKm@[mA_B{FYyAw@yFY{A_@wBGy@@yBGaAa@{DGqCE}@c@uB]}@YWl@{@`FkI`EwG~@{ANg@Vi@vA}BtAoBxHeKXO|@sAhD}E|A}Bl@gA|AgCRLnHpFrApAh@Zz@\\\\b@LTB`BEzAKfD]F\\\\FnB@vAZ]PWr@u@fBoAbBkBbA_Cn@sCD]AoDNuG@GJMJE^G|AK@aA@QuAq@i@Wd@oA\"},\"summary\":\"Pashan Rd\",\"warnings\":[],\"waypoint_order\":[]}],\"status\":\"OK\"}";

    private class CallDirectionAPI extends AsyncTask<String,Integer,String>{
        OperationCallback operationCallback;
        public CallDirectionAPI(OperationCallback operationCallback) {
            this.operationCallback=operationCallback;
        }

        @Override
        protected String doInBackground(String... params) {
            String url = "http://maps.googleapis.com/maps/api/directions/json?origin=" + origin_latitude + "," + origin_longitude + "&destination=" + destination_latitude + "," + destination_longitude + "&sensor=false";
            httpURLConnectionRequester.sendHttpURLConnectionRequest("GET", url, null, null, "", new OperationCallback() {
                @Override
                public void onSuccess(Object parameter1, Object parameter2) {
                    try {
                        ResultModel resultModel = (ResultModel) parameter1;
                        ArrayList<DirectionRoute> test = getDirectionListModels(resultModel.getResponse());
                        operationCallback.onSuccess(test, null);
                    } catch (Exception e) {
                        operationCallback.onFailed(null,null);
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailed(Object parameter1, Object parameter2) {
                    try {
//                    ResultModel resultModel = (ResultModel) parameter1;
                        operationCallback.onFailed(null,null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return null;
        }
    }
}
