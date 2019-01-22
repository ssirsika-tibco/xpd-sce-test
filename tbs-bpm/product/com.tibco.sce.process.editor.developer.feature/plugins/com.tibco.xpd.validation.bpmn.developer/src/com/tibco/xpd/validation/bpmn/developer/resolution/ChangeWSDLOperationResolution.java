/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */

package com.tibco.xpd.validation.bpmn.developer.resolution;

import java.util.Collections;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.implementer.script.ActivityMessageProvider;
import com.tibco.xpd.implementer.script.ActivityMessageProviderFactory;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.indexer.IndexerItem;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.util.WsdlIndexerUtil;
import com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution;
import com.tibco.xpd.validation.resolutions.ResolutionException;
import com.tibco.xpd.wsdl.WsdlServiceKey;
import com.tibco.xpd.wsdl.ui.pickers.OperationPicker;
import com.tibco.xpd.wsdl.ui.pickers.OperationPicker.WsdlType;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author rsomayaj
 * 
 */
public class ChangeWSDLOperationResolution extends
        AbstractWorkingCopyResolution {

    /**
     * @see com.tibco.xpd.validation.resolutions.AbstractWorkingCopyResolution#getResolutionCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, org.eclipse.core.resources.IMarker)
     * 
     * @param editingDomain
     * @param target
     * @param marker
     * @return
     * @throws ResolutionException
     */
    @Override
    protected Command getResolutionCommand(EditingDomain editingDomain,
            EObject target, IMarker marker) throws ResolutionException {

        Command cmd = null;

        if (target instanceof Activity) {

            Activity activity = (Activity) target;
            ActivityMessageProvider activityMessage =
                    ActivityMessageProviderFactory.INSTANCE
                            .getMessageProvider(activity);

            Shell shell = Display.getDefault().getActiveShell();
            WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(activity);

            if (wc != null) {
                IProject project = WorkingCopyUtil.getProjectFor(activity); // wc.getEclipseResources().get(0).getProject();

                OperationPicker picker =
                        new OperationPicker(shell, WsdlType.STANDARD);

                WsdlServiceKey currentKey =
                        ProcessUIUtil.getSpecificWsdlServiceKey(activity);
                IndexerItem currentOpItem =
                        WsdlIndexerUtil.getOperationItem(project,
                                currentKey,
                                true,
                                true);
                picker.setInitialElementSelections(Collections
                        .singletonList(currentOpItem));

                if (picker.open() == OperationPicker.OK) {

                    String serviceName = picker.getServiceName();
                    String portTypeName = picker.getPortTypeName();
                    String portOperationName = picker.getOperationName();
                    String portName = picker.getPortName();
                    String operationName = picker.getOperationName();

                    WsdlServiceKey key =
                            new WsdlServiceKey(serviceName, portName,
                                    operationName, portTypeName,
                                    portOperationName,
                                    picker.getLocalFilePath());

                    String wsdlUrl = picker.getLocalFilePath();

                    boolean isRemote = false;

                    WebServiceOperation webServiceOperation =
                            activityMessage.getWebServiceOperation(activity);

                    if (null != webServiceOperation) {
                        String aliasId =
                                (String) Xpdl2ModelUtil
                                        .getOtherAttribute(webServiceOperation,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_Alias());
                        /*
                         * Ensure that when end point participant is set, the
                         * IsRemote flag is also set.
                         */
                        if (null != aliasId) {
                            isRemote = true;
                        }
                    }

                    String projectName = picker.getProjectName();
                    if (projectName != null) {

                        IProject opProject =
                                project.getWorkspace().getRoot()
                                        .getProject(projectName);

                        updateWsdlServiceKeyWithTransportName(opProject, key);

                        cmd =
                                activityMessage
                                        .getAssignWebServiceCommand(editingDomain,
                                                activity.getProcess(),
                                                activity,
                                                wsdlUrl,
                                                isRemote,
                                                key);

                        /*
                         * If the selected operation is from an external project
                         * then a project reference needs to be set up
                         */

                        if (!ProcessUIUtil.checkAndAddProjectReference(shell,
                                project,
                                opProject)) {
                            // User refused to set project reference so
                            // cannot set operation
                            cmd = null;

                        }
                    }
                }
            }

        }
        return cmd;
    }

    protected void updateWsdlServiceKeyWithTransportName(IProject project,
            WsdlServiceKey key) {
        if (project != null && key != null) {
            key.setTransportURI(WsdlIndexerUtil.getTransportUri(project,
                    key,
                    true,
                    true));
        }
    }

}
