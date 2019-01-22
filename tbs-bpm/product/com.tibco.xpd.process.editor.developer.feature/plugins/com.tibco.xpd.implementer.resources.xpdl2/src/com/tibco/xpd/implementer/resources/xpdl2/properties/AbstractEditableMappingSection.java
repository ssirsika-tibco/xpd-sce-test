/*
 * Copyright (c) TIBCO Software Inc 2004, 20079. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.IExpansionListener;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.xsd.XSDTypeDefinition;

import com.tibco.xpd.implementer.resources.xpdl2.internal.Messages;
import com.tibco.xpd.implementer.script.IWsdlPath;
import com.tibco.xpd.mapper.MapperActivator;
import com.tibco.xpd.mapper.MapperUtil;
import com.tibco.xpd.mapper.MapperViewer;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.AbstractActivityMappingProblemMarkerHandlingSection;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.ui.internal.BaseScriptSection;
import com.tibco.xpd.script.ui.internal.extension.ScriptGrammarElement;
import com.tibco.xpd.ui.properties.PropertiesPlugin;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.commands.LateExecuteCompoundCommand;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Mapping section that provides all the capability of the super class
 * {@link AbstractActivityMappingProblemMarkerHandlingSection} with additional
 * script editor section at the bottom which will aid the implementers to have
 * the script section (via {@link #getScriptSection()}) below the mapper.
 * 
 * 
 * @author nwilson
 * @since
 */
