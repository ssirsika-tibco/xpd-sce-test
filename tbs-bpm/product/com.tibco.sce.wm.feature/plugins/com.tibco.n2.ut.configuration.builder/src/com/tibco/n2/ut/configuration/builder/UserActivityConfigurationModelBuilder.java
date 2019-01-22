/*
 *
 *      ENVIRONMENT:    Java Generic
 *
 *      DESCRIPTION:    Main Class for User Task Conversions
 *      
 *      COPYRIGHT:      (C) 2009 Tibco Software Inc
 *
 */
package com.tibco.n2.ut.configuration.builder;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.tibco.bx.xpdl2bpel.extensions.IActivityConfigurationModelBuilder;
import com.tibco.xpd.xpdl2.Activity;
import com.tibco.xpd.xpdl2.Participant;
import com.tibco.xpd.xpdl2.util.Xpdl2ModelUtil;

/**
 * 
 * This class implements an Eclipse extension point invoked at design time when
 * generating a .bpel file. This allows conversion of default XPDL EMF Model for
 * an Activity to a user defined EMF Model.
 * 
 */
public class UserActivityConfigurationModelBuilder implements
        IActivityConfigurationModelBuilder {

    public UserActivityConfigurationModelBuilder() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tibco.bpel.convertxpdltobpel.extensions.
     * IActivityConfigurationModelBuilder
     * #transformConfigModel(com.tibco.xpd.xpdl2.Activity, java.util.Map)
     */
    public EObject transformConfigModel(Activity activity,
            Map<String, Participant> participantMap) {
        
        EObject result = null;
        try {
            
            // Check to see if it is a User Task or Page Activity
            if( Xpdl2ModelUtil.isPageflow(activity.getProcess()) ) {
                PageActivityConfigurationModelBuilder pageActivityBuilder = new PageActivityConfigurationModelBuilder();
                result = pageActivityBuilder.convertToPageActivityDataModel(activity);
            } else {
                UserTaskConfigurationModelBuilder userTaskBuilder = new UserTaskConfigurationModelBuilder();
                result = userTaskBuilder.convertToUserTaskDataModel(activity);                
            }
        }
        catch (RuntimeException e) {
            // Catch any Runtime exceptions.  The following logger command
            // will print an error in the eclipse log with the one line
            // message displayed and then the full dialog allowing the
            // user to see the full exception
            Activator.getDefault().getLogger().error(e, e.getMessage());
            throw e;
        }

        // Enable the following in order to Dump the model to console
        //Resource resource = new UsertaskResourceFactoryImpl().createResource(URI
        // .createFileURI("dummy.xml"));
        // resource.getContents().add(result);
        // try {
        // resource.save(System.out, Collections.EMPTY_MAP);
        // } catch (IOException e) {
        // e.printStackTrace();
        // }

        return result;
    }

}
