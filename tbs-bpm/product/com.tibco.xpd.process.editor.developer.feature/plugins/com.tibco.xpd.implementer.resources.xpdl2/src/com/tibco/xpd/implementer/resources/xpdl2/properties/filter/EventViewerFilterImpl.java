package com.tibco.xpd.implementer.resources.xpdl2.properties.filter;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IFilter;

import com.tibco.xpd.processeditor.xpdl2.properties.script.ScriptGrammarFactory;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.DirectionType;
import com.tibco.xpd.xpdl2.EndEvent;
import com.tibco.xpd.xpdl2.Event;
import com.tibco.xpd.xpdl2.ImplementationType;
import com.tibco.xpd.xpdl2.IntermediateEvent;
import com.tibco.xpd.xpdl2.ResultType;
import com.tibco.xpd.xpdl2.StartEvent;
import com.tibco.xpd.xpdl2.TriggerConditional;
import com.tibco.xpd.xpdl2.TriggerResultMessage;
import com.tibco.xpd.xpdl2.TriggerTimer;
import com.tibco.xpd.xpdl2.TriggerType;

class EventViewerFilterImpl implements EventViewerFilter {

    private static final HasTriggerResultMessageFilter HAS_TRIGGER_RESULT_MESSAGE_FILTER =
            new HasTriggerResultMessageFilter();

    @Override
    public IFilter hasType(EventType... eventTypes) {
        return new HasTypeFilter(eventTypes);
    }

    class HasTypeFilter implements IFilter {

        private final Collection<EventType> eventTypes;

        public HasTypeFilter(EventType... eventTypes) {
            this.eventTypes = Arrays.asList(eventTypes);
        }

        @Override
        public boolean select(Object toTest) {
            boolean result = false;
            EObject eo = null;
            if (toTest instanceof EObject) {
                eo = (EObject) toTest;
            } else if (toTest instanceof IAdaptable) {
                eo = (EObject) ((IAdaptable) toTest).getAdapter(EObject.class);
            }

            if (eo != null && eo instanceof Activity) {
                Activity activity = (Activity) eo;
                Event event = activity.getEvent();
                EventType type = recogniseEvent(event);
                result = (type == null) ? false : eventTypes.contains(type);
            }

            return result;
        }

        private EventType recogniseEvent(Event task) {
            EventType result;

            if (task instanceof EndEvent) {
                result = EventType.END;
            } else if (task instanceof StartEvent) {
                result = EventType.START;
            } else if (task instanceof IntermediateEvent) {
                result = EventType.INTERMEDIATE;
            } else {
                result = null;
            }
            return result;
        }
    }

    @Override
    public synchronized IFilter hasTriggerResultMessage() {
        return HAS_TRIGGER_RESULT_MESSAGE_FILTER;
    }

    private static class HasTriggerResultMessageFilter implements IFilter {

        public HasTriggerResultMessageFilter() {
        }

        @Override
        public boolean select(Object toTest) {
            boolean result = false;
            EObject eo = null;
            if (toTest instanceof EObject) {
                eo = (EObject) toTest;
            } else if (toTest instanceof IAdaptable) {
                eo = (EObject) ((IAdaptable) toTest).getAdapter(EObject.class);
            }

            if (eo != null && eo instanceof Activity) {
                Activity activity = (Activity) eo;
                Event event = activity.getEvent();

                if (event != null) {
                    if (event instanceof EndEvent) {
                        result =
                                ResultType.MESSAGE_LITERAL
                                        .equals(((EndEvent) event).getResult());
                    } else if (event instanceof StartEvent) {
                        result =
                                TriggerType.MESSAGE_LITERAL
                                        .equals(((StartEvent) event)
                                                .getTrigger());
                    } else if (event instanceof IntermediateEvent) {
                        result =
                                TriggerType.MESSAGE_LITERAL
                                        .equals(((IntermediateEvent) event)
                                                .getTrigger());
                    }
                }

            }
            return result;
        }
    }

    @Override
    public synchronized IFilter isImplementedBy(ImplementationType i) {
        return new ImplementedByFilter(i);
    }

    private class ImplementedByFilter implements IFilter {
        private final ImplementationType implType;

