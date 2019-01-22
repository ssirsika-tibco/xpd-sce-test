package com.tibco.bds.designtime.generator.anno;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import com.tibco.bds.designtime.generator.common.BDSConstants;
import com.tibco.bds.shared.anno.JPAAnnotation;
import com.tibco.bds.shared.anno.JPAAnnotationValue;
import com.tibco.bds.shared.anno.JPAAnnotations;
import com.tibco.bds.shared.anno.JpaConstants;
import com.tibco.xpd.bom.gen.biz.GenerationException;
import com.tibco.xpd.bom.xsdtransform.api.XSDUtil;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

/**
 * Contains logic that applies annotations to Ecore elements for the purposes of
 * BDS global data, particularly Teneo/JPA/hibernate schema manipulation.
 * 
 * @author smorgan
 * 
 */
public class AnnotationManager extends JpaConstants {

    // Hibernate mapping datatypes for calendar types
    private static final String HIBERNATE_XSDDATE =
            "com.tibco.bds.core.hibernate.XMLGCDate";

    private static final String HIBERNATE_XSDTIME =
            "com.tibco.bds.core.hibernate.XMLGCTime";

    private static final String HIBERNATE_XSDDATETIME =
            "com.tibco.bds.core.hibernate.XMLGCDateTime";

    // The maximum number of characters for a column name
    private static final int MAX_COLUMN_NAME_LENGTH = 30;

    // The name of the BDS Generator
    private static final String BDS_GENERATOR_NAME = "bdsIdGenerator";

    private static final String BDS_GENERATOR_STRATEGY = "native";

    /**
     * Obtains the value of the given key in the given object's BDS annotation,
     * or null if one does not exist.
     * 
     * @param element
     * @param key
     * @return
     */
    private static Object getBDSAnnotationValue(EModelElement element,
            String key) {

        String value = null;
        EAnnotation annot = element.getEAnnotation(BDSConstants.BDS_ANNOTATION);
        if (annot != null) {
            value = annot.getDetails().get(key);
        }
        return value;
    }

    public static void addBDSIdAnnotations(EStructuralFeature feat) {
        // Add Teneo annotations
        JPAAnnotations annots = new JPAAnnotations();
        JPAAnnotation idAnnot = new JPAAnnotation(TNO_ID);
        annots.add(idAnnot);
        JPAAnnotation genValAnnot = new JPAAnnotation(TNO_GENERATED_VALUE);
        genValAnnot.put(TNO_GENERATED_VALUE_GENERATOR, new JPAAnnotationValue(
                BDS_GENERATOR_NAME));
        annots.add(genValAnnot);
        JPAAnnotation colAnnot = new JPAAnnotation(TNO_COLUMN);
        colAnnot.put(TNO_COLUMN_NAME, new JPAAnnotationValue(
                BDSConstants.BDS_ID_COLNAME));
        // Make sure that the BdsId can not be null
        colAnnot.put(TNO_NULLABLE, new JPAAnnotationValue(BDSConstants.FALSE));
        annots.add(colAnnot);
        addTeneoAnnotation(feat, annots.toString());

        // Add BDS annotation (used at runtime to prevent serialization in XML)
        addAnnotation(feat,
                BDSConstants.BDS_ANNOTATION,
                BDSConstants.BDS_IS_ID,
                BDSConstants.TRUE);
    }

    public static void addBDSVersionAnnotations(EStructuralFeature feat) {
        // Add Teneo annotations
        JPAAnnotations annots = new JPAAnnotations();
        JPAAnnotation verAnnot = new JPAAnnotation(TNO_VERSION);
        annots.add(verAnnot);
        JPAAnnotation colAnnot = new JPAAnnotation(TNO_COLUMN);
        colAnnot.put(TNO_COLUMN_NAME, new JPAAnnotationValue(
                BDSConstants.BDS_VERSION_COLNAME));
        annots.add(colAnnot);
        addTeneoAnnotation(feat, annots.toString());

        // Add BDS annotation (used at runtime to prevent serialization in XML)
        addAnnotation(feat,
                BDSConstants.BDS_ANNOTATION,
                BDSConstants.BDS_IS_VERSION,
                BDSConstants.TRUE);
    }

