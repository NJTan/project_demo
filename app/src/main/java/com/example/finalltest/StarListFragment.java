package com.example.finalltest;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import  com.example.finalltest.StartContent;
public class StarListFragment extends ListFragment {

    private Callbacks mCallbacks;
    //定义一个回调接口，在Activity中需要实现
    //该Framgent将通过该接口与他它所在的Activity交互
    public interface Callbacks{
        void onItemSelectedListener(Integer ino);
    }

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ArrayAdapter()里的第二个参数是系统提供的布局，第三个是布局里ＴextView的id
        setListAdapter(new ArrayAdapter<StartContent.Project>(getActivity()
                , android.R.layout.simple_list_item_activated_1,
                android.R.id.text1, StartContent.ITEMS));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)){
            throw new IllegalStateException("必须实现Callbacks接口");
        }
        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mCallbacks = null;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //回调该接口，传入了id
        mCallbacks.onItemSelectedListener(StartContent.ITEMS.get(position).Ino);
    }
}

