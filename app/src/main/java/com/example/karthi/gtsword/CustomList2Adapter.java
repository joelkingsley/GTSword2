package com.example.karthi.gtsword;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joel Kingsley on 02-10-2017.
 */

public class CustomList2Adapter extends ArrayAdapter<Chunk>{
    Context context;
    List<String> chunks = new ArrayList<String>();
    List<Boolean> levels = new ArrayList<Boolean>();
    public CustomList2Adapter(@NonNull Context context, @NonNull List<Chunk> objects) {
        super(context, R.layout.custom_list2, objects);
        this.context = context;
        for(Chunk c:objects){
            chunks.add(c.toString());
            levels.add(c.isMastered());
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.custom_list2,parent,false);

        TextView item = (TextView) rowView.findViewById(R.id.tv1);
        TextView subitem = (TextView) rowView.findViewById(R.id.tv2);

        item.setText(chunks.get(position));
        if(levels.get(position) == false){
            subitem.setTextColor(Color.RED);
            subitem.setText("Memorizing");
        }
        else{
            subitem.setTextColor(Color.GREEN);
            subitem.setText("Retained");
        }
        return rowView;
    }
}
