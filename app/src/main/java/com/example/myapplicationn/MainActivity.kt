package com.example.myapplicationn

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.progressindicator.LinearProgressIndicator

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var fenyero: Sensor? = null
    private lateinit var text: TextView
    private lateinit var hatter: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text = findViewById(R.id.luxvalue)

        setUpSensor()
    }
    private fun setUpSensor() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        fenyero = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        hatter = findViewById(R.id.view3)
        if (p0?.sensor?.type == Sensor.TYPE_LIGHT) {
            val light1 = p0.values[0]

            text.text = "Fenyero(Lux): $light1\n${brightness(light1)}"
            if(light1 > 1 && light1 < 30){
                hatter.setBackgroundColor(Color.parseColor("#c4c4c4"))
            }
            if(light1 > 31 && light1 < 100){
                hatter.setBackgroundColor(Color.parseColor("#878787"))
            }
            if(light1 > 101 && light1 < 5000){
                hatter.setBackgroundColor(Color.parseColor("#4a4a4a"))
            }
            if(light1 > 5001 ){
                hatter.setBackgroundColor(Color.parseColor("#000000"))
            }



        }
    }

    private fun brightness(brightness: Float): String {

        return when (brightness.toInt()) {
            0 -> "Éjszaka"
            in 1..30 -> "Este"
            in 31..100 -> "Szürkület"
            in 101..500 -> "Pirkadat"
            in 501..30000 -> "Nappal"
            else -> "VAKÍTÓ FÉNY"
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }
    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, fenyero, SensorManager.SENSOR_DELAY_NORMAL)
    }


    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}