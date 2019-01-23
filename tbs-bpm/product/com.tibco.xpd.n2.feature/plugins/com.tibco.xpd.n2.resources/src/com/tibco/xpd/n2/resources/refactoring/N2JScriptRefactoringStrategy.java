package com.tibco.xpd.n2.resources.refactoring;

import antlr.LLkParser;

import com.tibco.xpd.process.js.parser.internal.refactoring.ProcessJScriptRefactorInfoObject;
import com.tibco.xpd.script.parser.internal.expr.IRefactoringInfoObject;
import com.tibco.xpd.script.parser.internal.refactoring.IExpressionRefactor;
import com.tibco.xpd.script.parser.internal.refactoring.IExpressionRefactorFactory;
import com.tibco.xpd.script.parser.internal.refactoring.jscript.BaseJScriptRefactoringStrategy;
import com.tibco.xpd.script.parser.internal.validator.jscript.AntlrScriptParser;

public class N2JScriptRefactoringStrategy extends BaseJScriptRefactoringStrategy{

    
    private IExpressionRefactorFactory expressionRefactorFactory = null;

    private IExpressionRefactor identifierExpressionRefactor = null;    
    
    private IRefactoringInfoObject infoObject;

    public IExpressionRefactorFactory getExpresionRefactorFactory() {
        if(expressionRefactorFactory == null){
            expressionRefactorFactory = new N2JSExpressionRefactorFactory();
        }
        return expressionRefactorFactory;
    }

    @Override
    public IRefactoringInfoObject getInfoObject() {
        if(infoObject == null){
            infoObject = new ProcessJScriptRefactorInfoObject();
            infoObject.setDestinationName(getDestinationName());
            infoObject.setScriptType(getScriptType());
            infoObject.setExpresionRefactorFactory(getExpresionRefactorFactory());
            infoObject.setExpressionFactory(getExpresionFactory());
            LLkParser scriptParser = getScriptParser();
            infoObject.setScriptParser(new AntlrScriptParser(scriptParser));
            infoObject.setVarNameResolver(getVarNameResolver());
            infoObject.setDataTypeMapper(getDataTypeMapper());
            infoObject.setScriptRelevantDataFactory(getScriptRelevantDataFactory());
            infoObject.setDynamicTypeResolvers(getDynamicTypeResolvers());
            infoObject.setValidateGlobalFunctions(isValidateGlobalFunctions());
            infoObject.setClassDefinitionReaders(getClassDefinitionReaders());
            infoObject.setRefactoringInfo(getRefactoringInfo());
        }
        return infoObject;
    }

    @Override
    protected IExpressionRefactor getUndefinedVariableUseRefactor() {
        if (identifierExpressionRefactor == null) {
            identifierExpressionRefactor =
                    new N2JScriptIdentifierExpressionRefactor();
        }
        return identifierExpressionRefactor;
    }

}
