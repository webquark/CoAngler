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
     * @param userId
     * @param facilityType
     * @return
     */
    @GET("/api/facility/list/recommendList.php")
    Call<APIResponse<Facility>> getMyFacility(@Query("uid") String userId, @Query("type") String facilityType);

    /**
     * WaterLevelList
     * @return
     */
    @GET("/api/facility/list/waterLevelList.php")
    Call<APIResponse<Facility>> getWaterLevelList();

    /**
     * 저수지 WaterLevel
     * @return
     */
    @GET("/api/reservoir/waterLevel/get.php")
    Call<APIResponse<Facility>> getReservoirWaterLevel(@Query("fcode") String facCode,
                                                       @Query("sdate") String sdate,
                                                       @Query("edate") String edate);

    /**
     * 댐 WaterLevel
     * @return
     */
    @GET("/api/dam/waterLevel/get.php")
    Call<APIResponse<Facility>> getDamWaterLevel(@Query("fcode") String facCode,
                                                 @Query("sdate") String sdate,
                                                 @Query("edate") String edate);
}
