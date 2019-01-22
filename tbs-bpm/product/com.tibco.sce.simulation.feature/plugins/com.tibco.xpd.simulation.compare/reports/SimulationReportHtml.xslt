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
		
	<xsl:variable name="SimMsgs" select="java:com.tibco.xpd.simulation.compare.SimMsgs.new()" />
	
<!--
===================================================================================================================
	XSLT processing start point (process whole document)
===================================================================================================================
-->
	<xsl:template match="/" >

		<xsl:text disable-output-escaping="yes">&lt;!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"&gt;</xsl:text>

		<html xmlns="http://www.w3.org/1999/xhtml" >
			<head>
				<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
				<title><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationHeaderTag_title')"/></title>
				<link rel="stylesheet" href="Style/simrep.css" type="text/css"/>				
				<script type="text/javascript">
					<xsl:text disable-output-escaping="yes">
						
						//The view titles
						var PRINTVIEWTITLE = "</xsl:text><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationSwitchToPrint_label')"/><xsl:text disable-output-escaping="yes">";						
						var NORMALVIEWTITLE = "</xsl:text><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationSwitchToNormal_label')"/><xsl:text disable-output-escaping="yes">";
						
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
								<xsl:element name="a">
								    <xsl:attribute name="id">lnkview</xsl:attribute>
								    <xsl:attribute name="class">link</xsl:attribute>
								    <xsl:attribute name="onclick">switchView (this)</xsl:attribute>
								    <xsl:attribute name="title">
								    	<xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_ChangeView_title')"/>
								    </xsl:attribute>
									<script type="text/javascript">
										//Update the link title based on current view setting
										if (getCookie ("view") == NORMAL || getCookie ("view") == "")
											document.write (PRINTVIEWTITLE);
										else
											document.write (NORMALVIEWTITLE);
									</script>
								</xsl:element>
							</td>
						</tr>
					</tbody>
				</table>
				<!-- Header -->
				<table class="mainTitle">
					<tbody>
						<tr>
							<td class="noBorder">
								<xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_Simulation_title')"/>
							</td><td class="noBorder" align="center">
								<xsl:value-of select="Experiment/@Name" />
							</td>
							<td class="noBorder" align="right"><img src="images/TibcoLogo.gif" alt="TIBCO&#174; Software Inc." title="TIBCO&#174; Software Inc."/></td>
						</tr>
					</tbody>
				</table>
				<!-- Header - END -->	
				
				<!-- Add simulation information -->
				<xsl:apply-templates select="Experiment" />
				
				<div id="normalview">
					<table>
						<tbody>
							<tr>
								<td class="noBorder" width="25%" nowrap="1" valign="top">
									<br/>
									<div id="frameoptions" >
										<!-- Build tree view -->
										<xsl:apply-templates select="Experiment" mode="treeOptions" />
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
					<xsl:apply-templates select="Experiment/ExperimentData" />
					
					<!-- Add cases connected statistics -->
					<xsl:apply-templates select="Experiment/Cases" />
						
					<!-- Add participants -->
					<xsl:apply-templates select="Experiment/Participants" />
						
					<!-- Add activities -->
					<xsl:apply-templates select="Experiment/Activities" />
				
				</div>
				
			</body>
		</html>

	</xsl:template>
	
<!--
===================================================================================================================
	Experiment = The simulation details.
===================================================================================================================
-->		
	<xsl:template match="Experiment">
	
		<table>
			<tbody>
				<tr>
					<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationName_label')"/></th><td><xsl:value-of select="@Name" /></td>
				</tr><tr>
					<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationPackageName_label')"/></th><td><xsl:value-of select="@PackageName" /></td>
				</tr><tr>
					<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationProcessName_label')"/></th><td><xsl:value-of select="@ProcessName" /></td>
				</tr>
			</tbody>
		</table>
		
	</xsl:template>
	
<!--
===================================================================================================================
	Experiment (treeOptions) = This will create the tree selection view in the normal view.
