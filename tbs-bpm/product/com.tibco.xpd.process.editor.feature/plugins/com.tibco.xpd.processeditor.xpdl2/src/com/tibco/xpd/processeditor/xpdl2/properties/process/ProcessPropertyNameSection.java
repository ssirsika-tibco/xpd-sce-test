/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties.process;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.tibco.xpd.analyst.resources.xpdl2.properties.general.BaseTypeSection.CaseClassPicker;
import com.tibco.xpd.analyst.resources.xpdl2.propertytesters.XpdlFileContentPropertyTester;
import com.tibco.xpd.analyst.resources.xpdl2.utils.ProcessUIUtil;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processeditor.xpdl2.properties.NamedElementSection;
import com.tibco.xpd.processeditor.xpdl2.wizards.NewBusinessProcessWizard;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.CaseService;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.ExternalReference;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.RecordType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Section that displays the named element section for all process types and
 * additional case class picker control for a case service process type
 * 
 * @author NWilson
 * 
 */
public class ProcessPropertyNameSection extends NamedElementSection {

    CaseClassPicker caseClassPickerCtrl;

    /**
     * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
     * 
     * @param toTest
     * @return
     */
    @Override
    public boolean select(Object toTest) {

        Object item = getBaseSelectObject(toTest);

        if (item instanceof Process) {

            Process process = (Process) item;
            if ((XpdlFileContentPropertyTester.isXpdlFileContent(process))
                    || NewBusinessProcessWizard.NEW_BUSINESSPROCESS_PROCESS_ID
                            .equals(process.getId())) {

                return true;
            } else if (Xpdl2ModelUtil.isPageflow(process)) {

                return true;
            } else if (Xpdl2ModelUtil.isServiceProcess(process)) {

                return true;
            }
        }
        return false;
    }

    @Override
    protected String getDuplicateNameMessage(EObject duplicate) {

        return Messages.ProcessPropertySection_ProcessNameError_shortmsg;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.NamedElementSection#doCreateControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param toolkit
     * @return
     */
    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {

        EObject eObject = getInput();

        if (eObject instanceof Process) {

            boolean isCaseService =
                    Xpdl2ModelUtil.isCaseService((Process) eObject);
            if (isCaseService) {

                return caseServiceControls(parent, toolkit);
            }
        }

        return super.doCreateControls(parent, toolkit);
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.NamedElementSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {

        if (caseClassPickerCtrl != null) {

            EObject object = getInput();
            if (object instanceof Process) {

                Process process = (Process) object;
                CaseService caseService =
                        (CaseService) Xpdl2ModelUtil.getOtherElement(process,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_CaseService());
                if (null != caseService) {

                    ExternalReference externalRef =
                            caseService.getCaseClassType();
                    if (externalRef != null) {

                        caseClassPickerCtrl.setValue(ProcessUIUtil
                                .xpdl2RefToComplexDataTypeRef(externalRef));
                    }
                } else {

                    caseClassPickerCtrl.setValue(null);
                }
            }
        }
        super.doRefresh();
    }

    /**
     * @param parent
     * @param toolkit
     * @return named element section and case class picker control
     */
    private Control caseServiceControls(Composite parent, XpdFormToolkit toolkit) {

        Control controls = super.doCreateControls(parent, toolkit);
        controls.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
                | GridData.VERTICAL_ALIGN_CENTER));

        Composite composite = new Composite(controls.getParent(), SWT.NULL);
        composite.setLayout(new GridLayout(3, false));

        final CLabel labelCaseRefType =
                toolkit.createCLabel(composite,
                        Messages.CaseServicePropertySection_labelCaseClass);
        labelCaseRefType.setLayoutData(new GridData(
                GridData.VERTICAL_ALIGN_CENTER));

        caseClassPickerCtrl =
                new CaseClassPicker(composite, SWT.NONE, toolkit,
                        getEditingDomain(), getSectionContainerType()) {

                    /**
                     * @see com.tibco.xpd.analyst.resources.xpdl2.properties.general.BaseTypeSection.BOMTypePicker#getProject()
                     * 
                     * @return
                     */
                    @Override
                    protected IProject getProject() {

                        IProject project =
                                WorkingCopyUtil
                                        .getProjectFor(getInputContainer());
                        return project;
                    }

                    @Override
                    protected Command setCaseRefTypeCmd(RecordType recordType) {

                        CompoundCommand cmpCommand = new CompoundCommand();

                        Process proc = (Process) getInput();

                        ExternalReference externalRef =
                                EcoreUtil.copy(recordType.getMember().get(0)
                                        .getExternalReference());
                        CaseService caseService =
                                XpdExtensionFactory.eINSTANCE
                                        .createCaseService();
                        caseService.setCaseClassType(externalRef);
                        cmpCommand.append(Xpdl2ModelUtil
                                .getSetOtherElementCommand(getEditingDomain(),
                                        proc,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_CaseService(),
                                        caseService));

                        cmpCommand
                                .setLabel(Messages.CaseServicePropertySection_setCaseClassCmdLabel);
                        return cmpCommand;
                    }

                };
        return composite;
    }
}
