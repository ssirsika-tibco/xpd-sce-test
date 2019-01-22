package com.tibco.xpd.processeditor.xpdl2.highlighting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.navigator.INavigatorContentService;
import org.eclipse.ui.navigator.NavigatorContentServiceFactory;

import com.tibco.xpd.analyst.resources.xpdl2.Xpdl2ResourcesPlugin;
import com.tibco.xpd.processeditor.xpdl2.AbstractProcessDiagramEditor;
import com.tibco.xpd.processeditor.xpdl2.ProcessEditorInput;
import com.tibco.xpd.processeditor.xpdl2.Xpdl2ProcessEditorPlugin;
import com.tibco.xpd.processeditor.xpdl2.extensions.AbstractReferenceHighlighterContribution;
import com.tibco.xpd.processeditor.xpdl2.extensions.AbstractStaticHighlighterContribution;
import com.tibco.xpd.processeditor.xpdl2.internal.Messages;
import com.tibco.xpd.processwidget.ProcessWidget;
import com.tibco.xpd.processwidget.parts.WidgetRootEditPart;
import com.tibco.xpd.processwidget.process.progression.AbstractPulsingProgressionPart;
import com.tibco.xpd.processwidget.process.progression.EditPartHighlighter;
import com.tibco.xpd.processwidget.process.progression.EmbeddedSubProcessProgressionPart;
import com.tibco.xpd.processwidget.process.progression.ProcessProgressionPartFactory;
import com.tibco.xpd.processwidget.progression.AbstractProgressionPart;
import com.tibco.xpd.processwidget.progression.AbstractProgressionPartFactory;
import com.tibco.xpd.processwidget.progression.model.ProgressionModel;
import com.tibco.xpd.processwidget.progression.model.ProgressionState;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ViewType;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * ISelectionService listener that controls the highlighting of selected objects
 * elsewhere on the workbench that are referenced by the diagram objects.
 * <p>
 * For instance highlighting of any activities that reference a selected
 * participant.
 * 
 * @author aallway
 * @since 6 Jan 2011
 */
public class ReferenceHighlighterEditPartListener implements ISelectionListener {

    private static final String HIGHLIGHTREF_DISABLED_PREF =
            "HighlightReferencesDisabled"; //$NON-NLS-1$

    private static final String ANIMATE_HIGHLIGHT_DISABLED_PREF =
            "AnimateHighlightDisabled"; //$NON-NLS-1$

    private static final String EXTPROP_OBJECT_HIGHLIGHTED =
            "EXTPROP_OBJECT_HIGHLIGHTED"; //$NON-NLS-1$

    /**
     * Contributions of selected object reference highlighters
     */
    private static Collection<AbstractReferenceHighlighterContribution> referenceContributions;

    /**
     * Contributions to static highlighter contributions.
     */
    private static Collection<AbstractStaticHighlighterContribution> staticContributions;

    /**
     * Active static contributions
     */
    private static Set<AbstractStaticHighlighterContribution> activeStaticContributions =
            new HashSet<AbstractStaticHighlighterContribution>();

    /*
     * List of these listeners that are activie for open editors (so that each
     * can be switched off when highlighter enable button selected.
     */
    private static Set<ReferenceHighlighterEditPartListener> registeredHighlighters =
            new HashSet<ReferenceHighlighterEditPartListener>();

    private final AbstractProcessDiagramEditor processEditor;

    private EditPartHighlighter editPartHighlighter;

    private PerformHighlightRunnable performHighlightRunnable = null;

    /*
     * Last raw selection we performed a highlight for (used when
     * re-highlighting for that selection when switching from (say) project
     * explorer having selected participant and then clicking background process
     * diagram etc
     */
    private ISelection lastRefHighlightSelection = null;

    /* And the set of diagram objects that resulted from that. */
    private Collection<EObject> lastRefHighlightDiagramObjects = null;

    /*
     * And the last set of objects highlighted on selection of a static
     * highlighter(s) from menu.
     */
    private Collection<? extends EObject> lastStaticHighlightDiagramObjects =
            null;

    /*
     * Keep track of last active part - when user JUST activates a different
     * view then we don't want to remove highlighting.
     * 
     * For instance when user is in process editor, selects a static highlighter
     * then selects project explorer we do not necessarily wish to remove the
     * highlight at that point.
     * 
     * If the user actually changes selection at the same time as activating a
     * view then we will get TWO selectionChanged (one because view was
     * activated where the selection is the current selection in that view and
     * another because the user clicked on something that affects the
     * selection).
     * 
     * Therefore if the user clicks on project explorer datafield after
     * highlighting tasks with scripts we will ignore the selection chaneg
     * because of activation (which could remove highlighting if the existing
     * proj explorer selection is not valid for ref highlighting) and then act
     * upon the real selection change.
     */
    private IWorkbenchPart lastActivePart = null;

    private ILabelProvider commonLabelProvider;

    private boolean referenceHighlightingActive = false;

    /**
     * @param abstractProcessDiagramEditor
     */
    public ReferenceHighlighterEditPartListener(
            AbstractProcessDiagramEditor abstractProcessDiagramEditor) {
        super();

        processEditor = abstractProcessDiagramEditor;

        loadProviderContributions();

        registeredHighlighters.add(this);

        NavigatorContentServiceFactory fact =
                NavigatorContentServiceFactory.INSTANCE;
        INavigatorContentService service =
                fact.createContentService("org.eclipse.ui.navigator.ProjectExplorer"); //$NON-NLS-1$

        if (service != null) {
            commonLabelProvider = service.createCommonLabelProvider();
        }
    }

