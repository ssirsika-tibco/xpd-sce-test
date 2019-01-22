/*******************************************************************************
 * Copyright 2006 by TIBCO, Inc.
 * ALL RIGHTS RESERVED
 */
package com.tibco.bx.xpdl2bpel.util.internal;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.bpel.model.extensions.BPELActivityDeserializer;
import org.eclipse.bpel.model.Activity;
import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.ExtensionActivity;
import org.eclipse.bpel.model.Process;
import org.eclipse.bpel.model.resource.BPELReader;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.namespace.QName;
import javax.wsdl.extensions.ExtensionRegistry;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: goldberg
 * Date: Aug 2, 2007
 * Time: 11:31:06 AM
 */

/**
 * This class is used to deserializer the extension activity.
 * It works in conjuction with the EObjectActivitySerializer.
 * It supports the case where an EMF object is used for the activity specific configuration of the extension activity
 */
public class EObjectActivityDeserializer implements BPELActivityDeserializer {

    public Activity unmarshall(QName elementType, Node node, Process process, Map nsMap, ExtensionRegistry extReg, URI uri, BPELReader bpelReader) {
        BPELFactory bpelFactory = BPELFactory.eINSTANCE;
        ExtensionActivity activity = bpelFactory.createExtensionActivity();
        activity.setElement((Element) node);
        return activity;
    }

    static public EObject unmarshall(Node node) {

        DOMSource domSource = new DOMSource(node);
        Transformer serializer = null;

        try {
            serializer = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerFactoryConfigurationError e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            serializer.transform(domSource, new StreamResult(baos));
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        byte[] b = baos.toByteArray();
        XMIResourceImpl res = new XMIResourceImpl();
        res.setEncoding("UTF-8");
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        try {
            res.load(bais, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List contents = res.getContents();
        EObject eobject = (!contents.isEmpty()) ? (EObject) contents.get(0) : null;
        return eobject;

    }

    /**
     * Can convert XML node representing activity specific configuration into byte array
     * @param node XML node of activity specific configuration
     * @return  byte array containing activity configuration
     */
    static public byte[] nodeToByteArray(Node node) {

        DOMSource domSource = new DOMSource(node);
        Transformer serializer = null;

        try {
            serializer = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();   //todo handle exception case
        } catch (TransformerFactoryConfigurationError e) {
            e.printStackTrace();   //todo handle exception case
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            serializer.transform(domSource, new StreamResult(baos));
        } catch (TransformerException e) {
            e.printStackTrace();   //todo handle exception case
        }

        byte[] b = baos.toByteArray();
        return b;
    }

    /**
     * Can convert byte array produced by noteToByteArray method into EMF object
     * Requires that emf object package has been previously registered.
     * @param b byte array to convert
     * @return emf object
     */
    static public EObject byteArrayToObject(byte[] b) {
    	XMIResourceImpl  res = new XMIResourceImpl();
        res.setEncoding("UTF-8");
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        try {
            res.load(bais, null);
        } catch (IOException e) {
            e.printStackTrace();   //todo handle exception case
        }
        List contents = res.getContents();
        EObject eobject = (!contents.isEmpty()) ? (EObject) contents.get(0) : null;
        return eobject;
    }

}
