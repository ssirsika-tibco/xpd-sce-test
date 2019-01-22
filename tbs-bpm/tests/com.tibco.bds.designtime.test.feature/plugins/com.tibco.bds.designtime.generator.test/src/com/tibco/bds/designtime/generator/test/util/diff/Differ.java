package com.tibco.bds.designtime.generator.test.util.diff;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.Manifest;
import java.util.regex.Pattern;

import com.tibco.bds.designtime.generator.test.util.diff.Difference.DifferenceType;

public class Differ {

    private static boolean loggingOn = false;

    private static String MARKER = "      >>> | <<<"; //$NON-NLS-1$

    private static boolean matchesAnyPattern(Collection<String> patterns,
            String str) {
        boolean match = false;
        Iterator<String> iter = patterns.iterator();
        while (iter.hasNext() && !match) {
            match = Pattern.matches(iter.next(), str);
        }
        if (match && loggingOn) {
            System.out.println("Ignore " + str); //$NON-NLS-1$
        }
        return match;
    }

    public static List<Difference> diff(File root1, File root2,
            List<String> ignoreNames, Map<String, List<String>> ignoreContents)
            throws DifferException {

        List<Difference> diffs = new ArrayList<Difference>();

        for (File file1 : root1.listFiles()) {
            if (!matchesAnyPattern(ignoreNames, file1.getAbsolutePath())) {
                // Derive equiv path beneath root2
                String relPath =
                        file1.getAbsolutePath().substring(root1
                                .getAbsolutePath().length());
                File file2 = new File(root2, relPath);

                // If file2 doesn't exists, it's a removal diff
                if (!file2.exists()) {
                    diffs.add(new Difference(DifferenceType.REMOVE, file1));
                    System.out.println(String.format("REMOVE(1):   %s", file1)); //$NON-NLS-1$
                } else {
                    // File 2 does exist, so compare.
                    // If one is a file and the other a folder, that's a change
                    if ((file1.isFile() && file2.isDirectory())
                            || (file1.isDirectory() && file2.isFile())) {
                        diffs.add(new Difference(DifferenceType.CHANGE, file1));
                        System.out.println(String.format("CHANGE(2):   %s", //$NON-NLS-1$
                                file1));
                    } else {
                        // They both exist and are of the same type
                        if (file1.isDirectory()) {
                            // They're both folders, so recurse
                            List<Difference> diffList =
                                    diff(file1,
                                            file2,
                                            ignoreNames,
                                            ignoreContents);
                            diffs.addAll(diffList);
                            for (Difference d : diffList) {
                                System.out.println(String.format("%s(3):   %s", //$NON-NLS-1$
                                        d.getType(),
                                        d.getFile()));
                            }
                        } else {
                            // They're both files, so compare contents
                            try {
                                if (!filesAreTheSame(file1,
                                        file2,
                                        ignoreContents)) {
                                    diffs.add(new Difference(
                                            DifferenceType.CHANGE, file2));
                                    System.out.println(String
                                            .format("CHANGE(4):   %s", file2)); //$NON-NLS-1$
                                }
                            } catch (NoSuchAlgorithmException e) {
                                throw new DifferException(
                                        "Checksum generation failed", e); //$NON-NLS-1$
                            } catch (IOException e) {
                                throw new DifferException("IO exception", e); //$NON-NLS-1$
                            }
                        }
                    }
                }
            }
        }

        // Stage 2: Check for add'l files beneath root2 that didn't exist in
        // root1
        if (root2.exists()) {
            for (File file2 : root2.listFiles()) {
                if (!matchesAnyPattern(ignoreNames, file2.getAbsolutePath())) {
                    String relPath =
                            file2.getAbsolutePath().substring(root2
                                    .getAbsolutePath().length());
                    File file1 = new File(root1, relPath);
                    if (!file1.exists()) {
                        diffs.add(new Difference(DifferenceType.ADD, file2));
                        System.out
                                .println(String.format("ADD(5):   %s", file2)); //$NON-NLS-1$
                    }
                }
            }
        }

        return diffs;
    }

