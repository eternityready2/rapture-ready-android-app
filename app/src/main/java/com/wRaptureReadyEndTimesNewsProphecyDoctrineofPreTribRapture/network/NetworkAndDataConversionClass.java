package com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.network;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.BuildConfig;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.ApiResponse;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.ButtonItem;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.Constants;
import com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.utility.AppExecutors;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NetworkAndDataConversionClass {
    public static List<Pair<String, List<ButtonItem>>> fetchDataFromNetwork(Context context) {
        Constants.resetBaseImagePath();
        try {
            Gson gson = new Gson();
            HttpURLConnection connection =
                    (HttpURLConnection) new URL(Constants.BASE_URL + "/data").openConnection();
            connection.setConnectTimeout(15_000); // 10 seconds to establish connection
            connection.setReadTimeout(15_000);    // 15 seconds to read data from server
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());

                Type responseType = new TypeToken<ApiResponse>() {}.getType();
                ApiResponse response = gson.fromJson(inputStreamReader, responseType);

                if (response != null && response.status && response.data != null) {
                    AppExecutors.getSingleThreadExecutor()
                            .execute(() -> saveToDataCache(context, response.data));

                    return getSections(response.data);
                } else {
                    throw new Exception("Invalid response structure");
                }
            } else {
                throw new Exception("Network request failed with response code: " + connection.getResponseCode());
            }
        } catch (Exception e) {
            Log.e("NetworkClass", "Error fetching data: " + e.getMessage(), e);

            ApiResponse.Data data = getDataFromCache(context);
            if (data != null) { return getSections(data); } else { return null; }
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

    private static void saveToDataCache(Context context, ApiResponse.Data data) {
        File file = new File(context.getFilesDir(), "DATA_FOLDER/cache.json");

        // Ensure parent directories exist
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            parentDir.mkdirs(); // creates any missing directories
        }

        try (FileWriter writer = new FileWriter(file)) {
            Gson gson = new Gson();
            String jsonString = gson.toJson(data);
            writer.write(jsonString);
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e("NetworkClass", "Error saving to cache: " + e.getMessage(), e);
            }
        }
    }

    private static ApiResponse.Data getDataFromCache(Context context) {
        Gson gson = new Gson();
        Type responseType = new TypeToken<ApiResponse.Data>() {}.getType();
        File file = new File(context.getFilesDir(), "DATA_FOLDER/cache.json");

        if (file.exists()) {
            //noinspection IOStreamConstructor
            try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file))) {
                return gson.fromJson(reader, responseType);
            } catch (Exception e) {
                if (BuildConfig.DEBUG) {
                    Log.e("NetworkClass", "Error loading from cache: " + e.getMessage(), e);
                }
                return getDataFromAssets(context, gson, responseType);
            }
        } else {
            if (BuildConfig.DEBUG) { Log.e("NetworkClass", "Cache file not found"); }
            return getDataFromAssets(context, gson, responseType);
        }
    }

    private static ApiResponse.Data getDataFromAssets(Context context, Gson gson, Type type) {
        Constants.setBaseImagePath("file:///android_asset");
        try {
            InputStreamReader reader =
                    new InputStreamReader(context.getAssets().open("data.json"));
            return gson.fromJson(reader, type);
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e("NetworkClass", "Error loading from assets: " + e.getMessage(), e);
            }
            return null;
        }
    }
}
