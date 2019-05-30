package com.bzh.floodview.api;

import com.bzh.floodview.model.ApiCommon;
import com.bzh.floodview.model.ApiCounty;
import com.bzh.floodview.model.ApiFriends;
import com.bzh.floodview.model.ApiRainInfo;
import com.bzh.floodview.model.ApiRainStInfo;
import com.bzh.floodview.model.ApiStations;
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
    @POST("usercontroller/login")
    Observable<BaseApi<Integer>> login(@Field("username") String username,
                                            @Field("password") String password);

    //注销
    @GET("server/user/login-out")
    Observable<ApiCommon> loginOut(@Query("username") String username);

    //获取用户信息,搜索用户
    @GET("server/user/get-user-info")
    Observable<ApiUserInfo> getUserInfo(@Query("username") String username);

    //注册用户
    @FormUrlEncoded
    @POST("server/user/register")
    Observable<ApiRegister> register(@FieldMap Map<String, String> requestRegister);

    //获取用户信息,搜索用户(模糊搜索,多个用户)
    @GET("server/user/get-user-infos")
    Observable<ApiUserInfos> getUserInfos(@Query("username") String username);

    //获取推荐用户
    @GET("server/user/recommendFriend")
    Observable<ApiUserInfos> getRecUser(@Query("username") String username);

    //添加好友
    @FormUrlEncoded
    @POST("server/user/addFriend")
    Observable<ApiaddFriends> addFriends(@Field("username") String username,
                                         @Field("friendName") String friendName,
                                         @Field("remarkName") String remarkName);

    //查询好友信息
    @GET("server/user/getFriends")
    Observable<ApiFriends> getFriends(@Query("username") String username);


    //上传图片
    @Multipart
    @POST("server/user/uploadPng")
    Observable<ResponseBody> uploadPng(@Part("username") RequestBody username, @Part MultipartBody.Part headPortrait);

    //删除好友
    @GET("server/user/deleteFriend")
    Observable<ApiCommon> delFriend(@Query("username") String username, @Query("friendName") String friendName);


    //降雨信息
    @GET("server/rainInfo/rainfalls_all")
    Observable<ApiRainInfo> getRainfallInfo(@Query("stm") String stm, @Query("etm") String etm,@Query("addvcd") String adcd);

    //雨强信息
    @GET("server/rainInfo/rainIntensity_all")
    Observable<ApiRainStInfo> getIntensityOfRainInfo(@Query("stm") String stm,@Query("addvcd") String adcd);

    //河道信息
    @GET("server/waterInfo/river_all")
    Observable<ApiRiverInfo> getRiverInfo(@Query("stm") String stm, @Query("etm") String etm,@Query("addvcd") String adcd);

    //水库信息
    @GET("server/waterInfo/reservoir_all")
    Observable<ApiRsvrInfo> getRsvrInfo(@Query("stm") String stm, @Query("etm") String etm,@Query("addvcd") String adcd);

    //汛情摘要降雨量
    @GET("server/floodAbstract/floodAbstract_rain_all")
    Observable<ApiRainInfo> getFloodRainInfo(@Query("stm") String stm, @Query("etm") String etm, @Query("nums") String nums,@Query("addvcd")String adcd);

    //县域信息
    @GET("server/OtherController/allAddvcds")
    Observable<BaseApi<List<ApiCounty>>> getCounty();

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //地图相关

    //获取坐标信息
    @GET("server/mapController/getStcd")
    Observable<ApiStcd> getAoordinate();

    //获取降雨量的站点信息
    @GET("server/mapController/rainfallInformation")
    Observable<BaseApi<List<ApiRainMapData>>> getStateRain(@Query("stcd") String stcd, @Query("tmstart") String stm, @Query("tmend") String etm);

    //获取河道水情的站点信息
    @GET("server/mapController/riverStationInformation")
    Observable<BaseApi<ApiRiverMapData>> getStateRiver(@Query("stcd") String stcd, @Query("tmstart") String stm, @Query("tmend") String etm);

    //获取水库水位的站点信息
    @GET("server/mapController/reservoirWaterLevelStationInformation")
    Observable<BaseApi<ApiRsvrMapData>> getStateRsvr(@Query("stcd") String stcd, @Query("tmstart") String stm, @Query("tmend") String etm);

    //降雨量表格
    @GET("server/mapController/rainfallTable")
    Observable<BaseApi<List<ApiRainTable>>> getRainTable(@Query("tmstart") String stm, @Query("tmend") String etm);

    //河道表格
    @GET("server/mapController/theRiverForms")
    Observable<BaseApi<List<ApiRiverTable>>> getRiverTable(@Query("tmstart") String stm, @Query("tmend") String etm);

    //水库表格
    @GET("server/mapController/reservoirForm")
    Observable<BaseApi<List<ApiRsvrTable>>> getRsvrTable(@Query("tmstart") String stm, @Query("tmend") String etm);

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    @GET("server/auth/say")
    Observable<BaseApi<Test>> getSay();

    //添加意见反馈
    @POST("server/addFeedBack")
    Observable<BaseApi<Boolean>> getaddFeedBack(@Query("contents") String contents, @Query("uName") String uname );

    @GET("server/")
    Observable<BaseApi<Administrativearea>> getAdministrativearea();

    ///////////////////////////2019-05-23新增模块///////////////////////

    //获取县域站号
    @GET("server/stationController/selectSSA")
    Observable<BaseApi<List<ApiStations>>> getAdcdAndStations(@Query("id") int id);

    //给当前用户添加县域站号
    @FormUrlEncoded
    @POST("server/stationController/addUserStcd")
    Observable<BaseApi<String>> addUserStcd(@Field("id") int id,@Field("list")List<String> list);

}
