package org.beyond_infinity.app.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import org.beyond_infinity.app.domain.VehicleOwnership;
import org.beyond_infinity.app.domain.*; // for static metamodels
import org.beyond_infinity.app.repository.VehicleOwnershipRepository;
import org.beyond_infinity.app.service.dto.VehicleOwnershipCriteria;

import org.beyond_infinity.app.service.dto.VehicleOwnershipDTO;
import org.beyond_infinity.app.service.mapper.VehicleOwnershipMapper;

/**
 * Service for executing complex queries for VehicleOwnership entities in the database.
 * The main input is a {@link VehicleOwnershipCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VehicleOwnershipDTO} or a {@link Page} of {@link VehicleOwnershipDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VehicleOwnershipQueryService extends QueryService<VehicleOwnership> {

    private final Logger log = LoggerFactory.getLogger(VehicleOwnershipQueryService.class);


    private final VehicleOwnershipRepository vehicleOwnershipRepository;

    private final VehicleOwnershipMapper vehicleOwnershipMapper;

    public VehicleOwnershipQueryService(VehicleOwnershipRepository vehicleOwnershipRepository, VehicleOwnershipMapper vehicleOwnershipMapper) {
        this.vehicleOwnershipRepository = vehicleOwnershipRepository;
        this.vehicleOwnershipMapper = vehicleOwnershipMapper;
    }

    /**
     * Return a {@link List} of {@link VehicleOwnershipDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VehicleOwnershipDTO> findByCriteria(VehicleOwnershipCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<VehicleOwnership> specification = createSpecification(criteria);
        return vehicleOwnershipMapper.toDto(vehicleOwnershipRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VehicleOwnershipDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VehicleOwnershipDTO> findByCriteria(VehicleOwnershipCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<VehicleOwnership> specification = createSpecification(criteria);
        final Page<VehicleOwnership> result = vehicleOwnershipRepository.findAll(specification, page);
        return result.map(vehicleOwnershipMapper::toDto);
    }

    /**
     * Function to convert VehicleOwnershipCriteria to a {@link Specifications}
     */
    private Specifications<VehicleOwnership> createSpecification(VehicleOwnershipCriteria criteria) {
        Specifications<VehicleOwnership> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), VehicleOwnership_.id));
            }
            if (criteria.getVehicleId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getVehicleId(), VehicleOwnership_.vehicle, Vehicle_.id));
            }
            if (criteria.getOwnerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getOwnerId(), VehicleOwnership_.owner, User_.id));
            }
        }
        return specification;
    }

}
