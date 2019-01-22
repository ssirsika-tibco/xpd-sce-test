/*
 * Copyright (c) TIBCO Software Inc 2004, 2015. All rights reserved.
 */

package com.tibco.xpd.rsd.ui.components;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.uml2.uml.Classifier;

import com.tibco.xpd.resources.util.SpecialFolderUtil;
import com.tibco.xpd.rest.schema.JsonSchemaUtil;
import com.tibco.xpd.rest.schema.ui.RestConstants;
import com.tibco.xpd.rsd.PayloadReference;
import com.tibco.xpd.rsd.RsdFactory;
import com.tibco.xpd.rsd.ui.internal.Messages;

/**
 * Utility class to deal with JSON Schema Type references.
 * 
 * @author jarciuch
 * @since 6 Feb 2015
 */
public class JsonTypeReference {

    /**
     * Default media type for this reference.
     */
    private static final String DEFAULT_MEDIA_TYPE = MediaType.STANDARD_JSON
            .getModelValue();

    /**
     * Default namespace used for this reference.
     */
    public static final String DEFAULT_NAMESPACE =
            "http://xpd.tibco.com/jsd/1.0".toString(); //$NON-NLS-1$

    private final String ref, location, mediaType, namespace;

    /**
     * Creates a JSON payload reference.
     * 
     * @param ref
     *            id of the classifier (root of the JSON schema)
     * @param location
     *            special folder relative location of the json schema file.
     * @param mediaType
     *            media type (default: {@link MediaType#STANDARD_JSON
     *            #getModelValue()}).
     * @param namespace
     *            the namespace (default: {@link #DEFAULT_NAMESPACE})
     */
    public JsonTypeReference(String ref, String location, String mediaType,
            String namespace) {
        this.ref = ref;
        this.location = location;
        this.mediaType = mediaType;
        this.namespace = namespace;
    }

    /**
     * Creates a JSON payload reference.
     * 
     * @param ref
     *            id of the classifier (root of the JSON schema)
     * @param location
     *            special folder relative location of the json schema file.
     */
    public JsonTypeReference(String ref, String location) {
        this(ref, location, DEFAULT_MEDIA_TYPE, DEFAULT_NAMESPACE);
    }

    /**
     * Creates a JSON payload reference from the {@link PayloadReference} model
     * object.
     * 
     * @param payloadRef
     *            the model object representing payload.
     * @return a new instance representing {@link PayloadReference}.
     */
    public static JsonTypeReference getJsonReference(PayloadReference payloadRef) {
        return new JsonTypeReference(payloadRef.getRef(),
                payloadRef.getLocation(), payloadRef.getMediaType(),
                payloadRef.getNamespace());
    }

    /**
     * Creates a JSON payload reference for the {@link Classifier} (usally
     * representing root of JSON schema).
     * 
     * @param classifier
     *            the clasifier.
     * @return a new instance of JSON payload reference for the classfier.
     */
    public static JsonTypeReference getJsonReference(Classifier classifier) {
        Resource resource = classifier.eResource();
        assert resource != null : "The classifier must be contained in a resource."; //$NON-NLS-1$

        String reference = resource.getURIFragment(classifier);
        String location =
                SpecialFolderUtil.getSpecialFolderRelativePath(classifier,
                        RestConstants.REST_SPECIAL_FOLDER_KIND)
                        .toPortableString();
        return new JsonTypeReference(reference, location);
    }

    /**
     * The {@link PayloadReference} for this reference.
     * 
     * @return {@link PayloadReference} for this reference.
     */
    public PayloadReference toPayloadRefernce() {
        PayloadReference r = RsdFactory.eINSTANCE.createPayloadReference();
        r.setRef(ref);
        r.setLocation(location);
        r.setMediaType(mediaType);
        r.setNamespace(namespace);
        return r;
    }

    /**
     * Resolves this reference and returns a classifier of <code>null</code> if
     * the reference can't be resolved.
     * 
     * @param ed
     *            the context editing domain.
     * @param contextProject
     *            the context project for this reference.
     * @return a classifier of <code>null</code> if the reference can't be
     *         resolved.
     */
    public Classifier resolveReference(EditingDomain ed, IProject contextProject) {
        assert ed != null : "Editing domain should not be null."; //$NON-NLS-1$
        boolean useReferencedProjects = true;
        if (location != null) {
            IFile file =
                    SpecialFolderUtil
                            .resolveSpecialFolderRelativePath(contextProject,
                                    RestConstants.REST_SPECIAL_FOLDER_KIND,
                                    location,
                                    useReferencedProjects);

            if (file != null) {
                URI uri =
                        URI.createPlatformResourceURI(file.getFullPath()
                                .toPortableString(),
                                true).appendFragment(ref);
                EObject result = ed.getResourceSet().getEObject(uri, true);
                if (result instanceof Classifier) {
                    return (Classifier) result;
                }
            }
        }
        return null;
    }

    /**
     * Gets a label for this reference.
     * 
     * @param ed
     *            the context editing domain.
     * @param contextProject
     *            the context project for this reference.
     * @return label for this reference.
     */
    public String getLabel(EditingDomain ed, IProject contextProject) {
        if (JsonSchemaUtil.UNPROCESSED_TEXT_PAYLOAD_REFERENCE.equals(ref)) {
            return Messages.JsonRootTypePicker_PayloadLabel;
        }
        Classifier classifier = resolveReference(ed, contextProject);
        if (classifier != null) {
            return classifier.getLabel();
        } else {
            return Messages.JsonTypeReference_UnresolvedRef_label;
        }
    }

    /**
     * @return the ref part. This is the id of the root JSON schema type.
     */
    public String getRef() {
        return ref;
    }

    /**
     * @return the location. Location is the special folder relative path to the
     *         file containing reference.
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return the mediaType the media type used by this reference (usually
     *         "application/json").
     */
    public String getMediaType() {
        return mediaType;
    }

    /**
     * @return the namespace the namespace used for this reference type.
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * @see java.lang.Object#hashCode()
     * 
     * @return
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result =
                prime * result + ((location == null) ? 0 : location.hashCode());
        result =
                prime * result
                        + ((mediaType == null) ? 0 : mediaType.hashCode());
        result =
                prime * result
                        + ((namespace == null) ? 0 : namespace.hashCode());
        result = prime * result + ((ref == null) ? 0 : ref.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * 
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        JsonTypeReference other = (JsonTypeReference) obj;
        if (location == null) {
            if (other.location != null)
                return false;
        } else if (!location.equals(other.location))
            return false;
        if (mediaType == null) {
            if (other.mediaType != null)
                return false;
        } else if (!mediaType.equals(other.mediaType))
            return false;
        if (namespace == null) {
            if (other.namespace != null)
                return false;
        } else if (!namespace.equals(other.namespace))
            return false;
        if (ref == null) {
            if (other.ref != null)
                return false;
        } else if (!ref.equals(other.ref))
            return false;
        return true;
    }
}
