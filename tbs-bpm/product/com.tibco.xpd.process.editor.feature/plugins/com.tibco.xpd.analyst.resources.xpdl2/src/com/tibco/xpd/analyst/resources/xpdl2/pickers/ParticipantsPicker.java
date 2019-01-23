/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.analyst.resources.xpdl2.pickers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import com.tibco.xpd.analyst.resources.xpdl2.internal.Messages;
import com.tibco.xpd.analyst.resources.xpdl2.projectexplorer.filters.EObjAssetGroupFilter;
import com.tibco.xpd.navigator.pickers.BaseObjectPicker;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.projectexplorer.viewerfilters.EObjectFilter;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * @author wzurek
 */
public class ParticipantsPicker extends BaseObjectPicker {

    private EObjectFilter processFilter;

    public ParticipantsPicker(Shell parent) {
        super(parent);
        setShellStyle(SWT.DIALOG_TRIM | SWT.RESIZE | SWT.APPLICATION_MODAL);
        Set<EClass> classes = new HashSet<EClass>();
        classes.add(Xpdl2Package.eINSTANCE.getPackage());
        classes.add(Xpdl2Package.eINSTANCE.getProcess());
        classes.add(Xpdl2Package.eINSTANCE.getParticipant());
        classes.add(Xpdl2Package.eINSTANCE.getFormalParameter());
        classes.add(Xpdl2Package.eINSTANCE.getDataField());
        addFilter(new EObjAssetGroupFilter(classes) {
            protected boolean extraValidation(EObject eObj) {
                if (eObj instanceof ProcessRelevantData) {
                    ProcessRelevantData data = (ProcessRelevantData) eObj;

                    // Only allow selection of performer data fields.
                    if (data.getDataType() instanceof BasicType
                            && BasicTypeType.PERFORMER_LITERAL
                                    .equals(((BasicType) data.getDataType())
                                            .getType())) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                return true;
            }
        });

        // Only show the given process (input) in the tree
        processFilter = new EObjectFilter();
        addFilter(processFilter);

        setTitle(Messages.ParticipantsPicker_TITLE);
        setMessage(Messages.ParticipantsPicker_MESSAGE);

        List<EClass> validClasses = new ArrayList<EClass>();
        validClasses.add(Xpdl2Package.eINSTANCE.getParticipant());
        validClasses.add(Xpdl2Package.eINSTANCE.getDataField());
        validClasses.add(Xpdl2Package.eINSTANCE.getFormalParameter());

        setValidator(new EClassStatusValidator(validClasses) {
            @Override
            protected boolean extraValidation(EObject eObj) {
                if (eObj instanceof Participant) {
                    return true;
                } else if (eObj instanceof ProcessRelevantData) {
                    ProcessRelevantData data = (ProcessRelevantData) eObj;

                    // Only allow selection of performer data fields.
                    if (data.getDataType() instanceof BasicType
                            && BasicTypeType.PERFORMER_LITERAL
                                    .equals(((BasicType) data.getDataType())
                                            .getType())) {
                        return true;
                    }
                }
                return false;
            }

        });

        setInvalidSelectionMessage(Messages.ParticipantsPicker_2);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.xpd.navigator.pickers.BaseObjectPicker#setInput(java.lang.Object)
     */
    public void setInput(Object input) {
        // if the input is process, change it to the xpdl resource
        IResource file = null;
        if (input instanceof Process) {
            Process process = (Process) input;
            processFilter.setObjectFilter(process);
            WorkingCopy xpdlWorkingCopy = WorkingCopyUtil
                    .getWorkingCopyFor(process);
            file = xpdlWorkingCopy.getEclipseResources().get(0);
        } else {
            processFilter.setObjectFilter(null);
        }
        super.setInput(file);
    }
}
