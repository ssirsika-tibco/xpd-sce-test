/*
 * Copyright (c) TIBCO Software Inc 2004, 2007. All rights reserved.
 */
package com.tibco.xpd.simulation.annotation;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Display;

import com.tibco.simulation.report.SimRepActivity;
import com.tibco.simulation.report.SimRepActivityQueue;
import com.tibco.simulation.report.SimRepParticipant;
import com.tibco.simulation.report.provider.SimRepItemProviderAdapterFactory;
import com.tibco.xpd.processwidget.figures.HoverDescriptionFigure;
import com.tibco.xpd.processwidget.policies.HoverInfo;
import com.tibco.xpd.simulation.ActivitySimulationDataType;
import com.tibco.xpd.simulation.ParticipantSimulationDataType;
import com.tibco.xpd.simulation.annotation.preferences.PreferenceConstants;

/**
 * Process diagram annotator for displaying simulation results data alongside
 * the workflow task.
 * 
 * @author nwilson
 */
public class SimRepActivityAnnotator implements INotifyChangedListener {

    /** Percentage maximum. */
    private static final int PERCENT_MAX = 100;

    /** The task annotation figure. */
    private TaskAnnotationFigure figure;

    /** The activity adapter. */
    private ItemProviderAdapter activityAdapter;

    /** Maximum values reacher. */
    private double[] max;

    /** Upper SLAs. */
    private Double[] slaMax;

    /** Lower SLAs. */
    private Double[] slaMin;

    /** Current values. */
    private double[] params;

    /** Data providers. */
    private IAnnotationDataProvider[] providers;

