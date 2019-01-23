package com.tibco.xpd.script.ui.internal.extension;

import java.util.ArrayList;
import java.util.List;

public class CertifiedScriptGramm {

    private final List<String> regiteredIds;

    CertifiedScriptGramm() {
        regiteredIds = new ArrayList<String>();
        regiteredIds.add("JavaScript"); //$NON-NLS-1$
        regiteredIds.add("Text"); //$NON-NLS-1$
        regiteredIds.add("ConstantPeriod"); //$NON-NLS-1$
        regiteredIds.add("XPath"); //$NON-NLS-1$
        regiteredIds.add("XSLT"); //$NON-NLS-1$
        regiteredIds.add("Java"); //$NON-NLS-1$
        regiteredIds.add("Unspecified"); //$NON-NLS-1$
        regiteredIds.add("Transform"); //$NON-NLS-1$
        regiteredIds.add("XSLT_Transform"); //$NON-NLS-1$
        regiteredIds.add("RQL"); //$NON-NLS-1$

        /*
         * SID SIA-1#; Have to certify the iProcessScript grammar to allow users
         * to view iProcessScript even though they aren't allowed to edit it.
         */
        regiteredIds.add("iProcessScript"); //$NON-NLS-1$

        // Process Data Mapper Script Grammar
        regiteredIds.add("DataMapper"); //$NON-NLS-1$

    }

    public boolean isCertified(String grammarId) {
        boolean result = regiteredIds.contains(grammarId);
        return result;
    }

}
