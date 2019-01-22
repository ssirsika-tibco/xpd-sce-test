package com.tibco.xpd.simulation.ui.views;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import com.gface.date.DateSelectedEvent;
import com.gface.date.DateSelectionListener;
import com.tibco.simulation.report.SimRepExperimentState;
import com.tibco.xpd.simulation.launch.LaunchPlugin;
import com.tibco.xpd.simulation.launch.SimulationControler;
import com.tibco.xpd.simulation.launch.SimulationEventKeys;
import com.tibco.xpd.simulation.ui.Messages;
import com.tibco.xpd.simulation.ui.PreferenceStoreKeys;
import com.tibco.xpd.simulation.ui.SimulationUIPlugin;
import com.tibco.xpd.simulation.ui.views.widgets.DateTimeSelectComposite;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * View for controling simulation.
 */
public class SimulationControlView extends ViewPart {

    private static final int MAXIMUM_SIMULATION_DELAY = 500;

    private static final int MINIMUM_SIMULATION_DELAY = 5;

    /**
     * List of control for easy enable/disable changes.
     */
    private final List controls;

    private IPreferenceStore preferenceStore;

    private static final Locale DEFAULT_LOCALE = Locale.getDefault();

    private static final String DEFAULT_TIME_FORMAT = "#######0.0000"; //$NON-NLS-1$

    private static final DecimalFormat simulationTimeFormatter =
            getCurrentLocaleDecimalFormat(DEFAULT_TIME_FORMAT);

    /**
     * Listener for simulation's state changes. Used to control UI widget's
     * state (enable/disable etc.)
     */
    private PropertyChangeListener simulationEventListener;

    private ISelectionProvider selectionProvider = new SelectionProvider();

    private ProgressBar progressBar;

    private Label simulationTimeText;

    private DateFormat simulationTimeFormat;

    private DateTimeSelectComposite dateTimeSelectComposite;

    private Label realSimulationTimeText;

    /*
     * XPD-4199: Saket- Replacing process name with process label
     */
    private Label processLbl;

    /**
     * Returns Decimal Formatter in current Locale
     * 
     * @param format
     * @return
     */
    public static DecimalFormat getCurrentLocaleDecimalFormat(String format) {
        DecimalFormat f =
                (DecimalFormat) DecimalFormat.getInstance(DEFAULT_LOCALE);
        f.applyPattern(format);
        return f;
    }

    /**
     * The constructor.
     */
    public SimulationControlView() {
        SimulationUIPlugin plugin = SimulationUIPlugin.getDefault();
        preferenceStore = plugin.getPreferenceStore();
        LaunchPlugin.getSimulationControler()
                .setReferenceTime(getPreferedReferenceTime());
        controls = new ArrayList();
    }

    /**
     * Obtains simulation sitart time form preference store.
     * 
     * @return
     */
    private Date getPreferedReferenceTime() {
        Date referenceTime;
        if (preferenceStore.contains(PreferenceStoreKeys.SIMULATION_START_TIME)) {
            long ref =
                    preferenceStore
                            .getLong(PreferenceStoreKeys.SIMULATION_START_TIME);
            referenceTime = new Date(ref);
        } else {
            referenceTime = Calendar.getInstance().getTime();
        }
        return referenceTime;
    }

