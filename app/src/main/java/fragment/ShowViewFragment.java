package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.VideoView;

import com.example.administrator.newtest.R;

import MyAdapter.CommentAdapter;

/**
 * Created by huangan on 15/12/28.
 */
public class ShowViewFragment extends BaseFragment{


    private ListView mListView;
    private VideoView mVideoView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        container= (ViewGroup) inflater.inflate(R.layout.fragment_show_view,null);
        findViews(container);
        return container;
    }

    @Override
    public void findViews(View view) {
//        Toolbar toolbar = (Toolbar) view.findViewById(R.id.third_activity_toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.back);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setTitle("back");
//        }

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.play_comment_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new CommentAdapter(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void getData(){

    }
}
