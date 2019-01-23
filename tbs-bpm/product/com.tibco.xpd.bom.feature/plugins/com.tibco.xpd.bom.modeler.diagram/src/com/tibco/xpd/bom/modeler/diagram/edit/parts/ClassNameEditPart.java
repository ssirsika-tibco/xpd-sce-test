/*
 * Copyright (c) TIBCO Software Inc 2007. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.diagram.edit.parts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.NotificationFilter;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListener;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.gef.AccessibleEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.handles.NonResizableHandleKit;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserOptions;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserService;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramColorRegistry;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.diagram.ui.tools.TextDirectEditManager;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser;
import org.eclipse.gmf.runtime.notation.FontStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Stereotype;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager;
import com.tibco.xpd.bom.globaldata.resources.GlobalDataProfileManager.StereotypeKind;
import com.tibco.xpd.bom.modeler.diagram.edit.parts.custom.BOMStereoAwareCompartmentEditPart;
import com.tibco.xpd.bom.modeler.diagram.edit.policies.BOMLabelDirectEditPolicy;
import com.tibco.xpd.bom.modeler.diagram.edit.policies.UMLTextSelectionEditPolicy;
import com.tibco.xpd.bom.modeler.diagram.part.BOMDiagramEditorPlugin;
import com.tibco.xpd.bom.modeler.diagram.providers.UMLElementTypes;
import com.tibco.xpd.bom.modeler.diagram.providers.UMLParserProvider;
import com.tibco.xpd.bom.resources.firstclassprofiles.FirstClassProfileManager;
import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * @generated
 */
