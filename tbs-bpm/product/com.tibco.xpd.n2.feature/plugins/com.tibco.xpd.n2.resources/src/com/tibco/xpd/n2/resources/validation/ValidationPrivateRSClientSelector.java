package com.tibco.xpd.n2.resources.validation;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.validation.model.IClientSelector;

public class ValidationPrivateRSClientSelector implements IClientSelector {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.validation.model.IClientSelector#selects(java.lang.Object
     * )
     */
    public boolean selects(Object object) {
        if (object instanceof EObject) {
            Resource r = ((EObject) object).eResource();
            if (r != null && r.getResourceSet() instanceof N2ResourceSetImpl) {
                return true;
            }
        }
        return false;
    }

    public static ResourceSet createBRMResourceSet(Object data) {
        return new N2ResourceSetImpl(data);
    }

    public static class N2ResourceSetImpl extends ResourceSetImpl {
        private final Object data;

        public N2ResourceSetImpl(Object data) {
            this.data = data;

        }

        /**
         * @return the baseURI
         */
        public Object getData() {
            return data;
        }
    }
}
