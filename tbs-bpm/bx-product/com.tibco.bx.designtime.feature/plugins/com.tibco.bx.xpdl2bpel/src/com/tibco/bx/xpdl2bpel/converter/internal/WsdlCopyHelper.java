package com.tibco.bx.xpdl2bpel.converter.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.wsdl.Definition;
import javax.wsdl.Import;
import javax.wsdl.Types;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.UnknownExtensibilityElement;
import javax.wsdl.extensions.schema.Schema;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.wst.wsdl.XSDSchemaExtensibilityElement;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSchemaDirective;
import org.eclipse.xsd.impl.XSDSchemaImpl;
import org.eclipse.xsd.util.XSDParser;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.tibco.bx.xpdl2bpel.ConverterActivator;
import com.tibco.bx.xpdl2bpel.converter.ConversionException;
import com.tibco.bx.xpdl2bpel.util.ConverterUtil;
import com.tibco.bx.xpdl2bpel.util.WSDLUtils;
import com.tibco.xpd.resources.util.ProjectUtil;

public class WsdlCopyHelper {

	private static final String XSD = ".xsd";

	/**
	 * Helper class holding information about the files we need to copy
	 */
	private class XMLObjectInfo {

		public IPath path;
		public Object content;

		/**
		 * Constructor.
		 * 
		 * @param path
		 *            The path of the source file.
		 * @param content
		 *            A representation of the source file (either wsdl or xsd)
		 */
		public XMLObjectInfo(IPath path, Object content) {
			this.path = path; // we want a device independent path
			this.content = content;
		}
	}

	private URI sourceURI = null; // uri of the starting wsdl
	private Definition definition = null; // representation of the starting wsdl

	private URI targetFolderURI = null; // uri of the folder to copy to
	private String targetFilename = null; // optional new filename for the
	// starting wsdl

	private IPath pathPrefix = null; // the shortest common path of all files we
	// need to copy

	private Map<String, XMLObjectInfo> xmlObjectInfos = new LinkedHashMap<String, XMLObjectInfo>();

	/**
	 * copy wsdl to destination
	 * 
	 * @param sourcePath
	 *            : the source file absolute path
	 * @param targetPath
	 *            : the destination folder absolute path
	 * @param targetFileName
	 *            : target file name
	 */
	public WsdlCopyHelper(String sourcePath, String targetPath,
			String targetFileName) {

		this.sourceURI = URI.createFileURI(sourcePath);
		this.targetFolderURI = URI.createFileURI(targetPath);

		this.targetFilename = targetFileName;
	}

	public WsdlCopyHelper(IFile sourceFile, IFolder targetFolder,
			String targetFileName) {
		this(sourceFile.getLocation().toOSString(), targetFolder.getLocation()
				.toOSString(), targetFileName);
	}

	/*
	 * Compare the path prefix with the path in the info object, modify the path
	 * prefix accordingly.
	 */
	private void updatePathPrefix(XMLObjectInfo info) {
		if (pathPrefix == null)
			pathPrefix = info.path.removeLastSegments(1);
		else {
			int matching = pathPrefix.matchingFirstSegments(info.path);

			if (matching < pathPrefix.segmentCount())
				pathPrefix = pathPrefix.uptoSegment(matching);
		}
	}

	/*
	 * Determine if the given URI is a relative URI.
	 */
	private boolean isRelative(String uri) {
		return uri.indexOf(':') == -1;
	}

