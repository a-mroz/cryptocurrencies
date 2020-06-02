# Cryptocurrencies

Demo project for Gamedia.


# Building
Requires Java 14. Build with gradle:
```shell script
./gradlew build
```

# CoinApi API Key
Application requires API key for the [CoinApi service](https://www.coinapi.io/pricing?apikey).

# Running
After building can be started with:
```shell script
COINAPI_API_KEY=replace java -jar build/libs/cryptocurrencies-0.0.1-SNAPSHOT.jar 
```

or, using Gradle:
```shell script
COINAPI_API_KEY=replace ./gradlew bootRun
```

Replace CoinApi api key with your key. 