===================================================================================================================
-->	
	<xsl:template match="Experiment" mode="treeOptions">

		<table>
			<tbody>
				<!-- Experiment Data -->
				<xsl:if test="ExperimentData">
					<tr>
						<td colspan="3" id="optexperimentdata">
							<img id="imgexperimentdata" alt="no expand icon" title="" src="images/op_noexpand.gif"></img>
							<a id="lnkexperimentdata" class="option" onclick="updateDetails ('experimentdata');" ><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationExperimentData_label')"/></a>
						</td>
					</tr>
				</xsl:if>
				<!-- Cases -->
				<xsl:if test="Cases">
					<tr>
						<td colspan="3" id="optcases">
							<img id="imgcases" alt="no expand icon" title="" src="images/op_noexpand.gif"></img>
							<a id="lnkcases" class="option" onclick="updateDetails ('cases');"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationCases_label')"/></a>
						</td>
					</tr>
				</xsl:if>
			
				<!-- Participants -->
				<xsl:if test="Participants">
					<tr>
						<td id="optparticipants" colspan="3">
							<img id="imgparticipants" class="link" src="images/op_plus.gif" alt="Expand icon" title="Expand" onclick="expandOption (this, 'subparticipants');"></img>
							<a id="lnkparticipants" class="option" onclick="updateDetails ('participants');"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationParticipants_label')"/></a>
							
							<table id="subparticipants" style="display:none">
								<tbody>
									<!-- Participants -> Participant -->
									<xsl:for-each select="Participants/Participant">
										<tr>
											<td class="noBorder subsection">
												<xsl:attribute name="id">optparticipant_<xsl:value-of select="@Id" /></xsl:attribute>
												<img src="images/op_noexpand.gif" alt="no expand icon" title="">
													<xsl:attribute name="id">imgparticipant_<xsl:value-of select="@Id" /></xsl:attribute>
												</img>	
												<a class="option">
													<xsl:attribute name="id">lnkparticipant_<xsl:value-of select="@Id" /></xsl:attribute>
													<xsl:attribute name="onclick">updateDetails ('participant_<xsl:value-of select="@Id" />');</xsl:attribute>
													<xsl:value-of select="@Name" />
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
				<xsl:if test="Activities">
					<tr>
						<td colspan="3" id="optactivities">
							<img id="imgactivities" class="link" src="images/op_plus.gif" alt="Expand icon" title="Expand" onclick="expandOption (this, 'subactivities')"></img>
							<a id="lnkactivities" class="option" onclick="updateDetails ('activities');"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationActivities_label')"/></a>
							
							<table id="subactivities" style="display:none">
								<tbody>
								
									<!-- Activities -> Activity -->
									<xsl:for-each select="Activities/Activity">
										<tr>
											<td class="noBorder subsection">
												<xsl:attribute name="id">optactivity_<xsl:value-of select="@Id" /></xsl:attribute>
												<img src="images/op_noexpand.gif" alt="no expand icon" title="">
													<xsl:attribute name="id">imgactivity_<xsl:value-of select="@Id" /></xsl:attribute>
												</img>
												<a class="option">
													<xsl:attribute name="id">lnkactivity_<xsl:value-of select="@Id" /></xsl:attribute>
													<xsl:attribute name="onclick">updateDetails ('activity_<xsl:value-of select="@Id" />');</xsl:attribute>
													<xsl:value-of select="@Name" />
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
<xsl:template match="ExperimentData">
	<div id="experimentdata" class="section">
		<table>
			<thead>
				<tr>
					<th class="sectionTitle" colspan="2"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationExperimentData_label')"/></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationExperimentState_label')"/></th><td>&nbsp; <xsl:value-of select="ExperimentState" /></td>
				</tr><tr>
					<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationDuration_label')"/></th><td>&nbsp; <xsl:value-of select="java:com.tibco.xpd.simulation.compare.Messages.formatNumber(SimulationTime, 1)" />*</td>
				</tr><tr>
					<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationTimeUnit_label')"/></th><td>&nbsp; <xsl:value-of select="ReferenceTimeUnit" /></td>
				</tr><tr>
					<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationStartTime_label')"/></th>
					<td>&nbsp; <xsl:value-of select="ReferenceStartTime" /></td>					
				</tr><tr>
					<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationEndTime_label')"/></th>
					<td>&nbsp; <xsl:value-of select="RealTime" /></td>					
				</tr><tr>
					<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationCostUnit_label')"/></th><td>&nbsp; <xsl:value-of select="ReferenceCostUnit" /></td>
				</tr>
				
			</tbody>
		</table>
		<span style="font-size:smaller"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationRoundedZeroPlaces_label')"/></span>
	</div>
			
</xsl:template>
	
<!--
===================================================================================================================
	Cases = All cases connected statistics.
