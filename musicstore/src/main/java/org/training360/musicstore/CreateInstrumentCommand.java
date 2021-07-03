package org.training360.musicstore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateInstrumentCommand {

    @NotBlank
    private String brand;
    private InstrumentType type;
    @Min(0)
    private int price;
}
