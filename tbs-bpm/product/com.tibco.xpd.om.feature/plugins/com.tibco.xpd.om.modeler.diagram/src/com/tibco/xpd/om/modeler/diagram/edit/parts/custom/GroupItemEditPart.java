package com.tibco.xpd.om.modeler.diagram.edit.parts.custom;

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
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserOptions;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserService;
import org.eclipse.gmf.runtime.diagram.ui.editparts.CompartmentEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.LabelDirectEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ListItemComponentEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramColorRegistry;
import org.eclipse.gmf.runtime.diagram.ui.requests.PasteViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.diagram.ui.tools.DragEditPartsTrackerEx;
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

import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.provider.OMModelImages;
import com.tibco.xpd.om.modeler.diagram.edit.parts.OrganizationModelEditPartFactory;
import com.tibco.xpd.om.modeler.diagram.edit.policies.OrganizationModelTextNonResizableEditPolicy;
import com.tibco.xpd.om.modeler.diagram.edit.policies.OrganizationModelTextSelectionEditPolicy;
import com.tibco.xpd.om.modeler.diagram.edit.policies.custom.OrgModelGroupItemSemanticEditPolicy;
import com.tibco.xpd.om.modeler.diagram.part.Messages;
import com.tibco.xpd.om.modeler.diagram.providers.OrganizationModelElementTypes;
import com.tibco.xpd.om.modeler.diagram.providers.OrganizationModelParserProvider;

