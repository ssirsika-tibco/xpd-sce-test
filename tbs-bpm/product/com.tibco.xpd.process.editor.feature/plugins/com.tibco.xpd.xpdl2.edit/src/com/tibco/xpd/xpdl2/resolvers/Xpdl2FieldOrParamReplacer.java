/**
 * Xpdl2DataFieldReplacer.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.xpdl2.resolvers;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.xpdExtension.AssociatedCorrelationField;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationFields;
import com.tibco.xpd.xpdExtension.AssociatedParameter;
import com.tibco.xpd.xpdExtension.AssociatedParametersContainer;
import com.tibco.xpd.xpdExtension.CatchErrorMappings;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskUser;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.LocalPackageProcessInterfaceUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * <b>Xpdl2FieldOrParamReplacer</b>
 * <p>
 * The data field replacer provides the ability to check for data field
 * references within given model objects and replace those references with
 * references to a new field. (for instance during a data field rename).
 * </p>
 * <p>
 * <b>NOTE: The old item may be a Data Field whereas the new item may be a
 * FOrmal Parameter or visa-versa</b>
 * </p>
 * <p>
 * In some circumstances there may be nothing to do. For instance if a data
 * field is referenced by name and only the ID is different then there is
 * nothing to replace. If the reference is by ID and the name has changed bu the
 * ID is the same, then there will be nothing to do.
 * </p>
 * 
 * <p>
 * This allows complex operations on the model (such as refactoring) to discover
 * data field references so that they can perform appropriate actions (such as
 * moving/copying data fields that are used by objects).
 * </p>
 * <p>
 * Other plug-ins can extend this ability via extension points and in this way,
 * plug-in specific referencing of data fields are handled by the plug-ins
 * themselves.
 * </p>
 * 
 */
public class Xpdl2FieldOrParamReplacer {

    Map<String, String> idOrNameMap = null;

    boolean mapIsIdMap = true;

    /**
     * Create a field replacer for the given map of data fields
     * 
     * @param idOrNameMap
     *            key = old field id OR name, value = new field id OR name
     * @param mapIsIdMap
     *            If true the 2 strings in map are old and new id. If false then
     *            they are old and new name.
     * 
     */
    public Xpdl2FieldOrParamReplacer(Map<String, String> idOrNameMap,
            boolean mapIsIdMap) {
        this.idOrNameMap = idOrNameMap;
        this.mapIsIdMap = mapIsIdMap;

    }

    /**
     * Gets command to replace miscellaneous references within the given given
     * object but not in normal containers like activity and transition.
     * <p>
     * < Replacement for things like activity/transitions should be contributed
     * via {@link IFieldResolverExtension}'s.
     * <p>
     * Replacements for references elsewhere (such as directly in process etc)
     * are done using {@link IDataReferenceResolver} contributions and this
     * method deals with these.
     * <p>
     * Replacing these references can generally be handled separately and just
     * in standard rename because otherwise we only rename for things like copy
     * paste individual activities and their references and inline sub-processes
     * and so on. When we copy a whole process etc, it will always carry all of
     * it's content so there is no data renaming etc.
     * 
     * @param scopeObject
     *            Scope object (Package/Process/Activity in which to perform
     *            changes). Note that in the circumstance of process-interface
     *            data then the scope object may be the implementing process (so
     *            not necessarily the direct data container
     * 
     * @param editingDomain
     * @param scopeObject
     * 
     * @return EMF command to replace references or null if nothing to do.
     */
    public Command getReplaceMiscellaneousRefsCommand(
            EditingDomain editingDomain, EObject scopeObject) {

        List<IDataReferenceResolver> dataReferenceResolvers =
                ResolverExtPointHelper.INSTANCE.getDataReferenceResolvers();

        CompoundCommand cmd = new CompoundCommand();

        for (IDataReferenceResolver dataReferenceResolver : dataReferenceResolvers) {
            Set<Entry<String, String>> entrySet = idOrNameMap.entrySet();

            for (Entry<String, String> entry : entrySet) {
                Command c = null;

                if (!mapIsIdMap) {
                    c =
                            dataReferenceResolver
                                    .getSwapDataNameReferencesCommand(editingDomain,
                                            scopeObject,
                                            entry.getKey(),
                                            entry.getValue());
                } else {
                    throw new RuntimeException(
                            "Id replacement not available for IDataReferenceRsolver contributions"); //$NON-NLS-1$
                }

                if (c != null) {
                    cmd.append(c);
                }
            }
        }

        if (!cmd.isEmpty()) {
            return cmd;
        }

        return null;
    }

