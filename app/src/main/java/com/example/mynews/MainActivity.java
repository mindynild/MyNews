package com.example.mynews;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.mynews.Adapter.ListSourceAdapter;
import com.example.mynews.Common.Common;
import com.example.mynews.Interface.NewsService;
import com.example.mynews.Model.WebSite;
import com.google.gson.Gson;

import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView listWebsite;
    RecyclerView.LayoutManager layoutManager;
    NewsService mService;
    ListSourceAdapter adapter;
    AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init cache
        Paper.init(this);

        //Init Service
        mService = Common.getNewsService();


        //Init View
        listWebsite = (RecyclerView)

                findViewById(R.id.list_source);
        listWebsite.setHasFixedSize(true);
        layoutManager = new

                LinearLayoutManager(this);
        listWebsite.setLayoutManager(layoutManager);

        dialog = new SpotsDialog(this);

        loadWebsiteSource(false);
    }

    private void loadWebsiteSource (boolean isRefreshed) {
        if (!isRefreshed)
        {
            String cache = Paper.book().read("cache");
            if (cache != null && !cache.isEmpty()); // if have cache
            {
                WebSite website = new Gson().fromJson(cache, WebSite.class); // Convert cache from Json to Object
                adaper = new ListSourceAdapter(getBaseContext(), website);
                adapter.notifyDataSetChanged();
                listWebsite.setAdapter(adapter);
            }
            else // if not have cache
            {
                dialog.show();
                //Fetch new data
                mService.getSources().enqueue(new Callback<WebSite>() {
                    @Override
                    public void onResponse(Call<WebSite> call, Response<WebSite> response) {
                        adapter = new ListSourceAdapter(getBaseContext(),response.body());
                        adapter.notifyDataSetChanged();
                        listWebsite.setAdapter(adapter);

                        //Save to cache
                        Paper.book().write("cache",new Gson().toJson(response.body()));


                    }

                    @Override
                    public void onFailure(Call<WebSite> call, Throwable t) {

                    }
                });
            }

        }
        else // If from Swipe to Refresh
        {
            public void onResponse(Call<WebSite> call, Response<WebSite> response) {
            adapter = new ListSourceAdapter(getBaseContext(),response.body());
            adapter.notifyDataSetChanged();
            listWebsite.setAdapter(adapter);

            //Save to cache
            Paper.book().write("cache",new Gson().toJson(response.body()));


        }

}



}

