package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.newtest.R;

/**
 * Created by Administrator on 2015/12/10.
 */
public class TitleFragment extends BaseFragment {

    private Button titleButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_title,container,false);
        titleButton =(Button) view.findViewById(R.id.title_left_btn);
        titleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"执行返回操作",Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    @Override
    public void findViews(View view) {

    }
}
