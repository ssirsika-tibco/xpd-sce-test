/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.bom.resources.ui.internal.picker.stereotype;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMemento;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.edit.UMLEditPlugin;

import com.tibco.xpd.bom.resources.ui.Activator;
import com.tibco.xpd.bom.resources.wc.BOMWorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.ui.dialogs.FilteredMultiSelectionDialog;

/**
 * Picker used for picking complex types for <code>Stereotype</code> attributes.
 * This will show all <code>Element</code>s in the given <code>Resource</code>
 * with <code>Stereotype</code> applications of the given type.
 * 
 * @author njpatel
 * 
 */
public class ComplexTypePicker extends FilteredMultiSelectionDialog {

    private static final String MEMNTO_KEY = "complexpicker.uri"; //$NON-NLS-1$

    private final Resource resource;

    private Comparator<EObject> comparator;

    private Set<EObject> contents;

    private final ComplexTypePickerDetailsLabelProvider detailsLabelProvider;

    private final ComplexTypePickerLabelProvider labelProvider;

    private final Map<Object, EObject> refMap;

    private final EClass eClass;

    /**
     * Stereotype application picker.
     * 
     * @param shell
     *            parent <code>Shell</code>.
     * @param resource
     *            Resource to search for <code>Stereotype</code> applications.
     * @param eClass
     *            Type of <code>Element</code> to list.
     */
    public ComplexTypePicker(Shell shell, Resource resource, EClass eClass) {
        this(shell, false, resource, eClass);
    }

    /**
     * Stereotype application picker.
     * 
     * @param shell
     *            parent <code>Shell</code>.
     * @param multi
     *            <code>true</code> if multiple selection is allowed,
     *            <code>false</code> otherwise.
     * @param resource
     *            Resource to search for <code>Stereotype</code> applications.
     * @param eClass
     *            Type of <code>Element</code> to list.
     */
    public ComplexTypePicker(Shell shell, boolean multi, Resource resource,
            EClass eClass) {
        super(shell, multi);

        refMap = new HashMap<Object, EObject>();

        this.resource = resource;
        Assert.isNotNull(resource, "Resource"); //$NON-NLS-1$
        this.eClass = eClass;
        Assert.isNotNull(eClass, "EClass"); //$NON-NLS-1$

        // Set the label providers
        labelProvider = new ComplexTypePickerLabelProvider();
        setListLabelProvider(labelProvider);
        setListSelectionLabelDecorator(labelProvider);
        detailsLabelProvider = new ComplexTypePickerDetailsLabelProvider();
        setDetailsLabelProvider(detailsLabelProvider);

        // Set the selection history
        setSelectionHistory(new SelectionHistory() {

            @Override
            protected Object restoreItemFromMemento(IMemento memento) {
                String uriStr = memento.getString(MEMNTO_KEY);
                Object obj = null;
                if (uriStr != null) {
                    URI uri = URI.createURI(uriStr);

                    if (uri != null) {
                        Set<EObject> content = getContent();

                        for (EObject eo : content) {
                            if (EcoreUtil.getURI(eo).equals(uri)) {
                                obj = eo;
                                break;
                            }
                        }
                    }
                }
                return obj;
            }

            @Override
            protected void storeItemToMemento(Object item, IMemento memento) {
                if (item instanceof EObject) {
                    URI uri = EcoreUtil.getURI((EObject) item);

                    if (uri != null) {
                        memento.putString(MEMNTO_KEY, uri.toString());
                    }
                }
            }
        });
        // setInitialPattern("?"); //$NON-NLS-1$
    }

    @Override
    protected ItemsFilter createFilter() {
        return new ShowAllWhenEmptyItemsFilter() {

            @Override
            public boolean isConsistentItem(Object item) {
                return item instanceof EObject;
            }

            @Override
            public boolean matchItem(Object item) {
                boolean match = false;

                if (item instanceof EObject) {
                    // Match name first..
                    match = matches(getElementName(item));
                    // ..if that fails then match qualified name
                    if (!match) {
                        String qualifiedName = getQualifiedName((EObject) item);

                        match = qualifiedName != null && matches(qualifiedName);
                    }
                }

                return match;
            }
        };
    }

