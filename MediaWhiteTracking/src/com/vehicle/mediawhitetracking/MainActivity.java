package com.vehicle.mediawhitetracking;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	GoogleMap gMap;
	HalfSeekabr halfSeekBar;
	LatLng latLang;
    TextView textView,currentLocationAdress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		halfSeekBar = (HalfSeekabr) findViewById(R.id.volume_bar);
		textView = (TextView)findViewById(R.id.speed_textview);
		currentLocationAdress = (TextView)findViewById(R.id.text_currentlocation);
		SupportMapFragment supportedmapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		gMap = supportedmapFragment.getMap();
		gMap.setMyLocationEnabled(true);
		halfSeekBar.setMax(120);
		gMap.animateCamera(CameraUpdateFactory.zoomTo(12));
		gpsListener locationList = new gpsListener();
		LocationManager locationmanger = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationmanger.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				2000, 50, locationList);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	//Gps Location Listener
	
	private class gpsListener implements android.location.LocationListener {

		@Override
		public void onLocationChanged(Location location) {

			
			
			latLang = new LatLng(location.getLatitude(),
					location.getLongitude());
			if (location != null) {
				//Setting speed to Textview
				textView.setText(""+location.getSpeed() +" km/h");
				halfSeekBar.setProgress((int) location.getSpeed());
				//Animate to current Location
				gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLang,
						14));
				Geocoder geocoder;  
		        List<Address> addresses;
		        geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
		         try {
					addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
					currentLocationAdress.setText(addresses.get(0).getAdminArea() +","+addresses.get(0).getAddressLine(0));
				} catch (IOException e) {
					
					e.printStackTrace();
				}
		     
			}
			
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
	}

}