===================================================================================================================
-->	
	<xsl:template match="Cases">
		
		<div id="cases" class="section">
			<table>
				<thead>
					<tr>
						<th colspan="2" class="sectionTitle">
								<xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationCases_title')"/>
						</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationStarted_label')"/></th><td>&nbsp; <xsl:value-of select="StartedCases" /></td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationFinished_label')"/></th><td>&nbsp; <xsl:value-of select="FinishedCases" /></td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationAverageTime_label')"/></th><td>&nbsp; <xsl:value-of select="java:com.tibco.xpd.simulation.compare.Messages.formatNumber(AverageCaseTime, 1)" />*</td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationMinimumTime_label')"/></th><td>&nbsp; <xsl:value-of select="java:com.tibco.xpd.simulation.compare.Messages.formatNumber(MinCaseTime, 1)" />*</td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationMaximumTime_label')"/></th><td>&nbsp; <xsl:value-of select="java:com.tibco.xpd.simulation.compare.Messages.formatNumber(MaxCaseTime, 1)" />*</td>
					</tr>					
					<tr>						
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationCaseStartIntervalDistribution_label')"/></th>
						<td>
							<xsl:choose>
								<xsl:when test="CaseStartIntervalDistribution"><xsl:apply-templates select="CaseStartIntervalDistribution" /></xsl:when>
								<xsl:otherwise>&nbsp;</xsl:otherwise>
							</xsl:choose>
						</td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationCaseCost_label')"/></th>
						<td>
							<xsl:choose>
								<xsl:when test="CaseCost"><xsl:apply-templates select="CaseCost" /></xsl:when>
								<xsl:otherwise>&nbsp;</xsl:otherwise>
							</xsl:choose>
						</td>
					</tr>
				</tbody>
			</table>
			<span style="font-size:smaller"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationRoundedZeroPlaces_label')"/></span>
			<span style="font-size:smaller"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationRoundedTwoPlaces_label')"/></span>
		</div>
		
	</xsl:template>
	
<!--
===================================================================================================================
	CaseStartIntervalDistribution = Describes how frequently cases are started during the experiment.
===================================================================================================================
-->	
	<xsl:template match="CaseStartIntervalDistribution">
		<div id="csid">
			<table>
				<tbody>
					<tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationLastResetTime_label')"/></th><td>&nbsp; <xsl:value-of select="java:com.tibco.xpd.simulation.compare.Messages.formatNumber(LastResetTime, 1)" />*</td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationObservedElements_label')"/></th><td>&nbsp; <xsl:value-of select="ObservedElements" /></td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationDistributionCategory_label')"/></th><td>&nbsp; <xsl:value-of select="DistributionCategory" /></td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationParameterOne_label')"/></th><td>&nbsp; <xsl:value-of select="java:com.tibco.xpd.simulation.compare.Messages.formatNumber(Parameter1, 2)" />**</td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationParameterTwo_label')"/></th><td>&nbsp; <xsl:value-of select="java:com.tibco.xpd.simulation.compare.Messages.formatNumber(Parameter2, 2)" />**</td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationRandomiserSeed_label')"/></th><td>&nbsp; <xsl:value-of select="Seed" /></td>
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
	<xsl:template match="CaseCost">
		<div id="casecost">
			<table>
				<tbody>
					<tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationAverageCost_label')"/></th><td>&nbsp; <xsl:value-of select="java:com.tibco.xpd.simulation.compare.Messages.formatNumber(AverageCost, 2)" />**</td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationMinimumCost_label')"/></th><td>&nbsp; <xsl:value-of select="java:com.tibco.xpd.simulation.compare.Messages.formatNumber(MinCost, 2)" />**</td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationMaximumCost_label')"/></th><td>&nbsp; <xsl:value-of select="java:com.tibco.xpd.simulation.compare.Messages.formatNumber(MaxCost, 2)" />**</td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationCumulativeAverageCost_label')"/></th><td>&nbsp;<xsl:value-of select="java:com.tibco.xpd.simulation.compare.Messages.formatNumber(CumulativeAverageCost,2)" />**</td>
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
	<xsl:template match="Participants">
		<div id="participants" class="section">
			<table>
				<thead>
					<tr>
						<th colspan="3" class="sectionTitle"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationParticipants_label')"/> (<xsl:value-of select="count (Participant)" />)</th>
					</tr>
					<tr>
						<th class="headingId"></th><th><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationName_label')"/></th>
					</tr>
				</thead>
				<tbody>
					<xsl:for-each select="Participant">
						<tr class="link">
							<xsl:attribute name="onclick">updateDetails ('participant_<xsl:value-of select="@Id" />');</xsl:attribute>
							<td>&nbsp;<xsl:value-of select="position()" /></td>
							<td>&nbsp;<xsl:value-of select="@Name" /></td>
						</tr>
					</xsl:for-each>
				</tbody>
			</table>
		</div>

		<!-- Process each participant -->
		<xsl:apply-templates select="Participant" />
		
	</xsl:template>
