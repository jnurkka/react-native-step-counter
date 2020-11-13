package com.reactnativestepcounter

import android.util.Log
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule

class StepCounterModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
  private val mReactContext = reactContext;
  override fun getName(): String = "StepCounter"
  private fun logValueChange(values: FloatArray) {
    val joinedValues = values.joinToString();
    Log.d("StepCounter", joinedValues)
    try {
      mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
        .emit("AccelerometerChange", joinedValues)
    } catch (e: RuntimeException) {
      Log.e("ERROR", "java.lang.RuntimeException: Trying to invoke Javascript before CatalystInstance has been set!")
    }
  }
  var listener = SensorListener(reactContext, ::logValueChange);

  @ReactMethod
  fun start() {
    Log.d("StepCounter", "Starting accelerometer")
    listener.startListening();
  }

  fun stop () {
    Log.d("StepCounter", "Stopping accelerometer")
    listener.stopListening();
  }

  @ReactMethod
  fun multiply(a: Int, b: Int, promise: Promise) {

    promise.resolve(a * b)

  }
}
