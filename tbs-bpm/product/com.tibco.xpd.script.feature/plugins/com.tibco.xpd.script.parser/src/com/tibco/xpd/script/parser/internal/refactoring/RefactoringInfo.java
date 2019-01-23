/*
 * Copyright (c) TIBCO Software Inc 2004, 2010. All rights reserved.
 */
package com.tibco.xpd.script.parser.internal.refactoring;

import org.eclipse.emf.ecore.EObject;

/**
 * Information of the refactoring process
 * 
 * @author mtorres
 * 
 */
public class RefactoringInfo {

    private EObject refactoredElement;

    private String oldValue;

    private String newValue;

    public RefactoringInfo(EObject refactoredElement, String oldValue, String newValue) {
        this.refactoredElement = refactoredElement;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }
    
    public EObject getRefactoredElement() {
        return refactoredElement;
    }
    
    public String getOldValue() {
        return oldValue;
    }
    
    public String getNewValue() {
        return newValue;
    }

}
