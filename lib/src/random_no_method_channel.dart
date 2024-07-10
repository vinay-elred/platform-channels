import 'dart:developer';

import 'package:flutter/services.dart';

class RandomNoMethodChannel {
  final MethodChannel _methodChannel = const MethodChannel('com.example.platform_channels/no-generator');

  Future<int?> getRandomNo() async {
    try {
      final int data = await _methodChannel.invokeMethod('getRandomNumber');
      return data;
    } catch (e) {
      log('failed to generate no.');
      return null;
    }
  }
}
