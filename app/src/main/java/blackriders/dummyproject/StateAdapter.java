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

public class StateAdapter extends BaseAdapter {


    private Context context;
    private LayoutInflater inflater;
    List<State> stateList;

    public StateAdapter(Context context, List<State> stateList) {
        this.context = context;
        this.stateList = stateList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return stateList.size();
    }

    @Override
    public Object getItem(int position) {
        return stateList.get(position);
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
            convertView = inflater.inflate(R.layout.custom_row_spinner, parent, false);

            viewHolder.txt_custom_spinner = (TextView) convertView.findViewById(R.id.txt_custom_spinner);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txt_custom_spinner.setText(stateList.get(position).getStateName());

        return convertView;
    }

    class ViewHolder {
        TextView txt_custom_spinner;
    }
}