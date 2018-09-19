package k_spot.jnm.k_spot.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import k_spot.jnm.k_spot.R
import k_spot.jnm.k_spot.adapter.InfoWindowAdapter

class MapDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_detail)
        setStatusBarColor()

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnInfoWindowClickListener {
            if (it.isInfoWindowShown){
                it.hideInfoWindow()
            }
        }

        mMap.uiSettings.isMapToolbarEnabled = true

        val initSpot = LatLng(37.544664, 126.968159)

        val initMarker = MarkerOptions().apply {
            title("숙대 앞")
            snippet("오디야 오디야 오디야")
            position(initSpot)
            icon(BitmapDescriptorFactory.fromResource(R.drawable.google_map_circle))
        }
        mMap.setInfoWindowAdapter(InfoWindowAdapter(this))

        mMap.addMarker(initMarker)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initSpot, 15.0f))
    }

    private fun setStatusBarColor() {
        val view: View? = window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            view!!.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
    }
}
