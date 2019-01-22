package com.tibco.xpd.ui.properties.internal.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyRegistry;
import org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyRegistryFactory;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.eclipse.ui.views.properties.tabbed.ITabItem;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.xpd.resources.ui.util.ShowViewUtil;
import com.tibco.xpd.ui.properties.PropertiesPlugin;

/**
 * @author rassisi, njpatel
 * 
 */
public class XpdPropertiesUtil {

    /**
     * This flag is only needed for a JUnit test.
     */
    public static boolean showTabMethodWasCalledSuccessfully;

    /**
     * This method should not be used by applications. It has been made public
     * only for JUnit tests.
     * 
     * @param name
     * @deprecated Use {@link ShowViewUtil#showPropertyTab(String)} instead
     *             (uses property tab id instead of the label).
     */
    @Deprecated
    public static void _showTab(String name) {
        IViewPart viewer =
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage()
                        .findView("org.eclipse.ui.views.PropertySheet"); //$NON-NLS-1$
        if (viewer instanceof PropertySheet) {
            PropertySheet sheet = (PropertySheet) viewer;
            IPage currentPage = sheet.getCurrentPage();
            Control control = currentPage.getControl();
            if (control
                    .getClass()
                    .getName()
                    .equals("org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyComposite")) { //$NON-NLS-1$
                ArrayList<Control> allListElements = new ArrayList<Control>();
                findAllListElements(allListElements, control);
                for (Control control2 : allListElements) {
                    if (!control2.isDisposed()) {
                        String tabName = (String) control2.getData();
                        if (tabName.equals(name)) {
                            Event event = new Event();
                            event.type = SWT.MouseUp;
                            control2.notifyListeners(SWT.MouseUp, event);
                            showTabMethodWasCalledSuccessfully = true;
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * @param control
     * @return
     */
    static void findAllListElements(ArrayList<Control> map, Control control) {
        if (control instanceof Composite) {
            Control[] controls = ((Composite) control).getChildren();
            for (Control control2 : controls) {
                String name = control2.getClass().getName();
                if (name.equals("org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyList$ListElement")) { //$NON-NLS-1$
                    map.add(control2);
                    try {
                        Method m =
                                control2.getClass()
                                        .getDeclaredMethod("getTabItem", new Class[0]); //$NON-NLS-1$
                        try {
                            Object result = m.invoke(control2);
                            if (result instanceof ITabItem) {
                                control2.setData(((ITabItem) result).getText());
                            }
                        } catch (IllegalArgumentException e) {
                            PropertiesPlugin.getDefault().getLogger().error(e);
                            throw new AssertionError(
                                    "The API of the org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyList has changed."); //$NON-NLS-1$
                        } catch (IllegalAccessException e) {
                            PropertiesPlugin.getDefault().getLogger().error(e);
                        } catch (InvocationTargetException e) {
                            PropertiesPlugin.getDefault().getLogger().error(e);
                        }

                    } catch (SecurityException e1) {
                        PropertiesPlugin.getDefault().getLogger().error(e1);
                    } catch (NoSuchMethodException e1) {
                        PropertiesPlugin.getDefault().getLogger().error(e1);
                        throw new AssertionError(
                                "The API of the org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyList has changed."); //$NON-NLS-1$
                    }
                }
                findAllListElements(map, control2);
            }
        }
    }

    /**
     * Get the property sections that are contained in the given property sheet
     * page.
     * 
     * @param page
     *            <code>TabbedPropertySheetPage</code>.
     * @return array of <code>ISection</code>s, <code>null</code> if no sections
     *         found.
     */
    public static ISection[] getSections(TabbedPropertySheetPage page) {
        ISection[] sections = null;

        if (page != null) {
            sections = getSectionsFromTab(page.getCurrentTab());
        }
        return sections;
    }

    /**
     * Get sections from the given tab descriptor.
     * 
     * @param tabDescriptor
     *            tab descriptor
     * @return array of <code>ISection</code>s, or <code>null</code> if no
     *         sections found.
     */
    public static ISection[] getSections(ITabItem tabDescriptor) {
        ISection[] sections = null;

        if (tabDescriptor != null) {
            try {
                Method method = tabDescriptor.getClass().getMethod("createTab"); //$NON-NLS-1$
                sections = getSectionsFromTab(method.invoke(tabDescriptor));
            } catch (NoSuchMethodException e) {
                PropertiesPlugin.getDefault().getLogger().error(e);
                throw new AssertionError(
                        "API of org.eclipse.ui.internal.views.properties.tabbed.view.TabDescriptor has changed."); //$NON-NLS-1$
            } catch (Exception e) {
                PropertiesPlugin.getDefault().getLogger().error(e);
            }
        }

        return sections;
    }

    /**
     * Get the sections from the given tab.
     * 
     * @param tab
     *            tab object
     * @return array of <code>ISection</code>s.
     */
    private static ISection[] getSectionsFromTab(Object tab) {
        ISection[] sections = null;

        if (tab != null) {
            try {
                Method method = tab.getClass().getMethod("getSections"); //$NON-NLS-1$
                Object res = method.invoke(tab);

                if (res instanceof ISection[]) {
                    sections = (ISection[]) res;
                }
            } catch (NoSuchMethodException e) {
                PropertiesPlugin.getDefault().getLogger().error(e);
                throw new AssertionError(
                        "API of org.eclipse.ui.internal.views.properties.tabbed.view.Tab has changed."); //$NON-NLS-1$
            } catch (Exception e) {
                PropertiesPlugin.getDefault().getLogger().error(e);
            }
        }
        return sections;
    }

    /**
     * Get the tab descriptors for the given selection.
     * 
     * @param contributor
     *            tab property page contributor
     * @param selection
     *            selection for which the tab descriptors are required
     * @return array of <code>ITabItem</code>.
     */
    @SuppressWarnings("restriction")
    public static ITabItem[] getTabDescriptors(
            ITabbedPropertySheetPageContributor contributor,
            ISelection selection) {
        ITabItem[] items = null;
        if (contributor != null && selection != null) {
            TabbedPropertyRegistry registry =
                    TabbedPropertyRegistryFactory.getInstance()
                            .createRegistry(contributor);
            items = registry.getTabDescriptors(null, selection);
        }

        return items != null ? items : new ITabItem[0];
    }

    /**
     * Check whether the view is fast.
     * 
     * @param page
     *            workbench page
     * @param ref
     *            view part reference
     * @return <code>true</code> if the view is fast, <code>false</code>
     *         otherwise.
     * @deprecated FastViews were removed in eclipse 4.
     */
    @Deprecated
    @SuppressWarnings("restriction")
    public static boolean isFastView(IWorkbenchPage page, IViewReference ref) {
        boolean isFastView = false;

        // if (isInstanceofWorkbenchPage(page) && ref != null) {
        // isFastView = ((WorkbenchPage) page).isFastView(ref);
        // }

        return isFastView;
    }

    /**
     * Add a fast view.
     * 
     * @param page
     *            workbench page
     * @param ref
     *            view part reference
     * @deprecated FastViews were removed in eclipse 4.
     */
    @Deprecated
    @SuppressWarnings("restriction")
    public static void addFastView(IWorkbenchPage page, IViewReference ref) {
        // if (isInstanceofWorkbenchPage(page) && ref != null) {
        // ((WorkbenchPage) page).addFastView(ref);
        // }
    }

    /**
     * Remove a fast view.
     * 
     * @param page
     *            workbench page
     * @param ref
     *            view part reference
     * @deprecated FastViews were removed in eclipse 4.
     */
    @Deprecated
    @SuppressWarnings("restriction")
    public static void removeFastView(IWorkbenchPage page, IViewReference ref) {
        // if (isInstanceofWorkbenchPage(page) && ref != null) {
        // ((WorkbenchPage) page).removeFastView(ref);
        // }
    }

    /**
     * Toggles the visibility of a fast view. If the view is active it is
     * deactivated. Otherwise, it is activated.
     * 
     * @param page
     *            workbench page
     * @param ref
     *            view part reference
     * @deprecated FastViews were removed in eclipse 4.
     */
    @Deprecated
    @SuppressWarnings("restriction")
    public static void toggleFastView(IWorkbenchPage page, IViewReference ref) {
        // if (isInstanceofWorkbenchPage(page) && ref != null) {
        // ((WorkbenchPage) page).toggleFastView(ref);
        // }
    }

    /**
     * Check if the given object is an instanceof of
     * org.eclipse.ui.internal.WorkbenchPage.
     * 
     * @param page
     *            workbench page
     * @return <code>true</code> if the given page is an instanceof
     *         WorkbenchPage, <code>false</code> otherwise.
     */
    @SuppressWarnings("restriction")
    public static boolean isInstanceofWorkbenchPage(IWorkbenchPage page) {
        return page instanceof WorkbenchPage;
    }

}
