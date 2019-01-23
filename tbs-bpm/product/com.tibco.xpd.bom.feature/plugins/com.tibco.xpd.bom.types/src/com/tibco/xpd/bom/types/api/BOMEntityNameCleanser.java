package com.tibco.xpd.bom.types.api;

import java.lang.Character.UnicodeBlock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.codegen.util.CodeGenUtil;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;

import com.tibco.xpd.bom.resources.utils.JavaScriptReservedWords;
import com.tibco.xpd.bom.types.PrimitivesUtil;
import com.tibco.xpd.resources.XpdResourcesPlugin;

/**
 * This class exists for two purposes: (1) To provide methods for mapping BOM
 * entity labels to corresponding CDS-safe names. A 'CDS-safe' name is one that
 * obeys Java and EMF-specific naming restrictions and that will not undergo
 * further changes during EMF's XSD->Java transformation. In other words,
 * derived Java entity names will match their BOM entity names. It is expected
 * that these will be called when a user edits a BOM entity label. (2) To allow
 * 'quick-fix' of BOM entity names that have been manually entered by a user,
 * such that they are CDS-friendly. It is expected that these will be called
 * from Resolutions in the CDS BOM validation plug-in. Note that some methods
 * take a flag to determine whether the result should avoid inclusion of Java
 * reserved words. This should generally be set true.
 * 
 */
public class BOMEntityNameCleanser {

    private static final char UNDERSCORE = 0x5F;

    private static final BOMEntityNameCleanser INSTANCE =
            new BOMEntityNameCleanser();

    private Set<String> reservedWords;

    private Set<String> caseInsensitiveReservedWords;

    private Set<String> classEnumOrPackageOnlyCaseInsensitiveReservedWords;

    // HQL reserved words that apply (case insensitively) to the first fragment
    // of a model name.
    // See
    // https://docs.jboss.org/hibernate/orm/3.3/reference/en/html/queryhql.html
    private Set<String> packageFirstFragmentCaseInsensitiveReservedWords =
            new HashSet<String>(Arrays.asList(new String[] { "select", "from",
                    "delete", "update", "where", "order", "group", "by", "asc",
                    "desc", "insert", "into", "not", "as", "fetch", "all",
                    "properties", "join", "inner", "outer", "like", "min",
                    "max", "avg", "sum", "count", "distinct", "is", "and",
                    "or", "between", "empty", "when", "then", "end", "exists",
                    "having" }));

    private final String ENUMLIT_PREFIX = "ENUMLIT"; //$NON-NLS-1$

    public static BOMEntityNameCleanser getInstance() {
        return INSTANCE;
    }

    /**
     * Private constructor. Use {@link #getInstance()} to obtain an instance.
     */
    private BOMEntityNameCleanser() {
        // Create an amalgamated list of reserved words
        reservedWords = new HashSet<String>();
        reservedWords.addAll(CodeGenUtil.getJavaReservedWords());
        reservedWords.addAll(getJavaScriptReservedWords());
        // At the time of writing, all BDS reserved words
        // are case insensitive, given they exist to avoid
        // EMF feature name clashes, and EMF considers two
        // names a clash even when their case differs.
        caseInsensitiveReservedWords = new HashSet<String>();
        for (String word : getBDSReservedWords()) {
            caseInsensitiveReservedWords.add(word.toLowerCase());
        }
        classEnumOrPackageOnlyCaseInsensitiveReservedWords =
                new HashSet<String>();
        // Files/folder in Windows can't be called things like
        // 'com1' or 'LPT2' as those are device names.
        // Anything that determines a folder/filename can't contain these.
        for (String word : getWindowsDeviceNames()) {
            classEnumOrPackageOnlyCaseInsensitiveReservedWords.add(word
                    .toLowerCase());
        }
    }

    private Collection<String> getJavaScriptReservedWords() {
        return JavaScriptReservedWords.getReservedWords();
    }

    private Collection<String> getBDSReservedWords() {
        return Arrays
                .asList(new String[] { "bdsId", "bdsVersion", "bdsOwnerId" });
    }

    private Collection<String> getWindowsDeviceNames() {
        // See
        // http://msdn.microsoft.com/en-us/library/windows/desktop/aa365247%28v=vs.85%29.aspx
        return Arrays.asList(new String[] { "CON", "PRN", "AUX", "NUL", "COM1",
                "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9",
                "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8",
                "LPT9" });
    }

