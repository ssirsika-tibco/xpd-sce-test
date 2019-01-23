/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.implementer.script;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.tibco.xpd.xpdExtension.XpdExtensionPackage;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.CatchThrow;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.Message;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * Message adapter for the intermediate trigger event doing a REST call.
 * 
 * @author jarciuch
 * @since 17 Apr 2015
 */
public class RestEventMessageAdapter extends BaseAbstractActivityMessageAdapter
        implements RestActivityMessageProvider {

    private Event getEvent(Activity activity) {
        if (activity != null) {
            Event event = activity.getEvent();
            if (event instanceof IntermediateEvent || event instanceof EndEvent) {
                return activity.getEvent();
            }
        }
        return null;
    }

    private TriggerResultMessage getTriggerResultMessage(Activity activity) {
        TriggerResultMessage trm = null;
        Event event = getEvent(activity);
        trm = getTriggerResultMessage(event);
        return trm;
    }

    /**
     * @param trm
     * @param event
     * @return
     */
    private TriggerResultMessage getTriggerResultMessage(Event event) {
        TriggerResultMessage trm = null;
        if (event != null) {
            if (event instanceof IntermediateEvent) {
                trm = ((IntermediateEvent) event).getTriggerResultMessage();
            } else if (event instanceof EndEvent) {
                trm = ((EndEvent) event).getTriggerResultMessage();
            }
        }
        return trm;
    }

    /**
     * {@inheritDoc}
     * 
     * Only throw intermediate events with RESTService implementation are
     * supported.
     */
    @Override
    public boolean isSupported(Activity activity) {
        Event event = getEvent(activity);
        if (event != null) {
            Object type =
                    Xpdl2ModelUtil.getOtherAttribute(event,
                            XpdExtensionPackage.eINSTANCE
                                    .getDocumentRoot_ImplementationType());
            TriggerResultMessage triggerResultMessage =
                    getTriggerResultMessage(event);
            if (triggerResultMessage != null) {
                return RestActivityAdapterFactory.getRestServiceImplName()
                        .equals(type)
                        && triggerResultMessage.getCatchThrow() == CatchThrow.THROW;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Message getMessageIn(Activity activity) {
        TriggerResultMessage trm = getTriggerResultMessage(activity);
        if (trm != null) {
            return trm.getMessage();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Message getMessageOut(Activity activity) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ImplementationType getImplementation(Activity activity) {
        ImplementationType type = null;
        Event event = getEvent(activity);
        if (event != null) {
            if (event instanceof IntermediateEvent) {
                type = ((IntermediateEvent) event).getImplementation();
            } else if (event instanceof EndEvent) {
                type = ((EndEvent) event).getImplementation();
            }
        }
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Command getSetImplementationCommand(EditingDomain ed, Activity act,
            ImplementationType newImplType) {
        Event event = getEvent(act);
        if (event != null) {
            if (event instanceof IntermediateEvent) {
                return SetCommand.create(ed, event, Xpdl2Package.eINSTANCE
                        .getIntermediateEvent_Implementation(), newImplType);
            } else if (event instanceof EndEvent) {
                return SetCommand.create(ed,
                        event,
                        Xpdl2Package.eINSTANCE.getEndEvent_Implementation(),
                        newImplType);
            }
        }
        return UnexecutableCommand.INSTANCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EObject getMessageContainer(Activity activity) {
        return getTriggerResultMessage(activity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasMappingIn(Activity act) {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasMappingOut(Activity act) {
        return false;
    }

}