<!--
===================================================================================================================
	Participant = Statistics for participant
===================================================================================================================
-->	
	<xsl:template match="Participant">
		<div class="section">
			<xsl:attribute name="id">participant_<xsl:value-of select="@Id" /></xsl:attribute>
			<table>
				<thead>
					<tr>
						<th colspan="2" class="sectionTitle"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationParticipant_label')"/> <xsl:value-of select="position()" /></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationName_label')"/></th><td>&nbsp; <xsl:value-of select="@Name" /></td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationCount_label')"/></th><td>&nbsp; <xsl:value-of select="NoOfInstances" /></td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationCurrentIdleTime_label')"/></th><td>&nbsp; <xsl:value-of select="IdleInstances" /></td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationAverageIdleCount_label')"/></th><td>&nbsp; <xsl:value-of select="java:com.tibco.xpd.simulation.compare.Messages.formatNumber(AverageIdle, 2)" />**</td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationAverageIdleTime_label')"/></th><td>&nbsp; <xsl:value-of select="java:com.tibco.xpd.simulation.compare.Messages.formatNumber(AverageIdleTime, 2)" />**</td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationAverageBusyTime_label')"/></th><td>&nbsp; <xsl:value-of select="java:com.tibco.xpd.simulation.compare.Messages.formatNumber(AverageBusyTime, 2)" />**</td>
					</tr>
					<!-- JA: This is not currently generated by the model.
					<tr>
						<th class="heading">Cost of Work For Case</th>
						<td>
							<xsl:choose>
								<xsl:when test="CostOfWorkForCase"><xsl:apply-templates  select="CostOfWorkForCase" /></xsl:when>
								<xsl:otherwise>&nbsp;</xsl:otherwise>
							</xsl:choose>
						</td>
					</tr>-->
				</tbody>
			</table>
			<span style="font-size:smaller"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationRoundedTwoPlaces_label')"/></span>
		</div>
		
	</xsl:template>

<!--
===================================================================================================================
	CostOfWorkForCase = Participant's cost of work for case
===================================================================================================================
-->		
	<xsl:template match="CostOfWorkForCase">
		
		<div>
			<xsl:attribute name="id">participant_cc_<xsl:value-of select="parent::node()/@Id" /></xsl:attribute>
			<table>
				<tbody>
					<tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationAverageCost_label')"/></th><td><xsl:value-of select="java:com.tibco.xpd.simulation.compare.Messages.formatNumber(AverageCost, 2)" />**</td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationMinimumCost_label')"/></th><td><xsl:value-of select="java:com.tibco.xpd.simulation.compare.Messages.formatNumber(MinCost, 2)" />**</td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationMaximumCost_label')"/></th><td><xsl:value-of select="java:com.tibco.xpd.simulation.compare.Messages.formatNumber(MaxCost, 2)" />**</td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationCumulativeCost_label')"/></th><td><xsl:value-of select="java:com.tibco.xpd.simulation.compare.Messages.formatNumber(CumulativeAverageCost, 2)" />**</td>
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
	<xsl:template match="Activities">
	
		<div id="activities" class="section">
			<table>
				<thead>
					<tr>
						<th class="sectionTitle" colspan="3"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationActivities_label')"/> (<xsl:value-of select="count (Activity)" />)</th>
					</tr>
					<tr>
						<th class="headingId"></th><th><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationName_label')"/></th>
					</tr>
				</thead>
				<tbody>
					<xsl:for-each select="Activity">
						<tr class="link">
							<xsl:attribute name="onclick">updateDetails ('activity_<xsl:value-of select="@Id" />');</xsl:attribute>
							<td>&nbsp;<xsl:value-of select="position()" /></td>
							<td>&nbsp;<xsl:value-of select="@Name" /></td>
						</tr>
					</xsl:for-each>
				</tbody>
			</table>
		</div>
		
		<!-- Process each activity -->
		<xsl:apply-templates select="Activity" />
		
	</xsl:template>
	
<!--
===================================================================================================================
	Activity = Statistics for activity
