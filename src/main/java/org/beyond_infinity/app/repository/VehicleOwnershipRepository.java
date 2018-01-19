package org.beyond_infinity.app.repository;

import org.beyond_infinity.app.domain.User;
import org.beyond_infinity.app.domain.VehicleOwnership;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the VehicleOwnership entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleOwnershipRepository extends JpaRepository<VehicleOwnership, Long>, JpaSpecificationExecutor<VehicleOwnership> {

    @Query("select vo from VehicleOwnership vo where vo.owner.login = ?#{principal.username}")
    List<VehicleOwnership> findByOwnerIsCurrentUser();

    List<VehicleOwnership> findByOwner(User owner);

    @Query("delete from VehicleOwnership vo where vo.owner.id = :userId and vo.vehicle.id = :vehicleId")
    VehicleOwnership delete(Long userId, Long vehicleId);
}
