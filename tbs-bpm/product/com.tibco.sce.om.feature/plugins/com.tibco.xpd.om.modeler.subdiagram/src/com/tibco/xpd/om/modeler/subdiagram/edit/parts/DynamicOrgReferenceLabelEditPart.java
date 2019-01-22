package com.tibco.xpd.om.modeler.subdiagram.edit.parts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
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
import org.eclipse.gmf.runtime.diagram.ui.editparts.CompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.LabelDirectEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramColorRegistry;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.diagram.ui.tools.TextDirectEditManager;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser;
import org.eclipse.gmf.runtime.notation.FontStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;

import com.tibco.xpd.om.core.om.DynamicOrgReference;
import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.provider.OMModelImages;
import com.tibco.xpd.om.modeler.subdiagram.edit.parts.customfigures.HyperLinkWrappingLabel;
import com.tibco.xpd.om.modeler.subdiagram.edit.policies.OrganizationModelTextSelectionEditPolicy;
import com.tibco.xpd.om.modeler.subdiagram.part.Messages;
import com.tibco.xpd.om.modeler.subdiagram.providers.OrganizationModelElementTypes;
import com.tibco.xpd.om.modeler.subdiagram.providers.OrganizationModelParserProvider;

/**
 * @generated
 */
public class DynamicOrgReferenceLabelEditPart extends CompartmentEditPart
        implements ITextAwareEditPart {

    private static final String REF_LISTENER = "referenceListener"; //$NON-NLS-1$

    /**
     * @generated
     */
    public static final int VISUAL_ID = 4011;

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
     * @generated
     */
    public DynamicOrgReferenceLabelEditPart(View view) {
        super(view);
    }

    /**
     * @generated
     */
    @Override
    protected void createDefaultEditPolicies() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
                new LabelDirectEditPolicy());
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
     * @generated NOT
     */
    protected void setLabelTextHelper(IFigure figure, String text) {
        if (figure instanceof HyperLinkWrappingLabel) {
            EObject element = resolveSemanticElement();

            if (element instanceof DynamicOrgUnit
                    && ((DynamicOrgUnit) element).getDynamicOrganization() != null) {
                ((HyperLinkWrappingLabel) figure).setHyperLinkText(text);
            } else {
                ((HyperLinkWrappingLabel) figure).setText(text);
            }
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
     * @generated
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
        return ExtendedImageRegistry.INSTANCE.getImage(OMModelImages
                .getImageObject(OMModelImages.IMAGE_DYNAMIC_ORGANISATION));
    }

    /**
     * @generated NOT
     */
    protected String getLabelText() {
        EObject parserElement = getParserElement();
        if (parserElement instanceof DynamicOrgUnit) {
            DynamicOrgReference reference =
                    ((DynamicOrgUnit) parserElement).getDynamicOrganization();
            if (reference != null) {
                Organization to = reference.getTo();
                if (to != null) {
                    return to.getDisplayName();
                }
            }

        }
        return Messages.DynamicOrgReferenceLabelEditPart_notSet_label;

    }

    /**
     * @generated
     */
    @Override
    public void setLabelText(String text) {
        setLabelTextHelper(getFigure(), text);
        Object pdEditPolicy = getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
        if (pdEditPolicy instanceof OrganizationModelTextSelectionEditPolicy) {
            ((OrganizationModelTextSelectionEditPolicy) pdEditPolicy)
                    .refreshFeedback();
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
        return false;
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
                    new OrganizationModelParserProvider.HintAdapter(
                            OrganizationModelElementTypes.DynamicOrgUnit_1002,
                            getParserElement(), parserHint);
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
                    OrganizationModelEditPartFactory
                            .getTextCellEditorLocator(this)));
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
        if (pdEditPolicy instanceof OrganizationModelTextSelectionEditPolicy) {
            ((OrganizationModelTextSelectionEditPolicy) pdEditPolicy)
                    .refreshFeedback();
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
     * @see org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart#activate()
     * 
     */
    @Override
    public void activate() {
        super.activate();

        EObject eo = resolveSemanticElement();
        if (eo instanceof DynamicOrgUnit) {
            DynamicOrgReference ref =
                    ((DynamicOrgUnit) eo).getDynamicOrganization();

            /*
             * If a reference is set then start listening to it as if the target
             * Dynamic Organization changes then this edit part needs to be
             * updated.
             */
            if (ref != null) {
                addListenerFilter(REF_LISTENER,
                        this,
                        ref,
                        OMPackage.eINSTANCE.getDynamicOrgReference_To());
            }
        }
    }

    /**
     * @see org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart#deactivate()
     * 
     */
    @Override
    public void deactivate() {
        removeListenerFilter(REF_LISTENER);
        super.deactivate();
    }

    /**
     * @generated NOT
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
            EObject eo = resolveSemanticElement();

            if (eo instanceof DynamicOrgUnit) {
                DynamicOrgUnit ou = (DynamicOrgUnit) eo;

                if (event.getNotifier() == ou) {
                    if (event.getFeature() == OMPackage.eINSTANCE
                            .getDynamicOrgUnit_DynamicOrganization()) {

                        /*
                         * Update the listener of the reference
                         */
                        DynamicOrgReference reference =
                                ou.getDynamicOrganization();

                        removeListenerFilter(REF_LISTENER);
                        if (reference != null) {
                            // Start listening to changes to the target of this
                            // relationship
                            addListenerFilter(REF_LISTENER,
                                    this,
                                    reference,
                                    OMPackage.eINSTANCE
                                            .getDynamicOrgReference_To());
                        }

                        refreshLabel();
                    }
                } else if (ou.getDynamicOrganization() != null
                        && event.getNotifier() == ou.getDynamicOrganization()) {
                    if (event.getFeature() == OMPackage.eINSTANCE
                            .getDynamicOrgReference_To()) {
                        refreshLabel();
                    }
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

}
