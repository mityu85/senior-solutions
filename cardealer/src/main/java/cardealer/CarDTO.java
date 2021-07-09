package cardealer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {

    private String brand;
    private String type;
    private int year;
    private CarShape carShape;
    private List<KmState> kmStates = new ArrayList<>();
}
