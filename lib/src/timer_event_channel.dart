import 'dart:async';

import 'package:flutter/services.dart';

class TimerEventChannel {
  final EventChannel _eventChannel =
      const EventChannel('com.example.platform_channels/timer');

  Stream<int?> get timer {
    return _eventChannel.receiveBroadcastStream().map((event) => event as int?);
  }
}
