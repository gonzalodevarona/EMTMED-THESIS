package com.emt.med.pharmacy;

import com.emt.med.location.LocationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PharmacyEntityDTO extends LocationDTO {
    private PharmacyCategory category;
}