    /**
     * For the given NamedElement, this method derives a CDS-friendly name from
     * the NamedElement's label and assigns it to the NamedElement. It also
     * ensures that the name does not conflict with the element's siblings. This
     * method will have no effect unless the NamedElement is an instance of one
     * of the following: Class, Enumeration, PrimitiveType, Package, Property,
     * EnumerationLiteral.
     * 
     * @param ne
     */
    public void deriveAndSetNamedElementNameFromLabel(NamedElement ne) {
        if (ne instanceof org.eclipse.uml2.uml.Class) {
            deriveAndSetClassNameFromLabel((org.eclipse.uml2.uml.Class) ne);
        } else if (ne instanceof Enumeration) {
            deriveAndSetEnumerationNameFromLabel((Enumeration) ne);
        } else if (ne instanceof PrimitiveType) {
            deriveAndSetPrimitiveTypeNameFromLabel((PrimitiveType) ne);
        } else if (ne instanceof org.eclipse.uml2.uml.Package) {
            deriveAndSetPackageNameFromLabel((org.eclipse.uml2.uml.Package) ne);
        } else if (ne instanceof Property) {
            deriveAndSetAttributeNameFromLabel((Property) ne);
        } else if (ne instanceof EnumerationLiteral) {
            deriveAndSetEnumerationLiteralNameFromLabel((EnumerationLiteral) ne);
        }
    }

    /**
     * For the given NamedElement, this method derives a CDS-friendly name from
     * the NamedElement's label and assigns it to the NamedElement. It also
     * ensure that the name does not conflict with the elements's siblings. This
     * method will return null unless the NamedElement is an instance of one of
     * the following: Class, Enumeration, PrimitiveType, Package, Property,
     * EnumerationLiteral.
     * 
     * @param ne
     */
    public String deriveNamedElementNameFromLabel(NamedElement ne) {
        String result = null;
        if (ne instanceof org.eclipse.uml2.uml.Class) {
            result = deriveClassNameFromLabel((org.eclipse.uml2.uml.Class) ne);
        } else if (ne instanceof Enumeration) {
            result = deriveEnumerationNameFromLabel((Enumeration) ne);
        } else if (ne instanceof PrimitiveType) {
            result = derivePrimitiveTypeNameFromLabel((PrimitiveType) ne);
        } else if (ne instanceof org.eclipse.uml2.uml.Package) {
            result =
                    derivePackageNameFromLabel((org.eclipse.uml2.uml.Package) ne);
        } else if (ne instanceof Property) {
            result = deriveAttributeNameFromLabel((Property) ne);
        } else if (ne instanceof EnumerationLiteral) {
            result =
                    deriveEnumerationLiteralNameFromLabel((EnumerationLiteral) ne);
        }
        return result;
    }

    /**
     * Determines whether the supplied NamedElement is supported by this class's
     * name-cleansing capabilities.
     * 
     * @param element
     * @return
     */
    public boolean isSupportedElement(NamedElement element) {
        boolean isSupported =
                element instanceof org.eclipse.uml2.uml.Class
                        || element instanceof Enumeration
                        || element instanceof PrimitiveType
                        || element instanceof org.eclipse.uml2.uml.Package
                        || element instanceof Property
                        || element instanceof EnumerationLiteral;
        return isSupported;
    }

    /**
     * Cleanses the supplied name such that it can be applied to the supplied
     * NamedElement guaranteeing that it is both 'CDS-safe' and unique among the
     * NamedElement's siblings. This method will return null unless the
     * NamedElement is an instance of a supported class. Use
     * {@link #isSupportedElement(NamedElement)} to check first.
     * 
     * @param ne
     * @param name
     * @return
     */
    public String cleanseAndMakeUniqueNamedElementName(NamedElement ne,
            String name) {
        String result = null;
        if (ne instanceof org.eclipse.uml2.uml.Class) {
            result =
                    cleanseAndMakeUniqueClassName((org.eclipse.uml2.uml.Class) ne,
                            name,
                            true);
        } else if (ne instanceof Enumeration) {
            result =
                    cleanseAndMakeUniqueEnumerationName((Enumeration) ne,
                            name,
                            true);
        } else if (ne instanceof PrimitiveType) {
            result =
                    cleanseAndMakeUniquePrimitiveTypeName((PrimitiveType) ne,
                            name,
                            true);
        } else if (ne instanceof org.eclipse.uml2.uml.Package) {
            result =
                    cleanseAndMakeUniquePackageName((org.eclipse.uml2.uml.Package) ne,
                            name,
                            true);
        } else if (ne instanceof Property) {
            result =
                    cleanseAndMakeUniqueAttributeName((Property) ne, name, true);
        } else if (ne instanceof EnumerationLiteral) {
            result =
                    cleanseAndMakeUniqueEnumerationLiteralName((EnumerationLiteral) ne,
                            name);
        } else {
            // This shouldn't happen as the caller should have called
            // isSupportedElement() first, but just in case, return the existing
            // name to avoid a null pointer.
            result = name;
        }
        return result;
    }