public class ClassNameEditPart extends BOMStereoAwareCompartmentEditPart
        implements ITextAwareEditPart {

    /**
     * @generated
     */
    public static final int VISUAL_ID = 4003;

    /**
     * @generated
     */
    private DirectEditManager manager;

    /**
     * @generated
     */
    private IParser parser;

    /**
     * @generated
     */
    private List parserElements;

    /**
     * @generated
     */
    private String defaultText;

    /**
     * @generated NOT
     */
    private PrimTypeResourceListener rsl;

    /**
     * @generated
     */
    public ClassNameEditPart(View view) {
        super(view);
    }

    /**
     * @generated NOT
     */
    @Override
    protected void createDefaultEditPolicies() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
                new BOMLabelDirectEditPolicy());
        installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE,
                new NonResizableEditPolicy() {

                    @Override
                    protected List createSelectionHandles() {
                        List handles = new ArrayList();
                        NonResizableHandleKit
                                .addMoveHandle((GraphicalEditPart) getHost(),
                                        handles);
                        return handles;
                    }

                    @Override
                    public Command getCommand(Request request) {
                        return null;
                    }

                    @Override
                    public boolean understandsRequest(Request request) {
                        return false;
                    }
                });
    }

    /**
     * @generated
     */
    protected String getLabelTextHelper(IFigure figure) {
        if (figure instanceof WrappingLabel) {
            return ((WrappingLabel) figure).getText();
        } else {
            return ((Label) figure).getText();
        }
    }

    /**
     * @generated
     */
    protected void setLabelTextHelper(IFigure figure, String text) {
        if (figure instanceof WrappingLabel) {
            ((WrappingLabel) figure).setText(text);
        } else {
            ((Label) figure).setText(text);
        }
    }

    /**
     * @generated
     */
    protected Image getLabelIconHelper(IFigure figure) {
        if (figure instanceof WrappingLabel) {
            return ((WrappingLabel) figure).getIcon();
        } else {
            return ((Label) figure).getIcon();
        }
    }

    /**
     * @generated
     */
    protected void setLabelIconHelper(IFigure figure, Image icon) {
        if (figure instanceof WrappingLabel) {
            ((WrappingLabel) figure).setIcon(icon);
        } else {
            ((Label) figure).setIcon(icon);
        }
    }

    /**
     * @generated NOT
     */
    public void setLabel(WrappingLabel figure) {
        unregisterVisuals();
        setFigure(figure);
        defaultText = getLabelTextHelper(figure);
        registerVisuals();
        refreshVisuals();
    }

    /**
     * @generated
     */
    @Override
    protected List getModelChildren() {
        return Collections.EMPTY_LIST;
    }

    /**
     * @generated
     */
    @Override
    public IGraphicalEditPart getChildBySemanticHint(String semanticHint) {
        return null;
    }

    /**
     * @generated
     */
    protected EObject getParserElement() {
        return resolveSemanticElement();
    }

    /**
     * @generated NOT
     */
    protected Image getLabelIcon() {
        EObject parserElement = getParserElement();
        if (parserElement == null) {
            return null;
        }

        // Check for the BE profile stereotype
        if (parserElement instanceof Class) {
            Class cl = (Class) parserElement;

            if (FirstClassProfileManager.getInstance()
                    .isFirstClassProfileApplied(cl.getModel())) {
                // use the stereotype's icon if it has one
                EList<Stereotype> stereos = cl.getAppliedStereotypes();

                if (!stereos.isEmpty()) {
                    Stereotype st = stereos.get(0);
                    return getStereotypeImage(st);
                }
            }

            if (BOMGlobalDataUtils.isGlobalDataBOM(cl.getModel())) {
                Stereotype globalDataStereotype = null;

                if (BOMGlobalDataUtils.isGlobalClass(cl)) {
                    globalDataStereotype =
                            GlobalDataProfileManager.getInstance()
                                    .getStereotype(StereotypeKind.GLOBAL);
                } else if (BOMGlobalDataUtils.isCaseClass(cl)) {
                    globalDataStereotype =
                            GlobalDataProfileManager.getInstance()
                                    .getStereotype(StereotypeKind.CASE);
                }

                if (globalDataStereotype != null) {
                    return getStereotypeImage(globalDataStereotype);
                }

            }

        }

        return UMLElementTypes.getImage(parserElement.eClass());
    }

    /**
     * 
     * Returns the stereotype's icon if defined, otherwise null.
     * 
     * @param st
     * @return Image
     */
    private Image getStereotypeImage(Stereotype st) {

        Image img1 = null;

        ImageDescriptor stereoImageDesc =
                UML2ModelUtil.getStereotypeImageDescriptor(st);

        if (stereoImageDesc != null) {

            String location = UML2ModelUtil.getStereotypeImageLocation(st);

            img1 =
                    BOMDiagramEditorPlugin.getInstance().getImageRegistry()
                            .get(location);
            if (img1 == null) {
                // The image is not yet in the Registry, so put it in
                BOMDiagramEditorPlugin.getInstance().getImageRegistry()
                        .put(location, stereoImageDesc);
                img1 =
                        BOMDiagramEditorPlugin.getInstance().getImageRegistry()
                                .get(location);
            }

        }
        return img1;
    }

    /**
     * @generated
     */
    protected String getLabelText() {
        String text = null;
        EObject parserElement =
                (EObject) (getModel() instanceof View ? getModel()
                        : getParserElement());
        if (parserElement != null && getParser() != null) {
            text =
                    getParser()
                            .getPrintString(new EObjectAdapter(parserElement),
                                    getParserOptions().intValue());
        }
        if (text == null || text.length() == 0) {
            text = defaultText;
        }
        return text;
    }

    /**
     * @generated
     */
    @Override
    public void setLabelText(String text) {
        setLabelTextHelper(getFigure(), text);
        Object pdEditPolicy = getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
        if (pdEditPolicy instanceof UMLTextSelectionEditPolicy) {
            ((UMLTextSelectionEditPolicy) pdEditPolicy).refreshFeedback();
        }
    }

    /**
     * @generated
     */
    @Override
    public String getEditText() {
        if (getParserElement() == null || getParser() == null) {
            return ""; //$NON-NLS-1$
        }
        return getParser()
                .getEditString(new EObjectAdapter(getParserElement()),
                        getParserOptions().intValue());
    }

    /**
     * @generated
     */
    protected boolean isEditable() {
        return getParser() != null;
    }

    /**
     * @generated
     */
    @Override
    public ICellEditorValidator getEditTextValidator() {
        return new ICellEditorValidator() {

            @Override
            public String isValid(final Object value) {
                if (value instanceof String) {
                    final EObject element = getParserElement();
                    final IParser parser = getParser();
                    try {
                        IParserEditStatus valid =
                                (IParserEditStatus) getEditingDomain()
                                        .runExclusive(new RunnableWithResult.Impl() {

                                            @Override
                                            public void run() {
                                                setResult(parser
                                                        .isValidEditString(new EObjectAdapter(
                                                                element),
                                                                (String) value));
                                            }
                                        });
                        return valid.getCode() == ParserEditStatus.EDITABLE ? null
                                : valid.getMessage();
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }

                // shouldn't get here
                return null;
            }
        };
    }

    /**
     * @generated
     */
    @Override
    public IContentAssistProcessor getCompletionProcessor() {
        if (getParserElement() == null || getParser() == null) {
            return null;
        }
        return getParser().getCompletionProcessor(new EObjectAdapter(
                getParserElement()));
    }

    /**
     * @generated
     */
    @Override
    public ParserOptions getParserOptions() {
        return ParserOptions.NONE;
    }

    /**
     * @generated
     */
    @Override
    public IParser getParser() {
        if (parser == null) {
            String parserHint = ((View) getModel()).getType();
            IAdaptable hintAdapter =
                    new UMLParserProvider.HintAdapter(
                            UMLElementTypes.Class_1002, getParserElement(),
                            parserHint);
            parser = ParserService.getInstance().getParser(hintAdapter);
        }
        return parser;
    }

    /**
     * @generated
     */
    protected DirectEditManager getManager() {
        if (manager == null) {
            setManager(new TextDirectEditManager(this,
                    TextDirectEditManager.getTextCellEditorClass(this),
                    UMLEditPartFactory.getTextCellEditorLocator(this)));
        }
        return manager;
    }

    /**
     * @generated
     */
    protected void setManager(DirectEditManager manager) {
        this.manager = manager;
    }

    /**
     * @generated
     */
    protected void performDirectEdit() {
        getManager().show();
    }

    /**
     * @generated
     */
    protected void performDirectEdit(Point eventLocation) {
        if (getManager().getClass() == TextDirectEditManager.class) {
            ((TextDirectEditManager) getManager()).show(eventLocation
                    .getSWTPoint());
        }
    }

    /**
     * @generated
     */
    private void performDirectEdit(char initialCharacter) {
        if (getManager() instanceof TextDirectEditManager) {
            ((TextDirectEditManager) getManager()).show(initialCharacter);
        } else {
            performDirectEdit();
        }
    }

    /**
     * @generated
     */
    @Override
    protected void performDirectEditRequest(Request request) {
        final Request theRequest = request;
        try {
            getEditingDomain().runExclusive(new Runnable() {

                @Override
                public void run() {
                    if (isActive() && isEditable()) {
                        if (theRequest
                                .getExtendedData()
                                .get(RequestConstants.REQ_DIRECTEDIT_EXTENDEDDATA_INITIAL_CHAR) instanceof Character) {
                            Character initialChar =
                                    (Character) theRequest
                                            .getExtendedData()
                                            .get(RequestConstants.REQ_DIRECTEDIT_EXTENDEDDATA_INITIAL_CHAR);
                            performDirectEdit(initialChar.charValue());
                        } else if ((theRequest instanceof DirectEditRequest)
                                && (getEditText().equals(getLabelText()))) {
                            DirectEditRequest editRequest =
                                    (DirectEditRequest) theRequest;
                            performDirectEdit(editRequest.getLocation());
                        } else {
                            performDirectEdit();
                        }
                    }
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @generated
     */
    @Override
    protected void refreshVisuals() {
        super.refreshVisuals();
        refreshLabel();
        refreshFont();
        refreshFontColor();
        refreshUnderline();
        refreshStrikeThrough();
    }

    /**
     * @generated
     */
    protected void refreshLabel() {
        setLabelTextHelper(getFigure(), getLabelText());
        setLabelIconHelper(getFigure(), getLabelIcon());
        Object pdEditPolicy = getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
        if (pdEditPolicy instanceof UMLTextSelectionEditPolicy) {
            ((UMLTextSelectionEditPolicy) pdEditPolicy).refreshFeedback();
        }
    }

    /**
     * @generated
     */
    protected void refreshUnderline() {
        FontStyle style =
                (FontStyle) getFontStyleOwnerView()
                        .getStyle(NotationPackage.eINSTANCE.getFontStyle());
        if (style != null && getFigure() instanceof WrappingLabel) {
            ((WrappingLabel) getFigure()).setTextUnderline(style.isUnderline());
        }
    }

    /**
     * @generated
     */
    protected void refreshStrikeThrough() {
        FontStyle style =
                (FontStyle) getFontStyleOwnerView()
                        .getStyle(NotationPackage.eINSTANCE.getFontStyle());
        if (style != null && getFigure() instanceof WrappingLabel) {
            ((WrappingLabel) getFigure()).setTextStrikeThrough(style
                    .isStrikeThrough());
        }
    }

    /**
     * @generated
     */
    @Override
    protected void refreshFont() {
        FontStyle style =
                (FontStyle) getFontStyleOwnerView()
                        .getStyle(NotationPackage.eINSTANCE.getFontStyle());
        if (style != null) {
            FontData fontData =
                    new FontData(style.getFontName(), style.getFontHeight(),
                            (style.isBold() ? SWT.BOLD : SWT.NORMAL)
                                    | (style.isItalic() ? SWT.ITALIC
                                            : SWT.NORMAL));
            setFont(fontData);
        }
    }

    /**
     * @generated
     */
    @Override
    protected void setFontColor(Color color) {
        getFigure().setForegroundColor(color);
    }

    /**
     * @generated
     */
    @Override
    protected void addSemanticListeners() {
        if (getParser() instanceof ISemanticParser) {
            EObject element = resolveSemanticElement();
            parserElements =
                    ((ISemanticParser) getParser())
                            .getSemanticElementsBeingParsed(element);
            for (int i = 0; i < parserElements.size(); i++) {
                addListenerFilter("SemanticModel" + i, this, (EObject) parserElements.get(i)); //$NON-NLS-1$
            }
        } else {
            super.addSemanticListeners();
        }
    }

    /**
     * @generated
     */
    @Override
    protected void removeSemanticListeners() {
        if (parserElements != null) {
            for (int i = 0; i < parserElements.size(); i++) {
                removeListenerFilter("SemanticModel" + i); //$NON-NLS-1$
            }
        } else {
            super.removeSemanticListeners();
        }
    }

    /**
     * @generated
     */
    @Override
    protected AccessibleEditPart getAccessibleEditPart() {
        if (accessibleEP == null) {
            accessibleEP = new AccessibleGraphicalEditPart() {

                @Override
                public void getName(AccessibleEvent e) {
                    e.result = getLabelTextHelper(getFigure());
                }
            };
        }
        return accessibleEP;
    }

    /**
     * @generated
     */
    private View getFontStyleOwnerView() {
        return getPrimaryView();
    }

    /**
     * @generated
     */
    @Override
    protected void addNotationalListeners() {
        super.addNotationalListeners();
        addListenerFilter("PrimaryView", this, getPrimaryView()); //$NON-NLS-1$
    }

    /**
     * @generated
     */
    @Override
    protected void removeNotationalListeners() {
        super.removeNotationalListeners();
        removeListenerFilter("PrimaryView"); //$NON-NLS-1$
    }

    /**
     * @generated
     */
    @Override
    protected void handleNotificationEvent(Notification event) {
        Object feature = event.getFeature();
        if (NotationPackage.eINSTANCE.getFontStyle_FontColor().equals(feature)) {
            Integer c = (Integer) event.getNewValue();
            setFontColor(DiagramColorRegistry.getInstance().getColor(c));
        } else if (NotationPackage.eINSTANCE.getFontStyle_Underline()
                .equals(feature)) {
            refreshUnderline();
        } else if (NotationPackage.eINSTANCE.getFontStyle_StrikeThrough()
                .equals(feature)) {
            refreshStrikeThrough();
        } else if (NotationPackage.eINSTANCE.getFontStyle_FontHeight()
                .equals(feature)
                || NotationPackage.eINSTANCE.getFontStyle_FontName()
                        .equals(feature)
                || NotationPackage.eINSTANCE.getFontStyle_Bold()
                        .equals(feature)
                || NotationPackage.eINSTANCE.getFontStyle_Italic()
                        .equals(feature)) {
            refreshFont();
        } else {
            if (getParser() != null
                    && getParser().isAffectingEvent(event,
                            getParserOptions().intValue())) {
                refreshLabel();
            }
            if (getParser() instanceof ISemanticParser) {
                ISemanticParser modelParser = (ISemanticParser) getParser();
                if (modelParser.areSemanticElementsAffected(null, event)) {
                    removeSemanticListeners();
                    if (resolveSemanticElement() != null) {
                        addSemanticListeners();
                    }
                    refreshLabel();
                }
            }
        }
        super.handleNotificationEvent(event);
    }

    /**
     * @generated
     */
    @Override
    protected IFigure createFigure() {
        // Parent should assign one using setLabel() method
        return null;
    }

    @Override
    public void deactivate() {
        // TODO Auto-generated method stub

        if (rsl != null) {
            XpdResourcesPlugin.getDefault().getEditingDomain()
                    .removeResourceSetListener(rsl);
            rsl = null;
        }
        super.deactivate();
    }

    @Override
    public void activate() {
        super.activate();

        // Create a Listener to listen to all EMF events. We can sift through
        // them to find the addition of the stereotype

        rsl = new PrimTypeResourceListener();
        XpdResourcesPlugin.getDefault().getEditingDomain()
                .addResourceSetListener(rsl);
    }

    class PrimTypeResourceListener implements ResourceSetListener {

        public PrimTypeResourceListener() {
        }

        @Override
        public NotificationFilter getFilter() {
            return null;
        }

        @Override
        public boolean isAggregatePrecommitListener() {
            return false;
        }

        @Override
        public boolean isPostcommitOnly() {
            return true;
        }

        @Override
        public boolean isPrecommitOnly() {
            return false;
        }

        @Override
        public void resourceSetChanged(ResourceSetChangeEvent event) {
            // This is where we listen for resource changes

            // Get this element and resource
            EObject pt = resolveSemanticElement();
            if (pt == null) {
                // if cannot resolve, this part has been removed, need to remove
                // the listener.
                XpdResourcesPlugin.getDefault().getEditingDomain()
                        .removeResourceSetListener(this);
                return;
            }
            Resource res = pt.eResource();

            // Loop through all the notification this event contains and
            // locate the one we are interested in.
            List<Notification> notifications = event.getNotifications();
            for (Object object : notifications) {

                Notification n = (Notification) object;
                if (n.getNotifier() == res) {
                    EObject value = null;
                    // This notification is relevant to this editparts resource
                    if (n.getEventType() == Notification.ADD
                            && n.getNewValue() instanceof EObject) {
                        value = (EObject) n.getNewValue();
                    } else if (n.getEventType() == Notification.REMOVE
                            && n.getOldValue() instanceof EObject) {
                        value = (EObject) n.getOldValue();
                    }

                    if (value != null) {
                        // The stereotype info and its associated element
                        // is wrapped up inside a DynamicEObjectImpl class
                        EList<EReference> refs =
                                value.eClass().getEAllReferences();
                        EReference ref = null;

                        for (EReference reference : refs) {
                            if ("base_Class".equals(reference.getName())) { //$NON-NLS-1$
                                ref = reference;
                                break;
                            }
                        }

                        if (ref != null) {
                            Object referencedPT = value.eGet(ref);
                            if ((n.getEventType() == Notification.ADD && referencedPT == pt)
                                    || (n.getEventType() == Notification.REMOVE && referencedPT == null)) {
                                // Found it! The resource change relates to
                                // this editparts semantic element.
                                // So set the stereotype label.
                                refresh();
                            }
                        }
                    }

                }

            }

        }

        @Override
        public org.eclipse.emf.common.command.Command transactionAboutToCommit(
                ResourceSetChangeEvent event) throws RollbackException {
            return null;
        }
    }
}
