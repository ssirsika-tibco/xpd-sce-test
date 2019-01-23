/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.ui.sections;

import java.util.Set;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.components.XpdToolkit;
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
 * Section for a Method's header parameter section. Subclasses can extend to
 * resolve their specific inputs.
 * 
 * @author sajain
 * @since Aug 26, 2015
 */
public class MethodHeaderParametersSection extends AbstractParametersSection {

    /**
     * Creates a customised parameter control.
     * 
     * @see com.tibco.xpd.rsd.ui.sections.AbstractParametersSection#createParamsControl(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     */
    @Override
    protected ParamsControl createParamsControl(Composite parent,
            XpdFormToolkit toolkit) {

        return new ParamsControl(groupControl, toolkit) {

            @Override
            protected void addColumns(ColumnViewer viewer) {

                super.addColumns(viewer); // adds the name column

                new ParamDataTypeColumn(XpdResourcesPlugin.getDefault()
                        .getEditingDomain(), viewer);

                new ParamMandatoryColumn(XpdResourcesPlugin.getDefault()
                        .getEditingDomain(), viewer);

                new ParamDefaultValueColumn(XpdResourcesPlugin.getDefault()
                        .getEditingDomain(), viewer);

                setColumnProportions(new float[] { .4f, .2f, .2f, .2f });
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

                            return ((Parameter) element).getStyle() == ParameterStyle.HEADER;
                        }

                        return false;
                    }
                });
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
             * Adds style to the parameter.
             */
            @Override
            protected Parameter createNewParameter(
                    ParameterContainer paramContainer) {

                Parameter param = super.createNewParameter(paramContainer);

                param.setStyle(ParameterStyle.HEADER);

                return param;
            }
        };
    }

}
