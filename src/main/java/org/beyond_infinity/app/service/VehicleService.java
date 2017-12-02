package org.beyond_infinity.app.service;

import org.beyond_infinity.app.domain.Vehicle;
import org.beyond_infinity.app.repository.VehicleRepository;
import org.beyond_infinity.app.service.dto.VehicleDTO;
import org.beyond_infinity.app.service.mapper.VehicleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Vehicle.
 */
@Service
@Transactional
public class VehicleService {

    private final Logger log = LoggerFactory.getLogger(VehicleService.class);

    private final VehicleRepository vehicleRepository;

    private final VehicleMapper vehicleMapper;

    public VehicleService(VehicleRepository vehicleRepository, VehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
    }

    /**
     * Save a vehicle.
     *
     * @param vehicleDTO the entity to save
     * @return the persisted entity
     */
    public VehicleDTO save(VehicleDTO vehicleDTO) {
        log.debug("Request to save Vehicle : {}", vehicleDTO);
        Vehicle vehicle = vehicleMapper.toEntity(vehicleDTO);
        vehicle = vehicleRepository.save(vehicle);
        return vehicleMapper.toDto(vehicle);
    }

    /**
     * Get all the vehicles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<VehicleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Vehicles");
        return vehicleRepository.findAll(pageable)
            .map(vehicleMapper::toDto);
    }

    /**
     * Get one vehicle by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public VehicleDTO findOne(Long id) {
        log.debug("Request to get Vehicle : {}", id);
        Vehicle vehicle = vehicleRepository.findOne(id);
        return vehicleMapper.toDto(vehicle);
    }

    /**
     * Delete the vehicle by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Vehicle : {}", id);
        vehicleRepository.delete(id);
    }
}
