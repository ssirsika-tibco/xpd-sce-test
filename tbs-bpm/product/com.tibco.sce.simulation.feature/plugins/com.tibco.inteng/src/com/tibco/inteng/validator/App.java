package com.tibco.inteng.validator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
 * The main application entry point. 
 *
 * @author <a href="tstephenson@staffware.com">Tim Stephenson</a>
 *
 */
public class App {
    private AppConfig config;

    /** 
     * Logger for this class. 
     */
    private static Logger logger = Logger.getLogger(App.class); 
    
    /**
     * Default constructor. 
     */
    public App() throws IOException {
        
    }
    

    /**
     * @param config Java Bean used to configure the application. 
     */
    public App(AppConfig newConfig) throws IOException {
        this() ; 
        setConfig(newConfig) ; 
    }

    public static void main(String[] args) {
        try { 
            List msgs = new App(ArgParser.parse(args)).run() ;
            boolean error = false ; 
            for (Iterator it = msgs.iterator() ; it.hasNext() ;) {
            	ValidatorMsg msg = (ValidatorMsg) it.next() ; 
                System.out.println(msg.getThresholdString() 
                        + ": " + msg.getMsg()) ;
                if (msg.getThreshold() >= ValidatorMsg.ERROR_THRESHOLD) {
                    error = true ; 
                }
            }
            if (error) { 
                throw new Exception("Validation Failed") ; 
            }
        } catch (Exception e) {
            System.out.println("Error when invoking: ") ; 
            System.out.print("\t" + App.class.getName()) ;
            for (int i = 0 ; i < args.length ; i++) { 
                System.out.print(" " + args[i]) ;     
            }
            System.out.println() ;
            System.out.println("Error was: ") ;  
            System.out.println("\t" + e.getClass().getName() + ": " 
                    + e.getMessage()) ;
            System.out.println() ;
            ArgParser.usage() ; 
        }
    }

    /**
     * Run the configured validators. 
     */
    public List run() throws IOException {
        
        InputStream xpdlIS = config.getInputStream();
        List msgs = null;
        
        try {
            msgs = new Vector() ; 
            
            for (Iterator it = config.validatorsIterator() ; it.hasNext() ;) {
                Validator v = (Validator) it.next() ;
                msgs.addAll(v.validate(xpdlIS)) ;  
            }
        }
        finally {
            try {if (xpdlIS != null) xpdlIS.close(); }
            catch (Exception e) {
                logger.error("Error closing stream");
            }
        }
        return msgs ; 
    }
    
    /**
     * @return Returns the config.
     */
    public AppConfig getConfig() {
        return config;
    }
    
    /**
     * @param config The config to set.
     */
    public void setConfig(AppConfig config) {
        this.config = config;
    }
}
