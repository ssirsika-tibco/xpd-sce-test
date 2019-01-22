/**
 * Copyright (c) TIBCO Software Inc 2004-2009. All rights reserved.
 */

package com.tibco.xpd.processwidget.progression.model;

/**
 * Class for creating {@link ProgressionModel} elements that are appropriate for
 * give process diagram object model elements.
 * <p>
 * Because {@link ProgressionModel} is reasonably open for extension, it is not
 * anticipated that it will be necessary for this to be extended.
 * <p>
 * For detailed information of the use of the diagram progression model see
 * {@link ProgressionModel}.
 * 
 * @author aallway
 * @since 3.3 (3 Nov 2009)
 */
public class ProgressionModelFactory {

    /**
     * Create an appropriate object progression model for the given model object
     * that represents an object/connection in a process.
     * 
     * @param model
     * @return {@link ProgressionModel}
     */
    public ProgressionModel createObjectProgressionModel(
            Object processObjectModel) {

        return new ProgressionModel(processObjectModel);
    }

}
