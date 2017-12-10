package org.beyond_infinity.app.service;

import org.beyond_infinity.app.domain.Vehicle;
import org.beyond_infinity.app.domain.VehicleOwnership;
import org.beyond_infinity.app.repository.VehicleOwnershipRepository;
import org.beyond_infinity.app.service.dto.VehicleOwnershipDTO;
import org.beyond_infinity.app.service.mapper.VehicleOwnershipMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;


/**
 * Service Implementation for managing VehicleOwnership.
 */
@Service
@Transactional
public class VehicleOwnershipService {

    private final Logger log = LoggerFactory.getLogger(VehicleOwnershipService.class);

    private final VehicleOwnershipRepository vehicleOwnershipRepository;

    private final VehicleOwnershipMapper vehicleOwnershipMapper;

    public VehicleOwnershipService(VehicleOwnershipRepository vehicleOwnershipRepository, VehicleOwnershipMapper vehicleOwnershipMapper) {
        this.vehicleOwnershipRepository = vehicleOwnershipRepository;
        this.vehicleOwnershipMapper = vehicleOwnershipMapper;
    }

    /**
     * Save a vehicleOwnership.
     *
     * @param vehicleOwnershipDTO the entity to save
     * @return the persisted entity
     */
    public VehicleOwnershipDTO save(VehicleOwnershipDTO vehicleOwnershipDTO) {
        log.debug("Request to save VehicleOwnership : {}", vehicleOwnershipDTO);
        VehicleOwnership vehicleOwnership = vehicleOwnershipMapper.toEntity(vehicleOwnershipDTO);
        vehicleOwnership = vehicleOwnershipRepository.save(vehicleOwnership);
        return vehicleOwnershipMapper.toDto(vehicleOwnership);
    }

    public void updateMyVehicles(List<VehicleOwnershipDTO> futureOwnedVehiclesDtos) {
        List<VehicleOwnershipDTO> currentlyOwnedVehicles = vehicleOwnershipMapper
            .toDto(vehicleOwnershipRepository.findByOwnerIsCurrentUser());

        List<VehicleOwnershipDTO> toBeAdded = futureOwnedVehiclesDtos.stream()
            .filter(futureOwned -> currentlyOwnedVehicles.stream()
                .noneMatch(futureOwned::equalOwnership)).collect(toList());

        List<VehicleOwnershipDTO> toBeDeleted = currentlyOwnedVehicles.stream()
            .filter(currentlyOwned -> futureOwnedVehiclesDtos.stream()
                .noneMatch(currentlyOwned::equalOwnership)).collect(toList());

        vehicleOwnershipRepository.save(vehicleOwnershipMapper.toEntity(toBeAdded));
        vehicleOwnershipRepository.delete(vehicleOwnershipMapper.toEntity(toBeDeleted));
    }

    /**
     * Get all the vehicleOwnerships.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<VehicleOwnershipDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VehicleOwnerships");
        return vehicleOwnershipRepository.findAll(pageable)
            .map(vehicleOwnershipMapper::toDto);
    }

    /**
     * Get one vehicleOwnership by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public VehicleOwnershipDTO findOne(Long id) {
        log.debug("Request to get VehicleOwnership : {}", id);
        VehicleOwnership vehicleOwnership = vehicleOwnershipRepository.findOne(id);
        return vehicleOwnershipMapper.toDto(vehicleOwnership);
    }

    /**
     * Delete the vehicleOwnership by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete VehicleOwnership : {}", id);
        vehicleOwnershipRepository.delete(id);
    }
}
