/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.bpm.simplified.ui;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.ui.activities.ICategory;
import org.eclipse.ui.activities.IWorkbenchActivitySupport;

import com.tibco.xpd.bpm.simplified.ui.preferences.SimplifiedUiPreferenceInitializer;
import com.tibco.xpd.ui.internal.actions.ActivitiesUtil;
import com.tibco.xpd.ui.perspective.PerspectiveLifecycleAdapter;

/**
 * Listen to the perspectives and detects perspective switch.
 * 
 * @author jarciuch
 * @since 18 Nov 2014
 */
public class SimplifiedUiPerspectiveListener extends
        PerspectiveLifecycleAdapter {

    /**
     * 'Non-BPM Features' activity category.
     */
    @SuppressWarnings("restriction")
    private static final String NON_BPM_ACTIVITY_CATEGORY =
            "com.tibco.xpd.bpm.simplified.ui.nonbpm.catetory"; //$NON-NLS-1$

    private static final String BPM_MODELING_PERSPECTIVE =
            "com.tibco.xpd.bpm.modeling.perspective"; //$NON-NLS-1$

    private static final String LIVE_DEV_PERSPECTIVE =
            "com.tibco.xpd.n2.LiveDevelopment"; //$NON-NLS-1$

    private static final List<String> SIMPLIFIED_PERSPECTIVES = Arrays
            .asList(BPM_MODELING_PERSPECTIVE, LIVE_DEV_PERSPECTIVE);

    /**
     * Cached non-bpm category.
     */
    private ICategory nonBpmCategory = null;

    /**
     * @see com.tibco.xpd.ui.perspective.PerspectiveLifecycleAdapter#perspectiveInitialized(org.eclipse.ui.IWorkbenchWindow,
     *      org.eclipse.ui.IWorkbenchPage,
     *      org.eclipse.ui.IPerspectiveDescriptor)
     *
     * @param window
     * @param page
     * @param perspective
     */
    @Override
    public void perspectiveInitialized(IWorkbenchWindow window,
            IWorkbenchPage page, IPerspectiveDescriptor perspective) {
        /*
         * On the opening perspective in a new workbench window page the
         * 'activated' event is not triggered and so we make to call to the
         * perspectiveActivated ourself.
         */
        this.perspectiveActivated(page, perspective);
    }

    /**
     * @see org.eclipse.ui.IPerspectiveListener#perspectiveActivated(org.eclipse.ui.IWorkbenchPage,
     *      org.eclipse.ui.IPerspectiveDescriptor)
     */
    @Override
    @SuppressWarnings("restriction")
    public void perspectiveActivated(IWorkbenchPage page,
            IPerspectiveDescriptor perspective) {

        if (!PlatformUI.isWorkbenchRunning()) {
            return;
        }

        boolean switchCapablites =
                BpmSimplifiedUiActivator
                        .getDefault()
                        .getPreferenceStore()
                        .getBoolean(SimplifiedUiPreferenceInitializer.CHANGE_CAPABILITIES_WHEN_SWITCHING_PERSPECTIVE);

        if (!switchCapablites) {
            return;
        }

        if (nonBpmCategory == null) {
            nonBpmCategory =
                    ActivitiesUtil.getCategory(NON_BPM_ACTIVITY_CATEGORY);
        }
        boolean nonBpmEnabled =
                ActivitiesUtil.isCategoryEnabled(nonBpmCategory);
        boolean isSimplifiedPerspective =
                SIMPLIFIED_PERSPECTIVES.contains(perspective.getId());

        if (isSimplifiedPerspective && !isSetLastActivePerspective()) {
            /* Disable non-bpm first time workspace is run. */
            setEnabledCategoryActivities(NON_BPM_ACTIVITY_CATEGORY, false);
        } else if (isSimplifiedPerspective && nonBpmEnabled) {
            setEnabledCategoryActivities(NON_BPM_ACTIVITY_CATEGORY, false);
        } else if (!isSimplifiedPerspective && !nonBpmEnabled) {
            setEnabledCategoryActivities(NON_BPM_ACTIVITY_CATEGORY, true);
        }
        setLastActivePerspective(perspective.getId());
    }

    private void setEnabledCategoryActivities(String categoryId, boolean enable) {
        @SuppressWarnings("restriction")
        Set<String> categoryActivities =
                ActivitiesUtil.getCategoryActivities(categoryId);
        IWorkbenchActivitySupport activitySupport =
                PlatformUI.getWorkbench().getActivitySupport();
        IActivityManager activityManager = activitySupport.getActivityManager();
        @SuppressWarnings("unchecked")
        HashSet<String> enabledActivitiesIds =
                new HashSet<>(activityManager.getEnabledActivityIds());
        if (enable) {
            enabledActivitiesIds.addAll(categoryActivities);
        } else {
            enabledActivitiesIds.removeAll(categoryActivities);
        }
        activitySupport.setEnabledActivityIds(enabledActivitiesIds);
    }

    private String getLastActivePerspective() {
        return BpmSimplifiedUiActivator
                .getDefault()
                .getPreferenceStore()
                .getString(SimplifiedUiPreferenceInitializer.LAST_ACTIVE_PERSPECTIVE);
    }

    private boolean isSetLastActivePerspective() {
        String lastActivePerspective = getLastActivePerspective();
        return lastActivePerspective != null
                && !lastActivePerspective.trim().isEmpty();
    }

    private void setLastActivePerspective(String perspectiveId) {
        BpmSimplifiedUiActivator
                .getDefault()
                .getPreferenceStore()
                .setValue(SimplifiedUiPreferenceInitializer.LAST_ACTIVE_PERSPECTIVE,
                        perspectiveId);
    }
}