    /**
     * This is a callback that will allow us to create the viewer and initialize
     * it.
     */
    @Override
    public void createPartControl(Composite parent) {
        GridData gridData;

        ControlsBuilder builder = new ControlsBuilder(parent);

        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        parent.setLayout(layout);

        /*
         * XPD-4199: Saket- Replacing process name with process label
         */
        Label processLblLabel = new Label(parent, SWT.NONE);
        gridData = new GridData(SWT.END, SWT.CENTER, true, true);
        processLblLabel.setLayoutData(gridData);
        processLblLabel.setText(Messages.SimulationControlView_Process1);

        processLbl = new Label(parent, SWT.NONE);
        /*
         * XPD-4199: Saket- process label looks truncated on screen, hence
         * layout data needs to be put properly.
         */
        gridData = new GridData(SWT.FILL, SWT.CENTER, false, false);
        processLbl.setLayoutData(gridData);
        processLbl.setText(Messages.SimulationControlView_NoProcessSelected);

        int minimumSimulationDelay = MINIMUM_SIMULATION_DELAY;
        int maximumSimulationDelay = MAXIMUM_SIMULATION_DELAY;

        int defaultSimulationDelay;
        if (preferenceStore.contains(PreferenceStoreKeys.SIMULATION_DELAY)) {
            defaultSimulationDelay =
                    preferenceStore
                            .getInt(PreferenceStoreKeys.SIMULATION_DELAY);
        } else {
            defaultSimulationDelay =
                    ((maximumSimulationDelay - minimumSimulationDelay) / 2)
                            + minimumSimulationDelay;
        }

        SimulationControler simulationControl =
                LaunchPlugin.getSimulationControler();
        simulationControl.setDelay(defaultSimulationDelay);

        Label sliderLabel = new Label(parent, SWT.LEFT);
        gridData = new GridData();
        gridData.horizontalSpan = 2;
        sliderLabel.setLayoutData(gridData);
        sliderLabel.setText(Messages.SimulationControlView_Speed);

        // ---- [START] Speed control
        Composite speedControl = new Composite(parent, SWT.NONE);
        GridLayout speedLayout = new GridLayout();
        speedLayout.numColumns = 2;
        speedControl.setLayout(speedLayout);
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = 3;
        speedControl.setLayoutData(gridData);

        Label sliderLabelSlower = new Label(speedControl, SWT.LEFT);
        gridData = new GridData(SWT.BEGINNING, SWT.CENTER, true, true);
        sliderLabelSlower.setLayoutData(gridData);
        sliderLabelSlower.setText(Messages.SimulationControlView_Slower);

        Label sliderLabelFaster = new Label(speedControl, SWT.LEFT);
        gridData = new GridData(SWT.END, SWT.CENTER, true, true);
        sliderLabelFaster.setLayoutData(gridData);
        sliderLabelFaster.setText(Messages.SimulationControlView_Faster);

        Scale slider = new Scale(speedControl, SWT.HORIZONTAL);
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = 2;
        slider.setLayoutData(gridData);
        slider.setEnabled(true);
        slider.setSize(100, 10);
        slider.setMaximum(maximumSimulationDelay);
        slider.setMinimum(minimumSimulationDelay);
        slider.setIncrement(1);
        slider.setPageIncrement(10);
        int currentDelay = ((int) simulationControl.getDelay());
        int initialSelection = MAXIMUM_SIMULATION_DELAY - currentDelay;
        slider.setSelection(initialSelection);

        SimulationFactor simulationFactor =
                new SimulationFactor(simulationControl, slider);
        slider.addListener(SWT.MouseUp, simulationFactor);

        this.controls.add(sliderLabel);
        this.controls.add(slider);
        this.controls.add(sliderLabelFaster);
        this.controls.add(sliderLabelSlower);
        // ---- [END] Speed control

        Label label2 = new Label(parent, SWT.LEFT);
        gridData = new GridData();
        gridData.horizontalSpan = 2;
        label2.setLayoutData(gridData);
        label2.setText(Messages.SimulationControlView_Progress);
        this.controls.add(label2);

        this.progressBar = new ProgressBar(parent, SWT.HORIZONTAL);
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.horizontalSpan = 2;
        progressBar.setLayoutData(gridData);
        progressBar.setSelection(0);
        this.controls.add(progressBar);

        Label label = new Label(parent, SWT.LEFT);
        gridData = new GridData(SWT.END, SWT.CENTER, true, true);
        label.setLayoutData(gridData);
        label.setText(Messages.SimulationControlView_Time);
        this.controls.add(label);

        Composite simulationTimeComposite = new Composite(parent, SWT.NONE);
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        simulationTimeComposite.setLayoutData(gridData);
        GridLayout gl = new GridLayout(2, false);
        simulationTimeComposite.setLayout(gl);
        simulationTimeText =
                new Label(simulationTimeComposite, SWT.READ_ONLY | SWT.RIGHT);
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        simulationTimeText.setLayoutData(gridData);
        simulationTimeText.setText(simulationTimeFormatter.format(0));
        this.controls.add(simulationTimeText);

        Label l = new Label(simulationTimeComposite, SWT.READ_ONLY);
        gridData = new GridData(SWT.BEGINNING, SWT.CENTER, true, true);
        l.setLayoutData(gridData);
        l.setText(Messages.SimulationControlView_Minutes);
        this.controls.add(l);

        Label startTimeLabel = new Label(parent, SWT.LEFT);
        gridData = new GridData(SWT.END, SWT.CENTER, true, true);
        startTimeLabel.setLayoutData(gridData);
        startTimeLabel.setText(Messages.SimulationControlView_Start);

        simulationTimeFormat =
                SimpleDateFormat.getDateTimeInstance(DateFormat.FULL,
                        DateFormat.MEDIUM,
                        Locale.UK);

        Date referenceTime;
        referenceTime = getPreferedReferenceTime();

        String currentTimeString = simulationTimeFormat.format(referenceTime);
        DateFormat dateFormat =
                SimpleDateFormat.getDateInstance(DateFormat.FULL, Locale.UK);
        dateFormat.setLenient(false);
        dateTimeSelectComposite =
                new DateTimeSelectComposite(parent, dateFormat, referenceTime);
        gridData = new GridData(SWT.BEGINNING, SWT.CENTER, true, true);
        dateTimeSelectComposite.setLayoutData(gridData);
        dateTimeSelectComposite
                .addDateSelectionListener(new DateSelectionListener() {

                    @Override
                    public void dateSelected(DateSelectedEvent e) {
                        Date selectedDate = e.date;
                        String formattedDate =
                                simulationTimeFormat.format(selectedDate);
                        realSimulationTimeText.setText(formattedDate);
                        SimulationControler simulationControler =
                                LaunchPlugin.getSimulationControler();
                        simulationControler.setReferenceTime(selectedDate);

                        long time = selectedDate.getTime();
                        preferenceStore
                                .setValue(PreferenceStoreKeys.SIMULATION_START_TIME,
                                        time);
                    }
                });

        Label label3 = new Label(parent, SWT.LEFT);
        gridData = new GridData(SWT.END, SWT.CENTER, true, true);
        label3.setLayoutData(gridData);
        label3.setText(Messages.SimulationControlView_Current);
        this.controls.add(label3);

        realSimulationTimeText = new Label(parent, SWT.READ_ONLY);
        gridData = new GridData(SWT.BEGINNING, SWT.CENTER, true, true);
        realSimulationTimeText.setLayoutData(gridData);
        realSimulationTimeText.setText(currentTimeString);
        this.controls.add(realSimulationTimeText);

        builder.disableControls();

        simulationEventListener = builder;
        LaunchPlugin.getSimulationControler()
                .addListener(simulationEventListener);
    }