===================================================================================================================
-->
	<xsl:template match="Activity">
		<div class="section">
			<xsl:attribute name="Id">activity_<xsl:value-of select="@Id" /></xsl:attribute>
			<table>
				<thead>
					<tr>
						<th class="sectionTitle" colspan="2"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationActivity_label')"/> <xsl:value-of select="position()" /></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationName_label')"/></th><td>&nbsp;<xsl:value-of select="@Name" /></td>
					</tr>
					<tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationDurationDistribution_label')"/></th>
						<td>
							<xsl:choose>
								<xsl:when test="DurationDistribution"><xsl:apply-templates select="DurationDistribution" /></xsl:when>
								<xsl:otherwise>&nbsp;</xsl:otherwise>
							</xsl:choose>
						</td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationActivityQueue_label')"/></th>
						<td>
							<xsl:choose>
								<xsl:when test="ActivityQueue"><xsl:apply-templates select="ActivityQueue" /></xsl:when>
								<xsl:otherwise>&nbsp;</xsl:otherwise>
							</xsl:choose>
						</td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationActivityCost_label')"/></th>
						<td>
							<xsl:choose>
								<xsl:when test="ActivityCost"><xsl:apply-templates select="ActivityCost" /></xsl:when>
								<xsl:otherwise>&nbsp;</xsl:otherwise>
							</xsl:choose>
						</td>
					</tr>
				</tbody>
			</table>
			<span style="font-size:smaller"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationRoundedZeroPlaces_label')"/></span>
			<span style="font-size:smaller"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationRoundedTwoPlaces_label')"/></span>
		</div>
		
	</xsl:template>
	
<!--
===================================================================================================================
	DurationDistribution = Shows what distribution was used to determine times of activity duration.
===================================================================================================================
-->
	<xsl:template match="DurationDistribution">
		<div>
			<xsl:attribute name="id">activity_dd_<xsl:value-of select="parent::node()/@Id" /></xsl:attribute>
			<table>
				<tbody>
					<tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationLastResetTime_label')"/></th><td>&nbsp;<xsl:value-of select="java:com.tibco.xpd.simulation.compare.Messages.formatNumber(LastResetTime, 1)" />*</td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationObservedElements_label')"/></th><td>&nbsp;<xsl:value-of select="ObservedElements" /></td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationDistributionCategory_label')"/></th><td>&nbsp;<xsl:value-of select="DistributionCategory" /></td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationParameterOne_label')"/></th><td>&nbsp;<xsl:value-of select="java:com.tibco.xpd.simulation.compare.Messages.formatNumber(Parameter1, 2)" />**</td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationParameterTwo_label')"/></th><td>&nbsp;<xsl:value-of select="java:com.tibco.xpd.simulation.compare.Messages.formatNumber(Parameter2, 2)" />**</td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationRandomiserSeed_label')"/></th><td>&nbsp;<xsl:value-of select="Seed" /></td>
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
<xsl:template match="ActivityQueue">
	<div>
		<xsl:attribute name="id">activity_aq_<xsl:value-of select="parent::node()/@Id" /></xsl:attribute>
		<table>
			<tbody>
				<tr>
					<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationProcessedCases_label')"/></th><td>&nbsp;<xsl:value-of select="ObservedCases" /></td>
				</tr><tr>
					<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationCurrentQueueSize_label')"/></th><td>&nbsp;<xsl:value-of select="CurrentSize" /></td>
				</tr><tr>
					<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationMaximumQueueSize_label')"/></th><td>&nbsp;<xsl:value-of select="MaxSize" /></td>
				</tr><tr>
					<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationAverageQueueSize_label')"/></th><td>&nbsp;<xsl:value-of select="java:com.tibco.xpd.simulation.compare.Messages.formatNumber(AverageSize, 2)" />**</td>
				</tr><tr>
					<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationAverageWait_label')"/></th><td>&nbsp;<xsl:value-of select="java:com.tibco.xpd.simulation.compare.Messages.formatNumber(AverageWait, 1)" />*</td>
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
	<xsl:template match="ActivityCost">
		<div>
			<xsl:attribute name="id">activity_ac_<xsl:value-of select="parent::node()/@Id" /></xsl:attribute>
			<table>
				<tbody>
					<tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationAverageCost_label')"/></th><td>&nbsp;<xsl:value-of select="java:com.tibco.xpd.simulation.compare.Messages.formatNumber(AverageCost, 2)" />**</td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationMinimumCost_label')"/></th><td>&nbsp;<xsl:value-of select="java:com.tibco.xpd.simulation.compare.Messages.formatNumber(MinCost, 2)" />**</td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationMaximumCost_label')"/></th><td>&nbsp;<xsl:value-of select="java:com.tibco.xpd.simulation.compare.Messages.formatNumber(MaxCost, 2)" />**</td>
					</tr><tr>
						<th class="heading"><xsl:value-of select="java:getMessage($SimMsgs, 'SimulationReportHtml_SimulationCumulativeCost_label')"/></th><td>&nbsp;<xsl:value-of select="java:com.tibco.xpd.simulation.compare.Messages.formatNumber(CumulativeAverageCost, 2)" />**</td>
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
