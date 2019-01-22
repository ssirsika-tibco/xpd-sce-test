/**
 * PaletteButtonStack.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.gmf.extensions.palette;

import org.eclipse.gef.palette.PaletteContainer;

/**
 * PaletteButtonStack Simply a virtual class so that our edit part factory can
 * tell difference between Standard Palette stack and our own special
 * SubTypePaletteStack control.
 * 
 * If we don't want to use special one then simply don't use our custom
 * XPDPaletteEditPartFactory then this will fall thru and be created as a normal
 * palette stack (drop down control).
 * 
 */
public class PaletteButtonStack extends PaletteContainer {

    /** Type identifier **/
    public static final String PALETTE_TYPE_BUTTONSTACK = "$PaletteButtonStack"; //$NON-NLS-1$

    public PaletteButtonStack() {
        super("", "", null, PALETTE_TYPE_BUTTONSTACK); //$NON-NLS-1$ //$NON-NLS-2$

    }

}
