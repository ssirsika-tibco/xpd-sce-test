package com.tibco.xpd.xpdl2.commands;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Provide list of objects that have children that should be unique
 * 
 * @author wzurek
 */
public interface UniquenessScopeProvider {

    /**
     * Provied list of objects that have given feature and object contained by
     * this feature should be unique
     * 
     * @param owner
     * @param feature
     * @return
     */
    public EObject[] getUniquenessScope(EObject owner,
            EStructuralFeature feature);
}
