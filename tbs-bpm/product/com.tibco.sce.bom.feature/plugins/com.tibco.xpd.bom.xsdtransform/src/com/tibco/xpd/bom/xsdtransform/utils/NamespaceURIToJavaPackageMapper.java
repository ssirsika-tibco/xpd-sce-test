package com.tibco.xpd.bom.xsdtransform.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;

import com.tibco.xpd.bom.gen.ui.preferences.BomGenPropertyStore;
import com.tibco.xpd.bom.resources.BOMResourcesPlugin;

/**
 * Provides the ability to map from a Namespace URI (as would appear in a WSDL
 * or XSD) to a Java package name. At the time of writing, it has been decided
 * that model and package names in BOM will be considered equivalent to Java
 * package names. Therefore, this method is suitable for use in both
 * WSDL/XSD->BOM transformation as well as BOM->(XSD->)CDS. The majority of this
 * class is copy-and-pasted [1] from two EMF classes and these sources are
 * clearly identified in comment blocks. An alternative would be to call the
 * original code within EMF. After discussion with Tim, the rationale for doing
 * the former is that it makes us immune to future changes in EMF, should we
 * ever migrate to a later version. We are effectively accepting EMF's
 * implementation as best-practice and making it our own. As of January 2011, we
 * have made various changes relax the amount of transformation a name undergoes
 * (XPD-1461)
 * 
 * [1] Please note that visibility of copy-and-pasted methods have been reduced
 * from public to protected wherever possible and all methods have been made
 * static.
 * 
 * @author smorgan
 * 
 */
public class NamespaceURIToJavaPackageMapper {

    private static final String UNDERSCORE = "_"; //$NON-NLS-1$

    public static Collection<String> windowsDeviceNames = Arrays
            .asList(new String[] { "con", "prn", "aux", "nul", "com1", "com2", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
                    "com3", "com4", "com5", "com6", "com7", "com8", "com9", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
                    "lpt1", "lpt2", "lpt3", "lpt4", "lpt5", "lpt6", "lpt7", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
                    "lpt8", "lpt9" }); //$NON-NLS-1$ //$NON-NLS-2$

    /**
     * Tranforms the given namespace URI into a legal Java package name.
     * 
     * @param nsURI
     *            the namespace URI
     * @return the corresponding Java package name that should be used for
     *         generated code corresponding to the schema with the provided
     *         namespace URI
     * @deprecated XPD-5145 replaced with version that takes project as an
     *             argument as well as the namespace.
     */
    @Deprecated
    public static String getJavaPackageNameFromNamespaceURI(String nsURI) {
        return getJavaPackageNameFromNamespaceURI(null, nsURI);
    }

    /**
     * Tranforms the given namespace URI into a legal Java package name.
     * 
     * @param project
     *            The project containing the resource to which the namespace
     *            applies. Can be null.
     * @param nsURI
     *            the namespace URI
     * @return the corresponding Java package name that should be used for
     *         generated code corresponding to the schema with the provided
     *         namespace URI
     */
    public static String getJavaPackageNameFromNamespaceURI(IProject project,
            String nsURI) {
        String qpn = qualifiedPackageName(project, nsURI);
        StringBuilder buf = new StringBuilder();
        String[] fragments = qpn.split("\\."); //$NON-NLS-1$
        for (int i = 0; i < fragments.length; i++) {
            if (i != 0) {
                buf.append("."); //$NON-NLS-1$
            }
            String frag = safeName(fragments[i]);
            buf.append(frag);
            if (windowsDeviceNames.contains(frag.toLowerCase())) {
                buf.append(UNDERSCORE);
            }
        }
        String result = buf.toString();
        return result;

    }

    // EMF CODE COPY START #1/2
    // Until the corresponding 'EMF CODE COPY END' comment, the following
    // code is copied from org.eclipse.emf.codegen.util.CodeGenUtil
    // Eclipse CVS details for the source file are:
    // CodeGenUtil.java,v 1.27 2007/05/15 22:33:38 emerks Exp

    private static Set<String> javaReservedWords;

