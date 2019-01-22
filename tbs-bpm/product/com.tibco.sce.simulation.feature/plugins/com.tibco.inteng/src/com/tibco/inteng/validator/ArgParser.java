/* 
** 
**  MODULE:             $RCSfile: ArgParser.java $ 
**                      $Revision: 1.2 $ 
**                      $Date: 2004/06/16 08:13:03Z $ 
** 
** DESCRIPTION    :           
**                                              
** 
**  ENVIRONMENT:  Java - Platform independent 
** 
**  COPYRIGHT:    (c) 2004 Staffware plc, All Rights Reserved. 
** 
**  MODIFICATION HISTORY: 
** 
**    $Log: ArgParser.java $ 
**    Revision 1.2  2004/06/16 08:13:03Z  TimSt 
**    added usage info 
**    Revision 1.1  2004/06/15 11:57:32Z  TimSt 
**    Initial revision 
**    Revision 1.0  11-Jun-2004  TimSt 
**    Initial revision 
** 
*/
package com.tibco.inteng.validator;

import java.io.File;
import java.net.URL;

/**
 * Utility class to parse command line parameters into a more paletable form
 * for the main application. 
 * 
 * @author TimSt
 */
public class ArgParser {

    /**
     * @param args Command line arguments. 
     * @return Configuration Bean for the main application. 
     */
    public static AppConfig parse(String[] args) throws Exception {
        AppConfig config = new AppConfig() ;
        
        for (int i = 0 ; i < args.length ; i++) { 
            if (args[i].equals("-u")) {
                config.setUrl(new URL(args[++i])) ; 
            }
            if (args[i].equals("-f")) {
                config.setFile(new File(args[++i])) ; 
            }
        }
        
        if (config.getUrl() == null && config.getFile() == null) {
            throw new IllegalStateException
                    ("Must specify either URL or file to validate") ; 
        }
        
        return config ; 
    }

    /**
     * Display the command line usage for the application. 
     */
    public static void usage() {
        System.out.println("Usage: ") ; 
        System.out.println("\t" + App.class.getName() 
                + " -u <xpdl URL> | -f <XPDL file>") ;  
    }

}