    /**
     * Cleanses the supplied name such that it can be applied to the supplied
     * NamedElement guaranteeing that it is 'CDS-safe'. This method will return
     * null unless the NamedElement is an instance of a supported class. Use
     * {@link #isSupportedElement(NamedElement)} to check first.
     * 
     * @param ne
     * @param name
     * @return
     */
    public String cleanseNamedElementName(NamedElement ne, String name) {
        String result = null;
        if (ne instanceof org.eclipse.uml2.uml.Class) {
            result = cleanseClassName(name, true);
        } else if (ne instanceof Enumeration) {
            result = cleanseEnumerationName(name, true);
        } else if (ne instanceof PrimitiveType) {
            result = cleansePrimitiveTypeName(name, true);
        } else if (ne instanceof org.eclipse.uml2.uml.Package) {
            result =
                    cleansePackageName(name,
                            true,
                            !(ne instanceof Model),
                            ne instanceof Model);
        } else if (ne instanceof Property) {
            result = cleanseAttributeName(name, true);
        } else if (ne instanceof EnumerationLiteral) {
            result = cleanseEnumerationLiteralName(name);
        } else {
            // This shouldn't happen as the caller should have called
            // isSupportedElement() first, but just in case, return the existing
            // name to avoid a null pointer.
            result = name;
        }
        return result;
    }

    /**
     * For the given Class, this method derives a CDS-friendly name from the
     * Class's label and assigns it to the Class. It also ensures the name does
     * not clash with other Classifier names in the same package.
     * 
     * @param clazz
     */
    public void deriveAndSetClassNameFromLabel(org.eclipse.uml2.uml.Class clazz) {
        String name = deriveClassNameFromLabel(clazz);
        setName(clazz, name);
    }

    /**
     * For the given Class, this method derives a CDS-friendly name from the
     * Class's label. It also ensures the name does not clash with other
     * Classifier names in the same package.
     * 
     * @param clazz
     */
    public String deriveClassNameFromLabel(org.eclipse.uml2.uml.Class clazz) {
        String label = PrimitivesUtil.getDisplayLabel(clazz, true);
        return cleanseAndMakeUniqueClassName(clazz, label, true);
    }

    /**
     * Cleanses the supplied name such that can be applied to the supplied Class
     * guaranteeing that it is both 'CDS-safe' and unique among the Class's
     * siblings.
     * 
     * @param clazz
     * @param name
     * @return
     */
    public String cleanseAndMakeUniqueClassName(
            org.eclipse.uml2.uml.Class clazz, String name,
            boolean suffixReservedWord) {
        String cleansed = cleanseClassName(name, suffixReservedWord);
        String unique = makeClassifierNameUniqueWithinPackage(clazz, cleansed);
        return unique;
    }

    /**
     * For the given Enumeration, this method derives a CDS-friendly name from
     * the Enumeration's label and assigns it to the Enumeration. It also
     * ensures the name does not clash with other Classifier names in the same
     * package.
     * 
     * @param enu
     */
    public void deriveAndSetEnumerationNameFromLabel(Enumeration enu) {
        String name = deriveEnumerationNameFromLabel(enu);
        setName(enu, name);
    }

    /**
     * For the given Enumeration, this method derives a CDS-friendly name from
     * the Enumeration's label. It also ensures the name does not clash with
     * other Classifier names in the same package.
     * 
     * @param enu
     */
    public String deriveEnumerationNameFromLabel(Enumeration enu) {
        String label = PrimitivesUtil.getDisplayLabel(enu, true);
        return cleanseAndMakeUniqueEnumerationName(enu, label, true);
    }

    /**
     * Cleanses the supplied name such that it can be applied to the supplied
     * Enumeration guaranteeing that it is both 'CDS-safe' and unique among the
     * Enumeration's siblings.
     * 
     * @param enu
     * @param name
     * @return
     */
    public String cleanseAndMakeUniqueEnumerationName(Enumeration enu,
            String name, boolean suffixReservedWord) {
        String cleansed = cleanseEnumerationName(name, suffixReservedWord);
        String unique = makeClassifierNameUniqueWithinPackage(enu, cleansed);
        return unique;
    }

