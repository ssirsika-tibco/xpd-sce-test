<?xml version="1.0" encoding="UTF-8"?>
<!--
===================================================================================================================
XSLT:		MigrateStudioXPDL_1.xslt
				TIBCO Business Studio v2.0

DESCRIPTION:
	This xslt will upgrade a xpdl of format version 2 to format version 3.  This includes (summary):
		1. Migration to XPDL 2.0

===================================================================================================================
-->
<!-- 
	DON'T USE <xsl:copy> for source doc's extensions:elements !!!!!  THIS FORCES ANY MISSING namespace declarations from old document onto attributes of the copied element.

	When we created the <xpdl:Package> root element we forced swap of extensions: to be namespace 1.0.0

	If we create an xsl:element then xslt engine will just assume it's for the output doc's idea of "extensions" namespace.

	If we use xsl:copy the xsl engine assumes the element is from the source document' s extensions namespace.

	Any extensions: elements output from this xslt will have to have the new namespace provided, otherwise it will carry the old namespace to the output file
-->
<xsl:stylesheet 
	version="1.0" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 

	xmlns:xpdl="http://www.wfmc.org/2002/XPDL1.0" 
	xmlns:extensions="http://www.tibco.com/xpd/XpdlExtensions1.0.0" 

	xmlns="http://www.wfmc.org/2004/XPDL2.0alpha" 
	xmlns:xpdl2="http://www.wfmc.org/2004/XPDL2.0alpha" 
	
	xmlns:xpdExt="http://www.tibco.com/XPD/xpdExtension1.0.0" 	
	xmlns:simulation="http://www.tibco.com/xpd/Simulation1.0.1"
	xmlns:database="http://www.tibco.com/XPD/database1.0.0" 	
	xmlns:email="http://www.tibco.com/XPD/email1.0.0"
	xmlns:iProcessExt="http://www.tibco.com/XPD/iProcessExt1.0.0"	

	xmlns:java="http://xml.apache.org/xslt/java"
	
	exclude-result-prefixes="extensions xpdl java"
	>

	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	
	<xsl:variable name="ToolId">XPD</xsl:variable>
	<xsl:variable name="EventHeight">27.0</xsl:variable>
	<xsl:variable name="EventWidth">27.0</xsl:variable>
	<xsl:variable name="GatewayHeight">42.0</xsl:variable>
	<xsl:variable name="GatewayWidth">40.0</xsl:variable>
	<xsl:variable name="BorderColor">0,0,128</xsl:variable>
	<xsl:variable name="FillColor">255,219,74</xsl:variable>
	<xsl:variable name="TaskHeight">64.0</xsl:variable>
	<xsl:variable name="TaskWidth">96.0</xsl:variable>
	
	<!-- import package process/interface id -> actual workspace process id util. -->
	<xsl:variable name="procIdConverter" select="java:com.tibco.xpd.xpdl2.resources.XpdlMigrateProcessIdConverter.new()"/>
	
	
	
<!--
===============================================================================
	XSLT processing start point (process whole document)
===============================================================================
-->
<xsl:template match="/">

    <!-- Load up the process and interface into the import package process Id to actual id converter class. -->
    <xsl:for-each select="/xpdl:Package/xpdl:WorkflowProcesses/xpdl:WorkflowProcess">
		<!-- we use value of to ensure that the method is called (note that the method outputs nothing. -->
		<xsl:value-of select="java:addImportPackageProcess($procIdConverter, @Id)"/>
    </xsl:for-each>
    <xsl:for-each select="/xpdl:Package/xpdExt:ProcessInterfaces/xpdExt:ProcessInterface">
		<!-- we use value of to ensure that the method is called (note that the method outputs nothing. -->
		<xsl:value-of select="java:addImportPackageInterface($procIdConverter, @Id)"/>
    </xsl:for-each>

	<xsl:apply-templates/>
</xsl:template>
	
<!--
===============================================================================
	Convert XPDL 1.0 to XPDL 2.0 namespace alias
===============================================================================
-->
<xsl:template match="xpdl:*">
	<xsl:element name="xpdl2:{local-name(.)}">
		<xsl:apply-templates select="* | @* | node()"/>
	</xsl:element>
</xsl:template>

<!--
===================================================================================================================
	XSLT processing start point (process whole document)
===================================================================================================================
-->
<xsl:template match="xpdl:Package">

	<!-- Create our own package element so that we can override the xmlns:simulation to reference 1.0.1 namespace -->
	<!-- SIA-2 Change to not use generate-id instead use java call to generateUUId -->
	<xpdl2:Package Name="{@Name}">
		<xsl:attribute name="Id"><xsl:value-of select="java:com.tibco.xpd.xpdl2.resources.XpdlMigrateXsltUtil.generateUUID()"/></xsl:attribute>
		<xsl:attribute name="xsi:schemaLocation">http://www.wfmc.org/2004/XPDL2.0alpha http://www.wfmc.org/standards/docs/TC-1025_bpmnxpdl_24.xsd</xsl:attribute>

		<xsl:apply-templates select="xpdl:PackageHeader"/>

		<xsl:apply-templates select="xpdl:RedefinableHeader"/>
		
		<xsl:apply-templates select="xpdl:ExternalPackages"/>

		<!--SIA-2 Go through each workflowprocess, identify if the package is an external package and add the externalpackage reference here -->
		<xsl:call-template name="OutputExternalPackageReferences" />

		<xsl:apply-templates select="xpdl:TypeDeclarations"/>
		
		<xsl:apply-templates select="xpdl:Participants"/>

		<xsl:apply-templates select="xpdl:DataFields"/>

		<xpdl2:Pools>
			<xsl:for-each select="xpdl:WorkflowProcesses/xpdl:WorkflowProcess">
				<xsl:variable name="process" select="."/>
				
				<xsl:element name="xpdl2:Pool">
					<xsl:attribute name="Id"><xsl:value-of select="generate-id(xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='XPD']/extensions:Diagram/extensions:Pool)"/></xsl:attribute>
					<xsl:attribute name="Name"><xsl:value-of select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='XPD']/extensions:Diagram/extensions:Pool/extensions:Name"/></xsl:attribute>
					<xsl:attribute name="Process"><xsl:call-template name="GetProcessId"><xsl:with-param name="processName" select="$process/@Name"/></xsl:call-template></xsl:attribute>
					<xsl:attribute name="BoundaryVisible">true</xsl:attribute>

					<xpdl2:Lanes>
						<xsl:for-each select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='XPD']/extensions:Diagram/extensions:Pool/extensions:Lane">
							<xsl:element name="xpdl2:Lane">
								<xsl:attribute name="Id"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
								<xsl:attribute name="Name"><xsl:value-of select="extensions:Name"/></xsl:attribute>
								<xpdl2:NodeGraphicsInfos>

									<xpdl2:NodeGraphicsInfo>
										<xsl:attribute name="ToolId"><xsl:value-of select="$ToolId"/></xsl:attribute>
										<xsl:attribute name="Height"><xsl:value-of select="extensions:Size"/></xsl:attribute>
									</xpdl2:NodeGraphicsInfo>
									
								</xpdl2:NodeGraphicsInfos>
							</xsl:element>
						</xsl:for-each>
					</xpdl2:Lanes>
				</xsl:element>
			</xsl:for-each>
		</xpdl2:Pools>

		<!-- Add Associations (in v1.1 these only join Annotations to objects) -->
		<xsl:if test="count(xpdl:WorkflowProcesses/xpdl:WorkflowProcess/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='XPD']/extensions:Diagram/extensions:Association) > 0">
			<xpdl2:Associations>
			
				<xsl:for-each select="xpdl:WorkflowProcesses/xpdl:WorkflowProcess">
					<xsl:variable name="notes" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='XPD']/extensions:Diagram/extensions:Pool/extensions:Lane/extensions:Note"/>
					<xsl:variable name="acts" select="xpdl:Activities/xpdl:Activity"/>

					<xsl:for-each select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='XPD']/extensions:Diagram/extensions:Association">
						<xsl:variable name="srcId" select="extensions:Source"/>
						<xsl:variable name="tgtId" select="extensions:Target"/>
						
						<xsl:variable name="src" select="$notes[@Id = $srcId] | $acts[@Id = $srcId]"/>
						<xsl:variable name="tgt" select="$acts[@Id = $tgtId] | $notes[@Id = $tgtId]"/>
						
						<xsl:if test="$src and $tgt">
							<xsl:element name="xpdl2:Association">
								<xsl:attribute name="Id"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
								<xsl:attribute name="Name"></xsl:attribute>
								<xsl:attribute name="Source"><xsl:value-of select="generate-id($src)"/></xsl:attribute>
								<xsl:attribute name="Target"><xsl:value-of select="generate-id($tgt)"/></xsl:attribute>
								
								<xpdl2:Object Id="assObj_{generate-id()}"/>
								
								<xpdl2:ConnectorGraphicsInfos>
									<xpdl2:ConnectorGraphicsInfo BorderColor="{$BorderColor}" ToolId="{$ToolId}"/>
								</xpdl2:ConnectorGraphicsInfos>
								
							</xsl:element>
						</xsl:if>
					</xsl:for-each>
				
				</xsl:for-each>
			
			</xpdl2:Associations>
		</xsl:if>
		
		<!-- Add Artifacts (in v1.1 these are only Annotations/Notes) -->
		<xsl:variable name="notes" select="xpdl:WorkflowProcesses/xpdl:WorkflowProcess/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='XPD']/extensions:Diagram/extensions:Pool/extensions:Lane/extensions:Note"/>
		<xsl:if test="count($notes) > 0">
			<xpdl2:Artifacts>
				<xsl:for-each select="$notes">

					<xsl:element name="xpdl2:Artifact">
						<xsl:attribute name="Id"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
						<xsl:attribute name="ArtifactType">Annotation</xsl:attribute>
						<xsl:attribute name="TextAnnotation"><xsl:value-of select="extensions:Text"/></xsl:attribute>

						<xpdl2:NodeGraphicsInfos>
							<xsl:element name="xpdl2:NodeGraphicsInfo">
								<xsl:attribute name="BorderColor"><xsl:value-of select="$BorderColor"/></xsl:attribute>
								<xsl:attribute name="LaneId"><xsl:value-of select="generate-id(..)"/></xsl:attribute>
								<xsl:attribute name="ToolId"><xsl:value-of select="$ToolId"/></xsl:attribute>
								
								<xsl:element name="xpdl2:Coordinates">
									<xsl:attribute name="XCoordinate"><xsl:value-of select="extensions:Location/extensions:XOffset"/></xsl:attribute>
									<xsl:attribute name="YCoordinate"><xsl:value-of select="extensions:Location/extensions:YOffset"/></xsl:attribute>
								</xsl:element>
							</xsl:element>
						</xpdl2:NodeGraphicsInfos>

					</xsl:element>

				</xsl:for-each>
			</xpdl2:Artifacts>
		</xsl:if>
		
		<xsl:apply-templates select="xpdl:WorkflowProcesses"/>

		<xpdl2:ExtendedAttributes>
			<xpdl2:ExtendedAttribute Name="CreatedBy" Value="TIBCO Business Studio"/>
			<xpdl2:ExtendedAttribute Name="FormatVersion" Value="3"/>
		</xpdl2:ExtendedAttributes>
		<!-- 
		
		Intentionally include all the xpdExt elements underneath the Package header that may have been introduced by the import from 
		Modeler xslt. 
		
		Specifically to import IO Templates as Process Interfaces
		 -->			
		<xsl:apply-templates select="xpdExt:*"/>
		<xsl:apply-templates select="iProcessExt:*"/>

	</xpdl2:Package>