    private static boolean filesAreTheSame(File file1, File file2,
            Map<String, List<String>> ignoreContents)
            throws NoSuchAlgorithmException, IOException {

        boolean result = false;
        // MANIFEST.MF requires special comparison, as attribute order may
        // differ
        if (file1.getName().equalsIgnoreCase("MANIFEST.MF")) { //$NON-NLS-1$
            result = manifestFilesAreTheSame(file1, file2);
        } else {
            // If there are content ignore patterns appropriate
            // to this file, retrieve them now.
            List<String> contentIgnorePatterns = null;
            for (Iterator<String> iter = ignoreContents.keySet().iterator(); contentIgnorePatterns == null
                    && iter.hasNext();) {
                String namePattern = iter.next();
                if (Pattern.matches(namePattern, file1.getAbsolutePath())) {
                    contentIgnorePatterns = ignoreContents.get(namePattern);
                }
            }

            result =
                    compareFilesWithIgnores(file1, file2, contentIgnorePatterns);
        }
        if (loggingOn) {
            if (result) {
                System.out.println(String.format("Match: %s", file1)); //$NON-NLS-1$
            } else {
                System.out.println(String.format("DIFF:  %s\n       %s", //$NON-NLS-1$
                        file1,
                        file2));
            }
        }
        return result;
    }

    private static boolean manifestFilesAreTheSame(File file1, File file2)
            throws IOException {
        InputStream fis1 = null;
        InputStream fis2 = null;
        try {
            fis1 = new FileInputStream(file1);
            fis2 = new FileInputStream(file2);
            Manifest mf1 = new Manifest(fis1);
            Manifest mf2 = new Manifest(fis2);
            boolean result = mf1.equals(mf2);
            if (loggingOn && !result) {
                String s1 = readContents(file1);
                String s2 = readContents(file2);
                // Dump mismatch to aid debugging
                dumpFirstDifference(s1, s2, 135, file1, file2);
            }
            return result;
        } finally {
            if (fis1 != null) {
                fis1.close();
            }
            if (fis2 != null) {
                fis2.close();
            }
        }
    }

    public static byte[] createChecksum(File file) throws IOException,
            NoSuchAlgorithmException {
        InputStream fis = new FileInputStream(file);

        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5"); //$NON-NLS-1$
        int numRead;
        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);
        fis.close();
        return complete.digest();
    }

    public static String getMD5Checksum(File file)
            throws NoSuchAlgorithmException, IOException {
        byte[] b = createChecksum(file);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            result.append(Integer.toString((b[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return result.toString();
    }

    private static boolean compareFilesWithIgnores(File file1, File file2,
            List<String> ignores) throws IOException {
        String s1 = readContents(file1);
        String s2 = readContents(file2);
        // If ignore patterns have been provided,
        // remove matching regions from the files'
        // content before comparison.
        if (ignores != null) {
            for (String ignore : ignores) {
                s1 = s1.replaceAll(ignore, ""); //$NON-NLS-1$
                s2 = s2.replaceAll(ignore, ""); //$NON-NLS-1$
            }
        }
        boolean result = s1.equals(s2);
        if (!result) {
            // Dump mismatch to aid debugging
            dumpFirstDifference(s1, s2, 135, file1, file2);
        }
        return result;
    }

    /**
     * Reads a given file into a string
     * 
     * @param file1
     * @return
     * @throws IOException
     */
    private static String readContents(File file1) throws IOException {

        StringBuilder str = new StringBuilder();

        // Get the object of DataInputStream
        DataInputStream in = new DataInputStream(new FileInputStream(file1));
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        while ((strLine = br.readLine()) != null) {
            // Print the content on the console
            str.append(strLine);
        }
        // Close the input stream
        in.close();

        return str.toString();
    }

    public static void dumpFirstDifference(String s1, String s2, int maxChars,
            File file1, File file2) {
        int i = 0;
        boolean done = false;
        while (i < s1.length() && i < s2.length() && !done) {
            if (s1.charAt(i) != s2.charAt(i)) {
                String mismatchMessage = "Mismatch at position " + i; //$NON-NLS-1$
                if (file1 != null && file2 != null) {
                    mismatchMessage +=
                            String.format(" comparing file [%s] with [%s]", //$NON-NLS-1$
                                    file1.getAbsolutePath(),
                                    file2.getAbsolutePath());
                }
                String r1 =
                        String.format("%1$-10s%2$s", //$NON-NLS-1$
                                s1.substring(Math.max(0, i - 10), i),
                                s1.substring(i,
                                        Math.min(i + maxChars, s1.length())));
                String r2 =
                        String.format("%1$-10s%2$s", //$NON-NLS-1$
                                s2.substring(Math.max(0, i - 10), i),
                                s2.substring(i,
                                        Math.min(i + maxChars, s2.length())));
                System.out.println(mismatchMessage);
                System.out.println(MARKER);
                System.out.println(r1);
                System.out.println(r2);
                System.out.println(MARKER);
                done = true;
            }
            i++;
        }
    }
}
