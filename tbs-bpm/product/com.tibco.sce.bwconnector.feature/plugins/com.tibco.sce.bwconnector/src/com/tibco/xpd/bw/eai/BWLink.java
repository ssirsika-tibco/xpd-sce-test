package com.tibco.xpd.bw.eai;

import java.io.IOException;
import java.io.StringReader;
import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class BWLink {

    protected static final String SOAP_TO_XML_XSLT =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" //$NON-NLS-1$
                    + "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" " //$NON-NLS-1$
                    + "xmlns:TIBCO=\"http://www.tibco.com/plugin/staffware/ServiceAgentInterfaceDefinition\" " //$NON-NLS-1$
                    + "xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\" " //$NON-NLS-1$
                    + "xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" //$NON-NLS-1$
                    + "<xsl:output method=\"xml\" version=\"1.0\" encoding=\"UTF-8\" indent=\"yes\"/>" //$NON-NLS-1$
                    + "<xsl:template match=\"/\">" //$NON-NLS-1$
                    + "<xsl:copy-of select=\"*/SOAP-ENV:Body/TIBCO:ServiceAgentInterfaceDefinition/wsdlFile/.\">" //$NON-NLS-1$
                    + "</xsl:copy-of>" + "</xsl:template>" //$NON-NLS-1$ //$NON-NLS-2$
                    + "</xsl:stylesheet>"; //$NON-NLS-1$

    protected static String soapRequest =
            "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><SOAP-ENV:Body>" //$NON-NLS-1$
                    + "</SOAP-ENV:Body></SOAP-ENV:Envelope>"; //$NON-NLS-1$

    protected DocumentBuilder docBuilder;

    protected TransformerFactory transformerFactory;

    public BWLink() throws BWLinkException {
        try {
            initialiseXMLFactory();
            initialiseXSLTFactory();
        } catch (ParserConfigurationException e) {
            throw new BWLinkException(Messages.getString("BWLink.XmlFactoryInitialisationError"), e); //$NON-NLS-1$
        }
    }

    public String getInterface(ProviderVO provider, DestinationVO destination)
            throws BWLinkException {

        // Connect to JMS
        Context context = null;

        try {
            context = connectToJMS(provider);
        } catch (NamingException e) {
            throw new BWLinkException("NamingException: "+ 
                    Messages.getString("BWLink.NamingException") //$NON-NLS-1$ //$NON-NLS-2$
                            + e.getLocalizedMessage(), e);
        } catch (Exception e) {
            throw new BWLinkException(String.format(Messages.getString("BWLink.ConnectionFailed"),
                    e.getLocalizedMessage()), e); //$NON-NLS-1$
        }

        if (null == context)
        {
            throw new BWLinkException(Messages.getString("BWLink.JmsContextError")); //$NON-NLS-1$
        }

        // Send Request
        String soapResponse;

        try {
            soapResponse =
                    sendRequest(context, soapRequest, destination, provider,
                            "getWSDL"); //$NON-NLS-1$
        } catch (JMSException e) {
            throw new BWLinkException(Messages.getString("BWLink.BwSendJmsError") //$NON-NLS-1$
                    + e.getLocalizedMessage(), e);
        } catch (NamingException e) {
            throw new BWLinkException(Messages.getString("BWLink.BwSendNamingError") //$NON-NLS-1$
                    + e.getLocalizedMessage(), e);
        }

        if (soapResponse == null) {
            throw new BWLinkException(Messages.getString("BWLink.BWSoapResponseNull")); //$NON-NLS-1$
        }

        String response;

        try {
            response = parseSOAPMessage(soapResponse);
        } catch (ParserConfigurationException e) {
            throw new BWLinkException(Messages.getString("BWLink.BwResponseParserConfigurationError"), e); //$NON-NLS-1$
        } catch (TransformerException e) {
            throw new BWLinkException(Messages.getString("BWLink.BwResponseTransformerError"), e); //$NON-NLS-1$
        } catch (BWLinkException e) {
            throw new BWLinkException(Messages.getString("BWLink.BwResponseBwLinkError"), e); //$NON-NLS-1$
        }

        return response;
    }

    protected Context connectToJMS(ProviderVO providerInfo)
            throws NamingException {

        Hashtable<Object, Object> env = new Hashtable<Object, Object>();

        env.put(Context.INITIAL_CONTEXT_FACTORY, providerInfo.get_factory());
        env.put(Context.PROVIDER_URL, providerInfo.get_url());

        if (providerInfo.get_jndi_user() != null) {
            env.put(Context.SECURITY_PRINCIPAL, providerInfo.get_jndi_user());

            if (providerInfo.get_jndi_password() != null) {
                env.put(Context.SECURITY_CREDENTIALS, providerInfo
                        .get_jndi_password());
            }
        }

        return new InitialContext(env);
    }

    protected String sendRequest(Context context, String soapDocument,
            DestinationVO destinationVO, ProviderVO providerVO,
            String soapAction) throws JMSException, NamingException {

        QueueConnection connection = null;
        QueueSession session = null;

        try {
            QueueConnectionFactory qcf =
                    (QueueConnectionFactory) context.lookup(destinationVO
                            .get_factory());
            Queue sendQueue =
                    (Queue) context.lookup(destinationVO.get_target_name());

            connection =
                    qcf.createQueueConnection(providerVO.get_jndi_user(),
                            providerVO.get_jndi_password());
            session =
                    connection.createQueueSession(false,
                            Session.AUTO_ACKNOWLEDGE);

            Queue receiveQueue = null;

            receiveQueue = session.createTemporaryQueue();

            Message request;

            request = session.createTextMessage(soapDocument);

            request.setStringProperty("SOAPAction", soapAction); //$NON-NLS-1$

            request.setJMSReplyTo(receiveQueue);

            QueueSender sender = session.createSender(sendQueue);

            sender.send(request);

            QueueReceiver receiver = session.createReceiver(receiveQueue);

            connection.start();

            Message response = receiver.receive(50000);

            connection.stop();

            String msg = null;

            if (response != null) {
                msg = ((TextMessage) response).getText();
            }

            return msg;
        } catch (JMSException e) {
            throw e;
        } catch (NamingException e) {
            throw e;
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    // do nothing
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    // do nothing
                }
            }
        }
    }

    protected String parseSOAPMessage(String response)
            throws ParserConfigurationException, TransformerException,
            BWLinkException {
        Document translatedDoc;

        translatedDoc =
                translateDocument(createDocument(new InputSource(
                        new StringReader(response))),
                        createDocument(new InputSource(new StringReader(
                                SOAP_TO_XML_XSLT))), null);

        String message = null;
        try{
            message = translatedDoc.getDocumentElement().getFirstChild()
                .getNodeValue();
        }catch(NullPointerException e){
            throw new BWLinkException(e.getMessage());
        }
        return message;
    }

    /**
     * @param input
     * @return
     * @throws FactoryConfigurationError
     * @throws SWHandlerException
     */
    protected Document createDocument(InputSource input)
            throws FactoryConfigurationError, BWLinkException {
        try {
            Document doc = docBuilder.parse(input);

            return doc;
        } catch (SAXException e) {
            throw new BWLinkException(Messages.getString("BWLink.CreateXmlSaxError"), e); //$NON-NLS-1$
        } catch (IOException e) {
            throw new BWLinkException(Messages.getString("BWLink.CreateXmlIoError"), e); //$NON-NLS-1$
        }
    }

    /**
     * @param input
     * @return
     * @throws FactoryConfigurationError
     * @throws SWHandlerException
     */
    protected Document translateDocument(Document input, Document stylesheet,
            String parameterName) throws ParserConfigurationException,
            TransformerException {
        // Use a Transformer for output
        Document returnDocument = docBuilder.newDocument();

        DOMSource inputSource = new DOMSource(input);
        DOMSource stylesource = new DOMSource(stylesheet);

        Transformer transformer =
                transformerFactory.newTransformer(stylesource);

        if (parameterName != null) {
            transformer.setParameter(parameterName, input.getDocumentElement());
        }

        DOMResult result = new DOMResult(returnDocument);

        transformer.transform(inputSource, result);

        return (returnDocument);
    }

    /**
     * @return
     */
    protected void initialiseXMLFactory() throws ParserConfigurationException {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);

        docBuilder = dbf.newDocumentBuilder();

        transformerFactory = TransformerFactory.newInstance();
    }

    /**
     * @return
     */
    protected void initialiseXSLTFactory() throws ParserConfigurationException {

        transformerFactory = TransformerFactory.newInstance();
    }

    public static String getWsdl(String host, int port, String jndi, String target) throws BWLinkException {
        String url = "tibjmsnaming://" + host + ":" + port; //$NON-NLS-1$ //$NON-NLS-2$
        String providerFactory = "com.tibco.tibjms.naming.TibjmsInitialContextFactory"; //$NON-NLS-1$
        BWLink link = new BWLink();
        ProviderVO providerVO =
                new ProviderVO(null, null, null, url,
                        providerFactory, null, null, null);
        DestinationVO destinationVO =
                new DestinationVO(null, null, null,
                        null, target, null,
                        jndi);
        String wsdl = link.getInterface(providerVO, destinationVO);
        return wsdl;
    }
    
    /**
     * Gets the corresponding wsdl for the provider and destination. Provider has got user and password
     * details filled in. 
     * @see other overloaded method if no need for user and password authentication.
     * @param host
     * @param port
     * @param target
     * @param user
     * @param password
     * @return
     * @throws BWLinkException
     */
    public static String getWsdl(String host, int port, String jndi, String target, String user, String password) throws BWLinkException {
        String url = "tibjmsnaming://" + host + ":" + port; //$NON-NLS-1$ //$NON-NLS-2$
        String providerFactory = "com.tibco.tibjms.naming.TibjmsInitialContextFactory"; //$NON-NLS-1$
        BWLink link = new BWLink();
        ProviderVO providerVO =
                new ProviderVO(null, null, null, url,
                        providerFactory, null, null, null);
        DestinationVO destinationVO =
                new DestinationVO(null, null, null,
                        null, target, null,
                        jndi);
        if (user.trim().length() > 0){
            providerVO.set_jndi_user(user);
        }
        if (password.trim().length() > 0){
            providerVO.set_jndi_password(password);
        }
        
        String wsdl = link.getInterface(providerVO, destinationVO);
        return wsdl;
    }
}
