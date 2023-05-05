package milos.jovanovic.sopinglista;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class ShopListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Zadatak> mList;
    private DBHelper db;

    public ShopListAdapter(Context mContext) {
        this.mContext = mContext;
        this.mList = new ArrayList<Zadatak>();
        db = new DBHelper(mContext, DBHelper.DB_NAME, null, 1);
    }

    public void update(Zadatak[] lists) {
        mList.clear();
        if (lists != null) {
            for (Zadatak list : lists) {
                mList.add(list);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void removeItem(Zadatak item) {
        mList.remove(item);
        notifyDataSetChanged();
    }

    public void addItem(Zadatak item) {
        mList.add(item);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.shop_list_adapter, null);
            viewHolder = new ViewHolder();
            viewHolder.item = (TextView) convertView.findViewById(R.id.naslov_shop_list_adapter);
            viewHolder.box = (CheckBox) convertView.findViewById(R.id.checkbox_shop_list_adapter);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /* Get data Object on position from list/database */

        Zadatak a = mList.get(position);
        viewHolder.item.setText(a.getNaslov());

        if (viewHolder.box.isChecked()) {
            viewHolder.item.setPaintFlags(viewHolder.item.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            viewHolder.item.setPaintFlags(viewHolder.item.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }

        viewHolder.box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    viewHolder.item.setPaintFlags(viewHolder.item.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    a.setProvera(true);
                    db.updateItem(a.getId(), Boolean.toString(true));
                } else {
                    viewHolder.item.setPaintFlags(viewHolder.item.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                    a.setProvera(false);
                    db.updateItem(a.getId(), Boolean.toString(false));
                }
            }
        });

        convertView.setOnLongClickListener(view -> {
            mList.remove(position);

            notifyDataSetChanged();
            return true;
        });

        return convertView;
    }


    private class ViewHolder {
        TextView item;
        CheckBox box;
    }
}
