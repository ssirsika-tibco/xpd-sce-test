package com.tibco.xpd.om.modeler.subdiagram.part;

import java.util.List;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListenerImpl;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.ResourceUndoContext;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.ui.palette.PaletteCustomizer;
import org.eclipse.gef.ui.parts.DomainEventDispatcher;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.gmf.runtime.common.ui.services.marker.MarkerNavigationService;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.actions.ActionIds;
import org.eclipse.gmf.runtime.diagram.ui.internal.parts.PaletteToolTransferDropTargetListener;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramGraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDiagramDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocument;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.document.IDocumentProvider;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.eclipse.gmf.runtime.gef.ui.palette.customize.PaletteCustomizerEx;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorMatchingStrategy;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.ISaveablePart2;
import org.eclipse.ui.ISaveablesSource;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.Saveable;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.IShowInTargetList;
import org.eclipse.ui.part.ShowInContext;

import com.tibco.xpd.gmf.extensions.palette.SelectionToolExEx;
import com.tibco.xpd.om.core.om.NamedElement;
import com.tibco.xpd.om.core.om.OMPackage;
import com.tibco.xpd.om.core.om.Organization;
import com.tibco.xpd.om.modeler.subdiagram.navigator.OrganizationModelSubNavigatorItem;
import com.tibco.xpd.om.resources.ui.clipboard.LocalSelectionDropTargetListener;
import com.tibco.xpd.om.resources.ui.editor.IGotoObject;
import com.tibco.xpd.quickfixtooltip.api.QuickFixToolTipEnabledDomainEventDispatcher;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.ui.IRefreshableTitle;

