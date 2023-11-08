package com.emt.med.weightUnit;

import com.emt.med.supply.Supply;
import com.emt.med.supply.SupplyDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeightUnitEntityDTO {

    @Positive(message = "id number should be greater than zero")
    private Long id;

    @NotBlank(message = "name shouldn't be blank")
    private String name;


    private List<SupplyDTO> supplyDTOList;
}
