/*
 * Copyright (c) TIBCO Software Inc 2004, 2006. All rights reserved.
 */
package com.tibco.xpd.simulation.compare.editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * Combines multiple XML resource files into a single file and surrounds them
 * all with a new root element.
 * 
 * @author nwilson
 */
public final class XmlCombiner {
    /**
     * Utility class, cannot be instantiated.
     */
    private XmlCombiner() {
    }

    /**
     * @param inputs An array of XML resource files.
     * @param output The combined XML file path.
     * @param documentElement The new root element to use.
     */
    public static void combine(final IResource[] inputs, final IPath output,
            final String documentElement) {
        try {
            DocumentBuilder builder =
                    DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document combined = builder.newDocument();
            Element root = combined.createElement(documentElement);
            combined.appendChild(root);
            for (int i = 0; i < inputs.length; i++) {
                try {
                    String filename = inputs[i].getName();
                    filename = filename.substring(0, filename.lastIndexOf(".")); //$NON-NLS-1$
                    InputStream inputStream =
                            new FileInputStream(inputs[i].getRawLocation()
                                    .toFile());
                    Document individual = builder.parse(inputStream);
                    inputStream.close();
                    Element individualRoot = individual.getDocumentElement();
                    Element imported =
                            (Element) combined.importNode(individualRoot, true);
                    imported.setAttribute("File", filename); //$NON-NLS-1$
                    root.appendChild(imported);
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            File outputFile = output.toFile();
            outputFile.delete();
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            Transformer transformer =
                    TransformerFactory.newInstance().newTransformer();
            Source source = new DOMSource(combined);
            Result result = new StreamResult(outputFile);
            transformer.transform(source, result);
            outputStream.flush();
            outputStream.close();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerFactoryConfigurationError e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
