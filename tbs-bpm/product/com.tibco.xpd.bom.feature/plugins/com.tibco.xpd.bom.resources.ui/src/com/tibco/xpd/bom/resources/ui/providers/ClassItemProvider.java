/*
 */
package com.tibco.xpd.bom.resources.ui.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.emf.type.core.ClientContextManager;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.globaldata.api.BOMGlobalDataUtils;
import com.tibco.xpd.bom.resources.firstclassprofiles.FirstClassProfileManager;
import com.tibco.xpd.bom.resources.firstclassprofiles.IFirstClassProfileExtension;
import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.ui.BOMImages;
import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.resources.ui.picker.BOMPicker;
import com.tibco.xpd.bom.resources.ui.picker.BOMPicker.BOMPickerType;

/**
 * ItemProvider adapter for UML class
 * 
 * @author wzurek
 */
public class ClassItemProvider extends TypeItemProvider {

    public enum ChildrenFeature {
        ATTRIBUTE
    }

    public ClassItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.emf.edit.provider.IItemLabelProvider#getImage(java.lang.Object
     * )
     */
    @Override
    public Object getImage(Object object) {
        Image img = null;
        if (object instanceof Class) {
            Class cl = (Class) object;

            // If class has first-class stereotype then set its image if
            // available
            img = getFirstClassStereotypeImage(cl);
            if (null == img) {
                if (BOMGlobalDataUtils.isCaseClass(cl)) {
                    img =
                            Activator.getDefault().getImageRegistry()
                                    .get(BOMImages.CASE_CLASS);
                } else if (BOMGlobalDataUtils.isGlobalClass(cl)) {
                    img =
                            Activator.getDefault().getImageRegistry()
                                    .get(BOMImages.GLOBAL_CLASS);
                }
            }
        }

        return img != null ? img : Activator.getDefault().getImageRegistry()
                .get(BOMImages.CLASS);
    }

    /**
     */
    @Override
    public String getText(Object object) {
        String text = super.getText(object);

        if (text == null || text.equals("")) { //$NON-NLS-1$
            text = Messages.ClassItemProvider_EmptyClassName_label;
        }

        return text;

    }

    private Class getClass(Object object) {
        return ((org.eclipse.uml2.uml.Class) object);
    }

    @Override
    public Collection<?> getChildren(Object object) {
        LinkedList<Element> result = new LinkedList<Element>();
        result.addAll(getClass(object).getOwnedAttributes());
        result.addAll(getClass(object).getOwnedOperations());
        return result;
    }

    @Override
    public Object getParent(Object object) {
        return getClass(object).getPackage();
    }

    @Override
    public Collection<?> getNewChildDescriptors(Object object,
            EditingDomain editingDomain, Object sibling) {
        IClientContext cc =
                ClientContextManager.getInstance()
                        .getClientContextFor((EObject) object);

        List<CommandParameter> result = new ArrayList<CommandParameter>();
        result.add(new CommandParameter(null, ElementTypeRegistry.getInstance()
                .getElementType(UMLPackage.Literals.PROPERTY, cc), null));

        Class cl = null;
        if (object instanceof Class) {
            cl = (Class) object;

        }

        IFirstClassProfileExtension ext =
                FirstClassProfileManager.getInstance()
                        .getAppliedFirstClassProfile(cl.getModel());
        if (ext == null || ext.showOperations()) {
            /* Sid ACE-3625 Remove Add Child -> Operation for classes in project explorer menu */
            // result.add(new CommandParameter(null, ElementTypeRegistry
            // .getInstance()
            // .getElementType(UMLPackage.Literals.OPERATION, cc), null));

        }

        return result;
    }

    @Override
    protected BOMPicker getSuperTypePicker(Control parentControl) {
        return new BOMPicker(parentControl.getShell(), BOMPickerType.CLASS);
    }
}