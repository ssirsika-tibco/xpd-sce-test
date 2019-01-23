/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     
 *******************************************************************************/
package com.tibco.xpd.script.sourceviewer.internal.preferences;

/**
 * Preference keys for Common UI
 * 
 */
public abstract class AbstractScriptCommonUIPreferenceNames {
    /**
     * 
     */
    public AbstractScriptCommonUIPreferenceNames() {

    }

    /**
     * A named preference that controls if code assist gets auto activated.
     * <p>
     * Value is of type <code>Boolean</code>.
     * </p>
     */
    public abstract String getAutoProposeKey();

    /**
     * A named preference that holds the characters that auto activate code
     * assist.
     * <p>
     * Value is of type <code>String</code>. All characters that trigger auto
     * code assist.
     * </p>
     */
    public abstract String getAutoProposeCodeKey();

    /**
     * The character used for indentation.
     * <p>
     * Value is of type <code>String</code>.<br />
     * Possible values: {TAB, SPACE}
     * </p>
     */
    public abstract String getIndentationChar();

    /**
     * The number of #INDENTATION_CHAR for 1 indentation.
     * <p>
     * Value is of type <code>Integer</code>.
     * </p>
     */
    public abstract String getIndentationSize();
}
