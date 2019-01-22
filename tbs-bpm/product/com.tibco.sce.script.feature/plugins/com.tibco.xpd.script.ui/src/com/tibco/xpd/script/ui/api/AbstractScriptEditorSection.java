/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.script.ui.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.texteditor.ResourceMarkerAnnotationModel;
import org.eclipse.ui.views.properties.PropertySheet;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;
import com.tibco.xpd.script.sourceviewer.client.ISiteProvider;
import com.tibco.xpd.script.sourceviewer.internal.contentassist.AbstractTibcoContentAssistProcessor;
import com.tibco.xpd.script.sourceviewer.internal.preferences.AbstractScriptCommonUIPreferenceNames;
import com.tibco.xpd.script.sourceviewer.internal.viewer.listeners.AbstractLineStyleListenerProvider;
import com.tibco.xpd.script.sourceviewer.viewer.ScriptSourceViewer;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;
import com.tibco.xpd.script.ui.ScriptUIPlugin;
import com.tibco.xpd.script.ui.internal.AbstractScriptInfoProvider;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;

/**
 * This is the abstract section of all script grammar editor sections. Script
 * grammars provider editing features such as content assist, editor styling and
 * so on.
 * 
 * @author rsomayaj
 * 
 */
public abstract class AbstractScriptEditorSection extends
        AbstractFilteredTransactionalSection {

    private boolean selectionChanged;

    private String scriptContext;

    private AbstractScriptInfoProvider scriptProvider;

    private List<?> additionalScriptData = null;

    boolean loadProcessData = true;

    private ScriptSourceViewer scriptViewer;

    private Color defaultForegroundColor;

    /**
     * XPD-5676: Saket: Method to gray out the text in script editor to let the
     * user know that the script editor is not editable.
     */
    public void setEnabled(boolean enable) {

        if (scriptViewer != null) {
            /*
             * defensively adding a null check over here.
             */
            StyledText styledTextControl =
                    scriptViewer.getSourceViewer().getTextWidget();

            if (defaultForegroundColor == null) {
                defaultForegroundColor = styledTextControl.getForeground();
            }

            Color color = defaultForegroundColor;
            if (!enable) {
                Display display = Display.getCurrent();
                color = display.getSystemColor(SWT.COLOR_GRAY);
            }
            styledTextControl.setForeground(color);
        }
    }

    private Label scriptDescLabel;

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    private ISiteProvider siteProvider = new ISiteProvider() {
        public IWorkbenchPartSite getWorkbenchPartSite() {
            return getViewSite();
        }
    };

    private ResourceMarkerAnnotationModel resourceMarkerAnnotationModel;

    private Collection<String> enabledDestinations;

    private boolean debug = false;

    /**
     * 
     * @param eClass
     */
    public AbstractScriptEditorSection(EClass eClass) {
        super(eClass);
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit toolkit) {
        Composite scriptComposite = toolkit.createComposite(parent);
        scriptComposite.setLayout(new GridLayout());
        toolkit.paintBordersFor(scriptComposite);
        /*
         * can't do this as we get class cast exception
         * scriptComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
         */
        scriptDescLabel = toolkit.createLabel(scriptComposite, ""); //$NON-NLS-1$

        /*
         * Sid XPD_7575 : we will re-layotu when we get the label for script on
         * doRefresh()
         */
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.heightHint = 0;
        scriptDescLabel.setLayoutData(gd);

        int styles = SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL;
        scriptViewer =
                new ScriptSourceViewer(scriptComposite, styles,
                        getScriptGrammar());

        try {
            AbstractTibcoContentAssistProcessor contentAssistProcessor =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getContentAssistProcessor(getScriptGrammar());
            scriptViewer.setContentAssistProcessor(contentAssistProcessor);

            scriptViewer.addPreferenceStore(getPreferenceStore());

            AbstractScriptCommonUIPreferenceNames scriptCommonUIPreferenceNames =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getScriptCommonUIPreferenceNames(getScriptGrammar());
            scriptViewer
                    .setScriptCommonUIPreferenceNames(scriptCommonUIPreferenceNames);

            AbstractLineStyleListenerProvider lineStyleListenerProvider =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getLineStyleListenerProvider(getScriptGrammar());
            scriptViewer
                    .setLineStyleListenerProvider(lineStyleListenerProvider);
        } catch (CoreException e) {
            LOG.error(e);
        }
        scriptViewer.installSiteProvider(siteProvider);
        scriptViewer.configure();
        scriptViewer.getControl()
                .setLayoutData(new GridData(GridData.FILL_BOTH));
        scriptViewer.getControl().setData(FormToolkit.KEY_DRAW_BORDER,
                FormToolkit.TREE_BORDER);

        return scriptComposite;
    }

    /**
     * @return
     */
    protected IPreferenceStore getPreferenceStore() {
        return ScriptUIPlugin.getDefault().getPreferenceStore();
    }

    @Override
    protected Command doGetCommand(Object obj) {
        return UnexecutableCommand.INSTANCE;
    }

    @Override
    protected void doRefresh() {
        String strScript = getScript();
        if (scriptViewer != null) {
            scriptViewer.setIsNewScript(isNewScriptInformation());
            if (isSelectionChanged()) {
                scriptViewer.setScriptSelectionChanged(true);
                setSelectionChanged(false);
            } else {
                scriptViewer.setScriptSelectionChanged(false);
            }
            scriptViewer.setInputString(strScript);

            scriptViewer.installSiteProvider(siteProvider);
            scriptViewer.installScriptInfoProvider(getScriptDetailsProvider());
            scriptViewer
                    .installResourceMarkerAnnotationModel(getResourceMarkerAnnotationModel());
            scriptViewer.installCommandProvider(getScriptDetailsProvider());
            scriptViewer.installClassDefinitionReaders(getDefinitionReaders());
            scriptViewer.installValidationStrategies(getValidationStrategies());

            /*
             * Sid XPD-7575 - get rid of wasted space when ther's no label for
             * script.
             */
            String descLabel = getScriptDescLabel();
            if (descLabel == null) {
                descLabel = ""; //$NON-NLS-1$
            }

            if (!descLabel.equals(scriptDescLabel.getText())) {
                scriptDescLabel.setText(descLabel);

                GridData gd = new GridData(GridData.FILL_HORIZONTAL);

                if (descLabel.length() == 0) {
                    gd.heightHint = 0;
                }
                scriptDescLabel.setLayoutData(gd);
                scriptDescLabel.getParent().layout(true);
            }

            boolean enabled = getInput() != null;
            scriptViewer.getControl().setEnabled(enabled);
            Color bg =
                    Display.getDefault()
                            .getSystemColor(enabled ? SWT.COLOR_WHITE
                                    : SWT.COLOR_WIDGET_BACKGROUND);
            scriptViewer.getControl().setBackground(bg);
            if (scriptViewer instanceof ScriptSourceViewer) {
                ScriptSourceViewer ssv = scriptViewer;
                SourceViewer sv = ssv.getSourceViewer();
                StyledText widget = sv.getTextWidget();
                widget.setEnabled(enabled);
                widget.setBackground(bg);
            }
            scriptViewer.doRefresh();
            // second call to doRefresh() is a hack to
            // solve the content assist problem defect 28061
            scriptViewer.doRefresh();
        }
    }

    /**
     * 
     * @param scriptContext
     */
    public void setScriptContext(String scriptContext) {
        this.scriptContext = scriptContext;
    }

    /**
     * 
     * @return
     */
    public String getScriptContext() {
        return scriptContext;
    }

    /**
     * 
     * @return
     */
    protected IViewSite getViewSite() {
        IViewSite propertySite = null;
        IWorkbenchWindow activeWorkbenchWindow =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        if (activeWorkbenchWindow == null) {
            return propertySite;
        }
        IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
        if (activePage == null) {
            return propertySite;
        }
        IViewReference[] viewReferences = activePage.getViewReferences();
        for (int index = 0; index < viewReferences.length; index++) {
            IWorkbenchPart tempPart = viewReferences[index].getPart(false);
            if (tempPart instanceof PropertySheet) {
                propertySite = (IViewSite) tempPart.getSite();
                break;
            }
        }
        return propertySite;
    }

    /**
     * @param scriptProvider
     */
    public void setScriptProvider(AbstractScriptInfoProvider scriptProvider) {
        this.scriptProvider = scriptProvider;
    }

    /**
     * 
     * @return
     */
    public AbstractScriptInfoProvider getScriptDetailsProvider() {
        return scriptProvider;
    }

    /**
     * 
     * @param editingDomain
     * @param strScript
     * @param eObject
     * @return
     */
    protected Command getSetScriptCommand(EditingDomain editingDomain,
            String strScript, EObject eObject) {
        Command cmd = null;
        if (getScriptDetailsProvider() != null) {
            updateScriptDetailsProvider();

            cmd =
                    getScriptDetailsProvider()
                            .getSetScriptCommand(editingDomain,
                                    eObject,
                                    strScript,
                                    getScriptGrammar());
        }
        return cmd;
    }

    /**
     * 
     * @return
     */
    public String getScript() {
        String script = null;
        if (getScriptDetailsProvider() != null && getInput() != null) {
            getScriptDetailsProvider().setScriptContext(getScriptContext());
            getScriptDetailsProvider().setScriptGrammar(getScriptGrammar());
            script = getScriptDetailsProvider().getScript(getInput());
        }
        return script;
    }

    /**
     * 
     * @return
     */
    protected EObject getRelevantEObject() {
        EObject relevant = null;
        if (getScriptDetailsProvider() != null) {
            updateScriptDetailsProvider();

            relevant =
                    getScriptDetailsProvider().getRelevantEObject(getInput());
        }
        return relevant;
    }

    /**
     * 
     * @return
     */
    protected String getScriptDescLabel() {
        String toReturn = ""; //$NON-NLS-1$
        if (getScriptDetailsProvider() != null) {
            updateScriptDetailsProvider();

            toReturn =
                    getScriptDetailsProvider().getDescriptionLabel(getInput());

        }
        return toReturn;
    }

    /**
     * 
     * @return
     */
    public List<?> getAdditionalScriptData() {
        if (additionalScriptData == null) {
            additionalScriptData = new ArrayList<Object>();
        }
        return additionalScriptData;
    }

    /**
     * 
     * @param additionalScriptData
     */
    public void setAdditionalScriptData(List<?> additionalScriptData) {
        this.additionalScriptData = additionalScriptData;
    }

    /**
     * 
     * @return
     */
    public boolean isLoadProcessData() {
        return loadProcessData;
    }

    /**
     * 
     * @param loadProcessData
     */
    public void setLoadProcessData(boolean loadProcessData) {
        this.loadProcessData = loadProcessData;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractEObjectSection#setInput(java.util.Collection)
     * 
     * @param items
     */
    @Override
    public void setInput(Collection<?> items) {
        super.setInput(items);
        /*
         * if (getScriptDetailsProvider() != null && getInput() != null) {
         * getScriptDetailsProvider().setInput(getInput()); }
         */
        setSelectionChanged(true);

        /*
         * Sid: Noticed that sometimes when changing between mapping sections
         * with editor section showing a script mapping, when changed to a
         * different section the old script was still shown in the editor
         * section (this is becuase the input is set to null and when this
         * happens then refresh won't call doRefresh()).
         */
        if (getInput() == null || isNewScriptInformation()) {
            doRefresh();
        }
        return;
    }

    /**
     * 
     * @param selectionChanged
     */
    public void setSelectionChanged(boolean selectionChanged) {
        this.selectionChanged = selectionChanged;
    }

    /**
     * 
     * @return
     */
    public boolean isSelectionChanged() {
        return this.selectionChanged;
    }

    /**
     * @return
     */
    protected abstract String getScriptGrammar();

    /**
     * This provides the ResourceAnnotationModel.
     * 
     * This method needs to be over-riden by the subclass that which uses the
     * source viewer instantiated in this class.
     * 
     * @return
     */
    protected ResourceMarkerAnnotationModel getResourceMarkerAnnotationModel() {
        return resourceMarkerAnnotationModel;
    }

    /**
     * @param resourceMarkerAnnotationModel
     */
    public void setResourceMarkerAnnotationModel(
            ResourceMarkerAnnotationModel resourceMarkerAnnotationModel) {
        this.resourceMarkerAnnotationModel = resourceMarkerAnnotationModel;

    }

    /**
     * Tests to provide whether the script information is a new Script
     * Information.
     * 
     * Appropriate information needs to be provided by sub-class.
     * 
     * @return
     */
    protected Boolean isNewScriptInformation() {
        if (getScriptDetailsProvider() != null) {
            updateScriptDetailsProvider();
            return getScriptDetailsProvider()
                    .isNewScriptInformation(getInput());
        }
        return Boolean.FALSE;

    }

    /**
     * 
     */
    private void updateScriptDetailsProvider() {
        if (debug) {
            getScriptDetailsProvider().setInput(getInput());
            getScriptDetailsProvider().setScriptContext(getScriptContext());
            getScriptDetailsProvider().setScriptGrammar(getScriptGrammar());
        }
    }

    /**
     * This method should be implemented to handle grammar change in the combo
     * 
     * @param editingDomain
     * @param eObject
     * @return
     */
    public Command getSetScriptGrammarCommand(EditingDomain editingDomain,
            EObject eObject) {
        if (getScriptDetailsProvider() != null) {
            updateScriptDetailsProvider();

            return getScriptDetailsProvider()
                    .getSetScriptGrammarCommand(editingDomain,
                            eObject,
                            getScriptGrammar());
        }
        return null;
    }

    protected List<JsClassDefinitionReader> getDefinitionReaders() {
        return Collections.EMPTY_LIST;
    }

    protected List<IValidationStrategy> getValidationStrategies() {
        return Collections.EMPTY_LIST;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#dispose()
     * 
     */
    @Override
    public void dispose() {
        super.dispose();
        if (scriptViewer != null) {
            scriptViewer.dispose();
        }
    }

    /**
     * @param enabledDestinations
     */
    public void setEnabledDestinations(Collection<String> enabledDestinations) {
        this.enabledDestinations = enabledDestinations;
    }

    /**
     * @return the enabledDestinations
     */
    public Collection<String> getEnabledDestinations() {
        return enabledDestinations;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#isInputRemoved(java.util.List)
     * 
     * @param notifications
     * @return
     */
    @Override
    protected boolean isInputRemoved(List<Notification> notifications) {
        if (super.isInputRemoved(notifications)) {
            /*
             * Sid XPD-3792: We were experiencing problems when script mapping
             * deleted because the input to the script editor section isn't
             * necessarily the main input for property tab. BUT
             * AbstractTransactionalSection.isInputRemoved() only checks for a
             * remove notification on teh input that is set on the section.
             * 
             * This means that if the ScriptInformation input on this section is
             * removed and re-added elsewhere then actually OUR input has not
             * been removed and we should therefore not say it has.
             * 
             * If we don't do this then AbstractTransactionalSection will wipe
             * out the selection for the entire property tab.
             */
            if (getInput() != null) {
                if (getInput().eContainer() == null) {
                    /*
                     * OK our input truly has been removed rather than removed
                     * and readded.
                     */
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Allows the sub-classes to contribute controls on the RHS of grammar
     * selection combo
     * 
     * @param parent
     * @return
     */
    public Control createGrammarSelectorRHSControls(Composite parent) {
        return null;
    }

    /**
     * Sid XPD-7575 - Allow currently selected script section to adjust the
     * offset on it's container (some sections are nested sections and in that
     * case they do not have a choice of their own container's margins and hence
     * we get much wasted space)
     * 
     * @return Point.x=left offset, y= top offset Offset (can be negative if
     *         desired). <code>null</code> if no offset required
     */
    public Point getSectionMarginOffset() {
        return null;
    }
}
