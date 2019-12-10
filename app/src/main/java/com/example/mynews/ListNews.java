package com.example.mynews;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mynews.Adapter.ListNewsAdapter;
import com.example.mynews.Common.Common;
import com.example.mynews.Interface.NewsService;
import com.example.mynews.Model.News;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.florent37.diagonallayout.DiagonalLayout;
import com.squareup.picasso.Picasso;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.System.load;

public class ListNews extends AppCompatActivity {

    KenBurnsView kbv;
    DiagonalLayout diagonalLayout;
    AlertDialog dialog;
    NewsService mService;
    TextView top_author, top_tittle;
    SwipeRefreshLayout swipeRefreshLayout;

    String source="", sortBy = "", webHotUrl = "";

    ListNewsAdapter adapter;
    RecyclerView listNews;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);

        //Service

        mService = Common.getNewsService();

        dialog = new SpotsDialog(this);

        //View
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews(source,true);
            }
        });

        diagonalLayout = (DiagonalLayout)findViewById(R.id.diagonalLayout);
        diagonalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //click to hot /latest news to read
            }
        });
        kbv = (KenBurnsView)findViewById(R.id.top_image);
        top_author = (TextView)findViewById(R.id.top_author);
        top_tittle = (TextView)findViewById(R.id.top_title);

        //Intent

        if (getIntent() != null)
        {
            source = getIntent().getStringExtra("source");
            sortBy = getIntent().getStringExtra("sortBy");

            if (!source.isEmpty() && !sortBy.isEmpty())
            {
                loadNews(source,false);
            }
        }


    }

    private void loadNews(String source, boolean isRefreshed) {
        if (!isRefreshed)
        {
            dialog.show();
            mService.getNewestArticles(Common.getAPIUrl(source, sortBy, Common.API_KEY))
                    .enqueue(new Callback<News>() {
                        @Override
                        public void onResponse(Call<News> call, Response<News> response) {
                            dialog.dismiss();

                            //Get first article
                            Picasso.with(getBaseContext());
                                    .load(response.body().getArticles().get(0).getUrlToImage())
                                    .into(kbv);


                            top_tittle.setText(response.body().getArticles().get(0).getTitle());
                            top_author.setText(response.body().getArticles().get(0).getAuthor());

                            webHotUrl = response.body().getArticles().get(0).getUrl();

                        }

                        @Override
                        public void onFailure(Call<News> call, Throwable t) {

                        }
                    });

        }
    }
}
