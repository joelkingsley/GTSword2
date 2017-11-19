package com.example.karthi.gtsword;

import android.content.Context;
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
 * Created by Joel Kingsley on 24-09-2017.
 */

public class SectionListAdapter extends ArrayAdapter<Section> {
    private final Context context;
    private final List<Section> objects = new ArrayList<Section>();
    private final List<String> items = new ArrayList<String>();
    private final List<String> subitems1 = new ArrayList<String>();
    private final List<String> subitems2 = new ArrayList<String>();

    public SectionListAdapter(@NonNull Context context, @NonNull List<Section> objects) {
        super(context, R.layout.section_list, objects);
        this.context = context;
        for(int i=0;i<objects.size();i++){
            this.objects.add(objects.get(i));
            this.items.add(objects.get(i).toString());
            DBHelper db = new DBHelper(context);
            if(db.checkIfMasteredSection(objects.get(i).get_sec_id())){
                this.subitems1.add("Retained Section");
                this.subitems2.add("Percentage: 100%");
            }
            else{
                List<Chunk> chunks = db.getChunksOfSection(objects.get(i).get_sec_id());
                int mastered=0;
                int total=chunks.size();
                for(int j=0;j<chunks.size();j++){
                    if(db.checkIfMasteredChunk(chunks.get(j).get_id())){
                        mastered++;
                    }
                }
                this.subitems1.add("Chunks " + mastered + "/" + total + " completed");
                this.subitems2.add("Percentage: " + (float)(mastered*100)/total);
            }
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.section_list,parent,false);

        TextView item = (TextView) rowView.findViewById(R.id.item);
        TextView subitem1 = (TextView) rowView.findViewById(R.id.subItem1);
        TextView subitem2 = (TextView) rowView.findViewById(R.id.subItem2);

        item.setText(items.get(position));
        subitem1.setText(subitems1.get(position));
        subitem2.setText(subitems2.get(position));
        return rowView;
    }
}
