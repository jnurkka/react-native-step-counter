package com.reactnativestepcounter

import android.util.Log
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule

class StepCounterModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
  private val mReactContext = reactContext;
  override fun getName(): String = "StepCounter"

  private fun emitStep(timestamp: Long) {
    Log.d("StepCounter", "Registered a step")
    try {
      mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
        .emit("StepRegistered", timestamp.toString())
    } catch (e: RuntimeException) {
      Log.e("StepCounter - ERROR", "java.lang.RuntimeException: Trying to invoke Javascript before CatalystInstance has been set!")
    }
  }

  var listener = StepListener(mReactContext, ::emitStep);

  @ReactMethod
  fun start() {
    listener.startListening();
    Log.d("StepCounter", "Started listening to steps using accelerometer")
  }

  @ReactMethod
  fun stop() {
    listener.stopListening();
    Log.d("StepCounter", "Stopped listening to steps using accelerometer")
  }
}
