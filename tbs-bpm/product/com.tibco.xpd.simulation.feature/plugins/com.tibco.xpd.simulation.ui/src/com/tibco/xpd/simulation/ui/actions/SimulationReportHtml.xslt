<?xml version="1.0" encoding="UTF-8"?>
<!--
===================================================================================================================
XSLT:		SimulationReport.xslt

DESCRIPTION:
	This xslt presents the simulation report in a HTML form.

===================================================================================================================
-->

<!--
=================================================================================================================== 
	Define &nbsp; 
===================================================================================================================
-->
<!DOCTYPE xsl:stylesheet [
	<!ENTITY nbsp  '<xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>'>
]>


<xsl:stylesheet version="1.0" 
	xmlns="http://www.w3.org/1999/xhtml" 
	xmlns:html="http://www.w3.org/1999/xhtml"
	xmlns:java="http://xml.apache.org/xslt/java"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:SimulationReport="http://www.tibco.com/models/SimulationReport.xsd" 
	 exclude-result-prefixes="html xsl"
>
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" />
	
<!--
===================================================================================================================
	XSLT processing start point (process whole document)
===================================================================================================================
-->
	<xsl:template match="/">
		<xsl:apply-templates select="SimulationReport:DocumentRoot"/>
	</xsl:template>
	<xsl:template match="SimulationReport:DocumentRoot" >

		<xsl:text disable-output-escaping="yes">&lt;!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"&gt;</xsl:text>

		<html xmlns="http://www.w3.org/1999/xhtml" >
			<head>
				<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
				<title>TIBCO Business Studio - Simulation Report</title>
				<link rel="stylesheet" href="Style/simrep.css" type="text/css"/>				
				<script type="text/javascript">
					<xsl:text disable-output-escaping="yes">
						
						//The view titles
						var PRINTVIEWTITLE = "[Switch to Print View]";
						var NORMALVIEWTITLE = "[Switch to Normal View]";
						
						//View cookie settings
						var PRINT = "print";
						var NORMAL = "normal";
						
						var lastSelection = null;
						
						//Function called when the page is loaded or refreshed - this will update the view to the
						//last selected one - print or normal.  If normal view the last selected option will also be selected
						function updateView () {
						
							//If current view is not set then set it to normal view
							if (getCookie ("view") == "")
								setCookie ("view", NORMAL);
						
							//If no option selected then set the experiment data as the default selection
							if (getCookie ("option") == "")
								setCookie ("option", "experimentdata");
								
							//Show the selected view
							showView();
						}
						
						//Function will update the (normal) view when an option in the tree is selected
						function updateDetails (detailsID) {
						
							//If in normal view then update the frame with the selected option
							if (getCookie ("view") == NORMAL) {
								var frameDetails = parent.window.document.getElementById ("framedetails");
								var srcData = parent.window.document.getElementById (detailsID);
								var linkObj = document.getElementById ("lnk" + detailsID);
						
								if (frameDetails &amp;&amp; srcData) {
									frameDetails.innerHTML = srcData.innerHTML;
						
									if (linkObj) {		
										linkObj.className = "option hilit";
						
										if (lastSelection)
											lastSelection.className = "option";
								
										//update last selection
										lastSelection = linkObj;
									}
						
									//Update cookie with option selection
									setCookie ("option", detailsID);
						
									// Expand the parent option if not expanded
									expandParent (linkObj.parentNode);
								}
							} else {
								//In print view so just go to the selected item by updating the
								//reference in the URL
								var url = document.location.href.split ('#');
								document.location.href = url[0] + "#" + detailsID;
							}
						}
						
						//Expand the parent of the current node in the tree view
						function expandParent (obj) {
						
							if (obj) {
								//Find the parent table node and if it's not displayed then set it visible
								if (obj.tagName == "TABLE" &amp;&amp; obj.style.display == "none") {
									//Get the image object and update it
									var imgObj = document.getElementById ("img" + obj.id.substring (3));
						
									if (imgObj)
										imgObj.src = "images/op_minus.gif";
						
									obj.style.display = "block";
								}
								else if (obj.tagName != "DIV")	//stop when DIV is met				
									expandParent (obj.parentNode);
							}
						}
						
						//Expand the option menu to show the suboptions
						function expandOption (img, id) {
							var subOptions = document.getElementById (id);
								
							if (subOptions.style.display == "none") {
								subOptions.style.display = "block";
								img.src = "images/op_minus.gif";
								img.title = "Collapse";
							} else {
								subOptions.style.display = "none";
								img.src = "images/op_plus.gif";
								img.title = "Expand";
							}
						}
						
					function switchView (obj) {
							//If in normal view then switch to print view, and vice versa
							if (getCookie ("view") == NORMAL) {
								//switch to print view
								setCookie ("view", PRINT);
								//update link title
								obj.innerHTML = NORMALVIEWTITLE;
							} else {
								//switch to normal view
								setCookie ("view", NORMAL);
								//update link title
								obj.innerHTML = PRINTVIEWTITLE;
							}
							//Show the selected view
							showView();
						}
						
						//Show the selected view - print or normal
						function showView () {
							//Get the views
							var normView = document.getElementById ("normalview");
							var printView = document.getElementById ("printview");
							
							if (getCookie ("view") == NORMAL) {
								//Show normal view
								printView.style.display = "none";
								normView.style.display = "block";
						
								//Select the last selected option
								if (getCookie ("option") != "") {
									updateDetails (getCookie ("option"));
						
									//hilit the last selected option
									document.getElementById ("lnk" + getCookie ("option")).className = "option hilit";
								}
							} else {	
								//Show print view
								normView.style.display = "none";
								printView.style.display = "block";
							}
						}
						
						//function to set cookie
						function setCookie (szName, szValue)
						{
							var szCookie = "";
							
							//only proceed if we have all required data
							if (szName != null &amp;&amp; szValue != null &amp;&amp; szName != "" &amp;&amp; szValue != "")
							{	
								//set cookie
								szCookie = szName + "=" + escape (szValue);
										
								//set the cookie
								document.cookie=szCookie;
							}
						}
						
						//function to get the value of a cookie param
						function getCookie (szName)
						{
							//cookies are separated by ;
							var aParams = document.cookie.split("; ");
							var aPart;
							var szRetVal = "";
						
							//search for required cookie
							for (var idx = 0; idx &lt; aParams.length; idx++)
							{
								//name/value pair is separated by an =
								aPart = aParams[idx].split ("=");
								if (szName == aPart[0])
									szRetVal = unescape (aPart[1]);
							}
							
							return szRetVal;
						}
						
						//Format the xsl datetime into a generic date time display
						function formatDate (xslDateTime) {
							var dateTimeSplit = xslDateTime.split ('T');
							var dateSplit = dateTimeSplit[0].split ('-');
							
							//change format to dd/mm/yyyy hh:mm:ss.s
							return (dateSplit[2] + "/" + dateSplit[1] + "/" + dateSplit[0] + 
								" "  + dateTimeSplit[1]);;
						}

					</xsl:text>
				</script>
			</head>
			<body onload="updateView();">
			
				<!-- option to change view -->
				<table class="smallprint">
					<tbody>
						<tr>
							<td align="right" class="noBorder">
								<a id="lnkview" class="link" title="Change view" onclick="switchView (this)">
									<script type="text/javascript">
										//Update the link title based on current view setting
										if (getCookie ("view") == NORMAL || getCookie ("view") == "")
											document.write (PRINTVIEWTITLE);
										else
											document.write (NORMALVIEWTITLE);
										
									</script>
								</a>
							</td>
						</tr>
					</tbody>
				</table>
				<!-- Header -->
				<table class="mainTitle">
					<tbody>
						<tr>
							<td class="noBorder">
								Simulation:
							</td><td class="noBorder" align="center">
								<xsl:value-of select="experiment/@name" />
							</td>
							<td class="noBorder" align="right"><img src="images/TibcoLogo.gif" alt="TIBCO&#174; Software Inc." title="TIBCO&#174; Software Inc."/></td>
						</tr>
					</tbody>
				</table>
				<!-- Header - END -->	
				
				<!-- Add simulation information -->
				<xsl:apply-templates select="experiment" />
				
				<div id="normalview">
					<table>
						<tbody>
							<tr>
								<td class="noBorder" width="25%" nowrap="1" valign="top">
									<br/>
									<div id="frameoptions" >
										<!-- Build tree view -->
										<xsl:apply-templates select="experiment" mode="treeOptions" />
									</div>
								</td>
								<td class="noBorder" nowrap="1" valign="top">
									<div id="framedetails">
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>			
			
				<div id="printview" style="display:none">
					
					<!-- Add experiment specific parameters -->
					<xsl:apply-templates select="experiment/experimentData" />
					
					<!-- Add cases connected statistics -->
					<xsl:apply-templates select="experiment/cases" />
						
					<!-- Add participants -->
					<xsl:apply-templates select="experiment/participants" />
						
					<!-- Add activities -->
					<xsl:apply-templates select="experiment/activities" />
				
				</div>
				
			</body>
		</html>

	</xsl:template>
	
