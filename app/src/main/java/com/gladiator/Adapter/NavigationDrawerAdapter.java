package com.gladiator.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gladiator.CustomView.CustomFontTextView;
import com.gladiator.R;
import com.gladiator.icomoon.IconTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kkhurana on 26/01/17.
 */

public class NavigationDrawerAdapter extends BaseAdapter {
    private Context context;
    private List<NavDrawerItem> navDrawerItem;
    int mSelectedPostion = -1;

    public NavigationDrawerAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItem) {
        this.context = context;
        this.navDrawerItem = navDrawerItem;
    }

    @Override
    public int getCount() {
        return navDrawerItem.size();
    }

    @Override
    public Object getItem(int position) {
        return navDrawerItem.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view;
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflator.inflate(R.layout.navigation_drawer_item, null);
            holder = new ViewHolder();
            holder.ivNavigationDrawerItemIcon = (IconTextView) view.findViewById(R.id.iv_navigation_drawer_item_icon);
            holder.tvNavigationDrawerItemTitle = (CustomFontTextView) view.findViewById(R.id.tv_navigation_drawer_item_title);
            holder.tvNavigationDrawerItemCounter = (CustomFontTextView) view.findViewById(R.id.tv_item_count);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.ivNavigationDrawerItemIcon.setText(navDrawerItem.get(position).getResId());
        holder.tvNavigationDrawerItemTitle.setText(navDrawerItem.get(position).getTitle());
        holder.tvNavigationDrawerItemTitle.setContentDescription(navDrawerItem.get(position).getTitle());

        if(getmSelectedPostion() == position){
            view.setSelected(true);
        }else{
            view.setSelected(false);
        }

        return view;
    }

    public void setSelectedPosition(int position) {
        mSelectedPostion = position;
    }

    public int getmSelectedPostion() {
        return mSelectedPostion;
    }

    private class ViewHolder {
        public IconTextView ivNavigationDrawerItemIcon;
        public CustomFontTextView tvNavigationDrawerItemTitle;
        public CustomFontTextView tvNavigationDrawerItemCounter;
    }

}