    /**
     * For the given PrimitiveType, this method derives a CDS-friendly name from
     * the PrimitiveType label and assigns it to the PrimitiveType. It also
     * ensures the name does not clash with other Classifier names in the same
     * package.
     * 
     * @param pt
     */
    public void deriveAndSetPrimitiveTypeNameFromLabel(PrimitiveType pt) {
        String name = derivePrimitiveTypeNameFromLabel(pt);
        setName(pt, name);
    }

    /**
     * For the given PrimitiveType, this method derives a CDS-friendly name from
     * the PrimitiveType label. It also ensures the name does not clash with
     * other Classifier names in the same package.
     * 
     * @param pt
     */
    public String derivePrimitiveTypeNameFromLabel(PrimitiveType pt) {
        String label = PrimitivesUtil.getDisplayLabel(pt, true);
        return cleanseAndMakeUniquePrimitiveTypeName(pt, label, true);
    }

    /**
     * Cleanses the supplied name such that it can be applied to the supplied
     * PrimitiveType guaranteeing that it is 'CDS-safe' and unique among the
     * PrimitiveType's siblings.
     * 
     * @param pt
     * @param name
     * @return
     */
    public String cleanseAndMakeUniquePrimitiveTypeName(PrimitiveType pt,
            String name, boolean suffixReservedWord) {
        String cleansed = cleansePrimitiveTypeName(name, true);
        String unique = makeClassifierNameUniqueWithinPackage(pt, cleansed);
        return unique;
    }

    /**
     * For the given Package (or Model), this method derives a CDS-friendly name
     * from the Package's label and assigns it to the Package. It also ensures
     * that the name does not clash with sibling Packages.
     * 
     * @param pkg
     */
    public void deriveAndSetPackageNameFromLabel(
            org.eclipse.uml2.uml.Package pkg) {
        String name = derivePackageNameFromLabel(pkg);
        setName(pkg, name);
    }

    /**
     * For the given Package (or Model), this method derives a CDS-friendly name
     * from the Package's label. It also ensures that the name does not clash
     * with sibling Packages.
     * 
     * @param pkg
     */
    public String derivePackageNameFromLabel(org.eclipse.uml2.uml.Package pkg) {
        String label = PrimitivesUtil.getDisplayLabel(pkg, true);
        return cleanseAndMakeUniquePackageName(pkg, label, true);
    }

    /**
     * Cleanses the supplied name such that it can be applied to the supplied
     * Package guaranteeing that it is both 'CDS-safe' and unique among the
     * Package's siblings. Optionally suffixes Java reserved words that would
     * otherwise be illegal.
     * 
     * @param pkg
     * @param name
     * @param suffixReservedWord
     * @return
     */
    public String cleanseAndMakeUniquePackageName(
            org.eclipse.uml2.uml.Package pkg, String name,
            boolean suffixReservedWord) {
        // For non-model packages (i.e. sub-packages), we
        // are disallowing dots in names (see XPD-453)
        String cleansed =
                cleansePackageName(name,
                        suffixReservedWord,
                        !(pkg instanceof Model),
                        pkg instanceof Model);
        String unique = makePackageNameUniqueWithinPackage(pkg, cleansed);
        return unique;
    }

    /**
     * For the given Property, this method derives a CDS-friendly name from the
     * Property's label and assigns it to the Property. It also ensures that the
     * name does not clash with other Properties of the same Class.
     * 
     * @param attr
     */
    public void deriveAndSetAttributeNameFromLabel(Property attr) {
        String name = deriveAttributeNameFromLabel(attr);
        setName(attr, name);
    }

    /**
     * For the given Property, this method derives a CDS-friendly name from the
     * Property's label. It also ensures that the name does not clash with other
     * Properties of the same Class.
     * 
     * @param attr
     */
    public String deriveAttributeNameFromLabel(Property attr) {
        String label = PrimitivesUtil.getDisplayLabel(attr, true);
        return cleanseAndMakeUniqueAttributeName(attr, label, true);
    }

    /**
     * Cleanses the supplied name such that it can be applied to the supplied
     * Property guaranteeing that it is 'CDS-safe' and unique among the
     * Property's siblings. Optionally suffixes Java reserved words that would
     * otherwise form illegal names.
     * 
     * @param attr
     * @param name
     * @param suffixReservedWord
     * @return
     */
    public String cleanseAndMakeUniqueAttributeName(Property attr, String name,
            boolean suffixReservedWord) {
        String cleansed = cleanseAttributeName(name, suffixReservedWord);
        org.eclipse.uml2.uml.Class clazz = attr.getClass_();
        List<String> existingNames = getAttributeNamesFromClass(clazz, attr);
        String unique = makeNameUnique(existingNames, cleansed);
        return unique;

    }

