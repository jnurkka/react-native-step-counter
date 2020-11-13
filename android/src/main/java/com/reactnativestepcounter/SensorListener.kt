package com.reactnativestepcounter

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.WritableMap
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter

class SensorListener(reactContext: ReactContext, onFilteredValueChange: (value: FloatArray) -> Unit) : SensorEventListener {
  private val mContext = reactContext.applicationContext
  private val mReactContext = reactContext;
  private var mSensorManager : SensorManager ?= null
  private var mAccelerometer : Sensor ?= null
  private val callback = onFilteredValueChange

  init {
    Log.d("StepCounter", "Initiated app")

    mSensorManager = mContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    mAccelerometer = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
  }

  override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
  }

  override fun onSensorChanged(event: SensorEvent?) {
    if (event != null) {
      this.callback(event.values)
    }
  }

  fun startListening() {
    Log.d("StepCounter", "Starting listener")
    mSensorManager!!.registerListener(this, mAccelerometer,
      SensorManager.SENSOR_DELAY_GAME)
  }

  fun stopListening() {
    mSensorManager!!.unregisterListener(this)
  }
}
