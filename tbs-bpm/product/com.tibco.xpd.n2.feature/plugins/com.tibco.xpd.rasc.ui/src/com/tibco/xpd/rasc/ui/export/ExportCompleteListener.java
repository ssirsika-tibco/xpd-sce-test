/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.ui.export;

/**
 * Listens for completeion of Export, either success or failure.
 *
 * @author nwilson
 * @since 4 Apr 2019
 */
public interface ExportCompleteListener {
    void exportComplete();
}
