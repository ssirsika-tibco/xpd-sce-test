/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.bom.modeler.custom.parsers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserOptions;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.modeler.custom.commands.SetDisplayLabelCommand;
import com.tibco.xpd.bom.resources.firstclassprofiles.FirstClassProfileManager;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * 
 */
public class NameRoleParser implements ISemanticParser {

    private final EAttribute attrib;

    private final List<EStructuralFeature> affectingFeatures;

    /**
     * The Constructor
     * 
     * @param index
     *            index of the association end to display/edit
     */
    public NameRoleParser(EAttribute attrib,
            List<EStructuralFeature> affectingFeatures) {
        this.attrib = attrib;
        this.affectingFeatures = affectingFeatures;
    }

    public String getEditString(IAdaptable element, int flags) {
        EObject obj = getElement(element);

        if (obj instanceof NamedElement) {
            NamedElement ne = (NamedElement) obj;
            return PrimitivesUtil.getDisplayLabel(ne);
        }

        return ""; //$NON-NLS-1$
    }

    public ICommand getParseCommand(IAdaptable element, String newString,
            int flags) {

        // TODO: BOMDisplayLabel
        EObject obj = getElement(element);

        if (obj instanceof NamedElement) {
            final NamedElement ne = (NamedElement) obj;
            TransactionalEditingDomain ed =
                    TransactionUtil.getEditingDomain(ne);
            Resource resource = ne.eResource();

            if (resource != null) {
                IFile file = WorkspaceSynchronizer.getFile(resource);

                return new SetDisplayLabelCommand(ed, file, ne, newString);
            }
        }

        return UnexecutableCommand.INSTANCE;

    }

    public boolean isAffectingEvent(Object event, int flags) {
        if (event instanceof Notification) {
            Notification n = (Notification) event;

            if (n.getFeature() instanceof EAttribute) {
                EAttribute ea = (EAttribute) n.getFeature();

                // Check for changes to attribute name or multiplicity values.
                return ea == UMLPackage.Literals.NAMED_ELEMENT__NAME
                        || ea == UMLPackage.Literals.LITERAL_UNLIMITED_NATURAL__VALUE
                        || ea == UMLPackage.Literals.LITERAL_INTEGER__VALUE;

            }
        }
        return false;
    }

    public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
        return null;
    }

    public String getPrintString(IAdaptable element, int flags) {
        String text = null;
        List<?> lstStereos = null;
        final FirstClassProfileManager firstClassManager =
                FirstClassProfileManager.getInstance();

        EObject eo = getElement(element);

        if (eo instanceof Association) {
            Association assoc = (Association) eo;
            text = PrimitivesUtil.getDisplayLabel(assoc);
            if (!firstClassManager.isFirstClassProfileApplied(assoc.getModel())) {
                lstStereos = assoc.getAppliedStereotypes();
            }
        }

        if (eo instanceof EnumerationLiteral) {
            EnumerationLiteral enLit = (EnumerationLiteral) eo;
            text = PrimitivesUtil.getDisplayLabel(enLit);
            if (!firstClassManager.isFirstClassProfileApplied(enLit.getModel())) {
                lstStereos = enLit.getAppliedStereotypes();
            }
        }

        if (eo instanceof Property) {
            Property prop = (Property) eo;

            if (ParserOptions.isSet(flags, ParserOptions.SHOW_TYPE)) {
                if (prop.getType() == null) {
                    text = PrimitivesUtil.getDisplayLabel(prop);
                } else if (prop.getType() instanceof Classifier) {
                    // Return the name of the type, used to do this fully
                    // qualified, but now we do it via a tooltip
                    Classifier cl = (Classifier) prop.getType();

                    if (cl != null && cl.eIsProxy() == false) {
                        text = PrimitivesUtil.getDisplayLabel(prop) + " : "; //$NON-NLS-1$
                        text = text + PrimitivesUtil.getDisplayLabel(cl);
                    } else {
                        text = PrimitivesUtil.getDisplayLabel(prop);
                    }
                } else {
                    text = WorkingCopyUtil.getText(eo);
                }
            } else {
                text = prop.getLabel();
            }

            // Only check for stereotype list if this is NOT a Concept Model
            if (!firstClassManager.isFirstClassProfileApplied(prop.getModel())) {
                lstStereos = prop.getAppliedStereotypes();
            } else {
                lstStereos = null;
            }

        }

        // Check for stereotypes
        String stereos = ""; //$NON-NLS-1$

        if (lstStereos != null) {
            if (lstStereos.size() > 0) {
                int idx = 0;
                for (Object ob : lstStereos) {
                    Stereotype nv = (Stereotype) ob;

                    Profile profile = nv.getProfile();

                    if (profile != null && profile.eResource() != null) {
                        URI uri = profile.eResource().getURI();
                        if (uri != null && !uri.isPlatformResource()) {
                            continue;
                        }
                    }

                    if (idx == 0) {
                        stereos = nv.getName();
                    } else {
                        stereos = stereos + ", " + nv.getName(); //$NON-NLS-1$
                    }
                    idx++;
                }

                if (idx > 0) {
                    if (eo instanceof Association
                            || eo instanceof EnumerationLiteral) {
                        text = "<<" + stereos + ">>" + " " + text; //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
                    } else if (eo instanceof Property) {
                        text = "<<" + stereos + ">>" + " " + text; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
                    }
                }

            }
        }

        return text;
    }

    public IParserEditStatus isValidEditString(IAdaptable element,
            String editString) {
        return ParserEditStatus.EDITABLE_STATUS;
    }

    protected EObject getElement(IAdaptable element) {
        return (EObject) element.getAdapter(EObject.class);
    }

    public boolean areSemanticElementsAffected(EObject listener,
            Object notification) {
        if (notification instanceof Notification) {
            Notification n = (Notification) notification;
            if (affectingFeatures.contains(n.getFeature())) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public List getSemanticElementsBeingParsed(EObject element) {
        if (element instanceof Property) {
            ArrayList<EObject> list = new ArrayList<EObject>();
            list.add(element);
            Type type = ((Property) element).getType();
            if (type != null) {
                list.add(type);

                Package package1 = type.getPackage();
                while (package1 != null) {
                    list.add(package1);
                    package1 = package1.getNestingPackage();
                }
            }
            if (((Property) element).getLowerValue() != null) {
                list.add(((Property) element).getLowerValue());
            }
            if (((Property) element).getUpperValue() != null) {
                list.add(((Property) element).getUpperValue());
            }
            return (list);
        }
        return Collections.singletonList(element);
    }

}
