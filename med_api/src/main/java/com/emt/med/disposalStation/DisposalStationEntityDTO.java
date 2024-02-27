package com.emt.med.disposalStation;

import com.emt.med.location.LocationDTO;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
@JsonTypeName("disposalStation")
public class DisposalStationEntityDTO extends LocationDTO {
}
