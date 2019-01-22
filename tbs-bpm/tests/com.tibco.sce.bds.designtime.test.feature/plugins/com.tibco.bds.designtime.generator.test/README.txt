README Notes
============

In order to run these tests they should be run as a JUnit Plugin-Test within Eclipse.

Make sure that the following settings are used:

1) The VM Args are at least "-Xms128m -Xmx512m -XX:MaxPermSize=256m"

2) That the workspace target for the test is:
E:\junitTest\bds-test-ws

This choice of workspace path is historic, reflecting this plug-in's former home in the N2 Test feature. Changing it would require updating of expected results.

Note: If your Computer does not have an E Drive then you can simulate one on windows
      by using the following command:
      	subst E: C:\Builder\E_Drive
      Where the "C:\Builder\E_Drive" can be any location on your computers drive.

      