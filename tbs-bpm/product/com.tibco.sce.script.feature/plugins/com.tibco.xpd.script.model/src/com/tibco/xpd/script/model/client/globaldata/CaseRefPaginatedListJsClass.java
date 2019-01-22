/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.script.model.client.globaldata;

import org.eclipse.uml2.uml.Class;

/**
 * Case Ref Paginated List java script class for those cac or case ref methods
 * that require the return type as paginated list (instead of list)
 * 
 * @author bharge
 * @since 6 Nov 2013
 */
public class CaseRefPaginatedListJsClass extends CaseRefJsClass {

    private boolean canPaginate;

    /**
     * @return the canPaginate
     */
    public boolean isCanPaginate() {
        return canPaginate;
    }

    /**
     * Use this constructor if you want cac or case ref methods to have a
     * paginated list as the return type
     * 
     * @param caseClass
     */
    public CaseRefPaginatedListJsClass(Class caseClass) {

        super(caseClass);
        this.canPaginate = true;
    }

}