    /**
     * @param simActivity
     *            The activity to annotate.
     * @param simParticipant
     *            The participant to annotate.
     * @param activitySimulation
     *            Activity simulation data type.
     * @param participantSimualtion
     *            Participant simulation data type.
     * @param factory
     *            The ItemProviderAdapter factory for the activity.
     * @param parent
     *            The base figure that will be annotated.
     * @param caseCount
     *            The total number of cases.
     */
    public SimRepActivityAnnotator(SimRepActivity simActivity,
            SimRepParticipant simParticipant,
            ActivitySimulationDataType activitySimulation,
            ParticipantSimulationDataType participantSimualtion,
            SimRepItemProviderAdapterFactory factory, int caseCount,
            IFigure parent) {
        IPreferenceStore store =
                SimulationAnnotationPlugin.getDefault().getPreferenceStore();
        boolean showObserved = store.getBoolean(PreferenceConstants.P_OBSERVED);
        boolean showCurrent = store.getBoolean(PreferenceConstants.P_CURRENT);
        boolean showDelay = store.getBoolean(PreferenceConstants.P_DELAY);
        boolean showUtilisation =
                store.getBoolean(PreferenceConstants.P_UTILISATION);
        ArrayList<IAnnotationDataProvider> providerList =
                new ArrayList<IAnnotationDataProvider>();
        if (showObserved) {
            IAnnotationDataProvider provider =
                    new ObservedCasesProvider(simActivity, simParticipant);
            provider.setMax(caseCount);
            providerList.add(provider);
        }
        if (showCurrent) {
            IAnnotationDataProvider provider =
                    new CurrentQueueSizeProvider(simActivity, simParticipant);
            provider.setMax(caseCount);
            providerList.add(provider);
        }
        if (showDelay) {
            IAnnotationDataProvider provider =
                    new ActivityDelayProvider(simActivity, simParticipant);
            provider.setMax(activitySimulation.getSlaMaximumDelay() == null ? 0
                    : activitySimulation.getSlaMaximumDelay().doubleValue());
            provider.setSlaMax(activitySimulation.getSlaMaximumDelay());
            providerList.add(provider);
        }
        if (showUtilisation) {
            IAnnotationDataProvider provider =
                    new ParticipantUtilisationProvider(simActivity,
                            simParticipant);
            provider.setMax(PERCENT_MAX);
            Double slaMin = new Double(0);
            Double slaMax = new Double(0);
            if (participantSimualtion != null) {
                Double slaMinUtilisation =
                        participantSimualtion.getSlaMinimumUtilisation();
                Double slaMaxUtilisation =
                        participantSimualtion.getSlaMaximumUtilisation();
                if (slaMinUtilisation != null) {
                    slaMin = slaMinUtilisation;
                }
                if (slaMaxUtilisation != null) {
                    slaMax = slaMaxUtilisation;
                }
            }
            provider.setSlaMin(slaMin);
            provider.setSlaMax(slaMax);
            providerList.add(provider);
        }
        providers = new IAnnotationDataProvider[providerList.size()];
        providerList.toArray(providers);

        if (simActivity != null) {
            activityAdapter =
                    (ItemProviderAdapter) factory.adapt(simActivity,
                            IStructuredItemContentProvider.class);
            activityAdapter.addListener(this);
        }
        figure = new TaskAnnotationFigure(parent, providers.length);
        max = new double[providers.length];
        slaMin = new Double[providers.length];
        slaMax = new Double[providers.length];
        params = new double[providers.length];
        StringBuffer infoText = new StringBuffer();
        for (int i = 0; i < providers.length; i++) {
            max[i] = providers[i].getMax();
            slaMin[i] = providers[i].getSlaMin();
            slaMax[i] = providers[i].getSlaMax();
            params[i] = 0;
            infoText.append(Messages
                    .getString("SimRepActivityAnnotator.ColumnLabel")); //$NON-NLS-1$
            infoText.append(i + 1);
            infoText.append(": "); //$NON-NLS-1$
            infoText.append(providers[i].getLabel());
            if ((i + 1) != providers.length) {
                infoText.append("\n"); //$NON-NLS-1$
            }
        }
        figure.setMaximums(max);
        figure.setSLA(slaMax, slaMin);
        DecimalFormat format = new DecimalFormat("#########0"); //$NON-NLS-1$
        figure.setNumberFormat(format);
        HoverDescriptionFigure hoverDescriptionFigure =
                new HoverDescriptionFigure();
        HoverInfo hoverInfo =
                new HoverInfo(
                        Messages.getString("SimRepActivityAnnotator.SimulationDataLabel"), infoText.toString()); //$NON-NLS-1$
        hoverDescriptionFigure.setInfo(hoverInfo);
        figure.setToolTip(hoverDescriptionFigure);
    }

    /**
     * @return The figure for this annotation.
     */
    public IFigure getFigure() {
        return figure;
    }

    /**
     * Called when the annotation for the activity is no longer needed.
     */
    public void dispose() {
        if (activityAdapter != null) {
            activityAdapter.removeListener(this);
        }
        figure.dispose();
    }

    /**
     * Callback from the activity to indicate that the data has changed and the
     * figure may need updating.
     * 
     * @param notification
     *            The change notification.
     * @see org.eclipse.emf.edit.provider.INotifyChangedListener#notifyChanged(org.eclipse.emf.common.notify.Notification)
     */
    @Override
    public void notifyChanged(Notification notification) {
        boolean changed = false;
        for (int i = 0; i < providers.length; i++) {
            providers[i].update();
            double newValue = providers[i].getValue();
            if (params[i] != newValue) {
                params[i] = newValue;
                max[i] = providers[i].getMax();
                changed = true;
            }
        }
        if (changed) {
            figure.setParams(params);
            figure.setMaximums(max);
            Display.getDefault().asyncExec(new Runnable() {
                @Override
                public void run() {
                    figure.repaint();
                }
            });
        }
    }

    /**
     * @author nwilson
     */
    class ObservedCasesProvider extends AbstractAnnotationDataProvider {
        /**
         * @param simActivity
         *            The simulation report activity.
         * @param simParticipant
         *            The simulation report participant.
         */
        public ObservedCasesProvider(SimRepActivity simActivity,
                SimRepParticipant simParticipant) {
            super(simActivity, simParticipant);
        }

