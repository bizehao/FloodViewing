package com.bzh.floodview.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.bzh.floodview.data.model.StationInfo;

import java.util.List;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/11/29 9:47
 */
@Dao
public interface StationInfoDao {

    @Query("select * from stationinfo")
    List<StationInfo> getStations();

    @Insert
    void addStations(List<StationInfo> stationInfos);
}
