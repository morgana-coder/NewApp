package com.example.android.newsapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.android.newsapp.model.Article;

import java.util.List;



public  class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{
    private Context context;
    private List<Article> articles;
    private AdapterView.OnItemClickListener onItemClickListener;
    public Adapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }
    public interface OnItemClickListener{


        void onItemClick(View v, int adapterPosition);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener= (AdapterView.OnItemClickListener) onItemClickListener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item,parent,false);


        return new MyViewHolder(view, (OnItemClickListener) onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final MyViewHolder holder1=holder;
        Article model=articles.get(position);

       RequestOptions requestOptions=new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();
        requestOptions.timeout(3000);
        Glide.with(context)
                .load(model.getUrlToImage())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder1.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder1.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder1.imageView);
        holder1.title.setText(model.getTitle());
        holder1.desc.setText(model.getDescription());
        holder1.source.setText(model.getSource().getName());
        holder1.time.setText('\u2022'+Utils.DateToTimeFormat(model.getPublishedAt()));
        holder1.publishedAd.setText(Utils.DateFormat(model.getPublishedAt()));
        holder1.author.setText(model.getAuthor());



    }


    @Override
    public int getItemCount() {
        return articles.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title,desc,author,publishedAd,source,time;
        ImageView imageView;
        ProgressBar progressBar;
        OnItemClickListener onItemClickListener;
        public MyViewHolder(@NonNull View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            title=itemView.findViewById(R.id.title);
            desc=itemView.findViewById(R.id.desc);
            author=itemView.findViewById(R.id.author);
            publishedAd=itemView.findViewById(R.id.publishedAt);
            source=itemView.findViewById(R.id.source);
            time=itemView.findViewById(R.id.time);
            imageView=itemView.findViewById(R.id.img);
            progressBar=imageView.findViewById(R.id.progress_load_photo);
            this.onItemClickListener=onItemClickListener;

        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v,getAdapterPosition());
        }
    }
}