    /**
     * Build and return a command to replace any references to fields/params
     * (according to map we were constructed with.
     * 
     * @param editingDomain
     * @param referencer
     * @return EMF command to replace references or null if nothing to do.
     */
    public Command getReplaceFieldReferencesCommand(
            EditingDomain editingDomain, EObject referencer) {
        Command cmd = null;
        if (referencer instanceof Activity) {
            cmd =
                    replaceReferencesInActivityCommand(editingDomain,
                            (Activity) referencer);
        } else if (referencer instanceof Transition) {
            cmd =
                    replaceReferencesInTransitionCommand(editingDomain,
                            (Transition) referencer);
        } else if (referencer instanceof AssociatedParametersContainer) {
            cmd =
                    replaceReferencesInInterfaceObjectCommand(editingDomain,
                            (AssociatedParametersContainer) referencer);
        } else if (referencer instanceof ProcessRelevantData) {
            cmd =
                    replaceReferencesInDataCommand(editingDomain,
                            (ProcessRelevantData) referencer);

        }

        return cmd;
    }

    /**
     * Return EMF command to replace any references to data fields in the given
     * activity
     * 
     * @param editingDomain
     * @param activity
     * @return EMF command to replace references or null if nothing to do.
     * @deprecated Use getReplaceFieldReferencesCommand(EditingDomain
     *             editingDomain, EObject referencer) instead - this will make
     *             it easier as you will not have to distinguish between
     *             different possible referencers.
     */
    @Deprecated
    public Command getReplaceFieldReferencesCommand(
            EditingDomain editingDomain, Activity activity) {
        return replaceReferencesInActivityCommand(editingDomain, activity);
    }

    /**
     * @param editingDomain
     * @param activity
     * @return EMF command to replace references or null if nothing to do.
     */
    private Command replaceReferencesInActivityCommand(
            EditingDomain editingDomain, Activity activity) {
        CompoundCommand resultCmd = null;

        if (!mapIsIdMap) {
            CompoundCommand replaceCmd = new CompoundCommand();

            if (replaceStandardActivityNameReferences(activity,
                    editingDomain,
                    replaceCmd)) {
                if (resultCmd == null) {
                    resultCmd = new CompoundCommand();
                }
                resultCmd.append(replaceCmd);
            }
        } else {
            CompoundCommand replaceCmd = new CompoundCommand();

            if (replaceStandardActivityIDReferences(activity,
                    editingDomain,
                    replaceCmd)) {
                if (resultCmd == null) {
                    resultCmd = new CompoundCommand();
                }
                resultCmd.append(replaceCmd);
            }

        }

        // Pass the activity to all our data field resolver extenders.
        Collection<IFieldResolverExtension> extResolvers =
                ResolverExtPointHelper.INSTANCE.getExtensions();

        for (IFieldResolverExtension resolver : extResolvers) {
            Command extCmd = null;

            if (mapIsIdMap) {
                extCmd =
                        resolver.getSwapActivityDataIdReferencesCommand(editingDomain,
                                activity,
                                idOrNameMap);
            } else {
                extCmd =
                        resolver.getSwapActivityDataNameReferencesCommand(editingDomain,
                                activity,
                                idOrNameMap);
            }

            if (extCmd != null) {
                if (resultCmd == null) {
                    resultCmd = new CompoundCommand();
                }
                resultCmd.append(extCmd);
            }
        }

        return resultCmd;
    }

    /**
     * Return EMF command to replace any references to data fields in the given
     * process relevant data
     * 
     * @param editingDomain
     * @param cmd
     *            Add EMF modification commands to this command.
     * @param processRelevantData
     * @return EMF command to replace references or null if nothing to do.
     * @deprecated Use getReplaceFieldReferencesCommand(EditingDomain
     *             editingDomain, EObject referencer) instead - this will make
     *             it easier as you will not have to distinguish between
     *             different possible referencers.
     */
    @Deprecated
    public Command getReplaceFieldReferencesCommand(
            EditingDomain editingDomain, ProcessRelevantData processRelevantData) {
        return replaceReferencesInDataCommand(editingDomain,
                processRelevantData);

    }

