package com.dawn.restroview.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dawn.restroview.R;
import com.dawn.restroview.model.AddBusiness;

import java.util.List;

public class BusinessListAdapter extends RecyclerView.Adapter<BusinessListAdapter.BusinessViewHolder> {
    private List<AddBusiness> itemList;
    private Context mContext;

    public BusinessListAdapter(Context mContext) {
        this.mContext = mContext;
    }


    public BusinessListAdapter(List<AddBusiness> itemList, Context mContext) {
        this.itemList = itemList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BusinessViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.custom_list_row, viewGroup, false);
        return new BusinessViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessViewHolder businessViewHolder, int i) {
        AddBusiness currentBusiness = itemList.get(i);
        businessViewHolder.businessTitle.setText(currentBusiness.getBusinessName());


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class BusinessViewHolder extends RecyclerView.ViewHolder{

        public TextView businessTitle;
        public BusinessViewHolder(@NonNull View itemView) {
            super(itemView);

            businessTitle = itemView.findViewById(R.id.custom_list_row_title);
        }
    }
}
