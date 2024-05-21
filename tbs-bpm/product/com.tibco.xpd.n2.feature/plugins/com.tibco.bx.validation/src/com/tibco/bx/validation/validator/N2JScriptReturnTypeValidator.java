/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.bx.validation.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.processscriptlibrary.resource.editor.util.PslEditorUtil;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.internal.client.ITypeResolution;
import com.tibco.xpd.script.model.jscript.JScriptUtils;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptReturnTypeValidator;
import com.tibco.xpd.script.parser.util.ParseUtil;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

import antlr.Token;
import antlr.collections.AST;

/**
 * Replacement for the default return type validator to enforce return types
 * only on the last statement of the script and correctly identify the return
 * type.
 * 
 * @author nwilson
 * @since 29 Apr 2015
 */
public class N2JScriptReturnTypeValidator extends JScriptReturnTypeValidator {

	/**
     * @see com.tibco.xpd.script.parser.internal.validator.jscript.AbstractExpressionValidator#validate(antlr.collections.AST,
     *      antlr.Token)
     * 
     * @param astTree
     *            The complete AST tree.
     * @param token
     *            The top level token.
     */
    @SuppressWarnings("restriction")
    @Override
    public void validate(AST astTree, Token token) {
        if (astTree == null) {
            return;
        }
        AST lastStatementAST = ParseUtil.getLastExpressionFromTree(astTree);
        AST lastExpressionAST = lastStatementAST;
        if (lastExpressionAST == null) {
            return;
        }
        int statType = lastExpressionAST.getType();
        if (statType == JScriptTokenTypes.LITERAL_return) {
            AST expr = lastExpressionAST.getFirstChild();
            if (expr != null && JScriptTokenTypes.EXPR == expr.getType()) {
                lastExpressionAST = expr.getFirstChild();
            }
        }
        if (lastExpressionAST == null) {
            return;
        }
        IValidateResult delegateEvaluateExpression =
                delegateEvaluateExpression(lastExpressionAST, token);
        if (delegateEvaluateExpression != null
                && delegateEvaluateExpression.getType() != null) {
            IScriptRelevantData type = delegateEvaluateExpression.getType();
            if (JScriptUtils.isGenericType(type.getType())) {
                if (type instanceof ITypeResolution) {
                    IScriptRelevantData genericContextType =
                            ((ITypeResolution) type).getGenericContextType();
                    if (genericContextType != null) {
                        type = genericContextType;
                    }
                }
            }
            registerReturnType(type);
        }
        // Prevent non-return errors from being reported.
        getErrorMessageList().clear();
        getWarningMessageList().clear();

        /*
         * Sid XPD-7996: Many mapping scenarios support both JavaScript and
         * DataMapper mapping grammars. User defined mapping scripts are exactly
         * equivalent in both EXCEPT that data mapper scripts demand that last
         * statement MUST be RETURN and mapping scripts for JavaScripts mapping
         * grammar do not support return.
         * 
         * In order to NOT have to have SEPARATE script contexts and
         * contributions for JavaScript and Datamapper mapping scripts FOR EVERY
         * mapping scenario that supports both JavaScript and DataMapper, this
         * 'must have return statement as last line' strategy is now
         * conditionalised. If the input ScriptInformation element is contained
         * within a ScriptDataMapper element then we know that it is a
         * DataMapper grammar scenario and can enforce this rule.
         * 
         * This means that the N2JScriptFunctionValidationStrategy can be
         * contributed to a single mapping script context for both JavaScript
         * and DataMapper scenarios.
         */
        boolean isDataMapperScenario = isDataMapperMappingScript();

		if (isDataMapperScenario)
		{
            if (statType != JScriptTokenTypes.LITERAL_return) {
                String errorMessage =
                        Messages.N2FunctionStatementValidator_LastStatementReturn;
                addErrorMessage(token, errorMessage);
            }
        }

		/*
		 * Chaitanya ACE-8027: Validate return statements for PSL Functions.
		 * 
		 * Purpose: As part of ACE-8027, we introduced the use of "return" statements in Process Script Library
		 * Functions.
		 * 
		 * Description: We now support the use of "return" statements in Process Script Library Functions. We need to
		 * consider the following cases:
		 * 
		 * 1. We want to raise a validation to-do when the last statement for Process Script Library Functions with a
		 * NON-VOID RETURN type is NOT a "return" statement.
		 */
		boolean isPSLfunctionWithNonVoidReturnScenario = isPSLfunctionWithNonVoidReturnScenario();
		if (isPSLfunctionWithNonVoidReturnScenario)
		{
			// For Process Script Library Functions with NON Void Return must always end with return statement.
			if (statType != JScriptTokenTypes.LITERAL_return)
			{
				String errorMessage = Messages.N2FunctionStatementValidator_PSFunctionWithNonVoidReturnTypeParamter_LastStatementWithReturn;
				addErrorMessage(token, errorMessage);
			}
				
		}
		
        
        
		boolean isProcessScriptLibraryFunctionScenario = isProcessScriptLibraryFunction();
		/*
		 * Chaitanya ACE-8027: Validate return statements for PSL Functions.
		 * 
		 * We ignore the problem marker "Return statements can only be on the last line of script" for Process Script
		 * Library Functions.
		 */
		if (!isProcessScriptLibraryFunctionScenario)
		{
			// Check that there are no other return statements
			validateOtherReturns(astTree, lastStatementAST, token);
		}
    }


