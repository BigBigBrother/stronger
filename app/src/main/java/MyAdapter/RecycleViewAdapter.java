package MyAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.administrator.newtest.R;

import java.util.List;

import model.Prodect;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MasonryView>{
    private List<Prodect> mData;
    private MyInterface mOnItemClickLitener;
    public void setOnItemClickLitener(MyInterface mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public RecycleViewAdapter(List<Prodect> list) {
        mData=list;
    }

    @Override
    public MasonryView onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_card_item, viewGroup, false);
        return new MasonryView(view);
    }

    @Override
    public void onBindViewHolder(final MasonryView masonryView, final int position) {
//        masonryView.imageView.setImageResource(products.get(position).img);
        masonryView.textView.setText(mData.get(position).name);
        //定义接口的回调
        if (mOnItemClickLitener != null){
            masonryView.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    mOnItemClickLitener.onItemClick(masonryView.itemView, position);
                }
            });
            masonryView.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickLitener.onItemLongClick(masonryView.itemView, position);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MasonryView extends  RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public MasonryView(View itemView){
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.base_swipe_item_icon );
            textView= (TextView) itemView.findViewById(R.id.base_swipe_item_title);
            //textView.setText(mData.get(0)+"");
        }

    }

}