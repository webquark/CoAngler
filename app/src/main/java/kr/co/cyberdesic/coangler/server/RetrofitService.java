package kr.co.cyberdesic.coangler.server;

import kr.co.cyberdesic.coangler.model.APIResponse;
import kr.co.cyberdesic.coangler.model.Facility;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {

    /**
     * 버전체크
     * @param version
     * @return
     */
//    @GET("")
//    Call<APIResponse<VersionModel>> getVersionModel(@Query("version") String version);

    /**
     * MyFacility
     * @param userName
     * @return
     */
    @GET("/api/facility/list/mylist.php")
    Call<APIResponse<Facility>> getMyFacility(@Query("keyword") String userName);

    /**
     * WaterLevelList
     * @return
     */
    @GET("/api/reservoir/waterLevel/list.php")
    Call<APIResponse<Facility>> getWaterLevelList();

    /**
     * WaterLevel
     * @return
     */
    @GET("/api/reservoir/waterLevel/get.php")
    Call<APIResponse<Facility>> getWaterLevel(@Query("fcode") String facCode,
                                              @Query("sdate") String sdate,
                                              @Query("edate") String edate);
}