<!--
===================================================================================================================
	Experiment = The simulation details.
===================================================================================================================
-->		
	<xsl:template match="experiment">
	
		<table>
			<tbody>
				<tr>
					<th class="heading">Name</th><td><xsl:value-of select="@name" /></td>
				</tr><tr>
					<th class="heading">Package Name</th><td><xsl:value-of select="@packageName" /></td>
				</tr><tr>
					<th class="heading">ProcessName</th><td><xsl:value-of select="@processName" /></td>
				</tr>
			</tbody>
		</table>
		
	</xsl:template>
	
<!--
===================================================================================================================
	Experiment (treeOptions) = This will create the tree selection view in the normal view.
===================================================================================================================
-->	
	<xsl:template match="experiment" mode="treeOptions">

		<table>
			<tbody>
				<!-- Experiment Data -->
				<xsl:if test="experimentData">
					<tr>
						<td colspan="3" id="optexperimentdata">
							<img id="imgexperimentdata" alt="no expand icon" title="" src="images/op_noexpand.gif"></img>
							<a id="lnkexperimentdata" class="option" onclick="updateDetails ('experimentdata');" >Experiment Data</a>
						</td>
					</tr>
				</xsl:if>
				<!-- Cases -->
				<xsl:if test="cases">
					<tr>
						<td colspan="3" id="optcases">
							<img id="imgcases" alt="no expand icon" title="" src="images/op_noexpand.gif"></img>
							<a id="lnkcases" class="option" onclick="updateDetails ('cases');">Cases</a>
						</td>
					</tr>
				</xsl:if>
			
				<!-- Participants -->
				<xsl:if test="participants">
					<tr>
						<td id="optparticipants" colspan="3">
							<img id="imgparticipants" class="link" src="images/op_plus.gif" alt="Expand icon" title="Expand" onclick="expandOption (this, 'subparticipants');"></img>
							<a id="lnkparticipants" class="option" onclick="updateDetails ('participants');">Participants</a>
							
							<table id="subparticipants" style="display:none">
								<tbody>
									<!-- Participants -> Participant -->
									<xsl:for-each select="participants/participant">
										<tr>
											<td class="noBorder subsection">
												<xsl:attribute name="id">optparticipant_<xsl:value-of select="@id" /></xsl:attribute>
												<img src="images/op_noexpand.gif" alt="no expand icon" title="">
													<xsl:attribute name="id">imgparticipant_<xsl:value-of select="@id" /></xsl:attribute>
												</img>	
												<a class="option">
													<xsl:attribute name="id">lnkparticipant_<xsl:value-of select="@id" /></xsl:attribute>
													<xsl:attribute name="onclick">updateDetails ('participant_<xsl:value-of select="@id" />');</xsl:attribute>
													<xsl:value-of select="@name" />
												</a>
											</td>
										</tr>
									</xsl:for-each>
								</tbody>
							</table>
							
						</td>
					</tr>
				</xsl:if>
				
				<!-- Activities -->
				<xsl:if test="activities">
					<tr>
						<td colspan="3" id="optactivities">
							<img id="imgactivities" class="link" src="images/op_plus.gif" alt="Expand icon" title="Expand" onclick="expandOption (this, 'subactivities')"></img>
							<a id="lnkactivities" class="option" onclick="updateDetails ('activities');">Activities</a>
							
							<table id="subactivities" style="display:none">
								<tbody>
								
									<!-- Activities -> Activity -->
									<xsl:for-each select="activities/activity">
										<tr>
											<td class="noBorder subsection">
												<xsl:attribute name="id">optactivity_<xsl:value-of select="@id" /></xsl:attribute>
												<img src="images/op_noexpand.gif" alt="no expand icon" title="">
													<xsl:attribute name="id">imgactivity_<xsl:value-of select="@id" /></xsl:attribute>
												</img>
												<a class="option">
													<xsl:attribute name="id">lnkactivity_<xsl:value-of select="@id" /></xsl:attribute>
													<xsl:attribute name="onclick">updateDetails ('activity_<xsl:value-of select="@id" />');</xsl:attribute>
													<xsl:value-of select="@name" />
												</a>
											</td>
										</tr>
										
									</xsl:for-each>
									
								</tbody>
							</table>
						</td>
					</tr>
					
				</xsl:if>
			
			</tbody>
		</table>
	
	</xsl:template>
	
