package com.tibco.xpd.script.parser.validator.jscript;

import java.util.List;

import antlr.LLkParser;

import com.tibco.xpd.script.model.JsConsts;
import com.tibco.xpd.script.model.client.ITypeResolver;
import com.tibco.xpd.script.parser.internal.expr.IInfoObject;
import com.tibco.xpd.script.parser.internal.validator.AbstractValidationStrategy;
import com.tibco.xpd.script.parser.internal.validator.IValidator;
import com.tibco.xpd.script.parser.internal.validator.IVarNameResolver;
import com.tibco.xpd.script.parser.internal.validator.jscript.AntlrScriptParser;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptConditionalExprValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptExpressionValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptInfoObject;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptMethodDefinitionValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptNewExpressionValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptUndefinedVarUseValidator;
import com.tibco.xpd.script.parser.internal.validator.jscript.JScriptVariableDeclarationValidator;
import com.tibco.xpd.script.parser.validator.IExpressionValidator;

public class JScriptValidationStrategy extends AbstractValidationStrategy {

    private IExpressionValidator newExprValidator = null;

    private JScriptInfoObject infoObject = null;

    @Override
    protected IExpressionValidator getNewExpressionValidator() {
        if (newExprValidator == null) {
            newExprValidator = new JScriptNewExpressionValidator();
        }
        return newExprValidator;
    }

    private IExpressionValidator conditionalExprValidator = null;

    @Override
    protected IExpressionValidator getConditionalExpressionValidator() {
        if (conditionalExprValidator == null) {
            conditionalExprValidator = new JScriptConditionalExprValidator();
        }
        return conditionalExprValidator;
    }

    private IExpressionValidator methodCallValidator = null;

    @Override
    protected IExpressionValidator getMethodCallValidator() {
        if (methodCallValidator == null) {
            methodCallValidator = new JScriptMethodCallValidator();
        }
        return methodCallValidator;
    }

    private IExpressionValidator methodDefValidator = null;

    @Override
    protected IExpressionValidator getMethodDefinitionValidator() {
        if (methodDefValidator == null) {
            methodDefValidator = new JScriptMethodDefinitionValidator();
        }
        return methodDefValidator;
    }

    private IExpressionValidator undefinedVarUseValidator = null;

    @Override
    protected IExpressionValidator getUndefinedVariableUseValidator() {
        if (undefinedVarUseValidator == null) {
            undefinedVarUseValidator = new JScriptUndefinedVarUseValidator();
        }
        return undefinedVarUseValidator;
    }

    private IExpressionValidator varDeclaratorValidator = null;

    @Override
    protected IExpressionValidator getVariableDeclarationValidator() {
        if (varDeclaratorValidator == null) {
            varDeclaratorValidator = new JScriptVariableDeclarationValidator();
        }
        return varDeclaratorValidator;
    }

    IExpressionValidator expressionValidator = null;

    @Override
    protected IExpressionValidator getExpressionValidator() {
        if (expressionValidator == null) {
            expressionValidator = new JScriptExpressionValidator();
        }
        return expressionValidator;
    }

    @Override
    protected IExpressionValidator getASTTreeValidator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected IValidator getStatementValidator() {
        return null;
    }

    @Override
    public IInfoObject getInfoObject() {
        if (infoObject == null) {
            infoObject = new JScriptInfoObject();
            infoObject.setDestinationName(JsConsts.JSCRIPT_DESTINATION);
            infoObject.setScriptType(JsConsts.JAVASCRIPT_GRAMMAR);
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

    /**
     * @see com.tibco.xpd.script.parser.internal.validator.AbstractValidationStrategy#setDynamicTypeResolvers(java.util.List)
     * 
     * @param dynamicTypeResolvers
     */
    /*
     * XPD-4088: overridden the below method to make it available for Forms team
     * as the base class is internal
     */
    @Override
    public void setDynamicTypeResolvers(List<ITypeResolver> dynamicTypeResolvers) {
        super.setDynamicTypeResolvers(dynamicTypeResolvers);
    }

    /**
     * @see com.tibco.xpd.script.parser.internal.validator.AbstractValidationStrategy#setVarNameResolver(com.tibco.xpd.script.parser.internal.validator.IVarNameResolver)
     * 
     * @param varNameResolver
     */
    /*
     * XPD-4088: overridden the below method to make it available for Forms team
     * as the base class is internal
     */
    @Override
    public void setVarNameResolver(IVarNameResolver varNameResolver) {
        super.setVarNameResolver(varNameResolver);
    }
}
