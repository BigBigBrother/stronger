package fragment;

import android.content.Intent;
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
import android.widget.Toast;

import com.example.administrator.newtest.PlayActivity;
import com.example.administrator.newtest.R;

import java.util.ArrayList;
import java.util.List;

import MyAdapter.MyInterface;
import MyAdapter.RecycleViewAdapter;
import MyAdapter.SpacesItemDecoration;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import model.Prodect;
import view.LoadMoreRecyclerView;

/**
 * Created by Administrator on 2015/11/25.
 */
public class MainFragment extends BaseFragment{

    private LoadMoreRecyclerView mRecyclerView;
    private RecycleViewAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList<Prodect> mData;


    private int drawable_Res []={R.drawable.lhs,R.drawable.lsh2,R.drawable.lhs3,R.drawable.lhs4,R.drawable.lhs5};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        container= (ViewGroup) inflater.inflate(R.layout.fragment_main,null);
        findViews(container);
        return container;
    }

    public List getData(){
        List data=new ArrayList();
        for (int i=0;i<5;i++){
            data.add(new Prodect("龙虎山",drawable_Res[i]));
        }
        return data;
    }

    public List getNewData(){
        List data=new ArrayList();
        for (int i=0;i<5;i++){
            data.add(new Prodect("刷新"+i,drawable_Res[i]));
        }
        return data;
    }

    @Override
    public void findViews(View view) {
        getActivity().findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.scrollToPosition(0);
            }
        });

        mRecyclerView= (LoadMoreRecyclerView) view.findViewById(R.id.content_main_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //间距  GridView
        SpacesItemDecoration decoration=new SpacesItemDecoration(16);
        mRecyclerView.addItemDecoration(decoration);

        /**
         * recyclerView item的动画
         * (爱鲜蜂 App效果动画)
         * 动画的类型可选   参照  https://github.com/wasabeef/recyclerview-animators
         */
        mRecyclerView.setItemAnimator(new FadeInAnimator());
        mAdapter = new RecycleViewAdapter(getData());
        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(mAdapter);
        SlideInBottomAnimationAdapter slideInBottomAnimationAdapter = new SlideInBottomAnimationAdapter(alphaInAnimationAdapter);
        mRecyclerView.setAdapter(slideInBottomAnimationAdapter);

        mAdapter.setOnItemClickLitener(new MyInterface() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), PlayActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(),"长按Recy",Toast.LENGTH_SHORT).show();
            }
        });
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
                        mAdapter.clearData();
                        mData= (ArrayList<Prodect>) getNewData();
                        mRecyclerView.setItemAnimator(new FadeInAnimator());
                        mAdapter = new RecycleViewAdapter(mData);
                        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(mAdapter);
                        SlideInBottomAnimationAdapter slideInBottomAnimationAdapter = new SlideInBottomAnimationAdapter(alphaInAnimationAdapter);
                        mRecyclerView.setAdapter(slideInBottomAnimationAdapter);
                    }
                }, 1000);
            }
        });

        mRecyclerView.setAutoLoadMoreEnable(true);//支持上拉加载更多
        mRecyclerView.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                mRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        mAdapter.appendData(getData());
                        mRecyclerView.notifyMoreFinish(true);
                    }
                }, 1000);
            }
        });

    }

}
