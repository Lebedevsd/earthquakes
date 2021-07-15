<img src="app_usage.gif" width="30%" height="30%">

## Info

Application fetches the EarthQuakes data from api: 
`http://api.geonames.org/earthquakesJSONformatted=true&north=44.1&south=-9.9&east=-22.4&west=55.2&username=mkoppelman`

Displays earthquakes in a list format, if the magnitude of the quake is equal or greater than 8, then the magnitude is hightited and icon shows bigger quakes. 
By clicking the item in the list, second screen is opened, showing the location of the quake on a map.

## Secrets

1. Put `MAPS_API_KEY` variable to your local.properties, otherwise the map would not be available.

## How to run:

1. Run `./gradlew app:assembleDebug` in your terminal to build an application
2. Run `./gradlew app:installDebug` in your terminal to install app on connected device/emulator
3. You can install app-release.apk directly from the repository by `adb install app-release.apk`

## Architecture

I have chose standard layering architecture, where MVVM is used for representation layer.

Api level objects are being hidden by the repository and converted to domain level objects (which potentially could be stored in database of some kind)

I have used **Hilt** as a dependency injection library, since it is build on top of Dagger2, and simplify setup of DI framework in the project.

Following libraries were used for development: RxJava3, Retrofit2, Moshi, Navigation, Hilt, ViewModel

## Tests

To run unit-tests run `./gradlew app:test`, or select `Run all tests` on `app/src/test/java` folder of the project in Android Studio
