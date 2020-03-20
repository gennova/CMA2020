package com.mio.miocma2020.transaction;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.mio.miocma2020.R;

import java.util.ArrayList;
import java.util.List;

public class ListProductAdapter extends ArrayAdapter<ProductItem> implements Filterable {
    private List<ProductItem> productItems,tempArray;
    private Context context;
    CustomFilter cs;

    public ListProductAdapter(List<ProductItem> playerItemList, Context context) {
        super(context, R.layout.list_item_product2, playerItemList);
        this.productItems = playerItemList;
        this.tempArray = playerItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View listViewItem = inflater.inflate(R.layout.list_item_product2, null, true);
        TextView productName = listViewItem.findViewById(R.id.pname);
        TextView productID = listViewItem.findViewById(R.id.prodid2);

        ProductItem p = productItems.get(position);
        productName.setText(p.getProduct_name());
        productID.setText(p.getProduct_id());
        return listViewItem;
    }

    @Override
    public Filter getFilter() {
        if(cs==null){
            cs = new CustomFilter();
        }
        return cs;
    }

    class CustomFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            Log.d("CUSTUM FILTER TENDY","Watching" +constraint);
            FilterResults results = new FilterResults();
            if(constraint!=null && constraint.length()>0){
                constraint = constraint.toString().toUpperCase();
                Log.d("CUSTUM FILTER TENDY","STARTED " +constraint);
            List<ProductItem> filters = new ArrayList<>();
            for(int i=0;i<tempArray.size();i++){
                if(tempArray.get(i).getProduct_name().toUpperCase().contains(constraint)){
                    ProductItem item = new ProductItem(tempArray.get(i).getProduct_id(),tempArray.get(i).getProduct_name());
                    filters.add(item);
                    productItems.add(item);
                }
            }
            results.count= filters.size();
            results.values=filters;
            }else {
                results.count= tempArray.size();
                results.values=tempArray;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productItems = (List<ProductItem>)results.values;
            notifyDataSetChanged();
        }
    }
}