public abstract class AbstractEditableMappingSection extends
        AbstractActivityMappingProblemMarkerHandlingSection {
    /**
     * The main container that contains the mapping section and the script
     * section.
     */
    private Composite mainContainer;

    /**
     * the container that holds the mapping viewer
     */
    private Composite mappingSectionContainer;

    /**
     * the container for script section.
     */
    protected Composite scriptSectionContainer;

    private ComboViewer grammarViewer;

    private ExpandableComposite expandableScriptEditor;

    private BaseScriptSection scriptSection;

    private String currentGrammar;

    /**
     * Sid XPD-7996. Grammar at previous doRefresh() (or null if first refresh
     * since input changed.
     */
    private String grammarOnPreviousRefresh = null;

    private boolean expanded = false;

    private Map<String, String> grammarNameMap;

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    /**
     * the sash that will be dividing the mapping and script sections.
     */
    private Sash sash;

    /**
     * the default sash percent is 60%(mapping section) and 40%(script editor
     * section)
     */
    private float sashPercent = 0.6f;

    /**
     * the Min side size is 5% so that none of the sections get completely
     * hidden from the user.
     */
    public static float MIN_SIDE_SIZE = 0.25f;

    /**
     * the Horizontal Sash percent preference Id that would be stored in
     * preference store.
     */
    private String horzSashPercentPrefId;

    /**
     * @param direction
     */
    public AbstractEditableMappingSection(MappingDirection direction) {
        super(direction);
        /*
         * the preference ID
         */
        this.horzSashPercentPrefId = "AbstractEditableMappingSection"; //$NON-NLS-1$

        grammarNameMap = new HashMap<String, String>();
        scriptSection = getScriptSection();
        if (scriptSection != null) {
            scriptSection.setGrammarEnabled(false);
        }
    }

    /**
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractItemProviderMappingSection#setInput(java.util.Collection)
     * 
     * @param items
     */
    @Override
    public void setInput(Collection<?> items) {
        grammarOnPreviousRefresh = null;

        super.setInput(items);
        if (scriptSection != null) {
            scriptSection.setInput(items);
        }
    }

    /**
     * 
     * @see com.tibco.xpd.ui.properties.AbstractXpdSection#setInput(org.eclipse.ui.IWorkbenchPart,
     *      org.eclipse.jface.viewers.ISelection)
     * 
     * @param part
     * @param selection
     */
    @Override
    public void setInput(IWorkbenchPart part, ISelection selection) {
        super.setInput(part, selection);
        if (scriptSection != null) {
            scriptSection.setInput(part, selection);
        }
    }

    /**
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#createHeaderControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param header
     * @param tk
     */
    @Override
    protected void createHeaderControls(Composite header, XpdFormToolkit tk) {

        GridLayout layout = new GridLayout();
        /*
         * Sid XPD-31456: noticed that grammar combo was having edges cut off
         * (caused by 0 margins).
         */
        layout.marginHeight = 2;
        layout.marginWidth = 0;
        layout.marginRight = 1;
        layout.numColumns = 2;
        header.setLayout(layout);

        boolean showGrammarSelectionOpton = showGrammarSelectionCombo();

        Label label =
                tk.createLabel(header,
                        Messages.AbstractEditableMappingSection_ScriptGrammarLabel);
        GridData data1 = new GridData(SWT.FILL, SWT.FILL, false, false);
        label.setLayoutData(data1);
        label.setVisible(showGrammarSelectionOpton);

        CCombo grammar = tk.createCCombo(header, "grammarCombo"); //$NON-NLS-1$
        GridData data2 = new GridData(SWT.FILL, SWT.FILL, true, false);
        data2.widthHint = 120;
        grammar.setLayoutData(data2);
        grammar.setVisible(showGrammarSelectionOpton);

        grammarViewer = new ComboViewer(grammar);
        grammarViewer.setContentProvider(new GrammarContentProvider());
        grammarViewer.setLabelProvider(new GrammarLabelProvider());

        grammarViewer
                .addSelectionChangedListener(new ISelectionChangedListener() {

                    @Override
                    public void selectionChanged(SelectionChangedEvent event) {
                        ISelection selection = grammarViewer.getSelection();
                        if (selection instanceof StructuredSelection) {
                            StructuredSelection structured =
                                    (StructuredSelection) selection;
                            if (structured.size() == 1) {
                                Object item = structured.getFirstElement();
                                if (item instanceof String) {
                                    String grammarName = (String) item;
                                    if (!grammarName.equals(currentGrammar)) {
                                        changeGrammar(grammarName);
                                    }
                                }
                            }
                        }
                    }

                });
    }

    /**
     * @return <code>true</code> if the gammar selection combo box should be
     *         visible, <code>false</code> otherwise. Sub-classed may override
     *         this method to hide the combo.
     */
    protected boolean showGrammarSelectionCombo() {
        return true;
    }

    @Override
    protected Control doCreateControls(Composite parent, XpdFormToolkit tk) {
        mainContainer = tk.createComposite(parent);
        tk.paintBordersFor(mainContainer);

        /*
         * XPD-7139:(Since TBS 4.0.0) The Sash is used to divide the mapping and
         * script section so that the user can resize the sections as required.
         * By default when the script section is expanded the sash ratio would
         * be 60:40. However if the user resizes the section then the sash
         * percent would be stored in preference.
         */
        mainContainer.setLayout(new SashDividedLayout());

        mappingSectionContainer = tk.createComposite(mainContainer);
        tk.paintBordersFor(mappingSectionContainer);

        FillLayout mappingSectionContainerLayout = new FillLayout();
        mappingSectionContainerLayout.marginHeight = 2;

        mappingSectionContainer.setLayout(mappingSectionContainerLayout);

        Control mapperControl =
                super.doCreateControls(mappingSectionContainer, tk);

        if (mapperControl instanceof Composite) {
            Layout mapperLayout = ((Composite) mapperControl).getLayout();
            if (mapperLayout instanceof GridLayout) {
                /*
                 * set the margin height to 1 as we do not want the margin to
                 * take too much space.
                 */
                ((GridLayout) mapperLayout).marginHeight = 1;
            }
        }

        /**
         * Sid XPD-???? MapperScriptProperties (the normal class for mapping
         * script editor sections) hard-wires itsself as a listener based upoin
         * the input and mapping direction alone and will respond to listener
         * events for ANY mapper for the same input and direction - not just the
         * one it is associated.
         * 
         * In order to prevent this happening, we need to connect the
         * MapperScriptProperties to the mapperViewer for the same section (so
         * that when it gets an event it can ignore it when it's from a
         * different mapper).
         * 
         * NOTE: This could be done better by NOT having static listeners (see
         * MapperActivator) and having the MapperScriptProperties listen direct
         * to mapper viewer when we set the related mapper viewer, but that is
         * too large a change at the moment.
         */
        if (scriptSection instanceof MapperScriptProperties) {
            ((MapperScriptProperties) scriptSection)
                    .setRelatedMapperViewer(getMapperViewer());
        }

        /*
         * XPD-7139: Create the horizontal Sash section between the mapping and
         * script section.
         */
        sash = new Sash(mainContainer, SWT.HORIZONTAL);
        sash.addListener(SWT.Selection, sashListener);
        sash.setEnabled(false);

        scriptSectionContainer = tk.createComposite(mainContainer);
        tk.paintBordersFor(scriptSectionContainer);

        FillLayout scriptSectionContainerLayout = new FillLayout();
        /*
         * leave some margin so that the sash is visible.
         */
        scriptSectionContainerLayout.marginHeight = 3;

        scriptSectionContainer.setLayout(scriptSectionContainerLayout);

        // Allow sub-class to control whether script section appears.
        if (showScriptEditor()) {
            expandableScriptEditor =
                    tk.createSection(scriptSectionContainer,
                            ExpandableComposite.TWISTIE
                                    | ExpandableComposite.TITLE_BAR);
            expandableScriptEditor
                    .setText(Messages.AbstractEditableMappingSection_ScriptEditorSectionLabel);
            expandableScriptEditor.setLayout(new FillLayout());

            expandableScriptEditor
                    .addExpansionListener(new IExpansionListener() {
                        @Override
                        public void expansionStateChanged(ExpansionEvent e) {
                            expanded = e.getState();

                            /*
                             * XPD-7139: Dont enable the sash when the script
                             * section is collapsed.
                             */
                            if (!expanded && sash.getEnabled()) {
                                sash.setEnabled(false);
                            } else if (!sash.getEnabled()) {
                                sash.setEnabled(true);
                            }

                            mainContainer.layout();
                        }

                        @Override
                        public void expansionStateChanging(ExpansionEvent e) {
                        }
                    });

            if (scriptSection != null) {
                scriptSection.createControls(expandableScriptEditor,
                        getPropertySheetPage());
                expandableScriptEditor.setClient(scriptSection.getControl());
            } else {
                expandableScriptEditor.setEnabled(false);
            }
        }
        return mainContainer;
    }

    /**
     * @return <code>true</code> if the script editor secytion should be shown
     *         (default implementatiuon is <code>true</code>)
     */
    protected boolean showScriptEditor() {
        return true;
    }

    /**
     * Sash listener - Adjusts the relative size of the mapping and script
     * editor sections on the position of the sash when dragged.
     */
    private final Listener sashListener = new Listener() {

        @Override
        public void handleEvent(Event event) {

            Rectangle sashRect = sash.getBounds();
            Rectangle shellRect = mainContainer.getClientArea();

            float newPerc = sashPercent;

            // Do nothing unless sash has actually moved some
            if (event.y < (sashRect.y - 1) || event.y > (sashRect.y + 1)) {
                // Calculate the percent of the sash
                if (shellRect.height > 0) {
                    newPerc = (float) event.y / (float) shellRect.height;
                }
            }

            if (newPerc != sashPercent) {
                if (newPerc < MIN_SIDE_SIZE) {
                    newPerc = MIN_SIDE_SIZE;
                } else if (newPerc > (1.0f - MIN_SIDE_SIZE)) {
                    newPerc = (1.0f - MIN_SIDE_SIZE);
                }

                setSashPercent(newPerc);
                mainContainer.layout();
                PropertiesPlugin
                        .getDefault()
                        .getPreferenceStore()
                        .setValue(horzSashPercentPrefId,
                                (int) (sashPercent * 100));
            }
            return;
        }
    };

    /**
     * Sets the sash percent that is passed to this method and calls layout on
     * the main container.
     * 
     * @param newPercent
     *            the new sash percent.
     */
    protected void setSashPercent(float newPercent) {
        if (newPercent == sashPercent) {
            return;
        }

        sashPercent = newPercent;

        mainContainer.layout();

        return;
    }

    /**
     * @param enable
     */
    @Override
    protected void setMapperEnabled(boolean enable) {
        super.setMapperEnabled(enable);
        if (expandableScriptEditor != null) {
            if (enable) {
                expandableScriptEditor.setExpanded(expanded);
            } else {
                expandableScriptEditor.setExpanded(true);
            }
        }
        mainContainer.layout();

        if (!enable) {
            DirectionType dir =
                    MappingDirection.IN.equals(getDirection()) ? DirectionType.IN_LITERAL
                            : DirectionType.OUT_LITERAL;
            Activity activity = (Activity) getInput();
            ScriptInformation information = null;
            List<?> others =
                    Xpdl2ModelUtil.getOtherElementList(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script());
            for (Object other : others) {
                if (other instanceof ScriptInformation) {
                    ScriptInformation current = (ScriptInformation) other;
                    if (dir.equals(current.getDirection())
                            && current.getName() == null) {
                        information = current;
                        break;
                    }
                }
            }
            if (information == null) {
                information =
                        XpdExtensionFactory.eINSTANCE.createScriptInformation();
            }
            Object[] items = new Object[] { information };
            MapperActivator
                    .getDefault()
                    .fireSourceSelectionChanged(MapperUtil.getMapperId(getDirection(),
                            getInput()),
                            scriptSection,
                            items);
        }
    }

    @Override
    protected void doRefresh() {
        if (mappingSectionContainer.isDisposed()) {
            return;
        }

        MapperViewer viewer = getMapperViewer();

        if (null != viewer) {
            viewer.setRedraw(false);
        }

        /* Sid XPD-7575 - allow grammarViewer to be null if not created. */
        if (grammarViewer != null) {
            grammarViewer.getCCombo().setRedraw(false);
        }

        currentGrammar = getGrammar();

        /*
         * Sid XPD-7996: If grammar has changed from previous refresh then we
         * need to refreshTabs as the grammar may be handled by a completely
         * separate section.
         */
        if (grammarOnPreviousRefresh != null && currentGrammar != null) {
            if (!grammarOnPreviousRefresh.equals(currentGrammar)) {
                refreshTabs();
            }
        }
        grammarOnPreviousRefresh = currentGrammar;

        Activity activity = (Activity) getInput();
        if (scriptSection != null) {
            if (activity != null) {
                /* Sid XPD-7575 - allow grammarViewer to be null if not created. */
                if (grammarViewer != null) {
                    Control grammarControl = grammarViewer.getControl();
                    if (grammarControl != null && !grammarControl.isDisposed()) {
                        grammarViewer.setInput(activity);
                    }
                }
            }
            scriptSection.refresh();
        }

        /* Sid XPD-7575 - allow grammarViewer to be null if not created. */
        if (grammarViewer != null) {
            grammarViewer.setSelection(new StructuredSelection(currentGrammar));
        }

        if (!mappingSectionContainer.isDisposed()) {
            mappingSectionContainer.layout();
            mainContainer.layout();

            super.doRefresh();
            if (null != viewer) {
                viewer.setRedraw(true);
            }

            /* Sid XPD-7575 - allow grammarViewer to be null if not created. */
            if (grammarViewer != null) {
                grammarViewer.getCCombo().setRedraw(true);
            }

            if (shouldDisableEditGrammarViewer()) {
                /* Sid XPD-7575 - allow grammarViewer to be null if not created. */
                if (grammarViewer != null) {
                    if (currentGrammar != null) {
                        /*
                         * IF grammar combo is disabled then it may not have
                         * been populated. so force it to the hard-coded
                         * grammar.
                         */
                        grammarViewer.getCCombo().setText(currentGrammar);
                    }
                    grammarViewer.getCCombo().setEnabled(false);
                } else {
                    grammarViewer.getCCombo().setEnabled(true);
                }
            }
        }
    }

    /**
     * @return The grammar if a single grammar found, null if none or
     *         duplicates.
     */
    protected String getGrammar() {
        Activity activity = (Activity) getInput();
        DirectionType dir =
                MappingDirection.IN.equals(getDirection()) ? DirectionType.IN_LITERAL
                        : DirectionType.OUT_LITERAL;
        String grammar = ScriptGrammarFactory.getScriptGrammar(activity, dir);
        if (grammar == null) {
            grammar = ScriptGrammarFactory.getDefaultScriptGrammar(activity);
        }
        return grammar;
    }

    /**
     * Get the command to set the grammar to use for mappings.
     * <p>
     * Sid. XPD-7996. The default implementation has been changed to now DELETE
     * all existing mappings for the mapping direction that this section is
     * dealing with (previously it had attempted to preserve existing mappings,
     * changing grammar on each and checking for and running a grammar converter
     * if one was contributed. However this NEVER really worked (even for simple
     * field mapping) and now with the advent of DataMapper grammar scenarios it
     * is not possible to generically handle this (as the data mapping model is
     * completely different for Datamapper and for instance JavaScript
     * <p>
     * Unmapped user defined scripts are also deleted (named
     * xpdExt:ScriptInformation's at activity level) along with any existing
     * 'explicit grammar selector' (an unnamed xpdExt:ScriptInformation's at
     * activity level).
     * <p>
     * Then finally the 'explicit grammar selector' (an unnamed
     * xpdExt:ScriptInformation's at activity level) is added to explicitly
     * state the selected grammar even though there are no mappings. (In
     * circumstances where mappings are drawn in the DEFAULT grammar, without
     * user explicit selection of grammar, then this will not be present (the
     * presence of the mappings themselves govern the selected grammar)
     * 
     * @return Command to set the grammar.
     */
    protected Command getSetGrammarCommand(String grammar) {

        /*
         * Use LateExec command as we may delete multiple ##other extension
         * elements and it's generally safer
         */
        CompoundCommand cmd = new LateExecuteCompoundCommand();
        cmd.setLabel(Messages.AbstractEditableMappingSection_SetScriptGrammarCommand);

        Activity activity = (Activity) getInput();

        if (activity != null && grammar != null) {
            EditingDomain ed = WorkingCopyUtil.getEditingDomain(activity);

            DirectionType dir =
                    MappingDirection.IN.equals(getDirection()) ? DirectionType.IN_LITERAL
                            : DirectionType.OUT_LITERAL;

            /*
             * Remove all existing mappings
             */
            Command removeAllMappingsCmd =
                    getRemoveAllMappingsCmdCosGrammarSwitched(ed);

            if (removeAllMappingsCmd != null) {
                cmd.append(removeAllMappingsCmd);
            }

            /*
             * Remove any Activity-level ScriptInformation extensions (unmapped
             * user defined scripts and existing explicit-grammar-selector
             * ScriptInformation for specific direction)
             */
            List<?> others =
                    Xpdl2ModelUtil.getOtherElementList(activity,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Script());

            for (Object other : others) {
                if (other instanceof ScriptInformation) {
                    ScriptInformation information = (ScriptInformation) other;
                    if (dir.equals(information.getDirection())) {
                        cmd.append(Xpdl2ModelUtil
                                .getRemoveOtherElementCommand(ed,
                                        activity,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_Script(),
                                        information));
                    }
                }
            }

            /*
             * Add the explicit grammar selection element.
             */
            ScriptInformation grammarScript =
                    XpdExtensionFactory.eINSTANCE.createScriptInformation();

            grammarScript.setDirection(MappingDirection.IN
                    .equals(getDirection()) ? DirectionType.IN_LITERAL
                    : DirectionType.OUT_LITERAL);
            Expression expression = Xpdl2Factory.eINSTANCE.createExpression();
            expression.setScriptGrammar(grammar);
            grammarScript.setExpression(expression);
            cmd.append(Xpdl2ModelUtil.getAddOtherElementCommand(ed,
                    activity,
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script(),
                    grammarScript));

            return cmd;
        }

        return UnexecutableCommand.INSTANCE;
    }

    /**
     * The grammar is being changed, need to delete existing mappings. thios
     * methods should return the command to do that.
     * 
     * @param ed
     * @return A command that will remove all of the data mappings
     */
    protected Command getRemoveAllMappingsCmdCosGrammarSwitched(EditingDomain ed) {
        Command removeAllMappingsCmd = null;

        if (getMapperViewer() != null) {

            List<EObject> deleteMappings = new ArrayList<>();

            for (Mapping mapping : getMapperViewer().getMappings()) {
                Object mappingModel = mapping.getMappingModel();
                if (mappingModel instanceof EObject) {
                    deleteMappings.add((EObject) mappingModel);
                }
            }

            if (!deleteMappings.isEmpty()) {
                removeAllMappingsCmd = RemoveCommand.create(ed, deleteMappings);
            }
        }
        return removeAllMappingsCmd;
    }

    private void changeGrammar(String grammarName) {
        currentGrammar = grammarName;
        Command cmd = getSetGrammarCommand(grammarName);
        if (cmd.canExecute()) {
            EditingDomain ed = getEditingDomain();
            ed.getCommandStack().execute(cmd);
        }
        // refresh(); we will getg a refresh from exec'ing he command anyhwayh
    }

    @Override
    public void dispose() {
        if (scriptSection != null) {
            scriptSection.dispose();
        }
        super.dispose();
    }

    /**
     * Create the User-defined-mapping-script section to be contained in, This
     * is called once on construction.
     * 
     * @return The script mapping editing section.
     */
    protected abstract BaseScriptSection getScriptSection();

    class GrammarContentProvider implements IStructuredContentProvider {

        /**
         * @param inputElement
         * @return
         * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
         */
        @Override
        public Object[] getElements(Object inputElement) {
            List<String> elements = new ArrayList<String>();
            if (inputElement instanceof Activity) {
                Activity activity = (Activity) inputElement;
                List<ScriptGrammarElement> grammars = Collections.EMPTY_LIST;
                try {
                    grammars =
                            ScriptGrammarFactory.getEnabledGrammars(activity,
                                    scriptSection.getScriptContext());
                } catch (CoreException e) {
                    LOG.error(e);
                }

                // Ensure current grammar is in list if already set.
                if (currentGrammar != null) {
                    boolean inList = false;

                    for (Iterator<ScriptGrammarElement> iterator =
                            grammars.iterator(); iterator.hasNext();) {
                        ScriptGrammarElement grEl = iterator.next();

                        if (grEl.getGrammarId().equals(currentGrammar)) {
                            inList = true;
                            break;
                        }
                    }

                    if (!inList) {
                        // Add existing grammar to list.
                        List<ScriptGrammarElement> allGrammars =
                                Collections.EMPTY_LIST;
                        try {
                            allGrammars =
                                    ScriptGrammarFactory
                                            .getGrammars(scriptSection
                                                    .getScriptContext());
                            for (Iterator<ScriptGrammarElement> iterator =
                                    allGrammars.iterator(); iterator.hasNext();) {
                                ScriptGrammarElement grEl = iterator.next();
                                if (grEl.getGrammarId().equals(currentGrammar)) {
                                    grammars.add(grEl);
                                }
                            }
                        } catch (CoreException e) {
                            LOG.error(e);
                        }

                    }

                }

                List<String> unmappable = new ArrayList<String>();
                grammarNameMap = new HashMap<String, String>();
                for (ScriptGrammarElement element : grammars) {
                    String id = element.getGrammarId();
                    String name = element.getGrammarName();
                    elements.add(id);
                    grammarNameMap.put(id, name);
                    if (!element.getMappable()) {
                        unmappable.add(id);
                    }
                }

                setMapperEnabled(!unmappable.contains(currentGrammar));
            }
            return elements.toArray();
        }

        /**
         * 
         * @see org.eclipse.jface.viewers.IContentProvider#dispose()
         */
        @Override
        public void dispose() {
        }

        /**
         * @param viewer
         * @param oldInput
         * @param newInput
         * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
         *      java.lang.Object, java.lang.Object)
         */
        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }

    }

    class GrammarLabelProvider extends LabelProvider {

        @Override
        public String getText(Object element) {
            String text = ""; //$NON-NLS-1$
            if (element instanceof String) {
                String key = (String) element;
                text = grammarNameMap.get(key);
                if (text == null) {
                    text = key;
                }
            }
            return text;
        }

    }

    /**
     * @return true if the script grammar selection combo should be disabled
     */
    protected boolean shouldDisableEditGrammarViewer() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.mapper.IErrorProvider#getSourceObjectTypeForRecusionComparison
     * (java.lang.Object)
     */
    @Override
    public Object getSourceObjectTypeForRecursionComparison(
            Object sourceTreeContentObject) {
        Object type =
                getObjectTypeForRecursionComparison(sourceTreeContentObject);
        if (type != null) {
            return type;
        }
        return super
                .getSourceObjectTypeForRecursionComparison(sourceTreeContentObject);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.tibco.xpd.mapper.IErrorProvider#getTargetObjectTypeForRecusionComparison
     * (java.lang.Object)
     */
    @Override
    public Object getTargetObjectTypeForRecursionComparison(
            Object targetTreeContentObject) {
        Object type =
                getObjectTypeForRecursionComparison(targetTreeContentObject);
        if (type != null) {
            return type;
        }

        return super
                .getTargetObjectTypeForRecursionComparison(targetTreeContentObject);
    }

    /**
     * @param object
     */
    private Object getObjectTypeForRecursionComparison(Object object) {
        if (object instanceof IWsdlPath) {
            IWsdlPath xsdPath = (IWsdlPath) object;

            XSDTypeDefinition xsdType = xsdPath.getType();
            if (xsdType != null) {
                return new XsdTypeForRecursionComparison(xsdType,
                        xsdPath.isArray());
            }
        }

        return null;
    }

    /**
     * For use with getObjectTypeForRecursionComparison.
     * <p>
     * That method has to return a type object that will be used by mapper for
     * comparing with ancerstor items to ensure against infinite recursions.
     * <p>
     * Trouble is that when you have a sequence of TypeA objects in XsdPath in
     * mapper there are automatically two members (the seuence and first child)
     * which are the same type (i.e. the seuence header is TypeA as well as each
     * element in sequence).
     * <p>
     * To resolve this issue we simply wrap the XsdType up in a class that also
     * compares whether it is a sequence header element.
     * 
     * 
     * @author aallway
     * @since 3.3 (31 Mar 2010)
     */
    private static class XsdTypeForRecursionComparison {
        Object xsdType;

        boolean isSequence;

        /**
         * @param xsdType
         * @param isSequence
         */
        public XsdTypeForRecursionComparison(Object xsdType, boolean isSequence) {
            super();
            this.xsdType = xsdType;
            this.isSequence = isSequence;
        }

        /**
         * we MUST override hashCode as well as equals because hashset checks
         * hashcode before even attempting to compare with equals.
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (isSequence ? 1231 : 1237);
            result =
                    prime * result
                            + ((xsdType == null) ? 0 : xsdType.hashCode());
            return result;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            XsdTypeForRecursionComparison other =
                    (XsdTypeForRecursionComparison) obj;
            if (isSequence != other.isSequence)
                return false;
            if (xsdType == null) {
                if (other.xsdType != null)
                    return false;
            } else if (!xsdType.equals(other.xsdType))
                return false;
            return true;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return (isSequence ? "[]" : "") + xsdType.toString(); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * Layout for the Sash divided section.
     * 
     * 
     * @author kthombar
     * @since TBS 4.0.0 (Apr 22, 2015)
     */
    private class SashDividedLayout extends Layout {

        @Override
        protected Point computeSize(Composite composite, int wHint, int hHint,
                boolean flushCache) {
            if (checkControls()) {
                if (wHint != SWT.DEFAULT && hHint != SWT.DEFAULT) {
                    //
                    // Now we have been given a size, let the children(mapping
                    // and script section) catch up.

                    Point sz = sash.computeSize(SWT.DEFAULT, SWT.DEFAULT);

                    int sashOffset = (int) (hHint * getSashPercent());

                    mappingSectionContainer
                            .computeSize(SWT.DEFAULT, sashOffset);
                    scriptSectionContainer.computeSize(SWT.DEFAULT, hHint
                            - sashOffset);

                }
            }

            // Make sure we're never bigger than the hint (this should prevent
            // us ever causing the section's main scrollbars into appearing).
            // We want our 2 sides to have individual scrolling.
            return new Point(wHint, hHint);
        }

        @Override
        protected void layout(Composite composite, boolean flushCache) {
            if (!checkControls()) {
                return;
            }

            float percent = getSashPercent();

            Rectangle bounds = composite.getBounds();

            // Position the Sash

            Point sz = sash.computeSize(SWT.DEFAULT, SWT.DEFAULT);

            int sashOffset = (int) (bounds.height * percent);

            sash.setBounds(0, sashOffset, bounds.width, sz.y);

            //
            // Position the mapping (upper) section.
            mappingSectionContainer.setBounds(0, 0, bounds.width, sashOffset
                    - (sz.y));

            //
            // Position the script (lower) section.
            scriptSectionContainer.setBounds(0,
                    sashOffset + (sz.y),
                    bounds.width,
                    bounds.height - (sashOffset - (sz.y)));

            return;
        }

        /**
         * 
         * @return <code>true</code> if none of the controls that sash is
         *         concerned with are <code>null</code> or disposed, else return
         *         <code>false</code>
         */
        private boolean checkControls() {
            if (sash != null && !sash.isDisposed()
                    && mappingSectionContainer != null
                    && !mappingSectionContainer.isDisposed()
                    && scriptSectionContainer != null
                    && !scriptSectionContainer.isDisposed()) {
                return true;
            }
            return false;
        }

        /**
         * 
         * @return the sash percent for the top(mapper) section.
         */
        private float getSashPercent() {
            /*
             * set the sash percent initially to default(60%).
             */
            float percent = sashPercent;

            if (getMapperViewer() != null) {
                if (!expanded) {
                    /*
                     * If the script container is collapsed then set the sash
                     * percent such that most of the mapping section is visible
                     * and only the script section header is visible.
                     */
                    Point scriptSectSz =
                            scriptSectionContainer.computeSize(SWT.DEFAULT,
                                    SWT.DEFAULT);

                    Point mainContainerSize = mainContainer.getSize();

                    if (scriptSectSz.y > 0 && mainContainerSize.y > 0) {
                        percent =
                                ((float) mainContainerSize.y - (float) scriptSectSz.y)
                                        / mainContainerSize.y;
                    }

                } else if (PropertiesPlugin.getDefault().getPreferenceStore()
                        .contains(horzSashPercentPrefId)) {
                    /*
                     * If the script section is excpanded and the preference
                     * store has an ertry for the last set sash percent then use
                     * it.
                     */
                    int sPercent =
                            PropertiesPlugin.getDefault().getPreferenceStore()
                                    .getInt(horzSashPercentPrefId);

                    if (sPercent < 1 || sPercent > 100) {
                        sPercent = 60;
                    }
                    percent = (float) sPercent / 100;
                }
            } else {
                /*
                 * if there is no mapper section then let the script section
                 * take the entire space.
                 */
                percent = 0;
            }
            return percent;
        }
    }
}
