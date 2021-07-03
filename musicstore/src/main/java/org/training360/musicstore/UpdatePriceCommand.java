package org.training360.musicstore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePriceCommand {

    @Min(0)
    private int price;
}
