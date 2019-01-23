/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rest.datamapper;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;

import com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.UnprocessedTextRestMapperTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMapperTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestMapperTreeItemFactory;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestParamTreeItem;
import com.tibco.xpd.implementer.resources.xpdl2.mappings.RestPayloadContainerTreeItem;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.processeditor.xpdl2.properties.RestConceptPath;
import com.tibco.xpd.rsd.Parameter;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Mapping rule info content provider for validation of REST mapping content.
 * 
 * @author jarciuch
 * @since 16 Apr 2015
 */
public class RestDataMapperInfoProvider extends AbstractDataMapperInfoProvider {

    private MappingDirection direction;

    /**
     * @param direction
     *            The mapping direction.
     * @param contentProvider
     *            The content provider.
     * @param labelProvider
     *            The label provider.
     * @param caching
     *            true to enable caching, can be turned on for rules.
     */
    public RestDataMapperInfoProvider(MappingDirection direction,
            ITreeContentProvider contentProvider, ILabelProvider labelProvider,
            boolean caching) {
        super(contentProvider, labelProvider, caching);
        this.direction = direction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getObjectPath(Object o) {
        if (o instanceof RestParamTreeItem) {
            return ((RestParamTreeItem) o).getPath();
        } else if (o instanceof ConceptPath) {
            return ((ConceptPath) o).getPath();
        } else if (o instanceof ScriptInformation) {
            return ((ScriptInformation) o).getName();
        } else if (o instanceof UnprocessedTextRestMapperTreeItem) {
            return ((UnprocessedTextRestMapperTreeItem) o).getPath();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getObjectPathDescription(Object o) {
        if (o instanceof RestMapperTreeItem) {
            RestMapperTreeItem item = (RestMapperTreeItem) o;
            RestMapperTreeItem parent = item.getParent();
            if (parent != null) {
                return getObjectPathDescription(parent) + "." + item.getText(); //$NON-NLS-1$
            } else {
                return item.getText();
            }
        } else if (o instanceof ConceptPath) {
            ConceptPath item = (ConceptPath) o;
            ConceptPath parent = item.getParent();
            if (parent != null) {
                return getObjectPathDescription(parent) + "." + item.getName(); //$NON-NLS-1$
            } else {
                return item.getName();
            }
        } else if (o instanceof ScriptInformation) {
            return ((ScriptInformation) o).getName();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isReadOnlyTarget(Object targetObjectInTree) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isMultiInstance(Object objectInTree) {
        if (objectInTree instanceof ConceptPath) {
            ConceptPath cp = (ConceptPath) objectInTree;
            return cp.isArray();
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMinimumInstances(Object objectInTree) {
        if (objectInTree instanceof RestPayloadContainerTreeItem) {
            RestPayloadContainerTreeItem container =
                    (RestPayloadContainerTreeItem) objectInTree;
            if (container.hasChildren()) {
                return 1;
            }
        } else if (objectInTree instanceof RestParamTreeItem) {
            Parameter param = ((RestParamTreeItem) objectInTree).getParam();
            if (param != null) {
                return param.isMandatory() && param.getDefaultValue() == null ? 1
                        : 0;
            }
        } else if (objectInTree instanceof ConceptPath) {
            ConceptPath cp = (ConceptPath) objectInTree;
            Object item = cp.getItem();
            if (item instanceof Property) {
                Property prop = (Property) item;
                return prop.getLower();
            } else if (item instanceof RestPayloadContainerTreeItem) {
                RestPayloadContainerTreeItem container =
                        (RestPayloadContainerTreeItem) item;
                if (container.hasChildren()) {
                    return 1;
                }
            }
        }
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaximumInstances(Object objectInTree) {
        if (objectInTree instanceof ConceptPath) {
            ConceptPath cp = (ConceptPath) objectInTree;
            if (cp.isArray()) {
                return -1;
            }
        }
        return 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getInstanceIndex(Object objectInTree) {
        // no Instance index specified
        return -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSimpleTypeContent(Object objectInTree) {
        if (objectInTree instanceof RestParamTreeItem
                || objectInTree instanceof UnprocessedTextRestMapperTreeItem) {
            return true;
        } else if (objectInTree instanceof ConceptPath) {
            ConceptPath cp = (ConceptPath) objectInTree;
            Object item = cp.getItem();
            if (item instanceof Property) {
                return ((Property) item).getType() instanceof PrimitiveType;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNullSimpleContentAllowed(Object objectInTree) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isArtificialObject(Object objectInTree) {
        if (objectInTree instanceof RestParamTreeItem
                || objectInTree instanceof ConceptPath
                || objectInTree instanceof ScriptInformation
                || objectInTree instanceof UnprocessedTextRestMapperTreeItem) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isChoiceObject(Object objectFromMappingOrContent) {
        return false;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider#getObjectForPath(java.lang.Object,
     *      java.lang.Object)
     * 
     * @param objectPath
     * @param mapperInput
     * @return
     */
    @Override
    public Object getObjectForPath(String objectPath, Object mapperInput) {
        Object o = null;
        if (mapperInput instanceof Activity) {
            Activity activity = (Activity) mapperInput;
            o =
                    RestMapperTreeItemFactory.getInstance()
                            .resolveParameter(activity, direction, objectPath);
        }
        return o;
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider#getObjectName(java.lang.Object)
     * 
     * @param object
     * @return
     */
    @Override
    public String getObjectName(Object object) {
        if (object instanceof RestConceptPath) {
            return ((RestConceptPath) object).getPath();
        }
        if (object instanceof ConceptPath) {
            return ((ConceptPath) object).getName();
        }
        return getLabelProvider().getText(object);
    }

    /**
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider#getObjectType(java.lang.Object)
     * 
     * @param object
     * @return
     */
    @Override
    public String getObjectType(Object object) {
        if (object instanceof ConceptPath) {
            ConceptPath cp = (ConceptPath) object;
            return cp.getType().toString();
        }
        return null;
    }

}
