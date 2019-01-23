package com.tibco.bx.composite.core.it.internal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.wsdl.Definition;
import javax.wsdl.Import;
import javax.wsdl.Types;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.UnknownExtensibilityElement;
import javax.wsdl.extensions.schema.Schema;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.wst.wsdl.XSDSchemaExtensibilityElement;
import org.eclipse.xsd.XSDSchema;
import org.eclipse.xsd.XSDSchemaDirective;
import org.eclipse.xsd.impl.XSDSchemaImpl;
import org.eclipse.xsd.util.XSDParser;
import org.w3c.dom.Element;

import com.tibco.bx.composite.core.BxCompositeCoreActivator;

/**
 * this class used to get all referenced by the wsdl
 * like the wsdls, xsds
 * @author 
 *
 */
public class WsdlDependenciesResolver {

	private static final String XSD = ".xsd";
	private static final String WSDL = ".wsdl";

	private URI sourceURI = null; // uri of the starting wsdl
	private Definition definition = null; // representation of the starting wsdl

	private List<URI> requirements = new ArrayList<URI>();

	private IWorkspaceRoot root;

	/**
	 * copy wsdl to destination
	 * 
	 * @param sourcePath
	 *            : the source file platform path
	 */
	public WsdlDependenciesResolver(String sourcePath) {
		this.sourceURI = URI.createPlatformResourceURI(sourcePath, false);
		root = ResourcesPlugin.getWorkspace().getRoot();
	}

	public WsdlDependenciesResolver(IFile sourceFile) {
		this(sourceFile.getFullPath().toString());
	}

	private Definition loadDefinition(URI uri) throws Exception {
		IFile file = root.getFile(new Path(uri.toPlatformString(true)));
		WSDLFactory factory = WSDLFactory.newInstance();
		WSDLReader reader = factory.newWSDLReader();
		Definition definition = reader
				.readWSDL(file.getLocation().toOSString());
		return definition;
	}

	/*
	 * Analyze the wsdl document at the given URI, and traverse any relative
	 * files that it imports. Can optionally pass in a parsed Definition if
	 * one's available so we don't have to parse the wsdl again (otherwise just
	 * pass in null).
	 */
	private void analyzeWSDL(URI uri, Definition definition) throws Exception {

		// already seen this wsdl, skip
		if (requirements.contains(uri))
			return;

		if (definition == null) {
			definition = loadDefinition(uri);
		}

		// save a reference to the starting wsdl
		if (this.definition == null)
			this.definition = definition;

		requirements.add(uri);

		// now look at wsdl imports

		for (Iterator<List<Import>> it = definition.getImports().values()
				.iterator(); it.hasNext();) {

			List<Import> list = it.next();

			for (Iterator<Import> listIt = list.iterator(); listIt.hasNext();) {

				Import wsdlImport = listIt.next();
				String wsdlImportLocation = wsdlImport.getLocationURI();

				// analyze any relative imports we find
				if (wsdlImportLocation != null) {

					// bad form, importing xsd with wsdl:import, but need to
					// handle
					if (wsdlImportLocation.toLowerCase().endsWith(XSD)){
						analyzeXSD(URI.createURI(wsdlImportLocation).resolve(
								uri));
					}else if(wsdlImportLocation.toLowerCase().endsWith(WSDL)){
						analyzeWSDL(URI.createURI(wsdlImportLocation).resolve(
								uri), null);
					}
				}
			}
		}

		// now look at xsd imports
		Types types = definition.getTypes();

		// there's no wsdl:types, we're done
		if (types == null)
			return;

		for (Iterator<ExtensibilityElement> it = types
				.getExtensibilityElements().iterator(); it.hasNext();) {

			ExtensibilityElement extElement = it.next();
			Element element;

			// we'll try to parse any UnknownExtensibilityElements and
			// XSDSchemaExtensibilityElements into an XSD schema

			if (extElement instanceof Schema)
				element = ((Schema) extElement).getElement();
			else if (extElement instanceof UnknownExtensibilityElement)
				element = ((UnknownExtensibilityElement) extElement)
						.getElement();
			else if (extElement instanceof XSDSchemaExtensibilityElement)
				element = ((XSDSchemaExtensibilityElement) extElement)
						.getElement();
			else
				continue;

			try {
				XSDSchema xsdSchema = XSDSchemaImpl.createSchema(element);

				// analyze the inlined schema at the current uri
				analyzeXSD(uri, xsdSchema);
			} catch (Exception t) {
				BxCompositeCoreActivator.logError("Failed to analyze the schema at " + uri.toString(), t);
			}
		}
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
			if (xsdSchemaDirectiveLocation != null) {
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
		if (requirements.contains(uri))
			return;

		XSDSchema xsdSchema = XSDSchemaImpl.getSchemaForSchema(uri.toString());

		// if schema is not cached, parse it
		if (xsdSchema == null) {
			XSDParser p = new XSDParser(null);
			IFile file = root.getFile(new Path(uri.toPlatformString(true)));
			InputStream is = new FileInputStream(file.getLocation()
					.toOSString());

			if (is != null) {
				p.parse(is);
				xsdSchema = p.getSchema();
			}
		}

		if (xsdSchema != null) {

			requirements.add(uri);

			// analyze its imports and includes
			analyzeXSD(uri, xsdSchema);
		}
	}

	/**
	 * Executes the copying action.
	 * 
	 * @param monitor
	 *            An optional progress monitor.
	 * @return
	 * @throws CoreException
	 *             Thrown if the copying is unsuccessful. Possible causes
	 *             include: target folder URI was not specified; source URI has
	 *             incorrect format; problem parsing wsdl or xsd documents;
	 *             problem writing to filesystem.
	 */
	public List<URI> run(IProgressMonitor monitor) throws Exception {

		requirements.clear();

		String fileName = sourceURI.lastSegment().toLowerCase();
		if(fileName.endsWith(WSDL)){
			analyzeWSDL(sourceURI, definition);
		}else if(fileName.endsWith(XSD)){
			analyzeXSD(sourceURI);
		}

		return requirements;
	}
}
