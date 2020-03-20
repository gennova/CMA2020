package com.mio.miocma2020.transaction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.annotation.NonNull;
import com.mio.miocma2020.R;

import java.util.List;

public class ListTransactionAdapter extends ArrayAdapter {
    private List<Transaction> transactions;
    private Context context;

    public ListTransactionAdapter(List<Transaction> transactions, Context context) {
        super(context, R.layout.list_item_farmer_card,transactions);
        this.transactions = transactions;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View listViewItem = inflater.inflate(R.layout.list_item_farmer_card, null, true);
        TextView transtext = listViewItem.findViewById(R.id.transcode);
        TextView cnotext = listViewItem.findViewById(R.id.cnoid);
        TextView fnotext = listViewItem.findViewById(R.id.fcnoid);
        TextView productid = listViewItem.findViewById(R.id.productid);
        TextView weightid = listViewItem.findViewById(R.id.weightid);
        TextView rdate = listViewItem.findViewById(R.id.dateid);
        TextView flotcode = listViewItem.findViewById(R.id.flotID);

        Transaction transaction = transactions.get(position);
        transtext.setText(transaction.getTransid());
        cnotext.setText(transaction.getCno());
        fnotext.setText(transaction.getFno());
        productid.setText(transaction.getProdid());
        weightid.setText(transaction.getWeight());
        rdate.setText(transaction.getRdate());
        flotcode.setText(transaction.getFlot());
        return listViewItem;
    }
}