    public static void addBDSCaseIdAnnotations(EStructuralFeature feat) {
        JPAAnnotations annots = new JPAAnnotations();
        JPAAnnotation colAnnot = new JPAAnnotation(TNO_COLUMN);
        colAnnot.put(TNO_COLUMN_NAME, new JPAAnnotationValue(
                BDSConstants.BDS_OWNER_ID_COLNAME));
        // In an email discussion on 18 January 2013, we decided
        // to allow null values in the database for bdsCaseId.
        // However, the current implementation will always
        // set a value for it at present.
        colAnnot.put(TNO_NULLABLE, new JPAAnnotationValue(BDSConstants.TRUE));
        annots.add(colAnnot);

        addTeneoAnnotation(feat, annots.toString());

        // Add BDS annotation (used at runtime to prevent serialization in XML)
        addAnnotation(feat,
                BDSConstants.BDS_ANNOTATION,
                BDSConstants.BDS_IS_CASE_ID,
                BDSConstants.TRUE);
    }

    /**
     * Add appropriate annotations to the EReferences participating in a
     * containment opposites partnership.
     * 
     * @param sourceRef
     *            the EReference of the parent class
     */
    public static void addContainmentOppositesAnnotations(EReference sourceRef) {
        EReference oppRef = sourceRef.getEOpposite();
        if (oppRef != null) {
            String baseName =
                    String.format("%s_%s", sourceRef.getEContainingClass()
                            .getName().toUpperCase(), sourceRef.getName()
                            .toUpperCase());

            JPAAnnotations annots = new JPAAnnotations();
            // For a to-one containment, we add an annotation to
            // say the source reference is 'mapped by' its opposite.
            if (sourceRef.getUpperBound() == 1) {
                // Add a @OneToOne(mappedBy=) annotation to the source end.
                // This is what makes Teneo have a child-to-parent FK reference
                // instead of parent-to-child.
                JPAAnnotation oneToOneAnno = new JPAAnnotation(TNO_ONE_TO_ONE);
                oneToOneAnno.put(TNO_ONE_TO_ONE_MAPPED_BY,
                        new JPAAnnotationValue(oppRef.getName()));
                annots.add(oneToOneAnno);
            }
            // We are knowingly replacing column name annotations
            // as they have no effect here (column name doesn't appear
            // in hibernate mapping at all)
            if (!annots.keySet().isEmpty()) {
                addTeneoAnnotation(sourceRef, annots.toString());
            }

            // Put a JoinColumn annotations on the opposite reference
            // This determines the name of the DB column
            // representing the child-to-parent reference.
            JPAAnnotations oAnnots = new JPAAnnotations();

            JPAAnnotation ocjAnno = new JPAAnnotation(TNO_JOIN_COLUMN);
            ocjAnno.put(TNO_COLUMN_NAME, new JPAAnnotationValue(baseName
                    + "_BDSID"));
            oAnnots.add(ocjAnno);
            if (!oAnnots.keySet().isEmpty()) {
                addTeneoAnnotation(oppRef, oAnnots.toString());
            }
        }
    }

    /**
     * Adds the global annotations to the top level ePackage
     * 
     * @param pkg
     *            EPackage to add annotations to
     */
    public static void addEPackageAnnotations(EPackage pkg) {

        // Create the top level ePackage Generator annotation
        // This is the name of the generator that is used to create all
        // the different unique IDs - it will be defaulted to use a
        // native database sequence - but will then be overwritten
        // at runtime with whatever strategy we wish
        JPAAnnotations annots = new JPAAnnotations();
        JPAAnnotation genAnnot = new JPAAnnotation(TNO_GENERIC_GENERATOR);
        genAnnot.put(TNO_GENERIC_GENERATOR_NAME, new JPAAnnotationValue(
                BDS_GENERATOR_NAME));
        genAnnot.put(TNO_GENERIC_GENERATOR_STRATEGY, new JPAAnnotationValue(
                BDS_GENERATOR_STRATEGY));
        annots.add(genAnnot);
        addTeneoAnnotation(pkg, annots.toString());
    }

    public static void addCaseClassAnnotation(EClass eClass) {
        addAnnotation(eClass,
                BDSConstants.BDS_ANNOTATION,
                BDSConstants.BDS_IS_CASE,
                BDSConstants.TRUE);
    }

    public static void addCaseClassSummaryAnnotation(EClass eClass, String value) {
        addAnnotation(eClass,
                BDSConstants.BDS_ANNOTATION,
                BDSConstants.BDS_SUMMARY,
                value);
    }

    public static void addGlobalClassAnnotation(EClass eClass) {
        addAnnotation(eClass,
                BDSConstants.BDS_ANNOTATION,
                BDSConstants.BDS_IS_GLOBAL,
                BDSConstants.TRUE);
    }