	/**
	 * @return <code>true</code> if we are validating a user defined mapping script within a DataMapper mapping grammar
	 *         scenario.
	 */
    protected boolean isDataMapperMappingScript() {
        boolean isDataMapperScenario = false;
        if (getInfoObject() != null) {
            EObject input = getInput(getInfoObject());

            if (input != null
                    && Xpdl2ModelUtil
                            .getAncestor(input, ScriptDataMapper.class) != null) {
                isDataMapperScenario = true;
            }
        }
        return isDataMapperScenario;
    }

	/**
	 * Function to determine weather the current input in selection is a script library function.
	 * 
	 * @return true if the current input in selection is a script library function or else false.
	 */
	protected boolean isProcessScriptLibraryFunction()
	{
		boolean isProcessScriptLibraryFunction = false;
		EObject input = getInput(getInfoObject());

		if (input != null)
		{
			isProcessScriptLibraryFunction = PslEditorUtil.isScriptLibraryFunction(input);
		}
		return isProcessScriptLibraryFunction;
	}

	/**
	 * Function to determine weather the $RETURN type parameter in process script library function is of type non-void
	 * return.
	 * 
	 * @return true if the $RETURN type parameter in a script library function is of type non-void return or else false.
	 */
	protected boolean isReturnTypeParamaterOfTypeNonVoidReturn()
	{
		boolean isReturnTypeParameterOfTypeNonVoidReturnScenario = false;
		ProcessRelevantData processRelevantDataForReturnTypeParameter = getProcessRelevantDataForReturnTypeParameter();

		if (processRelevantDataForReturnTypeParameter != null)
		{
			DataType dataType = processRelevantDataForReturnTypeParameter.getDataType();
			isReturnTypeParameterOfTypeNonVoidReturnScenario = (dataType != null);
		}

		return isReturnTypeParameterOfTypeNonVoidReturnScenario;
	}

	/**
	 * Function that returns the {@link EList}<{@link DataField}> from the current input selection (i.e.
	 * {@link Activity}) context.
	 * 
	 * @return
	 */
	protected EList<DataField> getProcessDataList()
	{

		EObject input = getInput(getInfoObject());

		if (input instanceof Activity)
		{
			return ((Activity) input).getDataFields();
		}

		return null;
	}

	/**
	 * Function that returns $RETURN type parameter in the currently selected Activity. (i.e. Process Script Library
	 * Function.)
	 * 
	 * @return Returns the {@link ProcessRelevantData} for $RETURN type parameter in the currently selected Activity.
	 */
	protected ProcessRelevantData getProcessRelevantDataForReturnTypeParameter()
	{
		Collection<DataField> activityDataList = new ArrayList<DataField>();
		ProcessRelevantData processDataForReturnTypeParameter = null;
		activityDataList = getProcessDataList();

		if (activityDataList != null)
		{
			for (Iterator iterator = activityDataList.iterator(); iterator.hasNext();)
			{
				ProcessRelevantData processRelevantData = (ProcessRelevantData) iterator.next();
				if (PslEditorUtil.RETURN_PARAMETER_NAME.equals(processRelevantData.getName()))
				{
					processDataForReturnTypeParameter = processRelevantData;
				}
			}
		}

		return processDataForReturnTypeParameter;
	}

	/**
	 * Function to determine weather the current input in selection is a script library function and the $RETURN type
	 * parameter in PSL function is of type non-void return.
	 * 
	 * @return true if the the current input in selection is a script library function and the $RETURN type parameter in
	 *         PSL function is of type non-void return or else false.
	 */
	protected boolean isPSLfunctionWithNonVoidReturnScenario()
	{
		return isProcessScriptLibraryFunction() && isReturnTypeParamaterOfTypeNonVoidReturn();
	}

    /**
     * @param astTree
     *            The current AST tree location.
     * @param lastExpressionAST
     *            The last expression in the script.
     * @param token
     *            The top level token.
     */
    private void validateOtherReturns(AST astTree, AST lastExpressionAST,
            Token token) {
        // Ignore last statement. Note this must use != not 'equals' as the
        // equals method only compares text and type.
        if (astTree != null && astTree != lastExpressionAST) {
            int statType = astTree.getType();
            if (statType == JScriptTokenTypes.LITERAL_return) {
                String errorMessage =
                        Messages.N2JScriptReturnTypeValidator_ReturnLastLineOnly;
                addErrorMessage(token, errorMessage);
            }
            validateOtherReturns(astTree.getFirstChild(),
                    lastExpressionAST,
                    token);
            validateOtherReturns(astTree.getNextSibling(),
                    lastExpressionAST,
                    token);
        }
    }
}
