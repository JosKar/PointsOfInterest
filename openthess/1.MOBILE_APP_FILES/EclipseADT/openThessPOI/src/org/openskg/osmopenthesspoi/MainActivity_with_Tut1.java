package org.openskg.osmopenthesspoi;

import java.util.ArrayList;
import java.util.List;

//import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IMyLocationOverlay;
import org.osmdroid.bonuspack.overlays.ExtendedOverlayItem;
import org.osmdroid.bonuspack.overlays.ItemizedOverlayWithBubble;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener;
import org.osmdroid.views.overlay.MyLocationOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
//import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
/*
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
*/
import android.view.Menu;
//import android.view.MotionEvent;
import android.widget.Toast;

public class MainActivity_with_Tut1 extends Activity {

	private MapView myOpenMapView;
	private MapController myMapController;
	private ScaleBarOverlay myScaleBarOverlay;
	private MyLocationNewOverlay myLocationOverlay;
	//private ResourceProxy myResourceProxy;
	
	LocationManager locationManager;
	
	//ArrayList<OverlayItem> overlayItemArray;
	//ArrayList<OverlayItem> anotherOverlayItemArray;
	ArrayList<ExtendedOverlayItem> anotherOverlayItemArray;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		myOpenMapView = (MapView)findViewById(R.id.openmapview); // προστέθηκε με @+id στο activity_main.xml layout
		myOpenMapView.setBuiltInZoomControls(true); // ενεργοποίηση του zoom ui
		myOpenMapView.setMultiTouchControls(true);  // ενεργοποίηση multi touch πχ zoom με 2 fingers
		myOpenMapView.setMinZoomLevel(4);
		//myOpenMapView.setMaxZoomLevel(16);
		//myOpenMapView.setTileSource(TileSourceFactory.MAPNIK);
			
		myMapController = (MapController) myOpenMapView.getController();
		myMapController.setZoom(14);  // θέτω το intro zoom σε 14
		
		//GeoPoint startPoint = ;
		myMapController.setCenter(new GeoPoint(40.63875,22.9515));

		//DefaultResourceProxyImpl defResourceProxyImpl = new DefaultResourceProxyImpl(this);
		
		// Create Overlay
		//overlayItemArray = new ArrayList<OverlayItem>();
		
		//--- Create Another Overlay for multi marker
        anotherOverlayItemArray = new ArrayList<ExtendedOverlayItem>();
               
        anotherOverlayItemArray.add(new ExtendedOverlayItem(
          "US", "US", new GeoPoint(38.883333, -77.016667),this));
        anotherOverlayItemArray.add(new ExtendedOverlayItem(
          "China", "China", new GeoPoint(39.916667, 116.383333),this));
        anotherOverlayItemArray.add(new ExtendedOverlayItem(
          "United Kingdom", "United Kingdom", new GeoPoint(51.5, -0.116667),this));
        anotherOverlayItemArray.add(new ExtendedOverlayItem(
          "Germany", "Germany", new GeoPoint(52.516667, 13.383333),this));
        anotherOverlayItemArray.add(new ExtendedOverlayItem(
          "Korea", "Korea", new GeoPoint(38.316667, 127.233333),this));
        anotherOverlayItemArray.add(new ExtendedOverlayItem(
          "India", "India", new GeoPoint(28.613333, 77.208333),this));
        anotherOverlayItemArray.add(new ExtendedOverlayItem(
          "Russia", "Russia", new GeoPoint(55.75, 37.616667),this));
        anotherOverlayItemArray.add(new ExtendedOverlayItem(
          "France", "France", new GeoPoint(48.856667, 2.350833),this));
        anotherOverlayItemArray.add(new ExtendedOverlayItem(
          "Canada", "Canada", new GeoPoint(45.4, -75.666667),this));
        anotherOverlayItemArray.add(new ExtendedOverlayItem(
          "Σπίτι θεσσαλονίκη", "Βλαχάβα 8", new GeoPoint(40.6385,22.9515),this));
		
        // extended marker from osmdroidbonus library
        Drawable marker = getResources().getDrawable(R.drawable.marker_poi);        
        for (ExtendedOverlayItem poi : anotherOverlayItemArray) {
			poi.setMarkerHotspot(OverlayItem.HotspotPlace.CENTER);
			poi.setMarker(marker);
			poi.setDescription(poi.getSnippet());
			poi.setSubDescription("this is a test for subdescription!!!");
			poi.setTitle(poi.getTitle());
		}
        
        
		//MyItemizedIconOverlay myItemizedIconOverlay = new MyItemizedIconOverlay(overlayItemArray, null, defResourceProxyImpl);
        // Initail code for tapping
		//ItemizedIconOverlay<OverlayItem> multiItemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(this, anotherOverlayItemArray, null);
        // after class MyItemizedOverlay bla bla
        //MyOwnItemizedOverlay multiItemizedIconOverlay = new MyOwnItemizedOverlay(this, anotherOverlayItemArray);
        // χρήση με osmdroid bonuspack, bubbles :)
        ItemizedOverlayWithBubble<ExtendedOverlayItem> multiItemizedIconOverlay = 
        		new ItemizedOverlayWithBubble<ExtendedOverlayItem>(this, anotherOverlayItemArray, myOpenMapView);
		
		//myOpenMapView.getOverlays().add(myItemizedIconOverlay);
		myOpenMapView.getOverlays().add(multiItemizedIconOverlay);
		
		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
				
		Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if(lastLocation != null) {
			//updateLoc(lastLocation);
		}
		
