package com.emt.med.countingUnit;

import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountingUnitEntityServiceImpl implements CountingUnitEntityService{

    private CountingUnitEntityRepository countingUnitEntityRepository;

    static CountingUnitEntityMapper countingUnitEntityMapper = Mappers.getMapper(CountingUnitEntityMapper.class);

    public CountingUnitEntityServiceImpl(CountingUnitEntityRepository countingUnitEntityRepository) {
        this.countingUnitEntityRepository = countingUnitEntityRepository;
    }

    @Override
    public CountingUnitEntity getCountingUnitById(Long countingUnitEntityId) {
        return countingUnitEntityRepository.findById(countingUnitEntityId).orElseThrow(() -> new RuntimeException("No counting unit found with id "+countingUnitEntityId));
    }

    @Override
    public CountingUnitEntityDTO getCountingUnitDTOById(Long countingUnitEntityId) {
        CountingUnitEntity countingUnitEntity = countingUnitEntityRepository.findById(countingUnitEntityId).orElseThrow(() -> new RuntimeException("No counting unit found with id "+countingUnitEntityId));
        return countingUnitEntityMapper.toDTO(countingUnitEntity);
    }

    @Override
    public List<CountingUnitEntityDTO> getAllCountingUnits() {
        return countingUnitEntityRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream().map(countingUnitEntityMapper::toDTO).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    @Transactional
    public CountingUnitEntityDTO addCountingUnit(CountingUnitEntityDTO countingUnitEntityDTO) {
        if (countingUnitEntityDTO.getId() != null) {
            throw new RuntimeException("A new counting unit cannot already have an ID");
        }
        CountingUnitEntity countingUnitEntity = countingUnitEntityMapper.toEntity(countingUnitEntityDTO);
        countingUnitEntity = countingUnitEntityRepository.save(countingUnitEntity);
        return countingUnitEntityMapper.toDTO(countingUnitEntity);
    }

    @Override
    public CountingUnitEntity saveCountingUnit(CountingUnitEntity countingUnit) {
        return countingUnitEntityRepository.save(countingUnit);
    }

    @Override
    @Transactional
    public CountingUnitEntityDTO updateCountingUnit(CountingUnitEntityDTO countingUnitEntityDTO) {
        CountingUnitEntity existingCountingUnitEntity = countingUnitEntityRepository.findById(countingUnitEntityDTO.getId()).orElseThrow(() -> new RuntimeException("No counting unit found with id "+countingUnitEntityDTO.getId()));
        countingUnitEntityMapper.updateCountingUnitFromDTO(countingUnitEntityDTO, existingCountingUnitEntity);
        return countingUnitEntityMapper.toDTO(countingUnitEntityRepository.save(existingCountingUnitEntity));
    }

    @Override
    @Transactional
    public void deleteCountingUnit(Long id) {
        countingUnitEntityRepository.deleteById(id);
    }
}
