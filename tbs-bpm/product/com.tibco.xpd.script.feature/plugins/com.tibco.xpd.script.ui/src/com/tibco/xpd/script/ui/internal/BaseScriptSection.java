/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.script.ui.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;
import com.tibco.xpd.script.ui.api.AbstractScriptEditorSection;
import com.tibco.xpd.script.ui.internal.extension.ScriptGrammarDestinationBindingElement.ScriptGrammarReferenceElement;
import com.tibco.xpd.script.ui.internal.extension.ScriptGrammarElement;
import com.tibco.xpd.ui.properties.AbstractFilteredTransactionalSection;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.ui.util.CapabilityUtil;

/**
 * Base Script Section is the abstract property section that displays the
 * grammar selection combo and the script editor contributed by the grammar
 * itself.
 * 
 * @author kupadhya
 * @author rsomayaj
 * 
 */
public abstract class BaseScriptSection extends
        AbstractFilteredTransactionalSection {

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

	/**
	 * if {@link #isUseOwnScriptInfoProviderInstance()} is true then this field stores the instance that was created on the last
	 * call to {@link #getScriptInfoProvider(String)} for a given grammarid
	 */
	private AbstractScriptInfoProvider							scriptInfoProviderOwnInstance			= null;

	/**
	 * if {@link #isUseOwnScriptInfoProviderInstance()} is true then this field stores the grammarId for which
	 * {@link #scriptInfoProviderOwnInstance} was created. Therefore if grammar is changed then
	 * {@link #scriptInfoProviderOwnInstance} can be replaced.
	 */
	private String												scriptInfoProviderOwnInstanceGrammarId	= "";

    /**
     * 
     */
    private static final String JAVA_SCRIPT_GRAMMAR = "JavaScript"; //$NON-NLS-1$

    public static final String UNDEFINED_SCRIPTGRAMMAR = "Unspecified"; //$NON-NLS-1$

    // Map having destinationId as key
    private Map<String, List<EditorSectionComposite>> destIdToEditorSectionCompositeMap =
            null;

    // Map having the grammar element as the key
    private Map<ScriptGrammarElement, EditorSectionComposite> grammarElementToEditorSectionMap =
            null;

    // Map having grammarId as key and ScriptGrammarElement as value
    private Map<String, ScriptGrammarElement> grammarElementMap = null;

    private CCombo grammarTypesCombo;

    private PageBook implTypeBook;

    // Implementation selected before the update is made when a new item is
    // selected in the impl combo
    private EditorSectionComposite previousSelectedEditorSection = null;

    // No implementation page for the pagebook
    private Composite noImplPage;

    private Label noEditorMessage;

    private Object previousEditorInput;

    private Composite sectionComposite;

    private boolean enableGrammar = true;

    protected String instrumentationPrefixName;

    private boolean scriptSelectionChanged;

    /*
     * Sid XPD-4021 - allow sub-classes to create controls below grammar
     * dropdown.
     */
    private Composite grammarExtraContainer;

    /*
     * XPD-7062 - allow sub-classes to create controls on the right side of the
     * grammar dropdown.
     */
    private Composite grammarRHSControlContainer;

	private FormText											scriptDefinitionLabel;

	private String scriptDefinitionLabelStr;

    /**
     * Class contains the implementation extension and the book page that
     * belongs to it in the properties view
     */
    protected class EditorSectionComposite {

        public ScriptGrammarElement grammarElement;

        public Composite page;

        private boolean selectable = true;

        public EditorSectionComposite(ScriptGrammarElement grammarElement) {
            this.grammarElement = grammarElement;
            this.page = null;
        }

        public String getGrammarName() {
            return grammarElement.getGrammarName();
        }

        public String getGrammarId() {
            return grammarElement.getGrammarId();
        }

        public AbstractScriptEditorSection getEditorSection() {
            AbstractScriptEditorSection editorSection = null;
            try {
                editorSection = grammarElement.getISectionExec();
            } catch (CoreException ce) {
            }
            return editorSection;
        }

        /**
         * @param set
         *            if user is allowed to select grammar if it's not the
         *            currently selected grammar in the model.
         */
        public void setSelectable(boolean selectable) {
            this.selectable = selectable;
        }

        /**
         * @return true if user is allowed to select grammar if it's not the
         *         currently selected grammar in the model.
         */
        public boolean isSelectable() {
            return selectable;
        }

        public void dispose() {
            if (page != null) {
                page.dispose();
                page = null;
            }
        }

    };

    /**
     * Task type service section
     */
    public BaseScriptSection(EClass inputType) {
        super(inputType);

        grammarElementToEditorSectionMap =
                new HashMap<ScriptGrammarElement, EditorSectionComposite>();
        destIdToEditorSectionCompositeMap =
                new HashMap<String, List<EditorSectionComposite>>();
        grammarElementMap = new HashMap<String, ScriptGrammarElement>();
        readContributedScriptGrammar();
        readGrammarDestinationBindingElement();
    }

	/**
	 * @return <code>false</code> to use singleton instance of ScriptInfoProvider (default) or <code>true</code> to
	 *         create a new instance of {@link AbstractScriptInfoProvider} for the given grammar for every individual
	 *         instance of the subclass. The latter is useful when the lifecycle is not controlled by Tabbed property 
	 *         Sheets and therefore it is necessary to maintain separate providers each with their own input object.
	 */
	protected boolean isUseOwnScriptInfoProviderInstance()
	{
		return false;
	}

    /**
     * @param grammarId
     * @return
     */
    protected AbstractScriptInfoProvider getScriptInfoProvider(String grammarId) {
		// SID: when sub-class requires individual instance of AbstractScriptInfoProvider for given grammar then use
		// existing or create new and save it for next call.
		boolean useOwnScriptInfoProviderInstance = isUseOwnScriptInfoProviderInstance();

		if (useOwnScriptInfoProviderInstance && scriptInfoProviderOwnInstanceGrammarId.equals(grammarId))
		{
			if (scriptInfoProviderOwnInstance != null) {
				return scriptInfoProviderOwnInstance;
			}
		}
		else
		{
			scriptInfoProviderOwnInstance = null;
			scriptInfoProviderOwnInstanceGrammarId = "";
		}

        Collection<String> enabledDestinations =
                getEnabledDestinations(getInput());

        AbstractScriptInfoProvider scriptInfoProvider = null;
        for (String destination : enabledDestinations) {
            try {
                scriptInfoProvider =
                        ScriptGrammarContributionsUtil.INSTANCE
                                .getScriptInfoProvider(destination,
                                        getScriptContext(),
										grammarId, 
										// SID: request new instance of provider if required.
										useOwnScriptInfoProviderInstance);
                if (scriptInfoProvider != null) {
                    break;
                }
            } catch (CoreException e) {
                LOG.error(e, "Retrieving Script Details Provider:"); //$NON-NLS-1$
            }
            if (scriptInfoProvider != null) {
                break;
            }
        }

        if (scriptInfoProvider == null) {

            /*
             * Sid XPD-1718: After checking all enabled destinations for info
             * providers for the grammar, if we did not find a provider then
             * check all destinations for one that can handle grammar - this
             * will allow us to show the editor when the script is already set
             * to a grammar not supported by the currently selected destinations
             * - thus we can help the user to migrate between destinations.
             */
            Set<String> contributedDestinations =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getContributedDestinations();

            for (String destination : contributedDestinations) {
                try {
                    scriptInfoProvider =
                            ScriptGrammarContributionsUtil.INSTANCE
                                    .getScriptInfoProvider(destination,
                                            getScriptContext(),
											grammarId, 
											// SID: request new instance of provider if required.
											useOwnScriptInfoProviderInstance);
                    if (scriptInfoProvider != null) {
                        break;
                    }
                } catch (CoreException e) {
                    LOG.error(e, "Retrieving Script Details Provider:"); //$NON-NLS-1$
                }
                if (scriptInfoProvider != null) {
                    break;
                }
            }

        }

		// SID: when sub-class requires individual instance of AbstractScriptInfoProvider for given grammar then save it
		// for next call. Also save the grammarId the provider is for, so that if grammar changes we will recreate the
		// provider.
		if (useOwnScriptInfoProviderInstance)
		{
			scriptInfoProviderOwnInstance = scriptInfoProvider;
			scriptInfoProviderOwnInstanceGrammarId = grammarId;
		}

        return scriptInfoProvider;
    }

    /**
     * Override this method to provide the additional Script data that will
     * appear in the Content Assist,
     * 
     * The list elements should be of type IScriptRelevantData or
     * ProcessRelevantData
     * 
     * @return
     */
    protected List<?> getAdditionalScriptData() {
        return Collections.EMPTY_LIST;
    }

    /**
     * Override this method if the script should not take into account the
     * process data
     * 
     * @return
     */
    protected boolean isLoadProcessData() {
        return true;
    }

    @Override
    protected Control doCreateControls(final Composite parent,
            XpdFormToolkit toolkit) {
        sectionComposite = toolkit.createComposite(parent);
        toolkit.adapt(sectionComposite);

        GridLayout mainSectionLayout = new GridLayout(1, false);

        mainSectionLayout.marginHeight--;
        sectionComposite.setLayout(mainSectionLayout);

        // If no implementations are available then don't show any controls
        if (destIdToEditorSectionCompositeMap.isEmpty()) {
            return sectionComposite;
        }

        /*
         * Sid XPD-7575 There are 4 horizontal groups for the grammar selection
         * line (that have come into being over the long while this class has
         * existed...
         * 
         * <Descriptive Label> | <Grammar Selection> | <spacer to right-justify
         * next bit> |<Grammar Extra COntrols (can these are set by the
         * currently selected grammar editor section> <<Grammar selection RHS
         * container (replaced on every refresh!)>
         * 
         * IN order to simplify and close things up, we now make sure that we
         * always create the containers for these
         */
        Composite grammarLineContainer =
                toolkit.createComposite(sectionComposite);
        // grammarLineContainer.setBackground(new Color(null, 255, 0, 0));
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        grammarLineContainer.setLayoutData(gd);

		GridLayout gl = new GridLayout(4, false);
        grammarLineContainer.setLayout(gl);

        /* LHS Label */
        scriptDefinitionLabelStr = getScriptDefinitionLabel();
		scriptDefinitionLabel = new NoFocusFormText(grammarLineContainer, SWT.WRAP);
		toolkit.adapt(scriptDefinitionLabel, false, false);

		if (scriptDefinitionLabelStr != null)
		{
			scriptDefinitionLabel.setText(scriptDefinitionLabelStr,
				shouldParseScriptDefinitionLabel(), false);
		}


		// SID: Setup the label and grammar line container layout depending on whether it contains any data
		if (scriptDefinitionLabelStr == null || scriptDefinitionLabelStr.length() == 0)
		{
			// SID: If not showing label then don't fill LHS with empty label
			gd = new GridData(SWT.NONE, SWT.CENTER, false, false);
			gd.heightHint = 1;
			// SID: If we don't have a label then remove margins
			gl.marginWidth = 0;
			gl.marginHeight = 0;
		}
		else
		{
			// SID: Set a width hint to start with otherwise layout will take the text-width as the nominal width
			gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
			gd.widthHint = 1;
		}

		scriptDefinitionLabel.setLayoutData(gd);

        /* Grammar selection label and combo. */
        Composite grammarSelectionContainer =
                toolkit.createComposite(grammarLineContainer);
        // grammarSelectionContainer.setBackground(new Color(null, 0, 0, 255));

		gd = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
        grammarSelectionContainer.setLayoutData(gd);

        gl = new GridLayout(2, false);
        gl.marginWidth = 0;
        gl.marginRight = 2;
        gl.marginHeight = 2;
        gl.marginBottom = 2;
        grammarSelectionContainer.setLayout(gl);

        if (enableGrammar) {
            /*
             * If grammar selection is enabled then show the selector.
             */
            Label label =
                    createLabel(grammarSelectionContainer,
                            toolkit,
                            Messages.BaseScriptSection_ScriptDefinedAs_Label);
            /*
             * Add a little space between LHS label and "Script defined as" when
             * it's defined.
             */
            gd = new GridData();
			if (scriptDefinitionLabelStr != null && scriptDefinitionLabelStr.length() > 0)
			{
                gd.horizontalIndent = 10;
            }
            label.setLayoutData(gd);

            grammarTypesCombo =
                    toolkit.createCCombo(grammarSelectionContainer,
                            SWT.NONE,
                            instrumentationPrefixName + "DefinedScript"); //$NON-NLS-1$
            grammarTypesCombo.setData("name", "comboScriptGrammerTypes"); //$NON-NLS-1$ //$NON-NLS-2$
            grammarTypesCombo
                    .setData(TabbedPropertySheetWidgetFactory.KEY_DRAW_BORDER,
                            TabbedPropertySheetWidgetFactory.TEXT_BORDER);
            grammarTypesCombo.setEditable(false);
            grammarTypesCombo.setLayoutData(new GridData());
        }

		/*
		 * SPacer to right-justify grammarRHSControlCOntainer
		 */
		Composite spacer = toolkit.createComposite(grammarLineContainer);
		gl = new GridLayout();
		gl.marginHeight = 0;
		gl.marginWidth = 0;
		spacer.setLayout(gl);

		// SID: Want spacer to the right of grammar combo to take up no space if has no content.
		gd = new GridData();
		gd.widthHint = 1;
		spacer.setLayoutData(gd);

		/*
		 * RHS container for controls inserted by selected
		 */
        grammarRHSControlContainer =
                toolkit.createComposite(grammarLineContainer);
        // grammarRHSControlContainer.setBackground(new Color(null, 0, 255,
        // 255));

        grammarRHSControlContainer.setLayoutData(new GridData());

		gl = new GridLayout();
		gl.marginWidth = 0;

		gl.marginHeight = 0;
		grammarRHSControlContainer.setLayout(gl);

		/*
         * Sid XPD-4021 - allow sub-classes to create controls alongside grammar
         * dropdown.
		 */
        grammarExtraContainer =
                createGrammarAreaExtras(sectionComposite, toolkit);
		// grammarExtraContainer.setBackground(new Color(null, 255, 0, 255));

        gd = new GridData(GridData.FILL_HORIZONTAL);
        grammarExtraContainer.setLayoutData(gd);

        // toolkit.createLabel(grammarExtraContainer, "XXX");

        // Create pagebook to hold the service type specific controls
        implTypeBook = new PageBook(sectionComposite, SWT.NONE);

        /*
         * implTypeBook is the composite. Text and Label are created in
         * PlainTextScriptEditor.java
         */
        implTypeBook.setData("name", "ScriptEditorComposite"); //$NON-NLS-1$ //$NON-NLS-2$
        implTypeBook.setBackground(sectionComposite.getBackground());

        GridData gData = new GridData(GridData.FILL_BOTH);
        // gData.verticalIndent = -20;
        implTypeBook.setLayoutData(gData);

        // Create no service page
        noImplPage = toolkit.createComposite(implTypeBook);
        noImplPage.setLayout(new GridLayout(1, false));

        noEditorMessage =
                toolkit.createLabel(noImplPage,
                        Messages.BaseScriptSection_EditorUnavailable_shortdesc);
        noEditorMessage.setLayoutData(new GridData());

        // For each possible editor section create a continer and create the
        // controls in the section.
        for (EditorSectionComposite editorSection : grammarElementToEditorSectionMap
                .values()) {
            editorSection.page =
                    toolkit.createComposite(implTypeBook, SWT.NONE);
            FillLayout fillLayout = new FillLayout();
            fillLayout.marginHeight = 0;
            fillLayout.marginWidth = 0;
            editorSection.page.setLayout(fillLayout);
            try {

                // For a given script Context, for the given destination, fetch
                // the AbstractScriptInfoProvider
                // There could multiple destinations selected for the Script
                // Section.

                editorSection.grammarElement.getISectionExec()
                        .setScriptContext(getScriptContext());
                // At the moment, selects the first one from the list.

				if (getPropertySheetPage() != null)
				{

					editorSection.grammarElement.getISectionExec().createControls(editorSection.page,
							getPropertySheetPage());

				}
				else
				{
					editorSection.grammarElement.getISectionExec().createControls(editorSection.page, toolkit);
				}

            } catch (CoreException e) {
                LOG.error(e);
            }
        }

        if (grammarTypesCombo != null) {
            manageControl(grammarTypesCombo);
        }
        return sectionComposite;
    }

    /**
	 * @param parent
	 * @param toolkit
	 * 
     * @return The control for the right hand side of the grammar selection
     *         combo.
	 */
    protected Composite createGrammarAreaExtras(Composite parent,
            XpdFormToolkit toolkit) {
        Composite cmp = toolkit.createComposite(parent);
        GridLayout gl = new GridLayout();
        gl.marginHeight = 0;
        gl.marginWidth = 0;
        cmp.setLayout(gl);
        return cmp;
    }

    /**
     * Override this method to add the label before the composite section
     */
    protected String getScriptDefinitionLabel() {
        return null;
    }

	/**
	 * Override this method to specify if script definition label should be parsed or not. As ScriptDefinitationLabel
	 * control is a {@link FormText}, overriding class control the parsing condition. By default, parsing is disabled.
	 */
	protected boolean shouldParseScriptDefinitionLabel()
	{
		return false;
	}

    protected boolean showDetails(EditorSectionComposite editorSection) {
        boolean showDetails = false;
        try {
            showDetails =
                    !WorkbenchActivityHelper
                            .filterItem(editorSection.grammarElement
                                    .getISectionExec().getPluginContributon());
        } catch (CoreException e) {
            LOG.error(e);
        }
        return showDetails;
    }

    @Override
    protected Command doGetCommand(Object obj) {
        if (grammarTypesCombo == null || grammarTypesCombo.isDisposed()
                || getInput() == null) {
            return null;
        }

        Command cmd = null;
        // Handle the impl type combo
        if (obj == grammarTypesCombo) {
            String grammarName = grammarTypesCombo.getText();
            EditorSectionComposite section =
                    getEditorSectionCompositeForGrammarName(grammarName);
            if (section != null) {
                section.getEditorSection().setScriptContext(getScriptContext());
                String id = section.getGrammarId();
                // Check if selection in the combo was changed
                String existingGrammarId = getFixedCurrentSetScriptGrammarId();
                // if the id has not changed
                if (id.equals(existingGrammarId)) {
                    return cmd;
                }
                if (existingGrammarId != null) {
                    // Record the currently selected impl before change
                    previousSelectedEditorSection =
                            getEditorSectionCompositeForGrammarId(existingGrammarId);
                }
                // Item selection changed in the combo so update the
                // model with the new script grammar
                AbstractScriptEditorSection editorSection =
                        getScriptEditorSection(section, id);

                cmd = getSetScriptGrammarCommand(id, editorSection);

                /*
                 * XPD-7078 It is possible that the set script grammar command
                 * failed (because a converter may ask user for confirmation. In
                 * which case we need to put the combo box back to original
                 * grammar.
                 * 
                 * We must set the combo grammar back to what it was else it
                 * won't read the correct value and we will get pinged again.
                 */
                if (cmd == null || !cmd.canExecute()) {

                    if (existingGrammarId != null) {
                        com.tibco.xpd.script.ui.internal.extension.ScriptGrammarElement element =
                                grammarElementMap
                                        .get(getFixedCurrentSetScriptGrammarId());

                        if (element != null) {
                            try {
                                setIgnoreEvents(true);

                                String existingGrammarName =
                                        element.getGrammarName();

                                for (int i = 0; i < grammarTypesCombo
                                        .getItemCount(); i++) {
                                    if (existingGrammarName
                                            .equals(grammarTypesCombo
                                                    .getItem(i))) {
                                        grammarTypesCombo.select(i);
                                        break;
                                    }
                                }
                                grammarTypesCombo.setText(existingGrammarName);

                            } finally {
                                setIgnoreEvents(false);
                            }

                            Display.getDefault().asyncExec(new Runnable() {

                                @Override
								public void run() {
                                    refresh();

                                }
                            });
                        }

                    }
                }

            }
        }
        return cmd;
    }

    /**
     * @param section
     * @param grammar
     * @return
     */
    private AbstractScriptEditorSection getScriptEditorSection(
            EditorSectionComposite section, String grammar) {
        AbstractScriptEditorSection editorSection = section.getEditorSection();
        if (editorSection.getScriptDetailsProvider() == null) {

            AbstractScriptInfoProvider scriptInfoProvider =
                    getScriptInfoProvider(grammar);
            if (scriptInfoProvider != null) {
                scriptInfoProvider.setInput(getInput());
                scriptInfoProvider.setScriptGrammar(grammar);

                scriptInfoProvider.setScriptContext(getScriptContext());
                editorSection.setScriptProvider(scriptInfoProvider);
            }
        }
        return editorSection;
    }

    @Override
    public void setInput(Collection<?> items) {

        Object oldInput = getInput();

        super.setInput(items);

        if (oldInput != getInput()) {
            /*
             * Don't set selection changed unless it really has! Otherwise the
             * cursor will keep jumping back to beginning (ScriptSourceViewer)
             * checks whether selection has changed in deciding whether to set
             * the document in the TextViewer)
             */
            setScriptSelectionChanged(true);
        }

        /*
         * If no service has already been selected in the model then set the
         * default impl and update the model. This will be usually the case when
         * a new impl type is added to the diagram
         */
        if (getInput() == null) {
            return;
        }
        String currentScriptGrammarId = getFixedCurrentSetScriptGrammarId();
        if (currentScriptGrammarId == null) {
            return;
        }
        // Update the relevant sections
        try {
            EditorSectionComposite currentEditorSectionComposite =
                    getEditorSectionCompositeForGrammarId(currentScriptGrammarId);
            updateSectionInput(currentEditorSectionComposite);

            EditorSectionComposite currentImpl =
                    getEditorSectionCompositeForGrammarId(currentScriptGrammarId);
            if (currentImpl != null && implTypeBook != null
                    && !implTypeBook.isDisposed()) {
                AbstractScriptEditorSection sectionExec =
                        currentImpl.grammarElement.getISectionExec();
                sectionExec.aboutToBeShown();

                implTypeBook.showPage(currentImpl.page);

                sectionExec.refresh();

            }

        } catch (CoreException e) {
            LOG.error(e);
        }
    }

    @Override
    protected void doRefresh() {
        if (sectionComposite == null || sectionComposite.isDisposed()
                || getInput() == null) {
            return;
        }

        if (destIdToEditorSectionCompositeMap.isEmpty()) {
            return;
        }

        String currentSetGrammarId = getFixedCurrentSetScriptGrammarId();
        EditorSectionComposite currentImpl = null;
        // Script grammar name will be null during transition from one grammar
        // type to another - at this time do nothing

        /*
         * XPD-5410: If this grammar is not available in the current context and
         * was added to the drop-down because a value has been set then disable
         * the script editor and grammar selection in Analyst mode (which is
         * done at the end of this function).
         */
        boolean isGrammarNotAvailableInContext =
                addGrammarNamesToDropDown(currentSetGrammarId);
        if (currentSetGrammarId != null) {
            currentImpl =
                    getEditorSectionCompositeForGrammarId(currentSetGrammarId);
            if (currentImpl == null) {
                // Opportunity to provide an alternative Editor Section
                currentImpl = getAlternativeEditorSection(currentSetGrammarId);
            }
        }

        /*
         * Sid - XPD-1718: Show editor if it is available even if selected
         * destinations don't support grammar
         */
        if (currentImpl != null) {
            try {
                // If grammar has changed then update then hide the
                // previous section if available and show new
                // section

                if (currentImpl != previousSelectedEditorSection) {
                    // Update the combo
                    if (grammarTypesCombo != null) {
                        grammarTypesCombo.setText(currentImpl.getGrammarName());

                    }

                    /*
                     * XPD-7062: Add the extra control contributed by the
                     * section to be displayed next to grammar combo in RHS
                     * container
                     */
                    AbstractScriptEditorSection scriptSection =
                            currentImpl.grammarElement.getISectionExec();

                    if (grammarRHSControlContainer != null) {
                        for (Control c : grammarRHSControlContainer
                                .getChildren()) {
                            c.dispose();
                        }

                        Control rhsControl =
                                scriptSection
                                        .createGrammarSelectorRHSControls(grammarRHSControlContainer);
                        if (rhsControl != null) {
                            rhsControl.setData(new GridData(
                                    GridData.FILL_HORIZONTAL));
                        }

                        grammarRHSControlContainer
                                .setLayoutData(new GridData());
                        grammarRHSControlContainer.getParent().layout(true);
                    }

                    /*
                     * Sid XPD-7575 - Allow currently selected script section to
                     * adjust the offset on it's container (some sections are
                     * nested sections and in that case they do not have a
                     * choice of their own container's margins and hence we get
                     * much wasted space)
                     */
                    Point scriptSectOffset =
                            scriptSection.getSectionMarginOffset();

                    if (scriptSectOffset == null) {
                        scriptSectOffset = new Point(0, 0);
                    }

                    GridData oldGd = (GridData) implTypeBook.getLayoutData();

                    if (oldGd.horizontalIndent != scriptSectOffset.x
                            || oldGd.verticalIndent != scriptSectOffset.y) {
                        GridData gData = new GridData(GridData.FILL_BOTH);
                        gData.horizontalIndent = scriptSectOffset.x;
                        gData.verticalIndent = scriptSectOffset.y;
                        implTypeBook.setLayoutData(gData);
                        implTypeBook.getParent().layout();
                    }

                    // Hide previous section
                    if (previousSelectedEditorSection != null) {
                        previousSelectedEditorSection.grammarElement
                                .getISectionExec().aboutToBeHidden();
                    }
                    // Update input
                    updateSectionInput(currentImpl);

                    // Show new section
                    currentImpl.grammarElement.getISectionExec()
                            .aboutToBeShown();
                    implTypeBook.showPage(currentImpl.page);

                    // refresh tabs only if changed and not first
                    // time
                    if (previousSelectedEditorSection != null) {
                        previousSelectedEditorSection = currentImpl;
                        // Nick: commented out. Calling refreshTabs from
                        // doRefresh causes a NullPointer. Was this just to
                        // update the layout?
                        // Miguel: The NullPointer doesn't seem to happen
                        // anymore
                        // This is needed to show tabs associated with specific
                        // grammars
                        doRefreshTabs();
                    } else {
                        // Update previous service cache
                        previousSelectedEditorSection = currentImpl;
                    }
                } else if (!isEqual(previousEditorInput, getEditorInput())) {
                    updateSectionInput(currentImpl);

                } else {
                    AbstractScriptInfoProvider scriptInfoProvider =
                            getScriptInfoProvider(currentSetGrammarId);

                    if (scriptInfoProvider != null
                            && !isEqual(scriptInfoProvider.getInput(),
                                    getEditorInput())) {
                        updateSectionInput(currentImpl);
                    }
                }
                // Refresh the current section
                currentImpl.grammarElement.getISectionExec()
                        .setScriptContext(getScriptContext());
                if (isScriptSelectionChanged()) {
                    currentImpl.grammarElement.getISectionExec()
                            .setSelectionChanged(true);
                    setScriptSelectionChanged(false);
                }
                currentImpl.grammarElement.getISectionExec().refresh();

            } catch (CoreException e) {
                LOG.error(e);
            }

        } else {
            /*
             * No grammar selected or no editor installed for grammar (we don't
             * come here for "not supported by selected destinations any more).
             */

            // Hide previous section if any
            if (previousSelectedEditorSection != null) {
                try {
                    previousSelectedEditorSection.grammarElement
                            .getISectionExec().aboutToBeHidden();
                    previousSelectedEditorSection = null;
                } catch (CoreException e) {
                    LOG.error(e);
                }
            }

            // No current service found - this means that the
            // implementation for the service set
            // is not available so show blank page and update the
            // combo
            if (currentSetGrammarId == null
                    || currentSetGrammarId.length() == 0) {
                /* Grammar not selected yet. */
                noEditorMessage.setVisible(false);
                if (grammarTypesCombo != null) {
                    grammarTypesCombo.setText(""); //$NON-NLS-1$
                }
            } else {
                /* Editor for grammar not installed. */
                noEditorMessage.setVisible(true);
                if (grammarTypesCombo != null) {
                    grammarTypesCombo.setText(currentSetGrammarId);
                }
            }

            implTypeBook.showPage(noImplPage);
        }
        setScriptSelectionChanged(false);

        if (grammarTypesCombo != null && !grammarTypesCombo.isDisposed()) {
            grammarTypesCombo.setEnabled(true);
            grammarTypesCombo.setLayoutData(new GridData());
            grammarTypesCombo.getParent().layout();
        }
        // Disable editors with invalid grammar id for destination
        if (XpdResourcesPlugin.isRCP()
                && !isValidGrammarIdForCurrentDestinations(currentSetGrammarId)) {
            enableEditorSection(false, currentImpl);
        } else if ((XpdResourcesPlugin.isRCP() || !CapabilityUtil
                .isDeveloperActivityEnabled())
                && isGrammarNotAvailableInContext) {
            /*
             * XPD-5410: If the current grammar selection is not available in
             * the current context and is added because a value (script) has
             * been set then don't allow editing. For example, if a java script
             * is set then disable editing when in Analyst mode as when in
             * Analyst mode the JavaScript option would not be otherwise
             * available (and we don't want an Analyst to change a script
             * entered by a developer).
             */
            if (grammarTypesCombo != null && !grammarTypesCombo.isDisposed()) {
                grammarTypesCombo.setEnabled(false);
            }
            enableEditorSection(false, currentImpl);
        }

		// Refresh script definition label, if changed.

		String newScriptDefinitionLabelStr = getScriptDefinitionLabel();
		if (newScriptDefinitionLabelStr != null)
		{
			if (!newScriptDefinitionLabelStr.equals(scriptDefinitionLabelStr))
			{
				scriptDefinitionLabel.setText(newScriptDefinitionLabelStr, true, false);
				scriptDefinitionLabelStr = newScriptDefinitionLabelStr;
				sectionComposite.layout(true);
			}
		}
    }

    /**
     * This method offers an opportunity to provide an alternative Editor
     * Section when there is no EditorSection Composite contributed for the
     * given gramar Id
     * 
     * @param currentSetGrammarId
     * @return
     */
    protected EditorSectionComposite getAlternativeEditorSection(
            String currentSetGrammarId) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 
     */
    protected void doRefreshTabs() {
        refreshTabs();
    }

    /**
     * @param previous
     * @param current
     * @return
     */
    private boolean isEqual(Object previous, Object current) {
        boolean equal = false;
        if (previous == null) {
            if (current == null) {
                equal = true;
            }
        } else {
            if (previous.equals(current)) {
                equal = true;
            }
        }
        return equal;
    }

    /**
     * This fixes a previous problem whereby in release Studio 2.0 and before,
     * script gramma was incorrectly set to "javascript" instead of "JavaScript"
     * 
     * @return
     */
    private String getFixedCurrentSetScriptGrammarId() {
        String grammar = getCurrentSetScriptGrammarId();
        if (JAVA_SCRIPT_GRAMMAR.equalsIgnoreCase(grammar)) {
            return JAVA_SCRIPT_GRAMMAR;
        } else if (grammar == null) {
            return UNDEFINED_SCRIPTGRAMMAR;
        }
        return grammar;
    }

    /**
     * Update the input of all service implementation sections
     * 
     * @param currentSelectedSection
     * @throws CoreException
     */
    protected void updateSectionInput(
            EditorSectionComposite currentSelectedSection) throws CoreException {

        if (getInput() != null) {
            // Get input model to pass to the implementation section - this will
            // be the Activity

            Object editorInput = getEditorInput();
            for (EditorSectionComposite editorSectionComposite : grammarElementToEditorSectionMap
                    .values()) {
                AbstractScriptEditorSection sectionExec =
                        editorSectionComposite.grammarElement.getISectionExec();

                ResourceMarkerAnnotationModelProvider resourceMarkerAnnotationModelProvider =
                        ScriptGrammarContributionsUtil.INSTANCE
                                .getResourceMarkerAnnotationModelProvider(getEnabledDestinations(getInput()),
                                        getScriptContext(),
                                        editorSectionComposite.grammarElement
                                                .getGrammarId());

                if (resourceMarkerAnnotationModelProvider != null) {
                    IFile resource = WorkingCopyUtil.getFile(getInput());
                    if (resource != null && editorInput instanceof EObject) {
                        resourceMarkerAnnotationModelProvider
                                .setInput((EObject) editorInput);
                        sectionExec
                                .setResourceMarkerAnnotationModel(resourceMarkerAnnotationModelProvider
                                        .getResourceMarkerAnnotationModel(resource,
                                                getMarkerContainerId(),
                                                getScriptContext()));
                    }
                }

                AbstractScriptInfoProvider scriptInfoProvider =
                        getScriptInfoProvider(editorSectionComposite
                                .getGrammarId());

                if (editorInput != null
                        && currentSelectedSection != null
                        && currentSelectedSection
                                .equals(editorSectionComposite)) {
                    if (scriptInfoProvider != null
                            && editorInput instanceof EObject) {
                        if (!editorInput.equals(scriptInfoProvider.getInput())) {
                            scriptInfoProvider.setInput((EObject) editorInput);
                        }
                        scriptInfoProvider
                                .setScriptGrammar(editorSectionComposite
                                        .getGrammarId());

                        scriptInfoProvider.setScriptContext(getScriptContext());
                        sectionExec.setScriptProvider(scriptInfoProvider);
                    }
                    sectionExec.setScriptContext(getScriptContext());
                    sectionExec.setLoadProcessData(isLoadProcessData());
                    sectionExec
                            .setEnabledDestinations(getEnabledDestinations(getInput()));
                    sectionExec
                            .setAdditionalScriptData(getAdditionalScriptData());
                    if (!editorInput.equals(sectionExec.getInput())) {
                        sectionExec.setInput(getPart(),
                                new StructuredSelection(editorInput));
                    }
                } else {
                    if (scriptInfoProvider != null) {
                        scriptInfoProvider.setInput(null);
                        scriptInfoProvider
                                .setScriptGrammar(editorSectionComposite
                                        .getGrammarId());

                        scriptInfoProvider.setScriptContext(getScriptContext());
                        sectionExec.setScriptProvider(scriptInfoProvider);

                    }
                    sectionExec.setScriptContext(getScriptContext());
                    sectionExec.setLoadProcessData(isLoadProcessData());
                    sectionExec
                            .setEnabledDestinations(getEnabledDestinations(getInput()));
                    sectionExec
                            .setAdditionalScriptData(getAdditionalScriptData());
                    sectionExec.setInput(getPart(), new StructuredSelection());
                }
            }

            addGrammarNamesToDropDown(getCurrentSetScriptGrammarId());
            previousEditorInput = editorInput;
        }
    }

    /**
     * @return
     */
    protected Object getEditorInput() {
        return getInput();
    }

    /**
     * This is the id of the activity towards which the resource marker is
     * added. This needs to be overriden by subclasses to provide actual id of
     * the eobject
     * 
     * @return
     */
    protected String getMarkerContainerId() {
        return null;
    }

    /**
     * @return
     */
    protected Object getCurrentEditorInput() {
        return previousEditorInput;
    }

    /**
     * 
     */
    private void readContributedScriptGrammar() {
        grammarElementMap =
                ScriptGrammarContributionsUtil.INSTANCE
                        .getContributedScriptGrammars();

    }

    private void readGrammarDestinationBindingElement() {
        // if (grammarElementToEditorSectionMap.isEmpty()) {
        Set<String> contributedDestinations =
                ScriptGrammarContributionsUtil.INSTANCE
                        .getContributedDestinations();

        Map<String, ScriptGrammarElement> scriptGrammars =
                ScriptGrammarContributionsUtil.INSTANCE
                        .getContributedScriptGrammars();
        for (String destination : contributedDestinations) {
            Set<ScriptGrammarReferenceElement> grammarForGivenContextAndDestination =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getGrammarForGivenContextAndDestination(destination,
                                    getScriptContext());
            for (ScriptGrammarReferenceElement scriptGrammarRefElement : grammarForGivenContextAndDestination) {
                addScriptGrammar(destination,
                        scriptGrammars.get(scriptGrammarRefElement.getGrammar()),
                        scriptGrammarRefElement);
            }
        }
        // }
    }

    private void addScriptGrammar(String destinationId,
            ScriptGrammarElement scriptGrammarElement,
            ScriptGrammarReferenceElement scriptGrammarRefElement) {

        // We now keep 2 cache maps. One is of destinationId to
        // EditorSectionComposite (but MANY destinations use the same editor
        // section - so we also keep a base map of grammar to
        // EditorSectionComposite - this way we can look up on either criteria
        // (i.e. we can go from grammar name/id to editor OR destinationId to
        // list of editor sections for that

        // Sort out the editor section first.
        EditorSectionComposite editorSection =
                grammarElementToEditorSectionMap.get(scriptGrammarElement);

        if (editorSection == null) {
            // Not cache the editor section for this grammar yet - do it now,
            editorSection = new EditorSectionComposite(scriptGrammarElement);
            grammarElementToEditorSectionMap.put(scriptGrammarElement,
                    editorSection);
        }

        /*
         * SIA-1: Save whether the script grammar is selectable (if not it will
         * only appear when already set in model
         */
        editorSection.setSelectable(scriptGrammarRefElement.isSelectable());

        // Now add this grammar's editor section to the list for this
        // destination id.
        List<EditorSectionComposite> grammarEleList =
                destIdToEditorSectionCompositeMap.get(destinationId);
        if (grammarEleList == null) {
            grammarEleList = new ArrayList<EditorSectionComposite>();
        }
        // checking if there are no duplicate entry for grammarId
        boolean grammarExists =
                isGrammarExists(scriptGrammarElement.getGrammarId(),
                        grammarEleList);

        if (!grammarExists) {
            grammarEleList.add(editorSection);
            destIdToEditorSectionCompositeMap
                    .put(destinationId, grammarEleList);
        }

        return;
    }

    /**
     * @param grammarId
     * @param editorSectionList
     * @return
     */
    private boolean isGrammarExists(String grammarId,
            List<EditorSectionComposite> editorSectionList) {
        boolean found = false;
        for (EditorSectionComposite editorSection : editorSectionList) {
            if (grammarId.equals(editorSection.getGrammarId())) {
                found = true;
                break;
            }
        }
        return found;
    }

    /**
     * @param currentSetGrammarId
     * @return <code>true</code> if this grammar type is not available in the
     *         current context and was added to the drop-down,
     *         <code>false</code> if the grammar type is available in the
     *         current context.
     */
    protected boolean addGrammarNamesToDropDown(String currentSetGrammarId) {

        /*
         * SId XPD-7078 - noticed that when this method sets up the grammar
         * combo it is not with Ignore events set on and we're not always called
         * from inside doRefresh() which is ignoring control change events.
         * 
         * Therefore we really should ignore events so we don't get a nasty
         * feedback loop thru doGetCommand() when initialisaing the combo
         * content.
         */
        boolean wasAlreadyIgnoringEvents = isIgnoreEvents();
        try {
            if (!wasAlreadyIgnoringEvents) {
                setIgnoreEvents(true);
            }

            boolean isGrammarNotAvailableInContext = false;
            if (getInput() == null || grammarTypesCombo == null) {
                return false;
            }

            Collection<String> enabledDestinations =
                    getEnabledDestinations(getInput());

            Map<String, String> grammarMap = new HashMap<String, String>();
            for (String destination : enabledDestinations) {
                List<EditorSectionComposite> editorSectionList =
                        destIdToEditorSectionCompositeMap.get(destination);
                if (editorSectionList == null || editorSectionList.isEmpty()) {
                    continue;
                }
                for (EditorSectionComposite editorSection : editorSectionList) {
                    if (showDetails(editorSection)) {
                        /*
                         * SIA-1: Only add non-selectable grammars to list if
                         * the grammar is already selected in the model.
                         */
                        if (editorSection.isSelectable()
                                || editorSection.getGrammarId()
                                        .equals(currentSetGrammarId)) {

                            String grammarName =
                                    grammarMap
                                            .get(editorSection.getGrammarId());
                            if (grammarName != null) {
                                continue;
                            }
                            grammarMap.put(editorSection.getGrammarId(),
                                    editorSection.getGrammarName());

                        }
                    }
                }
            }
            String existingGrammarId = getFixedCurrentSetScriptGrammarId();
            String existingGrammarName = null;
            if (existingGrammarId != null) {
                com.tibco.xpd.script.ui.internal.extension.ScriptGrammarElement element =
                        grammarElementMap.get(existingGrammarId);
                isGrammarNotAvailableInContext =
                        grammarMap.get(existingGrammarId) == null;
                if (element != null) {
                    existingGrammarName = element.getGrammarName();
                    grammarMap.put(existingGrammarId, existingGrammarName);
                } else {
                    grammarMap.put(existingGrammarId, existingGrammarId);
                    existingGrammarName = existingGrammarId;
                }
            }

            Collection<String> grammarNamesToAdd = grammarMap.values();
            if (grammarNamesToAdd == null || grammarNamesToAdd.isEmpty()) {
                return false;
            }
            if (grammarTypesCombo.getItemCount() > 0) {
                grammarTypesCombo.removeAll();
            }
            for (String grammarName : grammarNamesToAdd) {
                grammarTypesCombo.add(grammarName);
            }
            if (existingGrammarName != null) {
                grammarTypesCombo.setText(existingGrammarName);
            }

            return isGrammarNotAvailableInContext;

        } finally {
            /*
             * SId XPD-7078 - noticed that when this method sets up the grammar
             * combo it is not with Ignore events set on and we're not always
             * called from inside doRefresh() which is ignoring control change
             * events.
             * 
             * Therefore we really should ignore events so we don't get a nasty
             * feedback loop thru doGetCommand() when initialisaing the combo
             * content.
             */
            if (!wasAlreadyIgnoringEvents) {
                setIgnoreEvents(false);
            }
        }
    }

    protected EditorSectionComposite getEditorSectionCompositeForGrammarId(
            String grammarId) {

        for (EditorSectionComposite editorSection : grammarElementToEditorSectionMap
                .values()) {
            if (editorSection.getGrammarId().equals(grammarId)) {
                enableEditorSection(true, editorSection);
                return editorSection;
            }
        }
        return null;
    }

    protected boolean isValidGrammarIdForCurrentDestinations(String grammarId) {
        if (getInput() != null) {
            Collection<String> enabledDestinations =
                    getEnabledDestinations(getInput());
            for (String destination : enabledDestinations) {
                Set<ScriptGrammarReferenceElement> grammarForGivenContextAndDestination =
                        ScriptGrammarContributionsUtil.INSTANCE
                                .getGrammarForGivenContextAndDestination(destination,
                                        getScriptContext());
                for (ScriptGrammarReferenceElement scriptGrammarRefElement : grammarForGivenContextAndDestination) {
                    if (scriptGrammarRefElement != null
                            && scriptGrammarRefElement.getGrammar() != null
                            && scriptGrammarRefElement.getGrammar()
                                    .equals(grammarId)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    protected EditorSectionComposite getEditorSectionCompositeForGrammarName(
            String grammarName) {

        for (EditorSectionComposite editorSection : grammarElementToEditorSectionMap
                .values()) {
            if (editorSection.getGrammarName().equals(grammarName)) {
                return editorSection;
            }
        }
        return null;
    }

    /**
     * @return
     */
    @Override
    public Control getControl() {
        return sectionComposite;
    }

    public void setEnabled(boolean enabled) {
        if (grammarTypesCombo != null) {
            grammarTypesCombo.setEnabled(enabled);
        }
    }

    /**
     * Must be called before controls are created.
     * 
     * @param true to display grammar combo, otherwise false.
     */
    public void setGrammarEnabled(boolean enableGrammar) {
        this.enableGrammar = enableGrammar;
    }

    /**
     * 
     * @return
     */
    public boolean isScriptSelectionChanged() {
        return this.scriptSelectionChanged;
    }

    /**
     * 
     * @param scriptSelectionChanged
     */
    public void setScriptSelectionChanged(boolean scriptSelectionChanged) {
        this.scriptSelectionChanged = scriptSelectionChanged;
    }

    /**
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#dispose()
     * 
     */
    @Override
    public void dispose() {
        if (grammarElementMap != null) {
            for (String grammar : grammarElementMap.keySet()) {
                ScriptGrammarElement scriptGrammarElement =
                        grammarElementMap.get(grammar);
                try {
                    if (scriptGrammarElement != null
                            && scriptGrammarElement.getISectionExec() != null) {
                        scriptGrammarElement.getISectionExec().dispose();
                    }
                } catch (CoreException e) {
                    LOG.error(e, "Disposing grammar elements"); //$NON-NLS-1$
                }
            }
            grammarElementMap.clear();
        }
        if (grammarElementToEditorSectionMap != null) {
            for (EditorSectionComposite composite : grammarElementToEditorSectionMap
                    .values()) {
                composite.dispose();
            }

            grammarElementToEditorSectionMap.clear();
        }
        super.dispose();

    }

    /**
     * @param input
     * @return List of disabled destinations for input.
     */
    public abstract Collection<String> getEnabledDestinations(EObject input);

    /**
     * @return
     */
    protected abstract String getCurrentSetScriptGrammarId();

    /**
     * The context this script editor is used in. For e.g. TaskScript.
     * 
     * @return
     */
    public abstract String getScriptContext();

    /**
     * 
     * @param grammar
     * @param editorSection
     * @return
     */
    protected abstract Command getSetScriptGrammarCommand(String grammar,
            AbstractScriptEditorSection editorSection);

    protected void enableEditorSection(boolean enable,
            EditorSectionComposite editorSectionComposite) {
        // Enable/Disable the section
        if (editorSectionComposite != null
                && editorSectionComposite.getEditorSection() != null
                && editorSectionComposite.getEditorSection()
                        .getControlsContainer() != null
                && editorSectionComposite.getEditorSection()
                        .getControlsContainer().isEnabled() != enable) {
            editorSectionComposite.getEditorSection().getControlsContainer()
                    .setEnabled(enable);
            /*
             * XPD-5676: Saket: Gray out the text in script editor to let the
             * user know that the script editor is not editable.
             */
            try {
                editorSectionComposite.grammarElement.getISectionExec()
                        .setEnabled(enable);
            } catch (CoreException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

	/**
	 * Special {@link FormText} class with focus disabled.
	 *
	 * @author ssirsika
	 * @since 04-Mar-2024
	 */
	private class NoFocusFormText extends FormText
	{

		/**
		 * @param parent
		 * @param style
		 */
		public NoFocusFormText(Composite parent, int style)
		{
			super(parent, style);
		}

		/**
		 * @see org.eclipse.ui.forms.widgets.FormText#setFocus()
		 *
		 * @return
		 */
		@Override
		public boolean setFocus()
		{
			// Do nothing here to disable the focus.
			return true;
		}
	}
}
