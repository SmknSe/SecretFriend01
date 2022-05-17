package com.example.secretfriend01;

import android.content.Context;

import androidx.room.Room;

import room.AppDB;


public class DBClient {

    private Context mCtx;
    private static DBClient mInstance;

    private AppDB appDatabase;

    private DBClient(Context mCtx){
        this.mCtx = mCtx;
        appDatabase = Room.databaseBuilder(mCtx,AppDB.class,"MyGames").build();
    }

    public static DBClient getInstance(Context mCtx){
        if (mInstance == null){
            mInstance = new DBClient(mCtx);
        }
        return mInstance;
    }

    public AppDB getAppDatabase() {
        return appDatabase;
    }
}
