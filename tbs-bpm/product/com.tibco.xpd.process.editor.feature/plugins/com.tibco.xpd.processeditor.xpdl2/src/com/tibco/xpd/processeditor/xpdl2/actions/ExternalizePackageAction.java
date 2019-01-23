/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.actions;

import java.io.PrintWriter;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.actions.WorkspaceAction;

import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.ArtifactType;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DocumentRoot;
import com.tibco.xpd.xpdl2.FormalParameter;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author rsomayaj
 * 
 */
public class ExternalizePackageAction extends WorkspaceAction {

    private IFile xpdlFile;

    /**
     * @param shell
     * @param text
     */
    public ExternalizePackageAction() {
        super(Display.getCurrent().getActiveShell(), "Externalize Package"); //$NON-NLS-1$

        // FIXME ImageDescriptor needs to be set.
    }

    /**
     * @see org.eclipse.ui.actions.WorkspaceAction#getOperationMessage()
     * 
     * @return
     */
    @Override
    protected String getOperationMessage() {
        return "Externalize"; //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.ui.actions.WorkspaceAction#invokeOperation(org.eclipse.core.resources.IResource,
     *      org.eclipse.core.runtime.IProgressMonitor)
     * 
     * @param resource
     * @param monitor
     * @throws CoreException
     */
    @Override
    protected void invokeOperation(IResource resource, IProgressMonitor monitor)
            throws CoreException {
        // Do nothing.
    }

    /**
     * @see org.eclipse.ui.actions.WorkspaceAction#run()
     * 
     */
    @Override
    public void run() {
        super.run();

        URI createFileURI =
                URI.createFileURI(xpdlFile.getLocationURI().getPath());

        ResourceSet rset = new ResourceSetImpl();
        Resource res = rset.getResource(createFileURI, true);
        EObject rootElement = res.getContents().get(0);
        if (rootElement instanceof DocumentRoot) {

            Package pkg = (Package) ((DocumentRoot) rootElement).getPackage();
            PrintWriter pw =
                    ExternalizeActionUtil.getPropertiesWriter(rootElement);
            externalize(pkg, pw);
            pw.close();
        }
    }

    /**
     * @see org.eclipse.ui.actions.WorkspaceAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
     * 
     * @param selection
     * @return
     */
    @Override
    protected boolean updateSelection(IStructuredSelection selection) {
        if (super.updateSelection(selection)) {
            xpdlFile = null;

            if (selection.size() == 1) {
                Object sel = selection.getFirstElement();

                if (sel instanceof IFile) {
                    xpdlFile = (IFile) sel;
                    return true;
                }
            }
            return true;
        }

        return false;
    }

