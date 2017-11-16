<img src="https://i.annihil.us/u/prod/misc/marvel.svg" width=200/>

# great-monster-wars

### Table of Contents

* [Overview](#overview)
* [Getting Started](#getting-started)
    * [Prerequisites](#prerequisites)
    * [Execution](#execution)
* [Deployment](#deployment)
    * [Prerequisites on a Live Environment](#prerequisites-on-a-live-environment)
* [License](#build-with)
* [Acknowledgments](#acknowledgments)

## Overview

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.
See deployment for notes on how to deploy the project on a live system.

### Prerequisites
* Java 1.8 JDK (for installation instructions see [here](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html))

### Execution
For building the source code, running the tests and launching the war, please navigate in the root folder of the project
from your console and execute the following command.

#### Unix
```
./gradlew test run -PappArgs="100"
```

#### Windows
```
gradlew test run -PappArgs="100"
```

## Deployment
In order to deploy the application navigate in the root folder of the project from your console and execute the following command.

#### Unix
`./gradlew build`

#### Windows
`gradlew build`

Then copy **build/distributions/great-monsters-war.zip** and unzip in a folder on the target machine, navigate to the unziped folder and execute:

#### Unix
```
./bin/great-monsters-war
```

#### Windows
```
bin/great-monsters-war
```

### Prerequisites on a Live Environment
* Java 1.8 JRE (for installation instructions see [here](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html))

## License
This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details