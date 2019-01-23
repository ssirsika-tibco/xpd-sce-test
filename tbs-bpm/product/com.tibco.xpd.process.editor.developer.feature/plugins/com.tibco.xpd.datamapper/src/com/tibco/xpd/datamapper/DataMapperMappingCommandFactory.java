/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import com.tibco.xpd.datamapper.api.AbstractDataMapperContentContributor;
import com.tibco.xpd.datamapper.api.AbstractDataMapperInfoProvider;
import com.tibco.xpd.datamapper.infoProviders.WrappedContributedContent;
import com.tibco.xpd.datamapper.internal.Messages;
import com.tibco.xpd.datamapper.scripts.AbstractScriptDataMapperEditorProvider;
import com.tibco.xpd.mapper.Mapping;
import com.tibco.xpd.processeditor.xpdl2.properties.IMoveableMappingCommandFactory;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.processeditor.xpdl2.util.ScriptInformationUtil;
import com.tibco.xpd.xpdExtension.DataMapperArrayInflation;
import com.tibco.xpd.xpdExtension.DataMapperArrayInflationType;
import com.tibco.xpd.xpdExtension.LikeMappingExclusion;
import com.tibco.xpd.xpdExtension.LikeMappingExclusions;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.XpdExtensionFactory;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.commands.LateExecuteRemoveCommand;
import com.tibco.xpd.xpdl2.util.ResetExpressionContentCommand;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * This extends IMappingCommandFactory2 for additional commands needed for the
 * Data Mapper functionality(i.e., like mapping, like mapping exclusions and
 * array inflation types). It also deals with ScriptInformation elements at this
 * level.
 * <p>
 * Sub-Classes for each scenario provide the
 * {@link AbstractScriptDataMapperEditorProvider} on construction, this provides
 * a generic access mechanism tho the xpdExt:ScriptDataMapper element whose
 * location will be script-context-specific.
 * 
 * @author Ali
 * @since 9 Feb 2015
 */
