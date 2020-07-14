/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.script.sourceviewer.viewer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.operations.IOperationApprover;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.bindings.Binding;
import org.eclipse.jface.bindings.TriggerSequence;
import org.eclipse.jface.commands.ActionHandler;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.Assert;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.ITextViewerExtension;
import org.eclipse.jface.text.IUndoManager;
import org.eclipse.jface.text.IUndoManagerExtension;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationRulerColumn;
import org.eclipse.jface.text.source.ChangeRulerColumn;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.IAnnotationAccess;
import org.eclipse.jface.text.source.IChangeRulerColumn;
import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.IVerticalRulerColumn;
import org.eclipse.jface.text.source.OverviewRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.VerifyKeyListener;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.operations.LinearUndoViolationUserApprover;
import org.eclipse.ui.operations.OperationHistoryActionHandler;
import org.eclipse.ui.operations.RedoActionHandler;
import org.eclipse.ui.operations.UndoActionHandler;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.ui.texteditor.AnnotationPreference;
import org.eclipse.ui.texteditor.DefaultMarkerAnnotationAccess;
import org.eclipse.ui.texteditor.DefaultRangeIndicator;
import org.eclipse.ui.texteditor.IAbstractTextEditorHelpContextIds;
import org.eclipse.ui.texteditor.IReadOnlyDependent;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.eclipse.ui.texteditor.IUpdate;
import org.eclipse.ui.texteditor.IWorkbenchActionDefinitionIds;
import org.eclipse.ui.texteditor.MarkerAnnotationPreferences;
import org.eclipse.ui.texteditor.ResourceMarkerAnnotationModel;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;
import com.tibco.xpd.script.sourceviewer.client.CommandProvider;
import com.tibco.xpd.script.sourceviewer.client.IScriptCompiler;
import com.tibco.xpd.script.sourceviewer.client.IScriptInfoProvider;
import com.tibco.xpd.script.sourceviewer.client.IScriptInfoProviderExt;
import com.tibco.xpd.script.sourceviewer.client.ISiteProvider;
import com.tibco.xpd.script.sourceviewer.client.ScriptEditor;
import com.tibco.xpd.script.sourceviewer.internal.Messages;
import com.tibco.xpd.script.sourceviewer.internal.action.TextOperationAction;
import com.tibco.xpd.script.sourceviewer.internal.action.TextViewerAction;
import com.tibco.xpd.script.sourceviewer.internal.client.IDisposable;
import com.tibco.xpd.script.sourceviewer.internal.contentassist.AbstractTibcoContentAssistProcessor;
import com.tibco.xpd.script.sourceviewer.internal.preferences.AbstractScriptCommonUIPreferenceNames;
import com.tibco.xpd.script.sourceviewer.internal.reader.JavaPairMatcher;
import com.tibco.xpd.script.sourceviewer.internal.util.Consts;
import com.tibco.xpd.script.sourceviewer.internal.util.PreferenceUtils;
import com.tibco.xpd.script.sourceviewer.internal.viewer.NonLocalUndoUserApprover;
import com.tibco.xpd.script.sourceviewer.internal.viewer.ScriptSourceViewerConfiguration;
import com.tibco.xpd.script.sourceviewer.internal.viewer.listeners.AbstractLineStyleListener;
import com.tibco.xpd.script.sourceviewer.internal.viewer.listeners.AbstractLineStyleListenerProvider;
import com.tibco.xpd.script.sourceviewer.internal.viewer.listeners.ScriptDocumentListener;
import com.tibco.xpd.script.sourceviewer.internal.viewer.listeners.ScriptFocusListener;
import com.tibco.xpd.script.sourceviewer.internal.viewer.listeners.TextListener;

/**
 * Source Viewer extended for the script editor. Configurations provided by
 * respective Grammar specific editors.
 * 
 * @author rsomayaj
 * 
 */
public class ScriptSourceViewer implements ISiteProvider, ScriptEditor {

    private ResourceMarkerAnnotationModel annotationModel = null;

    private IDocument scriptDoc = null;

    private AbstractLineStyleListener fLineStyleListener = null;

    public final static String ACTION_NAME_CONTENT_ASSIST_PROPOSAL =
            "ContentAssistProposal";//$NON-NLS-1$

    /** The actions registered with the editor. */
    private Map<String, IAction> fActions = new HashMap<String, IAction>(10);

    /** The verify key listener for activation code triggering. */
    private ActivationCodeTrigger fActivationCodeTrigger =
            new ActivationCodeTrigger();

    /** The editor's action activation codes. */
    private List fActivationCodes = new ArrayList(2);

    /** The width of the vertical ruler. */
    protected final static int VERTICAL_RULER_WIDTH = 12;

    /** The editor's preference store. */
    private IPreferenceStore fPreferenceStore;

    private PropertyChangeListener fPropertyChangeListener =
            new PropertyChangeListener(this);

    /** Selection changed listener. */
    private ISelectionChangedListener fSelectionChangedListener;

    /** The actions marked as selection dependent. */
    private List fSelectionActions = new ArrayList(5);

    /** The actions marked as content dependent. */
    private List<String> fContentActions = new ArrayList<String>(5);

    private ScriptSourceViewerConfiguration scriptSourceViewerConfiguration;

    /** This editor's supported brackets */
    private final static char[] BRACKETS = { '{', '}', '(', ')', '[', ']' };

    protected JavaPairMatcher fBracketMatcher = new JavaPairMatcher(BRACKETS);

    private final static String CURRENT_LINE =
            AbstractDecoratedTextEditorPreferenceConstants.EDITOR_CURRENT_LINE;

    /**
     * Preference key for highlight color of current line.
     */
    private final static String CURRENT_LINE_COLOR =
            AbstractDecoratedTextEditorPreferenceConstants.EDITOR_CURRENT_LINE_COLOR;

    /**
     * Preference key for showing print margin ruler.
     */
    private final static String PRINT_MARGIN =
            AbstractDecoratedTextEditorPreferenceConstants.EDITOR_PRINT_MARGIN;

    /**
     * Preference key for print margin ruler color.
     */
    private final static String PRINT_MARGIN_COLOR =
            AbstractDecoratedTextEditorPreferenceConstants.EDITOR_PRINT_MARGIN_COLOR;

    /**
     * Preference key for print margin ruler column.
     */
    private final static String PRINT_MARGIN_COLUMN =
            AbstractDecoratedTextEditorPreferenceConstants.EDITOR_PRINT_MARGIN_COLUMN;

    /**
     * The operation approver used to warn on undoing of non-local operations.
     * 
     * @since 3.1
     */
    private IOperationApprover fNonLocalOperationApprover;

    /**
     * The operation approver used to warn of linear undo violations.
     * 
     * @since 3.1
     */
    private IOperationApprover fLinearUndoViolationApprover;

    /**
     * The overview ruler of this editor.
     * 
     * <p>
     * This field should not be referenced by subclasses. It is
     * <code>protected</code> for API compatibility reasons and will be made
     * <code>private</code> soon. Use {@link #getOverviewRuler()} instead.
     * </p>
     */
    private IOverviewRuler overViewRuler;