        /**
         * @see com.tibco.xpd.simulation.annotation.IAnnotationDataProvider#update()
         */
        @Override
        public void update() {
            SimRepActivityQueue queue = getSimActivity().getActivityQueue();
            if (queue != null) {
                setValue(queue.getObservedCases());
            }
        }

        /**
         * @return The label.
         * @see com.tibco.xpd.simulation.annotation.IAnnotationDataProvider#getLabel()
         */
        @Override
        public Object getLabel() {
            return Messages
                    .getString("SimRepActivityAnnotator.ObservedCasesLabel"); //$NON-NLS-1$
        }
    }

    /**
     * @author nwilson
     */
    class CurrentQueueSizeProvider extends AbstractAnnotationDataProvider {
        /**
         * @param simActivity
         *            The simulation report activity.
         * @param simParticipant
         *            The simulation report participant.
         */
        public CurrentQueueSizeProvider(SimRepActivity simActivity,
                SimRepParticipant simParticipant) {
            super(simActivity, simParticipant);
        }

        /**
         * @see com.tibco.xpd.simulation.annotation.IAnnotationDataProvider#update()
         */
        @Override
        public void update() {
            SimRepActivityQueue queue = getSimActivity().getActivityQueue();
            if (queue != null) {
                setValue(queue.getCurrentSize());
            }
        }

        /**
         * @return The label.
         * @see com.tibco.xpd.simulation.annotation.IAnnotationDataProvider#getLabel()
         */
        @Override
        public Object getLabel() {
            return Messages.getString("SimRepActivityAnnotator.QueueSizeLabel"); //$NON-NLS-1$
        }
    }

    /**
     * @author nwilson
     */
    class ActivityDelayProvider extends AbstractAnnotationDataProvider {
        /**
         * @param simActivity
         *            The simulation report activity.
         * @param simParticipant
         *            The simulation report participant.
         */
        public ActivityDelayProvider(SimRepActivity simActivity,
                SimRepParticipant simParticipant) {
            super(simActivity, simParticipant);
        }

        /**
         * @see com.tibco.xpd.simulation.annotation.IAnnotationDataProvider#update()
         */
        @Override
        public void update() {
            setValue(getSimActivity().getAverageDuration());
            if (getValue() > getMax()) {
                setMax(getValue());
            }
        }

        /**
         * @return The label.
         * @see com.tibco.xpd.simulation.annotation.IAnnotationDataProvider#getLabel()
         */
        @Override
        public Object getLabel() {
            return Messages
                    .getString("SimRepActivityAnnotator.ActivityDelayLabel"); //$NON-NLS-1$
        }
    }

    /**
     * @author nwilson
     */
    class ParticipantUtilisationProvider extends AbstractAnnotationDataProvider {

        /**
         * @param simActivity
         *            The simulation report activity.
         * @param simParticipant
         *            The simulation report participant.
         */
        public ParticipantUtilisationProvider(SimRepActivity simActivity,
                SimRepParticipant simParticipant) {
            super(simActivity, simParticipant);
        }

        /**
         * @see com.tibco.xpd.simulation.annotation.IAnnotationDataProvider#update()
         */
        @Override
        public void update() {
            double averageBusyTime = getSimParticipant().getAverageBusyTime();
            double averageIdleTime = getSimParticipant().getAverageIdleTime();
            double totalTime = averageBusyTime + averageIdleTime;
            setValue(totalTime == 0 ? 0 : (PERCENT_MAX * averageBusyTime)
                    / totalTime);

        }

        /**
         * @return The label.
         * @see com.tibco.xpd.simulation.annotation.IAnnotationDataProvider#getLabel()
         */
        @Override
        public Object getLabel() {
            return Messages
                    .getString("SimRepActivityAnnotator.ParticipantUtilisationLabel"); //$NON-NLS-1$
        }
    }
}