    public static void addCaseDocumentationAnnotation(EClass eClass) {
        addAnnotation(eClass,
                BDSConstants.BDS_ANNOTATION,
                BDSConstants.BDS_IS_CASE_DOCUMENT,
                BDSConstants.TRUE);
    }

    public static void addCIDAnnotation(EStructuralFeature feat) {
        addAnnotation(feat,
                BDSConstants.BDS_ANNOTATION,
                BDSConstants.BDS_IS_CID,
                BDSConstants.TRUE);
    }

    /**
     * Adds the annotation for an auto generate CID
     * 
     * @param feat
     *            Feature to add the annotation to
     */
    public static void addAutoCIDAnnotation(EStructuralFeature feat) {
        addAnnotation(feat,
                BDSConstants.BDS_ANNOTATION,
                BDSConstants.BDS_AUTO_GENERATED_KEY,
                BDSConstants.TRUE);
    }

    /**
     * Adds the annotation for a composite CID
     * 
     * @param feat
     *            Feature to add the annotation to
     */
    public static void addCompositeCIDAnnotation(EStructuralFeature feat) {
        addAnnotation(feat,
                BDSConstants.BDS_ANNOTATION,
                BDSConstants.BDS_IS_COMPOSITE_CID,
                BDSConstants.TRUE);
    }

    /**
     * Adds the annotation for a search-able attribute
     * 
     * @param feat
     */
    public static void addSearchableAnnotation(EStructuralFeature feat) {
        addAnnotation(feat,
                BDSConstants.BDS_ANNOTATION,
                BDSConstants.BDS_IS_SEARCHABLE,
                BDSConstants.TRUE);
    }

    /**
     * Adds the annotation for a Case State on an attribute
     * 
     * @param feat
     */
    public static void addCaseStateAnnotation(EStructuralFeature feat) {
        addAnnotation(feat,
                BDSConstants.BDS_ANNOTATION,
                BDSConstants.BDS_IS_CASE_STATE,
                BDSConstants.TRUE);
        // Need to also add the search-able annotation as case states are
        // search-able, this differs to how CID's are determined to be
        // search-able, as that is calculated at runtime. I did want to keep it
        // consistent for both CIDs and Case States, but was overruled, so we
        // add it here
        addSearchableAnnotation(feat);
    }

    /**
     * Adds the annotation for a label on an attribute or class
     * 
     * @param feat
     *            Feature to add the label to
     * @param label
     *            Label value to be added
     */
    public static void addLabelAnnotation(ENamedElement feat, String label) {
        addAnnotation(feat,
                BDSConstants.BDS_ANNOTATION,
                BDSConstants.BDS_LABEL,
                label);
    }

    /**
     * Adds the annotation for a description on an attribute or class
     * 
     * @param feat
     *            Feature to add the description to
     * @param desc
     *            Description value to be added
     */
    public static void addDescriptionAnnotation(ENamedElement feat, String desc) {
        addAnnotation(feat,
                BDSConstants.BDS_ANNOTATION,
                BDSConstants.BDS_DESCRIPTION,
                desc);
    }

    /**
     * Adds the annotation for a fetching batch size
     * 
     * @param feat
     *            Feature to add the annotation to
     * @param batchSize
     *            Value of the batch size
     */
    public static void addBatchSizeAnnotation(EStructuralFeature feat,
            Integer batchSize) {
        addAnnotation(feat,
                BDSConstants.BDS_ANNOTATION,
                BDSConstants.BDS_BATCH_SIZE_ANNO,
                batchSize.toString());
    }

    public static void addVersionAnnotation(EPackage pkg, String version) {
        String major = version.split("\\.")[0];
        addAnnotation(pkg,
                BDSConstants.BDS_ANNOTATION,
                BDSConstants.BDS_MAJOR_VERSION,
                major);
    }

    /**
     * Add the eCore version to the package, this is used so that the runtime
     * has an idea which release generated the eCore.
     * 
     * @param pkg
     *            Root package of the eCore
     */
    public static void addEcoreFormatAnnotation(EPackage pkg) {
        addAnnotation(pkg,
                BDSConstants.BDS_ANNOTATION,
                BDSConstants.ECORE_FORMAT,
                BDSConstants.currentEcoreFormat);
    }

