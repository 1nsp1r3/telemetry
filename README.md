# Android application
- Log and graph telemetry data from https://github.com/1nsp1r3/esp32/tree/main/bosch project

# Performance
## Samsung S23
Tourne nickel pendant 10mn (Rendement à 90%)

## Doogee
Les 2 premières minutes sont laborieuses, ensuite le rendement augmente
Après 5mn de fonctionnement, 33% de rendement (43% en split)
Après 10mn de fonctionnement, 28% de rendement (38% en split)

# TODO
- Faire un bouton : Exporter vers... (Au lieu de sauver automatiquement dans un fichier)
- Load view :
  - Gestion de beaucoup d'élement

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