    /**
     * Passing the focus request to the viewer's control.
     * 
     * @see org.eclipse.ui.IWorkbenchPart#setFocus()
     */
    @Override
    public void setFocus() {
        // viewer.getControl().setFocus();
    }

    private class SimulationFactor implements Listener {

        private final SimulationControler simulationControler;

        private final Scale slider;

        /**
         * Constructor of a listenter.
         * 
         * @param simulationControler
         *            controler reference.
         * @param slider
         *            scale control reference.
         */
        public SimulationFactor(SimulationControler simulationControler,
                Scale slider) {
            this.simulationControler = simulationControler;
            this.slider = slider;
        }

        /**
         * Gets current value from scale control and sets simulation controler
         * delay attribute.
         * 
         * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
         */
        @Override
        public void handleEvent(Event event) {
            // get current value
            int currentVal =
                    MAXIMUM_SIMULATION_DELAY - slider.getSelection()
                            + slider.getMinimum();
            simulationControler.setDelay(currentVal);
            preferenceStore.setValue(PreferenceStoreKeys.SIMULATION_DELAY,
                    currentVal);
        }

    }

    /**
     * @see org.eclipse.ui.part.WorkbenchPart#dispose()
     */
    @Override
    public void dispose() {
        SimulationControler simulationControler =
                LaunchPlugin.getSimulationControler();
        simulationControler.removeListener(simulationEventListener);
    }

    /**
     * Dispatcher of simulation events.
     */
    private class ControlsBuilder implements PropertyChangeListener {

        private Composite parent;

        private int finishedCases;

        /**
         * Constructor.
         */
        public ControlsBuilder(Composite parent) {
            this.parent = parent;
        }

        public void started() {
            enableControls();
            Display d = progressBar.getDisplay();
            d.syncExec(new Runnable() {
                @Override
                public void run() {
                    progressBar.setSelection(0);
                }
            });
        }

