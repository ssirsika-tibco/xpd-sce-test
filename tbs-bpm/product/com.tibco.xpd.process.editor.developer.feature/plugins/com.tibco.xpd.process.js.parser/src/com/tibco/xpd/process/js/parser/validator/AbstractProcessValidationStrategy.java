/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.process.js.parser.validator;

import antlr.LLkParser;
import antlr.Token;
import antlr.collections.AST;

import com.tibco.xpd.process.js.parser.internal.validator.ProcessJScriptInfoObject;
import com.tibco.xpd.script.parser.internal.expr.IInfoObject;
import com.tibco.xpd.script.parser.internal.validator.AbstractValidationStrategy;
import com.tibco.xpd.script.parser.internal.validator.IValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.AbstractExpressionValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.AntlrScriptParser;
import com.tibco.xpd.script.parser.validator.IExpressionValidator;

/**
 * 
 * <p>
 * <i>Created: 3 Oct 2007</i>
 * </p>
 * 
 * @author Kamlesh Upadhyaya
 * 
 */
public abstract class AbstractProcessValidationStrategy extends
        AbstractValidationStrategy implements IProcessValidationStrategy {

    private String destinationName = null;

    private String scriptType = null;

    private String expectedResultDataType = null;

    private String scriptSubContext = null;

    private IInfoObject infoObject = null;

    @Override
    public void validateStatement(AST statementAST, Token token) {

        // AST myAST = (AST) passedObject;
        IValidator statementValidator = getStatementValidator();
        if (statementValidator != null) {
            /*
             * Sid XPD-7996: Add the relavent things like input object and so on
             * to normal statements as well as the other 'sopecial cases' else
             * cannot distinguish certain rules.
             */
            if (statementValidator instanceof AbstractExpressionValidator) {
                initialiseExpressionValidator((IExpressionValidator) statementValidator);
            }

            initialiseValidator(statementValidator);
            statementValidator.validate(statementAST, token);
            reportErrorsAndWarnings(statementValidator);
        }
    }

    @Override
    protected void initialiseExpressionValidator(
            IExpressionValidator expressionValidator) {
        if (expressionValidator != null) {
            initialiseValidator(expressionValidator);
            expressionValidator.setScriptParser(getScriptParser());
            expressionValidator.setVarNameResolver(getVarNameResolver());
            expressionValidator.setInfoObject(getInfoObject());
        }
    }

    protected void initialiseValidator(IValidator validator) {
        if (validator != null && validator instanceof IProcessValidator) {
            IProcessValidator pValidator = (IProcessValidator) validator;
            pValidator.setProcessDestination(getDestinationName());
            pValidator.setScriptType(getScriptType());
        }
    }

    @Override
    public String getDestinationName() {
        return destinationName;
    }

    @Override
    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    @Override
    public String getScriptType() {
        return scriptType;
    }

    @Override
    public void setScriptType(String scriptType) {
        this.scriptType = scriptType;
    }

    @Override
    public void setExpectedResultantDataType(String dataType) {
        this.expectedResultDataType = dataType;
    }

    public String getExpectedResultantDataType() {
        return this.expectedResultDataType;
    }

    public String getScriptSubContext() {
        return scriptSubContext;
    }

    @Override
    public void setScriptSubContext(String scriptSubContext) {
        this.scriptSubContext = scriptSubContext;
    }

    @Override
    public IInfoObject getInfoObject() {
        if (infoObject == null) {
            infoObject = new ProcessJScriptInfoObject();
            infoObject.setDestinationName(getDestinationName());
            infoObject.setScriptType(getScriptType());
            infoObject
                    .setExpresionValidatorFactory(getExpresionValidatorFactory());
            infoObject.setExpressionFactory(getExpresionFactory());

            infoObject.setVarNameResolver(getVarNameResolver());
            infoObject.setDataTypeMapper(getDataTypeMapper());
            infoObject
                    .setScriptRelevantDataFactory(getScriptRelevantDataFactory());
            infoObject.setDynamicTypeResolvers(getDynamicTypeResolvers());
            infoObject.setValidateGlobalFunctions(isValidateGlobalFunctions());
            infoObject.setClassDefinitionReaders(getClassDefinitionReaders());
        }
        /*
         * XPD-5565: if local variables are used in the script, local variables
         * map in the symbol table (in script parser) gets updated. but info
         * object was referring to the older version of script parser and was
         * not having the latest local variable map. this was causing problems
         * in content assist. so trying to get the script parser before
         * returning the info object so we have access to latest local variable
         * map.
         * 
         * PLEASE NOTE THAT this is not a global data specific issue. It has
         * always been there
         * 
         * For eg. if you type the following script in a script task
         * 
         * var list1 = ScriptUtil.copyAll(bomTypeArr);
         * 
         * var iter1 = list1.listIterator();
         * 
         * var x = iter1.next();
         * 
         * x. -> this used to fail to list the content assist.
         */
        LLkParser scriptParser = getScriptParser();
        infoObject.setScriptParser(new AntlrScriptParser(scriptParser));
        return infoObject;
    }

}
