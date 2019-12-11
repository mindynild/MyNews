package com.example.mynews.Adapter;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynews.Common.Common;
import com.example.mynews.Interface.FaviconFinderService;
import com.example.mynews.Interface.ItemClickListener;
import com.example.mynews.ListNews;
import com.example.mynews.Model.FaviconFinder;
import com.example.mynews.Model.WebSite;
import com.example.mynews.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ListSourceViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener
{
    ItemClickListener itemClickListener;

    TextView source_title;
    CircleImageView source_image;

    public ListSourceViewHolder(@NonNull View itemView) {
        super(itemView);

        source_image = (CircleImageView)itemView.findViewById(R.id.source_image);
        source_title = (TextView)itemView.findViewById(R.id.source_name);

        itemView.setOnClickListener(this);

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
public class ListSourceAdapter extends RecyclerView.Adapter<ListSourceViewHolder>{
    private Context context;
    private WebSite webSite;

    private FaviconFinderService mService;


    public ListSourceAdapter(Context context, WebSite webSite) {
        this.context = context;
        this.webSite = webSite;

        mService = Common.getIconService();

    }

    @NonNull
    @Override
    public ListSourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.source_layout, parent, false);
        return new ListSourceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListSourceViewHolder holder, int position)
    {
        StringBuilder iconBetterAPI = new StringBuilder("https://besticon-demo.herokuapp.com/allicons.json?url=");
        iconBetterAPI.append(webSite.getSources().get(position).getUrl());

        mService.getIconUrl(iconBetterAPI.toString())
                .enqueue(new Callback<FaviconFinder>() {
                    @Override
                    public void onResponse(Call<FaviconFinder> call, Response<FaviconFinder> response) {
                        if (response.body().getIcons().size() > 0 )
                        {
                            Picasso.with(context)
                                    .load(response.body().getIcons().get(0).getUrl())
                                    .into(holder.source_image);

                        }


                    }

                    @Override
                    public void onFailure(Call<FaviconFinder> call, Throwable t) {

                    }
                });

        holder.source_title.setText(webSite.getSources().get(position).getName());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(context, ListNews.class);
                intent.putExtra("source", webSite.getSources().get(position).getId());
                intent.putExtra("sortBy", (Bundle) webSite.getSources().get(position).getSortByAvailable().get(0)); // get default SortBy Correction : (Bundle)
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (webSite!= null) //correction suite  a fatal exception
        {
            return webSite.getSources().size();
        }
        else
        {
            return (-1);
        }
    }
}
