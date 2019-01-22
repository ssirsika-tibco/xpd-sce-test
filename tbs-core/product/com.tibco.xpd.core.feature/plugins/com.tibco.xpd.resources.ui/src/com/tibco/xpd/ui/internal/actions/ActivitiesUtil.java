package com.tibco.xpd.ui.internal.actions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.gmf.runtime.common.ui.util.ActivityUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.IActivity;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.ui.activities.IActivityRequirementBinding;
import org.eclipse.ui.activities.ICategory;
import org.eclipse.ui.activities.ICategoryActivityBinding;
import org.eclipse.ui.activities.IWorkbenchActivitySupport;
import org.eclipse.ui.activities.NotDefinedException;

import com.tibco.xpd.resources.ui.XpdResourcesUIActivator;
import com.tibco.xpd.resources.ui.internal.Messages;

public class ActivitiesUtil {

    /**
     * 
     */
    private static final String XPD_ACTIVITIES_PREFIX = "com.tibco"; //$NON-NLS-1$

    private static final String CATEGORY_DEFAULT_PREFIX_STORE_KEY =
            "CATEGORY_DEFAULT_PREFIX_STORE_KEY"; //$NON-NLS-1$

    public static final String CATEGORY_ENABLEMENT_ENABLED = "ENABLED"; //$NON-NLS-1$

    public static final String CATEGORY_ENABLEMENT_NOT_ENABLED = "NOT_ENABLED"; //$NON-NLS-1$

    private static final String CATEGORIES_EXTENSION = "categories"; //$NON-NLS-1$

    private static final String CATEGORY_ID = "id"; //$NON-NLS-1$

    private static Set<String> categoryDefaultEnablementIds;

    /**
     * The workbenchWindow for this action.
     */
    private final IWorkbenchWindow workbenchWindow;

    /**
     * This field should only be used by JUnit tests.
     */
    private static String testBuffer;

    public ActivitiesUtil(IWorkbenchWindow workbenchWindow) {
        this.workbenchWindow = workbenchWindow;
    }

    /**
     * This flag should only be used by JUnit tests.
     */
    private static boolean testMode;

