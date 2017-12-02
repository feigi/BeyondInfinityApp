package org.beyond_infinity.app.domain.enumeration;

/**
 * The Manufacturer enumeration.
 */
public enum Manufacturer {
    AEGIS_DYNAMICS("Aegis Dynamics"),
    ANVIL_AEROSPACE("Anvil Aerospace"),
    ARGO("Argo"),
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

    public String getName() {
        return name;
    }
}
