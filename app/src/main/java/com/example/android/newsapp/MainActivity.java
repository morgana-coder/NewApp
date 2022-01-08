package com.example.android.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.android.newsapp.Api.Api;
import com.example.android.newsapp.Api.ApiClient;
import com.example.android.newsapp.model.Article;
import com.example.android.newsapp.model.News;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final String API_KEY="ca478af900964f1a9c06198d6f1c7b2c";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article>articles=new ArrayList<>();
    private Adapter adapter;
    private String TAG=MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        LoadJson();

    }
    public  void LoadJson(){
        Api apiInterface= ApiClient.getApiClient().create(Api.class);
        String country=Utils.getCountry();
        Call<News> call;
        call=apiInterface.getNews(country,API_KEY);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if(response.isSuccessful() && response.body().getArticles()!=null)
                {
                    if(!articles.isEmpty()){
                        articles.clear();
                    }
                    articles=response.body().getArticles();
                    adapter=new Adapter(MainActivity.this, articles);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
                else
                {
                    Toast.makeText(MainActivity.this,"Something is wrong",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });


    }
}