    /**
     * Cleanses the supplied name such that it can be applied to the supplied
     * Property guaranteeing that it is 'CDS-safe' and unique among the supplied
     * classes. Optionally suffixes Java reserved words that would otherwise
     * form illegal names.
     * 
     * @param attr
     * @param name
     * @param suffixReservedWord
     * @param classes
     * @return
     */
    public String cleanseAndMakeUniqueAttributeName(Property attr, String name,
            boolean suffixReservedWord, List<org.eclipse.uml2.uml.Class> classes) {
        String cleansed = cleanseAttributeName(name, suffixReservedWord);

        List<String> existingNames = new ArrayList<String>();
        for (org.eclipse.uml2.uml.Class aClass : classes) {
            existingNames.addAll(getAttributeNamesFromClass(aClass, attr));
        }
        String unique = makeNameUnique(existingNames, cleansed);
        return unique;
    }

    /**
     * For the given EnumerationLiteral, this method derives a CDS-friendly name
     * from the EnumerationLiteral's label and assigns it to the
     * EnumerationLiteral. It also ensure that the name does not clash with
     * other EnumerationLiterals in the same Enumeration.
     * 
     * @param lit
     */
    public void deriveAndSetEnumerationLiteralNameFromLabel(
            EnumerationLiteral lit) {
        String name = deriveEnumerationLiteralNameFromLabel(lit);
        setName(lit, name);
    }

    /**
     * For the given EnumerationLiteral, this method derives a CDS-friendly name
     * from the EnumerationLiteral's label. It also ensure that the name does
     * not clash with other EnumerationLiterals in the same Enumeration.
     * 
     * @param lit
     */
    public String deriveEnumerationLiteralNameFromLabel(EnumerationLiteral lit) {
        String label = PrimitivesUtil.getDisplayLabel(lit, true);
        return cleanseAndMakeUniqueEnumerationLiteralName(lit, label);
    }

    /**
     * Cleanses the supplied name such that it can be applied to the supplied
     * EnumerationLiteral guaranteeing that it is both 'CDS-safe' and unique
     * among the literal's siblings.
     * 
     * @param lit
     * @param name
     * @return
     */
    public String cleanseAndMakeUniqueEnumerationLiteralName(
            EnumerationLiteral lit, String name) {
        String cleansed = cleanseEnumerationLiteralName(name);
        Enumeration enu = lit.getEnumeration();
        List<String> existingNames =
                getEnumerationLiteralNamesFromEnumeration(enu, lit);
        String unique = makeNameUnique(existingNames, cleansed);
        return unique;
    }

    /**
     * Determines whether the supplied String is a legal Java identifier in
     * terms of the characters it contains. It does NOT detect Java reserved
     * words.
     * 
     * @param name
     * @return
     */
    public boolean isLegalJavaIdentifier(String name) {
        boolean valid =
                name != null && name.length() > 0
                        && Character.isJavaIdentifierStart(name.charAt(0));
        if (valid) {
            for (int i = 1; i < name.length() && valid; i++) {
                valid = Character.isJavaIdentifierPart(name.charAt(i));
            }
        }
        return valid;
    }

    /**
     * Determines whether the supplied String is a reserved word (whether Java,
     * JavaScript or BDS, etc)
     * 
     * @param name
     * @return
     */
    public boolean isReservedWord(String name) {
        return reservedWords.contains(name)
                || caseInsensitiveReservedWords.contains(name.toLowerCase());
    }

    public boolean isClassEnumOrPackageOnlyReservedWord(String name) {
        return classEnumOrPackageOnlyCaseInsensitiveReservedWords.contains(name
                .toLowerCase());
    }

    protected String removeIllegalJavaCharacters(String name) {
        StringBuilder buf = new StringBuilder();
        int i = 0;
        // Search through the string until we find a character
        // that is legal at the START of a Java identifier
        while (i < name.length() && buf.length() == 0) {
            if (Character.isJavaIdentifierStart(name.charAt(i))) {
                buf.append(name.charAt(i));
            }
            i++;
        }

        // Chomp through the remainder of the name, retaining
        // only those characters that are Java-legal
        while (i < name.length()) {
            if (Character.isJavaIdentifierPart(name.charAt(i))) {
                buf.append(name.charAt(i));
            }
            i++;
        }
        return buf.toString();
    }

