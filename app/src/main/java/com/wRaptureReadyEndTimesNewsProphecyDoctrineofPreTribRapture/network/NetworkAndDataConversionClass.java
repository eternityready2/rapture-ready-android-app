package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.network;

import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.ApiResponse;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.ButtonItem;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.Constants;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NetworkAndDataConversionClass {
    public static List<Pair<String, List<ButtonItem>>> fetchDataFromNetwork() {
        try {
            Gson gson = new Gson();
            HttpURLConnection connection =
                    (HttpURLConnection) new URL(Constants.BASE_URL + "/data").openConnection();
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());

                Type responseType = new TypeToken<ApiResponse>() {}.getType();
                ApiResponse response = gson.fromJson(inputStreamReader, responseType);

                if (response != null && response.status && response.data != null) {
                    return getSections(response.data);
                } else {
                    throw new Exception("Invalid response structure");
                }
            } else {
                throw new Exception("Network request failed with response code: " + connection.getResponseCode());
            }
        } catch (Exception e) {
            Log.e("NetworkClass", "Error fetching data: " + e.getMessage(), e);
            return null;
        }
    }

    private static List<Pair<String, List<ButtonItem>>> getSections(ApiResponse.Data data) {
        List<Pair<String, List<ButtonItem>>> items = new ArrayList<>();

        // Add regular sections
        if (data.sections != null) {
            for (ApiResponse.Section section : data.sections) {
                if (section.title != null && section.buttons != null) {
                    items.add(new Pair<>(section.title, section.buttons));
                }
            }
        }

        // Add bottomNav
        if (data.bottomNav != null && !data.bottomNav.isEmpty()) {
            items.add(new Pair<>("BottomNav", data.bottomNav));
        }

        // Add more
        if (data.more != null && !data.more.isEmpty()) {
            items.add(new Pair<>("More", data.more));
        }

        return items;
    }
}
