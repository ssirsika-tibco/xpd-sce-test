/*
 * Copyright (c) TIBCO Software Inc 2004, 2011. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.utils;

import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.ModeType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;

/**
 * Object that represents the interface data associated using activity's
 * interface settings.
 * <p>
 * Note that the {@link #equals(Object)} can also check equality against a
 * {@link ProcessRelevantData} and {@link #hashCode()} uses the wrapped
 * {@link ProcessRelevantData} (therefore a set.contains(ProcessReleavandData or
 * ActivityInterfaceData) should wirh equally.
 * 
 * @author aallway
 * @since 17 Aug 2011
 */
public class ActivityInterfaceData {
    private ProcessRelevantData data;

    private ModeType mode;

    private boolean mandatory;

    private boolean explicitAssociation;

    /**
     * Construct an explicit activity interface data item from
     * {@link AssociatedParameter}
     * 
     * @param associatedParameter
     * @param data
     */
    public ActivityInterfaceData(AssociatedParameter associatedParameter,
            ProcessRelevantData data) {
        super();
        this.data = data;
        this.mandatory = associatedParameter.isMandatory();
        this.mode = associatedParameter.getMode();
        if (this.mode == null) {
            this.mode = ModeType.INOUT_LITERAL;
        }
        this.explicitAssociation = true;
    }

    /**
     * Construct an implicit interface data association from field/param.
     * 
     * @param data
     */
    public ActivityInterfaceData(ProcessRelevantData data) {
        super();
        this.data = data;

        if (data instanceof FormalParameter) {
            /* Can extrapolate the mode and mandatory from formal params. */
            this.mode = ((FormalParameter) data).getMode();
            this.mandatory = ((FormalParameter) data).isRequired();

        } else {
            /* For data fields default to non-mandatory in/out. */
            this.mandatory = false;
            this.mode = ModeType.INOUT_LITERAL;
        }

        if (this.mode == null) {
            this.mode = ModeType.INOUT_LITERAL;
        }

        this.explicitAssociation = false;
    }

    /**
     * Construct an implicit interface data association wherein we can specify
     * the mode of the process data.
     * 
     * @param data
     *            the process data
     * @param mode
     *            Mode type(IN/OUT/IN-OUT)
     * @param mandatory
     *            true if mandatory else false
     */
    public ActivityInterfaceData(ProcessRelevantData data, ModeType mode,
            boolean mandatory) {
        super();
        this.data = data;
        this.mode = mode;
        this.mandatory = mandatory;
    }

    /**
     * @return the mandatory
     */
    public boolean isMandatory() {
        return mandatory;
    }

    /**
     * @return the mode
     */
    public ModeType getMode() {
        return mode;
    }

    /**
     * @return the data
     */
    public ProcessRelevantData getData() {
        return data;
    }

    /**
     * @return The name of the data associated in the activity interface.
     */
    public String getId() {
        return data.getId();
    }

    /**
     * @return The name of the data associated in the activity interface.
     */
    public String getName() {
        return data.getName();
    }

    /**
     * @return <code>true</code> if the data is explicitly associated in the
     *         interface data (ratehr than being implicitly associated).
     */
    public boolean isExplicitAssociation() {
        return explicitAssociation;
    }

    /**
     * @return <code>true</code> if the data is explicitly associated in the
     *         interface data (ratehr than being implicitly associated).
     */
    public boolean isImplicitAssociation() {
        return !explicitAssociation;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof ActivityInterfaceData) {
            return getId().equals(((ActivityInterfaceData) obj).getId());

        } else if (obj instanceof ProcessRelevantData) {
            return data.equals(obj);
        }
        return false;
    }

    /**
     * Perform a strict comparison.
     * 
     * @param obj
     * @return true associated data is exactly the same (including mandatory and
     *         mode state whether it is explicitly associated)
     */
    public boolean equalsStrict(Object obj) {
        if (obj == this) {
            return true;

        } else if (obj instanceof ActivityInterfaceData) {
            ActivityInterfaceData otherData = (ActivityInterfaceData) obj;

            if (data.equals(otherData.data) && mode.equals(otherData.mode)
                    && mandatory == otherData.mandatory
                    && explicitAssociation == otherData.explicitAssociation) {
                return true;
            }
        }

        return false;
    }

    /**
     * @see java.lang.Object#hashCode()
     * 
     * @return
     */
    @Override
    public int hashCode() {
        return data.hashCode();
    }

    /**
     * @see java.lang.Object#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        return String
                .format("ActivityInterfaceData %s: Mandatory=%s, Mode=%s, isExplicitAssociation=%s", //$NON-NLS-1$
                        getName(),
                        isMandatory(),
                        getMode(),
                        isExplicitAssociation());
    }
}