    private void externalize(NamedElement namedElement, PrintWriter pw) {
        switch (namedElement.eClass().getClassifierID()) {
        case Xpdl2Package.PACKAGE:
            write(pw, namedElement);
            Package pkg = (Package) namedElement;
            List<Pool> pools = pkg.getPools();
            for (Pool pool : pools) {
                externalize(pool, pw);
            }
            List<Artifact> artifacts = pkg.getArtifacts();
            for (Artifact artifact : artifacts) {
                externalize(artifact, pw);
            }

            List<Association> associations = pkg.getAssociations();
            for (Association association : associations) {
                externalize(association, pw);
            }

            List<DataField> fields = pkg.getDataFields();
            for (DataField field : fields) {
                externalize(field, pw);
            }

            List<MessageFlow> messageFlows = pkg.getMessageFlows();
            for (MessageFlow msgFlow : messageFlows) {
                externalize(msgFlow, pw);
            }

            List<Participant> participantslist = pkg.getParticipants();
            for (Participant participant : participantslist) {
                externalize(participant, pw);
            }

            List<Process> processes = pkg.getProcesses();
            for (Process proc : processes) {
                externalize(proc, pw);
            }

            List<TypeDeclaration> typeDeclarations = pkg.getTypeDeclarations();
            for (TypeDeclaration typeDec : typeDeclarations) {
                externalize(typeDec, pw);
            }

            break;
        case Xpdl2Package.PROCESS:
            write(pw, namedElement);
            Process process = (Process) namedElement;
            List<Activity> activities = process.getActivities();
            for (Activity act : activities) {
                externalize(act, pw);
            }
            List<ActivitySet> activitySets = process.getActivitySets();
            for (ActivitySet actSet : activitySets) {
                externalize(actSet, pw);
            }

            List<DataField> dataFields = process.getDataFields();
            for (DataField dataField : dataFields) {
                externalize(dataField, pw);
            }

            List<FormalParameter> formalParameters =
                    process.getFormalParameters();
            for (FormalParameter param : formalParameters) {
                externalize(param, pw);
            }

            List<Participant> participants = process.getParticipants();
            for (Participant participant : participants) {
                externalize(participant, pw);
            }

            List<Transition> transitions = process.getTransitions();
            for (Transition transition : transitions) {
                externalize(transition, pw);
            }

            break;
        case Xpdl2Package.ACTIVITY:
            write(pw, namedElement);
            break;
        case Xpdl2Package.ACTIVITY_SET:
            ActivitySet actSet = (ActivitySet) namedElement;
            for (Activity act : actSet.getActivities()) {
                externalize(act, pw);
            }
            for (Transition transition : actSet.getTransitions()) {
                externalize(transition, pw);
            }
            break;
        case Xpdl2Package.ASSOCIATION:
            write(pw, namedElement);
            break;
        case Xpdl2Package.CATEGORY:
        case Xpdl2Package.EXTERNAL_PACKAGE:
            break;
        case Xpdl2Package.ARTIFACT:
            Artifact artifact = (Artifact) namedElement;
            if (ArtifactType.ANNOTATION_LITERAL.equals(artifact
                    .getArtifactType())) {
                write(pw, namedElement, artifact.getTextAnnotation());
            } else {
                write(pw, namedElement);
            }
            break;
        case Xpdl2Package.DATA_OBJECT:
        case Xpdl2Package.LANE:
            write(pw, namedElement);
            break;
        case Xpdl2Package.MESSAGE:
            break;
        case Xpdl2Package.OBJECT:
            break;
        case Xpdl2Package.PARTICIPANT:
            write(pw, namedElement);
            break;
        case Xpdl2Package.POOL:
            write(pw, namedElement);
            List<Lane> lanes = ((Pool) namedElement).getLanes();
            for (Lane lane : lanes) {
                externalize(lane, pw);
            }
            break;
        case Xpdl2Package.PROCESS_RELEVANT_DATA:
            write(pw, namedElement);
            break;
        case Xpdl2Package.FORMAL_PARAMETER:
            write(pw, namedElement);
            break;
        case Xpdl2Package.DATA_FIELD:
            write(pw, namedElement);
            break;
        case Xpdl2Package.RULE:
            break;
        case Xpdl2Package.TRANSITION:
            write(pw, namedElement);
            break;
        case Xpdl2Package.TYPE_DECLARATION:
            write(pw, namedElement);
            break;

        }
    }

    /**
     * @param namedElement
     * @param string
     */
    private void write(PrintWriter pw, NamedElement namedElement) {
        if (namedElement.getName() != null
                && namedElement.getName().length() != 0) {
            boolean stripLeadingSpace =
                    namedElement instanceof ProcessRelevantData
                            || namedElement instanceof TypeDeclaration;
            pw.println(String
                    .format("_label_%1$s_%2$s%3$s%4$s", namedElement.getId()//$NON-NLS-1$
                            ,
                            NameUtil.getInternalName(namedElement.getName(),
                                    stripLeadingSpace),
                            ExternalizeActionUtil.PROPERTIES_SEPARATOR,
                            Xpdl2ModelUtil.getOtherAttribute(namedElement,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_DisplayName())));
        }
    }

    /**
     * @param element
     * @param string
     */
    private void write(PrintWriter pw, NamedElement namedElement, String element) {
        if (element != null) {
            if (namedElement.getName() != null) {
                boolean stripLeadingSpace =
                        namedElement instanceof ProcessRelevantData
                                || namedElement instanceof TypeDeclaration;
                pw
                        .println(String
                                .format("_label_%1$s_%2$s%3$s%4$s", namedElement.getId()//$NON-NLS-1$
                                        ,
                                        NameUtil.getInternalName(namedElement
                                                .getName(), stripLeadingSpace),
                                        ExternalizeActionUtil.PROPERTIES_SEPARATOR,
                                        element));
            } else {
                pw.println(String
                        .format("_label_%1$s%2$s%3$s", namedElement.getId()//$NON-NLS-1$
                                ,
                                ExternalizeActionUtil.PROPERTIES_SEPARATOR,
                                element.replace("\r\n", ""))); //$NON-NLS-1$ //$NON-NLS-2$
            }
        }
    }

}
