package com.emt.med.medicine;

import com.emt.med.supply.Supply;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineEntity extends Supply {

    private String activePharmaceuticalIngredient;
    private String concentration;
}
