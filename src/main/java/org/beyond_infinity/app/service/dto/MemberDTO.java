package org.beyond_infinity.app.service.dto;

import java.util.List;
import java.util.Objects;

public class MemberDTO {

    private Long id;

    private String name;

    private List<VehicleDTO> vehicles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<VehicleDTO> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleDTO> vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberDTO memberDTO = (MemberDTO) o;
        return Objects.equals(id, memberDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "MemberDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", vehicles=" + vehicles +
            '}';
    }
}
