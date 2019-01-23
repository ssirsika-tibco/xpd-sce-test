/*
 * Copyright (c) TIBCO Software Inc 2004, 2008. All rights reserved.
 */

package com.tibco.xpd.analyst.resources.xpdl2.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.URIConverter.ReadableInputStream;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;

import com.tibco.xpd.fragments.IFragment;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.logger.Logger;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.ActivitySet;
import com.tibco.xpd.xpdl2.BlockActivity;
import com.tibco.xpd.xpdl2.DocumentRoot;
import com.tibco.xpd.xpdl2.ExtendedAttribute;
import com.tibco.xpd.xpdl2.Package;
import com.tibco.xpd.xpdl2.Pool;
import com.tibco.xpd.xpdl2.Process;
import com.tibco.xpd.xpdl2.Transition;
import com.tibco.xpd.xpdl2.Xpdl2Package;
import com.tibco.xpd.xpdl2.extension.EMFSearchUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;
import com.tibco.xpd.xpdl2.util.Xpdl2ResourceImpl;
import com.tibco.xpd.xpdl2.util.Xpdl2XMLProcessor;

/**
 * Utility class that provides XPDL2 resource from a string, and other way round
 * as well. The process editor fragments are constructed so that each package
 * corresponds to a fragment. All the elements in the process and artifacts and
 * message flows in the package are part of the same fragment.
 * 
 * @author rsomayaj
 * 
 */
public class Xpdl2ProcessorUtil {

    private final static Logger LOG = XpdResourcesPlugin.getDefault()
            .getLogger();

    private static final String XML_EXTENSION = "xml"; //$NON-NLS-1$

    public final static URI XML_URI = URI.createFileURI(XML_EXTENSION);

    private final static String DUMMYPOOL_ID = "_Fragment_Dummy_Pool_"; //$NON-NLS-1$

    private final static String FRAG_FEEDBACK_PREFIX = "FragmentFeedbackRect_"; //$NON-NLS-1$

    private static final String DEST_EXT_ATTR = "Destination"; //$NON-NLS-1$