public class GroupItemEditPart extends CompartmentEditPart implements
        ITextAwareEditPart {

    public static final int VISUAL_ID = 2050;

    private DirectEditManager manager;

    private IParser parser;

    private List parserElements;

    private String defaultText;

    public GroupItemEditPart(View view) {
        super(view);
    }

    public DragTracker getDragTracker(Request request) {
        if (request instanceof SelectionRequest
                && ((SelectionRequest) request).getLastButtonPressed() == 3) {
            return null;
        }
        return new DragEditPartsTrackerEx(this);
    }

    protected void createDefaultEditPolicies() {
        super.createDefaultEditPolicies();
        installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE,
                new OrgModelGroupItemSemanticEditPolicy());
        installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE,
                new OrganizationModelTextNonResizableEditPolicy());
        installEditPolicy(EditPolicy.COMPONENT_ROLE,
                new ListItemComponentEditPolicy());
        installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
                new LabelDirectEditPolicy());
    }

    @Override
    public Command getCommand(Request _request) {
        if (_request instanceof PasteViewRequest) {
            // If this is a paste request then push it up to the parent
            EditPart parent = getParent();
            if (parent != null) {
                return parent.getCommand(_request);
            }
        }
        return super.getCommand(_request);
    }

    protected String getLabelTextHelper(IFigure figure) {
        if (figure instanceof WrappingLabel) {
            return ((WrappingLabel) figure).getText();
        } else {
            return ((Label) figure).getText();
        }
    }

    protected void setLabelTextHelper(IFigure figure, String text) {
        if (figure instanceof WrappingLabel) {
            ((WrappingLabel) figure).setText(text);
        } else {
            ((Label) figure).setText(text);
        }
    }

    protected Image getLabelIconHelper(IFigure figure) {
        if (figure instanceof WrappingLabel) {
            return ((WrappingLabel) figure).getIcon();
        } else {
            return ((Label) figure).getIcon();
        }
    }

    protected void setLabelIconHelper(IFigure figure, Image icon) {
        if (figure instanceof WrappingLabel) {
            ((WrappingLabel) figure).setIcon(icon);
        } else {
            ((Label) figure).setIcon(icon);
        }
    }

    public void setLabel(IFigure figure) {
        unregisterVisuals();
        setFigure(figure);
        defaultText = getLabelTextHelper(figure);
        registerVisuals();
        refreshVisuals();
    }

    protected List getModelChildren() {
        return Collections.EMPTY_LIST;
    }

    public IGraphicalEditPart getChildBySemanticHint(String semanticHint) {
        return null;
    }

    protected EObject getParserElement() {
        return resolveSemanticElement();
    }

    protected Image getLabelIcon() {
        EObject parserElement = getParserElement();
        if (parserElement == null) {
            return null;
        }

        if (parserElement instanceof Position) {
            Position pos = (Position) parserElement;

            if (pos.getType() != null) {
                Image img = null;
                img =
                        ExtendedImageRegistry.INSTANCE
                                .getImage(OMModelImages
                                        .getImageObject(OMModelImages.IMAGE_POSITION_FEATURE));
                return img;
            }
        }

        return OrganizationModelElementTypes.getImage(parserElement.eClass());
    }

    protected String getLabelText() {
        String text = null;
        EObject parserElement = getParserElement();
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

    public void setLabelText(String text) {
        setLabelTextHelper(getFigure(), text);
        Object pdEditPolicy = getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
        if (pdEditPolicy instanceof OrganizationModelTextSelectionEditPolicy) {
            ((OrganizationModelTextSelectionEditPolicy) pdEditPolicy)
                    .refreshFeedback();
        }
    }

    public String getEditText() {
        if (getParserElement() == null || getParser() == null) {
            return ""; //$NON-NLS-1$
        }
        return getParser()
                .getEditString(new EObjectAdapter(getParserElement()),
                        getParserOptions().intValue());
    }

    protected boolean isEditable() {
        return getParser() != null;
    }

    public ICellEditorValidator getEditTextValidator() {
        return new ICellEditorValidator() {

            public String isValid(final Object value) {
                if (value instanceof String) {
                    final EObject element = getParserElement();
                    final IParser parser = getParser();
                    try {
                        IParserEditStatus valid =
                                (IParserEditStatus) getEditingDomain()
                                        .runExclusive(new RunnableWithResult.Impl() {

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

    public IContentAssistProcessor getCompletionProcessor() {
        if (getParserElement() == null || getParser() == null) {
            return null;
        }
        return getParser().getCompletionProcessor(new EObjectAdapter(
                getParserElement()));
    }

    public ParserOptions getParserOptions() {
        return ParserOptions.NONE;
    }

    public IParser getParser() {
        if (parser == null) {
            String parserHint = ((View) getModel()).getType();
            IAdaptable hintAdapter =
                    new OrganizationModelParserProvider.HintAdapter(
                            OrganizationModelElementTypes.Group_2050,
                            getParserElement(), parserHint);
            parser = ParserService.getInstance().getParser(hintAdapter);
        }
        return parser;
    }

    protected DirectEditManager getManager() {
        if (manager == null) {
            setManager(new TextDirectEditManager(this, TextDirectEditManager
                    .getTextCellEditorClass(this),
                    OrganizationModelEditPartFactory
                            .getTextCellEditorLocator(this)));
        }
        return manager;
    }

    protected void setManager(DirectEditManager manager) {
        this.manager = manager;
    }

    protected void performDirectEdit() {
        getManager().show();
    }

    protected void performDirectEdit(Point eventLocation) {
        if (getManager().getClass() == TextDirectEditManager.class) {
            ((TextDirectEditManager) getManager()).show(eventLocation
                    .getSWTPoint());
        }
    }

    private void performDirectEdit(char initialCharacter) {
        if (getManager() instanceof TextDirectEditManager) {
            ((TextDirectEditManager) getManager()).show(initialCharacter);
        } else {
            performDirectEdit();
        }
    }

    protected void performDirectEditRequest(Request request) {
        final Request theRequest = request;
        try {
            getEditingDomain().runExclusive(new Runnable() {

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

    protected void refreshVisuals() {
        super.refreshVisuals();
        refreshLabel();
        refreshFont();
        refreshFontColor();
        refreshUnderline();
        refreshStrikeThrough();
    }

    protected void refreshLabel() {
        setLabelTextHelper(getFigure(), getLabelText());
        setLabelIconHelper(getFigure(), getLabelIcon());
        Object pdEditPolicy = getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
        if (pdEditPolicy instanceof OrganizationModelTextSelectionEditPolicy) {
            ((OrganizationModelTextSelectionEditPolicy) pdEditPolicy)
                    .refreshFeedback();
        }
    }

    protected void refreshUnderline() {
        FontStyle style =
                (FontStyle) getFontStyleOwnerView()
                        .getStyle(NotationPackage.eINSTANCE.getFontStyle());
        if (style != null && getFigure() instanceof WrappingLabel) {
            ((WrappingLabel) getFigure()).setTextUnderline(style.isUnderline());
        }
    }

    protected void refreshStrikeThrough() {
        FontStyle style =
                (FontStyle) getFontStyleOwnerView()
                        .getStyle(NotationPackage.eINSTANCE.getFontStyle());
        if (style != null && getFigure() instanceof WrappingLabel) {
            ((WrappingLabel) getFigure()).setTextStrikeThrough(style
                    .isStrikeThrough());
        }
    }

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

    protected void setFontColor(Color color) {
        getFigure().setForegroundColor(color);
    }

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

    protected void removeSemanticListeners() {
        if (parserElements != null) {
            for (int i = 0; i < parserElements.size(); i++) {
                removeListenerFilter("SemanticModel" + i); //$NON-NLS-1$
            }
        } else {
            super.removeSemanticListeners();
        }
    }

    protected AccessibleEditPart getAccessibleEditPart() {
        if (accessibleEP == null) {
            accessibleEP = new AccessibleGraphicalEditPart() {

                public void getName(AccessibleEvent e) {
                    e.result = getLabelTextHelper(getFigure());
                }
            };
        }
        return accessibleEP;
    }

    private View getFontStyleOwnerView() {
        return getPrimaryView();
    }

    protected void addNotationalListeners() {
        super.addNotationalListeners();
        addListenerFilter("PrimaryView", this, getPrimaryView()); //$NON-NLS-1$
    }

    protected void removeNotationalListeners() {
        super.removeNotationalListeners();
        removeListenerFilter("PrimaryView"); //$NON-NLS-1$
    }

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

        if (event.getFeature() == OMPackage.Literals.POSITION__FEATURE) {
            if (event.getNotifier() == resolveSemanticElement()) {
                refreshLabel();
            }
        }

        super.handleNotificationEvent(event);
    }

    protected IFigure createFigure() {
        IFigure label = createFigurePrim();
        defaultText = getLabelTextHelper(label);
        return label;
    }

    protected IFigure createFigurePrim() {
        return new GroupNameLabelFigure();
    }

    public class GroupNameLabelFigure extends WrappingLabel {

        private WrappingLabel fFigurePositionNameLabelFigure;

        public GroupNameLabelFigure() {
            this
                    .setText(Messages.OrgModelGroupItemEditPart_GroupsFigureName_Label);
        }

        public WrappingLabel getFigurePositionNameLabelFigure() {
            return fFigurePositionNameLabelFigure;
        }

    }

}
