package com.tibco.xpd.processwidget.figures;

import java.util.List;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.ScalableFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Pattern;

import com.tibco.xpd.processwidget.ProcessWidgetPlugin;
import com.tibco.xpd.processwidget.adapters.XpdFlowRoutingStyle;
import com.tibco.xpd.processwidget.figures.ProcessFigure.DiagramViewType;

/**
 * Utility class for calculating scaling factor currently applied to a given
 * figure.
 * 
 * @author aallway
 * 
 */
public class XPDFigureUtilities {
    /**
     * Shadow offset
     */
    public static final int SHADOW_OFFSETCX = 4;

    public static final int SHADOW_OFFSETCY = 3;

    private static Font processEditorFont = null;

    /**
     * 
     * @return The font to use for process diagram editor (static resource so DO
     *         NOT dispose!)
     */
    public static Font getProcessEditorFont() {
        if (processEditorFont == null) {
            Font df = JFaceResources.getDialogFont();
            FontData fds = df.getFontData()[0];

            FontData custFd = new FontData();
            custFd.setName(fds.getName());
            custFd.setLocale(fds.getLocale());
            custFd.setStyle(fds.getStyle());

            int fontSize =
                    ProcessWidgetPlugin.getDefault().getPreferenceStore()
                            .getInt("processEditorFontSize"); //$NON-NLS-1$
            if (fontSize < 1) {
                fontSize = fds.getHeight();
            }

            custFd.setHeight(fontSize);

            processEditorFont = new Font(null, custFd);
        }

        return processEditorFont;
    }

    /**
     * Get the scaling factor that should be applied to the given figure
     * <p>
     * This will include any scaling factors applied to the figure's parent
     * tree.
     * 
     * @param fig
     *            Figure to get currently applied scale for.
     * 
     * @return double Total scale factor that should be applied to the given
     *         figure.
     */
    public static double getFigureScale(IFigure fig) {
        double scale = 1d;

        while (fig != null) {
            if (fig instanceof ScalableFigure) {
                scale = scale * ((ScalableFigure) fig).getScale();
            }
            fig = fig.getParent();
        }

        return (scale);
    }

    private XPDFigureUtilities() {
    };

    /**
     * Set background fill pattern for gradient fill of the provided rectangle.
     * From the current bgackground color TO the current foreground color.
     * 
     * the method behave properly for SWTGraphics and XpdScaledGraphics, the
     * return value have to be passed to resetBackgroundPattern in order to
     * dispose. Return value might be null.
     * 
     * @param gr
     *            Graphics
     * @param x1
     *            Start Corner x
     * @param y1
     *            Start Corner y
     * @param x2
     *            End corner x
     * @param y2
     *            End Corner y
     * @return
     */
    public static Pattern setBackgroundPattern(Graphics gr, int x1, int y1,
            int x2, int y2) {
        return setBackgroundPattern(gr, x1, y1, x2, y2, 255, 255);
    }

    /**
     * Set background fill pattern for gradient fill of the provided rectangle.
     * From the current bgackground color TO the current foreground color.
     * 
     * the method behave properly for SWTGraphics and XpdScaledGraphics, the
     * return value have to be passed to resetBackgroundPattern in order to
     * dispose. Return value might be null.
     * 
     * @param gr
     *            Graphics
     * @param x1
     *            Start Corner x
     * @param y1
     *            Start Corner y
     * @param x2
     *            End corner x
     * @param y2
     *            End Corner y
     * @param alpha1
     *            TODO
     * @param alpha2
     *            TODO
     * @return
     */
    public static Pattern setBackgroundPattern(Graphics gr, int x1, int y1,
            int x2, int y2, int alpha1, int alpha2) {
        Color c1 = gr.getBackgroundColor();
        Color c2 = gr.getForegroundColor();

        if (gr instanceof XpdScaledGraphics) {
            XpdScaledGraphics xgr = (XpdScaledGraphics) gr;
            xgr.setBackgroundPattern(null,
                    x1,
                    y1,
                    x2,
                    y2,
                    c1,
                    alpha1,
                    c2,
                    alpha1);
            return null;
        } else if (gr instanceof SWTGraphics) {

            Pattern p =
                    new Pattern(null, x1, y1, x2, y2, c1, alpha1, c2, alpha2);
            try {
                gr.setBackgroundPattern(p);
            } catch (RuntimeException e) {
                // ignore
                p.dispose();
                p = null;
            }
            return p;
        }
        return null;
    }

