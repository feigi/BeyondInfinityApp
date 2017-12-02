package org.beyond_infinity.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.beyond_infinity.app.service.VehicleOwnershipService;
import org.beyond_infinity.app.web.rest.errors.BadRequestAlertException;
import org.beyond_infinity.app.web.rest.util.HeaderUtil;
import org.beyond_infinity.app.web.rest.util.PaginationUtil;
import org.beyond_infinity.app.service.dto.VehicleOwnershipDTO;
import org.beyond_infinity.app.service.dto.VehicleOwnershipCriteria;
import org.beyond_infinity.app.service.VehicleOwnershipQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing VehicleOwnership.
 */
@RestController
@RequestMapping("/api")
public class VehicleOwnershipResource {

    private final Logger log = LoggerFactory.getLogger(VehicleOwnershipResource.class);

    private static final String ENTITY_NAME = "vehicleOwnership";

    private final VehicleOwnershipService vehicleOwnershipService;

    private final VehicleOwnershipQueryService vehicleOwnershipQueryService;

    public VehicleOwnershipResource(VehicleOwnershipService vehicleOwnershipService, VehicleOwnershipQueryService vehicleOwnershipQueryService) {
        this.vehicleOwnershipService = vehicleOwnershipService;
        this.vehicleOwnershipQueryService = vehicleOwnershipQueryService;
    }

    /**
     * POST  /vehicle-ownerships : Create a new vehicleOwnership.
     *
     * @param vehicleOwnershipDTO the vehicleOwnershipDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vehicleOwnershipDTO, or with status 400 (Bad Request) if the vehicleOwnership has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vehicle-ownerships")
    @Timed
    public ResponseEntity<VehicleOwnershipDTO> createVehicleOwnership(@Valid @RequestBody VehicleOwnershipDTO vehicleOwnershipDTO) throws URISyntaxException {
        log.debug("REST request to save VehicleOwnership : {}", vehicleOwnershipDTO);
        if (vehicleOwnershipDTO.getId() != null) {
            throw new BadRequestAlertException("A new vehicleOwnership cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehicleOwnershipDTO result = vehicleOwnershipService.save(vehicleOwnershipDTO);
        return ResponseEntity.created(new URI("/api/vehicle-ownerships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vehicle-ownerships : Updates an existing vehicleOwnership.
     *
     * @param vehicleOwnershipDTO the vehicleOwnershipDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vehicleOwnershipDTO,
     * or with status 400 (Bad Request) if the vehicleOwnershipDTO is not valid,
     * or with status 500 (Internal Server Error) if the vehicleOwnershipDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vehicle-ownerships")
    @Timed
    public ResponseEntity<VehicleOwnershipDTO> updateVehicleOwnership(@Valid @RequestBody VehicleOwnershipDTO vehicleOwnershipDTO) throws URISyntaxException {
        log.debug("REST request to update VehicleOwnership : {}", vehicleOwnershipDTO);
        if (vehicleOwnershipDTO.getId() == null) {
            return createVehicleOwnership(vehicleOwnershipDTO);
        }
        VehicleOwnershipDTO result = vehicleOwnershipService.save(vehicleOwnershipDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vehicleOwnershipDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vehicle-ownerships : get all the vehicleOwnerships.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of vehicleOwnerships in body
     */
    @GetMapping("/vehicle-ownerships")
    @Timed
    public ResponseEntity<List<VehicleOwnershipDTO>> getAllVehicleOwnerships(VehicleOwnershipCriteria criteria, Pageable pageable) {
        log.debug("REST request to get VehicleOwnerships by criteria: {}", criteria);
        Page<VehicleOwnershipDTO> page = vehicleOwnershipQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vehicle-ownerships");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /vehicle-ownerships/:id : get the "id" vehicleOwnership.
     *
     * @param id the id of the vehicleOwnershipDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vehicleOwnershipDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vehicle-ownerships/{id}")
    @Timed
    public ResponseEntity<VehicleOwnershipDTO> getVehicleOwnership(@PathVariable Long id) {
        log.debug("REST request to get VehicleOwnership : {}", id);
        VehicleOwnershipDTO vehicleOwnershipDTO = vehicleOwnershipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vehicleOwnershipDTO));
    }

    /**
     * DELETE  /vehicle-ownerships/:id : delete the "id" vehicleOwnership.
     *
     * @param id the id of the vehicleOwnershipDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vehicle-ownerships/{id}")
    @Timed
    public ResponseEntity<Void> deleteVehicleOwnership(@PathVariable Long id) {
        log.debug("REST request to delete VehicleOwnership : {}", id);
        vehicleOwnershipService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
