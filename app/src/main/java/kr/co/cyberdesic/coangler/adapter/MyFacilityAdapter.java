package kr.co.cyberdesic.coangler.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kr.co.cyberdesic.coangler.R;
import kr.co.cyberdesic.coangler.helper.ImageViewHelper;
import kr.co.cyberdesic.coangler.model.Facility;
import kr.co.cyberdesic.coangler.model.ModelBase;

public class MyFacilityAdapter extends RecyclerView.Adapter<MyFacilityAdapter.ViewHolder> {

    private Context mContext;
    private List<Facility> mItems;
    private int mSelectedItemPosition = -1;
    private ViewHolder mSelectedViewHolder;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(ModelBase item);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewGroup contentFrame;
        public TextView tvName;
        public TextView tvRate;
        public TextView tvLevel;
        public ImageView ivArrow;
        public TextView tvDate;

        public ViewHolder(View view) {
            super(view);

            contentFrame = (ViewGroup) view.findViewById(R.id.content_frame);

            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvRate = (TextView) view.findViewById(R.id.tv_rate);
            tvLevel = (TextView) view.findViewById(R.id.tv_level);
            ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);
            tvDate = (TextView) view.findViewById(R.id.tv_date);
        }
    }

    public MyFacilityAdapter(Context context, List<Facility> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_myfacility, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Facility facility = mItems.get(position);

        holder.tvName.setText(facility.name);

        if (facility.level != null && facility.level.size() > 0) {
            holder.tvRate.setText( String.format("%s%%", facility.last_rate) );
            holder.tvLevel.setText( String.format("%sm", facility.last_level) );
            holder.tvDate.setText( facility.last_date);

            int levelCnt = facility.level.size();
            float fLevel1 = 0F;
            float fLevel2 = 0F;
            if (levelCnt > 2) {
                fLevel1 = Float.parseFloat( facility.level.get(levelCnt -2).level );
                fLevel2 = Float.parseFloat( facility.level.get(levelCnt -1).level );

                if (fLevel1 < fLevel2) {
                    holder.ivArrow.setVisibility(View.VISIBLE);
                    holder.ivArrow.setImageDrawable(mContext.getDrawable(R.drawable.ic_arrow_up));
                    ImageViewHelper.with(mContext)
                            .withImageView(holder.ivArrow)
                            .tint(Color.parseColor("#0000FF"));

                } else if (fLevel1 == fLevel2) {
                    holder.ivArrow.setVisibility(View.INVISIBLE);

                } else {
                    holder.ivArrow.setVisibility(View.VISIBLE);
                    holder.ivArrow.setImageDrawable(mContext.getDrawable(R.drawable.ic_arrow_down));
                    ImageViewHelper.with(mContext)
                            .withImageView(holder.ivArrow)
                            .tint(Color.parseColor("#FF0000"));
                }
            }

        } else {
            holder.tvLevel.setText("No data");
            holder.tvDate.setText("--");
        }

        holder.contentFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(facility);
                }

                mSelectedViewHolder = holder;
                mSelectedItemPosition = position;

                MyFacilityAdapter.this.notifyDataSetChanged();
            }
        });

        if (mSelectedViewHolder != null) {
            if (mSelectedItemPosition == position) {
                holder.contentFrame.setAlpha(1.0f);
                holder.contentFrame.setScaleX(1.0f);
                holder.contentFrame.setScaleY(1.0f);
            } else {
                holder.contentFrame.setAlpha(0.5f);
                holder.contentFrame.setScaleX(0.95f);
                holder.contentFrame.setScaleY(0.95f);
            }

        } else {
            holder.contentFrame.setAlpha(1.0f);
            holder.contentFrame.setScaleX(1.0f);
            holder.contentFrame.setScaleY(1.0f);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}