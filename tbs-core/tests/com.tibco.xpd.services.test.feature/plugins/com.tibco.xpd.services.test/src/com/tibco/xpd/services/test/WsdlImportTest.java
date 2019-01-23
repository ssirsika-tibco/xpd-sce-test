package com.tibco.xpd.services.test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.osgi.framework.Bundle;

import com.tibco.xpd.core.test.util.TestUtil;
import com.tibco.xpd.registry.ui.wizard.WsdlImportBaseWizard;
import com.tibco.xpd.wsdl.Activator;
import com.tibco.xpd.wsdl.CertificateAcceptanceTracker;
import com.tibco.xpd.wsdl.CertificateManager;
import com.tibco.xpd.wsdl.internal.WsdlCopier;

import junit.framework.TestCase;

/**
 * This test simulates the action of importing a service to a local wsdl file as
 * performed by WsdlImportBaseWizard. For that reason it has to setup a project
 * and a folder to import the wsdl file into. The url to the service can be
 * overridden by setting a property
 * 
 * @author pwells
 * 
 *         TODO JA: Some test were 'disabled' (by prefixing test method's names
 *         with '_') as they were failing due to lack of test resources (like
 *         web services) being deployed. This class needs to be reviewed.
 */
public class WsdlImportTest extends TestCase {
    private static final String PROJECT_NAME = "WsdlImportTestProject"; //$NON-NLS-1$

    private static final String wsdlSourceURL =
            System.getProperty("WsdlImportHttpsTestServerURL", //$NON-NLS-1$
                    "https://bs-winxp-dev:8443/axis2/services/WebiPE?wsdl"); //$NON-NLS-1$

    private IFolder importDestinationFolder;

    private IProject project;

    private File sourceFolder;

    private File keyStoreFolder;

