NOTE: SID 30/03/10 
This User Acceptance Test was created at a time (25/03/2010) where it would not actually execute on the 
BPM server because of BX-846 (http://jira.tibco.com:8080/browse/BX-846).

Note that the process FudgedUC1PcMutliInstanceParallelLoop is a version of the process that works 
around all the errors and hence should complete from start to finish.

The Gold DAA should however only be created when BX-846 is fixed and the official UC1PcMutliInstanceParallelLoop 
works correctly (at which time FudgedUC1PcMutliInstanceParallelLoop should be removed).
 