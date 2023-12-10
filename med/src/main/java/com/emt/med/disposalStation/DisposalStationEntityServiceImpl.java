package com.emt.med.disposalStation;

import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DisposalStationEntityServiceImpl implements DisposalStationEntityService{

    private DisposalStationEntityRepository disposalStationEntityRepository;

    static DisposalStationEntityMapper disposalStationEntityMapper = Mappers.getMapper(DisposalStationEntityMapper.class);

    public DisposalStationEntityServiceImpl(DisposalStationEntityRepository disposalStationEntityRepository) {
        this.disposalStationEntityRepository = disposalStationEntityRepository;
    }


    @Override
    public DisposalStationEntityDTO getDisposalStationEntityDTOById(Long disposalStationEntityId) {
        DisposalStationEntity disposalStationEntity = disposalStationEntityRepository.findById(disposalStationEntityId).orElseThrow(() -> new RuntimeException("No disposal station found with id "+disposalStationEntityId));
        return disposalStationEntityMapper.toDTO(disposalStationEntity);
    }

    @Override
    public List<DisposalStationEntityDTO> getAllDisposalStations() {
        return disposalStationEntityRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream().map(disposalStationEntityMapper::toDTO).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    @Transactional
    public DisposalStationEntityDTO addDisposalStation(DisposalStationEntityDTO disposalStationEntityDTO) {
        if (disposalStationEntityDTO.getId() != null) {
            throw new RuntimeException("A new disposal station cannot already have an ID");
        }
        DisposalStationEntity disposalStationEntity = disposalStationEntityMapper.toEntity(disposalStationEntityDTO);
        disposalStationEntity = disposalStationEntityRepository.save(disposalStationEntity);
        return disposalStationEntityMapper.toDTO(disposalStationEntity);
    }

    @Override
    @Transactional
    public DisposalStationEntityDTO updateDisposalStation(DisposalStationEntityDTO disposalStationEntityDTO) {
        DisposalStationEntity existingFieldEntity = disposalStationEntityRepository.findById(disposalStationEntityDTO.getId()).orElseThrow(() -> new RuntimeException("No field found with id "+disposalStationEntityDTO.getId()));
        disposalStationEntityMapper.updateDisposalStationFromDTO(disposalStationEntityDTO, existingFieldEntity);
        return disposalStationEntityMapper.toDTO(disposalStationEntityRepository.save(existingFieldEntity));
    }

    @Override
    @Transactional
    public void deleteDisposalStation(Long id) {
        disposalStationEntityRepository.deleteById(id);
    }
}