    /**
     * Adds the annotation to highlight the name-space tag to be used for this
     * model
     * 
     * @param pkg
     *            Package to add the annotation to
     * @param tag
     *            Value of the tag
     */
    public static void addNamespaceTagAnnotation(EPackage pkg, String tag) {
        addAnnotation(pkg,
                BDSConstants.BDS_ANNOTATION,
                BDSConstants.BDS_NAMESPACE_TAG,
                tag);
    }

    public static void addTeneoAnnotation(ENamedElement elem, String jpaString) {
        addAnnotation(elem,
                AnnotationManager.TENEO_SOURCE,
                AnnotationManager.TENEO_KEY,
                jpaString);
    }

    public static void applyUserAnnotation(ENamedElement elem,
            JPAAnnotations userAnno) {
        if ((userAnno == null) || userAnno.isEmpty()) {
            return;
        }
        addAnnotation(elem,
                AnnotationManager.USER_SOURCE,
                AnnotationManager.USER_KEY,
                userAnno.toString());
    }

    public static void applyBdsAnnotation(ENamedElement elem,
            JPAAnnotations userAnno) {
        if ((userAnno == null) || userAnno.isEmpty()) {
            return;
        }

        // Add each BDS annotation one at a time, we just want to add the
        // name-value pairs, not the entire annotation structure
        for (String userAnnoKey : userAnno.keySet()) {
            JPAAnnotation annotation = userAnno.get(userAnnoKey);
            for (String key : annotation.keySet()) {
                addAnnotation(elem,
                        BDSConstants.BDS_ANNOTATION,
                        key,
                        annotation.get(key).getUnquotedName());
            }
        }
    }

    /**
     * Adds an annotation to the given target object with the given details
     * 
     * @param target
     * @param source
     * @param key
     * @param value
     */
    public static void addAnnotation(EModelElement target, String source,
            String key, String value) {

        // Attempt to locate existing annotation with required source
        EAnnotation annot = null;
        for (Iterator<EAnnotation> iter = target.getEAnnotations().iterator(); annot == null
                && iter.hasNext();) {
            EAnnotation nextAnnot = iter.next();
            if (nextAnnot.getSource().equals(source)) {
                annot = nextAnnot;
            }
        }

        // If no existing annotation, create one
        if (annot == null) {
            annot = EcoreFactory.eINSTANCE.createEAnnotation();
            annot.setSource(source);
            target.getEAnnotations().add(annot);
        }

        // Add the key/value pair to the annotation
        annot.getDetails().put(key, value);
    }

    public static boolean isCaseClass(EClass clazz) {
        // It's a case type if it has the BDS isCase annotation
        Object value = getBDSAnnotationValue(clazz, BDSConstants.BDS_IS_CASE);
        return (value != null && value.equals(BDSConstants.TRUE));
    }

    public static boolean isGlobalClass(EClass clazz) {
        // It's a case type if it has the BDS isGlobal annotation
        Object value = getBDSAnnotationValue(clazz, BDSConstants.BDS_IS_GLOBAL);
        return (value != null && value.equals(BDSConstants.TRUE));
    }

    public static boolean isCID(EAttribute attr) {
        Object cidAnnotValue =
                getBDSAnnotationValue(attr, BDSConstants.BDS_IS_CID);
        return (cidAnnotValue != null && cidAnnotValue
                .equals(BDSConstants.TRUE));
    }

    /**
     * Checks to see if a given Case Identifier is marked as a composite case
     * identifier
     * 
     * @param element
     *            Element to check
     * @return True if a composite, otherwise false
     */
    private static boolean isCompositeCID(EModelElement element) {
        Object value =
                getBDSAnnotationValue(element,
                        BDSConstants.BDS_IS_COMPOSITE_CID);
        return (value != null && value.equals(BDSConstants.TRUE));
    }

