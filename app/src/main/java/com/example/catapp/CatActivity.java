package com.example.catapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Arrays;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

public class CatActivity extends AppCompatActivity {

    private ImageView catPhoto;
    private TextView catName;
    private TextView catDescription;
    private TextView catWeight;
    private TextView catTemperament;
    private TextView catOrigin;
    private TextView catLifespan;
    private TextView catWikilink;
    private TextView catDogFriend;
    private ArrayList<ImageModel> imageArrayList;
    private ConstraintLayout constraintLayout;
    private String imageUrl;
    private Button favButton;
    private String catNameToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Mapping attributes to XML items in syntax catAttribute = cat_item
        setContentView(R.layout.activity_catdetail);
        catPhoto = findViewById(R.id.cat_photo);
        catName = findViewById(R.id.favcat_name);
        catOrigin = findViewById(R.id.cat_origin);
        catDogFriend = findViewById(R.id.cat_dogfriendlvl);
        catWeight = findViewById(R.id.cat_weight);
        catLifespan = findViewById(R.id.cat_lifespan);
        catWikilink = findViewById(R.id.cat_wikilink);
        catDescription = findViewById(R.id.cat_description);
        catTemperament = findViewById(R.id.cat_temperament);


        favButton = findViewById(R.id.fav_button);

        //Get given content from API
        Intent intent = getIntent();

        String id = intent.getStringExtra("id");
        System.out.println(id);
        final CatModel cat = FakeDatabase.getCatById(id);
        catNameToast = cat.getName();

        //Assigning attributes if existing, if not then return [Doesn't Exist]
        if (cat.getName() != null) {
            catName.setText(cat.getName());
        } else {
            catName.setText("[Doesn't Exist]");
        }
        if (cat.getDescription() != null) {
            catDescription.setText(cat.getDescription());
        } else {
            catDescription.setText("[Doesn't exist]");
        }
        if (cat.getOrigin() != null) {
            catOrigin.setText(cat.getOrigin());
        } else {
            catOrigin.setText("[Doesn't exist]");
        }
        if(cat.getWeight()!= null) {
            //Changing to metric system
            catWeight.setText(cat.getWeight().getMetric() + " KG");
        } else {
            catWeight.setText("[Doesn't exist]");
        }
        if(cat.getTemperament()!= null) {
            catTemperament.setText(cat.getTemperament());
        } else {
            catTemperament.setText("[Doesn't exist]");
        }
        if(cat.getLife_span()!= null) {
            catLifespan.setText(cat.getLife_span() + " years");
        } else {
            catLifespan.setText("[Doesn't exist]");
        }
        if(cat.getWikipedia_url()!= null) {
            catWikilink.setText(cat.getWikipedia_url());
        } else {
            catWikilink.setText("[Doesn't exist]");
        }
        if(cat.getDog_friendly() != 0) {
            catDogFriend.setText("Level: " + cat.getDog_friendly());
        } else {
            catDogFriend.setText("[Doesn't exist]");
        }

        //Image
        String potentialUrl = "https://api.thecatapi.com/v1/images/search?breed_ids=" + cat.getId();

        //Context:
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Response
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                Gson gson = new Gson();
                ImageModel[] imageArray = gson.fromJson(response, ImageModel[].class);
                imageArrayList = new ArrayList<ImageModel>(Arrays.asList(imageArray));
                ImageModel thisImage = imageArrayList.get(0);
                System.out.println(thisImage.getUrl());
                imageUrl = thisImage.getUrl();
                Glide.with(CatActivity.this).load(imageUrl).into(catPhoto);

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError error) {
                System.out.println(error.toString());
            }
        };
        StringRequest stringRequest = new StringRequest(Request.Method.GET, potentialUrl, responseListener, errorListener);
        requestQueue.add(stringRequest);


        //Adding item to favourite through onCickListener
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavouriteAdapter.favCats.add(cat);
                confirmToast(v);

            }
        });


//Toast for successful adding to favourites
    }

    public void confirmToast (View v) {
        Toast.makeText(CatActivity.this, catNameToast + " added to favourites!",
                Toast.LENGTH_SHORT).show();
    }
}
