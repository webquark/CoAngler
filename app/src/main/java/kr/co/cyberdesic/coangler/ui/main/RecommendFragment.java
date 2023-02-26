package kr.co.cyberdesic.coangler.ui.main;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import kr.co.cyberdesic.coangler.R;
import kr.co.cyberdesic.coangler.adapter.MyFacilityAdapter;
import kr.co.cyberdesic.coangler.decoration.SpacesItemDecoration;
import kr.co.cyberdesic.coangler.model.APIResponse;
import kr.co.cyberdesic.coangler.model.Facility;
import kr.co.cyberdesic.coangler.model.ModelBase;
import kr.co.cyberdesic.coangler.server.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 추천 탭 프로그먼트
 * {@link RecommendFragment#newInstance} 를 호출하여 인스턴스화
 */
public class RecommendFragment extends SectionFragmentBase
        implements MyFacilityAdapter.OnItemClickListener,
                    SwipeRefreshLayout.OnRefreshListener {

    private static final String LOG_TAG = "Recommend";

    private SwipeRefreshLayout mSwipeContainer;

    private RecyclerView mRecyclerViewReservoir;
    private MyFacilityAdapter mReservoirAdapter;
    private ArrayList<Facility> mReservoirList = new ArrayList<>();    // 저수지 목록

    private RecyclerView mRecyclerViewDam;
    private MyFacilityAdapter mDamAdapter;
    private ArrayList<Facility> mDamList = new ArrayList<>();    // 댐 목록

    private int mItemSpacingInPixel = 0;

    public RecommendFragment() {
        // Required empty public constructor
    }

    /**
     * create a new instance of
     * this fragment
     * @return A new instance of fragment RecommendFragment.
     */
    public static RecommendFragment newInstance() {
        RecommendFragment fragment = new RecommendFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);

        if (mContext == null) {
            mContext = getContext();
        }

        mSwipeContainer = view.findViewById(R.id.swipe_container);
        mSwipeContainer.setOnRefreshListener(this);

        // 내시설 아이템간 간격
        mItemSpacingInPixel = getResources().getDimensionPixelSize(R.dimen.marginNormal);

        initializeMyReservoirList(view);
        initializeMyDamList(view);

        return view;
    }

    /**
     * 내 저수지 목록 초기화
     * @param view
     */
    private void initializeMyReservoirList(View view) {
        mRecyclerViewReservoir = view.findViewById(R.id.recycler_view);
        mRecyclerViewReservoir.setHasFixedSize(true);
        mRecyclerViewReservoir.setLayoutManager(new LinearLayoutManager(mContext));

        mReservoirAdapter = new MyFacilityAdapter(mContext, mReservoirList);
        mReservoirAdapter.setOnItemClickListener(this);
        mRecyclerViewReservoir.setAdapter(mReservoirAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerViewReservoir.setLayoutManager(layoutManager);

        mRecyclerViewReservoir.addItemDecoration(new SpacesItemDecoration(mItemSpacingInPixel));
    }

    /**
     * 내 댐 목록 초기화
     * @param view
     */
    private void initializeMyDamList(View view) {
        mRecyclerViewDam = view.findViewById(R.id.recycler_view_dam);
        mRecyclerViewDam.setHasFixedSize(true);
        mRecyclerViewDam.setLayoutManager(new LinearLayoutManager(mContext));

        mDamAdapter = new MyFacilityAdapter(mContext, mDamList);
        mDamAdapter.setOnItemClickListener(this);
        mRecyclerViewDam.setAdapter(mDamAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerViewDam.setLayoutManager(layoutManager);

        mRecyclerViewDam.addItemDecoration(new SpacesItemDecoration(mItemSpacingInPixel));
    }

    @Override
    public void onResume() {
        super.onResume();

        onRefresh();
    }

    @Override
    public void onItemClick(ModelBase item) {
        Facility facility = (Facility)item;

        focusMapPage().focusFacility(facility);
    }

    @Override
    public void onRefresh() {
        loadMyReservoir();
        loadMyDam();
    }

    /**
     * 나의시설 목록 - 저수지
     */
    private void loadMyReservoir() {
        RetrofitClient.getMyFacility("hansolo", "reservoir", new Callback<APIResponse<Facility>>() {
            @Override
            public void onResponse(Call<APIResponse<Facility>> call, Response<APIResponse<Facility>> response) {
                if (response.isSuccessful()) {
                    mReservoirList.clear();

                    if (response.body().getData() == null) {
                        reportNoData();
                        return;
                    }

                    mReservoirList.addAll(response.body().getData());
                    mReservoirAdapter.notifyDataSetChanged();
                }

                mSwipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<APIResponse<Facility>> call, Throwable t) {
                mSwipeContainer.setRefreshing(false);
                Log.d(LOG_TAG,"onFailure : " + t.getMessage());
            }
        });
    }

    /**
     * 나의시설 목록 - 댐
     */
    private void loadMyDam() {
        RetrofitClient.getMyFacility("hansolo", "dam", new Callback<APIResponse<Facility>>() {
            @Override
            public void onResponse(Call<APIResponse<Facility>> call, Response<APIResponse<Facility>> response) {
                if (response.isSuccessful()) {
                    mDamList.clear();

                    if (response.body().getData() == null) {
                        reportNoData();
                        return;
                    }

                    mDamList.addAll(response.body().getData());
                    mDamAdapter.notifyDataSetChanged();
                }

                mSwipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<APIResponse<Facility>> call, Throwable t) {
                mSwipeContainer.setRefreshing(false);
                Log.d(LOG_TAG,"onFailure : " + t.getMessage());
            }
        });
    }

}