    /**
     * Calculates the value for a unique constraint applied to a Table
     * 
     * @param clazz
     *            Class the Table represents
     * @return Unique Constraint value - null if none to set
     * @throws GenerationException
     */
    private static String getTableUniqueConstraintValue(EClass clazz)
            throws GenerationException {
        ArrayList<String> columnNames = new ArrayList<String>();

        // Check each of the BOM Class Attributes to see if any of them
        // are flaged as composite CIDs
        for (EStructuralFeature feat : clazz.getEAllStructuralFeatures()) {
            if (isCompositeCID(feat)) {
                String name = ExtendedMetaData.INSTANCE.getName(feat);
                if ((name != null) && !name.isEmpty()) {
                    columnNames.add("\"" + name.toUpperCase() + "\"");
                }
            }
        }

        String uniqueConstraint = null;

        // If there are any Composite CIDs, put together what will be the
        // value of the Unique Constraint
        if (columnNames.size() > 0) {
            // Get all the column names in a single string separated by a comma
            final String separator = ", ";
            StringBuffer allColumnString = new StringBuffer();
            for (String columnName : columnNames) {
                allColumnString.append(columnName);
                allColumnString.append(separator);
            }
            // Now remove the last separator as there will be one too many
            // and add it to the constraint value
            uniqueConstraint =
                    String.format(TNO_TABLE_UNIQUE_CONSTRAINT_VALUE,
                            allColumnString.substring(0,
                                    allColumnString.length()
                                            - separator.length()));
        }

        return uniqueConstraint;
    }

    /**
     * From what I can make out, this method is supposed to add all the
     * annotations to the eCore for anything that is an EClass this would be
     * things like Classes in a BOM.
     * 
     * -- But I didn't write this method, so I might be wrong, I thought this
     * comment was better than nothing (Which was what it was before) --
     * 
     * @param clazz
     * @return
     * @throws GenerationException
     */
    public static String computeJPAAnnotations(EClass clazz)
            throws GenerationException {

        JPAAnnotations jpaAnnots = new JPAAnnotations();
        if (isCaseClass(clazz) || isGlobalClass(clazz)) {
            IProject project = WorkingCopyUtil.getProjectFor(clazz, true);
            String javaPkg =
                    XSDUtil.getJavaPackageNameFromNamespaceURI(project, clazz
                            .getEPackage().getNsURI());
            JPAAnnotation dvAnnot = new JPAAnnotation(TNO_DISCRIMINATOR_VALUE);
            dvAnnot.put(new JPAAnnotationValue(javaPkg + "." + clazz.getName()));
            jpaAnnots.add(dvAnnot);

            // Add the desired name of the table as well
            // Start with the name-space the table resides in
            String tableName = getNamespaceTableNameLabel(clazz);
            JPAAnnotation tableAnno = new JPAAnnotation(TNO_TABLE);
            // Set the table name, making it upper-case
            tableAnno.put(TNO_TABLE_NAME, new JPAAnnotationValue(tableName));

            // Check to see if there is a composite CID
            String uniqueConstraintValue = getTableUniqueConstraintValue(clazz);
            if ((uniqueConstraintValue != null)
                    && !uniqueConstraintValue.isEmpty()) {
                tableAnno.put(TNO_TABLE_UNIQUE_CONSTRAINT,
                        new JPAAnnotationValue(uniqueConstraintValue, false));
            }

            // Add the table annotation to the annotations
            jpaAnnots.mergeAnnotation(tableAnno);
        } else {
            // Not persistent, so add Transient annotation
            JPAAnnotation trAnnot = new JPAAnnotation(TNO_TRANSIENT);
            jpaAnnots.add(trAnnot);
        }
        return jpaAnnots.toString();
    }

