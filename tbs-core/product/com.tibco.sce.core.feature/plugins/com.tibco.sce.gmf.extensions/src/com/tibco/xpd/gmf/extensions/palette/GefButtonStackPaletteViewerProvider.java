/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.gmf.extensions.palette;

import org.eclipse.gef.EditDomain;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gmf.runtime.gef.ui.palette.customize.PaletteCustomizerEx;
import org.eclipse.gmf.runtime.gef.ui.palette.customize.PaletteViewerEx;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Composite;

/**
 * This class enables the use of the TIBCO palette button stack tool.
 * <p>
 * This palette viewer provider creates a PaletteViewer that uses the
 * ButtonStackPaletteEditPartFactory class instead of the standard GEF edit part
 * factory.
 * <p>
 * This edit part factory understands the concept of button stack drawers and
 * can either use them for specific palette entries or for all PaletteDrawer /
 * PaletteStack palette entries.
 * <p>
 * On construction you can set whether...
 * <li>All drop-down type palette entries (PaletteStack) are converted to button
 * stacker tools. AND/OR</li>
 * <li>All drawer type palette entries (PaletteDrawer) are converted to button
 * stacker tools. OR</li>
 * <li>Specify that no auto conversion should be done.</li>
 * <br>
 * .
 * <p>
 * If you elect not to auto convert drawers and/or drop downs, you can if you
 * wish, <i>specifically</i> specify particular drawers / drop-downs to be
 * converted by changing the PaletteFactory to create PaletteButtonStack objects
 * instead of the standard PaletteStack or PaletteDrawer objects that are
 * usually used.</li> <br>
 * .
 * <p>
 * See constructor for more details. <br>
 * .
 * </p>
 * 
 * @author aallway
 * 
 */
public class GefButtonStackPaletteViewerProvider extends PaletteViewerProvider {

    private int useBtnStackFlags =
            ButtonStackPaletteEditPartFactory.USE_BTNSTACK_ALL_DROPDOWNS;

    private String basePreferenceId = null;

    private boolean userCustomisable = false;

    private IPreferenceStore preferenceStore = null; // Optional!

    private PaletteRoot paletteRoot = null; // Optional!

    /**
     * Copy of GMF (1.0.3) PaletteViewerProvider that creates a palette viewer
     * that understands TIBCO button stacker type palette entries (or replaces
     * all drawer/drop-down entries with button stacker style drawers).
     * 
     * @param graphicalViewerDomain
     * @param basePreferenceId
     *            Base id for (i.e. of editor) for btn stacker pin state
     *            preference store items.
     * @param useBtnStackFlags
     *            Combination of
     *            ButtonStackPaletteEditPartFactory.USE_BTNSTACK_xxx flags
     *            specifying which tool palette drawer/drop-down entries to use
     *            button stacker tool for.
     */
    public GefButtonStackPaletteViewerProvider(
            EditDomain graphicalViewerDomain, String basePreferenceId,
            int useBtnStackFlags) {
        super(graphicalViewerDomain);

        this.useBtnStackFlags = useBtnStackFlags;
        this.basePreferenceId = basePreferenceId;
    }

    /**
     * Setup this provider to allow the palette viewers it creates to be user
     * customisable.
     * <p>
     * In order to function correctly this method MUST be called before the
     * createPaletteViewer() method is called.
     * 
     * @param preferenceStore
     * @param paletteRoot
     * @param setPermissions
     *            true if you wish this method to set permissions according to
     *            the next param - use fale if you have already used the
     *            setUserModificationPermissions() method for each palette model
     *            entry yourself.
     * @param permission
     *            see {@link PaletteEntry} PERMISSION_xxx values
     */
    public void allowUserCustomisation(IPreferenceStore preferenceStore,
            PaletteRoot paletteRoot, boolean setPermissions, int permission) {
        this.paletteRoot = paletteRoot;
        this.preferenceStore = preferenceStore;

        if (paletteRoot != null && preferenceStore != null) {
            // We must ensure that all palette model entries have Id's - this is
            // required before any of the controls are created for them in order
            // to
            // have preserved customisations restored when palette is created.
            // We may also need to set permissions if the user has asked us to.
            recursiveSetPalettePermissionsAndIds(basePreferenceId,
                    paletteRoot,
                    setPermissions,
                    permission);

            userCustomisable = true;

        } else {
            userCustomisable = false;
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gef.ui.palette.PaletteViewerProvider#createPaletteViewer(
     * org.eclipse.swt.widgets.Composite)
     */
    @Override
    public PaletteViewer createPaletteViewer(Composite parent) {

        PaletteViewer pViewer = new PaletteViewerEx();

        PaletteCustomizerEx customiser = null;

        if (userCustomisable) {
            customiser = new PaletteCustomizerEx(preferenceStore);
            customiser.applyCustomizationsToPalette(paletteRoot);
            pViewer.setCustomizer(customiser);
        }

        /* Extra non-standard stuff. */
        pViewer.setEditPartFactory(new ButtonStackPaletteEditPartFactory(
                pViewer.getEditPartFactory(), basePreferenceId,
                useBtnStackFlags));

        /* Copy of subclass */
        pViewer.createControl(parent);
        configurePaletteViewer(pViewer);
        hookPaletteViewer(pViewer);

        return pViewer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.gef.ui.palette.PaletteViewerProvider#configurePaletteViewer
     * (org.eclipse.gef.ui.palette.PaletteViewer)
     */
    @Override
    protected void configurePaletteViewer(PaletteViewer viewer) {
        super.configurePaletteViewer(viewer);

    }

    /**
     * Recursively ensure that all palette model entries have id's and
     * (optionally) the requested permissions.
     * 
     * @param parentPrefId
     * @param paletteContainer
     * @param setPermissions
     * @param permission
     */
    private void recursiveSetPalettePermissionsAndIds(String parentPrefId,
            PaletteContainer paletteContainer, boolean setPermissions,
            int permission) {
        int i = 0;
        for (Object child : paletteContainer.getChildren()) {
            i++;

            if (child instanceof PaletteEntry) {
                PaletteEntry paletteEntry = (PaletteEntry) child;

                if (setPermissions) {
                    paletteEntry.setUserModificationPermission(permission);
                }

                String finalId = null;

                String id = paletteEntry.getId();
                if (id == null || id.length() == 0) {
                    finalId = parentPrefId + "." + i; //$NON-NLS-1$
                    paletteEntry.setId(finalId);

                } else {
                    finalId = id;
                }

                if (paletteEntry instanceof PaletteContainer) {
                    recursiveSetPalettePermissionsAndIds(finalId,
                            (PaletteContainer) paletteEntry,
                            setPermissions,
                            permission);
                }
            }

        }

        return;
    }
}
