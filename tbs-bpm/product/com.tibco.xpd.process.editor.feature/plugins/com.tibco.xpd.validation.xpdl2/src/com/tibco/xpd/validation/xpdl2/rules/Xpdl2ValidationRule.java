/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */
package com.tibco.xpd.validation.xpdl2.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.tibco.xpd.processeditor.xpdl2.util.DataMappingUtil;
import com.tibco.xpd.resources.WorkingCopy;
import com.tibco.xpd.resources.util.WorkingCopyUtil;
import com.tibco.xpd.validation.provider.IPreProcessor;
import com.tibco.xpd.validation.provider.IValidationScope;
import com.tibco.xpd.validation.rules.IValidationRule;
import com.tibco.xpd.validation.xpdl2.Messages;
import com.tibco.xpd.validation.xpdl2.tools.IActivityContainer;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.DataMapping;
import com.tibco.xpd.xpdl2.FlowContainer;
import com.tibco.xpd.xpdl2.NamedElement;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * @author nwilson
 */
public abstract class Xpdl2ValidationRule implements IValidationRule {

    /** Default name for unnamed items. */
    private static final String DEFAULT_NAME = "?"; //$NON-NLS-1$

    /** The current validation scope. */
    private IValidationScope scope;

    /**
     * @param scope
     *            The current validation scope.
     * @param o
     *            The object to validate.
     * @see com.tibco.xpd.validation.rules.IValidationRule#validate(com.tibco.xpd.validation.provider.IValidationScope,
     *      java.lang.Object)
     */
    @Override
    public final void validate(IValidationScope scope, final Object o) {
        this.scope = scope;
        validate(o);
    }

    /**
     * @return The current validation scope.
     */
    protected final IValidationScope getScope() {
        return scope;
    }

    /**
     * Sid XPD-8009 - Allow setting of the scope (to support delegate patterns).
     * 
     * @param scope
     *            the scope to set
     */
    public void setScope(IValidationScope scope) {
        this.scope = scope;
    }

    /**
     * @param id
     *            The ID of the issue.
     * @param o
     *            The object on which the issue occurred.
     */
    protected void addIssue(String id, EObject o) {
        Resource resource = o.eResource();
        if (resource != null) {
            String uri = resource.getURIFragment(o);
            String location = getLocation(o);
            scope.createIssue(id, location, uri);
        }
    }

    /**
     * @param id
     *            The ID of the issue.
     * @param o
     *            The object on which the issue occurred.
     * @param messages
     *            Messages to use in formatting error message text.
     */
    protected void addIssue(String id, EObject o, List<String> messages) {
        Resource resource = o.eResource();
        if (resource != null) {
            String uri = resource.getURIFragment(o);
            String location = getLocation(o);
            scope.createIssue(id, location, uri, messages);
        }
    }

    /**
     * @param id
     *            The ID of the issue.
     * @param o
     *            The object on which the issue occurred.
     * @param messages
     *            Messages to use in formatting error message text.
     * @param container
     *            The container on which the issue occurred.
     */
    protected void addIssue(String id, EObject o, List<String> messages,
            IActivityContainer container) {
        Resource resource = o.eResource();
        if (resource != null) {
            String uri = resource.getURIFragment(o);
            String location = getLocation(container);
            Map<String, String> info = Collections
                    .singletonMap(IActivityContainer.ID, container.getId());
            scope.createIssue(id, location, uri, messages, info);
        }
    }

    /**
     * @param id
     *            The ID of the issue.
     * @param o
     *            The object on which the issue occurred.
     * @param messages
     *            Messages to use in formatting error message text.
     * @param additionalInfo
     *            Additional info map.
     */
    protected void addIssue(String id, EObject o, List<String> messages,
            Map<String, String> additionalInfo) {
        Resource resource = o.eResource();
        if (resource != null) {
            String uri = resource.getURIFragment(o);
            String location = getLocation(o);
            scope.createIssue(id, location, uri, messages, additionalInfo);
        }
    }

    /**
     * @param id
     *            The ID of the issue.
     * @param parent
     *            parent.
     * @param loop
     *            The loop on which the issue occurred.
     */
    protected final void addIssue(String id, EObject parent,
            IActivityContainer loop) {
        Resource resource = parent.eResource();
        if (resource != null) {
            String uri = resource.getURIFragment(parent);
            String location = getLocation(loop);
            List<String> list = Collections.emptyList();
            Map<String, String> info = Collections
                    .singletonMap(IActivityContainer.ID, loop.getId());
            scope.createIssue(id, location, uri, list, info);
        }
    }

