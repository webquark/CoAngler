package kr.co.cyberdesic.coangler.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kr.co.cyberdesic.coangler.R;
import kr.co.cyberdesic.coangler.model.Facility;
import kr.co.cyberdesic.coangler.model.ModelBase;

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
        public TextView tvDate;

        public ViewHolder(View view) {
            super(view);

            contentFrame = (ViewGroup) view.findViewById(R.id.content_frame);

            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvLevel = (TextView) view.findViewById(R.id.tv_level);
            tvDate = (TextView) view.findViewById(R.id.tv_date);
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

        if (facility.last_level != null) {
            holder.tvLevel.setText( String.format("%sm(%s%%)", facility.last_level, facility.last_rate) );
            holder.tvDate.setText( facility.last_date);

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
