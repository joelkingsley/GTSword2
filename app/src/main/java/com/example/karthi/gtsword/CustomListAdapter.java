package com.example.karthi.gtsword;

import android.content.Context;
import android.graphics.Color;
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
 * Created by Joel Kingsley on 22-09-2017.
 */

public class CustomListAdapter extends ArrayAdapter<Chunk>{
    private final Context context;

    private final List<String> chunk = new ArrayList<String>();
    private final List<String> nextDateOfReview = new ArrayList<String>();

    public CustomListAdapter(@NonNull Context context, List<Chunk> chunks) {
        super(context, R.layout.custom_list, chunks);
        this.context = context;
        for(int i=0;i<chunks.size();i++){
            this.chunk.add(chunks.get(i).toString());
            this.nextDateOfReview.add(chunks.get(i).getNextDateOfReview());
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.custom_list,parent,false);

        TextView item = (TextView) rowView.findViewById(R.id.item);
        TextView subitem = (TextView) rowView.findViewById(R.id.subItem);

        item.setText(chunk.get(position));
        String subItemString = "Next Date of Review: " + nextDateOfReview.get(position);
        subitem.setText(subItemString);
        subitem.setTextColor(Color.LTGRAY);

        return rowView;
    }
}
