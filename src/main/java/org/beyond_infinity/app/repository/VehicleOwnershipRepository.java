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

    @Query("select vehicle_ownership from VehicleOwnership vehicle_ownership where vehicle_ownership.owner.login = ?#{principal.username}")
    List<VehicleOwnership> findByOwnerIsCurrentUser();

    List<VehicleOwnership> findByOwner(User owner);
}
