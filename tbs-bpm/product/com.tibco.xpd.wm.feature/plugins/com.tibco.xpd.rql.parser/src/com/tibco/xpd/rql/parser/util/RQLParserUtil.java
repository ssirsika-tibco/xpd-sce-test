/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.rql.parser.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.n2.de.rql.parser.ASTPropertyExpr;
import com.tibco.n2.de.rql.parser.Node;
import com.tibco.n2.de.rql.parser.ParseException;
import com.tibco.n2.de.rql.parser.SimpleNode;
import com.tibco.n2.de.rql.parser.TokenMgrError;
import com.tibco.n2.de.rql.parser.base.EntityNode;
import com.tibco.xpd.om.core.om.Capability;
import com.tibco.xpd.om.core.om.CapabilityAssociation;
import com.tibco.xpd.om.core.om.DynamicOrgUnit;
import com.tibco.xpd.om.core.om.Group;
import com.tibco.xpd.om.core.om.Location;
import com.tibco.xpd.om.core.om.OrgElementType;
import com.tibco.xpd.om.core.om.OrgUnit;
import com.tibco.xpd.om.core.om.OrgUnitRelationship;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.core.om.Position;
import com.tibco.xpd.om.core.om.Privilege;
import com.tibco.xpd.om.core.om.PrivilegeAssociation;
import com.tibco.xpd.om.core.om.Resource;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.ProjectUtil;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.rql.model.RQLConsts;
import com.tibco.xpd.rql.parser.RQLScriptParser;
import com.tibco.xpd.rql.parser.eval.EntityType;
import com.tibco.xpd.rql.parser.eval.QueryVisitor;
import com.tibco.xpd.rql.parser.eval.ValidationContext;
import com.tibco.xpd.rql.parser.jccvalidator.IJCCProcessValidationStrategy;
import com.tibco.xpd.rql.parser.jccvalidator.IJCCSymbolTable;
import com.tibco.xpd.rql.parser.script.RQLScriptRelevantData;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.model.internal.client.IModelScriptRelevantData;
import com.tibco.xpd.script.parser.internal.util.ValidationConstants;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.script.parser.validator.ErrorType;
import com.tibco.xpd.script.parser.validator.IValidationStrategy;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;

/**
 * 
 * @author Miguel Torres
 * 
 */
public class RQLParserUtil {

    public static String N2PE_DESTINATION = "n2pe1.x";//$NON-NLS-1$

    public static String N2UT_DESTINATION =
            "com.tibco.n2.ut.resources.destination.v1.0.0";//$NON-NLS-1$

    public static String RQL_GRAMMAR = "RQL";//$NON-NLS-1$

    public final static String OM_FILE_EXTENSION = "om"; //$NON-NLS-1$

    public final static String OM_SPECIAL_FOLDER_KIND = "om"; //$NON-NLS-1$

    public final static String ENTITY_ELEMENT_NAME = "name"; //$NON-NLS-1$

    public final static String ENTITY_ELEMENT_TYPE = "type"; //$NON-NLS-1$

    public final static String ENTITY_ELEMENT_ATTRIBUTE = "attribute"; //$NON-NLS-1$

    /**
     * Validates the RQL Script
     * 
     * @param strScript
     * @param destinationList
     * @param symbolTable
     * @param validationStrategyList
     * @param validationErrorMap
     * @param validationWarningMap
     * @return
     */
    public static RQLScriptParser validateRQLScript(String strScript,
            List<String> destinationList, IJCCSymbolTable symbolTable,
            List<IValidationStrategy> validationStrategyList,
            Map<String, List<ErrorMessage>> validationErrorMap,
            Map<String, List<ErrorMessage>> validationWarningMap) {
        if (strScript == null || strScript.trim().length() < 1) {
            return null;
        }

        RQLScriptParser parser =
                getRQLScriptParser(strScript,
                        destinationList,
                        validationErrorMap);
        if (parser == null) {
            return null;
        }
        parser.setSymbolTable(symbolTable);
        parser.setValidationStrategyList(validationStrategyList);
        parser.startValidation();

        if (validationErrorMap == null) {
            validationErrorMap = new HashMap<String, List<ErrorMessage>>();
        }
        if (validationWarningMap == null) {
            validationWarningMap = new HashMap<String, List<ErrorMessage>>();
        }
        validationErrorMap.putAll(getErrorMap(parser));
        validationWarningMap.putAll(getWarningMap(parser));
        return parser;
    }

