/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */

package com.tibco.xpd.gmf.extensions.palette;

import org.eclipse.gef.EditDomain;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.Tool;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gmf.runtime.diagram.ui.internal.parts.PaletteToolTransferDragSourceListener;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditorWithFlyOutPalette;
import org.eclipse.gmf.runtime.diagram.ui.tools.ConnectionCreationTool;
import org.eclipse.gmf.runtime.diagram.ui.tools.CreationTool;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

/**
 * This class enables the use of the TIBCO palette button stack tool for GMF
 * diagrams.
 * <p>
 * This palette viewer provider creates a PaletteViewer that uses the
 * ButtonStackPaletteEditPartFactory class instead of the standard GEF edit part
 * factory.
 * <p>
 * This edit part factory understands the concept of button stack drawers and
 * can either use them for specific palette entries or for all PaletteDrawer
 * and/or PaletteStack (drop-down) palette entries.
 * <p>
 * In GMF the types of palette entry are fixed because the PaletteViewerProvider
 * provided by the GMF
 * DiagramEditorWithFlyoutPalette.createPaletteViewerProvider() method uses the
 * standard GEF PaletteEditPartFactory.
 * <p>
 * Therefore this class is a copy of the GMF PaletteViewerProvider used by GMF (@
 * v1.0.3) plus our own button stack capability.
 * <p>
 * On construction you can set whether...
 * <li>All drop-down type palette entries (PaletteStack) are converted to
 * button stacker tools. AND/OR</li>
 * <li>All drawer type palette entries (PaletteDrawer) are converted to button
 * stacker tools. OR</li>
 * <li>Specify that no auto conversion should be done.</li>
 * <br/> .
 * <p>
 * If you elect not to auto convert drawers and/or drop downs, you can if you
 * wish, <i>specifically</i> specify particular drawers / drop-downs to be
 * converted by changing the PaletteFactory to create PaletteButtonStack objects
 * instead of the standard PaletteStack or PaletteDrawer objects that are
 * usually used.</li>
 * <br/> .
 * <p>
 * <b>To use this in your GMF diagram editor you must ...</b>
 * <li>In the GMF-generated <MyDiagram>DiagramEditor.java class, override the
 * createPaletteViewerProvider() method with the following code...</li>
 * <br/> <code>
 * <br/> protected PaletteViewerProvider createPaletteViewerProvider() {
 * <br/>     getEditDomain().setPaletteRoot(createPaletteRoot(null));
 * <br/>
 * <br/>     return new GmfButtonStackPaletteViewerProvider(getEditDomain(), this, ID, ButtonStackEditPartFactory.USE_BTNSTACK_ALL_DROPDOWNS); 
 * <br/> }
 * </code><br/>
 * <i>This will automatically convert Drop-Down tool entries to button stackers</i>
 * <br/> .
 * <p>
 * <b>If you wish override specific drawers / drop-downs in palette...</b>
 * <li>Change the PaletteFactory to create PaletteButtonStack objects in place
 * of specific PaletteDrawer/PaletteStack objects...</li><br>
 * <code> PaletteContainer paletteContainer = new PaletteButtonStack(); </code>
 * <li><b>Not forgetting to set the method's</b> generated NOT <b>tag.</b>
 * </p>
 * <br> .
 * @author aallway
 * 
 */
