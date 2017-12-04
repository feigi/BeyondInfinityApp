package org.beyond_infinity.app.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.beyond_infinity.app.domain.enumeration.Manufacturer;

/**
 * A DTO for the Vehicle entity.
 */
public class VehicleDTO implements Serializable {

    private Long id;

    @NotNull
    private Manufacturer manufacturer;

    @NotNull
    @Size(min = 1)
    private String model;

    @NotNull
    @Size(min = 20)
    private String url;

    private String fullName;

    private boolean ownedByUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isOwnedByUser() {
        return ownedByUser;
    }

    public void setOwnedByUser(boolean ownedByUser) {
        this.ownedByUser = ownedByUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VehicleDTO vehicleDTO = (VehicleDTO) o;
        if(vehicleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vehicleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VehicleDTO{" +
            "id=" + getId() +
            ", manufacturer='" + getManufacturer() + "'" +
            ", model='" + getModel() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
