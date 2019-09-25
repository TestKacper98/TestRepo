# CarManagementSystem2
CarManagementSystem2 is extended version of CarManagementSystem project with various improvements of model classes and data management.
It allows user to:
* Sort cars by:
    * Components quantity
    * Engine power
    * Tyre size
* Show cars with car body type and price range given as arguments
* Show sorted cars in alphabetical with engine type given as argument
* Show summary statistics, that contains average, maximum and minimum for:
    * Car price
    * Car mileage
    * Engine power
* Show a juxtaposition with car and its mileage
* Show a juxtaposition with tyre type and list of cars with this tyre type
* Show cars with components given as arguments

## Installation

* From _CarManagementSystem_ module: 
```bash
    mvn clean install
``` 
* From _main_ module
```bash
    mvn clean compile assembly::single
```

## Usage

* From _main/target_ 
```bash
    java --enable-preview -cp main-1.0-SNAPSHOT-jar-with-dependencies.jar stefanowicz.kacper.main.App
```

Please make sure that _cars.json_ file is located in the same folder as _main-1.0-SNAPSHOT-jar-with-dependencies.jar_


