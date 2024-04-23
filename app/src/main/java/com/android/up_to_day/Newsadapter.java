package com.android.up_to_day;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kwabenaberko.newsapilib.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Newsadapter extends RecyclerView.Adapter<Newsadapter.NewsViewHolder>{

    List<Article>articleList;
    Newsadapter(List<Article>articleList){
        this.articleList = articleList;

    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_recycler_row,parent,false);
         return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        Article article = articleList.get(position);
        holder.texttitle.setText(article.getTitle());
        holder.textsource.setText(article.getSource().getName());
        Picasso.get().load(article.getUrlToImage())
                .error(R.drawable.hide_img)
                .placeholder(R.drawable.hide_img)
                .into(holder.article_img);

        holder.itemView.setOnClickListener((v -> {
            Intent intent = new Intent(v.getContext(),Fullnewsactivity.class);
            intent.putExtra("url",article .getUrl());
            v.getContext().startActivity(intent);
        }));

    }

    void  updateData(List<Article> data){
        articleList.clear();
        articleList.addAll(data);
    }

    @Override
    public int getItemCount() {

        return articleList.size();
    }

    class  NewsViewHolder extends RecyclerView.ViewHolder{

        TextView texttitle,textsource;
        ImageView article_img;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            texttitle= itemView.findViewById(R.id.article_title);
            textsource = itemView.findViewById(R.id.article_source);
            article_img = itemView.findViewById(R.id.article_img_view);

        }
    }
}
