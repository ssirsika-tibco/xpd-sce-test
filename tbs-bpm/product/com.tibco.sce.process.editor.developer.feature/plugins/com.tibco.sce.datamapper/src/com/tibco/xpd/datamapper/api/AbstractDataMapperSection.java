/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.CheckedTreeSelectionDialog;

import com.tibco.xpd.datamapper.DataMapperConstants;
import com.tibco.xpd.datamapper.DataMapperMappingCommandFactory;
import com.tibco.xpd.datamapper.DataMapperMappingContentProvider;
import com.tibco.xpd.datamapper.DataMapperMappingTransferValidator;
import com.tibco.xpd.datamapper.DataMapperPlugin;
import com.tibco.xpd.datamapper.infoProviders.ContributableDataMapperInfoProvider;
import com.tibco.xpd.datamapper.infoProviders.WrappedContributedContent;
import com.tibco.xpd.datamapper.internal.Messages;
import com.tibco.xpd.datamapper.rules.AbstractDataMapperMappingRule;
import com.tibco.xpd.datamapper.scripts.AbstractScriptDataMapperEditorProvider;
import com.tibco.xpd.datamapper.scripts.IScriptDataMapperProvider;
import com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractEditableMappingSection;
import com.tibco.xpd.mapper.IMappingTransferValidator;
import com.tibco.xpd.mapper.MapperLabelProvider;
import com.tibco.xpd.mapper.MapperViewer;
import com.tibco.xpd.mapper.MapperViewerInput;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.mapper.MappingDirection;
import com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory2;
import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.DataMapperArrayInflation;
import com.tibco.xpd.xpdExtension.DataMapperArrayInflationType;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.Xpdl2Package;

/**
 * <p>
 * This class is responsible for providing data mapper source/target info
 * providers created through its sub-classes for a particular mapping scenario
 * (e.g., ProcessDataMapperSection, WebServiceInputMappingSection etc).
 * </p>
 * 
 * <p>
 * This class is model agnostic and only deals with source and target mapper
 * elements as objects. Any functionality that needs to know the type of the
 * content should no be added here and should be done in the sub-classes and
 * info providers, which actually provide the content and know what they are
 * contributing.
 * </p>
 * 
 * <p>
 * The sub-classes:
 * <li>will be asked to create the source and target content providers during
 * the construction</li>
 * <li>will be asked for the command provider to deal with model changes based
 * on the user actions in the Data Mapper viewer</li>
 * <li>will be asked to provide the {@link ScriptDataMapper} element for the
 * current input, which will be used as a container for the data mappings in the
 * XPDL model.</li>
 * </p>
 * 
 * @author Ali
 * @since 12 Jan 2015
 */