</xsl:template>

<!--
===============================================================================
	Output ExternalPackage References
===============================================================================
-->
<xsl:template name="OutputExternalPackageReferences">
	<xpdl2:ExternalPackages>
    
		<xsl:for-each select="/xpdl:Package/xpdl:WorkflowProcesses//xpdl:Activities/xpdl:Activity/xpdl:Implementation/xpdl:SubFlow"><!-- For every SubFlow activity -->
			<!-- For each of the subprocesses find if the subprocess can be found in the same package, if not use the java method to retrieve the id -->
			<xsl:variable name="subFlowId" select="@Id"/>

			<!-- If the Id matches any of the Workflow process names then dont consider adding Package reference --> 
			<xsl:variable name="localSubProcess" select="/xpdl:Package/xpdl:WorkflowProcesses/xpdl:WorkflowProcess[@Name=$subFlowId]"/>
				
			<xsl:if test="count($localSubProcess) &lt; 1">
				<xsl:variable name="packageName" select="java:getPackageName($procIdConverter, $subFlowId, 'PROCESS')"/>
				<xsl:variable name="packageSpecialFolderRelativePath" select="java:getPackageSpecialFolderRelativePath($procIdConverter, $subFlowId, 'PROCESS')"/>
				
				<!-- SIA-49 Don't create external package reference if we didn't find anything AND don't use same id as activity (by generating a unique id rather than one based on selected node). -->
				<xsl:if test="$packageName != ''">
					<xpdl2:ExternalPackage Id="{generate-id()}" href="{$packageName}">
					  <xpdl2:ExtendedAttributes>
						<xpdl2:ExtendedAttribute Name="location" Value="{$packageSpecialFolderRelativePath}"/>
					  </xpdl2:ExtendedAttributes>
					</xpdl2:ExternalPackage>
				</xsl:if>
			</xsl:if>
				
		</xsl:for-each><!-- next SubFlowActivity -->
		
		<!-- For each activity which has 'DynamicSubprocObject' in its extended attribute -->
		<xsl:for-each select="/xpdl:Package/xpdl:WorkflowProcesses//xpdl:Activities/xpdl:Activity[xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='v2XPD_TemplateName']]"	>
			<xsl:variable name="templateName" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='v2XPD_TemplateName']/text()"/>
			
			<xsl:variable name="procIfcInPkg" select="/xpdl:Package/xpdExt:ProcessInterfaces/xpdExt:ProcessInterface[@Name=$templateName]"/>
					
			<xsl:if test="count($procIfcInPkg) &lt; 1">
				<xsl:variable name="packageName" select="java:getPackageName($procIdConverter, $templateName, 'PROCESSINTERFACE')"/>
				<xsl:variable name="packageSpecialFolderRelativePath" select="java:getPackageSpecialFolderRelativePath($procIdConverter, $templateName, 'PROCESSINTERFACE')"/>
			
				<!-- SIA-49 Don't create external package reference if we didn't find anything AND don't use same id as activity (by generating a unique id rather than one based on selected node). -->
				<xsl:if test="$packageName != ''">
					<xpdl2:ExternalPackage Id="{generate-id()}" href="{$packageName}">
					  <xpdl2:ExtendedAttributes>
						<xpdl2:ExtendedAttribute Name="location" Value="{$packageSpecialFolderRelativePath}"/>
					  </xpdl2:ExtendedAttributes>
					</xpdl2:ExternalPackage>
				</xsl:if>
			</xsl:if>
			
		</xsl:for-each><!-- next activity -->
		
		<xsl:for-each select="/xpdl:Package/xpdl:WorkflowProcesses/xpdl:WorkflowProcess[xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='v2XPD_ProcessInterface']]">
			<xsl:variable name="templateName" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='v2XPD_ProcessInterface']/xpdExt:ImplementedInterface/@xpdExt:ProcessInterfaceId"/>
			<xsl:variable name="procIfcInPkg" select="/xpdl:Package/xpdExt:ProcessInterfaces/xpdExt:ProcessInterface[@Name=$templateName]"/>
					
			<xsl:if test="count($procIfcInPkg) &lt; 1">
				<xsl:variable name="packageName" select="java:getPackageName($procIdConverter, $templateName, 'PROCESSINTERFACE')"/>
				<xsl:variable name="packageSpecialFolderRelativePath" select="java:getPackageSpecialFolderRelativePath($procIdConverter, $templateName, 'PROCESSINTERFACE')"/>
			
				<!-- SIA-49 Don't create external package reference if we didn't find anything AND don't use same id as activity (by generating a unique id rather than one based on selected node). -->
				<xsl:if test="$packageName != ''">
					<xpdl2:ExternalPackage Id="{generate-id()}" href="{$packageName}">
					  <xpdl2:ExtendedAttributes>
						<xpdl2:ExtendedAttribute Name="location" Value="{$packageSpecialFolderRelativePath}"/>
					  </xpdl2:ExtendedAttributes>
					</xpdl2:ExternalPackage>
				</xsl:if>
			</xsl:if>
		</xsl:for-each>
	
	</xpdl2:ExternalPackages>
	
</xsl:template>

<!--
===============================================================================
	Gets the process id, given the process name. The process names need to be unique now, 
	so having changed to use EMF.generateUUId, need to retrieve the Unique Id generated for a process.
===============================================================================
-->
<xsl:template name="GetProcessId">
	<xsl:param name="processName"/>

	<xsl:value-of select="java:getActualProcessId($procIdConverter, $processName)"/>
</xsl:template>

<!--
===============================================================================
	Some xpdl 1 WorkflowProcess elements have changed order in xpdl 2
===============================================================================
-->
<xsl:template match="xpdl:WorkflowProcess">
	
	<xsl:element name="xpdl2:WorkflowProcess">
		<!-- ensure Id is unique in file. -->
		<!-- SIA-2 generate-id doesn't generate , changing it to use emf generate UUID-->
		<xsl:attribute name="Id"><xsl:call-template name="GetProcessId"><xsl:with-param name="processName" select="@Name"/></xsl:call-template></xsl:attribute>
		<xsl:attribute name="Name"><xsl:value-of select="@Name"/></xsl:attribute>
		<xsl:if test="@AccessLevel != ''">
			<xsl:attribute name="AccessLevel"><xsl:value-of select="@AccessLevel"/></xsl:attribute>
		</xsl:if>
		
		<!-- SIA-2 Begin -->
		<xsl:variable name="processInterfaceId"><!-- Record the implemented process interface Id -->
			<xsl:variable name="processInterfaceExtendedAttrib" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='v2XPD_ProcessInterface']"/>
			
			<xsl:if test="$processInterfaceExtendedAttrib">
				<xsl:variable name="procIfcName" select="$processInterfaceExtendedAttrib/xpdExt:ImplementedInterface/@xpdExt:ProcessInterfaceId"/>
				
				<xsl:variable name="procIfc" select="/xpdl:Package/xpdExt:ProcessInterfaces/xpdExt:ProcessInterface[@Name=$procIfcName]"/>
				<!-- If the process interface cannot be found. Use Java method to retrieve the process Interface Id -->
				<xsl:choose>
					
					<xsl:when test="$procIfc">
						<xsl:value-of select="$procIfc/@Id"/>
					</xsl:when>
					
					<xsl:otherwise>
						<xsl:value-of select="java:getActualProcessInterfaceId($procIdConverter, $procIfcName)"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
		</xsl:variable>
		
		<!-- SIA-2 End -->

		<xsl:apply-templates select="@xpdExt:* | @iProcessExt:*"/>
		
		<xsl:apply-templates select="xpdl:ProcessHeader"/>
		<xsl:apply-templates select="xpdl:RedefinableHeader"/>
		<xsl:apply-templates select="xpdl:FormalParameters"/>
		<xsl:apply-templates select="xpdl:Participants"/>
		<xsl:apply-templates select="xpdl:Applications"/>
		<xsl:apply-templates select="xpdl:DataFields"/>
		<xsl:apply-templates select="xpdl:ActivitySets"/>
		<xsl:apply-templates select="xpdl:Activities"/>
		<xsl:apply-templates select="xpdl:Transitions"/>
		<xsl:apply-templates select="xpdl:ExtendedAttributes"/>
		
		<!-- SIA-2 Begin -->
		<xsl:if test="$processInterfaceId != ''"> <!-- Output the ImplementedInterface value -->
			<xpdExt:ImplementedInterface xpdExt:ProcessInterfaceId="{$processInterfaceId}">
			<xsl:variable name="procIfc" select="/xpdl:Package/xpdExt:ProcessInterfaces/xpdExt:ProcessInterface[@Id=$processInterfaceId]"/>
				<xsl:if test="count($procIfc) &lt; 1">
					<xsl:variable name="procIfcName" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='v2XPD_ProcessInterface']/xpdExt:ImplementedInterface/@xpdExt:ProcessInterfaceId"/>
					<xsl:attribute name="xpdExt:PackageRef"><xsl:value-of select="java:getPackageName($procIdConverter, $procIfcName, 'PROCESSINTERFACE')"/></xsl:attribute>
				</xsl:if>
				
			</xpdExt:ImplementedInterface>	
		</xsl:if>
		<!-- SIA-2 End -->

		<!-- Carry over any extension elements attributes. -->
		<xsl:apply-templates select="xpdExt:*"/>
		<xsl:apply-templates select="iProcessExt:*"/>
	
	</xsl:element>

