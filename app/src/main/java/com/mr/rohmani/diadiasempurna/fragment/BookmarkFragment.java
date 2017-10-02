package com.mr.rohmani.diadiasempurna.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mr.rohmani.diadiasempurna.Pages;
import com.mr.rohmani.diadiasempurna.R;
import com.mr.rohmani.diadiasempurna.adapter.bookmarkAdapter;
import com.mr.rohmani.diadiasempurna.dbHelper;
import com.mr.rohmani.diadiasempurna.model.modelBookmark;
import com.mr.rohmani.diadiasempurna.recycleClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 30/07/2017.
 */

public class BookmarkFragment extends Fragment{

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private bookmarkAdapter adapter;
    private dbHelper dbhelper;
    private List<modelBookmark> bookList = new ArrayList<>();
    private List<Integer> pilihanBookmark = new ArrayList<>();

    private TextView tv_info;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle SavedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bookmark, container, false);

        dbhelper = new dbHelper(getActivity());

        tv_info = (TextView) rootView.findViewById(R.id.tv_info);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);

        bookList = dbhelper.getAllBookmark();

        adapter = new bookmarkAdapter(bookList, getActivity());
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        if (adapter.getItemCount() ==0){
            tv_info.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tv_info.setVisibility(View.GONE);

            recyclerView.addOnItemTouchListener(
                    new recycleClickListener(getActivity(), recyclerView, new recycleClickListener.OnItemClickListener() {
                        @Override public void onItemClick(View view, int position) {
                            // TODO Handle item click
                            modelBookmark book = bookList.get(position);
                            int part = book.getPart();
                            Intent i = new Intent(getActivity(), Pages.class);
                            i.putExtra("part", part);
                            startActivity(i);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                            view.setBackgroundColor(Color.parseColor("#f70505"));

                            modelBookmark book = bookList.get(position);
                            pilihanBookmark.add(book.getPart());
                            dataPasser.onDataPass(pilihanBookmark);
                        }
                    })
            );
        }

        return rootView;
    }

    public interface OnDataPass {
        public void onDataPass(List<Integer> bookList);
    }

    OnDataPass dataPasser;

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        dataPasser = (OnDataPass) a;
    }


}
