package com.tibco.xpd.xpdl2.extension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMIException;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.XMLParserPool;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.SAXWrapper;
import org.eclipse.emf.ecore.xmi.impl.XMLLoadImpl;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class LoadExtensions extends XMLLoadImpl {

    public LoadExtensions(XMLHelper helper) {
        super(helper);
    }

    protected DefaultHandler makeDefaultHandler() {
        return new SAXWrapper(
                new SAXParserExtensions(resource, helper, options));
    }

    public void setAnyType(XMLResource res, EObject parent, String content)
            throws IOException {
        this.resource = res;
        is = new ByteArrayInputStream(content.getBytes());
        this.options = res.getDefaultLoadOptions();

        XMLParserPool pool = (XMLParserPool) options
                .get(XMLResource.OPTION_USE_PARSER_POOL);
        Map parserFeatures = (Map) options
                .get(XMLResource.OPTION_PARSER_FEATURES);
        Map parserProperties = (Map) options
                .get(XMLResource.OPTION_PARSER_PROPERTIES);
        parserFeatures = (parserFeatures == null) ? Collections.EMPTY_MAP
                : parserFeatures;
        parserProperties = (parserProperties == null) ? Collections.EMPTY_MAP
                : parserProperties;

        // HACK: reading encoding
        String encoding = getEncoding();
        resource.setEncoding(encoding);

        try {
            SAXParser parser;

            if (pool != null) {
                // use the pool to retrieve the parser
                parser = pool.get(parserFeatures, parserProperties,
                        Boolean.TRUE.equals(options
                                .get(XMLResource.OPTION_USE_LEXICAL_HANDLER)));
            } else {
                parser = makeParser();
                // set features and properties
                if (parserFeatures != null) {
                    for (Iterator i = parserFeatures.keySet().iterator(); i
                            .hasNext();) {
                        String feature = (String) i.next();
                        parser.getXMLReader().setFeature(
                                feature,
                                ((Boolean) parserFeatures.get(feature))
                                        .booleanValue());
                    }
                }
                if (parserProperties != null) {
                    for (Iterator i = parserProperties.keySet().iterator(); i
                            .hasNext();) {
                        String property = (String) i.next();
                        parser.getXMLReader().setProperty(property,
                                parserProperties.get(property));
                    }
                }
            }

            InputSource inputSource = new InputSource(is);
            if (resource.getURI() != null) {
                String resourceURI = resource.getURI().toString();
                inputSource.setPublicId(resourceURI);
                inputSource.setSystemId(resourceURI);
            }

            DefaultHandler defaultHandler = new SAXWrapper(
                    new SAXParserExtensions(resource, helper, options, parent));

            // set lexical handler
            if (options != null
                    && Boolean.TRUE.equals(options
                            .get(XMLResource.OPTION_USE_LEXICAL_HANDLER))) {
                if (parserProperties == null
                        || parserProperties.get(SAX_LEXICAL_PROPERTY) == null) {
                    parser.setProperty(SAX_LEXICAL_PROPERTY, defaultHandler);
                }
            }

            parser.parse(inputSource, defaultHandler);

            // release parser back to the pool
            if (pool != null) {
                pool.release(parser, parserFeatures, parserProperties,
                        Boolean.TRUE.equals(options
                                .get(XMLResource.OPTION_USE_LEXICAL_HANDLER)));
            }

            helper = null;
            if (!resource.getErrors().isEmpty()) {
                Exception error = (Exception) resource.getErrors().get(0);
                if (error instanceof XMIException) {
                    XMIException exception = (XMIException) error;
                    if (exception.getWrappedException() != null) {
                        throw new Resource.IOWrappedException(exception
                                .getWrappedException());
                    }
                }
                throw new Resource.IOWrappedException(error);
            }
        } catch (SAXException exception) {
            if (exception.getException() != null) {
                throw new Resource.IOWrappedException(exception.getException());
            }
            throw new Resource.IOWrappedException(exception);
        } catch (ParserConfigurationException exception) {
            throw new Resource.IOWrappedException(exception);
        }
    }

}