    /**
     * @param editingDomain
     * @param processRelevantData
     * @return EMF command to replace references or null if nothing to do.
     */
    private Command replaceReferencesInDataCommand(EditingDomain editingDomain,
            ProcessRelevantData processRelevantData) {
        CompoundCommand resultCmd = null;

        DataType dataType = processRelevantData.getDataType();
        if (dataType instanceof BasicType) {
            BasicType basicType = (BasicType) dataType;
            if (basicType.getType().equals(BasicTypeType.PERFORMER_LITERAL)) {

                Collection<IFieldResolverExtension> extResolvers =
                        ResolverExtPointHelper.INSTANCE.getExtensions();

                for (IFieldResolverExtension resolver : extResolvers) {
                    Command extCmd = null;
                    if (resolver instanceof IFieldResolverExtension2) {
                        IFieldResolverExtension2 resolver2 =
                                (IFieldResolverExtension2) resolver;

                        if (mapIsIdMap) {
                            extCmd =
                                    resolver2
                                            .getSwapProcessRelevantDataIdReferencesCommand(editingDomain,
                                                    processRelevantData,
                                                    idOrNameMap);
                        } else {
                            extCmd =
                                    resolver2
                                            .getSwapProcessRelevantDataNameReferencesCommand(editingDomain,
                                                    processRelevantData,
                                                    idOrNameMap);
                        }
                        if (extCmd != null) {
                            if (resultCmd == null) {
                                resultCmd = new CompoundCommand();
                            }
                            resultCmd.append(extCmd);
                        }
                    }
                }
            }
        }
        return resultCmd;
    }

    /**
     * Return EMF command to replace any references to data fields in the given
     * transition
     * 
     * @param editingDomain
     * @param cmd
     *            Add EMF modification commands to this command.
     * @param transition
     * @return EMF command to replace references or null if nothing to do.
     * @deprecated Use getReplaceFieldReferencesCommand(EditingDomain
     *             editingDomain, EObject referencer) instead - this will make
     *             it easier as you will not have to distinguish between
     *             different possible referencers.
     */
    @Deprecated
    public Command getReplaceFieldReferencesCommand(
            EditingDomain editingDomain, Transition transition) {
        return replaceReferencesInTransitionCommand(editingDomain, transition);
    }

    /**
     * @param editingDomain
     * @param transition
     * @return EMF command to replace references or null if nothing to do.
     */
    private Command replaceReferencesInTransitionCommand(
            EditingDomain editingDomain, Transition transition) {
        CompoundCommand resultCmd = null;

        // Pass the transition to all our data field resolver extenders.
        Collection<IFieldResolverExtension> extResolvers =
                ResolverExtPointHelper.INSTANCE.getExtensions();

        for (IFieldResolverExtension resolver : extResolvers) {
            Command extCmd = null;

            if (mapIsIdMap) {
                extCmd =
                        resolver.getSwapTransitionDataIdReferencesCommand(editingDomain,
                                transition,
                                idOrNameMap);
            } else {
                extCmd =
                        resolver.getSwapTransitionDataNameReferencesCommand(editingDomain,
                                transition,
                                idOrNameMap);
            }

            if (extCmd != null) {
                if (resultCmd == null) {
                    resultCmd = new CompoundCommand();
                }
                resultCmd.append(extCmd);
            }
        }

        return resultCmd;
    }

    /**
     * Return EMF command to replace any references to data fields in the given
     * process interface start or intermediate method
     * 
     * @param editingDomain
     * @param assocParamsContainer
     * @return EMF command to replace references or null if nothing to do.
     * @deprecated Use getReplaceFieldReferencesCommand(EditingDomain
     *             editingDomain, EObject referencer) instead - this will make
     *             it easier as you will not have to distinguish between
     *             different possible referencers.
     */
    @Deprecated
    public Command getReplaceFieldReferencesCommand(
            EditingDomain editingDomain,
            AssociatedParametersContainer assocParamsContainer) {
        return replaceReferencesInInterfaceObjectCommand(editingDomain,
                assocParamsContainer);
    }