        public void stopped() {
            Display d = progressBar.getDisplay();
            d.syncExec(new Runnable() {
                @Override
                public void run() {
                    progressBar.setSelection(0);
                }
            });
            disableControls();
        }

        public void finished() {
            disableControls();
            Display d = progressBar.getDisplay();
            d.syncExec(new Runnable() {
                @Override
                public void run() {
                    progressBar.setSelection(0);
                }
            });
        }

        public void paused() {/* do nothing */
        }

        public void resumed() {/* do nothing */
        }

        public void updateProcessLabel(final String newProcessLbl) {
            Display d = progressBar.getDisplay();
            d.syncExec(new Runnable() {
                @Override
                public void run() {
                    processLbl.setText(newProcessLbl);
                }
            });
        }

        /**
         * TODO comment me!
         * 
         * @see com.tibco.xpd.simulation.ui.SimulationControler.SimulationEventListener#caseFinished(int,
         *      int)
         */
        public void caseFinished(int finishedCases, int totalCases) {
            final int progress = ((finishedCases * 100 / totalCases));

            Display d = progressBar.getDisplay();
            d.syncExec(new Runnable() {
                @Override
                public void run() {
                    progressBar.setSelection(progress);
                }
            });
        }

        /**
         * TODO comment me!
         * 
         * @see com.tibco.xpd.simulation.ui.SimulationControler.SimulationEventListener#simulationTimeChanged(double)
         */
        public void simulationTimeChanged(final double timeValue) {
            Display display = simulationTimeText.getDisplay();
            display.syncExec(new Runnable() {
                @Override
                public void run() {
                    simulationTimeText.setText(simulationTimeFormatter
                            .format(timeValue));
                }
            });
        }

        public void realTimeChanged(final String realTimeValue) {
            Display display = simulationTimeText.getDisplay();
            display.syncExec(new Runnable() {
                @Override
                public void run() {
                    realSimulationTimeText.setText(realTimeValue);
                }
            });
        }

        public void realTimeChanged(final Date realTimeValue) {
            Display display = simulationTimeText.getDisplay();
            display.syncExec(new Runnable() {
                @Override
                public void run() {
                    realSimulationTimeText.setText(simulationTimeFormat
                            .format(realTimeValue));
                }
            });
        }

        /**
         * TODO comment me!
         * 
         * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
         */
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            String propertyName = evt.getPropertyName();
            int totalNoOfCases =
                    LaunchPlugin.getSimulationControler()
                            .getTotalNumberOfCases();

            try {
                if (propertyName
                        .equalsIgnoreCase(SimulationEventKeys.FINISHED_CASES)) {
                    // case finished
                    Integer fCases = (Integer) evt.getNewValue();
                    int finishedCases = fCases.intValue();
                    caseFinished(finishedCases, totalNoOfCases);
                } else if (propertyName
                        .equalsIgnoreCase(SimulationEventKeys.SIMULATION_TIME)) {
                    // simulation time changed
                    Double simulationTime = (Double) evt.getNewValue();
                    simulationTimeChanged(simulationTime.doubleValue());
                } else if (propertyName
                        .equalsIgnoreCase(SimulationEventKeys.EXPERIMENT_STATE)) {
                    // simulation state changed
                    SimRepExperimentState newStateName =
                            (SimRepExperimentState) evt.getNewValue();
                    if (newStateName
                            .getName()
                            .equalsIgnoreCase(SimulationEventKeys.SIMULATION_STATE_ABORTED)) {
                        stopped();
                    } else if (newStateName
                            .getName()
                            .equalsIgnoreCase(SimulationEventKeys.SIMULATION_STATE_FINISHED)) {
                        stopped();
                    } else if (newStateName
                            .getName()
                            .equalsIgnoreCase(SimulationEventKeys.SIMULATION_STATE_NOT_STARTED)) {
                        stopped();
                    } else if (newStateName
                            .getName()
                            .equalsIgnoreCase(SimulationEventKeys.SIMULATION_STATE_PAUSED)) {
                        paused();
                    } else if (newStateName
                            .getName()
                            .equalsIgnoreCase(SimulationEventKeys.SIMULATION_STATE_RUNNING)) {
                        started();
                    }
                } else if (propertyName
                        .equalsIgnoreCase(SimulationEventKeys.REAL_TIME)) {
                    Object realTime = evt.getNewValue();
                    if (realTime instanceof String) {
                        realTimeChanged((String) realTime);
                    } else if (realTime instanceof Date) {
                        realTimeChanged((Date) realTime);
                    }
                } else if (propertyName
                        .equalsIgnoreCase(SimulationEventKeys.REFERENCE_START_TIME)) {
                    // Date refTime=(Date)evt.getNewValue();
                    // refTimeChanged(refTime);
                } else if (propertyName
                        .equalsIgnoreCase(SimulationEventKeys.PROCESS_LABEL)) {
                    updateProcessLabel((String) evt.getNewValue());
                }
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }

