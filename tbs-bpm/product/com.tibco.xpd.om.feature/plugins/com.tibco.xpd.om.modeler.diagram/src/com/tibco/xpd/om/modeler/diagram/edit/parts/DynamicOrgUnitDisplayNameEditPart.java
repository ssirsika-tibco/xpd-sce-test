package com.tibco.xpd.om.modeler.diagram.edit.parts;

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
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.provider.OMModelImages;
import com.tibco.xpd.om.modeler.diagram.edit.policies.OrganizationModelTextSelectionEditPolicy;
import com.tibco.xpd.om.modeler.diagram.part.Messages;
import com.tibco.xpd.om.modeler.diagram.providers.OrganizationModelElementTypes;
import com.tibco.xpd.om.modeler.diagram.providers.OrganizationModelParserProvider;

/**
 * @generated
 */
public class DynamicOrgUnitDisplayNameEditPart extends CompartmentEditPart
        implements ITextAwareEditPart {

    private static final String REF_LISTENER = "referenceListener"; //$NON-NLS-1$

    private static final String REF_DYN_ORG_LISTENER =
            "referencedDynOrgListener"; //$NON-NLS-1$

    private static final String REF_ROOT_ORG_UNIT_LISTENER_ID =
            "referenceRootOrgUnitListener"; //$NON-NLS-1$

    /**
     * @generated
     */
    public static final int VISUAL_ID = 4006;

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

    private OrgUnit lastRootOrgUnit;

    /**
     * @generated
     */
    public DynamicOrgUnitDisplayNameEditPart(View view) {
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
        // Show Dynamic OrgUnit icon
        return ExtendedImageRegistry.INSTANCE.getImage(OMModelImages
                .getImageObject(OMModelImages.IMAGE_DYNAMIC_ORG_UNIT));
    }

    /**
     * @generated NOT
     */
    protected String getLabelText() {
        String text = null;
        Organization organization = getReferencedDynamicOrganization();
        if (organization != null) {
            lastRootOrgUnit = getRootOrgUnit(organization);
            if (lastRootOrgUnit != null) {
                // Should only be one root org unit
                text = lastRootOrgUnit.getDisplayName();
            } else {
                text = organization.getDisplayName();
            }
        }

        return text != null ? text
                : Messages.DynamicOrgUnitDisplayNameEditPart_notSet_label;
    }

    /**
     * Get the root org unit of the given (Dynamic) organization. This will find
     * the first org unit without any incoming relationships.
     * 
     * @param org
     * @return
     */
    private OrgUnit getRootOrgUnit(Organization org) {
        EList<OrgUnit> units = org.getSubUnits();
        if (!units.isEmpty()) {
            return units.get(0);
        }
        return null;
    }

    /**
     * Get the referenced Dynamic Organization.
     * 
     * @return Organization, or <code>null</code> if one is not referenced.
     */
    private Organization getReferencedDynamicOrganization() {
        EObject parserElement = getParserElement();
        if (parserElement instanceof DynamicOrgUnit) {
            DynamicOrgReference reference =
                    ((DynamicOrgUnit) parserElement).getDynamicOrganization();
            if (reference != null) {
                return reference.getTo();
            }
        }
        return null;
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
                    new OrganizationModelParserProvider.HintAdapter(
                            OrganizationModelElementTypes.DynamicOrgUnit_2003,
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
        EObject eObject = resolveSemanticElement();

        if (eObject instanceof DynamicOrgUnit) {
            DynamicOrgReference ref =
                    ((DynamicOrgUnit) eObject).getDynamicOrganization();
            if (ref != null) {
                addReferencedDynOrgListeners(ref);

                // Also register listener to root org unit (need to monitor name
                // change)
                if (ref.getTo() != null) {
                    OrgUnit rootOrgUnit = getRootOrgUnit(ref.getTo());
                    if (rootOrgUnit != null) {
                        addDynamicOrgRootOrgUnitListener(rootOrgUnit);
                    }
                }
            }
        }
    }

    /**
     * @see org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart#deactivate()
     * 
     */
    @Override
    public void deactivate() {
        removeReferencedDynOrgListeners();
        removeDynamicOrgRootOrgUnitListener();
        super.deactivate();
    }

    /**
     * Register additional listeners to the referenced dynamic organization.
     * 
     * @param reference
     */
    private void addReferencedDynOrgListeners(DynamicOrgReference reference) {
        if (reference != null) {
            // Start listening to changes to the target of this
            // relationship
            addListenerFilter(REF_LISTENER,
                    this,
                    reference,
                    OMPackage.eINSTANCE.getDynamicOrgReference_To());

            if (reference.getTo() != null) {
                // Listen to changes of the referenced Dynamic Organization -
                // when name of the org changes this edit part needs to be
                // updated
                addListenerFilter(REF_DYN_ORG_LISTENER, this, reference.getTo());
            }
        }
    }

    /**
     * Unregister all dynamic org listeners.
     */
    private void removeReferencedDynOrgListeners() {
        removeListenerFilter(REF_DYN_ORG_LISTENER);
        removeListenerFilter(REF_LISTENER);
    }

    /**
     * Register a listener to listen to changes to the root org unit of the
     * referenced dynamic organization.
     * 
     * @param rootOrgUnit
     */
    private void addDynamicOrgRootOrgUnitListener(OrgUnit rootOrgUnit) {
        if (rootOrgUnit != null) {
            addListenerFilter(REF_ROOT_ORG_UNIT_LISTENER_ID,
                    this,
                    rootOrgUnit,
                    OMPackage.eINSTANCE.getNamedElement_DisplayName());
        }
    }

    /**
     * Unregister a listener to listen to changes to the root org unit of the
     * referenced dynamic organization.
     */
    private void removeDynamicOrgRootOrgUnitListener() {
        removeListenerFilter(REF_ROOT_ORG_UNIT_LISTENER_ID);
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
                if (event.getNotifier() == ou
                        && event.getFeature() == OMPackage.eINSTANCE
                                .getDynamicOrgUnit_DynamicOrganization()) {

                    DynamicOrgReference reference = ou.getDynamicOrganization();

                    removeReferencedDynOrgListeners();
                    if (reference != null) {
                        // Start listening to changes to the target of this
                        // relationship
                        addReferencedDynOrgListeners(reference);

                        // Also start listening to new root org unit - if any
                        removeDynamicOrgRootOrgUnitListener();
                        if (reference.getTo() != null) {
                            addDynamicOrgRootOrgUnitListener(getRootOrgUnit(reference
                                    .getTo()));
                        }
                    }

                    refreshLabel();
                } else if (ou.getDynamicOrganization() != null) {
                    DynamicOrgReference ref = ou.getDynamicOrganization();
                    if (ref == event.getNotifier()
                            && event.getFeature() == OMPackage.eINSTANCE
                                    .getDynamicOrgReference_To()) {
                        /*
                         * target of the dynamic org reference has changed (by
                         * dragging in editor) so start listening to new org and
                         * root org unit, if any, and refresh this label
                         */
                        removeReferencedDynOrgListeners();
                        addReferencedDynOrgListeners(ref);

                        removeDynamicOrgRootOrgUnitListener();
                        if (ref.getTo() != null) {
                            addDynamicOrgRootOrgUnitListener(getRootOrgUnit(ref
                                    .getTo()));
                        }
                        refreshLabel();
                    } else if (event.getNotifier() == ref.getTo()) {
                        if (event.getFeature() == OMPackage.eINSTANCE
                                .getNamedElement_DisplayName()) {
                            /*
                             * Label of the referenced organization has changed.
                             * So refresh incase we are showing the org label in
                             * this edit part.
                             */
                            refreshLabel();
                        }

                        /*
                         * Target organization content has changed - maybe the
                         * root edit part has changed?
                         */
                        if (event.getFeature() == OMPackage.eINSTANCE
                                .getOrganization_Units()
                                || event.getFeature() == OMPackage.eINSTANCE
                                        .getOrganization_OrgUnitRelationships()) {
                            OrgUnit currentRootUnit =
                                    getRootOrgUnit(ref.getTo());
                            if (lastRootOrgUnit != currentRootUnit) {

                                // Remove listener of last root org unit
                                // and listen to new root orgunit
                                removeDynamicOrgRootOrgUnitListener();
                                addDynamicOrgRootOrgUnitListener(currentRootUnit);

                                refreshLabel();
                            }
                        }
                    } else if (lastRootOrgUnit == event.getNotifier()
                            && event.getFeature() == OMPackage.eINSTANCE
                                    .getNamedElement_DisplayName()) {
                        // Name of the root org unit has changed
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
