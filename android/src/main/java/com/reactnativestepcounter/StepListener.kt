package com.reactnativestepcounter

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import com.facebook.react.bridge.ReactApplicationContext
import kotlin.math.sqrt

fun floatsToDoubles(input: FloatArray): DoubleArray {
  val output = DoubleArray(input.size)
  for (i in input.indices) {
    output[i] = input[i].toDouble()
  }
  return output
}

fun getMagnitude(sensorReading: FloatArray) : Double {
  val (x, y, z) = sensorReading;
  return sqrt(x.toDouble() * x.toDouble() + y.toDouble() * y.toDouble() + z.toDouble() * z.toDouble())
}

fun filterMagnitude(prev: Double, next: Double) : Double {
  return 0.8 * prev + 0.2 * next
}

class StepListener(mContext: ReactApplicationContext, onStep: (timestamp: Long) -> Unit) : SensorEventListener {
  private var mSensorManager : SensorManager ?= null
  private var mAccelerometer : Sensor ?= null
  private val callback = onStep
  private var prevMagnitude = 0.0
  private var prevStepTimestamp = System.currentTimeMillis()
  private var isPeak = false
  init {
    mSensorManager = mContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    mAccelerometer = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
  }

  override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
  }

  private fun isStep(magnitude: Double, timestamp: Long): Boolean {
    if (magnitude > 0.5 || magnitude < -0.5) {
      if (isPeak === false) {
        isPeak = true
        return true
      }
    } else if ((timestamp - prevStepTimestamp) > 300) {
      isPeak = false
    }
    return false
  }

  override fun onSensorChanged(event: SensorEvent?) {
    if (event != null) {
      val currentTime: Long = System.currentTimeMillis()
      val magnitude : Double = getMagnitude(event.values) - 9.81
      val filteredMagnitude : Double = filterMagnitude(prevMagnitude, magnitude)
      if (isStep(filteredMagnitude, currentTime) === true) this.callback(currentTime)
    }
  }

  fun startListening() {
    Log.d("StepCounter", "Starting listener")
    mSensorManager!!.registerListener(this, mAccelerometer,
      SensorManager.SENSOR_DELAY_NORMAL)
  }

  fun stopListening() {
    mSensorManager!!.unregisterListener(this)
  }
}
