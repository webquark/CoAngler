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
import kr.co.cyberdesic.coangler.util.DateUtil;

public class FacilityAdapter extends RecyclerView.Adapter<FacilityAdapter.ViewHolder> {

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
        public TextView tvLevel;
        public TextView tvRate;
        public TextView tvDate;

        public ViewGroup vgLast;
        public ImageView ivArrow;
        public TextView tvLevel1;
        public TextView tvRate1;
        public TextView tvDate1;

        public ViewHolder(View view) {
            super(view);

            contentFrame = (ViewGroup) view.findViewById(R.id.content_frame);

            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvLevel = (TextView) view.findViewById(R.id.tv_level);
            tvRate = (TextView) view.findViewById(R.id.tv_rate);
            tvDate = (TextView) view.findViewById(R.id.tv_date);

            vgLast = (ViewGroup) view.findViewById(R.id.vg_last);
            ivArrow = (ImageView) view.findViewById(R.id.iv_arrow);
            tvLevel1 = (TextView) view.findViewById(R.id.tv_level1);
            tvRate1 = (TextView) view.findViewById(R.id.tv_rate1);
            tvDate1 = (TextView) view.findViewById(R.id.tv_date1);

        }
    }

    public FacilityAdapter(Context context, List<Facility> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public FacilityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_facility, parent, false);

        return new FacilityAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FacilityAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Facility facility = mItems.get(position);

        holder.tvName.setText(facility.name);

        float fRate = 0;
        float fLevel = 0;

        // 마지막 수위
        if (facility.level != null && facility.level.size() > 0) {
            fLevel = Float.parseFloat( facility.getLevel(0) );
            fRate = Float.parseFloat( facility.getRate(0) );

            holder.tvLevel.setText( String.format("%.2fm", fLevel) );
            holder.tvRate.setText( String.format("%.2f%%", fRate) );
            holder.tvDate.setText(DateUtil.dateFormat(facility.getDate(0), "MM.dd"));

        } else {
            holder.tvLevel.setText("No data");

            holder.tvDate.setText("--");
        }

        // 신규 수위
        if (facility.last_level != null) {
            float fLevel1 = Float.parseFloat( facility.last_level );

            holder.vgLast.setVisibility(View.VISIBLE);
            holder.tvLevel1.setText( String.format("%.2fm", Float.parseFloat(facility.last_level)) );
            holder.tvRate1.setText( String.format("%s%%", facility.last_rate) );
            holder.tvDate1.setText(DateUtil.dateFormat(facility.last_date, "MM.dd"));

            if (fLevel < fLevel1) {
                holder.ivArrow.setVisibility(View.VISIBLE);
                holder.ivArrow.setImageDrawable(mContext.getDrawable(R.drawable.ic_arrow_drop_up));
                ImageViewHelper.with(mContext)
                        .withImageView(holder.ivArrow)
                        .tint(Color.parseColor("#0000FF"));

            } else if (fLevel == fLevel1) {
                holder.ivArrow.setVisibility(View.INVISIBLE);

            } else {
                holder.ivArrow.setVisibility(View.VISIBLE);
                holder.ivArrow.setImageDrawable(mContext.getDrawable(R.drawable.ic_arrow_drop_down));
                ImageViewHelper.with(mContext)
                        .withImageView(holder.ivArrow)
                        .tint(Color.parseColor("#FF0000"));
            }

        } else {
            holder.vgLast.setVisibility(View.INVISIBLE);
        }

        holder.contentFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(facility);
                }

                mSelectedViewHolder = holder;
                mSelectedItemPosition = position;

                FacilityAdapter.this.notifyDataSetChanged();
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
