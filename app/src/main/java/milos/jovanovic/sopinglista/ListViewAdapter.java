package milos.jovanovic.sopinglista;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Lists> mList;

    public ListViewAdapter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<Lists>();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        Object rv = null;

        try {
            rv = mList.get(position);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return rv;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(Lists item) {
        mList.add(item);
        notifyDataSetChanged();
    }

    public void removeItem(Lists item) {
        mList.remove(item);
        notifyDataSetChanged();
    }

    public void update (Lists[] lists) {
        mList.clear();
        if(lists != null) {
            for(Lists list : lists) {
                mList.add(list);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_list_view, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.id_naslov);
            viewHolder.share = (TextView) convertView.findViewById(R.id.id_share);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /* Get data Object on position from list/database */

        Lists listView = mList.get(position);
        viewHolder.name.setText(listView.getName());

        Lists ls = mList.get(position);
        viewHolder.share.setText(listView.getShare());

        return convertView;
    }

    private class ViewHolder {
        TextView name;
        TextView share;
    }
}