<!--
===================================================================================================================
	ExperimentData = Experiment specific parameters
===================================================================================================================
-->
<xsl:template match="experimentData">
	<div id="experimentdata" class="section">
		<table>
			<thead>
				<tr>
					<th class="sectionTitle" colspan="2">Experiment Data</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th class="heading">Experiment State</th><td>&nbsp; <xsl:value-of select="@experimentState" /></td>
				</tr><tr>
					<th class="heading">Simulation Time</th><td>&nbsp; <xsl:value-of select="java:com.tibco.xpd.simulation.ui.Messages.formatNumber(@simulationTime,2)" /></td>
				</tr><tr>
					<th class="heading">Reference Time Unit</th><td>&nbsp; <xsl:value-of select="@referenceTimeUnit" /></td>
				</tr><tr>
					<th class="heading">Reference Start Time</th>					
					<td>&nbsp;<script type="text/javascript">document.write (formatDate ("<xsl:value-of select="@referenceStartTime" />"));</script></td>
				</tr><tr>
					<th class="heading">Real Time</th>
					<td>&nbsp;<script type="text/javascript">document.write (formatDate ("<xsl:value-of select="@realTime" />"));</script></td>
				</tr><tr>
					<th class="heading">Reference Cost Unit</th><td>&nbsp; <xsl:value-of select="@referenceCostUnit" /></td>
				</tr>
				
			</tbody>
		</table>
	</div>
			
