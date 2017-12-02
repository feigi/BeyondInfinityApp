package org.beyond_infinity.app.service.dto;

import java.io.Serializable;
import org.beyond_infinity.app.domain.enumeration.Manufacturer;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Vehicle entity. This class is used in VehicleResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /vehicles?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VehicleCriteria implements Serializable {
    /**
     * Class for filtering Manufacturer
     */
    public static class ManufacturerFilter extends Filter<Manufacturer> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private ManufacturerFilter manufacturer;

    private StringFilter model;

    private StringFilter url;

    public VehicleCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ManufacturerFilter getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerFilter manufacturer) {
        this.manufacturer = manufacturer;
    }

    public StringFilter getModel() {
        return model;
    }

    public void setModel(StringFilter model) {
        this.model = model;
    }

    public StringFilter getUrl() {
        return url;
    }

    public void setUrl(StringFilter url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "VehicleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (manufacturer != null ? "manufacturer=" + manufacturer + ", " : "") +
                (model != null ? "model=" + model + ", " : "") +
                (url != null ? "url=" + url + ", " : "") +
            "}";
    }

}
