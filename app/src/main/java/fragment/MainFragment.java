package fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.example.administrator.newtest.R;

import java.util.ArrayList;

import MyAdapter.RecycleViewAdapter;
import Utils.SpacesItemDecoration;
import model.Prodect;

/**
 * Created by Administrator on 2015/11/25.
 */
public class MainFragment extends BaseFragment{

    private RecyclerView mRecyclerView;
    private RecycleViewAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList<Prodect> mData;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        container= (ViewGroup) inflater.inflate(R.layout.fragment_main,null);
        findViews(container);
        return container;
    }

    @Override
    public void findViews(View view) {
        mData=new ArrayList();
        for (int i=0;i<5;i++){
            mData.add(new Prodect("name"+i));
        }

        mRecyclerView= (RecyclerView) view.findViewById(R.id.content_main_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //间距
        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        mRecyclerView.addItemDecoration(decoration);
        mAdapter=new RecycleViewAdapter(mData);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.fragment_main_refreshLayout);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent),getResources().getColor(R.color.colorPrimary),getResources().getColor(R.color.holo_green_light));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                Log.d("Swipe", "Refreshing Number");
                ( new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        //Http请求
                        mData=new ArrayList();
                        for (int i=0;i<5;i++){
                            mData.add(new Prodect("刷新"+i));
                        }
                        mAdapter.clearData();
                        mAdapter.appendData(mData);
                        mAdapter.notifyDataSetChanged();
                    }
                }, 2000);
            }
        });
    }

}
