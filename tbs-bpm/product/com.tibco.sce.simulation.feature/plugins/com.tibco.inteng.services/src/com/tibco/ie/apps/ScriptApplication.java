/* 
 ** 
 **  MODULE:             $RCSfile: ScriptApplication.java $ 
 **                      $Revision: 1.13 $ 
 **                      $Date: 2005/03/01 19:27:51Z $ 
 ** 
 ** DESCRIPTION    :           
 **                                              
 ** 
 **  ENVIRONMENT:  Java - Platform independent 
 ** 
 **  COPYRIGHT:    (c) 2003 Staffware plc, All Rights Reserved. 
 ** 
 **  MODIFICATION HISTORY: 
 ** 
 **    $Log: ScriptApplication.java $ 
 **    Revision 1.13  2005/03/01 19:27:51Z  KamleshU 
 **    Change in the inheritance hierarchy 
 **    Revision 1.12  2005/02/04 17:27:29Z  KamleshU 
 **    Changed the paramater to the method which registers the objects on Context 
 **    Revision 1.11  2005/01/26 16:34:24Z  KamleshU 
 **    Revision 1.9  2004/12/21 17:36:25Z  KamleshU 
 **    Made changes to move from BSF to Rhino Scripting 
 **    Revision 1.8  2004/08/11 09:54:37Z  WojciechZ 
 **    better error messages 
 **    Revision 1.7  2004/08/09 08:52:35Z  WojciechZ 
 **    added: Extenrnal Package References 
 **    Revision 1.6  2004/08/02 16:13:11Z  WojciechZ 
 **    New Features: 
 **    - resource locator 
 **    - interaction factory 
 **    - different interface to automatic application (possibility to mix: install predefined aplication and configure it for every call using extended attributes) 
 **    Revision 1.5  2004/07/21 15:52:15Z  WojciechZ 
 **    new feature: SubFlows 
 **    Revision 1.4  2004/05/10 16:24:51Z  WojciechZ 
 **    script application + test 
 **    Revision 1.3  2004/04/13 16:32:51Z  WojciechZ 
 **    work up to 13/04/2004 
 **    Revision 1.2  2004/04/08 16:02:11Z  WojciechZ 
 **    code review 
 **    move to apache xml beans 
 **    xpdl data use xml beans to hold the data 
 **    Revision 1.1  2004/03/26 15:07:48Z  WojciechZ 
 **    Initial revision 
 **    Revision 1.1  2004/03/18 13:01:20Z  WojciechZ 
 **    Initial revision 
 **    Revision 1.0  11-Mar-2004 WojciechZ 
 **    Initial revision 
 ** 
 */
package com.tibco.ie.apps;

import org.apache.log4j.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.tibco.inteng.ExtendedAttribute;
import com.tibco.inteng.apps.AbstractApplication;
import com.tibco.inteng.apps.AutomaticApplication;
import com.tibco.inteng.exceptions.XpdlException;
import com.tibco.inteng.xpdldata.XpdlData;
import com.tibco.inteng.xpdldata.scriptable.ScriptableLogger;
import com.tibco.inteng.xpdldata.scriptable.ScriptableXpdlData;

/**
 * Simple implementation of automatic application. Use extended attributes to 
 * hold source code.<br>
 * 
 * @author WojciechZ
 */
public class ScriptApplication extends AbstractApplication
        implements
            AutomaticApplication {

    /** log4j */
    private final static Logger log = Logger.getLogger(ScriptApplication.class);
    /** 
     * the constructor
     * @param exts
     * @param applicationId
     * 
     * @return
     */
    public String getScript(ExtendedAttribute[] exts, String applicationId) {

        ExtendedAttribute ext = getExtendedAttribute(
        		ServicesConsts.SCRIPT_APP_NAME, exts);
        if (ext == null) {
            XpdlException e = new XpdlException(
                    "There is no 'ScriptApplication' extended attribute in application: '"
                            + applicationId + "'");
            log.error(e.getMessage(), e);
            throw e;
        }
        String script = ext.getContent();
        if (script == null) {
            XpdlException e = new XpdlException(
                    "There is no 'ScriptApplication' extended attribute in application: '"
                            + applicationId + "'");
            log.error(e.getMessage(), e);
            throw e;
        }
        return script;
    }
    /**
     * invoke application
     * @param applicationId
     * @param exts
     * @param args arguments
     */
    public void invoke(String applicationId, ExtendedAttribute[] exts,
            XpdlData[] args) {
        String script = getScript(exts, applicationId);
        if (log.isInfoEnabled()) {
            log.info("execute script: " + script);
        }
        Context cx = Context.enter();
        try {
            Scriptable scope = cx.initStandardObjects(null, false);
            for (int j = 0; j < args.length; j++) {
                String name = args[j].getName();
                ScriptableXpdlData data = new ScriptableXpdlData(args[j],
                        scope, cx);
                scope.put(name, scope, data);
            }
            ScriptableLogger logger = new ScriptableLogger(scope, cx);
            scope.put("log", scope, logger);
            cx.evaluateString(scope, script, "<cmd>", 1, null);
        } catch (Exception e) {
            XpdlException e1 = new XpdlException(
                    "Error when evaluating script in Script Application (ID:"
                            + applicationId + "): " + e.getMessage());
            log.error(e.getMessage(), e);
            throw e1;
        } finally {
            Context.exit();
        }
    }
}