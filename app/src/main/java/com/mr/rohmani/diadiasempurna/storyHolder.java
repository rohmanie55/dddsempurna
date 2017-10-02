package com.mr.rohmani.diadiasempurna;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by USER on 01/08/2017.
 */

public class storyHolder extends RecyclerView.ViewHolder{

    public TextView tv_no;
    public TextView tv_judul;
    public CardView card_view;

    public storyHolder(View itemView) {
        super(itemView);

        tv_no= (TextView) itemView.findViewById(R.id.tv_no);
        //menampilkan text dari widget CardView pada id daftar_judul
        tv_judul= (TextView) itemView.findViewById(R.id.tv_judul);

        card_view = (CardView) itemView.findViewById(R.id.card_view);
    }
}