    private IVerticalRuler verticalRuler;

    /**
     * The annotation ruler column used in the vertical ruler.
     */
    private AnnotationRulerColumn fAnnotationRulerColumn;

    /**
     * The change ruler column.
     */
    private IChangeRulerColumn fChangeRulerColumn;

    /**
     * Helper for accessing annotation from the perspective of this editor.
     * 
     * <p>
     * This field should not be referenced by subclasses. It is
     * <code>protected</code> for API compatibility reasons and will be made
     * <code>private</code> soon. Use {@link #getAnnotationAccess()} instead.
     * </p>
     */
    protected IAnnotationAccess fAnnotationAccess;

    /** The editor's range indicator. */
    private Annotation fRangeIndicator;

    /**
     * Helper for managing the decoration support of this editor's viewer.
     * 
     * <p>
     * This field should not be referenced by subclasses. It is
     * <code>protected</code> for API compatibility reasons and will be made
     * <code>private</code> soon. Use
     * {@link #getSourceViewerDecorationSupport(ISourceViewer)} instead.
     * </p>
     */
    protected SourceViewerDecorationSupport fSourceViewerDecorationSupport;

    private SourceViewer scriptViewer;

    private Composite parent;

    private int styles;

    private String grammarType;

    private boolean enableUndoRedoActions = false;

    private boolean enableCopyAndPasteActions = true;

    private boolean enableContentAssistActions = true;

    /**
     * The key binding scopes of this editor.
     * 
     * @since 2.1
     */
    private String[] fKeyBindingScopes;

    private IDocumentListener documentListener;

    /** The editor's text listener. */
    private TextListener fTextListener = new TextListener(this);

    private FocusListener focusListener = new ScriptFocusListener(this);

    private String newScript;

    private boolean isNewScript;

    private boolean isScriptSelectionChanged;

    private AbstractTibcoContentAssistProcessor contentAssistProcessor;

    private AbstractLineStyleListenerProvider lineStyleListenerProvider;

    public ScriptSourceViewer(Composite parent, int styles, String grammarType) {
        this.parent = parent;
        this.styles = styles;
        this.grammarType = grammarType;
    }

    @Override
    public void setInputString(String strScript) {
        this.newScript = strScript;
        this.scriptDoc = new Document(newScript);
    }

    @Override
    public void configure() {
        setPreferenceStore(PreferenceUtils.getPreferenceStore());
        setRangeIndicator(new DefaultRangeIndicator());
        setKeyBindingScopes(new String[] { Consts.CONTEXT_ID }); //$NON-NLS-1$
        verticalRuler = createVerticalRuler();
        overViewRuler = createOverViewRuler();
        boolean showOverviewRuler =
                fPropertyChangeListener.isOverviewRulerVisible();
        this.scriptViewer =
                new SourceViewer(this.parent, verticalRuler, overViewRuler,
                        showOverviewRuler, this.styles);
        this.scriptViewer
                .addSelectionChangedListener(getSelectionChangedListener());
        setDecorationSupport();
        if (fRangeIndicator != null) {
            getSourceViewer().setRangeIndicator(fRangeIndicator);
        }
        fPropertyChangeListener.initializeViewerFont(scriptViewer);
        fPropertyChangeListener.initializeViewerColors(scriptViewer);
        fPropertyChangeListener.initializeFindScopeColor(scriptViewer);
        JFaceResources.getFontRegistry().addListener(fPropertyChangeListener);
        getSourceViewer().setEditable(true);

        String contextMenuId = getEditorContextMenuId();
        StyledText styledText = scriptViewer.getTextWidget();
        manager = new MenuManager(contextMenuId, contextMenuId);
        manager.setRemoveAllWhenShown(true);
        manager.addMenuListener(getContextMenuListener());
        fTextContextMenu = manager.createContextMenu(styledText);
        // comment this line if using gestures, above.
        styledText.setMenu(fTextContextMenu);
    }

    /**
     * This method is now called from JsFocusListener. Registering the action
     * when source viewer gains focus. This is the fix for MR 31276
     */
    public void initialiseActions() {
        if (this.siteProvider != null) {
            initializeActivationCodeTrigger();
            if (isEnableContentAssistActions()) {
                createContentAssistActions();
            }
            if (isEnableCopyAndPasteActions()) {
                createCopyAndPasteActions();
            }
            if (isEnableUndoRedoActions()) {
                createUndoRedoActions();
            }
        }
    }

    @Override
    public void doRefresh() {
        if (isIgnoreRefresh()) {
            return;
        }
        StyledText textWidget = scriptViewer.getTextWidget();
        if (fLineStyleListener != null) {
            textWidget.removeLineStyleListener(fLineStyleListener);
            fLineStyleListener.dispose();
            fLineStyleListener = null;
        }
        if (fActivationCodeTrigger != null) {
            fActivationCodeTrigger.uninstall();
        }
        if (fTextListener != null) {
            scriptViewer.removeTextListener(fTextListener);
            fTextListener = null;
        }
        if (focusListener != null) {
            scriptViewer.getTextWidget().removeFocusListener(focusListener);
            focusListener = null;
        }

        removeDocumentListener();

        getSourceViewer().unconfigure();
        scriptViewer.setDocument(this.getScriptDoc(), annotationModel);

        AbstractLineStyleListener lineStyleListener = getLineStyleListener();
        if (lineStyleListener != null) {
            textWidget.addLineStyleListener(lineStyleListener);
        }

        fTextListener = new TextListener(this);
        scriptViewer.addTextListener(fTextListener);
        documentListener = new ScriptDocumentListener(this);
        scriptViewer.getDocument().addDocumentListener(documentListener);
        getSourceViewer().configure(getSourceViewerConfiguration());
        /*
         * Moved at the top of method, to solve the problem of content assist
         * not refreshing properly. defect 28061
         * initializeActivationCodeTrigger(); createActions();
         * createUndoRedoActions();
         */
        String contextMenuId = getEditorContextMenuId();
        ISelectionProvider selectionProvider =
                scriptViewer.getSelectionProvider();
        if (getWorkbenchPartSite() != null) {
            getWorkbenchPartSite().registerContextMenu(contextMenuId,
                    manager,
                    selectionProvider);
            getWorkbenchPartSite().setSelectionProvider(selectionProvider);
        }
        focusListener = new ScriptFocusListener(this);
        scriptViewer.getTextWidget().addFocusListener(focusListener);

    }

    private void removeDocumentListener() {
        if (documentListener != null && scriptViewer.getDocument() != null) {
            ((ScriptDocumentListener) documentListener).stop();
            scriptViewer.getDocument().removeDocumentListener(documentListener);
            documentListener = null;
        }
    }

