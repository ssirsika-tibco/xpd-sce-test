qftest -batch -compact -runlogdir .\results\logs -report.html .\results\html -report.junit .\results\junit -serverhost localhost .\AllStudioFeatureTests.qft .\AllStudioFunctionalTests.qft .\AllStudioMiscellaneousTests.qft .\AllAnalystTests.qft

@echo off
set result=%errorlevel%

echo QF-Test result: %result%

rem Adapt result to return 0 in case of a successful run 
rem with results OK, Warning, Error, Exception.
rem Only fatal faults shall lead to a build error.
if %result% geq 1 if %result% leq 3 (
  set result=0
)

echo Batch result: %result%
exit /B %result%