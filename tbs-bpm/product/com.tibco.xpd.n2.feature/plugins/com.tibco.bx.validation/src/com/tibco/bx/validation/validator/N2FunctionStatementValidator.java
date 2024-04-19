package com.tibco.bx.validation.validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.destinations.ui.DestinationUtil;
import com.tibco.xpd.n2.cds.utils.CDSUtils;
import com.tibco.xpd.process.js.model.ProcessJsConsts;
import com.tibco.xpd.processeditor.xpdl2.properties.script.ProcessScriptContextConstants;
import com.tibco.xpd.processscriptlibrary.resource.editor.util.PslEditorUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.model.client.JsClassDefinitionReader;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.script.parser.internal.validator.IValidateResult;
import com.tibco.xpd.script.ui.ScriptGrammarContributionsUtil;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

import antlr.Token;
import antlr.collections.AST;

/**
 * N2 expression validator for scripts that will be used as the body of a
 * function.
 * 
 * @author nwilson
 * 
 */
public class N2FunctionStatementValidator extends N2ExpressionValidator {

    @SuppressWarnings("restriction")
    @Override
    public void validate(AST statementAST, Token token) {

        if (statementAST == null) {
            return;
        }
        int statType = statementAST.getType();
        if (statType == JScriptTokenTypes.LITERAL_try) {
            String errorMessage =
                    Messages.N2StatementValidator_TryCatchStatNotSupported;
            addErrorMessage(token, errorMessage);
        } else if (statType == JScriptTokenTypes.LITERAL_switch) {
            String errorMessage =
                    Messages.N2StatementValidator_SwitchStatNotSupported;
            addErrorMessage(token, errorMessage);
        } else if (statType == JScriptTokenTypes.LITERAL_throw) {
            String errorMessage =
                    Messages.N2StatementValidator_ThrowStatNotSupported;
            addErrorMessage(token, errorMessage);
        } else if (statType == JScriptTokenTypes.LITERAL_return) {
            /*
             * Sid XPD-7996: Many mapping scenarios support both JavaScript and
             * DataMapper mapping grammars. User defined mapping scripts are
             * exactly equivalent in both EXCEPT that data mapper scripts demand
             * that last statement MUST be RETURN and mapping scripts for
             * JavaScripts mapping grammar do not support return.
             * 
             * In order to NOT have to have SEPARATE script contexts and
             * contributions for JavaScript and Datamapper mapping scripts FOR
             * EVERY mapping scenario that supports both JavaScript and
             * DataMapper, this 'must have return statement as last line'
             * strategy is now conditionalised. If the input ScriptInformation
             * element is contained within a ScriptDataMapper element then we
             * know that it is a DataMapper grammar scenario and can enforce
             * this rule.
             * 
             * This means that the N2JScriptFunctionValidationStrategy can be
             * contributed to a single mapping script context for both
             * JavaScript and DataMapper scenarios.
             */
            boolean isDataMapperScenario = isDataMapperMappingScript();

			/*
			 * Chaitanya ACE-8027 :- Validate return statements for PSL Functions.
			 * 
			 * As part of ACE-8027 we introduced the use of "return" in Process
			 * Script Library Functions.
			 * 
			 * Since now we support the use "return" statements in Process
			 * Script Library Functions, we do not want to raise the validation 
			 * to-do for "Return Statements not supported."
			 * 
			 * Hence, we only raise the "Return Statements not supported." to-do
			 * when it is not
			 * 1. DataMappingScript Editor.
			 * 2. Process Script Library Function Editior.
			 */
			boolean isProcessScriptLibraryFunctionScenario = isProcessScriptLibraryFunction();

			if (!isDataMapperScenario && !isProcessScriptLibraryFunctionScenario)
			{
                String errorMessage =
                        Messages.N2StatementValidator_ReturnStatNotSupported;
                addErrorMessage(token, errorMessage);
            }


			AST lastExpressionAST = statementAST;
			AST expr = statementAST.getFirstChild();
            if (expr != null && JScriptTokenTypes.EXPR == expr.getType()) {
                lastExpressionAST = expr.getFirstChild();
            }
			if (lastExpressionAST == null){
				return;
			}




			IValidateResult delegateEvaluateExpression = delegateEvaluateExpression(lastExpressionAST,
					token);

			/*
			 * Chaitanya ACE-8027: Validate return statements for PSL Functions.
			 * 
			 * Purpose: As part of ACE-8027, we introduced the use of "return" statements in Process Script Library
			 * Functions.
			 * 
			 * Description: We now support the use of "return" statements in Process Script Library Functions. We need
			 * to consider the following cases:
			 * 
			 * 1. We want to raise a validation to-do when the last statement for Process Script Library Functions with
			 * a NON-VOID RETURN type is NOT a "return" statement.
			 * 
			 * 2. We want to raise a validation to-do when the return type for Process Script Library Functions with a
			 * NON-VOID RETURN type is not compatible with the return type from the statement. NON-VOID RETURN type
			 * (i.e. Return Type Parameter from PSL) is not compatible with the return type from the statement (i.e.
			 * return "<any_statement_or_expression>").
			 * 
			 * 3. We want to raise a validation to-do when the last statement for Process Script Library Functions with
			 * a VOID RETURN type is a "return" statement.
			 */
			boolean isReturnTypeParameterOfTypeNonVoidReturnScenario = isReturnTypeParamaterOfTypeNonVoidReturn();
			if (isProcessScriptLibraryFunctionScenario)
			{
				if (isReturnTypeParameterOfTypeNonVoidReturnScenario)
				{
					// For Process Script Library Functions with NON Void Return must always end with return statement.
					if (statType != JScriptTokenTypes.LITERAL_return)
					{
						String errorMessage = Messages.N2FunctionStatementValidator_PSFunctionWithNonVoidReturnTypeParamter_LastStatementWithReturn;
						addErrorMessage(token, errorMessage);
					}
					else
					{
						// For Process Script Library Functions with NON Void Return having return statement , must have
						// return value compltiable with NON Void Return Parameter Type.
						if (delegateEvaluateExpression != null && delegateEvaluateExpression.getType() != null)
						{
							IScriptRelevantData rhsDataType = delegateEvaluateExpression.getType();

							IScriptRelevantData lhsReturnDataType = convertToScriptRelevantData(
									getProcessRelevantDataForReturnTypeParameter());

							evaluateAssignmentOperator(token, lhsReturnDataType, rhsDataType);
						}
					}
				}
				else
				{
					// For Process Script Library Functions with Void Return , return statements with value are not
					// supported.
					if (delegateEvaluateExpression != null && delegateEvaluateExpression.getType() != null)
					{
						IScriptRelevantData rhsDataType = delegateEvaluateExpression.getType();

						IScriptRelevantData returnDataType = createScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE,
								JsConsts.UNDEFINED_DATA_TYPE, false, null, null);

						if (rhsDataType.getType() != null && !rhsDataType.getType().equals(returnDataType.getType()))
						{
							String errorMessage = Messages.N2FunctionStatementValidator_PSFunctionWithVoidReturnTypeParamter_LastStatementWithReturnValue;
							addErrorMessage(token, errorMessage);
						}
					}

				}
			}

        }
    }


    /**
     * @return <code>true</code> if we are validating a user defined mapping
     *         script within a DataMapper mapping grammar scenario.
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
	 * Evaluates an assignment operator with the given operands.
	 * 
	 * This function to evalute weather the assignment of passed lhsDataType and rhsDataType is comptiable. If
	 * assignment is not comptiable this function raises/adds to-do related to assignment of incomptiable types.
	 * 
	 * @param token
	 *            The token representing the assignment operator.
	 * @param lhsDataType
	 *            The data type of the left-hand side operand.
	 * @param rhsDataType
	 *            The data type of the right-hand side operand.
	 * @return Returns the assignment operator type LHS side.
	 */
	protected IScriptRelevantData evaluateAssignmentOperator(Token currentLineToken, IScriptRelevantData lhsDataType,
			IScriptRelevantData rhsDataType)
	{
		IScriptRelevantData dataType = getAssignmentOperatorType(lhsDataType, rhsDataType);
		String lhsStrType = JsConsts.UNDEFINED_DATA_TYPE;
		if (lhsDataType != null && lhsDataType.getType() != null)
		{
			lhsStrType = lhsDataType.getType();
		}
		String rhsStrType = JsConsts.UNDEFINED_DATA_TYPE;
		if (rhsDataType != null && rhsDataType.getType() != null)
		{
			rhsStrType = rhsDataType.getType();
		}

		if (lhsStrType == null || rhsStrType == null || lhsStrType.equals(JsConsts.UNDEFINED_DATA_TYPE)
				|| rhsStrType.equals(JsConsts.UNDEFINED_DATA_TYPE))
		{
			String errorMessage = Messages.N2FunctionStatementValidator_PSFunctionWithNonVoidReturnTypeParamter_NoReturnType;
			addErrorMessage(currentLineToken, errorMessage);
		}
		else if (!isValidAssignment(lhsDataType, rhsDataType))
		{
			String errorMessage = null;
			if (haveSameMultiplicity(lhsDataType, rhsDataType))
			{
				errorMessage = Messages.N2FunctionStatementValidator_PSFunctionWithNonVoidReturnTypeParamter_AssignmentOperationBetween;
			}
			else
			{
				errorMessage = Messages.N2FunctionStatementValidator_PSFunctionWithNonVoidReturnTypeParamter_AssignmentMultiplicityOperationBetween;
			}
			List<String> additionalAttributes = new ArrayList<String>();
			additionalAttributes.add(parseTypeMessage(rhsDataType));
			additionalAttributes.add(parseTypeMessage(lhsDataType));
			addErrorMessage(currentLineToken, errorMessage, additionalAttributes);
		}
		return dataType;
	}

	/**
	 * Function that returns the assignment operator type.
	 * 
	 * If the passed 'lhsDataType' and 'rhsDataType' are not null , assignment operator type is passed 'lhsDataType'
	 * else we return 'UndefinedDataType'.
	 * 
	 * @param lhsDataType
	 *            The data type of the left-hand side operand.
	 * @param rhsDataType
	 *            The data type of the right-hand side operand.
	 * @return The resulting data type from the assignment operation
	 */
	protected IScriptRelevantData getAssignmentOperatorType(IScriptRelevantData lhsDataType,
			IScriptRelevantData rhsDataType)
	{
		IScriptRelevantData returnDataType = null;
		if (lhsDataType != null && lhsDataType.getType() != null && rhsDataType != null
				&& rhsDataType.getType() != null)
		{
			returnDataType = lhsDataType;
		}
		else
		{
			returnDataType = createScriptRelevantData(JsConsts.UNDEFINED_DATA_TYPE, JsConsts.UNDEFINED_DATA_TYPE, false,
					null, null);
		}
		return returnDataType;
	}

	/**
	 * Function to convert passed {@link ProcessRelevantData} into {@link IScriptRelevantData}.
	 * 
	 * @param processData
	 * @return Returns the {@link IScriptRelevantData} for passed {@link ProcessRelevantData}
	 */
	protected IScriptRelevantData convertToScriptRelevantData(ProcessRelevantData processData)
	{
		return CDSUtils.convertToScriptRelevantData(processData, getProject(),
				readContributedDefinitionReaders(getProcessDestinationList(getProcess())));
	}

	/**
	 * Function that returns the {@link IProject} from the current input selection context.
	 * 
	 * @return
	 */
	protected IProject getProject()
	{
		if (getInput(getInfoObject()) != null)
		{
			return WorkingCopyUtil.getProjectFor(getInput(getInfoObject()));
		}
		return null;
	}

	/**
	 * Function that returns the {@link Process} from the current input selection context.
	 * 
	 * @return
	 */
	protected Process getProcess()
	{
		return Xpdl2ModelUtil.getProcess(getInput(getInfoObject()));
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
	 * Retrieves a list of process destinations for the given process.
	 * 
	 * @param process
	 *            The process for which to retrieve the destination list.
	 * @return A list of strings representing the process destinations.
	 */
	protected List<String> getProcessDestinationList(Process process)
	{
		List<String> processDestList = new ArrayList<String>(DestinationUtil.getEnabledValidationDestinations(process));

		if (processDestList == null || processDestList.isEmpty())
		{
			processDestList = new ArrayList<String>();
			processDestList.add(ProcessJsConsts.JSCRIPT_DESTINATION);
		}
		return processDestList;
	}

	/**
	 * Reads contributed JavaScript class definition readers for the given list of process destinations.
	 * 
	 * @param processDestList
	 *            The list of process destinations for which to read contributed definition readers.
	 * @return A list of {@link JsClassDefinitionReader} JavaScript class definition readers.
	 */
	protected List<JsClassDefinitionReader> readContributedDefinitionReaders(List<String> processDestList)
	{
		List<JsClassDefinitionReader> jsClassProvider = Collections.EMPTY_LIST;
		try
		{
			jsClassProvider = ScriptGrammarContributionsUtil.INSTANCE.getJsClassDefinitionReader(processDestList,
					ProcessScriptContextConstants.PROCESS_SCRIPT_LIBRARY_FUNCTION, ProcessJsConsts.JAVASCRIPT_GRAMMAR,
					CDSUtils.N2PE_DESTINATION);
		}
		catch (CoreException e)
		{
			XpdResourcesPlugin.getDefault().getLogger().error(e);
		}
		return jsClassProvider;
	}

}
