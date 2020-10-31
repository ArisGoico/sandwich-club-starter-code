package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    //UI variables
    TextView mAlsoKnownAsTV;
    TextView mIngredientsTV;
    TextView mOriginTV;
    TextView mDescriptionTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //UI variables
        mAlsoKnownAsTV = (TextView) findViewById(R.id.also_known_tv);
        mIngredientsTV = (TextView) findViewById(R.id.ingredients_tv);
        mOriginTV = (TextView) findViewById(R.id.origin_tv);
        mDescriptionTV =  (TextView) findViewById(R.id.description_tv);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich input) {
        //DONE Fill up this method: create appropriate variables up, fill them, etc.

        String temp = input.getAlsoKnownAs().toString().replace('[', ' ').replace(']', ' ').trim();
        if (temp.isEmpty()) {
            ((LinearLayout)(mAlsoKnownAsTV.getParent())).setVisibility(View.INVISIBLE);
        } else {
            mAlsoKnownAsTV.setText(temp);
        }

        temp = input.getIngredients().toString().replace('[', ' ').replace(']', ' ').trim();
        if (temp.isEmpty()) {
            ((LinearLayout)(mIngredientsTV.getParent())).setVisibility(View.INVISIBLE);
        } else {
            mIngredientsTV.setText(temp);
        }

        if (input.getPlaceOfOrigin().isEmpty()) {
            ((LinearLayout)(mOriginTV.getParent())).setVisibility(View.INVISIBLE);
        } else {
            mOriginTV.setText(input.getPlaceOfOrigin());
        }

        if (input.getDescription().isEmpty()) {
            ((LinearLayout)(mDescriptionTV.getParent())).setVisibility(View.INVISIBLE);
        } else {
            mDescriptionTV.setText(input.getDescription());
        }
    }
}
