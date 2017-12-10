package org.beyond_infinity.app.service;


import io.github.jhipster.service.QueryService;
import org.beyond_infinity.app.domain.Vehicle;
import org.beyond_infinity.app.domain.VehicleOwnership;
import org.beyond_infinity.app.domain.Vehicle_;
import org.beyond_infinity.app.repository.VehicleOwnershipRepository;
import org.beyond_infinity.app.repository.VehicleRepository;
import org.beyond_infinity.app.security.SecurityUtils;
import org.beyond_infinity.app.service.dto.VehicleCriteria;
import org.beyond_infinity.app.service.dto.VehicleDTO;
import org.beyond_infinity.app.service.mapper.VehicleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Service for executing complex queries for Vehicle entities in the database.
 * The main input is a {@link VehicleCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VehicleDTO} or a {@link Page} of {@link VehicleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VehicleQueryService extends QueryService<Vehicle> {

    private final Logger log = LoggerFactory.getLogger(VehicleQueryService.class);


    private final VehicleRepository vehicleRepository;

    private final VehicleMapper vehicleMapper;

    private final VehicleOwnershipRepository vehicleOwnershipRepository;

    public VehicleQueryService(VehicleRepository vehicleRepository, VehicleMapper vehicleMapper,
                               VehicleOwnershipRepository vehicleOwnershipRepository) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
        this.vehicleOwnershipRepository = vehicleOwnershipRepository;
    }

    /**
     * Return a {@link List} of {@link VehicleDTO} which matches the criteria from the database
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VehicleDTO> findByCriteria(VehicleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Vehicle> specification = createSpecification(criteria);

        List<VehicleOwnership> ownershipsCurrentUser = new ArrayList<>();
        if (SecurityUtils.isAuthenticated()) {
            ownershipsCurrentUser.addAll(vehicleOwnershipRepository.findByOwnerIsCurrentUser());
        }

        return vehicleMapper.toDto(vehicleRepository.findAll(specification), ownershipsCurrentUser);
    }

    /**
     * Return a {@link Page} of {@link VehicleDTO} which matches the criteria from the database
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleDTO> findByCriteria(VehicleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Vehicle> specification = createSpecification(criteria);

        List<VehicleOwnership> ownershipsCurrentUser = new ArrayList<>();
        if (SecurityUtils.isAuthenticated()) {
            ownershipsCurrentUser.addAll(vehicleOwnershipRepository.findByOwnerIsCurrentUser());
        }

        final Page<Vehicle> result = vehicleRepository.findAll(specification, page);
        return result.map(entity -> vehicleMapper.toDto(entity, ownershipsCurrentUser));
    }

    /**
     * Function to convert VehicleCriteria to a {@link Specifications}
     */
    private Specifications<Vehicle> createSpecification(VehicleCriteria criteria) {
        Specifications<Vehicle> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Vehicle_.id));
            }
            if (criteria.getManufacturer() != null) {
                specification = specification.and(buildSpecification(criteria.getManufacturer(), Vehicle_.manufacturer));
            }
            if (criteria.getModel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModel(), Vehicle_.model));
            }
            if (criteria.getUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrl(), Vehicle_.url));
            }
            /*if (criteria.getOwnedByUser() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrl(), Vehicle_.url));
            }*/
        }
        return specification;
    }

}
