/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.rql.script.contentassist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.swt.graphics.Image;
import org.w3c.dom.Node;

import com.tibco.n2.de.rql.parser.SimpleNode;
import com.tibco.n2.de.rql.parser.base.EntityNode;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.rql.parser.RQLScriptParser;
import com.tibco.xpd.rql.parser.util.RQLParserUtil;
import com.tibco.xpd.rql.script.RQLScriptPlugin;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsAttribute;
import com.tibco.xpd.script.model.client.JsClass;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.model.client.JsMethod;
import com.tibco.xpd.script.model.client.JsReference;
import com.tibco.xpd.script.model.internal.client.IModelScriptRelevantData;
import com.tibco.xpd.script.parser.validator.ErrorMessage;
import com.tibco.xpd.script.sourceviewer.internal.contentassist.AbstractTibcoContentAssistProcessor;
import com.tibco.xpd.script.sourceviewer.internal.contentassist.CustomCompletionProposal;
import com.tibco.xpd.script.sourceviewer.internal.contentassist.MyCompletionStringNode;
import com.tibco.xpd.script.sourceviewer.internal.contentassist.MyFollowClass;
import com.tibco.xpd.script.sourceviewer.internal.preferences.AbstractScriptCommonUIPreferenceNames;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;

/**
 * Content Assist Processor for RQL
 * 
 * @author rsomayaj
 * 
 */
