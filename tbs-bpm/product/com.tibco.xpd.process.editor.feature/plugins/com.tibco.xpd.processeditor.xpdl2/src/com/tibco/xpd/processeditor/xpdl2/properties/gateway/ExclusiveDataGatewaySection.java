/**
 * Copyright (c) TIBCO Software Inc 2004-2008. All rights reserved.
 */
package com.tibco.xpd.processeditor.xpdl2.properties.gateway;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IPluginContribution;

import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * ExclusiveDataGatewaySection
 * 
 * @author aallway
 */
public class ExclusiveDataGatewaySection extends AbstractFilteredTransactionalSection
        implements IPluginContribution {

    private Button markerVisible;

    private long start = 0; 

    /**
     * @param class1
     */
    public ExclusiveDataGatewaySection() {
        super(Xpdl2Package.eINSTANCE.getActivity());
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite section = toolkit.createComposite(parent);
        GridLayout layout = new GridLayout(1, false);
//        layout.marginWidth = 0;
//        layout.marginHeight = 0;
        section.setLayout(layout);

        markerVisible =
                toolkit.createButton(section,
                        Messages.ExclusiveDataGatewaySection_ShowMarker_label,
                        SWT.CHECK,
                        "gateway.marker.visible"); //$NON-NLS-1$
        markerVisible
                .setToolTipText(Messages.ExclusiveDataGatewaySection_XMarkerOptional_tooltip);
        manageControl(markerVisible);
        markerVisible.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        return section;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        if (obj == markerVisible) {
            Activity act = getActivity();
            if (act != null) {
                Route route = act.getRoute();
                if (route != null) {
                    CompoundCommand cmd =
                            new CompoundCommand(
                                    Messages.ExclusiveDataGatewaySection_SetShowMarker_menu);
                    cmd.append(SetCommand.create(getEditingDomain(),
                            route,
                            Xpdl2Package.eINSTANCE.getRoute_MarkerVisible(),
                            markerVisible.getSelection()));

                    return cmd;
                }
            }
        }
        return null;
    }

    @Override
    protected void doRefresh() {
        if (markerVisible != null && !markerVisible.isDisposed()
                && getActivity() != null) {
            Activity act = getActivity();

            Route route = act.getRoute();
            if (route != null) {
                markerVisible.setSelection(route.isMarkerVisible());
            }
        }
    }

    public String getLocalId() {
        return "analyst.implSection"; //$NON-NLS-1$
    }

    public String getPluginId() {
        return Xpdl2ProcessEditorPlugin.ID;
    }

    /**
     * Get xpdl2 Activity from the input
     * 
     * @return <code>Activity</code> if input is valid, <b>null</b> otherwise.
     */
    private Activity getActivity() {
        Object o = getInput();
        if (o instanceof Activity) {
            return (Activity) o;
        }
        return null;
    }

}
