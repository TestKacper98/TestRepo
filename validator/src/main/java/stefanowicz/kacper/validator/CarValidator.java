package stefanowicz.kacper.validator;

import stefanowicz.kacper.model.Car;
import stefanowicz.kacper.validator.generic.AbstractValidator;

import java.math.BigDecimal;
import java.util.Map;

public class CarValidator extends AbstractValidator<Car> {

    @Override
    public Map<String, String> validate(Car car) {
        errors.clear();

        if ( car == null ) {
            errors.put("carObject", "car object is null");
            return errors;
        }

        Map<String, String> wheelErrors = getWheelErrors(car);
        if( !wheelErrors.isEmpty()) {
            errors.putAll(wheelErrors);
        }

        Map<String, String> engineErrors = getEngineErrors(car);
        if( !engineErrors.isEmpty() ){
            errors.putAll(engineErrors);
        }

        Map<String, String> carBodyErrors = getCarBodyErrors(car);
        if( !carBodyErrors.isEmpty()){
            errors.putAll(carBodyErrors);
        }

        if( !isCarModelValid(car)){
            errors.put("carModel", "car model is not valid, it has to consists of capital letter and whitespaces only");
        }

        if( !isCarMileageValid(car)){
            errors.put("carMileage", "car mileage is not valid, it has to be greater than or equal to 0");
        }

        if( !isCarPriceValid(car)){
            errors.put("carPrice", "car price is not valid, it has to be greater than or equal to 0");
        }

        return errors;
    }

    private Map<String, String> getWheelErrors( Car car) {
        var wheelValidator = new WheelValidator();
        return wheelValidator.validate(car.getWheel());
    }

    private Map<String, String> getEngineErrors(Car car) {
        var engineValidator = new EngineValidator();
        return engineValidator.validate(car.getEngine());
    }

    private Map<String, String> getCarBodyErrors(Car car) {
        var carBodyValidator = new CarBodyValidator();
        return carBodyValidator.validate(car.getCarBody());
    }

    private boolean isCarModelValid(Car car){
        return car.getModel() != null && car.getModel().matches("([A-Z]+\\s)?[A-Z]+");
    }

    private boolean isCarPriceValid(Car car){
        return car.getPrice() != null && car.getPrice().compareTo(BigDecimal.ZERO) >= 0;
    }

    private boolean isCarMileageValid(Car car) {
        return car.getMileage() != null && car.getMileage().compareTo(BigDecimal.ZERO) >= 0;
    }
}
