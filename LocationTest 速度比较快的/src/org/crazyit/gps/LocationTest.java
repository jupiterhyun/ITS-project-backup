package org.crazyit.gps;


import java.util.List;

import android.location.*;
import android.location.Criteria;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.EditText;

/**
 * Description:
 * <br/>site: <a href="http://www.crazyit.org">crazyit.org</a>
 * <br/>Copyright (C), 2001-2014, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class LocationTest extends Activity
{
	// 定义LocationManager对象
	LocationManager locManager;
	// 定义程序界面中的EditText组件
	EditText show;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 获取程序界面上的EditText组件
		show = (EditText) findViewById(R.id.show);
		// 创建LocationManager对象
		/* modified by xin 10/7/2014*/
		locManager = (LocationManager) getSystemService
			(Context.LOCATION_SERVICE);
		
		//create a criteria
		Criteria cri = new Criteria();
		cri.setCostAllowed(false);//free charge
		cri.setAccuracy(1);//ACCURACY_FINE 
		
		cri.setAltitudeRequired(true);//altitude required
		cri.setBearingRequired(true);//direction
		cri.setSpeedAccuracy(1);
		cri.setSpeedRequired(true);//speed
		
	
		List<String> providerNames = locManager.getProviders(cri, false);
		
		// 从GPS获取最近的最近的定位信息
		/*
		Location location = locManager.getLastKnownLocation(
			LocationManager.GPS_PROVIDER);
		//第一个
		LocationProvider locationProvider = locManager.getProvider(
				providerNames.get(0));	
		*/
		
		
		Location location = locManager.getLastKnownLocation(
				providerNames.get(0));
		// 使用location根据EditText的显示
		updateView(location);
			// 设置每3秒获取一次GPS的定位信息
			locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER
				,100, 1, new LocationListener()  //①
			{
				@Override
				public void onLocationChanged(Location location)
				{            
					// 当GPS定位信息发生改变时，更新位置
					updateView(location);
				}

				@Override
				public void onProviderDisabled(String provider)
				{
					updateView(null);
				}

				@Override
				public void onProviderEnabled(String provider)
				{
					// 当GPS LocationProvider可用时，更新位置
					updateView(locManager
						.getLastKnownLocation(provider));
				}

				@Override
				public void onStatusChanged(String provider, int status,
					Bundle extras)
				{
				}
			});
		
	}

	// 更新EditText中显示的内容
	public void updateView(Location newLocation)
	{
		if (newLocation != null)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("current location：\n");
			sb.append("Longitude：");
			sb.append(newLocation.getLongitude());
			sb.append("\nLatitude：");
			sb.append(newLocation.getLatitude());
			sb.append("\nAltitude：");
			sb.append(newLocation.getAltitude());
			sb.append("\nSpeed：");
			sb.append(newLocation.getSpeed());
			sb.append("\nDirection：");
			sb.append(newLocation.getBearing());
			show.setText(sb.toString());
		}
		else
		{
			// 如果传入的Location对象为空则清空EditText
			show.setText("");
		}
	}
}