</xsl:template>
	
<!--
===================================================================================================================
	Cases = All cases connected statistics.
===================================================================================================================
-->	
	<xsl:template match="cases">
		
		<div id="cases" class="section">
			<table>
				<thead>
					<tr>
						<th colspan="2" class="sectionTitle">
								Cases
						</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th class="heading">Started</th><td>&nbsp; <xsl:value-of select="@startedCases" /></td>
					</tr><tr>
						<th class="heading">Finished</th><td>&nbsp; <xsl:value-of select="@finishedCases" /></td>
					</tr><tr>
						<th class="heading">Average Time</th><td>&nbsp; <xsl:value-of select="java:com.tibco.xpd.simulation.ui.Messages.formatNumber(@averageCaseTime,2)" /></td>
					</tr><tr>
						<th class="heading">Minimum Time</th><td>&nbsp; <xsl:value-of select="java:com.tibco.xpd.simulation.ui.Messages.formatNumber(@minCaseTime,2)" /></td>
					</tr><tr>
						<th class="heading">Maximum Time</th><td>&nbsp; <xsl:value-of select="java:com.tibco.xpd.simulation.ui.Messages.formatNumber(@maxCaseTime,2)" /></td>
					</tr>					
					<tr>						
						<th class="heading">Case Start Interval Distribution</th>
						<td>
							<xsl:choose>
								<xsl:when test="caseStartIntervalDistribution"><xsl:apply-templates select="caseStartIntervalDistribution" /></xsl:when>
								<xsl:otherwise>&nbsp;</xsl:otherwise>
							</xsl:choose>
						</td>
					</tr><tr>
						<th class="heading">Case Cost</th>
						<td>
							<xsl:choose>
								<xsl:when test="CaseCost"><xsl:apply-templates select="caseCost" /></xsl:when>
								<xsl:otherwise>&nbsp;</xsl:otherwise>
							</xsl:choose>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		
	</xsl:template>
	
<!--
===================================================================================================================
	CaseStartIntervalDistribution = Describes how frequently cases are started during the experiment.
