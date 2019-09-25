package stefanowicz.kacper.validator;

import stefanowicz.kacper.model.Wheel;
import stefanowicz.kacper.validator.generic.AbstractValidator;

import java.math.BigDecimal;
import java.util.Map;

public class WheelValidator extends AbstractValidator<Wheel> {

    @Override
    public Map<String, String> validate(Wheel wheel) {

         errors.clear();

         if(wheel == null ){
             errors.put("wheelObject", "wheel object is null");
             return errors;
         }

         if( !isWheelModelValid(wheel) ) {
             errors.put("wheelModel", "wheel model has to consist of capital letter and whitespaces only");
         }

         if( !isWheelSizeValid(wheel) ) {
             errors.put("wheelSize", "wheel size has to be greater than 0");
         }

         if( !isWheelTyreTypeValid(wheel) ) {
             errors.put("wheelTyreType", "wheel tyre type cannot be null");
         }

         return errors;
    }

    private boolean isWheelModelValid(Wheel wheel) {
        return wheel.getModel() != null && wheel.getModel().matches("([A-Z]+\\s)?[A-Z]+");
    }

    private boolean isWheelSizeValid(Wheel wheel) {
        return wheel.getSize() != null && wheel.getSize().compareTo(BigDecimal.ZERO) > 0;
    }

    private boolean isWheelTyreTypeValid(Wheel wheel){
        return wheel.getType() != null;
    }

}
