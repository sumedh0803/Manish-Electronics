package com.example.sumedh.manishelectronics;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service implements LocationListener {
	
	LocationManager lm ;
	Location l;
	Context c;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public MyService(Context c) {
		// TODO Auto-generated constructor stub
		this.c = c;
	}
	
	
	@SuppressLint("MissingPermission")
	public Location getLocation(){
		
		lm = (LocationManager) c.getSystemService(LOCATION_SERVICE);
		
		if(lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
		{
			lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000*60*1, 0 , this);
			// parameters : 1> PROVIDER which could be NETWORK or GPS
			// 2> time after which update should happen
			// 3> distance after which update should happen
			// remember  specify either 2 or 3 
			// 4> context
			
			l = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			//Toast.makeText(c, "got"+l.getLatitude(), Toast.LENGTH_SHORT).show();
		}
		else
		{
			Toast.makeText(c, "Network not found", Toast.LENGTH_SHORT).show();
		}
		return l;
	}

	
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
	}


	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
}