    /**
     * From what I can make out, this method is supposed to add all the
     * annotations to the eCore for anything that is an EAttribute this would be
     * things like Attributes of a Class in a BOM.
     * 
     * -- But I didn't write this method, so I might be wrong, I thought this
     * comment was better than nothing (Which was what it was before) --
     * 
     * @param attr
     * @return
     * @throws GenerationException
     */
    public static String computeJPAAnnotations(EAttribute attr)
            throws GenerationException {

        ExtendedMetaData emd = ExtendedMetaData.INSTANCE;
        XMLTypePackage xmlPkg = XMLTypePackage.eINSTANCE;
        JPAAnnotations jpaAnnots = new JPAAnnotations();

        EDataType type = attr.getEAttributeType();
        EDataType rootType = getRootType(type);

        // Start by adding the column name to the annotations
        // Get to column name that will be used for the annotation
        JPAAnnotation colAnno = getColumnNameAnnotation(attr, TNO_COLUMN);

        if (rootType == xmlPkg.getString()) {
            // String needs a length and long ones require a LOB
            int length = emd.getMaxLengthFacet(type);
            if ((length == -1)
                    || (length > BDSConstants.CASE_DATA_STORE_LOB_AVOIDANCE_THRESHOLD)) {
                // This is an unlimited string or a string that is larger than
                // what we are able to store in a string field in the database,
                // so we need to use a LOB
                jpaAnnots.add(new JPAAnnotation(TNO_LOB));
                // Although we have create a LOB for this data, some databases
                // have several different types of LOB and so we want to make
                // sure we use a reasonable one, so set the size to 1MB
                // More information on this can be read here:
                // http://wiki.eclipse.org/Teneo/Hibernate/ModelRelational/Annotations_Support#Lob_annotation
                colAnno.put(TNO_COLUMN_LENGTH, new JPAAnnotationValue(1048576));
            } else {
                // Check if the length of the string defined in the BOM is less
                // than our default string, if it is then we want to increase
                // the size, this will allow for a little bit of an increase at
                // a later date without the user having to bump the major
                // version of the BOM
                if (length < BDSConstants.CASE_DATA_STORE_DEFAULT_MINIMUM_STRING_LENGTH) {
                    length =
                            BDSConstants.CASE_DATA_STORE_DEFAULT_MINIMUM_STRING_LENGTH;
                }
                colAnno.put(TNO_COLUMN_LENGTH, new JPAAnnotationValue(length));
            }
        } else if (rootType == xmlPkg.getInteger()) {
            // Fixed-length integer needs scale/precision
            // Default the size of the numeric so that it can be increased later
            // if required (In theory we could create it to exactly the right
            // size, but that would prevent it being increased later)
            colAnno.put(TNO_COLUMN_PRECISION,
                    new JPAAnnotationValue(
                            BDSConstants.CASE_DATA_STORE_DEFAULT_MAXIMUM_NUMERIC_PRECISION));
            colAnno.put(TNO_COLUMN_SCALE, new JPAAnnotationValue(0));
        } else if (rootType == xmlPkg.getDecimal()) {
            // Fixed-length decimal needs scale/precision
            int fracDigits = emd.getFractionDigitsFacet(type);

            // Set the size of the decimal to max out the available size, this
            // will allow for future expansion
            colAnno.put(TNO_COLUMN_PRECISION,
                    new JPAAnnotationValue(
                            BDSConstants.CASE_DATA_STORE_DEFAULT_MAXIMUM_NUMERIC_PRECISION));

            // Make sure some-one hasn't specified a scale greater than the
            // the number of digits available
            if (fracDigits > BDSConstants.CASE_DATA_STORE_DEFAULT_MAXIMUM_NUMERIC_PRECISION) {
                fracDigits =
                        BDSConstants.CASE_DATA_STORE_DEFAULT_MAXIMUM_NUMERIC_PRECISION;
            }

            // If the fraction part is unlimited we need to use the default as a
            // value needs to be specified
            if (fracDigits == -1) {
                fracDigits = BDSConstants.CASE_DATA_STORE_DEFAULT_DECIMAL_SCALE;
            }

            colAnno.put(TNO_COLUMN_SCALE, new JPAAnnotationValue(fracDigits));

        } else if (rootType == xmlPkg.getDate()) {
            // A date (without time) needs a Date
            JPAAnnotation typeAnno = new JPAAnnotation(TNO_TYPE);
            typeAnno.put(TNO_TYPE_TYPE, new JPAAnnotationValue(
                    HIBERNATE_XSDDATE));
            jpaAnnots.add(typeAnno);
        } else if (rootType == xmlPkg.getDateTime()) {
            JPAAnnotation typeAnno = new JPAAnnotation(TNO_TYPE);
            typeAnno.put(TNO_TYPE_TYPE, new JPAAnnotationValue(
                    HIBERNATE_XSDDATETIME));
            jpaAnnots.add(typeAnno);
        } else if (rootType == xmlPkg.getTime()) {
            // Use our custom time-only mapping to avoid
            // meaningless (1970-01-01) dates on retrieval
            JPAAnnotation typeAnno = new JPAAnnotation(TNO_TYPE);
            typeAnno.put(TNO_TYPE_TYPE, new JPAAnnotationValue(
                    HIBERNATE_XSDTIME));
            jpaAnnots.add(typeAnno);
        }

        if (isCID(attr)) {
            // Check to see if this is a composite CID, in which case we
            // do not want it to be unique on it's own
            if (!isCompositeCID(attr)) {
                colAnno.put(TNO_COLUMN_UNIQUE, new JPAAnnotationValue(
                        BDSConstants.TRUE));
            }
            colAnno.put(TNO_COLUMN_NULLABLE, new JPAAnnotationValue(
                    BDSConstants.FALSE));
        }

        // Make sure we merge the column annotations that have been populated
        jpaAnnots.mergeAnnotation(colAnno);

        List<JPAAnnotation> multiplicityAnnotations =
                getMultiplicityAnnotation(attr, false);
        for (JPAAnnotation multiplicityAnnotation : multiplicityAnnotations) {
            jpaAnnots.mergeAnnotation(multiplicityAnnotation);
        }

        return jpaAnnots.toString();
    }

