/**
 * 
 */
package com.tibco.xpd.xpdl2.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap.Entry;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CopyCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;

import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.XpdProjectResourceFactory;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.resources.util.XpdEcoreUtil;
import com.tibco.xpd.ui.util.CapabilityUtil;
import com.tibco.xpd.ui.util.NameUtil;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationField;
import com.tibco.xpd.xpdExtension.AssociatedCorrelationFields;
import com.tibco.xpd.xpdExtension.BindingType;
import com.tibco.xpd.xpdExtension.CatchErrorMappings;
import com.tibco.xpd.xpdExtension.CorrelationDataMappings;
import com.tibco.xpd.xpdExtension.Discriminator;
import com.tibco.xpd.xpdExtension.ErrorMethod;
import com.tibco.xpd.xpdExtension.InterfaceMethod;
import com.tibco.xpd.xpdExtension.IntermediateMethod;
import com.tibco.xpd.xpdExtension.ParticipantSharedResource;
import com.tibco.xpd.xpdExtension.PortTypeOperation;
import com.tibco.xpd.xpdExtension.ProcessInterface;
import com.tibco.xpd.xpdExtension.ProcessInterfaces;
import com.tibco.xpd.xpdExtension.ReplyImmediateDataMappings;
import com.tibco.xpd.xpdExtension.ScriptInformation;
import com.tibco.xpd.xpdExtension.SignalData;
import com.tibco.xpd.xpdExtension.SoapBindingStyle;
import com.tibco.xpd.xpdExtension.StartMethod;
import com.tibco.xpd.xpdExtension.StructuredDiscriminator;
import com.tibco.xpd.xpdExtension.TransformScript;
import com.tibco.xpd.xpdExtension.WsInbound;
import com.tibco.xpd.xpdExtension.WsResource;
import com.tibco.xpd.xpdExtension.WsSoapHttpInboundBinding;
import com.tibco.xpd.xpdExtension.WsSoapJmsInboundBinding;
import com.tibco.xpd.xpdExtension.WsdlGeneration;
import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdExtension.XpdInterfaceType;
import com.tibco.xpd.xpdExtension.XpdModelType;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.ArtifactType;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.BlockActivity;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.ConnectorGraphicsInfo;
import com.tibco.xpd.xpdl2.Coordinates;
import com.tibco.xpd.xpdl2.DataField;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.DataType;
import com.tibco.xpd.xpdl2.DeclaredType;
import com.tibco.xpd.xpdl2.DeprecatedXorType;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.ExclusiveType;
import com.tibco.xpd.xpdl2.Expression;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.GatewayType;
import com.tibco.xpd.xpdl2.GraphicalConnector;
import com.tibco.xpd.xpdl2.GraphicalNode;
import com.tibco.xpd.xpdl2.Implementation;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.JoinSplitType;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.No;
import com.tibco.xpd.xpdl2.NodeGraphicsInfo;
import com.tibco.xpd.xpdl2.OtherAttributesContainer;
import com.tibco.xpd.xpdl2.OtherElementsContainer;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.Performer;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.ProcessRelevantData;
import com.tibco.xpd.xpdl2.Reference;
import com.tibco.xpd.xpdl2.ResultError;
import com.tibco.xpd.xpdl2.ResultType;
import com.tibco.xpd.xpdl2.Route;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.SubFlow;
import com.tibco.xpd.xpdl2.Task;
import com.tibco.xpd.xpdl2.TaskReceive;
import com.tibco.xpd.xpdl2.TaskScript;
import com.tibco.xpd.xpdl2.TaskSend;
import com.tibco.xpd.xpdl2.TaskService;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.TriggerResultCompensation;
import com.tibco.xpd.xpdl2.TriggerResultLink;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerResultSignal;
import com.tibco.xpd.xpdl2.TriggerType;
import com.tibco.xpd.xpdl2.TypeDeclaration;
import com.tibco.xpd.xpdl2.UniqueIdElement;
import com.tibco.xpd.xpdl2.WebServiceOperation;
import com.tibco.xpd.xpdl2.XorType;
import com.tibco.xpd.xpdl2.Xpdl2Factory;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.edit.internal.Messages;
import com.tibco.xpd.xpdl2.provider.Xpdl2EditPlugin;
import com.tibco.xpd.xpdl2.resolvers.AbstractUniqueIdsReassignedListener;
import com.tibco.xpd.xpdl2.resolvers.FixReassignedIdReferencesExtensionPoint;
import com.tibco.xpd.xpdl2.resolvers.FixReassignedIdReferencesExtensionPointManager;
import com.tibco.xpd.xpdl2.resolvers.Xpdl2FieldOrParamReplacer;

/**
 * Convenience methods for XPDL2 model
 * 
 * @author wzurek
 * 
 */
public class Xpdl2ModelUtil {

    private static final String DOT = "."; //$NON-NLS-1$

    public static final String STUDIO_SPECIFIC_TOOL_ID = "XPD"; //$NON-NLS-1$

    public static final String CONNECTION_INFO_IDSUFFIX = "ConnectionInfo"; //$NON-NLS-1$

    public static final String START_ANCHOR_IDSUFFIX = "StartAnchorPosition"; //$NON-NLS-1$

    public static final String END_ANCHOR_IDSUFFIX = "EndAnchorPosition"; //$NON-NLS-1$

    public static final String LABEL_ANCHOR_IDSUFFIX = "LabelAnchorPosition"; //$NON-NLS-1$

    public static final String LABEL_SIZE_IDSUFFIX = "LabelSize"; //$NON-NLS-1$

    public static final String BORDER_EVENTPOS_IDSUFFIX = "BorderEventPosition"; //$NON-NLS-1$

    public static final String DEFAULT_WORK_ITEM_PRIORITY = "50"; //$NON-NLS-1$

    public static final String REPLY_IMMEDIATE_PROCESS_ID_PARAMETER_NAME =
            "__PROCESS_ID__"; //$NON-NLS-1$

    public static final String GENERATED_SERVICES =
            Messages.Xpdl2ModelUtil_GeneratedServiceFolder_label;

    public static final String GENERATED_SERVICE_TARGETNS_PREFIX =
            "http://www.tibco.com/bs3.0/"; //$NON-NLS-1$

    private static final String WSDL_FILE_EXTN = "wsdl"; //$NON-NLS-1$

    /**
     * SetOtherAttributeCommand
     * 
     * EMF Command to set an ##other attribute (it is removed when set to null)
     */
    private static class SetOtherAttributeCommand extends AbstractCommand {
        private EditingDomain editingDomain;

        private OtherAttributesContainer owner;

        private EStructuralFeature attributeFeature;

        private Object value;

        private Object oldValue;

