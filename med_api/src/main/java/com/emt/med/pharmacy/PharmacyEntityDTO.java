package com.emt.med.pharmacy;

import com.emt.med.location.LocationDTO;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("pharmacy")
public class PharmacyEntityDTO extends LocationDTO {
    @NotNull(message = "category should not be null")
    private PharmacyCategory category;
}