public final class DataMapperMappingCommandFactory implements
        IMoveableMappingCommandFactory {

    private AbstractScriptDataMapperEditorProvider scriptDataMapperEditorProvider;

    /**
     * provide the {@link AbstractScriptDataMapperEditorProvider} on
     * construction, this provides a generic access mechanism tho the
     * xpdExt:ScriptDataMapper element whose location will be
     * script-context-specific.
     * 
     * @param scriptDataMapperEditorProvider
     */
    public DataMapperMappingCommandFactory(
            AbstractScriptDataMapperEditorProvider scriptDataMapperEditorProvider) {
        super();
        this.scriptDataMapperEditorProvider = scriptDataMapperEditorProvider;
    }

    /**
     * Added to actual, when the source is a script
     */
    private static final String SCRIPT_MAPPING_TEXT = "__SCRIPT__"; //$NON-NLS-1$

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory2#getAddMappingCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, java.lang.Object, java.lang.Object)
     * 
     * @param ed
     * @param owner
     * @param source
     * @param target
     * @return
     */
    @Override
    public final Command getAddMappingCommand(EditingDomain ed, EObject owner,
            Object source, Object target) {

        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.AbstractDataMapperMappingCommandFactory_AddMappingCommand);
        if ((source instanceof WrappedContributedContent || source instanceof ScriptInformation)
                && target instanceof WrappedContributedContent) {

            Object sourceObject;
            WrappedContributedContent srcWrappedItem = null;
            if (source instanceof ScriptInformation) {
                sourceObject = source;
            } else {
                srcWrappedItem = ((WrappedContributedContent) source);
                sourceObject = srcWrappedItem.getContributedObject();
            }

            WrappedContributedContent targetWrappedItem =
                    ((WrappedContributedContent) target);

            Object targetObject = targetWrappedItem.getContributedObject();

            // Get or create the scriptDataMapper (the data mappings container).
            ScriptDataMapper scriptDataMapper =
                    scriptDataMapperEditorProvider
                            .getOrCreateScriptDataMapper(owner, ed, cmd);

            String targetObjectPath =
                    targetWrappedItem.getContributor().getInfoProvider()
                            .getObjectPath(targetObject);
            // create mapping
            DataMapping xpdlMapping =
                    Xpdl2Factory.eINSTANCE.createDataMapping();
            cmd.append(AddCommand.create(ed,
                    scriptDataMapper,
                    XpdExtensionPackage.eINSTANCE
                            .getScriptDataMapper_DataMappings(),
                    xpdlMapping));

            /*
             * If source is script then we add scriptInfo element to the mapping
             * and set __SCRIPT__ in actual, otherwise we set the actual value
             * as the object path from contributor
             */

            if (sourceObject instanceof ScriptInformation) {
                setSourceScriptModel(ed,
                        owner,
                        scriptDataMapper,
                        xpdlMapping,
                        (ScriptInformation) sourceObject,
                        cmd);
                /*
                 * set actual to '_SCRIPT_' as its a requirement to have this
                 * element (though we never use this in Data Mapper)
                 */
                Expression actualExpression =
                        Xpdl2ModelUtil.createExpression(SCRIPT_MAPPING_TEXT);
                actualExpression
                        .setScriptGrammar(ScriptGrammarFactory.JAVASCRIPT);
                cmd.append(SetCommand.create(ed,
                        xpdlMapping,
                        Xpdl2Package.eINSTANCE.getDataMapping_Actual(),
                        actualExpression));

            } else {

                if (srcWrappedItem != null) {
                    String srcObjectPath =
                            srcWrappedItem.getContributor().getInfoProvider()
                                    .getObjectPath(sourceObject);
                    if (srcObjectPath != null) {
                        Expression expression =
                                Xpdl2ModelUtil.createExpression(srcObjectPath);
                        expression
                                .setScriptGrammar(ScriptGrammarFactory.JAVASCRIPT);
                        cmd.append(SetCommand.create(ed,
                                xpdlMapping,
                                Xpdl2Package.eINSTANCE.getDataMapping_Actual(),
                                expression));
                    }
                }
            }

            /*
             * target is always stored in formal and it will be the object path
             * string provided by the contributor
             */

            if (targetObjectPath != null) {
                cmd.append(SetCommand.create(ed,
                        xpdlMapping,
                        Xpdl2Package.eINSTANCE.getDataMapping_Formal(),
                        targetObjectPath));
            }

            /*
             * Data Mapper sets mapping direction as IN, override
             * setMappingDirection() for scenario specific mapping direction
             */
            setMappingDirection(ed, cmd, xpdlMapping);

            /*
             * add source contributor ID attribute to the mapping, if its not
             * scriptInfo
             */
            if (srcWrappedItem != null) {
                cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                        xpdlMapping,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_SourceContributorId(),
                        srcWrappedItem.getContributor().getContributorId()));
            }
            /* add target contributor ID attributed to the mapping */
            cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                    xpdlMapping,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_TargetContributorId(),
                    targetWrappedItem.getContributor().getContributorId()));

        }
        return cmd;
    }

    /**
     * Sub-classes can override this to set different mapping direction if
     * needed for a particular scenario
     * 
     * @param ed
     * @param cmd
     * @param xpdlMapping
     */
    protected void setMappingDirection(EditingDomain ed, CompoundCommand cmd,
            DataMapping xpdlMapping) {
        cmd.append(SetCommand.create(ed,
                xpdlMapping,
                Xpdl2Package.eINSTANCE.getDataMapping_Direction(),
                DirectionType.IN_LITERAL));
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.IMappingCommandFactory2#getRemoveMappingCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, com.tibco.xpd.mapper.Mapping)
     * 
     * @param ed
     * @param owner
     * @param mapping
     * @return
     */
    @Override
    public Command getRemoveMappingCommand(EditingDomain ed, EObject owner,
            Mapping mapping) {
        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.AbstractDataMapperMappingCommandFactory_RemoveMappingCommand);

        // Get the scriptDataMapper (the data mappings container).
        ScriptDataMapper scriptDataMapper =
                scriptDataMapperEditorProvider
                        .getOrCreateScriptDataMapper(owner, ed, cmd);

        if (mapping != null && scriptDataMapper != null) {

            /*
             * if source is a script then we need to move the script to unmapped
             * scripts inside the ScriptDataMapper before deleting the mapping
             */
            if (mapping.getSource() instanceof ScriptInformation) {
                ScriptInformation information =
                        (ScriptInformation) mapping.getSource();
                /*
                 * XPD-7874: Create a copy of the 'ScriptInformation' else EMF
                 * will remove the ScriptInformation from the DataMapping and
                 * add it to the Unmapped Scripts.
                 */
                cmd.append(AddCommand.create(ed,
                        scriptDataMapper,
                        XpdExtensionPackage.eINSTANCE
                                .getScriptDataMapper_UnmappedScripts(),
                        EcoreUtil.copy(information)));

            }
            /*
             * XPD-7874: Using 'LateExecuteRemoveCommand' as that will handle
             * situations when we remove multiple objects from the same parent.
             */
            cmd.append(LateExecuteRemoveCommand.create(ed,
                    mapping.getMappingModel()));

        }
        return cmd;
    }

    /**
     * Provides command to set 'like mapping' attribute with the value of the
     * given isLikeMapping param for the given dataMapping
     * 
     * @param ed
     * @param owner
     * @param dataMapping
     * @param isLikeMapping
     * @return Command
     */
    final public Command getSetLikeMappingCommand(EditingDomain ed,
            EObject owner, DataMapping dataMapping, boolean isLikeMapping) {
        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.AbstractDataMapperMappingCommandFactory_SetLikeMapping);
        if (dataMapping != null) {
            // when un-setting like mapping, remove like mapping exclusion
            // elements
            if (!isLikeMapping) {
                cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(ed,
                        dataMapping,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_LikeMappingExclusions(),
                        null));
            }
            cmd.append(Xpdl2ModelUtil
                    .getSetOtherAttributeCommand(ed,
                            dataMapping,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_LikeMapping(),
                            isLikeMapping));
        }
        return cmd;
    }

    /**
     * 
     * @param ed
     * @param owner
     * @param target
     * @param targetInfoProvider
     * 
     * @return command to set default array inflation type for the given data
     *         mapping's target element (i.e., remove array inflation element if
     *         its set)
     */
    final public Command getSetDefaultArrayInflationType(EditingDomain ed,
            EObject owner, Object target,
            AbstractDataMapperInfoProvider targetInfoProvider) {
        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.AbstractDataMapperMappingCommandFactory_SetArrayInflationType);

        ScriptDataMapper scriptDataMapper =
                scriptDataMapperEditorProvider
                        .getOrCreateScriptDataMapper(owner, ed, cmd);

        if (scriptDataMapper != null) {
            String targetItemPath = targetInfoProvider.getObjectPath(target);

            // get array inflation type element and delete if it exists
            DataMapperArrayInflation arryInflationElement =
                    getArrayInflationTypeElement(scriptDataMapper,
                            targetItemPath);

            if (arryInflationElement != null) {

                cmd.append(DeleteCommand.create(ed, arryInflationElement));
            }
        }
        return cmd;
    }

    /**
     * Provides command to set the given array inflation type for the given data
     * mapping's target element
     * 
     * @param ed
     * @param owner
     * @param target
     * @param inflationType
     * @return
     */
    final public Command getSetArrayInflationType(EditingDomain ed,
            EObject owner, Object target,
            AbstractDataMapperInfoProvider targetInfoProvider,
            DataMapperArrayInflationType inflationType) {

        String targetItemPath = targetInfoProvider.getObjectPath(target);

        if (targetItemPath != null && targetItemPath.length() > 0) {

            CompoundCommand cmd =
                    new CompoundCommand(
                            Messages.AbstractDataMapperMappingCommandFactory_SetArrayInflationType);

            ScriptDataMapper scriptDataMapper =
                    scriptDataMapperEditorProvider
                            .getOrCreateScriptDataMapper(owner, ed, cmd);

            if (scriptDataMapper != null) {

                /*
                 * get array inflation type element or create one if doesn't
                 * exist already
                 */
                DataMapperArrayInflation arryInflationElement =
                        getArrayInflationTypeElement(scriptDataMapper,
                                targetItemPath);

                if (arryInflationElement == null) {
                    arryInflationElement =
                            XpdExtensionFactory.eINSTANCE
                                    .createDataMapperArrayInflation();
                    arryInflationElement.setPath(targetItemPath);
                    arryInflationElement.setMappingType(inflationType);
                    String contributorId = null;
                    if (target instanceof WrappedContributedContent) {
                        AbstractDataMapperContentContributor contributor =
                                ((WrappedContributedContent) target)
                                        .getContributor();
                        if (contributor != null) {
                            contributorId = contributor.getContributorId();
                        }
                    }
                    arryInflationElement.setContributorId(contributorId);
                    cmd.append(AddCommand.create(ed,
                            scriptDataMapper,
                            XpdExtensionPackage.eINSTANCE
                                    .getScriptDataMapper_ArrayInflationType(),
                            arryInflationElement));

                } else {
                    /*
                     * array inflation type element for the target item exists
                     * already, so set its inflation type if its different
                     */
                    if (!inflationType.equals(arryInflationElement
                            .getMappingType())) {
                        cmd.append(SetCommand
                                .create(ed,
                                        arryInflationElement,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDataMapperArrayInflation_MappingType(),
                                        inflationType));
                    }
                }
            }
            return cmd;
        }

        return null;
    }

    /**
     * 
     * Provides command to set the given items as the like mappings exclusion
     * list of the given dataMapping
     * 
     * @param ed
     * @param owner
     * @param dataMapping
     * @param targetItems
     * @param targetInfoProvider
     * @return
     */
    final public Command getSetLikeMappingExclusionsListCommand(
            EditingDomain ed, DataMapping dataMapping,
            List<Object> targetItems,
            AbstractDataMapperInfoProvider targetInfoProvider) {

        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.AbstractDataMapperMappingCommandFactory_LikeMappingExclusion);

        LikeMappingExclusions likeMappingExclusions = null;

        List<LikeMappingExclusion> likeMappingExclusionsList =
                new ArrayList<>();

        if (dataMapping != null && targetItems != null) {

            likeMappingExclusions =
                    XpdExtensionFactory.eINSTANCE.createLikeMappingExclusions();

            for (Object item : targetItems) {
                String targetItemPath = targetInfoProvider.getObjectPath(item);

                if (targetItemPath != null && targetItemPath.length() > 0) {
                    LikeMappingExclusion exclusion =
                            XpdExtensionFactory.eINSTANCE
                                    .createLikeMappingExclusion();
                    exclusion.setPath(targetItemPath);

                    likeMappingExclusionsList.add(exclusion);
                    likeMappingExclusions.getExclusions().add(exclusion);
                }
            }
            // delete existing ones
            cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(ed,
                    dataMapping,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_LikeMappingExclusions(),
                    null));

        }

        // add new one based on the given items, if any
        if (likeMappingExclusions != null
                && !likeMappingExclusions.getExclusions().isEmpty()) {

            cmd.append(Xpdl2ModelUtil.getSetOtherElementCommand(ed,
                    dataMapping,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_LikeMappingExclusions(),
                    likeMappingExclusions));
        }
        return cmd;
    }

    /**
     * Returns the dataMapperArrayInflation element if the given
     * ScripTdataMapper element has an element with same path as the target
     * item's path
     * 
     * @param scriptDataMapper
     * @param targetItem
     * @return
     */
    private DataMapperArrayInflation getArrayInflationTypeElement(
            ScriptDataMapper scriptDataMapper, String targetItemPath) {
        if (targetItemPath != null) {
            EList<DataMapperArrayInflation> arrayInflationTypeList =
                    scriptDataMapper.getArrayInflationType();
            for (DataMapperArrayInflation dataMapperArrayInflation : arrayInflationTypeList) {
                if (dataMapperArrayInflation.getPath().equals(targetItemPath)) {
                    return dataMapperArrayInflation;
                }
            }
        }
        return null;
    }

    /**
     * 
     * @param ed
     * @param input
     * @param scriptDataMapper
     * @param xpdlMapping
     * @param scriptInfo
     * @param cmd
     */
    protected void setSourceScriptModel(EditingDomain ed, EObject input,
            ScriptDataMapper scriptDataMapper, DataMapping xpdlMapping,
            ScriptInformation scriptInfo, CompoundCommand cmd) {
        if (scriptInfo != null) {

            EObject informationContainer = scriptInfo.eContainer();

            boolean isScriptCopy = false;
            if (informationContainer instanceof DataMapping) {
                /*
                 * User as dragged a script that is already mapped to a target,
                 * create a copy. for the mapping.
                 */
                isScriptCopy = true;

                scriptInfo = copyMappingScript(scriptInfo, scriptDataMapper);
                cmd.append(Xpdl2ModelUtil.getAddOtherElementCommand(ed,
                        xpdlMapping,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script(),
                        scriptInfo));
            }

            if (informationContainer == null) {
                /*
                 * Brand new script or copy of existing..
                 */
                if (!isScriptCopy) {
                    /*
                     * If it's brand new then make sure we set the initial
                     * values up.
                     */
                    scriptInfo.setName(ScriptInformationUtil
                            .getNextScriptName(scriptDataMapper));

                    Expression expression = Xpdl2ModelUtil.createExpression(""); //$NON-NLS-1$                    
                    expression
                            .setScriptGrammar(ScriptGrammarFactory.JAVASCRIPT);
                    scriptInfo.setExpression(expression);
                }
                cmd.append(Xpdl2ModelUtil.getAddOtherElementCommand(ed,
                        xpdlMapping,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script(),
                        scriptInfo));

            } else if (informationContainer instanceof ScriptDataMapper) {
                /*
                 * Add existing unmapped script to data mapping added above.
                 */
                ScriptInformation newInformation = EcoreUtil.copy(scriptInfo);

                cmd.append(Xpdl2ModelUtil.getAddOtherElementCommand(ed,
                        xpdlMapping,
                        XpdExtensionPackage.eINSTANCE.getDocumentRoot_Script(),
                        newInformation));
                // And remove it from unmapped scripts
                cmd.append(RemoveCommand.create(ed,
                        scriptDataMapper,
                        XpdExtensionPackage.eINSTANCE
                                .getScriptDataMapper_UnmappedScripts(),
                        scriptInfo));
            }
        }
    }

    /**
     * @param script
     * @param scriptDataMapper
     * @return a copy of the given script, uniquely named.
     */
    private ScriptInformation copyMappingScript(ScriptInformation script,
            ScriptDataMapper scriptDataMapper) {
        ScriptInformation newScript = EcoreUtil.copy(script);

        int i = 0;
        String name;
        do {
            i++;
            name =
                    String.format(Messages.AbstractDataMapperMappingCommandFactory_CopyOfScript,
                            i,
                            newScript.getName());

        } while (scriptNameExists(scriptDataMapper, name));

        newScript.setName(name);
        return newScript;
    }

    /**
     * 
     * @param scriptDataMapper
     * @param name
     * @return
     */
    private boolean scriptNameExists(ScriptDataMapper scriptDataMapper,
            String name) {
        List<ScriptInformation> existingScripts =
                getExistingScripts(scriptDataMapper);

        for (ScriptInformation scriptInformation : existingScripts) {
            if (name.equals(scriptInformation.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param scriptDataMapper
     * @return
     */
    private List<ScriptInformation> getExistingScripts(
            ScriptDataMapper scriptDataMapper) {
        List<ScriptInformation> scripts = new ArrayList<>();

        if (scriptDataMapper != null) {
            // get scripts that aren't mapped yet

            scripts.addAll(scriptDataMapper.getUnmappedScripts());

            // get mapped scripts
            List<DataMapping> mappings = scriptDataMapper.getDataMappings();
            for (DataMapping mapping : mappings) {
                Object other =
                        Xpdl2ModelUtil.getOtherElement(mapping,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_Script());
                if (other instanceof ScriptInformation) {
                    ScriptInformation information = (ScriptInformation) other;
                    scripts.add(information);
                }
            }
        }
        return scripts;
    }

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.IMoveableMappingCommandFactory#getMoveMappingCommand(org.eclipse.emf.edit.domain.EditingDomain,
     *      org.eclipse.emf.ecore.EObject, com.tibco.xpd.mapper.Mapping,
     *      com.tibco.xpd.mapper.Mapping)
     * 
     * @param ed
     * @param owner
     * @param beforeMove
     * @param afterMove
     * @return
     */
    @Override
    public Command getMoveMappingCommand(EditingDomain ed, EObject owner,
            Mapping beforeMove, Mapping afterMove) {

        /* Check what we're moving to is what we expect. */
        if ((!(afterMove.getSource() instanceof WrappedContributedContent) && !(afterMove
                .getSource() instanceof ScriptInformation))
                || !(afterMove.getTarget() instanceof WrappedContributedContent)
                || !(beforeMove.getMappingModel() instanceof DataMapping)) {
            return null;
        }

        CompoundCommand cmd =
                new CompoundCommand(
                        Messages.DataMapperMappingCommandFactory_MoveMapping_menu);

        DataMapping xpdlMapping = (DataMapping) beforeMove.getMappingModel();

        /*
         * Handle move of target.
         */
        WrappedContributedContent newTargetWrappedItem =
                (WrappedContributedContent) afterMove.getTarget();

        if (!newTargetWrappedItem.equals(beforeMove.getTarget())) {
            /*
             * For data mapper the target is always specified in the
             * xpdl2:DataMapping/Formal attribute
             * 
             * We also need to change the target contributor id.
             */
            Object newTargetObject =
                    newTargetWrappedItem.getContributedObject();

            String targetObjectPath =
                    newTargetWrappedItem.getContributor().getInfoProvider()
                            .getObjectPath(newTargetObject);

            if (targetObjectPath != null) {
                cmd.append(SetCommand.create(ed,
                        xpdlMapping,
                        Xpdl2Package.eINSTANCE.getDataMapping_Formal(),
                        targetObjectPath));

                cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                        xpdlMapping,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_TargetContributorId(),
                        newTargetWrappedItem.getContributor()
                                .getContributorId()));
            }
        }

        /*
         * Move source if necessary.
         * 
         * [Sid: For now we will not deal with moving from/to mapping script,
         * it's all quite messy and complicated and until someone really wants
         * it probably nopt worthwhile.]
         */
        if (!(beforeMove.getSource() instanceof ScriptInformation)
                && (afterMove.getSource() instanceof WrappedContributedContent)) {
            WrappedContributedContent newSourceWrappedItem =
                    (WrappedContributedContent) afterMove.getSource();

            if (!newSourceWrappedItem.equals(beforeMove.getSource())) {
                /*
                 * For Data Mapper the source is always specified in the
                 * xpdl2:DataMapping/Actual.
                 * 
                 * We also need to set the source contributor id
                 */
                Object newSourceObject =
                        newSourceWrappedItem.getContributedObject();

                String sourceObjectPath =
                        newSourceWrappedItem.getContributor().getInfoProvider()
                                .getObjectPath(newSourceObject);

                if (sourceObjectPath != null) {
                    Expression actual = xpdlMapping.getActual();

                    cmd.append(new ResetExpressionContentCommand(
                            (TransactionalEditingDomain) ed, "", actual, //$NON-NLS-1$
                            sourceObjectPath));

                    cmd.append(Xpdl2ModelUtil.getSetOtherAttributeCommand(ed,
                            xpdlMapping,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_SourceContributorId(),
                            newSourceWrappedItem.getContributor()
                                    .getContributorId()));
                }
            }
        }

        /*
         * Don't return command if we did not create anything.
         */
        if (!cmd.isEmpty()) {
            return cmd;
        }

        return null;
    }
}
