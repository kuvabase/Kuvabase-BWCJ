[ ![Download](https://api.bintray.com/packages/kuvabase/maven/bwcj/images/download.svg) ](https://bintray.com/kuvabase/maven/bwcj/_latestVersion)

# Kuvabase-BWCJ
The client library written on java for [**BWS Bitcore wallet service**](https://github.com/bitpay/bitcore-wallet-service).

---
### Description
This library communicates with **BWS Bitcore wallet service** using the REST API. 

The library includes [Retrofit2](https://github.com/square/retrofit) realization for a server interaction, but there is no strong dependecy in a domain level on **Retrofit2**. So you free to implement any other library/framework to interact with a server.

---

### Get Started
Add dependecy on **bwcj** to a `build.gradle` file:
```gradle
repositories {
    maven {
        url 'https://dl.bintray.com/kuvabase/maven'
    }
}

dependencies {
    implementation 'org.openkuva.kuvabase:bwcj:0.1.8.21'
}
```
Check newest version of Kuvabase-BWCJ [here](https://bintray.com/kuvabase/maven/bwcj).

---
### Flows overview

- [Create wallet flow](https://github.com/kuvabase/Kuvabase-BWCJ/blob/development/CreateWallet.md)
- [Send funds flow](https://github.com/kuvabase/Kuvabase-BWCJ/blob/development/SendFunds.md)

---
### Example

See the [**Sample**](https://github.com/kuvabase/Kuvabase-BWCJ-sample-app) Android application for a simple wallet implementation that relays on **BWS** and uses **Kuvabase-BWCJ**.

---