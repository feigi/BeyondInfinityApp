package org.beyond_infinity.app.service.mapper;

import org.beyond_infinity.app.domain.*;
import org.beyond_infinity.app.service.dto.VehicleOwnershipDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VehicleOwnership and its DTO VehicleOwnershipDTO.
 */
@Mapper(componentModel = "spring", uses = {VehicleMapper.class, UserMapper.class})
public interface VehicleOwnershipMapper extends EntityMapper<VehicleOwnershipDTO, VehicleOwnership> {

    @Mapping(source = "vehicle.id", target = "vehicleId")
    @Mapping(source = "vehicle.fullName", target = "vehicleFullName")
    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.login", target = "ownerLogin")
    VehicleOwnershipDTO toDto(VehicleOwnership vehicleOwnership);

    @Mapping(source = "vehicleId", target = "vehicle")
    @Mapping(source = "ownerId", target = "owner")
    VehicleOwnership toEntity(VehicleOwnershipDTO vehicleOwnershipDTO);

    default VehicleOwnership fromId(Long id) {
        if (id == null) {
            return null;
        }
        VehicleOwnership vehicleOwnership = new VehicleOwnership();
        vehicleOwnership.setId(id);
        return vehicleOwnership;
    }
}
