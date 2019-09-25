package stefanowicz.kacper.validator;

import stefanowicz.kacper.model.CarBody;
import stefanowicz.kacper.validator.generic.AbstractValidator;

import java.util.Map;

public class CarBodyValidator extends AbstractValidator<CarBody> {
    @Override
    public Map<String, String> validate(CarBody carBody) {
       errors.clear();

       if( carBody == null ){
           errors.put("carBody", "car body is null");
           return errors;
       }

       if( !isCarBodyColorValid(carBody)){
           errors.put("carBodyColor", "car body color cannot be null");
       }

       if( !isCarBodyTypeValid(carBody) ){
           errors.put("carBodyType", "car body type cannot be null");
       }

       if( !isCarBodyComponents(carBody) ) {
           errors.put("carBodyComponents", "car body components are not valid, they have to consists of capital letters and whitespaces only");
       }

       return errors;
    }

    private boolean isCarBodyColorValid(CarBody carBody){
        return carBody.getColor() != null;
    }

    private boolean isCarBodyTypeValid(CarBody carBody) {
        return  carBody.getType() != null;
    }

    private boolean isCarBodyComponents(CarBody carBody ){
        return carBody.getComponents()
                .stream()
                .allMatch(comp -> comp.matches("([A-Z]+\\s)?[A-Z]+"));
    }
}