    private void createContentAssistActions() {
        TextViewerAction action =
                new TextOperationAction(this,
                        ISourceViewer.CONTENTASSIST_PROPOSALS);
        action.setText(Messages.JScriptSourceViewer_ContentAssistAction);
        action.setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS);
        setAction(ACTION_NAME_CONTENT_ASSIST_PROPOSAL, action);

    }

    private void createCopyAndPasteActions() {

        /*
         * if the line below is uncommented then the key binding does not work
         * any more see https://bugs.eclipse.org/bugs/show_bug.cgi?id=53417
         * action.setActionDefinitionId(ITextEditorActionDefinitionIds.SAVE);
         */

        TextViewerAction action =
                new TextOperationAction(this, ITextOperationTarget.CUT);
        action.setText(Messages.JScriptSourceViewer_CutAction);
        action.setActionDefinitionId(IWorkbenchActionDefinitionIds.CUT);
        setAction(ITextEditorActionConstants.CUT, action);
        registerGlobalAction(ITextEditorActionConstants.CUT, action);

        action = new TextOperationAction(this, ITextOperationTarget.COPY);
        action.setText(Messages.JScriptSourceViewer_CopyAction);
        action.setActionDefinitionId(IWorkbenchActionDefinitionIds.COPY);
        setAction(ITextEditorActionConstants.COPY, action);
        registerGlobalAction(ITextEditorActionConstants.COPY, action);

        action = new TextOperationAction(this, ITextOperationTarget.PASTE);
        action.setText(Messages.JScriptSourceViewer_PasteAction);
        action.setActionDefinitionId(IWorkbenchActionDefinitionIds.PASTE);
        setAction(ITextEditorActionConstants.PASTE, action);
        registerGlobalAction(ITextEditorActionConstants.PASTE, action);

        action = new TextOperationAction(this, ITextOperationTarget.DELETE);
        action.setText(Messages.JScriptSourceViewer_DeleteAction);
        action.setActionDefinitionId(IWorkbenchActionDefinitionIds.DELETE);
        setAction(ITextEditorActionConstants.DELETE, action);
        registerGlobalAction(ITextEditorActionConstants.DELETE, action);

        action = new TextOperationAction(this, ITextOperationTarget.SELECT_ALL);
        action.setText(Messages.JScriptSourceViewer_SelectAllAction);
        action.setActionDefinitionId(IWorkbenchActionDefinitionIds.SELECT_ALL);
        setAction(ITextEditorActionConstants.SELECT_ALL, action);
        registerGlobalAction(ITextEditorActionConstants.SELECT_ALL, action);

        markAsContentDependentAction(ITextEditorActionConstants.UNDO, true);
        markAsContentDependentAction(ITextEditorActionConstants.REDO, true);

        markAsSelectionDependentAction(ITextEditorActionConstants.CUT, true);
        markAsSelectionDependentAction(ITextEditorActionConstants.COPY, true);
        markAsSelectionDependentAction(ITextEditorActionConstants.PASTE, true);
        markAsSelectionDependentAction(ITextEditorActionConstants.DELETE, true);
    }

    public IAction getAction(String actionID) {
        Assert.isNotNull(actionID);
        IAction action = fActions.get(actionID);

        if (action == null) {
            // TODO: action= findContributedAction(actionID);
            /*
             * We need to query the actions which can be contributed by plug-in
             * xml file
             */
            if (action != null)
                setAction(actionID, action);
        }

        return action;
    }

    public void setAction(String actionID, IAction action) {
        Assert.isNotNull(actionID);
        if (action == null) {
            action = fActions.remove(actionID);
            if (action != null) {
                fActivationCodeTrigger
                        .unregisterActionFromKeyActivation(action);
            }
        } else {
            fActions.put(actionID, action);
            fActivationCodeTrigger.registerActionForKeyActivation(action);
        }
    }

    /**
     * Sets the key binding scopes for this editor.
     * 
     * @param scopes
     *            a non-empty array of key binding scope identifiers
     * @since 2.1
     */
    protected void setKeyBindingScopes(String[] scopes) {
        Assert.isTrue(scopes != null && scopes.length > 0);
        fKeyBindingScopes = scopes;
    }

    /**
     * Initializes the activation code trigger.
     * 
     * @since 2.1
     */
    private void initializeActivationCodeTrigger() {
        fActivationCodeTrigger.install();
        fActivationCodeTrigger.setScopes(fKeyBindingScopes);
    }

    /**
     * Internal key verify listener for triggering action activation codes.
     */
    public class ActivationCodeTrigger implements VerifyKeyListener {

        /** Indicates whether this trigger has been installed. */
        private boolean fIsInstalled = false;

        /**
         * The key binding service.
         * 
         * @since 3.2.0
         */
        private IBindingService bindingService;

        /**
         * Service managing scopes.
         * 
         * @since 3.2.0
         */
        private IContextService contextService;

        /**
         * Sid ACE-2915 Need to track the action handlers we replace with Script Editor ones so that we can replce them
         * back on defocus.
         */
        private Map<String, IHandler> cmdIdToOriginalHandlerMap = new HashMap<String, IHandler>();

        /*
         * @see VerifyKeyListener#verifyKey(org.eclipse.swt.events.VerifyEvent)
         */
        @Override
        public void verifyKey(VerifyEvent event) {

            ActionActivationCode code = null;
            int size = fActivationCodes.size();
            for (int i = 0; i < size; i++) {
                code = (ActionActivationCode) fActivationCodes.get(i);
                if (code.matches(event)) {
                    IAction action = getAction(code.fActionId);
                    if (action != null) {

                        if (action instanceof IUpdate)
                            ((IUpdate) action).update();

                        if (!action.isEnabled()
                                && action instanceof IReadOnlyDependent) {
                            IReadOnlyDependent dependent =
                                    (IReadOnlyDependent) action;
                            boolean writable = dependent.isEnabled(true);
                            if (writable) {
                                event.doit = false;
                                return;
                            }
                        } else if (action.isEnabled()) {
                            event.doit = false;
                            action.run();
                            return;
                        }
                    }
                }
            }
        }

        /**
         * Installs this trigger on the editor's text widget.
         * 
         * @since 2.0
         */
        public void install() {
            if (!fIsInstalled) {

                if (ScriptSourceViewer.this instanceof ITextViewerExtension) {
                    ITextViewerExtension e =
                            (ITextViewerExtension) ScriptSourceViewer.this;
                    e.prependVerifyKeyListener(this);
                } else {
                    StyledText text =
                            ScriptSourceViewer.this.scriptViewer
                                    .getTextWidget();
                    text.addVerifyKeyListener(this);
                }

                IWorkbenchPartSite site = getWorkbenchPartSite();
                if (site != null) {
                    bindingService =
                            site
                                    .getService(org.eclipse.ui.keys.IBindingService.class);
                    contextService =
                            site
                                    .getService(IContextService.class);
                    fIsInstalled = true;
                }
            }
        }

        /**
         * Uninstalls this trigger from the editor's text widget.
         * 
         * @since 2.0
         */
        public void uninstall() {
            if (fIsInstalled) {
                if (ScriptSourceViewer.this instanceof ITextViewerExtension) {
                    ITextViewerExtension e =
                            (ITextViewerExtension) ScriptSourceViewer.this;
                    e.removeVerifyKeyListener(this);
                } else if (ScriptSourceViewer.this != null) {
                    StyledText text =
                            ScriptSourceViewer.this.scriptViewer
                                    .getTextWidget();
                    if (text != null && !text.isDisposed()) {
                        text.removeVerifyKeyListener(fActivationCodeTrigger);
                    }
                }
                fIsInstalled = false;
                bindingService = null;
                contextService = null;
            }
        }

        /**
         * Registers the given action for key activation.
         * 
         * @param action
         *            the action to be registered
         * @since 2.0
         */
        public void registerActionForKeyActivation(IAction action) {
            if (bindingService != null
                    && action.getActionDefinitionId() != null) {
                TriggerSequence triggerSequence =
                        bindingService.getBestActiveBindingFor(action
                                .getActionDefinitionId());
                Binding binding =
                        bindingService.getPerfectMatch(triggerSequence);
                IHandler handler = new ActionHandler(action);
                if (binding != null) {
                    ParameterizedCommand pc = binding.getParameterizedCommand();
                    if (pc != null) {
                        Command cmd = pc.getCommand();
                        if (cmd != null) {
                            /*
                             * Sid ACE-2915 preserve the original handler so that we can restore it later when we
                             * unregister our action.
                             */
                            String cmdId = pc.getId();
                            IHandler originalHandler = cmd.getHandler();

                            cmdIdToOriginalHandlerMap.put(cmdId, originalHandler);
                            cmd.setHandler(handler);
                        }
                    }
                }
            }
        }

        /**
         * The given action is no longer available for key activation
         * 
         * @param action
         *            the action to be unregistered
         * @since 2.0
         */
        public void unregisterActionFromKeyActivation(IAction action) {
            if (bindingService != null
                    && action.getActionDefinitionId() != null) {
                TriggerSequence triggerSequence =
                        bindingService.getBestActiveBindingFor(action
                                .getActionDefinitionId());
                Binding binding =
                        bindingService.getPerfectMatch(triggerSequence);
                if (binding != null) {
                    ParameterizedCommand pc = binding.getParameterizedCommand();
                    if (pc != null) {
                        Command cmd = pc.getCommand();
                        if (cmd != null) {
                            String cmdId = cmd.getId();

                            IHandler originalHandler = null;

                            if (cmdIdToOriginalHandlerMap.containsKey(cmdId)) {
                                originalHandler = cmdIdToOriginalHandlerMap.get(cmdId);
                                cmdIdToOriginalHandlerMap.remove(cmdId);
                            }

                            cmd.setHandler(originalHandler);
                        }
                    }
                }
            }
        }

        public void unregisterAllActionFromKeyActivation() {

            Collection<IAction> values = fActions.values();
            if (values == null || bindingService == null) {
                return;
            }
            for (IAction action : values) {
                /*
                 * Sid ACE-2915 Code was a copy of what unregisterActionFromKeyActivation() so should call that (as it's
                 * now changed).
                 */
                unregisterActionFromKeyActivation(action);
            }
        }

        /**
         * Sets the key binding scopes for this editor.
         * 
         * @param keyBindingScopes
         *            the key binding scopes
         * @since 2.1
         */
        public void setScopes(String[] keyBindingScopes) {
            if (contextService != null && keyBindingScopes.length > 0) {
                for (String contextId : keyBindingScopes)
                    contextService.activateContext(contextId);
            }
        }
    }

    @Override
    public IWorkbenchPartSite getWorkbenchPartSite() {
        if (this.siteProvider != null) {
            return this.siteProvider.getWorkbenchPartSite();
        }
        return null;
    }

    @Override
    public void dispose() {

        removeDocumentListener();

        if (manager != null) {
            if (fMenuListener != null) {
                manager.removeMenuListener(fMenuListener);
                fMenuListener = null;
            }
            manager = null;

        }

        if (fLineStyleListener != null) {
            fLineStyleListener.dispose();
            fLineStyleListener = null;
        }
        fAnnotationAccess = null;
        fAnnotationRulerColumn = null;

        if (fPropertyChangeListener != null) {
            JFaceResources.getFontRegistry()
                    .removeListener(fPropertyChangeListener);
            fPropertyChangeListener.dispose();
        }

        if (fPropertyChangeListener != null) {
            if (fPreferenceStore != null) {
                fPreferenceStore
                        .removePropertyChangeListener(fPropertyChangeListener);
                fPreferenceStore = null;
            }
            fPropertyChangeListener = null;
        }

        if (fActivationCodeTrigger != null) {
            fActivationCodeTrigger.uninstall();
            fActivationCodeTrigger = null;
        }
        if (scriptViewer != null) {
            Control control = scriptViewer.getControl();
            if (control != null) {
                if (focusListener != null) {
                    control.removeFocusListener(focusListener);
                    focusListener = null;
                }
            }
        }
        if (scriptViewer != null) {

            if (fSelectionChangedListener != null) {
                scriptViewer
                        .removeSelectionChangedListener(fSelectionChangedListener);
                fSelectionChangedListener = null;
            }

            if (fTextListener != null) {
                scriptViewer.removeTextListener(fTextListener);
                fTextListener = null;
            }
            scriptViewer = null;
        }
        if (fTextContextMenu != null) {
            fTextContextMenu.dispose();
            fTextContextMenu = null;
        }
        //
        if (fSourceViewerDecorationSupport != null) {
            fSourceViewerDecorationSupport.dispose();
            fSourceViewerDecorationSupport = null;
        }

        if (fActions != null) {
            fActions.clear();
            fActions = null;
        }

        if (fContentActions != null) {
            fContentActions.clear();
            fContentActions = null;
        }

        if (fSelectionActions != null) {
            fSelectionActions.clear();
            fSelectionActions = null;
        }
        if (fActivationCodes != null) {
            fActivationCodes.clear();
            fActivationCodes = null;
        }
        if (scriptSourceViewerConfiguration != null) {
            if (scriptSourceViewerConfiguration instanceof IDisposable) {
                ((IDisposable) scriptSourceViewerConfiguration).dispose();
            }
            scriptSourceViewerConfiguration = null;
        }

        if (verticalRuler != null) {
            verticalRuler = null;
        }

        IOperationHistory history =
                OperationHistoryFactory.getOperationHistory();
        if (history != null) {
            if (fNonLocalOperationApprover != null) {
                history.removeOperationApprover(fNonLocalOperationApprover);
            }
            if (fLinearUndoViolationApprover != null) {
                history.removeOperationApprover(fLinearUndoViolationApprover);
            }
        }
        fNonLocalOperationApprover = null;
        fLinearUndoViolationApprover = null;
    }

    protected AbstractLineStyleListener getLineStyleListener() {
        if (lineStyleListenerProvider != null) {
            fLineStyleListener =
                    lineStyleListenerProvider.getLineStyleListener();
            if (fLineStyleListener != null) {
                fLineStyleListener.init(getScriptDoc(), scriptViewer);
            }
        }
        return fLineStyleListener;
    }

    public void setLineStyleListenerProvider(
            AbstractLineStyleListenerProvider lineStyleListenerProvider) {
        this.lineStyleListenerProvider = lineStyleListenerProvider;
    }

    /**
     * Returns {@link #createCompositeRuler()}. Subclasses should not override
     * this method, but rather <code>createCompositeRuler</code> if they want to
     * contribute their own vertical ruler implementation. If not an instance of
     * {@link CompositeRuler} is returned, the built-in ruler columns (line
     * numbers, annotations) will not work.
     * 
     * <p>
     * May become <code>final</code> in the future.
     * </p>
     * 
     * @see AbstractTextEditor#createVerticalRuler()
     */
    protected IVerticalRuler createVerticalRuler() {
        CompositeRuler ruler = createCompositeRuler();
        IPreferenceStore store = this.fPreferenceStore;
        if (ruler != null && store != null) {
            for (Iterator iter = ruler.getDecoratorIterator(); iter.hasNext();) {
                IVerticalRulerColumn column =
                        (IVerticalRulerColumn) iter.next();
                if (column instanceof AnnotationRulerColumn) {
                    fAnnotationRulerColumn = (AnnotationRulerColumn) column;
                    for (Iterator iter2 =
                            getMarkerAnnotationPreferences()
                                    .getAnnotationPreferences().iterator(); iter2
                            .hasNext();) {
                        AnnotationPreference preference =
                                (AnnotationPreference) iter2.next();
                        String key = preference.getVerticalRulerPreferenceKey();
                        boolean showAnnotation = true;
                        if (key != null && store.contains(key))
                            showAnnotation = store.getBoolean(key);
                        if (showAnnotation)
                            fAnnotationRulerColumn.addAnnotationType(preference
                                    .getAnnotationType());
                    }
                    fAnnotationRulerColumn
                            .addAnnotationType(Annotation.TYPE_UNKNOWN);
                    break;
                }
            }
        }
        return ruler;
    }

    /**
     * Creates a composite ruler to be used as the vertical ruler by this
     * editor. Subclasses may re-implement this method.
     * 
     * @return the vertical ruler
     */
    protected CompositeRuler createCompositeRuler() {
        CompositeRuler ruler = new CompositeRuler();
        ruler.addDecorator(0, createAnnotationRulerColumn(ruler));
        if (PreferenceUtils.isLineNumberRulerVisible()) {
            ruler.addDecorator(1,
                    fPropertyChangeListener.createLineNumberRulerColumn());
        } else if (PreferenceUtils.isPrefQuickDiffAlwaysOn()) {
            ruler.addDecorator(1, createChangeRulerColumn());
        }
        return ruler;
    }

    /**
     * Creates a new change ruler column for quick diff display independent of
     * the line number ruler column
     * 
     * @return a new change ruler column
     */
    protected IChangeRulerColumn createChangeRulerColumn() {
        IChangeRulerColumn column =
                new ChangeRulerColumn(PreferenceUtils.getSharedColors());
        column.setHover(PreferenceUtils.getChangeHover());
        fChangeRulerColumn = column;
        fPropertyChangeListener.initializeChangeRulerColumn(fChangeRulerColumn);
        return fChangeRulerColumn;
    }

    /**
     * Returns the source viewer decoration support.
     * 
     * @param viewer
     *            the viewer for which to return a decoration support
     * @return the source viewer decoration support
     */
    protected void setDecorationSupport() {
        if (fSourceViewerDecorationSupport == null) {
            fSourceViewerDecorationSupport =
                    new SourceViewerDecorationSupport(this.scriptViewer,
                            getOverviewRuler(), getAnnotationAccess(),
                            PreferenceUtils.getSharedColors());
            configureSourceViewerDecorationSupport(fSourceViewerDecorationSupport);

        }
    }

    protected void configureSourceViewerDecorationSupport(
            SourceViewerDecorationSupport support) {
        support.setCharacterPairMatcher(fBracketMatcher);
        support.setMatchingCharacterPainterPreferenceKeys(Consts.MATCHING_BRACKETS,
                Consts.MATCHING_BRACKETS_COLOR);
        // pa_TODO we should inherit values from either the text pref page or
        // java pref page for annotations
        Iterator e =
                getMarkerAnnotationPreferences().getAnnotationPreferences()
                        .iterator();
        while (e.hasNext()) {
            support.setAnnotationPreference((AnnotationPreference) e.next());
        }
        support.setCursorLinePainterPreferenceKeys(CURRENT_LINE,
                CURRENT_LINE_COLOR);
        support.setMarginPainterPreferenceKeys(PRINT_MARGIN,
                PRINT_MARGIN_COLOR,
                PRINT_MARGIN_COLUMN);
        support.setSymbolicFontName(PreferenceUtils
                .getFontPropertyPreferenceKey());
        support.install(this.fPreferenceStore);
    }

    /**
     * Creates this editor's undo/redo actions.
     * <p>
     * Subclasses may override or extend.
     * </p>
     * 
     * @since 3.1
     */
    protected void createUndoRedoActions() {
        IUndoContext undoContext = null;
        IUndoManager undoManager = getSourceViewer().getUndoManager();
        if (undoManager instanceof IUndoManagerExtension) {
            undoContext =
                    ((IUndoManagerExtension) undoManager).getUndoContext();
        }
        if (undoContext != null) {
            // Use actions provided by global undo/redo

            // Create the undo action
            OperationHistoryActionHandler undoAction =
                    new UndoActionHandler(getWorkbenchPartSite(), undoContext);
            PlatformUI
                    .getWorkbench()
                    .getHelpSystem()
                    .setHelp(undoAction,
                            IAbstractTextEditorHelpContextIds.UNDO_ACTION);
            undoAction
                    .setActionDefinitionId(IWorkbenchActionDefinitionIds.UNDO);
            registerUndoRedoAction(ITextEditorActionConstants.UNDO, undoAction);

            // Create the redo action.
            OperationHistoryActionHandler redoAction =
                    new RedoActionHandler(getWorkbenchPartSite(), undoContext);
            PlatformUI
                    .getWorkbench()
                    .getHelpSystem()
                    .setHelp(redoAction,
                            IAbstractTextEditorHelpContextIds.REDO_ACTION);
            redoAction
                    .setActionDefinitionId(IWorkbenchActionDefinitionIds.REDO);
            registerUndoRedoAction(ITextEditorActionConstants.REDO, redoAction);

            // Install operation approvers
            IOperationHistory history =
                    OperationHistoryFactory.getOperationHistory();

            // The first approver will prompt when operations affecting outside
            // elements are to be undone or redone.
            if (fNonLocalOperationApprover != null) {
                history.removeOperationApprover(fNonLocalOperationApprover);
            }
            fNonLocalOperationApprover =
                    getUndoRedoOperationApprover(undoContext);
            history.addOperationApprover(fNonLocalOperationApprover);

            // The second approver will prompt from this editor when an undo is
            // attempted on an operation
            // and it is not the most recent operation in the editor.
            if (fLinearUndoViolationApprover != null) {
                history.removeOperationApprover(fLinearUndoViolationApprover);
            }
            fLinearUndoViolationApprover =
                    new LinearUndoViolationUserApprover(undoContext,
                            getWorkbenchPartSite().getPart());
            history.addOperationApprover(fLinearUndoViolationApprover);

        } else {
            // Use text operation actions (pre 3.1 style)

            TextViewerAction action;

            if (getAction(ITextEditorActionConstants.UNDO) == null) {
                action =
                        new TextOperationAction(this, ITextOperationTarget.UNDO);
                action.setText(Messages.JScriptSourceViewer_UndoAction);
                action.setActionDefinitionId(IWorkbenchActionDefinitionIds.UNDO);
                setAction(ITextEditorActionConstants.UNDO, action);
            }

            if (getAction(ITextEditorActionConstants.REDO) == null) {
                action =
                        new TextOperationAction(this, ITextOperationTarget.REDO);
                action.setText(Messages.JScriptSourceViewer_RedoAction);
                action.setActionDefinitionId(IWorkbenchActionDefinitionIds.REDO);
                setAction(ITextEditorActionConstants.REDO, action);
            }
        }
    }

    /**
     * Registers the given undo/redo action under the given ID and ensures that
     * previously installed actions get disposed. It also takes care of
     * re-registering the new action with the global action handler.
     * 
     * @param actionId
     *            the action id under which to register the action
     * @param action
     *            the action to register
     * @since 3.1
     */
    private void registerUndoRedoAction(String actionId,
            OperationHistoryActionHandler action) {
        IAction oldAction = getAction(actionId);
        if (oldAction instanceof OperationHistoryActionHandler) {
            ((OperationHistoryActionHandler) oldAction).dispose();
        }
        setAction(actionId, action);
        registerGlobalAction(actionId, action);
    }

    private void registerGlobalAction(String actionId, IAction action) {
        IWorkbenchPartSite workbenchPartSite = getWorkbenchPartSite();
        if (workbenchPartSite instanceof IViewSite) {
            IActionBars actionBars =
                    ((IViewSite) getWorkbenchPartSite()).getActionBars();
            if (actionBars != null) {
                actionBars.setGlobalActionHandler(actionId, action);
            }
        }
    }

    /**
     * Return an {@link IOperationApprover} appropriate for approving the undo
     * and redo of operations that have the specified undo context.
     * <p>
     * Subclasses may override.
     * </p>
     * 
     * @param undoContext
     *            the IUndoContext of operations that should be examined by the
     *            operation approver
     * @return the <code>IOperationApprover</code> appropriate for approving
     *         undo and redo operations inside this editor, or <code>null</code>
     *         if no approval is needed
     * @since 3.1
     */
    protected IOperationApprover getUndoRedoOperationApprover(
            IUndoContext undoContext) {
        return new NonLocalUndoUserApprover(undoContext,
                getWorkbenchPartSite(), new Object[] { this.getScriptDoc() },
                Object.class);
    }

    /**
     * Creates the annotation ruler column. Subclasses may re-implement or
     * extend.
     * 
     * @param ruler
     *            the composite ruler that the column will be added
     * @return an annotation ruler column
     * @since 3.2
     */
    protected IVerticalRulerColumn createAnnotationRulerColumn(
            CompositeRuler ruler) {
        return new AnnotationRulerColumn(VERTICAL_RULER_WIDTH,
                getAnnotationAccess());
    }

    /**
     * Returns the annotation access.
     * 
     * @return the annotation access
     */
    protected IAnnotationAccess getAnnotationAccess() {
        if (fAnnotationAccess == null)
            fAnnotationAccess = createAnnotationAccess();
        return fAnnotationAccess;
    }

    /**
     * Creates the annotation access for this editor.
     * 
     * @return the created annotation access
     */
    protected IAnnotationAccess createAnnotationAccess() {
        return new DefaultMarkerAnnotationAccess();
    }

    private IOverviewRuler createOverViewRuler() {
        IOverviewRuler ruler =
                new OverviewRuler(getAnnotationAccess(), VERTICAL_RULER_WIDTH,
                        PreferenceUtils.getSharedColors());
        Iterator e =
                getMarkerAnnotationPreferences().getAnnotationPreferences()
                        .iterator();
        while (e.hasNext()) {
            AnnotationPreference preference = (AnnotationPreference) e.next();
            if (preference.contributesToHeader())
                ruler.addHeaderAnnotationType(preference.getAnnotationType());
        }
        return ruler;
    }

    @Override
    public Control getControl() {
        return getSourceViewer().getControl();
    }

    /**
     * Sets this editor's preference store. This method must be called before
     * the editor's control is created.
     * 
     * @param store
     *            the preference store or <code>null</code> to remove the
     *            preference store
     */
    protected void setPreferenceStore(IPreferenceStore store) {
        if (fPreferenceStore != null) {
            fPreferenceStore
                    .removePropertyChangeListener(fPropertyChangeListener);
        }

        fPreferenceStore = store;

        if (fPreferenceStore != null) {
            fPreferenceStore.addPropertyChangeListener(fPropertyChangeListener);
        }
    }

    protected SourceViewerConfiguration getSourceViewerConfiguration() {
        if (scriptSourceViewerConfiguration == null) {
            scriptSourceViewerConfiguration =
                    new ScriptSourceViewerConfiguration();
        }
        if (getScriptInfoProvider() != null) {
            scriptSourceViewerConfiguration.setScriptInfoProvider(this
                    .getScriptInfoProvider());
        }
        if (getClassDefinitionReaders() != null) {
            scriptSourceViewerConfiguration.setClassDefinitionReaders(this
                    .getClassDefinitionReaders());
        }
        if (getValidationStrategies() != null) {
            scriptSourceViewerConfiguration.setValidationStrategyList(this
                    .getValidationStrategies());
        }
        if (getGrammarType() != null) {
            scriptSourceViewerConfiguration.setGrammarType(getGrammarType());
        }

        if (getContentAssistProcessor() != null) {
            scriptSourceViewerConfiguration
                    .setContentAssistProcessor(getContentAssistProcessor());
        }
        return scriptSourceViewerConfiguration;
    }

    /**
     * Sets the annotation which this editor uses to represent the highlight
     * range if the editor is configured to show the entire document. If the
     * range indicator is not set, this editor will not show a range indication.
     * 
     * @param rangeIndicator
     *            the annotation
     */
    protected void setRangeIndicator(Annotation rangeIndicator) {
        Assert.isNotNull(rangeIndicator);
        fRangeIndicator = rangeIndicator;
    }

    /**
     * Returns the editor's range indicator. May return <code>null</code> if no
     * range indicator is installed.
     * 
     * @return the editor's range indicator which may be <code>null</code>
     */
    protected Annotation getRangeIndicator() {
        return fRangeIndicator;
    }

    public SourceViewer getSourceViewer() {
        return this.scriptViewer;
    }

    protected IOverviewRuler getOverviewRuler() {
        return this.overViewRuler;
    }

    protected IVerticalRuler getVerticalRuler() {
        return this.verticalRuler;
    }

    protected SourceViewerDecorationSupport getDecorationSupport() {
        return this.fSourceViewerDecorationSupport;
    }

    protected MarkerAnnotationPreferences getMarkerAnnotationPreferences() {
        return PreferenceUtils.getMarkerAnnotationPreferences();
    }

    protected AnnotationRulerColumn getAnnotationRuleColumn() {
        return this.fAnnotationRulerColumn;
    }

    /**
     * Representation of action activation codes.
     */
    static class ActionActivationCode {

        /** The action id. */
        public String fActionId;

        /** The character. */
        public char fCharacter;

        /** The key code. */
        public int fKeyCode = -1;

        /** The state mask. */
        public int fStateMask = SWT.DEFAULT;

        /**
         * Creates a new action activation code for the given action id.
         * 
         * @param actionId
         *            the action id
         */
        public ActionActivationCode(String actionId) {
            fActionId = actionId;
        }

        /**
         * Returns <code>true</code> if this activation code matches the given
         * verify event.
         * 
         * @param event
         *            the event to test for matching
         * @return whether this activation code matches <code>event</code>
         */
        public boolean matches(VerifyEvent event) {
            return (event.character == fCharacter
                    && (fKeyCode == -1 || event.keyCode == fKeyCode) && (fStateMask == SWT.DEFAULT || event.stateMask == fStateMask));
        }
    }

    /**
     * Returns this editor's selection changed listener to be installed on the
     * editor's source viewer.
     * 
     * @return the listener
     */
    protected final ISelectionChangedListener getSelectionChangedListener() {
        if (fSelectionChangedListener == null) {
            fSelectionChangedListener = new ISelectionChangedListener() {

                private Runnable fRunnable = new Runnable() {
                    @Override
                    public void run() {
                        // check whether editor has not been disposed yet
                        if (getSourceViewer() != null && getScriptDoc() != null) {
                            updateSelectionDependentActions();
                        }
                    }
                };

                private Display fDisplay;

                @Override
                public void selectionChanged(SelectionChangedEvent event) {
                    if (fDisplay == null) {
                        fDisplay = XpdResourcesPlugin.getStandardDisplay();
                    }
                    fDisplay.asyncExec(fRunnable);
                    handleCursorPositionChanged();
                }
            };
        }
        return fSelectionChangedListener;
    }

    /**
     * Updates all selection dependent actions.
     */
    protected void updateSelectionDependentActions() {
        if (fSelectionActions != null) {
            Iterator e = fSelectionActions.iterator();
            while (e.hasNext())
                updateAction((String) e.next());
        }
    }

    /**
     * Marks or unmarks the given action to be updated on text selection
     * changes.
     * 
     * @param actionId
     *            the action id
     * @param mark
     *            <code>true</code> if the action is selection dependent
     */
    public void markAsSelectionDependentAction(String actionId, boolean mark) {
        Assert.isNotNull(actionId);
        if (mark) {
            if (!fSelectionActions.contains(actionId))
                fSelectionActions.add(actionId);
        } else
            fSelectionActions.remove(actionId);
    }

    /**
     * Updates the specified action by calling <code>IUpdate.update</code> if
     * applicable.
     * 
     * @param actionId
     *            the action id
     */
    private void updateAction(String actionId) {
        Assert.isNotNull(actionId);
        if (fActions != null) {
            IAction action = fActions.get(actionId);
            if (action instanceof IUpdate)
                ((IUpdate) action).update();
        }
    }

    /**
     * Handles a potential change of the cursor position. Subclasses may extend.
     * 
     * @since 2.0
     */
    protected void handleCursorPositionChanged() {
        // TODO
        // updateStatusField(ITextEditorActionConstants.STATUS_CATEGORY_INPUT_POSITION);
    }

    /** The text context menu to be disposed. */
    private Menu fTextContextMenu;

    /** Menu id for the editor context menu. */
    public final static String DEFAULT_EDITOR_CONTEXT_MENU_ID =
            "#EditorContext"; //$NON-NLS-1$

    /** Context menu listener. */
    private IMenuListener fMenuListener;

    private MenuManager manager;

    private CommandProvider commandProvider;

    private ISiteProvider siteProvider;

    private IScriptInfoProvider scriptInfoProvider;

    private IScriptCompiler scriptCompiler;

    /**
     * Creates and returns the listener on this editor's context menus.
     * 
     * @return the menu listener
     */
    protected final IMenuListener getContextMenuListener() {
        if (fMenuListener == null) {
            fMenuListener = new IMenuListener() {

                @Override
                public void menuAboutToShow(IMenuManager menu) {
                    String id = menu.getId();
                    /*
                     * if (getRulerContextMenuId().equals(id)) { setFocus();
                     * rulerContextMenuAboutToShow(menu); } else
                     */if (getEditorContextMenuId().equals(id)) {
                        setFocus();
                        editorContextMenuAboutToShow(menu);
                    }
                }
            };
        }
        return fMenuListener;
    }

    /**
     * Returns the editor's context menu id. May return <code>null</code> before
     * the editor's part has been created.
     * 
     * @return the editor's context menu id which may be <code>null</code>
     */
    protected final String getEditorContextMenuId() {
        return DEFAULT_EDITOR_CONTEXT_MENU_ID;
    }

    /*
     * @see IWorkbenchPart#setFocus()
     */
    public void setFocus() {
        if (this.scriptViewer != null
                && this.scriptViewer.getTextWidget() != null)
            this.scriptViewer.getTextWidget().setFocus();
    }

    /**
     * Sets up this editor's context menu before it is made visible.
     * <p>
     * Subclasses may extend to add other actions.
     * </p>
     * 
     * @param menu
     *            the menu
     */
    protected void editorContextMenuAboutToShow(IMenuManager menu) {

        menu.add(new Separator(ITextEditorActionConstants.GROUP_UNDO));
        menu.add(new GroupMarker(ITextEditorActionConstants.GROUP_SAVE));
        menu.add(new Separator(ITextEditorActionConstants.GROUP_COPY));
        menu.add(new Separator(ITextEditorActionConstants.GROUP_PRINT));
        menu.add(new Separator(ITextEditorActionConstants.GROUP_EDIT));
        menu.add(new Separator(ITextEditorActionConstants.GROUP_FIND));
        menu.add(new Separator(IWorkbenchActionConstants.GROUP_ADD));
        menu.add(new Separator(ITextEditorActionConstants.GROUP_REST));
        menu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));

        addAction(menu,
                ITextEditorActionConstants.GROUP_UNDO,
                ITextEditorActionConstants.UNDO);
        addAction(menu,
                ITextEditorActionConstants.GROUP_UNDO,
                ITextEditorActionConstants.REVERT_TO_SAVED);
        addAction(menu,
                ITextEditorActionConstants.GROUP_SAVE,
                ITextEditorActionConstants.SAVE);
        addAction(menu,
                ITextEditorActionConstants.GROUP_COPY,
                ITextEditorActionConstants.CUT);
        addAction(menu,
                ITextEditorActionConstants.GROUP_COPY,
                ITextEditorActionConstants.COPY);
        addAction(menu,
                ITextEditorActionConstants.GROUP_COPY,
                ITextEditorActionConstants.PASTE);
        addAction(menu,
                ITextEditorActionConstants.GROUP_FIND,
                ITextEditorActionConstants.SELECT_ALL);

    }

    /**
     * Convenience method to add the action installed under the given action id
     * to the specified group of the menu.
     * 
     * @param menu
     *            the menu to add the action to
     * @param group
     *            the group in the menu
     * @param actionId
     *            the id of the action to add
     */
    protected final void addAction(IMenuManager menu, String group,
            String actionId) {
        IAction action = getAction(actionId);
        if (action != null) {
            if (action instanceof IUpdate)
                ((IUpdate) action).update();

            IMenuManager subMenu = menu.findMenuUsingPath(group);
            if (subMenu != null)
                subMenu.add(action);
            else
                menu.appendToGroup(group, action);
        }
    }

    /**
     * Marks or unmarks the given action to be updated on content changes.
     * 
     * @param actionId
     *            the action id
     * @param mark
     *            <code>true</code> if the action is content dependent
     */
    public void markAsContentDependentAction(String actionId, boolean mark) {
        Assert.isNotNull(actionId);
        if (mark) {
            if (!fContentActions.contains(actionId))
                fContentActions.add(actionId);
        } else
            fContentActions.remove(actionId);
    }

    /**
     * Updates all content dependent actions.
     */
    public void updateContentDependentActions() {
        if (fContentActions != null) {
            Iterator e = fContentActions.iterator();
            while (e.hasNext())
                updateAction((String) e.next());
        }
    }

    private boolean isIgnoreRefresh() {
        boolean toReturn = false;
        IDocument document = scriptViewer.getDocument();
        String existingDocString = null;
        if (document != null) {
            existingDocString = document.get();
        }
        if (existingDocString != null && newScript != null && !isNewScript()
                && !getScriptSelectionChanged()) {
            if (existingDocString.equals(newScript)) {
                toReturn = true;
            }
        }
        return toReturn;
    }

    @Override
    public void installCommandProvider(CommandProvider provider) {
        this.commandProvider = provider;
    }

    public CommandProvider getCommandProvider() {
        return this.commandProvider;
    }

    @Override
    public void installSiteProvider(ISiteProvider siteProvider) {
        this.siteProvider = siteProvider;
    }

    @Override
    public void installScriptInfoProvider(IScriptInfoProvider scriptInfoProvider) {
        if (this.scriptInfoProvider != null
                && this.scriptInfoProvider instanceof IScriptInfoProviderExt) {
            ((IScriptInfoProviderExt) this.scriptInfoProvider)
                    .clearCachedInfo();
        }
        this.setScriptInfoProvider(scriptInfoProvider);
    }

    private void setScriptInfoProvider(IScriptInfoProvider scriptInfoProvider) {
        this.scriptInfoProvider = scriptInfoProvider;
    }

    private IScriptInfoProvider getScriptInfoProvider() {
        return scriptInfoProvider;
    }

    @Override
    public void installScriptCompiler(IScriptCompiler compiler) {
        this.scriptCompiler = compiler;
    }

    public IScriptCompiler getScriptCompiler() {
        return this.scriptCompiler;
    }

    private IDocument getScriptDoc() {
        return scriptDoc;
    }

    public ActivationCodeTrigger getActivationCodeTrigger() {
        return fActivationCodeTrigger;
    }

    public void setVerticalRuler(IVerticalRuler verticalRuler) {
        this.verticalRuler = verticalRuler;
    }

    private List<JsClassDefinitionReader> definitionReaderList =
            Collections.EMPTY_LIST;

    private AbstractScriptCommonUIPreferenceNames scriptCommonUIPreferenceNames;

    @Override
    public void installClassDefinitionReaders(
            List<JsClassDefinitionReader> definitionReaderList) {
        this.definitionReaderList = definitionReaderList;
    }

    public List<JsClassDefinitionReader> getClassDefinitionReaders() {
        return this.definitionReaderList;
    }

    private List<IValidationStrategy> validationStrategyList =
            Collections.EMPTY_LIST;

    @Override
    public void installValidationStrategies(
            List<IValidationStrategy> validationStrategyList) {
        this.validationStrategyList = validationStrategyList;
    }

    public List<IValidationStrategy> getValidationStrategies() {
        return this.validationStrategyList;
    }

    @Override
    public void installResourceMarkerAnnotationModel(
            ResourceMarkerAnnotationModel annotationModel) {
        this.annotationModel = annotationModel;
    }

    public boolean isEnableUndoRedoActions() {
        return enableUndoRedoActions;
    }

    @Override
    public void setEnableUndoRedoActions(boolean enableUndoRedoActions) {
        this.enableUndoRedoActions = enableUndoRedoActions;
    }

    public boolean isEnableCopyAndPasteActions() {
        return enableCopyAndPasteActions;
    }

    @Override
    public void setEnableCopyAndPasteActions(boolean enableCopyAndPasteActions) {
        this.enableCopyAndPasteActions = enableCopyAndPasteActions;
    }

    public boolean isEnableContentAssistActions() {
        return enableContentAssistActions;
    }

    @Override
    public void setEnableContentAssistActions(boolean enableContentAssistActions) {
        this.enableContentAssistActions = enableContentAssistActions;
    }

    public String getGrammarType() {
        return grammarType;
    }

    public void setGrammarType(String grammarType) {
        this.grammarType = grammarType;
    }

    public boolean isNewScript() {
        return isNewScript;
    }

    @Override
    public void setIsNewScript(boolean isNewScript) {
        this.isNewScript = isNewScript;
    }

    /**
     * @return
     */
    public AbstractTibcoContentAssistProcessor getContentAssistProcessor() {
        return contentAssistProcessor;
    }

    /**
     * @param contentAssistProcessor
     *            the contentAssistProcessor to set
     */
    public void setContentAssistProcessor(
            AbstractTibcoContentAssistProcessor contentAssistProcessor) {
        this.contentAssistProcessor = contentAssistProcessor;
    }

    /**
     * @see com.tibco.xpd.script.sourceviewer.client.ScriptEditor#setScriptSelectionChanged(boolean)
     * 
     * @param isScriptSelectionChanged
     */
    @Override
    public void setScriptSelectionChanged(boolean isScriptSelectionChanged) {
        this.isScriptSelectionChanged = isScriptSelectionChanged;
    }

    /**
     * @see com.tibco.xpd.script.sourceviewer.client.ScriptEditor#getScriptSelectionChanged()
     * 
     * @return
     */
    @Override
    public boolean getScriptSelectionChanged() {
        return this.isScriptSelectionChanged;
    }

    /**
     * @param grammarType
     * @return
     */
    public AbstractScriptCommonUIPreferenceNames getScriptCommonUIPreferenceNames(
            String grammarType) {
        return scriptCommonUIPreferenceNames;
    }

    /**
     * @param scriptCommonUIPreferenceNames
     *            the scriptCommonUIPreferenceNames to set
     */
    public void setScriptCommonUIPreferenceNames(
            AbstractScriptCommonUIPreferenceNames scriptCommonUIPreferenceNames) {
        this.scriptCommonUIPreferenceNames = scriptCommonUIPreferenceNames;
    }

    public void addPreferenceStore(IPreferenceStore preferenceStore) {
        PreferenceUtils.addPreferenceStore(preferenceStore);
    }
}