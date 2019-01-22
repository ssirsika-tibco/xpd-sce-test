package com.tibco.xpd.studioiprocess.scriptconverter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * String line list that indents lines according to a settable nesting level.
 * 
 * 
 * @author aallway
 * @since 20 May 2011
 */
public class NestedLineList extends ArrayList<String> {

    private int identLevel;

    private int indentSize;

    private String indentStr = ""; //$NON-NLS-1$

    /**
     * @param indentSize
     *            Number of spaces per indent level
     */
    public NestedLineList(int indentSize) {
        super();
        this.indentSize = indentSize;
    }

    /**
     * @param identLevel
     *            the identLevel to set
     */
    public void setIdentLevel(int identLevel) {
        this.identLevel = identLevel > 0 ? identLevel : 0;

        StringBuffer is = new StringBuffer();

        for (int i = 0; i < (identLevel * indentSize); i++) {
            is.append(' ');
        }

        indentStr = is.toString();
    }

    /**
     * @return the identLevel
     */
    public int getIdentLevel() {
        return identLevel;
    }

    /**
     * @see java.util.ArrayList#add(java.lang.Object)
     * 
     * @param s
     * @return
     */
    @Override
    public boolean add(String s) {
        return super.add(indentStr + s);
    }

    /**
     * @see java.util.ArrayList#add(int, java.lang.Object)
     * 
     * @param index
     * @param s
     */
    @Override
    public void add(int index, String s) {
        super.add(index, indentStr + s);
    }

    /**
     * @see java.util.ArrayList#addAll(java.util.Collection)
     * 
     * @param c
     * @return
     */
    @Override
    public boolean addAll(Collection<? extends String> c) {
        for (String string : c) {
            add(string);
        }
        return !c.isEmpty();
    }

    /**
     * @see java.util.ArrayList#addAll(int, java.util.Collection)
     * 
     * @param index
     * @param c
     * @return
     */
    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        for (String string : c) {
            add(index, string);
            index++;
        }
        return !c.isEmpty();
    }

    /**
     * @see java.util.AbstractCollection#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        StringBuffer buff = new StringBuffer();

        boolean first = true;

        for (String line : this) {
            if (!first) {
                buff.append("\n"); //$NON-NLS-1$
            } else {
                first = false;
            }

            buff.append(line);
        }

        return buff.toString();
    }
}