    public static RQLScriptParser getRQLScriptParser(String strScript,
            List<String> destinationList,
            Map<String, List<ErrorMessage>> validationErrorMap) {
        RQLScriptParser parser = new RQLScriptParser(strScript);
        try {
            parser.parseQuery();
            return parser;
        } catch (ParseException e) {
            // there could be errors thrown which we should handle
            handleParseException(e, destinationList, validationErrorMap);
        } catch (TokenMgrError e) {
            // there could be errors thrown which we should handle
            handleParseTokenError(e, destinationList, validationErrorMap);
        }
        return null;
    }

    private static void handleParseException(ParseException e,
            List<String> destinationList,
            Map<String, List<ErrorMessage>> validationErrorMap) {
        String errMsg = e.getLocalizedMessage();
        errMsg = parseRQLErrorMessage(errMsg);
        ErrorType errorType =
                new ErrorType(ValidationConstants.ERRORTYPE_KEY_WRONGFORMAT,
                        null);
        ErrorMessage msg = new ErrorMessage(1, 1, errMsg, errorType);
        handleException(msg, destinationList, validationErrorMap);
    }

    private static void handleParseTokenError(TokenMgrError e,
            List<String> destinationList,
            Map<String, List<ErrorMessage>> validationErrorMap) {
        String errMsg = e.getLocalizedMessage();
        errMsg = parseRQLErrorMessage(errMsg);
        ErrorType errorType =
                new ErrorType(ValidationConstants.ERRORTYPE_KEY_WRONGFORMAT,
                        null);
        ErrorMessage msg = new ErrorMessage(1, 1, errMsg, errorType);
        handleException(msg, destinationList, validationErrorMap);
    }

    private static void handleException(ErrorMessage errorMessage,
            List<String> destinationList,
            Map<String, List<ErrorMessage>> validationErrorMap) {
        List<ErrorMessage> msgList = new ArrayList<ErrorMessage>();
        msgList.add(errorMessage);
        if (validationErrorMap == null) {
            validationErrorMap = new HashMap<String, List<ErrorMessage>>();
        }
        if (destinationList != null) {
            for (String destinationName : destinationList) {
                validationErrorMap.put(destinationName, msgList);
            }
        }
    }

    /**
     * Returns the list of errors in a Map. The key being the destination
     * environment and the value is the list of errors
     * 
     * @param parser
     * @param destinationName
     * @return
     */
    private static Map<String, List<ErrorMessage>> getErrorMap(
            RQLScriptParser parser) {
        List<IValidationStrategy> validatorStrategyList =
                getValidatorStrategyList(parser);
        Map<String, List<ErrorMessage>> errorMap =
                new HashMap<String, List<ErrorMessage>>();
        for (IValidationStrategy strategy : validatorStrategyList) {
            if (strategy instanceof IJCCProcessValidationStrategy) {
                IJCCProcessValidationStrategy pStrategy =
                        (IJCCProcessValidationStrategy) strategy;
                errorMap.put(pStrategy.getDestinationName(),
                        pStrategy.getErrorList());
            }
        }
        return errorMap;
    }