	/*
	 * Analyze the wsdl document at the given URI, and traverse any relative
	 * files that it imports. Can optionally pass in a parsed Definition if
	 * one's available so we don't have to parse the wsdl again (otherwise just
	 * pass in null).
	 */
	private void analyzeWSDL(URI uri, Definition definition) throws ConversionException, FileNotFoundException {

		// already seen this wsdl, skip
		if (xmlObjectInfos.containsKey(uri.toString()))
			return;

		if (definition == null) {
			definition = WSDLUtils.loadWsdlDefinition(uri);
		}

		// save a reference to the starting wsdl
		if (this.definition == null)
			this.definition = definition;

		IPath path = new Path(uri.toFileString());

		// a target filename was given, so we need to modify the path with the
		// new name
		if (definition == this.definition && targetFilename != null)
			path = path.removeLastSegments(1).append(targetFilename);

		XMLObjectInfo info = new XMLObjectInfo(path, definition);
		xmlObjectInfos.put(uri.toString(), info);
		updatePathPrefix(info);

		// now look at wsdl imports

		for (Iterator<List<Import>> it = definition.getImports().values()
				.iterator(); it.hasNext();) {

			List<Import> list = it.next();

			for (Iterator<Import> listIt = list.iterator(); listIt.hasNext();) {

				Import wsdlImport = listIt.next();
				String wsdlImportLocation = wsdlImport.getLocationURI();

				// analyze any relative imports we find
				if (wsdlImportLocation != null
						&& isRelative(wsdlImportLocation)) {

					// bad form, importing xsd with wsdl:import, but need to
					// handle
					if (wsdlImportLocation.toLowerCase().endsWith(XSD))
						analyzeXSD(URI.createURI(wsdlImportLocation).resolve(
								uri));
					else
						analyzeWSDL(URI.createURI(wsdlImportLocation).resolve(
								uri), null);
				}
			}
		}

		// now look at xsd imports

		Types types = definition.getTypes();

		// there's no wsdl:types, we're done
		if (types == null)
			return;

    	for (Iterator<ExtensibilityElement> it = types.getExtensibilityElements().iterator(); it.hasNext();) {			
			
    		ExtensibilityElement extElement = it.next();    		
    		Element element;
    		
    		// we'll try to parse any UnknownExtensibilityElements and
    		// XSDSchemaExtensibilityElements into an XSD schema    		
    		
    		if (extElement instanceof UnknownExtensibilityElement)
    			element = ((UnknownExtensibilityElement) extElement).getElement();
   			else if (extElement instanceof XSDSchemaExtensibilityElement)
   				element = ((XSDSchemaExtensibilityElement) extElement).getElement();
   			else if (extElement instanceof Schema)
   				element = ((Schema) extElement).getElement();
   			else
   				continue;
    			    			
			try {
				XSDSchema xsdSchema = XSDSchemaImpl.createSchema(element);
				
				// analyze the inlined schema at the current uri
				analyzeXSD(uri, xsdSchema);
			} catch (Exception t) {
				// ignore any extensibility elements that cannot be parsed into a
				// XSDSchema instance
			}
        }
    	
    	addSchemaLocationXsdsToImportedResources(definition);
    	addNoNamespaceSchemaLocationXsdsToImportedResources(definition);
	}

	/*
	 * Analyze the schema at the given URI, traverse its imports and includes.
	 * The schema information is not stored in XMLObjectInfos because it's
	 * either not neccessary (schema inlined in a wsdl) or it has already been
	 * stored.
	 */
	private void analyzeXSD(URI uri, XSDSchema schema)
			throws FileNotFoundException {

		if (schema == null)
			return;

		// look at the imports and includes

		for (Iterator<?> it = schema.getContents().iterator(); it.hasNext();) {

			Object content = it.next();

			if (!(content instanceof XSDSchemaDirective))
				continue;

			XSDSchemaDirective xsdSchemaDirective = (XSDSchemaDirective) content;
			String xsdSchemaDirectiveLocation = xsdSchemaDirective
					.getSchemaLocation();

			// analyze any relative imports and includes we find
			if (xsdSchemaDirectiveLocation != null
					&& isRelative(xsdSchemaDirectiveLocation)) {
				analyzeXSD(URI.createURI(xsdSchemaDirectiveLocation).resolve(
						uri));
			}
		}
	}

