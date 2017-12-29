package org.beyond_infinity.app.service.mapper;

import org.beyond_infinity.app.domain.User;
import org.beyond_infinity.app.domain.VehicleOwnership;
import org.beyond_infinity.app.service.dto.MemberDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {VehicleOwnershipMapper.class})
public interface MemberMapper {

    @Mapping(source = "user.login", target = "name")
    @Mapping(source = "ownedVehicles", target = "vehicles")
    MemberDTO toDto(User user, List<VehicleOwnership> ownedVehicles);
}