public class RQLContentAssistProcessor extends
        AbstractTibcoContentAssistProcessor {

    List<JsMethod> abreviationMethods = null;

    private static final Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    public RQLContentAssistProcessor() {
        super();
    }

    @Override
    public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
            int documentOffset) {
        String text = viewer.getDocument().get();
        String string =
                RQLContentAssistUtils.getRQLStartText(text, documentOffset);
        String prefix = ""; //$NON-NLS-1$
        String rest = ""; //$NON-NLS-1$
        ICompletionProposal[] result =
                getProposalForConfiguredClasses(viewer,
                        string,
                        prefix,
                        documentOffset);
        ICompletionProposal templates[] =
                templateComputeCompletionProposals(viewer, documentOffset);
        if (result == null || result.length == 0) {
            return templates;
        }
        if (templates == null) {
            return result;
        }
        return result;
    }

    @Override
    protected Template[] getTemplates(String contextTypeId) {
        return RQLScriptPlugin.getDefault().getRQLTemplateStore()
                .getTemplates();
    }

    @Override
    protected String getStringToComplete(ITextViewer viewer,
            int documentPosition, Node node) {
        String text = viewer.getDocument().get();
        String prefix =
                RQLContentAssistUtils.getRQLStartText(text, documentPosition);
        return prefix;
    }

    /**
     * given the existing string to complete, return a list of completions
     * 
     * @param estring
     *            the string in the document at which the cursor is at the end
     *            of and which we are to complete
     * @param docPosition
     *            the position in the document that the cursor and the end of
     *            the estring are at.
     */
    @SuppressWarnings("unchecked")//$NON-NLS-1$
    @Override
    protected Vector<CustomCompletionProposal> getProposalVector(
            ITextViewer viewer, String oestring, int docpos,
            MyFollowClass myFollowClass) {
        Vector<CustomCompletionProposal> retval =
                new Vector<CustomCompletionProposal>();
        if (oestring != null) {
            myFollowClass = new MyFollowClass(START_CLASS);
            getProposalRQLVector(retval, myFollowClass, oestring, docpos);
        }
        return retval;
    }

    /**
     * Get the proposal vector
     * 
     * @param retval
     * @param myFollowClass
     * @param oestring
     * @param docpos
     */
    private void getProposalRQLVector(Vector<CustomCompletionProposal> retval,
            MyFollowClass myFollowClass, String oestring, int docpos) {
        if (oestring != null) {
            if (RQLContentAssistUtils.isInsideLiteral(oestring,
                    oestring.length(),
                    true)) {
                getProposalRQLForInsideLiteral(retval,
                        myFollowClass,
                        oestring,
                        docpos,
                        RQLContentAssistUtils.RQL_SINGLE_LITERAL_STRING);
            } else if (RQLContentAssistUtils.isInsideLiteral(oestring,
                    oestring.length(),
                    false)) {
                getProposalRQLForInsideLiteral(retval,
                        myFollowClass,
                        oestring,
                        docpos,
                        RQLContentAssistUtils.RQL_DOUBLE_LITERAL_STRING);
            } else {
                String lastSignificantKeyWord =
                        RQLContentAssistUtils
                                .findLastSignificantKeyword(oestring, true);
                if (lastSignificantKeyWord == null) {
                    addStaticScriptRelevantData(myFollowClass);
                    addMyFollowCombinationExpressions(myFollowClass);
                    getProposalRQLVectorAttributes(retval,
                            myFollowClass,
                            oestring,
                            docpos);
                } else {
                    if (lastSignificantKeyWord
                            .equals(RQLContentAssistUtils.RQL_OPEN_BRACKET_STRING)) {
                        getProposalRQLForOpenBracket(retval,
                                myFollowClass,
                                oestring,
                                docpos);
                    } else if (lastSignificantKeyWord
                            .equals(RQLContentAssistUtils.RQL_CLOSING_BRACKET_STRING)) {
                        getProposalRQLForClosingBracket(retval,
                                myFollowClass,
                                oestring,
                                docpos);
                    } else if (lastSignificantKeyWord
                            .equals(RQLContentAssistUtils.RQL_CONNECTION_WORD_STRING)) {
                        getProposalRQLForConnectionWord(retval,
                                myFollowClass,
                                oestring,
                                docpos);
                    }
                }
            }
        }
    }

    /**
     * Returns the JsClass that ends with the entity passed as parameter
     * 
     * @param oestring
     * @return
     */
    private JsClass getEndsWithEntity(String oestring) {
        Collection<JsClass> contributedClasses = readContributedClass();
        if (contributedClasses != null && !contributedClasses.isEmpty()) {
            for (JsClass jsClass : contributedClasses) {
                if (oestring.endsWith(jsClass.getName())) {
                    return jsClass;
                }
            }
        }
        return null;
    }

    private void getProposalRQLForInsideLiteral(
            Vector<CustomCompletionProposal> retval,
            MyFollowClass myFollowClass, String oestring, int docpos,
            String literalString) {
        // Find the begining of the literal
        int beginingOfLiteral = oestring.lastIndexOf(literalString);
        if (beginingOfLiteral != -1
                && oestring.length() >= beginingOfLiteral
                        + literalString.length()) {
            String literal =
                    oestring.substring(beginingOfLiteral
                            + literalString.length(),
                            oestring.length());
            if (literal != null) {
                // Find the operator
                String lastOperator =
                        RQLContentAssistUtils
                                .findLastOperatorInEntityBracket(oestring, true);
                if (lastOperator != null) {
                    int lastOperatorPos = oestring.lastIndexOf(lastOperator);
                    if (lastOperatorPos != -1) {
                        String stringWithoutOperator =
                                oestring.substring(0, lastOperatorPos);
                        if (stringWithoutOperator != null) {
                            String lastSignificantKeywordInEntityBracket =
                                    RQLContentAssistUtils
                                            .findLastSignificantKeywordInEntityBracket(stringWithoutOperator
                                                    .trim(),
                                                    true);
                            if (lastSignificantKeywordInEntityBracket != null) {
                                int attributePos =
                                        stringWithoutOperator
                                                .lastIndexOf(lastSignificantKeywordInEntityBracket);
                                if (attributePos != -1) {
                                    String attributeName =
                                            stringWithoutOperator
                                                    .substring(attributePos
                                                            + lastSignificantKeywordInEntityBracket
                                                                    .length());
                                    if (attributeName != null
                                            && oestring.length() >= attributePos + 1) {
                                        attributeName = attributeName.trim();
                                        // Find the next entity
                                        JsClass nextEntity =
                                                findNextEntity(oestring
                                                        .substring(0,
                                                                attributePos + 1));
                                        if (nextEntity != null
                                                && isValidEntityAttribute(nextEntity,
                                                        attributeName)) {
                                            List<IScriptRelevantData> modelEntities =
                                                    getModelEntities(nextEntity.getName(),
                                                            oestring);
                                            if (modelEntities != null
                                                    && !modelEntities.isEmpty()) {
                                                if (attributeName
                                                        .equals(RQLContentAssistUtils.RQL_NAME_ATTRIBUTE)) {
                                                    for (IScriptRelevantData scriptRelevantData : modelEntities) {
                                                        if (scriptRelevantData instanceof IModelScriptRelevantData) {
                                                            IModelScriptRelevantData modelScriptRelevantData =
                                                                    (IModelScriptRelevantData) scriptRelevantData;
                                                            JsClass jsClass =
                                                                    modelScriptRelevantData
                                                                            .getJsClass();
                                                            if (jsClass != null) {
                                                                MyCompletionStringNode myCompletionStringNode =
                                                                        new MyCompletionStringNode(
                                                                                jsClass);
                                                                myFollowClass
                                                                        .addMyCompletionStringNode(myCompletionStringNode);
                                                            }
                                                        }
                                                    }
                                                } else if (attributeName
                                                        .equals(RQLContentAssistUtils.RQL_TYPE_ATTRIBUTE)) {
                                                    for (IScriptRelevantData scriptRelevantData : modelEntities) {
                                                        if (scriptRelevantData instanceof IModelScriptRelevantData) {
                                                            IModelScriptRelevantData modelScriptRelevantData =
                                                                    (IModelScriptRelevantData) scriptRelevantData;
                                                            String modelTypeName =
                                                                    modelScriptRelevantData
                                                                            .getModelTypeName();
                                                            if (modelTypeName != null) {
                                                                MyCompletionStringNode myCompletionStringNode =
                                                                        new MyCompletionStringNode(
                                                                                modelTypeName,
                                                                                RQLContentAssistUtils.EMPTY_STRING,
                                                                                modelScriptRelevantData
                                                                                        .getIcon(),
                                                                                false);
                                                                myFollowClass
                                                                        .addMyCompletionStringNode(myCompletionStringNode);
                                                            }
                                                        }
                                                    }
                                                }
                                                getProposalRQLVectorAttributes(retval,
                                                        myFollowClass,
                                                        literal,
                                                        docpos);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private List<IScriptRelevantData> getModelEntities(String entityName,
            String context) {
        if (entityName != null) {
            if (isDotExpression(context)) {
                return getContextModelEntities(entityName, context);
            } else {
                return getModelEntities(entityName, getScriptDataList());
            }
        }
        return Collections.emptyList();
    }

    private List<IScriptRelevantData> getModelEntities(String entityName,
            List<IScriptRelevantData> scriptDataList) {
        if (scriptDataList != null && !scriptDataList.isEmpty()) {
            List<IScriptRelevantData> entityList =
                    new ArrayList<IScriptRelevantData>();
            for (IScriptRelevantData scriptRelevantData : scriptDataList) {
                if (scriptRelevantData != null
                        && scriptRelevantData.getType() != null) {
                    String type = scriptRelevantData.getType();
                    if (type.equals("ORGANIZATIONAL_UNIT")//$NON-NLS-1$
                            && !RQLParserUtil.isDynamicRoot(scriptRelevantData)) {
                        type = "orgunit";//$NON-NLS-1$
                    }
                    if (type.equalsIgnoreCase(entityName)) {
                        entityList.add(scriptRelevantData);
                    }
                }
            }
            return entityList;
        }
        return Collections.emptyList();
    }

    @SuppressWarnings("restriction")
    private List<IScriptRelevantData> getContextModelEntities(
            String entityName, String context) {
        // Remove the last entity
        int lastDotPosition = context.lastIndexOf(".");//$NON-NLS-1$
        if (lastDotPosition > 1) {
            String substring = context.substring(0, lastDotPosition);
            RQLScriptParser scriptParser =
                    RQLParserUtil.getRQLScriptParser(substring,
                            new ArrayList<String>(),
                            new HashMap<String, List<ErrorMessage>>());
            if (scriptParser == null) {
                return getModelEntities(entityName, getScriptDataList());
            } else {
                EntityNode lastEntity =
                        findLastChild(scriptParser.getParseResult());
                if (lastEntity != null) {
                    return getNavigableModelEntities(lastEntity,
                            substring,
                            entityName,
                            scriptParser);
                }
            }
        }
        return Collections.emptyList();
    }

    private EntityNode findLastChild(SimpleNode aNode) {
        EntityNode result = null;
        for (int i = 0, n = aNode.jjtGetNumChildren(); i < n; i++) {
            com.tibco.n2.de.rql.parser.Node child = aNode.jjtGetChild(i);
            if (child instanceof EntityNode) {
                result = (EntityNode) child;
            }

            if (child instanceof SimpleNode) {
                EntityNode lastChild = findLastChild((SimpleNode) child);
                if (lastChild != null) {
                    result = lastChild;
                }
            }
        }

        return (result);
    }

    private List<IScriptRelevantData> getNavigableModelEntities(
            EntityNode query, String aExpression, String entityName,
            RQLScriptParser scriptParser) {
        List<IScriptRelevantData> contextScriptRelevantData =
                RQLParserUtil.getScriptRelevantData(query,
                        aExpression,
                        scriptParser,
                        getScriptDataList());

        String type = RQLContentAssistUtils.getEntityType(entityName);
        List<IScriptRelevantData> navigableScriptRelevantData =
                RQLParserUtil.getNavigableScriptRelevantData(type,
                        contextScriptRelevantData,
                        Collections.singleton(getScriptDataList()));

        return getModelEntities(entityName, navigableScriptRelevantData);
    }

    private boolean isDotExpression(String context) {
        if (context != null && context.contains(".")) {//$NON-NLS-1$
            return true;
        }
        return false;
    }

    private boolean isValidEntityAttribute(JsClass entity, String attributeName) {
        if (entity != null && attributeName != null) {
            for (JsAttribute jsAttribute : entity.getAttributeList()) {
                if (jsAttribute != null && jsAttribute.getName() != null
                        && jsAttribute.getName().equals(attributeName)) {
                    return true;
                }
            }
        }
        return false;
    }

    private JsClass findNextEntity(String oestring) {
        if (oestring != null) {
            // Get next open bracket
            String lastSignificantKeyword =
                    RQLContentAssistUtils.findLastSignificantKeyword(oestring,
                            true);
            int lastSignificantKeywordPos =
                    oestring.lastIndexOf(lastSignificantKeyword);
            String newOestring = oestring;
            while (lastSignificantKeyword != null
                    && !lastSignificantKeyword
                            .equals(RQLContentAssistUtils.RQL_OPEN_BRACKET_STRING)) {
                if (lastSignificantKeywordPos != -1) {
                    newOestring =
                            newOestring.substring(0, lastSignificantKeywordPos);
                    lastSignificantKeyword =
                            RQLContentAssistUtils
                                    .findLastSignificantKeyword(newOestring,
                                            true);
                    lastSignificantKeywordPos =
                            oestring.lastIndexOf(lastSignificantKeyword);
                } else {
                    lastSignificantKeyword = null;
                }
            }
            if (lastSignificantKeyword != null
                    && lastSignificantKeyword
                            .equals(RQLContentAssistUtils.RQL_OPEN_BRACKET_STRING)) {
                if (newOestring.length() - 1 >= lastSignificantKeywordPos) {
                    // Check if it is an entity
                    String entityCandidate =
                            newOestring.substring(0, lastSignificantKeywordPos);
                    if (entityCandidate != null) {
                        return getEndsWithEntity(entityCandidate);
                    }
                }
            }
        }
        return null;
    }

    private void getProposalRQLForOpenBracket(
            Vector<CustomCompletionProposal> retval,
            MyFollowClass myFollowClass, String oestring, int docpos) {
        int lastOpenBracketPos =
                oestring.lastIndexOf(RQLContentAssistUtils.RQL_OPEN_BRACKET_STRING);
        if (oestring.length() - 1 >= lastOpenBracketPos) {
            // Check if it is an entity
            String entityCandidate = oestring.substring(0, lastOpenBracketPos);
            if (entityCandidate != null) {
                JsClass endsWithEntity = getEndsWithEntity(entityCandidate);
                if (oestring.length() >= lastOpenBracketPos + 1) {
                    String textToComplete =
                            oestring.substring(lastOpenBracketPos + 1);
                    if (textToComplete != null) {
                        if (endsWithEntity == null) {
                            addStaticScriptRelevantData(myFollowClass);
                            getProposalRQLVectorAttributes(retval,
                                    myFollowClass,
                                    textToComplete,
                                    docpos);
                        } else {
                            textToComplete =
                                    getTextToCompleteInsideEntityBrackets(textToComplete);
                            for (JsAttribute jsAttribute : endsWithEntity
                                    .getAttributeList()) {
                                if (jsAttribute != null) {
                                    addMyFollowJsAttribute(myFollowClass,
                                            jsAttribute,
                                            false);
                                }
                            }
                            addMyFollowRegulatorExpressions(myFollowClass);
                            getProposalRQLVectorAttributes(retval,
                                    myFollowClass,
                                    textToComplete,
                                    docpos);
                        }
                    }
                }
            }
        }
    }

    private void getProposalRQLForClosingBracket(
            Vector<CustomCompletionProposal> retval,
            MyFollowClass myFollowClass, String oestring, int docpos) {
        int lastClossingBracketPos =
                oestring.lastIndexOf(RQLContentAssistUtils.RQL_CLOSING_BRACKET_STRING);
        if (oestring.length() >= lastClossingBracketPos
                + RQLContentAssistUtils.RQL_CLOSING_BRACKET_STRING.length()) {
            String textToComplete =
                    oestring.substring(lastClossingBracketPos
                            + RQLContentAssistUtils.RQL_CLOSING_BRACKET_STRING
                                    .length());
            if (textToComplete != null) {
                textToComplete = textToComplete.trim();
                addStaticScriptRelevantData(myFollowClass);
                addMyFollowCombinationExpressions(myFollowClass);
                getProposalRQLVectorAttributes(retval,
                        myFollowClass,
                        textToComplete,
                        docpos);
            }
        }
    }

    private void getProposalRQLForConnectionWord(
            Vector<CustomCompletionProposal> retval,
            MyFollowClass myFollowClass, String oestring, int docpos) {
        int lastConnectionWordPos =
                oestring.lastIndexOf(RQLContentAssistUtils.RQL_CONNECTION_WORD_STRING);
        if (lastConnectionWordPos != -1
                && oestring.length() >= lastConnectionWordPos
                        + RQLContentAssistUtils.RQL_CONNECTION_WORD_STRING
                                .length()) {

            if (RQLContentAssistUtils.isInsideBrackets(oestring)) {
                getAttributeCompletionWord(retval,
                        myFollowClass,
                        oestring,
                        docpos);
            } else {
                String textToComplete =
                        oestring.substring(lastConnectionWordPos
                                + RQLContentAssistUtils.RQL_CONNECTION_WORD_STRING
                                        .length());

                if (textToComplete != null) {
                    textToComplete = textToComplete.trim();
                    // Find the entity
                    int lastClosingBracketPos =
                            oestring.lastIndexOf(RQLContentAssistUtils.RQL_CLOSING_BRACKET_STRING);
                    if (lastClosingBracketPos != -1
                            && lastClosingBracketPos == lastConnectionWordPos - 1) {
                        int lastOpeningBracketPos =
                                oestring.lastIndexOf(RQLContentAssistUtils.RQL_OPEN_BRACKET_STRING);
                        if (lastOpeningBracketPos != -1
                                && (oestring.length() - 1) >= lastOpeningBracketPos) {
                            // Check if it is an entity
                            String entityCandidate =
                                    oestring.substring(0, lastOpeningBracketPos);
                            if (entityCandidate != null) {
                                JsClass endsWithEntity =
                                        getEndsWithEntity(entityCandidate);
                                if (endsWithEntity != null) {
                                    for (JsReference jsReference : endsWithEntity
                                            .getReferenceList()) {
                                        if (jsReference != null) {
                                            addMyFollowJsReference(myFollowClass,
                                                    jsReference);
                                        }
                                    }
                                    for (JsMethod jsMethod : endsWithEntity
                                            .getMethodList()) {
                                        if (jsMethod != null) {
                                            addMyFollowJsMethod(myFollowClass,
                                                    jsMethod);
                                        }
                                    }

                                    getProposalRQLVectorAttributes(retval,
                                            myFollowClass,
                                            textToComplete,
                                            docpos);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void getAttributeCompletionWord(
            Vector<CustomCompletionProposal> retval,
            MyFollowClass myFollowClass, String oestring, int docpos) {
        int lastOpenBracketPos =
                oestring.lastIndexOf(RQLContentAssistUtils.RQL_OPEN_BRACKET_STRING);
        if (oestring.length() - 1 >= lastOpenBracketPos) {
            // Check if it is an entity
            String entityCandidate = oestring.substring(0, lastOpenBracketPos);
            if (entityCandidate != null) {
                JsClass endsWithEntity = getEndsWithEntity(entityCandidate);
                if (oestring.length() >= lastOpenBracketPos + 1) {
                    String textToComplete =
                            oestring.substring(lastOpenBracketPos + 1);
                    if (textToComplete != null) {
                        if (endsWithEntity != null) {
                            textToComplete =
                                    getTextToCompleteInsideEntityBrackets(textToComplete);
                            if (textToComplete != null) {
                                String[] splitText =
                                        textToComplete
                                                .split("\\"
                                                        + RQLContentAssistUtils.RQL_CONNECTION_WORD_STRING);
                                String attributeName = null;
                                String attributeValue =
                                        RQLContentAssistUtils.EMPTY_STRING;
                                if (splitText.length > 0) {
                                    attributeName = splitText[0];
                                }
                                if (splitText.length > 1) {
                                    attributeValue = splitText[1];
                                }
                                JsAttribute matchingAttribute = null;
                                for (JsAttribute jsAttribute : endsWithEntity
                                        .getAttributeList()) {
                                    if (jsAttribute != null
                                            && jsAttribute.getName() != null
                                            && jsAttribute.getName()
                                                    .equals(attributeName)) {
                                        matchingAttribute = jsAttribute;
                                        break;
                                    }
                                }
                                if (matchingAttribute != null
                                        && matchingAttribute.getName() != null) {
                                    List<IScriptRelevantData> modelEntities =
                                            getModelEntities(entityCandidate,
                                                    oestring);
                                    if (matchingAttribute
                                            .getName()
                                            .equals(RQLContentAssistUtils.RQL_ATTRIBUTE_ATTRIBUTE)) {
                                        for (IScriptRelevantData scriptRelevantData : modelEntities) {
                                            if (scriptRelevantData instanceof IModelScriptRelevantData) {
                                                IModelScriptRelevantData modelScriptRelevantData =
                                                        (IModelScriptRelevantData) scriptRelevantData;
                                                JsClass jsClass =
                                                        modelScriptRelevantData
                                                                .getJsClass();
                                                if (jsClass != null) {
                                                    List<JsAttribute> attributeList =
                                                            jsClass.getAttributeList();
                                                    for (JsAttribute jsAttribute : attributeList) {
                                                        addMyFollowJsAttribute(myFollowClass,
                                                                jsAttribute,
                                                                false);
                                                    }
                                                }
                                            }
                                        }
                                        getProposalRQLVectorAttributes(retval,
                                                myFollowClass,
                                                attributeValue,
                                                docpos);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private String getTextToCompleteInsideEntityBrackets(String oestring) {
        String findLastSignificantKeywordInEntityBracket =
                RQLContentAssistUtils
                        .findLastSignificantKeywordInEntityBracket(oestring,
                                false);
        if (findLastSignificantKeywordInEntityBracket != null) {
            int lastIndexOf =
                    oestring.lastIndexOf(findLastSignificantKeywordInEntityBracket);
            if (oestring.length() >= lastIndexOf
                    + findLastSignificantKeywordInEntityBracket.length()) {
                String text =
                        oestring.substring(lastIndexOf
                                + findLastSignificantKeywordInEntityBracket
                                        .length());
                return text.trim();
            }
        }
        return oestring;
    }

    private void getProposalRQLVectorAttributes(
            Vector<CustomCompletionProposal> retval,
            MyFollowClass myFollowClass, String oestring, int docpos) {
        Iterator<MyCompletionStringNode> myCompNodeItr =
                myFollowClass.getCompletionNodes().iterator();
        // Get the elements inside the start class
        while (myCompNodeItr.hasNext()) {
            MyCompletionStringNode myCompNode = myCompNodeItr.next();
            if (myCompNode != null) {
                String cs = myCompNode.getCompletionString();
                String additionalInfo = myCompNode.getComment();
                if (cs != null
                        && (cs.toUpperCase().startsWith(oestring.trim()
                                .toUpperCase())
                                || cs.equals("") || oestring.trim().equals(""))) { //$NON-NLS-1$
                    int cursorPosition = cs.length();
                    if (cs.trim()
                            .endsWith(RQLContentAssistUtils.RQL_CLOSING_BRACKET_STRING)) {
                        if (cs.trim().length() > 1) {
                            cursorPosition = cs.length() - 1;
                        }
                    }

                    CustomCompletionProposal cap =
                            new CustomCompletionProposal(cs, docpos
                                    - oestring.trim().length(), oestring.trim()
                                    .length(), cursorPosition,
                                    myCompNode.getImage(),
                                    myCompNode.getCompletionString(), null,
                                    additionalInfo);
                    retval.add(cap);
                }
            }
        }
    }

    private void addMyFollowRegulatorExpressions(MyFollowClass myFollowClass) {
        List<String> regulatorExpressionList =
                RQLContentAssistUtils.getRegulatorExpressionList();
        for (String string : regulatorExpressionList) {
            myFollowClass.addMyCompletionStringNode(new MyCompletionStringNode(
                    string, RQLContentAssistUtils.EMPTY_STRING, getImage(null),
                    false));
        }
    }

    private void addMyFollowJsAttribute(MyFollowClass myFollowClass,
            JsAttribute attribute, boolean includeAt) {
        if (attribute != null && myFollowClass != null) {
            myFollowClass.addMyCompletionStringNode(new MyCompletionStringNode(
                    attribute, includeAt));
        }
    }

    private void addMyFollowJsReference(MyFollowClass myFollowClass,
            JsReference reference) {
        if (reference != null && myFollowClass != null) {
            myFollowClass.addMyCompletionStringNode(new MyCompletionStringNode(
                    reference));
        }
    }

    private void addMyFollowJsMethod(MyFollowClass myFollowClass,
            JsMethod method) {
        if (method != null && myFollowClass != null) {
            myFollowClass.addMyCompletionStringNode(new MyCompletionStringNode(
                    method));
        }
    }

    @Override
    protected void addCustomCompletionString(MyFollowClass myStartFollowClass) {
        // Do nothing
    }

    @Override
    protected Image getImage(Template template) {
        Image image =
                RQLScriptPlugin.getDefault().getImageRegistry()
                        .get(RQLContentAssistConstants.Function);
        return image;
    }

    @Override
    protected String getArrayContentAssistName(String name) {
        return name;
    }

    @Override
    protected TemplateContextType getContextType(ITextViewer viewer,
            IRegion region) {
        return RQLScriptPlugin.getDefault().getContextTypeRegistry()
                .getContextType(RQLContentAssistConstants.RQL_TEMPLATE_CONTEXT);
    }

    @Override
    protected Map<String, IScriptRelevantData> getLocalScriptDataMap() {
        return null;
    }

    @Override
    protected Map<String, IScriptRelevantData> getGlobalScriptDataMap() {
        return null;
    }

    @Override
    protected MyFollowClass getStartMyFollowClass() {
        myStartFollowClass = new MyFollowClass(START_CLASS);
        if (myStartFollowClass == null) {
            return myStartFollowClass;
        }
        addCustomCompletionString(myStartFollowClass);
        return myStartFollowClass;
    }

    protected Collection<JsClass> readRQLContributedClass() {
        Collection<JsClass> allSupporteClasses = new ArrayList<JsClass>();
        List<JsClassDefinitionReader> jsClassProvider =
                getClassDefinitionReaders();
        for (JsClassDefinitionReader classDefinitionReader : jsClassProvider) {
            if (classDefinitionReader != null) {
                Collection<JsClass> supportedClasses =
                        classDefinitionReader.getSupportedClasses();
                allSupporteClasses.addAll(supportedClasses);
            }
        }
        return allSupporteClasses;
    }

    /**
     * @see com.tibco.xpd.script.sourceviewer.internal.contentassist.AbstractTibcoContentAssistProcessor#getGrammarType()
     * 
     * @return
     */
    @Override
    protected String getGrammarType() {
        return RQLContentAssistConstants.RQL_GRAMMAR;
    }

    /**
     * @see com.tibco.xpd.script.sourceviewer.internal.contentassist.AbstractTibcoContentAssistProcessor#getNodeImage(org.w3c.dom.Node)
     * 
     * @param node
     * @return
     */
    @Override
    protected Image getNodeImage(Node node) {
        return RQLContentAssistUtils.getIconForNode(node);
    }

    protected void addStaticScriptRelevantData(MyFollowClass myFollowClass) {
        Collection<JsClass> contributedClasses = readContributedClass();
        if (contributedClasses != null) {
            for (JsClass jsClass : contributedClasses) {
                MyCompletionStringNode myCompletionStringNode =
                        new MyCompletionStringNode(jsClass);
                myFollowClass.addMyCompletionStringNode(myCompletionStringNode);
            }
        }
    }

    private void addMyFollowCombinationExpressions(MyFollowClass myFollowClass) {
        List<String> combiningExpressionList =
                RQLContentAssistUtils.getCombiningExpressionList();
        for (String string : combiningExpressionList) {
            myFollowClass.addMyCompletionStringNode(new MyCompletionStringNode(
                    string, RQLContentAssistUtils.EMPTY_STRING,
                    RQLContentAssistUtils.getCombiningExpressionIcon(string),
                    false));
        }
    }

    /**
     * @see com.tibco.xpd.script.sourceviewer.internal.contentassist.AbstractTibcoContentAssistProcessor#getCommonUIPreferenceNames()
     * 
     * @return
     * @throws CoreException
     */
    @Override
    protected AbstractScriptCommonUIPreferenceNames getCommonUIPreferenceNames() {
        try {
            return ScriptGrammarContributionsUtil.INSTANCE
                    .getScriptCommonUIPreferenceNames(getGrammarType());
        } catch (CoreException e) {
            LOG.error(e);
        }
        return null;
    }

    /**
     * @see com.tibco.xpd.script.sourceviewer.internal.contentassist.AbstractTibcoContentAssistProcessor#getPreferenceStore()
     * 
     * @return
     */
    @Override
    protected IPreferenceStore getPreferenceStore() {
        return RQLScriptPlugin.getDefault().getPreferenceStore();
    }

}
