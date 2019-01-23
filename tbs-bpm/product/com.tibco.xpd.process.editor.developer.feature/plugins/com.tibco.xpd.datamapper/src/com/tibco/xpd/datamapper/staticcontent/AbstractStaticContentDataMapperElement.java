/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.datamapper.staticcontent;

import com.tibco.xpd.processeditor.xpdl2.properties.ConceptPath;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.BasicType;
import com.tibco.xpd.xpdl2.BasicTypeType;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Base class for the data mapper content object that represents a static
 * content object (such as a JavaScript class property (or getter method) for
 * use cases where static JavaScript class is presented as content in a data
 * mapper tree).
 * <p>
 * Each of these is backed by a specially created xpdl2:DataField and
 * ConceptPath (never added to model) with the appropriate name, label, type and
 * read-only status etc. This is done in order to be able to re-use standard
 * Data Mapper / Mapping Rule Info providers for this data. Also so that type
 * compatibility checking is made easy; because the properties can then be
 * directly compared with other process data as if they were process data
 * themselves.
 * 
 * @author Sid
 * @since 14 May 2015
 */
public abstract class AbstractStaticContentDataMapperElement extends
        ConceptPath {

    private String javascriptText;

    /**
     * 
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
    protected AbstractStaticContentDataMapperElement(String pathModelName,
            String javascriptText, BasicTypeType type, boolean readOnly,
            ConceptPath parent) {
        super(parent, createBackingDataField(pathModelName, type, readOnly),
                null);
        this.javascriptText = javascriptText;

    }

    /**
     * Create data field that backs the property (allowing some standard type
     * compatibility / info providers to be used).
     * 
     * @param pathModelName
     *            token name (used to store in data-mapping model itself.
     * @param type
     *            Equivalent Process Data type
     * @param readOnly
     *            Is read only or not.
     * 
     * @return The DataField designed to represent the given WorkManagerFactory
     *         JavaScript property
     */
    private static DataField createBackingDataField(String pathModelName,
            BasicTypeType type, boolean readOnly) {
        DataField dataField = Xpdl2Factory.eINSTANCE.createDataField();

        Xpdl2ModelUtil.setOtherAttribute(dataField,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName(),
                pathModelName);

        dataField.setName(pathModelName);

        BasicType dataType = Xpdl2Factory.eINSTANCE.createBasicType();
        dataType.setType(type);
        dataField.setDataType(dataType);

        dataField.setReadOnly(readOnly);

        return dataField;
    }

    /**
     * @return the name
     */
    @Override
    public String getName() {
        return getDataField().getName();
    }

    /**
     * @return the javascriptText
     */
    public String getJavascriptText() {
        return javascriptText;
    }

    /**
     * Get the data field that backs the property (allowing some standard type
     * compatibility / info providers to be used).
     */
    public DataField getDataField() {
        return (DataField) this.getItem();
    }

}