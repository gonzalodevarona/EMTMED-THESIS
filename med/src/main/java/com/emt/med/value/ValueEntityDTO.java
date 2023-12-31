package com.emt.med.value;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValueEntityDTO {

    @Positive(message = "id number should be greater than zero")
    private Long id;

    @NotBlank(message = "value shouldn't be blank")
    private String value;


}
