package com.rootlol.chatrootlol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.rootlol.chatrootlol.R;
import com.rootlol.chatrootlol.objMess.bodyMess;

import java.util.List;

import com.rootlol.chatrootlol.R;
import retrofit2.Callback;

public class MessBody extends BaseAdapter {

    private List<bodyMess> list;
    private LayoutInflater layoutInflater;

    public MessBody(Context context, List<bodyMess> list){
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            view = layoutInflater.inflate(R.layout.item_mess, parent, false);
        }
        bodyMess objbodyMess =getobjbodymess(position);
        TextView ContentMess = (TextView) view.findViewById(R.id.layout3TextView1);
        TextView userLoginMess = (TextView) view.findViewById(R.id.layout3TextView2);
        ContentMess.setText(objbodyMess.getLogin());
        userLoginMess.setText(objbodyMess.getMessege());

        return view;
    }

    private bodyMess getobjbodymess(int position){
        return (bodyMess) getItem(position);
    }
}