    @Override
    protected void fillContentProvider(AbstractContentProvider contentProvider,
            ItemsFilter itemsFilter, IProgressMonitor progressMonitor)
            throws CoreException {

        Set<EObject> content = getContent();

        for (EObject eo : content) {
            contentProvider.add(eo, itemsFilter);
        }
    }

    /**
     * Get the content for this picker. Calls to this method will return the
     * same result as the result is cached during the first call.
     * 
     * @return Content or empty collection if no content found.
     */
    private Set<EObject> getContent() {
        if (contents == null) {
            contents = new HashSet<EObject>();

            TreeIterator<Object> iter =
                    EcoreUtil.getAllProperContents(resource, true);

            if (iter != null) {
                while (iter.hasNext()) {
                    Object obj = iter.next();

                    if (obj instanceof EObject
                            && this.eClass.isSuperTypeOf(((EObject) obj)
                                    .eClass())) {
                        contents.add((EObject) obj);
                    }
                }
            }
        }

        return contents;
    }

    @Override
    protected IDialogSettings getDialogSettings() {
        IDialogSettings settings =
                Activator.getDefault().getDialogSettings()
                        .getSection("complextype.picker"); //$NON-NLS-1$

        if (settings == null) {
            settings =
                    Activator.getDefault().getDialogSettings()
                            .addNewSection("complextype.picker"); //$NON-NLS-1$
        }

        return settings;
    }

