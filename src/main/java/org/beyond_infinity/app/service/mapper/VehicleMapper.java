package org.beyond_infinity.app.service.mapper;

import org.beyond_infinity.app.domain.*;
import org.beyond_infinity.app.service.dto.VehicleDTO;

import org.beyond_infinity.app.service.dto.VehicleOwnershipDTO;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for the entity Vehicle and its DTO VehicleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VehicleMapper extends EntityMapper<VehicleDTO, Vehicle> {


    @Named("primary")
    @Mapping(expression = "java( toUserIsOwner(vehicle, ownerships) )", target = "ownedByUser")
    VehicleDTO toDto(Vehicle vehicle, @Context List<VehicleOwnership> ownerships);

    @IterableMapping(qualifiedByName = "primary")
    List<VehicleDTO> toDto(List<Vehicle> vehicles, @Context List<VehicleOwnership> ownerships);

    default VehicleOwnershipDTO toVehicleOwnershipDto(VehicleDTO vehicleDto, User user) {
        if (!vehicleDto.isOwnedByUser()) {
            return null;
        }
        VehicleOwnershipDTO vehicleOwnership = new VehicleOwnershipDTO();
        vehicleOwnership.setOwnerId(user.getId());
        vehicleOwnership.setVehicleId(vehicleDto.getId());

        return vehicleOwnership;
    }

    default boolean toUserIsOwner(Vehicle vehicle, List<VehicleOwnership> ownerships) {
        if (vehicle == null || ownerships == null) {
            return false;
        }
        return ownerships.stream()
            .map(VehicleOwnership::getVehicle)
            .anyMatch(aVehicle -> aVehicle.equals(vehicle));
    }

    default Vehicle fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vehicle vehicle = new Vehicle();
        vehicle.setId(id);
        return vehicle;
    }
}
