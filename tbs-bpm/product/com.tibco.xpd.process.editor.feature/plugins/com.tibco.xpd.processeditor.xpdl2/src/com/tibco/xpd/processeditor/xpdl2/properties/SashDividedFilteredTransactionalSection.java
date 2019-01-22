/**
 * 
 */
package com.tibco.xpd.processeditor.xpdl2.properties;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.activities.WorkbenchActivityHelper;

import com.tibco.xpd.ui.properties.AbstractEObjectSection;
import com.tibco.xpd.ui.properties.SashDividedTransactionalSection;

/**
 * @author NWilson
 *
 */
public abstract class SashDividedFilteredTransactionalSection extends
        SashDividedTransactionalSection implements IFilter {

    protected final EClass eClass;

    public SashDividedFilteredTransactionalSection(EClass eClass, String actionIdPrefix) {
        super(actionIdPrefix);
        this.eClass = eClass;
    }
    
    public boolean select(Object toTest) {
        if (getBaseSelectObject(toTest) != null) {
            return true;
        }
        return false;
    }

    /**
     * Return the toTest object if it is of the EClass type that this section
     * was constructed with <i>and it is enabledInCapabilities()</i>.
     * <p>
     * This is especially useful for sub-filtering of selection (where the
     * property sheet is included only for a particular sub-type of the
     * selection object).
     * <p>
     * See {@link AbstractEObjectSection#select(Object)} for more details.
     * 
     * @param toTest
     * 
     * @return <b>toTest</b> object if it matches base EClass that section was
     *         constructed with <b>or null</b> if it does not match or is not
     *         enabled in capabilities.</b>
     */
    public EObject getBaseSelectObject(Object toTest) {
        if (enabledInCapabilities()) {
            EObject eo = null;
            if (toTest instanceof EObject) {
                eo = (EObject) toTest;
            } else if (toTest instanceof IAdaptable) {
                eo = (EObject) ((IAdaptable) toTest).getAdapter(EObject.class);
            }
            if (eo != null && eClass != null) {
                if (eClass.isSuperTypeOf(eo.eClass())) {
                    return eo;
                }
            }
        }
        return null;
    }

    /**
     * Check if the contribution provided by section should be enabled
     * concerning current set of capabilities.
     * 
     * @return true if section should be enabled, false otherwise.
     * 
     */
    protected boolean enabledInCapabilities() {
        IPluginContribution pluginContributon = getPluginContributon();
        if (pluginContributon != null
                && WorkbenchActivityHelper.filterItem(pluginContributon)) {
            return false;
        }
        return true;
    }
    
    /**
     * Returns plug-in contribution descriptor if the section should be
     * capabilities enabled (highly recommended) or null otherwise. <p/>
     * Returned descriptor should reflect plug-in contribution which contributes
     * this section so the <code>getPluginId()</code> should return id of the
     * contributing plug-in and <code>getLocalId()</code> should return id of
     * the section declared in the plug-in. It is also important to properly
     * override <code>AbstractEObjectSection.select(Object)</code> method to
     * properly support capabilities.
     * 
     * @return plug-in contribution descriptor if the section should be
     *         capabilities enabled (highly recommended) or null otherwise.
     * @see IPluginContribution
     * @see AbstractEObjectSection#select(Object)
     */
    public IPluginContribution getPluginContributon() {
        return null;
    }
    
    @Override
    protected EObject resollveInput(Object object) {
        if (object instanceof EObject) {
            return (EObject) object;
        }
        if (object instanceof IAdaptable) {
            return (EObject) ((IAdaptable) object).getAdapter(EObject.class);
        }
        return null;
    }
}
