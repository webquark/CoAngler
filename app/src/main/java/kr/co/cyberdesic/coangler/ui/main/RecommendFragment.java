package kr.co.cyberdesic.coangler.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import kr.co.cyberdesic.coangler.ui.fragment.FragmentBase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 추천 탭 프로그먼트
 * {@link RecommendFragment#newInstance} 를 호출하여 인스턴스화
 */
public class RecommendFragment extends FragmentBase
        implements MyFacilityAdapter.OnItemClickListener,
                    SwipeRefreshLayout.OnRefreshListener {

    private static final String LOG_TAG = "Recommend";

    private MyFacilityAdapter mMyFacilityAdapter;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeContainer;

    private ArrayList<Facility> mMyFacilities = new ArrayList<>();    // My Facility 목록

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

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mSwipeContainer = view.findViewById(R.id.swipe_container);
        mSwipeContainer.setOnRefreshListener(this);

        mMyFacilityAdapter = new MyFacilityAdapter(mContext, mMyFacilities);
        mMyFacilityAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mMyFacilityAdapter);

        mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // 내시설 아이템간 간격
        int spacingInPixel = getResources().getDimensionPixelSize(R.dimen.marginNormal);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixel));
        
        loadMyFacility();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onItemClick(ModelBase item) {
        Facility facility = (Facility)item;

        showToast(facility.name + "이 선택되었습니다.");
    }

    @Override
    public void onRefresh() {
        loadMyFacility();
    }

    // 나의시설 목록
    private void loadMyFacility() {

        RetrofitClient.getMyFacility("hansolo", new Callback<APIResponse<Facility>>() {
            @Override
            public void onResponse(Call<APIResponse<Facility>> call, Response<APIResponse<Facility>> response) {
                if (response.isSuccessful()){
                    mMyFacilities.clear();

                    if (response.body().getData() == null) {
                        showToast("데이터가 없습니다.");
                        Log.i(LOG_TAG,"데이터가 없습니다.");
                        return;
                    }

                    mMyFacilities.addAll(response.body().getData());
                    mMyFacilityAdapter.notifyDataSetChanged();
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