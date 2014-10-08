package jupiter.maps;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;

//maps
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
//location
import android.location.Criteria;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;



public class MainActivity extends Activity {

	//static final LatLng CAR = new LatLng(53.558, 9.927);
	//static final LatLng KIEL = new LatLng(53.570, 9.993);
	//static final LatLng JUP = new LatLng(53.760, 9.983);
	
	private GoogleMap map;
	// 定义LocationManager对象
	LocationManager locManager;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//get location 
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
			
			
			
		Location location = locManager.getLastKnownLocation(
					providerNames.get(0));
		// 使用location根据EditText的显示
		//updateView(location);

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
		        .getMap();
		Marker car = map.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude()))
		        .title("You"));
		final Marker f_car = car;
		
		
		// 设置每3秒获取一次GPS的定位信息
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER
				,100, 1, new LocationListener()  //①
		{
			@Override
			public void onLocationChanged(Location location)
			{            
				// 当GPS定位信息发生改变时，更新位置
				//updateView(location);
				Context context = getApplicationContext();
				CharSequence text = "Position changed!";
				int duration = Toast.LENGTH_SHORT;
				Toast.makeText(context, text, duration).show();
				
				f_car.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
			}

			@Override
			public void onProviderDisabled(String provider)
			{
				//updateView(null);
			}

			@Override
			public void onProviderEnabled(String provider)
			{
				// 当GPS LocationProvider可用时，更新位置
				/*
				updateView(locManager
						.getLastKnownLocation(provider));
						*/
				
			}

			@Override
			public void onStatusChanged(String provider, int status,
				Bundle extras)
			{
			}
		});
				
				
				//get location end
		
		/*
		Marker kiel = map.addMarker(new MarkerOptions()
		        .position(KIEL)
		        .title("Kiel")
		        .snippet("Kiel here")
		        .icon(BitmapDescriptorFactory
		            .fromResource(R.drawable.ic_launcher)));
		
		Marker jup = map.addMarker(new MarkerOptions()
        .position(JUP)
        .title("jup")
        .snippet("jup here")
        .icon(BitmapDescriptorFactory
            .fromResource(R.drawable.ic_launcher)));
		*/
		// Move the camera instantly to hamburg with a zoom of 15.
		
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));

		// Zoom in, animating the camera.
		map.animateCamera(CameraUpdateFactory.zoomTo(21), 2000, null);
		
	}

}