    /**
     * Removes disallowed Unicode characters. At the time of writing, the script
     * editor considers a character such as 'e with acute' (0xE9) illegal. I
     * suspect this will be revisited in the future (Java, EMF, etc can
     * theoretically support extended Latin and beyond: Japanese, Chinese....)
     * 
     * @param name
     * @return
     */
    protected String removeNonLatinCharacters(String name) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            UnicodeBlock block = UnicodeBlock.of(c);
            if (block == UnicodeBlock.BASIC_LATIN) {
                buf.append(c);
            }
        }
        return buf.toString();
    }

    /**
     * Cleanses the supplied Package name such that is 'CDS-safe'. Results may
     * contain (illegal) Java reserved words unless the suffixReservedWord
     * argument is set true. 1) The root package (model) name becomes part of
     * the bundle name. The rule for bundle names is: Legal characters are A-Z
     * a-z 0-9 . _ - 2) Furthermore, each dot-separated segment must be a legal
     * Java identifier. i.e. It must be non-zero length, start with a letter and
     * not be a reserved word or contain hyphens. 3) If the removeDots argument
     * is set, dots are stripped form the name; At the time of writing, this is
     * the case for sub-packages, but not for the root package (Model) - see
     * XPD-453
     * 
     * Combining these constraints means that names must be a dot-separated set
     * of segments, each beginning with a lower-case letter and containing only
     * lower-case letters and numbers, avoiding Java reserved words.
     * 
     * @param name
     * @param suffixReservedWord
     * @param removeDots
     * @return
     */
    public String cleansePackageName(String name, boolean suffixReservedWord,
            boolean removeDots, boolean isModelName) {
        String[] nameFragments;
        if (removeDots) {
            // Single dotless fragment
            nameFragments = new String[] { name.replaceAll("\\.", "") };
        } else {
            // Split into fragments at dots
            nameFragments = name.split("\\.");
        }
        StringBuilder buf = new StringBuilder();
        for (int fragmentIdx = 0; fragmentIdx < nameFragments.length; fragmentIdx++) {
            String fragment = nameFragments[fragmentIdx];
            // Remove non-Latin characters
            fragment = removeNonLatinCharacters(fragment);
            StringBuilder fragBuf = new StringBuilder();
            int i = 0;
            // Find a letter or underscore to start with
            while (i < fragment.length() && fragBuf.length() == 0) {
                char ch = fragment.charAt(i);
                // And append, if it's an underscore or letter
                if (ch == UNDERSCORE || Character.isLetter(ch)) {
                    fragBuf.append(ch);
                }
                i++;
            }

            // Append remaining letters, numbers and underscores
            // in the fragment
            while (i < fragment.length()) {
                char ch = fragment.charAt(i);
                // and append it, as long as it's a letter, digit
                // or underscore
                if (ch == UNDERSCORE || Character.isLetterOrDigit(ch)) {
                    fragBuf.append(ch);
                }
                i++;
            }
            String fragBufString = fragBuf.toString();
            if (suffixReservedWord
                    && (isReservedWord(fragBufString))
                    || isClassEnumOrPackageOnlyReservedWord(fragBufString)
                    || (fragmentIdx == 0 && packageFirstFragmentCaseInsensitiveReservedWords
                            .contains(fragBufString.toLowerCase()))) {
                fragBuf.append("1");
            }
            // As long as cleansing this fragment hasn't reduced it to
            // nothing...
            if (fragBuf.length() != 0) {
                // If fragments are already in the buffer, add a dot
                // before we add this one.
                if (buf.length() != 0) {
                    buf.append(".");
                }
                // Add the cleansed fragment to our result
                buf.append(fragBuf);
            }
        }
        return buf.toString();
    }

    /**
     * Cleanses the supplied Classifier name such that it is 'CDS-safe'
     * 
     * @param name
     * @return
     */
    protected String cleanseClassifierName(String name,
            boolean suffixReservedWord) {
        // Remove Java-illegal chars
        name = removeIllegalJavaCharacters(name);
        // Remove non-Latin characters
        name = removeNonLatinCharacters(name);
        // Enforce CDS-specific rules:
        // - name must start with letter or underscore
        // - remainder must be letters, underscores and numbers
        StringBuilder buf = new StringBuilder();
        int i = 0;

        // Find first character
        while (i < name.length() && buf.length() == 0) {
            char c = name.charAt(i);
            if (Character.isLetter(c) || c == UNDERSCORE) {
                buf.append(c);
            }
            i++;
        }

        // For the second character, ensure it's a letter, number or underscore.
        while (i < name.length()) {
            char c = name.charAt(i);
            if (Character.isLetterOrDigit(c) || c == UNDERSCORE) {
                buf.append(c);
            }
            i++;
        }

        // If result clashes with a reserved word, append a '1'.
        String result = buf.toString();
        if (suffixReservedWord && (isReservedWord(result))) {
            result += "1";
        }
        return result;
    }

    /**
     * Cleanses the supplied Class name such that it is 'CDS-safe'. Optionally
     * suffixes (otherwise-illegal) Java reserved words.
     * 
     * @param name
     * @param suffixReservedWord
     * @return
     */
    public String cleanseClassName(String name, boolean suffixReservedWord) {
        String result = cleanseClassifierName(name, suffixReservedWord);
        if (suffixReservedWord && isClassEnumOrPackageOnlyReservedWord(result)) {
            result += "1";
        }
        return result;
    }

    /**
     * Cleanses the supplied Attribute name such that it is 'CDS-safe'.
     * Optionally suffixes (otherwise-illegal) Java reserved words.
     * 
     * @param name
     * @param suffixReservedWord
     * @return
     */
    public String cleanseAttributeName(String name, boolean suffixReservedWord) {
        return cleanseAttributeName(name, suffixReservedWord, false);
    }

    public String cleanseAttributeName(String name, boolean suffixReservedWord,
            boolean skipBX1439Fix) {
        // Remove Java-illegal chars
        name = removeIllegalJavaCharacters(name);
        // Remove non-Latin characters
        name = removeNonLatinCharacters(name);
        // Enforce CDS-specific rules:
        // - name must start with letter or underscore
        // - remainder must be letters, underscores and numbers
        StringBuilder buf = new StringBuilder();
        int i = 0;

        // Find first character
        while (i < name.length() && buf.length() == 0) {
            char c = name.charAt(i);
            if (Character.isLetter(c) || c == UNDERSCORE) {
                buf.append(c);
            }
            i++;
        }

        // For the second character, ensure it's a letter, number or underscore.
        while (i < name.length()) {
            char c = name.charAt(i);
            if (Character.isLetterOrDigit(c) || c == UNDERSCORE) {
                buf.append(c);
            }
            i++;
        }

        // BX-1439 work-around. If the first two characters are
        // letters of opposite case, make them both lower-case.
        if ((!skipBX1439Fix) && name.length() >= 2) {
            if (Character.isUpperCase(buf.charAt(0))
                    && Character.isLowerCase(buf.charAt(1))) {
                buf.setCharAt(0, Character.toLowerCase(buf.charAt(0)));
            } else if (Character.isLowerCase(buf.charAt(0))
                    && Character.isUpperCase(buf.charAt(1))) {
                buf.setCharAt(1, Character.toLowerCase(buf.charAt(1)));
            }
        }

        // If result clashes with a reserved word, append a '1'.
        String result = buf.toString();
        if (suffixReservedWord && isReservedWord(result)) {
            result += "1";
        }
        return result;
    }

    protected String makePackageNameUniqueWithinPackage(
            org.eclipse.uml2.uml.Package pkg, String name) {
        org.eclipse.uml2.uml.Package parentPkg = pkg.getNestingPackage();
        if (parentPkg == null) {
            return name;
        } else {
            List<String> existingNames = new ArrayList<String>();
            for (org.eclipse.uml2.uml.Package aPkg : parentPkg
                    .getNestedPackages()) {
                if (aPkg != pkg) {
                    existingNames.add(aPkg.getName());
                }
            }
            String newName = makeNameUnique(existingNames, name);
            return newName;
        }
    }

    protected String makeClassifierNameUniqueWithinPackage(Classifier cfier,
            String name) {
        Package pkg = cfier.getPackage();
        List<String> existingNames = getClassifierNamesFromPackage(pkg, cfier);
        // Also include the first segment of the fully-qualified package
        // name, as we need to avoid a conflict with that too
        String pkgName = pkg.getQualifiedName();
        if (pkgName != null) {
            existingNames.add(pkgName.toLowerCase().replaceAll("::", ".")
                    .split("\\.")[0]);
        }
        String newName = makeNameUnique(existingNames, name);
        return newName;
    }

    protected void setName(NamedElement ne, String name) {
        TransactionalEditingDomain domain =
                XpdResourcesPlugin.getDefault().getEditingDomain();
        Command command =
                SetCommand.create(domain,
                        ne,
                        UMLPackage.eINSTANCE.getNamedElement_Name(),
                        name);
        domain.getCommandStack().execute(command);
    }

    protected List<String> getClassifierNamesFromPackage(Package pkg,
            Classifier ignore) {
        List<String> result = new ArrayList<String>();
        for (EObject eo : pkg.eContents()) {
            if (eo instanceof org.eclipse.uml2.uml.Class
                    || eo instanceof Enumeration || eo instanceof PrimitiveType) {
                if (eo != ignore) {
                    String name = ((Classifier) eo).getName();
                    if (name != null) {
                        result.add(name.toLowerCase());
                    }
                }
            }
        }
        return result;
    }

    protected List<String> getAttributeNamesFromClass(
            org.eclipse.uml2.uml.Class clazz, Property ignore) {
        List<String> result = new ArrayList<String>();
        for (Property attr : clazz.getAllAttributes()) {
            if (attr != ignore) {
                result.add(attr.getName().toLowerCase());
            }
        }
        return result;
    }

    protected List<String> getEnumerationLiteralNamesFromEnumeration(
            Enumeration enu, EnumerationLiteral ignore) {
        List<String> result = new ArrayList<String>();
        for (EnumerationLiteral lit : enu.getOwnedLiterals()) {
            if (lit != ignore) {
                String name = lit.getName().toLowerCase();
                result.add(name);
            }
        }
        return result;
    }

    protected String makeNameUnique(List<String> existingNames, String name) {
        if (existingNames.contains(name.replaceAll("_", "").toLowerCase())) {
            int suffix = 1;
            // extract existing numeric part, if present
            int pos = name.length() - 1;
            while (pos != 0 && Character.isDigit(name.charAt(pos))) {
                pos--;
            }
            if (pos < name.length() - 1) {
                suffix = Integer.parseInt(name.substring(pos + 1));
            }
            // Find a suffix that produces a name that's not already in the list
            String nonNumericPart = name.substring(0, pos + 1);
            while (existingNames.contains(nonNumericPart.replaceAll("_", "")
                    .toLowerCase() + suffix)) {
                suffix++;
            }
            return nonNumericPart + suffix;
        } else {
            return name;
        }
    }

    /**
     * Cleanses the supplied EnumerationLiteral name such that it is 'CDS-safe'.
     * Removes MultiByte characters from the name.
     * 
     * @param name
     * @return
     */
    public String cleanseEnumerationLiteralName(String name) {
        /*
         * - name must begin with an upper-case letter (as a number causing EMF
         * to prefix it with '_') - name must contain only UPPER-CASE letters
         * and numbers and no MultiByte characters
         */
        /*
         * XPD-3976 Remove multibyte characters from enum Literal name, Filter
         * out OR exclude multibyte characters from the given string, before
         * processing further.
         */

        // convert to upper case, after filtering the multibyte chars.
        name = removeNonLatinCharacters(name);
        name = name.toUpperCase();
        StringBuilder buf = new StringBuilder();
        int i = 0;
        // Search through the name until a character is found
        // that becomes an UPPERCASE_LETTER after upper-casing.
        while (i < name.length() && buf.length() == 0) {
            char c = name.charAt(i);
            if (Character.getType(c) == Character.UPPERCASE_LETTER) {
                buf.append(c);
            }
            i++;
        }
        // Start changes XPD-3463 :
        // if name does not contain any character , [nothing will be
        // appended to buf in prev steps]
        // Prefix with 'ENUMLIT' and then copy the digits from the name.
        if (buf.length() == 0) {
            buf.append(ENUMLIT_PREFIX);
            for (Character ch : name.toCharArray()) {
                if (Character.isDigit(ch)) {
                    buf.append(ch);
                }
            }
        }
        // End changes XPD-3463 :
        // Append the remainder of the name, where characters
        // are letters or digits.
        while (i < name.length()) {
            char c = name.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                buf.append(c);
            }
            i++;
        }
        return buf.toString();

    }

    /**
     * Cleanses the supplied PrimitiveType name such that it is 'CDS-safe'.
     * 
     * @param name
     * @return
     */
    public String cleansePrimitiveTypeName(String name,
            boolean suffixReservedWord) {
        return cleanseClassifierName(name, suffixReservedWord);
    }

    /**
     * Cleanses the supplied Enumeration name such that it is 'CDS-safe'.
     * 
     * @param name
     * @return
     */
    public String cleanseEnumerationName(String name, boolean suffixReservedWord) {

        return cleanseClassName(name, suffixReservedWord);
    }
}
