package com.example.mynews.Adapter;


import android.content.Context;
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
import com.example.mynews.Model.FaviconFinder;
import com.example.mynews.Model.WebSite;
import com.example.mynews.R;

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

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View v) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
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
    public void onBindViewHolder(@NonNull ListSourceViewHolder holder, int position)
    {
        StringBuilder iconBetterAPI = new StringBuilder("https://besticon-demo.herokuapp.com/allicons.json?url=");
        iconBetterAPI.append(webSite.getSources().get(position).getUrl());

        mService.getIconUrl(iconBetterAPI.toString())
                .enqueue(new Callback<FaviconFinder>() {
                    @Override
                    public void onResponse(Call<FaviconFinder> call, Response<FaviconFinder> response) {
                        
                    }

                    @Override
                    public void onFailure(Call<FaviconFinder> call, Throwable t) {

                    }
                });

    }

    @Override
    public int getItemCount() {
        return webSite.getSources().size();
    }
}
