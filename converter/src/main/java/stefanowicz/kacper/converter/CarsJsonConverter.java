package stefanowicz.kacper.converter;


import stefanowicz.kacper.model.Car;

import java.util.Set;

public class CarsJsonConverter extends JsonConverter<Set<Car>> {

    public CarsJsonConverter(String jsonFilename) {
        super(jsonFilename);
    }
}