    /**
     * Called when an EReference is encountered so that an annotation can be
     * added detailing the name of a column to be used
     * 
     * @param ref
     *            The EReference to use
     * @param isCrossProject
     *            If the feature references something in a different BOM
     * @return The Annotated data
     * @throws GenerationException
     */
    public static String computeJPAAnnotations(EReference ref,
            boolean isCrossProject) throws GenerationException {

        // Get to column name that will be used for the annotation
        JPAAnnotation colAnno = getColumnNameAnnotation(ref, TNO_COLUMN);

        // Make sure we merge the column annotations that have been populated
        JPAAnnotations jpaAnnots = new JPAAnnotations();
        jpaAnnots.add(colAnno);

        // Need to specify the JoinColumn name, unless it is a containment
        // in which case it has already been specified, also only want to
        // put the join column on if there is not some form of multiplicity
        if (!ref.isContainment() && (ref.getUpperBound() == 1)) {
            EReference eOpposite = ref.getEOpposite();
            if ((eOpposite == null)
                    || ((eOpposite != null) && (eOpposite.getUpperBound() == 1))) {
                JPAAnnotation colJoinAnno =
                        getColumnNameAnnotation(ref, TNO_JOIN_COLUMN);
                jpaAnnots.mergeAnnotation(colJoinAnno);
            }
        }

        List<JPAAnnotation> multiplicityAnnotations =
                getMultiplicityAnnotation(ref, isCrossProject);
        for (JPAAnnotation multiplicityAnnotation : multiplicityAnnotations) {
            jpaAnnots.mergeAnnotation(multiplicityAnnotation);
        }

        return jpaAnnots.toString();
    }

    public static void applyTeneoAnnotations(EAttribute attr)
            throws GenerationException {
        String text = computeJPAAnnotations(attr);
        if (text != null && text.length() > 0) {
            addTeneoAnnotation(attr, text);
        }
    }

    public static void applyTeneoAnnotations(EClass clazz)
            throws GenerationException {
        String text = computeJPAAnnotations(clazz);
        if (text != null && text.length() > 0) {
            addTeneoAnnotation(clazz, text);
        }
    }

    public static void applyTeneoAnnotations(EReference ref,
            boolean isCrossProject) throws GenerationException {
        String text = computeJPAAnnotations(ref, isCrossProject);
        if (text != null && text.length() > 0) {
            addTeneoAnnotation(ref, text);
        }
    }

    private static EDataType getRootType(EDataType dataType)
            throws GenerationException {
        // Keep track of what we've seen to avoid an infinite
        // loop given a circular dependency.
        List<EDataType> foundTypes = new ArrayList<EDataType>();
        EDataType superType = dataType;
        do {
            foundTypes.add(superType);
            superType = ExtendedMetaData.INSTANCE.getBaseType(superType);
            if (foundTypes.contains(superType)) {
                throw new GenerationException(
                        "Circular dependency in type hierarchy for " + dataType);
            }
        } while (superType != null);
        // Most recently encountered type is the root
        return foundTypes.get(foundTypes.size() - 1);
    }

    /**
     * Generates a string to represent a table which includes the name-space
     * prefixed to it
     * 
     * @param clazz
     *            EClass to generate the name for
     * @return String representation for the table
     */
    private static String getNamespaceTableNameLabel(EClass clazz) {
        // Get the name-space prefix to use
        String tableName =
                addSeparator(getNamespacePrefix(clazz.getEPackage()));
        tableName += ExtendedMetaData.INSTANCE.getName(clazz);

        return tableName.toUpperCase();
    }

    /**
     * Calculates the name-space prefix
     * 
     * @param ePackage
     *            EPackage to use for the prefix
     * @return Name-space prefix
     */
    private static String getNamespacePrefix(EPackage ePackage) {
        // Get the name-space prefix to use
        String namespace = ePackage.getName();
        if (namespace == null) {
            namespace = "";
        }

        return namespace;
    }

    /**
     * Add the separator to the end of the string
     * 
     * @param src
     *            String to add to
     * @return New value with the separator appended
     */
    private static String addSeparator(String src) {
        if (src == null) {
            return null;
        }
        // Adds the separator to the string
        if (src.length() > 0) {
            src += "_";
        }
        return src;
    }

