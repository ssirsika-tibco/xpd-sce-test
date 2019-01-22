/*
 * Copyright (c) TIBCO Software Inc 2004, 2013. All rights reserved.
 */
package com.tibco.xpd.resources.ui.internal.actions;

import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.InternalHandlerUtil;
import org.eclipse.ui.internal.handlers.SaveHandler;
import org.eclipse.ui.internal.util.Util;

/**
 * Save handler for the save command that is active when the properties section
 * is active. (XPD-5103)
 * <p>
 * In Eclipse 3.7 the global save handling has changed and so when a change is
 * made in the properties section for an element selected in the Project
 * Explorer (without editor open) then the save option does not get enabled
 * until you select the item in the Project Explorer. This is because the
 * default save handler (defined in <code>org.eclipse.ui</code>) assumes that
 * the source of the properties section is always going to be an editor.
 * </p>
 * 
 * @author njpatel
 * @since 3.6.1
 */
@SuppressWarnings("restriction")
public class PropertiesSectionSaveHandler extends SaveHandler {

    /**
     * @see org.eclipse.ui.internal.handlers.AbstractSaveHandler#getSaveablePart(org.eclipse.core.expressions.IEvaluationContext)
     * 
     * @param context
     * @return
     */
    @Override
    protected ISaveablePart getSaveablePart(IEvaluationContext context) {
        ISaveablePart part = super.getSaveablePart(context);

        if (part == null) {
            /*
             * This is what the getSaveablePart method that gets called during
             * the execution of this handler does so doing the same here to get
             * hold of the active part (should be the project explorer).
             */
            IWorkbenchPart activePart =
                    InternalHandlerUtil.getActivePart(context);
            if (activePart != null) {
                part =
                        (ISaveablePart) PropertiesSectionSaveHandler.getAdapter(activePart,
                                ISaveablePart.class);
            }
        }

        return part;
    }
    
    /**
     * If it is possible to adapt the given object to the given type, this
     * returns the adapter. Performs the following checks:
     * 
     * <ol>
     * <li>Returns <code>sourceObject</code> if it is an instance of the
     * adapter type.</li>
     * <li>If sourceObject implements IAdaptable, it is queried for adapters.</li>
     * <li>If sourceObject is not an instance of PlatformObject (which would have
     * already done so), the adapter manager is queried for adapters</li>
     * </ol>
     * 
     * Otherwise returns null.
     * 
     * @param sourceObject
     *            object to adapt, or null
     * @param adapterType
     *            type to adapt to
     * @return a representation of sourceObject that is assignable to the
     *         adapter type, or null if no such representation exists
     *         
     * Note: This method implementation is copied from: 
     * org.eclipse.ui.internal.util.Util 
     * It was in: org.eclipse.ui.workbench_3.106.1 (4.4-Luna release) 
     * but no longer exist in: org.eclipse.ui.workbench_3.110.1 (4.7 Oxygen)       
     */
    private static Object getAdapter(Object sourceObject, Class adapterType) {
    	Assert.isNotNull(adapterType);
        if (sourceObject == null) {
            return null;
        }
        if (adapterType.isInstance(sourceObject)) {
            return sourceObject;
        }

        if (sourceObject instanceof IAdaptable) {
            IAdaptable adaptable = (IAdaptable) sourceObject;

            Object result = adaptable.getAdapter(adapterType);
            if (result != null) {
                // Sanity-check
                Assert.isTrue(adapterType.isInstance(result));
                return result;
            }
        } 
        
        if (!(sourceObject instanceof PlatformObject)) {
            Object result = Platform.getAdapterManager().getAdapter(sourceObject, adapterType);
            if (result != null) {
                return result;
            }
        }

        return null;
    }

}