    /**
     * Returns the list of warnings in a Map. The key being the destination
     * environment and the value is the list of warnings
     * 
     * @param parser
     * @param destinationName
     * @return
     */
    private static Map<String, List<ErrorMessage>> getWarningMap(
            RQLScriptParser parser) {
        List<IValidationStrategy> validatorStrategyList =
                getValidatorStrategyList(parser);
        Map<String, List<ErrorMessage>> warningMap =
                new HashMap<String, List<ErrorMessage>>();
        for (IValidationStrategy strategy : validatorStrategyList) {
            if (strategy instanceof IJCCProcessValidationStrategy) {
                IJCCProcessValidationStrategy pStrategy =
                        (IJCCProcessValidationStrategy) strategy;
                warningMap.put(pStrategy.getDestinationName(),
                        pStrategy.getWarningList());
            }

        }
        return warningMap;
    }

    public static List<IValidationStrategy> getValidatorStrategyList(
            RQLScriptParser parser) {
        List<IValidationStrategy> validatorStrategyList =
                parser.getValidatorStrategyList();
        return validatorStrategyList;
    }

    public static String parseRQLErrorMessage(String errorMsg) {
        String parsedMsg = errorMsg;
        if (errorMsg != null) {
            // Remove return characters
            parsedMsg = errorMsg.replaceAll("\r\n", "");
        }
        return parsedMsg;
    }

    public static List<IResource> getReferencedOMFiles(IProject project) {
        if (project != null) {
            Set<IProject> referencedProjectsHierarchy =
                    ProjectUtil.getReferencedProjectsHierarchy(project,
                            new HashSet<IProject>());
            if (referencedProjectsHierarchy == null) {
                referencedProjectsHierarchy = new HashSet<IProject>();
            }
            referencedProjectsHierarchy.add(project);
            List<IResource> referencedOMFiles = new ArrayList<IResource>();
            for (IProject referencedProject : referencedProjectsHierarchy) {
                if (referencedProject != null) {
                    referencedOMFiles
                            .addAll(SpecialFolderUtil
                                    .getAllDeepResourcesInSpecialFolderOfKind(referencedProject,
                                            OM_SPECIAL_FOLDER_KIND,
                                            OM_FILE_EXTENSION,
                                            false));
                }
            }
            return referencedOMFiles;
        }
        return Collections.emptyList();
    }

    @SuppressWarnings("unchecked")//$NON-NLS-1$
    public static List<JsClass> getSupportedJsClasses(String destination,
            String scriptType) {
        if (destination == null || destination.trim().length() < 1) {
            return Collections.EMPTY_LIST;
        }
        List<String> destList = new ArrayList<String>();
        destList.add(destination);
        return getSupportedJsClasses(destList, scriptType);
    }