    /**
     * @param editingDomain
     * @param assocParamsContainer
     * @return EMF command to replace references or null if nothing to do.
     */
    private Command replaceReferencesInInterfaceObjectCommand(
            EditingDomain editingDomain,
            AssociatedParametersContainer assocParamsContainer) {
        CompoundCommand resultCmd = null;

        if (!mapIsIdMap) {
            EList assocParams = assocParamsContainer.getAssociatedParameters();

            for (Iterator iterator = assocParams.iterator(); iterator.hasNext();) {
                AssociatedParameter param =
                        (AssociatedParameter) iterator.next();

                String newName = idOrNameMap.get(param.getFormalParam());
                if (newName != null) {
                    // We have a new name to replace this reference with.
                    if (resultCmd == null) {
                        resultCmd = new CompoundCommand();
                        resultCmd.append(SetCommand.create(editingDomain,
                                param,
                                XpdExtensionPackage.eINSTANCE
                                        .getAssociatedParameter_FormalParam(),
                                newName));
                    }
                }
            }
        }

        return resultCmd;
    }

    /**
     * Replace any references to data fields in the given activity
     * 
     * @param editingDomain
     * @param cmd
     *            Add EMF modification commands to this command.
     * @param activity
     * @return True if replacements made.
     */
    public boolean replaceFieldReferences(EditingDomain editingDomain,
            Activity activity) {
        boolean replacementsMade = false;

        //
        // Do standard replacements that we know about.
        if (!mapIsIdMap) {
            if (replaceStandardActivityNameReferences(activity,
                    editingDomain,
                    null)) {
                replacementsMade = true;
            }
        } else {
            if (replaceStandardActivityIDReferences(activity,
                    editingDomain,
                    null)) {
                replacementsMade = true;
            }
        }

        // Pass the activity to all our data field resolver extenders.
        Collection<IFieldResolverExtension> extResolvers =
                ResolverExtPointHelper.INSTANCE.getExtensions();

        for (IFieldResolverExtension resolver : extResolvers) {
            Command extCmd = null;

            if (mapIsIdMap) {
                extCmd =
                        resolver.getSwapActivityDataIdReferencesCommand(editingDomain,
                                activity,
                                idOrNameMap);
            } else {
                extCmd =
                        resolver.getSwapActivityDataNameReferencesCommand(editingDomain,
                                activity,
                                idOrNameMap);
            }

            if (extCmd != null && extCmd.canExecute()) {
                extCmd.execute();
                replacementsMade = true;
            }
        }

        return replacementsMade;
    }

    /**
     * Replace any references to data fields in the given processRelevantData
     * 
     * @param editingDomain
     * @param cmd
     *            Add EMF modification commands to this command.
     * @param processRelevantData
     * @return True if replacements made.
     */
    public boolean replaceFieldReferences(EditingDomain editingDomain,
            ProcessRelevantData processRelevantData) {
        boolean replacementsMade = false;
        Collection<IFieldResolverExtension> extResolvers =
                ResolverExtPointHelper.INSTANCE.getExtensions();

        for (IFieldResolverExtension resolver : extResolvers) {
            Command extCmd = null;
            if (resolver instanceof IFieldResolverExtension2) {
                IFieldResolverExtension2 resolver2 =
                        (IFieldResolverExtension2) resolver;
                if (mapIsIdMap) {
                    extCmd =
                            resolver2
                                    .getSwapProcessRelevantDataIdReferencesCommand(editingDomain,
                                            processRelevantData,
                                            idOrNameMap);
                } else {
                    extCmd =
                            resolver2
                                    .getSwapProcessRelevantDataNameReferencesCommand(editingDomain,
                                            processRelevantData,
                                            idOrNameMap);
                }
                if (extCmd != null && extCmd.canExecute()) {
                    extCmd.execute();
                    replacementsMade = true;
                }
            }
        }
        return replacementsMade;
    }

