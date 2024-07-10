package com.example.platform_channels

import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodChannel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MainActivity: FlutterActivity(){
    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        var channel = "com.example.platform_channels/no-generator";

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, channel).setMethodCallHandler {
            call, result ->
            if(call.method == "getRandomNumber") {
                val rand = Random.nextInt(100)
                result.success(rand)
            }
            else {
                result.notImplemented()
            }
        }

        var eventChannel  = "com.example.platform_channels/timer";
        EventChannel(flutterEngine.dartExecutor.binaryMessenger,eventChannel).setStreamHandler(CustomStreamHandler())
    }
}


class CustomStreamHandler : EventChannel.StreamHandler {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)
    private var isListening = false
    private var counter = 0

    // Code to set up and manage the event stream
    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
        if (events != null) {
            isListening = true
            scope.launch {
                while (isListening) {
                    if (counter==100){
                        counter=0
                        isListening= false
                    }
                    val value = counter++
                    withContext(Dispatchers.Main) {
                        // Send the value to Flutter as a success event.
                        events.success(value)
                    }
                    delay(1000) // Send value every 1000 milliseconds
                }
            }
        }

    }

    override fun onCancel(arguments: Any?) {
        // Set isListening to false to stop generating events.
        isListening = false
    }

}