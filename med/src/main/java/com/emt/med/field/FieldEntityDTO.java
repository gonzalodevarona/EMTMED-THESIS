package com.emt.med.field;

import com.emt.med.value.ValueEntityDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FieldEntityDTO {

    @Positive(message = "id number should be greater than zero")
    private Long id;

    @NotBlank(message = "name shouldn't be blank")
    private String name;

    @NotBlank(message = "type shouldn't be blank")
    private FieldType type;


    private List<ValueEntityDTO> values;
}
