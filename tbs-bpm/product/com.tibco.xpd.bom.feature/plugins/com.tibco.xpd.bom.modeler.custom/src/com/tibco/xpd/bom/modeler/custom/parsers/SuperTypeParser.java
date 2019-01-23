package com.tibco.xpd.bom.modeler.custom.parsers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.resources.utils.UML2ModelUtil;
import com.tibco.xpd.bom.types.PrimitivesUtil;

public class SuperTypeParser implements ISemanticParser {

    @Override
    public boolean areSemanticElementsAffected(EObject listener,
            Object notification) {
        if (notification instanceof Notification) {
            Notification n = (Notification) notification;

            if (n.getFeatureID(UMLPackage.class) == UMLPackage.GENERALIZATION_SET) {
                // Check its the superclass of this class!
                if (listener instanceof Classifier) {
                    Classifier clf = (Classifier) listener;
                    // The notifier needs to tell itself that it has just
                    // added a generalization to itself and needs to
                    // refresh its label
                    if (clf == n.getNotifier()) {
                        return true;
                    }
                }
            }

            // Below is used to rebuild the list of classes being listened to
            // Returning true will make the HandleNotification method rebuild
            // its list of semantic listeners and pick up any new class in this
            // model. We can then listen out for name changes.
            int eventType = n.getEventType();

            switch (eventType) {
            case Notification.ADD:
                if ((listener instanceof Class && (n.getNewValue() instanceof Class))
                        || (listener instanceof PrimitiveType && (n
                                .getNewValue() instanceof PrimitiveType))
                        || (listener instanceof Enumeration && (n.getNewValue() instanceof Enumeration))) {
                    return true;
                }
            case Notification.REMOVE:
                // TODO: Check if we need to check for a Package deletion
                // here as well
                if ((listener instanceof Class && (n.getOldValue() instanceof Class))
                        || (listener instanceof PrimitiveType && (n
                                .getOldValue() instanceof PrimitiveType))
                        || (listener instanceof Enumeration && (n.getNewValue() instanceof Enumeration))) {
                    return listener != n.getOldValue();
                }
            case Notification.RESOLVE:
                // TODO: Check if we need to check for a Package deletion
                // here as well
                if ((listener instanceof Class && (n.getOldValue() instanceof Class))
                        || (listener instanceof PrimitiveType && (n
                                .getOldValue() instanceof PrimitiveType))
                        || (listener instanceof Enumeration && (n.getNewValue() instanceof Enumeration))) {
                    return true;
                }

            }

            // And here is our actual check for a name
            if ((listener instanceof Class && (n.getNotifier() instanceof Class))
                    || (listener instanceof PrimitiveType && (n.getNotifier() instanceof PrimitiveType))) {
                if (n.getFeature() instanceof EAttribute) {
                    EAttribute ea = (EAttribute) n.getFeature();

                    // Check for changes to attribute name.
                    if (ea == UMLPackage.Literals.NAMED_ELEMENT__NAME) {
                        return true;
                    }

                }
            }

        }
        return false;
    }

    @Override
    public List<?> getSemanticElementsBeingParsed(EObject element) {
        // The name of this method is not very enlightening!
        // Check the javadocs for a better description.
        // Basically, we build up a list of all the semantic
        // elements that our EObject element and Parser need to listen to.
        if (element instanceof Classifier) {
            ArrayList<EObject> list = new ArrayList<EObject>();
            list.add(element);

            Classifier cl = (Classifier) element;
            List<Generalization> gens = cl.getGeneralizations();

            if (gens.size() > 0) {
                Generalization gen = cl.getGeneralizations().get(0);
                Classifier superClass = gen.getGeneral();
                list.add(gen);

                // NOTE: The superclass may live in a different model
                // so we can set the listener to it here
                list.add(superClass);
            }

            return (list);
        }
        return Collections.singletonList(element);
    }

    @Override
    public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getEditString(IAdaptable element, int flags) {
        EObject prop = getElement(element);

        if (prop instanceof Classifier) {
            Classifier cl = (Classifier) prop;
            List<Generalization> gens = cl.getGeneralizations();

            if (gens.size() > 0) {
                Generalization gen = cl.getGeneralizations().get(0);
                Classifier superClass = gen.getGeneral();

                return superClass.getQualifiedName();
            } else {
                return ""; //$NON-NLS-1$
            }
        }
        return ""; //$NON-NLS-1$
    }

    protected EObject getElement(IAdaptable element) {
        return (EObject) element.getAdapter(EObject.class);
    }

