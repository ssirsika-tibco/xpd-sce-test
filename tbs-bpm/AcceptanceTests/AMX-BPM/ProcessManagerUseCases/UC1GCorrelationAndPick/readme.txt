This project contains a number of processes, each testing a different scenario related to message correlation.

1) Message 1 (M1) to start a process instance. M2, to be received by an intermediate event, to correlate. [M1(initiate), M2(correlate)]

2) M1 and M2 as join-correlation starters: either can come in first to create a process instance, but the other message needs to arrive also for the process instance to continue past the first merge point. [M1(join), M2(join)]

3) Either M1 or M2 can start a process instance; i.e. BPEL Pick. [pick{M1(initiate), M2(initiate)}, M3(correlate)]

4) M1 to start a process instance. M2 or M3 (in a Pick) to correlate. [M1(initiate), pick{M2(correlate), M3(correlate)}]

5) Variation of #4 to use signal, timer, message events in the intermediate pick.

6) (*) Variation of #2 to include signal and timer events in the pick. (TBD until we support signal and timer event starters)

7) Variation of #1, using multiple correlation fields
