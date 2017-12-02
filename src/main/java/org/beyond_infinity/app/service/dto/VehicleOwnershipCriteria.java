package org.beyond_infinity.app.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the VehicleOwnership entity. This class is used in VehicleOwnershipResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /vehicle-ownerships?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VehicleOwnershipCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LongFilter vehicleId;

    private LongFilter ownerId;

    public VehicleOwnershipCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(LongFilter vehicleId) {
        this.vehicleId = vehicleId;
    }

    public LongFilter getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(LongFilter ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "VehicleOwnershipCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (vehicleId != null ? "vehicleId=" + vehicleId + ", " : "") +
                (ownerId != null ? "ownerId=" + ownerId + ", " : "") +
            "}";
    }

}
