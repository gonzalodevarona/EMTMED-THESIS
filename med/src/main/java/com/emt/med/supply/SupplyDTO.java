package com.emt.med.supply;

import com.emt.med.batch.BatchEntityDTO;
import com.emt.med.countingUnit.CountingUnitEntityDTO;
import com.emt.med.location.LocationDTO;
import com.emt.med.order.OrderEntityDTO;
import com.emt.med.weightUnit.WeightUnitEntityDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplyDTO {

    @Positive(message = "id number should be greater than zero")
    private Long id;
    @NotBlank(message = "name shouldn't be blank")
    private String name;
    @Positive(message = "weight should be greater than zero")
    private Long weight;
    @Min(0)
    private Long quantity;
    private List<OrderEntityDTO> orders;

    private WeightUnitEntityDTO weightUnit;
    private CountingUnitEntityDTO countingUnit;
    @NotBlank(message = "purpose shouldn't be blank")
    private SupplyPurpose purpose;


}
