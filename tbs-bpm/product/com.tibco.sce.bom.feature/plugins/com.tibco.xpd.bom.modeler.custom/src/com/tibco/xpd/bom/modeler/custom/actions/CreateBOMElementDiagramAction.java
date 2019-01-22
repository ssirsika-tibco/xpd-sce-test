/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.parts.IDiagramGraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest.ViewAndElementDescriptor;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.modeler.custom.internal.Messages;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.ClassEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.EnumerationEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.EnumerationLiteralEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.OperationEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PackageEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PrimitiveTypeEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.PropertyEditPart;
import com.tibco.xpd.bom.modeler.diagram.part.BOMDiagramEditorPlugin;
import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;
import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.BOMImages;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;

/**
 * @author rgreen
 * 
 */
public class CreateBOMElementDiagramAction extends DiagramAction {

    public CreateBOMElementDiagramAction(IWorkbenchPage workbenchPage) {
        super(workbenchPage);
    }

    public CreateBOMElementDiagramAction(IWorkbenchPage workbenchPage,
            String actionId, ImageDescriptor imageDescriptor) {
        super(workbenchPage);
        setId(actionId);

        Image img = null;
        if (actionId.equals(BOMActionIds.ACTION_ADD_CLASS)) {
            setText(Messages.CreateBOMElementDiagramAction_Class_label);
            img =
                    Activator.getDefault().getImageRegistry()
                            .get(BOMImages.CLASS);
        }

        if (actionId.equals(BOMActionIds.ACTION_ADD_PACKAGE)) {
            setText(Messages.CreateBOMElementDiagramAction_Package_label);
            img =
                    Activator.getDefault().getImageRegistry()
                            .get(BOMImages.PACKAGE);
        }

        if (actionId.equals(BOMActionIds.ACTION_ADD_PRIMITIVE_TYPE)) {
            setText(Messages.CreateBOMElementDiagramAction_PrimType_label);
            img =
                    Activator.getDefault().getImageRegistry()
                            .get(BOMImages.PRIMITIVE_TYPE);
        }

        if (actionId.equals(BOMActionIds.ACTION_ADD_ENUMERATION)) {
            setText(Messages.CreateBOMElementDiagramAction_Enumeration_label);
            img =
                    Activator.getDefault().getImageRegistry()
                            .get(BOMImages.ENUMERATION);
        }

        if (actionId.equals(BOMActionIds.ACTION_ADD_ATTRIBUTE)) {
            setText(Messages.CreateBOMElementDiagramAction_Attribute_label);
            img =
                    Activator.getDefault().getImageRegistry()
                            .get(BOMImages.PROPERTY);
        }

        if (actionId.equals(BOMActionIds.ACTION_ADD_OPERATION)) {
            setText(Messages.CreateBOMElementDiagramAction_Operation_label);
            img =
                    Activator.getDefault().getImageRegistry()
                            .get(BOMImages.OPERATION);
        }

        if (actionId.equals(BOMActionIds.ACTION_ADD_ENUMERATION_LIT)) {
            setText(Messages.CreateBOMElementDiagramAction_EnumLit_label);
            img =
                    Activator.getDefault().getImageRegistry()
                            .get(BOMImages.ENUMLIT);
        }

        if (actionId.equals(BOMActionIds.ACTION_ADD_CASE_IDENTIFIER)) {
            setText(Messages.CaseIdentifierSection_label);
            img =
                    Activator.getDefault().getImageRegistry()
                            .get(BOMImages.AUTO_CASE_IDENTIFIER);
        }

        if (actionId.equals(BOMActionIds.ACTION_ADD_CASE_CLASS)) {
            setText(Messages.CaseClass_label);
            img =
                    Activator.getDefault().getImageRegistry()
                            .get(BOMImages.CASE_CLASS);
        }

        if (actionId.equals(BOMActionIds.ACTION_ADD_GLOBAL_CLASS)) {
            setText(Messages.GlobalClass_label);
            img =
                    Activator.getDefault().getImageRegistry()
                            .get(BOMImages.GLOBAL_CLASS);
        }

        if (actionId.equals(BOMActionIds.ACTION_ADD_CASE_STATE)) {
            setText(Messages.CaseState_label);
            img =
                    Activator.getDefault().getImageRegistry()
                            .get(BOMImages.CASE_STATE);
        }

        if (img != null) {
            setImageDescriptor(ImageDescriptor.createFromImage(img));
        }
    }

