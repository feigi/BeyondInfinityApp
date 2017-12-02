package org.beyond_infinity.app.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.stream.Stream;

/**
 * The Manufacturer enumeration.
 */
public enum Manufacturer {
    AEGIS_DYNAMICS("Aegis Dynamics"),
    ANVIL_AEROSPACE("Anvil Aerospace"),
    AOPOA("Aopoa"),
    ARGO("ARGO Astronautics"),
    BANU("Banu"),
    CONSOLIDATED_OUTLAND("Consolidated Outland"),
    CRUSADER_INDUSTRIES("Crusader Industries"),
    DRAKE_INTERPLANETARY("Drake Interplanetary"),
    ESPERIA("Esperia"),
    KRUGER_INTERGALACTIC("Kruger Intergalactic"),
    MISC("M.I.S.C."),
    ORIGIN_JUMPWORKS("Origin Jumpworks"),
    RSI("Roberts Space Industries"),
    TUMBRIL("Tumbril"),
    VANDUUL("Vanduul");

    private String name;

    Manufacturer(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public Manufacturer fromName(String name) {
        return Stream.of(Manufacturer.values())
            .filter(manufacturer -> manufacturer.name.equalsIgnoreCase(name))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }
}
