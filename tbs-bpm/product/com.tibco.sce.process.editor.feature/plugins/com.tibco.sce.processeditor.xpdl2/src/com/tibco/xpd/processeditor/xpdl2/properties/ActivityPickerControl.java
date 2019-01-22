/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */

package com.tibco.xpd.processeditor.xpdl2.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

import com.tibco.xpd.analyst.resources.xpdl2.pickers.ActivityFilterPicker;
import com.tibco.xpd.analyst.resources.xpdl2.pickers.ActivityFilterPicker.ActivityPickerType;
import com.tibco.xpd.analyst.resources.xpdl2.providers.ActivityFilterPickerProviderHelper.ActivityPickerItem;
import com.tibco.xpd.processeditor.xpdl2.properties.messages.Messages;
import com.tibco.xpd.ui.properties.XpdFormToolkit;
import com.tibco.xpd.xpdExtension.ActivityRef;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Activity picker control.
 * <p>
 * Note that before this control can be used to pick activities, the existing
 * selected activity(s) and process scope must have been set via one of the
 * following initialisation methods...
 * <li>{@link #setActivities(Process, Collection)}</li>
 * <li>{@link #setActivityRefs(Process, Collection)}</li>
 * <li>{@link #setActivity(Process, Activity)}</li>
 * 
 * @author aallway
 * @since 8 Mar 2013
 */
public abstract class ActivityPickerControl extends Composite {

    private String pickerTitle;

    private Set<IActivityPickerListener> listeners =
            new HashSet<ActivityPickerControl.IActivityPickerListener>();

    private List<Activity> activities = new ArrayList<Activity>();

    private int pickerStyle;

    private Text activitiesText;

    private Button browseActivities;

    private Process scopeProcess;

    private Button clearActivities;

    /**
     * Activity picker control.
     * <p>
     * Note that before this control can be used to pick activities, the
     * existing selected activity(s) and process scope must have been set via
     * one of the following initialisation methods...
     * <li>{@link #setActivities(Process, Collection)}</li>
     * <li>{@link #setActivityRefs(Process, Collection)}</li>
     * <li>{@link #setActivity(Process, Activity)}</li>
     * 
     * @param parent
     * @param toolkit
     * @param pickerTitle
     *            Title for browse dialog.
     * @param pickerStyle
     *            Combination of style bits... <li>SWT.MULTI - allow multi
     *            select</li>
     */
    public ActivityPickerControl(Composite parent, XpdFormToolkit toolkit,
            String pickerTitle, int pickerStyle) {
        super(parent, SWT.NONE);

        this.pickerTitle = pickerTitle;
        this.pickerStyle = pickerStyle;

        toolkit.adapt(this);
        toolkit.paintBordersFor(this);

        createControl(toolkit);
    }

    /**
     * Called only once picker is launched.
     * 
     * @return The activity filter.
     */
    protected abstract IFilter createActivityFilter();

    /**
     * Create the controls.
     * 
     * @param toolkit
     */
    protected void createControl(XpdFormToolkit toolkit) {
        GridLayout gl = new GridLayout(3, false);
        gl.marginWidth = 0;
        gl.marginHeight = 0;
        gl.horizontalSpacing = 0;
        this.setLayout(gl);

        /*
         * Main activity label control.
         */
        activitiesText = toolkit.createText(this, ""); //$NON-NLS-1$
        activitiesText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        activitiesText.setEditable(false);

        activitiesText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
                | GridData.VERTICAL_ALIGN_CENTER));

        Point sz = activitiesText.computeSize(SWT.DEFAULT, SWT.DEFAULT);

        /*
         * Launch picker button.
         */
        browseActivities =
                toolkit.createButton(this,
                        Messages.InitialiserSelectionSection_ELIPSES,
                        SWT.PUSH);

        GridData gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gd.heightHint = sz.y;
        browseActivities.setLayoutData(gd);

        browseActivities.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {

                if (scopeProcess == null) {
                    throw new RuntimeException(
                            "Process scope not set on activity picker control."); //$NON-NLS-1$
                }

                Collection<Activity> pickActivities = launchActivityPicker();

                if (pickActivities != null) {
                    internalSetActivities(pickActivities);

                    for (IActivityPickerListener listener : listeners) {
                        listener.activitiesPicked(pickActivities);
                    }
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });

        /*
         * Clear activities button
         */
        clearActivities =
                toolkit.createButton(this,
                        Messages.ActivityPickerControl_Clear_button,
                        SWT.PUSH);

        gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gd.heightHint = sz.y;
        clearActivities.setLayoutData(gd);

        clearActivities.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                ArrayList<Activity> clearList = new ArrayList<Activity>();
                internalSetActivities(clearList);

                for (IActivityPickerListener listener : listeners) {
                    listener.activitiesPicked(clearList);
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });

    }

    /**
     * Set the activities currently selected.
     * <p>
     * Note that <code>null</code> entries are supported and interpreted as
     * '<Unknown>' (i.e. deleted) activities.
     * 
     * @param activities
     *            the activities to set
     */
    public void setActivities(Process scopeProcess,
            Collection<Activity> activities) {
        this.scopeProcess = scopeProcess;
        internalSetActivities(activities);
    }

    /**
     * Set the activities currently selected.
     * <p>
     * Note that <code>null</code> entries are supported and interpreted as
     * '<Unknown>' (i.e. deleted) activities.
     * 
     * @param activities
     *            the activities to set
     */
    public void setActivityRefs(Process scopeProcess,
            Collection<ActivityRef> activityRefs) {

        List<Activity> activities = new ArrayList<Activity>();

        for (ActivityRef activityRef : activityRefs) {
            /*
             * Include null entries (refs to deleted activities) as we will show
             * them as '<Unknown>'
             */
            activities.add(activityRef.getActivity());
        }

        setActivities(scopeProcess, activities);
    }

    /**
     * Set single activity selection.
     * <p>
     * Note that <code>null</code> is supported and interpreted as '<Unknown>'
     * (i.e. deleted) activities.
     * 
     * @param scopeProcess
     * @param activity
     */
    public void setActivity(Process scopeProcess, Activity activity) {
        setActivities(scopeProcess, Collections.singletonList(activity));
    }

    /**
     * @return the currently selected activities
     */
    public List<Activity> getActivities() {
        return activities;
    }

    /**
     * Set the activities currently selected.
     * <p>
     * Note that <code>null</code> entries are supported and interpreted as
     * '<Unknown>' (i.e. deleted) activities.
     * 
     * @param activities
     */
    protected void internalSetActivities(Collection<Activity> activities) {
        this.activities = new ArrayList<Activity>();
        this.activities.addAll(activities);

        updateTextControl();
    }

    /**
     * Update the text control from the list of selected activities.
     */
    private void updateTextControl() {
        String activitiesListString = getActivitiesListString();
        /* set the text */
        activitiesText.setText(activitiesListString);
        /* set the tooltip */
        activitiesText.setToolTipText(activitiesListString);
    }

    /**
     * @return List of activities as a String.
     */
    private String getActivitiesListString() {
        ArrayList<String> labelList = new ArrayList<String>(activities.size());

        for (Activity activity : activities) {
            labelList.add(getActivityLabel(activity));
        }

        Collections.sort(labelList, new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                return o1.compareToIgnoreCase(o2);
            }
        });

        StringBuilder sb = new StringBuilder();

        for (String label : labelList) {
            if (sb.length() > 0) {
                sb.append(", "); //$NON-NLS-1$
            }

            sb.append(label);
        }

        String t = sb.toString();
        return t;
    }

    /**
     * @param activity
     * @return The activity label (including '<Unknown>' for null activity and
     *         '<Unnamed>' for activity with no label or name.
     */
    public String getActivityLabel(Activity activity) {
        String label;

        if (activity == null) {
            label =
                    Messages.EventTriggerTypeMessageSection_EventHandlerInitializingActivityMissing_text;
        } else {
            label = Xpdl2ModelUtil.getDisplayNameOrName(activity);

            if (label == null || label.length() == 0) {
                label = Messages.EventTriggerTypeMessageSection_Unnamed_label;
            }
        }

        return label;
    }

    /**
     * Add listener for notifications of activities being picked.
     * 
     * @param listener
     */
    public void addActivitiesPickedListener(IActivityPickerListener listener) {
        listeners.add(listener);
    }

    /**
     * Remove listener.
     * 
     * @param listener
     */
    public void removeActivitiesPickedListener(IActivityPickerListener listener) {
        listeners.remove(listener);
    }

    /**
     * Launch the activity picker dialog.
     * 
     * @return New selected activities or <code>null</code> if user cancels.
     */
    private Collection<Activity> launchActivityPicker() {
        ActivityFilterPicker activityPicker =
                new ActivityFilterPicker(Display.getDefault().getActiveShell(),
                        ActivityPickerType.ACTIVITY,
                        (pickerStyle | SWT.MULTI) != 0);

        List<Activity> ret = Collections.<Activity> emptyList();

        activityPicker.addFilter(new ActivityFilter(createActivityFilter()));

        activityPicker.setTitle(pickerTitle);
        activityPicker.setScope(scopeProcess);

        /**
         * to show the available tasks as the initial selection
         */
        activityPicker.setInitialElementSelections(activities);

        int returnCode = activityPicker.open();

        if (returnCode == ActivityFilterPicker.OK) {

            Object[] rawResultSet = activityPicker.getResult();
            if (rawResultSet != null && rawResultSet.length > 0) {
                ret = new ArrayList<Activity>();
                for (Object rawResult : rawResultSet) {
                    if (rawResult instanceof Activity) {
                        ret.add((Activity) rawResult);
                    }
                }
            }
        } else {
            ret = null;
        }

        return ret;

    }

    /**
     * @see org.eclipse.swt.widgets.Control#setEnabled(boolean)
     * 
     * @param enabled
     */
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        Control[] children = this.getChildren();

        if (children != null) {
            for (Control control : children) {
                control.setEnabled(enabled);
            }
        }
    }

    /**
     * Listener interface for {@link ActivityPickerControl}.
     * <p>
     * Invoked when the user has picked a different set of activities.
     * 
     * @author aallway
     * @since 8 Mar 2013
     */
    public interface IActivityPickerListener {

        /**
         * @return User has chosen a new set of activities.
         */
        public void activitiesPicked(Collection<Activity> selectedActivities);

    }

    /**
     * Activity filter, first ascertains if an activity can be extrapolated from
     * the given object (for IAdaptable and AcitivtyPickerItem etc) then passes
     * it to the delegate filter for furtehr filtering.
     * 
     * 
     * @author aallway
     * @since 11 Mar 2013
     */
    private static class ActivityFilter implements IFilter {
        IFilter delegateFilter;

        /**
         * @param delegateFilter
         */
        public ActivityFilter(IFilter delegateFilter) {
            this.delegateFilter = delegateFilter;
        }

        /**
         * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
         * 
         * @param toTest
         * @return
         */
        @Override
        public boolean select(Object toTest) {
            Activity activity = getActivity(toTest);

            if (activity != null) {
                if (delegateFilter == null) {
                    return true;
                }

                return delegateFilter.select(activity);
            }

            return false;
        }

        /**
         * Get the activity for anything likely to be passed to the filters
         * here.
         * 
         * @param object
         * @return Activity or <code>null</code> if cannot be ascertained.
         */
        public static Activity getActivity(Object object) {
            if (object instanceof Activity) {
                return (Activity) object;

            } else if (object instanceof ActivityPickerItem
                    && ((ActivityPickerItem) object).getItem() instanceof Activity) {
                return (Activity) ((ActivityPickerItem) object).getItem();

            } else if (object instanceof IAdaptable) {
                Object act = ((IAdaptable) object).getAdapter(Activity.class);

                if (act instanceof Activity) {
                    return (Activity) act;
                }
            }
            return null;
        }
    }
}
