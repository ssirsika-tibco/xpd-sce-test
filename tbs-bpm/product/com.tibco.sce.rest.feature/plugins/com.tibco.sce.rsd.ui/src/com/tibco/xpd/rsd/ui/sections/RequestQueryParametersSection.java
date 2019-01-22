/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.ui.sections;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
import com.tibco.xpd.rsd.Method;
import com.tibco.xpd.rsd.Parameter;
import com.tibco.xpd.rsd.ParameterContainer;
import com.tibco.xpd.rsd.ParameterStyle;
import com.tibco.xpd.rsd.RsdPackage;
import com.tibco.xpd.rsd.ui.components.ParamsControl;
import com.tibco.xpd.rsd.ui.components.columns.ParamDataTypeColumn;
import com.tibco.xpd.rsd.ui.components.columns.ParamDefaultValueColumn;
import com.tibco.xpd.rsd.ui.components.columns.ParamMandatoryColumn;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Section for RSD's request parameters.
 * 
 * @author jarciuch
 * @since 2 Feb 2015
 */
public class RequestQueryParametersSection extends AbstractParametersSection {

    /**
     * Maps input to the request.
     */
    @Override
    protected EObject resollveInput(Object object) {
        if (object instanceof Method) {
            return ((Method) object).getRequest();
        }
        return super.resollveInput(object);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ParamsControl createParamsControl(Composite parent,
            XpdFormToolkit toolkit) {
        return new ParamsControl(groupControl, toolkit) {
            @Override
            protected void addColumns(ColumnViewer viewer) {
                super.addColumns(viewer); // adds name column
                new ParamDataTypeColumn(XpdResourcesPlugin.getDefault()
                        .getEditingDomain(), viewer);
                new ParamMandatoryColumn(XpdResourcesPlugin.getDefault()
                        .getEditingDomain(), viewer);
                new ParamDefaultValueColumn(XpdResourcesPlugin.getDefault()
                        .getEditingDomain(), viewer);
                setColumnProportions(new float[] { .4f, .2f, .2f, .2f });
            }

            /** {@inheritDoc} */
            @Override
            protected Set<EStructuralFeature> getMovableFeatures() {
                if (movableFeatures == null) {
                    movableFeatures = super.getMovableFeatures();
                    movableFeatures.add(RsdPackage.eINSTANCE
                            .getParameterContainer_Parameters());
                }
                return movableFeatures;
            }

            /**
             * Create the usual content and then add filter to the viewer.
             */
            @Override
            protected void createContents(Composite parent, XpdToolkit toolkit,
                    Object viewerInput) {
                super.createContents(parent, toolkit, viewerInput);
                getViewer().addFilter(new ViewerFilter() {
                    @Override
                    public boolean select(Viewer viewer, Object parentElement,
                            Object element) {
                        if (element instanceof Parameter) {
                            return ((Parameter) element).getStyle() == ParameterStyle.QUERY;
                        }
                        return false;
                    }
                });
            }

            /**
             * Adds style to the parameter.
             */
            @Override
            protected Parameter createNewParameter(
                    ParameterContainer paramContainer) {
                Parameter param = super.createNewParameter(paramContainer);
                param.setStyle(ParameterStyle.QUERY);
                return param;
            }
        };
    }
}