    @Override
    public String getElementName(Object item) {
        String name = null;

        if (item instanceof EObject) {
            name = getName((EObject) item);
        }
        return name;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Comparator getItemsComparator() {

        if (comparator == null) {
            comparator = new Comparator<EObject>() {

                public int compare(EObject o1, EObject o2) {
                    int res = 0;
                    String o1Name = getElementName(o1);
                    String o2Name = getElementName(o2);

                    if (o1Name == null && o2Name != null) {
                        res = 1;
                    } else if (o1Name != null && o2Name == null) {
                        res = -1;
                    } else if (o1Name != null && o2Name != null) {
                        res = o1Name.compareTo(o2Name);
                    }

                    return res;
                }
            };
        }
        return comparator;
    }

    @Override
    protected IStatus validateItem(Object item) {
        return Status.OK_STATUS;
    }

    /**
     * Get the object that the give <code>Stereotype</code> application applies
     * to.
     * 
     * @param element
     *            <code>Stereotype</code> application
     * @return <code>EObject</code> that the application applies to,
     *         <code>null</code> if it cannot be determined.
     */
    protected EObject getReferencedObject(DynamicEObjectImpl element) {
        EObject eo = refMap.get(element);

        if (eo == null) {
            EList<EStructuralFeature> features =
                    element.eClass().getEAllStructuralFeatures();

            if (features != null) {
                for (EStructuralFeature feature : features) {
                    if (isExtension(feature)) {
                        Object obj = element.eGet(feature);

                        if (obj instanceof EObject) {
                            eo = (EObject) obj;
                            break;
                        }
                    }
                }
            }
            refMap.put(element, eo);
        }

        return eo;
    }

    /**
     * Check if the given feature is a stereotype extension.
     * 
     * @param feat
     *            <code>EStructuralFeature</code> to inspect.
     * @return <code>true</code> if it's an extension, <code>false</code> if
     *         it's not (i.e. it's a stereotype attribute).
     */
    private boolean isExtension(EStructuralFeature feat) {
        boolean ext = false;

        if (feat instanceof EReference) {
            EClass refType = ((EReference) feat).getEReferenceType();

            if (refType != null) {
                ext = ("base_" + refType.getName()) //$NON-NLS-1$
                        .equals(((EReference) feat).getName());
            }
        }

        return ext;
    }

    /**
     * Get the name of the given element. If this is a <code>Stereotype</code>
     * application then the name of the object it applies to will be returned.
     * 
     * @param element
     *            element to get name of.
     * @return name.
     */
    private String getName(EObject element) {
        String name = null;
        EObject eo = null;

        if (element instanceof DynamicEObjectImpl) {
            eo = getReferencedObject((DynamicEObjectImpl) element);
        }

        if (eo == null) {
            eo = element;
        }

        if (eo != null) {
            name = WorkingCopyUtil.getText(eo);
        }

        return name != null ? name : ""; //$NON-NLS-1$
    }

    /**
     * Get fully qualified name of the given element. If this is a Stereotype
     * application then the qualified name of the object it applies to will be
     * returned.
     * 
     * @param element
     *            element to get qualified name of.
     * @return qualified name.
     */
    private String getQualifiedName(EObject element) {
        String qualifiedName = getQualification(element);
        String name = getName(element);

        if (qualifiedName != null) {
            qualifiedName += BOMWorkingCopy.JAVA_PACKAGE_SEPARATOR + name;
        } else {
            qualifiedName = name;
        }
        return qualifiedName;
    }

    /**
     * Prefix the type name to the given string (e.g. &lt;Stereotype&gt;).
     * Typically used by the label providers to include type name.
     * 
     * @param name
     * @return name with type prefix.
     */
    private String prefixTypeName(String name) {
        return name != null ? String.format("<%s> %s", eClass.getName(), name) //$NON-NLS-1$
                : null;
    }

    /**
     * Get the qualification of the given element.
     * 
     * @param element
     * @return qualification, or <code>null</code> if element doesn't have a
     *         qualified name.
     */
    private String getQualification(EObject element) {
        String qualification = null;
        EObject eo = null;

        if (element instanceof DynamicEObjectImpl) {
            eo = getReferencedObject((DynamicEObjectImpl) element);
        }

        if (eo == null) {
            eo = element;
        }

        if (eo != null) {
            if (eo instanceof NamedElement) {
                qualification =
                        BOMWorkingCopy.getQualifiedClassName((NamedElement) eo);

                if (qualification.length() > 0
                        && qualification
                                .indexOf(BOMWorkingCopy.JAVA_PACKAGE_SEPARATOR) > 0) {
                    qualification =
                            qualification
                                    .substring(0,
                                            qualification
                                                    .lastIndexOf(BOMWorkingCopy.JAVA_PACKAGE_SEPARATOR));
                } else {
                    qualification = null;
                }
            }
        }
        return qualification;
    }

    /**
     * Complex type picker's details label provider.
     * 
     * @author njpatel
     * 
     */
    private class ComplexTypePickerDetailsLabelProvider extends LabelProvider {

        // Use stereotype image for all stereotype applications
        private final Image stereotypeImage;

        public ComplexTypePickerDetailsLabelProvider() {
            Object imgData =
                    UMLEditPlugin.getPlugin().getImage("full/obj16/Stereotype"); //$NON-NLS-1$

            stereotypeImage =
                    imgData != null ? ExtendedImageRegistry.getInstance()
                            .getImage(imgData) : null;
        }

        @Override
        public String getText(Object element) {
            String text = null;
            if (element instanceof EObject) {
                text = getQualifiedName((EObject) element);
            }

            return text != null ? prefixTypeName(text) : super.getText(element);
        }

        @Override
        public Image getImage(Object element) {
            Image img = null;

            if (element instanceof DynamicEObjectImpl) {
                img = stereotypeImage;
            } else if (element instanceof EObject) {
                img = WorkingCopyUtil.getImage((EObject) element);
            }

            return img != null ? img : super.getImage(element);
        }
    }

    /**
     * Complex type picker's label and selection label provider.
     * 
     * @author njpatel
     * 
     */
    private class ComplexTypePickerLabelProvider extends
            ComplexTypePickerDetailsLabelProvider implements ILabelDecorator {

        @Override
        public String getText(Object element) {
            String text = null;

            if (element instanceof EObject) {
                text = getName((EObject) element);

                if (isDuplicateElement(element)) {
                    text = decorateText(text, element);
                } else {
                    text = prefixTypeName(text);
                }
            }
            return text != null ? text : super.getText(element);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.ILabelDecorator#decorateImage(org.eclipse
         * .swt.graphics.Image, java.lang.Object)
         */
        public Image decorateImage(Image image, Object element) {
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.eclipse.jface.viewers.ILabelDecorator#decorateText(java.lang.
         * String, java.lang.Object)
         */
        public String decorateText(String text, Object element) {
            String qualifiedName = null;

            if (element instanceof EObject) {
                String name = getName((EObject) element);
                String qual = getQualification((EObject) element);

                if (name != null && qual != null) {
                    qualifiedName = String.format("%1$s - %2$s", name, qual); //$NON-NLS-1$
                } else if (name != null) {
                    qualifiedName = name;
                }
            }
            return prefixTypeName(qualifiedName);
        }
    }
}