    /**
     * Returns the set of all Java's keywords and textual literals, as of Java
     * 5.0.
     */
    protected static Set<String> getJavaReservedWords() {
        if (javaReservedWords == null) {
            Set<String> result = new HashSet<String>(100);
            result.add("abstract"); //$NON-NLS-1$
            result.add("assert"); //$NON-NLS-1$
            result.add("boolean"); //$NON-NLS-1$
            result.add("break"); //$NON-NLS-1$
            result.add("byte"); //$NON-NLS-1$
            result.add("case"); //$NON-NLS-1$
            result.add("catch"); //$NON-NLS-1$
            result.add("char"); //$NON-NLS-1$
            result.add("class"); //$NON-NLS-1$
            result.add("const"); //$NON-NLS-1$
            result.add("continue"); //$NON-NLS-1$
            result.add("default"); //$NON-NLS-1$
            result.add("do"); //$NON-NLS-1$
            result.add("double"); //$NON-NLS-1$
            result.add("else"); //$NON-NLS-1$
            result.add("enum"); //$NON-NLS-1$
            result.add("extends"); //$NON-NLS-1$
            result.add("false"); //$NON-NLS-1$
            result.add("final"); //$NON-NLS-1$
            result.add("finally"); //$NON-NLS-1$
            result.add("float"); //$NON-NLS-1$
            result.add("for"); //$NON-NLS-1$
            result.add("goto"); //$NON-NLS-1$
            result.add("if"); //$NON-NLS-1$
            result.add("implements"); //$NON-NLS-1$
            result.add("import"); //$NON-NLS-1$
            result.add("instanceof"); //$NON-NLS-1$
            result.add("int"); //$NON-NLS-1$
            result.add("interface"); //$NON-NLS-1$
            result.add("long"); //$NON-NLS-1$
            result.add("native"); //$NON-NLS-1$
            result.add("new"); //$NON-NLS-1$
            result.add("null"); //$NON-NLS-1$
            result.add("package"); //$NON-NLS-1$
            result.add("private"); //$NON-NLS-1$
            result.add("protected"); //$NON-NLS-1$
            result.add("public"); //$NON-NLS-1$
            result.add("return"); //$NON-NLS-1$
            result.add("short"); //$NON-NLS-1$
            result.add("static"); //$NON-NLS-1$
            result.add("strictfp"); //$NON-NLS-1$
            result.add("super"); //$NON-NLS-1$
            result.add("switch"); //$NON-NLS-1$
            result.add("synchronized"); //$NON-NLS-1$
            result.add("this"); //$NON-NLS-1$
            result.add("throw"); //$NON-NLS-1$
            result.add("throws"); //$NON-NLS-1$
            result.add("transient"); //$NON-NLS-1$
            result.add("true"); //$NON-NLS-1$
            result.add("try"); //$NON-NLS-1$
            result.add("void"); //$NON-NLS-1$
            result.add("volatile"); //$NON-NLS-1$
            result.add("while"); //$NON-NLS-1$

            javaReservedWords = Collections.unmodifiableSet(result);
        }
        return javaReservedWords;
    }

    /**
     * Tests whether a given string is a Java reserved word.
     */
    protected static boolean isJavaReservedWord(String s) {
        return getJavaReservedWords().contains(s);
    }

    protected static String safeName(String name) {
        if (isJavaReservedWord(name))
            return name + "_"; //$NON-NLS-1$
        return name;
    }

    // EMF CODE COPY END #1/2
    // This comment marks the end of code copied from the
    // aforementioned CodeGenUtil EMF class.

    // EMF CODE COPY START #2/2
    // Until the corresponding 'EMF CODE COPY END' comment, the following
    // code is copied from org.eclipse.emf.codegen.util.CodeGenUtil
    // Eclipse CVS details for the source file are:
    // NameMangler.java,v 1.2 2006/12/15 18:59:56 emerks Exp

    // This behaves like CodeGenUtil.uncapPrefixedName(), which isn't available
    // here,
    // except without the forceDifferent option.
    // The two methods should be kept in synch.
    //
    protected static String uncapName(String name) {
        if (name.length() == 0) {
            return name;
        } else {
            String lowerName = name.toLowerCase();
            int i;
            for (i = 0; i < name.length(); i++) {
                if (name.charAt(i) == lowerName.charAt(i)) {
                    break;
                }
            }
            if (i > 1 && i < name.length()
                    && !Character.isDigit(name.charAt(i))) {
                --i;
            }
            return name.substring(0, i).toLowerCase() + name.substring(i);
        }
    }

