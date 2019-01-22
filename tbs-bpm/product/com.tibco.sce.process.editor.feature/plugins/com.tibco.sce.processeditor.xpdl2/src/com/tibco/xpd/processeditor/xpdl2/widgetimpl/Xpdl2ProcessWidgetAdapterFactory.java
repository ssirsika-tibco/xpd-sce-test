/*
 ** 
 **  MODULE:             $RCSfile: $ 
 **                      $Revision: $ 
 **                      $Date: $ 
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2005 TIBCO Software Inc., All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: $ 
 ** 
 */

package com.tibco.xpd.processeditor.xpdl2.widgetimpl;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.Xpdl2AssociationAdapter;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.Xpdl2DataObjectAdapter;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.Xpdl2EventAdapter;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.Xpdl2GatewayAdapter;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.Xpdl2GroupAdapter;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.Xpdl2LaneAdapter;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.Xpdl2MessageFlowAdapter;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.Xpdl2NoteAdapter;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.Xpdl2PoolAdapter;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.Xpdl2ProcessDiagramAdapter;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.Xpdl2ProcessWidgetAdapter;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.Xpdl2SequenceFlowAdapter;
import com.tibco.xpd.processeditor.xpdl2.widgetimpl.adapters.Xpdl2TaskAdapter;
import com.tibco.xpd.processwidget.ProcessWidgetConstants;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Artifact;
import com.tibco.xpd.xpdl2.ArtifactType;
import com.tibco.xpd.xpdl2.Association;
import com.tibco.xpd.xpdl2.Lane;
import com.tibco.xpd.xpdl2.MessageFlow;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2Switch;

/**
 * Adapter factory that can provide adapters required by Process Widget <br>
 * <br>
 * Note: this adapter factory is not connected to ItemProviderAdapterFactory and
 * they cannot be used together in ComposedAdapterFactory
 * 
 * @author wzurek
 */
public class Xpdl2ProcessWidgetAdapterFactory extends AdapterFactoryImpl {

    /**
     * Use switch pattern to provite proper adapters
     */
    private Xpdl2Switch xpdlSW = new Xpdl2Switch() {
        public Object caseActivity(Activity activity) {

            if (activity.getRoute() != null) {
                return new Xpdl2GatewayAdapter();
            }
            if (activity.getEvent() != null) {
                return new Xpdl2EventAdapter();
            }
            return new Xpdl2TaskAdapter();
        }

        public Object caseTransition(Transition object) {
            return new Xpdl2SequenceFlowAdapter();
        }

        public Object caseMessageFlow(MessageFlow object) {
            return new Xpdl2MessageFlowAdapter();
        }

        public Object caseProcess(Process object) {
            return new Xpdl2ProcessDiagramAdapter();
        }

        public Object casePool(Pool object) {
            return new Xpdl2PoolAdapter();
        }

        public Object caseLane(Lane object) {
            return new Xpdl2LaneAdapter();
        }

        public Object caseAssociation(Association object) {
            return new Xpdl2AssociationAdapter();
        }

        public Object caseArtifact(Artifact object) {
            Object result = null;
            ArtifactType at = object.getArtifactType();
            switch (at.getValue()) {
            case ArtifactType.DATA_OBJECT:
                result = new Xpdl2DataObjectAdapter();
                break;
            case ArtifactType.GROUP:
                result = new Xpdl2GroupAdapter();
                break;
            case ArtifactType.ANNOTATION:
                result = new Xpdl2NoteAdapter();
                break;
            }
            return result;
        }
    };

    /*
     * @see org.eclipse.emf.common.notify.impl.AdapterFactoryImpl#createAdapter(org.eclipse.emf.common.notify.Notifier)
     */
    protected Adapter createAdapter(Notifier target) {
        EObject tr = (EObject) target;
        Xpdl2ProcessWidgetAdapter adapter;
        if (tr.eClass().getEPackage() == Xpdl2Package.eINSTANCE) {
            adapter = (Xpdl2ProcessWidgetAdapter) xpdlSW
                    .doSwitch((EObject) target);
        } else {
            throw new IllegalArgumentException();
        }

        // It is possible (on occasion) that we will be asked to adapt an object
        // that we don't have adapter for.
        // In this case, throw an exception rather than just trying to use it.
        if (adapter != null) {
            adapter.setAdapterFactory(this);
        } else {
            throw new IllegalArgumentException();
        }
        
        return adapter;
    }

    /*
     * @see org.eclipse.emf.common.notify.AdapterFactory#isFactoryForType(java.lang.Object)
     */
    public boolean isFactoryForType(Object type) {
        return ProcessWidgetConstants.ADAPTER_TYPE == type;
    }
}
