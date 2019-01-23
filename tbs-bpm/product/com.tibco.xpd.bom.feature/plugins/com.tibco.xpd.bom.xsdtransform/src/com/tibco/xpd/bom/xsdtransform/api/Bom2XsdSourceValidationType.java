package com.tibco.xpd.bom.xsdtransform.api;

/**
 * Type of validation required on source boms during transformation.
 * 
 * @author aallway
 * @since 6 Apr 2011
 */
public enum Bom2XsdSourceValidationType {
    /** The caller has already validated, no further validation required. */
    NONE,

    /**
     * Base the 'is valid' on the result of the previous validation build
     * (i.e. look at existing problem markers).
     */
    USE_MARKERS_FROM_PREVIOUS_BUILD,

    /**
     * Perform a live validation on the source bom (this should be used for
     * manual exports etc as the problem markers may not exist when
     * Build-Automatically is switched off)
     */
    LIVE_VALIDATION
}