    /**
     * Replace any references to data fields in the given transition
     * 
     * @param editingDomain
     * @param cmd
     *            Add EMF modification commands to this command.
     * @param transition
     * @return True if replacements made.
     */
    public boolean replaceFieldReferences(EditingDomain editingDomain,
            Transition transition) {
        boolean replacementsMade = false;

        // Pass the activity to all our data field resolver extenders.
        Collection<IFieldResolverExtension> extResolvers =
                ResolverExtPointHelper.INSTANCE.getExtensions();

        for (IFieldResolverExtension resolver : extResolvers) {
            Command extCmd = null;

            if (mapIsIdMap) {
                extCmd =
                        resolver.getSwapTransitionDataIdReferencesCommand(editingDomain,
                                transition,
                                idOrNameMap);
            } else {
                extCmd =
                        resolver.getSwapTransitionDataNameReferencesCommand(editingDomain,
                                transition,
                                idOrNameMap);
            }

            if (extCmd != null && extCmd.canExecute()) {
                extCmd.execute();
                replacementsMade = true;
            }
        }

        return replacementsMade;
    }

    /**
     * Replace any references to data fields in the given process interface
     * start or intermediate method
     * 
     * @param editingDomain
     * @param cmd
     *            Add EMF modification commands to this command.
     * @param startOrIntermediateMethod
     * @return True if replacements made.
     */
    public boolean replaceFieldReferences(EditingDomain editingDomain,
            AssociatedParametersContainer startOrIntermediateMethod) {
        boolean replacementsMade = false;

        if (!mapIsIdMap) {
            EList assocParams =
                    startOrIntermediateMethod.getAssociatedParameters();

            for (Iterator iterator = assocParams.iterator(); iterator.hasNext();) {
                AssociatedParameter param =
                        (AssociatedParameter) iterator.next();

                String newName = idOrNameMap.get(param.getFormalParam());
                if (newName != null) {
                    // We have a new name to replace this reference with.
                    replacementsMade = true;

                    param.setFormalParam(newName);
                }
            }
        }

        return replacementsMade;
    }

