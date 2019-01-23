/*
 */
package com.tibco.xpd.bom.resources.ui.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.BOMImages;

/**
 */
public class GeneralizationItemProvider extends AbstractItemProvider {

    /**
     */
    public GeneralizationItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /**
     * 
     */
    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        return new ArrayList<IItemPropertyDescriptor>(0);
    }

    /**
     */
    public Object getImage(Object object) {
        return Activator.getDefault().getImageRegistry()
                .get(BOMImages.GENERALIZATION);
    }

    /**
     */
    public String getText(Object object) {
        Classifier general = getGeneralization(object).getGeneral();
        String text = general == null ? null : general.getQualifiedName();
        return text == null ? "" : text; //$NON-NLS-1$
    }

    private Generalization getGeneralization(Object object) {
        return ((Generalization) object);
    }

    public Collection<?> getChildren(Object object) {
        return Collections.EMPTY_LIST;
    }

    public Object getParent(Object object) {
        return getGeneralization(object).getSpecific();
    }

    @Override
    protected Command getSetCommand(Object object, EditingDomain editingDomain,
            CommandParameter parameter) {

        Command cmd = super.getSetCommand(object, editingDomain, parameter);

        EStructuralFeature structuralFeature =
                parameter.getEStructuralFeature();

        // Depending on destination environment, may need to set the default
        // subtype

        if (structuralFeature == UMLPackage.eINSTANCE
                .getGeneralization_General()) {
            System.out.println();
        }

        return cmd;

    }

}