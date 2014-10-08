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
	// ����LocationManager����
	LocationManager locManager;
	// �����������е�EditText���
	EditText show;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// ��ȡ��������ϵ�EditText���
		show = (EditText) findViewById(R.id.show);
		// ����LocationManager����
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
		
		// ��GPS��ȡ���������Ķ�λ��Ϣ
		/*
		Location location = locManager.getLastKnownLocation(
			LocationManager.GPS_PROVIDER);
		//��һ��
		LocationProvider locationProvider = locManager.getProvider(
				providerNames.get(0));	
		*/
		
		
		Location location = locManager.getLastKnownLocation(
				providerNames.get(0));
		// ʹ��location����EditText����ʾ
		updateView(location);
			// ����ÿ3���ȡһ��GPS�Ķ�λ��Ϣ
			locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER
				,100, 1, new LocationListener()  //��
			{
				@Override
				public void onLocationChanged(Location location)
				{            
					// ��GPS��λ��Ϣ�����ı�ʱ������λ��
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
					// ��GPS LocationProvider����ʱ������λ��
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

	// ����EditText����ʾ������
	public void updateView(Location newLocation)
	{
		if (newLocation != null)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("current location��\n");
			sb.append("Longitude��");
			sb.append(newLocation.getLongitude());
			sb.append("\nLatitude��");
			sb.append(newLocation.getLatitude());
			sb.append("\nAltitude��");
			sb.append(newLocation.getAltitude());
			sb.append("\nSpeed��");
			sb.append(newLocation.getSpeed());
			sb.append("\nDirection��");
			sb.append(newLocation.getBearing());
			show.setText(sb.toString());
		}
		else
		{
			// ��������Location����Ϊ�������EditText
			show.setText("");
		}
	}
}