    /**
     * @see org.eclipse.ui.ISelectionListener#selectionChanged(org.eclipse.ui.IWorkbenchPart,
     *      org.eclipse.jface.viewers.ISelection)
     * 
     * @param part
     * @param selection
     */
    @Override
    public void selectionChanged(IWorkbenchPart part, ISelection selection) {

        /*
         * Keep track of last active part - when user JUST activates a different
         * view then we don't want to remove highlighting.
         * 
         * For instance when user is in process editor, selects a static
         * highlighter then selects project explorer we do not necessarily wish
         * to remove the highlight at that point.
         * 
         * If the user actually changes selection at the same time as activating
         * a view then we will get TWO selectionChanged (one because view was
         * activated where the selection is the current selection in that view
         * and another because the user clicked on something that affects the
         * selection).
         * 
         * Therefore if the user clicks on project explorer datafield after
         * highlighting tasks with scripts we will ignore the selection chaneg
         * because of activation (which could remove highlighting if the
         * existing proj explorer selection is not valid for ref highlighting)
         * and then act upon the real selection change.
         */
        if (lastActivePart != null) {
            if (lastActivePart != part) {
                /*
                 * This is a selection change JUST because part is activated and
                 * we've been given the existing selection, ignore this time
                 * thru.
                 * 
                 * This will make much more sense to the user bercause they will
                 * have to physically change the selection in the view they've
                 * activated before the highlighting will be removed.
                 */
                lastActivePart = part;
                return;
            }
        }

        lastActivePart = part;

        // System.out.println(this.getClass().getCanonicalName()
        // + ".selectionChanged()");

        /* Cancel the current job if it exists. */
        if (performHighlightRunnable != null) {
            performHighlightRunnable.cancel();
            performHighlightRunnable = null;
        }

        Shell shell = processEditor.getEditorSite().getShell();

        if (shell != null && !shell.isDisposed()) {
            performHighlightRunnable = new PerformHighlightRunnable(selection);

            shell.getDisplay().asyncExec(performHighlightRunnable);
        }
        return;
    }

    /**
     * reconsider the highlight in light of the new active static highlighters
     * and the current selection.
     * 
     * @param part
     * @param selection
     */
    public void updateFromStaticHighlighters() {

        /* Cancel the current job if it exists. */
        if (performHighlightRunnable != null) {
            performHighlightRunnable.cancel();
            performHighlightRunnable = null;
        }

        Shell shell = processEditor.getEditorSite().getShell();
        if (shell != null && !shell.isDisposed()) {
            performHighlightRunnable =
                    new PerformHighlightRunnable(
                            RUNTYPE.STATIC_HIGHLIGHT_ACTIVATED);

            shell.getDisplay().asyncExec(performHighlightRunnable);

        }
        return;
    }

    /**
     * Re-do highlight according to user's last selected criteria.
     * <p>
     * Useful when model changes or highlight switched on/off.
     * 
     */
    public void redoHighlight() {
        /* Cancel the current job if it exists. */
        if (performHighlightRunnable != null) {
            performHighlightRunnable.cancel();
            performHighlightRunnable = null;
        }

        Shell shell = processEditor.getEditorSite().getShell();
        if (shell != null && !shell.isDisposed()) {
            performHighlightRunnable =
                    new PerformHighlightRunnable(RUNTYPE.REDO_HIGHLIGHT);

            shell.getDisplay().asyncExec(performHighlightRunnable);

        }
        return;
    }

    /**
     * Dispose of this highlighter
     */
    public void dispose() {
        registeredHighlighters.remove(this);

        if (performHighlightRunnable != null) {
            performHighlightRunnable.cancel();
            performHighlightRunnable = null;
        }

        if (editPartHighlighter != null) {
            editPartHighlighter.dispose();
            editPartHighlighter = null;
        }
        return;
    }

