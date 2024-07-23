# Android application
- Log and graph telemetry data from https://github.com/1nsp1r3/esp32/tree/main/bosch project

# TODO
- Envoyer donner brut sonde et filter coté android
- Axe X en heure
- Ne pas planter au démarrage si il manque les droits

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
 ________       ____________________    _________________________    _______________ 
|        |     |                    |  |                         |  |               |
| ESP-S3 |     | BleGapScanCallBack |  | BleGapScanCallBack.data |  | bleListener() |
|________|     |____________________|  |_________________________|  |_______________|
    |                    |                          |                       |
    |---- GAP packet --->|                          |                       |
    |                    |------- onNext(data) ---->|                       |
    |---- GAP packet --->|                          |------ new data ------>|
    |                    |------- onNext(data) ---->|                       |
    |---- GAP packet --->|                          |------ new data ------>|
    |                    |------- onNext(data) ---->|                       |
    |                    |                          |------ new data ------>|
    |                    |                          |                       |
```