    /**
     * @param <T>
     *            The tool type.
     * @param clss
     *            The tool class.
     * @param input
     *            The tool input object.
     * @return The tool.
     */
    protected <T extends IPreProcessor> T getTool(Class<T> clss, Object input) {
        return scope.getTool(clss, input);
    }

    /**
     * @param o
     *            The object to validate.
     */
    protected abstract void validate(Object o);

    /**
     * @param o
     *            The EObject to get the location for.
     * @return The descriptive location of the object.
     */
    protected String getLocation(EObject o) {
        String location = null;
        if (o != null) {
            location = getLocationPath(o);
            if (o instanceof Transition) {
                Transition trans = (Transition) o;
                location = getTransitionName(trans);
            } else if (o instanceof NamedElement) {
                String name = ((NamedElement) o).getName();
                location += name == null ? DEFAULT_NAME : name;
            } else if (o instanceof DataMapping) {
                String name = DataMappingUtil.getTarget((DataMapping) o);
                location += name == null ? DEFAULT_NAME : name;
            }
            /*
             * Sid XPD-8467. This was added for XPD-8270 unnecessarily and
             * caused duplicate acitivty name in issue location string until the
             * change in getLocationPath() for scirptDataMapper handling was
             * added. Unfortunately THAT change cause the activity name to
             * disappear from the location. Given the fact that the change HERE
             * isn't actually needed because the location string builder would
             * look at the container of scriptdatamapper until it foound
             * activity and used tha anyway, we should be able to safely remove
             * the code here and in getLocationPath()
             */
            // } else if (o instanceof ScriptDataMapper) {
            // Activity parentActivity = Xpdl2ModelUtil.getParentActivity(o);
            //
            // String name = parentActivity != null ? parentActivity.getName()
            // : null;
            // location += name == null ? DEFAULT_NAME : name;
            // }
        }
        return location;
    }

    /**
     * Gets transition name or provide descriptive name if transition doesn't
     * have a name.
     * 
     * @param transition
     *            The transition.
     * @return The transition name.
     */
    private String getTransitionName(Transition transition) {
        String name = DEFAULT_NAME;
        String description = transition.getName();
        if (description != null && description.length() != 0) {
            name = description;
        } else {
            EObject process = transition.eContainer();
            while (process != null && !(process instanceof Process)) {
                process = process.eContainer();
            }
            if (process != null) {
                Activity in =
                        getActivity((Process) process, transition.getFrom());
                Activity out =
                        getActivity((Process) process, transition.getTo());
                String activityInName = in == null ? "" : in.getName(); //$NON-NLS-1$
                if (activityInName == null || activityInName.length() == 0) {
                    activityInName = Messages.getString(
                            "Xpdl2ValidationRule_UnnamedActivity_shortdesc"); //$NON-NLS-1$
                }
                String activityOutName = out == null ? "" : out.getName(); //$NON-NLS-1$
                if (activityOutName == null || activityOutName.length() == 0) {
                    activityOutName = Messages.getString(
                            "Xpdl2ValidationRule_UnnamedActivity_shortdesc"); //$NON-NLS-1$
                }
                name = activityInName + " -> " + activityOutName; //$NON-NLS-1$
            }
        }
        return name;

    }

    /**
     * This method will return the first Activity with the passed Id.
     * 
     * @param process
     *            The conaining process.
     * @param id
     *            The activity ID.
     * @return The activity.
     */
    private Activity getActivity(Process process, String id) {
        EList<EObject> contents = process.eContents();
        for (Iterator<EObject> i = contents.iterator(); i.hasNext();) {
            Object nexti = i.next();
            if (nexti instanceof Activity) {
                Activity activity = (Activity) nexti;
                if (activity.getId().equals(id)) {
                    return activity;
                }
            }
        }
        return null;
    }

    /**
     * @param o
     *            The object to get the path for.
     * @return The colon seperated path string.
     */
    protected String getLocationPath(EObject o) {
        return getLocationPath("", o); //$NON-NLS-1$
    }