    private IFolder temporaryKeyStoreFolder;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        project = TestUtil.createProject(PROJECT_NAME);
        importDestinationFolder = project.getFolder("importedWsdls"); //$NON-NLS-1$
        importDestinationFolder.create(true, true, new NullProgressMonitor());
        final Bundle bundle = Platform.getBundle("com.tibco.xpd.services.test"); //$NON-NLS-1$
        assertNotNull("Failed to load bundle", bundle); //$NON-NLS-1$
        final java.net.URL sourceUrl = FileLocator.find(bundle,
                new Path("./wsdlImportTestFiles"), //$NON-NLS-1$
                null);
        sourceFolder = new File(FileLocator.toFileURL(sourceUrl).getFile());
        assertTrue("Cannot find folder containing test wsdl and xsd files", //$NON-NLS-1$
                sourceFolder.exists());
        final java.net.URL keyStoreFilesUrl =
                FileLocator.find(bundle, new Path("./keyStoreFiles"), null); //$NON-NLS-1$
        keyStoreFolder =
                new File(FileLocator.toFileURL(keyStoreFilesUrl).getFile());
        assertTrue("Cannot find folder containing test keystore files", //$NON-NLS-1$
                sourceFolder.exists());
        temporaryKeyStoreFolder = project.getFolder("temporaryKeyStoreFolder"); //$NON-NLS-1$
        temporaryKeyStoreFolder.create(true, true, new NullProgressMonitor());
    }

    @Override
    protected void tearDown() throws Exception {
        TestUtil.removeProject(project.getName());
        super.tearDown();
    }

    public void testUniqueFileNameGeneration() throws Exception {
        final IFile destinationIFile =
                importDestinationFolder.getFile(new Path("account.wsdl")); //$NON-NLS-1$
        assertFalse("Destination file already exists", //$NON-NLS-1$
                destinationIFile.exists());
        final IFile generatedIFile1 =
                WsdlCopier.generateUniqueTargetFile(destinationIFile);
        assertTrue(
                "Stem file did not exist so generator should have returned it unchanged", //$NON-NLS-1$
                generatedIFile1 == destinationIFile);
        InputStream inputStream = new ByteArrayInputStream(
                "<?xml version=\"1.0\"?><definitions name=\"Content1\"/>" //$NON-NLS-1$
                        .getBytes(StandardCharsets.UTF_8));
        destinationIFile.create(inputStream, true, new NullProgressMonitor());
        inputStream.close();
        assertTrue("Destination file should exist", destinationIFile.exists()); //$NON-NLS-1$
        final IFile generatedIFile2 =
                WsdlCopier.generateUniqueTargetFile(destinationIFile);
        assertFalse(
                "Stem file did exist so generator should have returned a different file", //$NON-NLS-1$
                generatedIFile2.equals(destinationIFile));
        assertFalse("Generated file should not already exist", //$NON-NLS-1$
                generatedIFile2.exists());
        inputStream = new ByteArrayInputStream(
                "<?xml version=\"1.0\"?><definitions name=\"Content2\"/>" //$NON-NLS-1$
                        .getBytes(StandardCharsets.UTF_8));
        generatedIFile2.create(inputStream, true, new NullProgressMonitor());
        inputStream.close();
        assertTrue("Generated file should now exist", generatedIFile2.exists()); //$NON-NLS-1$
        final IFile generatedIFile3 =
                WsdlCopier.generateUniqueTargetFile(generatedIFile2);
        assertFalse("Generated file should not already exist", //$NON-NLS-1$
                generatedIFile3.exists());
    }

    /**
     * Check that a WSDL file and any included WSDL and XSD files are imported
     * from a local file source
     */
    public void testWsdlFileImport() throws Exception {
        final File fileToTriggerImport =
                new File(sourceFolder, "approval_management.wsdl"); //$NON-NLS-1$
        assertTrue("Cannot locate wsdl file approval_management.wsdl", //$NON-NLS-1$
                fileToTriggerImport.exists());
        final IFile destinationIFile = importDestinationFolder
                .getFile(new Path("approval_management.wsdl")); //$NON-NLS-1$
        assertFalse("Wsdl import destination file should not already exist", //$NON-NLS-1$
                destinationIFile.exists());
        final String sourceURLString =
                URI.createFileURI(fileToTriggerImport.getAbsolutePath())
                        .toString();
        Activator.getDefault().copyWsdl(sourceURLString,
                destinationIFile,
                CertificateAcceptanceTracker.NULL);
        IResource[] importedFileNames = importDestinationFolder.members();
        assertTrue("incorrect number of imported files " //$NON-NLS-1$
                + importedFileNames.length + " != 6", //$NON-NLS-1$
                importedFileNames.length == 6);
    }

    /**
     * Check that a WSDL file and any included WSDL and XSD files are imported
     * from a local file source
     */
    // TODO JA: this test is disabled (method name prefixed with '_') because it
    // fails due
    // to missing service. This needs to be revisited and fixed or removed.
    public void _testWsdlURLImport() throws Exception {
        final IFile destinationIFile = importDestinationFolder
                .getFile(new Path("approval_management.wsdl")); //$NON-NLS-1$
        assertFalse("Wsdl import destination file should not already exist", //$NON-NLS-1$
                destinationIFile.exists());
        final String sourceURLString =
                "http://10.100.71.88:81/vbtest/Service.asmx?wsdl"; //$NON-NLS-1$
        Activator.getDefault().copyWsdl(sourceURLString,
                destinationIFile,
                CertificateAcceptanceTracker.NULL);
        IResource[] importedFileNames = importDestinationFolder.members();
        assertTrue("incorrect number of imported files " //$NON-NLS-1$
                + importedFileNames.length + " != 1", //$NON-NLS-1$
                importedFileNames.length == 1);
    }

    /**
     * Check that on Windows the case of the wsdl file extension is ignored
     */
    public void testFileExtensionCase() throws Exception {
        if (!Platform.getOS().equals(Platform.OS_WIN32)) {
            return;
        }
        final File sourceFile = new File(sourceFolder, "BasicElements.WSDL"); //$NON-NLS-1$
        assertTrue("Cannot locate wsdl file BasicElements.WSDL", //$NON-NLS-1$
                sourceFile.exists());
        final IFile destinationIFile =
                importDestinationFolder.getFile(new Path("BasicElements.wsdl")); //$NON-NLS-1$
        assertFalse("Wsdl import destination file should not already exist", //$NON-NLS-1$
                destinationIFile.exists());
        final String sourceURLString =
                URI.createFileURI(sourceFile.getAbsolutePath()).toString();
        Activator.getDefault().copyWsdl(sourceURLString,
                destinationIFile,
                CertificateAcceptanceTracker.NULL);
        IResource[] importedFileNames = importDestinationFolder.members();
        assertTrue("incorrect number of imported files " //$NON-NLS-1$
                + importedFileNames.length + " != 1", //$NON-NLS-1$
                importedFileNames.length == 1);
    }

    /**
     * Check that if two WSDL files import the same xsd file the result is two
     * copies of the xsd file
     */
    public void testSameXSDReferenceImportFromTwoWSDLs() throws Exception {
        File sourceFileOne =
                new File(sourceFolder, "approval_management_portType.wsdl"); //$NON-NLS-1$
        assertTrue("Cannot locate wsdl file approval_management_portType.wsdl", //$NON-NLS-1$
                sourceFileOne.exists());
        File sourceFileTwo = new File(sourceFolder, "customer.wsdl"); //$NON-NLS-1$
        assertTrue("Cannot locate wsdl file customer.wsdl", //$NON-NLS-1$
                sourceFileTwo.exists());
        final String sourceURLStringOne =
                URI.createFileURI(sourceFileOne.getAbsolutePath()).toString();
        final String sourceURLStringTwo =
                URI.createFileURI(sourceFileTwo.getAbsolutePath()).toString();
        final IFile destinationIFileOne =
                importDestinationFolder.getFile(new Path("One.wsdl")); //$NON-NLS-1$
        final IFile destinationIFileTwo =
                importDestinationFolder.getFile(new Path("Two.wsdl")); //$NON-NLS-1$
        Activator.getDefault().copyWsdl(sourceURLStringOne,
                destinationIFileOne,
                CertificateAcceptanceTracker.NULL);
        Activator.getDefault().copyWsdl(sourceURLStringTwo,
                destinationIFileTwo,
                CertificateAcceptanceTracker.NULL);
        IResource[] importedFileNames = importDestinationFolder.members();
        int countOfXSDFiles = 0;
        boolean foundApprovalManagement = false;
        for (IResource thisResource : importedFileNames) {
            if (thisResource.getName().endsWith(".xsd")) { //$NON-NLS-1$
                countOfXSDFiles++;
            }
            if (thisResource.getName().equals("approval_management.xsd")) { //$NON-NLS-1$
                foundApprovalManagement = true;
            }
        }
        assertTrue("Expected 3 imported xsd files", countOfXSDFiles == 3); //$NON-NLS-1$
        assertTrue("approval_management.xsd not found", //$NON-NLS-1$
                foundApprovalManagement);
    }

    /**
     * Check that the UI handles the completion of filenames correctly to have a
     * .wsdl suffix
     */
    public void testAppendageOfWSDLExtension() throws Exception {
        String result =
                WsdlImportBaseWizard.applyWsdlExtension("testName.wsdl"); //$NON-NLS-1$
        assertTrue(result + " != testName.wsdl", //$NON-NLS-1$
                result.equals("testName.wsdl")); //$NON-NLS-1$
        result = WsdlImportBaseWizard.applyWsdlExtension("testName"); //$NON-NLS-1$
        assertTrue(result + " != testName.wsdl", //$NON-NLS-1$
                result.equals("testName.wsdl")); //$NON-NLS-1$
        result = WsdlImportBaseWizard.applyWsdlExtension("testName.wsDL"); //$NON-NLS-1$
        assertTrue(result + " != testName.wsdl", //$NON-NLS-1$
                result.equals("testName.wsdl")); //$NON-NLS-1$
        result = WsdlImportBaseWizard.applyWsdlExtension("testName."); //$NON-NLS-1$
        assertTrue(result + " != testName.wsdl", //$NON-NLS-1$
                result.equals("testName.wsdl")); //$NON-NLS-1$
        result = WsdlImportBaseWizard.applyWsdlExtension("testName...."); //$NON-NLS-1$
        assertTrue(result + " != testName.wsdl", //$NON-NLS-1$
                result.equals("testName.wsdl")); //$NON-NLS-1$
        result = WsdlImportBaseWizard.applyWsdlExtension("wsdl"); //$NON-NLS-1$
        assertTrue(result + " != wsdl.wsdl", result.equals("wsdl.wsdl")); //$NON-NLS-1$ //$NON-NLS-2$
        result = WsdlImportBaseWizard.applyWsdlExtension(""); //$NON-NLS-1$
        assertTrue(result + " != .wsdl", result.equals(".wsdl")); //$NON-NLS-1$ //$NON-NLS-2$
        result = WsdlImportBaseWizard.applyWsdlExtension(".wsdl"); //$NON-NLS-1$
        assertTrue(result + " != .wsdl", result.equals(".wsdl")); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * Check that where a URI contains a query (denoted by a question mark) that
     * the import target filename generated does not contain a question mark.
     */
    public void testQueryURIHandling() throws Exception {
        String result = WsdlImportBaseWizard.guessImportedFileNameFromURI(
                "http://www.somewhere.com/aWsdlFile"); //$NON-NLS-1$
        assertTrue(result + "!= aWsdlFile.wsdl", //$NON-NLS-1$
                result.equals("aWsdlFile.wsdl")); //$NON-NLS-1$
        result = WsdlImportBaseWizard.guessImportedFileNameFromURI(
                "http://www.somewhere.com/pathPart/aWsdlFile"); //$NON-NLS-1$
        assertTrue(result + "!= aWsdlFile.wsdl", //$NON-NLS-1$
                result.equals("aWsdlFile.wsdl")); //$NON-NLS-1$
        result = WsdlImportBaseWizard.guessImportedFileNameFromURI(
                "http://www.somewhere.com/aWsdlFile.wsdl"); //$NON-NLS-1$
        assertTrue(result + "!= aWsdlFile.wsdl", //$NON-NLS-1$
                result.equals("aWsdlFile.wsdl")); //$NON-NLS-1$
        result = WsdlImportBaseWizard
                .guessImportedFileNameFromURI("http://www.somewhere.com"); //$NON-NLS-1$
        assertTrue(
                result + "!= empty string - there was no filename in the uri", //$NON-NLS-1$
                result.equals("")); //$NON-NLS-1$
        result = WsdlImportBaseWizard.guessImportedFileNameFromURI(""); //$NON-NLS-1$
        assertTrue(
                "Guessing a filename from an empty URI should return an empty string", //$NON-NLS-1$
                result.equals("")); //$NON-NLS-1$
        result = WsdlImportBaseWizard.guessImportedFileNameFromURI("http"); //$NON-NLS-1$
        assertTrue("Uri contains no protocol", result.equals("http.wsdl")); //$NON-NLS-1$ //$NON-NLS-2$
        result = WsdlImportBaseWizard.guessImportedFileNameFromURI("http:"); //$NON-NLS-1$
        assertTrue(
                "Uri contains no path, result should be an empty string (http:)", //$NON-NLS-1$
                result.equals("")); //$NON-NLS-1$
        result = WsdlImportBaseWizard.guessImportedFileNameFromURI("http:/"); //$NON-NLS-1$
        assertTrue(
                "Uri contains no path, result should be an empty string (http:/)", //$NON-NLS-1$
                result.equals("")); //$NON-NLS-1$
        result = WsdlImportBaseWizard.guessImportedFileNameFromURI("http://"); //$NON-NLS-1$
        assertTrue(
                "Uri contains an empty path so the result should be empty too (http://)", //$NON-NLS-1$
                result.equals("")); //$NON-NLS-1$
        result = WsdlImportBaseWizard.guessImportedFileNameFromURI(
                "http://www.somewhere.com/aWsdlFile.wsdl?queryToIgnore"); //$NON-NLS-1$
        assertTrue(result
                + "!= aWsdlFile.wsdl, query part should have been ignored", //$NON-NLS-1$
                result.equals("aWsdlFile.wsdl")); //$NON-NLS-1$
        result = WsdlImportBaseWizard.guessImportedFileNameFromURI(
                "http://www.somewhere.com/aWsdlFile.wsdl?queryToIgnore/with.unusual>characters-invalidatingURI%in$it"); //$NON-NLS-1$
        assertTrue(result + "!= emptyString - invalid uri", result.equals("")); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * A test to confirm that a wsdl can be imported from a server over https
     */
    // TODO JA: this test is disabled (method name prefixed with '_') because it
    // fails due
    // to missing service. This needs to be revisited and fixed or removed.
    public void _testImportFromHttps() throws Exception {
        final IFile destinationIFile =
                importDestinationFolder.getFile(new Path("destination.wsdl")); //$NON-NLS-1$
        assertFalse(destinationIFile.exists());
        final File singleCertificateFile = new File(keyStoreFolder, "cacerts"); //$NON-NLS-1$
        assertTrue("Problem finding test key store file", //$NON-NLS-1$
                singleCertificateFile.exists());
        CertificateAcceptanceTracker tracker =
                new CertificateAcceptanceTracker() {
                    @Override
                    public boolean shouldAccept(X509Certificate[] chain,
                            String urlString, String[] existingAliases) {
                        fail("Was not expecting to be asked to accept a certificate which is already trusted"); //$NON-NLS-1$
                        return false;
                    }

                    @Override
                    public X509Certificate getTrustedCertificate() {
                        fail("Was not expecting to be asked which certificate to trust when already trusted"); //$NON-NLS-1$
                        return null;
                    }

                    @Override
                    public String getAlias() {
                        fail("Was not expecting to be asked for an alias from a certificate which is already trusted"); //$NON-NLS-1$
                        return null;
                    }
                };
        Activator.getDefault().copyWsdl(wsdlSourceURL,
                destinationIFile,
                singleCertificateFile,
                tracker);
        assertTrue("Imported wsdl over https doesn't exist", //$NON-NLS-1$
                destinationIFile.exists());
        // check the imported file isn't empty
        final InputStream inputStream = destinationIFile.getContents();
        final int available = inputStream.available();
        byte[] buffer = new byte[available];
        inputStream.read(buffer);
        assertTrue("Imported wsdl file is empty", available > 0); //$NON-NLS-1$
        inputStream.close();
    }

    /**
     * A test to check that the certificate manager can discover the
     * certificates issued by a secured server
     */
    // TODO JA: this test is disabled (method name prefixed with '_') because it
    // fails due
    // to missing service. This needs to be revisited and fixed or removed.
    public void _testCertificateCollection() throws Exception {
        final X509Certificate[] certificateChain = CertificateManager
                .getCertificateChain(new java.net.URI(wsdlSourceURL));
        assertTrue(certificateChain.length + "!=1", //$NON-NLS-1$
                certificateChain.length == 1);
        X509Certificate certificate = certificateChain[0];
        final String nameString =
                "CN=bs-winxp-dev, OU=Unknown, O=Unknown, L=Unknown, ST=Unknown, C=Unknown"; //$NON-NLS-1$
        assertTrue("Name != " + nameString, //$NON-NLS-1$
                nameString.equals(certificate.getSubjectDN().getName()));
        assertTrue("Name != " + nameString, //$NON-NLS-1$
                nameString.equals(certificate.getIssuerDN().getName()));
    }

    /**
     * To test that the persisted certificate list can be created, read and
     * written to
     */
    // TODO JA: this test is disabled (method name prefixed with '_') because it
    // fails due
    // to missing service. This needs to be revisited and fixed or removed.
    public void _testPersistedCertificateList() throws Exception {
        final X509Certificate certificate = CertificateManager
                .getCertificateChain(new java.net.URI(wsdlSourceURL))[0];
        assertNotNull(certificate);
        // let's use the project as a temporary parent folder
        final File keyStoreParentFolder = project.getLocation().toFile();
        assertTrue("keyStoreParentFolder isn't a directory", //$NON-NLS-1$
                keyStoreParentFolder.isDirectory());
        CertificateManager.addToPersistedUserTrustedCertificateList("toAdd", //$NON-NLS-1$
                certificate,
                keyStoreParentFolder);
        CertificateManager.addToPersistedUserTrustedCertificateList(
                "alternativeAlias", //$NON-NLS-1$
                certificate,
                keyStoreParentFolder);
    }

    /**
     * Checking that authentication fails if supplied with a keystore which
     * contains no certificates.
     */
    public void testSSLInputStreamCreationFromEmptyKeystore() throws Exception {
        final File nonExistentFile = new File("thisFileDoesNotExist"); //$NON-NLS-1$
        assertFalse("File found to exist which should not exist", //$NON-NLS-1$
                nonExistentFile.exists());
        InputStream sslUncertifiedInputStream = null;
        try {
            sslUncertifiedInputStream = CertificateManager.getSSLInputStream(
                    new java.net.URI(wsdlSourceURL),
                    nonExistentFile);
            assertNotNull(
                    "CertificateManager.getSSLInputStream not expected to return null", //$NON-NLS-1$
                    sslUncertifiedInputStream);
            fail("Should have thrown an exception because we did not claim to trust this uri"); //$NON-NLS-1$
        } catch (Throwable e) {
            // trust anchors error should be caught here
            // do nothing - this is the expected behaviour
        } finally {
            if (sslUncertifiedInputStream != null) // it should be null, but
            // just in case it isn't
            {
                sslUncertifiedInputStream.close();
            }
        }
    }

    public void testCertificateConfirmationWithGarbageParameters()
            throws Exception {
        final java.net.URI nonexistentSourceURI =
                new java.net.URI("https://www.doesntexistsdflkjhasdklfjhl.com"); //$NON-NLS-1$
        final File nonexistentFile = new File("kjhkjh98y7987kljl"); //$NON-NLS-1$
        assertFalse(nonexistentFile.exists());
        CertificateAcceptanceTracker nonAcceptingTracker =
                new CertificateAcceptanceTracker() {
                    @Override
                    public boolean shouldAccept(X509Certificate[] chain,
                            String urlString, String[] existingAliases) {
                        return false;
                    }

                    @Override
                    public X509Certificate getTrustedCertificate() {
                        return null;
                    }

                    @Override
                    public String getAlias() {
                        return null;
                    }
                };
        try {
            CertificateManager.checkCertificate(nonexistentSourceURI,
                    nonexistentFile,
                    nonAcceptingTracker);
            fail("Should have thrown and exception when given garbage parameters"); //$NON-NLS-1$
        } catch (Throwable exception) {
            // correct
        }
        assertFalse("Certificate should have been rejected", //$NON-NLS-1$
                nonAcceptingTracker.isTrusted());
    }

    // TODO JA: this test is disabled (method name prefixed with '_') because it
    // fails due
    // to missing service. This needs to be revisited and fixed or removed.
    public void _testCertificateConfirmationWithCertificateRejection()
            throws Exception {
        // it doesn't matter whether this file exists or not, it will simply be
        // treated as an empty keystore
        final File emptyKeyStoreFile =
                new File(keyStoreFolder, "empty_cacerts"); //$NON-NLS-1$
        CertificateAcceptanceTracker nonAcceptingTracker =
                new CertificateAcceptanceTracker() {
                    @Override
                    public boolean shouldAccept(X509Certificate[] chain,
                            String urlString, String[] existingAliases) {
                        return false;
                    }

                    @Override
                    public X509Certificate getTrustedCertificate() {
                        return null;
                    }

                    @Override
                    public String getAlias() {
                        return null;
                    }
                };
        CertificateManager.checkCertificate(new java.net.URI(wsdlSourceURL),
                emptyKeyStoreFile,
                nonAcceptingTracker);
        assertFalse(
                "Unknown certificate rejected by the user should not have been accepted", //$NON-NLS-1$
                nonAcceptingTracker.isTrusted());
    }

    // TODO JA: this test is disabled (method name prefixed with '_') because it
    // fails due
    // to missing service. This needs to be revisited and fixed or removed.
    public void _testCertificateConfirmationWithAlreadyTrustedCertificate()
            throws Exception {
        final File singleCertificateFile = new File(keyStoreFolder, "cacerts"); //$NON-NLS-1$
        assertTrue("single certificate file does not exist", //$NON-NLS-1$
                singleCertificateFile.exists());
        CertificateAcceptanceTracker nonAcceptingTracker =
                new CertificateAcceptanceTracker() {
                    @Override
                    public boolean shouldAccept(X509Certificate[] chain,
                            String urlString, String[] existingAliases) {
                        return false;
                    }

                    @Override
                    public X509Certificate getTrustedCertificate() {
                        return null;
                    }

                    @Override
                    public String getAlias() {
                        return null;
                    }
                };
        CertificateManager.checkCertificate(new java.net.URI(wsdlSourceURL),
                singleCertificateFile,
                nonAcceptingTracker);
        assertTrue(
                "Expected non-accepting tracker not to cause already trusted certificate to be rejected", //$NON-NLS-1$
                nonAcceptingTracker.isTrusted());
    }

    // TODO JA: this test is disabled (method name prefixed with '_') because it
    // fails due
    // to missing service. This needs to be revisited and fixed or removed.
    public void _testOnlySingleAcceptanceOfCertificateNecessaryAccrossMultipleConfirmationRequests()
            throws Exception {
        File keyStoreFile = new File(temporaryKeyStoreFolder
                .getFile("initiallyEmptyKeystore").getRawLocation() //$NON-NLS-1$
                .toOSString());
        assertFalse("Not expecting the keyStore file to already exist", //$NON-NLS-1$
                keyStoreFile.exists());
        final boolean[] callFlags = new boolean[] { false, false, false };
        final X509Certificate[] certificateHolder = new X509Certificate[1];
        CertificateAcceptanceTracker acceptingTracker =
                new CertificateAcceptanceTracker() {
                    @Override
                    public boolean shouldAccept(X509Certificate[] chain,
                            String urlString, String[] existingAliases) {
                        callFlags[0] = true;
                        assertTrue(chain.length > 0);
                        certificateHolder[0] = chain[0];
                        assertTrue(urlString != null);
                        assertTrue(existingAliases.length == 0);
                        return true;
                    }

                    @Override
                    public X509Certificate getTrustedCertificate() {
                        callFlags[1] = true;
                        return certificateHolder[0];
                    }

                    @Override
                    public String getAlias() {
                        callFlags[2] = true;
                        return "uniqueAliasName_" + System.currentTimeMillis(); //$NON-NLS-1$
                    }
                };
        CertificateManager.checkCertificate(new java.net.URI(wsdlSourceURL),
                keyStoreFile,
                acceptingTracker);
        assertTrue("shouldAccept was not called", callFlags[0]); //$NON-NLS-1$
        assertTrue("getTrustedCertificate was not called", callFlags[1]); //$NON-NLS-1$
        assertTrue("getAlias was not called", callFlags[2]); //$NON-NLS-1$

        CertificateAcceptanceTracker dontCallTracker =
                new CertificateAcceptanceTracker() {
                    @Override
                    public boolean shouldAccept(X509Certificate[] chain,
                            String urlString, String[] existingAliases) {
                        fail("certificate should already be trusted"); //$NON-NLS-1$
                        return false;
                    }

                    @Override
                    public X509Certificate getTrustedCertificate() {
                        fail("certificate should already be trusted"); //$NON-NLS-1$
                        return null;
                    }

                    @Override
                    public String getAlias() {
                        fail("certificate should already be trusted"); //$NON-NLS-1$
                        return null;
                    }
                };
        CertificateManager.checkCertificate(new java.net.URI(wsdlSourceURL),
                keyStoreFile,
                dontCallTracker);
    }

    // Other tests:
    // 1. Check that a server is accepted if other than the last certificate in
    // the chain is trusted
    // 2. Check that a non-empty keystore without a certificate match in the
    // chain is rejected
    // 3. Check that a keystore with a certificate and a server with a different
    // certificate is rejected
    // even if the two certificates share a common ancestor
    // 4. Check that a keystore containing a certificate which has been tampered
    // with causes
    // authentication of a trusted server to fail
    // 5. End to end test of wsdl import (commented out test)
    // 6. Import of wsdl from https server including references to xsd files on
    // same|other secured|non-secured servers
    // 7. Open a keystore after a certificate has been added and check that new
    // certificate is listed
    // 8. Check successful handling of import of a wsdl with circular references
    // 9. Check the CertificateManager can handle being given a duplicate alias
    // 10. Check acceptance of a system-trusted certificate (godaddy, verisign
    // or thawte)
    // 11. Check acceptance of a certificate which contains a system-trusted
    // certificate in its chain
    // 12. Check successful rollback of a partially completed import which
    // references a number of secured
    // servers, some (initially) which are trusted then others (subsequently)
    // which are not.
    // 13. Check correct handling of a trusted|untrusted server which is not
    // accessible
    // 14. Connection to a http|https server through a proxy
    // 15. Check import is rejected if the keystore or the certificate
    // acceptance tracker is null
}
