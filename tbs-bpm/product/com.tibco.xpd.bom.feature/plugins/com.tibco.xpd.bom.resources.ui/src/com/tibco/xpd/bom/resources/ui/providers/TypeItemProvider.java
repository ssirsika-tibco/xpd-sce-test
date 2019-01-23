/*
 */
package com.tibco.xpd.bom.resources.ui.providers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.workspace.EMFCommandOperation;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.UMLFactory;

import com.tibco.xpd.bom.resources.ui.internal.Messages;
import com.tibco.xpd.bom.resources.ui.internal.properties.ItemPropertyDescriptorForModifier;
import com.tibco.xpd.bom.resources.ui.internal.properties.PropertyModifier;
import com.tibco.xpd.bom.resources.ui.picker.BOMPicker;
import com.tibco.xpd.bom.resources.ui.util.BomUIUtil;

/**
 */
public abstract class TypeItemProvider extends NamedElementItemProvider {

    protected List<IItemPropertyDescriptor> itemPropertyDescriptors;

    public TypeItemProvider(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    public List<IItemPropertyDescriptor> getPropertyDescriptors(Object object) {
        return new ArrayList<IItemPropertyDescriptor>(0);
    }

    /**
     * disabled due to lack of localisation of the boolean properties in the
     * sub-property descriptions
     */
    private void addSuperTypeProperty() {
        PropertyModifier modifier = new PropertyModifier() {
            public Object performAction(Control parentControl,
                    IItemPropertyDescriptor itemPropertyDescriptor,
                    Object object) {
                return BomUIUtil.getSuperclassFromPicker(parentControl
                        .getShell(), (EObject) object);
            }

            public Object getPropertyValue(Object object) {
                EList<Classifier> generals =
                        ((Classifier) object).getGenerals();
                return generals.isEmpty() ? null : generals.get(0);
            }

            public void setPropertyValue(Object object, Object value) {
                final Classifier cls = (Classifier) object;
                final Classifier superCls = (Classifier) value;
                TransactionalEditingDomain ed =
                        TransactionUtil.getEditingDomain(object);
                RecordingCommand cmd = new RecordingCommand(ed) {
                    @Override
                    protected void doExecute() {
                        cls.getGeneralizations().clear();
                        if (superCls != null) {
                            Generalization gen =
                                    UMLFactory.eINSTANCE.createGeneralization();
                            gen.setSpecific(cls);
                            gen.setGeneral(superCls);
                        }
                    }
                };
                try {
                    PlatformUI.getWorkbench().getOperationSupport()
                            .getOperationHistory()
                            .execute(new EMFCommandOperation(ed, cmd),
                                    new NullProgressMonitor(),
                                    null);
                } catch (ExecutionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            public void resetPropertyValue(Object object) {
                setPropertyValue(object, null);
            }
        };

        ItemPropertyDescriptorForModifier ipdm =
                new ItemPropertyDescriptorForModifier(getAdapterFactory(),
                        null, Messages.TypeItemProvider_superclass_label,
                        Messages.TypeItemProvider_superclass_shortdesc, true,
                        false, false, getImage(null), GENERAL_CATEGORY, null,
                        modifier);
        itemPropertyDescriptors.add(ipdm);
    }

    protected abstract BOMPicker getSuperTypePicker(Control parentControl);
}