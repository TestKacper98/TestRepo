package stefanowicz.kacper.validator;

import stefanowicz.kacper.model.Engine;
import stefanowicz.kacper.validator.generic.AbstractValidator;

import java.math.BigDecimal;
import java.util.Map;

public class EngineValidator extends AbstractValidator<Engine> {
    @Override
    public Map<String, String> validate(Engine engine) {
        errors.clear();

        if( engine == null ){
            errors.put("carEngine", "car engine is null");
            return errors;
        }

        if( !isEnginePowerValid(engine) ){
            errors.put("enginePower", "engine power has to bo greater than zero");
        }

        if( !isEngineTypeValid(engine) ){
            errors.put("engineType", "engine type cannot be null");
        }

        return errors;
    }

    private boolean isEngineTypeValid(Engine engine) {
        return engine.getType() != null;
    }

    private boolean isEnginePowerValid(Engine engine ){
        return engine.getPower() != null && engine.getPower().compareTo(BigDecimal.ZERO) > 0;
    }
}
