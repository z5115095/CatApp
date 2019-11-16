package com.example.catapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private SearchView catSearchView;


    public SearchFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.rv_search);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        final CatAdapter catAdapter = new CatAdapter();

        //Creating search
        catSearchView = view.findViewById(R.id.search_bar);
        catSearchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        catSearchView.setQueryHint("Search");

        //Logic for keyboard positioning, source code online tutorial
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        catSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                catAdapter.getFilter().filter(newText);
                return false;
            }
        });

        //URL string

        String catUrl = "https://api.thecatapi.com/v1/breeds?api_key=ca69eb41-44d8-4c48-95df-bbde10618970\n" +
                "\n";

        //Create context
        Context context = getContext();
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
                Gson gson = new Gson();

                CatModel[] catsArray = gson.fromJson(response, CatModel[].class);
                ArrayList<CatModel> catArrayList = new ArrayList<CatModel>(Arrays.asList(catsArray));

                catAdapter.setData(catArrayList);
                FakeDatabase.saveCatsToFakeDatabase(catArrayList);
                recyclerView.setAdapter(catAdapter);


            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse (VolleyError error) {
                System.out.println(error.toString());
            }
        };
        StringRequest stringRequest = new StringRequest(Request.Method.GET, catUrl, responseListener, errorListener);
        requestQueue.add(stringRequest);


        return view;
    }

}