    /**
     * Set background fill pattern for gradient fill of the provided rectangle.
     * From the current bgackground color TO the current foreground color.
     * 
     * the method behave properly for SWTGraphics and XpdScaledGraphics, the
     * return value have to be passed to resetBackgroundPattern in order to
     * dispose. Return value might be null.
     * 
     * @param gr
     *            Graphics
     * @param ca
     *            Area for brush
     * @return
     */
    public static Pattern setBackgroundPattern(Graphics gr, Rectangle ca) {

        return (setBackgroundPattern(gr, ca.x, ca.y, ca.x + ca.width, ca.y
                + ca.height, gr.getAlpha(), gr.getAlpha()));

    }

    /**
     * Reset (unset) gradient fill and dispose pattern (if it was created)
     * 
     * @param gr
     * @param p
     */
    public static void resetBackgroundPattern(Graphics gr, Pattern p) {
        if (gr instanceof XpdScaledGraphics) {
            XpdScaledGraphics xgr = (XpdScaledGraphics) gr;
            xgr.resetBackgroundPattern();
        } else if (gr instanceof SWTGraphics) {
            try {
                gr.setBackgroundPattern(null);
            } catch (RuntimeException e) {
                // ignore
            } finally {
                if (p != null) {
                    p.dispose();
                }
            }
        } else {
            if (p != null) {
                p.dispose();
            }
        }
    }

    /**
     * Get the parent process figure of the given figure.
     */
    private static ProcessFigure getProcessFigure(IFigure fig) {
        do {
            if (fig instanceof ProcessFigure) {
                return (ProcessFigure) fig;
            }
            fig = fig.getParent();
        } while (fig != null);

        throw new IllegalArgumentException(
                "Figure is not part of a process diagram."); //$NON-NLS-1$
    }

    /**
     * Given a figure return the diagram view type that it is a part of.
     * <p>
     * NOTE that the figure has to be a decscendant child of a ProcessFigure in
     * order to ascertain this.
     * 
     * @param fig
     * 
     * @return DiagramViewType
     */
    public static DiagramViewType getDiagramViewType(IFigure fig) {
        return getProcessFigure(fig).getDiagramViewType();
    }

    public static boolean isInTaskLibraryAlternateView(IFigure fig) {
        return DiagramViewType.TASK_LIBRARY_ALTERNATE
                .equals(getDiagramViewType(fig));
    }

    public static boolean isInProcessDiagramView(IFigure fig) {
        return DiagramViewType.PROCESS.equals(getDiagramViewType(fig));
    }

    /**
     * XPD-1140: Allow tag process editor figure as readonly.
     * 
     * @param fig
     * @return <code>true</code> if the parent process is read-only edit session
     */
    public static boolean isReadOnly(IFigure fig) {
        ProcessFigure processFigure = getProcessFigure(fig);
        return processFigure.isReadOnly();
    }

    /**
     * @param fig
     * @return The flow routing style that is in use for the process that is the
     *         ancestor of the given figure.
     */
    public static XpdFlowRoutingStyle getXpdRouterStyle(IFigure fig) {
        return getRootWidgetLayer(fig).getFlowRoutingStyle();
    }

    /**
     * @param fig
     * @return The root figure layer for the process diagram.
     */
    public static RootXPDScalableFreeformLayeredPane getRootWidgetLayer(
            IFigure fig) {
        IFigure lastNonNullAncestorFigure = null;

        while (fig != null) {
            lastNonNullAncestorFigure = fig;

            if (fig instanceof RootXPDScalableFreeformLayeredPane) {
                return (RootXPDScalableFreeformLayeredPane) fig;
            }
            fig = fig.getParent();
        }

        /*
         * Most things are descendents of RootXPDScalableFreeformLayeredPane BUT
         * not everything (some feedback goes in a layer above). If we didn't
         * find it then search from top-down instead.
         */
        if (lastNonNullAncestorFigure != null) {
            RootXPDScalableFreeformLayeredPane found =
                    recursiveFindRootWidgetLayer(lastNonNullAncestorFigure);

            if (found != null) {
                return found;
            }
        }

        throw new IllegalArgumentException(
                "Figure is not part of a process diagram."); //$NON-NLS-1$    
    }

    /**
     * Recursively searches for an descendent instance of
     * {@link RootXPDScalableFreeformLayeredPane}
     * 
     * @param fig
     * @return {@link RootXPDScalableFreeformLayeredPane} or <code>null</code>
     *         if not found.
     */
    private static RootXPDScalableFreeformLayeredPane recursiveFindRootWidgetLayer(
            IFigure fig) {
        if (fig instanceof RootXPDScalableFreeformLayeredPane) {
            return (RootXPDScalableFreeformLayeredPane) fig;

        } else {
            List children = fig.getChildren();

            if (children != null) {
                for (Object child : children) {

                    if (child instanceof IFigure) {
                        RootXPDScalableFreeformLayeredPane found =
                                recursiveFindRootWidgetLayer((IFigure) child);

                        if (found != null) {
                            return found;
                        }
                    }
                }
            }
        }

        return null;
    }

}
