/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.staticcontent;

import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.xpdl2.BasicTypeType;

/**
 * Class for the data mapper content object that represents a static content
 * object (such as a JavaScript class property (or getter method) for use cases
 * where static JavaScript class is presented as content in a data mapper tree.)
 * <p>
 * Each of these is backed by a specially created xpdl2:DataField and
 * ConceptPath (never added to model) with the appropriate name, label, type and
 * read-only status etc. This is done in order to be able to re-use standard
 * Data Mapper / Mapping Rule Info providers for this data. Also so that type
 * compatibility checking is made easy; because the properties can then be
 * directly compared with other process data as if they were process data
 * themselves.
 * <p>
 * <b>Note</b> Implements {@link Comparable} and compares based on
 * {@link #getLabel()} (case insensitive).
 * 
 * @author aallway
 * @since 18 May 2015
 */
public class StaticContentDataMapperElement extends
        AbstractStaticContentDataMapperElement implements
        Comparable<StaticContentDataMapperElement> {

    private String label;

    /**
     * @param label
     *            the Display label.
     * @param pathModelName
     *            token name (used to store in data-mapping model itself.
     * @param type
     *            Equivalent Process Data type
     * @param readOnly
     *            Is read only or not.
     * @param javascriptText
     *            The text that should be used for the javaScrpt generation for
     *            this object.
     * @param parent
     *            The parent object of this object
     */
    public StaticContentDataMapperElement(String label, String pathModelName,
            String javascriptText, BasicTypeType type, boolean readOnly,
            ConceptPath parent) {
        super(pathModelName, javascriptText, type, readOnly, parent);
        this.label = label;
    }

    /**
     * @return the display label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     * 
     * @param o
     * @return
     */
    @Override
    public int compareTo(StaticContentDataMapperElement o) {
        return this.getLabel().compareToIgnoreCase(o.getLabel());
    };

    /**
     * @see com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath#toString()
     * 
     * @return
     */
    @Override
    public String toString() {
        String lbl = getLabel();

        return lbl != null ? lbl : super.toString();
    }

}
