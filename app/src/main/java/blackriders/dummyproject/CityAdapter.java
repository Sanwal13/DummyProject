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

public class CityAdapter extends BaseAdapter {


    List<City> cityList;
    private Context context;
    private LayoutInflater inflater;

    public CityAdapter(Context context, List<City> cityList) {
        this.context = context;
        this.cityList = cityList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return cityList.size();
    }

    @Override
    public Object getItem(int position) {
        return cityList.get(position);
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

        viewHolder.txt_custom_spinner.setText(cityList.get(position).getCityName());

        return convertView;
    }

    class ViewHolder {
        TextView txt_custom_spinner;
    }
}