    /**
     * Do the highlight of the given selection.
     * 
     * @param selection
     */
    private void doHighlightSelection(ISelection selection) {

        // System.out
        //                .println("==> selectionChanged(HighlightRef): " + processEditor); //$NON-NLS-1$
        long startTime = System.currentTimeMillis();

        createEditPartHighlighter();

        boolean retainCurrentHighlight = false;
        boolean itemsToHighlight = false;

        /*
         * If the selection consists ONLY of things that are highlighted then
         * retain the current highlighting.
         */
        Set<EObject> selectedObjects = getSelectedEObject(selection);
        if (selectedObjects.size() > 0) {
            boolean allSelectionsAreHighlightedObjects = true;

            for (EObject sel : selectedObjects) {
                ProgressionModel progressionModel =
                        editPartHighlighter
                                .getProgressionModelForDiagramObject(sel);

                if (progressionModel != null) {
                    Boolean bHighlighted =
                            (Boolean) progressionModel
                                    .getExtendedProperty(EXTPROP_OBJECT_HIGHLIGHTED);

                    if (bHighlighted == null || !bHighlighted) {
                        allSelectionsAreHighlightedObjects = false;
                        break;
                    }

                } else {
                    allSelectionsAreHighlightedObjects = false;
                    break;
                }
            }

            if (allSelectionsAreHighlightedObjects) {
                retainCurrentHighlight = true;
            }
        }

        /*
         * When selection changes we need to keep up to date with that selection
         * regardless of whether the high;lighting is currently active or not.
         * 
         * This is so that when the user switches off the highlighting, makes
         * some changes or changes selection, when they switch highlighting back
         * on we will obey the highlighting according to the existing selection.
         * 
         * However we do NOT want to incur the performance hit of getting the
         * referenced objects (because if there is performance problem then we
         * want to be able to tell user
         * "just switch of reference highlighting and it will be fine".
         */

        if (!retainCurrentHighlight) {

            /* Save last heighlighted selection. */
            lastRefHighlightSelection = selection;

            if (isHighlightReferencingEnabled()) {
                /*
                 * Create a set of model objects for the main diagram parts that
                 * reference all the selected objects.
                 */
                lastRefHighlightDiagramObjects = null;

                if (selection != null) {
                    lastRefHighlightDiagramObjects =
                            getReferencingDiagramModelObjects(selection);
                }

                /*
                 * Reset the highlight state on all the model objects for
                 * diagram objects.
                 */
                Map<Object, ProgressionModel> progressionModelRegistry =
                        editPartHighlighter.getProgressionModelRegistry();

                if (lastRefHighlightDiagramObjects == null) {
                    /*
                     * There are no highlighter contributions for all of the
                     * selected object types so don't do ANY highlighting (i.e.
                     * don't fade everything out when user selects something
                     * unreferencable from the process).
                     * 
                     * So we can just ignore this circumstance and hide the
                     * visuals
                     */
                    itemsToHighlight = false;

                    for (ProgressionModel progModel : progressionModelRegistry
                            .values()) {
                        setHighlightOff(progModel);
                    }

                    /*
                     * And also clear the set of active static highlighters when
                     * selection changes to nothing interesting.
                     */
                    activeStaticContributions.clear();
                    lastStaticHighlightDiagramObjects = null;

                } else {
                    /*
                     * All of the selected objects have highlighter
                     * contributions so we will fade out all except the selected
                     * ones.
                     */
                    itemsToHighlight = true;

                    /*
                     * Filter the set of objects highlighted for referencing
                     * with the set of objects highlighted by static highlight
                     * contributors.
                     */
                    Collection<EObject> highlightedObjects;

                    if (lastStaticHighlightDiagramObjects != null) {
                        highlightedObjects =
                                new HashSet<EObject>(
                                        lastRefHighlightDiagramObjects);
                        highlightedObjects
                                .retainAll(lastStaticHighlightDiagramObjects);
                    } else {
                        highlightedObjects = lastRefHighlightDiagramObjects;
                    }

                    for (Entry<Object, ProgressionModel> progModelEntry : progressionModelRegistry
                            .entrySet()) {
                        /*
                         * See if this model object is in the set of referencing
                         * objects.
                         */
                        if (highlightedObjects
                                .contains(progModelEntry.getKey())) {
                            setHighlightOn(progModelEntry.getValue());
                        } else {
                            setHighlightOff(progModelEntry.getValue());
                        }
                    }
                }
            }

            if (itemsToHighlight) {
                editPartHighlighter.updateProgression();

                referenceHighlightingActive = true;

            } else {
                editPartHighlighter.deactivateProgression();

                referenceHighlightingActive = false;
            }
        }

        setDiagramTooltip(getThisEditorActiveHighlighters());

        // System.out
        //                .println("<== selectionChanged(HighlightRef): " + (((float) System.currentTimeMillis() - (float) startTime) / 1000f) + "  " + processEditor); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * reconsider the highlight in light of the new active static highlighters
     * and the current selection.
     * <p>
     * This hihlighting is removed the next time the user selects something that
     * is not in the already highlighted objects or user selects something not
     * contributed to fot reference highlighting.
     */
    private void doUpdateFromStaticHighlighterActivation() {
        // System.out
        //                .println("==> doUpdateFromStaticHighlighters(HighlightRef): " + processEditor); //$NON-NLS-1$

        long startTime = System.currentTimeMillis();

        createEditPartHighlighter();

        /*
         * Create a set of model objects for the main diagram parts that
         * reference all the selected objects.
         */
        lastStaticHighlightDiagramObjects = null;

        /*
         * Filter the statically held list of user-selected active high-lighters
         * down to the set that the contributors say are appropriate to the
         * process for this particular editor.
         */
        Set<AbstractStaticHighlighterContribution> thisEditorHighlighters =
                getThisEditorActiveHighlighters();

        lastStaticHighlightDiagramObjects =
                getStaticHighlightDiagramModelObjects(thisEditorHighlighters);

        if (isHighlightReferencingEnabled()) {

            /*
             * Reset the highlight state on all the model objects for diagram
             * objects.
             */
            Map<Object, ProgressionModel> progressionModelRegistry =
                    editPartHighlighter.getProgressionModelRegistry();

            /*
             * FIlter static contributor highlighted objects by any reference
             * highlighted objects if there are any.
             */
            Collection<? extends EObject> highlightObjects =
                    lastStaticHighlightDiagramObjects;

            if (lastStaticHighlightDiagramObjects != null) {
                if (lastRefHighlightDiagramObjects != null) {
                    highlightObjects =
                            new HashSet<EObject>(
                                    lastStaticHighlightDiagramObjects);
                    highlightObjects.retainAll(lastRefHighlightDiagramObjects);
                }
            } else {
                /*
                 * All have been deactivated, just go with whatever the last ref
                 * highlight objects were.
                 */
                highlightObjects = lastRefHighlightDiagramObjects;

            }

            if (lastRefHighlightDiagramObjects != null
                    || lastStaticHighlightDiagramObjects != null) {
                for (Entry<Object, ProgressionModel> progModelEntry : progressionModelRegistry
                        .entrySet()) {
                    /*
                     * See if this model object is in the set of referencing
                     * objects.
                     */
                    if (highlightObjects != null
                            && highlightObjects.contains(progModelEntry
                                    .getKey())) {
                        setHighlightOn(progModelEntry.getValue());
                    } else {
                        setHighlightOff(progModelEntry.getValue());
                    }
                }

                /* This will reshow the visuals. */
                editPartHighlighter.updateProgression();

            } else {
                /*
                 * No static highlighters or ref highlighters active so disable
                 * the progression visuals
                 */
                editPartHighlighter.deactivateProgression();
            }
        }

        setDiagramTooltip(thisEditorHighlighters);

        // System.out
        //                .println("<== doUpdateFromStaticHighlighters(HighlightRef): " + (((float) System.currentTimeMillis() - (float) startTime) / 1000f) + "  " + processEditor); //$NON-NLS-1$ //$NON-NLS-2$

    }

    /**
     * Filter the statically held list of user-selected active highlighters down
     * to the set that the contributors say are appropriate to the process for
     * this particular editor.
     * 
     * @return Active highlighters for this editor's process.
     */
    private Set<AbstractStaticHighlighterContribution> getThisEditorActiveHighlighters() {
        Set<AbstractStaticHighlighterContribution> thisEditorHighlighters =
                new HashSet<AbstractStaticHighlighterContribution>();
        Process editorProcess =
                (Process) processEditor.getAdapter(Process.class);
        if (editorProcess != null) {
            for (AbstractStaticHighlighterContribution activeStaticHighlighter : activeStaticContributions) {
                if (activeStaticHighlighter.shouldShow(editorProcess)) {
                    thisEditorHighlighters.add(activeStaticHighlighter);
                }
            }
        }
        return thisEditorHighlighters;
    }

    /**
     * Update the highlight if the model has changed or some other criteria such
     * as options and enablement of features switched on/off.
     */
    private void doRedoHighlight() {
        /*
         * Recalculate the highlight according to the previosu selected objects
         * and/or the last selected static highlighters
         */
        boolean itemsToHighlight = false;

        createEditPartHighlighter();

        Set<AbstractStaticHighlighterContribution> thisEditorActiveHighlighters =
                getThisEditorActiveHighlighters();

        if (isHighlightReferencingEnabled()) {

            if (lastRefHighlightSelection != null
                    || !thisEditorActiveHighlighters.isEmpty()) {

                Collection<? extends EObject> highlightObjects = null;
                if (lastRefHighlightSelection != null) {
                    lastRefHighlightDiagramObjects =
                            getReferencingDiagramModelObjects(lastRefHighlightSelection);

                    if (lastRefHighlightDiagramObjects != null) {
                        highlightObjects =
                                new HashSet<EObject>(
                                        lastRefHighlightDiagramObjects);
                    }
                }

                if (!thisEditorActiveHighlighters.isEmpty()) {
                    lastStaticHighlightDiagramObjects =
                            getStaticHighlightDiagramModelObjects(thisEditorActiveHighlighters);
                    if (lastStaticHighlightDiagramObjects != null) {
                        if (highlightObjects == null) {
                            highlightObjects =
                                    lastStaticHighlightDiagramObjects;
                        } else {
                            highlightObjects
                                    .retainAll(lastStaticHighlightDiagramObjects);
                        }
                    }
                }

                if (highlightObjects != null) {
                    itemsToHighlight = true;

                    /*
                     * Reset the highlight state on all the model objects for
                     * diagram objects.
                     */
                    Map<Object, ProgressionModel> progressionModelRegistry =
                            editPartHighlighter.getProgressionModelRegistry();

                    for (Entry<Object, ProgressionModel> progModelEntry : progressionModelRegistry
                            .entrySet()) {
                        /*
                         * See if this model object is in the set of referencing
                         * objects.
                         */
                        if (highlightObjects.contains(progModelEntry.getKey())) {
                            setHighlightOn(progModelEntry.getValue());
                        } else {
                            setHighlightOff(progModelEntry.getValue());
                        }
                    }
                }
            }
        }

        if (itemsToHighlight) {
            editPartHighlighter.updateProgression();
        } else {
            editPartHighlighter.deactivateProgression();
        }

        setDiagramTooltip(thisEditorActiveHighlighters);

        return;
    }

    /**
     * Set the process editor diagram tooltip according to the current criteria.
     * 
     * @param thisEditorHighlighters
     */
    private void setDiagramTooltip(
            Set<AbstractStaticHighlighterContribution> thisEditorHighlighters) {
        Control control =
                processEditor.getProcessWidget().getGraphicalViewer()
                        .getControl();

        if (control != null && !control.isDisposed()) {
            String tip = null;

            if (isHighlightReferencingEnabled()
                    && (referenceHighlightingActive || !thisEditorHighlighters
                            .isEmpty())) {

                tip =
                        Messages.ReferenceHighlighterEditPartListener_HighlightRefsTooltipLeader_tooltip;
                boolean first = true;

                if (referenceHighlightingActive
                        && lastRefHighlightSelection != null) {
                    /*
                     * Go thru the selected objects, looking for a ref
                     * highlighter contributer for each
                     */
                    for (Iterator iterator =
                            ((IStructuredSelection) lastRefHighlightSelection)
                                    .iterator(); iterator.hasNext();) {
                        Object selectedObject = iterator.next();

                        /*
                         * Go thru each contribution building the list of things
                         * that reference this object
                         */
                        String label = null;

                        for (AbstractReferenceHighlighterContribution referenceContribution : referenceContributions) {
                            Class<? extends Object> clazz =
                                    referenceContribution
                                            .getInterestingReferencedObjectClass();

                            /*
                             * See if object is the class that this contribution
                             * is interested in (or is adaptable to it).
                             */
                            Object interestingObject = null;

                            if (clazz.isInstance(selectedObject)) {
                                interestingObject = selectedObject;
                            } else if (selectedObject instanceof IAdaptable) {
                                interestingObject =
                                        ((IAdaptable) selectedObject)
                                                .getAdapter(clazz);
                            }

                            if (interestingObject != null) {
                                label =
                                        referenceContribution
                                                .getInterestingObjectLabel(interestingObject);
                                if (label != null) {
                                    break;
                                }
                            }
                        } /* Next contributor */

                        if (label == null) {
                            String name = null;
                            if (commonLabelProvider != null) {
                                name =
                                        commonLabelProvider
                                                .getText(selectedObject);
                            }

                            if (name == null) {
                                name =
                                        Messages.ReferenceHighlighterEditPartListener_Unknown_label;
                            }

                            label =
                                    String.format(Messages.ReferenceHighlighterEditPartListener_UnhandledObjectType_leader,
                                            name);
                        }

                        tip = tip + "\n  -"; //$NON-NLS-1$

                        if (first) {
                            tip =
                                    tip
                                            + String.format(Messages.ReferenceHighlighterEditPartListener_ReferencesLeader_tooltip,
                                                    label);
                        } else {
                            tip =
                                    tip
                                            + String.format(Messages.ReferenceHighlighterEditPartListener_AndReferencesLeader_tooltip,
                                                    label);
                        }

                        first = false;

                    }

                } /* End of reference highlights. */

                /*
                 * Go thru static highlight contributions.
                 * 
                 * Add separator from ref highlight descriptions if necessary.
                 */
                if (!thisEditorHighlighters.isEmpty()) {
                    if (referenceHighlightingActive
                            && lastRefHighlightSelection != null) {
                        tip =
                                tip
                                        + "\n  -----------------------------------------------"; //$NON-NLS-1$
                    }

                    for (AbstractStaticHighlighterContribution staticHighlighter : thisEditorHighlighters) {
                        String activatedTooltipLabel =
                                staticHighlighter.getActivatedTooltipLabel();

                        tip = tip + "\n  -"; //$NON-NLS-1$

                        if (first) {
                            tip = tip + activatedTooltipLabel;
                        } else {
                            tip =
                                    tip
                                            + String.format(Messages.ReferenceHighlighterEditPartListener_AndLeader_tooltip,
                                                    activatedTooltipLabel);
                        }

                        first = false;
                    }
                }
            }

            control.setToolTipText(tip);
        }
        return;
    }

    /**
     * Set progression part animation to enabled / disabled.
     * 
     * @param enabled
     */
    private void enableHighlightAnimation(boolean enabled) {
        if (editPartHighlighter != null) {
            Map<ProgressionModel, AbstractProgressionPart> partRegistry =
                    editPartHighlighter.getProgressionPartRegistry();

            if (partRegistry != null) {
                for (AbstractProgressionPart progressionPart : partRegistry
                        .values()) {
                    if (progressionPart instanceof AbstractPulsingProgressionPart) {
                        /*
                         * Set should animate according to users wishes (in pref
                         * store via menu option) - except for embedded process
                         * because they can be huge which has a very large
                         * performance impact.
                         */
                        if (!(progressionPart instanceof EmbeddedSubProcessProgressionPart)) {
                            if (((AbstractPulsingProgressionPart) progressionPart)
                                    .isShouldAnimateInProgressFigure() != enabled) {
                                ((AbstractPulsingProgressionPart) progressionPart)
                                        .setShouldAnimateInProgressFigure(enabled);
                            }
                        }
                    }
                }
            }
        }
        return;
    }

    /**
     * @param progressionModel
     */
    private void setHighlightOn(ProgressionModel progressionModel) {
        progressionModel.setProgressionState(ProgressionState.IN_PROGRESS);
        progressionModel.setExtendedProperty(EXTPROP_OBJECT_HIGHLIGHTED,
                Boolean.TRUE);
    }

    /**
     * @param progressionModel
     */
    private void setHighlightOff(ProgressionModel progressionModel) {
        progressionModel.setProgressionState(ProgressionState.NOT_PROCESSED);
        progressionModel.removeExtendedProperty(EXTPROP_OBJECT_HIGHLIGHTED);
    }

    /**
     * Get a list of the model objects for diagram parts in our process editor
     * that reference the combined selection.
     * 
     * @param selection
     * @return <code>null</code> no selected object has is in scope of the
     *         process in dfiagram or has no ref-highlight contributor OR is out
     *         of scope of the process. Empty set if at least one selected
     *         objects have referenceHighlighterContribution but no referencers
     *         were found that reference all of the selected objects. Set of >=1
     *         referencing diagram model objects that reference all of the
     *         selected objects.
     */
    private HashSet<EObject> getReferencingDiagramModelObjects(
            ISelection selection) {

        HashSet<EObject> referencingObjects = null;

        if (selection instanceof IStructuredSelection && !selection.isEmpty()) {
            Process editorProcess =
                    (Process) processEditor.getAdapter(Process.class);

            if (editorProcess != null) {
                /*
                 * First check that we have a referenceHighlighterCotnribution
                 * for each of the selected objects.
                 * 
                 * AND that all objects are in the scope of this editor.
                 */
                boolean allHaveContributionsAndInScope = true;
                boolean atLeastOneHasContributionInScope = false;

                for (Iterator iterator =
                        ((IStructuredSelection) selection).iterator(); iterator
                        .hasNext();) {
                    Object object = iterator.next();

                    if (!hasReferenceContributionAndInScope(object,
                            editorProcess)) {
                        allHaveContributionsAndInScope = false;
                    } else {
                        atLeastOneHasContributionInScope = true;
                    }
                }

                if (allHaveContributionsAndInScope) {
                    /*
                     * Ok we have reference highlighter contr's for all selected
                     * objects so now go get the references to sleected
                     * objects..
                     */
                    for (Iterator iterator =
                            ((IStructuredSelection) selection).iterator(); iterator
                            .hasNext();) {
                        Object selectedObject = iterator.next();

                        /*
                         * Go thru each contribution building the list of things
                         * that reference this object
                         */
                        HashSet<EObject> thingsThatRefThisObject =
                                new HashSet<EObject>();

                        for (AbstractReferenceHighlighterContribution referenceContribution : referenceContributions) {
                            Class<? extends Object> clazz =
                                    referenceContribution
                                            .getInterestingReferencedObjectClass();

                            /*
                             * See if object is the class that this contribution
                             * is interested in (or is adaptable to it).
                             */
                            Object interestingObject = null;

                            if (clazz.isInstance(selectedObject)) {
                                interestingObject = selectedObject;
                            } else if (selectedObject instanceof IAdaptable) {
                                interestingObject =
                                        ((IAdaptable) selectedObject)
                                                .getAdapter(clazz);
                            }

                            if (interestingObject != null) {
                                Collection<? extends EObject> selRefObjects =
                                        referenceContribution
                                                .getReferencingDiagramModelObjects(interestingObject,
                                                        editorProcess);
                                if (selRefObjects != null) {
                                    thingsThatRefThisObject
                                            .addAll(selRefObjects);
                                }
                            }
                        }

                        /*
                         * Ok now we have a list of all the referencing objects
                         * of this selected object So combine that with the list
                         * we already have.
                         */
                        if (referencingObjects == null) {
                            referencingObjects = thingsThatRefThisObject;
                        } else {
                            /*
                             * Create a list that contains only objects in the
                             * list of referencers of previous slected objects
                             * with the list of referencers of this slected
                             * object.
                             */
                            referencingObjects
                                    .retainAll(thingsThatRefThisObject);
                        }
                    }

                } else if (atLeastOneHasContributionInScope) {
                    /*
                     * Return an empty list if any selected object is in scope
                     * and highlightable (to show user that part of the
                     * selection can be referenced but not all things selcted
                     * are referenced
                     */
                    referencingObjects = new HashSet<EObject>();

                }
            }
        }

        /*
         * XPD-7474: Re-calculating actual object to highlight because If the
         * Activity is in an Embedded/Event Sub proc then we highlight the
         * topmost 'COLLAPSED' embedded sub process which contains the activity.
         */
        HashSet<EObject> actualObjectsToHighlight =
                getActualObjectsToHighlight(referencingObjects);

        return actualObjectsToHighlight;
    }

    /**
     * 
     * @param highlightObjects
     *            the Objects to Highlight.
     * @return Set of Objects to Highlight by additionally re-calculating actual
     *         object to highlight because If the Activity is in an
     *         Embedded/Event Sub proc then we highlight the topmost 'COLLAPSED'
     *         embedded sub process which contains the activity, else returns
     *         <code>null</code> is the passed 'highlightObjects' is null.
     */
    private HashSet<EObject> getActualObjectsToHighlight(
            Collection<? extends EObject> highlightObjects) {

        HashSet<EObject> actualObjectsToHighlight = null;

        if (highlightObjects != null) {

            actualObjectsToHighlight = new HashSet<EObject>();

            for (EObject eachObject : highlightObjects) {
                /*
                 * We are interested in Activities inside Embedded/Event Sub
                 * process.
                 */
                if ((eachObject instanceof Activity)
                        && ((Activity) eachObject).getFlowContainer() instanceof ActivitySet) {

                    Activity topmostCollapsedEmbeddedSubProc =
                            getTopmostCollapsedEmbeddedSubProc((Activity) eachObject);

                    if (topmostCollapsedEmbeddedSubProc != null) {
                        /*
                         * Highlight the topmost collapsed embedded sub process.
                         */
                        actualObjectsToHighlight
                                .add(topmostCollapsedEmbeddedSubProc);
                    } else {
                        /*
                         * If we are here that means that the activity resides
                         * in embedded sub process and all its parent embedded
                         * sub processes are open(i.e. not collapsed) and hence
                         * the activity is visible in process editor , thus
                         * highlight it.
                         */
                        actualObjectsToHighlight.add(eachObject);
                    }

                } else {
                    /*
                     * keep adding all other Eobjects to the list.
                     */
                    actualObjectsToHighlight.add(eachObject);
                }
            }
        }

        return actualObjectsToHighlight;
    }

    /**
     * 
     * @param activity
     *            the Activity inside Embedded sub-process
     * @return the Topmost/Outermost embedded/event sub-proc activity that
     *         contains the passed activity AND which is in COLLAPSED state.
     */
    private Activity getTopmostCollapsedEmbeddedSubProc(Activity activity) {

        Activity topmostCollapsedEmbeddedSubProc = null;
        /*
         * get all the parent embedded sub-procs.
         */
        List<Activity> allParentEmbeddedSubProcActivites =
                getAllParentEmbeddedSubProcActivites(activity);

        for (Activity eachParentEmbeddedSubProc : allParentEmbeddedSubProcActivites) {

            if (eachParentEmbeddedSubProc.getBlockActivity() != null
                    && ViewType.COLLAPSED.equals(eachParentEmbeddedSubProc
                            .getBlockActivity().getView())) {
                /*
                 * keep track of topmost collapsed embedded sub proc.
                 */
                topmostCollapsedEmbeddedSubProc = eachParentEmbeddedSubProc;
            }
        }
        return topmostCollapsedEmbeddedSubProc;
    }

    /**
     * 
     * 
     * @param activity
     *            the Activity inside Embedded sub-process
     * @return 'LinkedList' of all the Parent Embedded/Event Sub Processes that
     *         contain the passed activity such that the inner most embedded
     *         sub-process is the first element in the returned LinkedList and
     *         the outermost is the last element.
     */
    private List<Activity> getAllParentEmbeddedSubProcActivites(
            Activity activity) {
        List<Activity> allParentEmbeddedSubProcs = new LinkedList<Activity>();

        Activity parentActivity = activity;

        /*
         * keep going untill the parent activity is inside an event/embedded sub
         * process
         */
        while (parentActivity.getFlowContainer() instanceof ActivitySet) {

            Activity embSubProcActivityForActSet =
                    Xpdl2ModelUtil
                            .getEmbSubProcActivityForActSet(parentActivity
                                    .getProcess(),
                                    ((ActivitySet) parentActivity
                                            .getFlowContainer()).getId());

            if (embSubProcActivityForActSet != null) {

                allParentEmbeddedSubProcs.add(embSubProcActivityForActSet);
                parentActivity = embSubProcActivityForActSet;
            }
        }

        return allParentEmbeddedSubProcs;
    }

    /**
     * Get a list of the model objects for diagram parts in our process editor
     * that are highlighted according to the active static highlighter
     * contributions.
     * 
     * @param selection
     * @param activeEditorHighlighters
     *            highlighters that are active for this editor (i.e. selected by
     *            user and Contributor.shouldShow() for this editor's process.
     * 
     * @return <code>null</code> if there are no active contributors or List of
     *         diagram part model objects that should be highlighted according
     *         to the static highlight contributions.
     */
    private Collection<? extends EObject> getStaticHighlightDiagramModelObjects(
            Set<AbstractStaticHighlighterContribution> activeEditorHighlighters) {

        Collection<? extends EObject> highlightObjects = null;

        Process editorProcess =
                (Process) processEditor.getAdapter(Process.class);

        if (editorProcess != null && !activeEditorHighlighters.isEmpty()) {
            for (AbstractStaticHighlighterContribution staticContributor : activeEditorHighlighters) {

                Collection<? extends EObject> thisContributionsObjects =
                        staticContributor
                                .getHighlightedDiagramModelObjects(editorProcess);
                if (thisContributionsObjects != null) {
                    /*
                     * Ok now we have a list of all the highlighted objects of
                     * this selected object So combine that with the list we
                     * already have.
                     */
                    if (highlightObjects == null) {
                        highlightObjects = thisContributionsObjects;
                    } else {
                        highlightObjects.retainAll(thisContributionsObjects);
                    }
                }
            }
        }
        /*
         * XPD-7474: Re-calculating actual object to highlight because If the
         * Activity is in an Embedded/Event Sub proc then we highlight the
         * topmost 'COLLAPSED' embedded sub process which contains the activity.
         */
        HashSet<EObject> actualObjectsToHighlight =
                getActualObjectsToHighlight(highlightObjects);

        return actualObjectsToHighlight;
    }

    /**
     * @param selectedObject
     * @return <code>true</code> if there is at least one reference highlighter
     *         contribution for the given object type AND that contribution says
     *         it's in scope of the editor's process or <code>false</code> if
     *         none found
     */
    private boolean hasReferenceContributionAndInScope(Object selectedObject,
            Process process) {
        for (AbstractReferenceHighlighterContribution referenceContribution : referenceContributions) {

            Class<? extends Object> clazz =
                    referenceContribution.getInterestingReferencedObjectClass();

            Object correctObject = null;
            if (clazz.isInstance(selectedObject)) {

                correctObject = selectedObject;
            } else if (selectedObject instanceof IAdaptable) {
                /* Select object can adapt to interesting clazz. */
                correctObject = ((IAdaptable) selectedObject).getAdapter(clazz);
            }

            if (correctObject != null) {
                if (referenceContribution.isInScope(correctObject, process)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param selection
     * @return Objects in the selection that are EObjects.
     */
    private Set<EObject> getSelectedEObject(ISelection selection) {
        Set<EObject> eObjects = new HashSet<EObject>();

        Process editorProcess =
                ((ProcessEditorInput) processEditor.getEditorInput())
                        .getProcess();

        if (selection instanceof IStructuredSelection) {
            for (Iterator iterator =
                    ((IStructuredSelection) selection).iterator(); iterator
                    .hasNext();) {
                Object selectedObject = iterator.next();

                if (!(selectedObject instanceof EObject)) {
                    if (selectedObject instanceof IAdaptable) {
                        selectedObject =
                                ((IAdaptable) selectedObject)
                                        .getAdapter(EObject.class);
                    }
                }

                if (selectedObject instanceof EObject) {
                    eObjects.add((EObject) selectedObject);
                }
            }
        }
        return eObjects;
    }

    /**
     * Create the edit part highlighter if it isn't already.
     */
    private void createEditPartHighlighter() {
        if (editPartHighlighter == null) {
            ProcessWidget processWidget = processEditor.getProcessWidget();

            editPartHighlighter =
                    new EditPartHighlighter((WidgetRootEditPart) processWidget
                            .getGraphicalViewer().getRootEditPart()) {
                        /**
                         * @see com.tibco.xpd.processwidget.process.progression.EditPartHighlighter#createProgressionPartFactory()
                         * 
                         * @return
                         */
                        @Override
                        protected AbstractProgressionPartFactory createProgressionPartFactory() {
                            return new HighlighterProgressionPartFactory();
                        }
                    };

            editPartHighlighter.initialiseProgressionModel();

            processWidget.setReferenceHighlighter(editPartHighlighter);
        }
    }

    /**
     * Load the contributions from extrenion point.
     */
    private static void loadProviderContributions() {
        referenceContributions =
                new ArrayList<AbstractReferenceHighlighterContribution>();

        staticContributions =
                new ArrayList<AbstractStaticHighlighterContribution>();

        IExtensionPoint point =
                Platform.getExtensionRegistry()
                        .getExtensionPoint(Xpdl2ProcessEditorPlugin.ID,
                                "processDiagramReferenceHighlighter"); //$NON-NLS-1$
        if (point != null) {
            IConfigurationElement[] errorProviders =
                    point.getConfigurationElements();

            if (errorProviders != null) {
                for (int c = 0; c < errorProviders.length; c++) {
                    IConfigurationElement extContribution = errorProviders[c];

                    if ("ReferenceHighlighterProvider".equals(extContribution.getName())) { //$NON-NLS-1$
                        Object propClass = null;
                        try {
                            propClass =
                                    extContribution
                                            .createExecutableExtension("class"); //$NON-NLS-1$
                        } catch (CoreException ce) {
                            System.err
                                    .println(ReferenceHighlighterEditPartListener.class
                                            .getName()
                                            + "CoreException: " + ce.getMessage()); //$NON-NLS-1$
                            ce.printStackTrace(System.err);
                        }

                        if (propClass instanceof AbstractReferenceHighlighterContribution) {
                            referenceContributions
                                    .add((AbstractReferenceHighlighterContribution) propClass);
                        } else {
                            String contributerId =
                                    extContribution.getContributor().getName();
                            Xpdl2ResourcesPlugin
                                    .getDefault()
                                    .getLogger()
                                    .error(contributerId
                                            + ": " //$NON-NLS-1$
                                            + point.getUniqueIdentifier()
                                            + ": Incorrectly defined extension - class must implement " //$NON-NLS-1$
                                            + AbstractReferenceHighlighterContribution.class.getCanonicalName());
                        }

                    } else if ("StaticHighlighterProvider".equals(extContribution.getName())) { //$NON-NLS-1$
                        Object propClass = null;
                        try {
                            propClass =
                                    extContribution
                                            .createExecutableExtension("class"); //$NON-NLS-1$
                        } catch (CoreException ce) {
                            System.err
                                    .println(ReferenceHighlighterEditPartListener.class
                                            .getName()
                                            + "CoreException: " + ce.getMessage()); //$NON-NLS-1$
                            ce.printStackTrace(System.err);
                        }

                        if (propClass instanceof AbstractStaticHighlighterContribution) {
                            staticContributions
                                    .add((AbstractStaticHighlighterContribution) propClass);
                        } else {
                            String contributerId =
                                    extContribution.getContributor().getName();
                            Xpdl2ResourcesPlugin
                                    .getDefault()
                                    .getLogger()
                                    .error(contributerId
                                            + ": " //$NON-NLS-1$
                                            + point.getUniqueIdentifier()
                                            + ": Incorrectly defined extension - class must implement " //$NON-NLS-1$
                                            + AbstractStaticHighlighterContribution.class.getCanonicalName());
                        }
                    }
                }
            }
        }

        return;
    }

    /**
     * @return <code>true</code> if highlight referencing is enabled.
     */
    public static boolean isHighlightReferencingEnabled() {
        IPreferenceStore prefStore =
                Xpdl2ProcessEditorPlugin.getDefault().getPreferenceStore();

        return !prefStore.getBoolean(HIGHLIGHTREF_DISABLED_PREF);
    }

    /**
     * Set enablement of highlight referencing.
     * 
     * @param enabled
     */
    public static void setHighlightReferencingEnabled(boolean enabled) {
        IPreferenceStore prefStore =
                Xpdl2ProcessEditorPlugin.getDefault().getPreferenceStore();

        prefStore.setValue(HIGHLIGHTREF_DISABLED_PREF, !enabled);

        /* Turn highlighting off all the active editors */
        for (ReferenceHighlighterEditPartListener refHighlighter : registeredHighlighters) {
            ProcessWidget processWidget =
                    refHighlighter.processEditor.getProcessWidget();
            if (processWidget != null && processWidget.getControl() != null) {
                if (!processWidget.getControl().isDisposed()) {
                    refHighlighter.redoHighlight();
                }
            }
        }
        return;
    }

    /**
     * @return <code>true</code> if highlight animationis enabled.
     */
    public static boolean isAnimateHighlightEnabled() {
        IPreferenceStore prefStore =
                Xpdl2ProcessEditorPlugin.getDefault().getPreferenceStore();

        return !prefStore.getBoolean(ANIMATE_HIGHLIGHT_DISABLED_PREF);
    }

    /**
     * Set enablement of highlight animation.
     * 
     * @param enabled
     */
    public static void setAnimateHighlightEnabled(boolean enabled) {
        IPreferenceStore prefStore =
                Xpdl2ProcessEditorPlugin.getDefault().getPreferenceStore();

        prefStore.setValue(ANIMATE_HIGHLIGHT_DISABLED_PREF, !enabled);

        /* Turn highlighting off all the active editors */
        for (ReferenceHighlighterEditPartListener refHighlighter : registeredHighlighters) {
            ProcessWidget processWidget =
                    refHighlighter.processEditor.getProcessWidget();
            if (processWidget != null && processWidget.getControl() != null) {
                if (!processWidget.getControl().isDisposed()) {
                    refHighlighter.enableHighlightAnimation(enabled);
                }
            }
        }
        return;
    }

    /**
     * @return The contributed static highlighter contributions.
     */
    public static Collection<AbstractStaticHighlighterContribution> getStaticHighlighters() {
        return staticContributions;
    }

    /**
     * Activate the static highlighter contribution (which will cause the
     * highlighted objects to be further filtered by the list of diagram objects
     * returned by the given highlighter.
     * 
     * @param staticHighlighter
     */
    public static void activateStaticHighlighter(
            AbstractStaticHighlighterContribution staticHighlighter,
            boolean activate) {

        if (activate && !activeStaticContributions.contains(staticHighlighter)) {
            activeStaticContributions.add(staticHighlighter);

        } else if (!activate
                && activeStaticContributions.contains(staticHighlighter)) {
            activeStaticContributions.remove(staticHighlighter);

        } else {
            return;
        }

        /*
         * Tell each diagram's highlighter to reconsider the highlight in light
         * of the new active static highlighters and the current selecton.
         */

        for (ReferenceHighlighterEditPartListener refHighlighter : registeredHighlighters) {
            ProcessWidget processWidget =
                    refHighlighter.processEditor.getProcessWidget();
            if (processWidget != null && processWidget.getControl() != null) {
                if (!processWidget.getControl().isDisposed()) {
                    refHighlighter.updateFromStaticHighlighters();
                }
            }
        }
        return;
    }

    public static boolean isStaticHighlighterActive(
            AbstractStaticHighlighterContribution staticHighlighter) {
        return activeStaticContributions.contains(staticHighlighter);
    }

    /**
     * Runnable to schedule the highlight when UI has completed the seleciton
     * process.
     * 
     * @author aallway
     * @since 20 Jan 2011
     */
    private class PerformHighlightRunnable implements Runnable {
        private boolean cancelled = false;

        private ISelection iselection;

        private RUNTYPE runtype;

        /**
         * 
         */
        public PerformHighlightRunnable(RUNTYPE runtype) {
            this.runtype = runtype;
        }

        /**
         * @param iselection
         */
        public PerformHighlightRunnable(ISelection iselection) {
            this(RUNTYPE.SELECTION_CHANGED);
            this.iselection = iselection;
        }

        /**
         * @see java.lang.Runnable#run()
         * 
         */
        @Override
        public void run() {
            if (!cancelled) {
                Shell shell = processEditor.getSite().getShell();
                if (shell != null && !shell.isDisposed()) {
                    if (RUNTYPE.SELECTION_CHANGED.equals(runtype)) {
                        doHighlightSelection(iselection);
                    } else if (RUNTYPE.STATIC_HIGHLIGHT_ACTIVATED
                            .equals(runtype)) {
                        doUpdateFromStaticHighlighterActivation();
                    } else if (RUNTYPE.REDO_HIGHLIGHT.equals(runtype)) {
                        doRedoHighlight();
                    }
                }
            }
        }

        public void cancel() {
            cancelled = true;
        }

    }

    private static enum RUNTYPE {
        SELECTION_CHANGED, STATIC_HIGHLIGHT_ACTIVATED, REDO_HIGHLIGHT
    }

    private static class HighlighterProgressionPartFactory extends
            ProcessProgressionPartFactory {
        /**
         * 
         */
        HighlighterProgressionPartFactory() {
            super();
        }

        /**
         * @see com.tibco.xpd.processwidget.process.progression.ProcessProgressionPartFactory#createObjectProgressionPart(com.tibco.xpd.processwidget.progression.model.ProgressionModel,
         *      org.eclipse.gef.GraphicalEditPart, org.eclipse.draw2d.IFigure)
         * 
         * @param progressionModel
         * @param originalGraphicalEditPart
         * @param progressionFiguresLayer
         * @return
         */
        @Override
        public AbstractProgressionPart createObjectProgressionPart(
                ProgressionModel progressionModel,
                GraphicalEditPart originalGraphicalEditPart,
                IFigure progressionFiguresLayer) {

            AbstractProgressionPart progressionPart =
                    super.createObjectProgressionPart(progressionModel,
                            originalGraphicalEditPart,
                            progressionFiguresLayer);

            if (progressionPart instanceof AbstractPulsingProgressionPart) {
                /*
                 * Set should animate according to users wishes (in pref store
                 * via menu option) - except for embedded process because they
                 * can be huge which has a very large performance impact.
                 */
                if (!(progressionPart instanceof EmbeddedSubProcessProgressionPart)) {
                    ((AbstractPulsingProgressionPart) progressionPart)
                            .setShouldAnimateInProgressFigure(isAnimateHighlightEnabled());
                }
            }

            return progressionPart;
        }
    }
}