	/*
	 * Analyze the schema at the given URI, the schema is parsed and stored in
	 * XMLObjectInfos.
	 */
	private void analyzeXSD(URI uri) throws FileNotFoundException {

		// already seen this xsd, skip it
		if (xmlObjectInfos.containsKey(uri.toString()))
			return;

		XSDSchema xsdSchema = XSDSchemaImpl.getSchemaForSchema(uri.toString());

		// if schema is not cached, parse it
		if (xsdSchema == null) {
			XSDParser p = new XSDParser(null);
			InputStream is = new FileInputStream(uri.toFileString());

			if (is != null) {
				p.parse(is);
				xsdSchema = p.getSchema();
			}
		}

		if (xsdSchema != null) {

			XMLObjectInfo info = new XMLObjectInfo(
					new Path(uri.toFileString()), xsdSchema);
			xmlObjectInfos.put(uri.toString(), info);
			updatePathPrefix(info);

			// analyze its imports and includes
			analyzeXSD(uri, xsdSchema);
		}
	}

    /**
     * add the no namespace XML Schema Instance in the wsdl to imported
     * resources
     * 
     * @param definition
     * @throws FileNotFoundException 
     */
    private void addNoNamespaceSchemaLocationXsdsToImportedResources(Definition definition) throws FileNotFoundException {

        if (definition instanceof org.eclipse.wst.wsdl.Definition) {
            Element element =
                    ((org.eclipse.wst.wsdl.Definition) definition).getElement();
            if (null != element && null != element.getAttributes()) {
                /*
                 * Once the XML Schema Instance namespace is available in the
                 * wsdl we can use the no namespace schemaLocation attribute
                 */
                Node namedItem =
                        element.getAttributes()
                                .getNamedItem("xsi:noNamespaceSchemaLocation"); //$NON-NLS-1$
                /*
                 * This no namespace schema location attribute has one value
                 * that is the location of the XML schema to use
                 */
                if (null != namedItem) {
                    String xsiNoNamespaceSchemaLoc = namedItem.getTextContent();
                    if (null != xsiNoNamespaceSchemaLoc) {
                        String[] noNamespaceSchemaLocations =
                                xsiNoNamespaceSchemaLoc.split(" "); //$NON-NLS-1$
                        for (int i = 0; i < noNamespaceSchemaLocations.length; i++) {
                            String xsdLocation = noNamespaceSchemaLocations[i];
                            if (null != xsdLocation) {
                                /*
                                 * resolving the referenced schema file by
                                 * sending the xsd name extracted from schema
                                 * location attribute
                                 */
                                try {
                                    URI resolve =
                                            resolveReferencedFile(xsdLocation,
                                            		((org.eclipse.wst.wsdl.Definition) definition).eResource().getURI());
                                    if (resolve != null) {
                                    	analyzeXSD(resolve);
                                    }
                                } catch (URISyntaxException e) {
                                    ConverterActivator.logError("Failed to resolve the referenced schema at " + xsdLocation, e);
                                }
                            }
                        }
                    }

                }
            }
        }

    }

    /**
     * * add namespace xml schema instance(s) referred in the wsdl to imported
     * resources
     * 
     * @param definition
     * @throws FileNotFoundException 
     */
    private void addSchemaLocationXsdsToImportedResources(Definition definition) throws FileNotFoundException {
        /*
         * XPD-2956: if xsd schema instance is referred in the wsdl then the xsd
         * for that schema instance was not getting imported
         */
        if (definition instanceof org.eclipse.wst.wsdl.Definition) {
            Element element =
                    ((org.eclipse.wst.wsdl.Definition) definition).getElement();
            if (null != element && null != element.getAttributes()) {
                /*
                 * Once the XML Schema Instance namespace is available in the
                 * wsdl we can use the schemaLocation attribute
                 */
                Node namedItem =
                        element.getAttributes()
                                .getNamedItem("xsi:schemaLocation"); //$NON-NLS-1$
                /*
                 * This schema location attribute has two values, separated by a
                 * space. The first value is the namespace to use. The second
                 * value is the location of the XML schema to use for that
                 * namespace
                 */
                if (null != namedItem) {
                    String xsiSchemaLoc = namedItem.getTextContent();

                    if (null != xsiSchemaLoc) {
                        String[] schemaLocations = xsiSchemaLoc.split(" "); //$NON-NLS-1$

                        for (int i = 1; i < schemaLocations.length;) {
                            String xsdLocation = schemaLocations[i];

                            if (null != xsdLocation) {
                                /*
                                 * resolving the referenced schema file by
                                 * sending the xsd name extracted from schema
                                 * location attribute
                                 */
                                try {
                                    URI resolve =
                                            resolveReferencedFile(xsdLocation,
                                            		((org.eclipse.wst.wsdl.Definition) definition).eResource().getURI());
                                    if (resolve != null) {
                                    	analyzeXSD(resolve);
                                    }
                                } catch (URISyntaxException e) {
                                    ConverterActivator.logError("Failed to resolve the referenced schema at " + xsdLocation, e);
                                }
                            }
                            /*
                             * starting the loop counter 'i' with 1 and
                             * incrementing by 2 because Multiple pairs of URI
                             * references can be listed, each with a different
                             * namespace name part. For instance
                             * 
                             * xsi:schemaLocation= "http://contoso.com/People
                             * http://contoso.com/schemas/people.xsd
                             * http://contoso.com/schemas/Vehicles
                             * http://contoso.com/schemas/vehicles.xsd
                             * http://contoso.com/schemas/People
                             * http://contoso.com/schemas/people.xsd">
                             * 
                             * where "http://contoso.com/People" is the
                             * namespace and
                             * "http://contoso.com/schemas/people.xsd" is the
                             * schema location,
                             * "http://contoso.com/schemas/Vehicles" is the
                             * namespace and
                             * "http://contoso.com/schemas/vehicles.xsd" is the
                             * schema location
                             */
                            i = i + 2;
                        }
                    }
                }
            }
        }
    }

