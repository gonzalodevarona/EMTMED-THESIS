package com.emt.med.supply;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SupplyService {

    SupplyDTO getSupplyDTOById(Long supplyId);
    Supply getSupplyById(Long supplyId);
    List<SupplyDTO> getAllSupplies();
    SupplyDTO addSupply(SupplyDTO supplyDTO);
    SupplyDTO updateSupply(SupplyDTO medicineEntityDTO);
    void deleteSupply(Long id);
  

   

    
}
