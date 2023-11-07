package com.emt.med.pharmacy;

import com.emt.med.location.Location;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PharmacyEntity extends Location {
    private PharmacyCategory category;
}
