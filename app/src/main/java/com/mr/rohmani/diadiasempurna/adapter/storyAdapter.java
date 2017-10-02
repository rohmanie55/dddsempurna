package com.mr.rohmani.diadiasempurna.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mr.rohmani.diadiasempurna.R;
import com.mr.rohmani.diadiasempurna.model.storyModel;
import com.mr.rohmani.diadiasempurna.storyHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 31/07/2017.
 */

public class storyAdapter extends RecyclerView.Adapter<storyHolder>{
    private List<storyModel> storyList = new ArrayList<>();
    LayoutInflater inflater;
    private final Context context;

    public storyAdapter(List<storyModel> storyList, Context context) {
        this.storyList = storyList;
        this.context=context;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public storyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_chapter, parent, false);
        storyHolder ViewHolder = new storyHolder(view);

        return ViewHolder;
    }



    @Override
    public void onBindViewHolder(storyHolder holder, int position) {
        holder.tv_no.setText( String.valueOf(storyList.get(position).getPart()));
        holder.tv_judul.setText(storyList.get(position).getTitle());
    }


    @Override
    public int getItemCount() {
        return storyList.size();
    }

}
