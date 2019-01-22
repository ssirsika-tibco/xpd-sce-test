/*
 * Copyright (c) TIBCO Software Inc 2004, 2009. All rights reserved.
 */
package com.tibco.xpd.script.model.client;

import org.eclipse.swt.graphics.Image;

/**
 * This interface provides the content of the data presented in the respective
 * models. For example, in XPDL, the process relevant data i.e. Formal
 * Parameters, and Data Fields are represented as these, so that the script
 * editor is agnostic of the model specific details
 * 
 * @author rsomayaj
 * 
 */
public interface IScriptRelevantData {

    void setType(String type);

    String getType();

    void setName(String name);

    String getName();

    void setId(String id);

    String getId();

    /*
     * XPD-7277 Sid removed get/setDescription because nothing really used them.
     * additionalInfo is the thing that's used for content assist popup help, so
     * makes more sense to use that.
     */

    void setIcon(Image icon);

    Image getIcon();

    void setIsArray(boolean isArray);

    boolean isArray();

    void setAdditionalInfo(String additionalInfo);

    String getAdditionalInfo();

    /**
     * Whether this ScriptRelevantData should be treated as a static (class).
     * <p>
     * Normally IScriptRelevantData objects represent data and classes that are
     * non-static.
     * <p>
     * However, we now sometimes need to wrap UML Definition Readers that load
     * JS Classes from UML models in ScriptRelevantDataProviders (in order to
     * conditionalise their content based on input object configuration).
     * <p>
     * Overriding this method allows those wrapped UML Js CLasses to still
     * appear to be static. See {@link UMLScriptClassWrapperRelevantData}
     */
    boolean isStatic();
}
