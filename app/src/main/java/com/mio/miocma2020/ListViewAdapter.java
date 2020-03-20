package com.mio.miocma2020;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<FarmerItem> {

    private List<FarmerItem> farmerItems;
    private Context context;
    public ListViewAdapter(List<FarmerItem> playerItemList, Context context) {
        super(context, R.layout.list_item, playerItemList);
        this.farmerItems = playerItemList;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View listViewItem = inflater.inflate(R.layout.list_item, null, true);
        TextView textViewNo = listViewItem.findViewById(R.id.fcode);
        TextView textViewName = listViewItem.findViewById(R.id.fname);
        TextView textViewAddress = listViewItem.findViewById(R.id.faddress);
        TextView textViewGender = listViewItem.findViewById(R.id.fgender);
        TextView textViewPhone = listViewItem.findViewById(R.id.fphone);
        ImageView imgVIew = listViewItem.findViewById(R.id.Poster);

        FarmerItem farmerItem = farmerItems.get(position);
        textViewNo.setText(farmerItem.getNo());
        textViewName.setText(farmerItem.getName());
        textViewAddress.setText(farmerItem.getAddress());
        textViewGender.setText(farmerItem.getGender());
        textViewPhone.setText(farmerItem.getPhone());
        Glide.with(context).load("https://www.tendydeveloper.com/assets/img/2019/1.jpg").into(imgVIew);
        return listViewItem;
    }
}