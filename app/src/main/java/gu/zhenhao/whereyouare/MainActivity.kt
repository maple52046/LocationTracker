package gu.zhenhao.whereyouare

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var button: Button
    lateinit var textView: TextView
    lateinit var locationManager: LocationManager
    lateinit var lattitude: String
    lateinit var longitude: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION)

        textView = findViewById<View>(R.id.text_location) as TextView
        button = findViewById<View>(R.id.button_location) as Button

        button.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps()

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation()
        }
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(this@MainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this@MainActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this@MainActivity, arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION)

        } else {
            val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

            val location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

            val location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)

            if (location != null) {
                val latti = location.latitude
                val longi = location.longitude
                lattitude = latti.toString()
                longitude = longi.toString()

                textView.setText("Your current location is" + "\n" + "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude)

            } else if (location1 != null) {
                val latti = location1.latitude
                val longi = location1.longitude
                lattitude = latti.toString()
                longitude = longi.toString()

                textView.setText("Your current location is" + "\n" + "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude)


            } else if (location2 != null) {
                val latti = location2.latitude
                val longi = location2.longitude
                lattitude = latti.toString()
                longitude = longi.toString()

                textView.setText("Your current location is" + "\n" + "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude)

            } else {

                Toast.makeText(this, "Unble to Trace your location", Toast.LENGTH_SHORT).show()

            }
        }
    }

    protected fun buildAlertMessageNoGps() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
        val alert = builder.create()
        alert.show()
    }

    companion object {
        private val REQUEST_LOCATION = 1
    }

}
