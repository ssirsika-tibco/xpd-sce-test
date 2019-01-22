package com.tibco.bx.emulation.ui.properties;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.OperationHistoryFactory;

import com.tibco.bx.emulation.ui.EmulationUIActivator;

public class OperationRunnable implements Runnable
  {
    private IUndoableOperation operation;
    
    public void run()
    {
    	IUndoableOperation localOperation = popOperation();
      if (localOperation != null) {
    	 try {
			OperationHistoryFactory.getOperationHistory().execute(localOperation, null, null);
    	 } catch (ExecutionException e) {
				EmulationUIActivator.log(e);
    	 }
      }
    }

    public void setOperation(IUndoableOperation operation)
    {
      this.operation = operation;
    }

    public IUndoableOperation getOperation()
    {
      return operation;
    }

    public IUndoableOperation popOperation()
    {
    	IUndoableOperation localOperation = this.operation;
      this.operation = null;
      return localOperation;
    }
  }
