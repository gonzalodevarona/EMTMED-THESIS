package com.emt.med.consumable;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ConsumableEntityService {
    ConsumableEntity getConsumableEntityById(Long consumableEntityId);
    ConsumableEntityDTO getConsumableEntityDTOById(Long consumableEntityId);
    List<ConsumableEntityDTO> getAllConsumables();
    ConsumableEntityDTO addConsumable(ConsumableEntityDTO consumableEntityDTO);

    ConsumableEntityDTO updateConsumable(ConsumableEntityDTO consumableEntityDTO);

    void deleteConsumable(Long id);
}
