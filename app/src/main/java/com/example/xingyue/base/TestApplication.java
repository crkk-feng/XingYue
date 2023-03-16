package com.example.xingyue.base;

import android.app.Application;
import android.content.Context;

public class TestApplication extends Application {
	private Context mContext;
	
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }
    
     public Context getContext() {
        return mContext;
    }
}
