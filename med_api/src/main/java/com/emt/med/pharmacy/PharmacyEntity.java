package com.emt.med.pharmacy;

import com.emt.med.location.Location;
import com.fasterxml.jackson.annotation.JsonTypeName;
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