//Don't put any generated NOT here! The Class definition line is custom and removing
//the "generated" and "generated NOT" line ensures that this line is not regenerated
//but the contents of the Class IS. Putting a "generated NOT" at the top
//will have the affect of blocking the whole class from being regenerated.
//We don't want the generator to overwrite the following line because we are
//extending our own custom class.
public class OrganizationModelSubDiagramEditor extends DiagramDocumentEditor
        implements ISaveablesSource, IGotoMarker, IGotoObject, ISaveablePart2, IRefreshableTitle {

	private OMDiagramUndoContext undoContext;

	private NameChangeListener nameChangeListener;

	private TransactionalEditingDomain editorEditingDomain;

	/**
	 * @generated
	 */
	public static final String ID = "com.tibco.xpd.om.modeler.subdiagram.part.OrganizationModelSubDiagramEditorID"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final String CONTEXT_ID = "com.tibco.xpd.om.modeler.subdiagram.ui.diagramContext"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public OrganizationModelSubDiagramEditor() {
		super(true);
	}

	/**
	 * @generated
	 */
	@Override
	protected String getContextID() {
		return CONTEXT_ID;
	}

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditorWithFlyOutPalette#createPaletteCustomizer()
	 * 
	 * @return
	 */
	@Override
	protected PaletteCustomizer createPaletteCustomizer() {
		return new PaletteCustomizerEx(getPreferenceStore()) {
			@Override
			public void applyCustomizationsToPalette(PaletteRoot paletteRoot) {
				super.applyCustomizationsToPalette(paletteRoot);

				/*
				 * XPD-3814 Override the default SelectionTool class with our
				 * class that shows properties view on object double-click.
				 */
				SelectionToolEntry selToolEntry = findSelectionToolEntry(paletteRoot
						.getChildren());
				if (selToolEntry != null) {
					selToolEntry.setToolClass(SelectionToolExEx.class);
				}

			}

			private SelectionToolEntry findSelectionToolEntry(
					List<Object> paletteChildren) {
				for (Object child : paletteChildren) {
					if (child instanceof SelectionToolEntry) {
						return (SelectionToolEntry) child;

					} else if (child instanceof PaletteContainer) {
						/* Recurs into drawers */
						SelectionToolEntry selToolEntry = findSelectionToolEntry(((PaletteContainer) child)
								.getChildren());

						if (selToolEntry != null) {
							return selToolEntry;
						}
					}
				}

				return null;
			}

		};

	}

	/**
	 * @generated
	 */
	@Override
	protected PaletteRoot createPaletteRoot(PaletteRoot existingPaletteRoot) {
		PaletteRoot root = super.createPaletteRoot(existingPaletteRoot);
		new OrganizationModelPaletteFactory().fillPalette(root);
		return root;
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);

		if (nameChangeListener == null) {
			nameChangeListener = new NameChangeListener();
			TransactionalEditingDomain editingDomain = getEditingDomain();
			if (editingDomain != null) {
				editingDomain.addResourceSetListener(nameChangeListener);
			}
		}
	}

	@Override
	protected void setPartName(String partName) {
		if (partName.indexOf('%') >= 0) {
			// This is possibly an URI encoded partName so decode it
			partName = URI.decode(partName);
		}

		/*
		 * If this Organization is openened from the history then need to mark
		 * is as read-only.
		 */
		IDocumentProvider provider = getDocumentProvider(getEditorInput());
		if (provider != null && provider.isReadOnly(getEditorInput())) {
			partName = String
					.format(
							Messages.OrganizationModelSubDiagramEditor_readOnlyTagged_editor_title,
							partName);
		}

		super.setPartName(partName);
	}

	@Override
	public void dispose() {
		if (nameChangeListener != null) {
			TransactionalEditingDomain editingDomain = getEditingDomain();
			if (editingDomain != null) {
				editingDomain.removeResourceSetListener(nameChangeListener);
			}
			nameChangeListener = null;
		}
		super.dispose();
	}

	@Override
	protected void initializeGraphicalViewer() {
		/*
		 * 29.06.09: When GMF2.1.3 was introduced in the target platform DND got
		 * broken. Whenever a OM element was dragged from the project explorer
		 * to the diagram an SWT exception occurred. This was because of the
		 * change to the GMF class ImageFileDropTargetListener which is
		 * registered in the super class (the previous version used to check for
		 * a FileTransfer and if there wasn't on would check for a
		 * LocalSelectionTransfer, the new version only checks for a
		 * FileTransfer). The workaround is to not call the super class and
		 * instead call the initializeGraphicalViewerContent which the
		 * super.super method does. The bugzilla report on this matter can be
		 * found at: https://bugs.eclipse.org/bugs/show_bug.cgi?id=249718
		 */

		// super.initializeGraphicalViewer();
		initializeGraphicalViewerContents();

		// Add listener for local selection transfers
		getDiagramGraphicalViewer().addDropTargetListener(
				new LocalSelectionDropTargetListener(
						getDiagramGraphicalViewer()));

		// Add a transfer drag target listener that is supported on
		// palette template entries whose template is a creation tool.
		// This will enable drag and drop of the palette shape creation
		// tools.
		getDiagramGraphicalViewer()
				.addDropTargetListener(
						new PaletteToolTransferDropTargetListener(
								getGraphicalViewer()) {
							@Override
							public boolean isEnabled(DropTargetEvent event) {
								// Set this so that we get the unexecutable icon when
								// mouse is over the canvas
								setEnablementDeterminedByCommand(true);
								return super.isEnabled(event);
							}
						});
	}

	@Override
	protected ScrollingGraphicalViewer createScrollingGraphicalViewer() {
		/*
		 * Create our own graphical viewer that supports the quickfix tooltip
		 * for problem decorators.
		 */
		return new OMGraphicalViewer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.xpd.bom.resources.ui.editor.IGotoObject#reveal(org.eclipse.
	 * emf.ecore.EObject)
	 */
	@Override
	public void reveal(EObject eo) {
		TreeIterator<EObject> content = getDiagram().eAllContents();
		while (content.hasNext()) {
			EObject ref = content.next();

			if (ref instanceof View) {
				if (((View) ref).getElement() != eo) {
					continue;
				}
				EditPart ep = (EditPart) getGraphicalViewer()
						.getEditPartRegistry().get(ref);
				getGraphicalViewer().select(ep);
				getGraphicalViewer().reveal(ep);
				return;
			}
		}
	}

	@Override
	public void doSetInput(IEditorInput input, boolean releaseEditorContents)
			throws CoreException {
		undoContext = null;
		super.doSetInput(input, releaseEditorContents);
	}

	@Override
	public String getTitleToolTip() {

		String toolTip = null;

		IEditorInput input = getEditorInput();
		if (input instanceof URIEditorInput) {
			URI uri = ((URIEditorInput) input).getURI();
			if (uri.isPlatformResource()) {
				toolTip = uri.toPlatformString(true);
				if (toolTip.startsWith(String.valueOf(IPath.SEPARATOR))) {
					toolTip = toolTip.substring(1);
				}
			}
		}

		if (toolTip == null) {
			Diagram diagram = getDiagram();
			URI uri = EcoreUtil.getURI(diagram);

			EObject element = diagram.getElement();

			if (element instanceof Organization) {
				Organization org = (Organization) element;
				toolTip = uri.lastSegment() + "#" + org.getDisplayName(); //$NON-NLS-1$
			} else {
				toolTip = super.getTitleToolTip();
			}
		}

		return toolTip != null ? toolTip : super.getTitleToolTip();
	}

	/**
	 * @generated
	 */
	@Override
	protected PreferencesHint getPreferencesHint() {
		return OrganizationModelSubDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT;
	}

	/**
	 * @generated
	 */
	@Override
	public String getContributorId() {
		return OrganizationModelSubDiagramEditorPlugin.ID;
	}

	/**
	 * @generated
	 */
	@Override
	public Object getAdapter(Class type) {
		if (type == IShowInTargetList.class) {
			return new IShowInTargetList() {
				@Override
                public String[] getShowInTargetIds() {
					return new String[] { ProjectExplorer.VIEW_ID };
				}
			};
		}
		return super.getAdapter(type);
	}

	@Override
	protected IUndoContext getUndoContext() {
		if (undoContext == null) {
			/*
			 * Generate an undo context for this editor (diagram)
			 */
			Diagram diagram = getDiagram();
			if (diagram != null && diagram.eResource() != null) {
				String fragment = diagram.eResource().getURIFragment(diagram);

				if (fragment != null) {
					undoContext = new OMDiagramUndoContext(diagram.eResource(),
							fragment);
				}
			}
		}

		return undoContext;
	}

	@Override
	protected boolean shouldAddUndoContext(IUndoableOperation operation) {
		/*
		 * The default implementation of this adds this editor's context to all
		 * operation on the same editing domain as it was designed for multiple
		 * editing domains. As we use a single editing domain we should not add
		 * this editor's undo context (the correct context will already be
		 * applied to the operation).
		 */
		return false;
	}

	/**
	 * @generated
	 */
	@Override
	protected IDocumentProvider getDocumentProvider(IEditorInput input) {
		if (input instanceof IFileEditorInput
				|| input instanceof URIEditorInput) {
			return OrganizationModelSubDiagramEditorPlugin.getInstance()
					.getDocumentProvider();
		}
		return super.getDocumentProvider(input);
	}

	/**
	 * @generated NOT
	 */
	@Override
	public TransactionalEditingDomain getEditingDomain() {
		if (editorEditingDomain == null) {
			IDocument document = (getEditorInput() != null && getDocumentProvider() != null) ? getDocumentProvider()
					.getDocument(getEditorInput())
					: null;
			if (document instanceof IDiagramDocument) {
				editorEditingDomain = ((IDiagramDocument) document)
						.getEditingDomain();
			} else {
				editorEditingDomain = super.getEditingDomain();
			}

		}
		return editorEditingDomain;
	}

	/**
	 * @generated
	 */
	@Override
	protected void setDocumentProvider(IEditorInput input) {
		if (input instanceof IFileEditorInput
				|| input instanceof URIEditorInput) {
			setDocumentProvider(OrganizationModelSubDiagramEditorPlugin
					.getInstance().getDocumentProvider());
		} else {
			super.setDocumentProvider(input);
		}
	}

	/**
	 * @generated
	 */
	@Override
	public void gotoMarker(IMarker marker) {
		MarkerNavigationService.getInstance().gotoMarker(this, marker);
	}

	/**
	 * @generated
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	/**
	 * @generated
	 */
	@Override
	public void doSaveAs() {
		performSaveAs(new NullProgressMonitor());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.ISaveablesSource#getActiveSaveables()
	 */
	@Override
	public Saveable[] getActiveSaveables() {
		IDocumentProvider documentProvider = getDocumentProvider(getEditorInput());

		if (documentProvider instanceof OrganizationModelDocumentProvider) {
			return ((OrganizationModelDocumentProvider) documentProvider)
					.getSaveables(getEditorInput());
		}

		return new Saveable[0];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.ISaveablesSource#getSaveables()
	 */
	@Override
	public Saveable[] getSaveables() {
		return getActiveSaveables();
	}

	/**
	 * @generated
	 */
	@Override
	protected void performSaveAs(IProgressMonitor progressMonitor) {
		Shell shell = getSite().getShell();
		IEditorInput input = getEditorInput();
		SaveAsDialog dialog = new SaveAsDialog(shell);
		IFile original = input instanceof IFileEditorInput ? ((IFileEditorInput) input)
				.getFile()
				: null;
		if (original != null) {
			dialog.setOriginalFile(original);
		}
		dialog.create();
		IDocumentProvider provider = getDocumentProvider();
		if (provider == null) {
			// editor has been programmatically closed while the dialog was open
			return;
		}
		if (provider.isDeleted(input) && original != null) {
			String message = NLS
					.bind(
							Messages.OrganizationModelSubDiagramEditor_SavingDeletedFile,
							original.getName());
			dialog.setErrorMessage(null);
			dialog.setMessage(message, IMessageProvider.WARNING);
		}
		if (dialog.open() == Window.CANCEL) {
			if (progressMonitor != null) {
				progressMonitor.setCanceled(true);
			}
			return;
		}
		IPath filePath = dialog.getResult();
		if (filePath == null) {
			if (progressMonitor != null) {
				progressMonitor.setCanceled(true);
			}
			return;
		}
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IFile file = workspaceRoot.getFile(filePath);
		final IEditorInput newInput = new FileEditorInput(file);
		// Check if the editor is already open
		IEditorMatchingStrategy matchingStrategy = getEditorDescriptor()
				.getEditorMatchingStrategy();
		IEditorReference[] editorRefs = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.getEditorReferences();
		for (int i = 0; i < editorRefs.length; i++) {
			if (matchingStrategy.matches(editorRefs[i], newInput)) {
				MessageDialog
						.openWarning(
								shell,
								Messages.OrganizationModelSubDiagramEditor_SaveAsErrorTitle,
								Messages.OrganizationModelSubDiagramEditor_SaveAsErrorMessage);
				return;
			}
		}
		boolean success = false;
		try {
			provider.aboutToChange(newInput);
			getDocumentProvider(newInput).saveDocument(progressMonitor,
					newInput,
					getDocumentProvider().getDocument(getEditorInput()), true);
			success = true;
		} catch (CoreException x) {
			IStatus status = x.getStatus();
			if (status == null || status.getSeverity() != IStatus.CANCEL) {
				ErrorDialog
						.openError(
								shell,
								Messages.OrganizationModelSubDiagramEditor_SaveErrorTitle,
								Messages.OrganizationModelSubDiagramEditor_SaveErrorMessage,
								x.getStatus());
			}
		} finally {
			provider.changed(newInput);
			if (success) {
				setInput(newInput);
			}
		}
		if (progressMonitor != null) {
			progressMonitor.setCanceled(!success);
		}
	}

	/**
	 * @generated
	 */
	@Override
	public ShowInContext getShowInContext() {
		return new ShowInContext(getEditorInput(), getNavigatorSelection());
	}

	/**
	 * @generated NOT
	 */
	private ISelection getNavigatorSelection() {
		IDiagramDocument document = getDiagramDocument();
		if (document == null) {
			return StructuredSelection.EMPTY;
		}
		Diagram diagram = document.getDiagram();

		if (diagram != null && diagram.eResource() != null) {
			IFile file = WorkspaceSynchronizer.getFile(diagram.eResource());
			if (file != null) {
				OrganizationModelSubNavigatorItem item = new OrganizationModelSubNavigatorItem(
						diagram, file, false);
				return new StructuredSelection(item);
			}
		}
		return StructuredSelection.EMPTY;
	}

	/**
	 * @generated
	 */
	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		DiagramEditorContextMenuProvider provider = new DiagramEditorContextMenuProvider(
				this, getDiagramGraphicalViewer());
		getDiagramGraphicalViewer().setContextMenu(provider);
		getSite().registerContextMenu(ActionIds.DIAGRAM_EDITOR_CONTEXT_MENU,
				provider, getDiagramGraphicalViewer());
	}

	/**
	 * Undo context used by the sub-diagram so that we can have separate undo
	 * threads for each diagram (the default is a single thread for the resource
	 * being edited but the resource will have multiple diagrams).
	 * 
	 * @author njpatel
	 * 
	 */
	private class OMDiagramUndoContext implements IUndoContext {

		private final Resource resource;

		private final String diagramId;

		public OMDiagramUndoContext(Resource resource, String diagramId) {
			this.resource = resource;
			this.diagramId = diagramId;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.core.commands.operations.IUndoContext#getLabel()
		 */
		@Override
		public String getLabel() {
			return String.format(
					"OM Diagram undo context (res: %1$s, diagram: %2$s", //$NON-NLS-1$
					resource.toString(), diagramId);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.core.commands.operations.IUndoContext#matches(org.eclipse
		 * .core.commands.operations.IUndoContext)
		 */
		@Override
		public boolean matches(IUndoContext context) {
			if (context == this) {
				return true;
			}
			if (context instanceof OMDiagramUndoContext) {
				OMDiagramUndoContext other = (OMDiagramUndoContext) context;
				return diagramId.equals(other.diagramId)
						&& resource.equals(other.resource);
			} else if (context instanceof ResourceUndoContext) {
				/*
				 * Should also match the resource undo context of this diagram's
				 * resource.
				 */
				return resource.equals(((ResourceUndoContext) context)
						.getResource());
			}
			return false;
		}
	}

	/**
	 * Extension of the diagram graphical viewer that includes the quick fix
	 * tooltip.
	 * 
	 * @author njpatel
	 * 
	 */
	private class OMGraphicalViewer extends DiagramGraphicalViewer {
		private QuickFixToolTipEnabledDomainEventDispatcher stickyToolTipEnabledDomainEventDispatcher;

		@Override
		public void setEditDomain(EditDomain domain) {
			super.setEditDomain(domain);
			stickyToolTipEnabledDomainEventDispatcher = new QuickFixToolTipEnabledDomainEventDispatcher(
					domain, this);
			getLightweightSystem().setEventDispatcher(
					stickyToolTipEnabledDomainEventDispatcher);
		}

		@Override
		protected DomainEventDispatcher getEventDispatcher() {
			/*
			 * Override to set our own domain event dispatcher, doing so seems
			 * to be the ONLY way of providing a different ToolTipHelper which
			 * seems to be the only way of providing a way of not destroying the
			 * tooltip if the mouse is moved awy from original host figure and
			 * over the tooltip.
			 * 
			 * We overrode the domain event dispatcher so we should get
			 * underlying class to use ours.
			 */
			return stickyToolTipEnabledDomainEventDispatcher;
		}
	}

	/**
	 * 
	 * A resource listener that responds to changes in the Organizations
	 * DisplayName by updating the Editor's title.
	 * 
	 * 
	 * @author rgreen
	 * 
	 */
	private class NameChangeListener extends ResourceSetListenerImpl {

		@Override
		public boolean isPostcommitOnly() {
			return true;
		}

		@Override
		public void resourceSetChanged(ResourceSetChangeEvent event) {
			Diagram diag = getDiagram();

			if (diag != null) {
				EObject eo = diag.getElement();

				if (eo != null) {
					for (Notification notification : event.getNotifications()) {
						if (notification.getNotifier() == eo
								&& notification
										.getFeature()
										.equals(
												OMPackage.Literals.NAMED_ELEMENT__DISPLAY_NAME)) {
							setPartName(((NamedElement) eo).getDisplayName());
						}
					}
				}
			}
		}
	}

	@Override
	public int promptToSaveOnClose() {
		/*
		 * Don't prompt the user to save on editor close when running in RCP
		 * application
		 */
		if (XpdResourcesPlugin.isRCP()) {
			return NO;
		}

		return DEFAULT;
	}

    /**
     * @see com.tibco.xpd.resources.ui.IRefreshableTitle#refreshTitle()
     *
     */
    @Override
    public void refreshTitle() {
        setPartName(getDiagram().getName());
    }

}