    /**
     * Resolves a relative location
     * 
     * @param schemaLocation
     * @param sourceFileURI
     * @return
     * @throws URISyntaxException
     */
    private static URI resolveReferencedFile(String schemaLocation,
            URI sourceFileURI) throws URISyntaxException {

        if (schemaLocation != null && sourceFileURI != null) {
            URI importedURI = URI.createURI(schemaLocation);
            if (importedURI != null) {
                URI resolve = importedURI.resolve(sourceFileURI);
                return resolve;
            }
        }
        return null;
    }

	/*
	 * Appends a path to a URI and returns the new combined URI in an array of 2
	 * elements, where the first element is in decoded form and the second
	 * element is in encoded form.
	 */
	private String appendPathToURI(URI uriObj, IPath path) {

		String decode = uriObj.toFileString();
		decode = decode.replace('\\', '/');
		if (!decode.endsWith("/")) {
			decode += "/";
		}
		return decode + path.toString();
	}

    private String writeXMLObj(IPath path, IProgressMonitor monitor)
			throws Exception {

		IPath tmp = path.setDevice(null).removeFirstSegments(
				pathPrefix.segmentCount());
		String targetURI = appendPathToURI(targetFolderURI, tmp);

		InputStream is = new FileInputStream(path.toFile());
		OutputStream os = null;
		try {
			StreamSource source = new StreamSource(is);
			/*
			 * If the file is being created in the workspace then use Eclipse
			 * workspace API to do so.
			 */
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			if (root != null) {
				IPath targetPath = new Path(targetURI);
				if (root.getLocation().isPrefixOf(targetPath)) {
					// This is a workspace file
					IFile file = root.getFile(targetPath
							.removeFirstSegments(root.getLocation()
									.segmentCount()));
					writeXMLWorkspaceObj(source, file);
					return targetURI;
				}
			}

			File targetFile = new File(targetURI);
			if (!targetFile.getParentFile().exists()) {
				ConverterUtil.createFolder(targetFile.getParent());
			}
			os = new FileOutputStream(targetURI);

			StreamResult result = new StreamResult(os);

			transform(source, result);
		} finally {
			if (is != null) {
				is.close();
			}

			if (os != null) {
				os.close();
			}
		}

		return targetURI;
	}