        void enableControls() {
            for (Iterator iter = controls.iterator(); iter.hasNext();) {
                final Control widget = (Control) iter.next();
                Display d = widget.getDisplay();
                d.syncExec(new Runnable() {
                    @Override
                    public void run() {
                        widget.setEnabled(true);
                    }
                });
            }

            Display d = dateTimeSelectComposite.getDisplay();
            d.syncExec(new Runnable() {
                @Override
                public void run() {
                    dateTimeSelectComposite.setEnabled(false);
                }
            });
        }

        void disableControls() {
            for (Iterator iter = controls.iterator(); iter.hasNext();) {
                final Control widget = (Control) iter.next();
                Display d = widget.getDisplay();
                d.syncExec(new Runnable() {
                    @Override
                    public void run() {
                        widget.setEnabled(false);
                    }
                });
            }
            Display d = dateTimeSelectComposite.getDisplay();
            d.syncExec(new Runnable() {
                @Override
                public void run() {
                    dateTimeSelectComposite.setEnabled(true);
                }
            });
        }
    }

    /**
     * @see org.eclipse.ui.part.ViewPart#init(org.eclipse.ui.IViewSite)
     */
    @Override
    public void init(IViewSite site) throws PartInitException {
        super.init(site);
        site.setSelectionProvider(selectionProvider);
    };

    /**
     * A selection provider/listener for this view. It is a selection provider
     * for this view's site.
     */
    protected class SelectionProvider implements ISelectionProvider {
        /**
         * Selection change listeners.
         */
        private List<ISelectionChangedListener> selectionChangedListeners = new ArrayList<>();

        private ISelection selection;

        private ILaunchConfiguration launchConfig;

        /*
         * (non-Javadoc) Method declared on ISelectionProvider.
         */
        @Override
        public void addSelectionChangedListener(
                ISelectionChangedListener listener) {
            selectionChangedListeners.add(listener);
        }

        /*
         * (non-Javadoc) Method declared on ISelectionProvider.
         */
        @Override
        public ISelection getSelection() {
            return selection;
        }

        /*
         * (non-Javadoc) Method declared on ISelectionProvider.
         */
        @Override
        public void removeSelectionChangedListener(
                ISelectionChangedListener listener) {
            selectionChangedListeners.remove(listener);
        }

        /**
         * The selection has changed. Process the event.
         * 
         * @param event
         */
		public void selectionChanged(final SelectionChangedEvent event) {
			// pass on the notification to listeners
			for (final ISelectionChangedListener l : selectionChangedListeners) {
				SafeRunner.run(new SafeRunnable() {
					@Override
					public void run() {
						l.selectionChanged(event);
					}
				});
			}
		}

        /*
         * (non-Javadoc) Method declared on ISelectionProvider.
         */
        @Override
        public void setSelection(ISelection newSelection) {
            if (selection != newSelection) {
                this.selection = newSelection;
                if (selection instanceof IStructuredSelection) {
                    IStructuredSelection structSel =
                            (IStructuredSelection) selection;
                    Object selectedObject = structSel.getFirstElement();
                    if (selectedObject instanceof Process) {
                        Process process = (Process) selectedObject;

                        /*
                         * XPD-4199: Saket- Replacing process name with process
                         * label
                         */
                        String workflowProcessLbl =
                                Xpdl2ModelUtil.getDisplayNameOrName(process);
                        processLbl.setText(workflowProcessLbl);
                    }
                    if (selectedObject instanceof ILaunchConfiguration) {
                        launchConfig = (ILaunchConfiguration) selectedObject;
                    }
                }
                selectionChanged(new SelectionChangedEvent(this, newSelection));
            }
        }
    }
}