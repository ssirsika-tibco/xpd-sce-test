/**
 * 
 */
package com.tibco.xpd.bom.resources.ui.providers;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.ProfileApplication;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.edit.providers.UMLItemProviderAdapterFactory;
import org.eclipse.uml2.uml.util.UMLSwitch;

import com.tibco.xpd.bom.resources.BOMResourcesPlugin;

/**
 * 
 * An EMF adapter factory for BOM specific item provider adapters for the UML
 * model. Please note that not all objects have adapter here, if some model
 * doesn't - the creation is delegated to standard uml adapter factory.
 * 
 * @author wzurek
 * 
 */
public class BOMItemProviderAdapterFactory extends AdapterFactoryImpl implements
        IDisposable {

    private static final String BOM_PRIMITIVE_TYPES_LIBRARY_UML = "BomPrimitiveTypes.library.uml"; //$NON-NLS-1$

    /** List of supported types */
    protected Collection<Object> supportedTypes = new ArrayList<Object>();

    /** fallback adapter factory */
    private UMLItemProviderAdapterFactory umlAdapterFactory = new UMLItemProviderAdapterFactory();

    /**
     * The constructor.
     */
    public BOMItemProviderAdapterFactory() {
        supportedTypes.add(IStructuredItemContentProvider.class);
        supportedTypes.add(ITreeItemContentProvider.class);
        supportedTypes.add(IItemLabelProvider.class);
        supportedTypes.add(IItemPropertySource.class);
        supportedTypes.add(IEditingDomainItemProvider.class);
        supportedTypes.add(UMLPackage.eINSTANCE);
    }

    public void dispose() {
        umlAdapterFactory.dispose();
    }

    /** uml switch that will direct the request to the specific factory method */
    private UMLSwitch<Adapter> umlSwitch = new UMLSwitch<Adapter>() {

        @Override
        public Adapter casePackage(Package object) {
            return createPackageAdapter(object);
        }

        @Override
        public Adapter caseClass(Class object) {
            return createClassAdapter(object);
        }

        @Override
        public Adapter casePrimitiveType(PrimitiveType object) {
            return createPrimitiveTypeAdapter(object);
        }

        @Override
        public Adapter caseProperty(Property object) {
            return createPropertyAdapter(object);
        }

        @Override
        public Adapter caseAssociation(Association object) {
            return createAssociationAdapter(object);
        }

        @Override
        public Adapter caseGeneralization(Generalization object) {
            return createGeneralizationAdapter(object);
        }

        @Override
        public Adapter caseOperation(Operation object) {
            return createOperationAdapter(object);
        }

        @Override
        public Adapter caseEnumeration(Enumeration object) {
            return createEnumerationAdapter(object);
        }

        @Override
        public Adapter caseEnumerationLiteral(EnumerationLiteral object) {
            return createEnumLitAdapter(object);
        }

        @Override
        public Adapter caseProfileApplication(ProfileApplication object) {
            // TODO Auto-generated method stub
            return createProfileApplicationAdapter(object);
        }

    };

    @Override
    public boolean isFactoryForType(Object type) {
        return supportedTypes.contains(type);
    }

    protected Adapter createProfileApplicationAdapter(ProfileApplication object) {
        return new ProfileApplicationItemProvider(this);
    }

    protected Adapter createEnumerationAdapter(Enumeration object) {
        return new EnumerationItemProvider(this);
    }

    protected Adapter createEnumLitAdapter(EnumerationLiteral object) {
        return new EnumLitItemProvider(this);
    }

    protected Adapter createAssociationAdapter(Association object) {
        return new AssociationItemProvider(this);
    }

    protected Adapter createGeneralizationAdapter(Generalization object) {
        return new GeneralizationItemProvider(this);
    }

    protected Adapter createPropertyAdapter(Property object) {
        return new PropertyItemProvider(this);
    }

    protected Adapter createOperationAdapter(Operation object) {
        return new OperationItemProvider(this);
    }

    protected Adapter createPackageAdapter(Package object) {
        return new PackageItemProvider(this);
    }

    protected Adapter createPrimitiveTypeAdapter(PrimitiveType object) {
        return new PrimitiveTypeItemProvider(this);
    }

    protected Adapter createClassAdapter(Class object) {
        return new ClassItemProvider(this);
    }

    /**
     * create BOM adapter using the switch and factory methods, if failed -
     * forward to the uml adapter factory.
     */
    @Override
    protected Adapter createAdapter(Notifier target) {
        Adapter adapter = null;

        if (target instanceof EObject && isBOMObject((EObject) target)) {
            adapter = umlSwitch.doSwitch((EObject) target);
        }

        if (adapter == null) {
            /*
             * Fallback to the base uml adapter factory if adapter not found or
             * the target object is not in a BOM resource
             */
            adapter = umlAdapterFactory.adapt(target, IItemLabelProvider.class);
        }

        return adapter;
    }

    /**
     * Check if the given object comes from a BOM resource.
     * 
     * @param object
     * @return <code>false</code> if the resource of the EObject can be
     *         determined and is not a BOM file, <code>true</code> otherwise.
     */
    private boolean isBOMObject(EObject object) {
        boolean result = true;

        Resource resource = object.eResource();
        if (resource != null) {
            IFile file = WorkspaceSynchronizer.getFile(resource);
            if (file != null) {
                result = BOMResourcesPlugin.BOM_FILE_EXTENSION
                        .equalsIgnoreCase(file.getFileExtension());

                if (!result) {
                    // special case for build-in types
                    if (BOM_PRIMITIVE_TYPES_LIBRARY_UML.equals(file.getName())) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }
}