    /**
     * @param path
     *            The current path.
     * @param o
     *            The object to get the path for.
     * @return The colon seperated path string.
     */
    protected String getLocationPath(String path, EObject o) {
        /*
         * Sid XPD-8467. This was added for XPD-8270 because a change in
         * getLocation() for ScriptDataMapper element problem markers
         * unnecessarily added activity to the location and that ccaused
         * activity name to be duplicated in the location. This change was meant
         * to suppress that duplication but caused activity name not to be
         * output in location for other data mapping problem markers.
         * 
         * Location text appears to be fine for both scenarios if we remove the
         * special scriptDataMapepr handling code here and in getLocation() for
         * scriptdataMapper - so removing from both places. and caused duplicate
         * activity name in issue location string until the change in
         * getLocationPath() for scirptDataMapper handling was added.
         * Unfortunately THAT change cause the activity name to disappear from
         * the location. Given the fact that the change HERE isn't actually
         * needed because the location string builder would look at the
         * container of scriptdatamapper until it foound activity and used tha
         * anyway, we should be able to safely remove the code here and in
         * getLocationPath()
         */
        // if (o instanceof ScriptDataMapper) {
        // o = Xpdl2ModelUtil.getParentActivity(o);
        // }

        EObject container = o.eContainer();
        if (!(o instanceof Package)) {
            path = getLocationPath(path, container);
        }

        if (container != null) {
            if (container instanceof Process) {
                path += ((Process) container).getName() + ":"; //$NON-NLS-1$
            } else if (container instanceof ActivitySet) {
                // Activity set's are not given names, just the task that
                // references them.
                String actSetId = ((ActivitySet) container).getId();

                Collection<Activity> activities =
                        Xpdl2ModelUtil.getAllActivitiesInProc(
                                ((ActivitySet) container).getProcess());
                Activity embSubProcAct = null;

                for (Activity act : activities) {
                    if (act.getBlockActivity() != null) {
                        if (actSetId.equals(
                                act.getBlockActivity().getActivitySetId())) {
                            embSubProcAct = act;
                            break;
                        }
                    }
                }

                if (embSubProcAct != null && embSubProcAct.getName() != null) {
                    path += embSubProcAct.getName() + ":"; //$NON-NLS-1$
                } else {
                    path += ":"; //$NON-NLS-1$
                }

            } else if (container instanceof Activity) {
                path += ((Activity) container).getName() + ":"; //$NON-NLS-1$
            }
        }
        return path;
    }

    /**
     * @param loop
     *            The loop to get the location for.
     * @return The descriptive location of the loop.
     */
    protected String getLocation(IActivityContainer loop) {
        String location = null;
        FlowContainer container = loop.getFlowContainer();
        WorkingCopy wc = WorkingCopyUtil.getWorkingCopyFor(container);
        String[] activities = loop.getActivities();
        if (activities.length > 0) {
            StringBuffer path = new StringBuffer();
            if (container instanceof Process) {
                path.append(((Process) container).getName());
            } else if (container instanceof ActivitySet) {
                path.append(((ActivitySet) container).getProcess().getName());
            }
            path.append(":"); //$NON-NLS-1$
            boolean isFirst = true;
            for (String activity : activities) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    path.append("->"); //$NON-NLS-1$
                }
                String name = DEFAULT_NAME;
                EObject eo = wc.resolveEObject(activity);
                if (eo instanceof NamedElement) {
                    name = ((NamedElement) eo).getName();
                }
                path.append(name);
            }
            location = path.toString();
        }
        return location;
    }

    /**
     * A safe "equals" implementation that allows either or both objects to be
     * assigned or null.
     * 
     * @param object1
     * @param object2
     * @return true if the objects are equal.
     */
    protected boolean safeEquals(Object object1, Object object2) {
        if (object1 == null) {
            if (object2 == null) {
                return true;
            } else {
                return false;
            }
        } else if (object2 == null) {
            if (object1 == null) {
                return true;
            } else {
                return false;
            }
        } else {
            return object1.equals(object2);
        }
    }

    /**
     * @param args
     * @return List of string for args.
     */
    protected List<String> createMessageList(Object... args) {
        List<String> list = new ArrayList<String>();
        for (Object arg : args) {
            list.add(arg != null ? arg.toString() : ""); //$NON-NLS-1$
        }

        return list;
    }

    /**
     * Create a map of strings from args that should be specified as...
     * <p>
     * Key,Value,Key2,Value2 and so on.
     * 
     * @param addInfoPairs
     * @return map of strings.
     */
    protected Map<String, String> createAdditionalInfo(String... addInfoPairs) {
        Map<String, String> map = new HashMap<String, String>();

        /*
         * Sid XPD-7676 - noticed that this wasn't processing key, value,
         * key,value correctly.
         * 
         * It was actually processing Key,value value,key key2,value2
         * 
         * Changed to += 2;
         */
        for (int i = 0; i < (addInfoPairs.length - 1); i += 2) {
            map.put(addInfoPairs[i], addInfoPairs[i + 1]);
        }
        return map;
    }

}
