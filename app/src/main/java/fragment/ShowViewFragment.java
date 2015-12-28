package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.VideoView;

import com.example.administrator.newtest.R;

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
        Toolbar toolbar= (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("lhs");
        mListView= (ListView) view.findViewById(R.id.svf_lv);
        mVideoView= (VideoView) view.findViewById(R.id.svf_play);
    }

    public void getData(){

    }
}