===================================================================================================================
-->	
	<xsl:template match="caseStartIntervalDistribution">
		<div id="csid">
			<table>
				<tbody>
					<tr>
						<th class="heading">Last Reset Time</th><td>&nbsp; <xsl:value-of select="java:com.tibco.xpd.simulation.ui.Messages.formatNumber(@lastResetTime,1)" /></td>
					</tr><tr>
						<th class="heading">Observed Elements</th><td>&nbsp; <xsl:value-of select="@observedElements" /></td>
					</tr><tr>
						<th class="heading">Distribution Category</th><td>&nbsp; <xsl:value-of select="@distributionCategory" /></td>
					</tr><tr>
						<th class="heading">Parameter 1</th><td>&nbsp; <xsl:value-of select="java:com.tibco.xpd.simulation.ui.Messages.formatNumber(@parameter1,1)" /></td>
					</tr><tr>
						<th class="heading">Parameter 2</th><td>&nbsp; <xsl:value-of select="java:com.tibco.xpd.simulation.ui.Messages.formatNumber(@parameter2,1)" /></td>
					</tr><tr>
						<th class="heading">Seed</th><td>&nbsp; <xsl:value-of select="@seed" /></td>
					</tr>
				</tbody>
			</table>
		</div>
	</xsl:template>
	
<!--
===================================================================================================================
	CaseCost = Case cost statistic data.
===================================================================================================================
-->	
	<xsl:template match="caseCost">
		<div id="casecost">
			<table>
				<tbody>
					<tr>
						<th class="heading">Average Cost</th><td>&nbsp; <xsl:value-of select="java:com.tibco.xpd.simulation.ui.Messages.formatNumber(@averageCost,2)" /></td>
					</tr><tr>
						<th class="heading">Minimum Cost</th><td>&nbsp; <xsl:value-of select="java:com.tibco.xpd.simulation.ui.Messages.formatNumber(@minCost,2)" /></td>
					</tr><tr>
						<th class="heading">Maximum Cost</th><td>&nbsp; <xsl:value-of select="java:com.tibco.xpd.simulation.ui.Messages.formatNumber(@maxCost,2)" /></td>
					</tr><tr>
						<th class="heading">Cumulative Average Cost</th><td>&nbsp;<xsl:value-of select="java:com.tibco.xpd.simulation.ui.Messages.formatNumber(@cumulativeAverageCost,2)" /></td>
					</tr>
				</tbody>
			</table>
		</div>
	</xsl:template>
		
		
<!--
===================================================================================================================
	Participants = Participant group
===================================================================================================================
-->		
	<xsl:template match="participants">
		<div id="participants" class="section">
			<table>
				<thead>
					<tr>
						<th colspan="3" class="sectionTitle">Participants (<xsl:value-of select="count (participant)" />)</th>
					</tr>
					<tr>
						<th class="headingId"></th><th>Name</th>
					</tr>
				</thead>
				<tbody>
					<xsl:for-each select="participant">
						<tr class="link">
							<xsl:attribute name="onclick">updateDetails ('participant_<xsl:value-of select="@id" />');</xsl:attribute>
							<td>&nbsp;<xsl:value-of select="position()" /></td>
							<td>&nbsp;<xsl:value-of select="@name" /></td>
						</tr>
					</xsl:for-each>
				</tbody>
			</table>
		</div>

		<!-- Process each participant -->
		<xsl:apply-templates select="participant" />
		
	</xsl:template>
<!--
===================================================================================================================
	Participant = Statistics for participant
===================================================================================================================
-->	
	<xsl:template match="participant">
		<div class="section">
			<xsl:attribute name="id">participant_<xsl:value-of select="@id" /></xsl:attribute>
			<table>
				<thead>
					<tr>
						<th colspan="2" class="sectionTitle">Participant <xsl:value-of select="position()" /></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th class="heading">Name</th><td>&nbsp; <xsl:value-of select="@name" /></td>
					</tr><tr>
						<th class="heading">Count</th><td>&nbsp; <xsl:value-of select="@noOfInstances" /></td>
					</tr><tr>
						<th class="heading">Current Idle Count</th><td>&nbsp; <xsl:value-of select="@idleInstances" /></td>
					</tr><tr>
						<th class="heading">Average Idle Count</th><td>&nbsp; <xsl:value-of select="java:com.tibco.xpd.simulation.ui.Messages.formatNumber(@averageIdle,2)" /></td>
					</tr><tr>
						<th class="heading">Average Idle Time</th><td>&nbsp; <xsl:value-of select="java:com.tibco.xpd.simulation.ui.Messages.formatNumber(@averageIdleTime,2)" /></td>
					</tr>
					<!-- JA: This is not currently generated by the model.
					<tr>
						<th class="heading">Cost of Work For Case</th>
						<td>
							<xsl:choose>
								<xsl:when test="costOfWorkForCase"><xsl:apply-templates  select="costOfWorkForCase" /></xsl:when>
								<xsl:otherwise>&nbsp;</xsl:otherwise>
							</xsl:choose>
						</td>
					</tr>-->
				</tbody>
			</table>
		</div>
		
	</xsl:template>

