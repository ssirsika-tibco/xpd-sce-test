/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.ui.sections;

import java.util.Set;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.swt.widgets.Composite;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.rsd.Parameter;
import com.tibco.xpd.rsd.ParameterContainer;
import com.tibco.xpd.rsd.ParameterStyle;
import com.tibco.xpd.rsd.RsdPackage;
import com.tibco.xpd.rsd.ui.components.ParamsControl;
import com.tibco.xpd.rsd.ui.components.columns.ParamDataTypeColumn;
import com.tibco.xpd.rsd.ui.components.columns.ParamDefaultValueColumn;
import com.tibco.xpd.rsd.ui.internal.Messages;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * Section for RSD's resource path parameters.
 *
 * @author jarciuch
 * @since 2 Feb 2015
 */
public class PathParametersSection extends AbstractParametersSection {

    public PathParametersSection() {
        setTableLabel(Messages.PathParametersSection_PathParams_label);
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
                new ParamDefaultValueColumn(XpdResourcesPlugin.getDefault()
                        .getEditingDomain(), viewer);
                setColumnProportions(new float[] { .40f, .2f, .2f });
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
                param.setStyle(ParameterStyle.PATH);
                param.setMandatory(true);
                return param;
            }
        };
    }

}
