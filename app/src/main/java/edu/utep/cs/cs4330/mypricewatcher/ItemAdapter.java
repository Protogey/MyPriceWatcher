package edu.utep.cs.cs4330.mypricewatcher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/** Provide views for an AdapterView by returning a view
 * for each ToDoItem contained in a list. */
//Class implemented from the course website
public class ItemAdapter extends ArrayAdapter<Item> {

    public ItemAdapter(Context context, int resourceId, List<Item> items) {
        super(context, resourceId, items);
    }

    public interface ItemClickListener {
        void itemClicked(Item item);
    }

    //set method, used to udpate name or url, if the input is not name, it must be url
    public void set(int position, String s, View v, String mode){
        Item current = getItem(position);
        if(mode.equalsIgnoreCase("Name")){
            current.setName(s);
            TextView itemName = v.findViewById(R.id.ItemName);
            itemName.setText(s);
        }
        if(mode.equalsIgnoreCase("Update")){
            current.setNPrice(s);
            TextView price = v.findViewById(R.id.NewPrice);
            price.setText(s);
        }
        else{
            current.setURL(s);
            TextView itemURL = v.findViewById(R.id.ItemURL);
            itemURL.setText(s);
        }
    }

    private ItemClickListener listener;

    public void setItemClikListener(ItemClickListener listener) {
        this.listener = listener;
    }

    //returns the name of the item at the given position
    public String getName(int position){
        Item current = getItem(position);
        return current.Name();
    }

    //returns the url of the item at the given position
    public String getURL(int position){
        Item current = getItem(position);
        return current.URL();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item, parent, false);
        }
        Item current = getItem(position);
        TextView itemName = convertView.findViewById(R.id.ItemName);
        itemName.setText(current.Name());
        TextView itemURL = convertView.findViewById(R.id.ItemURL);
        itemURL.setText(current.URL());
        TextView OriginalPrice = convertView.findViewById(R.id.OriginalPrice);
        OriginalPrice.setText(current.Price());
        TextView NewPrice = convertView.findViewById(R.id.NewPrice);
        NewPrice.setText(current.NPrice());
        TextView PercentChange = convertView.findViewById(R.id.PercentChange);
        PercentChange.setText(current.Percent());

        return convertView;
    }
}