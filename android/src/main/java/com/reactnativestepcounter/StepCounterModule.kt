package com.reactnativestepcounter

import android.util.Log
import com.facebook.react.bridge.*
import com.facebook.react.modules.core.DeviceEventManagerModule

class StepCounterModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
  const val LOG_TAG = "RNStepCounter";
  private val mReactContext = reactContext;

  override fun getName(): String = "StepCounter"

  private fun emitStep(timestamp: Long) {
    Log.d(LOG_TAG, "Registered a step")
    try {
      mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
        .emit("StepRegistered", timestamp.toString())
    } catch (e: RuntimeException) {
      Log.e(LOG_TAG, "java.lang.RuntimeException: Trying to invoke Javascript before CatalystInstance has been set!")
    }
  }

  var listener = StepListener(mReactContext, ::emitStep);

  @ReactMethod
  fun start() {
    listener.startListening();
    Log.d(LOG_TAG, "Started listening to steps using accelerometer")
  }

  @ReactMethod
  fun stop() {
    listener.stopListening();
    Log.d(LOG_TAG, "Stopped listening to steps using accelerometer")
  }
}
