/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.mapper;


/**
 * Sub-class of {@link Mapper} {@link IMappingListener} that causes Mapper to
 * switch behaviour when mapping is moved.
 * <p>
 * With {@link IMappingListener} the listener will receive a remove then add
 * notication.
 * <p>
 * Implementers of {@link IMoveableMappingListener} will be asked to move the
 * mapping (given the old Mapping and the new Mapping.
 * 
 * @author aallway
 * @since 26 Jun 2015
 */
public interface IMoveableMappingListener extends IMappingListener {

    /**
     * The source or target of mapping has been moved
     * 
     * @param before
     *            The mapping as it was before it was moved.
     * @param after
     *            The mapping as is now.
     */
    public void mappingMoved(Mapping before, Mapping after);

}