    @SuppressWarnings("unchecked")
    public boolean setActivity(boolean enabled, String activityId,
            String categoryName) {
        synchronized (ActivityUtil.class) {
            testBuffer = null;
            IWorkbenchActivitySupport activitySupport =
                    PlatformUI.getWorkbench().getActivitySupport();
            IActivityManager activityManager =
                    activitySupport.getActivityManager();
            Set enabledActivityIds =
                    new HashSet(activityManager.getEnabledActivityIds());

            if (enabled) {
                if (enabledActivityIds.add(activityId)) {
                    activitySupport.setEnabledActivityIds(enabledActivityIds);
                    return true;
                }
            } else {

                // first check dependencies

                String dependencies = getDependencies(categoryName, activityId);

                if (dependencies == null) {
                    if (enabledActivityIds.remove(activityId)) {
                        activitySupport
                                .setEnabledActivityIds(enabledActivityIds);
                        return true;
                    }
                } else {

                    if (enabledActivityIds.remove(activityId)) {
                        activitySupport
                                .setEnabledActivityIds(enabledActivityIds);
                    }

                    if (isActivityEnabled(activityId)) {
                        if (testMode) {
                            testBuffer = dependencies;
                        } else {
                            MessageDialog
                                    .openInformation(workbenchWindow.getShell(),
                                            Messages.ActivityCapabilityAction_couldNotBeExecuted_label,
                                            Messages.ActivityCapabilityAction_Category_label
                                                    + " " + categoryName + ": \n\n" //$NON-NLS-1$//$NON-NLS-2$
                                                    + Messages.ActivityCapabilityAction_CategoryCannotBeRemovedBecauseOfTheFollowingDependencies_label
                                                    + "\n\n" + dependencies); //$NON-NLS-1$
                        }
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Set the category to be enabled or disabled. If the category is being
     * enabled then all required categories will also be enabled. The category
     * can be disabled only if all depending on it categories are disabled and
     * if it is not a case then message box will be displayed.
     * 
     * @param categoryId
     *            the id of the category to enable/disable.
     * @param enabled
     *            if category should be enabled or disabled.
     */
    @SuppressWarnings("unchecked")
    public static void setCategoryEnabled(String categoryId, boolean enabled) {
        IWorkbenchActivitySupport activitySupport =
                PlatformUI.getWorkbench().getActivitySupport();
        IActivityManager activityManager = activitySupport.getActivityManager();
        if (enabled) {
            Set<String> activitiesToEnable =
                    new HashSet<String>(activityManager.getEnabledActivityIds());
            boolean enabledChanged = false;
            // add all required categories' activities
            for (String reqCategory : getRequiredCategories(categoryId)) {
                boolean changed =
                        activitiesToEnable
                                .addAll(getCategoryActivities(reqCategory));
                if (changed) {
                    enabledChanged = true;
                }
            }
            // add all category's activities
            boolean changed =
                    activitiesToEnable
                            .addAll(getCategoryActivities(categoryId));
            if (changed) {
                enabledChanged = true;
            }

            if (enabledChanged) {
                activitySupport.setEnabledActivityIds(activitiesToEnable);
            }
        } else { // disable category
            Set<String> dependingCategories =
                    getDependingCategories(categoryId);
            Set<String> enabledDependingCategories =
                    getEnabledCategoriesSubset(dependingCategories);
            if (enabledDependingCategories.size() == 0) {
                HashSet<String> activitiesToEnable =
                        new HashSet<String>(
                                activityManager.getEnabledActivityIds());
                boolean enabledChanged =
                        activitiesToEnable
                                .removeAll(getCategoryActivities(categoryId));
                if (enabledChanged) {
                    activitySupport.setEnabledActivityIds(activitiesToEnable);
                }
            } else {

                String title =
                        Messages.ActivityCapabilityAction_couldNotBeExecuted_label;

                // build dependency list
                StringBuilder dependencies = new StringBuilder();
                int counter = 0;
                int maxDependenciesToShow = 10;
                for (String dependingCategoryId : enabledDependingCategories) {
                    ICategory category =
                            activityManager.getCategory(dependingCategoryId);
                    String categoryName = dependingCategoryId;
                    try {
                        categoryName = category.getName();
                    } catch (NotDefinedException e) {
                        // ignore the categoryName will be categoryId if name
                        // not defined
                    }
                    if (counter != 0) {
                        dependencies.append(',').append('\n');
                    }
                    dependencies.append(categoryName);
                    counter++;
                    if (counter == maxDependenciesToShow) {
                        dependencies.append(',').append('\n').append("..."); //$NON-NLS-1$
                        break;
                    }
                }

                ICategory category = activityManager.getCategory(categoryId);
                String categoryName = categoryId;
                try {
                    categoryName = category.getName();
                } catch (NotDefinedException e) {
                    // ignore the categoryName will be categoryId if name not
                    // defined
                }
                String message =
                        String.format(Messages.ActivitiesUtil_categoryCouldNotBeDisabled_message1,
                                categoryName,
                                dependencies.toString());
                MessageDialog.openInformation(PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getShell(), title, message);
            }
        }
    }

    /**
     * Returns the subset of enabled categories.
     * 
     * @param categoryIds
     *            the set of categorie's ids.
     * @return the subset of categoryIds containing only enabled categories.
     */
    private static Set<String> getEnabledCategoriesSubset(
            Set<String> categoryIds) {
        IActivityManager activityManager =
                PlatformUI.getWorkbench().getActivitySupport()
                        .getActivityManager();
        Set<String> result = new HashSet<String>();
        for (String catetoryId : categoryIds) {
            if (isCategoryEnabled(activityManager.getCategory(catetoryId))) {
                result.add(catetoryId);
            }
        }
        return result;
    }

    /**
     * Returns activities belonging to the category.
     * 
     * @param categoryId
     *            the category id.
     * @return the set of activity ids which belongs to the category.
     */
    @SuppressWarnings("unchecked")
    public static Set<String> getCategoryActivities(String categoryId) {
        IWorkbenchActivitySupport activitySupport =
                PlatformUI.getWorkbench().getActivitySupport();
        IActivityManager activityManager = activitySupport.getActivityManager();
        Set<String> result = new HashSet<String>();
        ICategory category = activityManager.getCategory(categoryId);
        Set<ICategoryActivityBinding> catBindings =
                category.getCategoryActivityBindings();
        for (ICategoryActivityBinding catBinding : catBindings) {
            result.add(catBinding.getActivityId());
        }
        return result;
    }

    /**
     * Returns all required categories of the category. The other category is
     * required if one of the category activities requires activity from the
     * other category. This relationship can be also transient.
     * 
     * @param categoryId
     *            the id of the category.
     * @return the set of required category ids.
     */
    @SuppressWarnings("unchecked")
    public static Set<String> getRequiredCategories(String categoryId) {
        IWorkbenchActivitySupport activitySupport =
                PlatformUI.getWorkbench().getActivitySupport();
        IActivityManager activityManager = activitySupport.getActivityManager();

        Map<String, Set<String>> activityCategories =
                new HashMap<String, Set<String>>();
        for (String catId : (Set<String>) activityManager
                .getDefinedCategoryIds()) {
            ICategory category = activityManager.getCategory(catId);
            Set<ICategoryActivityBinding> catBindings =
                    category.getCategoryActivityBindings();
            for (ICategoryActivityBinding catBinding : catBindings) {
                Set<String> actCat =
                        activityCategories.get(catBinding.getActivityId());
                if (actCat != null) {
                    actCat.add(catBinding.getCategoryId());
                } else {
                    actCat = new HashSet<String>();
                    actCat.add(catBinding.getCategoryId());
                    activityCategories.put(catBinding.getActivityId(), actCat);
                }
            }
        }

        Set<String> result = new HashSet<String>();
        Set<String> inputCats = new HashSet<String>();
        inputCats.add(categoryId);
        while (inputCats.size() > 0) {
            Set<String> dependentCategoriesId = new HashSet<String>();
            for (String catId : inputCats) {
                ICategory category = activityManager.getCategory(catId);
                Set<ICategoryActivityBinding> catBindings =
                        category.getCategoryActivityBindings();
                for (ICategoryActivityBinding categoryBinding : catBindings) {
                    String activityId = categoryBinding.getActivityId();
                    IActivity activity =
                            activityManager.getActivity(activityId);
                    Set<IActivityRequirementBinding> reqBindings =
                            activity.getActivityRequirementBindings();
                    for (IActivityRequirementBinding reqBinding : reqBindings) {
                        String reqActivityId =
                                reqBinding.getRequiredActivityId();
                        Set<String> reqActivityCat =
                                activityCategories.get(reqActivityId);
                        if (reqActivityCat != null) {
                            dependentCategoriesId.addAll(reqActivityCat);
                        }
                    }
                }
            }
            inputCats = new HashSet(dependentCategoriesId);
            inputCats.removeAll(result);
            result.addAll(dependentCategoriesId);
        }
        if (result.contains(categoryId)) {
            result.remove(categoryId);
        }
        return result;
    }

    /**
     * Returns set of category ids which depends on the provided category.
     * 
     * @param categoryId
     *            the category.
     * @return set of category ids which depends on the provided category.
     */
    @SuppressWarnings("unchecked")
    public static Set<String> getDependingCategories(String categoryId) {
        IWorkbenchActivitySupport activitySupport =
                PlatformUI.getWorkbench().getActivitySupport();
        IActivityManager activityManager = activitySupport.getActivityManager();

        // maps activity id to set of categories id
        Map<String, Set<String>> activityCategories =
                new HashMap<String, Set<String>>();
        for (String catId : (Set<String>) activityManager
                .getDefinedCategoryIds()) {
            ICategory category = activityManager.getCategory(catId);
            Set<ICategoryActivityBinding> catBindings =
                    category.getCategoryActivityBindings();
            for (ICategoryActivityBinding catBinding : catBindings) {
                Set<String> actCat =
                        activityCategories.get(catBinding.getActivityId());
                if (actCat != null) {
                    actCat.add(catBinding.getCategoryId());
                } else {
                    actCat = new HashSet<String>();
                    actCat.add(catBinding.getCategoryId());
                    activityCategories.put(catBinding.getActivityId(), actCat);
                }
            }
        }
        Set<String> activitiesOfCategory = getCategoryActivities(categoryId);

        Set<String> result = new HashSet<String>();
        for (String activityId : (Set<String>) activityManager
                .getDefinedActivityIds()) {
            IActivity activity = activityManager.getActivity(activityId);
            Set<IActivityRequirementBinding> reqBindings =
                    activity.getActivityRequirementBindings();
            for (IActivityRequirementBinding reqBinding : reqBindings) {
                if (activitiesOfCategory.contains(reqBinding
                        .getRequiredActivityId())) {
                    result.addAll(activityCategories.get(reqBinding
                            .getActivityId()));
                }
            }
        }

        if (result.contains(categoryId)) {
            result.remove(categoryId);
        }
        return result;
    }

    /**
     * @param categoryName
     * @param activityId
     * @return
     */
    @SuppressWarnings("unchecked")
    String getDependencies(String categoryName, String activityId) {
        synchronized (ActivityUtil.class) {
            StringBuffer buf = new StringBuffer();
            Set<ICategory> categories = getCategories();
            for (ICategory category : categories) {
                String catName = null;
                try {
                    catName = category.getName();
                } catch (NotDefinedException e) {
                }
                if (catName == null || catName.equals(categoryName)) {
                    continue;
                }
                Set<ICategoryActivityBinding> bindings =
                        category.getCategoryActivityBindings();
                for (ICategoryActivityBinding categoryActivityBinding : bindings) {
                    if (categoryActivityBinding.getActivityId()
                            .equals(activityId)) {
                        try {
                            buf.append(Messages.ActivityCapabilityAction_Category_label);
                            buf.append(" "); //$NON-NLS-1$
                            buf.append(category.getName());
                        } catch (NotDefinedException e) {
                            buf.append(category.getId());
                        }
                        buf.append(","); //$NON-NLS-1$
                    } else {
                        IActivity activity =
                                getActivity(categoryActivityBinding
                                        .getActivityId());
                        Set<IActivityRequirementBinding> required =
                                activity.getActivityRequirementBindings();
                        for (IActivityRequirementBinding binding : required) {
                            String requiredId = binding.getRequiredActivityId();
                            if (requiredId.equals(activityId)) {
                                try {
                                    buf.append(category.getName());
                                } catch (NotDefinedException e) {
                                    buf.append(category.getId());
                                }
                                buf.append(",\n"); //$NON-NLS-1$
                                break;
                            }
                        }
                    }
                }
                if (buf.toString().endsWith(",\n")) { //$NON-NLS-1$
                    buf.deleteCharAt(buf.length() - 2);
                }
            }
            if (buf.length() > 0) {
                return buf.toString();
            }

            return null;
        }
    }

    /**
     * @param category
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean isCategoryEnabled(ICategory category) {
        Set<ICategoryActivityBinding> activities =
                category.getCategoryActivityBindings();
        for (ICategoryActivityBinding binding : activities) {
            if (!isActivityEnabled(binding.getActivityId())) {
                return false;
            }
        }
        if (activities.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * @param category
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean isCategoryEmpty(ICategory category) {
        Set<Object> activities = category.getCategoryActivityBindings();
        if (activities.isEmpty()) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public static boolean isActivityEnabled(String activityId) {
        synchronized (ActivityUtil.class) {
            IWorkbenchActivitySupport activitySupport =
                    PlatformUI.getWorkbench().getActivitySupport();
            IActivityManager activityManager =
                    activitySupport.getActivityManager();
            Set<String> enabledActivityIds =
                    activityManager.getEnabledActivityIds();
            return enabledActivityIds.contains(activityId);
        }
    }

    @SuppressWarnings("unchecked")
    public String[] getRequiredActivities(String activityId) {
        synchronized (ActivityUtil.class) {
            IWorkbenchActivitySupport activitySupport =
                    PlatformUI.getWorkbench().getActivitySupport();
            IActivityManager activityManager =
                    activitySupport.getActivityManager();
            IActivity activity = activityManager.getActivity(activityId);
            Set<IActivity> requiredActivities =
                    activity.getActivityRequirementBindings();
            String[] result = new String[requiredActivities.size()];
            int i = 0;
            for (IActivity activity2 : requiredActivities) {
                result[i++] = activity2.getId();
            }
            return result;
        }
    }

    /**
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Set<ICategory> getCategories() {
        synchronized (ActivityUtil.class) {
            IWorkbenchActivitySupport activitySupport =
                    PlatformUI.getWorkbench().getActivitySupport();
            IActivityManager activityManager =
                    activitySupport.getActivityManager();
            Set<String> categoryIds = activityManager.getDefinedCategoryIds();
            HashSet<ICategory> categories = new HashSet<ICategory>();
            for (String categoryId : categoryIds) {
                if (isStudioCategory(categoryId)) {
                    ICategory category =
                            activityManager.getCategory(categoryId);
                    categories.add(category);
                }
            }
            return categories;
        }
    }

    private static boolean isStudioCategory(String categoryId) {
        /* 'Non-BPM Features' category is always excluded from the dropdown. */
        return categoryId != null
                && categoryId.startsWith(XPD_ACTIVITIES_PREFIX)
                && !categoryId
                        .equals("com.tibco.xpd.bpm.simplified.ui.nonbpm.catetory"); //$NON-NLS-1$
    }

    /**
     * @return
     */
    public static ICategory getCategory(String id) {
        IWorkbenchActivitySupport activitySupport =
                PlatformUI.getWorkbench().getActivitySupport();
        IActivityManager activityManager = activitySupport.getActivityManager();
        return activityManager.getCategory(id);
    }

    /**
     * @param activityId
     * @return
     */
    public IActivity getActivity(String activityId) {
        synchronized (ActivityUtil.class) {
            IWorkbenchActivitySupport activitySupport =
                    PlatformUI.getWorkbench().getActivitySupport();
            IActivityManager activityManager =
                    activitySupport.getActivityManager();
            return activityManager.getActivity(activityId);
        }
    }

    public static void setTestMode(boolean testMode) {
        ActivitiesUtil.testMode = testMode;
    }

    public static String getTestBuffer() {
        return testBuffer;
    }

    private static String getCategoryDefaultEnablementStoreKey(
            ICategory category) {
        if (category != null && category.getId() != null
                && !category.getId().equals("")) {
            return CATEGORY_DEFAULT_PREFIX_STORE_KEY + category.getId();
        }
        return null;
    }

    /**
     * Returns the category enablement defined in the preference store for a
     * given category
     * 
     * @param category
     *            the category to get the enablement from
     * 
     * @return "ENABLED", "NOT_ENABLED" or null if it has not been defined
     **/
    public static String getCategoryEnablementDefined(ICategory category) {
        if (category != null) {
            // Get the preferences key
            String categoryDefaultEnablementKey =
                    ActivitiesUtil
                            .getCategoryDefaultEnablementStoreKey(category);
            if (categoryDefaultEnablementKey != null) {
                String enablement =
                        XpdResourcesUIActivator.getDefault()
                                .getPreferenceStore()
                                .getString(categoryDefaultEnablementKey);
                if (enablement != null && enablement.equals("")) {
                    return null;
                }
                return enablement;
            }
        }
        return null;
    }

    /**
     * Returns if the category enablement has been previously defined
     * 
     * @param category
     *            the category to get the enablement from
     * 
     * @return true if it has been defined, false otherwise
     **/
    public static boolean isCategoryEnablementDefined(ICategory category) {
        if (category != null) {
            String categoryEnablement =
                    ActivitiesUtil.getCategoryEnablementDefined(category);
            return categoryEnablement != null;
        }
        return false;
    }

    /**
     * Sets the category enablement in the preferences store
     * 
     * @param category
     *            the category to get the enablement from
     * @param enabled
     *            if the category is enabled or not
     * 
     **/
    public static void setCategoryEnablementInStore(ICategory category,
            boolean enabled) {
        String categoryDefaultEnablementStoreKey =
                ActivitiesUtil.getCategoryDefaultEnablementStoreKey(category);
        if (categoryDefaultEnablementStoreKey != null) {
            if (enabled) {
                XpdResourcesUIActivator
                        .getDefault()
                        .getPreferenceStore()
                        .setValue(categoryDefaultEnablementStoreKey,
                                ActivitiesUtil.CATEGORY_ENABLEMENT_ENABLED);
            } else {
                XpdResourcesUIActivator
                        .getDefault()
                        .getPreferenceStore()
                        .setValue(categoryDefaultEnablementStoreKey,
                                ActivitiesUtil.CATEGORY_ENABLEMENT_NOT_ENABLED);
            }
        }
    }

    public static Set<String> readCategoryDefaultEnablementIds() {
        if (categoryDefaultEnablementIds == null) {
            categoryDefaultEnablementIds = new HashSet<String>();
            IExtensionRegistry registry = Platform.getExtensionRegistry();
            if (registry != null) {
                IConfigurationElement[] elements =
                        registry.getConfigurationElementsFor(XpdResourcesUIActivator.ID,
                                CATEGORIES_EXTENSION);
                if (elements != null) {
                    for (int i = 0; i < elements.length; i++) {
                        IConfigurationElement element = elements[i];
                        if (element != null) {
                            // Get the Default Enablements
                            String categoryId =
                                    element.getAttribute(CATEGORY_ID);
                            if (categoryId != null) {
                                categoryDefaultEnablementIds.add(categoryId);
                            }
                        }
                    }
                }
            }
        }
        return categoryDefaultEnablementIds;
    }

}
