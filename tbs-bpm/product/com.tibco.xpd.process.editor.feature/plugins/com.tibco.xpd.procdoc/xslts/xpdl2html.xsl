<?xml version="1.0" encoding="UTF-8"?>
<!--
===================================================================================================================
XSLT:		xpdl2html.xsl					

DESCRIPTION:
	This xslt will create the Procedure Documentation in HTML format from the selected package XPDL.

===================================================================================================================
-->

<!-- 
	Define &nbsp; and also strings used to describe event, gateway, 
	publication and transaction types 
-->
<!DOCTYPE xsl:stylesheet [
<!ENTITY nbsp  '<xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>'>
]>


<xsl:stylesheet version="1.0" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://www.w3.org/1999/xhtml" 
	xmlns:html="http://www.w3.org/1999/xhtml"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xpdExt="http://www.tibco.com/XPD/xpdExtension1.0.0" 
	xmlns:xpdl2="http://www.wfmc.org/2008/XPDL2.1"
	xmlns:java="http://xml.apache.org/xslt/java"
	xmlns:email="http://www.tibco.com/XPD/email1.0.0"
	xmlns:ej="http://www.tibco.com/XPD/EAIJava1.0.0"
	xmlns:database="http://www.tibco.com/XPD/database1.0.0"
	xmlns:simulation="http://www.tibco.com/xpd/Simulation1.0.1"
	exclude-result-prefixes="#default xsl xpdExt xpdl2 email database java ej simulation" 
>

<!-- 
	Parameters... 
-->
<xsl:param name="imagesFolder" select="'.'"/>
<xsl:param name="imageFileExtension" select="'.bmp'"/>
<xsl:param name="modifiedDate" select="' '"/>
<xsl:param name="createdDate" select="' '"/>
<xsl:param name="showProcessImages" select="'false'"/>
<xsl:param name="useSVGImages" select="'false'"/>
<xsl:param name="showParticipants" select="'true'"/>
<xsl:param name="showTypeDeclarations" select="'true'"/>
<xsl:param name="showDataFields" select="'true'"/>
<xsl:param name="showFormalParameters" select="'true'"/>
<xsl:param name="showFlows" select="'true'"/>
<xsl:param name="showImplementations" select="'true'"/>
<xsl:param name="showExtendedAttributes" select="'true'"/>
<xsl:param name="showActivityInterfaces" select="'true'"/>
<xsl:param name="messagePropertiesId" select="''"/>
<xsl:param name="showTokenName" select="'false'"/>
<xsl:param name="transformMonitorId"/>

<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>	


<!-- Find out if we are running with java extensions available. -->
<!-- Get package ID -->
<xsl:variable name="packageID"><xsl:value-of select="xpdl2:Package/@Id"/></xsl:variable>

<!-- Java extensions. -->
<xsl:variable name="javaPkgInfo" select="java:com.tibco.xpd.procdoc.ProcDocPackageInfo.new($packageID)"/>
<xsl:variable name="msgPropsId"><xsl:value-of select="$messagePropertiesId"/></xsl:variable>
<xsl:variable name="msgL10n" select="java:com.tibco.xpd.procdoc.xslt.messages.ProcDocMsgs.new()"/> 

<xsl:variable name="implicitAssociationDisabledTag" select="'__implicit.association.disabled__'"/>


<!--
===================================================================================================================
	XSLT processing start point (process whole document).