    /**
     * Method to generate the column name annotation for a given structural
     * feature
     * 
     * @param structFeature
     *            Feature to use for the name
     * @param columnType
     *            Either TNO_COLUMN or TNO_JOIN_COLUMN
     * @return Annotation for the column name
     * @throws GenerationException
     */
    private static JPAAnnotation getColumnNameAnnotation(
            EStructuralFeature structFeature, String columnType)
            throws GenerationException {

        // Start by adding the column name to the annotations
        JPAAnnotation colAnno = new JPAAnnotation(columnType);
        String columnName = ExtendedMetaData.INSTANCE.getName(structFeature);
        if (columnName != null) {
            // Check the length of the column to ensure that it does not exceed
            // the maximum supported for global data, this should have been
            // caught by the validation, but checking just to be sure
            if (columnName.length() > MAX_COLUMN_NAME_LENGTH) {
                throw new GenerationException("Length of attribute name \""
                        + columnName + "\" exceeds maximum of "
                        + MAX_COLUMN_NAME_LENGTH);
            }
            colAnno.put(TNO_COLUMN_NAME,
                    new JPAAnnotationValue(columnName.toUpperCase()));

            // If this entry is not a composite entry then make it null-able
            String isNullable = BDSConstants.TRUE;
            if (structFeature instanceof EModelElement) {
                if (isCompositeCID(structFeature)) {
                    isNullable = BDSConstants.FALSE;
                }
            }
            colAnno.put(TNO_COLUMN_NULLABLE, new JPAAnnotationValue(isNullable));
        }

        return colAnno;
    }

    /**
     * Method to check if a join column annotation is required and if it is to
     * then return the annotation
     * 
     * @param structFeature
     *            Feature to check
     * @param isCrossProject
     *            If the feature references something in a different BOM
     * @return Annotation to add, or null if none to add
     * @throws GenerationException
     */
    private static List<JPAAnnotation> getMultiplicityAnnotation(
            EStructuralFeature structFeature, boolean isCrossProject)
            throws GenerationException {

        List<JPAAnnotation> joinTableAnnos = new ArrayList<JPAAnnotation>();

        // Join Table annotation for only many-to-many
        // references OR many attributes

        if (shouldAddMultiplicityAnnotations(structFeature, isCrossProject)) {

            EClass containingClass = structFeature.getEContainingClass();

            if (containingClass != null) {
                String columnName =
                        ExtendedMetaData.INSTANCE.getName(structFeature);
                if (columnName != null) {

                    // Add Join Table
                    // Get the name used for the table and then add the column
                    // name to it to create the join table name
                    String joinTableName =
                            getNamespaceTableNameLabel(containingClass);
                    joinTableName += "_" + columnName;

                    JPAAnnotation joinTableAnno =
                            new JPAAnnotation(TNO_JOIN_TABLE);
                    joinTableAnno.put(TNO_TABLE_NAME, new JPAAnnotationValue(
                            joinTableName.toUpperCase()));
                    joinTableAnnos.add(joinTableAnno);
                }
            }

        }

        return joinTableAnnos;
    }

    private static boolean shouldAddMultiplicityAnnotations(
            EStructuralFeature structFeature, boolean isCrossProject) {

        boolean result = false;

        if (structFeature instanceof EAttribute) {

            EAttribute attr = (EAttribute) structFeature;
            if (attr.getUpperBound() == -1 || attr.getUpperBound() > 1) {
                result = true;
            }
        }

        if (structFeature instanceof EReference) {
            EReference eRef = (EReference) structFeature;

            if (eRef.isMany()) {
                // If we have a many attribute across projects we need to make
                // sure that we add a Join annotation
                if (isCrossProject) {
                    result = true;
                } else {
                    EReference opposite = eRef.getEOpposite();
                    if (opposite != null) {
                        if ((opposite.getUpperBound() == -1)
                                || opposite.getUpperBound() > 1) {
                            result = true;
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * Adds an BDS annotation the given EAttribute indicating the BOM base type
     * from which its type is derived. e.g. 'Text' or 'Date'.
     * 
     * @param attr
     * @param value
     */
    public static void addBOMBaseTypeAnnotation(EAttribute attr, String value) {
        addAnnotation(attr,
                BDSConstants.BDS_ANNOTATION,
                BDSConstants.BDS_BOM_BASE_TYPE,
                value);
    }
}