<!--
===================================================================================================================
	CostOfWorkForCase = Participant's cost of work for case
===================================================================================================================
-->		
	<xsl:template match="costOfWorkForCase">
		
		<div>
			<xsl:attribute name="id">participant_cc_<xsl:value-of select="parent::node()/@id" /></xsl:attribute>
			<table>
				<tbody>
					<tr>
						<th class="heading">Average Cost</th><td><xsl:value-of select="java:com.tibco.xpd.simulation.ui.Messages.formatNumber(@averageCost,2)" /></td>
					</tr><tr>
						<th class="heading">Minimum Cost</th><td><xsl:value-of select="java:com.tibco.xpd.simulation.ui.Messages.formatNumber(@minCost,2)" /></td>
					</tr><tr>
						<th class="heading">Maximum Cost</th><td><xsl:value-of select="java:com.tibco.xpd.simulation.ui.Messages.formatNumber(@maxCost,2)" /></td>
					</tr><tr>
						<th class="heading">Cumulative Cost</th><td><xsl:value-of select="java:com.tibco.xpd.simulation.ui.Messages.formatNumber(@cumulativeAverageCost,2)" /></td>
					</tr>				
				</tbody>
			</table>
		</div>
		
	</xsl:template>

<!--
===================================================================================================================
	Activities = Activity group
===================================================================================================================
-->
	<xsl:template match="activities">
	
		<div id="activities" class="section">
			<table>
				<thead>
					<tr>
						<th class="sectionTitle" colspan="3">Activities (<xsl:value-of select="count (activity)" />)</th>
					</tr>
					<tr>
						<th class="headingId"></th><th>Name</th>
					</tr>
				</thead>
				<tbody>
					<xsl:for-each select="activity">
						<tr class="link">
							<xsl:attribute name="onclick">updateDetails ('activity_<xsl:value-of select="@id" />');</xsl:attribute>
							<td>&nbsp;<xsl:value-of select="position()" /></td>
							<td>&nbsp;<xsl:value-of select="@name" /></td>
						</tr>
					</xsl:for-each>
				</tbody>
			</table>
		</div>
		
		<!-- Process each activity -->
		<xsl:apply-templates select="activity" />
		
	</xsl:template>
	
<!--
===================================================================================================================
	Activity = Statistics for activity
===================================================================================================================
-->
	<xsl:template match="activity">
		<div class="section">
			<xsl:attribute name="Id">activity_<xsl:value-of select="@id" /></xsl:attribute>
			<table>
				<thead>
					<tr>
						<th class="sectionTitle" colspan="2">Activity <xsl:value-of select="position()" /></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th class="heading">Name</th><td>&nbsp;<xsl:value-of select="@name" /></td>
					</tr>
					<tr>
						<th class="heading">Duration Distribution</th>
						<td>
							<xsl:choose>
								<xsl:when test="durationDistribution"><xsl:apply-templates select="durationDistribution" /></xsl:when>
								<xsl:otherwise>&nbsp;</xsl:otherwise>
							</xsl:choose>
						</td>
					</tr><tr>
						<th class="heading">Activity Queue</th>
						<td>
							<xsl:choose>
								<xsl:when test="activityQueue"><xsl:apply-templates select="activityQueue" /></xsl:when>
								<xsl:otherwise>&nbsp;</xsl:otherwise>
							</xsl:choose>
						</td>
					</tr><tr>
						<th class="heading">Activity Cost</th>
						<td>
							<xsl:choose>
								<xsl:when test="activityCost"><xsl:apply-templates select="activityCost" /></xsl:when>
								<xsl:otherwise>&nbsp;</xsl:otherwise>
							</xsl:choose>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		
	</xsl:template>
	
<!--
===================================================================================================================
	DurationDistribution = Shows what distribution was used to determine times of activity duration.
