package com.emt.med.order;

import com.emt.med.supply.Supply;
import com.emt.med.supply.SupplyDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntityDTO {

    @Positive(message = "id number should be greater than zero")
    private Long id;
    @Positive(message = "practitioner id should be greater than zero")
    private Long practitionerId;
    @Positive(message = "quantity should be greater than zero")
    private Long quantity;
    @PastOrPresent(message = "authored on date should be today or before")
    private LocalDateTime authoredOn;
    @NotNull
    private OrderStatus status;

    private List<SupplyDTO> supplies;
}
