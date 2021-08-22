package org.training360.finalexam.players;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePlayerCommand {

    @NotBlank(message = "name cannot be blank")
    private String name;

    private LocalDate dateOfBirth;
    private PositionType position;
}