    @Override
    protected Command getCommand(Request request) {

        /*
         * Reset the target request so it gets recreated for the next request.
         * This is necessary as the target request gets cached and there have
         * been occasions where the request's target edit part is pointing to a
         * previous model being edited.
         */
        clearTargetRequest();

        if (getId().equals(BOMActionIds.ACTION_ADD_PACKAGE)) {
            DiagramEditPart diagramEditPart = getDiagramEditPart();
            Diagram diagramView = diagramEditPart.getDiagramView();
            if (BomUIUtil.isUserDiagram(diagramView)) {
                return UnexecutableCommand.INSTANCE;
            }
        }
        return super.getCommand(request);

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction#createTargetRequest
     * ()
     */
    @Override
    protected Request createTargetRequest() {
        CreateViewAndElementRequest req = null;
        ViewAndElementDescriptor desc = null;

        // This method is overidden so that we can step in and create a
        // CreateViewAndElementRequest.
        if (getId() == BOMActionIds.ACTION_ADD_PACKAGE) {
            desc =
                    new ViewAndElementDescriptor(
                            new CreateElementRequestAdapter(
                                    new CreateElementRequest(
                                            UMLElementTypes.Package_1001)),
                            Node.class,
                            String.valueOf(PackageEditPart.VISUAL_ID),
                            BOMDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
        }

        if (getId() == BOMActionIds.ACTION_ADD_CLASS) {
            desc =
                    new ViewAndElementDescriptor(
                            new CreateElementRequestAdapter(
                                    new CreateElementRequest(
                                            UMLElementTypes.Class_1002)),
                            Node.class,
                            String.valueOf(ClassEditPart.VISUAL_ID),
                            BOMDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
        }

        if (getId() == BOMActionIds.ACTION_ADD_PRIMITIVE_TYPE) {
            desc =
                    new ViewAndElementDescriptor(
                            new CreateElementRequestAdapter(
                                    new CreateElementRequest(
                                            UMLElementTypes.PrimitiveType_1003)),
                            Node.class, String
                                    .valueOf(PrimitiveTypeEditPart.VISUAL_ID),
                            BOMDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
        }

        if (getId() == BOMActionIds.ACTION_ADD_ENUMERATION) {
            desc =
                    new ViewAndElementDescriptor(
                            new CreateElementRequestAdapter(
                                    new CreateElementRequest(
                                            UMLElementTypes.Enumeration_1004)),
                            Node.class,
                            String.valueOf(EnumerationEditPart.VISUAL_ID),
                            BOMDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
        }

        if (getId() == BOMActionIds.ACTION_ADD_ATTRIBUTE) {
            desc =
                    new ViewAndElementDescriptor(
                            new CreateElementRequestAdapter(
                                    new CreateElementRequest(
                                            UMLElementTypes.Property_2001)),
                            Node.class,
                            String.valueOf(PropertyEditPart.VISUAL_ID),
                            BOMDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
        }

        if (getId() == BOMActionIds.ACTION_ADD_OPERATION) {
            desc =
                    new ViewAndElementDescriptor(
                            new CreateElementRequestAdapter(
                                    new CreateElementRequest(
                                            UMLElementTypes.Operation_2002)),
                            Node.class,
                            String.valueOf(OperationEditPart.VISUAL_ID),
                            BOMDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
        }

        if (getId() == BOMActionIds.ACTION_ADD_ENUMERATION_LIT) {
            desc =
                    new ViewAndElementDescriptor(
                            new CreateElementRequestAdapter(
                                    new CreateElementRequest(
                                            UMLElementTypes.EnumerationLiteral_2003)),
                            Node.class,
                            String.valueOf(EnumerationLiteralEditPart.VISUAL_ID),
                            BOMDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
        }

        if (getId() == BOMActionIds.ACTION_ADD_CASE_IDENTIFIER) {
            // Need to create a standard property/attribute, but also want to
            // set the stereo type to the auto Case Identifier so that the
            // correct item is created
            CreateElementRequest elementRequest =
                    new CreateElementRequest(UMLElementTypes.Property_2001);
            Stereotype autoCIDStereo =
                    GlobalDataProfileManager.getInstance()
                            .getStereotype(StereotypeKind.AUTO_CASE_IDENTIFIER);

            elementRequest
                    .setParameter(BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.STEREOTYPE,
                            autoCIDStereo);
            desc =
                    new ViewAndElementDescriptor(
                            new CreateElementRequestAdapter(elementRequest),
                            Node.class,
                            String.valueOf(PropertyEditPart.VISUAL_ID),
                            BOMDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
        }

        if (getId() == BOMActionIds.ACTION_ADD_CASE_CLASS) {
            // Need to create a standard class, but also want to
            // set the stereo type to the Case Class so that the
            // correct item is created
            CreateElementRequest elementRequest =
                    new CreateElementRequest(UMLElementTypes.Class_1002);
            Stereotype caseStereo =
                    GlobalDataProfileManager.getInstance()
                            .getStereotype(StereotypeKind.CASE);

            elementRequest
                    .setParameter(BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.STEREOTYPE,
                            caseStereo);
            desc =
                    new ViewAndElementDescriptor(
                            new CreateElementRequestAdapter(elementRequest),
                            Node.class,
                            String.valueOf(ClassEditPart.VISUAL_ID),
                            BOMDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
        }

        if (getId() == BOMActionIds.ACTION_ADD_GLOBAL_CLASS) {
            // Need to create a standard class, but also want to
            // set the stereo type to the Global Class so that the
            // correct item is created
            CreateElementRequest elementRequest =
                    new CreateElementRequest(UMLElementTypes.Class_1002);
            Stereotype caseStereo =
                    GlobalDataProfileManager.getInstance()
                            .getStereotype(StereotypeKind.GLOBAL);

            elementRequest
                    .setParameter(BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.STEREOTYPE,
                            caseStereo);
            desc =
                    new ViewAndElementDescriptor(
                            new CreateElementRequestAdapter(elementRequest),
                            Node.class,
                            String.valueOf(ClassEditPart.VISUAL_ID),
                            BOMDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
        }

        if (getId() == BOMActionIds.ACTION_ADD_CASE_STATE) {
            // Need to create a standard property/attribute, but also want to
            // set the stereo type to the Case State so that the
            // correct item is created
            CreateElementRequest elementRequest =
                    new CreateElementRequest(UMLElementTypes.Property_2001);
            Stereotype caseStateStereo =
                    GlobalDataProfileManager.getInstance()
                            .getStereotype(StereotypeKind.CASE_STATE);

            elementRequest
                    .setParameter(BOMResourcesPlugin.CREATE_ELEMENT_REQUEST_PARAM.STEREOTYPE,
                            caseStateStereo);
            desc =
                    new ViewAndElementDescriptor(
                            new CreateElementRequestAdapter(elementRequest),
                            Node.class,
                            String.valueOf(PropertyEditPart.VISUAL_ID),
                            BOMDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
        }

        if (desc != null) {
            req = new CreateViewAndElementRequest(desc);
            req.setLocation(getMouseLocation());
        }

        return req;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction#isSelectionListener
     * ()
     */
    @Override
    protected boolean isSelectionListener() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gmf.runtime.diagram.ui.actions.DiagramAction#doRun(org.eclipse
     * .core.runtime.IProgressMonitor)
     */
    @Override
    protected void doRun(IProgressMonitor progressMonitor) {
        super.doRun(progressMonitor);
        selectAddedObject();
    }

    /**
     * Selects the newly added shape view(s) by default.
     */
    protected void selectAddedObject() {
        Object result = ((CreateRequest) getTargetRequest()).getNewObject();
        if (!(result instanceof Collection)) {
            return;
        }
        final List<Object> editparts = new ArrayList<Object>(1);

        IDiagramGraphicalViewer viewer = getDiagramGraphicalViewer();
        if (viewer == null) {
            return;
        }

        Map<?, ?> editpartRegistry = viewer.getEditPartRegistry();
        for (Iterator<?> iter = ((Collection<?>) result).iterator(); iter.hasNext();) {
            Object viewAdaptable = iter.next();
            if (viewAdaptable instanceof IAdaptable) {
                Object editPart =
                        editpartRegistry.get(((IAdaptable) viewAdaptable)
                                .getAdapter(View.class));
                if (editPart != null)
                    editparts.add(editPart);
            }
        }

        if (!editparts.isEmpty()) {
            viewer.setSelection(new StructuredSelection(editparts));

            // automatically put the first shape into edit-mode
            Display.getCurrent().asyncExec(new Runnable() {

                public void run() {
                    EditPart editPart = (EditPart) editparts.get(0);
                    editPart.performRequest(new Request(
                            RequestConstants.REQ_DIRECT_EDIT));
                }
            });
        }
    }
}
