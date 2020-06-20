package com.example.firstexercise.Services;

import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;

import com.example.firstexercise.Classes.State;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CountriesDataService extends AsyncTask<Void, Void, ArrayList<State>> {

    private ArrayList<State> myStates = new ArrayList<>();


    @Override
    protected ArrayList<State> doInBackground(Void... voids) {
        String sURL = "https://restcountries.eu/rest/v2/all?fields=name;nativeName;borders;flag"; // get all states

        // Connect to the URL using java's native library
        URL url = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            url = new URL(sURL);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection request = (HttpURLConnection) url.openConnection();

            request.connect();

            // Convert to a JSON object to print data
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element

            JsonArray rootObj = root.getAsJsonArray(); //May be an array, may be an object.


            for (JsonElement je : rootObj) {
                JsonObject obj = je.getAsJsonObject(); //since you know it's a JsonObject
                JsonElement entryName = obj.get("name");//will return members of your object
                JsonElement entryNative = obj.get("nativeName");
                JsonElement entryBorders = obj.get("borders");
                JsonElement entryFlag = obj.get("flag");

                String name = entryName.toString().replace("\"", "");
                String nativeName = entryNative.toString().replace("\"", "");
                String flag = entryFlag.toString().replace("\"", "");

                ArrayList<String> arrBorders = new ArrayList<String>();
                JsonArray entryBordersArray = entryBorders.getAsJsonArray();

                for (JsonElement j : entryBordersArray) {

                    String s = j.toString().replace("\"", "");
                    arrBorders.add(s);
                }

                myStates.add(new State(name, arrBorders, nativeName, flag));

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NetworkOnMainThreadException e) {
            e.printStackTrace();
        }

        return myStates;
    }

    @Override
    protected void onPostExecute(ArrayList<State> states) {
        super.onPostExecute(states);
        getMyStates(states);
    }


    /* Broker method to get the array of states */
    public ArrayList<State> getMyStates(ArrayList<State> finishedListOfStates) {
        return finishedListOfStates;
    }

    public State getStateWithStateCode(String stateCode) {

        String sURL = "https://restcountries.eu/rest/v2/alpha/" + stateCode + "?fields=name;nativeName;flag"; // gets a state by code
        State state = null;
        // Connect to the URL using java's native library
        URL url = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            url = new URL(sURL);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection request = (HttpURLConnection) url.openConnection();

            request.connect();


            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element

            JsonObject rootObj = root.getAsJsonObject();

            JsonElement entriesName = rootObj.get("name");
            JsonElement entriesNative = rootObj.get("nativeName");
            JsonElement entriesFlagUrl = rootObj.get("flag");

            String name = entriesName.toString().replace("\"", ""); // convert to pure string
            String nativeName = entriesNative.toString().replace("\"", "");
            String flag = entriesFlagUrl.getAsString();

            state = new State(name, nativeName, flag);

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return state;

    }

}
