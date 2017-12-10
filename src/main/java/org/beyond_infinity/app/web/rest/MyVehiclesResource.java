package org.beyond_infinity.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.beyond_infinity.app.domain.User;
import org.beyond_infinity.app.repository.UserRepository;
import org.beyond_infinity.app.security.SecurityUtils;
import org.beyond_infinity.app.service.VehicleOwnershipService;
import org.beyond_infinity.app.service.dto.VehicleDTO;
import org.beyond_infinity.app.service.dto.VehicleOwnershipDTO;
import org.beyond_infinity.app.service.mapper.VehicleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.beyond_infinity.app.security.AuthoritiesConstants.USER;

/**
 * REST controller for managing VehicleOwnership of the currently logged in user.
 */
@RestController
@RequestMapping("/api")
public class MyVehiclesResource {

    private static final String ENTITY_NAME = "vehicleOwnership";

    private final Logger log = LoggerFactory.getLogger(MyVehiclesResource.class);

    private final VehicleOwnershipService vehicleOwnershipService;

    private final UserRepository userRepository;

    private final VehicleMapper vehicleMapper;

    public MyVehiclesResource(VehicleOwnershipService vehicleOwnershipService, UserRepository userRepository, VehicleMapper vehicleMapper) {
        this.vehicleOwnershipService = vehicleOwnershipService;
        this.userRepository = userRepository;
        this.vehicleMapper = vehicleMapper;
    }

    @PutMapping("/vehicles/mine")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Timed
    @Secured(USER)
    public void updateMyVehicles(@Valid @RequestBody List<VehicleDTO> vehicleDTOs) {
        log.debug("REST request to update user's Vehicles : {}", vehicleDTOs);

        User user = SecurityUtils.getCurrentUserLogin()
            .flatMap(userRepository::findOneByLogin).orElse(null);

        List<VehicleOwnershipDTO> vehicleOwnershipDTOs = vehicleDTOs.stream()
            .filter(VehicleDTO::isOwnedByUser)
            .map(vehicleDTO -> vehicleMapper.toVehicleOwnershipDto(vehicleDTO, user))
            .collect(toList());

        vehicleOwnershipService.updateMyVehicles(vehicleOwnershipDTOs);
    }
}