    /**
     * Do a data field / formal param reference BY NAME replacement on the given
     * activity.
     * <p>
     * If the replaceCmd is supplied (not null) then commands are added to it.
     * Otherwise the changes are made directly to the model.
     * <p>
     * Here we can deal with user tasks and independent sub-process call tasks
     * because we know that their parameter mapping is by field/param name.
     * <br/>
     * 
     * @param activity
     * @param editingDomain
     * @param replaceCmd
     * 
     * @return true if any replacements were made.
     */
    private boolean replaceStandardActivityNameReferences(Activity activity,
            EditingDomain editingDomain, CompoundCommand replaceCmd) {
        boolean replacementsMade = false;

        Implementation impl = activity.getImplementation();

        //
        // Replace field/param name references in Independent Sub-Process Task
        // parameter mappings.
        //
        if (impl instanceof SubFlow) {
            SubFlow subFlow = (SubFlow) impl;

            EList<DataMapping> dataMappings = subFlow.getDataMappings();

            if (replaceByNameInDataMappingActual(editingDomain,
                    dataMappings,
                    replaceCmd)) {
                replacementsMade = true;
            }

            //
            // Replace process Identifier field name
            //
            Object obj =
                    Xpdl2ModelUtil.getOtherAttribute(subFlow,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ProcessIdentifierField());
            if (obj instanceof String) {
                String oldFieldName = (String) obj;
                String newName = idOrNameMap.get(oldFieldName);
                if (newName != null) {
                    if (replaceCmd != null) {
                        // If given a command then add to it.
                        replaceCmd
                                .append(Xpdl2ModelUtil
                                        .getSetOtherAttributeCommand(editingDomain,
                                                subFlow,
                                                XpdExtensionPackage.eINSTANCE
                                                        .getDocumentRoot_ProcessIdentifierField(),
                                                newName));
                    } else {
                        Xpdl2ModelUtil
                                .setOtherAttribute(subFlow,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_ProcessIdentifierField(),
                                        newName);
                    }
                    replacementsMade = true;
                }
            }

        } else if (impl instanceof Task && ((Task) impl).getTaskUser() != null) {
            //
            // Replace field/param references in User task parameters.
            //
            TaskUser taskUser = ((Task) impl).getTaskUser();

            Message messageIn = taskUser.getMessageIn();
            if (messageIn != null) {
                EList params = messageIn.getActualParameters();

                int index = 0;
                for (Iterator iter = params.iterator(); iter.hasNext(); index++) {
                    Expression param = (Expression) iter.next();

                    String newName = idOrNameMap.get(param.getText());
                    if (newName != null) {
                        // This name has been changed replace reference to
                        // it.

                        if (replaceCmd != null) {
                            // If given a command then add to it.
                            Expression newParam =
                                    Xpdl2Factory.eINSTANCE
                                            .createExpression(newName);
                            newParam.setScriptGrammar(param.getScriptGrammar());

                            // Add the new name param.
                            replaceCmd.append(AddCommand.create(editingDomain,
                                    messageIn,
                                    Xpdl2Package.eINSTANCE
                                            .getMessage_ActualParameters(),
                                    newParam,
                                    index));
                            // Delete the old one.
                            replaceCmd.append(RemoveCommand
                                    .create(editingDomain, param));

                        } else {
                            // Else change the name directly in model
                            param.getMixed()
                                    .unset(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text());
                            param.getMixed()
                                    .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                                            newName);
                        }

                        replacementsMade = true;

                    }
                }

            } // End of message in params

            Message messageOut = taskUser.getMessageOut();
            if (messageOut != null) {
                EList params = messageOut.getActualParameters();

                int index = 0;
                for (Iterator iter = params.iterator(); iter.hasNext(); index++) {
                    Expression param = (Expression) iter.next();

                    String newName = idOrNameMap.get(param.getText());
                    if (newName != null) {
                        // This name has been changed replace reference to
                        // it.

                        if (replaceCmd != null) {
                            // If given a command then add to it.
                            Expression newParam =
                                    Xpdl2Factory.eINSTANCE
                                            .createExpression(newName);
                            newParam.setScriptGrammar(param.getScriptGrammar());

                            // Add the new name param.
                            replaceCmd.append(AddCommand.create(editingDomain,
                                    messageOut,
                                    Xpdl2Package.eINSTANCE
                                            .getMessage_ActualParameters(),
                                    newParam,
                                    index));
                            // Delete the old one.
                            replaceCmd.append(RemoveCommand
                                    .create(editingDomain, param));

                        } else {
                            // Else change the name directly in model
                            param.getMixed()
                                    .unset(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text());
                            param.getMixed()
                                    .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                                            newName);
                        }

                        replacementsMade = true;

                    }
                }
            } // End of message out params.

        } else if (activity.getEvent() instanceof IntermediateEvent) {
            if (activity.getEvent().getEventTriggerTypeNode() instanceof ResultError) {
                //
                // Catch error events may have mappings to data from error
                // params.
                //
                ResultError resError =
                        (ResultError) activity.getEvent()
                                .getEventTriggerTypeNode();

                CatchErrorMappings catchErrorMappings =
                        (CatchErrorMappings) Xpdl2ModelUtil
                                .getOtherElement(resError,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_CatchErrorMappings());
                if (catchErrorMappings != null
                        && catchErrorMappings.getMessage() != null) {

                    EList<DataMapping> dataMappings =
                            catchErrorMappings.getMessage().getDataMappings();

                    if (replaceByNameInDataMappingActual(editingDomain,
                            dataMappings,
                            replaceCmd)) {
                        replacementsMade = true;
                    }
                }
            }
        }

        //
        // Replace parameter references in activity (Start/Intermediate event
        // and receive task) associated parameters.
        //
        EList associatedParams =
                LocalPackageProcessInterfaceUtil
                        .getActivityAssociatedParameters(activity);
        if (associatedParams != null) {
            for (Iterator iter = associatedParams.iterator(); iter.hasNext();) {
                AssociatedParameter assParam =
                        (AssociatedParameter) iter.next();

                String newName = idOrNameMap.get(assParam.getFormalParam());
                if (newName != null) {
                    // We have a new name for this old name, replace it.
                    replacementsMade = true;

                    if (replaceCmd != null) {
                        // Create command to replace.
                        replaceCmd.append(SetCommand.create(editingDomain,
                                assParam,
                                XpdExtensionPackage.eINSTANCE
                                        .getAssociatedParameter_FormalParam(),
                                newName));
                    } else {
                        // Replace direct in model.
                        assParam.setFormalParam(newName);
                    }
                }
            }
        }

        // Replace associated correlation field references.
        if (Xpdl2ModelUtil.canHaveCorrelationAssociated(activity)) {
            Object other =
                    Xpdl2ModelUtil
                            .getOtherElement(activity,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_AssociatedCorrelationFields());
            if (other instanceof AssociatedCorrelationFields) {
                AssociatedCorrelationFields fieldContainer =
                        (AssociatedCorrelationFields) other;
                List<AssociatedCorrelationField> fieldList =
                        fieldContainer.getAssociatedCorrelationField();
                for (AssociatedCorrelationField field : fieldList) {
                    String newName = idOrNameMap.get(field.getName());
                    if (newName != null) {
                        // We have a new name for this old name, replace it.
                        replacementsMade = true;

                        if (replaceCmd != null) {
                            // Create command to replace.
                            replaceCmd
                                    .append(SetCommand
                                            .create(editingDomain,
                                                    field,
                                                    XpdExtensionPackage.eINSTANCE
                                                            .getAssociatedCorrelationField_Name(),
                                                    newName));
                        } else {
                            // Replace direct in model.
                            field.setName(newName);
                        }
                    }
                }
            }
        }

        return replacementsMade;
    }

    /**
     * Replace field references by name in the given DataMappings(/Actual)
     * 
     * @param editingDomain
     * @param dataMappings
     *            mappings to analyse
     * @param replaceCmd
     *            Command to append any extra commands to.
     * 
     * @return true if any replacements were made.
     */
    private boolean replaceByNameInDataMappingActual(
            EditingDomain editingDomain, EList<DataMapping> dataMappings,
            CompoundCommand replaceCmd) {
        boolean replacementsMade = false;

        for (DataMapping dm : dataMappings) {

            Expression actual = dm.getActual();
            if (actual != null) {
                String oldName = actual.getText();

                if (oldName != null) {
                    String newName = idOrNameMap.get(oldName);

                    if (newName != null) {

                        // This name has been changed replace reference to
                        // it.

                        Expression newActual =
                                Xpdl2Factory.eINSTANCE.createExpression();
                        newActual.setScriptGrammar(actual.getScriptGrammar());
                        newActual
                                .getMixed()
                                .add(XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                                        newName.toString());

                        if (replaceCmd != null) {
                            // If given a command then add to it.
                            replaceCmd.append(SetCommand.create(editingDomain,
                                    dm,
                                    Xpdl2Package.eINSTANCE
                                            .getDataMapping_Actual(),
                                    newActual));

                        } else {
                            // Else change the name directly in model
                            dm.setActual(newActual);

                        }

                        replacementsMade = true;
                    }
                }
            }
        }
        return replacementsMade;
    }

    /**
     * Replace any standard (non extension parts of model that base studio knows
     * about) references to data field by Id.
     * <p>
     * Currently this just means activity perfromer references to Performer data
     * fields.
     * <p>
     * If the replaceCmd is supplied (not null) then commands are added to it.
     * Otherwise the changes are made directly to the model.
     * 
     * @param activity
     * @param editingDomain
     * @param replaceCmd
     * @return
     */
    private boolean replaceStandardActivityIDReferences(Activity activity,
            EditingDomain editingDomain, CompoundCommand replaceCmd) {
        boolean replacementsMade = false;

        EList performers = activity.getPerformerList();
        for (Iterator iter = performers.iterator(); iter.hasNext();) {
            Performer perf = (Performer) iter.next();

            String perfId = perf.getValue();
            if (perfId != null) {
                String newId = idOrNameMap.get(perfId);

                if (newId != null) {
                    // This performer references data field that is changing id.
                    if (replaceCmd != null) {
                        replaceCmd.append(SetCommand.create(editingDomain,
                                perf,
                                Xpdl2Package.eINSTANCE.getPerformer_Value(),
                                newId));

                    } else {
                        perf.setValue(newId);
                    }
                    replacementsMade = true;
                }
            }
        }

        return replacementsMade;
    }

}
