/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.mapper;

import java.util.Collection;

/**
 * This interface should be implemented by any class interested in receiving
 * notifications from the Mappper about mapping changes.
 * 
 * @author nwilson
 */
public interface IMappingListener {
    /**
     * @param mapping
     *            The added mapping.
     */
    void mappingAdded(Mapping mapping);

    /**
     * @param mapping
     *            The removed mapping.
     */
    void mappingRemoved(Mapping mapping);

    /**
     * @param changes
     *            The mapping changes.
     * @deprecated This method is never called by the Mapper.
     */
    @Deprecated
    void mappingChanged(Collection<MappingDelta> changes);
}