    protected static final List<String> DOMAINS = Arrays.asList(new String[] {
            "COM", "com", "ORG", "org" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

    /**
     * Converts a namespace into a qualified package name.
     * 
     * XPD-5145 added a project property allowing file extensions to be kept as
     * part of the package name, hence the requirement for the project.
     * 
     * @param project
     *            The project containing the resource. Can be null.
     * @param namespace
     *            The namespace from the resource.
     * @return The qualified package name derived from the namespace.
     */
    public static String qualifiedPackageName(IProject project, String namespace) {
        URI uri = URI.createURI(namespace);
        List<String> parsedName;
        if (uri.isHierarchical()) {
            String host = uri.host();
            if (host != null && host.startsWith("www.")) { //$NON-NLS-1$
                host = host.substring(4);
            }
            parsedName = parseName(host, '.');
            Collections.reverse(parsedName);
            // XPD-1461: Removed code to lower-case the first character

            // XPD-5145 Check preference setting before trimming file extension.
            boolean keepExtension = false;
            if (project != null) {
                BomGenPropertyStore store =
                        new BomGenPropertyStore(project, null);
                keepExtension =
                        Boolean.valueOf(store
                                .getProjectProperty(BOMResourcesPlugin.P_KEEP_NAMESPACE_FILE_EXTENSION_BOM_GENERATION));
            }

            String path;
            if (keepExtension) {
                path = uri.path();
            } else {
                path = uri.trimFileExtension().path();
            }
            parsedName.addAll(parseName(path, '/'));
        } else {
            String opaquePart = uri.opaquePart();
            int index = opaquePart.indexOf(":"); //$NON-NLS-1$
            if (index != -1 && "urn".equalsIgnoreCase(uri.scheme())) { //$NON-NLS-1$
                parsedName = parseName(opaquePart.substring(0, index), '-');
                if (parsedName.size() > 0
                        && DOMAINS
                                .contains(parsedName.get(parsedName.size() - 1))) {
                    Collections.reverse(parsedName);
                    parsedName.set(0, parsedName.get(0).toLowerCase());
                }
                parsedName.addAll(parseName(opaquePart.substring(index + 1),
                        '/'));
            } else {
                parsedName = parseName(opaquePart, '/');
            }
        }

        StringBuffer qualifiedPackageName = new StringBuffer();
        for (String packageName : parsedName) {
            if (packageName.length() > 0) {
                if (qualifiedPackageName.length() > 0) {
                    qualifiedPackageName.append('.');
                }
                qualifiedPackageName.append(validName(packageName, false));
            }
        }
        return qualifiedPackageName.toString();
    }

    protected static String validName(String name, boolean isUpperCase) {
        return validName(name, isUpperCase, "_"); //$NON-NLS-1$
    }

    protected static String validName(String name, boolean isUpperCase,
            String prefix) {
        return validName(name, isUpperCase ? UPPER_CASE : LOWER_CASE, prefix);
    }

    protected static final int UNCHANGED_CASE = 0;

    protected static final int UPPER_CASE = 1;

    protected static final int LOWER_CASE = 2;

    protected static String validName(String name, int casing, String prefix) {
        // XPD-1461: Using colon, knowing that will never be legal (as opposed
        // to underscore)
        List<String> parsedName = parseName(name, ':');
        StringBuffer result = new StringBuffer();
        for (String nameComponent : parsedName) {
            if (nameComponent.length() > 0) {
                if (result.length() > 0 || casing == UPPER_CASE) {
                    result.append(Character.toUpperCase(nameComponent.charAt(0)));
                    result.append(nameComponent.substring(1));
                } else {
                    result.append(nameComponent);
                }
            }
        }

        // XPD-1461: Altered to NOT lower-case the first character
        // result.toString() used to read: casing == LOWER_CASE ?
        // uncapName(result
        // .toString()) : result.toString()
        return result.length() == 0 ? prefix : Character
                .isJavaIdentifierStart(result.charAt(0)) ? result.toString()
                : prefix + result;
    }

    // This behaves like CodeGenUtil.parseName(), which isn't available here,
    // except it also removes invalid indentifier characters.
    // The two methods should be kept in synch.
    //
    protected static List<String> parseName(String sourceName, char separator) {
        List<String> result = new ArrayList<String>();
        if (sourceName != null) {
            StringBuffer currentWord = new StringBuffer();
            boolean lastIsLower = false;
            for (int index = 0, length = sourceName.length(); index < length; ++index) {
                char curChar = sourceName.charAt(index);
                if (!Character.isJavaIdentifierPart(curChar)) {
                    curChar = separator;
                }
                // XPD-1461: Removed logic that adds segment break (.) on
                // encountering change to upper-case or an underscore.
                if ((!lastIsLower && Character.isDigit(curChar))
                        || curChar == separator) {
                    if (lastIsLower && currentWord.length() > 1
                            || curChar == separator && currentWord.length() > 0) {
                        result.add(currentWord.toString());
                        currentWord = new StringBuffer();
                    }
                    lastIsLower = false;
                } else {
                    if (!lastIsLower) {
                        int currentWordLength = currentWord.length();
                        if (currentWordLength > 1) {
                            char lastChar =
                                    currentWord.charAt(--currentWordLength);
                            currentWord.setLength(currentWordLength);
                            result.add(currentWord.toString());
                            currentWord = new StringBuffer();
                            currentWord.append(lastChar);
                        }
                    }
                    lastIsLower = true;
                }

                if (curChar != separator) {
                    currentWord.append(curChar);
                }
            }

            result.add(currentWord.toString());
        }
        return result;
    }

    // EMF CODE COPY END #2/2
    // This comment marks the end of code copied from the
    // aforementioned NameMangler EMF class.

}