public abstract class AbstractDataMapperSection extends
        AbstractEditableMappingSection {

    /**
     * The command factory DO NOT access this directly, use the
     * {@link #getCommandFactory()} method instead.
     */
    private DataMapperMappingCommandFactory commandFactory;

    private AbstractDataMapperInfoProvider sourceInfoProvider;

    private AbstractDataMapperInfoProvider targetInfoProvider;

    private DataMapperMappingContentProvider mappingContentProvider;

    /**
     * @param direction
     */
    public AbstractDataMapperSection(MappingDirection direction) {
        super(direction);

        setMapperLabelProvider(new MapperLabelProvider(getSourceInfoProvider()
                .getLabelProvider(), getTargetInfoProvider().getLabelProvider()));
    }

    /**
     * Get the data mapper context for the given mapping scenario. The data
     * mapper context is what the data mapper info provider (source / target
     * content provision) contributions for a given mapping scenario will hook
     * into.
     * <p>
     * A data mapper context if for a given mapping scenario, for example
     * 'Sub-Process Input' would be one scenario and 'Sub process output' would
     * be another.
     * 
     * @return The data mapper context for the sub-classes specific mapping
     *         scenario.
     */
    protected abstract String getDataMapperContext();

    /**
     * This method should return the Script Data Mapper Provider (used to access
     * the xpdExt:ScriptDataMapper element for the specific script context
     * handled by the sub-class)
     * 
     * @return
     */
    protected abstract AbstractScriptDataMapperEditorProvider getScriptDataMapperProvider();

    /**
     * This method will be invoked from the constructor.
     * 
     * @return DataMapperInfoProvider for the mapper viewere's LHS content
     */
    @Override
    protected final IStructuredContentProvider createMappingContentProvider() {
        return getMappingContentProvider();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#doCreateMapperViewer(org.eclipse.swt.widgets.Composite)
     * 
     * @param container
     * @return
     */
    @Override
    protected MapperViewer doCreateMapperViewer(Composite container) {
        return new MapperViewer(container) {
            /**
             * @see com.tibco.xpd.mapper.MapperViewer#fillAdditionalMenuCOntent(org.eclipse.jface.action.IMenuManager)
             * 
             * @param manager
             */
            @Override
            protected void fillAdditionalMenuContent(IMenuManager manager) {
                super.fillAdditionalMenuContent(manager);

                List<Action> likeGroupActions =
                        getLikeMappingContextMenuActions();
                // add like menu actions to menu group 1
                if (likeGroupActions != null) {
                    for (Action act : likeGroupActions) {
                        manager.appendToGroup(MapperViewer.MAPPER_MENU_GROUP_1_ID,
                                act);
                    }
                }
                // add list menu actions to menu group 2
                List<Action> listGroupActions = getListContextMenuActions();
                if (listGroupActions != null) {
                    for (Action act : listGroupActions) {
                        manager.appendToGroup(MapperViewer.MAPPER_MENU_GROUP_2_ID,
                                act);
                    }
                }
            }
        };
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getMappingCommandFactory2()
     * 
     * @return
     */
    @Override
    protected IMappingCommandFactory2 getMappingCommandFactory2() {
        return getCommandFactory();
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getMappingCommandFactory()
     * 
     * @return
     * @deprecated
     */
    @Deprecated
    @Override
    protected final IMappingCommandFactory getMappingCommandFactory() {
        return null;
    }

    /**
     * 
     * @return source content provider
     */
    @Override
    protected ITreeContentProvider createSourceContentProvider() {
        return getSourceInfoProvider().getContentProvider();
    }

    /**
     * 
     * @return target content provider
     */
    @Override
    protected ITreeContentProvider createTargetContentProvider() {
        return getTargetInfoProvider().getContentProvider();
    }

    /**
     * Sid XPD-7996. Remove override of getGrammar()
     * 
     * Used to hard coded return ScriptGrammarFactory.JAVASCRIPT but that was a
     * mistake (I think) in original implementation. This method is only used to
     * indicate the Mapping Grammar (NOT the separate grammar of user defined
     * scripts (which is why I think this method was overridden originally
     * because Ali though this was required to get correct grammar in the user
     * defined script mappings, but that is defiend elsewhere).
     * 
     * So changed back to use use default implementation (uses
     * ScriptGrammarFactory). If the user switches to some other available
     * grammar in the grammar selection dropdown then the base mapping section
     * should really refresh tabs to switch this section off and the other
     * grammar's section on.
     */

    /**
     * Sid XPD-7575: We don't always want label in our data mapper section, it
     * should often be obvious from the script scenario we're being used for.
     * <p>
     * So if sub-class returns title as null we'll collapse to nothing.
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#createHeaderLabelControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param parent
     * @param tk
     * @return
     */
    @Override
    protected Control createHeaderLabelControls(Composite parent,
            XpdFormToolkit tk) {

        String title = getTitle();
        if (title != null) {
            return tk.createLabel(parent, title);

        } else {
            Composite blank = tk.createComposite(parent);
            GridLayout gl = new GridLayout();
            gl.marginHeight = 0;
            gl.marginWidth = 0;
            blank.setLayout(gl);
            return blank;
        }
    }

    /**
     * Sid XPD-7575 - don't want grammar selection AND don't want to use up any
     * space for it either (so we'll override the whole creation method).
     * <p>
     * Unless that is... If sub-class overrides 'don't show grammar selection
     * control' then we want to do normal header creation.
     * 
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractEditableMappingSection#createHeaderControls(org.eclipse.swt.widgets.Composite,
     *      com.tibco.xpd.ui.properties.XpdFormToolkit)
     * 
     * @param header
     * @param tk
     */
    @Override
    protected void createHeaderControls(Composite header, XpdFormToolkit tk) {
        /*
         * If sub-class overrides 'don't show grammar selection control' then we
         * want to do normal header creation.
         */
        if (showGrammarSelectionCombo()) {
            super.createHeaderControls(header, tk);

        } else {
            GridLayout gl = new GridLayout();
            gl.marginHeight = 0;
            gl.marginWidth = 0;
            header.setLayout(gl);
        }
    }

    /**
     * DataMapper is intended ONLY for JavaScript grammar, therefore do not
     * allow grammar selection.
     * 
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractEditableMappingSection#showGrammarSelectionCombo()
     * 
     * @return
     */
    @Override
    protected boolean showGrammarSelectionCombo() {
        return false;
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractEditableMappingSection#doRefresh()
     * 
     */
    @Override
    protected void doRefresh() {
        super.doRefresh();
        EObject input = getInput();
        
        /* Sid XPD-8251 Allow mapping content provider to re-evaluate any cached data. */
        getMappingContentProvider().refresh(input);
        
        if (input != null) {

            if (getMapperViewer().getInput() instanceof MapperViewerInput
                    && !input.equals(((MapperViewerInput) getMapperViewer()
                            .getInput()).getMappingInput())) {
                MapperViewerInput mapperInput =
                        new MapperViewerInput(input, input, input);
                setMapperViewerInput(mapperInput);

            } else {
                getMapperViewer().refresh();

            }
        }
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#updateName(com.tibco.xpd.xpdExtension.ScriptInformation,
     *      java.lang.String)
     * 
     * @param information
     * @param newValue
     */
    @Override
    protected void updateName(ScriptInformation information, String value) {
        String oldValue = information.getName();
        String newValue = value.trim();
        if (!newValue.equals(oldValue) && newValue.length() > 0) {
            CompoundCommand command =
                    new CompoundCommand(
                            Messages.AbstractDataMapperSection_editScriptNameTitle);
            Activity activity = (Activity) getInput();
            EditingDomain ed = WorkingCopyUtil.getEditingDomain(activity);
            if (ed != null) {
                if (information.eContainer() == null) {
                    if (activity != null) {
                        ScriptDataMapper scriptDataMapper =
                                getScriptDataMapperProvider()
                                        .getOrCreateScriptDataMapper(activity,
                                                ed,
                                                command);

                        information.setName(newValue);
                        command.append(AddCommand.create(ed,
                                scriptDataMapper,
                                XpdExtensionPackage.eINSTANCE
                                        .getScriptDataMapper_UnmappedScripts(),
                                information));
                    }
                } else {
                    command.append(SetCommand.create(ed,
                            information,
                            Xpdl2Package.eINSTANCE.getNamedElement_Name(),
                            newValue));
                }
                if (command != null && command.canExecute()) {
                    ed.getCommandStack().execute(command);
                }
            }
        }
    }

    /**
     * @return the sourceInfoProvider
     */
    private AbstractDataMapperInfoProvider getSourceInfoProvider() {
        if (sourceInfoProvider == null) {
            sourceInfoProvider =
                    new ContributableDataMapperInfoProvider(
                            getScriptDataMapperProvider(),
                            getDataMapperContext(), false, false,
                            isInputMapping());
        }
        return sourceInfoProvider;
    }

    /**
     * @return true if this is for an input mapping.
     */
    private boolean isInputMapping() {
        return MappingDirection.IN.equals(getDirection());
    }

    /**
     * @return the targetInfoProvider
     */
    private AbstractDataMapperInfoProvider getTargetInfoProvider() {
        if (targetInfoProvider == null) {
            targetInfoProvider =
                    new ContributableDataMapperInfoProvider(
                            getScriptDataMapperProvider(),
                            getDataMapperContext(), true, false,
                            isInputMapping());
        }
        return targetInfoProvider;
    }

    /**
     * @return the mappingContentProvider
     */
    protected DataMapperMappingContentProvider getMappingContentProvider() {
        if (mappingContentProvider == null) {
            mappingContentProvider = createDataMapperMappingContentProvider();
        }
        return mappingContentProvider;
    }

    /**
     * Get list of actions to be added to the mapper context menu for like
     * mappping. This includes: actions for setting like mapping and opening
     * like mapping exclusion list
     * 
     * @return list of actions
     */
    private List<Action> getLikeMappingContextMenuActions() {
        List<Action> actions = new ArrayList<>();

        Collection<Mapping> mapperSelection =
                getMapperViewer().getSelectedMappings();

        // don't show menu actions when multiple elements are selected
        if (mapperSelection != null && mapperSelection.size() == 1) {
            Mapping selectedMapping = mapperSelection.iterator().next();

            // add menu action to set/unset like mapping attribute
            if (!(selectedMapping instanceof VirtualLikeMapping)) {
                if (isLikeMappingActionEnabled(selectedMapping)
                        || isLikeMappingSet(selectedMapping)) {

                    Action likeMappingAction =
                            new ConfigureAsLikeMappingAction(selectedMapping);
                    /*
                     * Sid XPD-7399 - cannot see check state of 'Map Like Named
                     * Children' anyway so change to differnet text inthe action
                     * instead
                     */
                    actions.add(likeMappingAction);
                }

                /*
                 * add menu action to show like mapping exclusion list dialog
                 * box
                 */
                if (isLikeMappingSet(selectedMapping)) {
                    Action openLikeMappingExclusionsAction =
                            new OpenLikeMappingExclusionsAction(selectedMapping);
                    actions.add(openLikeMappingExclusionsAction);
                }
            }

            /*
             * Add 'Add To Like Mapping Exclusions' to the virtual mappigns that
             * are applied to the children of like mappings.
             */

            if (selectedMapping instanceof VirtualLikeMapping) {
                actions.add(new AddToLikeMappingExclusionsAction(
                        selectedMapping.getMappingModel(), selectedMapping
                                .getTarget()));
            }

        }

        /*
         * Add 'Remove From Like Mapping Exclusions' to any target object that
         * is included in a like mapping exclusions list.
         */
        Object selectedTarget = getCurrentSelectedTargetObject();

        if (selectedTarget != null) {
            List<DataMapping> removeFromMappingExclusions = new ArrayList<>();

            for (Mapping mapping : getMapperViewer().getMappings()) {
                if (mapping.getMappingModel() instanceof DataMapping) {
                    List<Object> likeMappingExclusionItems =
                            getLikeMappingExclusionItems((DataMapping) mapping
                                    .getMappingModel());

                    if (likeMappingExclusionItems.contains(selectedTarget)) {
                        removeFromMappingExclusions.add((DataMapping) mapping
                                .getMappingModel());
                    }

                }
            }

            if (!removeFromMappingExclusions.isEmpty()) {
                actions.add(new RemoveFromLikeMappingExclusionsAction(
                        selectedTarget, removeFromMappingExclusions));

            } else {
                /*
                 * SId XPD-7744 : Add 'Exclude From Like mapping' action to
                 * complex children of like mapping (and obviously is not mapped
                 * itself and not if it is child of explicit mapping under a
                 * like mapping etc).
                 */
                if (!getTargetInfoProvider()
                        .isSimpleTypeContent(selectedTarget)) {

                    /*
                     * If target is directly mapped then it won't be included in
                     * parent like mapping anyway so ignore if mapped.
                     */
                    if (getMappingToTarget(selectedTarget) == null) {
                        /*
                         * Find first parent mapped, if it's a like mapping then
                         * this target object is content of a like mapping.
                         */
                        Mapping closestParentMapping =
                                getClosestTargetAncestorMapping(selectedTarget);

                        /*
                         * if closest parent mapping is Like mapping then user
                         * can exclude this content.
                         */
                        if (closestParentMapping != null
                                && isLikeMappingSet(closestParentMapping)) {
                            actions.add(new AddToLikeMappingExclusionsAction(
                                    closestParentMapping.getMappingModel(),
                                    selectedTarget));
                        }

                    }
                }
            }
        }
        return actions;
    }

    /**
     * @param target
     * @return The mapping to the closest ancestor of the given target or
     *         <code>null</code> if no ancestor mapped.
     */
    private Mapping getClosestTargetAncestorMapping(Object target) {
        Mapping closestParentMapping = null;

        Object targetParent =
                getTargetInfoProvider().getContentProvider().getParent(target);

        while (targetParent != null) {
            Mapping mapping = getMappingToTarget(targetParent);
            if (mapping != null) {
                closestParentMapping = mapping;
                break;
            }
            targetParent =
                    getTargetInfoProvider().getContentProvider()
                            .getParent(targetParent);
        }
        return closestParentMapping;
    }

    /**
     * Get the mapping to the given target object if there is one.
     * 
     * @param target
     *            Object from target tree
     * @return Mapping or <code>null</code> if not mapped.
     */
    private Mapping getMappingToTarget(Object target) {
        for (Mapping mapping : getMapperViewer().getMappings()) {
            if (target.equals(mapping.getTarget())) {
                return mapping;
            }
        }
        return null;
    }

    /**
     * Get actions to be added to the mapper context menu for arrays. This
     * includes: actions for overwrite, append and merge.
     * 
     * @return list of actions
     */
    private List<Action> getListContextMenuActions() {
        List<Action> actions = new ArrayList<>();

        // don't show menu actions when multiple elements are selected
        if (!multipleItemsSelectedInMapperViewer()) {

            Action overwriteListAction = null;
            Action appendListAction = null;
            Action mergeListAction = null;

            // overwrite/append available for simple and complex type arrays
            if (isTargetSelectionMultiInstance()) {
                // add menu action to set array inflation type as overwrite list
                overwriteListAction =
                        new Action(
                                Messages.AbstractDataMapperSection_overwriteList) {

                            @Override
                            public void run() {
                                setArrayInflationType(getCurrentSelectedTargetObject(),
                                        null);
                            }

                            /**
                             * @see org.eclipse.jface.action.Action#getImageDescriptor()
                             * 
                             * @return
                             */
                            @Override
                            public ImageDescriptor getImageDescriptor() {
                                return DataMapperPlugin
                                        .getDefault()
                                        .getImageRegistry()
                                        .getDescriptor(DataMapperConstants.IMG_OVERWRITE_LIST);
                            }

                        };
                actions.add(overwriteListAction);

                // menu action to set array inflation type as append list
                appendListAction =
                        new Action(
                                Messages.AbstractDataMapperSection_appendList) {

                            @Override
                            public void run() {
                                setArrayInflationType(getCurrentSelectedTargetObject(),
                                        DataMapperArrayInflationType.APPEND_LIST);
                            }

                            /**
                             * @see org.eclipse.jface.action.Action#getImageDescriptor()
                             * 
                             * @return
                             */
                            @Override
                            public ImageDescriptor getImageDescriptor() {
                                return DataMapperPlugin
                                        .getDefault()
                                        .getImageRegistry()
                                        .getDescriptor(DataMapperConstants.IMG_APPEND_LIST);
                            }
                        };
                actions.add(appendListAction);

                /*
                 * Sid XPD-7737 - Merge list needs to be available for simple
                 * type because it is NOT always just same as an overwrite (for
                 * instance, if there are fewer source elements than tyarget
                 * then need to overwrite target elemetns up to that point but
                 * still want remainder of target elemetns left there)
                 */

                // menu action to set array inflation type as merge list
                mergeListAction =
                        new Action(Messages.AbstractDataMapperSection_mergeList) {

                            @Override
                            public void run() {
                                setArrayInflationType(getCurrentSelectedTargetObject(),
                                        DataMapperArrayInflationType.MERGE_LIST);
                            }

                            /**
                             * @see org.eclipse.jface.action.Action#getImageDescriptor()
                             * 
                             * @return
                             */
                            @Override
                            public ImageDescriptor getImageDescriptor() {
                                return DataMapperPlugin
                                        .getDefault()
                                        .getImageRegistry()
                                        .getDescriptor(DataMapperConstants.IMG_MERGE_LIST);
                            }
                        };
                actions.add(mergeListAction);
            }

            // select the current array inflation type
            ScriptDataMapper scriptDataMapper =
                    getScriptDataMapperProvider()
                            .getScriptDataMapper(getInput());

            DataMapperArrayInflationType currentArrayInflationType =
                    getArrayInflationType(scriptDataMapper,
                            targetInfoProvider
                                    .getObjectPath(getCurrentSelectedTargetObject()));

            if (DataMapperArrayInflationType.APPEND_LIST
                    .equals(currentArrayInflationType)
                    && appendListAction != null) {
                appendListAction.setChecked(true);

            } else if (DataMapperArrayInflationType.MERGE_LIST
                    .equals(currentArrayInflationType)
                    && mergeListAction != null) {
                mergeListAction.setChecked(true);

            } else if (overwriteListAction != null) {
                // overwrite is default selection if nothing is set
                overwriteListAction.setChecked(true);
            }

        }

        return actions;
    }

    /**
     * @param createScriptDataMapper
     * @param targetObjectPath
     * 
     * @return Array inflation type set for the given target object
     */
    private DataMapperArrayInflationType getArrayInflationType(
            ScriptDataMapper scriptDataMapper, String targetObjectPath) {
        if (scriptDataMapper != null && targetObjectPath != null) {

            for (DataMapperArrayInflation inflationTypeElement : scriptDataMapper
                    .getArrayInflationType()) {
                if (inflationTypeElement.getPath().equals(targetObjectPath)) {
                    return inflationTypeElement.getMappingType();
                }
            }
        }
        return null;
    }

    /**
     * Checks if the current selected object in the mapper target viewer is a
     * complex type array
     * 
     * @return boolean
     */
    private boolean isTargetSelectionComplexTypeArray() {

        Object targetObject = getCurrentSelectedTargetObject();
        if (targetObject != null) {
            if (!getTargetInfoProvider().isSimpleTypeContent(targetObject)
                    && getTargetInfoProvider().isMultiInstance(targetObject)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return DataMapping based on the current selected source and target
     *         objects in the mapper , null otherwise
     */
    public DataMapping getCurrentSelectecDataMapping() {

        Object source = getCurrentSelectedSourceObject();
        Object target = getCurrentSelectedTargetObject();

        return getMappingContentProvider().getDataMapping(getInput(),
                source,
                target);
    }

    /**
     * Checks if the current selected item in the mapper target viewer is a an
     * array
     * 
     * @return boolean
     */
    private boolean isTargetSelectionMultiInstance() {

        Object target = getCurrentSelectedTargetObject();
        return getTargetInfoProvider().isMultiInstance(target);
    }

    /**
     * Return current selected target object or null if there a zero or multiple
     * items selected.
     * 
     * @return selected objected or null
     */
    private Object getCurrentSelectedTargetObject() {

        IStructuredSelection targetSel =
                (IStructuredSelection) getMapperViewer().getTargetViewer()
                        .getSelection();
        if (targetSel.size() == 1 && targetSel.getFirstElement() != null) {
            return targetSel.getFirstElement();
        }
        return null;
    }

    /**
     * Return current selected source object
     * 
     * @return selected objected or null
     */
    private Object getCurrentSelectedSourceObject() {

        IStructuredSelection sourceSel =
                (IStructuredSelection) getMapperViewer().getSourceViewer()
                        .getSelection();
        if (sourceSel.getFirstElement() != null) {
            return sourceSel.getFirstElement();
        }
        return null;
    }

    /**
     * @param mapping
     * @return true if the current mapping selection's source and target items
     *         are complex type objects with children, false otherwise
     */
    protected boolean isLikeMappingActionEnabled(Mapping mapping) {

        Object sourceSelectedObj = mapping.getSource();
        Object targetSelectedObj = mapping.getTarget();
        if (sourceSelectedObj != null && targetSelectedObj != null) {
            if (!getSourceInfoProvider().isSimpleTypeContent(sourceSelectedObj)
                    && !getTargetInfoProvider()
                            .isSimpleTypeContent(targetSelectedObj)) {
                if (getSourceInfoProvider().getContentProvider()
                        .hasChildren(sourceSelectedObj)
                        && getTargetInfoProvider().getContentProvider()
                                .hasChildren(targetSelectedObj)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * @return true if more than one item selected on each side of the mapper
     *         viewer, false otherwise
     */
    protected boolean multipleItemsSelectedInMapperViewer() {

        MapperViewer mapperViewer = getMapperViewer();
        IStructuredSelection sourceSel =
                (IStructuredSelection) mapperViewer.getSourceViewer()
                        .getSelection();
        IStructuredSelection targetSel =
                (IStructuredSelection) mapperViewer.getTargetViewer()
                        .getSelection();
        if (sourceSel.size() > 1 && targetSel.size() > 1) {
            return true;
        }
        return false;
    }

    /**
     * @return true if the current mapper selection is has like mapping
     *         attribute set, false otherwise
     */
    private boolean isLikeMappingSet(Mapping mapping) {

        Object dataMapping = mapping.getMappingModel();
        if (dataMapping instanceof DataMapping) {
            return getMappingContentProvider()
                    .isLikeMappingSet((DataMapping) dataMapping);
        }
        return false;
    }

    /**
     * Sets 'like mapping' attribute for the given data mapping with the given
     * value
     * 
     * @param dataMapping
     * @param newValue
     */
    private void setLikeMapping(DataMapping dataMapping, boolean newValue) {

        EditingDomain editingDomain = getEditingDomain();
        Command command = null;
        if (dataMapping != null) {
            command =
                    getCommandFactory().getSetLikeMappingCommand(editingDomain,
                            getInput(),
                            dataMapping,
                            newValue);
        }

        if (command != null && command.canExecute()) {
            editingDomain.getCommandStack().execute(command);
        }
    }

    /**
     * Sets array inflation type for the given target item
     * 
     * @param dataMapping
     * @param newValue
     */
    private void setArrayInflationType(Object target,
            DataMapperArrayInflationType arrayInflationType) {

        EditingDomain editingDomain = getEditingDomain();
        Command command = null;
        if (target != null) {
            if (arrayInflationType == null) {
                command =
                        getCommandFactory()
                                .getSetDefaultArrayInflationType(editingDomain,
                                        getInput(),
                                        target,
                                        getTargetInfoProvider());
            } else {
                command =
                        getCommandFactory()
                                .getSetArrayInflationType(editingDomain,
                                        getInput(),
                                        target,
                                        getTargetInfoProvider(),
                                        arrayInflationType);
            }
        }
        if (command != null && command.canExecute()) {
            editingDomain.getCommandStack().execute(command);
        }
    }

    /**
     * Adds the given items to the data mapping's like mapping exclusion list
     * 
     * @param dataMapping
     * @param selectedItems
     */
    private void addLikeMappingExclusions(DataMapping dataMapping,
            ArrayList<Object> selectedItems) {
        EditingDomain editingDomain = getEditingDomain();
        Command command = null;
        if (selectedItems != null) {
            command =
                    getCommandFactory()
                            .getSetLikeMappingExclusionsListCommand(editingDomain,
                                    dataMapping,
                                    selectedItems,
                                    getTargetInfoProvider());
        }
        if (command != null && command.canExecute()) {
            editingDomain.getCommandStack().execute(command);
        }
    }

    /**
     * 
     * This class provides checked tree item dialog box for checking/un-checking
     * like mapping exclusions for a given like mapping data mapping
     */
    class LikeMappingExclusionSelectionDialogBox extends
            CheckedTreeSelectionDialog {

        /**
         * @param parent
         * @param labelProvider
         * @param contentProvider
         */
        public LikeMappingExclusionSelectionDialogBox(Shell parent,
                ILabelProvider labelProvider,
                ITreeContentProvider contentProvider) {
            super(parent, labelProvider, contentProvider);
            setTitle(Messages.AbstractDataMapperSection_LikeMappingExclusionsDialogBoxTitle);
            setMessage(Messages.AbstractDataMapperSection_LikeMappingExclusionsDialogBoxDescription);
        }
    }

    /**
     * Returns list of like mapping exclusions for the given data mapping
     * 
     * @param dataMapping
     * @return
     */
    private List<Object> getLikeMappingExclusionItems(DataMapping dataMapping) {
        return getMappingContentProvider()
                .getLikeMappingExclusionItems(dataMapping, getInput());
    }

    /**
     * Content provider for like mapping exclusion list dialog box
     * 
     */
    class LikeMappingExclusionContentProvider implements ITreeContentProvider {

        private ITreeContentProvider contentProvider;

        LikeMappingExclusionContentProvider(ITreeContentProvider contentProvider) {
            this.contentProvider = contentProvider;
        }

        /**
         * @see org.eclipse.jface.viewers.IContentProvider#dispose()
         * 
         */
        @Override
        public void dispose() {
            if (contentProvider != null) {
                contentProvider.dispose();
            }
        }

        /**
         * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
         *      java.lang.Object, java.lang.Object)
         * 
         * @param viewer
         * @param oldInput
         * @param newInput
         */
        @Override
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }

        /**
         * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
         * 
         * @param inputElement
         * @return
         */
        @Override
        public Object[] getElements(Object inputElement) {

            if (contentProvider != null) {
                return contentProvider.getChildren(inputElement);
            }
            return new Object[0];
        }

        /**
         * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
         * 
         * @param parentElement
         * @return
         */
        @Override
        public Object[] getChildren(Object parentElement) {
            if (contentProvider != null) {
                return contentProvider.getChildren(parentElement);
            }
            return new Object[0];
        }

        /**
         * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        public Object getParent(Object element) {
            if (contentProvider != null) {
                return contentProvider.getParent(element);
            }
            return null;
        }

        /**
         * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
         * 
         * @param element
         * @return
         */
        @Override
        public boolean hasChildren(Object element) {
            if (contentProvider != null) {
                return contentProvider.hasChildren(element);
            }
            return false;
        }
    }

    /**
     * Override super class as it does treats LHS and RHS as stored in different
     * places in the model when mapping is IN or OUT direction. Script
     * DataMapper always uses DataMapping/Actual for LHS and DataMapping/Formal
     * for RHS.
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractActivityMappingProblemMarkerHandlingSection#getProblemMarkerDataMappingURIs(java.lang.String,
     *      boolean)
     * 
     * @param dataMappingPath
     * @param isSourcePath
     * @return
     */
    @Override
    protected Collection<String> getProblemMarkerDataMappingURIs(
            String dataMappingPath, boolean isSourcePath) {
        Collection<String> uris = new ArrayList<>();

        Object[] mappings = mappingContentProvider.getElements(getInput());

        /*
         * Sid XPD-7399. We used to look compare the given dataMappingPath with
         * the content of the DataMapping formal/actual.
         * 
         * BUT the API contract is that the dataMappingPath will be passed to us
         * exactly as the return from getProblemMarkerDataMappingTargetPath() or
         * getProblemMarkerDataMappingSourcePath()
         * 
         * Therefore we should match the dataMappingPath to the Object path for
         * the source / target NOT the raw model object.
         * 
         * This will also mean that problems raised against virtual like child
         * content mappings wil resolve the correct mapping (because there won't
         * be a physical xpdl2:DataMapping with the virtual like mapping target
         * path)
         */

        for (Object mapping : mappings) {
            if (mapping instanceof Mapping) {
                Mapping m = (Mapping) mapping;

                Object model = m.getMappingModel();

                if (model instanceof DataMapping) {
                    DataMapping dm = (DataMapping) model;

                    if (isSourcePath) {
                        String sourcePath =
                                getProblemMarkerDataMappingSourcePath(((Mapping) mapping)
                                        .getSource());

                        if (dataMappingPath.equals(sourcePath)) {

                            addMappingUris(dm, uris);

                        } else {
                            ScriptInformation mappingScript =
                                    DataMappingUtil.getMappingScript(dm);

                            if (mappingScript != null
                                    && dataMappingPath.equals(mappingScript
                                            .getName())) {
                                addMappingUris(dm, uris);
                            }
                        }

                    } else {
                        String targetPath =
                                getProblemMarkerDataMappingTargetPath(((Mapping) mapping)
                                        .getTarget());

                        if (dataMappingPath.equals(targetPath)) {
                            addMappingUris(dm, uris);
                        }
                    }
                }
            }
        }

        return uris;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getTransferValidator()
     * 
     * @return
     */
    @Override
    protected IMappingTransferValidator getTransferValidator() {
        return new DataMapperMappingTransferValidator(getSourceInfoProvider(),
                getTargetInfoProvider());
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getProblemMarkerDataMappingTargetPath(java.lang.Object)
     * 
     * @param target
     * @return
     */
    @Override
    protected String getProblemMarkerDataMappingTargetPath(Object target) {
        String path = null;
        if (target instanceof WrappedContributedContent) {
            WrappedContributedContent wcc = (WrappedContributedContent) target;
            Object targetObject = wcc.getContributedObject();
            path =
                    wcc.getContributor().getInfoProvider()
                            .getObjectPath(targetObject);
        }
        return path;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractActivityMappingProblemMarkerHandlingSection#getProblemMarkerDataMappingSourcePath(java.lang.Object)
     * 
     * @param source
     * @return
     */
    @Override
    protected String getProblemMarkerDataMappingSourcePath(Object source) {
        String path = null;
        if (source instanceof WrappedContributedContent) {
            WrappedContributedContent wcc = (WrappedContributedContent) source;
            Object sourceObject = wcc.getContributedObject();
            path =
                    wcc.getContributor().getInfoProvider()
                            .getObjectPath(sourceObject);

        } else if (source instanceof ScriptInformation) {
            path = ((ScriptInformation) source).getName();
        }
        return path;
    }

    /**
     * 
     * @see com.tibco.xpd.datamapper.api.AbstractDataMapperSection#createDataMapperMappingContentProvider()
     * 
     * @return
     */
    protected DataMapperMappingContentProvider createDataMapperMappingContentProvider() {
        IScriptDataMapperProvider provider = getScriptDataMapperProvider();
        return new DataMapperMappingContentProvider(provider);
    }

    /**
     * Gets the command factory (creating on first request)
     * 
     * @return The command factory
     */
    private DataMapperMappingCommandFactory getCommandFactory() {
        if (commandFactory != null) {
            return commandFactory;
        }

        commandFactory =
                new DataMapperMappingCommandFactory(
                        getScriptDataMapperProvider());

        return commandFactory;
    }

    /**
     * AbstractDataMapperMappingRule always adds the specific data mapper
     * scenario context to the additional info in the marker.
     * 
     * So for our use case should just be a simple case of checking that the
     * script context in the problem marker matches the script context for
     * mapping section.
     * 
     * This reliease on the fact that ALL data mapper related rules will have
     * {@link AbstractDataMapperMappingRule#MARKER_INFO_DATA_MAPPER_SCRIPT_CONTEXT}
     * added to the marker info, AbstractDataMapperMappingRule handles this by
     * ensuring that every created issue has this info added.
     * 
     * See {@link AbstractDataMapperMappingRule}.createIssue().
     * 
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#isProblemMarkerApplicable(org.eclipse.core.resources.IMarker,
     *      java.util.Properties)
     * 
     * @param marker
     * @param problemMarkerAdditionalInfo
     * @return
     */
    @Override
    protected boolean isProblemMarkerApplicable(IMarker marker,
            Properties problemMarkerAdditionalInfo) {
        Object markerScriptContext =
                problemMarkerAdditionalInfo
                        .get(AbstractDataMapperMappingRule.MARKER_INFO_DATA_MAPPER_SCRIPT_CONTEXT);

        try {
            Object msg = marker.getAttributes().get("message"); //$NON-NLS-1$

            if (markerScriptContext instanceof String) {
                return markerScriptContext.equals(getDataMapperContext());
            }
        } catch (CoreException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Overriding shouldRefresh as many sections can belong to the same Activity
     * ancestor, but we only want to refresh if the change is related to this
     * specific section.
     * 
     * @see com.tibco.xpd.ui.properties.AbstractTransactionalSection#shouldRefresh(java.util.List)
     * 
     * @param notifications
     * @return
     */
    @Override
    protected boolean shouldRefresh(List<Notification> notifications) {
        boolean refresh = false;

        Activity activity = (Activity) getInput();
        if (activity != null) {
            ScriptDataMapper input =
                    getScriptDataMapperProvider().getScriptDataMapper(activity);

            /*
             * Sid XPD-7996. Used to ensure that ScripDatamapper element was set
             * here and not check notifications. However, if there is no
             * ScriptDatamapper element (yet!) then affecting changes are still
             * possible (such as change of explicitly selected script grammar
             * 
             * So moved null check lower.
             */
            if (notifications != null && !notifications.isEmpty()) {
                for (Notification notification : notifications) {
                    if (!notification.isTouch()) {
                        // AbstractProcessPreCommitContributor
                        // .outputNotfication(notification);

                        Object notifier = notification.getNotifier();

                        if (notifier instanceof EObject) {
                            if (input != null) {
                                /*
                                 * Refresh for any change under ScriptDataMapper
                                 * element.
                                 */
                                if (notifier == input
                                        || EcoreUtil.isAncestor(input,
                                                (EObject) notifier)) {
                                    refresh = true;
                                    break;
                                }
                            }
                            /*
                             * Sid XPD-7996 : Any change to a ScriptInformation
                             * directly under activity could constitute a
                             * grammar change (this is how grammar is preserved
                             * when user selects a grammar without drawing any
                             * mappings yet).
                             */
                            if (notifier instanceof ScriptInformation
                                    && ((EObject) notifier).eContainer() != null
                                    && ((EObject) notifier).eContainer()
                                            .equals(activity)) {
                                refresh = true;
                                break;
                            }
                            /*
                             * Same deal if ScriptInformation (e.g.
                             * explicit-grammar-selector was added ro removed.
                             * Both could signal a change in selected grammar
                             */
                            else if (notifier instanceof Activity
                                    && (notification.getEventType() == Notification.ADD && notification
                                            .getNewValue() instanceof ScriptInformation)
                                    || (notification.getEventType() == Notification.REMOVE && notification
                                            .getOldValue() instanceof ScriptInformation)) {
                                refresh = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return refresh;
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.AbstractEditableMappingSection#getRemoveAllMappingsCmdCosGrammarSwitched(org.eclipse.emf.edit.domain.EditingDomain)
     * 
     * @param ed
     * @return
     */
    @Override
    final protected Command getRemoveAllMappingsCmdCosGrammarSwitched(
            EditingDomain ed) {
        /*
         * The default implementation of this method deletes all of the mappings
         * returned by the mapping content provider.
         * 
         * We need to delete the ScriptDataMapper element instead.
         */
        AbstractScriptDataMapperEditorProvider scriptDataMapperProvider =
                getScriptDataMapperProvider();

        if (scriptDataMapperProvider != null) {
            /*
             * This method abstracted to enforce sub-classes to implement the
             * method.
             */
            return scriptDataMapperProvider.getDataMapperDeselectedCommand(ed,
                    getInput());
        }

        return null;
    }

    /**
     * Action to open the like mapping exclusions list.
     * 
     * @author aallway
     * @since 21 Jul 2015
     */
    private final class OpenLikeMappingExclusionsAction extends Action {
        private Mapping mapping;

        /**
         * @param text
         */
        private OpenLikeMappingExclusionsAction(Mapping mapping) {
            super(
                    Messages.AbstractDataMapperSection_OpenLikeMappingExclusionList);

            this.mapping = mapping;
        }

        @Override
        public void run() {
            // show like mappings exclusion list dialog box

            LikeMappingExclusionContentProvider likeMappingExclusionContentProvider =
                    new LikeMappingExclusionContentProvider(
                            getTargetInfoProvider().getContentProvider());

            LikeMappingExclusionSelectionDialogBox dialog =
                    new LikeMappingExclusionSelectionDialogBox(getSite()
                            .getShell(), getTargetInfoProvider()
                            .getLabelProvider(),
                            likeMappingExclusionContentProvider);
            dialog.setInput(getCurrentSelectedTargetObject());

            DataMapping dataMapping = (DataMapping) mapping.getMappingModel();

            List<Object> likeMappingExclusionItems =
                    getLikeMappingExclusionItems(dataMapping);

            dialog.setInitialElementSelections(likeMappingExclusionItems);

            /*
             * Expand all elements (not using container mode for dialog as we
             * don't want to select all children when parent item is selected
             */
            dialog.setExpandedElements(likeMappingExclusionContentProvider
                    .getChildren(getCurrentSelectedTargetObject()));

            ArrayList<Object> selectedItems = new ArrayList<Object>();

            if (dialog.open() == 0) {
                Object[] result = dialog.getResult();
                if (result.length > 0) {
                    for (Object object : result) {
                        selectedItems.add(object);
                    }
                }
                addLikeMappingExclusions(dataMapping, selectedItems);
            }
        }

        /**
         * @see org.eclipse.jface.action.Action#getImageDescriptor()
         * 
         * @return
         */
        @Override
        public ImageDescriptor getImageDescriptor() {
            return null;
        }
    }

    /**
     * Action to set mapping as a like mapping.
     * 
     * @author aallway
     * @since 21 Jul 2015
     */
    private final class ConfigureAsLikeMappingAction extends Action {

        private Mapping mapping;

        /**
         * @param text
         */
        private ConfigureAsLikeMappingAction(Mapping mapping) {
            super(
                    isLikeMappingSet(mapping) ? Messages.AbstractDataMapperSection_MapElementsDirectly_menu
                            : Messages.AbstractDataMapperSection_SetLikeMapping);

            this.mapping = mapping;
        }

        @Override
        public void run() {
            if (mapping.getMappingModel() instanceof DataMapping) {
                setLikeMapping((DataMapping) mapping.getMappingModel(),
                        !isLikeMappingSet(mapping));
            }
        }

        /**
         * @see org.eclipse.jface.action.Action#getImageDescriptor()
         * 
         * @return
         */
        @Override
        public ImageDescriptor getImageDescriptor() {
            return DataMapperPlugin
                    .getDefault()
                    .getImageRegistry()
                    .getDescriptor(isLikeMappingSet(mapping) ? DataMapperConstants.IMG_EXCLUDE_FROM_LIKE_MAPPING
                            : DataMapperConstants.IMG_LIKE_MAPPING);
        }
    }

    /**
     * Action to add virtual like mapping child mappings to the like mapping
     * exclusions list.
     * 
     * @author aallway
     * @since 21 Jul 2015
     */
    private final class AddToLikeMappingExclusionsAction extends Action {

        private Object likeMappingModel;

        private Object targetObjectToExclude;

        /**
         * @param likeMappingModel
         * @param targetObjectToExclude
         */
        public AddToLikeMappingExclusionsAction(Object likeMappingModel,
                Object targetObjectToExclude) {
            super(
                    Messages.AbstractDataMapperSection_AddToLikeMappingsExclusions_menu);
            this.likeMappingModel = likeMappingModel;
            this.targetObjectToExclude = targetObjectToExclude;
        }

        @Override
        public void run() {
            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.AbstractDataMapperSection_AddToLikeMappingsExclusions_menu);

            if (likeMappingModel instanceof DataMapping
                    && targetObjectToExclude != null) {
                DataMapping dataMapping = (DataMapping) likeMappingModel;

                List<Object> likeMappingExclusionItems =
                        new ArrayList<>(
                                getLikeMappingExclusionItems(dataMapping));

                if (!likeMappingExclusionItems.contains(targetObjectToExclude)) {
                    likeMappingExclusionItems.add(targetObjectToExclude);

                    Command addCmd =
                            getCommandFactory()
                                    .getSetLikeMappingExclusionsListCommand(getEditingDomain(),
                                            dataMapping,
                                            likeMappingExclusionItems,
                                            getTargetInfoProvider());
                    if (addCmd != null) {
                        cmd.append(addCmd);
                    }

                }
            }

            if (!cmd.isEmpty()) {
                getEditingDomain().getCommandStack().execute(cmd);
            }

        }

        /**
         * @see org.eclipse.jface.action.Action#getImageDescriptor()
         * 
         * @return
         */
        @Override
        public ImageDescriptor getImageDescriptor() {
            return DataMapperPlugin
                    .getDefault()
                    .getImageRegistry()
                    .getDescriptor(DataMapperConstants.IMG_EXCLUDE_FROM_LIKE_MAPPING);
        }
    }

    /**
     * Action to remove virtual like mapping child mappings from the like
     * mapping exclusions list.
     * 
     * @author aallway
     * @since 21 Jul 2015
     */
    private final class RemoveFromLikeMappingExclusionsAction extends Action {

        private Collection<DataMapping> removeFromMappingExclusions;

        private Object targetItemToRemove;

        /**
         * @param targetItemToRemove
         *            Target item to remove.
         * @param removeFromMappingExclusions
         *            DataMapping's whose exclusion lists the target appears in.
         */
        private RemoveFromLikeMappingExclusionsAction(
                Object targetItemToRemove,
                Collection<DataMapping> removeFromMappingExclusions) {
            super(Messages.AbstractDataMapperSection_RemoveFromExclusions_menu);

            this.targetItemToRemove = targetItemToRemove;
            this.removeFromMappingExclusions = removeFromMappingExclusions;
        }

        @Override
        public void run() {
            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.AbstractDataMapperSection_RemoveFromExclusions_menu);

            for (DataMapping dataMapping : removeFromMappingExclusions) {
                List<Object> likeMappingExclusionItems =
                        getLikeMappingExclusionItems(dataMapping);

                if (likeMappingExclusionItems.contains(targetItemToRemove)) {
                    likeMappingExclusionItems.remove(targetItemToRemove);

                    Command addCmd =
                            getCommandFactory()
                                    .getSetLikeMappingExclusionsListCommand(getEditingDomain(),
                                            dataMapping,
                                            likeMappingExclusionItems,
                                            getTargetInfoProvider());
                    if (addCmd != null) {
                        cmd.append(addCmd);
                    }
                }
            }

            if (!cmd.isEmpty()) {
                getEditingDomain().getCommandStack().execute(cmd);
            }

        }

        /**
         * @see org.eclipse.jface.action.Action#getImageDescriptor()
         * 
         * @return
         */
        @Override
        public ImageDescriptor getImageDescriptor() {
            return DataMapperPlugin
                    .getDefault()
                    .getImageRegistry()
                    .getDescriptor(DataMapperConstants.IMG_INCLUDE_IN_LIKE_MAPPING);
        }
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.AbstractMappingSection#getAutomapContribution()
     * 
     * @return
     */
    @Override
    protected ActionContributionItem getAutomapContribution() {
        /*
         * XPD-7878: Automap is not applicable for data mappers.
         */
        return null;
    }
}
