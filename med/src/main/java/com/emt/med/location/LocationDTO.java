package com.emt.med.location;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDTO {

    @Positive(message = "id should be greater than 0")
    private Long id;
    @NotBlank(message = "name shouldn't be blank")
    private String name;
}