	/*
	 * Writes the xsd schema to the file at the given path, relative to the
	 * target folder.
	 */
	private String writeXMLObj(IPath path, XSDSchema xsdSchema,
			IProgressMonitor monitor) throws Exception {

		Element e = xsdSchema.getElement();
		DOMSource domSource = new DOMSource(e);

		IPath tmp = path.setDevice(null).removeFirstSegments(
				pathPrefix.segmentCount());
		String targetURI = appendPathToURI(targetFolderURI, tmp);

		/*
		 * If the file is being created in the workspace then use Eclipse
		 * workspace API to do so.
		 */
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		if (root != null) {
			IPath targetPath = new Path(targetURI);
			if (root.getLocation().isPrefixOf(targetPath)) {
				// This is a workspace file
				IFile file = root.getFile(targetPath.removeFirstSegments(root
						.getLocation().segmentCount()));
				writeXMLWorkspaceObj(domSource, file);
				return targetURI;
			}
		}

		File targetFile = new File(targetURI);
		if (!targetFile.getParentFile().exists()) {
			ConverterUtil.createFolder(targetFile.getParent());
		}
		OutputStream os = new FileOutputStream(targetURI);
		try {
			StreamResult result = new StreamResult(os);
			transform(domSource, result);
		} finally {
			os.close();
		}

		return targetURI;
	}

	/**
	 * Create the target IFile.
	 * 
	 * @param source
	 * @param targetFile
	 * @throws CoreException
	 * @throws TransformerException
	 */
	private void writeXMLWorkspaceObj(Source source, IFile targetFile)
			throws CoreException, TransformerException {
		if (source != null && targetFile != null) {
			if (!targetFile.getParent().exists()) {
				ProjectUtil.createFolder(targetFile.getParent(), false, null);
			}

			ByteArrayOutputStream oStream = new ByteArrayOutputStream();
			StreamResult result = new StreamResult(oStream);
			transform(source, result);

			if (targetFile.exists()) {
				targetFile.setContents(new ByteArrayInputStream(oStream
						.toByteArray()), IResource.KEEP_HISTORY
						| IResource.FORCE, null);
			} else {
				targetFile.create(new ByteArrayInputStream(oStream
						.toByteArray()), true, null);
			}
		}
	}

	/**
	 * Run the transformation.
	 * 
	 * @param source
	 * @param result
	 * @throws TransformerException
	 */
	private void transform(Source source, Result result)
			throws TransformerException {
		Transformer transformer = TransformerFactory.newInstance()
				.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(
				"{http://xml.apache.org/xslt}indent-amount", "2");
		transformer.transform(source, result);
	}

	/*
	 * Compares the 2 uris and see if they point to the same file. We need to
	 * convert both uris to filesystem uris in order to compare.
	 */
	private boolean isSameLocation(String uri1, String uri2) {
		uri1 = uri1.trim();
		uri2 = uri2.trim();

		uri1 = uri1.replace('\\', '/');
		uri2 = uri2.replace('\\', '/');
		return uri1.equals(uri2);
	}

	/**
	 * Executes the copying action.
	 * 
	 * @param monitor
	 *            An optional progress monitor.
	 * @throws CoreException
	 *             Thrown if the copying is unsuccessful. Possible causes
	 *             include: target folder URI was not specified; source URI has
	 *             incorrect format; problem parsing wsdl or xsd documents;
	 *             problem writing to filesystem.
	 */
	public void run(IProgressMonitor monitor) throws Exception {

		xmlObjectInfos.clear();

		// target folder must be set
		if (targetFolderURI == null) {
			throw new CoreException(new Status(IStatus.ERROR,
					ConverterActivator.PLUGIN_ID,
					"A target folder was not specified."));
		}

		try {
			analyzeWSDL(sourceURI, definition);

			// begin writing out files

			Iterator<XMLObjectInfo> iter = xmlObjectInfos.values().iterator();

			while (iter.hasNext()) {
				XMLObjectInfo info = (XMLObjectInfo) iter.next();
				IPath relPath = info.path;

				if (info.content instanceof Definition) {
					Definition definition = (Definition) info.content;

					String targetURI = writeXMLObj(relPath, monitor);

					if (definition == this.definition
							&& isSameLocation(sourceURI.toString(), targetURI))
						return;
				} else
					writeXMLObj(relPath, (XSDSchema) info.content, monitor);
			}
		} catch (Exception t) {
			t.printStackTrace();
		}
	}
}
