package com.emt.med.disposalStation;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DisposalStationEntityService {
    DisposalStationEntityDTO getDisposalStationEntityDTOById(Long disposalStationEntityId);
    List<DisposalStationEntityDTO> getAllDisposalStations();
    DisposalStationEntityDTO addDisposalStation(DisposalStationEntityDTO disposalStationEntityDTO);

    DisposalStationEntityDTO updateDisposalStation(DisposalStationEntityDTO disposalStationEntityDTO);

    void deleteDisposalStation(Long id);
}
