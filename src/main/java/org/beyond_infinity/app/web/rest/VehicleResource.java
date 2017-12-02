package org.beyond_infinity.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.beyond_infinity.app.service.VehicleService;
import org.beyond_infinity.app.web.rest.errors.BadRequestAlertException;
import org.beyond_infinity.app.web.rest.util.HeaderUtil;
import org.beyond_infinity.app.web.rest.util.PaginationUtil;
import org.beyond_infinity.app.service.dto.VehicleDTO;
import org.beyond_infinity.app.service.dto.VehicleCriteria;
import org.beyond_infinity.app.service.VehicleQueryService;
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
 * REST controller for managing Vehicle.
 */
@RestController
@RequestMapping("/api")
public class VehicleResource {

    private final Logger log = LoggerFactory.getLogger(VehicleResource.class);

    private static final String ENTITY_NAME = "vehicle";

    private final VehicleService vehicleService;

    private final VehicleQueryService vehicleQueryService;

    public VehicleResource(VehicleService vehicleService, VehicleQueryService vehicleQueryService) {
        this.vehicleService = vehicleService;
        this.vehicleQueryService = vehicleQueryService;
    }

    /**
     * POST  /vehicles : Create a new vehicle.
     *
     * @param vehicleDTO the vehicleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vehicleDTO, or with status 400 (Bad Request) if the vehicle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vehicles")
    @Timed
    public ResponseEntity<VehicleDTO> createVehicle(@Valid @RequestBody VehicleDTO vehicleDTO) throws URISyntaxException {
        log.debug("REST request to save Vehicle : {}", vehicleDTO);
        if (vehicleDTO.getId() != null) {
            throw new BadRequestAlertException("A new vehicle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VehicleDTO result = vehicleService.save(vehicleDTO);
        return ResponseEntity.created(new URI("/api/vehicles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vehicles : Updates an existing vehicle.
     *
     * @param vehicleDTO the vehicleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vehicleDTO,
     * or with status 400 (Bad Request) if the vehicleDTO is not valid,
     * or with status 500 (Internal Server Error) if the vehicleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vehicles")
    @Timed
    public ResponseEntity<VehicleDTO> updateVehicle(@Valid @RequestBody VehicleDTO vehicleDTO) throws URISyntaxException {
        log.debug("REST request to update Vehicle : {}", vehicleDTO);
        if (vehicleDTO.getId() == null) {
            return createVehicle(vehicleDTO);
        }
        VehicleDTO result = vehicleService.save(vehicleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vehicleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vehicles : get all the vehicles.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of vehicles in body
     */
    @GetMapping("/vehicles")
    @Timed
    public ResponseEntity<List<VehicleDTO>> getAllVehicles(VehicleCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Vehicles by criteria: {}", criteria);
        Page<VehicleDTO> page = vehicleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vehicles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /vehicles/:id : get the "id" vehicle.
     *
     * @param id the id of the vehicleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vehicleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vehicles/{id}")
    @Timed
    public ResponseEntity<VehicleDTO> getVehicle(@PathVariable Long id) {
        log.debug("REST request to get Vehicle : {}", id);
        VehicleDTO vehicleDTO = vehicleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vehicleDTO));
    }

    /**
     * DELETE  /vehicles/:id : delete the "id" vehicle.
     *
     * @param id the id of the vehicleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vehicles/{id}")
    @Timed
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        log.debug("REST request to delete Vehicle : {}", id);
        vehicleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
