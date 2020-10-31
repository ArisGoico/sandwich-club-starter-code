package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = "JsonUtils";

    public static Sandwich parseSandwichJson(String json) {
        JSONObject fullJSON = null;

        String mainName;
        List<String> alsoKnownAs = new ArrayList<String>();
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients = new ArrayList<String>();

        try {
            fullJSON = new JSONObject(json);
            //Parse JSON
            JSONObject name = fullJSON.getJSONObject("name");
            mainName = name.getString("mainName");
            JSONArray otherNames = name.getJSONArray("alsoKnownAs");
            for (int i = 0; i < otherNames.length(); i++) {
                alsoKnownAs.add(otherNames.getString(i));
            }
            placeOfOrigin = fullJSON.getString("placeOfOrigin");
            description = fullJSON.getString("description");
            image = fullJSON.getString("image");
            JSONArray ingredientsArray = fullJSON.getJSONArray("ingredients");
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredients.add(ingredientsArray.getString(i));
            }
        }
        catch (JSONException e) {
            Log.w(TAG, "Error while parsing the JSON.");
            e.printStackTrace();
            return null;
        }
        catch (Exception e) {
            Log.w(TAG, "Unknown error while parsing the JSON.");
            e.printStackTrace();
            return null;
        }

        Sandwich result = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        return result;
    }
}