    @SuppressWarnings("unchecked")//$NON-NLS-1$
    public static List<JsClass> getSupportedJsClasses(
            List<String> destinationList, String scriptType) {
        List<JsClass> supportedJsClassList = new ArrayList<JsClass>();
        List<JsClassDefinitionReader> jsClassDefinitionReader =
                Collections.EMPTY_LIST;
        try {
            jsClassDefinitionReader =
                    ScriptGrammarContributionsUtil.INSTANCE
                            .getJsClassDefinitionReader(destinationList,
                                    scriptType,
                                    RQL_GRAMMAR,
                                    N2UT_DESTINATION);
        } catch (CoreException e) {
            XpdResourcesPlugin.getDefault().getLogger().error(e);
        }
        if (jsClassDefinitionReader.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        for (JsClassDefinitionReader reader : jsClassDefinitionReader) {
            List<JsClass> tempSupportedJsClassList =
                    reader.getSupportedClasses();
            if (tempSupportedJsClassList != null) {
                supportedJsClassList.addAll(tempSupportedJsClassList);
            }
        }
        return supportedJsClassList;
    }

    public static List<IScriptRelevantData> getScriptRelevantDataOfType(
            String type,
            Collection<List<IScriptRelevantData>> supportedScriptRelevantData) {
        if (supportedScriptRelevantData != null
                && !supportedScriptRelevantData.isEmpty()) {
            List<IScriptRelevantData> scriptRelevantDataOfType =
                    new ArrayList<IScriptRelevantData>();
            for (List<IScriptRelevantData> scriptRelevantDataList : supportedScriptRelevantData) {
                for (IScriptRelevantData scriptRelevantData : scriptRelevantDataList) {
                    if (scriptRelevantData != null
                            && scriptRelevantData.getType() != null
                            && scriptRelevantData.getType().equals(type)) {
                        scriptRelevantDataOfType.add(scriptRelevantData);
                    }
                }
            }
            return scriptRelevantDataOfType;
        }
        return Collections.emptyList();
    }

    public static List<IScriptRelevantData> getScriptRelevantData(
            EntityNode query, String aExpression, RQLScriptParser scriptParser,
            List<IScriptRelevantData> supportedScriptRelevantData) {
        ValidationContext validationContext =
                new ValidationContext(aExpression, null);
        HashMap<String, List<IScriptRelevantData>> map =
                new HashMap<String, List<IScriptRelevantData>>();
        map.put("arg", supportedScriptRelevantData);//$NON-NLS-1$
        validationContext.setSupportedScriptRelevantDataMap(map);
        Node parentASTParse = RQLParserUtil.getRootNode(query);
        if (parentASTParse != null) {
            Object jjtAccept =
                    parentASTParse.jjtAccept(QueryVisitor.getInstance(),
                            validationContext);
            if (jjtAccept instanceof Collection) {
                Collection result = (Collection) jjtAccept;
                if (!result.isEmpty()) {
                    List<IScriptRelevantData> allMatchingData =
                            new ArrayList<IScriptRelevantData>();
                    for (Object object : result) {
                        if (object instanceof IScriptRelevantData) {
                            allMatchingData.add((IScriptRelevantData) object);
                        }
                    }
                    return allMatchingData;
                }
            }
        }
        return Collections.emptyList();
    }

    private static Node getRootNode(SimpleNode node) {
        if (node != null) {
            Node parent = node;
            while (parent.jjtGetParent() != null) {
                parent = parent.jjtGetParent();
            }
            return parent;
        }
        return null;
    }

    public static String getScriptRelevantDataModelTypeName(
            IScriptRelevantData iScriptRelevantData) {
        if (iScriptRelevantData instanceof IModelScriptRelevantData) {
            IModelScriptRelevantData iModelScriptRelevantData =
                    (IModelScriptRelevantData) iScriptRelevantData;
            if (iModelScriptRelevantData.getModelType() instanceof OrgElementType) {
                return ((OrgElementType) iModelScriptRelevantData
                        .getModelType()).getName();
            }
        }
        return null;
    }

    public static JsClass getJsClassForName(String name,
            List<JsClass> supportedJsClasses) {
        if (name != null && supportedJsClasses != null) {
            for (JsClass jsClass : supportedJsClasses) {
                if (jsClass != null && jsClass.getName() != null
                        && jsClass.getName().equals(name)) {
                    return jsClass;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("restriction")
    public static List<IScriptRelevantData> getNavigableScriptRelevantData(
            String type, List<IScriptRelevantData> contextScriptRelevantData,
            Collection<List<IScriptRelevantData>> supportedScriptRelevantData) {
        if (type != null && contextScriptRelevantData != null) {
            List<IScriptRelevantData> scriptRelevantDataOfType =
                    RQLParserUtil.getScriptRelevantDataOfType(type,
                            supportedScriptRelevantData);
            if (scriptRelevantDataOfType != null
                    && !scriptRelevantDataOfType.isEmpty()) {
                List<IScriptRelevantData> navigableScriptRelevantData =
                        new ArrayList<IScriptRelevantData>();
                for (IScriptRelevantData scriptRelevantData : scriptRelevantDataOfType) {
                    if (scriptRelevantData instanceof IModelScriptRelevantData) {
                        IModelScriptRelevantData modelScriptRelevantData =
                                (IModelScriptRelevantData) scriptRelevantData;
                        EObject model = modelScriptRelevantData.getModel();
                        for (IScriptRelevantData context : contextScriptRelevantData) {
                            if (context instanceof IModelScriptRelevantData) {
                                IModelScriptRelevantData contextModelScriptData =
                                        (IModelScriptRelevantData) context;
                                EObject contextModel =
                                        contextModelScriptData.getModel();
                                if (RQLParserUtil.isValidReferencedModel(model,
                                        contextModel)) {
                                    navigableScriptRelevantData
                                            .add(modelScriptRelevantData);
                                    break;
                                }
                            }
                        }
                    }
                }
                return navigableScriptRelevantData;
            }
        }
        return Collections.emptyList();
    }

    private static boolean isValidReferencedModel(EObject model,
            EObject contextModel) {
        if (model != null) {
            if (contextModel instanceof Organization) {
                return isValidOrganizationReferencedModel(model,
                        (Organization) contextModel);
            } else if (contextModel instanceof OrgUnit) {
                return isValidOrgUnitReferencedModel(model,
                        (OrgUnit) contextModel);
            } else if (contextModel instanceof Group) {
                return isValidGroupReferencedModel(model, (Group) contextModel);
            } else if (contextModel instanceof Position) {
                return isValidPositionReferencedModel(model,
                        (Position) contextModel);
            } else if (contextModel instanceof Resource) {
                return true;
            }
        }
        return false;
    }

    private static boolean isValidOrganizationReferencedModel(EObject model,
            Organization organization) {
        if (model instanceof Location) {
            Location orgLocation = organization.getLocation();
            if (orgLocation != null && orgLocation.equals(model)) {
                return true;
            }
        } else if (model instanceof OrgUnit) {
            EList<OrgUnit> subUnits = organization.getSubUnits();
            if (subUnits != null) {
                for (OrgUnit subUnit : subUnits) {
                    if (subUnit != null && subUnit.equals(model)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isValidOrgUnitReferencedModel(EObject model,
            OrgUnit orgUnit) {
        if (model instanceof Location) {
            Location orgLocation = orgUnit.getLocation();
            if (orgLocation != null && orgLocation.equals(model)) {
                return true;
            }
        } else if (model instanceof OrgUnit) {
            EList<OrgUnit> subUnits = orgUnit.getSubUnits();
            if (subUnits != null) {
                for (OrgUnit subUnit : subUnits) {
                    if (subUnit != null && subUnit.equals(model)) {
                        return true;
                    }
                }
            }
        } else if (model instanceof Organization) {
            Organization organization = orgUnit.getOrganization();
            if (organization != null && organization.equals(model)) {
                return true;
            }
        } else if (model instanceof Position) {
            EList<Position> positions = orgUnit.getPositions();
            if (positions != null) {
                for (Position position : positions) {
                    if (position != null && position.equals(model)) {
                        return true;
                    }
                }
            }
        } else if (model instanceof Privilege) {
            EList<PrivilegeAssociation> privilegeAssociations =
                    orgUnit.getPrivilegeAssociations();
            if (privilegeAssociations != null) {
                for (PrivilegeAssociation privilegeAssociation : privilegeAssociations) {
                    if (privilegeAssociation != null
                            && privilegeAssociation.getPrivilege() != null
                            && privilegeAssociation.getPrivilege()
                                    .equals(model)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isValidPositionReferencedModel(EObject model,
            Position position) {
        if (model instanceof Capability) {
            EList<CapabilityAssociation> capabilitiesAssociations =
                    position.getCapabilitiesAssociations();
            if (capabilitiesAssociations != null) {
                for (CapabilityAssociation capabilityAssociation : capabilitiesAssociations) {
                    if (capabilityAssociation != null
                            && capabilityAssociation.getCapability() != null
                            && capabilityAssociation.getCapability()
                                    .equals(model)) {
                        return true;
                    }
                }
            }
        } else if (model instanceof Location) {
            Location orgLocation = position.getLocation();
            if (orgLocation != null && orgLocation.equals(model)) {
                return true;
            }
        } else if (model instanceof Privilege) {
            EList<PrivilegeAssociation> privilegeAssociations =
                    position.getPrivilegeAssociations();
            if (privilegeAssociations != null) {
                for (PrivilegeAssociation privilegeAssociation : privilegeAssociations) {
                    if (privilegeAssociation != null
                            && privilegeAssociation.getPrivilege() != null
                            && privilegeAssociation.getPrivilege()
                                    .equals(model)) {
                        return true;
                    }
                }
            }
        } else if (model instanceof Resource) {
            return true;
        }
        return false;
    }

    private static boolean isValidGroupReferencedModel(EObject model,
            Group group) {
        if (model instanceof Capability) {
            EList<CapabilityAssociation> capabilityAssociations =
                    group.getCapabilitiesAssociations();
            if (capabilityAssociations != null) {
                for (CapabilityAssociation capabilityAssociation : capabilityAssociations) {
                    if (capabilityAssociation != null
                            && capabilityAssociation.getCapability() != null
                            && capabilityAssociation.getCapability()
                                    .equals(model)) {
                        return true;
                    }
                }
            }
        } else if (model instanceof Group) {
            EList<Group> subGroups = group.getSubGroups();
            if (subGroups != null) {
                for (Group subGroup : subGroups) {
                    if (subGroup != null && subGroup.equals(model)) {
                        return true;
                    }
                }
            }
        } else if (model instanceof Privilege) {
            EList<PrivilegeAssociation> privilegeAssociations =
                    group.getPrivilegeAssociations();
            if (privilegeAssociations != null) {
                for (PrivilegeAssociation privilegeAssociation : privilegeAssociations) {
                    if (privilegeAssociation != null
                            && privilegeAssociation.getPrivilege() != null
                            && privilegeAssociation.getPrivilege()
                                    .equals(model)) {
                        return true;
                    }
                }
            }
        } else if (model instanceof Resource) {
            return true;
        }
        return false;
    }

    public static String getPropertyEntityTypeStr(ASTPropertyExpr property) {
        if (property != null) {
            EntityType type = getPropertyEntityType(property);
            if (type != null) {
                return type.toString();
            }
        }
        return RQLConsts.UNDEFINED;
    }

    public static EntityType getPropertyEntityType(ASTPropertyExpr property) {
        if (property != null) {
            EntityNode parentEntityNode = getParentEntityNode(property);
            if (parentEntityNode != null) {
                return EntityType.getType(parentEntityNode);
            }
        }
        return null;
    }

    private static EntityNode getParentEntityNode(Node node) {
        if (node != null) {
            Node parentNode = node.jjtGetParent();
            while (parentNode != null) {
                if (parentNode instanceof EntityNode) {
                    return (EntityNode) parentNode;
                }
                parentNode = parentNode.jjtGetParent();
            }
        }
        return null;
    }

    /**
     * @param scriptRelevantData
     * @return
     */
    public static boolean isDynamicRoot(IScriptRelevantData scriptRelevantData) {
        boolean isDynamic = false;
        if (scriptRelevantData instanceof RQLScriptRelevantData) {
            RQLScriptRelevantData data =
                    (RQLScriptRelevantData) scriptRelevantData;
            EObject model = data.getModel();
            if (model instanceof DynamicOrgUnit) {
                isDynamic = true;
            } else if (model instanceof OrgUnit) {
                OrgUnit orgUnit = (OrgUnit) model;
                EList<OrgUnitRelationship> incoming =
                        orgUnit.getIncomingRelationships();
                if (incoming == null || incoming.size() == 0) {
                    Organization organization = orgUnit.getOrganization();
                    if (organization != null && organization.isDynamic()) {
                        isDynamic = true;
                    }
                }
            }
        }
        return isDynamic;
    }

}