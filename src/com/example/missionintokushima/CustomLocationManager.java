package com.example.missionintokushima;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;

public class CustomLocationManager implements LocationListener {
    private LocationManager mLocationManager;
    private LocationCallback mLocationCallback;
    private Handler mHandler = new Handler();
    private static final int NETWORK_TIMEOUT = 5000;

    public CustomLocationManager(Context context) {
        mLocationManager = (LocationManager) context.getSystemService(Activity.LOCATION_SERVICE);
    }

    public void getNowLocationData(int delayMillis, LocationCallback locationCallback) {
        this.mLocationCallback = locationCallback;
        mHandler.postDelayed(gpsTimeOutRun, delayMillis);
        startLocation(LocationManager.GPS_PROVIDER);
    }

    // private static final int mAccuracy = Criteria.ACCURACY_FINE;// 菴咲ｽｮ諠��蜿門ｾ励�邊ｾ蠎ｦ
    // private static final int mBearingAccuracy = Criteria.ACCURACY_LOW;// 譁ｹ菴咲ｲｾ蠎ｦ
    // private static final int mHorizontalAccuracy = Criteria.ACCURACY_HIGH;// 邱ｯ蠎ｦ邨悟ｺｦ縺ｮ蜿門ｾ�
    // private static final int mVerticalAccuracy = Criteria.NO_REQUIREMENT;// 鬮伜ｺｦ縺ｮ蜿門ｾ�
    // private static final int mPowerLevel = Criteria.NO_REQUIREMENT;// 髮ｻ蜉帶ｶ郁ｲｻ繝ｬ繝吶Ν縺ｮ險ｭ螳�
    // private static final int mSpeedAccuracy = Criteria.NO_REQUIREMENT;// 騾溷ｺｦ縺ｮ邊ｾ蠎ｦ
    // private static final boolean isAltitude = false;// 鬮伜ｺｦ縺ｮ蜿門ｾ励�譛臥┌
    // private static final boolean isBearing = false;// 譁ｹ菴阪ｒ蜿門ｾ励�譛臥┌
    // private static final boolean isCostAllowed = false;// 菴咲ｽｮ諠��蜿門ｾ励↓髢｢縺励※驥鷹姦逧�↑繧ｳ繧ｹ繝医�險ｱ蜿ｯ
    // private static final boolean isSpeed = false;// 騾溷ｺｦ繧貞�縺吶°縺ｩ縺�°
    //
    // private String getBestProvider(boolean enabledOnly) {
    //
    // Criteria criteria = new Criteria();
    //
    // criteria.setAccuracy(mAccuracy);
    // criteria.setBearingAccuracy(mBearingAccuracy);
    // criteria.setAltitudeRequired(isAltitude);
    // criteria.setBearingRequired(isBearing);
    // criteria.setCostAllowed(isCostAllowed);
    // criteria.setHorizontalAccuracy(mHorizontalAccuracy);
    // criteria.setPowerRequirement(mPowerLevel);
    // criteria.setSpeedAccuracy(mSpeedAccuracy);
    // criteria.setSpeedRequired(isSpeed);
    // criteria.setVerticalAccuracy(mVerticalAccuracy);
    //
    // return mLocationManager.getBestProvider(criteria, enabledOnly);
    //
    // }

    private Runnable gpsTimeOutRun = new Runnable() {

        @Override
        public void run() {
            removeUpdate();
            mHandler.postDelayed(networkTimeOutRun, NETWORK_TIMEOUT);
            startLocation(LocationManager.NETWORK_PROVIDER);
        }
    };

    private Runnable networkTimeOutRun = new Runnable() {

        @Override
        public void run() {
            removeUpdate();
            if (mLocationCallback != null) {
                mLocationCallback.onTimeout();
            }
        }
    };

    public boolean checkGpsMode() {
        boolean gps = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return gps || network;
    }

    public void startLocation(String provider) {
        mLocationManager.requestLocationUpdates(provider, 60000, 0, this);
    }

    public void removeUpdate() {
        mLocationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        mHandler.removeCallbacks(gpsTimeOutRun);
        mHandler.removeCallbacks(networkTimeOutRun);
        if (this.mLocationCallback != null) {
            this.mLocationCallback.onComplete(location);
        }
        removeUpdate();
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public static interface LocationCallback {

        public void onComplete(Location location);

        public void onTimeout();
    }
}
