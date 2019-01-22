/*
 * Copyright (c) TIBCO Software Inc. 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.om.resources.ui.properties.sections.internal.general.groups;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.jface.viewers.TableViewer;

import com.tibco.xpd.om.core.om.OrgElement;
import com.tibco.xpd.om.core.om.OrgModel;
import com.tibco.xpd.om.core.om.provider.LocationsTransientItemProvider;
import com.tibco.xpd.om.core.om.provider.ResourcesTransientItemProvider;
import com.tibco.xpd.om.resources.ui.commonpicker.OMTypeQuery;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns.AllocationMethodColumn;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns.LabelColumn;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns.NameColumn;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns.PurposeColumn;
import com.tibco.xpd.om.resources.ui.properties.sections.internal.controls.table.columns.TypeColumn;
import com.tibco.xpd.ui.util.CapabilityUtil;

/**
 * General tab section for {@link OrgElement} groups. This will display a table
 * with the name, start date and end date columns to add the children of this
 * group.
 * <p>
 * If the following parameter is specified with the class then the Types column
 * will also be added with the appropriate picker:
 * <ul>
 * <li><b>location</b> - for locations group</li>
 * <li><b>organization</b> - for organizations group</li>
 * <li><b>resource</b> - for resources group</li>
 * </ul>
 * </p>
 * 
 * @author njpatel
 * 
 */
public class OrgElementGroupSection extends AbstractGroupSection implements
        IExecutableExtension, IFilter {

    private static final String PARAM_HEADING = "heading"; //$NON-NLS-1$

    private static final String PARAM_TYPE = "type"; //$NON-NLS-1$

    private static final String ORGANIZATION = "organization"; //$NON-NLS-1$

    private static final String RESOURCE = "resource"; //$NON-NLS-1$

    private static final String LOCATION = "location"; //$NON-NLS-1$

    private String type = null;

    @Override
    protected void addColumns(TableViewer viewer) {
        new LabelColumn(getEditingDomain(), viewer);
        if (CapabilityUtil.isDeveloperActivityEnabled()) {
            new NameColumn(getEditingDomain(), viewer);
        }
        String modelType = getType();
        if (modelType != null) {
            new TypeColumn(getEditingDomain(), viewer, modelType);
        }
        new PurposeColumn(getEditingDomain(), viewer);

        if (type != null) {
            if (type.equals(LOCATION) || type.equals(ORGANIZATION)) {
                // Add Allocation method column
                new AllocationMethodColumn(getEditingDomain(), viewer);
            }
        }
    }

    @Override
    protected boolean isRequiredChildDescriptor(CommandParameter descriptor) {
        return descriptor != null
                && descriptor.getValue() instanceof OrgElement;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org
     * .eclipse.core.runtime.IConfigurationElement, java.lang.String,
     * java.lang.Object)
     */
    @Override
    public void setInitializationData(IConfigurationElement config,
            String propertyName, Object data) throws CoreException {
        Map<?, ?> paramValues = null;
        if (data instanceof Map<?, ?>) {
            paramValues = (Map<?, ?>) data;
        } else if (data instanceof String) {
            String param = ((String) data).trim();

            if (param.startsWith("-")) { //$NON-NLS-1$
                Map<String, String> theMap = new HashMap<String, String>();
                paramValues = theMap;
                String params[] = param.split("\\s*-\\s*"); //$NON-NLS-1$
                for (String pair : params) {
                    int idx = pair.indexOf(' ');
                    if (idx > 0) {
                        String parameter = pair.substring(0, idx);
                        String value = pair.substring(idx + 1).trim();

                        if (value.length() > 0) {
                            theMap.put(parameter, value);
                        }
                    }
                }
            } else {
                type = param;
            }
        }

        if (paramValues != null) {
            for (Entry<?, ?> entry : paramValues.entrySet()) {
                if (PARAM_HEADING.equalsIgnoreCase(entry.getKey().toString())) {
                    setHeading(entry.getValue().toString());
                } else if (PARAM_TYPE.equalsIgnoreCase(entry.getKey()
                        .toString())) {
                    type = entry.getValue().toString();
                }
            }
        }
    }

    /**
     * Get the OM picker content type based on the parameter set for this class.
     */
    private String getType() {
        if (type != null) {
            if (type.equals(LOCATION)) { //$NON-NLS-1$
                return OMTypeQuery.TYPE_ID_LOCATION_TYPE;
            }

            if (type.equals(ORGANIZATION)) { //$NON-NLS-1$
                return OMTypeQuery.TYPE_ID_ORGANIZATION_TYPE;
            }

            if (type.equals(RESOURCE)) { //$NON-NLS-1$
                return OMTypeQuery.TYPE_ID_RESOURCE_TYPE;
            }
        }
        return null;
    }

    @Override
    protected IEditingDomainItemProvider getItemProvider(EditPart editPart) {

        EObject input = resollveInput(editPart);
        if (input instanceof OrgModel) {
            IEditingDomainItemProvider provider = getItemProvider(input);
            if (provider != null) {
                Collection<?> children = provider.getChildren(input);
                for (Object child : children) {
                    if (type.equals(LOCATION)) {
                        if (child instanceof LocationsTransientItemProvider) {
                            return (IEditingDomainItemProvider) child;
                        }
                    }
                    if (type.equals(RESOURCE)) {
                        if (child instanceof ResourcesTransientItemProvider) {
                            return (IEditingDomainItemProvider) child;
                        }
                    }
                }
            }
        }
        return null;

    }

    @Override
    public boolean select(Object toTest) {
        EObject eo = resollveInput(toTest);
        return eo instanceof OrgModel;

    }

}
