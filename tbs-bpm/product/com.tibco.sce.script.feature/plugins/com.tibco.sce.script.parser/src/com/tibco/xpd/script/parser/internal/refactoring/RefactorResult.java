/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.refactoring;

import java.util.List;

import com.tibco.xpd.script.model.client.IScriptRelevantData;
import com.tibco.xpd.script.parser.internal.expr.IExpr;
/**
 * 
 * Interface that provides the result of the expression refactor
 * 
 * @author mtorres
 */
public class RefactorResult {
    
    private IScriptRelevantData type;
    
    private IExpr expr;
    
    private List<RefactoringInfo> refatoringInfos;
    
    public IScriptRelevantData getType() {
        return type;
    }

    public void setType(IScriptRelevantData type) {
        this.type = type;
    }

    /**
     * Returns the expression that was refactored
     * 
     * @return the expression validated
     */
     public IExpr getExpr(){
         return expr;
     }
     
     /**
      * Sets the expression that is to be refactored
      * 
      * @param expr
      */
     public void setExpr(IExpr expr){
         this.expr = expr;
     }
     
     /**
      * Returns the list of refactoring infos
      * 
      * @return
      */
     public List<RefactoringInfo> getRefactoringInfos(){
         return refatoringInfos;
     }
     
     /**
      * Sets the list of refactoring infos
      * 
      * @param refatoringInfos
      */
     public void setRefactoringInfos(List<RefactoringInfo> refatoringInfos){
         this.refatoringInfos = refatoringInfos;
     }
}
