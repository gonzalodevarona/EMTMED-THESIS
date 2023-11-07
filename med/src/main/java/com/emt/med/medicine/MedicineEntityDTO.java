package com.emt.med.medicine;

import com.emt.med.supply.SupplyDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineEntityDTO extends SupplyDTO {
    private String activePharmaceuticalIngredient;
    private String concentration;
}