    @Override
    public ICommand getParseCommand(IAdaptable element, String newString,
            int flags) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPrintString(IAdaptable element, int flags) {
        EObject obj = getElement(element);

        // if (obj instanceof Class) {
        // Class cl = (Class) obj;
        // return getClassPrintString(cl);
        // }

        if (obj instanceof PrimitiveType) {
            PrimitiveType prim = (PrimitiveType) obj;
            return getPrimitiveTypePrintString(prim);
        }

        if (obj instanceof Classifier) {
            Classifier cl = (Classifier) obj;
            return getClassifierPrintString(cl);
        }

        return ""; //$NON-NLS-1$

    }

    private String getPrimitiveTypePrintString(PrimitiveType prim) {
        String text = ""; //$NON-NLS-1$
        List<Generalization> gens = prim.getGeneralizations();

        if (gens.size() > 0) {
            Generalization gen = prim.getGeneralizations().get(0);
            Classifier superType = gen.getGeneral();

            if (superType != null && !superType.eIsProxy()) {

                text = superType.getQualifiedName();

                if (text.indexOf("BomPrimitiveTypes::") != -1) { //$NON-NLS-1$
                    // Get the label of the primitive type
                    text = PrimitivesUtil.getDisplayLabel(superType);
                } else {
                    // Need to check whether to display the qualified or
                    // short name.
                    // Get the Model
                    Model modType = prim.getModel();
                    Model modSuperType = superType.getModel();
                    ArrayList<PrimitiveType> foundNames =
                            new ArrayList<PrimitiveType>();

                    UML2ModelUtil.searchPackageForPrimType(modType,
                            superType.getName(),
                            foundNames);

                    if (modSuperType == modType) {
                        // Its going to find itself so check > 1
                        if (foundNames.size() > 1) {
                            text = UML2ModelUtil.getQualifiedLabel(superType);
                        } else {
                            text = PrimitivesUtil.getDisplayLabel(superType);
                        }
                    } else {
                        if (foundNames.size() > 0) {
                            text = UML2ModelUtil.getQualifiedLabel(superType);
                        } else {
                            text = PrimitivesUtil.getDisplayLabel(superType);
                        }
                    }
                }
            }

        }
        return text;
    }

    private String getClassifierPrintString(Classifier subClassifier) {
        String text = ""; //$NON-NLS-1$
        List<Generalization> gens = subClassifier.getGeneralizations();

        if (gens.size() > 0) {
            Generalization gen = subClassifier.getGeneralizations().get(0);
            Classifier superClassifier = gen.getGeneral();

            if (!superClassifier.eIsProxy()) {
                Model modClassifier = subClassifier.getModel();

                // Now set the superclass name
                // Need to check whether to display the qualified or
                // short name.
                boolean useQualifiedName = false;
                List<Classifier> lstClassifiers = new ArrayList<Classifier>();
                UML2ModelUtil.searchPackageForClassifer(modClassifier,
                        superClassifier.getName(),
                        lstClassifiers);

                if (superClassifier instanceof Enumeration) {
                    List<Classifier> classes = new ArrayList<Classifier>();
                    UML2ModelUtil.searchPackageForEnumeration(modClassifier,
                            superClassifier.getName(),
                            classes);

                    if (classes.size() > 1) {
                        useQualifiedName = true;
                    }

                } else if (superClassifier instanceof Class) {
                    List<Class> classes = new ArrayList<Class>();
                    UML2ModelUtil.searchPackage(modClassifier,
                            superClassifier.getName(),
                            classes);
                    if (classes.size() > 1) {
                        useQualifiedName = true;
                    }

                } else if (superClassifier instanceof PrimitiveType) {
                    List<PrimitiveType> primTypes =
                            new ArrayList<PrimitiveType>();
                    UML2ModelUtil.searchPackageForPrimType(modClassifier,
                            superClassifier.getName(),
                            primTypes);
                    if (primTypes.size() > 1) {
                        useQualifiedName = true;
                    }
                }

                if (superClassifier == modClassifier) {
                    // Its going to find itself so check > 1
                    if (useQualifiedName) {
                        text = UML2ModelUtil.getQualifiedLabel(superClassifier);
                    } else {
                        text = PrimitivesUtil.getDisplayLabel(superClassifier);
                    }
                } else {
                    if (useQualifiedName) {
                        text = UML2ModelUtil.getQualifiedLabel(superClassifier);
                    } else {
                        text = PrimitivesUtil.getDisplayLabel(superClassifier);

                    }
                }

            }
        }

        return text;
    }

    // TODO:
    @Override
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

    @Override
    public IParserEditStatus isValidEditString(IAdaptable element,
            String editString) {
        return ParserEditStatus.EDITABLE_STATUS;
    }

}
