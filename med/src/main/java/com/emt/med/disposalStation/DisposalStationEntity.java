package com.emt.med.disposalStation;

import com.emt.med.location.Location;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class DisposalStationEntity extends Location {
}
