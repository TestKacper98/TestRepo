package stefanowicz.kacper.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import stefanowicz.kacper.model.enums.TyreType;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Wheel {

    private String model;
    private BigDecimal size;
    private TyreType type;

}
