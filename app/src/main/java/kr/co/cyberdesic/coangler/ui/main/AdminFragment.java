package kr.co.cyberdesic.coangler.ui.main;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import kr.co.cyberdesic.coangler.R;
import kr.co.cyberdesic.coangler.adapter.FacilityAdapter;
import kr.co.cyberdesic.coangler.model.APIResponse;
import kr.co.cyberdesic.coangler.model.Facility;
import kr.co.cyberdesic.coangler.model.ModelBase;
import kr.co.cyberdesic.coangler.server.RetrofitClient;
import kr.co.cyberdesic.coangler.ui.fragment.FragmentBase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 관리자 탭 프래그먼트
 * {@link AdminFragment#newInstance} 를 호출하여 인스턴스화
 */
public class AdminFragment extends FragmentBase
        implements  View.OnClickListener,
                    FacilityAdapter.OnItemClickListener,
                    SwipeRefreshLayout.OnRefreshListener {

    private static final String LOG_TAG = "Admin";

    private FloatingActionButton mFab;
    private ImageView mIvTop;

    private FacilityAdapter mFacilityAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeContainer;

    private ArrayList<Facility> mFacilities = new ArrayList<>();    // Facility 목록

    private boolean mGetWaterLevelStarted = false;
    private int mCurrentIndex = 0;
    private Facility mCurrentFacility;

    public AdminFragment() {
        // Required empty public constructor
    }

    public static AdminFragment newInstance() {
        AdminFragment fragment = new AdminFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_admin, container, false);

        mContext = getContext();

        setProgressBar(R.id.progressBar);

        mRecyclerView = mView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mSwipeContainer = mView.findViewById(R.id.swipe_container);
        mSwipeContainer.setOnRefreshListener(this);

        mFacilityAdapter = new FacilityAdapter(mContext, mFacilities);
        mFacilityAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mFacilityAdapter);

        mFab = mView.findViewById(R.id.fab);
        mFab.setOnClickListener(this);

        mIvTop = mView.findViewById(R.id.iv_top);
        mIvTop.setOnClickListener(this);

        loadWaterLevelList();

        return mView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.fab) {
            if (!mGetWaterLevelStarted) {
                startGetWaterLevel();

            } else {
                pauseGetWaterLevel();
            }

        } else if (id == R.id.iv_top) {
            mRecyclerView.scrollToPosition(0);
        }
    }

    @Override
    public void onItemClick(ModelBase item) {
        Facility facility = (Facility) item;

        showToast(facility.name + "이 선택되었습니다.");
    }

    @Override
    public void onRefresh() {
        mGetWaterLevelStarted = false;
        mCurrentIndex = 0;
        loadWaterLevelList();
    }

    /**
     * 시설의 수위 데이터 읽어오기 
     * @param position 리스트에서 대상시설의 위치
     */
    private void getWaterLevel(final int position) {
        if (!mGetWaterLevelStarted) {
            return;
        }

        mCurrentIndex = position;
        setProgress(mCurrentIndex);

        mCurrentFacility = mFacilities.get(position);
        mRecyclerView.scrollToPosition(position);

        Log.d(LOG_TAG, mCurrentFacility.fac_code + ": " + mCurrentFacility.name);

        if (mCurrentFacility.fac_type.equals("reservoir")) {
            RetrofitClient.getReservoirWaterLevel(mCurrentFacility.fac_code,
                                                mCurrentFacility.sdate, mCurrentFacility.edate,
                                                mWaterLevelHandler);

        } else if (mCurrentFacility.fac_type.equals("dam")) {
            RetrofitClient.getDamWaterLevel(mCurrentFacility.fac_code,
                                                mCurrentFacility.sdate, mCurrentFacility.edate,
                                                mWaterLevelHandler);
        }
    }

    Callback<APIResponse<Facility>> mWaterLevelHandler = new Callback<APIResponse<Facility>>() {
        @Override
        public void onResponse(Call<APIResponse<Facility>> call, Response<APIResponse<Facility>> response) {
            if (response.isSuccessful()) {
                if (response.body().getData() == null) {
                    Log.d(LOG_TAG, mCurrentFacility.fac_code + ": no data");

                    if ((mCurrentIndex + 1) < mFacilities.size()) {
                        getWaterLevel(mCurrentIndex + 1);

                    } else {
                        pauseGetWaterLevel();
                    }

                    return;
                }

                Facility res = (Facility) response.body().getData().get(0);

                mCurrentFacility.last_date = res.last_date;
                mCurrentFacility.last_level = res.last_level;
                mCurrentFacility.last_rate = res.last_rate;
                Log.d(LOG_TAG, res.fac_code + ": " + res.name + ": " + res.last_level);

                mFacilities.set(mCurrentIndex, mCurrentFacility);
                mFacilityAdapter.notifyItemChanged(mCurrentIndex);
            }

            if ((mCurrentIndex + 1) < mFacilities.size()) {
                getWaterLevel(mCurrentIndex + 1);

            } else {
                pauseGetWaterLevel();
            }
        }

        @Override
        public void onFailure(Call<APIResponse<Facility>> call, Throwable t) {
            Log.d(LOG_TAG, "onFailure : " + t.getMessage());

            if ((mCurrentIndex + 1) < mFacilities.size()) {
                getWaterLevel(mCurrentIndex + 1);

            } else {
                pauseGetWaterLevel();
            }
        }
    };

    /**
     * 수위데이터를 읽어오기 위한 시설 목록 가져오기
     */
    private void loadWaterLevelList() {
        RetrofitClient.getWaterLevelList(new Callback<APIResponse<Facility>>() {
            @Override
            public void onResponse(Call<APIResponse<Facility>> call, Response<APIResponse<Facility>> response) {
                if (response.isSuccessful()) {
                    mCurrentIndex = 0;
                    mFacilities.clear();

                    if (response.body().getData() == null) {
                        reportNoData();
                        return;
                    }

                    mFacilities.addAll( response.body().getData() );
                    mFacilityAdapter.notifyDataSetChanged();

                    getProgressBar().setMax(mFacilities.size());
                }

                mSwipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<APIResponse<Facility>> call, Throwable t) {
                mSwipeContainer.setRefreshing(false);
                Log.d(LOG_TAG, "onFailure : " + t.getMessage());
            }
        });
    }

    /**
     * 수위 데이터 가져오기 시작
     */
    private void startGetWaterLevel() {
        mGetWaterLevelStarted = true;
        mFab.setImageDrawable(mContext.getDrawable(R.drawable.ic_pause));

        showProgress();
        getWaterLevel(mCurrentIndex);
    }

    /**
     * 수위 데이터 가져오기 중지
     */
    private void pauseGetWaterLevel() {
        mGetWaterLevelStarted = false;
        mFab.setImageDrawable(mContext.getDrawable(R.drawable.ic_download));

        hideProgress();
    }
}