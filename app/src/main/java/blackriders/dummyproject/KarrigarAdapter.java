package blackriders.dummyproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sanwal Singh on 4/4/17.
 */

public class KarrigarAdapter extends BaseAdapter {


    List<Karrigar> karrigars;
    private Context context;
    private LayoutInflater inflater;

    public KarrigarAdapter(Context context, List<Karrigar> karrigars) {
        this.context = context;
        this.karrigars = karrigars;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return karrigars.size();
    }

    @Override
    public Object getItem(int position) {
        return karrigars.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.custom_list_row, parent, false);

            viewHolder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
            viewHolder.txt_email = (TextView) convertView.findViewById(R.id.txt_email);
            viewHolder.txt_address = (TextView) convertView.findViewById(R.id.txt_address);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txt_name.setText(("Name : ").concat(karrigars.get(position).getK_name()));
        viewHolder.txt_email.setText(("Email :").concat(karrigars.get(position).getK_email()));
        viewHolder.txt_address.setText(("Address :").concat(karrigars.get(position).getK_address()));

        return convertView;
    }

    class ViewHolder {
        TextView txt_name, txt_email, txt_address;
    }
}