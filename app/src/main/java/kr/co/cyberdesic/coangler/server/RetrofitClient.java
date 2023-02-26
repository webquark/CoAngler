package kr.co.cyberdesic.coangler.server;


import static kr.co.cyberdesic.coangler.application.CoAnglerApplication.CFG_REAL_SERVER;

import kr.co.cyberdesic.coangler.model.APIResponse;
import kr.co.cyberdesic.coangler.model.Facility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public static RetrofitService getRetrofitService(){
        return RetrofitClient.getClient(CFG_REAL_SERVER).create(RetrofitService.class);
    }

//    public static void checkVersion(String version, Callback<APIResponse<VersionModel>> callback){
//        getRetrofitService().getVersionModel(version).enqueue(callback);
//    }

    public static void getMyFacility(String userId, String type, Callback<APIResponse<Facility>> callback){
        getRetrofitService().getMyFacility(userId, type).enqueue(callback);
    }

    public static void getWaterLevelList(Callback<APIResponse<Facility>> callback){
        getRetrofitService().getWaterLevelList().enqueue(callback);
    }

    public static void getReservoirWaterLevel(String facCode, String sdate, String edate,
                                              Callback<APIResponse<Facility>> callback){
        getRetrofitService().getReservoirWaterLevel(facCode, sdate, edate).enqueue(callback);
    }

    public static void getDamWaterLevel(String facCode, String sdate, String edate,
                                        Callback<APIResponse<Facility>> callback){

        Call<APIResponse<Facility>> response = getRetrofitService().getDamWaterLevel(facCode, sdate, edate);
        response.enqueue(callback);
    }
}

