/**
 * 
 */
package com.tibco.xpd.gmf.extensions.palette;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.internal.ui.palette.editparts.DrawerEditPart;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.ui.palette.PaletteViewerPreferences;
import org.eclipse.jface.preference.IPreferenceStore;

import com.tibco.xpd.gmf.extensions.XpdGmfExtensionsPlugin;

/**
 * Drawer whose content layout is forced to icons only unless user selects
 * details layout.
 * <p>
 * This acts as a replacement for standard GEF DrawerEditPart when editor is
 * using ButtonStackEditPartFactory.
 * <p>
 * It also saves it's last expansion and pinned states which is used when
 * created next time.
 * 
 * @author aallway
 */
public class ButtonStackDrawerReplacement extends DrawerEditPart {

    public ButtonStackDrawerReplacement(PaletteDrawer drawer) {
        super(drawer);
    }

    @Override
    protected int getLayoutSetting() {
        // Stick with icons unless details mode is on.
        int setting = super.getLayoutSetting();

        if (setting != PaletteViewerPreferences.LAYOUT_DETAILS) {
            setting = PaletteViewerPreferences.LAYOUT_ICONS;
        }

        return setting;
    }

}
