package MyAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.newtest.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhahang on 2015/12/21.
 */
public class CommentAdapter extends RecyclerView.Adapter <CommentAdapter.ViewHolder>{

    private List<Map<String, Object>> dataList;


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView userName;
        public TextView commenContent;
        public TextView commentTime;
        public TextView ZanCount;
        public ImageButton imgZan;

        public ViewHolder(View itemView){
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img);
            userName = (TextView) itemView.findViewById(R.id.comment_user_name);
            commenContent = (TextView) itemView.findViewById(R.id.comment_content);
            commentTime = (TextView) itemView.findViewById(R.id.comment_time);
            ZanCount = (TextView) itemView.findViewById(R.id.comment_zan_count);
        }
    }

    public CommentAdapter(Context context){
        dataList = getData();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setBackgroundResource((Integer) dataList.get(position).get("userimg"));
        holder.userName.setText((String) dataList.get(position).get("username"));
        holder.commenContent.setText((String) dataList.get(position).get("usercomment"));
        holder.commentTime.setText((String) dataList.get(position).get("commenttime"));
        holder.ZanCount.setText((String) dataList.get(position).get("zancount") + " 赞");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item ,parent ,false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        for (int i = 0; i < 20; i++) {
            map = new HashMap<String, Object>();
            map.put("userimg", R.mipmap.ic_launcher);
            map.put("username", "用户名:");
            map.put("usercomment", ",用户评论,用户评论,用户评论,用户评论,用户评论,用户评论,用户评论,用户评论,用户评论,用户评论,用户评论,用户评论,用户评论,");
            map.put("commenttime", "2015-12-12");
            map.put("zancount", i + "");
            dataList.add(map);
        }
        return dataList;
    }

    public  List<Map<String, Object>> getDataList(){
        return dataList;
    }
}