</xsl:template>

<!--
===============================================================================
	Activities need new implementation details and GraphicsNodeInfo
===============================================================================
-->
<xsl:template match="xpdl:Activity">
	<xsl:variable name="src" select="."/>

	<!-- Earlier we swapped lane id for old '0, 1, 2' id to auto-generated-id() to save confusion over lane id's for notes/associaitons 
			So need to calculate the new unique lane id for this activity.... -->
	<xsl:variable name="oldLaneId" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='XPD']/extensions:Activity/@LaneId"/>
	<xsl:variable name="lanes" select="../../xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='XPD']/extensions:Diagram/extensions:Pool/extensions:Lane"/>
	<xsl:variable name="newLaneId" select="generate-id($lanes[@Id = $oldLaneId])"/>

	<!-- Calculate what type of activity this is. -->
	<xsl:variable name="actType">
		<xsl:call-template name="getActivityType">
			<xsl:with-param name="act" select="."/>
		</xsl:call-template>
	</xsl:variable>
	

	<!-- Handle the activity generic stuff that appears before implementation specifics... -->
	<xsl:element name="xpdl2:Activity">
		<xsl:attribute name="Id"><xsl:value-of select="generate-id(.)"/></xsl:attribute>
		<xsl:if test="@Name"><xsl:attribute name="Name"><xsl:value-of select="@Name"/></xsl:attribute></xsl:if>

		<xsl:choose>
			<xsl:when test="$actType = 'TASK_USER'">
				<xsl:attribute name="StartMode">Manual</xsl:attribute>
				<xsl:attribute name="FinishMode">Manual</xsl:attribute>
			</xsl:when>
			<xsl:when test="$actType = 'TASK_SERVICE' or $actType = 'TASK_SUBFLOW' or $actType ='DYNAMIC_SUBPROC'">
				<xsl:attribute name="StartMode">Automatic</xsl:attribute>
				<xsl:attribute name="FinishMode">Automatic</xsl:attribute>
			</xsl:when>
		</xsl:choose>

		<xsl:apply-templates select="@xpdExt:* | @iProcessExt:*"/>

		<xsl:apply-templates select="xpdl:Description"/>
		<xsl:apply-templates select="xpdl:Limit"/>

		<!-- Now the activity implementation specifics... -->
		<xsl:choose>
			<!-- EVENTs -->
			<xsl:when test="starts-with($actType, 'EVENT_')">
				<xpdl2:Event>
					<xsl:choose>
						<xsl:when test="$actType = 'EVENT_START'">
							<xpdl2:StartEvent Trigger="None"/>
						</xsl:when>
						<xsl:when test="$actType = 'EVENT_END'">
							<xpdl2:EndEvent Result="None"/>
						</xsl:when>
						<xsl:when test="$actType = 'EVENT_INTERMEDIATE_TIMER'">
							<xpdl2:IntermediateEvent Trigger="Timer">
								<xpdl2:TriggerTimer TimeDate=""/>
							</xpdl2:IntermediateEvent>
						</xsl:when>

						<xsl:when test="$actType = 'EVENT_INTERMEDIATE_ERROR'">
							<!-- If there are transitions from this activity then it's an intermediate event - else its an end. -->
							<xsl:choose>
								<xsl:when test="../../xpdl:Transitions/xpdl:Transition[@From = $src/@Id]">
									<xpdl2:IntermediateEvent Trigger="Error">
										<xpdl2:ResultError ErrorCode="{xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='XPD']/extensions:Activity/extensions:EventErrorCode}"/>
									</xpdl2:IntermediateEvent>
								</xsl:when>
								<xsl:otherwise>
									<xpdl2:EndEvent Result="Error">
										<xpdl2:ResultError ErrorCode="{xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='XPD']/extensions:Activity/extensions:EventErrorCode}"/>
									</xpdl2:EndEvent>
								</xsl:otherwise>
							</xsl:choose>

						</xsl:when>
						<xsl:otherwise>
							<xpdl2:IntermediateEvent Trigger="None"/>
						</xsl:otherwise>
					</xsl:choose>
				</xpdl2:Event>
			</xsl:when>
			
			<!-- GATEWAYs -->
			<xsl:when test="starts-with($actType, 'GATEWAY_')">
				<xsl:element name="xpdl2:Route">
					<xsl:choose>
						<xsl:when test="$actType = 'GATEWAY_PARALLEL'">
							<xsl:attribute name="GatewayType">AND</xsl:attribute>
						</xsl:when>
						<xsl:when test="$actType = 'GATEWAY_COMPLEX'">
							<xsl:attribute name="GatewayType">Complex</xsl:attribute>
						</xsl:when>
						<xsl:when test="$actType = 'GATEWAY_OR'">
							<xsl:attribute name="GatewayType">OR</xsl:attribute>
						</xsl:when>
						<xsl:when test="$actType = 'GATEWAY_EVENT'">
							<xsl:attribute name="GatewayType">XOR</xsl:attribute>
							<xsl:attribute name="Instantiate">true</xsl:attribute>
						</xsl:when>
						<xsl:otherwise>
							<xsl:attribute name="GatewayType">XOR</xsl:attribute>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:element>
			</xsl:when>

			<!-- TASKS... -->
			<xsl:otherwise>
				<xsl:element name="xpdl2:Implementation">
					<xsl:choose>
						<xsl:when test="$actType = 'TASK_RECEIVE'">
						
							<xsl:element name="xpdl2:Task">
								<xsl:element name="xpdl2:TaskReceive">
									<xsl:attribute name="Instantiate">false</xsl:attribute>
									<xsl:attribute name="Implementation">Unspecified</xsl:attribute>
									<xpdl2:Message Id="msg_{generate-id()}"/>
								</xsl:element>
							</xsl:element>
							
						</xsl:when>
						<xsl:when test="$actType = 'TASK_SUBFLOW'">
							<xsl:element name="xpdl2:SubFlow">
								<!--<xsl:apply-templates select="xpdl:Implementation/xpdl:SubFlow/@Id"/>-->
								<xsl:variable name="subId" select="xpdl:Implementation/xpdl:SubFlow/@Id"/>
								<xsl:attribute name="Id">
									<!--<xsl:variable name="subProc" select="/xpdl:Package/xpdl:WorkflowProcesses/xpdl:WorkflowProcess[@Id = $subId]"/>
									<xsl:if test="$subProc">-->
										<xsl:call-template name="GetProcessId"><xsl:with-param name="processName" select="$subId"/></xsl:call-template>
									<!--</xsl:if>-->
								</xsl:attribute>
								
								<!-- SIA-2 add packageRef attribute -->
								<xsl:variable name="refProcess" select="/xpdl:Package/xpdl:WorkflowProcesses/xpdl:WorkflowProcess[@Name=$subId]"/>
								
								<xsl:if test="count($refProcess) &lt; 1">
									<xsl:attribute name="PackageRef"><xsl:value-of select="java:getPackageName($procIdConverter,$subId, 'PROCESS')"/></xsl:attribute>
								</xsl:if>
								<!-- SIA-2 End -->
								
								<!-- SIA-2 Commented called to subFlowParams template --> 
								<!--<xsl:call-template name="subFlowParams"/>-->
								<!-- Instead call output subFlowParams -->
								<xsl:call-template name="OutputSubFlowParams">
									<xsl:with-param name="activity" select="."/>
								</xsl:call-template>
								<!-- SIA-2 End -->

							</xsl:element>
						</xsl:when>
						<xsl:when test="$actType = 'DYNAMIC_SUBPROC'">
							<xsl:element name="xpdl2:SubFlow">
								<!--<xsl:apply-templates select="xpdl:Implementation/xpdl:SubFlow/@Id"/>-->
								<xsl:variable name="subId" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='v2XPD_TemplateName']"/>
								<xsl:variable name="procIfc" select="/xpdl:Package/xpdExt:ProcessInterfaces/xpdExt:ProcessInterface[@Name=$subId]"/>

								<xsl:attribute name="Id">
									
									<xsl:choose>
										<xsl:when test="$procIfc">
											<xsl:value-of select="$procIfc/@Id"/>
										</xsl:when>
										<xsl:otherwise>
											<xsl:value-of select="java:getActualProcessInterfaceId($procIdConverter, $subId)"/>
										</xsl:otherwise>
									</xsl:choose>	
								</xsl:attribute>
								
								<!-- SIA-2 add packageRef attribute -->
								<xsl:variable name="refProcess" select="/xpdl:Package/xpdExt:ProcessInterfaces/xpdExt:ProcessInterface[@Name=$subId]"/>
								
								<xsl:if test="count($refProcess) &lt; 1">
									<xsl:attribute name="PackageRef"><xsl:value-of select="java:getPackageName($procIdConverter, $subId, 'PROCESSINTERFACE')"/></xsl:attribute>
								</xsl:if>
								<!-- SIA-2 End -->

								<!-- SIA-2 Commented called to subFlowParams template --> 
								<!--<xsl:call-template name="subFlowParams"/>-->
								<!-- Instead call output subFlowParams -->
								<xsl:call-template name="OutputSubFlowParams">
									<xsl:with-param name="activity" select="."/>
								</xsl:call-template>
								<!-- SIA-2 End -->

							</xsl:element>
						</xsl:when>
						<xsl:when test="$actType = 'TASK_USER'">

							<xsl:element name="xpdl2:Task">
								
								<!-- OUTPUT THE xpdl2:TaskUser - Have been forced to do so as unescaped text because for some reason the
										current java xslt engine we use is FORCING xpdl2: prefiox on the implementation attribute (doesn't for anything else)
										and we could not find a way of making it not do so. -->
								<xpdl2:TaskUser Implementation="Unspecified">
									
									<xpdl2:Performers>
									  <xpdl2:Performer>-defined-in-Activity-Performer-</xpdl2:Performer>
									</xpdl2:Performers>
									<xpdl2:MessageIn Id="msgIn_{generate-id()}">
										<xsl:call-template name="userTaskParams"/>
									</xpdl2:MessageIn>

									<xpdl2:MessageOut Id="msgOut_{generate-id()}">
									</xpdl2:MessageOut>
									
								</xpdl2:TaskUser>
							</xsl:element>
						</xsl:when>

						<xsl:when test="$actType = 'TASK_SERVICE'">
							<xpdl2:Task>
								<!-- Check for a TaskService extended attribute (that import may have placed in the intermediate v1 xpdl
											for us to pick up and use. -->
								<xsl:choose>
									<xsl:when test="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'Task_xpdl2_TaskService']/*">
										<xsl:call-template name="CopyAndConvertPrefix">
											<xsl:with-param name="input" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'Task_xpdl2_TaskService']/*"/>
										</xsl:call-template>
									</xsl:when>
									
									<xsl:otherwise>
										<!-- OUTPUT THE xpdl2:TaskService - Have been forced to do so as unescaped text because for some reason the
												current java xslt engine we use is FORCING xpdl2: prefiox on the implementation attribute (doesn't for anything else)
												and we could not find a way of making it not do so. -->
										<xpdl2:TaskService Implementation="Unspecified">
											
											<xpdl2:MessageIn Id="msgIn_{generate-id()}"/>
											<xpdl2:MessageOut Id="msgOut_{generate-id()}"/>
										</xpdl2:TaskService>
									</xsl:otherwise>
								</xsl:choose>

							</xpdl2:Task>
						</xsl:when>

						<xsl:when test="$actType = 'TASK_SCRIPT'">
							<xpdl2:Task>
								<xsl:call-template name="CopyAndConvertPrefix">
									<xsl:with-param name="input" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'Task_xpdl2_TaskScript']/*"/>
								</xsl:call-template>									
							</xpdl2:Task>
						</xsl:when>

						<!-- Everything else is Task Type NONE -->
						<xsl:otherwise>
							<xpdl2:No/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:element>
			
			</xsl:otherwise>
			
		</xsl:choose>
		<!-- End of implementation type specifics -->


		<!-- Carry over other same-def elements from xpdl 1 -->
		<xsl:call-template name="OutputPerformers"/>
		
		<xsl:apply-templates select="xpdl:Priority"/>
		
		<!-- If this is an in-flow event timer then carry over the ipm expression -->
		<xsl:if test="$actType = 'EVENT_INTERMEDIATE_TIMER'">
			<xsl:variable name="script" select="$src/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'v2XPD_Deadline']/xpdl:v2XPD_Deadline"/>
			<xsl:choose>
				<xsl:when test="$script">
					<xsl:variable name="grammar">
						<xsl:choose>
							<xsl:when test="$script/@ScriptGrammar"><xsl:value-of select="$script/@ScriptGrammar"/></xsl:when>
							<xsl:otherwise>JavaScript</xsl:otherwise>
						</xsl:choose>
					</xsl:variable>

					<xpdl2:Deadline>
						<xpdl2:DeadlineDuration ScriptGrammar="{$grammar}">
							<xsl:value-of select="$script"/>
						</xpdl2:DeadlineDuration>
					</xpdl2:Deadline>
				</xsl:when>
				<xsl:otherwise>
					<xsl:variable name="constPeriod" select="$src/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'v2XPD_DeadlinePeriod']/xpdExt:ConstantPeriod"/>
					<xsl:if test="$constPeriod">
						<xpdl2:Deadline>
							<xpdl2:DeadlineDuration ScriptGrammar="ConstantPeriod">
								<xpdExt:ConstantPeriod>
									<xsl:copy-of select="$constPeriod/@*"/>
								</xpdExt:ConstantPeriod>
							</xpdl2:DeadlineDuration>
						</xpdl2:Deadline>
					</xsl:if>
				</xsl:otherwise>
			</xsl:choose>

		</xsl:if>
		
		<xsl:apply-templates select="xpdl:SimulationInformation"/>
		<xsl:apply-templates select="xpdl:Icon"/>
		<xsl:apply-templates select="xpdl:Documentation"/>

		<!-- INTENTIONALLY DON'T carry thru transition restrictions - Studio v2 foes not define them -->
		<!-- <xsl:apply-templates select="xpdl:TransitionRestrictions"/> -->

		<xsl:apply-templates select="xpdl:ExtendedAttributes"/>

		<!--
			Create the new NodeGraphicsInfos from the old v1 extended attrs. 
		-->
		<xpdl2:NodeGraphicsInfos>
			<xsl:element name="xpdl2:NodeGraphicsInfo">
				<xsl:attribute name="LaneId"><xsl:value-of select="$newLaneId"/></xsl:attribute>

				<xsl:attribute name="ToolId"><xsl:value-of select="$ToolId"/></xsl:attribute>
				<xsl:attribute name="BorderColor"><xsl:value-of select="$BorderColor"/></xsl:attribute>
				<xsl:attribute name="FillColor">
					<xsl:choose>
						<xsl:when test="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='XPD']/extensions:Activity/extensions:Color != ''"><xsl:value-of select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='XPD']/extensions:Activity/extensions:Color"/></xsl:when>
						<xsl:otherwise><xsl:value-of select="$FillColor"/></xsl:otherwise>
					</xsl:choose>
				</xsl:attribute>
				
				<xsl:variable name="location" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='XPD']/extensions:Activity/extensions:Location"/>
				
				<xsl:choose>
					<xsl:when test="starts-with($actType, 'EVENT_')">
						<xsl:attribute name="Height"><xsl:value-of select="$EventHeight"/></xsl:attribute>
						<xsl:attribute name="Width"><xsl:value-of select="$EventWidth"/></xsl:attribute>
						<xsl:element name="xpdl2:Coordinates">
							<xsl:attribute name="XCoordinate"><xsl:value-of select="$location/extensions:XOffset"/></xsl:attribute>
							<xsl:attribute name="YCoordinate"><xsl:value-of select="$location/extensions:YOffset"/></xsl:attribute>
						</xsl:element>
					</xsl:when>
					<xsl:when test="starts-with($actType, 'GATEWAY_')">
						<xsl:attribute name="Height"><xsl:value-of select="$GatewayHeight"/></xsl:attribute>
						<xsl:attribute name="Width"><xsl:value-of select="$GatewayWidth"/></xsl:attribute>
						<xsl:element name="xpdl2:Coordinates">
							<xsl:attribute name="XCoordinate"><xsl:value-of select="$location/extensions:XOffset"/></xsl:attribute>
							<xsl:attribute name="YCoordinate"><xsl:value-of select="$location/extensions:YOffset"/></xsl:attribute>
						</xsl:element>
					</xsl:when>
					<xsl:otherwise>
						<!-- TASK -->
						<xsl:choose>
							<xsl:when test="$location/extensions:Height and $location/extensions:Width">
								<xsl:attribute name="Height"><xsl:value-of select="$location/extensions:Height"/></xsl:attribute>
								<xsl:attribute name="Width"><xsl:value-of select="$location/extensions:Width"/></xsl:attribute>
								<xsl:element name="xpdl2:Coordinates">
									<xsl:choose>
										<xsl:when test="$actType = 'TASK_RECEIVE'">
											<xsl:attribute name="XCoordinate"><xsl:value-of select="$location/extensions:XOffset"/></xsl:attribute>
											<xsl:attribute name="YCoordinate"><xsl:value-of select="$location/extensions:YOffset"/></xsl:attribute>
										</xsl:when>
										<xsl:otherwise>
											<xsl:attribute name="XCoordinate"><xsl:value-of select="$location/extensions:XOffset + ($location/extensions:Width div 2)"/></xsl:attribute>
											<xsl:attribute name="YCoordinate"><xsl:value-of select="$location/extensions:YOffset + ($location/extensions:Height div 2)"/></xsl:attribute>
										</xsl:otherwise>
									</xsl:choose>
								</xsl:element>
							</xsl:when>
							<xsl:otherwise>
								<!-- just in case width / height are missing (we may have defaulted to task none for unrecognised type. -->
								<xsl:attribute name="Height"><xsl:value-of select="$TaskHeight"/></xsl:attribute>
								<xsl:attribute name="Width"><xsl:value-of select="$TaskWidth"/></xsl:attribute>
								<xsl:element name="xpdl2:Coordinates">
									<xsl:attribute name="XCoordinate"><xsl:value-of select="$location/extensions:XOffset"/></xsl:attribute>
									<xsl:attribute name="YCoordinate"><xsl:value-of select="$location/extensions:YOffset"/></xsl:attribute>
								</xsl:element>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:otherwise>
				</xsl:choose>

			</xsl:element>
		</xpdl2:NodeGraphicsInfos>
		<xsl:if test="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'Task_xpdl2_TaskService_ScriptIn']">
			<xsl:element name="xpdExt:ScriptInformation">
			<xsl:element name="xpdExt:Expression"><xsl:attribute name="ScriptGrammar" >XSLT</xsl:attribute><xsl:value-of select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'Task_xpdl2_TaskService_ScriptIn']"/></xsl:element>
			</xsl:element>
		</xsl:if>
		<xsl:if test="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'Task_xpdl2_TaskService_ScriptOut']">
			<xsl:element name="xpdExt:ScriptInformation">
			<xsl:attribute name="Direction">OUT</xsl:attribute>
			<xsl:element name="xpdExt:Expression"><xsl:attribute name="ScriptGrammar" >XSLT</xsl:attribute><xsl:value-of select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'Task_xpdl2_TaskService_ScriptOut']"/></xsl:element>
			</xsl:element>
		</xsl:if>
		
		<!-- SIA-1 Carry thru any iProcessExt / xpdExt extensiuon elements created already by iPrcess import etc. -->
		<xsl:apply-templates select="xpdExt:*"/>
		<xsl:apply-templates select="iProcessExt:*"/>
		
	</xsl:element>
	<!-- END OF ACTIVITY -->

	<!-- 
		Check for any exception transitions because we will need to create intermediate events on task border.
	-->
	<xsl:if test="starts-with($actType, 'TASK_')">

		<xsl:variable name="exceptions" select="../../xpdl:Transitions/xpdl:Transition[xpdl:Condition/@Type = 'EXCEPTION' and @From = $src/@Id]"/>

		<!-- xpdl v1 that was created on import from iPM we merge all deadline links into a single timer event on task border -->
		<xsl:choose>
			<xsl:when test="/xpdl:Package/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ImportFrom_IPM' and @Value = 'true']">
				<!-- Create a single exception for all timer transitions. -->
				<xsl:if test="$src/xpdl:Deadline">
					<!-- if task has a deadline then add a timer event to task border. -->
					<xpdl2:Activity>
						<xsl:attribute name="Id"><xsl:call-template name="TimerExceptionEventId"><xsl:with-param name="activity" select="$src"/></xsl:call-template></xsl:attribute>
	
						<xpdl2:Event>
							<xpdl2:IntermediateEvent Target="{generate-id($src)}" Trigger="Timer">
								<xpdl2:TriggerTimer TimeDate="">
									<xsl:choose>
										<xsl:when test="$src/xpdl:Deadline/@Execution = 'ASYNCHR'">
											<xsl:attribute name="xpdExt:ContinueOnTimeout">true</xsl:attribute>
										</xsl:when>
										<xsl:otherwise>
											<xsl:attribute name="xpdExt:ContinueOnTimeout">false</xsl:attribute>
										</xsl:otherwise>
									</xsl:choose>
								</xpdl2:TriggerTimer>
							</xpdl2:IntermediateEvent>
						</xpdl2:Event>
						
						<!-- deadline can be either a script or a period. -->
						<xsl:variable name="script" select="$src/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'v2XPD_Deadline']/xpdl:v2XPD_Deadline"/>
						<xsl:variable name="constPeriod" select="$src/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'v2XPD_DeadlinePeriod']/xpdExt:ConstantPeriod"/>

						<xsl:choose>
							<xsl:when test="$script">
								<xsl:variable name="grammar">
									<xsl:choose>
										<xsl:when test="$script/@ScriptGrammar"><xsl:value-of select="$script/@ScriptGrammar"/></xsl:when>
										<xsl:otherwise>JavaScript</xsl:otherwise>
									</xsl:choose>
								</xsl:variable>
			
								<xpdl2:Deadline>
									<xpdl2:DeadlineDuration ScriptGrammar="{$grammar}">
										<xsl:value-of select="$script"/>
									</xpdl2:DeadlineDuration>
								</xpdl2:Deadline>
							</xsl:when>
							<xsl:when test="$constPeriod">
								<xpdl2:Deadline>
									<xpdl2:DeadlineDuration ScriptGrammar="ConstantPeriod">
										<xpdExt:ConstantPeriod>
											<xsl:copy-of select="$constPeriod/@*"/>
										</xpdExt:ConstantPeriod>
									</xpdl2:DeadlineDuration>
								</xpdl2:Deadline>
							</xsl:when>
							<xsl:otherwise>
								<xpdl2:Deadline>
									<xpdl2:DeadlineDuration/>
								</xpdl2:Deadline>
							</xsl:otherwise>
						</xsl:choose>
						
						<xpdl2:NodeGraphicsInfos>
							<xpdl2:NodeGraphicsInfo BorderColor="{$BorderColor}" FillColor="{$FillColor}" LaneId="{$newLaneId}" ToolId="{$ToolId}" Width="{$EventWidth}" Height="{$EventHeight}" >
								<xpdl2:Coordinates XCoordinate="0.0" YCoordinate="0.0"/>
							</xpdl2:NodeGraphicsInfo>
							<xpdl2:NodeGraphicsInfo ToolId="{$ToolId}.BorderEventPosition">
								<xpdl2:Coordinates XCoordinate="35.76" YCoordinate="0.0"/>
							</xpdl2:NodeGraphicsInfo>
						</xpdl2:NodeGraphicsInfos>
	
					</xpdl2:Activity>
				</xsl:if>
			</xsl:when>
			
			<!-- For simple migration from studio v1 then create individualu timer events on task border -->
			<xsl:otherwise>
				<xsl:variable name="timerExceptions" select="$exceptions[xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'XPD']/extensions:Transition[extensions:ExceptionType = 'TIMER']]"/>

				<xsl:for-each select="$timerExceptions">
					<!-- create task border event... -->
					<xpdl2:Activity>
						<xsl:attribute name="Id">
							<xsl:call-template name="ErrorExceptionEventId"><xsl:with-param name="transition" select="."/></xsl:call-template>
						</xsl:attribute>
	
						<xpdl2:Event>
							<xpdl2:IntermediateEvent Target="{generate-id($src)}" Trigger="Timer">
								<xpdl2:TriggerTimer TimeDate=""/>
							</xpdl2:IntermediateEvent>
						</xpdl2:Event>
						
						<xpdl2:Deadline>
							<xpdl2:DeadlineDuration/>
						</xpdl2:Deadline>
						
						<xpdl2:NodeGraphicsInfos>
							<xpdl2:NodeGraphicsInfo BorderColor="{$BorderColor}" FillColor="{$FillColor}" LaneId="{$newLaneId}" ToolId="{$ToolId}" Width="{$EventWidth}" Height="{$EventHeight}" >
								<xpdl2:Coordinates XCoordinate="0.0" YCoordinate="0.0"/>
							</xpdl2:NodeGraphicsInfo>
							<xpdl2:NodeGraphicsInfo ToolId="{$ToolId}.BorderEventPosition">
								<xpdl2:Coordinates XCoordinate="{35.76 + (5 * (position()-1))}" YCoordinate="0.0"/>
							</xpdl2:NodeGraphicsInfo>
						</xpdl2:NodeGraphicsInfos>
	
					</xpdl2:Activity>
					
				</xsl:for-each>
			</xsl:otherwise>

		</xsl:choose>


		<!-- And then error exceptions... -->
		<xsl:variable name="errExceptions" select="$exceptions[not(xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'XPD']/extensions:Transition[extensions:ExceptionType = 'TIMER'])]"/>

		<xsl:if test="count($errExceptions) > 0">
			<xsl:for-each select="$errExceptions">
				<!-- create task border event... -->
				<xpdl2:Activity>
					<xsl:attribute name="Id">
						<xsl:call-template name="ErrorExceptionEventId"><xsl:with-param name="transition" select="."/></xsl:call-template>
					</xsl:attribute>

					<xpdl2:Event>
						<xpdl2:IntermediateEvent Target="{generate-id($src)}" Trigger="Error">
							<xpdl2:ResultError ErrorCode=""/>
						</xpdl2:IntermediateEvent>
					</xpdl2:Event>
					
					<xpdl2:NodeGraphicsInfos>
						<xpdl2:NodeGraphicsInfo BorderColor="{$BorderColor}" FillColor="{$FillColor}" LaneId="{$newLaneId}" ToolId="{$ToolId}" Width="{$EventWidth}" Height="{$EventHeight}" >
							<xpdl2:Coordinates XCoordinate="0.0" YCoordinate="0.0"/>
						</xpdl2:NodeGraphicsInfo>
						<xpdl2:NodeGraphicsInfo ToolId="{$ToolId}.BorderEventPosition">
							<xpdl2:Coordinates XCoordinate="{20.76 - (5 * (position()-1))}" YCoordinate="0.0"/>
						</xpdl2:NodeGraphicsInfo>
					</xpdl2:NodeGraphicsInfos>

				</xpdl2:Activity>
				
			</xsl:for-each>
		</xsl:if>
		
		<!-- Finally add task boundary error events for catchers of errors thrown (created for withdraw-link equivalence on import from iPM only. -->
		<xsl:for-each select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'CatchErrorEventCode']">
			
			<!-- create task border event... -->
			<xpdl2:Activity Id="WithdrawEventCatcher_{generate-id($src)}_{@Value}">

				<xpdl2:Event>
					<xpdl2:IntermediateEvent Target="{generate-id($src)}" Trigger="Error">
						<xpdl2:ResultError ErrorCode="{@Value}"/>
					</xpdl2:IntermediateEvent>
				</xpdl2:Event>
				
				<xpdl2:NodeGraphicsInfos>
					<xpdl2:NodeGraphicsInfo BorderColor="{$BorderColor}" FillColor="{$FillColor}" LaneId="{$newLaneId}" ToolId="{$ToolId}" Width="{$EventWidth}" Height="{$EventHeight}" >
						<xpdl2:Coordinates XCoordinate="0.0" YCoordinate="0.0"/>
					</xpdl2:NodeGraphicsInfo>
					<xpdl2:NodeGraphicsInfo ToolId="{$ToolId}.BorderEventPosition">
						<xpdl2:Coordinates XCoordinate="{25.76 - (5 * (position()-1))}" YCoordinate="0.0"/>
					</xpdl2:NodeGraphicsInfo>
				</xpdl2:NodeGraphicsInfos>

			</xpdl2:Activity>
		
		</xsl:for-each>

	</xsl:if>
	
</xsl:template>

<!--
===============================================================================
	Output the Sub process data mappings
===============================================================================
-->
<xsl:template name="OutputSubFlowParams">
	<xsl:param name="activity"/>
		
	<xsl:variable name="dataMappings" select="$activity/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='v2XPD_DataMappings']"/>
	<xsl:value-of select="$dataMappings/text()"/>
	
	<xsl:if test="$dataMappings/xpdl:DataMappings">
		<xpdl2:DataMappings>
			<xsl:for-each select="$dataMappings/xpdl:DataMappings/xpdl:DataMapping">
				<xpdl2:DataMapping Direction="{@Direction}" Formal="{@Formal}">
					<xsl:if test="xpdExt:ScriptInformation">
						<xpdExt:ScriptInformation Id="{xpdExt:ScriptInformation/@Id}" Name="{xpdExt:ScriptInformation/@Name}">
							<xpdExt:Expression ScriptGrammar="{xpdExt:ScriptInformation/xpdExt:Expression/@ScriptGrammar}"/>
						</xpdExt:ScriptInformation>
					</xsl:if>
					<xpdl2:Actual ScriptGrammar="{xpdl:Actual/@ScriptGrammar}"><xsl:value-of select="xpdl:Actual/text()"/></xpdl2:Actual>
				</xpdl2:DataMapping>
			</xsl:for-each>
		</xpdl2:DataMappings>
	</xsl:if>
</xsl:template>

<!--
===============================================================================
	Output the Activity performers (multiple if there are any else pick the singleton.
===============================================================================
-->
<xsl:template name="OutputPerformers">

	<xsl:variable name="multiPerfs" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'v2XPD_Performers']/*"/>
	<xsl:variable name="partics" select="/xpdl:Package/xpdl:Participants/xpdl:Participant | ../../xpdl:Participants/xpdl:Participant"/>
	<xsl:variable name="fields" select="/xpdl:Package/xpdl:DataFields/xpdl:DataField | ../../xpdl:DataFields/xpdl:DataField | ../../xpdl:FormalParameters/xpdl:FormalParameter"/>
	
	<xsl:choose>
		<xsl:when test="count($multiPerfs) > 0">
			<xpdl2:Performers>
				<xsl:for-each select="$multiPerfs">
					<xsl:variable name="pId" select="text()"/>

					<xsl:choose>
						<xsl:when test="local-name() = 'v2FieldPerformer'">
							<xsl:variable name="field" select="$fields[@Id = $pId]"/>
							<xpdl2:Performer><xsl:value-of select="generate-id($field)"/></xpdl2:Performer>
						</xsl:when>
						<xsl:otherwise>
							<xsl:variable name="partic" select="$partics[@Id = $pId]"/>
							<xsl:if test="$partic">
								<xpdl2:Performer><xsl:value-of select="generate-id($partic)"/></xpdl2:Performer>
							</xsl:if>
						</xsl:otherwise>
					</xsl:choose>
					
				</xsl:for-each>
			</xpdl2:Performers>
		</xsl:when>
		<xsl:when test="xpdl:Performer">
			<xpdl2:Performers>
				<xsl:variable name="pId" select="xpdl:Performer/text()"/>
				<xsl:variable name="partic" select="$partics[@Id = $pId]"/>
				<xsl:if test="$partic">
					<xpdl2:Performer><xsl:value-of select="generate-id($partic)"/></xpdl2:Performer>
				</xsl:if>
			</xpdl2:Performers>
		</xsl:when>
	</xsl:choose>

</xsl:template>


<!--
===============================================================================
	Sort out user task params
	(Context is xpdl:Activity)
===============================================================================
-->
<xsl:template name="userTaskParams">

	<xsl:variable name="params" select="xpdl:Implementation/xpdl:Tool/xpdl:ActualParameters/xpdl:ActualParameter"/>
	
	<xsl:if test="count($params) > 0">
		<xpdl2:ActualParameters>

			<xsl:variable name="myFormals" select="../../xpdl:FormalParameters/xpdl:FormalParameter"/>
			<xsl:variable name="myFields" select="../../xpdl:DataFields/xpdl:DataField | /xpdl:Package/xpdl:DataFields/xpdl:DataField"/>

			<xsl:for-each select="$params">
					<xpdl2:ActualParameter>
						<xsl:variable name="prm" select="text()"/>
						
						<xsl:choose>
							<!-- For data fields map to Name -->
							<xsl:when test="$myFields[@Id = $prm]">
								<xsl:value-of select="$myFields[@Id = $prm]/@Name"/>
							</xsl:when>

							<xsl:when test="$myFormals[@Id = $prm]">
								<xsl:call-template name="getFormalParamName">
									<xsl:with-param name="param" select="$myFormals[@Id = $prm]"/>
								</xsl:call-template>
							</xsl:when>

							<!-- if all else fails just go with current value - at least user can see something might be off-kilter. -->
							<xsl:otherwise>
								<xsl:value-of select="."/>
							</xsl:otherwise>
						</xsl:choose>
					</xpdl2:ActualParameter>
			
			</xsl:for-each>
		
		
		</xpdl2:ActualParameters>
	
	</xsl:if>

</xsl:template>
	

<!--
===============================================================================
	Sort out subflow call params
	(Context is xpdl:Activity)
===============================================================================
-->
<xsl:template name="subFlowParams">

	<xsl:variable name="procId" select="xpdl:Implementation/xpdl:SubFlow/@Id"/>
	
	<xsl:variable name="subproc" select="/xpdl:Package/xpdl:WorkflowProcesses/xpdl:WorkflowProcess[@Id = $procId]"/>
	
	<xsl:if test="$subproc">
	
		<!-- Go from list of formal parameters -->
		<xsl:variable name="params" select="$subproc/xpdl:FormalParameters/xpdl:FormalParameter"/>
		<xsl:variable name="actuals" select="xpdl:Implementation/xpdl:SubFlow/xpdl:ActualParameters/xpdl:ActualParameter"/>
		
		<xsl:if test="count($params) > 0 and count($actuals) > 0">
			<xsl:variable name="myFormals" select="../../xpdl:FormalParameters/xpdl:FormalParameter"/>
			<xsl:variable name="myFields" select="../../xpdl:DataFields/xpdl:DataField | /xpdl:Package/xpdl:DataFields/xpdl:DataField"/>
		
			
			<xpdl2:DataMappings>
				
				<xsl:for-each select="$params">
					<xsl:variable name="pos" select="position()"/>
					
					<xsl:if test="$actuals[$pos]">

						<xsl:variable name="subProcFormalName">
							<xsl:call-template name="getFormalParamName">
								<xsl:with-param name="param" select="."/>
							</xsl:call-template>
						</xsl:variable>
						
						<xsl:choose>
							<!-- For data fields map to Name -->
							<xsl:when test="$myFields[@Id = $actuals[$pos]/text()]">
								<xpdl2:DataMapping Direction="{@Mode}" Formal="{$subProcFormalName}">
									<xpdl2:Actual><xsl:value-of select="$myFields[@Id = $actuals[$pos]]/@Name"/></xpdl2:Actual>
								</xpdl2:DataMapping>
							</xsl:when>

							<xsl:when test="$myFormals[@Id = $actuals[$pos]/text()]">
								<xsl:variable name="formalName">
									<xsl:call-template name="getFormalParamName">
										<xsl:with-param name="param" select="$myFormals[@Id = $actuals[$pos]/text()]"/>
									</xsl:call-template>
								</xsl:variable>

								<xpdl2:DataMapping Direction="{@Mode}" Formal="{$subProcFormalName}">
									<xpdl2:Actual><xsl:value-of select="$formalName"/></xpdl2:Actual>
								</xpdl2:DataMapping>
								
							</xsl:when>

							<!-- If all else fails, go with original actual param so user can see something has gone amiss -->
							<xsl:otherwise>
								<xpdl2:DataMapping Direction="{@Mode}" Formal="{$subProcFormalName}">
									<xpdl2:Actual><xsl:value-of select="$actuals[$pos]"/></xpdl2:Actual>
								</xpdl2:DataMapping>
							</xsl:otherwise>
						</xsl:choose>
						
					</xsl:if>
				
				</xsl:for-each>
			
			</xpdl2:DataMappings>
		
		</xsl:if>
	</xsl:if>

</xsl:template>
	

<!--
===============================================================================
	Sort out subflow activity ids
	(Context is xpdl:Activity)

	SIA -2 to link subprocesses and main process when imported in Studio

	<SID Q> - Isn't invoked at the moment - reasons being the generate-id doesn't generate unique ids causing the main
	proc to have the same id as the sub proc.
===============================================================================
-->
<xsl:template match="xpdl:SubFlow/@Id">
	<xsl:variable name="id" select="."/>
	<xsl:variable name="subProc" select="/Package/xpdl:WorkflowProcesses[@Name = $id]"/>

	<xsl:choose>
		<xsl:when test="$subProc"><!-- If the sub proc exists in the same package -->
			<xsl:attribute name="Id"><xsl:value-of select="$id"/></xsl:attribute>
		</xsl:when>
		
		<xsl:otherwise> <!-- If the sub proc doesn't exist in the same package, the use java static method to get id -->
			    <xsl:variable name="subProcId" select="java:getActualProcessId($procIdConverter, $id)"/>
			   
				<xsl:attribute name="Id"><xsl:value-of select="$subProcId"/></xsl:attribute>
		</xsl:otherwise>
		
	</xsl:choose>
</xsl:template>

<!--
===============================================================================
	Get specially created event on task border id (for ones we create from old v1 exception transitions)
===============================================================================
-->
	<xsl:template name="ErrorExceptionEventId">
		<xsl:param name="transition"/>
		
		<xsl:text>ErrorExceptionEvent_</xsl:text><xsl:value-of select="generate-id($transition)"/>
	</xsl:template>
	
	<xsl:template name="TimerExceptionEventId">
		<xsl:param name="activity"/>
		
		<xsl:text>TimerExceptionEvent_</xsl:text><xsl:value-of select="generate-id($activity)"/>
	</xsl:template>

<!--
===============================================================================
	Transitions - 
===============================================================================
-->
	<xsl:template match="xpdl:Transitions">
		<!-- Save keep looking up all activities for each transition -->
		<xsl:variable name="acts" select="../xpdl:Activities/xpdl:Activity"/>

		<xsl:element name="xpdl2:Transitions">
			<xsl:apply-templates select="xpdl:Transition">
				<xsl:with-param name="acts" select="$acts"/>
			</xsl:apply-templates>
		</xsl:element>

	</xsl:template>


<!--
===============================================================================
	Ditch applications
===============================================================================
-->
	<xsl:template match="xpdl:Applications">
	
	</xsl:template>

<!--
===============================================================================
	Transition - replace activity id's with unique generated id
===============================================================================
-->
	<xsl:template match="xpdl:Transition">
		<xsl:param name="acts"/>
		
		<xsl:variable name="srcId" select="@From"/>
		<xsl:variable name="tgtId" select="@To"/>
		
		<xsl:variable name="src" select="$acts[@Id = $srcId]"/>
		<xsl:variable name="tgt" select="$acts[@Id = $tgtId]"/>
		
		<xsl:if test="$src and $tgt">
			<xsl:element name="xpdl2:Transition">
				<xsl:attribute name="Id"><xsl:value-of select="generate-id(.)"/></xsl:attribute>

				<!-- If there is no initial name attribute then the initial direct-edit-label will not work in v2 studio -->
				<xsl:attribute name="Name">
					<xsl:value-of select="@Name"/>
				</xsl:attribute>
					
				<!-- 
					replace activity id's with unique generated id 
					BUT if this is an exception transition then we will have created a task-border intermediate event so we will need to link to that instead.
					-->
				<!-- Calculate what type of activity this is. -->
				<xsl:variable name="actType">
					<xsl:call-template name="getActivityType">
						<xsl:with-param name="act" select="$src"/>
					</xsl:call-template>
				</xsl:variable>

				<xsl:attribute name="From">
					<xsl:choose>
						<xsl:when test="xpdl:Condition/@Type = 'EXCEPTION' and starts-with($actType, 'TASK_')">
							<xsl:choose>
								<xsl:when test="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'XPD']/extensions:Transition[extensions:ExceptionType = 'TIMER']">
									<!-- xpdl v1 that was created on import from iPM we merge all deadline links into a single timer event on task border -->
									<xsl:choose>
										<xsl:when test="/xpdl:Package/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ImportFrom_IPM' and @Value = 'true']">
											<xsl:call-template name="TimerExceptionEventId"><xsl:with-param name="activity" select="$src"/></xsl:call-template>
										</xsl:when>
										<xsl:otherwise>
											<!-- for plain v1 migration then connect to the individual timer events we created. -->
											<xsl:call-template name="ErrorExceptionEventId"><xsl:with-param name="transition" select="."/></xsl:call-template>
										</xsl:otherwise>
									</xsl:choose>
								</xsl:when>
								<xsl:otherwise>
									<xsl:call-template name="ErrorExceptionEventId"><xsl:with-param name="transition" select="."/></xsl:call-template>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="generate-id($src)"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:attribute>
				
				<xsl:attribute name="To"><xsl:value-of select="generate-id($tgt)"/></xsl:attribute>
				
				<xsl:apply-templates select="@xpdExt:* | @iProcessExt:*"/>
			
				<!-- Don't carry over EXCEPTION types, these are now handled as normal transition from event on task border -->
				<xsl:apply-templates select="xpdl:Condition[@Type = 'CONDITION' or @Type = 'OTHERWISE']"/>

				<xsl:apply-templates select="xpdl:Description"/>
				<xsl:apply-templates select="xpdl:ExtendedAttributes"/>

				<xpdl2:ConnectorGraphicsInfos>
					<xpdl2:ConnectorGraphicsInfo BorderColor="{$BorderColor}" ToolId="XPD">
						
						<xsl:variable name="bends" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'XPD']/extensions:Transition/extensions:Router/extensions:BendPoints"/>

						<xsl:for-each select="$bends">
							<xpdl2:Coordinates XCoordinate="{extensions:Location/extensions:XOffset}" YCoordinate="{extensions:Location/extensions:YOffset}"/>
							<xpdl2:Coordinates XCoordinate="{extensions:Location/extensions:Width}" YCoordinate="{extensions:Location/extensions:Height}"/>

						</xsl:for-each>
					
					</xpdl2:ConnectorGraphicsInfo>
				</xpdl2:ConnectorGraphicsInfos>
				
				<!-- SIA-1 Carry thru any iProcessExt / xpdExt extensiuon elements created already by iPrcess import etc. -->
				<xsl:apply-templates select="xpdExt:*"/>
				<xsl:apply-templates select="iProcessExt:*"/>

			</xsl:element>
		</xsl:if>
		
	</xsl:template>

<!--
===============================================================================
	xpdl v1 transition condition to xpdl 2
===============================================================================
-->
<xsl:template match="xpdl:Transition/xpdl:Condition[@Type = 'CONDITION']">
	<xpdl2:Condition Type="CONDITION">
		<xsl:if test="./text() != ''">

			<xsl:variable name="grammar">
				<xsl:choose>
					<xsl:when test="@ScriptGrammar"><xsl:value-of select="@ScriptGrammar"/></xsl:when>
					<xsl:otherwise>JavaScript</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>

			<xpdl2:Expression ScriptGrammar="{$grammar}"><xsl:value-of select="./text()"/></xpdl2:Expression>
		</xsl:if>
	</xpdl2:Condition>
</xsl:template>
	

<!--
===============================================================================
	v1 Formal Params only have Id (which was the name) 

	SIA - 2 : Apply template to only xpdl:FormalParameter under the xpdl:WorkflowProcess hierarchy
===============================================================================
-->
	<xsl:template match="xpdl:WorkflowProcess/xpdl:FormalParameters/xpdl:FormalParameter">
	
		<xsl:element name="xpdl2:FormalParameter">
			
			<xsl:attribute name="Id"><xsl:value-of select="generate-id()"/></xsl:attribute>

			<xsl:attribute name="Name">
				<xsl:call-template name="getFormalParamName">
					<xsl:with-param name="param" select="."/>
				</xsl:call-template>
			</xsl:attribute>
			
			<xsl:apply-templates select="@xpdExt:* | @IsArray | @Mode | xpdl:DataType | xpdl:Description"/>
		
		</xsl:element>
	
	</xsl:template>

<!-- Previous template "<xsl:template match="xpdl:WorkflowProcess/*/xpdl:DataType/xpdl:BasicType">" was never executed (wrong xpath) but wasn't neede anyway -->

<!--
===============================================================================
	Get formal parameter name for v2 (either description or 
===============================================================================
-->
	<xsl:template name="getFormalParamName">
		<xsl:param name="param"/>
		
		<xsl:choose>
			<xsl:when test="$param/@Name != ''"><xsl:value-of select="$param/@Name"/></xsl:when>
			<xsl:when test="$param/xpdl:Description != '' and not(contains($param/xpdl:Description, '&#10;'))"><xsl:value-of select="$param/xpdl:Description"/></xsl:when>
			<xsl:otherwise><xsl:value-of select="$param/@Id"/></xsl:otherwise>
		</xsl:choose>
	
	</xsl:template>

<!--
===============================================================================
	Participants (replace id with unique id).
===============================================================================
-->
<xsl:template match="xpdl:Participant">
	<xpdl2:Participant Id="{generate-id(.)}" Name="{@Name}">
		<xsl:apply-templates select="@xpdExt:* | @iProcessExt:*"/>
		<xsl:apply-templates select="*"/>
	</xpdl2:Participant>

</xsl:template>

<!--
===============================================================================
	Data fields (replace id with unique id).
===============================================================================
-->
<xsl:template match="xpdl:DataField">
	<xpdl2:DataField Id="{generate-id(.)}" Name="{@Name}">
		<xsl:if test="@IsArray != ''">
			<xsl:attribute name="IsArray"><xsl:value-of select="@IsArray"/></xsl:attribute>
		</xsl:if>

		<xsl:apply-templates select="@xpdExt:* | @iProcessExt:*"/>

		<xsl:apply-templates select="xpdl:DataType"/>
		<xsl:apply-templates select="xpdl:InitialValue"/>
		<xsl:apply-templates select="xpdl:Description"/>
		
	</xpdl2:DataField>

</xsl:template>

<!--
===============================================================================
	iPM import may have set some dimension info in source file, if it's there output it to xpdl2
===============================================================================
-->
<xsl:template match="xpdl:DataField/xpdl:DataType/xpdl:BasicType">
	<xsl:variable name="details" select="../../xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'v2XPD_DataFieldExtra']/xpdl:details"/>

	<xpdl2:BasicType>
		<xsl:apply-templates select="@*"/>
		
		<xsl:if test="$details/xpdl:length != ''">
			<xpdl2:Length><xsl:value-of select="$details/xpdl:length"/></xpdl2:Length>
		</xsl:if>
		<xsl:if test="$details/xpdl:precision != ''">
			<xpdl2:Precision><xsl:value-of select="$details/xpdl:precision"/></xpdl2:Precision>
		</xsl:if>
		<xsl:if test="$details/xpdl:scale != ''">
			<xpdl2:Scale><xsl:value-of select="$details/xpdl:scale"/></xpdl2:Scale>
		</xsl:if>
	</xpdl2:BasicType>
	
</xsl:template>

<!--
===============================================================================
	strip v1 ExtendedAttributes completely for now
===============================================================================
-->
<xsl:template match="xpdl:ExtendedAttributes">
	<xsl:if test="count(xpdl:ExtendedAttribute[@Name != 'XPD']) > 0">
		<xpdl2:ExtendedAttributes>
			<xsl:for-each select="xpdl:ExtendedAttribute[@Name != 'XPD']">
				<xsl:apply-templates select="."/>
			</xsl:for-each>
		</xpdl2:ExtendedAttributes>
	</xsl:if>
</xsl:template>

<!--
===============================================================================
	Change XPDL version...
===============================================================================
-->
<xsl:template match="xpdl:XPDLVersion">
	<xpdl2:XPDLVersion>2.0</xpdl2:XPDLVersion>
</xsl:template>


<!--
===============================================================================
	Get internal activity type name (identifies what this xslt should trat activity object as.
===============================================================================
-->
<xsl:template name="getActivityType">
	<xsl:param name="act"/>
	
	<xsl:choose>
		<!-- EVENTs -->
		<xsl:when test="$act/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='XPD']/extensions:Activity/extensions:ActivityType='EVENT'">
			<xsl:choose>
				<xsl:when test="$act/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='XPD']/extensions:Activity/extensions:EventFlowType='START'">
					<xsl:text>EVENT_START</xsl:text>
				</xsl:when>
				<xsl:when test="$act/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='XPD']/extensions:Activity/extensions:EventFlowType='END'">
					<xsl:text>EVENT_END</xsl:text>
				</xsl:when>
				<xsl:when test="$act/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='XPD']/extensions:Activity/extensions:EventFlowType='TIMER'">
					<xsl:text>EVENT_INTERMEDIATE_TIMER</xsl:text>
				</xsl:when>
				<xsl:when test="$act/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='XPD']/extensions:Activity/extensions:EventFlowType='RECEIVE'">
					<xsl:text>TASK_RECEIVE</xsl:text>
				</xsl:when>
				<xsl:when test="$act/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='XPD']/extensions:Activity/extensions:EventFlowType='ERROR'">
					<xsl:text>EVENT_INTERMEDIATE_ERROR</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>TASK_NONE</xsl:text> <!-- URK - should not be possible -->
				</xsl:otherwise>
			</xsl:choose>
		</xsl:when>
	
		<!-- GATEWAYs -->
		<xsl:when test="$act/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='XPD']/extensions:Activity/extensions:ActivityType='GATEWAY'">
			<xsl:choose>
				<xsl:when test="$act/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='XPD']/extensions:Activity/extensions:GatewayType = 'PARALLEL'">
					<xsl:text>GATEWAY_PARALLEL</xsl:text>
				</xsl:when>
				<xsl:when test="$act/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='XPD']/extensions:Activity/extensions:GatewayType = 'COMPLEX'">
					<xsl:text>GATEWAY_COMPLEX</xsl:text>
				</xsl:when>
				<xsl:when test="$act/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='XPD']/extensions:Activity/extensions:GatewayType = 'OR'">
					<xsl:text>GATEWAY_OR</xsl:text>
				</xsl:when>
				<xsl:when test="$act/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='XPD']/extensions:Activity/extensions:GatewayType = 'XOR_EVENT'">
					<xsl:text>GATEWAY_EVENT</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>GATEWAY_XOR</xsl:text>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:when>
		
		<!-- TASKSs -->
		<xsl:otherwise>
			<xsl:choose>
				<xsl:when test="$act/xpdl:Implementation/xpdl:SubFlow">
					<xsl:text>TASK_SUBFLOW</xsl:text>
				</xsl:when>
				<xsl:when test="$act/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'v2XPD_TemplateName']">
					<xsl:text>DYNAMIC_SUBPROC</xsl:text>
				</xsl:when>
				<xsl:when test="$act/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'Task_xpdl2_TaskScript']">
					<xsl:text>TASK_SCRIPT</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:choose>
						<!-- When have Implementation of type APPLICATION and Start and Finish Mode  are both manual this is a UserTask -->
						<xsl:when test="$act/xpdl:Implementation/xpdl:Tool/@Type='APPLICATION' and $act/xpdl:StartMode/xpdl:Manual and xpdl:FinishMode/xpdl:Manual">
							<xsl:text>TASK_USER</xsl:text>
						</xsl:when>
						<!-- Else if Implementation of type APPLICATION assume is ServiceTask -->
						<xsl:when test="$act/xpdl:Implementation/xpdl:Tool/@Type='APPLICATION' and $act/xpdl:StartMode/xpdl:Automatic and xpdl:FinishMode/xpdl:Automatic">
							<xsl:text>TASK_SERVICE</xsl:text>
						</xsl:when>
						<xsl:otherwise>
							<xsl:text>TASK_NONE</xsl:text>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:otherwise>
	
	</xsl:choose>

</xsl:template>


<!--
===============================================================================
	Given input nodes that have xxx_ namespace prefixing rather than xxx: convert the xxx_ to xxx:
		This allows the provided of the source xml to 'fake' extra bits into xpdl 1 ext attrs (for instance) that
		it wants us to place directly in xpdl2
===============================================================================
-->
<xsl:template name="CopyAndConvertPrefix">
	<xsl:param name="input"/>

	<xsl:for-each select="$input">
		
		<xsl:variable name="name">
			<xsl:call-template name="ConvertPrefix">
				<xsl:with-param name="name" select="name(.)"/>
			</xsl:call-template>
		</xsl:variable>

		<xsl:element name="{$name}">
		
			<xsl:for-each select="@*">
				<xsl:variable name="attrName">
					<xsl:call-template name="ConvertPrefix">
						<xsl:with-param name="name" select="name(.)"/>
					</xsl:call-template>
				</xsl:variable>
				
				<xsl:attribute name="{$attrName}"><xsl:value-of select="."/></xsl:attribute>

			</xsl:for-each>
			
			<!-- if there are sub-elements then add a newline and recurs process them -->
			<xsl:choose>
				<xsl:when test="*">
					<xsl:call-template name="CopyAndConvertPrefix">
						<xsl:with-param name="input" select="*"/>
					</xsl:call-template>
				</xsl:when>
				<xsl:otherwise>
					<xsl:variable name="textVal"><xsl:value-of select="text()"/></xsl:variable>
					<xsl:if test="$textVal != ''">
						<xsl:value-of select="$textVal"/>
					</xsl:if>	
				</xsl:otherwise>
				
			</xsl:choose>

			<!-- and then append any text -->	
		
		</xsl:element>

	</xsl:for-each>
	
</xsl:template>


<!--
===============================================================================
	Return proper ns prefix for name in format xxx_Name
===============================================================================
-->
<xsl:template name="ConvertPrefix">
	<xsl:param name="name"/>
	
		<xsl:variable name="prefix">
			<xsl:choose>
				<xsl:when test="contains(local-name(.), '_')"><xsl:value-of select="substring-before(local-name(.), '_')"/></xsl:when>
				<xsl:otherwise></xsl:otherwise>
			</xsl:choose>
		</xsl:variable>

		<xsl:variable name="shortName">
			<xsl:choose>
				<xsl:when test="contains(local-name(.), '_')"><xsl:value-of select="substring-after(local-name(.), '_')"/></xsl:when>
				<xsl:otherwise><xsl:value-of select="local-name(.)"/></xsl:otherwise>
			</xsl:choose>
		</xsl:variable>

		<xsl:choose>
			<xsl:when test="$prefix != ''"><xsl:value-of select="$prefix"/>:<xsl:value-of select="$shortName"/></xsl:when>
			<xsl:otherwise><xsl:value-of select="$shortName"/></xsl:otherwise>
		</xsl:choose>

</xsl:template>


<!--
===============================================================================
	Ditch ext attrs that pre-mgrigration import has placed xpdl2 nodes in
===============================================================================
-->
<xsl:template match="xpdl:ExtendedAttribute[*[contains(name(), '_')]]">
</xsl:template>

<xsl:template match="xpdl:ExtendedAttribute[starts-with(@Name, 'v2XPD')]">
</xsl:template>

<xsl:template match="xpdl:ExtendedAttribute[starts-with(@Name, 'Task_xpdl2_TaskService_Script')]">
</xsl:template>

<!--
===============================================================================
	Convert destination environment,
===============================================================================
-->
<xsl:template match="xpdl:ExtendedAttribute[@Name = 'DestinationEnvironments']">
	<xsl:for-each select="extensions:DestinationEnvironments/extensions:DestinationEnvironment">
		<xsl:choose>
			<xsl:when test="starts-with(@Id, 'simulation1')">
				<!-- auto-upgrade simulation dest env, it should work. -->
				<xpdl2:ExtendedAttribute Name="Destination" Value="simulation1.2"/>
			</xsl:when>
			<xsl:otherwise>
				<xpdl2:ExtendedAttribute Name="Destination" Value="{@Id}"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:for-each>

</xsl:template>

<!--
===============================================================================
	strip extensions completely for now
===============================================================================
-->
	<xsl:template match="extensions:*">
	</xsl:template>

	
	
<!--
===============================================================================
	General template to copy all nodes
===============================================================================
-->
	<xsl:template match="node()">
		<xsl:if test="name() != ''">
			<xsl:element name="{name()}">
				<xsl:apply-templates select="@* | node()"/>
			</xsl:element>
		</xsl:if>
	</xsl:template>
	
<!--
===============================================================================
	General template to copy all attributes and text of an element
===============================================================================
-->
	<xsl:template match="@* | text()">
		<!-- Copy input attribute to output. -->
		<xsl:copy>
			<xsl:apply-templates select="@* | text()"/>
		</xsl:copy>
	</xsl:template>


</xsl:stylesheet>

