package com.bzh.floodview.api;

import com.bzh.floodview.model.ApiCommon;
import com.bzh.floodview.model.ApiCounty;
import com.bzh.floodview.model.ApiFriends;
import com.bzh.floodview.model.ApiRainInfo;
import com.bzh.floodview.model.ApiRainStInfo;
import com.bzh.floodview.model.BaseApi;
import com.bzh.floodview.model.back.Feedback;
import com.bzh.floodview.model.login.ApiLoginData;
import com.bzh.floodview.model.mapData.ApiRainMapData;
import com.bzh.floodview.model.mapData.ApiRainTable;
import com.bzh.floodview.model.ApiRegister;
import com.bzh.floodview.model.ApiRiverInfo;
import com.bzh.floodview.model.mapData.ApiRiverMapData;
import com.bzh.floodview.model.mapData.ApiRiverTable;
import com.bzh.floodview.model.ApiRsvrInfo;
import com.bzh.floodview.model.mapData.ApiRsvrMapData;
import com.bzh.floodview.model.mapData.ApiRsvrTable;
import com.bzh.floodview.model.mapData.ApiStcd;
import com.bzh.floodview.model.ApiUserInfo;
import com.bzh.floodview.model.ApiUserInfos;
import com.bzh.floodview.model.ApiaddFriends;
import com.bzh.floodview.model.test.Test;
import com.bzh.floodview.module.home.homeIndex.content.Adapter.entit.Administrativearea;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RetrofitService {

    //登陆
    @FormUrlEncoded
    @POST("user/login")
    Observable<BaseApi<ApiLoginData>> login(@Field("username") String username,
                                            @Field("password") String password);

    //注销
    @GET("user/login-out")
    Observable<ApiCommon> loginOut(@Query("username") String username);

    //获取用户信息,搜索用户
    @GET("user/get-user-info")
    Observable<ApiUserInfo> getUserInfo(@Query("username") String username);

    //注册用户
    @FormUrlEncoded
    @POST("user/register")
    Observable<ApiRegister> register(@FieldMap Map<String, String> requestRegister);

    //获取用户信息,搜索用户(模糊搜索,多个用户)
    @GET("user/get-user-infos")
    Observable<ApiUserInfos> getUserInfos(@Query("username") String username);

    //获取推荐用户
    @GET("user/recommendFriend")
    Observable<ApiUserInfos> getRecUser(@Query("username") String username);

    //添加好友
    @FormUrlEncoded
    @POST("user/addFriend")
    Observable<ApiaddFriends> addFriends(@Field("username") String username,
                                         @Field("friendName") String friendName,
                                         @Field("remarkName") String remarkName);

    //查询好友信息
    @GET("user/getFriends")
    Observable<ApiFriends> getFriends(@Query("username") String username);


    //上传图片
    @Multipart
    @POST("user/uploadPng")
    Observable<ResponseBody> uploadPng(@Part("username") RequestBody username, @Part MultipartBody.Part headPortrait);

    //删除好友
    @GET("user/deleteFriend")
    Observable<ApiCommon> delFriend(@Query("username") String username, @Query("friendName") String friendName);


    //降雨信息
    @GET("rainInfo/rainfalls_all")
    Observable<ApiRainInfo> getRainfallInfo(@Query("stm") String stm, @Query("etm") String etm,@Query("addvcd") String adcd);

    //雨强信息
    @GET("rainInfo/rainIntensity_all")
    Observable<ApiRainStInfo> getIntensityOfRainInfo(@Query("stm") String stm, @Query("etm") String etm);

    //河道信息
    @GET("waterInfo/river_all")
    Observable<ApiRiverInfo> getRiverInfo(@Query("stm") String stm, @Query("etm") String etm);

    //水库信息
    @GET("waterInfo/reservoir_all")
    Observable<ApiRsvrInfo> getRsvrInfo(@Query("stm") String stm, @Query("etm") String etm);

    //汛情摘要降雨量
    @GET("floodAbstract/floodAbstract_rain_all")
    Observable<ApiRainInfo> getFloodRainInfo(@Query("stm") String stm, @Query("etm") String etm, @Query("nums") String nums,@Query("addvcd")String adcd);

    //县域信息
    @GET("OtherController/allAddvcds")
    Observable<BaseApi<List<ApiCounty>>> getCounty();

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //地图相关

    //获取坐标信息
    @GET("mapController/getStcd")
    Observable<ApiStcd> getAoordinate();

    //获取降雨量的站点信息
    @GET("mapController/rainfallInformation")
    Observable<BaseApi<List<ApiRainMapData>>> getStateRain(@Query("stcd") String stcd, @Query("tmstart") String stm, @Query("tmend") String etm);

    //获取河道水情的站点信息
    @GET("mapController/riverStationInformation")
    Observable<BaseApi<ApiRiverMapData>> getStateRiver(@Query("stcd") String stcd, @Query("tmstart") String stm, @Query("tmend") String etm);

    //获取水库水位的站点信息
    @GET("mapController/reservoirWaterLevelStationInformation")
    Observable<BaseApi<ApiRsvrMapData>> getStateRsvr(@Query("stcd") String stcd, @Query("tmstart") String stm, @Query("tmend") String etm);

    //降雨量表格
    @GET("mapController/rainfallTable")
    Observable<BaseApi<List<ApiRainTable>>> getRainTable(@Query("tmstart") String stm, @Query("tmend") String etm);

    //河道表格
    @GET("mapController/theRiverForms")
    Observable<BaseApi<List<ApiRiverTable>>> getRiverTable(@Query("tmstart") String stm, @Query("tmend") String etm);

    //水库表格
    @GET("mapController/reservoirForm")
    Observable<BaseApi<List<ApiRsvrTable>>> getRsvrTable(@Query("tmstart") String stm, @Query("tmend") String etm);

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    @GET("auth/say")
    Observable<BaseApi<Test>> getSay();

    //添加意见反馈
    @POST("addFeedBack")
    Observable<BaseApi<Boolean>> getaddFeedBack(@Query("contents") String contents, @Query("uName") String uname );

    @GET("")
    Observable<BaseApi<Administrativearea>> getAdministrativearea();

}