        /**
         * 
         */
        public SetOtherAttributeCommand(EditingDomain ed,
                OtherAttributesContainer owner,
                EStructuralFeature attributeFeature, Object value) {
            this.editingDomain = ed;
            this.owner = owner;
            this.attributeFeature = attributeFeature;
            this.value = value;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
         */
        @Override
        protected boolean prepare() {
            if (editingDomain != null && owner != null
                    && attributeFeature != null) {
                /*
                 * XPD-1140: Cannot execute if editing domain is read-only!
                 */
                if (!editingDomain.isReadOnly(owner.eResource())) {
                    oldValue = getOtherAttribute(owner, attributeFeature);
                    return true;
                }
            }

            return false;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.command.Command#execute()
         */
        @Override
        public void execute() {
            setOtherAttribute(owner, attributeFeature, value);
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.command.Command#redo()
         */
        @Override
        public void redo() {
            setOtherAttribute(owner, attributeFeature, value);

        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.command.AbstractCommand#undo()
         */
        @Override
        public void undo() {
            setOtherAttribute(owner, attributeFeature, oldValue);
        }

        @Override
        public Collection<?> getAffectedObjects() {
            return Collections.singletonList(owner);
        }
    }

    /**
     * SetOtherElementCommand
     * 
     * EMF Command to set an ##other Element (it is removed when set to null)
     */
    private static class SetOtherElementCommand extends AbstractCommand {
        private EditingDomain editingDomain;

        private OtherElementsContainer oldOwner;

        private OtherElementsContainer owner;

        private EStructuralFeature elementFeature;

        private Object value;

        private Object oldValue;

        /**
         * Set the given ##other element in the given owner element (null will
         * cause a delete).
         */
        public SetOtherElementCommand(EditingDomain ed,
                OtherElementsContainer owner, EStructuralFeature elementFeature,
                Object value) {
            this.editingDomain = ed;
            this.owner = owner;
            this.elementFeature = elementFeature;
            this.value = value;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
         */
        @Override
        protected boolean prepare() {
            if (value instanceof EObject) {
                EObject parent = ((EObject) value).eContainer();
                if (parent instanceof OtherElementsContainer) {
                    oldOwner = (OtherElementsContainer) parent;
                }
            }
            if (editingDomain != null && owner != null
                    && elementFeature != null) {
                /*
                 * XPD-1140: Cannot execute if editing domain is read-only!
                 */
                if (!editingDomain.isReadOnly(owner.eResource())) {
                    oldValue = getOtherElement(owner, elementFeature);
                    return true;
                }
            }

            return false;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.command.Command#execute()
         */
        @Override
        public void execute() {
            setOtherElement(owner, elementFeature, value);
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.command.Command#redo()
         */
        @Override
        public void redo() {
            setOtherElement(owner, elementFeature, value);

        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.command.AbstractCommand#undo()
         */
        @Override
        public void undo() {
            setOtherElement(owner, elementFeature, oldValue);
            if (oldOwner != null) {
                setOtherElement(oldOwner, elementFeature, value);
            }
        }

        @Override
        public Collection<?> getAffectedObjects() {
            return Collections.singletonList(value);
        }

    }

    /**
     * SetOtherElementCommand
     * 
     * EMF Command to set an ##other Element (it is removed when set to null)
     */
    private static class AddOtherElementCommand extends AbstractCommand {
        private EditingDomain editingDomain;

        private OtherElementsContainer oldOwner;

        private OtherElementsContainer owner;

        private EStructuralFeature elementFeature;

        private Object value;

        /**
         * Set the given ##other element in the given owner element (null will
         * cause a delete).
         */
        public AddOtherElementCommand(EditingDomain ed,
                OtherElementsContainer owner, EStructuralFeature elementFeature,
                Object value) {
            this.editingDomain = ed;
            this.owner = owner;
            this.elementFeature = elementFeature;
            this.value = value;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
         */
        @Override
        protected boolean prepare() {
            if (value instanceof EObject) {
                EObject parent = ((EObject) value).eContainer();
                if (parent instanceof OtherElementsContainer) {
                    oldOwner = (OtherElementsContainer) parent;
                }
            }
            if (editingDomain != null && owner != null
                    && elementFeature != null) {
                /*
                 * XPD-1140: Cannot execute if editing domain is read-only!
                 */
                if (!editingDomain.isReadOnly(owner.eResource())) {
                    return true;
                }
            }

            return false;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.command.Command#execute()
         */
        @Override
        public void execute() {
            addOtherElement(owner, elementFeature, value);
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.command.Command#redo()
         */
        @Override
        public void redo() {
            addOtherElement(owner, elementFeature, value);

        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.command.AbstractCommand#undo()
         */
        @Override
        public void undo() {
            removeOtherElement(owner, elementFeature, value);
            if (oldOwner != null) {
                addOtherElement(oldOwner, elementFeature, value);
            }
        }

        @Override
        public Collection<?> getAffectedObjects() {
            return Collections.singletonList(value);
        }

    }

    /**
     * SetOtherElementCommand
     * 
     * EMF Command to set an ##other Element (it is removed when set to null)
     */
    private static class RemoveOtherElementCommand extends AbstractCommand {
        private EditingDomain editingDomain;

        private OtherElementsContainer owner;

        private EStructuralFeature elementFeature;

        private Object value;

        /**
         * Set the given ##other element in the given owner element (null will
         * cause a delete).
         */
        public RemoveOtherElementCommand(EditingDomain ed,
                OtherElementsContainer owner, EStructuralFeature elementFeature,
                Object value) {
            this.editingDomain = ed;
            this.owner = owner;
            this.elementFeature = elementFeature;
            this.value = value;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
         */
        @Override
        protected boolean prepare() {
            if (editingDomain != null && owner != null
                    && elementFeature != null) {
                /*
                 * XPD-1140: Cannot execute if editing domain is read-only!
                 */
                if (!editingDomain.isReadOnly(owner.eResource())) {
                    return true;
                }
            }

            return false;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.command.Command#execute()
         */
        @Override
        public void execute() {
            removeOtherElement(owner, elementFeature, value);
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.command.Command#redo()
         */
        @Override
        public void redo() {
            removeOtherElement(owner, elementFeature, value);

        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.emf.common.command.AbstractCommand#undo()
         */
        @Override
        public void undo() {
            addOtherElement(owner, elementFeature, value);
        }

        @Override
        public Collection<?> getAffectedObjects() {
            return Collections.singletonList(value);
        }

    }

    /**
     * Get ##other attribute from given parent (owner) element.
     * 
     * Nominally, the structural feature will be from the
     * XpdExtensionPackage.eINSTANCE. But I don't think that this necessarily
     * has to be.
     * 
     * @param owner
     *            Parent of element of feature.
     * @param attributeFeature
     *            The attribute to get
     * 
     * @return The value of the given attribute.
     */
    public static Object getOtherAttribute(OtherAttributesContainer owner,
            EStructuralFeature attributeFeature) {
        Object item = null;

        if (owner != null) {
            EList eList = owner.getOtherAttributes().list(attributeFeature);

            if (!eList.isEmpty()) {
                item = eList.get(0);
            }
        }

        return item;
    }

    public static boolean getOtherAttributeAsBoolean(
            OtherAttributesContainer owner,
            EStructuralFeature attributeFeature) {
        boolean isTrue = false;
        Object other =
                Xpdl2ModelUtil.getOtherAttribute(owner, attributeFeature);
        if (other instanceof Boolean) {
            isTrue = ((Boolean) other).booleanValue();
        }
        return isTrue;

    }

    /**
     * Set ##other attribute in given parent (owner) element.
     * 
     * Nominally, the structural feature will be from the
     * XpdExtensionPackage.eINSTANCE. But I don't think that this necessarily
     * has to be.
     * 
     * @param owner
     *            Parent of element of feature.
     * @param attributeFeature
     *            The attribute to get
     * 
     * @return The value of the given attribute.
     */
    public static void setOtherAttribute(OtherAttributesContainer owner,
            EStructuralFeature attributeFeature, Object value) {
        if (owner != null) {
            FeatureMap otherAttrs = owner.getOtherAttributes();

            for (int i = 0; i < otherAttrs.size(); i++) {
                EStructuralFeature feat = otherAttrs.getEStructuralFeature(i);

                if (feat == attributeFeature) {
                    otherAttrs.remove(i);
                }
            }

            if (value != null) {
                Entry entry =
                        FeatureMapUtil.createEntry(attributeFeature, value);

                owner.getOtherAttributes().add(entry);
            }
        }
        return;
    }

    /**
     * Get Command to set the given ##other attribute in the given owner
     * (parent) object.
     * 
     * @param ed
     *            Editing Domain
     * @param owner
     *            Parent element
     * @param attributeFeature
     *            Attribute feature to set
     * @param value
     *            Appropriate value object for feature content.
     * 
     * @return The to set the extension attribute
     */
    public static Command getSetOtherAttributeCommand(EditingDomain ed,
            OtherAttributesContainer owner, EStructuralFeature attributeFeature,
            Object value) {

        return new SetOtherAttributeCommand(ed, owner, attributeFeature, value);
    }

    /**
     * Get ##other element from given parent (owner) element.
     * 
     * Nominally, the structural feature will be from the
     * XpdExtensionPackage.eINSTANCE. But I don't think that this necessarily
     * has to be.
     * 
     * @param owner
     *            Parent of element of feature.
     * @param elementFeature
     *            The element to get
     * 
     * @return The value of the given attribute.
     */
    public static Object getOtherElement(OtherElementsContainer owner,
            EStructuralFeature elementFeature) {
        Object item = null;

        if (owner != null) {
            EList eList = owner.getOtherElements().list(elementFeature);

            if (!eList.isEmpty()) {
                item = eList.get(0);
            }
        }

        return item;
    }

    /**
     * Get ##other elements from given parent (owner) element.
     * 
     * Nominally, the structural feature will be from the
     * XpdExtensionPackage.eINSTANCE. But I don't think that this necessarily
     * has to be.
     * 
     * @param owner
     *            Parent of element of feature.
     * @param elementFeature
     *            The element to get
     * 
     * @return The value of the given attribute.
     */
    public static EList getOtherElementList(OtherElementsContainer owner,
            EStructuralFeature elementFeature) {
        if (owner != null) {
            return owner.getOtherElements().list(elementFeature);

        }

        return null;
    }

    /**
     * Get Command to set the given ##other attribute in the given owner
     * (parent) object.
     * 
     * @param ed
     *            Editing Domain
     * @param owner
     *            Parent element
     * @param attributeFeature
     *            Attribute feature to set
     * @param value
     *            Appropriate value object for feature content.
     * 
     * @return The command to set the extension element
     */
    public static Command getSetOtherElementCommand(EditingDomain ed,
            OtherElementsContainer owner, EStructuralFeature elementFeature,
            Object value) {

        return new SetOtherElementCommand(ed, owner, elementFeature, value);
    }

    /**
     * Set ##other element in given parent (owner) element.
     * 
     * Nominally, the structural feature will be from the
     * XpdExtensionPackage.eINSTANCE. But I don't think that this necessarily
     * has to be.
     * 
     * @param owner
     *            Parent of element of feature.
     * @param elementFeature
     *            The attribute to get
     * 
     * @return The value of the given attribute.
     */
    public static void setOtherElement(OtherElementsContainer owner,
            EStructuralFeature elementFeature, Object value) {
        if (owner != null) {
            FeatureMap otherElements = owner.getOtherElements();

            for (int i = 0; i < otherElements.size(); i++) {
                EStructuralFeature feat =
                        otherElements.getEStructuralFeature(i);

                if (feat == elementFeature) {
                    otherElements.remove(i);
                }
            }

            if (value != null) {
                Entry entry = FeatureMapUtil.createEntry(elementFeature, value);

                owner.getOtherElements().add(entry);
            }
        }
        return;
    }

    /**
     * Get Command to add the given ##other element in the given owner (parent)
     * object (allows for multiple extension elements of same type as opposed to
     * {@link #getSetOtherElementCommand(EditingDomain, OtherElementsContainer, EStructuralFeature, Object)}
     * )
     * 
     * @param ed
     *            Editing Domain
     * @param owner
     *            Parent element
     * @param attributeFeature
     *            Attribute feature to set
     * @param value
     *            Appropriate value object for feature content.
     * 
     * @return The to add the extensioin element
     */
    public static Command getAddOtherElementCommand(EditingDomain ed,
            OtherElementsContainer owner, EStructuralFeature elementFeature,
            Object value) {

        return new AddOtherElementCommand(ed, owner, elementFeature, value);
    }

    /**
     * Set ##other element in given parent (owner) element.
     * 
     * Nominally, the structural feature will be from the
     * XpdExtensionPackage.eINSTANCE. But I don't think that this necessarily
     * has to be.
     * 
     * @param owner
     *            Parent of element of feature.
     * @param elementFeature
     *            The attribute to get
     * 
     * @return The value of the given attribute.
     */
    public static void addOtherElement(OtherElementsContainer owner,
            EStructuralFeature elementFeature, Object value) {
        if (owner != null) {

            if (value != null) {
                Entry entry = FeatureMapUtil.createEntry(elementFeature, value);

                owner.getOtherElements().add(entry);
            }
        }
        return;
    }

    /**
     * Get Command to remove the given ##other element in the given owner
     * (parent) object.
     * 
     * @param ed
     *            Editing Domain
     * @param owner
     *            Parent element
     * @param attributeFeature
     *            Attribute feature to set
     * @param value
     *            Appropriate value object for feature content.
     * 
     * @return The command to remove the extension element
     */
    public static Command getRemoveOtherElementCommand(EditingDomain ed,
            OtherElementsContainer owner, EStructuralFeature elementFeature,
            Object value) {

        return new RemoveOtherElementCommand(ed, owner, elementFeature, value);
    }

    /**
     * Set ##other element in given parent (owner) element.
     * 
     * Nominally, the structural feature will be from the
     * XpdExtensionPackage.eINSTANCE. But I don't think that this necessarily
     * has to be.
     * 
     * @param owner
     *            Parent of element of feature.
     * @param elementFeature
     *            The attribute to get
     * 
     * @return The value of the given attribute.
     */
    public static void removeOtherElement(OtherElementsContainer owner,
            EStructuralFeature elementFeature, Object value) {
        if (owner != null) {
            FeatureMap otherElements = owner.getOtherElements();

            for (int i = 0; i < otherElements.size(); i++) {
                EStructuralFeature feat =
                        otherElements.getEStructuralFeature(i);
                Object otherValue = otherElements.getValue(i);

                if (feat == elementFeature && otherValue.equals(value)) {
                    otherElements.remove(i);
                }
            }

        }
        return;
    }

    /**
     * Returns existing or newly created graphic node info for give tool ID
     * 
     * @param node
     * @param ed
     * @param cmd
     * @return
     */
    public static NodeGraphicsInfo getOrCreateNodeGraphicsInfo(
            GraphicalNode node, EditingDomain ed, CompoundCommand cmd) {
        return getOrCreateNodeGraphicsInfo(node, ed, cmd, null);
    }

    /**
     * Returns existing or newly created graphic node info for give tool ID
     * 
     * @param node
     * @param ed
     * @param cmd
     * @param toolIdSuffix
     *            Extra indentifying tool id suffix or null if none required.
     * 
     * @return
     */
    public static NodeGraphicsInfo getOrCreateNodeGraphicsInfo(
            GraphicalNode node, EditingDomain ed, CompoundCommand cmd,
            String toolIdSuffix) {
        NodeGraphicsInfo gNode = getNodeGraphicsInfo(node, toolIdSuffix);
        if (gNode == null) {
            gNode = Xpdl2Factory.eINSTANCE.createNodeGraphicsInfo();

            String toolId;
            if (toolIdSuffix != null) {
                toolId = STUDIO_SPECIFIC_TOOL_ID + DOT + toolIdSuffix;
                gNode.setToolId(toolId);
            } else {
                // Don't write toolId for standard XPDL usage graphics info's
            }

            cmd.append(AddCommand.create(ed,
                    node,
                    Xpdl2Package.eINSTANCE.getGraphicalNode_NodeGraphicsInfos(),
                    gNode));
        }
        return gNode;
    }

    /**
     * Returns existing or newly created graphic node info
     * 
     * @param node
     * @return
     */
    public static NodeGraphicsInfo getOrCreateNodeGraphicsInfo(
            GraphicalNode node) {
        return getOrCreateNodeGraphicsInfo(node, null);
    }

    /**
     * Returns existing or newly created graphic node info
     * 
     * @param node
     * @param toolIdSuffix
     * @return
     */
    public static NodeGraphicsInfo getOrCreateNodeGraphicsInfo(
            GraphicalNode node, String toolIdSuffix) {
        NodeGraphicsInfo gNode = getNodeGraphicsInfo(node, toolIdSuffix);
        if (gNode == null) {
            gNode = Xpdl2Factory.eINSTANCE.createNodeGraphicsInfo();

            String toolId;
            if (toolIdSuffix != null) {
                toolId = STUDIO_SPECIFIC_TOOL_ID + DOT + toolIdSuffix;
                gNode.setToolId(toolId);
            } else {
                // Don't write toolId for standard XPDL usage graphics info's
            }

            EditingDomain editingDomain =
                    WorkingCopyUtil.getEditingDomain(node);
            if (editingDomain instanceof TransactionalEditingDomain) {
                Command command = AddCommand.create(editingDomain,
                        node,
                        Xpdl2Package.eINSTANCE
                                .getGraphicalNode_NodeGraphicsInfos(),
                        gNode);
                ((TransactionalEditingDomain) editingDomain).getCommandStack()
                        .execute(command);
            } else {
                node.getNodeGraphicsInfos().add(gNode);
            }
        }
        return gNode;
    }

    public static NodeGraphicsInfo getNodeGraphicsInfo(GraphicalNode node) {
        return getNodeGraphicsInfo(node, null);
    }

    public static NodeGraphicsInfo getNodeGraphicsInfo(GraphicalNode node,
            String toolIdSuffix) {
        if (toolIdSuffix != null) {
            return node.getNodeGraphicsInfoForTool(
                    STUDIO_SPECIFIC_TOOL_ID + DOT + toolIdSuffix);
        } else {
            NodeGraphicsInfo ngi = node.getNodeGraphicsInfoForTool(""); //$NON-NLS-1$
            if (ngi != null) {
                return ngi;
            }

            // Just in case there's some unmigrated route thru to reading a
            // NodeGraphicsInfo allow read from "XPD" as toolId.
            return node.getNodeGraphicsInfoForTool(STUDIO_SPECIFIC_TOOL_ID);
        }
    }

    /**
     * Returns existing or newly created graphic connector info for the default
     * ToolId for connections (XPD.ConnectionInfo)
     * 
     * @param connector
     * @param ed
     * @param cmd
     * @return
     */
    public static ConnectorGraphicsInfo getOrCreateConnectorGraphicsInfo(
            GraphicalConnector connector, EditingDomain ed,
            CompoundCommand cmd) {
        return getOrCreateConnectorGraphicsInfo(connector, ed, cmd, null);
    }

    /**
     * Returns existing or newly created graphic connector info for info for the
     * ToolId with the given suffix.
     * 
     * @param connector
     * @param ed
     * @param cmd
     * @param toolIdSuffix
     * @return
     */
    public static ConnectorGraphicsInfo getOrCreateConnectorGraphicsInfo(
            GraphicalConnector connector, EditingDomain ed, CompoundCommand cmd,
            String toolIdSuffix) {
        ConnectorGraphicsInfo gConnector =
                getConnectorGraphicsInfo(connector, toolIdSuffix);
        if (gConnector == null) {
            gConnector = Xpdl2Factory.eINSTANCE.createConnectorGraphicsInfo();

            String toolId;
            if (toolIdSuffix != null) {
                toolId = STUDIO_SPECIFIC_TOOL_ID + DOT + toolIdSuffix;
            } else {
                // We ALWAYS use a ToolId for ConnectorGraphicsInfo because
                // there is nothing standard about ours.
                toolId = START_ANCHOR_IDSUFFIX + DOT + CONNECTION_INFO_IDSUFFIX;
            }

            gConnector.setToolId(toolId);

            cmd.append(AddCommand.create(ed,
                    connector,
                    Xpdl2Package.eINSTANCE
                            .getGraphicalConnector_ConnectorGraphicsInfos(),
                    gConnector));
        }
        return gConnector;
    }

    /**
     * Returns existing or newly created graphic connector info for give tool ID
     * 
     * @param connector
     * @return
     */
    public static ConnectorGraphicsInfo getOrCreateConnectorGraphicsInfo(
            GraphicalConnector connector) {
        return getOrCreateConnectorGraphicsInfo(connector, null);
    }

    /*
     * Returns existing or newly created graphic connector info for give tool ID
     * 
     * @param connector @param toolIdSuffix Extra XPD tool id suffix (allowing
     * for extra graphic infos for same tool. @return
     */
    public static ConnectorGraphicsInfo getOrCreateConnectorGraphicsInfo(
            GraphicalConnector connector, String toolIdSuffix) {
        ConnectorGraphicsInfo gConnector =
                getConnectorGraphicsInfo(connector, toolIdSuffix);
        if (gConnector == null) {
            gConnector = Xpdl2Factory.eINSTANCE.createConnectorGraphicsInfo();

            String toolId;
            if (toolIdSuffix != null) {
                toolId = STUDIO_SPECIFIC_TOOL_ID + DOT + toolIdSuffix;
            } else {
                // We ALWAYS use a ToolId for ConnectorGraphicsInfo because
                // there is nothing standard about ours.
                toolId = STUDIO_SPECIFIC_TOOL_ID + DOT
                        + CONNECTION_INFO_IDSUFFIX;
            }

            gConnector.setToolId(toolId);

            connector.getConnectorGraphicsInfos().add(gConnector);
        }
        return gConnector;
    }

    public static ConnectorGraphicsInfo getConnectorGraphicsInfo(
            GraphicalConnector connector) {
        return getConnectorGraphicsInfo(connector, null);
    }

    /*
     * Returns existing or graphic connector info for give tool ID
     * 
     * @param connector @param toolIdSuffix Extra XPD tool id suffix (allowing
     * for extra graphic infos for same tool. @return
     */
    public static ConnectorGraphicsInfo getConnectorGraphicsInfo(
            GraphicalConnector connector, String toolIdSuffix) {
        if (toolIdSuffix != null) {
            return connector.getConnectorGraphicsInfoForTool(
                    STUDIO_SPECIFIC_TOOL_ID + DOT + toolIdSuffix);
        } else {
            ConnectorGraphicsInfo ngi = connector
                    .getConnectorGraphicsInfoForTool(STUDIO_SPECIFIC_TOOL_ID
                            + DOT + CONNECTION_INFO_IDSUFFIX);
            if (ngi != null) {
                // We ALWAYS use a ToolId for ConnectorGraphicsInfo because
                // there is nothing standard about ours.
                return ngi;
            }

            // Just in case there's some unmigrated route thru to reading a
            // NodeGraphicsInfo allow read from "XPD" as toolId.
            return connector
                    .getConnectorGraphicsInfoForTool(STUDIO_SPECIFIC_TOOL_ID);

        }

    }

    /**
     * Get parent process of given...
     * <li>Activity</li>
     * <li>Artifact</li>
     * <li>Lane</li>
     * <li>Pool</li>
     * <li>ActivitySet</li>
     * 
     */
    public static Process getProcess(EObject object) {
        Process process = null;

        if (object instanceof Process) {
            process = (Process) object;

        } else if (object instanceof Activity) {
            // nothing special to do for activity.
            process = ((Activity) object).getProcess();

        } else if (object instanceof Artifact) {
            EObject container = getContainer(object);

            if (container instanceof ActivitySet) {
                process = ((ActivitySet) container).getProcess();

            } else if (container instanceof Lane) {
                process = getProcessFromLane((Lane) container);
            }

        } else if (object instanceof Lane) {
            process = getProcessFromLane((Lane) object);

        } else if (object instanceof Pool) {
            process = getProcessFromPool((Pool) object);

        } else if (object instanceof ActivitySet) {
            process = ((ActivitySet) object).getProcess();

        } else if (object instanceof Transition) {
            process = ((Transition) object).getProcess();

        } else if (object instanceof MessageFlow) {
            Package pkg = ((MessageFlow) object).getPackage();
            if (pkg != null) {
                EObject from = pkg
                        .findNamedElement(((MessageFlow) object).getSource());

                if (from != null) {
                    process = getProcess(from);
                } else {
                    EObject to = pkg.findNamedElement(
                            ((MessageFlow) object).getTarget());

                    if (to != null) {
                        process = getProcess(to);
                    }
                }
            }

        } else if (object instanceof Association) {
            Package pkg = ((Association) object).getPackage();
            if (pkg != null) {
                EObject from = pkg
                        .findNamedElement(((Association) object).getSource());

                if (from != null) {
                    process = getProcess(from);
                } else {
                    EObject to = pkg.findNamedElement(
                            ((Association) object).getTarget());

                    if (to != null) {
                        process = getProcess(to);
                    }
                }
            }

        } else if (object instanceof ScriptInformation) {
            process =
                    getProcessFromScriptInformation((ScriptInformation) object);

        } else {
            if (object != null) {
                EObject parent = object.eContainer();
                while (parent != null) {
                    if (parent instanceof Process) {
                        process = (Process) parent;
                        break;
                    }
                    parent = parent.eContainer();
                }
            }
        }

        return process;
    }

    /**
     * @param scriptInformation
     */
    private static Process getProcessFromScriptInformation(
            ScriptInformation scriptInformation) {
        Process process = null;

        EObject parent = scriptInformation.eContainer();
        if (parent != null) {
            while (parent != null) {
                if (parent instanceof Process) {
                    process = (Process) parent;
                    break;
                }
                parent = parent.eContainer();
            }
        } else {
            Activity act = scriptInformation.getActivity();
            if (act != null) {
                process = getProcess(act);
            }
        }

        return process;
    }

    /**
     * Get the lane that is the parent of that embedded sub-process activity
     * that owns the given activity set.
     * 
     * @param actSet
     * @return
     */
    public static Lane getActivitySetParentLane(ActivitySet actSet) {

        // Find the embedded sub-proc activity that uses this activity set.
        Process process = actSet.getProcess();

        Lane lane = null;

        String lookForActSet = actSet.getId();

        while (lane == null) {

            Activity embSubProc =
                    getEmbSubProcActivityForActSet(process, lookForActSet);

            EObject laneOrActSet = getContainer(embSubProc);

            if (laneOrActSet instanceof Lane) {
                lane = (Lane) laneOrActSet;
            } else if (laneOrActSet instanceof ActivitySet) {
                // If parent of embedded sub-process activity that uses this
                // activity set is an activity set then reset what we're looking
                // for and go round again.
                lookForActSet = ((ActivitySet) laneOrActSet).getId();
            } else {
                break;
            }

        }

        if (lane == null) {
            throw new IllegalArgumentException(
                    Messages.Xpdl2ModelUtil_error3_label + lookForActSet);

        }

        return lane;
    }

    /**
     * @param procActivities
     * @param actSetId
     * @return
     */
    public static Activity getEmbSubProcActivityForActSet(Process process,
            String actSetId) {
        Collection procActivities = getAllActivitiesInProc(process);
        Activity embSubProc = null;

        // Look for activity that uses the activity set.
        for (Iterator iter = procActivities.iterator(); iter.hasNext();) {
            Activity act = (Activity) iter.next();

            BlockActivity ba = act.getBlockActivity();
            if (ba != null) {
                if (actSetId.equals(ba.getActivitySetId())) {
                    embSubProc = act;
                    break;
                }
            }
        }

        if (embSubProc == null) {
            throw new IllegalArgumentException(
                    Messages.Xpdl2ModelUtil_error2_label + actSetId);
        }
        return embSubProc;
    }

    /**
     * Get ALL activities in process Including ALL activities in activity sets.
     * 
     * @param process
     * @return
     */
    public static Collection<Activity> getAllActivitiesInProc(Process process) {
        List<Activity> activities = new ArrayList<Activity>();

        activities.addAll(process.getActivities());

        List actSets = process.getActivitySets();

        for (Iterator iter = actSets.iterator(); iter.hasNext();) {
            ActivitySet actSet = (ActivitySet) iter.next();

            activities.addAll(actSet.getActivities());
        }

        return activities;
    }

    /**
     * Get the activity with the given id from the process (or child embedded
     * sub-processes.
     * 
     * @param process
     * @param id
     * 
     * @return Activity or null if not found.
     */
    public static Activity getActivityById(Process process, String id) {
        for (Activity activity : process.getActivities()) {
            if (id.equals(activity.getId())) {
                return activity;
            }
        }

        for (ActivitySet activitySet : process.getActivitySets()) {
            for (Activity activity : activitySet.getActivities()) {
                if (id.equals(activity.getId())) {
                    return activity;
                }
            }
        }

        return null;
    }

    /**
     * Get the activity with the given name from the process (or child embedded
     * sub-processes.
     * 
     * @param process
     * @param name
     * 
     * @return Activity or null if not found.
     */
    public static Activity getActivityByName(Process process, String name) {
        for (Activity activity : process.getActivities()) {
            if (name.equals(activity.getName())) {
                return activity;
            }
        }

        for (ActivitySet activitySet : process.getActivitySets()) {
            for (Activity activity : activitySet.getActivities()) {
                if (name.equals(activity.getName())) {
                    return activity;
                }
            }
        }

        return null;
    }

    /**
     * @param association
     * @return True if the given associaiton is (or should be) a compensation
     *         association.
     */
    public static boolean isCompensationAssociation(Association association) {
        /*
         * Associations with activity at both ends must be (or at least if valid
         * should be) compensation associations.
         */
        Process process = getProcess(association);
        if (process != null) {
            Activity sourceActivity =
                    getActivityById(process, association.getSource());

            if (sourceActivity != null && getActivityById(process,
                    association.getTarget()) != null) {

                if (sourceActivity.getEvent()
                        .getEventTriggerTypeNode() instanceof TriggerResultCompensation) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 
     * @param activity
     * @return True if this activity is processed as a result of an catch
     *         compensation event.
     */
    public static boolean isCompensationActivity(Activity activity) {
        EList<Association> incomingAssociations =
                activity.getIncomingAssociations();

        for (Association association : incomingAssociations) {
            if (isCompensationAssociation(association)) {
                return true;
            }

        }

        return false;
    }

    /**
     * @param embeddedSubProcAct
     * @return The activity set for the contents of the given embedded
     *         sub-process activity or null if not found.
     */
    public static ActivitySet getEmbeddedSubProcessActivitySet(
            Activity embeddedSubProcAct) {
        Process process = embeddedSubProcAct.getProcess();
        if (process != null) {
            BlockActivity ba = embeddedSubProcAct.getBlockActivity();
            if (ba != null && ba.getActivitySetId() != null) {
                return process.getActivitySet(ba.getActivitySetId());
            }
        }
        return null;
    }

    /**
     * @param embeddedSubProcAct
     * @return The activity set for the given embedded subprocess activity PLUS
     *         any activity sets for nested embedded sub-process activities
     *         within it.
     */
    public static List<ActivitySet> getEmbeddedSubProcessesTree(
            Activity embeddedSubProcAct) {
        List<ActivitySet> sets = new ArrayList<ActivitySet>();

        addEmbeddedSubProcActSets(embeddedSubProcAct, sets);

        return sets;
    }

    private static void addEmbeddedSubProcActSets(Activity embeddedSubProcAct,
            List<ActivitySet> sets) {
        ActivitySet actSet =
                getEmbeddedSubProcessActivitySet(embeddedSubProcAct);
        if (actSet != null) {
            sets.add(actSet);
            for (Activity act : actSet.getActivities()) {
                if (act.getBlockActivity() != null) {
                    addEmbeddedSubProcActSets(act, sets);
                }
            }
        }

        return;
    }

    /**
     * @param embeddedSubProcAct
     * @return All activities in (or in nested embedded sub-processes of) the
     *         given embedded sub-process.
     */
    public static Collection<Activity> getAllActivitiesInEmbeddedSubProc(
            Activity embeddedSubProcAct) {
        List<Activity> activities = new ArrayList<Activity>();

        List<ActivitySet> sets =
                getEmbeddedSubProcessesTree(embeddedSubProcAct);
        for (ActivitySet actSet : sets) {
            activities.addAll(actSet.getActivities());
        }

        return activities;
    }

    /**
     * @param embeddedSubProcAct
     * @return All transitions in (or in nested embedded sub-processes of) the
     *         given embedded sub-process.
     */
    public static Collection<Transition> getAllTransitionsInEmbeddedSubProc(
            Activity embeddedSubProcAct) {
        List<Transition> transitions = new ArrayList<Transition>();

        List<ActivitySet> sets =
                getEmbeddedSubProcessesTree(embeddedSubProcAct);
        for (ActivitySet actSet : sets) {
            transitions.addAll(actSet.getTransitions());
        }
        return transitions;
    }

    /**
     * Get all the intermediate event activities attached to the given
     * (task-type) activity's boundary
     * 
     * @param act
     * @return attached event activities or empty list.
     */
    public static Collection<Activity> getAttachedEvents(Activity act) {
        List<Activity> attachedEvents = new ArrayList<Activity>();
        if (act != null) {
            // Attached events must be in list of siblings.
            FlowContainer container = act.getFlowContainer();

            if (container != null) {
                Collection activities = container.getActivities();
                for (Iterator iter = activities.iterator(); iter.hasNext();) {
                    Activity activity = (Activity) iter.next();

                    // Check if it's an intermediate event...
                    Event ev = activity.getEvent();

                    if (ev instanceof IntermediateEvent) {
                        String taskId = ((IntermediateEvent) ev).getTarget();

                        if (taskId != null && act.getId().equals(taskId)) {
                            attachedEvents.add(activity);
                        }
                    }
                }
            }
        }
        return attachedEvents;
    }

    /**
     * Get ALL activities and artifacts in Pool
     * 
     * @param process
     * @return
     */
    public static Collection<EObject> getAllNodesInPool(Pool pool) {
        List<EObject> nodes = new ArrayList<EObject>();

        List lanes = pool.getLanes();
        if (lanes != null) {
            for (Iterator iter = lanes.iterator(); iter.hasNext();) {
                Lane lane = (Lane) iter.next();

                Collection<EObject> objs = getAllNodesInLane(lane);

                nodes.addAll(objs);
            }
        }
        return nodes;
    }

    /**
     * @param pool
     * @return All activities in the given pool.
     */
    public static Collection<Activity> getAllActivitiesInPool(Pool pool) {
        List<Activity> activities = new ArrayList<Activity>();

        for (Lane lane : pool.getLanes()) {
            activities.addAll(getActivitiesInLane(lane));
        }

        return activities;
    }

    /**
     * Get ALL activities and artifacts in lane
     * 
     * @param process
     * @return
     */
    public static Collection<EObject> getAllNodesInLane(Lane lane) {
        List<EObject> nodes = new ArrayList<EObject>();

        nodes.addAll(getActivitiesInLane(lane));
        nodes.addAll(getArtifactsInLane(lane));

        return nodes;
    }

    /**
     * @param lane
     * 
     * @return All artifacts in the given lane (Intentionally does not include
     *         content of embedded sub-process in lane).
     */
    public static List<Artifact> getArtifactsInLane(Lane lane) {
        List<Artifact> artifactsInLane = new ArrayList<Artifact>();

        Process process = getProcessFromLane(lane);

        if (process != null) {
            String laneId = lane.getId();

            Package pkg = process.getPackage();

            List<Artifact> artifacts = pkg.getArtifacts();

            if (artifacts != null) {
                for (Iterator iter = artifacts.iterator(); iter.hasNext();) {
                    Artifact art = (Artifact) iter.next();

                    NodeGraphicsInfo gi = getNodeGraphicsInfo(art);
                    if (gi != null && laneId.equals(gi.getLaneId())) {
                        artifactsInLane.add(art);
                    }

                }
            }
        }
        return artifactsInLane;
    }

    /**
     * @param lane
     * 
     * @return All activities in the given lane (Intentionally does not include
     *         content of embedded sub-process in lane).
     */
    public static List<Activity> getActivitiesInLane(Lane lane) {
        List<Activity> activitiesInLane = new ArrayList<Activity>();

        Process process = getProcessFromLane(lane);

        if (process != null) {
            String laneId = lane.getId();

            List<Activity> activities = process.getActivities();

            if (activities != null) {
                for (Iterator iter = activities.iterator(); iter.hasNext();) {
                    Activity act = (Activity) iter.next();

                    NodeGraphicsInfo gi = getNodeGraphicsInfo(act);
                    if (gi != null && laneId.equals(gi.getLaneId())) {
                        activitiesInLane.add(act);
                    }
                }
            }
        }
        return activitiesInLane;
    }

    /**
     * @param pool
     * @return all the transitions between activities in the given pool.
     */
    public static Collection<Transition> getAllTransitionsInPool(Pool pool) {
        List<Transition> transitionsInPool = new ArrayList<Transition>();

        Process process = getProcessFromPool(pool);

        if (process != null) {
            EList<Transition> transitions = process.getTransitions();

            if (!transitions.isEmpty()) {
                Map<String, Object> activityMap =
                        createMapById(getAllActivitiesInPool(pool));

                for (Transition transition : transitions) {
                    if (activityMap.containsKey(transition.getFrom())
                            || activityMap.containsKey(transition.getTo())) {
                        transitionsInPool.add(transition);
                    }
                }
            }
        }

        return transitionsInPool;
    }

    /**
     * Return the lane in the given process with the given id.
     * 
     * @param process
     * @param laneId
     * @return lane or null
     */
    public static Lane getLane(Process process, String laneId) {
        if (process != null && laneId != null) {
            Collection<Pool> pools = getProcessPools(process);
            for (Pool pool : pools) {
                for (Lane lane : pool.getLanes()) {
                    if (laneId.equals(lane.getId())) {
                        return lane;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get ALL activities and artifacts in embedded sub-process.
     * 
     * @param process
     * @return
     */
    public static Collection<EObject> getAllNodesInEmbeddedSubProc(
            Activity embeddedSubProc) {

        List<EObject> nodes = new ArrayList<EObject>();

        BlockActivity ba = embeddedSubProc.getBlockActivity();

        if (ba != null) {
            Process process = embeddedSubProc.getProcess();

            String actSetId = ba.getActivitySetId();

            ActivitySet actSet = process.getActivitySet(actSetId);

            if (actSet != null) {
                nodes.addAll(actSet.getActivities());

                Package pkg = process.getPackage();

                List<Artifact> artifactsInEmbeddedSubProcAct =
                        getArtifactsInEmbeddedSubProc(embeddedSubProc);

                nodes.addAll(artifactsInEmbeddedSubProcAct);
            }
        }
        return nodes;
    }

    /**
     * @param embeddedSubProc
     * @return All the artifacts in this embedded sub-process activity's content
     *         (INTENTIONALLY excluding recursion into child sub-procs.
     */
    public static List<Artifact> getArtifactsInEmbeddedSubProc(
            Activity embeddedSubProc) {

        List<Artifact> artifactsInEmbeddedSubProcAct =
                new ArrayList<Artifact>();

        ActivitySet actSet = getEmbeddedSubProcessActivitySet(embeddedSubProc);

        if (actSet != null) {
            String actSetId = actSet.getId();

            Package pkg = getPackage(embeddedSubProc);

            if (pkg != null) {
                List artifacts = pkg.getArtifacts();
                if (artifacts != null) {

                    for (Iterator iter = artifacts.iterator(); iter
                            .hasNext();) {
                        Artifact art = (Artifact) iter.next();

                        NodeGraphicsInfo gi = getNodeGraphicsInfo(art);
                        if (gi != null && actSetId.equals(gi.getLaneId())) {
                            artifactsInEmbeddedSubProcAct.add(art);
                        }
                    }
                }
            }
        }
        return artifactsInEmbeddedSubProcAct;
    }

    /**
     * Get ALL transitions in process Including ALL transitions in activity
     * sets.
     * 
     * @param process
     * @return
     */
    public static Collection<Transition> getAllTransitionsInProc(
            Process process) {
        List<Transition> activities = new ArrayList<Transition>();

        activities.addAll(process.getTransitions());

        List actSets = process.getActivitySets();

        for (Iterator iter = actSets.iterator(); iter.hasNext();) {
            ActivitySet actSet = (ActivitySet) iter.next();

            activities.addAll(actSet.getTransitions());
        }

        return activities;
    }

    /**
     * Get ALL message flows between objects in process
     * 
     * @param process
     * @return
     */
    public static Collection<MessageFlow> getAllMessageFlowsInProc(
            Process process) {
        List<MessageFlow> msgFlows = new ArrayList<MessageFlow>();

        Package pkg = process.getPackage();

        List<MessageFlow> pkgFlows = pkg.getMessageFlows();

        if (pkgFlows.size() > 0) {
            for (MessageFlow flow : pkgFlows) {

                EObject from = pkg.findNamedElement(flow.getSource());

                if (from != null) {
                    if (getProcess(from) == process) {
                        msgFlows.add(flow);
                    }
                } else {
                    EObject to = pkg.findNamedElement(flow.getTarget());

                    if (to != null) {
                        if (getProcess(to) == process) {
                            msgFlows.add(flow);
                        }
                    }
                }
            }
        }

        return msgFlows;
    }

    /**
     * Get ALL associations between objects in process
     * 
     * @param process
     * @return
     */
    public static Collection<Association> getAllAssociationsInProc(
            Process process) {
        List<Association> associations = new ArrayList<Association>();

        Package pkg = process.getPackage();

        List<Association> pkgAssociations = pkg.getAssociations();

        if (pkgAssociations.size() > 0) {
            for (Association flow : pkgAssociations) {

                EObject from = pkg.findNamedElement(flow.getSource());

                if (from != null) {
                    if (getProcess(from) == process) {
                        associations.add(flow);
                    }
                } else {
                    EObject to = pkg.findNamedElement(flow.getTarget());

                    if (to != null) {
                        if (getProcess(to) == process) {
                            associations.add(flow);
                        }
                    }
                }
            }
        }

        return associations;
    }

    /**
     * Get all artifacts within the given process.
     */
    public static Collection<Artifact> getAllArtifactsInProcess(
            Process process) {
        List<Artifact> artifacts = new ArrayList<Artifact>();

        Package pkg = process.getPackage();

        EList pkgArtifacts = pkg.getArtifacts();
        if (pkgArtifacts.size() > 0) {
            //
            // Artifacts GraphicalNode lane id is either id of lane or activity
            // set.
            // So grab a set of lane/act set ids.
            HashSet<String> laneOrActSetIds = new HashSet<String>();

            String procId = process.getId();

            for (Iterator poolIter = pkg.getPools().iterator(); poolIter
                    .hasNext();) {
                Pool pool = (Pool) poolIter.next();

                if (procId.equals(pool.getProcessId())) {
                    // Keep a list of lane id's
                    for (Iterator laneIter =
                            pool.getLanes().iterator(); laneIter.hasNext();) {
                        Lane lane = (Lane) laneIter.next();

                        laneOrActSetIds.add(lane.getId());
                    }
                }
            } // Next pool

            //
            // Add artifacts that in lanes / embedded sub-processes in
            // process.
            for (Iterator actSetIter =
                    process.getActivitySets().iterator(); actSetIter
                            .hasNext();) {
                ActivitySet actSet = (ActivitySet) actSetIter.next();

                laneOrActSetIds.add(actSet.getId());
            }

            //
            // Now go thru all artifacts and look for the artifacts.
            for (Iterator iter = pkgArtifacts.iterator(); iter.hasNext();) {
                Artifact art = (Artifact) iter.next();

                NodeGraphicsInfo gi = Xpdl2ModelUtil.getNodeGraphicsInfo(art);
                if (gi != null) {
                    if (laneOrActSetIds.contains(gi.getLaneId())
                            || (ArtifactType.GROUP_LITERAL
                                    .equals(art.getArtifactType())
                                    && procId.equals(gi.getLaneId()))) {
                        artifacts.add(art);
                    }
                }
            }
        }

        return artifacts;

    }

    /**
     * Get a list of all the pools in a process.
     * 
     * @param process
     * @return list of pools or empty list.
     */
    public static Collection<Pool> getProcessPools(Process process) {
        List<Pool> pools = new ArrayList<Pool>();

        if (process != null && process.getPackage() != null) {
            String processId = process.getId();

            Package pkg = process.getPackage();

            for (Iterator iter = pkg.getPools().iterator(); iter.hasNext();) {
                Pool pool = (Pool) iter.next();

                if (processId.equals(pool.getProcessId())) {
                    pools.add(pool);
                }
            }
        }

        return pools;
    }

    /**
     * @param process
     * @return A list of all the lanes in all the pools in the given process.
     */
    public static List<Lane> getProcessLanes(Process process) {
        List<Lane> lanes = new ArrayList<Lane>();

        for (Pool pool : Xpdl2ModelUtil.getProcessPools(process)) {
            for (Lane lane : pool.getLanes()) {
                lanes.add(lane);
            }
        }
        return lanes;
    }

    /**
     * Given a list of activities, return a list containing...
     * <li>The given list of activities/artifacts</li>
     * <li>Any transitions between them</li>
     * <li>Any sub-activities (recursively) for any embedded sub-proc
     * activity</li>
     * <li>All transitions between sub-activities</li>
     * <li>Optionally, any associations between anyy of the objects collected
     * above</li>
     * 
     * @return
     */
    public static Collection<EObject> getAllSelectedActivitiesAndTransitions(
            Collection<EObject> selectedObjects, boolean wantTransitions,
            boolean wantAssociations) {
        return internalGetAllSelectedActivitiesAndTransitions(selectedObjects,
                wantTransitions,
                wantAssociations,
                true);
    }

    /**
     * Given a list of activities, return a list containing...
     * <li>Any transitions between selected objects</li>
     * <li>Any sub-activities (recursively) for any embedded sub-proc
     * activity</li>
     * <li>All transitions between sub-activities</li>
     * <li>Optionally, any associations between anyy of the objects collected
     * above</li>
     * 
     * @return
     */
    public static Collection<EObject> getObjectConnections(
            Collection<EObject> selectedObjects, boolean wantTransitions,
            boolean wantAssociations) {
        return internalGetAllSelectedActivitiesAndTransitions(selectedObjects,
                wantTransitions,
                wantAssociations,
                false);
    }

    /**
     * Given a list of model objects, return a list containing...
     * <li>The given list of activities/artifacts</li>
     * <li>Any transitions between them</li>
     * <li>Any sub-activities (recursively) for any embedded sub-proc
     * activity</li>
     * <li>All transitions between sub-activities</li>
     * <li>Optionally, any associations between anyy of the objects collected
     * above</li>
     * 
     * @return
     */
    private static Collection<EObject> internalGetAllSelectedActivitiesAndTransitions(
            Collection<EObject> selectedObjects, boolean wantTransitions,
            boolean wantAssociations, boolean includeSelectedObjects) {

        HashSet<EObject> selectedObjsAndConns =
                new HashSet<EObject>(selectedObjects.size());

        FlowContainer flowContainer = null;

        List<EObject> tmpObjects = new ArrayList<EObject>();

        for (Object element : selectedObjects) {
            EObject obj = (EObject) element;

            tmpObjects.add(obj);

            if (obj instanceof Pool) {
                Collection poolObjs = getAllNodesInPool((Pool) obj);
                tmpObjects.addAll(poolObjs);
            } else if (obj instanceof Lane) {
                Collection laneObjs = getAllNodesInLane((Lane) obj);
                tmpObjects.addAll(laneObjs);
            }
        }

        if (includeSelectedObjects) {
            selectedObjsAndConns.addAll(tmpObjects);
        }

        // Collect flow container and ids of all objects.
        HashSet<String> objIds = new HashSet<String>(selectedObjects.size());
        for (Object element : tmpObjects) {
            EObject obj = (EObject) element;

            if (obj instanceof UniqueIdElement) {
                objIds.add(((UniqueIdElement) obj).getId());
            }

            if (obj instanceof Activity) {
                Activity activity = ((Activity) obj);

                if (flowContainer == null) {
                    flowContainer = activity.getFlowContainer();
                }

                // Add sub-activities/connections if this is an embedded
                // sub-process
                addEmbActivities(activity,
                        objIds,
                        selectedObjsAndConns,
                        wantTransitions,
                        includeSelectedObjects);
            }
        }

        if (flowContainer != null) {
            if (wantTransitions) {
                // Collect transitions across selected objects.
                for (Iterator iter =
                        flowContainer.getTransitions().iterator(); iter
                                .hasNext();) {
                    Transition trans = (Transition) iter.next();

                    if (objIds.contains(trans.getFrom())
                            && objIds.contains(trans.getTo())) {
                        selectedObjsAndConns.add(trans);

                        // Add the transition Id to list of object ids so that
                        // associations to/from these can be picked up.
                        objIds.add(trans.getId());
                    }
                }

                // Collect message flows across selected objects.
                Package pkg = Xpdl2ModelUtil.getPackage(flowContainer);
                for (Iterator iter = pkg.getMessageFlows().iterator(); iter
                        .hasNext();) {
                    MessageFlow msg = (MessageFlow) iter.next();

                    if (objIds.contains(msg.getSource())
                            && objIds.contains(msg.getTarget())) {
                        selectedObjsAndConns.add(msg);
                    }
                }
            }

            if (wantAssociations) {
                // Collect associations across selected objects.
                Package pkg = Xpdl2ModelUtil.getPackage(flowContainer);

                for (Iterator iter = pkg.getAssociations().iterator(); iter
                        .hasNext();) {
                    Association ass = (Association) iter.next();

                    if (objIds.contains(ass.getSource())
                            && objIds.contains(ass.getTarget())) {
                        selectedObjsAndConns.add(ass);
                    }
                }
            }
        }

        return selectedObjsAndConns;
    }

    /**
     * If activity is embedded sub-process then add it's child object id's to
     * the id list (and update selectedObjsAndConns) This is done recursively
     * for sub-sub-embedded sub-process activities.
     * 
     * @param act
     * @param objIds
     */
    private static void addEmbActivities(Activity activity, Set<String> objIds,
            Collection<EObject> selectedObjsAndConns, boolean wantTransitions,
            boolean addObjects) {

        if (activity.getBlockActivity() != null) {
            // if selection contains an embedded sub-proc then select
            // all it's transitions
            Process prc = activity.getProcess();

            String activitySetId =
                    activity.getBlockActivity().getActivitySetId();
            ActivitySet actSet = prc.getActivitySet(activitySetId);

            EList subTransitions = actSet.getTransitions();
            selectedObjsAndConns.addAll(subTransitions);

            EList subActivities = actSet.getActivities();

            if (addObjects) {
                selectedObjsAndConns.addAll(subActivities);
            }

            // Add artifacts within the act set
            Package pkg = Xpdl2ModelUtil.getPackage(activity);
            for (Iterator iter = pkg.getArtifacts().iterator(); iter
                    .hasNext();) {
                Artifact art = (Artifact) iter.next();

                NodeGraphicsInfo gi = Xpdl2ModelUtil.getNodeGraphicsInfo(art);

                if (gi != null && activitySetId.equals(gi.getLaneId())) {
                    objIds.add(art.getId());

                    if (addObjects) {
                        selectedObjsAndConns.add(art);
                    }
                }

            }

            if (wantTransitions) {
                // Add sub-transition id's to list.
                for (Iterator iter = subTransitions.iterator(); iter
                        .hasNext();) {
                    Transition trans = (Transition) iter.next();

                    objIds.add(trans.getId());
                }
            }

            // Recurse for all the sub-activities.
            for (Iterator iter = subActivities.iterator(); iter.hasNext();) {
                Activity act = (Activity) iter.next();

                objIds.add(act.getId());

                addEmbActivities(act,
                        objIds,
                        selectedObjsAndConns,
                        wantTransitions,
                        addObjects);
            }

        }

    }

    /**
     * @param lane
     */
    private static Process getProcessFromLane(Lane lane) {
        Process process = null;

        Pool pool = lane.getParentPool();

        if (pool != null) {
            process = getProcessFromPool(pool);
        }

        return process;
    }

    /**
     * @param pool
     * @param process
     * @return
     */
    private static Process getProcessFromPool(Pool pool) {
        Process process = null;

        String processId = pool.getProcessId();

        Package pkg = getPackage(pool);

        if (pkg != null) {
            process = pkg.getProcess(processId);
        }
        return process;
    }

    /**
     * Get parent activity set / lane of...
     * 
     * <li>Activity</li>
     * <li>Artifact</li>
     * 
     */
    public static EObject getContainer(EObject object) {
        EObject container = null;

        if (object instanceof Activity) {
            EObject actContainer = ((Activity) object).getFlowContainer();

            // Activity set is a valid return
            if ((actContainer instanceof ActivitySet)) {
                container = actContainer;
            }
        } else if (!(object instanceof Artifact)) {
            NodeGraphicsInfo gi = getNodeGraphicsInfo((Artifact) object);
            if (gi != null) {
                String containerId = gi.getLaneId();

                EObject cont = getPackage(object).findNamedElement(containerId);

                if (cont instanceof ActivitySet) {
                    container = cont;
                }
            }

            throw new UnsupportedOperationException(
                    Messages.Xpdl2ModelUtil_error4_label + object.getClass());
        }

        if (container == null) {
            // For nonactivity or activity in lane

            NodeGraphicsInfo gi = null;

            if (object instanceof Activity) {
                gi = getNodeGraphicsInfo((Activity) object);

            } else if (object instanceof Artifact) {
                gi = getNodeGraphicsInfo((Artifact) object);
            }

            if (gi != null) {
                String laneId = gi.getLaneId();
                if (getPackage(object) != null) {
                    EObject laneOrActSet =
                            getPackage(object).findNamedElement(laneId);

                    if (laneOrActSet instanceof Lane
                            || laneOrActSet instanceof ActivitySet) {
                        container = laneOrActSet;
                    }
                }
            }
        }

        return container;
    }

    /**
     * Return the XPDL package that object belongs to.
     * 
     * @param any
     *            Any model object
     * @return
     */
    public static Package getPackage(EObject any) {
        EObject pkg = any;

        while (pkg != null && !(pkg instanceof Package)) {
            pkg = pkg.eContainer();
        }
        return (Package) pkg;
    }

    /**
     * Get the parent pool of the given Pool/Lane/Activity/Artifact
     * 
     * @param object
     * 
     * @return parent pool or null on error.
     */
    public static Pool getParentPool(EObject object) {
        Pool parentPool = null;
        if (object instanceof Pool) {
            parentPool = (Pool) object;
        } else if (object instanceof Lane) {
            parentPool = ((Lane) object).getParentPool();
        } else {

            EObject container = Xpdl2ModelUtil.getContainer(object);
            if (container instanceof ActivitySet) {
                container = Xpdl2ModelUtil
                        .getActivitySetParentLane((ActivitySet) container);
            }

            if (container instanceof Lane) {
                parentPool = ((Lane) container).getParentPool();
            }
        }

        return parentPool;
    }

    /**
     * Get bounding rectangle of an object according to its model definition
     * 
     * @param gi
     * @return
     */
    public static org.eclipse.swt.graphics.Rectangle getObjectBounds(
            GraphicalNode node) {
        org.eclipse.swt.graphics.Rectangle rc =
                new org.eclipse.swt.graphics.Rectangle(0, 0, 10, 10);

        NodeGraphicsInfo gi = getNodeGraphicsInfo(node);

        if (gi != null) {
            Coordinates c = gi.getCoordinates();
            if (c != null) {
                rc.x = (int) c.getXCoordinate();
                rc.y = (int) c.getYCoordinate();
            }

            if (gi.isSetWidth()) {
                rc.width = (int) gi.getWidth();
            }

            if (gi.isSetHeight()) {
                rc.height = (int) gi.getHeight();
            }

            // Coordinates are centered (except for width of text annotations
            boolean isAnnotation = false;
            if (node instanceof Artifact) {
                if (ArtifactType.ANNOTATION_LITERAL
                        .equals(((Artifact) node).getArtifactType())) {
                    isAnnotation = true;
                }
            }

            if (!isAnnotation) {
                rc.x -= rc.width / 2;
            }
            rc.y -= rc.height / 2;
        }
        return rc;
    }

    /**
     * Reassign unique ids of the given UniqueIdElement model objects (and all
     * children of them) and update any references to those id's in model
     * elements in the collection that are of <b>special type
     * IDRefereceString</b>.
     * <p>
     * See reassignUniqueIds(Collection elementCopies, EditingDomain
     * editingDomain, String idSuffix) for more details.
     * 
     * @param elementCopies
     * @param editingDomain
     * @return
     */
    public static Map<String, EObject> reassignUniqueIds(
            Collection elementCopies, EditingDomain editingDomain) {
        return reassignUniqueIds(elementCopies, editingDomain, null);
    }

    /**
     * Reassign unique ids of the given UniqueIdElement model objects (and all
     * children of them) and update any references to those id's in model
     * elements in the collection that are of <b>special type
     * IDRefereceString</b>.
     * <p>
     * Basically for this method to work automatically, ANY xml Element that
     * sub-classes UniqueIdElement will have it's Id attribute value replaced
     * with a new universally unique id <b>OR suffixed with the given
     * idSuffix</b> (this may be useful when you wish to copy elements and
     * assign new unique ids BUT guarantee the result is the same if you do it
     * again).
     * <p>
     * Then ANY xml Attribute which has a type of IDReferenceString is
     * considered to be a reference to a UniqueIdElement-based element and
     * therefore it will be changed to the new id for that element.
     * <p>
     * NOTE !!! This method fixes ONLY those id references that are entirely
     * internal to the object in collection. i.e. it will not reset process id
     * on pools or lane id in activity if lane is not also in objects.
     * <p>
     * NOTE 2 !!! If there is a IDReferenceString type attribute in an element
     * to be pasted that references a UniqueIdElement based element that is NOT
     * in the list of elements to be pasted then the value of the referencing
     * attribute will be set to REMAIN UNCHANGED. This circumstance should ONLY
     * EVER BE TEMPORARY (for instance when JUST an activity is in the list of
     * objects to be pasted (without its parent lane) then it is the job of the
     * calling code to reset the laneID to the target lane. <br/>
     * 
     * @param elementCopies
     *            Collection of all the copied model elements.
     * @param editingDomain
     * @param idSuffix
     *            If you want to simple add a suffix to id the pass this as
     *            non-null. If you wish to assign completely new id's then pass
     *            this as null).
     * 
     * @return Map of old Id to Object (use the object's getId() to get new
     *         value). <!-- end-user-doc -->
     * 
     * @see com.tibco.xpd.xpdl2.resources.EditingDomainWithSystemClipboard#fixClipboardCopyObjects(java.util.Collection)
     */
    public static Map<String, EObject> reassignUniqueIds(
            Collection elementCopies, EditingDomain editingDomain,
            String idSuffix) {

        Map uniqueIdElementMap = new HashMap();

        // Go thru paste objects assigning new unique ids to objects that have
        // them.
        if (elementCopies != null) {
            //
            // Keep track of data field / formal parameter id changes.
            HashMap<String, String> dataIdMap = new HashMap<String, String>();

            // We can afford to keep just one list as all id's are unique from
            // each other regardless of object type.

            // First off look for any object that subclasses UniqueIdElement
            // These are the ones with unique Id's.
            for (Iterator iter = elementCopies.iterator(); iter.hasNext();) {
                EObject obj = (EObject) iter.next();

                // Keep track of data field / param id reassignments.
                String oldDataId = null;
                if (obj instanceof ProcessRelevantData) {
                    oldDataId = ((ProcessRelevantData) obj).getId();
                }

                // If this element subclasses UniqueElementId then reset
                // it's Id value to a new universally unique Id.
                resetUniqueIds(uniqueIdElementMap, obj, idSuffix);

                // Keep track of data field / param id reassignments.
                if (obj instanceof ProcessRelevantData) {
                    String newDataId = ((ProcessRelevantData) obj).getId();

                    dataIdMap.put(oldDataId, newDataId);
                }

                // Create a tree iterator from this top level object
                // (which will return the entire hierarchy under top element.
                for (Iterator treeIt = obj.eAllContents(); treeIt.hasNext();) {
                    Object element = treeIt.next();

                    // If this element subclasses UniqueElementId then reset
                    // it's Id value to a new universally unique Id.
                    resetUniqueIds(uniqueIdElementMap, element, idSuffix);
                }
            }

            // Now fix the references to these changed id's.
            // i.e. Transitions to / from id's. Activities LaneId's and so on.

            // Create an reference replacer - this allows for other plug-ins
            // (such as service task implementer etc) to be notified of id
            // changes to data fields and formal parameters.
            // They can then change references to those fields/params in their
            // own extension parts of the mode.
            Xpdl2FieldOrParamReplacer referenceReplacer =
                    new Xpdl2FieldOrParamReplacer(dataIdMap, true);

            // Go thru the objects scanning for things we need to change.
            for (Iterator p = elementCopies.iterator(); p.hasNext();) {
                EObject obj = (EObject) p.next();

                replaceUniqueIdReferences(uniqueIdElementMap,
                        obj,
                        referenceReplacer,
                        editingDomain);

                // Create a tree iterator from this top level object
                // (which will return the entire hierarchy under top element
                // this will save us recursing).

                for (Iterator treeIt = obj.eAllContents(); treeIt.hasNext();) {
                    EObject eobj = (EObject) treeIt.next();

                    replaceUniqueIdReferences(uniqueIdElementMap,
                            eobj,
                            referenceReplacer,
                            editingDomain);
                } // Get get object under top level objects's tree
            } // get next object in collection of objects to paste.
        }

        if (!uniqueIdElementMap.isEmpty()) {
            /*
             * XPD-6874: Give a chance to the contributors of the
             * 'fixReassignedIdReferencesContribution' extension points so that
             * they can fix any references that depend on the re-generated ids.
             */
            FixReassignedIdReferencesExtensionPointManager extensionPointManager =
                    new FixReassignedIdReferencesExtensionPointManager();

            List<FixReassignedIdReferencesExtensionPoint> fixReassignedIdReferencesExtension =
                    extensionPointManager
                            .getFixReassignedIdReferencesExtension();

            for (FixReassignedIdReferencesExtensionPoint fixReassignedIdReferencesExtensionPoint : fixReassignedIdReferencesExtension) {

                AbstractUniqueIdsReassignedListener uniqueIdsReassignedListener =
                        fixReassignedIdReferencesExtensionPoint
                                .getUniqueIdsReassignedListener();

                if (uniqueIdsReassignedListener != null) {
                    /*
                     * update the references.
                     */
                    uniqueIdsReassignedListener.updateReassignedIdReferences(
                            elementCopies,
                            uniqueIdElementMap);
                }
            }
        }
        return (uniqueIdElementMap);
    }

    /**
     * @param uniqueIdElementMap
     * @param element
     * @param idSuffix
     *            If you want to simple add a suffix to id the pass this as
     *            non-null. If you wish to assign completely new id's then pass
     *            this as null).
     */
    private static void resetUniqueIds(Map uniqueIdElementMap, Object element,
            String idSuffix) {
        // If this element subclasses UniqueElementId then reset
        // it's Id value to a new universally unique Id.
        if (element instanceof UniqueIdElement) {
            // Create a new Id
            UniqueIdElement uid = (UniqueIdElement) element;
            String oldId = uid.getId();

            // Generate a new unique id, if suffix is passed then use it else
            // generate a completely new one.
            String newUID;
            if (idSuffix != null) {
                newUID = oldId + idSuffix;
            } else {
                newUID = XpdEcoreUtil.generateUUID();
            }

            uid.eSet(Xpdl2Package.eINSTANCE.getUniqueIdElement_Id(), newUID);

            // Put the object in the map, keyed on old id.
            uniqueIdElementMap.put(oldId, element);
        }
    }

    /**
     * @param uniqueIdElementMap
     * @param eobj
     * @param referenceReplacer
     */
    private static void replaceUniqueIdReferences(Map uniqueIdElementMap,
            EObject eobj, Xpdl2FieldOrParamReplacer referenceReplacer,
            EditingDomain editingDomain) {
        // All model element attributes that are a Reference
        // to another element (via the target element's Id
        // attribute) should have an emf eType = IdReferenceString.
        //
        // So all we have to do is look thru the attributes of all
        // elements looking for ones which are of type
        // IdReferenceString and lookup new id for the target
        // element in the id map and replace the existing value.
        List<EAttribute> allAttrs = new ArrayList<EAttribute>();
        allAttrs.addAll(eobj.eClass().getEAllAttributes());

        // EMF returns OtherAttributes as a single featuremap so we have to add
        // the attributes within it to our list.
        if (eobj instanceof OtherAttributesContainer) {
            OtherAttributesContainer oac = (OtherAttributesContainer) eobj;

            FeatureMap otherAttrs = oac.getOtherAttributes();
            for (int i = 0; i < otherAttrs.size(); i++) {
                EStructuralFeature feat = otherAttrs.getEStructuralFeature(i);

                if (feat instanceof EAttribute) {
                    allAttrs.add((EAttribute) feat);
                }
            }
        }

        for (Iterator attrIter = allAttrs.iterator(); attrIter.hasNext();) {
            EAttribute attr = (EAttribute) attrIter.next();

            // If this is an IDReferenceString type attribute then
            // its value is the unique Id of the element it
            // references and hence should be swapped for the new
            // one.
            if (attr.getEType()
                    .equals(Xpdl2Package.eINSTANCE.getIdReferenceString())) {

                String oldId = (String) eobj.eGet(attr);
                if (oldId != null && oldId.length() > 0) {
                    String newId = oldId;

                    UniqueIdElement uidObj =
                            (UniqueIdElement) uniqueIdElementMap.get(oldId);

                    if (uidObj != null) {
                        String id = uidObj.getId();
                        if (id != null && id.length() > 0) {
                            newId = id;
                        }
                    }

                    eobj.eSet(attr, newId);
                }
            }
        }

        //
        // Allow other plug-ins opportunity to replace their references
        // to data fields / formal parameters whose id's have changed.
        if (eobj instanceof Activity) {
            referenceReplacer.replaceFieldReferences(editingDomain,
                    (Activity) eobj);
        } else if (eobj instanceof Transition) {
            referenceReplacer.replaceFieldReferences(editingDomain,
                    (Transition) eobj);
        }

        return;
    }

    /**
     * Is the given object an Intermediate event that is attached to a task
     * border.
     * 
     * @param activity
     * @return
     */
    public static boolean isEventAttachedToTask(Object activity) {
        if (activity instanceof Activity) {
            Event ev = ((Activity) activity).getEvent();
            if (ev instanceof IntermediateEvent) {
                String taskId = ((IntermediateEvent) ev).getTarget();
                if (taskId != null && taskId.length() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Test if the given activity is one that indicates a start-of-process.
     * <p>
     * This is true of any activity that has no incoming flow excluding...
     * <li>intermediate events attached to task boundary</li>
     * <li>intermediate events with no incoming flow</li>
     * <li>event sub-processes</li>
     * <li>ad-hoc tasks</li> (as these are classed as start of a separate event
     * handler flow and do not'start' a process).
     * </p>
     * <br/>
     * <p>
     * The result is from the perspective of the activity's flow container i.e.
     * An activity within an embedded sub-process that meets the above criteria
     * is counted as a start of (embedded) process.
     * 
     * @param activity
     * @return <code>true</code> if activity is a place where a process can be
     *         started.
     */
    public static boolean isStartProcessActivity(Activity activity) {
        if (activity != null) {
            if (activity.getIncomingTransitions().isEmpty()) {

                /* Ignore intermediate events attached to task. */
                if (!isEventAttachedToTask(activity)) {

                    /*
                     * Ignore event handler activities XPD-6704: And Event
                     * sub-processes.
                     */
                    if (!isEventHandlerActivity(activity)
                            && !isEventSubProcess(activity)) {

                        /*
                         * Ignore compensation activities (they're linked using
                         * associations not incoming transitions so would not be
                         * caught by condition above) and also Catch Link
                         * activities.
                         */
                        if (!isCompensationActivity(activity)
                                && !isCatchLinkEvent(activity)) {

                            /*
                             * Sid XPD-6893: ad-hoc tasks are not start of
                             * process activities.
                             */
                            if (getOtherElement(activity,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_AdHocTaskConfiguration()) == null) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check if this activity is a Catch Link activity.
     * 
     * @param activity
     * @return
     */
    private static boolean isCatchLinkEvent(Activity activity) {

        if (activity.getEvent() instanceof IntermediateEvent) {
            EObject typeNode = activity.getEvent().getEventTriggerTypeNode();

            if (typeNode instanceof TriggerResultLink) {
                return ((TriggerResultLink) typeNode)
                        .getCatchThrow() == CatchThrow.CATCH;
            }
        }

        return false;
    }

    /**
     * Test if the given activity is one that is at the start of a thread of
     * flow
     * <p>
     * This is true of any activity that has no incoming flow excluding
     * intermediate events attached to task boundary, compensation tasks, catch
     * link events.
     * <p>
     * The result is from the perspective of the activity's flow container i.e.
     * An activity within an embedded sub-process that meets the above criteria
     * is counted as a start of (embedded) process.
     * 
     * @param activity
     * @return <code>true</code> if activity is a place where a process can be
     *         started.
     */
    public static boolean isStartFlowActivity(Activity activity) {
        if (activity != null) {
            if (activity.getIncomingTransitions().isEmpty()) {

                /* Ignore intermediate events attached to task. */
                if (!isEventAttachedToTask(activity)) {
                    /*
                     * Ignore compensation activities (they're linked using
                     * associations not incoming transitions so would not be
                     * caught by condition above) and also Catch Link
                     * activities.
                     */
                    if (!isCompensationActivity(activity)
                            && !isCatchLinkEvent(activity)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * An event handler is any intermediate event with no incoming flow.
     * Although this breaks the initial rules laid out in BPMN 1.2 specification
     * (that these must have a single incoming flow) it matches the correlation
     * between BPMN 1.2 and BPEL trigger events in the same specification.
     * 
     * @param activity
     * @return <code>true</code> if the given activity is an event handler
     *         activity
     */
    public static boolean isEventHandlerActivity(Activity activity) {
        if (activity != null) {
            /**
             * SID NOTE: NEVER EVER be tempted to make this treat Start event in
             * event sub-process as an event handler activity (otherwise we
             * won't distinguish betwen event handlers in event sub-processes
             * and the start event in same
             */
            if (activity.getEvent() instanceof IntermediateEvent) {

                EObject typeNode =
                        activity.getEvent().getEventTriggerTypeNode();
                boolean isCatchLinkEvent =
                        (typeNode instanceof TriggerResultLink
                                && CatchThrow.CATCH
                                        .equals(((TriggerResultLink) typeNode)
                                                .getCatchThrow()));

                if (!isCatchLinkEvent) {
                    if (!isEventAttachedToTask(activity)) {
                        if (activity.getIncomingTransitions().isEmpty()) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Check wether given name for data field / param is a duplicate within it's
     * scope and return first duplicate if there is one.
     * 
     * @param container
     * @param data2
     * @param checkName
     * @return
     */
    public static ProcessRelevantData getDuplicateFieldOrParam(
            EObject container, ProcessRelevantData checkData,
            String checkName) {

        // Build a list of fields and params 'in scope'
        Map<String, ProcessRelevantData> currNames =
                new HashMap<String, ProcessRelevantData>();

        if (container instanceof Package) {
            // Package container has all fields/params in pkg/all processes in
            // scope.
            Package pkg = (Package) container;
            for (Iterator iter = pkg.getDataFields().iterator(); iter
                    .hasNext();) {
                ProcessRelevantData data = (ProcessRelevantData) iter.next();

                // Don't add field we're changing name of.
                /**
                 * checkData would be null when we check duplicateFieldOrParam
                 * for a participant. hence adding the names to the map even in
                 * case of null checkData
                 */
                if (null == checkData || data != checkData) {
                    currNames.put(data.getName(), data);
                }
            }

            // Add the fields and params from all processes.
            for (Iterator iter = pkg.getProcesses().iterator(); iter
                    .hasNext();) {
                Process process = (Process) iter.next();

                for (Iterator paramIter = LocalPackageProcessInterfaceUtil
                        .getAllFormalParameters(process).iterator(); paramIter
                                .hasNext();) {
                    ProcessRelevantData data =
                            (ProcessRelevantData) paramIter.next();

                    // Don't add field we're changing name of.
                    /**
                     * checkData would be null when we check
                     * duplicateFieldOrParam for a participant. hence adding the
                     * names to the map even in case of null checkData
                     */
                    if (null == checkData || data != checkData) {
                        currNames.put(data.getName(), data);
                    }
                }

                for (Iterator fieldIter =
                        process.getDataFields().iterator(); fieldIter
                                .hasNext();) {
                    ProcessRelevantData data =
                            (ProcessRelevantData) fieldIter.next();

                    // Don't add field we're changing name of.
                    /**
                     * checkData would be null when we check
                     * duplicateFieldOrParam for a participant. hence adding the
                     * names to the map even in case of null checkData
                     */
                    if (null == checkData || data != checkData) {
                        currNames.put(data.getName(), data);
                    }
                }
                // MR 39410 - begin
                /**
                 * add fields from all activities of all processes
                 */
                getDupActLocalDataForProcess(checkData, currNames, process);
                // MR 39410 - end
            }

        } else if (container instanceof Process) {
            // Fields in scope of a process field are Package fields and fields
            // or params in same process.
            Process process = (Process) container;

            Package pkg = process.getPackage();

            if (pkg != null) {
                for (Iterator iter = pkg.getDataFields().iterator(); iter
                        .hasNext();) {
                    ProcessRelevantData data =
                            (ProcessRelevantData) iter.next();

                    // Don't add field we're changing name of.
                    /**
                     * checkData would be null when we check
                     * duplicateFieldOrParam for a participant. hence adding the
                     * names to the map even in case of null checkData
                     */
                    if (null == checkData || data != checkData) {
                        currNames.put(data.getName(), data);
                    }
                }
            }

            for (Iterator paramIter =
                    process.getDataFields().iterator(); paramIter.hasNext();) {
                ProcessRelevantData data =
                        (ProcessRelevantData) paramIter.next();

                // Don't add field we're changing name of.
                /**
                 * checkData would be null when we check duplicateFieldOrParam
                 * for a participant. hence adding the names to the map even in
                 * case of null checkData
                 */
                if (null == checkData || data != checkData) {
                    currNames.put(data.getName(), data);
                }
            }

            for (Iterator fieldIter = LocalPackageProcessInterfaceUtil
                    .getAllFormalParameters(process).iterator(); fieldIter
                            .hasNext();) {
                ProcessRelevantData data =
                        (ProcessRelevantData) fieldIter.next();

                // Don't add field we're changing name of.
                /**
                 * checkData would be null when we check duplicateFieldOrParam
                 * for a participant. hence adding the names to the map even in
                 * case of null checkData
                 */
                if (null == checkData || data != checkData) {
                    currNames.put(data.getName(), data);
                }
            }
            // MR 39410 - begin
            /**
             * add fields from all activities of a process
             */
            getDupActLocalDataForProcess(checkData, currNames, process);
            // MR 39410 - end
        }

        else if (container instanceof ProcessInterface) {
            // Fields in scope of a process field are Package fields and fields
            // or params in same process.
            ProcessInterface processInterface = (ProcessInterface) container;

            EObject procIfcContainer = processInterface.eContainer();

            EObject procIfcContainerContainer = null;
            if (procIfcContainer != null) {
                procIfcContainerContainer = procIfcContainer.eContainer();

            }
            if (procIfcContainerContainer != null) {
                Package pkg = (Package) procIfcContainerContainer;

                for (Iterator iter = pkg.getDataFields().iterator(); iter
                        .hasNext();) {
                    ProcessRelevantData data =
                            (ProcessRelevantData) iter.next();

                    // Don't add field we're changing name of.
                    /**
                     * checkData would be null when we check
                     * duplicateFieldOrParam for a participant. hence adding the
                     * names to the map even in case of null checkData
                     */
                    if (null == checkData || data != checkData) {
                        currNames.put(data.getName(), data);
                    }
                }
            }

            for (Iterator fieldIter =
                    processInterface.getFormalParameters().iterator(); fieldIter
                            .hasNext();) {
                ProcessRelevantData data =
                        (ProcessRelevantData) fieldIter.next();

                // Don't add field we're changing name of.
                /**
                 * checkData would be null when we check duplicateFieldOrParam
                 * for a participant. hence adding the names to the map even in
                 * case of null checkData
                 */
                if (null == checkData || data != checkData) {
                    currNames.put(data.getName(), data);
                }
            }
            // MR 39410 - begin
            /**
             * add activity local data fields of all processes that implement
             * this process interface
             */
            List<Process> implementingProcesses =
                    LocalPackageProcessInterfaceUtil
                            .getImplementingProcesses(processInterface);
            for (Process process : implementingProcesses) {
                getDupActLocalDataForProcess(checkData, currNames, process);
            }
            // MR 39410 - end
            // MR 39410 - begin
        } else if (container instanceof Activity) {
            Activity activity = (Activity) container;
            /**
             * if there are two embedded sub procs in a process say proc1 and
             * proc2; and proc1 has field1 then field1 in proc2 must be allowed
             */
            for (DataField dataField : activity.getProcess().getDataFields()) {
                if (null == checkData || dataField != checkData) {
                    currNames.put(dataField.getName(), dataField);
                }
            }
            for (DataField dataField : activity.getDataFields()) {
                if (null == checkData || dataField != checkData) {
                    currNames.put(dataField.getName(), dataField);
                }
            }

            /**
             * get all embedded sub procs for this embedded sub proc and add
             * their fields
             */
            if (null != activity.getBlockActivity()) {
                List<ActivitySet> embSubProcTree =
                        getEmbeddedSubProcessesTree(activity);
                for (ActivitySet activitySet : embSubProcTree) {
                    for (Activity actInES : activitySet.getActivities()) {
                        for (DataField dataField : actInES.getDataFields()) {
                            if (null == checkData || dataField != checkData) {
                                currNames.put(dataField.getName(), dataField);
                            }
                        }
                    }
                }
            }
            /**
             * add data fields of all activities present in the embedded sub
             * proc
             */
            ActivitySet actSet = getEmbeddedSubProcessActivitySet(activity);
            if (null != actSet) {
                for (Activity act : actSet.getActivities()) {
                    for (DataField dataField : act.getDataFields()) {
                        if (null == checkData || dataField != checkData) {
                            currNames.put(dataField.getName(), dataField);
                        }
                    }
                }
            }

            if (activity.eContainer() instanceof ActivitySet) {
                /**
                 * if the selected embSubProcActivity is embedded, then add
                 * parent embedded sub proc data fields
                 */
                List<ProcessRelevantData> embeddedSubProcessSetScopeData =
                        LocalPackageProcessInterfaceUtil
                                .getEmbeddedSubProcessSetScopeData(
                                        (ActivitySet) activity.eContainer());
                for (ProcessRelevantData processRelevantData : embeddedSubProcessSetScopeData) {
                    if (null == checkData || processRelevantData != checkData) {
                        currNames.put(processRelevantData.getName(),
                                processRelevantData);
                    }
                }
            }

        }
        // MR 39410 - end
        // Check if the new name already exists.
        ProcessRelevantData duplicate = currNames.get(checkName);
        return duplicate;
    }

    /**
     * @param checkData
     * @param currNames
     * @param process
     */
    private static void getDupActLocalDataForProcess(
            ProcessRelevantData checkData,
            Map<String, ProcessRelevantData> currNames, Process process) {
        /**
         * get all activities in a process and get all data fields and add to
         * the list
         */
        Collection<Activity> allActivitiesInProc =
                getAllActivitiesInProc(process);
        for (Activity activity : allActivitiesInProc) {
            for (DataField dataField : activity.getDataFields()) {
                if (null == checkData || dataField != checkData) {
                    currNames.put(dataField.getName(), dataField);
                }
            }
        }
    }

    /**
     * Check whether given name for data field / param is a duplicate within
     * it's scope and return first duplicate if there is one.
     * 
     * @param container
     * @param data2
     * @param checkName
     * @return
     */
    public static ProcessRelevantData getDuplicateDisplayFieldOrParam(
            EObject container, ProcessRelevantData checkData,
            String checkName) {

        // Build a list of fields and params 'in scope'
        Map<String, ProcessRelevantData> currNames =
                new HashMap<String, ProcessRelevantData>();

        if (container instanceof Package) {
            // Package container has all fields/params in pkg/all processes in
            // scope.
            Package pkg = (Package) container;
            for (Iterator iter = pkg.getDataFields().iterator(); iter
                    .hasNext();) {
                ProcessRelevantData data = (ProcessRelevantData) iter.next();

                // Don't add field we're changing name of.
                /**
                 * checkData would be null when we check duplicateFieldOrParam
                 * for a participant. hence adding the names to the map even in
                 * case of null checkData
                 */
                if (null == checkData || data != checkData) {
                    String name = (String) getOtherAttribute(data,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName());
                    if (name != null) {
                        currNames.put(name, data);
                    }
                }
            }

            // Add the fields and params from all processes.
            for (Iterator iter = pkg.getProcesses().iterator(); iter
                    .hasNext();) {
                Process process = (Process) iter.next();

                // MR 39291 - don't need to check implemented interface formal
                // params when checking for duplicates of package level fields.
                // We need to check the intefaces themselves (later)!
                for (Iterator paramIter =
                        process.getFormalParameters().iterator(); paramIter
                                .hasNext();) {
                    ProcessRelevantData data =
                            (ProcessRelevantData) paramIter.next();

                    // Don't add field we're changing name of.
                    /**
                     * checkData would be null when we check
                     * duplicateFieldOrParam for a participant. hence adding the
                     * names to the map even in case of null checkData
                     */
                    if (null == checkData || data != checkData) {
                        String name = (String) getOtherAttribute(data,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_DisplayName());
                        if (name != null) {
                            currNames.put(name, data);
                        }
                    }
                }

                for (Iterator fieldIter =
                        process.getDataFields().iterator(); fieldIter
                                .hasNext();) {
                    ProcessRelevantData data =
                            (ProcessRelevantData) fieldIter.next();

                    // Don't add field we're changing name of.
                    /**
                     * checkData would be null when we check
                     * duplicateFieldOrParam for a participant. hence adding the
                     * names to the map even in case of null checkData
                     */
                    if (null == checkData || data != checkData) {
                        String name = (String) getOtherAttribute(data,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_DisplayName());
                        if (name != null) {
                            currNames.put(name, data);
                        }
                    }
                }
                // MR 39410 - begin
                /**
                 * add fields from all activities of all processes
                 */
                getDupDisplayActLocalDataForProcess(checkData,
                        currNames,
                        process);
                // MR 39410 - end
            }

            //
            // MR 39291 - Check process interface parameters
            ProcessInterfaces processInterfaces =
                    LocalPackageProcessInterfaceUtil.getProcessInterfaces(pkg);
            if (processInterfaces != null) {
                for (ProcessInterface processInterface : processInterfaces
                        .getProcessInterface()) {
                    for (Iterator paramIter = processInterface
                            .getFormalParameters().iterator(); paramIter
                                    .hasNext();) {
                        ProcessRelevantData data =
                                (ProcessRelevantData) paramIter.next();

                        // Don't add field we're changing name of.
                        /**
                         * checkData would be null when we check
                         * duplicateFieldOrParam for a participant. hence adding
                         * the names to the map even in case of null checkData
                         */
                        if (null == checkData || data != checkData) {
                            String name = (String) getOtherAttribute(data,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_DisplayName());
                            if (name != null) {
                                currNames.put(name, data);
                            }
                        }
                    }
                }
            }

        } else if (container instanceof Process) {
            // Fields in scope of a process field are Package fields and fields
            // or params in same process.
            Process process = (Process) container;

            Package pkg = process.getPackage();

            if (pkg != null) {
                for (Iterator iter = pkg.getDataFields().iterator(); iter
                        .hasNext();) {
                    ProcessRelevantData data =
                            (ProcessRelevantData) iter.next();

                    // Don't add field we're changing name of.
                    /**
                     * checkData would be null when we check
                     * duplicateFieldOrParam for a participant. hence adding the
                     * names to the map even in case of null checkData
                     */
                    if (null == checkData || data != checkData) {
                        String name = (String) getOtherAttribute(data,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_DisplayName());
                        if (name != null) {
                            currNames.put(name, data);
                        }
                    }
                }
            }

            for (Iterator paramIter =
                    process.getDataFields().iterator(); paramIter.hasNext();) {
                ProcessRelevantData data =
                        (ProcessRelevantData) paramIter.next();

                // Don't add field we're changing name of.
                /**
                 * checkData would be null when we check duplicateFieldOrParam
                 * for a participant. hence adding the names to the map even in
                 * case of null checkData
                 */
                if (null == checkData || data != checkData) {
                    String name = (String) getOtherAttribute(data,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName());
                    if (name != null) {
                        currNames.put(name, data);
                    }
                }
            }

            for (Iterator fieldIter = LocalPackageProcessInterfaceUtil
                    .getAllFormalParameters(process).iterator(); fieldIter
                            .hasNext();) {
                ProcessRelevantData data =
                        (ProcessRelevantData) fieldIter.next();

                // Don't add field we're changing name of.
                /**
                 * checkData would be null when we check duplicateFieldOrParam
                 * for a participant. hence adding the names to the map even in
                 * case of null checkData
                 */
                if (null == checkData || data != checkData) {
                    String name = (String) getOtherAttribute(data,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName());
                    if (name != null) {
                        currNames.put(name, data);
                    }
                }
            }
            // MR 39410 - begin
            /**
             * add fields from all activities of a process
             */
            getDupDisplayActLocalDataForProcess(checkData, currNames, process);
            // MR 39410 - end
        }

        else if (container instanceof ProcessInterface) {
            // Fields in scope of a process field are Package fields and fields
            // or params in same process.
            ProcessInterface processInterface = (ProcessInterface) container;

            EObject procIfcContainer = processInterface.eContainer();

            EObject procIfcContainerContainer = null;
            if (procIfcContainer != null) {
                procIfcContainerContainer = procIfcContainer.eContainer();

            }
            if (procIfcContainerContainer != null) {
                Package pkg = (Package) procIfcContainerContainer;

                for (Iterator iter = pkg.getDataFields().iterator(); iter
                        .hasNext();) {
                    ProcessRelevantData data =
                            (ProcessRelevantData) iter.next();

                    // Don't add field we're changing name of.
                    /**
                     * checkData would be null when we check
                     * duplicateFieldOrParam for a participant. hence adding the
                     * names to the map even in case of null checkData
                     */
                    if (null == checkData || data != checkData) {
                        String name = (String) getOtherAttribute(data,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_DisplayName());
                        if (name != null) {
                            currNames.put(name, data);
                        }
                    }
                }
            }

            for (Iterator fieldIter =
                    processInterface.getFormalParameters().iterator(); fieldIter
                            .hasNext();) {
                ProcessRelevantData data =
                        (ProcessRelevantData) fieldIter.next();

                // Don't add field we're changing name of.
                /**
                 * checkData would be null when we check duplicateFieldOrParam
                 * for a participant. hence adding the names to the map even in
                 * case of null checkData
                 */
                if (null == checkData || data != checkData) {
                    String name = (String) getOtherAttribute(data,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName());
                    if (name != null) {
                        currNames.put(name, data);
                    }
                }
            }
            // MR 39410 - begin
            /**
             * add activity local data fields of all processes that implement
             * this process interface
             */
            List<Process> implementingProcesses =
                    LocalPackageProcessInterfaceUtil
                            .getImplementingProcesses(processInterface);
            for (Process process : implementingProcesses) {

                getDupDisplayActLocalDataForProcess(checkData,
                        currNames,
                        process);
            }
            // MR 39410 - end
            // MR 39410 - begin
        } else if (container instanceof Activity) {
            Activity activity = (Activity) container;

            /**
             * if there are two embedded sub procs in a process say proc1 and
             * proc2. proc1 has field1 then field1 in proc2 must be allowed
             */
            for (DataField dataField : activity.getProcess().getDataFields()) {
                if (null == checkData || dataField != checkData) {
                    String name = (String) getOtherAttribute(dataField,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName());
                    if (name != null) {
                        currNames.put(name, dataField);
                    }
                }
            }
            /**
             * to add the selected embedded sub proc data fields
             */
            for (DataField dataField : activity.getDataFields()) {
                if (null == checkData || dataField != checkData) {
                    String name = (String) getOtherAttribute(dataField,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName());
                    if (name != null) {
                        currNames.put(name, dataField);
                    }
                }
            }
            /**
             * get all embedded sub procs for this embedded sub proc and add
             * their fields
             */
            if (null != activity.getBlockActivity()) {
                List<ActivitySet> embSubProcTree =
                        getEmbeddedSubProcessesTree(activity);
                for (ActivitySet activitySet : embSubProcTree) {
                    for (Activity actInES : activitySet.getActivities()) {
                        for (DataField dataField : actInES.getDataFields()) {
                            if (null == checkData || dataField != checkData) {
                                String name = (String) getOtherAttribute(
                                        dataField,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_DisplayName());
                                if (name != null) {
                                    currNames.put(name, dataField);
                                }
                            }
                        }
                    }
                }
            }
            /**
             * add data fields of all activities present in the embedded sub
             * proc
             */
            ActivitySet actSet = getEmbeddedSubProcessActivitySet(activity);
            if (null != actSet) {
                for (Activity act : actSet.getActivities()) {
                    for (DataField dataField : act.getDataFields()) {
                        if (null == checkData || dataField != checkData) {
                            String name = (String) getOtherAttribute(dataField,
                                    XpdExtensionPackage.eINSTANCE
                                            .getDocumentRoot_DisplayName());
                            if (name != null) {
                                currNames.put(name, dataField);
                            }
                        }
                    }
                }
            }

            if (activity.eContainer() instanceof ActivitySet) {
                /**
                 * if the selected embSubProcActivity is embedded, then add
                 * parent embedded sub proc data fields
                 */
                List<ProcessRelevantData> embeddedSubProcessSetScopeData =
                        LocalPackageProcessInterfaceUtil
                                .getEmbeddedSubProcessSetScopeData(
                                        (ActivitySet) activity.eContainer());
                for (ProcessRelevantData processRelevantData : embeddedSubProcessSetScopeData) {
                    if (null == checkData || processRelevantData != checkData) {
                        String name =
                                (String) getOtherAttribute(processRelevantData,
                                        XpdExtensionPackage.eINSTANCE
                                                .getDocumentRoot_DisplayName());
                        if (name != null) {
                            currNames.put(name, processRelevantData);
                        }
                    }
                }
            }

        }
        // MR 39410 - end
        // Check if the new name already exists.
        ProcessRelevantData duplicate = currNames.get(checkName);
        return duplicate;
    }

    /**
     * @param checkData
     * @param currNames
     * @param process
     */
    private static void getDupDisplayActLocalDataForProcess(
            ProcessRelevantData checkData,
            Map<String, ProcessRelevantData> currNames, Process process) {
        /**
         * get all activities in a process and get all data fields and add to
         * the list
         */
        Collection<Activity> allActivitiesInProc =
                getAllActivitiesInProc(process);
        for (Activity activity : allActivitiesInProc) {
            for (DataField dataField : activity.getDataFields()) {
                if (null == checkData || dataField != checkData) {
                    String name = (String) getOtherAttribute(dataField,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName());
                    if (name != null) {
                        currNames.put(name, dataField);
                    }
                }
            }
        }
    }

    /**
     * Check whether given name for participant is a duplicate within it's scope
     * and return first duplicate if there is one.
     * 
     * @param container
     * @param checkParticipant
     * @param checkName
     * @return
     */
    public static Participant getDuplicateParticipant(EObject container,
            Participant checkParticipant, String checkName) {

        // Get a list of all participant names that are within scope of
        // the participant being edited/created.
        Map<String, Participant> currNames = new HashMap<String, Participant>();

        if (container instanceof Package) {
            Package pkg = (Package) container;

            for (Iterator iter = pkg.getParticipants().iterator(); iter
                    .hasNext();) {
                Participant partic = (Participant) iter.next();
                // Don't add the participant being edited.
                if (null == checkParticipant || partic != checkParticipant) {
                    currNames.put(partic.getName(), partic);
                }
            }

            // Add participants of all processes.
            for (Iterator iter = pkg.getProcesses().iterator(); iter
                    .hasNext();) {
                Process process = (Process) iter.next();

                for (Iterator particIter =
                        process.getParticipants().iterator(); particIter
                                .hasNext();) {
                    Participant partic = (Participant) particIter.next();
                    // Don't add the participant being edited.
                    if (null == checkParticipant
                            || partic != checkParticipant) {
                        currNames.put(partic.getName(), partic);
                    }
                }
            }

        } else if (container instanceof Process) {
            Process process = (Process) container;

            // Scope of participant in process is package participants
            // and other participants in same process.
            Package pkg = process.getPackage();
            if (pkg != null) {
                for (Iterator iter = pkg.getParticipants().iterator(); iter
                        .hasNext();) {
                    Participant partic = (Participant) iter.next();
                    // Don't add the participant being edited.
                    if (null == checkParticipant
                            || partic != checkParticipant) {
                        currNames.put(partic.getName(), partic);
                    }
                }
            }

            for (Iterator particIter =
                    process.getParticipants().iterator(); particIter
                            .hasNext();) {
                Participant partic = (Participant) particIter.next();
                // Don't add the participant being edited.
                if (null == checkParticipant || partic != checkParticipant) {
                    currNames.put(partic.getName(), partic);
                }
            }
        }

        Participant duplicate = currNames.get(checkName);
        return duplicate;
    }

    /**
     * Check whether given name for participant is a duplicate within it's scope
     * and return first duplicate if there is one.
     * 
     * @param container
     * @param checkParticipant
     * @param checkName
     * @return
     */
    public static Participant getDuplicateDisplayParticipant(EObject container,
            Participant checkParticipant, String checkName) {

        // Get a list of all participant names that are within scope of
        // the participant being edited/created.
        Map<String, Participant> currNames = new HashMap<String, Participant>();

        if (container instanceof Package) {
            Package pkg = (Package) container;

            for (Iterator iter = pkg.getParticipants().iterator(); iter
                    .hasNext();) {
                Participant partic = (Participant) iter.next();
                // Don't add the participant being edited.
                if (null == partic || partic != checkParticipant) {
                    String name = (String) getOtherAttribute(partic,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName());
                    if (name != null) {
                        currNames.put(name, partic);
                    }
                }
            }

            // Add participants of all processes.
            for (Iterator iter = pkg.getProcesses().iterator(); iter
                    .hasNext();) {
                Process process = (Process) iter.next();

                for (Iterator particIter =
                        process.getParticipants().iterator(); particIter
                                .hasNext();) {
                    Participant partic = (Participant) particIter.next();
                    // Don't add the participant being edited.
                    if (null == partic || partic != checkParticipant) {
                        String name = (String) getOtherAttribute(partic,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_DisplayName());
                        if (name != null) {
                            currNames.put(name, partic);
                        }
                    }
                }
            }

        } else if (container instanceof Process) {
            Process process = (Process) container;

            // Scope of participant in process is package participants
            // and other participants in same process.
            Package pkg = process.getPackage();
            if (pkg != null) {
                for (Iterator iter = pkg.getParticipants().iterator(); iter
                        .hasNext();) {
                    Participant partic = (Participant) iter.next();
                    // Don't add the participant being edited.
                    if (null == partic || partic != checkParticipant) {
                        String name = (String) getOtherAttribute(partic,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_DisplayName());
                        if (name != null) {
                            currNames.put(name, partic);
                        }
                    }
                }
            }

            for (Iterator particIter =
                    process.getParticipants().iterator(); particIter
                            .hasNext();) {
                Participant partic = (Participant) particIter.next();
                // Don't add the participant being edited.
                if (null == partic || partic != checkParticipant) {
                    String name = (String) getOtherAttribute(partic,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName());
                    if (name != null) {
                        currNames.put(name, partic);
                    }
                }
            }
        }
        Participant duplicate = currNames.get(checkName);
        return duplicate;
    }

    /**
     * Check wether given name for type declaration is a duplicate within it's
     * scope and return first duplicate if there is one.
     * 
     * @param pkg
     * @param checkTypeDeclaration
     * @param checkName
     * @return
     */
    public static TypeDeclaration getDuplicateTypeDeclaration(Package pkg,
            TypeDeclaration checkTypeDeclaration, String checkName) {

        TypeDeclaration duplicate = null;

        if (pkg != null) {
            // Search for duplicate.
            for (Iterator iter = pkg.getTypeDeclarations().iterator(); iter
                    .hasNext();) {
                TypeDeclaration type = (TypeDeclaration) iter.next();

                // Ignore type decl we are actually editing.
                if (type != checkTypeDeclaration) {
                    if (checkName.equals(type.getName())) {
                        duplicate = type;
                        break;
                    }
                }
            }
        }

        return duplicate;
    }

    /**
     * Check wether given name for type declaration is a duplicate within it's
     * scope and return first duplicate if there is one.
     * 
     * @param pkg
     * @param checkTypeDeclaration
     * @param checkName
     * @return
     */
    public static TypeDeclaration getDuplicateDisplayTypeDeclaration(
            Package pkg, TypeDeclaration checkTypeDeclaration,
            String checkName) {

        TypeDeclaration duplicate = null;

        if (pkg != null) {
            // Search for duplicate.
            for (Iterator<TypeDeclaration> iter =
                    pkg.getTypeDeclarations().iterator(); iter.hasNext();) {
                TypeDeclaration type = iter.next();

                // Ignore type decl we are actually editing.
                if (type != checkTypeDeclaration) {
                    String name = (String) getOtherAttribute(type,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName());
                    if (checkName.equals(name)) {
                        duplicate = type;
                        break;
                    }
                }
            }
        }
        return duplicate;
    }

    /**
     * Check wether given name for process is a duplicate within it's scope and
     * return first duplicate if there is one.
     * 
     * @param pkg
     * @param checkProcess
     * @param checkName
     * @return
     */
    public static Process getDuplicateProcess(Package pkg, Process checkProcess,
            String checkName) {

        Process duplicate = null;

        if (pkg != null) {
            // Search for duplicate.
            for (Iterator iter = pkg.getProcesses().iterator(); iter
                    .hasNext();) {
                Process type = (Process) iter.next();

                // Ignore type decl we are actually editing.
                if (type != checkProcess) {
                    if (checkName.equals(type.getName())) {
                        duplicate = type;
                        break;
                    }
                }
            }
        }
        return duplicate;
    }

    /**
     * Check wether given name for process is a duplicate within it's scope and
     * return first duplicate if there is one.
     * 
     * @param pkg
     * @param checkProcess
     * @param checkName
     * @return
     */
    public static Process getDuplicateDisplayNameProcess(Package pkg,
            Process checkProcess, String checkName) {

        Process duplicate = null;
        if (pkg != null) {
            // Search for duplicate.
            for (Iterator<Process> iter = pkg.getProcesses().iterator(); iter
                    .hasNext();) {
                Process type = iter.next();

                // Ignore type decl we are actually editing.
                if (type != checkProcess) {
                    String displayName = (String) getOtherAttribute(type,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName());
                    if (checkName.equals(displayName)) {
                        duplicate = type;
                        break;
                    }
                }
            }
        }
        return duplicate;
    }

    /**
     * Check whether given name for a process interface is a duplicate within
     * it's scope and return first duplicate if there is one.
     * 
     * @param pkg
     * @param checkProcessInterface
     * @param checkName
     * @return
     */
    public static ProcessInterface getDuplicateProcessInterface(
            EObject pkgOrInterfacesElement,
            ProcessInterface checkProcessInterface, String checkName) {
        ProcessInterfaces interfaces = null;

        if (pkgOrInterfacesElement instanceof ProcessInterfaces) {
            interfaces = (ProcessInterfaces) pkgOrInterfacesElement;
        } else if (pkgOrInterfacesElement instanceof Package) {
            interfaces = (ProcessInterfaces) ((Package) pkgOrInterfacesElement)
                    .getOtherElement(XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_ProcessInterfaces().getName());
        }

        ProcessInterface duplicate = null;
        if (interfaces != null) {
            // Search for duplicate.
            for (Iterator iter =
                    interfaces.getProcessInterface().iterator(); iter
                            .hasNext();) {
                ProcessInterface type = (ProcessInterface) iter.next();

                // Ignore type decl we are actually editing.
                if (type != checkProcessInterface) {
                    if (checkName.equals(type.getName())) {
                        duplicate = type;
                        break;
                    }
                }
            }
        }
        return duplicate;
    }

    /**
     * Check whether given name for a process interface is a duplicate within
     * it's scope and return first duplicate if there is one.
     * 
     * @param pkg
     * @param checkProcessInterface
     * @param checkName
     * @return
     */
    public static ProcessInterface getDuplicateDisplayProcessInterface(
            EObject pkgOrInterfacesElement,
            ProcessInterface checkProcessInterface, String checkName) {
        ProcessInterfaces interfaces = null;

        if (pkgOrInterfacesElement instanceof ProcessInterfaces) {
            interfaces = (ProcessInterfaces) pkgOrInterfacesElement;
        } else if (pkgOrInterfacesElement instanceof Package) {
            interfaces = (ProcessInterfaces) ((Package) pkgOrInterfacesElement)
                    .getOtherElement(XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_ProcessInterfaces().getName());
        }

        ProcessInterface duplicate = null;
        if (interfaces != null) {
            // Search for duplicate.
            for (Iterator iter =
                    interfaces.getProcessInterface().iterator(); iter
                            .hasNext();) {
                ProcessInterface type = (ProcessInterface) iter.next();

                // Ignore type decl we are actually editing.
                if (type != checkProcessInterface) {
                    String name = (String) getOtherAttribute(type,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName());
                    if (checkName.equals(name)) {
                        duplicate = type;
                        break;
                    }
                }
            }
        }
        return duplicate;
    }

    /**
     * Check whether given name for start method is a duplicate within it's
     * scope and return first duplicate if there is one.
     * 
     * @param container
     * @param checkParticipant
     * @param checkName
     * @return
     */
    public static StartMethod getDuplicateStartMethod(EObject container,
            NamedElement element, String checkName) {

        // the participant being edited/created.
        Map<String, StartMethod> currNames = new HashMap<String, StartMethod>();

        if (container instanceof ProcessInterface) {
            ProcessInterface processInterface = (ProcessInterface) container;

            // Ravi-processInterface getPackage required.
            for (Iterator iter =
                    processInterface.getStartMethods().iterator(); iter
                            .hasNext();) {
                StartMethod startMethod = (StartMethod) iter.next();

                if (startMethod != element) {
                    currNames.put(startMethod.getName(), startMethod);
                }
            }
        }

        StartMethod duplicate = currNames.get(checkName);
        return duplicate;
    }

    /**
     * Check whether given name for error method is a duplicate within it's
     * scope and return first duplicate if there is one.
     * 
     * @param container
     * @param element
     * @param checkName
     * @return
     */
    public static ErrorMethod getDuplicateError(EObject container,
            ErrorMethod element, String checkName) {

        Map<String, ErrorMethod> currNames = new HashMap<String, ErrorMethod>();

        if (container instanceof InterfaceMethod) {
            InterfaceMethod interfaceMethod = (InterfaceMethod) container;
            for (Iterator iter =
                    interfaceMethod.getErrorMethods().iterator(); iter
                            .hasNext();) {
                ErrorMethod errorMethod = (ErrorMethod) iter.next();

                if (errorMethod != element) {
                    currNames.put(errorMethod.getErrorCode(), errorMethod);
                }
            }
        }

        ErrorMethod duplicate = currNames.get(checkName);
        return duplicate;
    }

    /**
     * Check whether given name for start method is a duplicate within it's
     * scope and return first duplicate if there is one.
     * 
     * @param container
     * @param checkParticipant
     * @param checkName
     * @return
     */
    public static StartMethod getDuplicateDisplayStartMethod(EObject container,
            NamedElement element, String checkName) {

        // the participant being edited/created.
        Map<String, StartMethod> currNames = new HashMap<String, StartMethod>();

        if (container instanceof ProcessInterface) {
            ProcessInterface processInterface = (ProcessInterface) container;

            // Ravi-processInterface getPackage required.
            for (Iterator iter =
                    processInterface.getStartMethods().iterator(); iter
                            .hasNext();) {
                StartMethod startMethod = (StartMethod) iter.next();

                if (startMethod != element) {
                    String name = (String) getOtherAttribute(startMethod,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName());
                    if (name != null) {
                        currNames.put(name, startMethod);
                    }
                }
            }
        }

        StartMethod duplicate = currNames.get(checkName);
        return duplicate;
    }

    /**
     * Check whether given name for intermediate method is a duplicate within
     * it's scope and return first duplicate if there is one.
     * 
     * @param container
     * @param checkParticipant
     * @param checkName
     * @return
     */
    public static IntermediateMethod getDuplicateIntermediateMethod(
            EObject container, NamedElement element, String checkName) {

        // the participant being edited/created.
        Map<String, IntermediateMethod> currNames =
                new HashMap<String, IntermediateMethod>();

        if (container instanceof ProcessInterface) {
            ProcessInterface processInterface = (ProcessInterface) container;

            // Ravi-processInterface getPackage required.
            for (Iterator iter =
                    processInterface.getIntermediateMethods().iterator(); iter
                            .hasNext();) {
                IntermediateMethod intermediateMethod =
                        (IntermediateMethod) iter.next();

                if (intermediateMethod != element) {
                    currNames.put(intermediateMethod.getName(),
                            intermediateMethod);
                }
            }
        }

        IntermediateMethod duplicate = currNames.get(checkName);
        return duplicate;
    }

    /**
     * Check whether given name for intermediate method is a duplicate within
     * it's scope and return first duplicate if there is one.
     * 
     * @param container
     * @param checkParticipant
     * @param checkName
     * @return
     */
    public static IntermediateMethod getDuplicateDisplayIntermediateMethod(
            EObject container, NamedElement element, String checkName) {

        // the participant being edited/created.
        Map<String, IntermediateMethod> currNames =
                new HashMap<String, IntermediateMethod>();

        if (container instanceof ProcessInterface) {
            ProcessInterface processInterface = (ProcessInterface) container;

            // Ravi-processInterface getPackage required.
            for (Iterator iter =
                    processInterface.getIntermediateMethods().iterator(); iter
                            .hasNext();) {
                IntermediateMethod intermediateMethod =
                        (IntermediateMethod) iter.next();

                if (intermediateMethod != element) {
                    String name = (String) getOtherAttribute(intermediateMethod,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_DisplayName());
                    if (name != null) {
                        currNames.put(name, intermediateMethod);
                    }
                }
            }
        }

        IntermediateMethod duplicate = currNames.get(checkName);
        return duplicate;
    }

    /**
     * Return a display name based on baseDisplayName that is unique within the
     * given set of NamedElement's.
     * <p>
     * If the given baseDisplayName is already used within the set OR its
     * derived token name is already used then an index will be suffixed untiul
     * a new unqiue name is derived.
     * 
     * @param baseDisplayName
     * @param existingNamedSet
     * 
     * @return a display name that is unique within given set (and whose
     *         dserived token name is also unique.
     * @deprecated Use {@link #getUniqueDisplayNameInSet(String,Collection<?
     *             extends NamedElement>,boolean)} instead
     */
    @Deprecated
    public static String getUniqueDisplayNameInSet(String baseDisplayName,
            Collection<? extends NamedElement> existingNamedSet) {
        return getUniqueDisplayNameInSet(baseDisplayName,
                existingNamedSet,
                false);
    }

    /**
     * Return a display name based on baseDisplayName that is unique within the
     * given set of NamedElement's.
     * <p>
     * If the given baseDisplayName is already used within the set OR its
     * derived token name is already used then an index will be suffixed untiul
     * a new unqiue name is derived.
     * 
     * @param baseDisplayName
     * @param existingNamedSet
     * @param spaceBeforeIndex
     *            true if a space should be inserted before the suffixed index
     *            if one is required
     * 
     * @return a display name that is unique within given set (and whose derived
     *         token name is also unique.
     * @deprecated Should use {@link #getUniqueLabelInSet(String, Collection)}
     *             instead.
     */
    @Deprecated
    public static String getUniqueDisplayNameInSet(String baseDisplayName,
            Collection<? extends NamedElement> existingNamedSet,
            boolean spaceBeforeIndex) {
        return getUniqueLabelInSet(baseDisplayName,
                existingNamedSet,
                spaceBeforeIndex,
                false);
    }

    /**
     * Return a display name based on baseDisplayName that is unique within the
     * given set of NamedElement's.
     * <p>
     * If the given baseDisplayName is already used within the set OR its
     * derived token name is already used then an index will be suffixed with an
     * index number in brackets ("label (2)" etc) until a new unique label and
     * name is derived.
     * 
     * @param baseDisplayName
     * @param existingNamedSet
     * 
     * @return a display name that is unique within given set (and whose
     *         dserived token name is also unique.
     */
    public static String getUniqueLabelInSet(String baseDisplayName,
            Collection<? extends NamedElement> existingNamedSet) {
        return getUniqueLabelInSet(baseDisplayName,
                existingNamedSet,
                true,
                true);
    }

    /**
     * Return a display name based on baseDisplayName that is unique within the
     * given set of NamedElement's.
     * <p>
     * If the given baseDisplayName is already used within the set OR its
     * derived token name is already used then an index will be suffixed untiul
     * a new unqiue name is derived.
     * 
     * @param baseDisplayName
     * @param existingNamedSet
     * @param spaceBeforeIndex
     *            true if a space should be inserted before the suffixed index
     *            if one is required
     * @param bracketIndexSuffix
     *            <code>true</code> if the indexed number suffix is to be
     *            bracketed with "()" - this can help prevent problems when the
     *            original label MUST itself already be suffixed with a number.
     * 
     * @return a display name that is unique within given set (and whose
     *         dserived token name is also unique.
     */
    private static String getUniqueLabelInSet(String baseDisplayName,
            Collection<? extends NamedElement> existingNamedSet,
            boolean spaceBeforeIndex, boolean bracketIndexSuffix) {

        //
        // Do nothing if is not a duplicate name.
        boolean found = false;
        for (NamedElement existingData : existingNamedSet) {
            String existingDisplayName =
                    Xpdl2ModelUtil.getDisplayNameOrName(existingData);

            if (baseDisplayName.equals(existingDisplayName)
                    || NameUtil.getInternalName(baseDisplayName, false)
                            .equals(existingData.getName())) {
                found = true;
                break;
            }

        }

        if (!found) {
            return baseDisplayName;
        }

        /*
         * Remove trailing digit
         */
        String newBaseName = baseDisplayName;
        boolean isDigitsOnly = false;

        if (!bracketIndexSuffix) {
            int i = newBaseName.length() - 1;
            while (i >= 0) {
                if (Character.isDigit(newBaseName.charAt(i))) {
                    newBaseName = newBaseName.substring(0, i);
                    i--;
                } else {
                    break;
                }
            }

            if (i <= 0) {
                // All digits append an underscore to make things behave
                // sensibly when creating next.
                newBaseName = baseDisplayName;
                isDigitsOnly = true;
            }

        } else {
            /* Remove trailing brackets. */
            newBaseName = baseDisplayName.replaceAll("\\s*\\([0-9]*\\)$", ""); //$NON-NLS-1$//$NON-NLS-2$
        }

        String finalName = newBaseName;

        int idx = 1;
        while (true) {
            found = false;
            for (NamedElement existingData : existingNamedSet) {
                String existingDisplayName =
                        Xpdl2ModelUtil.getDisplayNameOrName(existingData);

                if (finalName.equals(existingDisplayName)
                        || NameUtil.getInternalName(finalName, false)
                                .equals(existingData.getName())) {
                    found = true;
                    break;
                }

            }

            if (!found) {
                break;
            }

            idx++;

            String idxStr;
            if (bracketIndexSuffix) {
                idxStr = String.format("(%d)", idx); //$NON-NLS-1$
            } else {
                idxStr = String.format("%d", idx); //$NON-NLS-1$
            }

            if (!isDigitsOnly) {
                if (spaceBeforeIndex) {
                    finalName = newBaseName + " " + idxStr; //$NON-NLS-1$
                } else {
                    finalName = newBaseName + idxStr;
                }
            } else {
                // All digits append an underscore to make things behave
                // sensibly when creating next.
                finalName = newBaseName + "_" + idxStr; //$NON-NLS-1$

            }
        }

        return finalName;
    }

    /**
     * Return a name based on name that is unique within the given set of
     * NamedElement's.
     * <p>
     * If the given nameName is already used within the set OR its derived token
     * name is already used then an index will be suffixed until a new unqiue
     * name is derived.
     * 
     * @param baseName
     * @param existingNamedSet
     * @param spaceBeforeIndex
     *            true if a space should be inserted before the suffixed index
     *            if one is required
     * 
     * @return a name that is unique within given set.
     */
    public static String getUniqueNameInSet(String baseName,
            Collection<? extends NamedElement> existingNamedSet,
            boolean spaceBeforeIndex) {

        //
        // Do nothing if is not a duplicate name.
        boolean found = false;
        for (NamedElement existingData : existingNamedSet) {
            String existingnameName = existingData.getName();

            if (baseName.equals(existingnameName)) {
                found = true;
                break;
            }

        }

        if (!found) {
            return baseName;
        }

        // Remove trailing digits.
        String newBaseName = baseName;
        boolean isDigitsOnly = false;
        int i = newBaseName.length() - 1;
        while (i >= 0) {
            if (Character.isDigit(newBaseName.charAt(i))) {
                newBaseName = newBaseName.substring(0, i);
                i--;
            } else {
                break;
            }
        }

        if (i <= 0) {
            // All digits append an underscore to make things behave
            // sensibly when creating next.
            newBaseName = baseName;
            isDigitsOnly = true;
        }

        String finalName = newBaseName;

        int idx = 1;
        while (true) {
            found = false;
            for (NamedElement existingData : existingNamedSet) {
                String existingName = existingData.getName();

                if (finalName.equals(existingName)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                break;
            }

            idx++;
            if (!isDigitsOnly) {
                if (spaceBeforeIndex) {
                    finalName = newBaseName + " " + idx; //$NON-NLS-1$
                } else {
                    finalName = newBaseName + idx;
                }
            } else {
                // All digits append an underscore to make things behave
                // sensibly when creating next.
                finalName = newBaseName + "_" + idx; //$NON-NLS-1$

            }
        }

        return finalName;
    }

    /**
     * Fully resolve data type, i.e. if the given type is a declared type then
     * return the type of the declared type.
     * 
     * @param pckg
     *            The package.
     * @param type
     *            The type.
     * @return The resolved type.
     * 
     * @deprecated Use
     *             com.tibco.xpd.analyst.resources.xpdl2.utils.BasicTypeConversionFactory
     *             .INSTANCE.getBasicType() instead (as this can deal with XPDL
     *             data that references user defined BOM primitive types.
     */
    @Deprecated
    public static DataType resolveDataType(Package pckg, DataType type,
            boolean allowTypeDeclarationNesting) {
        DataType resolved = type;
        if (type instanceof DeclaredType) {
            DeclaredType declared = (DeclaredType) type;
            String typeId = declared.getTypeDeclarationId();
            if (typeId != null) {
                TypeDeclaration typeDecl = pckg.getTypeDeclaration(typeId);
                if (typeDecl != null) {
                    if (typeDecl.getBasicType() != null) {
                        resolved = typeDecl.getBasicType();
                    } else if (allowTypeDeclarationNesting) {
                        // If we are allowing multiple nested type declarations
                        // then recurs.
                        if (typeDecl.getDeclaredType() != null) {
                            resolved = resolveDataType(pckg,
                                    typeDecl.getDeclaredType(),
                                    allowTypeDeclarationNesting);
                        }
                    }

                }
            }
        }
        return resolved;
    }

    /**
     * @param content
     *            The content to use in the expression.
     * @return The new Expression.
     */
    public static Expression createExpression(String content) {
        Expression expression = Xpdl2Factory.eINSTANCE.createExpression();
        expression.getMixed().add(
                XMLTypePackage.eINSTANCE.getXMLTypeDocumentRoot_Text(),
                content);
        return expression;
    }

    /**
     * @param activity
     *            The activity.
     * @param name
     *            The parameter name to resolve.
     * @return The parameter.
     * @deprecated This method cannot resolve data inherited from implemented
     *             process inteface defiend in external package - use
     *             com.tibco.xpd
     *             .analyst.resources.xpdl2.utils.ProcessDataUtil#resolveData()
     *             instead.
     */
    @Deprecated
    public static ProcessRelevantData resolveParameter(Activity activity,
            String name) {
        ProcessRelevantData field = null;
        if (name.startsWith("process.")) { //$NON-NLS-1$
            name = name.substring(8);
        }
        Process process = activity.getProcess();
        if (process != null) {
            field = resolveField(
                    LocalPackageProcessInterfaceUtil
                            .getAllFormalParameters(process),
                    name);
            if (field == null) {
                field = resolveField(activity.getDataFields(), name);
                if (field == null) {
                    if (activity.eContainer() instanceof ActivitySet) {
                        List<ProcessRelevantData> embData =
                                LocalPackageProcessInterfaceUtil
                                        .getEmbeddedSubProcessSetScopeData(
                                                (ActivitySet) activity
                                                        .eContainer());
                        field = resolveField(embData, name);
                    }

                    if (field == null) {
                        field = resolveField(process.getDataFields(), name);
                        if (field == null) {
                            Package pckg = process.getPackage();
                            field = resolveField(pckg.getDataFields(), name);
                        }
                    }
                }
            }
        }
        return field;
    }

    /**
     * @param fields
     *            A list of DataField objects.
     * @param name
     *            The ame of the field to resolve.
     * @return The resolved DataField or null if it can't be found.
     */
    private static ProcessRelevantData resolveField(List<?> fields,
            String name) {
        ProcessRelevantData field = null;
        for (Object next : fields) {
            if (next instanceof ProcessRelevantData) {
                ProcessRelevantData current = (ProcessRelevantData) next;
                if (name.equals(current.getName())) {
                    field = current;
                }
            }
        }
        return field;
    }

    /**
     * @param eo
     * @return Activity ancestor of given object.
     */
    public static Activity getParentActivity(EObject eo) {
        Activity activity = null;
        if (eo instanceof Activity) {
            activity = (Activity) eo;
        } else if (eo != null) {
            activity = getParentActivity(eo.eContainer());
        }
        return activity;
    }

    /**
     * Get all incoming transitions for given activity.
     * 
     * @param activityId
     * 
     * @return LIst of transitions (sequence flow) whose target is the given
     *         activity.
     */
    public static List<Transition> getIncomingTransitions(String activityId,
            FlowContainer flowContainer) {
        List<Transition> transitions = new ArrayList<Transition>();

        if (flowContainer != null) {
            for (Iterator iter = flowContainer.getTransitions().iterator(); iter
                    .hasNext();) {
                Transition trans = (Transition) iter.next();

                if (activityId.equals(trans.getTo())) {
                    transitions.add(trans);
                }
            }
        }

        return transitions;

    }

    /**
     * Get all outgoing transitions for given activity.
     * 
     * @param activityId
     * 
     * @return List of transitions (sequence flow) whose source is the given
     *         activity.
     */
    public static List<Transition> getOutgoingTransitions(String actId,
            FlowContainer flowContainer) {
        List<Transition> transitions = new ArrayList<Transition>();

        if (flowContainer != null) {
            for (Iterator iter = flowContainer.getTransitions().iterator(); iter
                    .hasNext();) {
                Transition trans = (Transition) iter.next();

                if (actId.equals(trans.getFrom())) {
                    transitions.add(trans);
                }
            }
        }

        return transitions;

    }

    /**
     * Create a copy of the given EObject
     * 
     * @param editingDomain
     * @param object
     * @return
     */
    public static EObject createEObjectCopy(EditingDomain editingDomain,
            EObject object) {
        Command cpyCmd = CopyCommand.create(editingDomain, object);
        cpyCmd.execute();
        Collection res = cpyCmd.getResult();

        EObject obj = (EObject) res.iterator().next();
        return obj;
    }

    /**
     * Create a copy of all the EObjects in the given collection.
     * 
     * @param editingDomain
     * @param eObjects
     * @return
     */
    public static Collection<EObject> createEObjectsCopy(
            EditingDomain editingDomain, Collection<EObject> eObjects) {
        Command cpyCmd = CopyCommand.create(editingDomain, eObjects);
        cpyCmd.execute();

        return (Collection<EObject>) cpyCmd.getResult();
    }

    /**
     * Sets the alias with the details from picker - could be datafield,
     * partipant or formal parameter
     * 
     * @param performer
     * @param wso
     * @return the selected performer id
     */
    public static String setEndpointFromDataPickerSelection(Object performer,
            WebServiceOperation wso) {
        String selectedPerformerId = ""; //$NON-NLS-1$
        if (performer instanceof Participant && wso != null) {
            Participant participant = (Participant) performer;
            selectedPerformerId = participant.getId();
            setOtherAttribute(wso,
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_Alias(),
                    selectedPerformerId);
        } else if (performer instanceof ProcessRelevantData && wso != null) {
            ProcessRelevantData processRelevantData =
                    (ProcessRelevantData) performer;
            selectedPerformerId = processRelevantData.getId();
            setOtherAttribute(wso,
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_Alias(),
                    selectedPerformerId);
        }
        return selectedPerformerId;
    }

    /**
     * Sets the alias with the details from picker - could be datafield,
     * partipant or formal parameter
     * 
     * @param ed
     *            The editing domain.
     * @param performer
     *            The performer.
     * @param wso
     *            The web service operation.
     * @return the selected performer id.
     */
    public static Command getSetEndpointFromDataPickerSelectionCommand(
            EditingDomain ed, Object performer, WebServiceOperation wso) {
        Command command = null;
        String selectedPerformerId = ""; //$NON-NLS-1$
        if (performer instanceof Participant && wso != null) {
            Participant participant = (Participant) performer;
            selectedPerformerId = participant.getId();
            command = getSetOtherAttributeCommand(ed,
                    wso,
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_Alias(),
                    selectedPerformerId);
        } else if (performer instanceof ProcessRelevantData && wso != null) {
            ProcessRelevantData processRelevantData =
                    (ProcessRelevantData) performer;
            selectedPerformerId = processRelevantData.getId();
            command = getSetOtherAttributeCommand(ed,
                    wso,
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_Alias(),
                    selectedPerformerId);
        }
        return command;
    }

    /**
     * @param activity
     *            The activity to get mappings for.
     * @param direction
     *            The DirectionType of the mapping.
     * @return A list of mappings.
     */
    public static List<DataMapping> getDataMappings(Activity activity,
            DirectionType direction) {
        List<DataMapping> mappings = new ArrayList<DataMapping>();
        List<DataMapping> allMappings = null;
        Implementation impl = activity.getImplementation();
        if (impl instanceof Task) {
            Task task = (Task) impl;
            TaskService service = task.getTaskService();
            TaskSend send = task.getTaskSend();
            TaskReceive receive = task.getTaskReceive();
            TaskScript script = task.getTaskScript();

            Message message = null;
            if (service != null) {
                if (DirectionType.IN_LITERAL.equals(direction)) {
                    message = service.getMessageIn();
                } else {
                    message = service.getMessageOut();
                }
            }

            if (send != null) {
                message = send.getMessage();
            }
            if (receive != null) {
                message = receive.getMessage();
            }
            if (message != null) {
                allMappings = getMessageMappings(message);
            }
            if (script != null) {
                if (allMappings == null) {
                    allMappings = new ArrayList<DataMapping>();
                }
                allMappings.addAll(
                        Xpdl2ModelUtil.getScriptTaskDataMappings(script));
            }
            if (DecisionFlowUtil.isDecisionServiceTask(activity)) {
                SubFlow decisionServiceExt =
                        DecisionFlowUtil.getDecisionServiceExt(activity);
                if (decisionServiceExt != null) {
                    allMappings = decisionServiceExt.getDataMappings();
                }
            }

        } else if (impl instanceof SubFlow) {
            SubFlow subflow = (SubFlow) impl;
            allMappings = subflow.getDataMappings();

        } else if (activity.getEvent() != null) {
            Event event = activity.getEvent();

            TriggerResultMessage resultMessage = null;
            ResultError resultError = null;
            TriggerResultSignal triggerResultSignal = null;

            if (event instanceof StartEvent) {
                StartEvent start = (StartEvent) event;
                resultMessage = start.getTriggerResultMessage();
                /*
                 * XPD-7075: TriggerResultSignal in start events can also have
                 * mappings now.
                 */
                triggerResultSignal = start.getTriggerResultSignal();
            } else if (event instanceof IntermediateEvent) {
                IntermediateEvent intermediate = (IntermediateEvent) event;
                resultMessage = intermediate.getTriggerResultMessage();
                resultError = intermediate.getResultError();
                triggerResultSignal = intermediate.getTriggerResultSignal();

            } else if (event instanceof EndEvent) {
                EndEvent end = (EndEvent) event;
                resultMessage = end.getTriggerResultMessage();
                resultError = end.getResultError();

                /*
                 * XPD-7630 TriggerResultSignal in end events can also have
                 * mappings now. (Shoudl have been added in XPD-7075).
                 */
                triggerResultSignal = end.getTriggerResultSignal();
            }

            if (resultMessage != null) {
                Message message = resultMessage.getMessage();
                if (message != null) {
                    allMappings = getMessageMappings(message);
                }
            } else if (resultError != null
                    && DirectionType.OUT_LITERAL.equals(direction)) {
                //
                // Get the data mappings (these are under
                // xpdl2:ResultError/xpdExt:CatchErrorMappings/
                // xpdl2:Message/xpdl2:DataMappings)
                //
                CatchErrorMappings catchErrorMappings =
                        (CatchErrorMappings) Xpdl2ModelUtil.getOtherElement(
                                resultError,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_CatchErrorMappings());
                if (catchErrorMappings != null) {
                    Message message = catchErrorMappings.getMessage();
                    allMappings = message.getDataMappings();
                }
            } else if (resultError != null
                    && DirectionType.IN_LITERAL.equals(direction)
                    && ThrowErrorEventUtil
                            .isThrowFaultMessageErrorEvent(activity)) {
                Message faultMessage =
                        ThrowErrorEventUtil.getThrowErrorFaultMessage(activity);
                if (faultMessage != null) {
                    allMappings = faultMessage.getDataMappings();
                }
            } else if (triggerResultSignal != null) {
                SignalData signalData = (SignalData) Xpdl2ModelUtil
                        .getOtherElement(triggerResultSignal,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_SignalData());
                if (signalData != null) {
                    /*
                     * XPD-7075: Signal Data now has Data Mappings and
                     * Correlation Mappings.
                     */
                    List<DataMapping> allDataMappings =
                            new ArrayList<DataMapping>();
                    EList<DataMapping> dataMappings =
                            signalData.getDataMappings();
                    if (dataMappings != null) {
                        allDataMappings.addAll(dataMappings);
                    }

                    if (signalData.getCorrelationMappings() != null) {
                        EList<DataMapping> correlationMappings = signalData
                                .getCorrelationMappings().getDataMappings();
                        if (correlationMappings != null
                                && !correlationMappings.isEmpty()) {
                            allDataMappings.addAll(correlationMappings);
                        }
                    }
                    allMappings = allDataMappings;
                }
            }
        }

        if (allMappings != null) {
            for (DataMapping mapping : allMappings) {
                if (direction.equals(mapping.getDirection())) {
                    mappings.add(mapping);
                }
            }
        }
        return mappings;
    }

    /**
     * Returns the container for script data mapper.
     * 
     * @param activity
     * @param direction
     * @return
     */
    public static OtherElementsContainer getScriptDataMapperContainer(
            Activity activity, DirectionType direction) {
        OtherElementsContainer oec = null;
        Implementation impl = activity.getImplementation();
        if (impl instanceof Task) {
            Task task = (Task) impl;
            TaskService taskService = task.getTaskService();
            if (taskService != null) {
                if (DirectionType.IN_LITERAL.equals(direction)) {
                    oec = taskService.getMessageIn();
                } else {
                    oec = taskService.getMessageOut();
                }
            }
            TaskSend taskSend = task.getTaskSend();
            if (taskSend != null) {
                if (DirectionType.IN_LITERAL.equals(direction)) {
                    oec = taskSend.getMessage();
                }
            }
        }
        Event event = activity.getEvent();
        if (event instanceof IntermediateEvent) {
            IntermediateEvent ie = (IntermediateEvent) event;
            ResultError re = ie.getResultError();
            if (DirectionType.OUT_LITERAL.equals(direction) && re != null) {
                oec = re;
            }
            TriggerResultMessage trm = ie.getTriggerResultMessage();
            if (DirectionType.IN_LITERAL.equals(direction) && trm != null) {
                oec = trm.getMessage();
            }
        } else if (event instanceof EndEvent) {
            EndEvent endEvent = (EndEvent) event;

            TriggerResultMessage trm = endEvent.getTriggerResultMessage();
            if (DirectionType.IN_LITERAL.equals(direction) && trm != null) {
                oec = trm.getMessage();
            }

        }

        return oec;
    }

    public static List<DataMapping> getMessageMappings(Message message) {
        List<DataMapping> mappings =
                new ArrayList<DataMapping>(message.getDataMappings());
        Object other = getOtherElement(message,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_CorrelationDataMappings());
        if (other instanceof CorrelationDataMappings) {
            CorrelationDataMappings correlationMappings =
                    (CorrelationDataMappings) other;
            mappings.addAll(correlationMappings.getDataMappings());
        }

        other = getOtherElement(message,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ReplyImmediateDataMappings());

        if (other instanceof ReplyImmediateDataMappings) {
            ReplyImmediateDataMappings replyImmediateDataMappings =
                    (ReplyImmediateDataMappings) other;
            mappings.addAll(replyImmediateDataMappings.getDataMappings());
        }

        return mappings;
    }

    /**
     * Gets the participant matched to this particular performer
     * 
     * @param process
     * @param performer
     * @return
     */
    public static Object getParticipantOrProcessData(Process process,
            Performer performer) {
        Package xpdlPackage = (Package) process.eContainer();

        Object obj = xpdlPackage.getParticipant(performer.getValue());
        if (obj == null) {
            obj = xpdlPackage.getDataField(performer.getValue());
            if (obj == null) {
                obj = process.getParticipant(performer.getValue());
                if (obj == null) {
                    obj = process.getProcessData(performer.getValue());
                }
            }
        }

        return obj;
    }

    /**
     * Given an EMF generated EEnumLiteral object (Like
     * ObjectType.CHOICE_LITERAL) return the EMF generated plugin properties
     * message string for it.
     * 
     * @param literal
     * @return
     */
    public static String getEnumLocalisedName(EEnumLiteral literal) {
        return Xpdl2EditPlugin.INSTANCE.getString("_UI_" //$NON-NLS-1$
                + literal.getEEnum().getName() + "_" + literal.getLiteral() //$NON-NLS-1$
                + "_literal"); //$NON-NLS-1$

    }

    /**
     * Checks whether a Participant is present in the passed process or in the
     * package with the specified participant name
     * 
     * @param process
     * @param participantName
     * @return
     */
    public static Participant getParticipant(Process process,
            String participantName) {
        Participant toReturn = null;
        // Scope of participant in process is package participants
        // and other participants in same process.
        for (Iterator iter =
                process.getPackage().getParticipants().iterator(); iter
                        .hasNext();) {
            Participant partic = (Participant) iter.next();
            if (partic.getName().equals(participantName)) {
                toReturn = partic;
                break;
            }
        }
        if (toReturn != null) {
            return toReturn;
        }

        for (Iterator particIter =
                process.getParticipants().iterator(); particIter.hasNext();) {
            Participant partic = (Participant) particIter.next();
            if (partic.getName().equals(participantName)) {
                toReturn = partic;
                break;
            }
        }

        return toReturn;
    }

    /**
     * Guaranteed to get new GatewayType enumeration (rather than old ones that
     * wfmc left in the model by mistake - EVEN if the old ones are defined).
     * 
     * @return JoinSplitType.EXCLUSIVE | INCLUSIVE | PARALLEL | COMPLEX
     */
    public static JoinSplitType safeGetGatewayType(Activity gatewayAct) {

        Route route = gatewayAct.getRoute();

        if (route != null) {
            JoinSplitType gatewayType = route.getGatewayType();
            if (gatewayType != null) {
                switch (gatewayType.getValue()) {
                case JoinSplitType.INCLUSIVE:
                case JoinSplitType.DEPRECATED_OR:
                    return JoinSplitType.INCLUSIVE_LITERAL;

                case JoinSplitType.PARALLEL:
                case JoinSplitType.DEPRECATED_AND:
                    return JoinSplitType.PARALLEL_LITERAL;

                case JoinSplitType.EXCLUSIVE:
                case JoinSplitType.DEPRECATED_XOR:
                    return JoinSplitType.EXCLUSIVE_LITERAL;

                default:
                    return gatewayType;
                }
            }
        }

        return JoinSplitType.EXCLUSIVE_LITERAL;
    }

    /**
     * Guaranteed to get new ExclusiveType enumeration (rather than old ones
     * that wfmc left in the model by mistake - EVEN if the old ones are
     * defined).
     * 
     * @param gatewayAct
     * @return
     */
    public static ExclusiveType safeGetExclusiveType(Activity gatewayAct) {
        Route route = gatewayAct.getRoute();

        if (route != null) {

            if (route.isSetExclusiveType()) {
                return route.getExclusiveType();
            } else {
                // XPDL2 introduced and deprecated XORType as the same things as
                // Exclusive type, so we'll check it for interoperability's
                // sake.
                if (route.isSetDeprecatedXorType()) {
                    DeprecatedXorType xorType = route.getDeprecatedXorType();
                    if (XorType.EVENT.equals(xorType)) {
                        return ExclusiveType.EVENT;
                    } else if (XorType.DATA.equals(xorType)) {
                        return ExclusiveType.DATA;
                    }
                } else {
                    // XPDL 2 XOR Event gateways are XOR type With
                    // Instantiate=true;
                    if (route.isDeprecatedInstantiate()) {
                        return ExclusiveType.EVENT;
                    }
                    return ExclusiveType.DATA;
                }
            }
        }

        return ExclusiveType.DATA;
    }

    public static String getReferencedGatewayActivityId(
            Activity gatewayActivity) {
        Route route = gatewayActivity.getRoute();
        if (null != route) {
            Discriminator discriminator =
                    (Discriminator) Xpdl2ModelUtil.getOtherElement(route,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Discriminator());
            if (null != discriminator) {
                StructuredDiscriminator structuredDiscriminator =
                        discriminator.getStructuredDiscriminator();
                if (null != structuredDiscriminator) {
                    return structuredDiscriminator.getUpStreamParallelSplit();
                }
            }
        }
        return null;
    }

    /**
     * Get the display name extension attribute for given named element.
     * 
     * @param namedElement
     * @return
     */
    public static String getDisplayName(NamedElement namedElement) {
        String displayName = (String) Xpdl2ModelUtil.getOtherAttribute(
                namedElement,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName());
        return displayName == null ? "" : displayName; //$NON-NLS-1$
    }

    /**
     * Get the display name extension attribute for given named element. If it
     * is undefined then get the normal xpdl name.
     * 
     * @param namedElement
     * @return the display name or name.
     */
    public static String getDisplayNameOrName(NamedElement namedElement) {
        String name = (String) Xpdl2ModelUtil.getOtherAttribute(namedElement,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName());
        if (name == null || name.length() == 0 && namedElement != null) {
            name = namedElement.getName();
        }

        return name == null ? "" : name; //$NON-NLS-1$
    }

    public static EObject getDuplicateNameObject(EObject container,
            EObject input, String name) {
        EObject duplicate = null;
        if (input instanceof NamedElement) {
            NamedElement named = (NamedElement) input;
            if (named instanceof Process) {
                Process process = (Process) named;
                duplicate =
                        getDuplicateProcess((Package) container, process, name);
            } else if (named instanceof ProcessInterface) {
                ProcessInterface field = (ProcessInterface) named;
                duplicate = getDuplicateProcessInterface(Xpdl2ModelUtil
                        .getPackage(container), field, name);
            } else if (named instanceof ProcessRelevantData) {
                ProcessRelevantData field = (ProcessRelevantData) named;
                duplicate = getDuplicateFieldOrParam(container, field, name);
                // MR 38533 - begin
                if (null == duplicate) {
                    duplicate = getDuplicateParticipant(container, null, name);
                }
                // MR 38533 - end
            } else if (named instanceof Participant) {
                Participant field = (Participant) named;
                duplicate = getDuplicateParticipant(container, field, name);
            } else if (named instanceof TypeDeclaration) {
                TypeDeclaration field = (TypeDeclaration) named;
                duplicate = getDuplicateTypeDeclaration((Package) container,
                        field,
                        name);
            } else if (named instanceof StartMethod) {
                StartMethod field = (StartMethod) named;
                duplicate = getDuplicateStartMethod(container, field, name);
            } else if (named instanceof IntermediateMethod) {
                IntermediateMethod field = (IntermediateMethod) named;
                duplicate =
                        getDuplicateIntermediateMethod(container, field, name);
            }
        }
        return duplicate;
    }

    public static EObject getDuplicateDisplayNameObject(EObject container,
            EObject input, String name) {
        EObject duplicate = null;
        if (input instanceof NamedElement) {
            NamedElement named = (NamedElement) input;
            if (named instanceof Process) {
                Process process = (Process) named;
                duplicate = getDuplicateDisplayNameProcess((Package) container,
                        process,
                        name);
            } else if (named instanceof ProcessInterface) {
                ProcessInterface field = (ProcessInterface) named;
                duplicate = getDuplicateDisplayProcessInterface(container,
                        field,
                        name);
            } else if (named instanceof ProcessRelevantData) {
                ProcessRelevantData field = (ProcessRelevantData) named;
                duplicate =
                        getDuplicateDisplayFieldOrParam(container, field, name);
                // MR 38533 - begin
                if (null == duplicate) {
                    duplicate = getDuplicateDisplayParticipant(container,
                            null,
                            name);
                }
                // MR 38533 - begin
            } else if (named instanceof Participant) {
                Participant field = (Participant) named;
                duplicate =
                        getDuplicateDisplayParticipant(container, field, name);
                // MR 38533 - begin
                if (null == duplicate) {
                    duplicate = getDuplicateDisplayFieldOrParam(container,
                            null,
                            name);
                }
                // MR 38533 - end
            } else if (named instanceof TypeDeclaration) {
                TypeDeclaration field = (TypeDeclaration) named;
                duplicate =
                        getDuplicateDisplayTypeDeclaration((Package) container,
                                field,
                                name);
            } else if (named instanceof StartMethod) {
                StartMethod field = (StartMethod) named;
                duplicate =
                        getDuplicateDisplayStartMethod(container, field, name);
            } else if (named instanceof IntermediateMethod) {
                IntermediateMethod field = (IntermediateMethod) named;
                duplicate = getDuplicateDisplayIntermediateMethod(container,
                        field,
                        name);
            }
        }
        return duplicate;
    }

    public static boolean shouldHaveDisplayName(EObject input) {
        boolean shouldHaveDisplayName = false;
        if (input instanceof NamedElement) {
            NamedElement named = (NamedElement) input;
            if (named instanceof Process) {
                shouldHaveDisplayName = true;
            } else if (named instanceof ProcessInterface) {
                shouldHaveDisplayName = true;
            } else if (named instanceof ProcessRelevantData) {
                shouldHaveDisplayName = true;
            } else if (named instanceof Participant) {
                shouldHaveDisplayName = true;
            } else if (named instanceof TypeDeclaration) {
                shouldHaveDisplayName = true;
            } else if (named instanceof StartMethod) {
                shouldHaveDisplayName = true;
            } else if (named instanceof IntermediateMethod) {
                shouldHaveDisplayName = true;
            }
        }
        return shouldHaveDisplayName;
    }

    public static boolean shouldHaveTokenName(EObject input) {
        boolean shouldHaveDisplayName = false;
        if (input instanceof NamedElement) {
            NamedElement named = (NamedElement) input;
            if (named instanceof Process) {
                shouldHaveDisplayName = true;
            } else if (named instanceof ProcessInterface) {
                shouldHaveDisplayName = true;
            } else if (named instanceof ProcessRelevantData) {
                shouldHaveDisplayName = true;
            } else if (named instanceof Participant) {
                shouldHaveDisplayName = true;
            } else if (named instanceof TypeDeclaration) {
                shouldHaveDisplayName = true;
            } else if (named instanceof StartMethod) {
                shouldHaveDisplayName = true;
            } else if (named instanceof IntermediateMethod) {
                shouldHaveDisplayName = true;
            }
        }
        return shouldHaveDisplayName;
    }

    /**
     * This returns the label text for the adapted class. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     */
    public static String getNamedElementLabelText(NamedElement namedElement) {
        String label = null;
        String displayName = (String) Xpdl2ModelUtil.getOtherAttribute(
                namedElement,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_DisplayName());
        if (namedElement != null
                && CapabilityUtil.isDeveloperActivityEnabled()) {
            String name = namedElement.getName();
            if (displayName == null || displayName.length() == 0) {
                label = name;
            } else {
                label = displayName + " (" + name + ")"; //$NON-NLS-1$ //$NON-NLS-2$
            }
        } else {
            label = displayName;
        }
        return label;
    }

    /**
     * Checks if the given activity is a correlating activity. An activity is a
     * correlating activity if it is a receive task with an in-flow, a catch
     * intermediate message event and a message start event in an event sub
     * proc. A message start event or a receive task that is process start
     * activity is <strong>not</strong> a correlating activity
     * <p>
     * In other words it is any incoming request activity that is received on an
     * existing process instance and therefore must do some correlation with
     * incoming mesage data to find the correct process instance to direct the
     * mesage to.
     * </p>
     * 
     * @param activity
     * @return <code>true</code> if the activity is correlating incoming request
     *         activity (see above)
     */
    public static boolean isCorrelatingActivity(Activity activity) {

        boolean isIncomingRequestActivity =
                ReplyActivityUtil.isIncomingRequestActivity(activity);
        boolean isMsgProcessStartAct = isMessageProcessStartActivity(activity);

        boolean isCorrelatingActivity =
                isIncomingRequestActivity && !isMsgProcessStartAct;

        return isCorrelatingActivity;
    }

    /**
     * Checks if the given activity can have correlation data associated
     * 
     * @param activity
     * @return <code>true</code> if the activity is a Receive Task/Start Message
     *         Event/Intermediate Catch Message Event <code>false</code>
     *         otherwise
     */
    public static boolean canHaveCorrelationAssociated(Activity activity) {

        boolean correlationActivity = false;
        Implementation impl = activity.getImplementation();
        if (impl != null) {

            if (impl instanceof Task) {

                Task task = (Task) impl;
                TaskReceive receive = task.getTaskReceive();
                if (receive != null) {
                    /*
                     * TaskReceive can have correlation data associated if it
                     * has incoming flow or if it's a process start activity.
                     */
                    correlationActivity = true;
                }
            }
        } else {

            Event event = activity.getEvent();
            if (event instanceof StartEvent) {

                StartEvent start = (StartEvent) event;
                if (TriggerType.MESSAGE_LITERAL.equals(start.getTrigger())) {

                    /*
                     * Message start events can have correlation data associated
                     */
                    correlationActivity = true;
                }
            } else if (event instanceof IntermediateEvent) {

                IntermediateEvent intermediate = (IntermediateEvent) event;
                if (TriggerType.MESSAGE_LITERAL
                        .equals(intermediate.getTrigger())) {
                    /* Catch events can have correlation data associated */
                    if (CatchThrow.CATCH.equals(intermediate
                            .getTriggerResultMessage().getCatchThrow())) {

                        correlationActivity = true;
                    }
                }
            }
        }
        return correlationActivity;
    }

    public static List<DataField> getAssociatedCorrelationData(
            Activity activity) {
        List<DataField> fields = new ArrayList<DataField>();
        Object other = getOtherElement(activity,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_AssociatedCorrelationFields());
        if (other instanceof AssociatedCorrelationFields) {
            AssociatedCorrelationFields fieldContainer =
                    (AssociatedCorrelationFields) other;

            List<AssociatedCorrelationField> fieldList =
                    fieldContainer.getAssociatedCorrelationField();

            if (fieldList.size() == 0) {
                /*
                 * If user has selected "disable implicit assocaition, then
                 * don't return all in process in the absence of any explicit
                 * associaitons.
                 */
                if (!fieldContainer.isDisableImplicitAssociation()) {
                    fields = getCorrelationDataFields(activity.getProcess());
                }
            } else {
                for (AssociatedCorrelationField correlation : fieldList) {
                    String name = correlation.getName();
                    if (name != null) {
                        for (DataField field : getCorrelationDataFields(
                                activity.getProcess())) {
                            if (name.equals(field.getName())) {
                                fields.add(field);
                                break;
                            }
                        }
                    }
                }
            }

        } else {
            fields = getCorrelationDataFields(activity.getProcess());
        }
        return fields;
    }

    public static List<DataField> getCorrelationDataFields(Process process) {
        List<DataField> fields = new ArrayList<DataField>();
        for (DataField field : process.getDataFields()) {
            if (field.isCorrelation()) {
                fields.add(field);
            }
        }
        return fields;
    }

    /**
     * Return <code>true</code> if the container of start event is an event sub
     * process, <code>false</code> otherwise.
     * <p>
     * Strongly recommend to use
     * EventObjectUtil.isEventSubProcessStartEvent(Activity) as that method
     * verifies the specified activity in a better way.
     * 
     * @param act
     * @return <code>true</code> if the container of start event is an event sub
     *         process, <code>false</code> otherwise.
     */
    public static boolean isEventSubProcessStartEvent(Activity act) {

        if (act != null && act.getEvent() != null
                && act.getEvent() instanceof StartEvent) {

            /*
             * SId XPD-6704: Probably not safe to rely on the fact that only
             * start events in event sub-proc are supposed to have the
             * interrupting/non-interuptiong flag on.
             * 
             * We shoudl check if the parent is an event sub-process.
             */
            if (act.getFlowContainer() instanceof ActivitySet) {

                Activity embSubProcAct =
                        getEmbSubProcActivityForActSet(act.getProcess(),
                                ((ActivitySet) act.getFlowContainer()).getId());

                return isEventSubProcess(embSubProcAct);
            }
        }

        return false;
    }

    /**
     * @param activity
     * @return true if activity is an event sub-process
     */
    public static boolean isEventSubProcess(Activity activity) {
        if (activity != null && activity.getBlockActivity() != null) {
            Object isEventSubProc =
                    getOtherAttribute(activity.getBlockActivity(),
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_IsEventSubProcess());

            if (isEventSubProc instanceof Boolean
                    && ((Boolean) isEventSubProc).booleanValue() == true) {
                return true;
            }

        }
        return false;
    }

    /**
     * Return <code>true</code> if the specified process is a business process,
     * <code>false</code> otherwise.
     * 
     * @param process
     *            Process to be checked.
     * @return <code>true</code> if the specified process is a business process,
     *         <code>false</code> otherwise.
     */
    public static boolean isBusinessProcess(Process process) {
        boolean bizProcess = false;
        if (process != null) {
            Object other =

                    getOtherAttribute(process,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_XpdModelType());
            if (other == null) {
                bizProcess = true;
            }
        }
        return bizProcess;
    }

    /**
     * Return <code>true</code> for any process that is a sub-type of pageflow
     * (i.e. currently that means a pageflow, a business service or a case
     * action).
     * <p>
     * <b>NOTE:</b> To find out if the process is precisely a pageflow (and not
     * one of it's derivatives, use isPageflowProcess(Process)
     * <p>
     * 
     * @param process
     *            Process to be checked.
     * @return <code>true</code> if the specified process is a pageflow process
     *         OR one of it's derivatives (Business Service, Case Action),
     *         <code>false</code> otherwise.
     * @deprecated Use isPageflowOrSubType(Process) instead
     */
    @Deprecated
    public static boolean isPageflow(Process process) {
        return isPageflowOrSubType(process);
    }

    /**
     * Return <code>true</code> for any process that is a sub-type of pageflow
     * (i.e. currently that means a pageflow, a business service or a case
     * action).
     * <p>
     * <b>NOTE:</b> To find out if the process is precisely a pageflow (and not
     * one of it's derivatives, use isPageflowProcess(Process)
     * <p>
     * 
     * @param process
     *            Process to be checked.
     * @return <code>true</code> if the specified process is a pageflow process
     *         OR one of it's derivatives (Business Service, Case Action),
     *         <code>false</code> otherwise.
     */
    public static boolean isPageflowOrSubType(Process process) {
        boolean pageflow = false;
        if (process != null) {

            Object other = getOtherAttribute(process,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_XpdModelType());
            if (XpdModelType.PAGE_FLOW.equals(other)) {

                pageflow = true;
            }
        }
        return pageflow;

    }

    /**
     * 
     * @param process
     *            Process to be checked.
     * @return <code>true</code> if the specified process is a pageflow process
     *         and <b>not</b> one of it's derivatives
     */
    public static boolean isPageflowProcess(Process process) {

        boolean isPageflow = false;
        Object other = getOtherAttribute(process,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_XpdModelType());
        if (XpdModelType.PAGE_FLOW.equals(other)) {

            isPageflow = true;
        }
        return isPageflow && !isPageflowBusinessService(process)
                && !isCaseService(process);
    }

    /**
     * Return <code>true</code> if the specified process is a service process,
     * <code>false</code> otherwise.
     * 
     * @param process
     *            Process to be checked.
     * @return <code>true</code> if the specified process is a service process,
     *         <code>false</code> otherwise.
     */
    public static boolean isServiceProcess(Process process) {

        if (null != process) {

            Object otherAttribute = getOtherAttribute(process,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_XpdModelType());
            if (XpdModelType.SERVICE_PROCESS.equals(otherAttribute)) {

                return true;
            }
        }
        return false;
    }

    /**
     * Return <code>true</code> if the specified process is a service process or
     * service process interface, <code>false</code> otherwise.
     * 
     * @param procOrIfcObject
     *            Process to be checked.
     * 
     * @return <code>true</code> if the specified process is a service process
     *         or process interface, <code>false</code> otherwise.
     */
    public static boolean isServiceProcessOrProcessInterface(
            EObject procOrIfcObject) {

        if (procOrIfcObject instanceof Process) {
            return isServiceProcess((Process) procOrIfcObject);

        } else if (procOrIfcObject instanceof ProcessInterface) {

            return isServiceProcessInterface(
                    (ProcessInterface) procOrIfcObject);
        }

        return false;
    }

    /**
     * Return <code>true</code> if the specified process is a business process
     * or process interface, <code>false</code> otherwise.
     * 
     * @param procOrIfcObject
     *            Process to be checked.
     * 
     * @return <code>true</code> if the specified process is a business process
     *         or process interface, <code>false</code> otherwise.
     */
    public static boolean isBusinessProcessOrProcessInterface(
            EObject procOrIfcObject) {

        if (procOrIfcObject instanceof Process) {
            return isBusinessProcess((Process) procOrIfcObject);

        } else if (procOrIfcObject instanceof ProcessInterface) {

            return isProcessInterface((ProcessInterface) procOrIfcObject);
        }

        return false;
    }

    /**
     * Check if the given process interface is a Service Process Interface
     * 
     * @param procIntf
     * @return <code>true</code> if the given process interface is a Service
     *         Process Interface
     */
    public static boolean isServiceProcessInterface(ProcessInterface procIntf) {

        boolean isServiceProcessIntf = false;
        XpdInterfaceType xpdInterfaceType = procIntf.getXpdInterfaceType();
        if (XpdInterfaceType.SERVICE_PROCESS_INTERFACE
                .equals(xpdInterfaceType)) {

            isServiceProcessIntf = true;
        }

        return isServiceProcessIntf;
    }

    /**
     * Check if the given interface is a Process Interface
     * 
     * @param procIntf
     * @return <code>true</code> if the given interface is a Process Interface
     */
    public static boolean isProcessInterface(ProcessInterface procIntf) {

        boolean isProcessIntf = false;
        XpdInterfaceType xpdInterfaceType = procIntf.getXpdInterfaceType();
        if (XpdInterfaceType.PROCESS_INTERFACE.equals(xpdInterfaceType)) {

            isProcessIntf = true;
        }

        return isProcessIntf;
    }

    public static boolean isTaskLibrary(Process process) {
        boolean taskLib = false;
        if (process != null) {
            Object other = getOtherAttribute(process,
                    XpdExtensionPackage.eINSTANCE
                            .getDocumentRoot_XpdModelType());
            if (XpdModelType.TASK_LIBRARY.equals(other)) {
                taskLib = true;
            }
        }
        return taskLib;
    }

    public static EObject resolveUri(URI uri) {
        EObject eo = null;
        if (uri.isPlatformResource()) {
            Path path = new Path(uri.toPlatformString(true));
            IWorkspace workspace = ResourcesPlugin.getWorkspace();
            IFile file = workspace.getRoot().getFile(path);
            if (file != null && file.exists()) {
                XpdProjectResourceFactory factory = XpdResourcesPlugin
                        .getDefault()
                        .getXpdProjectResourceFactory(file.getProject());
                WorkingCopy wc = factory.getWorkingCopy(file);
                if (wc != null) {
                    eo = wc.resolveEObject(uri.toString());
                }
            }
        }
        return eo;
    }

    /**
     * Utility to identify whether the activity has generated Web Service
     * operations and mappings.
     * 
     * This method checks only for Receive Tasks, Start Message Events, and
     * Intermediate Catch Message Event.
     * 
     * @param activity
     * @return
     */
    public static boolean isGeneratedRequestActivity(Activity activity) {

        Object genAttrib = null;
        if (activity != null) {
            if (activity.getImplementation() instanceof Task) {
                Task task = (Task) activity.getImplementation();
                if (task.getTaskReceive() != null) {
                    genAttrib = getOtherAttribute(task.getTaskReceive(),
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Generated());

                }

            } else {
                Event event = activity.getEvent();
                TriggerResultMessage triggerResultMessage = null;
                if (event instanceof StartEvent) {
                    triggerResultMessage =
                            ((StartEvent) event).getTriggerResultMessage();
                } else if (event instanceof IntermediateEvent) {
                    triggerResultMessage = ((IntermediateEvent) event)
                            .getTriggerResultMessage();
                }
                if (triggerResultMessage != null) {
                    genAttrib = getOtherAttribute(triggerResultMessage,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_Generated());
                }
            }
        }
        if (Boolean.TRUE.equals(genAttrib)) {
            return true;
        }
        return false;
    }

    /**
     * Utility to identify whether a given Task Receive has wsdl operations and
     * mappings generated.
     * 
     * @param taskReceive
     * @return
     */
    public static boolean isTaskReceiveGenerated(TaskReceive taskReceive) {
        Object genAttrib = Xpdl2ModelUtil.getOtherAttribute(taskReceive,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Generated());
        if (Boolean.TRUE.equals(genAttrib)) {
            return true;
        }
        return false;
    }




    /**
     * This method checks whether the WSDL has been generated for the given
     * object
     * 
     * @param obj
     * @return boolean
     */
    public static Boolean isWsdlAvailable(EObject obj) {
        Package pkg = getPackage(obj);
        if (pkg != null) {
            IFile xpdlFile = WorkingCopyUtil.getFile(pkg);
            if (xpdlFile != null) {
                String wsdlFileName = getWsdlFileName(xpdlFile);
                IProject project = xpdlFile.getProject();
                IFolder folder = project.getFolder(GENERATED_SERVICES);
                if (folder.exists()) {
                    IFile file = folder.getFile(wsdlFileName);
                    if (file.exists()) {
                        return true;
                    }
                }
            }
        }
        return Boolean.FALSE;
    }

    /**
     * Get the IFile for the WSDL that <i>could</i> be derived from the given
     * xpdlFile.
     * <p>
     * A non-null return does not necessarily mean that the WSDL exists (or
     * needs to exist) as this is controlled by whether a selected destination
     * environment switches on WSDL generation and whether there are any API
     * activities in the xpdl package.
     * </p>
     * <p>
     * <b>Note: if you want to get the derived file for an interface event
     * implementing activity then you should use
     * ProcessIntefaceUtil.getDerivedWsdl(Activity activity) instead </b>
     * <p>
     * 
     * @param xpdlFile
     * 
     * @return The WSDL that could (or has) been derived from the given xpdl
     *         file's api activities.
     */
    public static IFile getDerivedWsdlFile(IFile xpdlFile) {

        if (xpdlFile != null) {

            String wsdlFileName = getWsdlFileName(xpdlFile);
            IProject project = xpdlFile.getProject();
            IFolder folder = project.getFolder(GENERATED_SERVICES);
            IFile file = folder.getFile(wsdlFileName);
            return file;
        }

        return null;
    }

    /**
     * @param pkg
     * 
     * @return The target namespace for the WSDL that could be generated from
     *         the given xpdl package.
     */
    public static String getDerivedWsdlNamespace(Package pkg) {
        return GENERATED_SERVICE_TARGETNS_PREFIX + pkg.getId();
    }

    /**
     * Utility to return the corresponding Wsdl file name for a given xpdl file
     * name.
     * 
     * @param xpdlFileName
     * @return
     * @deprecated use {@link #getWsdlFileName(IFile)}
     */
    @Deprecated
    public static String getWsdlFileName(String xpdlFileName) {
        IPath xpdlWithoutExtn = new Path(xpdlFileName).removeFileExtension();
        String wsdlFileName = xpdlWithoutExtn.addFileExtension(WSDL_FILE_EXTN)
                .toPortableString();

        return wsdlFileName;
    }

    /**
     * Utility to return the corresponding Wsdl file name for a given xpdl file.
     * This will give the expected relative special folder path of the wsdl file
     * (ie if the xpdl file is contained in a subfolder within the xpdl special
     * folder then the wsdl path will include this subfolder).
     * 
     * @param xpdlFile
     * @return
     * 
     */
    public static String getWsdlFileName(IFile xpdlFile) {
        IProject xpdlProject = xpdlFile.getProject();
        IPath path = SpecialFolderUtil.getSpecialFolderRelativePath(xpdlFile);
        if (path != null) {
            path = path.removeFileExtension().addFileExtension(WSDL_FILE_EXTN);

            return path.toString();
        }
        return null;
    }

    public static Collection<IProject> getAllReferencedProjects(
            IProject project) {
        Collection<IProject> projects = new HashSet<IProject>();
        addProject(projects, project);
        return projects;
    }

    private static void addProject(Collection<IProject> validProjects,
            IProject project) {
        if (project != null) {
            if (!validProjects.contains(project)) {
                validProjects.add(project);
                try {
                    for (IProject next : project.getReferencedProjects()) {
                        addProject(validProjects, next);
                    }
                } catch (CoreException e) {
                }
            }
        }
    }

    /**
     * Get the path (relative to the com.tibco.xpd.xpdl.edit plug-in) to the
     * icon image path for the given activity's type.
     * 
     * @param act
     * @return path to activity icon relative to this plug-in.
     */
    public static String getActivityImagePath(Activity activity) {
        String img = null;

        if (activity.getEvent() != null) {
            Event ev = activity.getEvent();

            String trigger = ""; //$NON-NLS-1$

            if (ev instanceof EndEvent) {
                img = "event_end"; //$NON-NLS-1$

                ResultType type = ((EndEvent) ev).getResult();

                if (type != null && !ResultType.NONE_LITERAL.equals(type)) {
                    trigger = "_" + type.getLiteral(); //$NON-NLS-1$
                }

            } else if (ev instanceof IntermediateEvent) {
                img = "event_intermediate"; //$NON-NLS-1$

                TriggerType type = ((IntermediateEvent) ev).getTrigger();

                if (type != null && !TriggerType.NONE_LITERAL.equals(type)) {
                    trigger = "_" + type.getLiteral(); //$NON-NLS-1$
                }

                CatchThrow ctType =
                        getIntermediateEventCatchThrow((IntermediateEvent) ev);
                if (ctType != null) {
                    trigger += "_" + ctType.getLiteral(); //$NON-NLS-1$
                }

            } else if (ev instanceof StartEvent) {
                img = "event_start"; //$NON-NLS-1$

                TriggerType type = ((StartEvent) ev).getTrigger();

                if (type != null && !TriggerType.NONE_LITERAL.equals(type)) {
                    trigger = "_" + type.getLiteral(); //$NON-NLS-1$
                }
            }

            img += trigger;
            img = "events/" + img; //$NON-NLS-1$

        } else if (activity.getRoute() != null) {
            switch (activity.getRoute().getGatewayType().getValue()) {
            case GatewayType.AND:
            case GatewayType.PARALLEL:
                img = "gateway_Parallel"; //$NON-NLS-1$
                break;

            case GatewayType.OR:
            case GatewayType.INCLUSIVE:
                img = "gateway_Inclusive"; //$NON-NLS-1$
                break;

            case GatewayType.COMPLEX:
                img = "gateway_Complex"; //$NON-NLS-1$
                break;

            case GatewayType.DEPRECATEDXOREVENT:
                img = "gateway_ExclusiveEvent"; //$NON-NLS-1$
                break;

            case GatewayType.XOR:
                img = "gateway_Exclusive"; //$NON-NLS-1$
                break;

            case GatewayType.EXCLUSIVE:
                if (ExclusiveType.EVENT
                        .equals(activity.getRoute().getExclusiveType())) {
                    img = "gateway_ExclusiveEvent"; //$NON-NLS-1$
                } else {
                    img = "gateway_Exclusive"; //$NON-NLS-1$
                }
                break;

            }

            img = "gateways/" + img; //$NON-NLS-1$

        } else if (activity.getImplementation() != null) {
            Implementation impl = activity.getImplementation();

            if (impl instanceof SubFlow) {
                img = "SubFlow"; //$NON-NLS-1$
            } else if (impl instanceof Reference) {
                img = "TaskReference"; //$NON-NLS-1$
            } else if (impl instanceof Task) {
                Task task = (Task) impl;
                if (task.getTaskManual() != null) {
                    img = "TaskManual"; //$NON-NLS-1$
                } else if (task.getTaskReceive() != null) {
                    img = "TaskReceive"; //$NON-NLS-1$
                } else if (task.getTaskScript() != null) {
                    img = "TaskScript"; //$NON-NLS-1$
                } else if (task.getTaskSend() != null) {
                    img = "TaskSend"; //$NON-NLS-1$
                } else if (task.getTaskService() != null) {
                    img = "TaskService"; //$NON-NLS-1$
                } else if (task.getTaskUser() != null) {
                    img = "TaskUser"; //$NON-NLS-1$
                } else if (task.getTaskReference() != null) {
                    img = "TaskReference"; //$NON-NLS-1$
                } else {
                    img = "Task"; //$NON-NLS-1$
                }

            } else if (impl instanceof No) {
                img = "Task"; //$NON-NLS-1$
            }
        } else if (activity.getBlockActivity() != null) {
            if (isEventSubProcess(activity)) {
                img = "EventSubProcess"; //$NON-NLS-1$
            } else {
                img = "BlockActivity"; //$NON-NLS-1$
            }
        }

        if (img != null) {
            img = "full/obj16/" + img + ".png"; //$NON-NLS-1$//$NON-NLS-2$
        } else {
            img = "full/obj16/Activity"; //$NON-NLS-1$
        }

        return img;
    }

    /**
     * Returns the {@link PortTypeOperation} for a given Activity
     * 
     * @param activity
     * @return
     */
    public static PortTypeOperation getPortTypeOperation(Activity activity) {
        Event event = activity.getEvent();
        PortTypeOperation portTypeOperation = null;
        if (event != null) {
            TriggerResultMessage triggerResultMessage = null;
            if (event instanceof StartEvent) {
                triggerResultMessage =
                        ((StartEvent) event).getTriggerResultMessage();
            } else if (event instanceof IntermediateEvent) {
                triggerResultMessage =
                        ((IntermediateEvent) event).getTriggerResultMessage();
            } else if (event instanceof EndEvent) {
                triggerResultMessage =
                        ((EndEvent) event).getTriggerResultMessage();
            }

            if (triggerResultMessage != null) {
                Object otherElement =
                        Xpdl2ModelUtil.getOtherElement(triggerResultMessage,
                                XpdExtensionPackage.eINSTANCE
                                        .getDocumentRoot_PortTypeOperation());
                if (otherElement instanceof PortTypeOperation) {
                    portTypeOperation = (PortTypeOperation) otherElement;
                }
            }
        } else {
            Implementation implementation = activity.getImplementation();
            if (implementation instanceof Task) {
                Task task = (Task) implementation;
                if (task.getTaskReceive() != null) {
                    TaskReceive taskReceive = task.getTaskReceive();
                    Object otherElement = Xpdl2ModelUtil.getOtherElement(
                            taskReceive,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_PortTypeOperation());
                    if (otherElement instanceof PortTypeOperation) {
                        portTypeOperation = (PortTypeOperation) otherElement;
                    }
                } else if (task.getTaskSend() != null) {
                    TaskSend taskSend = task.getTaskSend();
                    Object otherElement = Xpdl2ModelUtil.getOtherElement(
                            taskSend,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_PortTypeOperation());
                    if (otherElement instanceof PortTypeOperation) {
                        portTypeOperation = (PortTypeOperation) otherElement;
                    }

                } else if (task.getTaskService() != null) {
                    TaskService taskService = task.getTaskService();
                    Object otherElement = Xpdl2ModelUtil.getOtherElement(
                            taskService,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_PortTypeOperation());
                    if (otherElement instanceof PortTypeOperation) {
                        portTypeOperation = (PortTypeOperation) otherElement;
                    }
                }

            }
        }
        return portTypeOperation;
    }

    public static List<DataMapping> getScriptTaskDataMappings(
            TaskScript taskScript) {
        if (taskScript != null) {
            TransformScript transformScript =
                    Xpdl2ModelUtil.getTransformScript(taskScript);
            if (transformScript != null) {
                List<DataMapping> dataMappings =
                        transformScript.getDataMappings();
                return dataMappings;
            }
        }
        return Collections.emptyList();
    }

    public static TransformScript getTransformScript(TaskScript taskScript) {
        TransformScript transformScript = null;
        if (taskScript != null && taskScript.getScript() != null
                && taskScript.getScript().getMixed() != null) {
            Object obj = taskScript
                    .getScript().getMixed().get(
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_TransformScript(),
                            true);
            if (obj instanceof List) {
                List list = (List) obj;
                if (list.size() > 0 && list.get(0) instanceof TransformScript) {
                    transformScript = (TransformScript) list.get(0);
                }
            }
        }
        return transformScript;
    }

    /**
     * <p>
     * Execute the given EMF command outside of command stack without incurring
     * "model changed outside of write transactrion" type exceptions.
     * <p>
     * Before transaction edit domain , you could create command and execute it
     * outside command stack. Since transactional edit domain was employed this
     * was not possible (you should get a "model change not in write
     * transaction" type exception).
     * <p>
     * But it is a handy way of sharing code that can either change an existing
     * model object or setup a brand new one without having to duplicate the
     * code without using EMF commands.
     * <p>
     * This methoid allows you o execute the given command outside of the normal
     * command stack.
     * 
     * @param ed
     * @param cmd
     * 
     * @return true on success or false on failure.
     */
    public static boolean executeCommandOutsideTransaction(EditingDomain ed,
            Command cmd) {
        boolean ret = true;
        Map<String, Object> attrs = new HashMap<String, Object>();

        attrs.put(Transaction.OPTION_UNPROTECTED, Boolean.TRUE);

        Transaction transaction = null;

        try {
            transaction = ((InternalTransactionalEditingDomain) ed)
                    .startTransaction(false, attrs);
            cmd.execute();

        } catch (InterruptedException e1) {
            ret = false;
        } finally {
            if (transaction != null) {
                try {
                    transaction.commit();
                } catch (RollbackException e) {
                    ret = false;
                }
            }
        }

        return true;
    }

    /**
     * Check whether the given activity is an API activity; these are...
     * <li>Receive Task</li>
     * <li>Start Message Event
     * 
     * @param act
     * @return
     */
    public static boolean isProcessAPIActivity(Activity act) {
        boolean result = false;
        if (ReplyActivityUtil.isReplyActivity(act)) {
            //
            // Reply to incoming request is always an API activity.
            return true;

        } else if (act.getEvent() != null) {
            Event event = act.getEvent();
            if (event instanceof StartEvent) {
                //
                // Start event type none and message are API activities.
                if (TriggerType.MESSAGE_LITERAL
                        .equals(((StartEvent) event).getTrigger())) {
                    result = true;
                }
                // Start Activity within an Embedded Sub-process is considered
                // not to be an API activity
                else if (TriggerType.NONE_LITERAL
                        .equals(((StartEvent) event).getTrigger())) {
                    if (act.eContainer() != null
                            && !(act.eContainer() instanceof ActivitySet)) {
                        result = true;
                    }
                    /* Sid ACE-3332 In ACE, Start event type non in main process OR event sub-process is an API activity (generic incoming request event handler). */
                    else if (isEventSubProcessStartEvent(act)) {
                        result = true;
                    }

                }

            } else if (event instanceof IntermediateEvent) {
                IntermediateEvent interEv = (IntermediateEvent) event;
                /* Sid ACE-3332 Intermediate catch type none is an API activity in ACE */
                if (TriggerType.NONE_LITERAL.equals(((IntermediateEvent) event).getTrigger())) {
                    result = true;

                } else if (interEv.getTriggerResultMessage() != null) {
                    //
                    // Catch message events are always API events.
                    // (Throw message event is also API activity if it is
                    // configured as a reply activity - but that's dealt with
                    // above).
                    if (CatchThrow.CATCH.equals(interEv
                            .getTriggerResultMessage().getCatchThrow())) {
                        result = true;
                    }
                }
            } else if (event instanceof EndEvent) {
                //
                // Throw error end event is an API actvitity.
                // So is end message event that replies to incoming request but
                // that is dealt with at the start of this method).
                if (ResultType.ERROR_LITERAL
                        .equals(((EndEvent) event).getResult())) {
                    result = true;
                }
            }

        } else if (act.getImplementation() instanceof Task) {
            Task task = (Task) act.getImplementation();
            if (task.getTaskReceive() != null) {
                //
                // Receive Tasks are always API activities.
                result = true;
            }
        }

        return result;
    }

    /**
     * Is the given activity one that can start the process on receipt of a
     * message.
     * <p>
     * In BPMN terms this means a Start MEssage Event or a Receive Task with no
     * incoming sequence flow.
     * 
     * @return true activity one that can start the process on receipt of a
     *         message.
     */
    public static boolean isMessageProcessStartActivity(Activity activity) {

        /*
         * XPD-6751: Saket: Ideally isMessageProcessStartActivity()should only
         * return true for potential start activities that are in the main
         * process – because it is not a message activity that starts a process!
         */
        if (activity.eContainer() instanceof Process) {
            if (activity.getEvent() != null) {
                Event event = activity.getEvent();

                if (event instanceof StartEvent) {
                    StartEvent startEvent = (StartEvent) event;

                    if (TriggerType.MESSAGE_LITERAL
                            .equals(startEvent.getTrigger())) {
                        return true;
                    }
                }

            } else if (activity.getImplementation() instanceof Task
                    && ((Task) activity.getImplementation())
                            .getTaskReceive() != null) {
                EList<Transition> incomingTransitions =
                        activity.getIncomingTransitions();
                if (incomingTransitions == null
                        || incomingTransitions.isEmpty()) {
                    return true;
                }
            }
        }

        return false;
    }

    public static List<Activity> getStartEventsForProcess(Process process) {
        if (process != null) {
            Collection<Activity> allActivitiesInProc =
                    Xpdl2ModelUtil.getAllActivitiesInProc(process);
            List<Activity> startEvents = new ArrayList<Activity>();
            for (Object next : allActivitiesInProc) {
                Activity activity = (Activity) next;
                Event event = activity.getEvent();
                if (event instanceof StartEvent) {
                    StartEvent start = (StartEvent) event;
                    if (TriggerType.NONE_LITERAL.equals(start.getTrigger())) {
                        startEvents.add(activity);
                    }
                }
            }
            return startEvents;
        }
        return Collections.emptyList();
    }

    public static String getWebServiceTransport(Activity activity) {
        if (activity != null) {
            WebServiceOperation wso = getWebServiceOperation(activity);
            if (wso != null) {
                return (String) Xpdl2ModelUtil.getOtherAttribute(wso,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_Transport());
            }
        }
        return null;
    }

    /**
     * @param activity
     * 
     * @return The xpdl2:WebServiceOperation element for the given activity or
     *         null if it has none.
     */
    public static WebServiceOperation getWebServiceOperation(
            Activity activity) {
        WebServiceOperation wso = null;
        if (activity != null) {
            if (activity.getImplementation() instanceof Task) {
                Task task = (Task) activity.getImplementation();
                if (task.getTaskService() != null) {
                    TaskService taskService = task.getTaskService();
                    wso = taskService.getWebServiceOperation();
                } else if (task.getTaskSend() != null) {
                    TaskSend taskSend = task.getTaskSend();
                    wso = taskSend.getWebServiceOperation();
                } else if (task.getTaskReceive() != null) {
                    TaskReceive taskRecieve = task.getTaskReceive();
                    wso = taskRecieve.getWebServiceOperation();
                }
            } else {
                TriggerResultMessage triggerResultMessage = null;
                Event event = activity.getEvent();
                if (event instanceof StartEvent) {
                    triggerResultMessage =
                            ((StartEvent) event).getTriggerResultMessage();
                } else if (event instanceof IntermediateEvent) {
                    triggerResultMessage = ((IntermediateEvent) event)
                            .getTriggerResultMessage();
                } else if (event instanceof EndEvent) {
                    triggerResultMessage =
                            ((EndEvent) event).getTriggerResultMessage();
                }
                if (triggerResultMessage != null) {
                    wso = triggerResultMessage.getWebServiceOperation();
                }
            }
        }
        return wso;
    }

    /**
     * Each process may have a single system participant that acts as the
     * default service EndPoint participant for process api activities (incoming
     * message activities at theior replies).
     * <p>
     * This method returns that participant.
     * 
     * @param process
     * @return participant or <code>null</code> if none defined.
     */
    public static Participant getProcessApiActivityParticipant(
            Process process) {
        Participant apiPartic = null;

        String apiParticId = (String) getOtherAttribute(process,
                XpdExtensionPackage.eINSTANCE
                        .getDocumentRoot_ApiEndPointParticipant());

        if (apiParticId != null && apiParticId.length() > 0) {
            apiPartic = process.getParticipant(apiParticId);

            if (apiPartic == null) {
                if (process.getPackage() != null) {
                    apiPartic =
                            process.getPackage().getParticipant(apiParticId);
                }
            }
        }

        return apiPartic;
    }

    /**
     * Utility to check whether an {@link Activity} implements any method in an
     * implemented {@link ProcessInterface}.
     * 
     * @param activity
     * @return
     */
    public static Boolean isEventImplemented(Activity activity) {
        Object obj = Xpdl2ModelUtil.getOtherAttribute(activity,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_Implements());
        if (obj instanceof String && ((String) obj).length() > 0) {
            return true;
        }
        return false;
    }

    /**
     * @param eo
     * @param ancestorClass
     * 
     * @return Ancestor object of given class type or <code>null</code>
     *         (includes eo so if eo instanceof ancestorClass then eo will be
     *         returned).
     */
    public static EObject getAncestor(EObject eo, Class ancestorClass) {
        EObject ancestor = null;
        if (eo != null) {
            if (ancestorClass.isInstance(eo)) {
                ancestor = eo;
            } else {
                ancestor = getAncestor(eo.eContainer(), ancestorClass);
            }

        }
        return ancestor;
    }

    /**
     * @param activity
     * @return true if the tested activity is a Send One Way Request activity,
     *         could be an intermediate throw, end event, or a Send Task but the
     *         option selected must be "Send One Way Request"
     */
    public static boolean isSendOneWayRequest(Activity activity) {
        Event event = activity.getEvent();
        Activity testActivity = null;
        if (event != null && event
                .getEventTriggerTypeNode() instanceof TriggerResultMessage) {
            TriggerResultMessage trm =
                    (TriggerResultMessage) event.getEventTriggerTypeNode();

            // It's a message event.
            if (event instanceof EndEvent) {
                // All message start events are catches.
                testActivity = activity;
            } else if (event instanceof IntermediateEvent) {
                // Intermediate ,message events can be catch or throw - we only
                // want catch.
                if (CatchThrow.THROW.equals(trm.getCatchThrow())) {
                    testActivity = activity;
                }
            }
        } else if (activity.getImplementation() instanceof Task) {
            Task task = (Task) activity.getImplementation();

            if (task.getTaskSend() != null) {
                testActivity = activity;
            }
        }
        if (null != testActivity
                && !ReplyActivityUtil.isReplyActivity(testActivity)) {
            return true;
        }

        return false;

    }

    /**
     * Business Service processes are those pageflows which can be published as
     * Business Services.
     * 
     * @param process
     * @return true if the given process is a Pageflow Business Service
     */
    public static boolean isPageflowBusinessService(Process process) {
        boolean ispageflowBusinessService = false;
        if (process != null) {
            if (isPageflow(process)) {
                Object publishAsService = getOtherAttribute(process,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_PublishAsBusinessService());
                if (Boolean.TRUE.equals(publishAsService)) {
                    ispageflowBusinessService = true;
                }
            }
        }
        return ispageflowBusinessService;
    }

    /**
     * 
     * Case Services are pageflow processes that are published as business
     * service for case classes
     * 
     * @param process
     * @return true if the given process is a Case Business Service
     */
    public static boolean isCaseService(Process process) {

        boolean isCaseServiceProcessType = false;
        boolean isCaseService = getOtherAttributeAsBoolean(process,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_IsCaseService());
        if (isPageflowBusinessService(process) && isCaseService) {

            isCaseServiceProcessType = true;
        }

        return isCaseServiceProcessType;
    }

    /**
     * Default Category is project name / package name
     * 
     * @param project
     *            name
     * @param package
     *            name
     * @return default category
     */
    public static String getBusinessServiceDefaultCategory(String projName,
            String pckgName) {
        String categoryName = projName + "/" + pckgName; //$NON-NLS-1$
        return categoryName;
    }

    /**
     * @param elements
     * @return A map of id to element for the given collection
     */
    public static Map<String, Object> createMapById(
            Collection<? extends UniqueIdElement> elements) {
        Map<String, Object> map = new HashMap<String, Object>();

        for (UniqueIdElement uniqueIdElement : elements) {
            map.put(uniqueIdElement.getId(), uniqueIdElement);
        }

        return map;
    }

    /**
     * Return the CatchThrow type for intermedaite event or null if it is a type
     * that can only be catch or throw.
     * 
     * @return
     */
    public static CatchThrow getIntermediateEventCatchThrow(
            IntermediateEvent intermediateEvent) {
        TriggerType trigger = intermediateEvent.getTrigger();

        switch (trigger.getValue()) {
        case TriggerType.COMPENSATION:
            Object objCatchThrow = Xpdl2ModelUtil.getOtherAttribute(
                    intermediateEvent.getTriggerResultCompensation(),
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_CatchThrow());
            if (objCatchThrow instanceof CatchThrow) {
                return (CatchThrow) objCatchThrow;
            }

            return CatchThrow.CATCH;

        case TriggerType.MESSAGE:
            TriggerResultMessage triggerResultMessage =
                    intermediateEvent.getTriggerResultMessage();
            if (triggerResultMessage != null) {
                CatchThrow msgCatchThrow = triggerResultMessage.getCatchThrow();
                if (msgCatchThrow != null) {
                    return msgCatchThrow;
                }
            }

            return CatchThrow.CATCH;

        case TriggerType.MULTIPLE:
            objCatchThrow = Xpdl2ModelUtil.getOtherAttribute(
                    intermediateEvent.getTriggerIntermediateMultiple(),
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_CatchThrow());
            if (objCatchThrow instanceof CatchThrow) {
                return (CatchThrow) objCatchThrow;
            }

            return CatchThrow.CATCH;

        case TriggerType.LINK:
            TriggerResultLink triggerResultLink =
                    intermediateEvent.getTriggerResultLink();
            if (triggerResultLink != null) {
                CatchThrow linkCatchThrow = triggerResultLink.getCatchThrow();
                if (linkCatchThrow != null) {
                    return linkCatchThrow;
                }
            }

            return CatchThrow.CATCH;

        case TriggerType.SIGNAL:
            TriggerResultSignal triggerResultSignal =
                    intermediateEvent.getTriggerResultSignal();
            if (triggerResultSignal != null) {
                CatchThrow signalCatchThrow =
                        triggerResultSignal.getCatchThrow();
                if (signalCatchThrow != null) {
                    return signalCatchThrow;
                }
            }

            return CatchThrow.CATCH;
        }

        return null;
    }

    /**
     * XPD-538: The process WSDL generation binding style doesn't exist on the
     * advanced property section.
     * 
     * This is looked up from the process API activity participant's web service
     * soap (http/jms) binding's binding style.
     * <p>
     * <b>NOTE, this method uses the process-api-participant which is assigned
     * ONLY FOR GENERATED WSDLs!</b>
     * 
     * @param process
     * @return {@link SoapBindingStyle} for a given process or process
     *         interface. If none is set or found DOC LITERAL is returned
     */
    public static SoapBindingStyle getWsdlBindingStyle(Process process) {
        /*
         * Process API Activity participant will indicate to us the process soap
         * binding style
         */
        Participant processApiActivityParticipant =
                getProcessApiActivityParticipant(process);

        if (processApiActivityParticipant != null) {
            /*
             * XPD-3553: this would return null if all the bindings were removed
             */
            SoapBindingStyle soapBindingStyle =
                    getInboundWsdlBindingStyle(processApiActivityParticipant);
            if (null != soapBindingStyle) {
                return soapBindingStyle;
            }
        }

        /*
         * XPD-5036 - reverted change to default style in absence of SOAP
         * bindings made by XPD-3744.
         * 
         * This solved the problem where xpdExt:ApiEndPointParticipant reference
         * was missing on process (so we defaulted to doc-lit BUT and the actual
         * participant style was RPC-literal.
         * 
         * In actual fact this change fixed that use case and broke the opposite
         * (participant was doc-lit and default here was rpc-lit). Worse still,
         * it change the binding style from doc-lit to rpc-lit for processes
         * whose api partic had ONLY VIRTUALISED BINDING defined.
         * 
         * That would be a change to customer processes (and caused further
         * validation errors downstream and on deploy.
         * 
         * But now that XPD-3744 has introduced re-addition of missing
         * xpdExt:ApiEndPointParticipant during migration and XPD-5036 has
         * introduced a catch-all validation to prevent broken API references,
         * we don't need to cvhange the default at all.
         * 
         * So putting back to doc-lit for backwards compatibility.
         */
        return SoapBindingStyle.DOCUMENT_LITERAL;
    }

    /**
     * Get the selected binding style for the given process-api-activity's
     * participant.
     * <p>
     * Unlike {@link #getWsdlBindingStyle(Process)} this should work for
     * process-api with user defined WSDLs as well as just ones that generate
     * WSDL
     * 
     * @param processAPIActivity
     * 
     * @return {@link SoapBindingStyle} for a given process or process
     *         interface. If none is set or found DOC LITERAL is returned
     */
    public static SoapBindingStyle getWsdlBindingStyle(
            Activity processAPIActivity) {
        /*
         * Activity participant will indicate to us the process soap binding
         * style
         */
        Participant activityEndpointParticipant =
                getEndPointAliasParticipant(processAPIActivity);

        if (activityEndpointParticipant != null) {
            /*
             * XPD-3553: this would return null if all the bindings were removed
             */
            SoapBindingStyle soapBindingStyle =
                    getInboundWsdlBindingStyle(activityEndpointParticipant);
            if (null != soapBindingStyle) {
                return soapBindingStyle;
            }
        }

        /*
         * To match the default in getWsdlBindingStyle(Process process) above.
         */
        return SoapBindingStyle.DOCUMENT_LITERAL;
    }

    /**
     * Get the configured binding style from the given inbound (process request
     * activity) participant. Please note that this gets the first found binding
     * style
     * 
     * @param inboundParticipant
     * 
     * @return SoapBindingStyle or <code>null</code> if not in-bound or no soap
     *         binding configuration.
     */
    public static SoapBindingStyle getInboundWsdlBindingStyle(
            Participant inboundParticipant) {
        Object participantSharedResObject =
                Xpdl2ModelUtil.getOtherElement(inboundParticipant,
                        XpdExtensionPackage.eINSTANCE
                                .getDocumentRoot_ParticipantSharedResource());
        if (participantSharedResObject instanceof ParticipantSharedResource) {
            ParticipantSharedResource participantSharedRes =
                    (ParticipantSharedResource) participantSharedResObject;
            WsResource webService = participantSharedRes.getWebService();
            if (webService != null) {
                WsInbound inbound = webService.getInbound();
                if (inbound != null) {
                    EList<WsSoapHttpInboundBinding> soapHttpBinding =
                            inbound.getSoapHttpBinding();
                    if (soapHttpBinding.size() > 0) {
                        WsSoapHttpInboundBinding wsSoapHttpInboundBinding =
                                soapHttpBinding.get(0);

                        SoapBindingStyle bindingStyle =
                                wsSoapHttpInboundBinding.getBindingStyle();
                        return bindingStyle;

                    } else {
                        EList<WsSoapJmsInboundBinding> soapJmsBinding =
                                inbound.getSoapJmsBinding();
                        if (soapJmsBinding.size() > 0) {
                            WsSoapJmsInboundBinding wsSoapJmsInboundBinding =
                                    soapJmsBinding.get(0);
                            SoapBindingStyle bindingStyle =
                                    wsSoapJmsInboundBinding.getBindingStyle();
                            return bindingStyle;
                        }
                    }

                }
            }
        }
        return null;
    }

    /**
     * WSDL generation can be of either RPC Literal, RPC Encoded, or Document
     * Literal
     * 
     * @param processInterface
     * @return {@link BindingType} for a given process or process interface. If
     *         none is set or found DOC LITERAL is returned
     */
    public static SoapBindingStyle getWsdlBindingStyle(
            ProcessInterface processInterface) {
        Object wsdlGenElement = Xpdl2ModelUtil.getOtherElement(processInterface,
                XpdExtensionPackage.eINSTANCE.getDocumentRoot_WsdlGeneration());
        if (wsdlGenElement instanceof WsdlGeneration) {
            WsdlGeneration wsdlGeneration = (WsdlGeneration) wsdlGenElement;
            if (null != wsdlGeneration.getSoapBindingStyle()) {
                return wsdlGeneration.getSoapBindingStyle();
            }
        }
        return SoapBindingStyle.DOCUMENT_LITERAL;
    }

    /**
     * @param activity
     * 
     * @return The participant set as the endpoint alias for web-service related
     *         activity.
     */
    public static Participant getEndPointAliasParticipant(Activity activity) {
        Participant participant = null;

        WebServiceOperation wso =
                Xpdl2ModelUtil.getWebServiceOperation(activity);
        if (wso != null) {

            String particId = (String) Xpdl2ModelUtil.getOtherAttribute(wso,
                    XpdExtensionPackage.eINSTANCE.getDocumentRoot_Alias());
            if (particId != null && particId.length() > 0) {

                Process process = activity.getProcess();
                if (process != null && process.getPackage() != null) {

                    participant = process.getPackage().getParticipant(particId);
                    if (participant == null) {
                        participant = process.getParticipant(particId);
                    }
                }
            }
        }
        return participant;
    }

}
