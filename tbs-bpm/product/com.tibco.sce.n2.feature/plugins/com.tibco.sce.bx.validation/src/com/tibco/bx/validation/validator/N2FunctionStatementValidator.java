package com.tibco.bx.validation.validator;

import org.eclipse.emf.ecore.EObject;

import antlr.Token;
import antlr.collections.AST;

import com.tibco.bx.validation.internal.Messages;
import com.tibco.xpd.script.parser.antlr.JScriptTokenTypes;
import com.tibco.xpd.xpdExtension.ScriptDataMapper;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

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

            if (!isDataMapperScenario) {
                String errorMessage =
                        Messages.N2StatementValidator_ReturnStatNotSupported;
                addErrorMessage(token, errorMessage);
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

}