		// Προσθήκη ScaleBar
		//myResourceProxy = new ResourceProxyImpl(getApplicationContext());
		myScaleBarOverlay = new ScaleBarOverlay(this);
		myOpenMapView.getOverlays().add(myScaleBarOverlay);
		//myScaleBarOverlay.setMetric();
		//myScaleBarOverlay.enableScaleBar();
		// Scale bar tries to draw as 1-inch, so to put it in the top center, set x offset to
		// half screen width, minus half an inch.
		myScaleBarOverlay.setScaleBarOffset(getResources().getDisplayMetrics().widthPixels/2 - getResources().getDisplayMetrics().xdpi / 2, 10);
		
		// Add MyLocationOverlay
		myLocationOverlay = new MyLocationNewOverlay(this, myOpenMapView);
		myOpenMapView.getOverlays().add(myLocationOverlay);
		myOpenMapView.postInvalidate();
	}

	/*
	OnItemGestureListener<OverlayItem> myOnItemGestureListener = new OnItemGestureListener<OverlayItem>() {

		@Override
		public boolean onItemLongPress(int arg0, OverlayItem arg1) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onItemSingleTapUp(int index, OverlayItem item) {
			// TODO Auto-generated method stub
			Toast.makeText(MainActivity.this,
				    item.getSnippet() + "\n"
				    	     + item.getTitle() + "\n"
				    	     + item.getPoint().getLatitudeE6() + " : " + item.getPoint().getLongitudeE6(), 
				    	     Toast.LENGTH_LONG).show();
			return true;
		}
	};
	*/
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, myLocationListener);
		//locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myLocationListener);
		myLocationOverlay.enableMyLocation();
		//myLocationOverlay.enableCompass();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(myLocationListener);
		myLocationOverlay.disableMyLocation();
		//myLocationOverlay.disableCompass();
	}

	private void updateLoc(Location loc) {
		GeoPoint locGeoPoint = new GeoPoint(loc.getLatitude(), loc.getLongitude());
		myMapController.setCenter(locGeoPoint);  // θέτει το τρέχων σημείο του χάρτη
		
		//setOverlayLoc(loc);
		
		myOpenMapView.invalidate(); // προκαλεί refresh του χάρτη ανανεώνοντας έτσι τις ιδιότητες του
	}
	
	/*
	private void setOverlayLoc(Location overlayloc) {
		GeoPoint overlocGeoPoint = new GeoPoint(overlayloc);

		//overlayItemArray.clear();
		
		OverlayItem newMyLocationItem = new OverlayItem("My Location", "My Location", overlocGeoPoint);
		overlayItemArray.add(newMyLocationItem);
	}
	*/
	
	private LocationListener myLocationListener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
						
		}
		
		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			//updateLoc(location); // λαμβάνει τα X/Y απο το GPS και τα τροφοδοτεί στο τρέχων σημείο του χάρτη 
		}
	};


/*==================================
	
	// http://stackoverflow.com/questions/12991175/osmdroid-ontap-example
	// http://code.google.com/p/osmdroid/issues/detail?id=245#makechanges
	class MyOwnItemizedOverlay extends ItemizedIconOverlay<ExtendedOverlayItem> {
		protected Context mContext;
		
		public MyOwnItemizedOverlay(final Context context, final List<ExtendedOverlayItem> aList) {
			 super(context, aList, new OnItemGestureListener<ExtendedOverlayItem>() {
	            @Override public boolean onItemSingleTapUp(final int index, final ExtendedOverlayItem item) {
	                    return false;
	            }
	            @Override public boolean onItemLongPress(final int index, final ExtendedOverlayItem item) {
	                    return false;
	            }
	    	  } );
		  mContext = context;
		}
		
		@Override protected boolean onSingleTapUpHelper(final int index, final ExtendedOverlayItem item, final MapView mapView) {
			//Toast.makeText(mContext, "Item " + index + " has been tapped!", Toast.LENGTH_SHORT).show();
						
			Toast.makeText(MainActivity.this,
				    item.getSnippet() + "\n"
				    	     + item.getTitle() + "\n"
				    	     + item.getPoint().getLatitudeE6() + " : " + item.getPoint().getLongitudeE6(), 
				    	     Toast.LENGTH_LONG).show();
			/*
			AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
			dialog.setTitle(item.getTitle());
			dialog.setMessage(item.getSnippet() + item.getPoint().getLongitudeE6() + ":" + item.getPoint().getLongitudeE6());
			dialog.show();
			
			return true;
		}
	}

==================================
*/



/*
	private class MyItemizedIconOverlay extends ItemizedIconOverlay<OverlayItem> {

		public MyItemizedIconOverlay(
				List<OverlayItem> pList,
				org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener<OverlayItem> pOnItemGestureListener,
				ResourceProxy pResourceProxy) {
			super(pList, pOnItemGestureListener, pResourceProxy);
			// TODO Auto-generated constructor stub
		}

/*
		@Override
		public void draw(Canvas canvas, MapView mapview, boolean arg2) {
			super.draw(canvas, mapview, arg2);
			
			if(!overlayItemArray.isEmpty()) {
				//overlayItemArray have only ONE element only, so I hard code to get(0)
				GeoPoint in = overlayItemArray.get(0).getPoint();
				Point out = new Point();
				
				mapview.getProjection().toPixels(in,out);
				
				Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_maps_indicator_current_position);
				canvas.drawBitmap(bm, 
						out.x - bm.getWidth()/2,   //shift the bitmap center
						out.y - bm.getHeight()/2,  //shift the bitmap center
						null);
//			}
		}
	
		@Override
		public boolean onSingleTapUp(MotionEvent event, MapView mapView) {
			//return super.onSingleTapUp(event, mapView);
			return true;
		}
	}

*/
}























