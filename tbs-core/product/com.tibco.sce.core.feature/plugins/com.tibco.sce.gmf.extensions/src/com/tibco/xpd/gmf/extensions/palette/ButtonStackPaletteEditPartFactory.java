/**
 * ButtonStackPaletteEditPartFactory.java
 *
 * 
 *
 * @author aallway
 * @copyright TIBCO Software Inc. (c) 2006
 */
package com.tibco.xpd.gmf.extensions.palette;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteStack;

/**
 * ButtonStackPaletteEditPartFactory
 * <p>
 * Delegate for standard Palette Edit Part factory. This allows creation of our
 * own 'special' Button Stacker palette drawer control
 * </p>
 */
public class ButtonStackPaletteEditPartFactory implements EditPartFactory {

    /**
     * Only use button stacker tool for Tool Palette model entries of class
     * PaletteButtonStack or PaletteButtonDrawer. You will have to change
     * PaletteFactory to create PaletteButtonStack/PaletteButtonDrawer objects
     * for the specific drawers/drop-downs That you wish to be button stacker
     * tools.
     */
    public final static int USE_BTNSTACK_FACTORY_SPECIFIED = 0x0000;

    /**
     * Automatically use button stacker for all palette drop-down tools
     * (PaletteStack objects).
     */
    public final static int USE_BTNSTACK_ALL_DROPDOWNS = 0x0001;

    /**
     * Automatcially use button stacker for all palette drawer tools.
     */
    public final static int USE_BTNSTACK_ALL_DRAWERS = 0x0002;

    private EditPartFactory delegateFactory; // Delegated factory.

    private int useBtnStackFlags = USE_BTNSTACK_FACTORY_SPECIFIED;

    private String basePreferenceId = null;

    /**
     * Create edit part factory that understands palette button stack model and
     * delegates palette entries that it does not understand
     * 
     * @param delegateFactory
     *            Factory to delegate to for non drawer/stack entries.
     * @param basePreferenceId
     *            Base id for (i.e. of editor) for btn stacker pin state
     *            preference store items.
     * @param useBtnStackFlags
     *            Whether to replace all Palette Drawer and palette Stack (drop
     *            down) entries with palette btn stacker OR just palette entries
     *            that are of class PaletteButtonStack
     */
    public ButtonStackPaletteEditPartFactory(EditPartFactory delegateFactory,
            String basePreferenceId, int useBtnStackFlags) {
        this.delegateFactory = delegateFactory;

        this.useBtnStackFlags = useBtnStackFlags;
        this.basePreferenceId = basePreferenceId;
    }

    /**
     * Overrides delegate factory createEditPart to allow construction of
     * SubTypePaletteStack
     * 
     * @param parentEditPart
     * @param model
     * @return
     * @see org.eclipse.gef.ui.palette.PaletteEditPartFactory#createEditPart(org.eclipse.gef.EditPart,
     *      java.lang.Object)
     */
    @Override
    public EditPart createEditPart(EditPart parentEditPart, Object model) {

        // Make sure that all model entries have Id's (else the customisation
        // stuff will not work).
        if (model instanceof PaletteEntry) {
            PaletteEntry pe = (PaletteEntry) model;
            String prefId = pe.getId();

            if (prefId == null || prefId.length() == 0) {
                prefId = createPaletteEntryId(pe);

                pe.setId(prefId);
            }
        }

        // Use button stacker tool according to initial setup.
        boolean bUseBtnStackerTool = false;

        if ((useBtnStackFlags & USE_BTNSTACK_ALL_DROPDOWNS) != 0
                && (model instanceof PaletteStack)) {
            bUseBtnStackerTool = true;

        } else if ((useBtnStackFlags & USE_BTNSTACK_ALL_DRAWERS) != 0
                && (model instanceof PaletteDrawer)) {
            bUseBtnStackerTool = true;

        } else if ((model instanceof PaletteButtonStack)
                || (model instanceof PaletteButtonDrawer)) {
            bUseBtnStackerTool = true;
        }

        if (bUseBtnStackerTool) {
            if (model instanceof PaletteDrawer) {
                // trial of ganymede gef changes for tool palette
                ButtonStackDrawerReplacement drawer =
                        new ButtonStackDrawerReplacement((PaletteDrawer) model);
                return drawer;

            } else {
                // Old europa gef button stacker
                return deprecated_createGEF30ButtonStackEditPart(model);
            }

        }

        // Otherwise delegate to std palette edit part factory.
        return delegateFactory.createEditPart(parentEditPart, model);
    }

    private EditPart deprecated_createGEF30ButtonStackEditPart(Object model) {
        // Old europa gef

        PaletteContainer pC = ((PaletteContainer) model);

        return new ButtonStackPaletteEditPart(pC, pC.getId());
    }

    private String createPaletteEntryId(PaletteEntry pe) {
        String prefId = ""; //$NON-NLS-1$

        PaletteContainer parent = pe.getParent();
        PaletteEntry currEntry = pe;
        while (parent != null) {
            // Find this entry in parent...
            int idx = parent.getChildren().indexOf(currEntry);

            /** this needed to match GefButtonStackPaletteViewerProvider */
            prefId += "." + (idx + 1); //$NON-NLS-1$

            currEntry = parent;
            parent = currEntry.getParent();
        }

        return basePreferenceId + prefId;

    }

}
