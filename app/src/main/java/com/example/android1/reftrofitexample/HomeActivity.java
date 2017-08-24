package com.example.android1.reftrofitexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kamal Verma on 23-August-2017
 * Kamalverma1207@gmail.com
 * Precise Automation and Robotics
 **/
public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();


    // TODO - insert your themoviedb.org API KEY here
    private final static String API_KEY = "4e577cec25c9a6d1819d75861e335586";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
            return;
        }

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<MovieResponse> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                int statusCode = response.code();
                List<MovieModel> movies = response.body().getResults();
                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }
}