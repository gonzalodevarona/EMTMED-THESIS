package com.emt.med.pharmacy;

import com.emt.med.location.LocationDTO;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeName("pharmacy")
public class PharmacyEntityDTO extends LocationDTO {
    private PharmacyCategory category;
}
