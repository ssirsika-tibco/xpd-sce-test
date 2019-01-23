package com.tibco.xpd.bom.gen.biz;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple JavaBean that encapsulates the configuration for the generator (used
 * in the oAW workflow). All known parameters are defined as public constants.
 * 
 * @author Jan Arciuchiewicz
 * 
 */
public class BomGeneratorConfig {

    private final Map<String, String> parameters;

    private final Map<String, Object> externalSlots;

    public static final String PARAM_MODEL_FILE = "model.file"; //$NON-NLS-1$

    public static final String PARAM_HIBERNATE_PROPS_FILE =
            "hibernate.properties.file"; //$NON-NLS-1$

    public static final String PARAM_PROFILE_PERSISTENCE_FILE =
            "profile.persistence.file"; //$NON-NLS-1$

    public static final String PARAM_SRC_GEN_DIR = "outlet.src.dir"; //$NON-NLS-1$

    public static final String PARAM_RES_GEN_DIR = "outlet.res.dir"; //$NON-NLS-1$

    public static final String PARAM_OUTLET_RES_ONCE_DIR =
            "outlet.res.once.dir"; //$NON-NLS-1$

    public static final String PARAM_OUTLET_SRC_ONCE_DIR =
            "outlet.src.once.dir"; //$NON-NLS-1$

    public static final String JAR_FILE = "jar.file";

    public static final String OVERWRITE_STATE = "overwrite.state";
    
    public static final String RETAIN_PROJECT = "retain.project";

    /**
     * The constructor.
     */
    public BomGeneratorConfig() {
        parameters = new HashMap<String, String>();
        externalSlots = new HashMap<String, Object>();
    }

    /**
     * Set configuration parameter.
     */
    public void setParameter(String key, String value) {
        parameters.put(key, value);
    }

    /**
     * Returns map<String, String> containing parameters.
     * 
     * @return
     */
    public Map<String, String> getParametersMap() {
        return new HashMap<String, String>(parameters);
    }

    /**
     * Returns map<String, String> containing parameters.
     * 
     * @return
     */
    public Map<String, Object> getExternalSlotsMap() {
        return new HashMap<String, Object>(externalSlots);
    }

}