===================================================================================================================
-->
	<xsl:template match="durationDistribution">
		<div>
			<xsl:attribute name="id">activity_dd_<xsl:value-of select="parent::node()/@Id" /></xsl:attribute>
			<table>
				<tbody>
					<tr>
						<th class="heading">Last Reset Time</th><td>&nbsp;<xsl:value-of select="java:com.tibco.xpd.simulation.ui.Messages.formatNumber(@lastResetTime,1)" /></td>
					</tr><tr>
						<th class="heading">Observed Elements</th><td>&nbsp;<xsl:value-of select="@observedElements" /></td>
					</tr><tr>
						<th class="heading">Distribution Category</th><td>&nbsp;<xsl:value-of select="@distributionCategory" /></td>
					</tr><tr>
						<th class="heading">Parameter 1</th><td>&nbsp;<xsl:value-of select="java:com.tibco.xpd.simulation.ui.Messages.formatNumber(@parameter1,1)" /></td>
					</tr><tr>
						<th class="heading">Parameter 2</th><td>&nbsp;<xsl:value-of select="java:com.tibco.xpd.simulation.ui.Messages.formatNumber(@parameter2,1)" /></td>
					</tr><tr>
						<th class="heading">Seed</th><td>&nbsp;<xsl:value-of select="@seed" /></td>
					</tr>
				</tbody>
			</table>
		</div>
	</xsl:template>
	
<!--
===================================================================================================================
	ActivityQueue = Represent queue which is used by activity to holds cases to be processed by activity.
===================================================================================================================
-->
<xsl:template match="activityQueue">
	<div>
		<xsl:attribute name="id">activity_aq_<xsl:value-of select="parent::node()/@id" /></xsl:attribute>
		<table>
			<tbody>
				<tr>
					<th class="heading">Queue Order</th><td>&nbsp;<xsl:value-of select="@queueOrder" /></td>
				</tr><tr>
					<th class="heading">Processed Cases</th><td>&nbsp;<xsl:value-of select="@observedCases" /></td>
				</tr><tr>
					<th class="heading">Current Queue Size</th><td>&nbsp;<xsl:value-of select="@currentSize" /></td>
				</tr><tr>
					<th class="heading">Maximum Queue Size</th><td>&nbsp;<xsl:value-of select="@maxSize" /></td>
				</tr><tr>
					<th class="heading">Average Queue Size</th><td>&nbsp;<xsl:value-of select="java:com.tibco.xpd.simulation.ui.Messages.formatNumber(@averageSize,2)" /></td>
				</tr><tr>
					<th class="heading">Average Wait</th><td>&nbsp;<xsl:value-of select="java:com.tibco.xpd.simulation.ui.Messages.formatNumber(@averageWait,2)" /></td>
				</tr>
			</tbody>
		</table>
	</div>
</xsl:template>

<!--
===================================================================================================================
	ActivityCost = Activity cost
===================================================================================================================
-->
	<xsl:template match="activityCost">
		<div>
			<xsl:attribute name="id">activity_ac_<xsl:value-of select="parent::node()/@id" /></xsl:attribute>
			<table>
				<tbody>
					<tr>
						<th class="heading">Average Cost</th><td>&nbsp;<xsl:value-of select="java:com.tibco.xpd.simulation.ui.Messages.formatNumber(@averageCost, 2)" /></td>
					</tr><tr>
						<th class="heading">Minimum Cost</th><td>&nbsp;<xsl:value-of select="java:com.tibco.xpd.simulation.ui.Messages.formatNumber(@minCost, 2)" /></td>
					</tr><tr>
						<th class="heading">Maximum Cost</th><td>&nbsp;<xsl:value-of select="java:com.tibco.xpd.simulation.ui.Messages.formatNumber(@maxCost, 2)" /></td>
					</tr><tr>
						<th class="heading">Cumulative Cost</th><td>&nbsp;<xsl:value-of select="java:com.tibco.xpd.simulation.ui.Messages.formatNumber(@cumulativeAverageCost, 2)" /></td>
					</tr>
				</tbody>
			</table>
		</div>
	</xsl:template>
	

		
		
	<!-- Default for all nodes not handled above. -->
	<xsl:template match="@* | node() | text()">
		<!-- Supress default copy of all nodes and text -->
	</xsl:template>
	
		
</xsl:stylesheet>
