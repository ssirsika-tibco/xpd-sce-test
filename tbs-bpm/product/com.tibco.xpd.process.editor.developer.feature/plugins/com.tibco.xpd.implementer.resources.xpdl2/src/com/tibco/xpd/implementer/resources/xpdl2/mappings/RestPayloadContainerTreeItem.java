/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.resources.xpdl2.mappings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.uml2.uml.Classifier;

import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.RestConceptPath;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.rest.schema.JsonSchemaUtil;
import com.tibco.xpd.rsd.PayloadReference;
import com.tibco.xpd.rsd.ui.components.JsonTypeReference;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Mapper tree item representing payload container.
 * 
 * @author jarciuch
 * @since 26 Mar 2015
 */
public abstract class RestPayloadContainerTreeItem extends RestMapperTreeItem {

    private final Activity activity;

    private final String label;

    private final MappingDirection direction;

    /**
     * 
     */
    public RestPayloadContainerTreeItem(Activity activity, String label,
            MappingDirection direction) {
        this.activity = activity;
        this.label = label;
        this.direction = direction;
    }

    protected abstract PayloadReference getPayloadReference(Activity activity);

    /**
     * {@inheritDoc}
     */
    @Override
    public RestMapperTreeItem getParent() {
        return null; // OR Activity
    }

    /**
	 * {@inheritDoc}
	 * 
	 * Sid ACE-8742 Change RSD content providers to cache their content for performance - now we create child content
	 */
    @Override
	public List< ? > createChildren()
	{
        if (activity != null) {
            List<Object> children = new ArrayList<>();
            PayloadReference payloadReference = getPayloadReference(activity);
            if (payloadReference != null) {
                if (JsonSchemaUtil.UNPROCESSED_TEXT_PAYLOAD_REFERENCE
                        .equals(payloadReference.getRef())) {
                    RestMapperTreeItem item =
                            new UnprocessedTextRestMapperTreeItem(this, activity);
                    children.add(item);
                } else {
                    Classifier jsonSchemaRoot =
                            JsonTypeReference
                                    .getJsonReference(payloadReference)
                                    .resolveReference(XpdResourcesPlugin
                                            .getDefault().getEditingDomain(),
                                            WorkingCopyUtil
                                                    .getProjectFor(activity));
                    ConceptPath conceptPath =
                            new RestConceptPath(
                                    RestMappingPrefix.PAYLOAD.getPrefix(),
                                    this, jsonSchemaRoot,
                                    payloadReference.isArray());
                    children.add(conceptPath);
                }
            }
            return children;
        }
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getText() {
        return label;
    }

    /**
	 * Sid ACE-8742 hasChildren() now handled in base class.
	 */

    /**
     * {@inheritDoc}
     */
    @Override
    public Activity getActivity() {
        return activity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result =
                prime * result + ((activity == null) ? 0 : activity.hashCode());
        result =
                prime * result
                        + ((direction == null) ? 0 : direction.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!getClass().equals(obj.getClass()))
            return false;
        RestPayloadContainerTreeItem other = (RestPayloadContainerTreeItem) obj;
        if (activity == null) {
            if (other.activity != null)
                return false;
        } else if (!activity.equals(other.activity))
            return false;
        if (direction != other.direction)
            return false;
        return true;
    }

}