===================================================================================================================
-->
<xsl:template match="/">
		<!-- Output the DOCTYPE required or strict xhtml documents. -->
	<xsl:text disable-output-escaping="yes">&lt;</xsl:text><![CDATA[!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"]]><xsl:text disable-output-escaping="yes">&gt;</xsl:text>
	
	<!-- Main <html> header section -->
	<xsl:element name="html" namespace="http://www.w3.org/1999/xhtml">
	
		<!-- Get package name - if no name set then set the name to ID -->
		<xsl:variable name="packageName">
			<xsl:variable name="name"><xsl:call-template name="getName"><xsl:with-param name="node" select="xpdl2:Package"/></xsl:call-template></xsl:variable>
			<xsl:choose>
				<xsl:when test="$name">
					<xsl:value-of select="$name" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$packageID" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		
		<head>
			<meta http-equiv="Content-Type" content="text/xml; charset=UTF-8"/>
			<title>TIBCO Business Studio - <xsl:value-of select="$packageName"/></title>
			<link rel="stylesheet" href="style/procdoc.css" type="text/css"/>
			<link rel="stylesheet" href="style/printprocdoc.css" type="text/css" media="print"/>
		</head>
		<body>
			<!-- Header -->
			<table class="mainTitle">
				<tbody>
					<tr>
						<td class="noBorder mainTitleCol1"><a id="{xpdl2:Package/@Id}"><xsl:value-of select="java:get($msgL10n, 'Package_label')"/>:</a>&nbsp;</td>
						<td class="noBorder mainTitle"><xsl:value-of select="$packageName"/>&nbsp;</td>
						<td class="noBorder mainTitle" align="right"><img src="images/Logo.png" alt="TIBCO Software Inc."/>&nbsp;</td>
					</tr>
				</tbody>
			</table>
			<table>
				<tbody>
					<tr>
						<th class="heading"><xsl:value-of select="java:get($msgL10n, 'Created_label')"/></th><td><xsl:value-of select="$createdDate"/>&nbsp;</td>
					</tr>
					<tr>
						<th class="heading"><xsl:value-of select="java:get($msgL10n, 'Modified_label')"/></th><td><xsl:value-of select="$modifiedDate"/>&nbsp;</td>
					</tr>
					<tr>
						<th class="heading"><xsl:value-of select="java:get($msgL10n, 'XpdlVersion_label')"/></th><td><xsl:value-of select="xpdl2:Package/xpdl2:PackageHeader/xpdl2:XPDLVersion"/>&nbsp;</td>
					</tr>
					<tr>
						<th class="heading"><xsl:value-of select="java:get($msgL10n, 'Vendor_label')"/></th><td><xsl:value-of select="xpdl2:Package/xpdl2:PackageHeader/xpdl2:Vendor"/>&nbsp;</td>
					</tr>
					<tr>
						<th class="heading"><xsl:value-of select="java:get($msgL10n, 'Documentation_label')"/></th><td><xsl:value-of select="xpdl2:Package/xpdl2:PackageHeader/xpdl2:Documentation"/>&nbsp;</td>
					</tr>
					<tr>
						<th class="heading"><xsl:value-of select="java:get($msgL10n, 'PublicationStatus_label')"/></th>
							<td>
								<xsl:choose>
									<xsl:when test="xpdl2:Package/xpdl2:RedefinableHeader/@PublicationStatus = 'UNDER_REVISION'"><xsl:value-of select="java:get($msgL10n, 'PublicationUNDERREV_label')"/></xsl:when>
									<xsl:when test="xpdl2:Package/xpdl2:RedefinableHeader/@PublicationStatus = 'UNDER_TEST'"><xsl:value-of select="java:get($msgL10n, 'PublicationUnderTest_label')"/></xsl:when>
									<xsl:when test="xpdl2:Package/xpdl2:RedefinableHeader/@PublicationStatus = 'RELEASED'"><xsl:value-of select="java:get($msgL10n, 'PublicationRELEASED_label')"/></xsl:when>
									<xsl:otherwise><xsl:value-of select="xpdl2:Package/xpdl2:RedefinableHeader/@PublicationStatus"/></xsl:otherwise>
								</xsl:choose>
								&nbsp;
							</td>
					</tr>
					<tr>
						<th class="heading"><xsl:value-of select="java:get($msgL10n, 'Author_label')"/></th><td><xsl:value-of select="xpdl2:Package/xpdl2:RedefinableHeader/xpdl2:Author"/>&nbsp;</td>
					</tr>
					<tr>
						<th class="heading"><xsl:value-of select="java:get($msgL10n, 'BusinessVersion_label')"/></th><td><xsl:value-of select="xpdl2:Package/xpdl2:RedefinableHeader/xpdl2:Version"/>&nbsp;</td>
					</tr>
					<tr>
						<th class="heading"><xsl:value-of select="java:get($msgL10n, 'Description_label')"/></th>
						<td>
							<xsl:call-template name="OutputDescription">
								<xsl:with-param name="descText" select="xpdl2:Package/xpdl2:PackageHeader/xpdl2:Description"/>
								<xsl:with-param name="docURL" select="xpdl2:Package/xpdl2:PackageHeader/xpdl2:Description/@xpdExt:DocumentationURL"/>
							</xsl:call-template>
							&nbsp;
						</td>
					</tr>

					<tr>
						<th class="heading"><xsl:value-of select="java:get($msgL10n, 'ContentSummary_label')"/></th>
						<td>
							<xsl:value-of select="java:get($msgL10n, 'Participants_label')"/>(<xsl:value-of select="count(xpdl2:Package/xpdl2:Participants/xpdl2:Participant)"/>)&nbsp;&nbsp;
							<xsl:value-of select="java:get($msgL10n, 'TypeDeclarations_label')"/>(<xsl:value-of select="count(xpdl2:Package/xpdl2:TypeDeclarations/xpdl2:TypeDeclaration)"/>)&nbsp;&nbsp;
							<xsl:value-of select="java:get($msgL10n, 'DataFieldsTitle_label')"/>(<xsl:value-of select="count(xpdl2:Package/xpdl2:DataFields/xpdl2:DataField)"/>)&nbsp;&nbsp;
							<xsl:value-of select="java:get($msgL10n, 'ProcessInterfaces_label')"/>(<xsl:value-of select="count(xpdl2:Package/xpdExt:ProcessInterfaces/xpdExt:ProcessInterface)"/>)&nbsp;&nbsp;
							<xsl:value-of select="java:get($msgL10n, 'Processes_label')"/>(<xsl:value-of select="count(xpdl2:Package/xpdl2:WorkflowProcesses/xpdl2:WorkflowProcess)"/>)&nbsp;&nbsp;
						</td>
					</tr>

					<tr class="screenOnly">
						<th class="heading"><xsl:value-of select="java:get($msgL10n, 'QuickLinks_label')"/></th>
						<td>
							<xsl:if test="xpdl2:Package/xpdl2:Participants/xpdl2:Participant and $showParticipants = 'true'">
								<a href="#parts_{xpdl2:Package/@Id}"><xsl:value-of select="java:get($msgL10n, 'Participants_label')"/>,&nbsp;</a>
							</xsl:if>
							<xsl:if test="xpdl2:Package/xpdl2:TypeDeclarations/xpdl2:TypeDeclaration and $showTypeDeclarations = 'true' ">
								<a href="#types_{xpdl2:Package/@Id}"><xsl:value-of select="java:get($msgL10n, 'TypeDeclarations_label')"/>,&nbsp;</a>
							</xsl:if>
							<xsl:if test="xpdl2:Package/xpdl2:DataFields/xpdl2:DataField and $showDataFields = 'true'">
								<a href="#fields_{xpdl2:Package/@Id}"><xsl:value-of select="java:get($msgL10n, 'DataFieldsTitle_label')"/>,&nbsp;</a>
							</xsl:if>
							<xsl:if test="xpdl2:Package/xpdExt:ProcessInterfaces/xpdExt:ProcessInterface">
								<a href="#interface_{xpdl2:Package/@Id}"><xsl:value-of select="java:get($msgL10n, 'ProcessInterfaces_label')"/>,&nbsp;</a>
							</xsl:if>
							<xsl:if test="xpdl2:Package/xpdl2:WorkflowProcesses/xpdl2:WorkflowProcess">
								<a href="#process_{xpdl2:Package/@Id}"><xsl:value-of select="java:get($msgL10n, 'Processes_label')"/>,&nbsp;</a>
							</xsl:if>
							<xsl:if test="xpdl2:Package/xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute and $showExtendedAttributes = 'true'">
								<a href="#extAttrs_{xpdl2:Package/@Id}"><xsl:value-of select="java:get($msgL10n, 'ExtendedAttributes_label')"/></a>
							</xsl:if>
							&nbsp;
						</td>
					</tr>
				</tbody>
			</table>
			<!-- Header - END -->

			<!-- Get associations -->
			<xsl:variable name="associations" select="xpdl2:Package/xpdl2:Associations/xpdl2:Association" />
			<!-- Get annotation artifacts -->
			<xsl:variable name="annotations" select="xpdl2:Package/xpdl2:Artifacts/xpdl2:Artifact[@ArtifactType = 'Annotation']" />
			<!-- Get data object artifacts -->
			<xsl:variable name="dataObjs" select="xpdl2:Package/xpdl2:Artifacts/xpdl2:Artifact[@ArtifactType = 'DataObject']" />
			<!-- Get pools -->
			<xsl:variable name="pools" select="xpdl2:Package/xpdl2:Pools/xpdl2:Pool" />
			
			<!-- Package participants -->
			<xsl:if test="$showParticipants = 'true'">
				<xsl:apply-templates select="xpdl2:Package/xpdl2:Participants"/>
			</xsl:if>
			
			<!-- Type Declarations -->
			<xsl:if test="$showTypeDeclarations = 'true'">
				<xsl:apply-templates select="xpdl2:Package/xpdl2:TypeDeclarations"/>
			</xsl:if>

			<!-- Package Data Fields -->
			<xsl:if test="$showDataFields = 'true'">
				<xsl:apply-templates select="xpdl2:Package/xpdl2:DataFields"/>
			</xsl:if>

			<!-- Extended attributes -->
			<xsl:if test="$showExtendedAttributes = 'true'">
				<div class="section">
					<table>
						<thead>
							<tr>
								<th colspan="2" class="sectionTitle"><a id="extAttrs_{xpdl2:Package/@Id}"><xsl:value-of select="java:get($msgL10n, 'ExtendedAttributes_label')"/></a></th>
							</tr>
							<tr>
								<th class="columnWidth"><xsl:value-of select="java:get($msgL10n, 'Name_label')"/></th>
								<th><xsl:value-of select="java:get($msgL10n, 'Value_label')"/></th>
							</tr>
						</thead>
						<tbody>
							<xsl:for-each select="xpdl2:Package/xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute">
								<tr>
									<td><xsl:value-of select="@Name"/>&nbsp;</td>
									<td><xsl:call-template name="extAttrAsHtml"><xsl:with-param name="el" select="."/></xsl:call-template>&nbsp;</td>
								</tr>
							</xsl:for-each>
						</tbody>
					</table>
				</div>
			</xsl:if>
			<!-- Extended attributes - END -->

			<!-- Process Interface summary -->
			<xsl:apply-templates select="xpdl2:Package/xpdExt:ProcessInterfaces" mode="summary">
				<xsl:sort select="xpdExt:ProcessInterface/@xpdExt:DisplayName" data-type="text"/>
			</xsl:apply-templates>
			
			<!-- Workflow processes summary -->
			<xsl:apply-templates select="xpdl2:Package/xpdl2:WorkflowProcesses" mode="summary">
				<xsl:sort select="xpdl2:WorkflowProcess/@xpdExt:DisplayName" data-type="text"/>
				<xsl:with-param name="associations" select="$associations" />
				<xsl:with-param name="annotations" select="$annotations" />
				<xsl:with-param name="dataObjs" select="$dataObjs" />
				<xsl:with-param name="pools" select="$pools" />
			</xsl:apply-templates>

			<!-- Process Interface summary -->
			<xsl:apply-templates select="xpdl2:Package/xpdExt:ProcessInterfaces/xpdExt:ProcessInterface">
				<xsl:sort select="@xpdExt:DisplayName" data-type="text"/>
			</xsl:apply-templates>
			
			<!-- Worklfow processes -->
			<xsl:apply-templates select="xpdl2:Package/xpdl2:WorkflowProcesses/xpdl2:WorkflowProcess">
				<xsl:sort select="@xpdExt:DisplayName" data-type="text"/>
				<xsl:with-param name="associations" select="$associations" />
				<xsl:with-param name="annotations" select="$annotations" />
				<xsl:with-param name="dataObjs" select="$dataObjs" />
				<xsl:with-param name="pools" select="$pools" />
			</xsl:apply-templates>
		</body>
	</xsl:element>
</xsl:template>		


<!--
===================================================================================================================
	xpdl2:Participants = List all participants
===================================================================================================================
-->
<xsl:template match="xpdl2:Participants">
	<xsl:if test="count(xpdl2:Participant) > 0">
		<div class="section">
			<table>
				<thead>
					<tr>
						<th colspan="3" class="sectionTitle"><a id="parts_{parent::node()/@Id}"><xsl:value-of select="java:get($msgL10n, 'Participants_label')"/></a></th>
					</tr>
					<tr>
						<th class="columnWidth"><xsl:value-of select="java:get($msgL10n, 'Name_label')"/></th><th><xsl:value-of select="java:get($msgL10n, 'Type_label')"/></th><th style="width: 60%;"><xsl:value-of select="java:get($msgL10n, 'Description_label')"/></th>
					</tr>
				</thead>
				<tbody>
					<xsl:for-each select="xpdl2:Participant">
						<xsl:sort select="@xpdExt:DisplayName"/>
						<tr>
							<td><a id="{@Id}"><xsl:call-template name="getName"/></a>&nbsp;</td>
							<td>
								<xsl:choose>
									<xsl:when test="xpdl2:ExternalReference"><xsl:value-of select="java:get($msgL10n, 'ParticipantTypeExtRef_label')"/></xsl:when>
									<xsl:when test="xpdl2:ParticipantType/@Type = 'ORGANIZATIONAL_UNIT'"><xsl:value-of select="java:get($msgL10n, 'ParticipantTypeOrganisationalUnit_label')"/></xsl:when>
									<xsl:when test="xpdl2:ParticipantType/@Type = 'HUMAN'"><xsl:value-of select="java:get($msgL10n, 'ParticipantTypeHuman_label')"/></xsl:when>
									<xsl:when test="xpdl2:ParticipantType/@Type = 'SYSTEM'"><xsl:value-of select="java:get($msgL10n, 'ParticipantTypeSystem_label')"/></xsl:when>
									<xsl:otherwise><xsl:value-of select="java:get($msgL10n, 'ParticipantTypeRole_label')"/></xsl:otherwise>
								</xsl:choose>
								&nbsp;
							</td>
							<td>
								<xsl:call-template name="OutputDescription">
									<xsl:with-param name="descText" select="xpdl2:Description"/>
									<xsl:with-param name="docURL" select="xpdl2:Description/@xpdExt:DocumentationURL"/>
								</xsl:call-template>
								&nbsp;
							</td>
						</tr>
					</xsl:for-each>
				</tbody>
			</table>
		</div>
	</xsl:if>
</xsl:template>

<!--
===================================================================================================================
	xpdl2:TypeDeclarations = List all type declarations
===================================================================================================================
-->
<xsl:template match="xpdl2:TypeDeclarations">

	<xsl:if test="count(xpdl2:TypeDeclaration) > 0">
		<div class="section">
			<table>
				<thead>
					<tr>
						<th colspan="4" class="sectionTitle">
							<a id="types_{parent::node()/@Id}"><xsl:value-of select="java:get($msgL10n, 'TypeDeclarations_label')"/></a>
						</th>
					</tr>
					<tr>
						<th class="columnWidth"><xsl:value-of select="java:get($msgL10n, 'Name_label')"/></th><th><xsl:value-of select="java:get($msgL10n, 'Type_label')"/></th><th><xsl:value-of select="java:get($msgL10n, 'DataTypeLength_label')"/></th><th style="width: 60%;"><xsl:value-of select="java:get($msgL10n, 'Description_label')"/></th>
					</tr>
				</thead>
				<tbody>
					<xsl:for-each select="xpdl2:TypeDeclaration">
						<xsl:sort select="@xpdExt:DisplayName" data-type="text"/>
						<tr>
							<td><a id="{@Id}"><xsl:call-template name="getName"/></a>&nbsp;</td>
							<xsl:call-template name="outputDataType">
								<xsl:with-param name="dataId" select="@Id"/>
								<xsl:with-param name="dataContainer" select="../.."/>
							</xsl:call-template>
							<td>
								<xsl:call-template name="OutputDescription">
									<xsl:with-param name="descText" select="xpdl2:Description"/>
									<xsl:with-param name="docURL" select="xpdl2:Description/@xpdExt:DocumentationURL"/>
								</xsl:call-template>
								&nbsp;
							</td>
						</tr>
					</xsl:for-each>
				</tbody>
			</table>
		</div>
	</xsl:if>
	
</xsl:template>
	
<!--
===================================================================================================================
	xpdl2:DataFields = List all data fields
===================================================================================================================
-->
<xsl:template match="xpdl2:DataFields">

	<xsl:if test="count(xpdl2:DataField) > 0">
		<div class="section">
			<table>
				<thead>
					<tr>
						<th colspan="4" class="sectionTitle">
							<a id="fields_{parent::node()/@Id}"><xsl:value-of select="java:get($msgL10n, 'DataFieldsTitle_label')"/></a>
						</th>
					</tr>
					<tr>
						<th class="columnWidth"><xsl:value-of select="java:get($msgL10n, 'Name_label')"/></th><th><xsl:value-of select="java:get($msgL10n, 'Type_label')"/></th><th><xsl:value-of select="java:get($msgL10n, 'DataTypeLength_label')"/></th><th style="width: 60%;"><xsl:value-of select="java:get($msgL10n, 'Description_label')"/></th>
					</tr>
				</thead>
				<tbody>
					<xsl:for-each select="xpdl2:DataField">
						<xsl:sort select="@xpdExt:DisplayName" data-type="text"/>
						<tr>
							<xsl:variable name="id"><xsl:value-of select="../../@Id"/><xsl:text>_</xsl:text><xsl:value-of select="@Id"/></xsl:variable>
							
							<td><a id="{$id}"><xsl:call-template name="getName"/></a>&nbsp;</td>

							<xsl:call-template name="outputDataType">
								<xsl:with-param name="dataId" select="@Id"/>
								<xsl:with-param name="dataContainer" select="../.."/>
							</xsl:call-template>

							<td>
								<xsl:call-template name="OutputDescription">
									<xsl:with-param name="descText" select="xpdl2:Description"/>
									<xsl:with-param name="docURL" select="xpdl2:Description/@xpdExt:DocumentationURL"/>
								</xsl:call-template>
								&nbsp;
							</td>
						</tr>
					</xsl:for-each>
				</tbody>
			</table>
		</div>
	</xsl:if>
</xsl:template>

<!--
===================================================================================================================
	outputDataType - Output 2 td's for type and  length for the data type for given type info.
===================================================================================================================
-->
<xsl:template name="outputDataType">
	<xsl:param name="dataId"/>
	<xsl:param name="dataContainer"/>
	
	<td style="white-space: nowrap;">
		<xsl:variable name="basicType" select="java:getDataBasicType($javaPkgInfo, $dataContainer/@Id, $dataId)"/>
	
		<xsl:variable name="typeName">
			<xsl:choose>
				<xsl:when test="$basicType != ''">
					<xsl:choose>
						<xsl:when test="$basicType = 'STRING'"><xsl:value-of select="java:get($msgL10n, 'DataTypeString_label')"/></xsl:when>
						<xsl:when test="$basicType = 'INTEGER'"><xsl:value-of select="java:get($msgL10n, 'DataTypeInteger_label')"/></xsl:when>
						<xsl:when test="$basicType = 'FLOAT'"><xsl:value-of select="java:get($msgL10n, 'DataTypeFloat_label')"/></xsl:when>
						<xsl:when test="$basicType = 'BOOLEAN'"><xsl:value-of select="java:get($msgL10n, 'DataTypeBoolean_label')"/></xsl:when>
						<xsl:when test="$basicType = 'PERFORMER'"><xsl:value-of select="java:get($msgL10n, 'DataTypePerformer_label')"/></xsl:when>
						<xsl:when test="$basicType = 'DATETIME'"><xsl:value-of select="java:get($msgL10n, 'DataTypeDateTime_label')"/></xsl:when>
						<xsl:when test="$basicType = 'DATE'"><xsl:value-of select="java:get($msgL10n, 'DataTypeDate_label')"/></xsl:when>
						<xsl:when test="$basicType = 'TIME'"><xsl:value-of select="java:get($msgL10n, 'DataTypeTime_label')"/></xsl:when>
						<xsl:otherwise><xsl:value-of select="$basicType"/></xsl:otherwise>
					</xsl:choose>
				</xsl:when>
				<xsl:otherwise>
					<xsl:variable name="declTypeId" select="java:getDataDeclaredTypeId($javaPkgInfo, $dataContainer/@Id, $dataId)"/>
					<xsl:choose>
						<xsl:when test="$declTypeId != ''">
							<a href="{java:getDeclaredTypeHrefId($javaPkgInfo, $dataContainer/@Id, $dataId)}"><xsl:value-of select="java:getDeclaredTypeName($javaPkgInfo, $dataContainer/@Id, $dataId)"/></a>
						</xsl:when>

						<xsl:otherwise>
							<xsl:variable name="tName" select="java:getDataExternalTypeName($javaPkgInfo, $dataContainer/@Id, $dataId)"/>
							<xsl:if test="$tName != ''">								
								<xsl:value-of select="$tName"/>								
							</xsl:if>
						</xsl:otherwise>
					</xsl:choose>
					
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>

		<xsl:choose>
			<xsl:when test="$typeName != ''"><xsl:copy-of select="$typeName"/></xsl:when>
			<xsl:otherwise><i><xsl:value-of select="java:get($msgL10n, 'Unspecified_label')"/></i></xsl:otherwise>
		</xsl:choose>

		&nbsp;
		<xsl:if test="java:isDataArray($javaPkgInfo, $dataContainer/@Id, $dataId)">
			<xsl:value-of select="java:get($msgL10n, 'DataTypeArray_label')"/>
		</xsl:if>
	</td>

	<!-- output length details -->
	<td>
		<xsl:variable name="len" select="java:getDataBasicTypeLength($javaPkgInfo, $dataContainer/@Id, $dataId)"/>
		<xsl:value-of select="$len"/>
		&nbsp;
	</td>

</xsl:template>


<!--
===================================================================================================================
	xpdExt:ProcessInterfaces (summary) = Summary of the process interfaces
===================================================================================================================
-->
<xsl:template match="xpdExt:ProcessInterfaces" mode="summary">
	<xsl:if test="xpdExt:ProcessInterface">
		<div class="section">
			<table>
				<thead>
					<tr>
						<th colspan="2" class="sectionTitle"><a id="interface_{../@Id}"><xsl:value-of select="java:get($msgL10n, 'ProcessInterfaces_label')"/></a></th>
					</tr>
					<tr>
						<th class="columnWidth"><xsl:value-of select="java:get($msgL10n, 'Name_label')"/></th><th style="width: 60%;"><xsl:value-of select="java:get($msgL10n, 'Description_label')"/></th>
					</tr>
				</thead>
				<tbody>
					<xsl:for-each select="xpdExt:ProcessInterface">
						<xsl:sort select="@xpdExt:DisplayName"/>
						<tr>
							<!--  Name -->
							<td><a href="#{@Id}"><xsl:call-template name="getName"/></a>&nbsp;</td>
							<!-- Description -->
							<td>
								<xsl:choose>
									<xsl:when test="string-length(xpdl2:Description) > 70"><xsl:value-of select="substring(xpdl2:Description, 1, 70)"/>...</xsl:when>
									<xsl:otherwise><xsl:value-of select="xpdl2:Description"/>&nbsp;</xsl:otherwise>
								</xsl:choose>
								&nbsp;
							</td>
						</tr>
					</xsl:for-each>
				</tbody>
			</table>
		</div>
	</xsl:if>
</xsl:template>
	
<!--
===================================================================================================================
	xpdl2:WorkflowProcesses (summary) = Summary of the processes
===================================================================================================================
-->
<xsl:template match="xpdl2:WorkflowProcesses" mode="summary">
	<xsl:param name="associations" />
	<xsl:param name="annotations" />
	<xsl:param name="dataObjs" />
	<xsl:param name="pools" />
			
	<div class="section">
		<table>
			<thead>
				<tr>
					<th colspan="2" class="sectionTitle"><a id="process_{../@Id}"><xsl:value-of select="java:get($msgL10n, 'Processes_label')"/></a></th>
				</tr>
				<tr>
					<th class="columnWidth"><xsl:value-of select="java:get($msgL10n, 'Name_label')"/></th><th style="width: 60%;"><xsl:value-of select="java:get($msgL10n, 'Description_label')"/></th>
				</tr>
			</thead>
			<tbody>
				<xsl:for-each select="xpdl2:WorkflowProcess">
					<xsl:sort select="@xpdExt:DisplayName"/>
					<tr>
						<!--  Name -->
						<td><a href="#{@Id}"><xsl:call-template name="getName"/></a>&nbsp;</td>
						<!-- Description -->
						<td>
							<xsl:choose>
								<xsl:when test="string-length(xpdl2:ProcessHeader/xpdl2:Description) > 70"><xsl:value-of select="substring(xpdl2:ProcessHeader/xpdl2:Description, 1, 70)"/>...</xsl:when>
								<xsl:otherwise><xsl:value-of select="xpdl2:ProcessHeader/xpdl2:Description"/>&nbsp;</xsl:otherwise>
							</xsl:choose>
							&nbsp;
						</td>
					</tr>
				</xsl:for-each>
			</tbody>
		</table>
	</div>
</xsl:template>


<!--
===================================================================================================================
	xpdExt:ProcessInterface = Process details
===================================================================================================================
-->
<xsl:template match="xpdExt:ProcessInterface">

	<!-- Show progress on each Process Interface / Process that is done. -->
	<xsl:variable name="ifcName">
		<xsl:choose>
			<xsl:when test="@xpdExt:DisplayName != ''"><xsl:value-of select="@xpdExt:DisplayName"/></xsl:when>
			<xsl:otherwise><xsl:value-of select="@Name"/></xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:value-of select="java:com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.monitorStartSubTask($transformMonitorId, $ifcName)"/>	

	<div class="section">
		<table>
			<tbody>
				<tr>
					<th class="processTitle" colspan="2">
						<a id="{@Id}">
							<xsl:value-of select="java:get($msgL10n, 'ProcessInterface_label')"/><xsl:text>: </xsl:text><xsl:call-template name="getName"/>
						</a>
					</th>
				</tr>

				<!-- Description -->
				
				<xsl:if test="xpdl2:Description != '' or xpdl2:Description/@xpdExt:DocumentationURL != ''">
					<tr>
						<th class="heading"><xsl:value-of select="java:get($msgL10n, 'Description_label')"/></th>
						<td>
							<xsl:call-template name="OutputDescription">
								<xsl:with-param name="descText" select="xpdl2:Description"/>
								<xsl:with-param name="docURL" select="xpdl2:Description/@xpdExt:DocumentationURL"/>
							</xsl:call-template>
							&nbsp;
						</td>
					</tr>
				</xsl:if>

				<!-- Destination Environments... -->
				<xsl:variable name="dest" select="xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'Destination']"/>
				<xsl:if test="$dest">
					<tr>
						<th class="heading"><xsl:value-of select="java:get($msgL10n, 'DestEnvs_label')"/></th>
						<td>
							<xsl:for-each select="$dest">
								<xsl:if test="position() != 1">,&nbsp;</xsl:if>
								<xsl:value-of select="@Value"/>
							</xsl:for-each>
							&nbsp;
						</td>
					</tr>
				</xsl:if>
				
				<!-- XPD-7193: Add Interface Type. -->
				<xsl:variable name="interfaceType" select="@XpdInterfaceType"/>
				<tr>
					<th class="heading"><xsl:value-of select="java:get($msgL10n, 'InterfaceType_label')"/></th>
					<td>
					<xsl:choose>
						<xsl:when test="$interfaceType ='ServiceProcessInterface'">
					  	  <xsl:value-of select="java:get($msgL10n, 'ServiceProcessInterface_label')"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="java:get($msgL10n, 'ProcessInterface_label')"/>
						</xsl:otherwise>
					</xsl:choose>
					</td>
				</tr>
				
				<!-- XPD-7193: Add Service Process Interface Deployment Target Info. -->
				<xsl:if test="$interfaceType ='ServiceProcessInterface'">
				<tr>
					<th class="heading"><xsl:value-of select="java:get($msgL10n, 'ProcessRuntimeDeploy_label')"/></th>
					<td>
						<xsl:if test="xpdExt:ServiceProcessConfiguration/@xpdExt:DeployToProcessRuntime ='true'">
							<img alt="Yes" src="images/tick.gif"/>
						</xsl:if>
					</td>
				</tr>
				<tr>
					<th class="heading"><xsl:value-of select="java:get($msgL10n, 'PageflowRuntimeDeploy_label')"/></th>
					<td>
						<xsl:if test="xpdExt:ServiceProcessConfiguration/@xpdExt:DeployToPageflowRuntime ='true'">
							<img alt="Yes" src="images/tick.gif"/>
						</xsl:if>
					</td>
				</tr>
				</xsl:if>

				<!-- Content summary -->
				<tr>
					<th class="heading"><xsl:value-of select="java:get($msgL10n, 'ContentSummary_label')"/></th>
					<td>
						<xsl:value-of select="java:get($msgL10n, 'FormalParameters_label')"/>(<xsl:value-of select="java:getFormalParameterCount($javaPkgInfo, @Id)"/>)&nbsp;&nbsp;
						<xsl:value-of select="java:get($msgL10n, 'StartEvents_label')"/>(<xsl:value-of select="count(xpdExt:StartMethods/xpdExt:StartMethod)"/>)&nbsp;&nbsp;
						<xsl:value-of select="java:get($msgL10n, 'IntermediateEvents_label')"/>(<xsl:value-of select="count(xpdExt:IntermediateMethods/xpdExt:IntermediateMethod)"/>)&nbsp;&nbsp;
					</td>
				</tr>
				
				<!-- Quick links (don't output for print -->
				<tr class="screenOnly">
					<th class="heading"><xsl:value-of select="java:get($msgL10n, 'QuickLinks_label')"/></th>
					<td>
						<xsl:if test="java:getFormalParameterCount($javaPkgInfo, @Id) > 0 and $showFormalParameters = 'true'">
							<a href="#formals_{@Id}"><xsl:value-of select="java:get($msgL10n, 'FormalParameters_label')"/>,&nbsp;</a>
						</xsl:if>
						<xsl:if test="xpdExt:StartMethods/xpdExt:StartMethod or xpdExt:IntermediateMethods/xpdExt:IntermediateMethod">
							<a href="#interfaceEvents_{@Id}"><xsl:value-of select="java:get($msgL10n, 'InterfaceEvents_label')"/>,&nbsp;</a>
						</xsl:if>
						<xsl:if test="xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name != 'Destination' and not(simulation:*)] and $showExtendedAttributes = 'true'">
							<a href="#extAttrs_{@Id}"><xsl:value-of select="java:get($msgL10n, 'ExtendedAttributes_label')"/>,&nbsp;</a>
						</xsl:if>
						<a href="#{../../@Id}"><xsl:value-of select="java:get($msgL10n, 'Package_label')"/></a>
						&nbsp;
					</td>
				</tr>
				
			</tbody>
		</table>
		
		<!-- Formal Parameters -->
		<xsl:if test="$showFormalParameters = 'true'">
			<xsl:call-template name="OutputFormalParameters">
				<xsl:with-param name="paramContainer" select="."/>
			</xsl:call-template>
		</xsl:if>
		
		<!-- Events -->
		<xsl:call-template name="OutputInterfaceEvents">
			<xsl:with-param name="interface" select="."/>
		</xsl:call-template>
		
		<!-- Extended attributes (but not destination environment - that's handled above.-->
		<xsl:if test="$showExtendedAttributes = 'true'">
			<xsl:variable name="extAttr" select="xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name != 'Destination' and not(simulation:*)]"/>
			<xsl:if test="$extAttr">
				<div class="section">
					<table>
						<thead>
							<tr>
								<th colspan="2" class="sectionTitle"><a id="extAttrs_{@Id}"><xsl:value-of select="java:get($msgL10n, 'ExtendedAttributes_label')"/></a></th>
							</tr>
							<tr>
								<th class="columnWidth"><xsl:value-of select="java:get($msgL10n, 'Name_label')"/></th>
								<th><xsl:value-of select="java:get($msgL10n, 'Value_label')"/></th>
							</tr>
						</thead>
						<tbody>
							<xsl:for-each select="$extAttr">
								<tr>
									<td><xsl:value-of select="@Name"/>&nbsp;</td>
									<td><xsl:call-template name="extAttrAsHtml"><xsl:with-param name="el" select="."/></xsl:call-template>&nbsp;</td>
								</tr>
							</xsl:for-each>
						</tbody>
					</table>
				</div>
			</xsl:if>
		</xsl:if>
		<!-- Extended attributes - END -->

	</div>

	<!-- Show progress on each Process Interface / Process that is done. -->
	<xsl:value-of select="java:com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.monitorSubTaskDone($transformMonitorId)"/>	

</xsl:template>

<!--
===================================================================================================================
	OutputInterfaceEvents - output process interface events.
===================================================================================================================
-->
<xsl:template name="OutputInterfaceEvents">
	<xsl:param name="interface"/>

	<xsl:if test="$interface/xpdExt:StartMethods/xpdExt:StartMethod or $interface/xpdExt:IntermediateMethods/xpdExt:IntermediateMethod">
		<div id="interfaceEvents_{$interface/@Id}">

			<xsl:for-each select="$interface/xpdExt:StartMethods/xpdExt:StartMethod | $interface/xpdExt:IntermediateMethods/xpdExt:IntermediateMethod">
				<xsl:sort select="@xpdExt:DisplayName" data-type="text"/>

				<xsl:variable name="eventTypeName">
					<xsl:choose>
						<xsl:when test="local-name() = 'StartMethod'">
							<xsl:choose>
								<xsl:when test="@Trigger = 'Message'"><xsl:value-of select="java:get($msgL10n, 'MethodTypeSTARTEVENTMessage_label')"/></xsl:when>
								<xsl:otherwise><xsl:value-of select="java:get($msgL10n, 'MethodTypeSTARTEVENT_label')"/></xsl:otherwise>
							</xsl:choose>
						</xsl:when>
						<xsl:otherwise>
							<xsl:choose>
								<xsl:when test="@Trigger = 'Message'"><xsl:value-of select="java:get($msgL10n, 'MethodTypeINTEREVENTMessage_label')"/></xsl:when>
								<xsl:otherwise><xsl:value-of select="java:get($msgL10n, 'MethodTypeINTEREVENT_label')"/></xsl:otherwise>
							</xsl:choose>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				
				<div class="section">
					<table >
						<thead>
							<tr>
								<th class="sectionTitle">
									<a id="{@Id}"><xsl:call-template name="getName"/></a>
								</th>
								<th class="sectionTitle screenOnly" style="text-align: right; font-weight: normal;">
									<a href="#{$interface/@Id}"><xsl:value-of select="java:get($msgL10n, 'GotoProcessInterface_label')"/></a>
									&nbsp;
								</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<th class="heading">
									<xsl:value-of select="java:get($msgL10n, 'Type_label')"/>
								</th>
								<td>
									<xsl:value-of select="$eventTypeName"/>
									&nbsp;
								</td>
							</tr>
							
							<!-- Description -->
							<xsl:if test="xpdl2:Description">
								<tr>
									<th><xsl:value-of select="java:get($msgL10n, 'Description_label')"/></th>
									<td>
										<xsl:call-template name="OutputDescription">
											<xsl:with-param name="descText" select="xpdl2:Description"/>
											<xsl:with-param name="docURL" select="xpdl2:Description/@xpdExt:DocumentationURL"/>
										</xsl:call-template>
										&nbsp;
									</td>
								</tr>
							</xsl:if>
						
							<xsl:choose>
								<!-- Event types -->
								<xsl:when test="@Trigger = 'Message'">
									<xsl:call-template name="outputTriggerResultMessage">
										<xsl:with-param name="message" select="xpdExt:TriggerResultMessage"/>
										<xsl:with-param name="activityId" select="''" />
										<xsl:with-param name="processId" select="''" />
									</xsl:call-template>
								</xsl:when> 
								
							</xsl:choose>
				
							<xsl:call-template name="OutputActivityInterface">
								<xsl:with-param name="dataContainer" select="$interface"/>
								<xsl:with-param name="actOrMethod" select="."/>
							</xsl:call-template>
				
				
							<!-- Add extended attributes (except ones we know about and handle during normal course of doc)  -->
							<xsl:if test="$showExtendedAttributes = 'true'">
								<xsl:if test="xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[not(simulation:*)]">
									<tr>
										<th class="heading"><xsl:value-of select="java:get($msgL10n, 'ExtendedAttributes_label')"/></th>
										<td>
											<table>
												<thead>
													<tr>
														<th><xsl:value-of select="java:get($msgL10n, 'Name_label')"/></th><th><xsl:value-of select="java:get($msgL10n, 'Value_label')"/></th>
													</tr>
												</thead>
												<tbody>
													<xsl:variable name="numAttr" select="count(xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name != 'bpmJspTask'])"/>
													<xsl:for-each select="xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name != 'bpmJspTask']">
														<tr>
															<xsl:variable name="class">
																<xsl:choose>
																	<xsl:when test="position() = $numAttr">noBorder</xsl:when>
																	<xsl:otherwise></xsl:otherwise>
																</xsl:choose>
															</xsl:variable>
															
															<td class="{$class}"><xsl:value-of select="@Name"/>&nbsp;</td>
															<td class="{$class}"><xsl:call-template name="extAttrAsHtml"><xsl:with-param name="el" select="."/></xsl:call-template>&nbsp;</td>
														</tr>
													</xsl:for-each>
												</tbody>
											</table>
											&nbsp;
										</td>
									</tr>
								</xsl:if>
							</xsl:if>
						</tbody>
					</table>
				</div>
			</xsl:for-each>

		</div>
		
	</xsl:if>
	
</xsl:template>

	
<!--
===================================================================================================================
	xpdl2:WorkflowProcess = Process details
===================================================================================================================
-->
<xsl:template match="xpdl2:WorkflowProcess">
	<xsl:param name="associations" />
	<xsl:param name="annotations" />
	<xsl:param name="dataObjs" />
	<xsl:param name="pools" />

	<!-- Show progress on each Process Interface / Process that is done. -->
	<xsl:variable name="prcName">
		<xsl:choose>
			<xsl:when test="@xpdExt:DisplayName != ''"><xsl:value-of select="@xpdExt:DisplayName"/></xsl:when>
			<xsl:otherwise><xsl:value-of select="@Name"/></xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	<xsl:value-of select="java:com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.monitorStartSubTask($transformMonitorId, $prcName)"/>	

		
	<!-- We may reference data fields and participants in process and package a lot. Best thing to do is store them all in a var
		and pass them down to all our templates.
	-->
	<xsl:variable name="participants" select="/xpdl2:Package/xpdl2:Participants/xpdl2:Participant | xpdl2:Participants/xpdl2:Participant"/>
	<xsl:variable name="actSets" select="xpdl2:ActivitySets/xpdl2:ActivitySet"/>
	
	<!-- 
		To determine which data objects and annotations belong to this process we need to get 
		the lanes of the process and use this to identify the artifacts
	 -->
	<xsl:variable name="processId" select="@Id" />
	<xsl:variable name="lanes" select="$pools[@Process = $processId]/xpdl2:Lanes/xpdl2:Lane" />
	<!-- Select annotations that have no associations and belong to this process -->
	<xsl:variable name="processAnnotations" 
		select="$annotations[xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo/@LaneId = $lanes/@Id or xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo/@LaneId = $actSets/@Id]"/>
	
	<xsl:variable name="processDataObjs" select="$dataObjs[xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo/@LaneId = $lanes/@Id]" />

	<div class="section">
		<table>
			<tbody>
				<tr>
					<th class="processTitle" colspan="2">
						<a id="{@Id}">
							<xsl:value-of select="java:get($msgL10n, 'Process_label')"/><xsl:text>: </xsl:text><xsl:call-template name="getName"/>
						</a>
					</th>
				</tr>

				<!-- Implements interface. -->
				<xsl:if test="xpdExt:ImplementedInterface">
					<tr>
						<th class="heading">
						<!-- XPD-7193: Make label specific so that it tells the type of interface the process implements. -->
						<xsl:choose>
							<xsl:when test="@xpdExt:XpdModelType = 'ServiceProcess'">
								<xsl:value-of select="java:get($msgL10n, 'ImplementsServiceInterface_label')"/>
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="java:get($msgL10n, 'ImplementsInterface_label')"/>
							</xsl:otherwise>
						</xsl:choose>						
						</th>
						<td>
							<xsl:variable name="ifcName" select="java:getImplementedInterfaceName($javaPkgInfo, $processId)"/>
							<xsl:if test="$ifcName != ''">
								<a href="{java:getImplementedInterfaceHrefId($javaPkgInfo, $processId)}"><xsl:value-of select="$ifcName"/>&nbsp;</a>
							</xsl:if>
							&nbsp;
						</td>
					</tr>
				</xsl:if>

				<!-- XPD-7193: Add Service Process Interface Deployment Target Info. -->
				<!-- XPD-7513: Add service procss deploy targets for processes not implementing ifc as well 
				     as those that do (and make consistent with process interface output for deploy targets)-->
				<xsl:if test="@xpdExt:XpdModelType = 'ServiceProcess'">
					<tr>
						<th class="heading"><xsl:value-of select="java:get($msgL10n, 'ProcessRuntimeDeploy_label')"/></th>
						<td>
							<xsl:if test="java:isProcessEngineServiceProcess($javaPkgInfo, $processId)">
								<img alt="Yes" src="images/tick.gif"/>
							</xsl:if>
						</td>
					</tr>
					<tr>
						<th class="heading"><xsl:value-of select="java:get($msgL10n, 'PageflowRuntimeDeploy_label')"/></th>
						<td>
							<xsl:if test="java:isPageflowEngineServiceProcess($javaPkgInfo, $processId)">
								<img alt="Yes" src="images/tick.gif"/>
							</xsl:if>
						</td>
					</tr>
				</xsl:if>
				<!--  XPD-7513 END -->

				<!-- Description -->
				<!-- General Notes -->
				<xsl:variable name="annotText">
					<!-- only print this section if we have general notes in the process -->
					<xsl:if test="$annotations[not (@Id = $associations/@Source or @Id = $associations/@Target)]">
						<!-- annotations without associations... -->
						<xsl:variable name="annots" select="$annotations[not (@Id = $associations/@Source or @Id = $associations/@Target)]"/>

						<xsl:variable name="indent" select="xpdl2:ProcessHeader/xpdl2:Description != ''"/>
			
						<xsl:for-each select="$lanes">
							<xsl:variable name="laneId" select="@Id"/>
							<xsl:variable name="laneAnnots" select="$annots[xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo/@LaneId = $laneId]"/>
			
							<xsl:if test="$laneAnnots">
								<xsl:if test="$indent">
									<xsl:call-template name="indent"><xsl:with-param name="level" select="1"/></xsl:call-template>
								</xsl:if>
								<a style="font-weight: bolder;"><xsl:call-template name="getName"><xsl:with-param name="node" select="../.."/></xsl:call-template> - <xsl:call-template name="getName"/></a><br/>
				
								<xsl:variable name="numAnnots" select="count($laneAnnots)"/>
			
								<xsl:for-each select="$laneAnnots">
									<xsl:choose>
										<xsl:when test="$indent">
											<xsl:call-template name="newline2br"><xsl:with-param name="input" select="@TextAnnotation"/><xsl:with-param name="indent" select="2"/></xsl:call-template><br/>
										</xsl:when>
										<xsl:otherwise>
											<xsl:call-template name="newline2br"><xsl:with-param name="input" select="@TextAnnotation"/><xsl:with-param name="indent" select="1"/></xsl:call-template><br/>
										</xsl:otherwise>
									</xsl:choose>
									
									<xsl:if test="position() = $numAnnots">
										<br/>
									</xsl:if>
								</xsl:for-each>
							</xsl:if>
			
						</xsl:for-each>
					</xsl:if>
				</xsl:variable>
				
				<xsl:if test="xpdl2:ProcessHeader/xpdl2:Description or $annotText != ''">
					<tr>
						<th class="heading"><xsl:value-of select="java:get($msgL10n, 'Description_label')"/></th>
						<td>
							<xsl:if test="xpdl2:ProcessHeader/xpdl2:Description">
								<xsl:call-template name="OutputDescription">
									<xsl:with-param name="descText" select="xpdl2:ProcessHeader/xpdl2:Description"/>
									<xsl:with-param name="docURL" select="xpdl2:ProcessHeader/xpdl2:Description/@xpdExt:DocumentationURL"/>
								</xsl:call-template>
								&nbsp;
							</xsl:if>
							<xsl:if test="$annotText != '' and xpdl2:ProcessHeader/xpdl2:Description != ''">
								<br/><a style="font-weight: bolder;"><xsl:value-of select="java:get($msgL10n, 'Annotations_label')"/></a><br/>
							</xsl:if>
							<xsl:if test="$annotText != ''">
								<xsl:copy-of select="$annotText"/>
							</xsl:if>
						</td>
					</tr>
				</xsl:if>

				<!-- Destination Environments... -->
				<xsl:variable name="dest" select="xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'Destination']"/>
				<xsl:if test="$dest">
					<tr>
						<th class="heading"><xsl:value-of select="java:get($msgL10n, 'DestEnvs_label')"/></th>
						<td>
							<xsl:for-each select="$dest">
								<xsl:if test="position() != 1">,&nbsp;</xsl:if>
								<xsl:value-of select="@Value"/>
							</xsl:for-each>
							&nbsp;
						</td>
					</tr>
				</xsl:if>

				<!-- Content summary -->
				<tr>
					<th class="heading"><xsl:value-of select="java:get($msgL10n, 'ContentSummary_label')"/></th>
					<td>
						<xsl:value-of select="java:get($msgL10n, 'Participants_label')"/>(<xsl:value-of select="count(xpdl2:Participants/xpdl2:Participant)"/>)&nbsp;&nbsp;
						<xsl:value-of select="java:get($msgL10n, 'FormalParameters_label')"/>(<xsl:value-of select="java:getFormalParameterCount($javaPkgInfo, $processId)"/>)&nbsp;&nbsp;
						<xsl:value-of select="java:get($msgL10n, 'DataFieldsTitle_label')"/>(<xsl:value-of select="count(xpdl2:DataFields/xpdl2:DataField)"/>)&nbsp;&nbsp;
						Pools(<xsl:value-of select="count($pools[@Process = $processId])"/>)&nbsp;&nbsp;
						<xsl:value-of select="java:get($msgL10n, 'TasksAndEvents_label')"/>(<xsl:value-of select="count(xpdl2:Activities/xpdl2:Activity[xpdl2:Implementation/xpdl2:Task or 
																					xpdl2:Implementation/xpdl2:SubFlow or 
																					xpdl2:Implementation/xpdl2:BlockActivity or 
																					xpdl2:Implementation/xpdl2:Reference or xpdl2:Event[not(xpdl2:IntermediateEvent/@Trigger = 'Link')]])"/>)&nbsp;&nbsp;
						<xsl:value-of select="java:get($msgL10n, 'DataObjs_label')"/>(<xsl:value-of select="count($processDataObjs)"/>)&nbsp;&nbsp;
					</td>
				</tr>
				
				<!-- Quick links (don't output for print -->
				<tr class="screenOnly">
					<th class="heading"><xsl:value-of select="java:get($msgL10n, 'QuickLinks_label')"/></th>
					<td>
						<xsl:if test="xpdl2:Participants/xpdl2:Participant and $showParticipants = 'true'">
							<a href="#parts_{@Id}"><xsl:value-of select="java:get($msgL10n, 'Participants_label')"/>,&nbsp;</a>
						</xsl:if>
						<xsl:if test="java:getFormalParameterCount($javaPkgInfo, $processId) > 0 and $showFormalParameters = 'true'">
							<a href="#formals_{@Id}"><xsl:value-of select="java:get($msgL10n, 'FormalParameters_label')"/>,&nbsp;</a>
						</xsl:if>
						<xsl:if test="xpdl2:DataFields/xpdl2:DataField and $showDataFields = 'true'">
							<a href="#fields_{@Id}"><xsl:value-of select="java:get($msgL10n, 'DataFieldsTitle_label')"/>,&nbsp;</a>
						</xsl:if>
						<xsl:if test="xpdl2:Activities/xpdl2:Activity">
							<a href="#acts_{@Id}"><xsl:value-of select="java:get($msgL10n, 'PoolLaneContent_label')"/>,&nbsp;</a>
						</xsl:if>
						<xsl:if test="xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name != 'Destination' and not(simulation:*)] and $showExtendedAttributes = 'true'">
							<a href="#extAttrs_{@Id}"><xsl:value-of select="java:get($msgL10n, 'ExtendedAttributes_label')"/>,&nbsp;</a>
						</xsl:if>
						<a href="#{../../@Id}"><xsl:value-of select="java:get($msgL10n, 'Package_label')"/></a>
						&nbsp;
					</td>
				</tr>
				
			</tbody>
		</table>
		
		<!-- Process image -->
		<xsl:if test="$showProcessImages = 'true'">
			<xsl:call-template name="outputProcessImage">
				<xsl:with-param name="process" select="."/>
			</xsl:call-template>
		</xsl:if>

		<!-- Process Participants -->
		<xsl:if test="$showParticipants = 'true'">
			<xsl:apply-templates select="xpdl2:Participants"/>
		</xsl:if>

		<!-- Formal Parameters -->
		<xsl:if test="$showFormalParameters = 'true'">
			<xsl:call-template name="OutputFormalParameters">
				<xsl:with-param name="paramContainer" select="."/>
			</xsl:call-template>
		</xsl:if>
		
		<!-- Process Data Fields -->
		<xsl:if test="$showDataFields = 'true'">
			<xsl:apply-templates select="xpdl2:DataFields">
			</xsl:apply-templates>
		</xsl:if>
		
		<!-- Activities summary -->
		<xsl:apply-templates select="xpdl2:Activities" mode="summary">
			<xsl:with-param name="processDataObjs" select="$processDataObjs"/>
		</xsl:apply-templates>
		
		<!-- Activities -->
		<xsl:apply-templates select="xpdl2:Activities/xpdl2:Activity">
			<xsl:sort select="@xpdExt:DisplayName" data-type="text"/>
			<xsl:with-param name="participants" select="$participants"/>
			<xsl:with-param name="associations" select="$associations" />
			<xsl:with-param name="annotations" select="$processAnnotations"/>
			<xsl:with-param name="dataObjs" select="$dataObjs"/>
			<xsl:with-param name="processes" select="../xpdl2:WorkflowProcess" />
			<xsl:with-param name="activitySets" select="xpdl2:ActivitySets/xpdl2:ActivitySet"/>
			<xsl:with-param name="process" select="."/>
		</xsl:apply-templates>
		
		<!-- Data objects -->
		<xsl:if test="$processDataObjs">
			<xsl:call-template name="outputDataObjects">
				<xsl:with-param name="dataObjs" select="$processDataObjs" />
				<xsl:with-param name="associations" select="$associations" />
				<xsl:with-param name="process" select="." />
			</xsl:call-template>
		</xsl:if>
	
		<!-- Extended attributes (but not destination environment - that's handled above.-->
		<xsl:if test="$showExtendedAttributes = 'true'">
			<xsl:variable name="extAttr" select="xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name != 'Destination' and not(simulation:*)]"/>
			<xsl:if test="$extAttr">
				<div class="section">
					<table>
						<thead>
							<tr>
								<th colspan="2" class="sectionTitle"><a id="extAttrs_{@Id}"><xsl:value-of select="java:get($msgL10n, 'ExtendedAttributes_label')"/></a></th>
							</tr>
							<tr>
								<th class="columnWidth"><xsl:value-of select="java:get($msgL10n, 'Name_label')"/></th>
								<th><xsl:value-of select="java:get($msgL10n, 'Value_label')"/></th>
							</tr>
						</thead>
						<tbody>
							<xsl:for-each select="$extAttr">
								<tr>
									<td><xsl:value-of select="@Name"/>&nbsp;</td>
									<td><xsl:call-template name="extAttrAsHtml"><xsl:with-param name="el" select="."/></xsl:call-template>&nbsp;</td>
								</tr>
							</xsl:for-each>
						</tbody>
					</table>
				</div>
			</xsl:if>
		</xsl:if>
		<!-- Extended attributes - END -->

	</div>
	
	<!-- Show progress on each Process Interface / Process that is done. -->
	<xsl:value-of select="java:com.tibco.xpd.ui.importexport.utils.ImportExportTransformer.monitorSubTaskDone($transformMonitorId)"/>	
	
</xsl:template>

<!--
===================================================================================================================
	xpdl2:FormalParameters = List all formals
===================================================================================================================
-->
<xsl:template name="OutputFormalParameters">
	<xsl:param name="paramContainer"/>

	<xsl:if test="java:getFormalParameterCount($javaPkgInfo, $paramContainer/@Id) > 0">
		<div class="section">
			<table>
				<thead>
					<tr>
						<th colspan="6" class="sectionTitle">
							<a id="formals_{$paramContainer/@Id}"><xsl:value-of select="java:get($msgL10n, 'FormalParameters_label')"/></a>
						</th>
					</tr>
					<tr>
						<th class="columnWidth"><xsl:value-of select="java:get($msgL10n, 'Name_label')"/></th><th><xsl:value-of select="java:get($msgL10n, 'ParameterDirection_label')"/></th><th><xsl:value-of select="java:get($msgL10n, 'Mandatory_label')"/></th><th><xsl:value-of select="java:get($msgL10n, 'Type_label')"/></th><th><xsl:value-of select="java:get($msgL10n, 'DataTypeLength_label')"/></th><th style="width: 60%;"><xsl:value-of select="java:get($msgL10n, 'Description_label')"/></th>
					</tr>
				</thead>
				<tbody>
					<!-- Get a list of formal parameters -->
					<xsl:variable name="paramIds" select="java:getAvailableDataIds($javaPkgInfo, $paramContainer/@Id, 'IF')"/>

					<xsl:if test="$paramIds != ''">
						<xsl:call-template name="OutputFormalParamFromIds">
							<xsl:with-param name="paramIds" select="$paramIds"/>
							<xsl:with-param name="dataContainer" select="$paramContainer"/>
						</xsl:call-template>
					</xsl:if>
					
				</tbody>
			</table>
		</div>
	</xsl:if>
</xsl:template>	

<!--
===================================================================================================================
	Take the first formal parameter id of of the newline-separated list and output the table row for it.
===================================================================================================================
-->
<xsl:template name="OutputFormalParamFromIds">
	<xsl:param name="paramIds"/>
	<xsl:param name="dataContainer"/>
	
	<xsl:variable name="id" select="substring-before($paramIds, '&#10;')"/>
	<xsl:if test="$id != ''">
		<tr>
			<td>
				<xsl:variable name="localProcessDataId"><xsl:value-of select="$dataContainer/@Id"/><xsl:text>_</xsl:text><xsl:value-of select="$id"/></xsl:variable>
				<xsl:choose>
					<xsl:when test="java:isImplementedInterfaceParameter($javaPkgInfo, $dataContainer/@Id, $id)">
						<!-- If it's a parameter inherited from an interface then make it a hyper line to the actual interface parameter in doc. -->
						<a id="{$localProcessDataId}" href="{java:getInterfaceParamDataHrefId($javaPkgInfo, $dataContainer/@Id, $id)}"><xsl:value-of select="java:getDataName($javaPkgInfo, $dataContainer/@Id, $id, $showTokenName)"/></a>
					</xsl:when>
					<xsl:otherwise>
						<a id="{$localProcessDataId}"><xsl:value-of select="java:getDataName($javaPkgInfo, $dataContainer/@Id, $id, $showTokenName)"/></a>
					</xsl:otherwise>
				</xsl:choose>
				&nbsp;

			</td>
			
			<td style="white-space: nowrap;">
				<xsl:call-template name="getInOutModeText"><xsl:with-param name="mode" select="java:getParameterMode($javaPkgInfo, $dataContainer/@Id, $id)"/></xsl:call-template>
				&nbsp;
			</td>

			<td>
				<xsl:if test="java:isParameterMandatory($javaPkgInfo, $dataContainer/@Id, $id)">
					<img alt="Yes" src="images/tick.gif"/>				
				</xsl:if>
				&nbsp;
			</td>

			<xsl:call-template name="outputDataType">
				<xsl:with-param name="dataId" select="$id"/>
				<xsl:with-param name="dataContainer" select="$dataContainer"/>
			</xsl:call-template>
			
			<td>
				<xsl:choose>
					<xsl:when test="java:isImplementedInterfaceParameter($javaPkgInfo, $dataContainer/@Id, $id)">
						<xsl:value-of select="java:get($msgL10n, 'InheritedFrom_label')"/>:&nbsp;<a href="{java:getImplementedInterfaceHrefId($javaPkgInfo, $dataContainer/@Id)}"><xsl:value-of select="java:getImplementedInterfaceName($javaPkgInfo, $dataContainer/@Id)"/></a>
						
						<xsl:variable name="descText" select="java:getDataDescription($javaPkgInfo, $dataContainer/@Id, $id)"/>
						<xsl:variable name="docURL" select="java:getDataDocURL($javaPkgInfo, $dataContainer/@Id, $id)"/>
						
						<xsl:if test="$descText != '' or $docURL != ''">
							<br/><br/>
						</xsl:if>

						<xsl:call-template name="OutputDescription">
							<xsl:with-param name="descText" select="java:getDataDescription($javaPkgInfo, $dataContainer/@Id, $id)"/>
							<xsl:with-param name="docURL" select="java:getDataDocURL($javaPkgInfo, $dataContainer/@Id, $id)"/>
						</xsl:call-template>
						&nbsp;

					</xsl:when>
					<xsl:otherwise>
						<xsl:call-template name="OutputDescription">
							<xsl:with-param name="descText" select="java:getDataDescription($javaPkgInfo, $dataContainer/@Id, $id)"/>
							<xsl:with-param name="docURL" select="java:getDataDocURL($javaPkgInfo, $dataContainer/@Id, $id)"/>
						</xsl:call-template>
						&nbsp;
					</xsl:otherwise>
				</xsl:choose>

			</td>
		</tr>

		<!-- Recurs with the remainder of the ids -->
		<xsl:call-template name="OutputFormalParamFromIds">
			<xsl:with-param name="paramIds" select="substring-after($paramIds, '&#10;')"/>
			<xsl:with-param name="dataContainer" select="$dataContainer"/>
		</xsl:call-template>
	</xsl:if>
	
</xsl:template>


<!--
===================================================================================================================
	outputProcessImage - output the process image and the reference map for it.
===================================================================================================================
-->
<xsl:template name="outputProcessImage">
	<xsl:param name="process"/>
	
	<xsl:if test="$showProcessImages = 'true'">

		<xsl:variable name="tempXMLName" select="$process/@Name"/>
		
		<xsl:variable name="unescapedXMLName">
			<xsl:choose>
				<xsl:when test="function-available('java:com.tibco.xpd.procdoc.TransformUtils.removeFilePathChars')"><xsl:value-of select="java:com.tibco.xpd.procdoc.TransformUtils.removeFilePathChars($tempXMLName)"/></xsl:when>
			</xsl:choose>
		</xsl:variable> 
	
		<xsl:variable name="imageFileName">
			<xsl:text>images/</xsl:text>
			<xsl:value-of select="$packageID"/>
			<xsl:text>_</xsl:text>
			<xsl:value-of select="$unescapedXMLName"/>
			<xsl:text>_</xsl:text>
			<xsl:value-of select="$process/@Id"/>
			<xsl:value-of select="$imageFileExtension"/>
		</xsl:variable>

		<xsl:choose>
			<xsl:when test="$useSVGImages = 'true'">
				<!--
					HANDLE SVG Images.
				-->
				<div class="image" id="img_{$process/@Id}">
					<xsl:variable name="diagramWidth">
						<xsl:call-template name="getDiagramWidth">
							<xsl:with-param name="process" select="$process"/>
						</xsl:call-template>
					</xsl:variable>
					<xsl:variable name="diagramHeight">
						<xsl:call-template name="getDiagramHeight">
							<xsl:with-param name="process" select="$process"/>
						</xsl:call-template>
					</xsl:variable>

					<embed src="{$imageFileName}" type="image/svg+xml" width="{$diagramWidth + 50}" height="{$diagramHeight + 50}" />
					
				</div>
			</xsl:when>
			<xsl:otherwise>
				<!--
					HANDLE Non-SVG Images.
				-->
				<xsl:variable name="allMapable" select="$process/xpdl2:Activities/xpdl2:Activity | 
																			$process/xpdl2:ActivitySets/xpdl2:ActivitySet/xpdl2:Activities/xpdl2:Activity | 
																			/xpdl2:Package/xpdl2:Pools/xpdl2:Pool | 
																			/xpdl2:Package/xpdl2:Pools/xpdl2:Pool/xpdl2:Lanes/xpdl2:Lane | 
																			/xpdl2:Package/xpdl2:Artifacts/xpdl2:Artifact[@ArtifactType = 'DataObject']"/>
			
				<xsl:variable name="mapId"><xsl:text>map_</xsl:text><xsl:value-of select="$process/@Id"/></xsl:variable>
				
				<!-- The image... -->
				<div class="image" id="img_{$process/@Id}">
					<xsl:element name="img">
						<xsl:attribute name="src"><xsl:value-of select="$imageFileName"/></xsl:attribute>
		
						<xsl:attribute name="class">process</xsl:attribute>
						
						<xsl:attribute name="usemap">#<xsl:value-of select="$mapId"/></xsl:attribute>
		
						<xsl:attribute name="alt"><xsl:value-of select="java:get($msgL10n, 'Process_label')"/><xsl:text>: </xsl:text><xsl:call-template name="getName"><xsl:with-param name="node" select="$process"/></xsl:call-template></xsl:attribute>
					</xsl:element>
			
					<!-- Create the map. -->
					<xsl:if test="$useSVGImages != 'true'">
						<xsl:variable name="mapFileName">
							<xsl:value-of select="$imagesFolder"/>
							<xsl:text>/map_</xsl:text>
							<xsl:value-of select="$packageID"/>
							<xsl:text>_</xsl:text>
							<xsl:value-of select="$unescapedXMLName"/>
							<xsl:text>_</xsl:text>
							<xsl:value-of select="$process/@Id"/>
							<xsl:text>.xml</xsl:text>
						</xsl:variable>
						
						<xsl:variable name="mapFile" select="document($mapFileName)"/>
						<xsl:if test="$mapFile/map/obj">
							<map id="{$mapId}" name="{$mapId}">
								<!-- Do thing in correct Z-order (i.e. make sure that lanes are below(defined after) activities, and tasks are below attached events -->
								
								<!-- all attached events. -->
								<xsl:for-each select="$mapFile/map/obj">
									
									<xsl:variable name="objId" select="@id"/>
									<xsl:variable name="mappedObj" select="$allMapable[@Id = $objId]"/>				
									
									<xsl:if test="local-name($mappedObj) = 'Activity' and $mappedObj/xpdl2:Event/xpdl2:IntermediateEvent/@Target != ''">
										<area shape="rect" href="#{$objId}">
											<xsl:attribute name="coords">
												<xsl:value-of select="@x"/>,<xsl:value-of select="@y"/>,<xsl:value-of select="@x + @width"/>,<xsl:value-of select="@y + @height"/>
											</xsl:attribute>
											<xsl:attribute name="alt"><xsl:value-of select="java:get($msgL10n, 'ImageMapGoto_label')"/> <xsl:call-template name="getActivityName"><xsl:with-param name="act" select="$mappedObj"/><xsl:with-param name="noItalics" select="true()"/></xsl:call-template></xsl:attribute>
										</area>
									</xsl:if>
									
								</xsl:for-each>
				
								<!-- Then all activities except attached events. -->
								<xsl:for-each select="$mapFile/map/obj">
								
									<xsl:variable name="objId" select="@id"/>
									<xsl:variable name="mappedObj" select="$allMapable[@Id = $objId]"/>				
									
									<xsl:if test="$mappedObj/@ArtifactType = 'DataObject' or (local-name($mappedObj) = 'Activity' and count($mappedObj/xpdl2:Event/xpdl2:IntermediateEvent/@Target) = 0)">
										<xsl:variable name="listIt"><xsl:call-template name="isListedActivity"><xsl:with-param name="act" select="$mappedObj"/></xsl:call-template></xsl:variable>
										
										<xsl:choose>
											<xsl:when test="$listIt = 'true'">
												<area shape="rect" href="#{$objId}">
													<xsl:attribute name="coords">
														<xsl:value-of select="@x"/>,<xsl:value-of select="@y"/>,<xsl:value-of select="@x + @width"/>,<xsl:value-of select="@y + @height"/>
													</xsl:attribute>
													<xsl:attribute name="alt"><xsl:value-of select="java:get($msgL10n, 'ImageMapGoto_label')"/> <xsl:call-template name="getActivityName"><xsl:with-param name="act" select="$mappedObj"/><xsl:with-param name="noItalics" select="true()"/></xsl:call-template></xsl:attribute>
												</area>
											</xsl:when>
											<xsl:otherwise>
												<!-- Things that aren't listed (gateways and intermediate links are handled in seq flow sections so jump there) -->
												<area shape="rect" href="#seq_{$objId}">
													<xsl:attribute name="coords">
														<xsl:value-of select="@x"/>,<xsl:value-of select="@y"/>,<xsl:value-of select="@x + @width"/>,<xsl:value-of select="@y + @height"/>
													</xsl:attribute>
													<xsl:attribute name="alt"><xsl:value-of select="java:get($msgL10n, 'ImageMapGotoSeqFlow_label')"/></xsl:attribute>
												</area>
											</xsl:otherwise>
										</xsl:choose>
									</xsl:if>
									
								</xsl:for-each>
				
								<!-- finally the background lanes. -->
								<xsl:for-each select="$mapFile/map/obj">
									<xsl:variable name="objId" select="@id"/>
									<xsl:variable name="mappedObj" select="$allMapable[@Id = $objId]"/>				
									
									<xsl:if test="local-name($mappedObj) = 'Pool' or local-name($mappedObj) = 'Lane'">
										<area shape="rect" href="#{$objId}">
											<xsl:attribute name="coords">
												<xsl:value-of select="@x"/>,<xsl:value-of select="@y"/>,<xsl:value-of select="@x + @width"/>,<xsl:value-of select="@y + @height"/>
											</xsl:attribute>
											<xsl:attribute name="alt"><xsl:value-of select="java:get($msgL10n, 'ImageMapGoto_label')"/> <xsl:value-of select="$mappedObj/@Name"/></xsl:attribute>
										</area>
									</xsl:if>
								</xsl:for-each>
								 
							</map>
						</xsl:if>
					</xsl:if>
					
				</div>
				
			</xsl:otherwise>
		</xsl:choose>
	
	</xsl:if>
	
</xsl:template>
	
<!--
===================================================================================================================
	xpdl2:Activities (summary) = Activites summary
===================================================================================================================
-->
<xsl:template match="xpdl2:Activities" mode="summary">
<xsl:param name="processDataObjs"/>

	<xsl:variable name="activities" select="xpdl2:Activity"/>

	<div class="section">
		<table>
			<thead>
				<tr>
					<th colspan="4" class="sectionTitle">
						<a id="acts_{parent::node()/@Id}">
							<xsl:value-of select="java:get($msgL10n, 'PoolLaneContent_label')"/>
						</a>
					</th>
				</tr>					
				<tr>
					<!-- <th class="headingId">ID</th> --> <th><xsl:value-of select="java:get($msgL10n, 'PoolLaneTitle_label')"/></th><th><xsl:value-of select="java:get($msgL10n, 'Name_label')"/></th><th><xsl:value-of select="java:get($msgL10n, 'Type_label')"/></th>
				</tr>
			</thead>
			<tbody>
				<!-- list acgtivities grouped by pool and lane. -->
				<xsl:variable name="procId" select="../@Id"/>
				
				<xsl:for-each select="/xpdl2:Package/xpdl2:Pools/xpdl2:Pool[@Process = $procId]">
					<!-- Add pool row. -->
					<xsl:variable name="poolId" select="@Id"/>
					<xsl:variable name="poolName"><xsl:call-template name="getName"/></xsl:variable>
					<xsl:variable name="poolPos" select="position()"/>
					<xsl:variable name="numLanes" select="count(xpdl2:Lanes/xpdl2:Lane)"/>
					
					<xsl:choose>
						<xsl:when test="$numLanes = 0">
							<tr>
								<td class="poolLaneTitle">
									<a id="{$poolId}"><xsl:value-of select="$poolName"/></a><br/>
								</td>
								<td><i><xsl:value-of select="java:get($msgL10n, 'PoolLaneEmpty_label')"/></i>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
						</xsl:when>
						<xsl:otherwise>
							<!-- add lane rows. -->
							<xsl:for-each select="xpdl2:Lanes/xpdl2:Lane">
								<xsl:variable name="laneId" select="@Id"/>
								<xsl:variable name="laneName"><xsl:call-template name="getName"/></xsl:variable>
								<xsl:variable name="lanePos" select="position()"/>
								
								<!-- Add rows for wanted activities in this lane -->
								<xsl:variable name="laneActs" select="$activities[xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo[(@ToolId = 'XPD' or @ToolId = '' or not(@ToolId)) and @LaneId = $laneId]]"/>
								<xsl:variable name="laneDataObjs" select="$processDataObjs[xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo[(@ToolId = 'XPD' or @ToolId = '' or not(@ToolId)) and @LaneId = $laneId]]"/>
								<xsl:variable name="listedActs" select="$laneActs[count(xpdl2:Route) = 0 and count(xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerResultLink) = 0 and count(xpdl2:Event/xpdl2:IntermediateEvent/@Target) = 0]"/>
								<xsl:variable name="numActs" select="count($listedActs) + count($laneDataObjs)"/>
								
								<xsl:choose>
									<xsl:when test="$numActs = 0">
										<!-- no activities that count in lane, output just the pool / lane -->
										<tr>
											<td class="poolLaneTitle">
												<xsl:if test="$lanePos = 1">
													<!-- Include pool name in header on first lane. -->
													<a id="{$poolId}"><xsl:value-of select="$poolName"/></a><br/>
												</xsl:if>
												<xsl:call-template name="indent"><xsl:with-param name="level" select="1"/></xsl:call-template>
												<a id="{$laneId}"><xsl:value-of select="$laneName"/></a>
												&nbsp;
											</td>
											<td><i><xsl:value-of select="java:get($msgL10n, 'PoolLaneEmpty_label')"/></i></td>
											<td>&nbsp;<br/></td>
										</tr>
									</xsl:when>
		
									<xsl:otherwise>
										<!-- have activities that count, list them row by row -->
										<xsl:for-each select="$listedActs | $laneDataObjs">
											<xsl:sort select="@xpdExt:DisplayName"/>
											
											<xsl:variable name="actPos" select="position()"/>
											
											<tr>
												<td>
													<!-- if this is not last row then don't output bottom border -->
													<xsl:choose>
														<xsl:when test="position() &lt; $numActs or $lanePos &lt; $numLanes">
															<xsl:attribute name="class">noBorder poolLaneTitle</xsl:attribute>
														</xsl:when>
														<xsl:otherwise>
															<xsl:attribute name="class">poolLaneTitle</xsl:attribute>
														</xsl:otherwise>
													</xsl:choose>
													
													<!-- If this first is activity in pool/lane, output pool/lane in left column. -->
													<xsl:choose>
														<xsl:when test="$lanePos = 1 and $actPos = 1">
															<!-- Include pool name in header on first lane. -->
															<a id="{$poolId}"><xsl:value-of select="$poolName"/></a><br/>
															<xsl:call-template name="indent"><xsl:with-param name="level" select="1"/></xsl:call-template>
															<a id="{$laneId}"><xsl:value-of select="$laneName"/></a>
														</xsl:when>
														<xsl:when test="$actPos = 1">
															<!-- include lane header for first activity in subsequence lanes. -->
															<xsl:call-template name="indent"><xsl:with-param name="level" select="1"/></xsl:call-template>
															<a id="{$laneId}"><xsl:value-of select="$laneName"/></a>
														</xsl:when>
														<xsl:otherwise>
															&nbsp;
														</xsl:otherwise>
													</xsl:choose>
												</td>
												
												<!-- Add activity name -->
												<td>
													<!-- if this is not last row then don't output bottom border -->
													<xsl:if test="position() &lt; $numActs">
														<xsl:attribute name="class">noBorder</xsl:attribute>
													</xsl:if>
		
													<!-- If this first is activity, then add extra linefeed to align. -->
													<xsl:if test="$actPos = 1">
														<br/>
													</xsl:if>
													<xsl:choose>
														<xsl:when test="local-name(.) = 'Artifact'">
															<a href="#{@Id}">
																<xsl:call-template name="getDataObjectName">
																	<xsl:with-param name="dataObj" select="." />
																</xsl:call-template>
															</a>
														</xsl:when>
														<xsl:otherwise>
															<xsl:call-template name="getActivityHyperLink"><xsl:with-param name="act" select="."/></xsl:call-template>
														</xsl:otherwise>
													</xsl:choose>
													&nbsp;
												</td>
		
												<!-- Add activity type -->
												<td>
													<!-- if this is not last row then don't output bottom border -->
													<xsl:if test="position() &lt; $numActs">
														<xsl:attribute name="class">noBorder</xsl:attribute>
													</xsl:if>
		
													<!-- If this first is activity, then add extra linefeed to align. -->
													<xsl:if test="$actPos = 1">
														<br/>
													</xsl:if>
													<xsl:choose>
														<xsl:when test="local-name(.) = 'Artifact'"><xsl:value-of select="java:get($msgL10n, 'DataObject_label')"/></xsl:when>
														<xsl:otherwise><xsl:call-template name="getActivityTypeName"><xsl:with-param name="act" select="."/></xsl:call-template></xsl:otherwise>
													</xsl:choose>
													&nbsp;
												</td>
											</tr>
										
										</xsl:for-each>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:for-each>
						</xsl:otherwise>
					</xsl:choose>

				</xsl:for-each>
			</tbody>
		</table>
	</div>
</xsl:template>

	
<!--
===================================================================================================================
	xpdl2:Activity = Activity details
===================================================================================================================
-->
<xsl:template match="xpdl2:Activity">
	<xsl:param name="participants"/>
	<xsl:param name="associations"/>
	<xsl:param name="annotations"/>
	<xsl:param name="dataObjs"/>
	<xsl:param name="processes" />
	<xsl:param name="divClass" select="'section'"/>
	<xsl:param name="activitySets"/>
	<xsl:param name="process"/>
	
	<!-- 
		Ignore gateways and intermediate link events and attached events - these are handled as part of sequence flow descriptive output. 
	-->
	<xsl:variable name="listIt"><xsl:call-template name="isListedActivity"><xsl:with-param name="act" select="."/></xsl:call-template></xsl:variable>
	<xsl:if test="$listIt = 'true'">
		<div class="{$divClass}">
			<xsl:call-template name="outputActivity">
				<xsl:with-param name="participants" select="$participants"/>
				<xsl:with-param name="associations" select="$associations"/>
				<xsl:with-param name="annotations" select="$annotations"/>
				<xsl:with-param name="dataObjs" select="$dataObjs"/>
				<xsl:with-param name="processes" select="$processes"/>
				<xsl:with-param name="activitySets" select="$activitySets"/>
				<xsl:with-param name="process" select="$process"/>
			</xsl:call-template>
		</div>
	</xsl:if>
</xsl:template>

<!--
===================================================================================================================
	outputActivity = Output activity details - curent context must be activity
===================================================================================================================
-->	
<xsl:template name="outputActivity">
	<xsl:param name="participants"/>
	<xsl:param name="associations"/>
	<xsl:param name="annotations"/>
	<xsl:param name="dataObjs"/>
	<xsl:param name="processes" />
	<xsl:param name="activitySets"/>
	<xsl:param name="process"/>

	<xsl:variable name="activityId" select="@Id" />
	<xsl:variable name="allActs" select="$process/xpdl2:Activities/xpdl2:Activity | $activitySets/xpdl2:Activities/xpdl2:Activity"/>
	
	<!-- Print appropriate table based on element type -->
	<xsl:variable name="activityType"><xsl:call-template name="getActivityTypeName"><xsl:with-param name="act" select="."/></xsl:call-template></xsl:variable>
	<table>
		<thead>
			<tr>
				<th class="sectionTitle">
					<a id="{@Id}">
						<xsl:variable name="actName"><xsl:call-template name="getName"/></xsl:variable>
						<xsl:choose>
							<xsl:when test="$actName != ''">
								<xsl:value-of select="$actName"/>
							</xsl:when>
							<xsl:otherwise>
								<xsl:call-template name="getActivityName">
									<xsl:with-param name="act" select="." />
								</xsl:call-template>
							</xsl:otherwise>
						</xsl:choose>
					</a>
				</th>
				
				<th class="sectionTitle screenOnly" style="text-align: right; font-weight: normal;">
					<xsl:if test="$showProcessImages = 'true'">
						<a href="#img_{$process/@Id}"><xsl:value-of select="java:get($msgL10n, 'GotoDiagram_label')"/></a>
					</xsl:if>
					&nbsp;
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th class="heading">
					<xsl:value-of select="java:get($msgL10n, 'Type_label')"/>
				</th>
				<td style="white-space: nowrap;">
					<xsl:value-of select="$activityType"/>

					<xsl:if test="@xpdExt:Implements != ''">
						<!-- If activity implements a process interface event then provide a hyperlink to it. -->
						&nbsp;&nbsp;(<xsl:value-of select="java:get($msgL10n, 'ImplementsEvent_label')"/>:&nbsp;
						<a href="{java:getImplementedMethodHrefId($javaPkgInfo, $process/@Id, $activityId)}">
							<xsl:value-of select="java:getImplementedInterfaceName($javaPkgInfo, $process/@Id)"/>
							&nbsp;-&nbsp;
							<xsl:value-of select="java:getImplementedMethodName($javaPkgInfo, $process/@Id, $activityId)"/>
						</a>
						<xsl:text>)</xsl:text>
					</xsl:if>

					<xsl:if test="@IsATransaction">
						&nbsp;(<xsl:value-of select="java:get($msgL10n, 'Transaction_label')"/>)
					</xsl:if>
					&nbsp;
				</td>
			</tr>
			<!-- Description / associated Annotations -->
			<!-- Get all associations to this activity -->
			<xsl:variable name="activityID" select="@Id" />
			<xsl:variable name="act" select="."/>
			<xsl:variable name="assocToActivity" select="$associations[@Target = $activityID or @Source = $activityID]" />
			<!-- Output every note association -->
			<xsl:variable name="annotText">
				<xsl:for-each select="$assocToActivity">
					<xsl:variable name="src" select="@Source"/>
					<xsl:variable name="tgt" select="@Target"/>
					
					<xsl:variable name="annot" select="$annotations[@Id = $src or @Id = $tgt]"/>
					<xsl:if test="$annot/@TextAnnotation != ''">
						<xsl:choose>
							<xsl:when test="$act/xpdl2:Description">
								<xsl:call-template name="newline2br"><xsl:with-param name="input" select="$annot/@TextAnnotation"/><xsl:with-param name="indent" select="1"/></xsl:call-template><br/>
							</xsl:when>
							<xsl:otherwise>
								<xsl:call-template name="newline2br"><xsl:with-param name="input" select="$annot/@TextAnnotation"/><xsl:with-param name="indent" select="0"/></xsl:call-template><br/>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:if>
				</xsl:for-each>
				
				<!-- For embedded sub-proc description, also pick up unassociated annotations -->
				<xsl:if test="xpdl2:BlockActivity">
					<xsl:variable name="actSetId" select="xpdl2:BlockActivity/@ActivitySetId"/>
					<!-- Get annotations inside this  this embedded subproc -->
					<xsl:variable name="annots" select="/xpdl2:Package/xpdl2:Artifacts/xpdl2:Artifact[@ArtifactType = 'Annotation' and xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo/@LaneId = $actSetId]"/>
					<!-- the for each of these that does not have associations -->
					<xsl:for-each select="$annots[not (@Id = $associations/@Source or @Id = $associations/@Target)]">
						<xsl:if test="@TextAnnotation != ''">
							<xsl:choose>
								<xsl:when test="$act/xpdl2:Description != ''">
									<xsl:call-template name="newline2br"><xsl:with-param name="input" select="@TextAnnotation"/><xsl:with-param name="indent" select="1"/></xsl:call-template><br/>
								</xsl:when>
								<xsl:otherwise>
									<xsl:call-template name="newline2br"><xsl:with-param name="input" select="@TextAnnotation"/><xsl:with-param name="indent" select="0"/></xsl:call-template><br/>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:if>
					</xsl:for-each>
				</xsl:if>
			</xsl:variable>

			<xsl:if test="xpdl2:Description or $annotText != '' ">
				<tr>
					<th><xsl:value-of select="java:get($msgL10n, 'Description_label')"/></th>
					<td>
						<xsl:if test="xpdl2:Description">
							<xsl:call-template name="OutputDescription">
								<xsl:with-param name="descText" select="xpdl2:Description"/>
								<xsl:with-param name="docURL" select="xpdl2:Description/@xpdExt:DocumentationURL"/>
							</xsl:call-template>
							&nbsp;
						</xsl:if>
						<xsl:if test="$annotText != '' and xpdl2:Description != ''">
							<br/><a style="font-weight: bolder;"><xsl:value-of select="java:get($msgL10n, 'Annotations_label')"/></a><br/>
						</xsl:if>
						<xsl:if test="$annotText != ''">
							<xsl:copy-of select="$annotText"/>
						</xsl:if>
					</td>
				</tr>
			</xsl:if>
		
			<xsl:choose>
				<!-- Event types -->
				<xsl:when test="xpdl2:Event">
					<xsl:call-template name="outputEventType">
						<xsl:with-param name="event" select="xpdl2:Event" />
						<xsl:with-param name="allActs" select="$allActs"/>
						<xsl:with-param name="activityId" select="$activityID" />
						<xsl:with-param name="processId" select="$process/@Id" /> 
					</xsl:call-template>
				</xsl:when> 
				
				<!-- Task types -->
				<xsl:when test="xpdl2:Implementation or xpdl2:BlockActivity">
					<xsl:call-template name="outputTaskType">
						<xsl:with-param name="activity" select="." />
						<xsl:with-param name="activitySets" select="$activitySets" />
						<xsl:with-param name="participants" select="$participants" />
						<xsl:with-param name="processes" select="$processes" />
						<xsl:with-param name="dataObjs" select="$dataObjs"/>
						<xsl:with-param name="associations" select="$associations"/>
						<xsl:with-param name="annotations" select="$annotations"/>
						<xsl:with-param name="allActs" select="$allActs"/>
						<xsl:with-param name="process" select="$process"/>
					</xsl:call-template>
				</xsl:when>
			</xsl:choose>

			<xsl:if test="not(xpdl2:BlockActivity) and not(xpdl2:Implementation/xpdl2:Reference)">
				<xsl:call-template name="OutputActivityInterface">
					<xsl:with-param name="dataContainer" select="$process"/>
					<xsl:with-param name="actOrMethod" select="."/>
				</xsl:call-template>
			</xsl:if>


			<!-- Add associated data objects -->
			<xsl:call-template name="outputDataObjAssociations">
				<xsl:with-param name="associations" select="$associations[@Source = $activityId or @Target = $activityId]" />
				<xsl:with-param name="dataObjs" select="$dataObjs" />
			</xsl:call-template>
			
			<!-- Add transitions -->
			<xsl:if test="$showFlows = 'true'">
				<xsl:call-template name="outputActivityFlow">
					<xsl:with-param name="act" select="."/>
					<xsl:with-param name="process" select="$process"/>
				</xsl:call-template>
			</xsl:if>
			
			<!-- Add caught events -->
			<xsl:call-template name="outputCaughtEvents">
				<xsl:with-param name="act" select="."/>
				<xsl:with-param name="participants" select="$participants"/>
				<xsl:with-param name="associations" select="$associations"/>
				<xsl:with-param name="annotations" select="$annotations"/>
				<xsl:with-param name="dataObjs" select="$dataObjs"/>
				<xsl:with-param name="processes" select="$processes"/>
				<xsl:with-param name="activitySets" select="$activitySets"/>
				<xsl:with-param name="process" select="$process"/>
			</xsl:call-template>

			<!--
				If embedded sub-proc then output contents
			-->
			<xsl:if test="xpdl2:BlockActivity">
				<tr>							
				<xsl:choose>
					<xsl:when test="xpdl2:BlockActivity/@xpdExt:IsEventSubProcess = 'true'"><th class="heading"><xsl:value-of select="java:get($msgL10n, 'EventSubProcContents_label')"/></th></xsl:when>				
					<xsl:otherwise><th class="heading"><xsl:value-of select="java:get($msgL10n, 'EmbeddedContents_label')"/></th></xsl:otherwise>
				</xsl:choose>				
					<td>
						<xsl:variable name="actSetId" select="xpdl2:BlockActivity/@ActivitySetId"/>
						<xsl:variable name="actSet" select="$activitySets[@Id = $actSetId]"/> 
						
						<xsl:variable name="embDataObjs" select="$dataObjs[xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo/@LaneId = $actSet/@Id]"/>
						
						<!-- Quick list of embedded contents -->
						<table class="screenOnly">
							<tbody>
								<tr>
									<th class="heading"><xsl:value-of select="java:get($msgL10n, 'QuickLinks_label')"/></th>
									<td>
										<xsl:for-each select="$actSet/xpdl2:Activities/xpdl2:Activity | $embDataObjs">
											<xsl:sort select="@xpdExt:DisplayName" data-type="text"/>

											<xsl:choose>
												<xsl:when test="local-name(.) = 'Artifact'">
													<a href="#{@Id}">
														<xsl:call-template name="getDataObjectName">
															<xsl:with-param name="dataObj" select="." />
														</xsl:call-template>
													</a>
													<xsl:text>,</xsl:text>&nbsp;
												</xsl:when>
												<xsl:otherwise>
													<!-- 
														Ignore gateways and intermediate link events and attached events - these are handled as part of sequence flow descriptive output. 
													-->
													<xsl:variable name="listIt"><xsl:call-template name="isListedActivity"><xsl:with-param name="act" select="."/></xsl:call-template></xsl:variable>
													<xsl:if test="$listIt = 'true'">
						
														<xsl:call-template name="getActivityHyperLink">
															<xsl:with-param name="act" select="."/>
														</xsl:call-template>
														<xsl:text>,</xsl:text>&nbsp;
													</xsl:if>
												</xsl:otherwise>
											</xsl:choose>

										</xsl:for-each>
										&nbsp;
									</td>
								</tr>
							</tbody>
						</table>
						
						<div>
							<xsl:for-each select="$actSet/xpdl2:Activities/xpdl2:Activity">
								<xsl:sort select="@xpdExt:DisplayName" data-type="text"/>
								
								<xsl:apply-templates select=".">
									<xsl:with-param name="participants" select="$participants"/>
									<xsl:with-param name="associations" select="$associations" />
									<xsl:with-param name="annotations" select="$annotations"/>
									<xsl:with-param name="dataObjs" select="$dataObjs"/>
									<xsl:with-param name="processes" select="$processes" />
									<xsl:with-param name="activitySets" select="$activitySets"/>
									<xsl:with-param name="process" select="$process"/>
								</xsl:apply-templates>
								
							</xsl:for-each>

							<!-- Data objects -->
							<xsl:if test="$dataObjs">
								<xsl:call-template name="outputDataObjects">
									<xsl:with-param name="dataObjs" select="$embDataObjs" />
									<xsl:with-param name="associations" select="$associations" />
									<xsl:with-param name="process" select="$actSet" />
								</xsl:call-template>
							</xsl:if>
						
						</div>
						&nbsp;
					</td>
				</tr>
			</xsl:if>

			<!-- Add extended attributes (except ones we know about and handle during normal course of doc)  -->
			<xsl:if test="$showExtendedAttributes = 'true'">
				<xsl:if test="xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name != 'bpmJspTask' and not(simulation:*)]">
					<tr>
						<th class="heading"><xsl:value-of select="java:get($msgL10n, 'ExtendedAttributes_label')"/></th>
						<td>
							<table>
								<thead>
									<tr>
										<th><xsl:value-of select="java:get($msgL10n, 'Name_label')"/></th><th><xsl:value-of select="java:get($msgL10n, 'Value_label')"/></th>
									</tr>
								</thead>
								<tbody>
									<xsl:variable name="numAttr" select="count(xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name != 'bpmJspTask'])"/>
									<xsl:for-each select="xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name != 'bpmJspTask']">
										<tr>
											<xsl:variable name="class">
												<xsl:choose>
													<xsl:when test="position() = $numAttr">noBorder</xsl:when>
													<xsl:otherwise></xsl:otherwise>
												</xsl:choose>
											</xsl:variable>
											
											<td class="{$class}"><xsl:value-of select="@Name"/>&nbsp;</td>
											<td class="{$class}"><xsl:call-template name="extAttrAsHtml"><xsl:with-param name="el" select="."/></xsl:call-template>&nbsp;</td>
										</tr>
									</xsl:for-each>
								</tbody>
							</table>
							&nbsp;
						</td>
					</tr>
				</xsl:if>
			</xsl:if>
		</tbody>
	</table>

</xsl:template>


<!--
===================================================================================================================
	outputCaughtEvents = Output the events caught by a task
===================================================================================================================
-->	
<xsl:template name="outputCaughtEvents">
	<xsl:param name="act"/>
	<xsl:param name="participants"/>
	<xsl:param name="associations"/>
	<xsl:param name="annotations"/>
	<xsl:param name="dataObjs"/>
	<xsl:param name="processes" />
	<xsl:param name="activitySets"/>
	<xsl:param name="process"/>

	<xsl:variable name="activities" select="$act/../xpdl2:Activity"/>

	<xsl:variable name="attachedEvents" select="$activities[xpdl2:Event/xpdl2:IntermediateEvent/@Target = $act/@Id]"/>
	
	<xsl:if test="count($attachedEvents) > 0">
		<tr>
			<th class="heading"><xsl:value-of select="java:get($msgL10n, 'CaughtEventsTitle_label')"/></th>
			<td>
				<xsl:for-each select="$attachedEvents">
					<xsl:sort select="@xpdExt:DisplayName" data-type="text"/>

					<xsl:variable name="class">
						<xsl:if test="position() > 1">section</xsl:if>
					</xsl:variable>

					<div class="{$class}">
						<xsl:call-template name="outputActivity">
							<xsl:with-param name="participants" select="$participants"/>
							<xsl:with-param name="associations" select="$associations"/>
							<xsl:with-param name="annotations" select="$annotations"/>
							<xsl:with-param name="dataObjs" select="$dataObjs"/>
							<xsl:with-param name="processes" select="$processes"/>
							<xsl:with-param name="activitySets" select="$activitySets"/>
							<xsl:with-param name="process" select="$process"/>
						</xsl:call-template>
					</div>
				</xsl:for-each>
				&nbsp;
			</td>
		</tr>
	</xsl:if>
	
</xsl:template>

<!--
===================================================================================================================
	outputActivityFlow = Output the outgoing flow from an activity.
===================================================================================================================
-->	
<xsl:template name="outputActivityFlow">
	<xsl:param name="act"/>
	<xsl:param name="process"/>
	
	<xsl:variable name="activities" select="$act/../xpdl2:Activity"/>
	<xsl:variable name="actTrans" select="$act/../../xpdl2:Transitions/xpdl2:Transition[@From = $act/@Id]"/>
	<xsl:variable name="transitions" select="$act/../../xpdl2:Transitions/xpdl2:Transition"/>
	<xsl:variable name="msgFlows" select="/xpdl2:Package/xpdl2:MessageFlows/xpdl2:MessageFlow[@Source = $act/@Id]"/>

	<xsl:variable name="isNoncancellingSignal" select="$act/xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerResultSignal/@xpdExt:NonCancelling='true' and $act/xpdl2:Event/xpdl2:IntermediateEvent/@Target != ''"/>

	<xsl:if test="(count($act/xpdl2:Event/xpdl2:EndEvent) = 0 or count($msgFlows) > 0) or $isNoncancellingSignal = true()"> <!-- don't output flow for end events. -->

		<tr>
			<th class="heading"><xsl:value-of select="java:get($msgL10n, 'SeqFlowsTitle_label')"/></th>
			<td class="sequenceFlowScript">

				<xsl:if test="count($act/xpdl2:Event/xpdl2:EndEvent) = 0">
				
					<xsl:if test="count($actTrans) > 0 or $isNoncancellingSignal">
							<!-- Describe what initiates these flows according to source object -->
							<xsl:call-template name="outputActivityFlowLeader">
								<xsl:with-param name="act" select="$act"/>
							</xsl:call-template>

							<!-- Output "Update work item." if it's a non-cancelling event. -->
							<xsl:if test="$isNoncancellingSignal = true()">
								<xsl:call-template name="indent"><xsl:with-param name="level" select="'1'"/></xsl:call-template>
								<xsl:value-of select="java:get($msgL10n, 'UpdateWorkItem_label')"/>
								<br/>
							</xsl:if>
					</xsl:if>
				
					<xsl:choose>
						<xsl:when test="count($actTrans) > 0">
							
							<!-- Check whether the sequence flows are too complex to list in full or not (certain constructs can result in 2*N-powerN lines output 
									(and when N is a large number this can result in MASSIVE output and running out of heap space). -->
							<xsl:choose>
								<xsl:when test="java:areSeqFlowActionsTooComplex($javaPkgInfo, $process/@Id, $act/@Id)">
									<!-- outgoing flow too complex, just list the possble resultant activities... -->
									<xsl:variable name="resultActs" select="java:getSequenceFlowResultActivities($javaPkgInfo, $process/@Id, $act/@Id)"/>

									<xsl:call-template name="indent"><xsl:with-param name="level" select="'1'"/></xsl:call-template>
									<i><xsl:value-of select="java:get($msgL10n, 'SeqFlowsTooComplex_label')"/>
									&nbsp;
									
									<xsl:choose>
										<xsl:when test="$resultActs/acts/@hasConditionals = 'true'">
											<xsl:value-of select="java:get($msgL10n, 'SeqFlowsMayResultIn_label')"/>
										</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="java:get($msgL10n, 'SeqFlowsResultIn_label')"/>
										</xsl:otherwise>
									</xsl:choose>
									</i>
									<br/>

									<!-- 
										XPD-6169 Sid - return from java:getSequenceFlowResultActivities() above had to change to Document.documentElement()
										now we have moved to Java 1.7. 
										Otherwise you get exception: Run-time internal error in 'Don't know how to convert node type 9 
										I think that the GregorSamsa stuff in java1.7 no longer recognises Document as a valid w3c dom node type 
									-->									
									<xsl:for-each select="$resultActs/act">
										<xsl:variable name="resultActId" select="@id"/>
										<xsl:variable name="resultAct" select="$process/xpdl2:Activities/xpdl2:Activity[@Id = $resultActId] | $process/xpdl2:Activitysets/xpdl2:ActivitySet/xpdl2:Activities/xpdl2:Activity[@Id = $resultActId]"/>
										
										<xsl:if test="$resultAct">
											<xsl:call-template name="indent"><xsl:with-param name="level" select="'2'"/></xsl:call-template>
											<xsl:call-template name="getActivityHyperLink"><xsl:with-param name="act" select="$resultAct"/></xsl:call-template>
											<br/>
										</xsl:if>
									</xsl:for-each>

									
								</xsl:when>
								
								<xsl:otherwise>
									<!-- Not too complex to list in full. -->
								
									<!-- Go thru each transition outputting a (recursive-thru-gateways) description of outgoing flow) -->
									<xsl:call-template name="describeSequenceFlows">
										<xsl:with-param name="level" select="1"/>
										<xsl:with-param name="act" select="$act"/>
										<xsl:with-param name="actTrans" select="$actTrans"/>
										<xsl:with-param name="activities" select="$activities"/>
										<xsl:with-param name="transitions" select="$transitions"/>
										<xsl:with-param name="alreadyProcessed" select="''"/>
									</xsl:call-template>
								</xsl:otherwise>
							</xsl:choose>
							
						</xsl:when>
						
						<xsl:otherwise><i><xsl:value-of select="java:get($msgL10n, 'SeqFlowsNone_label')"/></i></xsl:otherwise>
					</xsl:choose>
				</xsl:if>

				<!-- message flow... -->
				<xsl:if test="$msgFlows">
					<xsl:if test="$actTrans">
						<br/>
					</xsl:if>
					<xsl:for-each select="$msgFlows">
						<xsl:variable name="targetId" select="@Target"/>
						<xsl:variable name="target" select="//*[@Id = $targetId]"/>

						<xsl:variable name="href">
							<xsl:choose>
								<xsl:when test="local-name($target) = 'Activity'"><xsl:call-template name="getActivityHyperLink"><xsl:with-param name="act" select="$target"/></xsl:call-template></xsl:when>
								<xsl:otherwise><a href="#{$target/@Id}"><xsl:value-of select="$target/@xpdExt:DisplayName"/></a></xsl:otherwise>
							</xsl:choose>
						</xsl:variable>
						
						<xsl:value-of select="java:format($msgL10n, 'SendMessage_label', @xpdExt:DisplayName, $href)"/>

					</xsl:for-each>
				</xsl:if>
				&nbsp;
			</td>
		</tr>
	</xsl:if>
		
</xsl:template>

<!--
===================================================================================================================
	outputActivityFlowLeader = Such as "On start when message arrives"
===================================================================================================================
-->	
<xsl:template name="outputActivityFlowLeader">
	<xsl:param name="act"/>

	<xsl:choose>
		<xsl:when test="$act/xpdl2:Event/xpdl2:StartEvent">
			<xsl:variable name="trigger" select="$act/xpdl2:Event/xpdl2:StartEvent/@Trigger"/>
			<xsl:choose>
				<xsl:when test="$trigger = 'Message'"><xsl:value-of select="java:get($msgL10n, 'StartEventMessage_label')"/></xsl:when>
				<xsl:when test="$trigger = 'Timer'"><xsl:value-of select="java:get($msgL10n, 'StartEventTimer_label')"/></xsl:when>
				<xsl:when test="$trigger = 'Conditional'"><xsl:value-of select="java:get($msgL10n, 'StartEventConditional_label')"/></xsl:when>
				<xsl:when test="$trigger = 'Signal'">
					<xsl:choose>
						<xsl:when test="$act/xpdl2:Event/xpdl2:StartEvent/xpdl2:TriggerResultSignal/@Name != ''">
							<xsl:value-of select="java:format($msgL10n, 'StartEventSignal_label', $act/xpdl2:Event/xpdl2:StartEvent/xpdl2:TriggerResultSignal/@Name)"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="java:get($msgL10n, 'StartEventAnySignal_label')"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:when>
				<xsl:when test="$trigger = 'Multiple'"><xsl:value-of select="java:get($msgL10n, 'StartEventMultiple_label')"/></xsl:when>
				<xsl:otherwise><xsl:value-of select="java:get($msgL10n, 'StartEventNone_label')"/></xsl:otherwise>
			</xsl:choose>
			<br/>
		</xsl:when>
		
		<xsl:when test="$act/xpdl2:Event/xpdl2:IntermediateEvent">
			<xsl:variable name="trigger" select="$act/xpdl2:Event/xpdl2:IntermediateEvent/@Trigger"/>

			<xsl:choose>
				<xsl:when test="$act/xpdl2:Event/xpdl2:IntermediateEvent/@Target != ''">
					<xsl:variable name="catchThrow"><xsl:call-template name="catchThrow"><xsl:with-param name="act" select="$act"/></xsl:call-template></xsl:variable>
					
					<!-- Attached event. -->
					<xsl:choose>
						<xsl:when test="$catchThrow = 'THROW'"><xsl:value-of select="java:get($msgL10n, 'InterEventAttachThrowNotLegal_label')"/></xsl:when>
						<xsl:when test="$trigger = 'Message'"><xsl:value-of select="java:get($msgL10n, 'InterEventMessageCaught_label')"/></xsl:when>
						<xsl:when test="$trigger = 'Timer'"><xsl:value-of select="java:get($msgL10n, 'InterEventTimerCaught_label')"/></xsl:when>
						<xsl:when test="$trigger = 'Conditional'"><xsl:value-of select="java:get($msgL10n, 'InterEventConditionalCaught_label')"/></xsl:when>
						<xsl:when test="$trigger = 'Link'"><i><xsl:value-of select="java:get($msgL10n, 'InterEventLinkCaught_label')"/></i></xsl:when>
						<xsl:when test="$trigger = 'Signal'"><i><xsl:value-of select="java:get($msgL10n, 'InterEventSignalCaught_label')"/></i></xsl:when>
						<xsl:when test="$trigger = 'Multiple'"><xsl:value-of select="java:get($msgL10n, 'InterEventMultipleCaught_label')"/></xsl:when>
						<xsl:when test="$trigger = 'Compensation'"><xsl:value-of select="java:get($msgL10n, 'InterEventCompensationCaught_label')"/></xsl:when>
						<xsl:when test="$trigger = 'Error'"><xsl:value-of select="java:get($msgL10n, 'InterEventErrorCaught_label')"/></xsl:when>
						<xsl:when test="$trigger = 'Cancel'"><xsl:value-of select="java:get($msgL10n, 'InterEventCancelCaught_label')"/></xsl:when>
						<xsl:otherwise><xsl:value-of select="java:get($msgL10n, 'InterEventNoneCaught_label')"/></xsl:otherwise>
					</xsl:choose>
					<!-- Continue or Cancel on event? -->
					<xsl:choose>
						<xsl:when test="$act/xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerTimer/@xpdExt:ContinueOnTimeout='true'
												or $act/xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerResultSignal/@xpdExt:NonCancelling='true'">
							<xsl:value-of select="java:get($msgL10n, 'InterEventNonCancelling_label_2')"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="java:get($msgL10n, 'InterEventCancelOnEvent_label_2')"/>
						</xsl:otherwise> 
					</xsl:choose>
				</xsl:when>
				<xsl:otherwise>
					<!-- In-Flow CATCH events -->
					<xsl:variable name="catchThrow"><xsl:call-template name="catchThrow"><xsl:with-param name="act" select="$act"/></xsl:call-template></xsl:variable>
					
					<xsl:choose>
						<xsl:when test="$catchThrow = 'CATCH'">
							<xsl:choose>
								<xsl:when test="$trigger = 'Message'"><xsl:value-of select="java:get($msgL10n, 'InterEventMessage_label')"/></xsl:when>
								<xsl:when test="$trigger = 'Timer'"><xsl:value-of select="java:get($msgL10n, 'InterEventTimer_label')"/></xsl:when>
								<xsl:when test="$trigger = 'Conditional'"><xsl:value-of select="java:get($msgL10n, 'InterEventConditional_label')"/></xsl:when>
								<xsl:when test="$trigger = 'Link'"><xsl:value-of select="java:get($msgL10n, 'InterEventLink_label')"/></xsl:when>
								<xsl:when test="$trigger = 'Multiple'"><xsl:value-of select="java:get($msgL10n, 'InterEventMultiple_label')"/></xsl:when>
								<xsl:when test="$trigger = 'Compensation'"><xsl:value-of select="java:get($msgL10n, 'InterEventCatchCompensation_label')"/></xsl:when>
								<xsl:when test="$trigger = 'Error'"><xsl:value-of select="java:get($msgL10n, 'InterEventCatchError_label')"/></xsl:when>
								<xsl:when test="$trigger = 'Signal'"><xsl:value-of select="java:get($msgL10n, 'InterEventCatchSignal_label')"/></xsl:when>
								<xsl:when test="$trigger = 'Cancel'"><i><xsl:value-of select="java:get($msgL10n, 'InterEventBadCancel_label')"/></i></xsl:when>
								<xsl:otherwise><xsl:value-of select="java:get($msgL10n, 'InterEventNone_label')"/></xsl:otherwise>
							</xsl:choose>
						</xsl:when>
						<xsl:otherwise>
							<xsl:choose>
								<xsl:when test="$trigger = 'Message'"><xsl:value-of select="java:get($msgL10n, 'InterEventMessageThrow_label_2')"/></xsl:when>
								<xsl:when test="$trigger = 'Multiple'"><xsl:value-of select="java:get($msgL10n, 'InterEventMultipleThrow_label_2')"/></xsl:when>
								<xsl:when test="$trigger = 'Compensation'"><xsl:value-of select="java:get($msgL10n, 'InterEventThrowCompensation_label_2')"/></xsl:when>
								<xsl:when test="$trigger = 'Error'"><xsl:value-of select="java:get($msgL10n, 'InterEventCatchError_label')"/></xsl:when>
								<xsl:when test="$trigger = 'Signal'"><xsl:value-of select="java:get($msgL10n, 'InterEventCatchSignal_label')"/></xsl:when>
								<xsl:when test="$trigger = 'Cancel'"><i><xsl:value-of select="java:get($msgL10n, 'InterEventBadCancel_label')"/></i></xsl:when>
								<xsl:otherwise><xsl:value-of select="java:get($msgL10n, 'InterEventNone_label')"/></xsl:otherwise>
							</xsl:choose>
						
						</xsl:otherwise>
					</xsl:choose>
				</xsl:otherwise>
			</xsl:choose>
			<br/>
		</xsl:when>
		
		<xsl:when test="$act/xpdl2:Implementation or $act/xpdl2:BlockActivity">
			<xsl:value-of select="java:get($msgL10n, 'TaskCompletes_label')"/><br/>
		</xsl:when>
		
	</xsl:choose>
	
</xsl:template>


<!--
===================================================================================================================
	describeSequenceFlows = Receursively (thru gateways) Output text description of seq flow from an activity
===================================================================================================================
-->	
<xsl:template name="describeSequenceFlows">
	<xsl:param name="level"/>
	<xsl:param name="actTrans"/>
	<xsl:param name="act"/>
	<xsl:param name="activities"/>
	<xsl:param name="transitions"/>
	<xsl:param name="alreadyProcessed"/>

	<!-- Avoid infinite looping when gateway / link event loops back on itself. -->
	<xsl:variable name="lookFor">;<xsl:value-of select="$act/@Id"/>;</xsl:variable>
	
	<xsl:if test="not(contains($alreadyProcessed, $lookFor))">

		<xsl:variable name="newAlreadyProcessed"><xsl:value-of select="$alreadyProcessed"/>;<xsl:value-of select="$act/@Id"/>;</xsl:variable>

		<xsl:variable name="gatewayType"><xsl:call-template name="getGatewayType"><xsl:with-param name="act" select="$act"/></xsl:call-template></xsl:variable>
		
		<xsl:choose>
			<xsl:when test="$gatewayType = 'ExclusiveEvent'">
				<!-- Event based gateways are slightly different from everything else. -->
				<a id="seq_{$act/@Id}"/>
				<xsl:call-template name="indent"><xsl:with-param name="level" select="$level"/></xsl:call-template>
				<xsl:value-of select="java:get($msgL10n, 'EventGatewayLeader_label')"/><xsl:text>...</xsl:text><br/>

				<xsl:for-each select="$actTrans">
					<xsl:call-template name="describeSeqFlowAction">
						<xsl:with-param name="level" select="$level + 1"/>
						<xsl:with-param name="trans" select="."/>
						<xsl:with-param name="activities" select="$activities"/>
						<xsl:with-param name="transitions" select="$transitions"/>
						<xsl:with-param name="alreadyProcessed" select="$newAlreadyProcessed"/>
					</xsl:call-template>
				</xsl:for-each>
			</xsl:when>

			<xsl:when test="$gatewayType = 'Complex'">
				<!-- Complex gateways are slightly different from everything else. -->
				<a id="seq_{$act/@Id}"/>
				<xsl:call-template name="indent"><xsl:with-param name="level" select="$level"/></xsl:call-template>
				
				<xsl:choose>
					<xsl:when test="$act/@xpdExt:DisplayName != ''">
						<xsl:value-of select="java:format($msgL10n, 'ComplexGatewayFormat_label', $act/@xpdExt:DisplayName)"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="java:get($msgL10n, 'ComplexGatewayComplete_label')"/>
					</xsl:otherwise>
				</xsl:choose>
				
				<xsl:text>...</xsl:text><br/>

				<xsl:for-each select="$actTrans">
					<xsl:call-template name="describeSeqFlowAction">
						<xsl:with-param name="level" select="$level + 1"/>
						<xsl:with-param name="trans" select="."/>
						<xsl:with-param name="activities" select="$activities"/>
						<xsl:with-param name="transitions" select="$transitions"/>
						<xsl:with-param name="alreadyProcessed" select="$newAlreadyProcessed"/>
					</xsl:call-template>
				</xsl:for-each>
				
			</xsl:when>
			
			<xsl:otherwise>
				<!-- 
					Output unconditional flows first... 
				-->
				
				<!-- 
					Output uncontrolled sequence flow that is NOT to a gateway first (because we will recurs thru gateways and thery are likely to have conditional outgoing 
					i.e. iuncontrolled sequence flow to non-gateway are the first REALLY uncontrolled things we want to list.
					-->
				<xsl:for-each select="$actTrans[not(xpdl2:Condition)]">
					<xsl:variable name="targetId" select="@To"/>
					<xsl:variable name="targetAct" select="$activities[@Id = $targetId]"/>
					
					<xsl:if test="count($targetAct/xpdl2:Route) = 0">
						<xsl:call-template name="describeSeqFlowAction">
							<xsl:with-param name="level" select="$level"/>
							<xsl:with-param name="trans" select="."/>
							<xsl:with-param name="activities" select="$activities"/>
							<xsl:with-param name="transitions" select="$transitions"/>
							<xsl:with-param name="alreadyProcessed" select="$newAlreadyProcessed"/>
						</xsl:call-template>
					</xsl:if>
				</xsl:for-each>

				<!-- And then output the uncontrolled sequence flow that go thru gateways -->
				<xsl:for-each select="$actTrans[not(xpdl2:Condition)]">
					<xsl:variable name="targetId" select="@To"/>
					<xsl:variable name="targetAct" select="$activities[@Id = $targetId]"/>
					
					<xsl:if test="count($targetAct/xpdl2:Route) > 0">
						<xsl:call-template name="describeSeqFlowAction">
							<xsl:with-param name="level" select="$level"/>
							<xsl:with-param name="trans" select="."/>
							<xsl:with-param name="activities" select="$activities"/>
							<xsl:with-param name="transitions" select="$transitions"/>
							<xsl:with-param name="alreadyProcessed" select="$newAlreadyProcessed"/>
						</xsl:call-template>
					</xsl:if>
				</xsl:for-each>

			
				<!-- Output conditional sequence flow at this level. -->
				<xsl:for-each select="$actTrans[xpdl2:Condition/@Type = 'CONDITION']">
					<!-- Output leader line depending on source of sequence flow. -->
					<xsl:call-template name="outputConditionLeader">
						<xsl:with-param name="act" select="$act"/>
						<xsl:with-param name="trans" select="."/>
						<xsl:with-param name="level" select="$level"/>
						<xsl:with-param name="isFirst">
							<xsl:choose>
								<xsl:when test="position() = 1">true</xsl:when>
								<xsl:otherwise>false</xsl:otherwise>
							</xsl:choose>
						</xsl:with-param>
					</xsl:call-template>
				
					<xsl:call-template name="describeSeqFlowAction">
						<xsl:with-param name="level" select="$level + 1"/>
						<xsl:with-param name="trans" select="."/>
						<xsl:with-param name="activities" select="$activities"/>
						<xsl:with-param name="transitions" select="$transitions"/>
						<xsl:with-param name="alreadyProcessed" select="$newAlreadyProcessed"/>
					</xsl:call-template>
				</xsl:for-each>
		
				<!-- Output default sequence flow. -->
				<xsl:if test="count($actTrans[xpdl2:Condition/@Type = 'OTHERWISE']) > 0">
					<!-- Output ONE deault leader line depending on source of sequence flow. -->
					<xsl:call-template name="outputConditionLeader">
						<xsl:with-param name="act" select="$act"/>
						<xsl:with-param name="trans" select="$actTrans[xpdl2:Condition/@Type = 'OTHERWISE'][1]"/>
						<xsl:with-param name="level" select="$level"/>
					</xsl:call-template>
				
				</xsl:if>

				<xsl:for-each select="$actTrans[xpdl2:Condition/@Type = 'OTHERWISE']">
					<xsl:call-template name="describeSeqFlowAction">
						<xsl:with-param name="level" select="$level + 1"/>
						<xsl:with-param name="trans" select="."/>
						<xsl:with-param name="activities" select="$activities"/>
						<xsl:with-param name="transitions" select="$transitions"/>
						<xsl:with-param name="alreadyProcessed" select="$newAlreadyProcessed"/>
					</xsl:call-template>
				</xsl:for-each>
			</xsl:otherwise>

		</xsl:choose>

	</xsl:if>

	
</xsl:template>

<!--
===================================================================================================================
	outputConditionLeader = Output leader for condition depending on source activity of conditional transition.
===================================================================================================================
-->	
<xsl:template name="outputConditionLeader">
	<xsl:param name="act"/>
	<xsl:param name="trans"/>
	<xsl:param name="level"/>
	<xsl:param name="isFirst" select="'false'"/>
	
	<xsl:call-template name="indent"><xsl:with-param name="level" select="$level"/></xsl:call-template>
	
	<a>
		<xsl:if test="$trans/xpdl2:Condition/xpdl2:Expression != ''">
			<xsl:attribute name="title">
				<xsl:value-of select="$trans/xpdl2:Condition/xpdl2:Expression"/>
			</xsl:attribute>
		</xsl:if>

		<xsl:variable name="exprLabel">
			<xsl:choose>
				<xsl:when test="$trans/@xpdExt:DisplayName != ''"><xsl:value-of select="$trans/@xpdExt:DisplayName"/></xsl:when>
				<xsl:when test="$trans/xpdl2:Condition/xpdl2:Expression != ''">
					<xsl:choose>
						<xsl:when test="contains($trans/xpdl2:Condition/xpdl2:Expression, '&#10;')"><xsl:value-of select="substring-before($trans/xpdl2:Condition/xpdl2:Expression, '&#10;')"/></xsl:when>
						<xsl:otherwise><xsl:value-of select="$trans/xpdl2:Condition/xpdl2:Expression"/></xsl:otherwise>
					</xsl:choose>
				</xsl:when>
				<xsl:otherwise><xsl:value-of select="java:get($msgL10n, 'ConditionUndefined_label')"/></xsl:otherwise>
			</xsl:choose>
		</xsl:variable>

		<xsl:variable name="gatewayType"><xsl:call-template name="getGatewayType"><xsl:with-param name="act" select="$act"/></xsl:call-template></xsl:variable>

		<xsl:choose>
			<!-- Note that Complex, XOR Event and AND gateways are handled in describeSequenceFlows template above. -->
			<xsl:when test="$gatewayType = 'ExclusiveData'">
				<!-- XOR is If, Else If, Else If, Else -->
				<xsl:choose>
					<xsl:when test="$trans/xpdl2:Condition/@Type = 'OTHERWISE'"><xsl:value-of select="java:get($msgL10n, 'ConditionXORElse_label')"/><xsl:text>...</xsl:text><br/></xsl:when>
					<xsl:when test="$isFirst = 'true'"><xsl:value-of select="java:format($msgL10n, 'ConditionIf_label', $exprLabel)" disable-output-escaping="yes"/><xsl:text>...</xsl:text><br/></xsl:when>
					<xsl:otherwise><xsl:value-of select="java:format($msgL10n, 'ConditionElseIf_label', $exprLabel)" disable-output-escaping="yes"/><xsl:text>...</xsl:text><br/></xsl:otherwise>
				</xsl:choose>
			</xsl:when>
	
			<xsl:otherwise>
				<!-- Everthing else (i.e. OR gateway + conditional from tasks are IF, IF, IF -->
				<xsl:choose>
					<xsl:when test="$trans/xpdl2:Condition/@Type = 'OTHERWISE'"><xsl:value-of select="java:get($msgL10n, 'ConditionORElse_label')"/><xsl:text>...</xsl:text><br/></xsl:when>
					<xsl:otherwise><xsl:value-of select="java:format($msgL10n, 'ConditionIf_label', $exprLabel)" disable-output-escaping="yes"/><xsl:text>...</xsl:text><br/></xsl:otherwise>
				</xsl:choose>
			</xsl:otherwise>
		</xsl:choose>
		
	</a>
	
</xsl:template>


<!--
===================================================================================================================
	describeSeqFlowAction = Output textual description of an individial sequence flow action.
===================================================================================================================
-->	
<xsl:template name="describeSeqFlowAction">
	<xsl:param name="level"/>
	<xsl:param name="trans"/>
	<xsl:param name="activities"/>
	<xsl:param name="transitions"/>
	<xsl:param name="alreadyProcessed"/>

	<xsl:variable name="targetAct" select="$activities[@Id = $trans/@To]"/>

	<xsl:if test="$targetAct">
		
		<xsl:choose>
			<!--
				HANDLE GATEWAYS
			-->
			<xsl:when test="$targetAct/xpdl2:Route">
				<!-- Recurs thru gateways -->
				<a id="seq_{$targetAct/@Id}"/>

				<xsl:variable name="gatewayTrans" select="$transitions[@From = $targetAct/@Id]"/>
				
				<xsl:call-template name="describeSequenceFlows">
					<xsl:with-param name="level" select="$level"/>
					<xsl:with-param name="act" select="$targetAct"/>
					<xsl:with-param name="actTrans" select="$gatewayTrans"/>
					<xsl:with-param name="activities" select="$activities"/>
					<xsl:with-param name="transitions" select="$transitions"/>
					<xsl:with-param name="alreadyProcessed" select="$alreadyProcessed"/>
				</xsl:call-template>
			</xsl:when>
			
			<xsl:when test="$targetAct/xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerResultLink">
				<!-- link events are special, we want to treat them as if there was an unbroken sequence flow. -->
				<a id="seq_{$targetAct/@Id}"/>
				<xsl:variable name="event" select="$targetAct/xpdl2:Event/*"/>
				<xsl:variable name="linkTarget" select="$activities[@Id = $event/xpdl2:TriggerResultLink/@Name]"/>
				<xsl:if test="$linkTarget">
					<a id="seq_{$linkTarget/@Id}"/>
					<xsl:variable name="linkTgtTrans" select="$transitions[@From = $linkTarget/@Id]"/>
					
					<xsl:call-template name="describeSequenceFlows">
						<xsl:with-param name="level" select="$level"/>
						<xsl:with-param name="act" select="$linkTarget"/>
						<xsl:with-param name="actTrans" select="$linkTgtTrans"/>
						<xsl:with-param name="activities" select="$activities"/>
						<xsl:with-param name="transitions" select="$transitions"/>
						<!-- make sure we add the source of link events to list of already processed (as describeSequenceFlows will only add the linkTarget we pass it. -->
						<xsl:with-param name="alreadyProcessed"><xsl:value-of select="$alreadyProcessed"/>;<xsl:value-of select="$targetAct/@Id"/></xsl:with-param>
					</xsl:call-template>
				</xsl:if>
			</xsl:when>
			
			<xsl:otherwise>
				<xsl:variable name="targetLink"><xsl:call-template name="getActivityHyperLink"><xsl:with-param name="act" select="$targetAct"/></xsl:call-template></xsl:variable>
				
				<xsl:variable name="targetName"><xsl:call-template name="getActivityName"><xsl:with-param name="act" select="$targetAct"/></xsl:call-template></xsl:variable>
				<xsl:variable name="targetLinkId">#<xsl:value-of select="$targetAct/@Id"/></xsl:variable>
				
				<xsl:call-template name="indent"><xsl:with-param name="level" select="$level"/></xsl:call-template>
			
				<xsl:choose>
					<!--
						END EVENTS
					-->
					<xsl:when test="$targetAct/xpdl2:Event/xpdl2:EndEvent">
						<xsl:variable name="result" select="$targetAct/xpdl2:Event/xpdl2:EndEvent/@Result"/>
						<xsl:variable name="event" select="$targetAct/xpdl2:Event/xpdl2:EndEvent/*"/>
						
						<xsl:choose>
							<xsl:when test="$result = 'Message'"><xsl:value-of select="java:format($msgL10n, 'EndOfFlowMessage_label', $targetLinkId, $targetName)" disable-output-escaping="yes"/></xsl:when>
							<xsl:when test="$result = 'Error'">
								<xsl:variable name="errCode">
									<xsl:choose>
										<xsl:when test="$event/@ErrorCode != ''">'<xsl:value-of select="$event/@ErrorCode"/>'</xsl:when>
										<xsl:otherwise>'<xsl:value-of select="java:get($msgL10n, 'UnspecifiedErrorCode_label')"/>'</xsl:otherwise>
									</xsl:choose>
								</xsl:variable>
								
								<xsl:value-of select="java:format($msgL10n, 'EndOfFlowError_label', $errCode, $targetLinkId, $targetName)" disable-output-escaping="yes"/>
								
							</xsl:when>

							<xsl:when test="$result = 'Signal'">
								<xsl:choose>
									<xsl:when test="$event/@Name != ''">
										<xsl:value-of select="java:format($msgL10n, 'ThrowSignal_label', $targetLinkId, $event/@Name)" disable-output-escaping="yes"/>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="java:format($msgL10n, 'ThrowSignalNoSignalName_label', $targetLinkId, $targetName)" disable-output-escaping="yes"/>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:when>
							
							<xsl:when test="$result = 'Cancel'"><xsl:value-of select="java:format($msgL10n, 'EndOfFlowCancel_label', $targetLinkId, $targetName)" disable-output-escaping="yes"/></xsl:when>
							<xsl:when test="$result = 'Compensation'"><xsl:value-of select="java:format($msgL10n, 'EndOfFlowCompensation_label', $targetLinkId, $targetName)" disable-output-escaping="yes"/></xsl:when>
							<xsl:when test="$result = 'Terminate'"><xsl:value-of select="java:format($msgL10n, 'EndOfFlowTerminate_label', $targetLinkId, $targetName)" disable-output-escaping="yes"/></xsl:when>
							<xsl:when test="$result = 'Multiple'"><xsl:value-of select="java:format($msgL10n, 'EndOfFlowMultiple_label', $targetLinkId, $targetName)" disable-output-escaping="yes"/></xsl:when>
							<xsl:otherwise><xsl:value-of select="java:format($msgL10n, 'EndOfFlow_label', $targetLinkId, $targetName)" disable-output-escaping="yes"/></xsl:otherwise>
						</xsl:choose>
							
						<br/>
						
					</xsl:when>
					
					<!--
						INTERMEDIATE EVENTS
					-->
					<xsl:when test="$targetAct/xpdl2:Event/xpdl2:IntermediateEvent">
						<xsl:variable name="event" select="$targetAct/xpdl2:Event/xpdl2:IntermediateEvent"/>

						<xsl:variable name="catchThrow"><xsl:call-template name="catchThrow"><xsl:with-param name="act" select="$targetAct"/></xsl:call-template></xsl:variable>

						<xsl:choose>
							<xsl:when test="$catchThrow = 'THROW'">

								<!-- THROW EVENTS -->
								<xsl:choose>
									<xsl:when test="$event/xpdl2:TriggerResultMessage"><xsl:value-of select="java:format($msgL10n, 'ProcessSend_label', $targetLinkId, $targetName)" disable-output-escaping="yes"/></xsl:when>
									<xsl:when test="$event/xpdl2:TriggerIntermediateMultiple"><xsl:value-of select="java:format($msgL10n, 'InterEventProcessMultipleThrow_label', $targetLinkId, $targetName)"/></xsl:when>
									<xsl:when test="$event/xpdl2:TriggerResultCompensation"><xsl:value-of select="java:format($msgL10n, 'InterEventProcessThrowCompensation_label', $targetLinkId, $targetName)"/></xsl:when>
									<xsl:when test="$event/xpdl2:TriggerResultSignal">
										<xsl:choose>
											<xsl:when test="$event/xpdl2:TriggerResultSignal/@Name != ''">
												<xsl:value-of select="java:format($msgL10n, 'ThrowSignal_label', $targetLinkId, $event/xpdl2:TriggerResultSignal/@Name)" disable-output-escaping="yes"/>
											</xsl:when>
											<xsl:otherwise>
												<xsl:value-of select="java:format($msgL10n, 'ThrowSignalNoSignalName_label', $targetLinkId, $targetName)" disable-output-escaping="yes"/>
											</xsl:otherwise>
										</xsl:choose>
									</xsl:when>
								</xsl:choose>
							</xsl:when>
							<xsl:otherwise>
								<!-- CATCH EVENTS -->
								<xsl:choose>
									<xsl:when test="$event/xpdl2:TriggerResultMessage">
										<xsl:value-of select="java:format($msgL10n, 'WaitForMessage_label', $targetLinkId, $targetName)" disable-output-escaping="yes"/>
										<br/>
									</xsl:when>
									<xsl:when test="$event/xpdl2:TriggerTimer">
										<xsl:value-of select="java:format($msgL10n, 'WaitUntilTimeout_label', $targetLinkId, $targetName)" disable-output-escaping="yes"/>
										<br/>
									</xsl:when>
									<xsl:when test="$event/xpdl2:TriggerConditional">
										<xsl:value-of select="java:format($msgL10n, 'WaitForConditional_label', $targetLinkId, $targetName)" disable-output-escaping="yes"/>
										<br/>
									</xsl:when>
									<xsl:when test="$event/xpdl2:TriggerIntermediateMultiple">
										<xsl:value-of select="java:format($msgL10n, 'WaitForMultiple_label', $targetLinkId, $targetName)" disable-output-escaping="yes"/>
										<br/>
									</xsl:when>
									<xsl:when test="$event/xpdl2:TriggerResultSignal">
										<xsl:choose>
											<xsl:when test="$event/xpdl2:TriggerResultSignal/@Name != ''">
												<xsl:value-of select="java:format($msgL10n, 'WaitForSignal_label', $targetLinkId, $event/xpdl2:TriggerResultSignal/@Name)" disable-output-escaping="yes"/>
												<br/>
											</xsl:when>
											<xsl:otherwise>
												<xsl:value-of select="java:format($msgL10n, 'WaitForAnySignal_label', $targetLinkId, $targetName)" disable-output-escaping="yes"/>
												<br/>
											</xsl:otherwise>
										</xsl:choose>
									</xsl:when>
									<xsl:when test="$event/xpdl2:TriggerResultCompensation">
										<xsl:value-of select="java:format($msgL10n, 'ThrowCompensation_label', $targetLinkId, $targetName)" disable-output-escaping="yes"/>
										<br/>
									</xsl:when>
									<xsl:when test="$event/@Trigger = 'Cancel'">
										<xsl:value-of select="java:format($msgL10n, 'BadlyPlacedCancel_label', $targetLinkId, $targetName)" disable-output-escaping="yes"/>
										<br/>
									</xsl:when>
									<xsl:when test="$event/@Trigger = 'Error'">
										<xsl:value-of select="java:format($msgL10n, 'BadlyPlacedError_label', $targetLinkId, $targetName)" disable-output-escaping="yes"/>
										<br/>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="java:format($msgL10n, 'WaitFor_label', $targetLinkId, $targetName)" disable-output-escaping="yes"/>
										<br/>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
					
					<!--
						TASKS
					-->
					<xsl:when test="$targetAct/xpdl2:Implementation/xpdl2:Task">
						<xsl:variable name="task" select="$targetAct/xpdl2:Implementation/xpdl2:Task"/>
						<xsl:choose>
							<xsl:when test="$task/xpdl2:TaskReceive">
								<xsl:value-of select="java:format($msgL10n, 'ProcessReceive_label', $targetLinkId, $targetName)" disable-output-escaping="yes"/>
								<br/>
							</xsl:when>
							<xsl:when test="$task/xpdl2:TaskService">
								<xsl:value-of select="java:format($msgL10n, 'ProcessServiceCall_label', $targetLinkId, $targetName)" disable-output-escaping="yes"/>
								<br/>
							</xsl:when>
							<xsl:when test="$task/xpdl2:TaskManual">
								<xsl:value-of select="java:format($msgL10n, 'ProcessManual_label', $targetLinkId, $targetName)" disable-output-escaping="yes"/>
								<br/>
							</xsl:when>
							<xsl:when test="$task/xpdl2:TaskUser">
								<xsl:value-of select="java:format($msgL10n, 'ProcessUser_label', $targetLinkId, $targetName)" disable-output-escaping="yes"/>
								<br/>
							</xsl:when>
							<xsl:when test="$task/xpdl2:TaskScript">
								<xsl:value-of select="java:format($msgL10n, 'ProcessScript_label', $targetLinkId, $targetName)" disable-output-escaping="yes"/>
								<br/>
							</xsl:when>
							<xsl:when test="$task/xpdl2:TaskSend">
								<xsl:value-of select="java:format($msgL10n, 'ProcessSend_label', $targetLinkId, $targetName)" disable-output-escaping="yes"/>
								<br/>
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="java:format($msgL10n, 'ProcessTask_label', $targetLinkId, $targetName)" disable-output-escaping="yes"/>
								<br/>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
					
					<xsl:when test="$targetAct/xpdl2:Implementation/xpdl2:No">
						<xsl:value-of select="java:format($msgL10n, 'ProcessTask_label', $targetLinkId, $targetName)" disable-output-escaping="yes"/>
						<br/>
					</xsl:when>
					
					<xsl:when test="$targetAct/xpdl2:Implementation/xpdl2:Reference">
						<xsl:value-of select="java:format($msgL10n, 'ProcessRefTask_label', $targetLinkId, $targetName)" disable-output-escaping="yes"/>
						<br/>
					</xsl:when>
		
					<xsl:when test="$targetAct/xpdl2:Implementation/xpdl2:SubFlow">
						<!-- Get referenced process / interface information, this is returned in the format...
								process|interface|blank\n   (Type of object referenced)
								name\n                      (Including package name if external reference)
								RuntimeIdentifierField\n    (Blank if process reference).
								href                        (html href id for process). 
						-->
						<xsl:variable name="subInfo" select="java:getSubFlowReferenceInfo($javaPkgInfo, $targetAct/ancestor::xpdl2:WorkflowProcess/@Id, $targetAct/@Id)"/>
						
						<xsl:variable name="type" select="substring-before($subInfo, '&#10;')"/>
						<xsl:variable name="rem1" select="substring-after($subInfo, '&#10;')"/>
		
						<xsl:variable name="name" select="substring-before($rem1, '&#10;')"/>
						<xsl:variable name="rem2" select="substring-after($rem1, '&#10;')"/>
		
						<xsl:variable name="runtimeIdentifier" select="substring-before($rem2, '&#10;')"/>
						
						<xsl:variable name="href" select="substring-after($rem2, '&#10;')"/>

						<xsl:choose>
							<xsl:when test="$type = 'interface'">
								<xsl:choose>
									<xsl:when test="$runtimeIdentifier != ''">
										<xsl:variable name="fieldHref"><xsl:value-of select="java:getDataHrefFromName($javaPkgInfo, $targetAct/ancestor::xpdl2:WorkflowProcess/@Id, $runtimeIdentifier)"/></xsl:variable>
										<xsl:value-of select="java:format($msgL10n, 'ProcessSubProcInterfaceAndField_label', $href, $name, $fieldHref, $runtimeIdentifier, $targetLinkId, $targetName)" disable-output-escaping="yes"/>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="java:format($msgL10n, 'ProcessSubProcInterface_label', $href, $name, $targetLinkId, $targetName)" disable-output-escaping="yes"/>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:when>

							<xsl:when test="$type = 'process'">
								<xsl:value-of select="java:format($msgL10n, 'ProcessSubProc_label', $href, $name, $targetLinkId, $targetName)" disable-output-escaping="yes"/>
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="java:format($msgL10n, 'ProcessUnspecifiedSubProc_label', $targetLinkId, $targetName)" disable-output-escaping="yes"/>
							</xsl:otherwise>
						</xsl:choose>

						<br/>
						
					</xsl:when>
		
					<xsl:when test="$targetAct/xpdl2:BlockActivity">
						<xsl:value-of select="java:format($msgL10n, 'ProcessEmbSubProc_label', $targetLinkId, $targetName)" disable-output-escaping="yes"/>
						<br/>
					</xsl:when>
					
					<xsl:otherwise>
					</xsl:otherwise>
					
				</xsl:choose>

			</xsl:otherwise>
			
		</xsl:choose>
	</xsl:if>

</xsl:template>
	
	
<!--
===================================================================================================================
	outputDataObjects = Include the data objects
===================================================================================================================
-->			
	<xsl:template name="outputDataObjects">
		<xsl:param name="dataObjs" />
		<xsl:param name="associations" />
		<xsl:param name="process" />

		<xsl:if test="count($dataObjs) > 0">

				<div class="section">
					<table>
						<thead>
							<tr>
								<th class="sectionTitle">
										<a id="dataObjs_{$process/@Id}"><xsl:value-of select="java:get($msgL10n, 'DataObjs_label')"/></a>
								</th>
								<th class="sectionTitle screenOnly" style="text-align: right; font-weight: normal;"><a href="#img_{$process/@Id}"><xsl:value-of select="java:get($msgL10n, 'GotoDiagram_label')"/></a></th>
							</tr>
						</thead>
						<tbody>
							<xsl:for-each select="$dataObjs">
								<tr>
									<th class="heading">
										<a id="{@Id}">
											<xsl:variable name="objName"><xsl:call-template name="getName"/></xsl:variable>
											<xsl:choose>
												<xsl:when test="$objName != ''">
													<xsl:value-of select="$objName"/>
												</xsl:when>
												<xsl:otherwise>
													<xsl:call-template name="getDataObjectName">
														<xsl:with-param name="dataObj" select="." />
													</xsl:call-template>
												</xsl:otherwise>
											</xsl:choose>
										</a>
									</th>
						
									<td>
										<div>
											<table>
												<tbody>
													<tr>
														<th class="heading"><xsl:value-of select="java:get($msgL10n, 'DataObjectState_label')"/></th><td><xsl:value-of select="xpdl2:DataObject/@State" />&nbsp;</td>
													</tr>
													<xsl:if test="xpdl2:DataObject/xpdExt:DataObjectAttributes/xpdExt:Description">
														<tr>
															<th class="heading"><xsl:value-of select="java:get($msgL10n, 'Description_label')"/></th>
															<td><xsl:value-of select="xpdl2:DataObject/xpdExt:DataObjectAttributes/xpdExt:Description" />&nbsp;</td>
														</tr>
													</xsl:if>
													<xsl:if test="xpdl2:DataObject/xpdExt:DataObjectAttributes/@xpdExt:ExternalReference">
														<tr>
															<th class="heading"><xsl:value-of select="java:get($msgL10n, 'DataObjectExternalReference_label')"/></th>
															<td>
																<xsl:element name="a">
																	<xsl:attribute name="href">
																		<xsl:value-of select="xpdl2:DataObject/xpdExt:DataObjectAttributes/@xpdExt:ExternalReference" />
																	</xsl:attribute>
																	<xsl:value-of select="xpdl2:DataObject/xpdExt:DataObjectAttributes/@xpdExt:ExternalReference" />
																	&nbsp;
																</xsl:element>
															</td>
														</tr>
													</xsl:if>
													<xsl:if test="xpdl2:DataObject/xpdExt:DataObjectAttributes/xpdExt:Properties/xpdExt:Property">
														<tr>
															<th class="heading"><xsl:value-of select="java:get($msgL10n, 'DataObjectProperties_label')"/></th>
															<td>
																<!-- Output the properties in a sepatate table within this cell -->
																<table>
																	<thead>
																		<tr>
																			<th class="columnWidth"><xsl:value-of select="java:get($msgL10n, 'DataObjectProperty_label')"/></th><th>Value</th>
																		</tr>
																	</thead>
																	<tbody>
																		<xsl:for-each select="xpdl2:DataObject/xpdExt:DataObjectAttributes/xpdExt:Properties/xpdExt:Property">
																			<tr>
																				<td class="noBorder">&nbsp;<xsl:value-of select="@Name" />&nbsp;</td><td class="noBorder">&nbsp;<xsl:value-of select="." />&nbsp;</td>
																			</tr>
																		</xsl:for-each>
																	</tbody>
																</table>
																&nbsp;
															</td>
														</tr>
													</xsl:if>
												</tbody>
											</table>
										</div>
									</td>
								</tr>
							</xsl:for-each>
						</tbody>
					</table>
				</div>
		</xsl:if>
		
	</xsl:template>
	
<!--
===================================================================================================================
	outputDataObjAssociations = Output any associations for an activity
===================================================================================================================
-->		
	<xsl:template name="outputDataObjAssociations">
		<xsl:param name="associations" />
		<xsl:param name="dataObjs" />
		
		<!-- Only output if association is to a data object -->
		<xsl:variable name="output">
			<xsl:for-each select="$associations">
				<xsl:variable name="source" select="@Source" />
				<xsl:variable name="target" select="@Target" />
				<xsl:variable name="obj" select="$dataObjs[@Id = $source or @Id = $target]" />
				<xsl:if test="$obj">
					<xsl:text>&lt;tr&gt;&lt;td class="noBorder"&gt;&lt;a href="#</xsl:text>
					<xsl:value-of select="$obj/@Id" />
					<xsl:text>"&gt;</xsl:text>
						<xsl:call-template name="getDataObjectName">
							<xsl:with-param name="dataObj" select="$obj" />
						</xsl:call-template>
					<xsl:text>&lt;/a&gt;&lt;/td&gt;&lt;td class="noBorder"&gt;</xsl:text>
							<xsl:value-of select="@AssociationDirection" />
					<xsl:text>&lt;/td&gt;&lt;/tr&gt;</xsl:text>
				</xsl:if>
			</xsl:for-each>
		</xsl:variable>
		
		<xsl:if test="$output != ''">
			<tr>
				<th class="heading"><xsl:value-of select="java:get($msgL10n, 'DataObjectAssociations_label')"/></th>
				<td>
					<table>
						<thead>
							<tr><th class="columnWidth"><xsl:value-of select="java:get($msgL10n, 'DataObjectAssociationObject_label')"/></th><th><xsl:value-of select="java:get($msgL10n, 'DataObjectAssociationDirection_label')"/></th></tr>
						</thead>
						<tbody>
							<xsl:value-of select="$output" disable-output-escaping="yes"/>
						</tbody>
					</table>
					&nbsp;
				</td>
			</tr>
		</xsl:if>
		
	</xsl:template>
	
<!--
===================================================================================================================
	outputEventType = Output the details of the event type
===================================================================================================================
-->	
	<xsl:template name="outputEventType">
		<xsl:param name="event" />
		<xsl:param name="allActs"/>
		<xsl:param name="activityId"/>
		<xsl:param name="processId"/>
		
		<!-- Get trigger type / result type -->
		<xsl:variable name="trigger">
			<xsl:choose>
				<xsl:when test="$event/xpdl2:EndEvent"><xsl:value-of select="$event/xpdl2:EndEvent/@Result" /></xsl:when>
				<xsl:otherwise><xsl:value-of select="$event/child::*[position() = 1]/@Trigger" /></xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		

		<!-- Trigger dependent content -->
		<xsl:choose>
			<!-- Message trigger -->
			<xsl:when test="$trigger = 'Message'">
				<!-- Get the message element -->
				<xsl:variable name="message" select="$event/child::*/child::xpdl2:TriggerResultMessage" />
				
				<xsl:call-template name="outputTriggerResultMessage">
					<xsl:with-param name="message" select="$message"/>
					<xsl:with-param name="activityId" select="$activityId" />
					<xsl:with-param name="processId" select="$processId" />
				</xsl:call-template>
			</xsl:when>
			
			<!-- Timer trigger -->
			<xsl:when test="$trigger = 'Timer'">
				<!-- Get the timer element -->
				<xsl:variable name="timer" select="$event/child::*/child::xpdl2:TriggerTimer" />									
				<tr>
					<th class="heading"><xsl:value-of select="java:get($msgL10n, 'EventTimerType_label')"/></th>
					<td>
						<xsl:choose>
							<xsl:when test="$timer/xpdl2:TimeDate">
								<xsl:value-of select="java:get($msgL10n, 'EventTimerTypeDateTime_label')"/>
								<xsl:if test="$timer/xpdl2:TimeDate != ''">&nbsp;(<xsl:value-of select="$timer/xpdl2:TimeDate" />)</xsl:if>
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="java:get($msgL10n, 'EventTimerTypeCycle_label')"/>
								<xsl:if test="$timer/xpdl2:TimeCycle != ''">&nbsp;(<xsl:value-of select="$timer/xpdl2:TimeCycle" />)</xsl:if>
							</xsl:otherwise>
						</xsl:choose>
						
						<xsl:if test="$event/xpdl2:IntermediateEvent/@Target != ''"> <!-- attached evrent -->
							<xsl:choose>
								<xsl:when test="$timer/@xpdExt:ContinueOnTimeout = 'true'"><br/><xsl:value-of select="java:get($msgL10n, 'EventTimerTypeContinue_label')"/></xsl:when>
								<xsl:otherwise><br/><xsl:value-of select="java:get($msgL10n, 'EventTimerTypeWithdraw_label')"/></xsl:otherwise>
							</xsl:choose>
						</xsl:if>
					</td>
				</tr>
				
				<xsl:if test="$event/../xpdl2:Deadline/xpdl2:DeadlineDuration and $showImplementations = 'true'">
					<tr>
						<th class="heading"><xsl:value-of select="java:get($msgL10n, 'ScriptTitle_label')"/> (<xsl:value-of select="$event/../xpdl2:Deadline/xpdl2:DeadlineDuration/@ScriptGrammar"/>)</th>
						<td>
							<xsl:choose>
								<xsl:when test="$event/../xpdl2:Deadline/xpdl2:DeadlineDuration/@ScriptGrammar = 'ConstantPeriod'">
									<xsl:variable name="period" select="$event/../xpdl2:Deadline/xpdl2:DeadlineDuration/xpdExt:ConstantPeriod"/>
									<table>
										<thead>
											<tr>
												<th class="sectionTitle" colspan="2"><xsl:value-of select="java:get($msgL10n, 'EventTimerPeriod_label')"/></th>
											</tr>
										</thead>
										<tbody>
											<tr><th class="heading"><xsl:value-of select="java:get($msgL10n, 'EventTimerPeriodYears_label')"/></th><td><xsl:value-of select="$period/@Years"/></td></tr>
											<tr><th class="heading"><xsl:value-of select="java:get($msgL10n, 'EventTimerPeriodMonths_label')"/></th><td><xsl:value-of select="$period/@Months"/></td></tr>
											<tr><th class="heading"><xsl:value-of select="java:get($msgL10n, 'EventTimerPeriodWeeks_label')"/></th><td><xsl:value-of select="$period/@Weeks"/></td></tr>
											<tr><th class="heading"><xsl:value-of select="java:get($msgL10n, 'EventTimerPeriodDays_label')"/></th><td><xsl:value-of select="$period/@Days"/></td></tr>
											<tr><th class="heading"><xsl:value-of select="java:get($msgL10n, 'EventTimerPeriodHours_label')"/></th><td><xsl:value-of select="$period/@Hours"/></td></tr>
											<tr><th class="heading"><xsl:value-of select="java:get($msgL10n, 'EventTimerPeriodMinutes_label')"/></th><td><xsl:value-of select="$period/@Minutes"/></td></tr>
											<tr><th class="heading"><xsl:value-of select="java:get($msgL10n, 'EventTimerPeriodSeconds_label')"/></th><td><xsl:value-of select="$period/@Seconds"/></td></tr>
											<tr><th class="heading"><xsl:value-of select="java:get($msgL10n, 'EventTimerPeriodMicros_label')"/></th><td><xsl:value-of select="$period/@MicroSeconds"/></td></tr>
										</tbody>
									</table>
								</xsl:when>
								<xsl:otherwise>
									<xsl:call-template name="newline2br"><xsl:with-param name="input" select="$event/../xpdl2:Deadline/xpdl2:DeadlineDuration"/></xsl:call-template>
								</xsl:otherwise>
							</xsl:choose>
						</td>
					</tr>
				</xsl:if>
			</xsl:when>
			
			<!-- Conditional trigger -->
			<xsl:when test="$trigger = 'Conditional'">
				<tr>
					<th class="heading"><xsl:value-of select="java:get($msgL10n, 'EventConditionalName_label')"/></th>
					<td><xsl:value-of select="$event/child::*/child::xpdl2:TriggerConditional/@ConditionName" />&nbsp;</td>
				</tr>									
			</xsl:when>
			
			<!-- Link trigger (this really means start / end only as intermediate 'goto' links are handled in seq flow scripting. -->
			<xsl:when test="$trigger = 'Link'">
				<!-- Get the link element -->
				<xsl:variable name="link" select="$event/child::*/child::xpdl2:TriggerResultLink" />
				<xsl:choose>
					<xsl:when test="$event/xpdl2:StartEvent">
						<xsl:variable name="linksFrom" select="$allActs[xpdl2:Event/*/xpdl2:TriggerResultLink/@LinkId = $event/../@Id]"/>
						<tr>
							<th class="heading"><xsl:value-of select="java:get($msgL10n, 'LinksFrom_label')"/></th>
							<td>
								<xsl:for-each select="$linksFrom">
									<xsl:if test="position() > 1">
										<xsl:text>,</xsl:text>&nbsp;
									</xsl:if>
									<xsl:call-template name="getActivityHyperLink"><xsl:with-param name="act" select="."/></xsl:call-template>
								</xsl:for-each>
								&nbsp;
							</td>
						</tr>
					</xsl:when>
					<xsl:when test="$event/xpdl2:EndEvent">
						<xsl:variable name="linkEv" select="$allActs[@Id = $link/@LinkId]"/>
						<tr>
							<th class="heading"><xsl:value-of select="java:get($msgL10n, 'LinksTo_label')"/></th>
							<td>
								<xsl:choose>
									<xsl:when test="$linkEv">
										<xsl:call-template name="getActivityHyperLink"><xsl:with-param name="act" select="$linkEv"/></xsl:call-template>
									</xsl:when>
									<xsl:otherwise><i><xsl:value-of select="java:get($msgL10n, 'Unspecified_label')"/></i></xsl:otherwise>
								</xsl:choose>
								&nbsp;
							</td>
						</tr>
					</xsl:when>
				</xsl:choose>
			</xsl:when>
			
			<!-- Error result -->
			<xsl:when test="$trigger = 'Error'">
				<xsl:variable name="otherErrorEvents" select="$allActs[count(xpdl2:Event/*/xpdl2:ResultError) > 0]"/>
				<xsl:variable name="errCode" select="$event/*/xpdl2:ResultError/@ErrorCode"/>
				<xsl:variable name="target" select="$event/xpdl2:IntermediateEvent/@Target"/>

				<tr>
					<xsl:choose>
						<xsl:when test="$target != ''">
							<th class="heading"><xsl:value-of select="java:get($msgL10n, 'CatchesError_label')"/></th>
						</xsl:when>
						<xsl:otherwise>
							<th class="heading"><xsl:value-of select="java:get($msgL10n, 'ThrowsError_label')"/></th>
						</xsl:otherwise>
					</xsl:choose>
					<td>
						<xsl:choose>
							<xsl:when test="$errCode != ''">
								<xsl:value-of select="$event/*/xpdl2:ResultError/@ErrorCode"/>
								<br/>
							</xsl:when>
							<xsl:otherwise>
								<xsl:choose>
									<xsl:when test="$target != ''">
										<i><xsl:value-of select="java:get($msgL10n, 'AllErrors_label')"/></i>
									</xsl:when>
									<xsl:otherwise>
										<i><xsl:value-of select="java:get($msgL10n, 'ErrorUnspecified_label')"/></i>
									</xsl:otherwise>
								</xsl:choose>
								<br/>
							</xsl:otherwise>
						</xsl:choose>

						<xsl:choose>
							<xsl:when test="$target != ''"> <!-- Catcher, look for throwers -->
								&nbsp;&nbsp;<xsl:value-of select="java:get($msgL10n, 'ThrownBy_label')"/>...&nbsp;
								<xsl:for-each select="$otherErrorEvents">
									<xsl:if test="count(xpdl2:Event/xpdl2:IntermediateEvent/@Target) = 0">
										<xsl:choose>
											<xsl:when test="$errCode != ''">
												<!-- catches specific error code. -->
												<xsl:if test="xpdl2:Event/*/xpdl2:ResultError/@ErrorCode = $errCode">
													<xsl:call-template name="getActivityHyperLink"><xsl:with-param name="act" select="."/></xsl:call-template>
													<xsl:text>,</xsl:text>&nbsp;
												</xsl:if>
											</xsl:when>
											<xsl:otherwise>
												<!-- catches all errors -->
												<xsl:call-template name="getActivityHyperLink"><xsl:with-param name="act" select="."/></xsl:call-template>
												<xsl:text>,</xsl:text>&nbsp;
											</xsl:otherwise>
										</xsl:choose>
									</xsl:if>
								</xsl:for-each>
								
							</xsl:when>
							<xsl:otherwise> <!-- Thrower, look for catchers -->
								&nbsp;&nbsp;<xsl:value-of select="java:get($msgL10n, 'CaughtBy_label')"/>...&nbsp;
								<xsl:if test="$errCode != ''">
									<xsl:for-each select="$otherErrorEvents">
										<xsl:if test="xpdl2:Event/xpdl2:IntermediateEvent/@Target != ''">
											<xsl:variable name="otherErrCode" select="xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:ResultError/@ErrorCode"/>
											<xsl:if test="string($otherErrCode) = '' or $otherErrCode = $errCode">
												<xsl:variable name="targetId" select="xpdl2:Event/xpdl2:IntermediateEvent/@Target"/>
												<xsl:call-template name="getActivityHyperLink"><xsl:with-param name="act" select="$allActs[@Id = $targetId]"/></xsl:call-template>
												<xsl:text>,</xsl:text>&nbsp;
											</xsl:if>
										</xsl:if>
									</xsl:for-each>
								</xsl:if>
							</xsl:otherwise>
						</xsl:choose>
						&nbsp;
					</td>
				</tr>
				
			</xsl:when>

			<!-- Signal  -->
			<xsl:when test="$trigger = 'Signal'">
				<xsl:variable name="otherSignalEvents" select="$allActs[count(xpdl2:Event/*/xpdl2:TriggerResultSignal) > 0]"/>
				<xsl:variable name = "signalType" select = "$event/*/xpdl2:TriggerResultSignal/@xpdExt:SignalType" />							
				<xsl:variable name="signalName">
					<xsl:choose>
						<xsl:when test="(not($signalType) or $signalType = 'Local' or $signalType = 'CaseData')"> 
						<!-- For local signal type(null signal type is also considered as local.) or case data signal type the signal name is the same -->
							<xsl:value-of select="$event/*/xpdl2:TriggerResultSignal/@Name" />
						</xsl:when>
						<xsl:otherwise>
						<!-- For Global Signal the name in model is projectName/gsdFileName#globalSignalName hence the string after # will form our signal name and string before it will be in brackets (projectName/gsdFileName). -->
							<xsl:value-of select="substring-after($event/*/xpdl2:TriggerResultSignal/@Name,'#')"/>
							<xsl:text> (</xsl:text>
							<xsl:value-of select="substring-before($event/*/xpdl2:TriggerResultSignal/@Name,'#')"></xsl:value-of>
							<!-- Add extension to the filename. -->
							<xsl:text>.gsd)</xsl:text>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>

				<xsl:variable name="catchThrow"><xsl:call-template name="catchThrow"><xsl:with-param name="act" select="."/></xsl:call-template></xsl:variable>
			<!-- Specify the type of signal: Local , Case Data or Global -->
				<tr>
					<th class="heading"><xsl:value-of select="java:get($msgL10n, 'SignalType_label')"/></th>
					<td>
						<xsl:choose>
							<xsl:when test="(not($signalType) or $signalType = 'Local')"> 
								<xsl:value-of select="java:get($msgL10n, 'SignalTypeLocal_label')"/>
							</xsl:when>
							<xsl:when test="$signalType = 'CaseData'">
								<xsl:value-of select="java:get($msgL10n, 'SignalTypeCaseData_label')"/>
							</xsl:when>
							<xsl:otherwise>								
								<xsl:value-of select="java:get($msgL10n, 'SignalTypeGlobal_label')"/>
							</xsl:otherwise>
						</xsl:choose>
					</td>
				</tr>
				<tr>
					<xsl:choose>
						<xsl:when test="$catchThrow = 'CATCH'">
							<th class="heading"><xsl:value-of select="java:get($msgL10n, 'CatchesSignal_label')"/></th>
						</xsl:when>
						<xsl:otherwise>
							<th class="heading"><xsl:value-of select="java:get($msgL10n, 'ThrowsSignal_label')"/></th>
						</xsl:otherwise>
					</xsl:choose>
					<td>
						<xsl:choose>
							<xsl:when test="$signalName != ''">
								<xsl:value-of select="$signalName"/>
								<br/>
							</xsl:when>
							<xsl:otherwise>
								<xsl:choose>
									<xsl:when test="$catchThrow = 'CATCH'">
										<i><xsl:value-of select="java:get($msgL10n, 'AllSignals_label')"/></i>
									</xsl:when>
									<xsl:otherwise>
										<i><xsl:value-of select="java:get($msgL10n, 'SignalUnspecified_label')"/></i>
									</xsl:otherwise>
								</xsl:choose>
								<br/>
							</xsl:otherwise>
						</xsl:choose>

						<xsl:if test="(not($signalType) or $signalType = 'Local')">  
						<!-- Only Show the 'Broadcast By' Info if the signalType is local or no signalType is set(because no signaltype set means that its a local signal).-->
						<xsl:choose>
							<xsl:when test="$catchThrow = 'CATCH'"> <!-- Catcher, look for throwers -->
								&nbsp;&nbsp;<xsl:value-of select="java:get($msgL10n, 'SignalThrownBy_label')"/>...&nbsp;
								
								<xsl:for-each select="$otherSignalEvents">

									<xsl:variable name="otherCatchThrow"><xsl:call-template name="catchThrow"><xsl:with-param name="act" select="."/></xsl:call-template></xsl:variable>

									<xsl:if test="$otherCatchThrow = 'THROW'">
										<xsl:choose>
											<xsl:when test="$signalName != ''">
												<!-- catches specific signal code. -->
												<xsl:if test="xpdl2:Event/*/xpdl2:TriggerResultSignal/@Name = $signalName">
													<xsl:call-template name="getActivityHyperLink"><xsl:with-param name="act" select="."/></xsl:call-template>
													<xsl:text>,</xsl:text>&nbsp;
												</xsl:if>
											</xsl:when>
											<xsl:otherwise>
												<!-- catches all signals -->
												<xsl:call-template name="getActivityHyperLink"><xsl:with-param name="act" select="."/></xsl:call-template>
												<xsl:text>,</xsl:text>&nbsp;
											</xsl:otherwise>
										</xsl:choose>
									</xsl:if>
								</xsl:for-each>
								
							</xsl:when>
							<xsl:otherwise> <!-- Thrower, look for catchers -->
								&nbsp;&nbsp;<xsl:value-of select="java:get($msgL10n, 'SignalCaughtBy_label')"/>...&nbsp;
								<xsl:if test="$signalName != ''">
									<xsl:for-each select="$otherSignalEvents">

										<xsl:variable name="otherCatchThrow"><xsl:call-template name="catchThrow"><xsl:with-param name="act" select="."/></xsl:call-template></xsl:variable>

										<xsl:if test="$catchThrow = 'CATCH'">
											<xsl:variable name="otherSignalName" select="xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerResultSignal/@Name"/>
											<xsl:if test="string($otherSignalName) = '' or $otherSignalName = $signalName">
												<xsl:variable name="targetId" select="xpdl2:Event/xpdl2:IntermediateEvent/@Target"/>
												<xsl:choose>
													<xsl:when test="$targetId != ''">
														<xsl:call-template name="getActivityHyperLink"><xsl:with-param name="act" select="$allActs[@Id = $targetId]"/></xsl:call-template>
													</xsl:when>
													<xsl:otherwise>
														<xsl:call-template name="getActivityHyperLink"><xsl:with-param name="act" select="."/></xsl:call-template>
													</xsl:otherwise>
												</xsl:choose>
												
												<xsl:text>,</xsl:text>&nbsp;
											</xsl:if>
										</xsl:if>
									</xsl:for-each>
								</xsl:if>
							</xsl:otherwise>
						</xsl:choose>
						&nbsp;
						</xsl:if>
					</td>
				</tr>
				
			</xsl:when>
			
		</xsl:choose>
	
	</xsl:template>

<!--
===================================================================================================================
	outputTriggerResultMessage
===================================================================================================================
-->	
<xsl:template name="outputTriggerResultMessage">
	<xsl:param name="message"/>
	<xsl:param name="activityId"/>
	<xsl:param name="processId"/>
	
	<!-- Get the message element -->
	<xsl:choose>
		<xsl:when test="$message/xpdl2:Message/@Name != '' or $message/xpdl2:Message/@To != '' or $message/xpdl2:Message/@From != '' or $message/xpdl2:Message/@FaultName != ''">
			<tr>
				<th class="heading"><xsl:value-of select="java:get($msgL10n, 'MessageEventInformation_label')"/></th>
				<td>
					<table>
						<tbody>
							<tr><th class="heading"><xsl:value-of select="java:get($msgL10n, 'Name_label')"/></th><td><xsl:value-of select="$message/xpdl2:Message/@Name" />&nbsp;</td></tr>
							<tr><th class="heading"><xsl:value-of select="java:get($msgL10n, 'MessageTo_label')"/></th><td><xsl:value-of select="$message/xpdl2:Message/@To" />&nbsp;</td></tr>
							<tr><th class="heading"><xsl:value-of select="java:get($msgL10n, 'MessageFrom_label')"/></th><td><xsl:value-of select="$message/xpdl2:Message/@From" />&nbsp;</td></tr>
							<tr><th class="heading"><xsl:value-of select="java:get($msgL10n, 'MessageFault_label')"/></th><td><xsl:value-of select="$message/xpdl2:Message/@FaultName" />&nbsp;</td></tr>
						</tbody>
					</table>
				</td>
			</tr>
		</xsl:when>
		<xsl:when test="$message/ancestor::xpdl2:Activity/@xpdExt:Implements != ''">
			<!-- for an implementing message event, refer back to interfasce event -->
			<tr>
				<th class="heading"><xsl:value-of select="java:get($msgL10n, 'MessageEventInformation_label')"/></th>
				<td style="white-space: nowrap;">
					<xsl:value-of select="java:get($msgL10n, 'InheritedFrom_label')"/>:&nbsp;
					<a href="{java:getImplementedMethodHrefId($javaPkgInfo, $message/ancestor::xpdl2:WorkflowProcess/@Id, $message/ancestor::xpdl2:Activity/@Id)}">
						<xsl:value-of select="java:getImplementedInterfaceName($javaPkgInfo, $message/ancestor::xpdl2:WorkflowProcess/@Id)"/>
						&nbsp;-&nbsp;
						<xsl:value-of select="java:getImplementedMethodName($javaPkgInfo, $message/ancestor::xpdl2:WorkflowProcess/@Id, $message/ancestor::xpdl2:Activity/@Id)"/>
					</a>
				</td>
			</tr>
		</xsl:when>
	</xsl:choose>
	
	<xsl:if test="($message/xpdl2:WebServiceOperation/@OperationName != '' or $message/xpdExt:PortTypeOperation/@OperationName != '') and $showImplementations = 'true'">
		<tr>
			<th class="heading"><xsl:value-of select="java:get($msgL10n, 'WebServiceOperation_label')"/></th>
			<td>
				<xsl:call-template name="outputWebService">
					<xsl:with-param name="activityType" select="'event'"/>
					<xsl:with-param name="portTypeOperation" select="$message/xpdExt:PortTypeOperation"/>
					<xsl:with-param name="webSvcOperation" select="$message/xpdl2:WebServiceOperation"/>
					<xsl:with-param name="title" select="''"/>
				</xsl:call-template>
			</td>
		</tr>
	</xsl:if>
	
	<!-- XPD-7347: add REST Service Info column for REST message events -->
	<xsl:if test="($message/xpdExt:RestServiceOperation) and $activityId and $processId and $showImplementations = 'true'">
		<tr>
			<th class="heading"><xsl:value-of select="java:get($msgL10n, 'RestService_label')"/></th>
			<td>
				<xsl:call-template name="outputRestService">
					<xsl:with-param name="activityId" select="$activityId" />
					<xsl:with-param name="processId" select="$processId" />
					<xsl:with-param name="title" select="''"/>
				</xsl:call-template>							
			</td>
		</tr>	
	</xsl:if>	

</xsl:template>	

<!--
===================================================================================================================
	outputWebService
===================================================================================================================
-->	
<xsl:template name="outputWebService">
	<xsl:param name="activityType"/>
	<xsl:param name="webSvcOperation"/>
	<xsl:param name="portTypeOperation"/>
	<xsl:param name="title"/>
	
	<table>
		<xsl:if test="$title != ''">
			<thead>
				<tr>
					<th class="sectionTitle" colspan="2"><xsl:value-of select="$title"/></th>
				</tr>
			</thead>
		</xsl:if>
		<tbody>
			<!-- deal with the difference between Bound and Unbound -->
	
			<xsl:choose>
				<xsl:when test="$webSvcOperation/xpdl2:Service/@PortName != ''">
					<tr><th class="heading"><xsl:value-of select="java:get($msgL10n, 'WebServiceOperationName_label')"/></th><td><xsl:value-of select="$webSvcOperation/@OperationName"/>&nbsp;</td></tr>
					<tr><th class="heading"><xsl:value-of select="java:get($msgL10n, 'WebServicePortName_label')"/></th><td><xsl:value-of select="$webSvcOperation/xpdl2:Service/@PortName"/>&nbsp;</td></tr>
					<tr><th class="heading"><xsl:value-of select="java:get($msgL10n, 'WebServiceServiceName_label')"/></th><td><xsl:value-of select="$webSvcOperation/xpdl2:Service/@ServiceName"/>&nbsp;</td></tr>
				</xsl:when>
				<xsl:otherwise>
					<tr><th class="heading"><xsl:value-of select="java:get($msgL10n, 'WebServiceOperationName_label')"/></th><td><xsl:value-of select="$portTypeOperation/@OperationName"/>&nbsp;</td></tr>
					<tr><th class="heading"><xsl:value-of select="java:get($msgL10n, 'WebServicePortTypeName_label')"/></th><td><xsl:value-of select="$portTypeOperation/@PortTypeName"/>&nbsp;</td></tr>
				</xsl:otherwise>
			</xsl:choose>

			<xsl:choose>
				<xsl:when test="$webSvcOperation/xpdl2:Service/xpdl2:EndPoint/xpdl2:ExternalReference/@location != ''">
					<tr><th class="heading"><xsl:value-of select="java:get($msgL10n, 'WebServiceLocation_label')"/></th><td><xsl:value-of select="$webSvcOperation/xpdl2:Service/xpdl2:EndPoint/xpdl2:ExternalReference/@location"/>&nbsp;</td></tr>
				</xsl:when>
				<xsl:when test="$portTypeOperation/xpdExt:ExternalReference/@location != ''">
					<tr><th class="heading"><xsl:value-of select="java:get($msgL10n, 'WebServiceLocation_label')"/></th><td><xsl:value-of select="$portTypeOperation/xpdExt:ExternalReference/@location"/>&nbsp;</td></tr>
				</xsl:when>
			</xsl:choose>
		</tbody>
	</table>
	
</xsl:template>

<!--
===================================================================================================================
	outputRESTService
===================================================================================================================
-->	
<xsl:template name="outputRestService">
	<xsl:param name="activityId"/>
	<xsl:param name="processId"/>	
	<xsl:param name="title"/>
	
	<xsl:variable name="serviceName">
		<xsl:value-of select="java:getRestServiceName($javaPkgInfo, $processId, $activityId)"></xsl:value-of>
	</xsl:variable>
	
	<xsl:variable name="resourceName">
		<xsl:value-of select="java:getRestResourceName($javaPkgInfo, $processId, $activityId)"></xsl:value-of>
	</xsl:variable>
	
	<xsl:variable name="methodName">
		<xsl:value-of select="java:getRestMethodName($javaPkgInfo, $processId, $activityId)"></xsl:value-of>
	</xsl:variable>
	
	<table>
		<xsl:if test="$title != ''">
			<thead>
				<tr>
					<th class="sectionTitle" colspan="2"><xsl:value-of select="$title"/></th>
				</tr>
			</thead>
		</xsl:if>
		<tbody>				
			<tr>
				<th class="heading">
					<xsl:value-of select="java:get($msgL10n, 'Service_label')" />
				</th>
				<td>
					<xsl:if test="$serviceName">
						<xsl:value-of select="$serviceName"></xsl:value-of>
					</xsl:if>					
					&nbsp;
				</td>
			</tr>
			<tr>
				<th class="heading">
					<xsl:value-of select="java:get($msgL10n, 'Resource_label')" />
				</th>
				<td>
					<xsl:if test="$resourceName">
						<xsl:value-of select="$resourceName"></xsl:value-of>
					</xsl:if>					
					&nbsp;
				</td>
			</tr>
			<tr>
				<th class="heading">
					<xsl:value-of select="java:get($msgL10n, 'Method_label')" />
				</th>
				<td>
					<xsl:if test="$methodName">
						<xsl:value-of select="$methodName"></xsl:value-of>
					</xsl:if>					
					&nbsp;
				</td>
			</tr>
		</tbody>
	</table>	
</xsl:template>
	
<!--
===================================================================================================================
	outputTaskType = Output the details of the task type
===================================================================================================================
-->	
<xsl:template name="outputTaskType">
	<xsl:param name="activity" />
	<xsl:param name="activitySets" />
	<xsl:param name="participants" />
	<xsl:param name="processes" />
	<xsl:param name="associations"/>
	<xsl:param name="annotations"/>
	<xsl:param name="dataObjs"/>
	<xsl:param name="allActs"/>
	<xsl:param name="process"/>
	
	<!-- 
		Activity markers 
	-->
	<!-- Get activity marker if any -->
	<xsl:variable name="marker">
		<xsl:if test="$activity/xpdl2:Loop/@LoopType = 'MultiInstance'">
			<xsl:value-of select="java:get($msgL10n, 'ActivityMarkerMULTILOOP_label')"/>
		</xsl:if>
		<xsl:if test="$activity/xpdl2:Loop/@LoopType = 'Standard'">
			<xsl:value-of select="java:get($msgL10n, 'ActivityMarkerSTDLOOP_label')"/>			
		</xsl:if>
		<xsl:if test="xpdl2:BlockActivity">
			<xsl:variable name="setId" select="xpdl2:BlockActivity/@ActivitySetId" />
			<xsl:if test="$activitySets[@Id = $setId]/@AdHoc = 'true'">
				<xsl:if test="@StartQuantity != ''">,&nbsp;</xsl:if>								
				<xsl:if test="$activity/xpdl2:Loop">
				<!-- Comma separate markers if there are multiple markers -->
				<xsl:text>, </xsl:text>
				</xsl:if>				
				<xsl:value-of select="java:get($msgL10n, 'ActivityMarkerADHOC_label')"/>				
			</xsl:if>
		</xsl:if>
		<xsl:if test="$activity/xpdExt:AdHocTaskConfiguration">			
		        <xsl:if test="$activity/xpdl2:Loop">
		        <!-- Comma separate markers if there are multiple markers -->
				<xsl:text>, </xsl:text>
				</xsl:if>				
				<xsl:value-of select="java:get($msgL10n, 'ActivityMarkerADHOC_label')"/>					
		</xsl:if>
	</xsl:variable>
	<xsl:if test="$marker != ''">
		<tr>
			<th class="heading">Activity Marker</th><td><xsl:value-of select="$marker" />&nbsp;</td>
		</tr>
	</xsl:if>
	
	<!-- 
		Participants
	-->
	<xsl:if test="$showParticipants = 'true'">
		<xsl:variable name="perfs" select="$activity/xpdl2:Performer | $activity/xpdl2:Performers/xpdl2:Performer"/>
		<xsl:if test="$perfs">
			<tr>
				<th class="heading"><xsl:value-of select="java:get($msgL10n, 'Participants_label')"/></th>
				<td>
					
					<xsl:for-each select="$perfs">
						<xsl:variable name="performerId" select="."/> 
	
						<xsl:if test="position() > 1">
							<xsl:text>,</xsl:text>&nbsp;
						</xsl:if>

						<xsl:choose>
							<xsl:when test="$participants[@Id = $performerId]">
								<a href="#{$performerId}"><xsl:value-of select="$participants[@Id = $performerId]/@xpdExt:DisplayName"/></a>
							</xsl:when>
							<xsl:otherwise>
								<xsl:variable name="dataName" select="java:getDataName($javaPkgInfo, $process/@Id, $performerId, 'false')"/>
								<xsl:call-template name="OutputDataHyperLink"><xsl:with-param name="dataContainerId" select="$process/@Id"/><xsl:with-param name="dataName" select="$dataName"/></xsl:call-template>
							</xsl:otherwise>

						</xsl:choose>
						
					</xsl:for-each>
					&nbsp;
				</td>
			</tr>
		</xsl:if>
	</xsl:if>

	<xsl:choose>
		<!-- 
			If reference task then include reference
		-->
		<xsl:when test="$activity/xpdl2:Implementation/xpdl2:Reference">
			<!-- Get referred activity id -->
			<xsl:variable name="refId" select="$activity/xpdl2:Implementation/xpdl2:Reference/@ActivityId" />
			<!-- If this is a valid reference then include reference link-->
			<xsl:variable name="refActivity" select="$allActs[@Id = $refId]" />
			<tr>
				<th class="heading">Referenced Task</th>
				<td>
					<xsl:choose>
						<xsl:when test="$refActivity">
							<xsl:call-template name="getActivityHyperLink"><xsl:with-param name="act" select="$refActivity"/></xsl:call-template>
						</xsl:when>
						<xsl:otherwise><i><xsl:value-of select="java:get($msgL10n, 'Unspecified_label')"/></i></xsl:otherwise>
					</xsl:choose>
					&nbsp;
				</td>
			</tr>
		</xsl:when>
		
		<!--
			If sub-process task then include sub-process link
		-->
		<xsl:when test="$activity/xpdl2:Implementation/xpdl2:SubFlow">
			<xsl:variable name="subProcOrIfcId" select="$activity/xpdl2:Implementation/xpdl2:SubFlow/@Id"/>
			<xsl:variable name="subProcInvocationMode" select="$activity/xpdl2:Implementation/xpdl2:SubFlow/@xpdExt:AsyncExecutionMode"/>
			
			<!-- Get referenced process / interface information, this is returned in the format...
					process|interface|blank\n   (Type of object referenced)
					name\n                      (Including package name if external reference)
					RuntimeIdentifierField\n    (Blank if process reference).
					href                        (html href id for process). 
			-->
			<xsl:variable name="subInfo" select="java:getSubFlowReferenceInfo($javaPkgInfo, $process/@Id, $activity/@Id)"/>
			
			<xsl:variable name="type" select="substring-before($subInfo, '&#10;')"/>
			<xsl:variable name="rem1" select="substring-after($subInfo, '&#10;')"/>

			<xsl:variable name="name" select="substring-before($rem1, '&#10;')"/>
			<xsl:variable name="rem2" select="substring-after($rem1, '&#10;')"/>

			<xsl:variable name="runtimeIdentifier" select="substring-before($rem2, '&#10;')"/>
			
			<xsl:variable name="href" select="substring-after($rem2, '&#10;')"/>
			
			<tr>
				<th class="heading">
					<xsl:choose>
						<xsl:when test="$type = 'interface'"><xsl:value-of select="java:get($msgL10n, 'SubProcessInterface_label')"/></xsl:when>
						<xsl:otherwise><xsl:value-of select="java:get($msgL10n, 'SubProcess_label')"/></xsl:otherwise>
					</xsl:choose>
				</th>
				<td>
				<table>
					<tbody>
						<xsl:choose>
							<xsl:when test="$type = 'process'">
								<tr>
									<th class="heading"><xsl:value-of select="java:get($msgL10n, 'Invoke_SubProcess_label')"/></th>
									<td colspan="3">
										<a href="{$href}"><xsl:value-of select="$name"/></a>											
									</td>
								</tr>
							</xsl:when>
							<xsl:when test="$type = 'interface'">
								<tr>
									<th class="heading"><xsl:value-of select="java:get($msgL10n, 'Invoke_SubProcessInterface_label')"/></th>
									<td colspan="3">
										<a href="{$href}"><xsl:value-of select="$name"/></a>										
									</td>
								</tr>
								<tr>
									<th class="heading"><xsl:value-of select="java:get($msgL10n, 'RuntimeIdentifier_label')"/></th>
									<td colspan="3">
										<xsl:choose>
											<xsl:when test="$runtimeIdentifier != ''">
												<xsl:call-template name="OutputDataHyperLink">
													<xsl:with-param name="dataContainerId" select="$process/@Id"/>
													<xsl:with-param name="dataName" select="$runtimeIdentifier"/>
												</xsl:call-template>
											</xsl:when>
											<xsl:otherwise><i><xsl:value-of select="java:get($msgL10n, 'Unspecified_label')"/></i></xsl:otherwise>
										</xsl:choose>										
									</td>
								</tr>
							</xsl:when>
							<xsl:otherwise>
								<tr>
									<th class="heading"><xsl:value-of select="java:get($msgL10n, 'Invoke_SubProcess_label')"/></th>
									<td colspan="3">
										<i><xsl:value-of select="java:get($msgL10n, 'Unspecified_label')"/></i>									
									</td>
								</tr>
							</xsl:otherwise>
						</xsl:choose>
					
						<tr>
							<th class="heading"><xsl:value-of select="java:get($msgL10n, 'Invocation_Type_Label')"/></th>
							<td colspan="3">
								<xsl:choose>
									<xsl:when test="$subProcInvocationMode = 'Detached'">
										<xsl:value-of select="java:get($msgL10n, 'AsynchronousDetached_Label')"/>
									</xsl:when>
									<xsl:when test="$subProcInvocationMode = 'Attached'">
										<xsl:value-of select="java:get($msgL10n, 'AsynchronousAttached_Label')"/>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="java:get($msgL10n, 'Synchronous_Label')"/>	
									</xsl:otherwise>	
								</xsl:choose>								
							</td>
						</tr>
					</tbody>
				</table>
					
				&nbsp;
				</td>				
			</tr>
		
			<!-- Output sub-process parameter mappings if implementation detail required. -->
			<xsl:if test="$showImplementations = 'true' and $subProcOrIfcId != '' 
					and ($activity/xpdl2:Implementation/xpdl2:SubFlow/xpdl2:DataMappings/xpdl2:DataMapping or $activity/xpdExt:ScriptInformation)">
				<tr>
					<th class="heading"><xsl:value-of select="java:get($msgL10n, 'ParameterMappings_label')"/></th>
					<td>
						<table>
							<thead>
								<tr>
									<th><xsl:value-of select="java:get($msgL10n, 'CallingProcessData_label')"/></th>
									<th><xsl:value-of select="java:get($msgL10n, 'ParameterDirection_label')"/></th>
									<xsl:choose>
										<xsl:when test="$type = 'interface'">
											<th><xsl:value-of select="java:get($msgL10n, 'InterfaceParameter_label')"/></th>
										</xsl:when>
										<xsl:otherwise>
											<th><xsl:value-of select="java:get($msgL10n, 'SubProcessParameter_label')"/></th>
										</xsl:otherwise>
									</xsl:choose>
								</tr>
							</thead>
							<tbody>
								<xsl:for-each select="$activity/xpdl2:Implementation/xpdl2:SubFlow/xpdl2:DataMappings/xpdl2:DataMapping">
									<xsl:sort select="@Direction" data-type="text"/>
									
									<tr>
										<!-- Calling process data... -->
										<td>
											<xsl:choose>
												<xsl:when test="@Direction = 'IN'">
													<xsl:choose>
														<xsl:when test="xpdExt:ScriptInformation">
															<!-- mapping from script to sub-proc param. -->
															<a class="highlightText" style="white-space: nowrap;"><xsl:value-of select="xpdl2:Actual/@ScriptGrammar"/><xsl:text>: </xsl:text><xsl:value-of select="xpdExt:ScriptInformation/@Name"/></a>
															<br/>
															<xsl:call-template name="newline2br">
																<xsl:with-param name="input" select="xpdl2:Actual/text()"/>
																<xsl:with-param name="indent" select="1"/>
															</xsl:call-template>																																
														</xsl:when>
														<xsl:otherwise>
															<!-- mapping from local data to sub-proc param. -->
															<xsl:call-template name="OutputDataHyperLink">
																<xsl:with-param name="dataContainerId" select="$process/@Id"/>
																<xsl:with-param name="dataName" select="xpdl2:Actual/text()"/>
															</xsl:call-template>																
														</xsl:otherwise>
													</xsl:choose>
												</xsl:when>
												<xsl:otherwise>
													<!-- Output parameters. -->
													<xsl:call-template name="OutputDataHyperLink">
														<xsl:with-param name="dataContainerId" select="$process/@Id"/>
														<xsl:with-param name="dataName" select="xpdl2:Actual/text()"/>
													</xsl:call-template>																
												</xsl:otherwise>
											</xsl:choose>
										</td>
										
										<!-- Direction -->
										<td style="white-space: nowrap;">
											<xsl:call-template name="getInOutModeText"><xsl:with-param name="mode" select="@Direction"/></xsl:call-template>
										</td>
										
										<!-- Sub-process / interface param. -->
										<td>
											<xsl:choose>
												<xsl:when test="@Direction = 'IN'">
													<xsl:call-template name="OutputDataHyperLink">
														<xsl:with-param name="dataContainerId" select="$subProcOrIfcId"/>
														<xsl:with-param name="dataName" select="@Formal"/>
													</xsl:call-template>											
												</xsl:when>
												<xsl:otherwise>
													<!-- output mapping. -->
													<xsl:choose>
														<xsl:when test="xpdExt:ScriptInformation">
															<!-- Mapping from a script. -->
															<a class="highlightText" style="white-space: nowrap;"><xsl:value-of select="xpdl2:Actual/@ScriptGrammar"/><xsl:text>: </xsl:text><xsl:value-of select="xpdExt:ScriptInformation/@Name"/></a>
															<br/>
															<xsl:call-template name="newline2br">
																<xsl:with-param name="input" select="xpdExt:ScriptInformation/xpdExt:Expression/text()"/>
																<xsl:with-param name="indent" select="1"/>
															</xsl:call-template>																																
														</xsl:when>
														<xsl:otherwise>
															<xsl:call-template name="OutputDataHyperLink">
																<xsl:with-param name="dataContainerId" select="$subProcOrIfcId"/>
																<xsl:with-param name="dataName" select="@Formal"/>
															</xsl:call-template>											
														</xsl:otherwise>
													</xsl:choose>
												</xsl:otherwise>
												
											</xsl:choose>
										</td>
									</tr>
								</xsl:for-each> <!-- next dataMapping -->
								
								<xsl:if test="$activity/xpdExt:ScriptInformation">
									<!-- Unmapped scripts... -->
									<th colspan="3"><xsl:value-of select="java:get($msgL10n, 'UnmappedScripts_label')"/></th>
									
									<xsl:for-each select="$activity/xpdExt:ScriptInformation">
										<tr>
											<!-- Input side of mappings... -->
											<td>
												<xsl:if test="@Direction = 'IN' or not(@Direction)">
													<a class="highlightText" style="white-space: nowrap;"><xsl:value-of select="xpdExt:Expression/@ScriptGrammar"/><xsl:text>: </xsl:text><xsl:value-of select="xpdExt:ScriptInformation/@Name"/></a>
													<br/>
													<xsl:call-template name="newline2br">
														<xsl:with-param name="input" select="xpdExt:Expression/text()"/>
														<xsl:with-param name="indent" select="1"/>
													</xsl:call-template>																																
												</xsl:if>
												&nbsp;
											</td>
											
											<td style="white-space: nowrap;">
												<xsl:choose>
													<xsl:when test="@Direction = 'OUT'">
														<xsl:call-template name="getInOutModeText"><xsl:with-param name="mode" select="'OUT'"/></xsl:call-template>														
													</xsl:when>
													<xsl:otherwise>
														<xsl:call-template name="getInOutModeText"><xsl:with-param name="mode" select="'IN'"/></xsl:call-template>														
													</xsl:otherwise>
												</xsl:choose>
											</td>

											<!-- Output side of mappings... -->
											<td>
												<xsl:if test="@Direction = 'OUT'">
													<a class="highlightText" style="white-space: nowrap;"><xsl:value-of select="xpdExt:Expression/@ScriptGrammar"/><xsl:text>: </xsl:text><xsl:value-of select="xpdExt:ScriptInformation/@Name"/></a>
													<br/>
													<xsl:call-template name="newline2br">
														<xsl:with-param name="input" select="xpdExt:Expression/text()"/>
														<xsl:with-param name="indent" select="1"/>
													</xsl:call-template>																																
												</xsl:if>
												&nbsp;
											</td>
											
										</tr>
									</xsl:for-each>
								</xsl:if>
								
							</tbody>
						</table>
					</td>
				</tr>
			
			</xsl:if>
			
		</xsl:when>
		
		<!-- 
			Tasks
		-->
		<xsl:when test="$activity/xpdl2:Implementation/xpdl2:Task">
			<xsl:variable name="task" select="$activity/xpdl2:Implementation/xpdl2:Task"/>
			<xsl:variable name="taskType" select="$task/*"/>
			
			<xsl:choose>
				<!-- Service Task (+ Send and Receive WebService -->
				<xsl:when test="$task/xpdl2:TaskService | $task/xpdl2:TaskSend | $task/xpdl2:TaskReceive">
					<xsl:choose>
						<!-- Web Service -->
						<xsl:when test="$taskType/@xpdExt:ImplementationType = 'WebService'">
							<tr>
								<th class="heading"><xsl:value-of select="java:get($msgL10n, 'TaskServiceType_label')"/></th>
								<td>
									<xsl:choose>
										<xsl:when test="$showImplementations = 'true'">
											<xsl:call-template name="outputWebService">
												<xsl:with-param name="activityType" select="'task'"/>
												<xsl:with-param name="webSvcOperation" select="$taskType/xpdl2:WebServiceOperation"/>
												<xsl:with-param name="portTypeOperation" select="$taskType/xpdExt:PortTypeOperation"/>
												<xsl:with-param name="title"><xsl:value-of select="java:get($msgL10n, 'WebService_label')"/></xsl:with-param>
											</xsl:call-template>
										</xsl:when>
										<xsl:otherwise><xsl:value-of select="java:get($msgL10n, 'WebService_label')"/></xsl:otherwise>
									</xsl:choose>
								</td>
							</tr>
						</xsl:when>
						
						<!-- REST Service -->
						<xsl:when test="$taskType/@xpdExt:ImplementationType = 'RESTService'">
							<tr>
								<th class="heading"><xsl:value-of select="java:get($msgL10n, 'TaskServiceType_label')"/></th>
								<td>
									<xsl:choose>
										<xsl:when test="$showImplementations = 'true'">
											<xsl:call-template name="outputRestService">
												<xsl:with-param name="activityId" select="$activity/@Id"/>
												<xsl:with-param name="processId" select="$process/@Id"/>																							
												<xsl:with-param name="title"><xsl:value-of select="java:get($msgL10n, 'RestService_label')"/></xsl:with-param>
											</xsl:call-template>
										</xsl:when>
										<xsl:otherwise><xsl:value-of select="java:get($msgL10n, 'RestService_label')"/></xsl:otherwise>
									</xsl:choose>
								</td>
							</tr>
						</xsl:when>

						<xsl:when test="$taskType/@xpdExt:ImplementationType = 'BW Service'">
							<tr>
								<th class="heading"><xsl:value-of select="java:get($msgL10n, 'TaskServiceType_label')"/></th>
								<td>
									<xsl:choose>
										<xsl:when test="$showImplementations = 'true'">
											<xsl:call-template name="outputWebService">
												<xsl:with-param name="activityType" select="'event'"/>
												<xsl:with-param name="webSvcOperation" select="$taskType/xpdl2:WebServiceOperation"/>
												<xsl:with-param name="portTypeOperation" select="$taskType/xpdExt:PortTypeOperation"/>
												<xsl:with-param name="title"><xsl:value-of select="java:get($msgL10n, 'BWService_label')"/></xsl:with-param>
											</xsl:call-template>
		
											<xsl:if test="$taskType/xpdl2:WebServiceOperation/@xpdExt:Alias != ''">
												<table>
													<tbody>
														<tr><th class="heading">Service Alias</th><td><xsl:value-of select="$taskType/xpdl2:WebServiceOperation/@xpdExt:Alias"/></td></tr>
													</tbody>
												</table>
											</xsl:if>
										</xsl:when>
										<xsl:otherwise>BW Service</xsl:otherwise>
									</xsl:choose>
								</td>
							</tr>
						</xsl:when>
						
						<!-- EMail -->
						<xsl:when test="$taskType/@xpdExt:ImplementationType = 'E-Mail'">
							<xsl:variable name="email" select="$taskType/email:Email"/>
							<tr>
								<th class="heading"><xsl:value-of select="java:get($msgL10n, 'TaskServiceType_label')"/></th>
								<td>
									<xsl:choose>
										<xsl:when test="$showImplementations = 'true'">
											<table>
												<thead>
													<tr>
														<th class="sectionTitle" colspan="2"><xsl:value-of select="'Send eMail'"/></th>
													</tr>
												</thead>
												<tbody>
													<tr><th class="heading">To</th><td><xsl:value-of select="$email/email:Definition/email:To"/>&nbsp;</td></tr>
													<tr><th class="heading">Subject</th><td><xsl:value-of select="$email/email:Definition/email:Subject"/>&nbsp;</td></tr>
													<tr><th class="heading">Body</th>
													<td><xsl:call-template name="newline2br"><xsl:with-param name="input" select="$email/email:Body"/></xsl:call-template>&nbsp;</td></tr>
												</tbody>
											</table>
										</xsl:when>
										<xsl:otherwise>Send eMail</xsl:otherwise>
									</xsl:choose>
								</td>
							</tr>
						</xsl:when>
						
						<!-- Database -->
						<xsl:when test="$taskType/@xpdExt:ImplementationType = 'Database'">
							<xsl:variable name="database" select="$taskType/database:Database"/>
							<tr>
								<th class="heading"><xsl:value-of select="java:get($msgL10n, 'TaskServiceType_label')"/></th>
								<td>
									<xsl:choose>
										<xsl:when test="$showImplementations = 'true'">
											<table>
												<thead>
													<tr>
														<th class="sectionTitle" colspan="2"><xsl:value-of select="'Database Operation'"/></th>
													</tr>
												</thead>
												<tbody>
													<tr><th class="heading">Operation</th><td><xsl:value-of select="$database/database:Operation/database:Sql"/>&nbsp;</td></tr>
													<tr><th class="heading">Database</th><td><xsl:value-of select="$database/database:Schema"/>&nbsp;</td></tr>
													<tr><th class="heading">Server</th><td><xsl:value-of select="$database/database:Server"/>&nbsp;</td></tr>
												</tbody>
											</table>
										</xsl:when>
										<xsl:otherwise>Database Operation</xsl:otherwise>
									</xsl:choose>
								</td>
							</tr>
						</xsl:when>

						<!-- Java -->
						<xsl:when test="$taskType/@xpdExt:ImplementationType = 'Java'">
							<xsl:variable name="eaiJava" select="$taskType/ej:EAIJava"/>
							<tr>
								<th class="heading"><xsl:value-of select="java:get($msgL10n, 'TaskServiceType_label')"/></th>
								<td>
									<xsl:choose>
										<xsl:when test="$showImplementations = 'true'">
											<table>
												<thead>
													<tr>
														<th class="sectionTitle" colspan="2"><xsl:value-of select="'Java Operation'"/></th>
													</tr>
												</thead>
												<tbody>
													<tr><th class="heading">Project</th><td><xsl:value-of select="$eaiJava/@project"/>&nbsp;</td></tr>
													<tr><th class="heading">Class</th><td><xsl:value-of select="$eaiJava/ej:Pojo/@name"/>&nbsp;</td></tr>
													<tr><th class="heading">Method</th><td><xsl:value-of select="$eaiJava/ej:Pojo/ej:Method/@name"/>&nbsp;</td></tr>
													<xsl:if test="$eaiJava/ej:Factory/@name  != ''">
														<tr><th class="heading">Factory Class</th><td><xsl:value-of select="$eaiJava/ej:Factory/@name"/>&nbsp;</td></tr>
														<tr><th class="heading">Factory Method</th><td><xsl:value-of select="$eaiJava/ej:Factory/ej:Method/@name"/>&nbsp;</td></tr>
													</xsl:if>
												</tbody>
											</table>
										</xsl:when>
										<xsl:otherwise>Java Operation</xsl:otherwise>
									</xsl:choose>
								</td>
							</tr>
						</xsl:when>

						<!-- Anything else with an implementation -->
						<xsl:when test="$taskType/@xpdExt:ImplementationType != 'Unspecified' and $taskType/@xpdExt:ImplementationType != ''">
							<tr>
								<th class="heading"><xsl:value-of select="java:get($msgL10n, 'TaskServiceType_label')"/></th>
								<td><xsl:value-of select="$taskType/@xpdExt:ImplementationType"/></td>
							</tr>
						</xsl:when>
					</xsl:choose>
				</xsl:when>
				<!-- END Of Service / Send / Receive task -->

				<!-- Script task -->
				<xsl:when test="$task/xpdl2:TaskScript and $showImplementations = 'true'">
					<xsl:if test="$task/xpdl2:TaskScript/xpdl2:Script != ''">
						<tr>
							<th class="heading">Script (<xsl:value-of select="$task/xpdl2:TaskScript/xpdl2:Script/@ScriptGrammar"/>)</th>
							<td>
								<xsl:call-template name="newline2br"><xsl:with-param name="input" select="$task/xpdl2:TaskScript/xpdl2:Script"/></xsl:call-template>
							</td>
						</tr>
					</xsl:if>
				</xsl:when>

				<!-- User task -->
				<xsl:when test="$task/xpdl2:TaskUser">
					<xsl:if test="$activity/xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'bpmJspTask']/@Value != ''  and $showImplementations = 'true'">
						<tr>
							<th class="heading">Form URL</th>
							<td><xsl:value-of select="$activity/xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'bpmJspTask']/@Value"/>&nbsp;</td>
						</tr>
					</xsl:if>

					<xsl:if test="$task/xpdl2:TaskUser/xpdExt:UserTaskScripts and $showImplementations = 'true'">
						<tr>
							<th class="heading"><xsl:value-of select="java:get($msgL10n, 'TaskScripts_label')"/></th>
							<td>
								<table>
									<thead>
										<tr><th class="heading"><xsl:value-of select="java:get($msgL10n, 'TaskScriptExecutionTypeTitle_label')"/></th><th><xsl:value-of select="java:get($msgL10n, 'ScriptTitle_label')"/></th></tr>
									</thead>
									<tbody>
										<xsl:if test="$task/xpdl2:TaskUser/xpdExt:UserTaskScripts/xpdExt:OpenScript != ''">
											<tr>
												<th class="heading"><xsl:value-of select="java:get($msgL10n, 'TaskScriptExecutionTypeOpen_label')"/> (<xsl:value-of select="$task/xpdl2:TaskUser/xpdExt:UserTaskScripts/xpdExt:OpenScript/@ScriptGrammar"/>)</th>
												<td><xsl:call-template name="newline2br"><xsl:with-param name="input" select="$task/xpdl2:TaskUser/xpdExt:UserTaskScripts/xpdExt:OpenScript"/></xsl:call-template></td>
											</tr>
										</xsl:if>
										<xsl:if test="$task/xpdl2:TaskUser/xpdExt:UserTaskScripts/xpdExt:CloseScript != ''">
											<tr>
												<th class="heading"><xsl:value-of select="java:get($msgL10n, 'TaskScriptExecutionTypeClose_label')"/> (<xsl:value-of select="$task/xpdl2:TaskUser/xpdExt:UserTaskScripts/xpdExt:CloseScript/@ScriptGrammar"/>)</th>
												<td><xsl:call-template name="newline2br"><xsl:with-param name="input" select="$task/xpdl2:TaskUser/xpdExt:UserTaskScripts/xpdExt:CloseScript"/></xsl:call-template></td>
											</tr>
										</xsl:if>
										<xsl:if test="$task/xpdl2:TaskUser/xpdExt:UserTaskScripts/xpdExt:SubmitScript != ''">
											<tr>
												<th class="heading"><xsl:value-of select="java:get($msgL10n, 'TaskScriptExecutionTypeSubmit_label')"/> (<xsl:value-of select="$task/xpdl2:TaskUser/xpdExt:UserTaskScripts/xpdExt:SubmitScript/@ScriptGrammar"/>)</th>
												<td><xsl:call-template name="newline2br"><xsl:with-param name="input" select="$task/xpdl2:TaskUser/xpdExt:UserTaskScripts/xpdExt:SubmitScript"/></xsl:call-template></td>
											</tr>
										</xsl:if>
									</tbody>
								</table>
							</td>
						</tr>
					</xsl:if>
					
				</xsl:when>
			</xsl:choose>
			
		</xsl:when>
		<!-- End of Activity type Task -->
	</xsl:choose>
	
</xsl:template>


<!--
===================================================================================================================
	Output the Activity Interface Information...
===================================================================================================================
-->
<xsl:template name="OutputActivityInterface">
	<xsl:param name="dataContainer"/>
	<xsl:param name="actOrMethod"/>
	
	<xsl:if test="$showActivityInterfaces = 'true'">
		<tr>
			<th class="heading">
				<xsl:choose>
					<xsl:when test="local-name($actOrMethod) = 'StartMethod' or local-name($actOrMethod) = 'IntermediateMethod' or
											$actOrMethod/xpdl2:Event"><xsl:value-of select="java:get($msgL10n, 'EventInterface_label')"/></xsl:when>
					<xsl:otherwise><xsl:value-of select="java:get($msgL10n, 'TaskInterface_label')"/></xsl:otherwise>
				</xsl:choose>
			</th>
			<td>
				<table>
					<tbody>
						<tr>
							<th class="heading"><xsl:value-of select="java:get($msgL10n, 'Visibility_label')"/></th>
							<td colspan="3">
								<xsl:choose>
									<xsl:when test="$actOrMethod/@xpdExt:Visibility = 'Public'"><xsl:value-of select="java:get($msgL10n, 'Public_label')"/></xsl:when>
									<xsl:otherwise><xsl:value-of select="java:get($msgL10n, 'Private_label')"/></xsl:otherwise>
								</xsl:choose>
							</td>
						</tr>

						<tr style="height: 10px;"><td/><td/></tr>
						
						<tr><th class="heading"><xsl:value-of select="java:get($msgL10n, 'AssociatedData_label')"/></th><th><xsl:value-of select="java:get($msgL10n, 'ParameterDirection_label')"/></th><th><xsl:value-of select="java:get($msgL10n, 'Mandatory_label')"/></th><th style="width: 60%;"><xsl:value-of select="java:get($msgL10n, 'Description_label')"/></th></tr>

						<xsl:choose>
							<xsl:when test="contains(local-name($actOrMethod), 'Method') or $actOrMethod/@xpdExt:Implements != ''">
								<!-- Output interface method derived data associations for interface events or process events that implement interface events. -->
								
								<!-- Get a list of newline separated interface method associated parameters that we can pass back to java to get info on. -->
								<xsl:variable name="methodAssociations" select="java:getInterfaceMethodDataAssociations($javaPkgInfo, $dataContainer/@Id, $actOrMethod/@Id)"/>
							
								<xsl:choose>
									<xsl:when test="$methodAssociations = $implicitAssociationDisabledTag">
										<tr><td colspan="4"><i><xsl:value-of select="java:get($msgL10n, 'NoInterfaceParams_label')"/></i></td></tr>
									</xsl:when>
									<xsl:when test="$methodAssociations = ''">
										<tr><td colspan="4"><i><xsl:value-of select="java:get($msgL10n, 'AllApplicableInterfaceParams_label')"/></i></td></tr>
									</xsl:when>
									<xsl:otherwise>
										<!-- Output the interface method derived associations. -->
										<xsl:call-template name="OutputAssociatedData">
											<xsl:with-param name="dataContainer" select="$dataContainer"/>
											<xsl:with-param name="actOrMethod" select="$actOrMethod"/>
											<xsl:with-param name="associations" select="$methodAssociations"/>
										</xsl:call-template>
									</xsl:otherwise>
								</xsl:choose>
								
								<!-- Then for process events add the locally defined extra data associations. -->
								<xsl:if test="local-name($actOrMethod) = 'Activity'">
									<!-- Get a list of newline separated process event associated parameters that we can pass back to java to get info on. -->
									<xsl:variable name="eventAssociations" select="java:getActivityDataAssociations($javaPkgInfo, $dataContainer/@Id, $actOrMethod/@Id)"/>
									<xsl:if test="$eventAssociations">
										<xsl:call-template name="OutputAssociatedData">
											<xsl:with-param name="dataContainer" select="$dataContainer"/>
											<xsl:with-param name="actOrMethod" select="$actOrMethod"/>
											<xsl:with-param name="associations" select="$eventAssociations"/>
										</xsl:call-template>
									</xsl:if>
								</xsl:if>
							
							</xsl:when>
							
							<xsl:otherwise>
								<!-- Output non-method-implementing Event / Task data associations -->
								<xsl:variable name="actAssociations" select="java:getActivityDataAssociations($javaPkgInfo, $dataContainer/@Id, $actOrMethod/@Id)"/>
								
								<xsl:choose>
									<xsl:when test="$actAssociations = $implicitAssociationDisabledTag">
										<tr>
											<td colspan="4">
												<i>
													<xsl:choose>
														<xsl:when test="java:isProcessAPIActivity($javaPkgInfo, $dataContainer/@Id, $actOrMethod/@Id)"><xsl:value-of select="java:get($msgL10n, 'NoProcessParams_label')"/></xsl:when>
														<xsl:otherwise><xsl:value-of select="java:get($msgL10n, 'NoProcessData_label')"/></xsl:otherwise>
													</xsl:choose>
												</i>
											</td>
										</tr>
									</xsl:when>
									<xsl:when test="$actAssociations">
										<xsl:call-template name="OutputAssociatedData">
											<xsl:with-param name="dataContainer" select="$dataContainer"/>
											<xsl:with-param name="actOrMethod" select="$actOrMethod"/>
											<xsl:with-param name="associations" select="$actAssociations"/>
										</xsl:call-template>
									</xsl:when>
									<xsl:otherwise>
										<!-- for non-method-implementing the no associations means ALL data -->
										<tr>
											<td colspan="4">
												<i>
													<xsl:choose>
														<xsl:when test="java:isProcessAPIActivity($javaPkgInfo, $dataContainer/@Id, $actOrMethod/@Id)"><xsl:value-of select="java:get($msgL10n, 'AllApplicableProcessParams_label')"/></xsl:when>
														<xsl:otherwise><xsl:value-of select="java:get($msgL10n, 'AllApplicableData_label')"/></xsl:otherwise>
													</xsl:choose>
												</i>
											</td>
										</tr>
									</xsl:otherwise>
								</xsl:choose>
								
							</xsl:otherwise>
						</xsl:choose>

					</tbody>
				</table>
			</td>
		</tr>
	
	</xsl:if>
</xsl:template>

<!--
===================================================================================================================
	Output a activity-interface associated data table row
	- This method is recursive, taking the next newline separated 'id' (that java will recognise) and outputting the info for it.
===================================================================================================================
-->
<xsl:template name="OutputAssociatedData">
	<xsl:param name="dataContainer"/>
	<xsl:param name="actOrMethod"/>
	<xsl:param name="associations"/>
	
	<xsl:if test="$associations != ''">
		<xsl:variable name="id" select="substring-before($associations, '&#10;')"/>

		<!-- The following call will return...
				DataName\n
				DirectionMode\n
				MandatoryFlag\n
				Description\n
		-->
		<xsl:variable name="dataInfo" select="java:getAssociatedDataInfo($javaPkgInfo, $dataContainer/@Id, $actOrMethod/@Id, $id)"/>
		
		<!-- Extract the individual bits. -->
		<xsl:variable name="dataName" select="substring-before($dataInfo, '&#10;')"/>
		<xsl:variable name="remainder1" select="substring-after($dataInfo, '&#10;')"/>

		<xsl:variable name="direction" select="substring-before($remainder1, '&#10;')"/>
		<xsl:variable name="remainder2" select="substring-after($remainder1, '&#10;')"/>

		<xsl:variable name="mandatory" select="substring-before($remainder2, '&#10;')"/>
		<xsl:variable name="description" select="substring-after($remainder2, '&#10;')"/>
		
		<tr>
			<td>
				<xsl:call-template name="OutputDataHyperLink"><xsl:with-param name="dataContainerId" select="$dataContainer/@Id"/><xsl:with-param name="dataName" select="$dataName"/></xsl:call-template>
				&nbsp;
			</td>
			<td style="white-space: nowrap;"><xsl:call-template name="getInOutModeText"><xsl:with-param name="mode" select="$direction"/></xsl:call-template>&nbsp;</td>
			<td><xsl:if test="$mandatory = 'true'"><img alt="Yes" src="images/tick.gif"/></xsl:if>&nbsp;</td>
			<td>
				<xsl:call-template name="OutputDescription">
					<xsl:with-param name="descText" select="$description"/>
					<xsl:with-param name="docURL" select="''"/>
				</xsl:call-template>
				&nbsp;
			</td>
		</tr>
	
		<!-- Recurs and handle the remainder of the associated data ids... -->

		<xsl:call-template name="OutputAssociatedData">
			<xsl:with-param name="dataContainer" select="$dataContainer"/>
			<xsl:with-param name="actOrMethod" select="$actOrMethod"/>
			<xsl:with-param name="associations" select="substring-after($associations, '&#10;')"/>
		</xsl:call-template>		
	</xsl:if>
	
</xsl:template>

<!--
===================================================================================================================
	Output descriptive text for formal / associated data Mode (in/inout/out).
===================================================================================================================
-->
<xsl:template name="getInOutModeText">
	<xsl:param name="mode"/>
	<xsl:choose>
		<xsl:when test="$mode = 'IN'"><xsl:value-of select="java:get($msgL10n, 'Input_label')"/></xsl:when>
		<xsl:when test="$mode = 'INOUT'"><xsl:value-of select="java:get($msgL10n, 'InputOutput_label')"/></xsl:when>
		<xsl:when test="$mode = 'OUT'"><xsl:value-of select="java:get($msgL10n, 'Output_label')"/></xsl:when>
	</xsl:choose>
</xsl:template>

<!--
===================================================================================================================
	getActivityHyperLink = Get the activity name as a hyperlink.  If no name then use a created name instead.
===================================================================================================================
-->
<xsl:template name="getActivityHyperLink">
	<xsl:param name="act"/>
	
	<a href="#{$act/@Id}">
		<xsl:call-template name="getActivityName">
			<xsl:with-param name="act" select="$act"/>
		</xsl:call-template>
	</a>

</xsl:template>
	
<!--
===================================================================================================================
	getActivityName = Get the activity name.  If no name then use a created name instead ('[Element x]').
===================================================================================================================
-->
<xsl:template name="getActivityName">
	<xsl:param name="act" />
	<xsl:param name="noItalics" select="false()"/>
	
	<!-- if no activity name then return created name -->
	
	<xsl:choose>
		<xsl:when test="normalize-space($act/@xpdExt:DisplayName) != ''"><xsl:value-of select="$act/@xpdExt:DisplayName" /></xsl:when>
		<xsl:when test="normalize-space($act/@Name) != ''"><xsl:value-of select="$act/@Name" /></xsl:when>
		<xsl:when test="$act/@xpdExt:Implements != ''">
			<xsl:value-of select="java:getImplementedMethodName($javaPkgInfo, $act/ancestor::xpdl2:WorkflowProcess/@Id, $act/@Id)"/>
		</xsl:when>
		<xsl:when test="$noItalics"><xsl:call-template name="getActivityTypeName"><xsl:with-param name="act" select="$act"/></xsl:call-template></xsl:when>
		<xsl:otherwise><i><xsl:call-template name="getActivityTypeName"><xsl:with-param name="act" select="$act"/></xsl:call-template></i></xsl:otherwise>	
	</xsl:choose>
</xsl:template>

<!--
===================================================================================================================
	getActivityTypeName = Get the activity type name.
===================================================================================================================
-->
<xsl:template name="getActivityTypeName">
	<xsl:param name="act"/>
	
	<xsl:choose>
		<!-- Start Events -->
		<xsl:when test="$act/xpdl2:Event/xpdl2:StartEvent">
			<xsl:choose>
				<xsl:when test="$act/xpdl2:Event/xpdl2:StartEvent/xpdl2:TriggerResultMessage"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeSTARTEVENTMessage_label')"/></xsl:when>
				<xsl:when test="$act/xpdl2:Event/xpdl2:StartEvent/xpdl2:TriggerTimer"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeSTARTEVENTTimer_label')"/></xsl:when>
				<xsl:when test="$act/xpdl2:Event/xpdl2:StartEvent/xpdl2:TriggerConditional"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeSTARTEVENTConditional_label')"/></xsl:when>
				<xsl:when test="$act/xpdl2:Event/xpdl2:StartEvent/xpdl2:TriggerResultSignal"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeSTARTEVENTSignal_label')"/></xsl:when>
				<xsl:when test="$act/xpdl2:Event/xpdl2:StartEvent/xpdl2:TriggerMultiple"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeSTARTEVENTMultiple_label')"/></xsl:when>
				<xsl:otherwise><xsl:value-of select="java:get($msgL10n, 'ActivityTypeSTARTEVENT_label')"/></xsl:otherwise>
			</xsl:choose>
		</xsl:when>

		<!-- Intermediate Events -->
		<xsl:when test="$act/xpdl2:Event/xpdl2:IntermediateEvent">
			<xsl:variable name="catchThrow"><xsl:call-template name="catchThrow"><xsl:with-param name="act" select="$act"/></xsl:call-template></xsl:variable>

			<xsl:choose>
				<xsl:when test="$catchThrow = 'CATCH'">
					<xsl:choose>
						<xsl:when test="$act/xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerResultMessage"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeINTEREVENTCatchMessage_label')"/></xsl:when>
						<xsl:when test="$act/xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerTimer"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeINTEREVENTCatchTimer_label')"/></xsl:when>
						<xsl:when test="$act/xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:ResultError"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeINTEREVENTCatchError_label')"/></xsl:when>
						<xsl:when test="$act/xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerResultCompensation"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeINTEREVENTCatchCompensation_label')"/></xsl:when>
						<xsl:when test="$act/xpdl2:Event/xpdl2:IntermediateEvent/@Trigger = 'Cancel'"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeINTEREVENTCatchCancel_label')"/></xsl:when>
						<xsl:when test="$act/xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerConditional"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeINTEREVENTCatchConditional_label')"/></xsl:when>
						<xsl:when test="$act/xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerResultLink"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeINTEREVENTCatchLink_label')"/></xsl:when>
						<xsl:when test="$act/xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerResultSignal"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeINTEREVENTCatchSignal_label')"/></xsl:when>
						<xsl:when test="$act/xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerIntermediateMultiple"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeINTEREVENTCatchMultiple_label')"/></xsl:when>
						<xsl:otherwise><xsl:value-of select="java:get($msgL10n, 'ActivityTypeINTEREVENTCatch_label')"/></xsl:otherwise>
					</xsl:choose>
					
				</xsl:when>
				<xsl:otherwise>
					<xsl:choose>
						<xsl:when test="$act/xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerResultMessage"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeINTEREVENTMessage_label')"/></xsl:when>
						<xsl:when test="$act/xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerResultCompensation"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeINTEREVENTThrowCompensation_label')"/></xsl:when>
						<xsl:when test="$act/xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerResultSignal"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeINTEREVENTThrowSignal_label')"/></xsl:when>
						<xsl:when test="$act/xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerResultLink"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeINTEREVENTLink_label')"/></xsl:when>
						<xsl:when test="$act/xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerIntermediateMultiple"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeINTEREVENTMultiple_label')"/></xsl:when>
						<xsl:otherwise><xsl:value-of select="java:get($msgL10n, 'ActivityTypeINTEREVENT_label')"/></xsl:otherwise>
					</xsl:choose>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:when>

		<!-- End Events -->
		<xsl:when test="$act/xpdl2:Event/xpdl2:EndEvent">
			<xsl:choose>
				<xsl:when test="$act/xpdl2:Event/xpdl2:EndEvent/xpdl2:TriggerResultMessage"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeENDEVENTMessage_label')"/></xsl:when>
				<xsl:when test="$act/xpdl2:Event/xpdl2:EndEvent/xpdl2:ResultError"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeENDEVENTError_label')"/></xsl:when>
				<xsl:when test="$act/xpdl2:Event/xpdl2:EndEvent/xpdl2:TriggerResultCompensation"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeENDEVENTCompensation_label')"/></xsl:when>
				<xsl:when test="$act/xpdl2:Event/xpdl2:EndEvent/xpdl2:TriggerResultSignal"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeENDEVENTSignal_label')"/></xsl:when>
				<xsl:when test="$act/xpdl2:Event/xpdl2:EndEvent/xpdl2:ResultMultiple"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeENDEVENTMultiple_label')"/></xsl:when>
				<xsl:when test="$act/xpdl2:Event/xpdl2:EndEvent/@Result = 'Cancel'"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeENDEVENTCancel_label')"/></xsl:when>
				<xsl:when test="$act/xpdl2:Event/xpdl2:EndEvent/@Result = 'Terminate'"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeENDEVENTTerminate_label')"/></xsl:when>
				<xsl:otherwise><xsl:value-of select="java:get($msgL10n, 'ActivityTypeENDEVENT_label')"/></xsl:otherwise>
			</xsl:choose>
		</xsl:when>
		
		<!-- Gateways -->
		<xsl:when test="$act/xpdl2:Route">
			<xsl:variable name="gatewayType"><xsl:call-template name="getGatewayType"><xsl:with-param name="act" select="$act"/></xsl:call-template></xsl:variable>
			<xsl:choose>
				<xsl:when test="$gatewayType = 'ExclusiveData'"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeXORDATAGATEWAY_label')"/></xsl:when>
				<xsl:when test="$gatewayType = 'ExclusiveEvent'"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeXOREVENTGATEWAY_label')"/></xsl:when>
				<xsl:when test="$gatewayType = 'Parallel'"><xsl:value-of select="java:get($msgL10n, 'ActivityTypePARALLELGATEWAY_label')"/></xsl:when>
				<xsl:when test="$gatewayType = 'Inclusive'"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeORGATEWAY_label')"/></xsl:when>
				<xsl:when test="$gatewayType = 'Complex'"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeCOMPLEXGATEWAY_label')"/></xsl:when>
			</xsl:choose>
		</xsl:when>

		<!-- Tasks -->
		<xsl:when test="$act/xpdl2:Implementation/xpdl2:No"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeTASK_label')"/></xsl:when>
		<xsl:when test="$act/xpdl2:Implementation/xpdl2:Task/xpdl2:TaskUser"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeUSERTASK_label')"/></xsl:when>
		<xsl:when test="$act/xpdl2:Implementation/xpdl2:Task/xpdl2:TaskManual"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeMANUALTASK_label')"/></xsl:when>
		<xsl:when test="$act/xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeSERVICETASK_label')"/></xsl:when>
		<xsl:when test="$act/xpdl2:Implementation/xpdl2:Task/xpdl2:TaskScript"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeSCRIPTTASK_label')"/></xsl:when>
		<xsl:when test="$act/xpdl2:Implementation/xpdl2:Task/xpdl2:TaskSend"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeSENDTASK_label')"/></xsl:when>
		<xsl:when test="$act/xpdl2:Implementation/xpdl2:Task/xpdl2:TaskReceive"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeRECEIVETASK_label')"/></xsl:when>
		<xsl:when test="$act/xpdl2:Implementation/xpdl2:Reference"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeREFERENCETASK_label')"/></xsl:when>
		<xsl:when test="$act/xpdl2:Implementation/xpdl2:SubFlow"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeINDEPSUBFLOW_label')"/></xsl:when>
		<xsl:when test="$act/xpdl2:BlockActivity/@xpdExt:IsEventSubProcess = 'false'"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeEMBEDSUBFLOW_label')"/></xsl:when>
		<xsl:when test="$act/xpdl2:BlockActivity/@xpdExt:IsEventSubProcess = 'true'"><xsl:value-of select="java:get($msgL10n, 'ActivityTypeEVENTSUBFLOW_label')"/></xsl:when>
		<xsl:otherwise><xsl:value-of select="java:get($msgL10n, 'ActivityTypeTASK_label')"/></xsl:otherwise>
	</xsl:choose>
	
</xsl:template>


<!--
===================================================================================================================
	getDataObjectName = Get the data object name.  If no name then use a created name instead ('[Data Object x]').
===================================================================================================================
-->
<xsl:template name="getDataObjectName">
	<xsl:param name="dataObj" />
	
	<!-- if no data object name then return created name -->
	<xsl:choose>
		<xsl:when test="normalize-space($dataObj/@xpdExt:DisplayName) != ''">
			<xsl:value-of select="$dataObj/@xpdExt:DisplayName" />
		</xsl:when>
		<xsl:when test="normalize-space($dataObj/@Name) != ''">
			<xsl:value-of select="$dataObj/@Name" />
		</xsl:when>
		<xsl:otherwise>
			<i>[<xsl:value-of select="java:get($msgL10n, 'DataObject_label')"/> <xsl:value-of select="count($dataObj/preceding-sibling::xpdl2:Artifact[@ArtifactType = 'DataObject']) + 1" />]</i>
		</xsl:otherwise>	
	</xsl:choose>
</xsl:template>

<!--
===================================================================================================================
	isListedActivity = Outputs "true" if the given activity is one that is included in main activity list.
===================================================================================================================
-->
<xsl:template name="isListedActivity">
	<xsl:param name="act"/>
	
	<!-- 
		Ignore gateways and intermediate link events and attached events - these are handled as part of sequence flow descriptive output. 
	-->
	
	<xsl:choose>
		<xsl:when test="count($act/xpdl2:Route) = 0 and count($act/xpdl2:Event/xpdl2:IntermediateEvent/xpdl2:TriggerResultLink) = 0 and count($act/xpdl2:Event/xpdl2:IntermediateEvent/@Target) = 0">true</xsl:when>
		<xsl:otherwise>false</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!--
===================================================================================================================
	extAttrAsHtml - Copy content of extended attribute value to HTML (i.e. the actual alements and xml tags etc.
===================================================================================================================
-->
<xsl:template name="extAttrAsHtml">
	<xsl:param name="el" />
	<xsl:if test="$el/@Value">
		<xsl:value-of select="@Value" />
	</xsl:if>
	<xsl:if test="$el/*">
		<xsl:if test="$el/@Value">
		<br/>
		</xsl:if>
		<xsl:for-each select="$el/*">
			<xsl:call-template name="elementAsHtml">
				<xsl:with-param name="el" select="." />
			</xsl:call-template>
		</xsl:for-each>
	</xsl:if>
	<xsl:if test="$el/text()">
		<xsl:if test="$el/@Value">
		<br/>
		</xsl:if>
		<xsl:call-template name="newline2br">
			<xsl:with-param name="input" select="$el/text()" />
		</xsl:call-template>
	</xsl:if>
</xsl:template>

<!--
===================================================================================================================
	elementAsHtml - Copy an entire XML element to HTML (i.e. the actual alements and xml tags etc.
===================================================================================================================
-->
<xsl:template name="elementAsHtml">
	<xsl:param name="el"/>
	<xsl:param name="level" select="0"/>

	<xsl:call-template name="indent"><xsl:with-param name="level" select="$level"/></xsl:call-template>

	<!-- Start element -->
	<xsl:text>&amp;lt;</xsl:text><xsl:value-of select="name($el)"/>

	<!-- add any attrs -->
	<xsl:for-each select="$el/@*">
		<xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text><xsl:value-of select="name(.)"/><xsl:text>="</xsl:text><xsl:value-of select="."/><xsl:text>"</xsl:text>
	</xsl:for-each>
	
	<xsl:choose>
		<xsl:when test="$el/*">
			<!-- If there are sub/elements then recurs and output them -->
			<xsl:text>&amp;gt;</xsl:text><br/>
			
			<xsl:for-each select="$el/*">
				<xsl:call-template name="elementAsHtml">
					<xsl:with-param name="el" select="."/>
					<xsl:with-param name="level" select="$level + 1"/>
				</xsl:call-template>
			</xsl:for-each>
			
			<!-- finish element -->
			<xsl:call-template name="indent"><xsl:with-param name="level" select="$level"/></xsl:call-template>
			<xsl:text>&amp;lt;/</xsl:text><xsl:value-of select="name($el)"/><xsl:text>&amp;gt;</xsl:text><br/>
			
		</xsl:when>
		
		<xsl:when test="$el/text()">
			<!-- If there are sub text output it-->
			<xsl:text>&amp;gt;</xsl:text>
			
			<xsl:call-template name="newline2br"><xsl:with-param name="input" select="$el/text()"/></xsl:call-template>
			
			<!-- finish element -->
			<xsl:text>&amp;lt;/</xsl:text><xsl:value-of select="name($el)"/><xsl:text>&amp;gt;</xsl:text><br/>
			
		</xsl:when>
		
		<xsl:otherwise>
			<!-- No children, finish element here. -->
			<xsl:text>/&amp;gt;</xsl:text><br/>
		</xsl:otherwise>
	</xsl:choose>
	
</xsl:template>

<!--
===================================================================================================================
	indent - output indentation to the given level
===================================================================================================================
-->	
<xsl:template name="indent">
	<xsl:param name="level"/>

	<xsl:choose>
		<xsl:when test="$level > 0">
			<xsl:text disable-output-escaping="yes">&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;</xsl:text>
			<xsl:call-template name="indent"><xsl:with-param name="level" select="$level - 1"/></xsl:call-template>
		</xsl:when>
	</xsl:choose>

</xsl:template>

<!--
===================================================================================================================
	Output field/param hyperlkink
===================================================================================================================
-->
<xsl:template name="OutputDataHyperLink">
	<xsl:param name="dataContainerId"/>
	<xsl:param name="dataName"/>

	<!-- ProcDocPackageInfo cannot cope with hyperlinks to data in ext package processes so make sure the conmtainer is local. -->
	<xsl:variable name="localProcOrIfc" select="/xpdl2:Package/xpdl2:WorkflowProcesses/xpdl2:WorkflowProcess[@Id = $dataContainerId] 
								| /xpdl2:Package/xpdExt:ProcessInterfaces/xpdExt:ProcessInterface[@Id = $dataContainerId]"/>
	<xsl:choose>
		<xsl:when test="count($localProcOrIfc) > 0">
			<a>
				<xsl:attribute name="href"><xsl:value-of select="java:getDataHrefFromName($javaPkgInfo, $dataContainerId, $dataName)"/></xsl:attribute>
				<xsl:value-of select="java:getDataDisplayNameFromTokenName($javaPkgInfo, $dataContainerId, $dataName)"/>
			</a>
		</xsl:when>
		<xsl:otherwise>
			<a>
				<xsl:value-of select="$dataName"/>
			</a>
		</xsl:otherwise>
	</xsl:choose>
	
</xsl:template>

<!--
===================================================================================================================
	Output description
===================================================================================================================
-->
<xsl:template name="OutputDescription">
	<xsl:param name="descText"/>
	<xsl:param name="docURL"/>
	
	<xsl:if test="$docURL != ''">
		<xsl:value-of select="java:get($msgL10n, 'Documentation_label')"/>:&nbsp;<a href="{$docURL}"><xsl:value-of select="$docURL"/></a>
	</xsl:if>
	
	<xsl:if test="$docURL != '' and $descText != ''">
		<br/><br/>
	</xsl:if>
	
	<xsl:if test="$descText != ''">
		<xsl:call-template name="newline2br"><xsl:with-param name="input" select="$descText"/></xsl:call-template>
	</xsl:if>

</xsl:template>


<!--
===================================================================================================================
	newline2br - convert newlines in input to <br/> tags
===================================================================================================================
-->
<xsl:template name="newline2br">
	<xsl:param name="input" select="''"/>
	<xsl:param name="indent" select="0"/>

	<xsl:if test="$input != ''">
		<xsl:variable name="line">
			<xsl:choose>
				<xsl:when test="contains($input, '&#10;')"><xsl:value-of select="substring-before($input, '&#10;')"/></xsl:when>
				<xsl:when test="contains($input, '&#xD;')"><xsl:value-of select="substring-before($input, '&#xD;')"/></xsl:when>
				<xsl:otherwise><xsl:value-of select="$input"/></xsl:otherwise>
			</xsl:choose>
		</xsl:variable>

		<xsl:variable name="restOfText">
			<xsl:choose>
				<xsl:when test="contains($input, '&#10;')"><xsl:value-of select="substring-after($input, '&#10;')"/></xsl:when>
				<xsl:when test="contains($input, '&#xD;')"><xsl:value-of select="substring-after($input, '&#xD;')"/></xsl:when>
				<xsl:otherwise></xsl:otherwise>
			</xsl:choose>
		</xsl:variable>


		<!-- output line removing carriage return -->
		<!-- Convert leading space to -->
		<xsl:if test="$indent > 0">
			<xsl:call-template name="indent"><xsl:with-param name="level" select="$indent"/></xsl:call-template> 
		</xsl:if>
		<xsl:call-template name="leadSpace"><xsl:with-param name="input" select="translate($line, '&#13;', '')"/></xsl:call-template>

		<xsl:if test="contains($input, '&#10;')">
			<!-- only output br if original line had one. -->
			<br/>
		</xsl:if>
		
		<xsl:if test="$restOfText != ''">
			<!-- Recurse and do next line of buffer -->
			<xsl:call-template name="newline2br">
				<xsl:with-param name="input" select="$restOfText"/>
				<xsl:with-param name="indent" select="$indent"/>
			</xsl:call-template>
		</xsl:if>

	</xsl:if>
	
</xsl:template> 

<!--
===================================================================================================================
	Output Display Name and optionally token name (if required by the xslt args).
===================================================================================================================
-->
<xsl:template name="getName">
	<xsl:param name="node" select="."/>

	<xsl:choose>
		<xsl:when test="$showTokenName = 'true'">
			<!-- if token name output is required then output "Display Name (TokenName)" (or just "TokenName" if no display name defined). -->
			<xsl:choose>
				<xsl:when test="string-length($node/@xpdExt:DisplayName) > 0">
					<xsl:value-of select="$node/@xpdExt:DisplayName"/>
					<xsl:text> (</xsl:text><xsl:value-of select="$node/@Name"/><xsl:text>)</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$node/@Name"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:when>
		
		<xsl:otherwise>
			<!-- if not outputting token names then just output display name. -->
			<xsl:value-of select="$node/@xpdExt:DisplayName"/>
		</xsl:otherwise>
		
	</xsl:choose>
	
</xsl:template>


<!--
===================================================================================================================
	Get gateway type (have to handle all the different ways that wfmc have deemed top allow gateway type to be defined.
===================================================================================================================
-->
<xsl:template name="getGatewayType">
	<xsl:param name="act"/>

	<xsl:variable name="type">
		<xsl:choose>
			<!-- convert from old type vals to new -->
			<xsl:when test="$act/xpdl2:Route/@GatewayType = 'XOR'">Exclusive</xsl:when>
			<xsl:when test="$act/xpdl2:Route/@GatewayType = 'AND'">Parallel</xsl:when>
			<xsl:when test="$act/xpdl2:Route/@GatewayType = 'OR'">Inclusive</xsl:when>
			<xsl:otherwise><xsl:value-of select="$act/xpdl2:Route/@GatewayType"/></xsl:otherwise>
		</xsl:choose>
	</xsl:variable> 

	<xsl:choose>
		<xsl:when test="$type = 'Exclusive'">
			<!-- Convert between various options for exclusive type -->
			<xsl:variable name="exclusiveType">
				<xsl:choose>
					<xsl:when test="$act/xpdl2:Route/@ExclusiveType"><xsl:value-of select="$act/xpdl2:Route/@ExclusiveType"/></xsl:when>
					<xsl:when test="$act/xpdl2:Route/@XORType"><xsl:value-of select="$act/xpdl2:Route/@XORType"/></xsl:when>
				</xsl:choose>
			</xsl:variable>
			
			<xsl:choose>
				<xsl:when test="$exclusiveType = 'Event'">
					<xsl:text>ExclusiveEvent</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>ExclusiveData</xsl:text>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="$type"/>
		</xsl:otherwise>
	</xsl:choose>
	
</xsl:template>


<!--
===================================================================================================================
	catchThrow - Return CATCH or THROW depending on type of event.,
===================================================================================================================
-->
<xsl:template name="catchThrow">
	<xsl:param name="act"/>
	
	<xsl:choose>
		<xsl:when test="$act/xpdl2:Event/xpdl2:StartEvent">CATCH</xsl:when>
		<xsl:when test="$act/xpdl2:Event/xpdl2:EndEvent">THROW</xsl:when>
		<xsl:when test="$act/xpdl2:Event/xpdl2:IntermediateEvent/child::*[position() = 1]/@CatchThrow = 'THROW' or $act/xpdl2:Event/xpdl2:IntermediateEvent/child::*[position() = 1]/@xpdExt:CatchThrow = 'THROW'">THROW</xsl:when>
		<xsl:otherwise>CATCH</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!--
===================================================================================================================
	leadSpace - Convert leading spaces/tabs to nbsp's
===================================================================================================================
-->
<xsl:template name="leadSpace">
	<xsl:param name="input" select="''"/>

	<xsl:if test="$input != ''">
		<xsl:choose>
			<xsl:when test="starts-with($input, ' ')">&nbsp;<xsl:call-template name="leadSpace"><xsl:with-param name="input" select="substring($input, 2)"/></xsl:call-template></xsl:when>
			<xsl:when test="starts-with($input, '&#9;')">&nbsp;&nbsp;&nbsp;&nbsp;<xsl:call-template name="leadSpace"><xsl:with-param name="input" select="substring($input, 2)"/></xsl:call-template></xsl:when>
			<xsl:otherwise><xsl:value-of select="$input"/></xsl:otherwise>
		</xsl:choose>
	</xsl:if>
	
</xsl:template>

<!--
==============================================
Output the total height of the diagram for given process.
==============================================
-->
<xsl:template name="getDiagramWidth">
	<xsl:param name="process"/>

	<xsl:variable name="poolWidth"><xsl:call-template name="getPoolWidth"><xsl:with-param name="process" select="$process"/></xsl:call-template></xsl:variable>
	
	<xsl:value-of select="$poolWidth + 50"/>
</xsl:template>

<!--
==============================================
Output the total height of the diagram for given process.
==============================================
-->
<xsl:template name="getDiagramHeight">
	<xsl:param name="process"/>
	
	<xsl:variable name="pools" select="/xpdl2:Package/xpdl2:Pools/xpdl2:Pool[@Process = $process/@Id]"/>

	<xsl:choose>
		<xsl:when test="count($pools) > 0">
			<xsl:variable name="toolId">XPD</xsl:variable>
			<xsl:value-of select="sum($pools/xpdl2:Lanes/xpdl2:Lane/xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo[not(@ToolId) or @ToolId = '' or @ToolId = $toolId]/@Height) + (count($pools) * 5) + 50"/>
		</xsl:when>
		<xsl:otherwise>
			<!-- There are no lanes so we can calc the height from lowest (Y * Width/2) object -->
			<xsl:variable name="toolId">XPD</xsl:variable>
			<xsl:variable name="coords" select="$process//xpdl2:NodeGraphicsInfo[not(@ToolId) or @ToolId = '' or @ToolId = $toolId]/xpdl2:Coordinates"/>
			
			<xsl:choose>
				<xsl:when test="count($coords) > 0">
					<xsl:for-each select="$coords">
						<xsl:sort data-type="number" select="@YCoordinate + (@Height div 2)"/>
							
						<xsl:if test="position()=1"><xsl:value-of select="@YCoordinate + (../@Height div 2) + 50"/></xsl:if>
						
					</xsl:for-each>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>100</xsl:text>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:otherwise>
	</xsl:choose>
	
</xsl:template>

<!--
==============================================
Output the width of the pool(s) for given process.
==============================================
-->
<xsl:template name="getPoolWidth">
	<xsl:param name="process"/>

	<!-- TODO - This algorithm WILL NOT WORK if right most object by centre-location is not the object whose right edges is furtherest right 
						unlikely, but worth mentioning (i.e. if end event is furthest right by location there may be, say, an embedded sub-proc 
						whose centre is further left but is big enough for its right edge to be further right. 
			Would help if the Lane had a width (in Studio XPDL, others are probably ok.
		-->
	<xsl:variable name="toolId">XPD</xsl:variable>
	<xsl:variable name="coords" select="$process//xpdl2:NodeGraphicsInfo[not(@ToolId) or @ToolId = '' or @ToolId = $toolId]/xpdl2:Coordinates"/>
	
	<xsl:choose>
		<xsl:when test="count($coords) > 0">
			<xsl:for-each select="$coords">
				<xsl:sort data-type="number"  order="descending" select="@XCoordinate"/>
					
				<xsl:if test="position()=1"><xsl:value-of select="@XCoordinate + (../@Width div 2) + 150"/></xsl:if>
				
			</xsl:for-each>
		</xsl:when>
		<xsl:otherwise>
			<xsl:text>100</xsl:text>
		</xsl:otherwise>
	</xsl:choose>
	
</xsl:template>

<!--
===================================================================================================================
	Catch-all template
===================================================================================================================
-->
	<xsl:template match="@* | node() | text()">
		<!-- Supress default copy of all nodes and text -->
	</xsl:template>
	
</xsl:stylesheet>