    /**
     * Utility to retrieve an {@link Package} from a {@link String} data
     * provided.
     * 
     * @param fragData
     * @return
     */
    public static Package getFragmentPackage(String fragData) {
        StringReader reader = new StringReader(fragData);
        Resource res = null;

        URIConverter.ReadableInputStream stream =
                new ReadableInputStream(reader);
        Xpdl2XMLProcessor xpdl2Processor = new Xpdl2XMLProcessor();
        try {
            res = xpdl2Processor.load(stream, null);
            res.setTrackingModification(false);
            res.eAdapters().add(new FragmentAdapter());
        } catch (Exception ex) {
            LOG.error(ex);
            ex.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    LOG.error(e);
                } catch (Exception ex) {
                    LOG.error(ex);
                }
            }
        }
        if (res != null && !(res.getContents().isEmpty())) {
            EObject object = res.getContents().get(0);
            if (object instanceof com.tibco.xpd.xpdl2.DocumentRoot) {
                Package pkg = ((DocumentRoot) object).getPackage();
                return pkg;
            }
        }
        return null;
    }

    /**
     * Retrieves an {@link Package} provided an {@link IFragment}. The
     * understanding is that the {@link IFragment} provided is a Process editor
     * fragment and contains data relevant to Xpdl2 model.
     * 
     * @param fragment
     * @return
     */
    public static Package getFragmentPackage(IFragment fragment) {
        if (fragment.getLocalizedData() != null) {
            return getFragmentPackage(fragment.getLocalizedData());
        }
        return null;
    }

    /**
     * Retrieves fragment drop objects,
     * 
     * @param fragmentProcess
     * @return
     */
    public static List<EObject> getProcessElements(Process fragmentProcess) {
        List<EObject> fragmentElements = null;
        if (fragmentProcess != null) {
            fragmentElements = new ArrayList<EObject>();
            addPoolAndLaneIfRequired(fragmentElements, fragmentProcess);
            addActivities(fragmentElements, fragmentProcess);
            addTransitions(fragmentElements, fragmentProcess);
            fragmentElements.addAll(Xpdl2ModelUtil
                    .getAllArtifactsInProcess(fragmentProcess));
            fragmentElements.addAll(Xpdl2ModelUtil
                    .getAllAssociationsInProc(fragmentProcess));
            fragmentElements.addAll(Xpdl2ModelUtil
                    .getAllMessageFlowsInProc(fragmentProcess));
            fragmentElements.addAll(fragmentProcess.getFormalParameters());
            fragmentElements.addAll(fragmentProcess.getDataFields());
            fragmentElements.addAll(fragmentProcess.getParticipants());

            fragmentElements.addAll(fragmentProcess.getPackage()
                    .getTypeDeclarations());
            fragmentElements.addAll(fragmentProcess.getPackage()
                    .getDataFields());
            fragmentElements.addAll(fragmentProcess.getPackage()
                    .getParticipants());

        }
        return fragmentElements;
    }

    /**
     * @param fragmentElements
     * @param fragmentProcess
     */
    private static void addTransitions(List<EObject> fragmentElements,
            Process fragmentProcess) {
        Collection<Transition> transitionsInProc =
                Xpdl2ModelUtil.getAllTransitionsInProc(fragmentProcess);
        for (Transition transition : transitionsInProc) {
            if (transition.getFlowContainer() instanceof ActivitySet) {
                continue;
            } else {
                fragmentElements.add(transition);
            }
        }
    }

    /**
     * @param fragmentElements
     * @param fragmentProcess
     */
    private static void addActivities(List<EObject> fragmentElements,
            Process fragmentProcess) {
        Collection<Activity> activitiesInProc =
                Xpdl2ModelUtil.getAllActivitiesInProc(fragmentProcess);
        for (Activity act : activitiesInProc) {
            // for an embedded subproc, need to add the elements that is
            // contained within an ActivitySet
            BlockActivity blockActivity = act.getBlockActivity();
            if (blockActivity != null) {
                String activitySetId = blockActivity.getActivitySetId();
                ActivitySet activitySet =
                        fragmentProcess.getActivitySet(activitySetId);
                fragmentElements.add(activitySet);
            }

            if (act.getFlowContainer() instanceof ActivitySet) {
                continue;
            } else {
                fragmentElements.add(act);
            }
        }

    }

    /**
     * @param fragmentElements
     * @param fragmentProcess
     */
    @SuppressWarnings("unchecked")
    private static void addPoolAndLaneIfRequired(
            List<EObject> fragmentElements, Process fragmentProcess) {
        Package pkg = fragmentProcess.getPackage();
        List<Pool> poolList =
                EMFSearchUtil.findManyInList(pkg.getPools(),
                        Xpdl2Package.eINSTANCE.getPool_ProcessId(),
                        fragmentProcess.getId());
        for (Pool pool : poolList) {
            if (!(pool.getId().startsWith(DUMMYPOOL_ID))) {
                fragmentElements.add(pool);
            }
        }

    }

    public static Process getFragmentProcess(Package pkg) {
        if (pkg != null) {
            if (!(pkg.getProcesses().isEmpty())) {
                return pkg.getProcesses().get(0);
            }
        }
        return null;
    }

    /**
     * 
     * @param data
     * @return
     */
    public static Collection<EObject> getFragmentDropObjects(String data) {
        Package fragmentPackage = getFragmentPackage(data);
        if (fragmentPackage != null) {
            Process fragmentProcess = getFragmentProcess(fragmentPackage);
            if (fragmentProcess != null) {
                List<EObject> elements = getProcessElements(fragmentProcess);
                return elements;
            }
        }
        return null;
    }

    public static class FragmentAdapter implements Adapter,
            IEditingDomainProvider {

        /**
         * @see org.eclipse.emf.common.notify.Adapter#getTarget()
         * 
         * @return
         */
        @Override
        public Notifier getTarget() {
            return null;
        }

        /**
         * @see org.eclipse.emf.common.notify.Adapter#isAdapterForType(java.lang.Object)
         * 
         * @param type
         * @return
         */
        @Override
        public boolean isAdapterForType(Object type) {
            return IEditingDomainProvider.class == type;
        }

        /**
         * @see org.eclipse.emf.common.notify.Adapter#notifyChanged(org.eclipse.emf.common.notify.Notification)
         * 
         * @param notification
         */
        @Override
        public void notifyChanged(Notification notification) {
            // do nothing
        }

        /**
         * @see org.eclipse.emf.common.notify.Adapter#setTarget(org.eclipse.emf.common.notify.Notifier)
         * 
         * @param newTarget
         */
        @Override
        public void setTarget(Notifier newTarget) {
            // do nothing
        }

        /**
         * @see org.eclipse.emf.edit.domain.IEditingDomainProvider#getEditingDomain()
         * 
         * @return
         */
        @Override
        public EditingDomain getEditingDomain() {
            return XpdResourcesPlugin.getDefault().getEditingDomain();
        }

    }

    /**
     * @param fragPackage
     * @return
     */
    public static String getResourceString(Package fragPackage) {
        Xpdl2ResourceImpl res = new Xpdl2ResourceImpl(XML_URI);
        res.getContents().add(fragPackage);

        res.getDefaultSaveOptions().put(XMLResource.OPTION_EXTENDED_META_DATA,
                Boolean.TRUE);
        res.getDefaultLoadOptions().put(XMLResource.OPTION_EXTENDED_META_DATA,
                Boolean.TRUE);

        res.eAdapters().add(new FragmentAdapter());
        String strRes = null;
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            res.save(os, Collections.EMPTY_MAP);
            strRes = os.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return strRes;
    }

    public static Collection<Rectangle> getFeedbackRectangles(IFragment fragment) {
        Collection<Rectangle> feedbackRects = new ArrayList<Rectangle>();
        Package fragmentPackage = getFragmentPackage(fragment);
        if (fragmentPackage != null
                && !(fragmentPackage.getProcesses().isEmpty())) {
            Process fragmentProcess = getFragmentProcess(fragmentPackage);
            EList extAttrs = fragmentProcess.getExtendedAttributes();

            if (extAttrs != null) {
                for (Iterator iter = extAttrs.iterator(); iter.hasNext();) {
                    ExtendedAttribute extAttr = (ExtendedAttribute) iter.next();

                    String name = extAttr.getName();
                    if (name != null && name.startsWith(FRAG_FEEDBACK_PREFIX)) {
                        String rcStr = extAttr.getValue();

                        if (rcStr != null) {
                            StringTokenizer tok =
                                    new StringTokenizer(rcStr, ","); //$NON-NLS-1$

                            Rectangle rc = new Rectangle();

                            if (tok.hasMoreTokens() && tok.countTokens() == 4) {
                                rc.x = Integer.parseInt(tok.nextToken());
                                rc.y = Integer.parseInt(tok.nextToken());
                                rc.width = Integer.parseInt(tok.nextToken());
                                rc.height = Integer.parseInt(tok.nextToken());

                                feedbackRects.add(rc);
                            }
                        }
                    }
                }
            }

        }

        return feedbackRects;
    }

    public static HashMap<String, String> getDestinationEnvs(IFragment fragment) {
        HashMap<String, String> destEnvs = new HashMap<String, String>();
        Package fragmentPackage = getFragmentPackage(fragment);
        if (fragmentPackage != null
                && !(fragmentPackage.getProcesses().isEmpty())) {
            Process fragmentProcess = getFragmentProcess(fragmentPackage);
            EList extAttrs = fragmentProcess.getExtendedAttributes();

            if (extAttrs != null) {
                for (Iterator iter = extAttrs.iterator(); iter.hasNext();) {
                    ExtendedAttribute extAttr = (ExtendedAttribute) iter.next();

                    String name = extAttr.getName();
                    if (name != null && DEST_EXT_ATTR.equals(name)) {
                        String destValue = extAttr.getValue();
                        StringTokenizer sTokenizer =
                                new StringTokenizer(destValue, "::"); //$NON-NLS-1$
                        String destId = sTokenizer.nextToken();
                        String destName = sTokenizer.nextToken();
                        destEnvs.put(destId, destName);
                    }
                }
            }
        }
        return destEnvs;
    }

    /**
     * 
     * @param fragment
     * @return <code>true</code> if the given fragment represents a pageflow
     *         process <code>false</code> otherwise
     */
    public static boolean isPageflow(IFragment fragment) {
        boolean pageflow = false;
        Package fragmentPackage = getFragmentPackage(fragment);
        if (fragmentPackage != null
                && !(fragmentPackage.getProcesses().isEmpty())) {
            Process fragmentProcess = getFragmentProcess(fragmentPackage);
            if (fragmentProcess != null) {
                pageflow = Xpdl2ModelUtil.isPageflow(fragmentProcess);
            }
        }
        return pageflow;
    }

    /**
     * 
     * @param fragment
     * @return <code>true</code> if the given fragment represents a business
     *         service <code>false</code> otherwise
     */
    public static boolean isBusinessService(IFragment fragment) {

        Package fragmentPackage = getFragmentPackage(fragment);
        if (fragmentPackage != null
                && !(fragmentPackage.getProcesses().isEmpty())) {

            Process fragmentProcess = getFragmentProcess(fragmentPackage);
            if (fragmentProcess != null) {

                boolean isBusinessService =
                        Xpdl2ModelUtil
                                .isPageflowBusinessService(fragmentProcess);
                return isBusinessService;
            }
        }
        return false;
    }

    /**
     * 
     * @param fragment
     * @return <code>true</code> if the given fragment represents a case service
     *         <code>false</code> otherwise
     */
    public static boolean isCaseService(IFragment fragment) {

        Package fragmentPackage = getFragmentPackage(fragment);
        if (fragmentPackage != null
                && !(fragmentPackage.getProcesses().isEmpty())) {

            Process fragmentProcess = getFragmentProcess(fragmentPackage);
            if (fragmentProcess != null) {

                boolean isCaseService =
                        Xpdl2ModelUtil.isCaseService(fragmentProcess);
                return isCaseService;
            }
        }
        return false;
    }

    /**
     * 
     * @param fragment
     * @return <code>true</code> if the given fragment represents a service
     *         process <code>false</code> otherwise
     */
    public static boolean isServiceProcess(IFragment fragment) {

        Package fragmentPackage = getFragmentPackage(fragment);
        if (fragmentPackage != null
                && !(fragmentPackage.getProcesses().isEmpty())) {

            Process fragmentProcess = getFragmentProcess(fragmentPackage);
            if (fragmentProcess != null) {

                boolean isServiceProcess =
                        Xpdl2ModelUtil.isServiceProcess(fragmentProcess);
                return isServiceProcess;
            }
        }
        return false;
    }
}
