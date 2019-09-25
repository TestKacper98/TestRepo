package stefanowicz.kacper.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import stefanowicz.kacper.model.enums.CarBodyColor;
import stefanowicz.kacper.model.enums.CarBodyType;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarBody {

    private CarBodyColor color;
    private CarBodyType type;
    private List<String> components;

}
