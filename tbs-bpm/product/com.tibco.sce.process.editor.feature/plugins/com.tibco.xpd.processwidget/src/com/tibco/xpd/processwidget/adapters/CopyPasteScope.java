/**
 * CopyPasteScope.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.processwidget.adapters;

/**
 * Small class to carry the level of items in the copy list.
 * 
 * This doesn't mean that all items in the copy list are of the given type
 * just that the highest level of item in copy list of this type.
 * 
 * i.e. if copyScope = COPY_POOLS then the copy list of model elements will
 * contain Pools and activities etc.
 * 
 */
public class CopyPasteScope {
    public static final int COPY_NONE = 0;

    public static final int COPY_POOLS = 1;

    public static final int COPY_LANES = 2;

    public static final int COPY_ACTIVITIES_AND_ARTIFACTS = 3;

    // Contains only process relevant data, type declarations, participants etc.
    public static final int COPY_DATA_ONLY = 4;  
        
    private int copyScope = COPY_NONE;

    /**
     * @return the copyScope
     */
    public int getCopyScope() {
        return copyScope;
    }

    /**
     * @param copyScope
     *            the copyScope to set
     */
    public void setCopyScope(int copyScope) {
        this.copyScope = copyScope;
    }

}
