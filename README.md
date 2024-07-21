# Android application
- Log and graph telemetry data from https://github.com/1nsp1r3/esp32/tree/main/bosch project

# TODO
- Fenetre glissante sur les données à grapher

# Principe
## Hardware
```
 ___________
|           |
|  BOSCH    |
|  sensors  |
|___________|
   |     |
   | °C  | PSI
   |     |
 __|_____|__
|           |
|  ESP-S3   |
|___________|
      |

      | BLE (°C + PSI)

 _____|_____
|           |
|  Android  |
|___________|
```

## Android software
```
 ______________     _______________________    _____ 
|              |   |                       |  |     |
| MainActivity |   | MainForegroundService |  | Gap |
|______________|   |_______________________|  |_____|
       |                       |                 |
       |-------- START ------->|                 |
       |                       |-- START SCAN -->|
       |                       |                 |
```

```
 ________       ____________________    _________________________ 
|        |     |                    |  |                         |
| ESP-S3 |     | BleGapScanCallBack |  | BleGapScanCallBack.data |
|________|     |____________________|  |_________________________|
    |                    |                          |
    |---- GAP packet --->|                          |
    |                    |------- onNext(data) ---->|
    |---- GAP packet --->|                          |
    |                    |------- onNext(data) ---->|
    |---- GAP packet --->|                          |
    |                    |------- onNext(data) ---->|
    |                    |                          |
```

```
 _________________________     _______________ 
|                         |   |               |
| BleGapScanCallBack.data |   | bleListener() |
|_________________________|   |_______________|
        |                             |
        |---------- new data -------->|
        |                             |
        |---------- new data -------->|
        |                             |
        |---------- new data -------->|
        |                             |
```
