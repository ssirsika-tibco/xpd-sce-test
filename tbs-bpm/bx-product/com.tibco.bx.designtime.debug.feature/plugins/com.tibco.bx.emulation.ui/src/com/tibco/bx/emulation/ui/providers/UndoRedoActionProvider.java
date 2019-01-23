package com.tibco.bx.emulation.ui.providers;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.ResourceUndoContext;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;
import org.eclipse.ui.operations.UndoRedoActionGroup;

import com.tibco.bx.emulation.core.resource.EMWorkingCopy;
import com.tibco.bx.emulation.model.provider.EmulationElementItemProvider;
import com.tibco.xpd.resources.XpdResourcesPlugin;
import com.tibco.xpd.resources.util.WorkingCopyUtil;

public class UndoRedoActionProvider extends CommonActionProvider{
	
	/*private UndoAction undoAction;
	private RedoAction redoAction;*/
	private final TransactionalEditingDomain editingDomain;
	private IWorkbenchPartSite site;
	private UndoRedoActionGroup undoRedoActionGroup;
    public UndoRedoActionProvider(){
    	this.editingDomain = XpdResourcesPlugin.getDefault().getEditingDomain();
    }

    public void init(ICommonActionExtensionSite site){
    	 super.init(site);

    	    ICommonViewerWorkbenchSite localICommonViewerWorkbenchSite = (ICommonViewerWorkbenchSite)site.getViewSite();
    	    this.site = localICommonViewerWorkbenchSite.getSite();
    }

    public void fillActionBars(IActionBars actionBars){
    	IUndoContext localIUndoContext = getUndoContext();

        if ((localIUndoContext == null) || (this.site == null))
          return;
        if (this.undoRedoActionGroup != null) {
          this.undoRedoActionGroup.dispose();
        }

        this.undoRedoActionGroup = 
          new UndoRedoActionGroup(this.site, localIUndoContext, true);
        this.undoRedoActionGroup.setContext(getContext());
        this.undoRedoActionGroup.fillActionBars(actionBars);
    }

    
    private IUndoContext getUndoContext() {
        Object localObject1 = null;

        if ((getContext() != null) && 
          (getContext().getSelection() instanceof IStructuredSelection)) {
          IStructuredSelection localIStructuredSelection = 
            (IStructuredSelection)getContext().getSelection();

          if (localIStructuredSelection.size() == 1) {
            Resource localResource = null;

            Object localObject2 = localIStructuredSelection.getFirstElement();
            Object localObject3;
            if (localObject2 instanceof EmulationElementItemProvider) {
              localObject3 = 
              WorkingCopyUtil.getWorkingCopyFor((EObject)((EmulationElementItemProvider)localObject2).getTarget());
              if (localObject3 != null)
                localObject1 = ((EMWorkingCopy)localObject3).getUndoContext();
            }
            else {
              if (localObject2 instanceof EObject) {
                localResource = ((EObject)localObject2).eResource();
              } else if (localObject2 instanceof IFile) {
                localObject3 = 
                  URI.createFileURI(((IFile)localObject2).getFullPath()
                  .toString());
                localResource = 
                  this.editingDomain.getResourceSet().getResource((URI)localObject3, 
                  false);
              }

              if (localResource != null) {
                localObject1 = 
                  new ResourceUndoContext(this.editingDomain, localResource);
              }
            }
          }
        }

        return (IUndoContext)(IUndoContext)localObject1;
      }
    
    
}