public class GmfButtonStackPaletteViewerProvider extends
        GefButtonStackPaletteViewerProvider {

    private KeyHandler paletteKeyHandler = null;

    private MouseListener paletteMouseListener = null;

    private DiagramEditorWithFlyOutPalette diagramEditor = null;

    /**
     * Copy of GMF (1.0.3) PaletteViewerProvider that creates a palette viewer
     * that understands TIBCO button stacker type palette entries (or replaces
     * all drawer/drop-down entries with button stacker style drawers).
     * 
     * @param graphicalViewerDomain
     * @param diagramEditor
     *            The GMF generated <MyDiagram>DiagramEditor.java object.
     * @param basePreferenceId
     *            Base id for (i.e. of editor) for btn stacker pin state
     *            preference store items.
     * @param useBtnStackFlags
     *            Combination of
     *            ButtonStackPaletteEditPartFactory.USE_BTNSTACK_xxx flags
     *            specifying which tool palette drawer/drop-down entries to use
     *            button stacker tool for.
     * 
     */
    public GmfButtonStackPaletteViewerProvider(
            EditDomain graphicalViewerDomain,
            DiagramEditorWithFlyOutPalette diagramEditor,
            String basePreferenceId, int useBtnStackFlags) {
        super(graphicalViewerDomain, basePreferenceId, useBtnStackFlags);

        this.diagramEditor = diagramEditor;
    }

    /***************************************************************************
     * THINGS BELOW SHAMELESSLY EXTRACTED FROM GMF 1.0.3
     * DiagramEditorWithFlyOutPalette.createPaletteViewerProvider
     **************************************************************************/

    /**
     * Override to provide the additional behavior for the tools. Will intialize
     * with a PaletteEditPartFactory that has a TrackDragger that understand how
     * to handle the mouseDoubleClick event for shape creation tools. Also will
     * initialize the palette with a defaultTool that is the SelectToolEx that
     * undestands how to handle the enter key which will result in the creation
     * of the shape also.
     */
    protected void configurePaletteViewer(PaletteViewer viewer) {
        super.configurePaletteViewer(viewer);

        viewer.getKeyHandler().setParent(getPaletteKeyHandler());
        viewer.getControl().addMouseListener(getPaletteMouseListener());

        // Add a transfer drag target listener that is supported on
        // palette template entries whose template is a creation tool.
        // This will enable drag and drop of the palette shape creation
        // tools.
        viewer.addDragSourceListener(new PaletteToolTransferDragSourceListener(
                viewer));

    }

    /**
     * @return Palette Mouse listener for the palette
     */
    private MouseListener getPaletteMouseListener() {

        if (paletteMouseListener == null) {

            paletteMouseListener = new MouseListener() {

                /**
                 * Flag to indicate that the current active tool should be
                 * cleared after a mouse double-click event.
                 */
                private boolean clearActiveTool = false;

                /**
                 * Override to support double-clicking a palette tool entry to
                 * create a shape or connection (between two selected shapes).
                 * 
                 * @see org.eclipse.swt.events.MouseListener#mouseDoubleClick(org.eclipse.swt.events.MouseEvent)
                 */
                public void mouseDoubleClick(MouseEvent e) {
                    PaletteViewer pV = getEditDomain().getPaletteViewer();

                    Tool tool = pV.getActiveTool().createTool();

                    if (tool instanceof CreationTool
                            || tool instanceof ConnectionCreationTool) {

                        tool.setViewer(diagramEditor
                                .getDiagramGraphicalViewer());
                        tool.setEditDomain(diagramEditor
                                .getDiagramGraphicalViewer().getEditDomain());
                        tool.mouseDoubleClick(e, diagramEditor
                                .getDiagramGraphicalViewer());

                        // Current active tool should be deactivated,
                        // but if it is down here it will get
                        // reactivated deep in GEF palette code after
                        // receiving mouse up events.
                        clearActiveTool = true;
                    }
                }

                public void mouseDown(MouseEvent e) {
                    // do nothing
                }

                public void mouseUp(MouseEvent e) {
                    // Deactivate current active tool here if a
                    // double-click was handled.
                    if (clearActiveTool) {
                        PaletteViewer pV = getEditDomain().getPaletteViewer();

                        pV.setActiveTool(null);
                        clearActiveTool = false;
                    }

                }
            };

        }
        return paletteMouseListener;
    }

    /**
     * @return Palette Key Handler for the palette
     */
    private KeyHandler getPaletteKeyHandler() {

        if (paletteKeyHandler == null) {

            paletteKeyHandler = new KeyHandler() {

                /**
                 * Processes a <i>key released </i> event. This method is called
                 * by the Tool whenever a key is released, and the Tool is in
                 * the proper state. Override to support pressing the enter key
                 * to create a shape or connection (between two selected shapes)
                 * 
                 * @param event
                 *            the KeyEvent
                 * @return <code>true</code> if KeyEvent was handled in some way
                 */
                public boolean keyReleased(KeyEvent event) {

                    if (event.keyCode == SWT.Selection) {
                        PaletteViewer pV = getEditDomain().getPaletteViewer();

                        Tool tool = pV.getActiveTool().createTool();

                        if (tool instanceof CreationTool
                                || tool instanceof ConnectionCreationTool) {

                            tool.keyUp(event, diagramEditor
                                    .getDiagramGraphicalViewer());

                            // deactivate current selection
                            pV.setActiveTool(null);

                            return true;
                        }

                    }
                    return super.keyReleased(event);
                }

            };

        }
        return paletteKeyHandler;
    }

}
