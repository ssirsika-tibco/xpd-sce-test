/*
 * Copyright (c) TIBCO Software Inc 2004, 2019. All rights reserved.
 */

package com.tibco.xpd.rasc.ui.export;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.tibco.xpd.rasc.core.RascController;

/**
 * RascExportOperation test.
 *
 * @author nwilson
 * @since 12 Mar 2019
 */
public class RascExportOperationTest {

    @Mock
    private IProgressMonitor monitor;

    @Mock
    private RascController controller;

    @Mock
    private ExportProgressMonitorDialog dialog;

    @Mock
    private IProject project;

    @Mock
    private IFolder projectFolder;

    @Mock
    private IFile rascProjectFile;

    @Mock
    private File rascSystemFile;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        when(project.getFolder(Mockito.anyString())).thenReturn(projectFolder);
        when(projectFolder.getFile(Mockito.anyString()))
                .thenReturn(rascProjectFile);
    }

    @Test
    public void testProjectRelative() {
        List<IProject> projects = new ArrayList<>();
        projects.add(project);
        RascExportOperation operation = new RascExportOperation(controller,
                dialog, projects, "Exports/RASC", true);
        try {
            operation.run(monitor);
            verify(controller).generateRasc(Matchers.eq(project),
                    Matchers.eq(rascProjectFile),
                    Matchers.any());
        } catch (InvocationTargetException | InterruptedException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSystemFolder() {
        List<IProject> projects = new ArrayList<>();
        projects.add(project);
        RascExportOperation operation = new RascExportOperation(controller,
                dialog, projects, "Exports/RASC", false);
        try {
            operation.run(monitor);
            verify(controller).generateRasc(Matchers.eq(project),
                    Matchers.any(File.class),
                    Matchers.any());
        } catch (InvocationTargetException | InterruptedException e) {
            fail(e.getMessage());
        }
    }
}
