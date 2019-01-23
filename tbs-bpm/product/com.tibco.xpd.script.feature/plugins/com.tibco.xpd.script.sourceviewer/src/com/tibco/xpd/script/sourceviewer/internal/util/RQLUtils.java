package com.tibco.xpd.script.sourceviewer.internal.util;

import java.util.ArrayList;
import java.util.List;

public class RQLUtils {

    private static final String RQL_NAME_ATTRIBUTE = "name"; //$NON-NLS-1$

    private static final String RQL_TYPE_ATTRIBUTE = "type"; //$NON-NLS-1$

    private static final String RQL_ATTRIBUTE_ATTRIBUTE = "attribute"; //$NON-NLS-1$

    private static final String RQL_ID_ATTRIBUTE = "id"; //$NON-NLS-1$

    private static final String RQL_QUALIFIER_ATTRIBUTE = "qualifier"; //$NON-NLS-1$

    private static List<String> entityAttributeList;

    private static List<String> entityList;
    
    private static List<String> operatorCombinatorList;

    public static List<String> getEntityList() {
        if (entityList == null) {
            entityList = new ArrayList<String>();
            entityList.add("privilege");//$NON-NLS-1$
            entityList.add("capability");//$NON-NLS-1$
            entityList.add("organization");//$NON-NLS-1$
            entityList.add("orgunit");//$NON-NLS-1$
            entityList.add("position");//$NON-NLS-1$
            entityList.add("resource");//$NON-NLS-1$
            entityList.add("group");//$NON-NLS-1$
            entityList.add("location");//$NON-NLS-1$
            entityList.add("(");//$NON-NLS-1$
            entityList.add(")");//$NON-NLS-1$
        }
        return entityList;
    }    

    public static List<String> getOperatorCombinatorList() {
        if (operatorCombinatorList == null) {
            operatorCombinatorList = new ArrayList<String>();
            operatorCombinatorList.add("not");//$NON-NLS-1$
            operatorCombinatorList.add("union");//$NON-NLS-1$
            operatorCombinatorList.add("intersect");//$NON-NLS-1$
        }
        return operatorCombinatorList;
    }

    public static List<String> getEntityAttributeList() {
        if (entityAttributeList == null) {
            entityAttributeList = new ArrayList<String>();
            entityAttributeList.add(RQL_NAME_ATTRIBUTE);
            entityAttributeList.add(RQL_TYPE_ATTRIBUTE);
            entityAttributeList.add(RQL_ATTRIBUTE_ATTRIBUTE);
            entityAttributeList.add(RQL_ID_ATTRIBUTE);
            entityAttributeList.add(RQL_QUALIFIER_ATTRIBUTE);
        }
        return entityAttributeList;
    }
   
}
