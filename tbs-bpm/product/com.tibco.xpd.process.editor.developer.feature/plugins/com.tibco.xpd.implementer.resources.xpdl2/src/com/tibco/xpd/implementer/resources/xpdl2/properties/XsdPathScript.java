/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.implementer.resources.xpdl2.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.tibco.xpd.implementer.resources.xpdl2.Messages;

/**
 * The XsdPathScript class supports the generating and parsing of field mapping
 * scripts. These JavaScript framents containing <i>setField</i> methods.
 * <p>
 * The <i>setField</i> method takes two parameters; the name of the source
 * field and the name of the target field. These fields can either be standard
 * field names or XsdPath names indicating a component in a WSDL message.
 * 
 * <pre>
 *  setField('sourcefield','targetfield');
 * </pre>
 * 
 * @author nwilson
 */
public class XsdPathScript {
    /** A list of source field names. */
    private ArrayList<XsdPathMapping> mappings;

    /**
     * Constructor for an empty script.
     */
    public XsdPathScript() {
        mappings = new ArrayList<XsdPathMapping>();
    }

    /**
     * Constructor to initialise from an existing script.
     * 
     * @param script The script to parse.
     */
    public XsdPathScript(String script) {
        this();
        StringTokenizer lines = new StringTokenizer(script, ";\n"); //$NON-NLS-1$
        while (lines.hasMoreTokens()) {
            String line = lines.nextToken().trim();
            parse(line);
        }
    }

    /**
     * @param line The line to parse.
     */
    private void parse(String line) {
        if (line.startsWith("setField")) { //$NON-NLS-1$
            int b1 = line.indexOf('(');
            int c = line.indexOf(',');
            int b2 = line.indexOf(')');
            if (b1 != -1 && b2 != -1 && c != -1 && b1 < c && c < b2) {
                String f1 = line.substring(b1 + 1, c).trim();
                String f2 = line.substring(c + 1, b2).trim();
                if (f1.startsWith("'") && f1.endsWith("'") //$NON-NLS-1$ //$NON-NLS-2$
                        && f2.startsWith("'") && f2.endsWith("'")) { //$NON-NLS-1$ //$NON-NLS-2$
                    String source = f1.substring(1, f1.length() - 1);
                    String target = f2.substring(1, f2.length() - 1);
                    addMapping(source, target);
                }
            }
        }
    }

    /**
     * @return A list of mappings.
     */
    public List<XsdPathMapping> getMappings() {
        return mappings;
    }

    /**
     * Adds a new mapping to the script.
     * 
     * @param source The source field name.
     * @param target The target field name.
     */
    public synchronized void addMapping(String source, String target) {
        mappings.add(new XsdPathMapping(source, target));
    }

    /**
     * @return The script generated from the mappings.
     */
    public synchronized String getScript() {
        StringBuffer script = new StringBuffer();
        for (XsdPathMapping mapping : mappings) {
            script.append("setField('"); //$NON-NLS-1$
            script.append(mapping.getSource());
            script.append("','"); //$NON-NLS-1$
            script.append(mapping.getTarget());
            script.append("');\n"); //$NON-NLS-1$
        }
        return script.toString();
    }

}
