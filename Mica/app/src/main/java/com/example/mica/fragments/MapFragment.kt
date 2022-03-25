package com.example.mica.fragments

import android.content.res.Resources
import android.graphics.Rect
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.mica.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.*
import java.io.IOException
import java.util.*

class MapFragment: DialogFragment(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var locationCoordinates: LatLng

    companion object {
        const val TAG = "MapFragment"

        private const val CLINIC_NAME = "CLINIC_NAME"

        fun newInstance(name: String, longLat: String): MapFragment {
            val coors: List<String> = listOf(*longLat.split(",").toTypedArray())

            val args = Bundle()
            args.putString(CLINIC_NAME, name)
            val fragment = MapFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onMapReady(map: GoogleMap) {
        map.let{
            googleMap = it

            val clinic = LatLng(locationCoordinates.latitude, locationCoordinates.longitude)
            map.addMarker(MarkerOptions().position(clinic).title("The clinic is here."))
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(clinic, 15f), 3000, null)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mv_google_map.onCreate(savedInstanceState)
        mv_google_map.onResume()

        mv_google_map.getMapAsync(this)

        val coordinates = getCoordinates(arguments?.getString(CLINIC_NAME).toString())
        print(coordinates)
        if (coordinates != null)
            locationCoordinates = coordinates

        view.findViewById<TextView>(R.id.tv_close).setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        setDialogSize(90,70)
    }

    private fun setDialogSize(widthPercentage: Int, heightPercentage: Int) {
        val percentWidth = widthPercentage.toFloat() / 100
        val percentHeight = heightPercentage.toFloat() / 100
        val dm = Resources.getSystem().displayMetrics
        val rect = dm.run {
            Rect(0, 0, widthPixels, heightPixels)
        }
        val width = rect.width() * percentWidth
        val height = rect.height() * percentHeight
        dialog?.window?.setLayout(width.toInt(), height.toInt())
    }

    private fun getCoordinates(location: String): LatLng? {
        val geocoder = Geocoder(this.requireContext(), Locale.getDefault())
        var p1: LatLng? = null
        try {
            val addressList: List<Address> = geocoder.getFromLocationName(location, 1)
            if ((true) and (addressList.isNotEmpty())) {
                val address: Address = addressList[0]
                p1 = LatLng(address.latitude, address.longitude)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return p1
    }
}