package com.android.up_to_day;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {

    RecyclerView recyclerView;
    List<Article> articleList = new ArrayList<>();
    Newsadapter newsadapter;
    LinearProgressIndicator linearProgressIndicator;

    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,developer;

    SearchView searchView;

    Handler handler;

    private  static  int  REFRESH_TIMER = 20000;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views before calling getNews()
        recyclerView = findViewById(R.id.new_recyclerview);
        linearProgressIndicator = findViewById(R.id.progress_bar);

        handler = new Handler();

        searchView = findViewById(R.id.serch_view);

        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);
        btn5 = findViewById(R.id.btn_5);
        btn6 = findViewById(R.id.btn_6);
        btn7 = findViewById(R.id.btn_7);
        developer = findViewById(R.id.developerbtn);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);

        developer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             startActivity(new Intent(MainActivity.this,DeveloperActivity.class));
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                getNews("GENERAL", query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // Schedule the first execution of showHeadLines
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                // Fetch news after setting up views

                getNews("GENERAL",null);
                // Schedule the next execution after the specified interval
                handler.postDelayed(this, REFRESH_TIMER);

                // Set up RecyclerView

                setupRecyclerview();
            }
        }, REFRESH_TIMER);

    }


    void setupRecyclerview() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsadapter = new Newsadapter(articleList);
        recyclerView.setAdapter(newsadapter);
    }

    void changeInProgress(boolean show) {
        if (linearProgressIndicator != null) {
            if (show) {
                linearProgressIndicator.setVisibility(View.VISIBLE);
            } else {
                linearProgressIndicator.setVisibility(View.INVISIBLE);
            }
        }
    }

    void getNews(String Category, String query) {
        changeInProgress(true);
        NewsApiClient newsApiClient = new NewsApiClient("f026af75f4a041bcb20def9911244b10");
        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .language("en")
                        .category(Category)
                        .q(query)
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        changeInProgress(false);
                        articleList = response.getArticles();
                        newsadapter.updateData(articleList);
                        newsadapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.i("GOT FAILURE", throwable.getMessage());
                    }
                }
        );
    }

    @Override
    public void onClick(View v) {

        Button btn = (Button) v;
        String category = btn.getText().toString();
        getNews(category,null);
    }
}
