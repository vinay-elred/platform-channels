import 'package:flutter/material.dart';
import 'package:platform_channels/src/random_no_method_channel.dart';
import 'package:platform_channels/src/timer_event_channel.dart';

void main() {
  runApp(const MainApp());
}

class MainApp extends StatefulWidget {
  const MainApp({super.key});

  @override
  State<MainApp> createState() => _MainAppState();
}

class _MainAppState extends State<MainApp> {
  int? randomNo;

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text("Platform channels"),
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text(randomNo.toString()),
              ElevatedButton(
                onPressed: () async {
                  randomNo = await RandomNoMethodChannel().getRandomNo();
                  setState(() {});
                },
                child: const Text("generate random"),
              ),
              const SizedBox(height: 50),
              StreamBuilder(
                stream: TimerEventChannel().timer,
                builder: (context, snapshot) {
                  if (!snapshot.hasData) return const Text("Starting timer");
                  if (snapshot.hasError) return const Text("Timer error");
                  return Text(snapshot.data.toString());
                },
              )
            ],
          ),
        ),
      ),
    );
  }
}