        public ImplementedByFilter(ImplementationType i) {
            this.implType = i;
        }

        @Override
        public boolean select(Object toTest) {
            boolean result = false;
            EObject eo = null;
            if (toTest instanceof EObject) {
                eo = (EObject) toTest;
            } else if (toTest instanceof IAdaptable) {
                eo = (EObject) ((IAdaptable) toTest).getAdapter(EObject.class);
            }

            if (eo != null && eo instanceof Activity) {
                Activity activity = (Activity) eo;
                Event event = activity.getEvent();
                ImplementationType implementation = null;
                if (event instanceof EndEvent) {
                    implementation = ((EndEvent) event).getImplementation();
                } else if (event instanceof StartEvent) {
                    implementation = ((StartEvent) event).getImplementation();
                } else if (event instanceof IntermediateEvent) {
                    implementation =
                            ((IntermediateEvent) event).getImplementation();
                }

                if (implementation == null) {
                    result = false;
                } else {
                    result = implType.equals(implementation);
                }

            }
            return result;
        }
    }

    @Override
    public IFilter hasTriggerType(TriggerType... triggerType) {
        return new HasTriggerType(triggerType);
    }

    private class HasTriggerType implements IFilter {

        private final List<TriggerType> triggerTypes;

        public HasTriggerType(TriggerType[] triggerTypes) {
            this.triggerTypes = Arrays.asList(triggerTypes);
        }

        @Override
        public boolean select(Object toTest) {
            boolean result = false;
            EObject eo = null;
            if (toTest instanceof EObject) {
                eo = (EObject) toTest;
            } else if (toTest instanceof IAdaptable) {
                eo = (EObject) ((IAdaptable) toTest).getAdapter(EObject.class);
            }

            if (eo != null && eo instanceof Activity) {
                Activity activity = (Activity) eo;
                Event event = activity.getEvent();
                EObject eventTriggerTypeNode = event.getEventTriggerTypeNode();
                TriggerType type = recogniseTrigger(eventTriggerTypeNode);
                result = (type == null) ? false : triggerTypes.contains(type);
            }
            return result;
        }

        private TriggerType recogniseTrigger(EObject eventTriggerTypeNode) {
            TriggerType result;

            if (eventTriggerTypeNode instanceof TriggerResultMessage) {
                result = TriggerType.MESSAGE_LITERAL;
            }
            if (eventTriggerTypeNode instanceof TriggerConditional) {
                result = TriggerType.CONDITIONAL_LITERAL;
            }
            if (eventTriggerTypeNode instanceof TriggerTimer) {
                result = TriggerType.TIMER_LITERAL;
            } else {
                result = null;
            }
            return result;

        }
    }

    /**
     * @see com.tibco.xpd.implementer.resources.xpdl2.properties.filter.TaskViewerFilter#hasScriptGrammar(java.lang.String[])
     * @param direction
     * @param grammarType
     * 
     * @return
     */
    @Override
    public IFilter hasScriptGrammar(DirectionType direction,
            String... grammarType) {
        return new HasScriptGrammarFilter(direction, grammarType);
    }

    /**
     * Filter to test the Grammar type
     * 
     * @author ssirsika
     * @since 21-Jan-2016
     */
    class HasScriptGrammarFilter implements IFilter {

        private Collection<String> types;

        private DirectionType direction;

        /**
         * @param direction
         * @param grammarType
         */
        public HasScriptGrammarFilter(DirectionType direction,
                String... grammarType) {
            this.direction = direction;
            types = Arrays.asList(grammarType);
        }

        /**
         * Return 'true' if 'grammarType' contains grammar set in the activity.
         * 
         * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
         * 
         * @param toTest
         * @return
         */
        @Override
        public boolean select(Object toTest) {

            EObject eo = null;
            if (toTest instanceof EObject) {
                eo = (EObject) toTest;
            } else if (toTest instanceof IAdaptable) {
                eo = (EObject) ((IAdaptable) toTest).getAdapter(EObject.class);
            }

            if (eo instanceof Activity) {
                return types.contains(ScriptGrammarFactory
                        .getGrammarToUse((Activity) eo, direction));
            }

            return false;
        }

    }
}
