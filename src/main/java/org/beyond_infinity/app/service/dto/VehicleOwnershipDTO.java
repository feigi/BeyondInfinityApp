package org.beyond_infinity.app.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the VehicleOwnership entity.
 */
public class VehicleOwnershipDTO implements Serializable {

    private Long id;

    private Long vehicleId;

    private String vehicleFullName;

    private Long ownerId;

    private String ownerLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleFullName() {
        return vehicleFullName;
    }

    public void setVehicleFullName(String vehicleFullName) {
        this.vehicleFullName = vehicleFullName;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long userId) {
        this.ownerId = userId;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String userLogin) {
        this.ownerLogin = userLogin;
    }

    public boolean equalOwnership(VehicleOwnershipDTO vehicleOwnershipDTO) {
        if (this == vehicleOwnershipDTO) {
            return true;
        }
        if (vehicleOwnershipDTO == null || getClass() != vehicleOwnershipDTO.getClass()) {
            return false;
        }
        if (vehicleOwnershipDTO.getVehicleId() == null || getVehicleId() == null) {
            return false;
        }
        if (vehicleOwnershipDTO.getOwnerId() == null || getOwnerId() == null) {
            return false;
        }
        return Objects.equals(getVehicleId(), vehicleOwnershipDTO.getVehicleId())
            && Objects.equals(getOwnerId(), vehicleOwnershipDTO.getOwnerId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VehicleOwnershipDTO vehicleOwnershipDTO = (VehicleOwnershipDTO) o;
        if (vehicleOwnershipDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicleOwnershipDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VehicleOwnershipDTO{" +
            "id=" + getId() +
            "}";
    }
}
