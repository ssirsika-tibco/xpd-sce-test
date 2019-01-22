package com.tibco.xpd.implementer.resources.xpdl2.properties;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.implementer.script.RestActivityAdapterFactory;
import com.tibco.xpd.implementer.script.RestActivityMessageProvider;
import com.tibco.xpd.xpdl2.Activity;

/**
 * Selects only REST Service activities that can have applied in mappings.
 * 
 * @author jarciuch
 */
public class RestActivityInMappingFilter implements IFilter {

    private RestServiceTaskAdapter rsta;

    public RestActivityInMappingFilter() {
        rsta = new RestServiceTaskAdapter();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean select(Object toTest) {
        EObject eo = null;
        if (toTest instanceof EObject) {
            eo = (EObject) toTest;
        } else if (toTest instanceof IAdaptable) {
            eo = (EObject) ((IAdaptable) toTest).getAdapter(EObject.class);
        }
        if (eo instanceof Activity
                && rsta.isRestServiceImplementation((Activity) eo)) {
            RestActivityMessageProvider messageAdapter =
                    RestActivityAdapterFactory.getInstance()
                            .getMessageProvider((Activity) eo);

            return messageAdapter != null
                    && messageAdapter.hasMappingIn((Activity) eo);
        }
        return false;
    }
}