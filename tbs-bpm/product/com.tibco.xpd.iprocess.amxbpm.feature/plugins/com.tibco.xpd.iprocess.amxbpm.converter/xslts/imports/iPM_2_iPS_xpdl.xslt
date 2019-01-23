<?xml version="1.0" encoding="UTF-8"?>
<!--
===================================================================================================================
XSLT:		iPM_2_iPS_xpdl.xslt

DESCRIPTION:
	This xslt will convert the iPM XPDL to iPS XPDL.

===================================================================================================================
-->

<!-- 
	Define constant strings 
-->
<!DOCTYPE xsl:stylesheet [
	<!ENTITY laneHorizontal				'Horizontal'>
	<!ENTITY defaultPool					'Default Pool'>
	<!ENTITY extraLaneName			'[Outside Lane]'>
	<!ENTITY defaultLane					'Default Lane'>
	<!ENTITY defaultActivityWidth 	'96'>
	<!ENTITY defaultActivityHeight	'64'>
	<!ENTITY complexRouterWidth 	'48'>
	<!ENTITY complexRouterHeight	'48'>
	<!ENTITY eventDiameter 	'28'>
	<!ENTITY vendor							'TIBCO'>
	<!ENTITY iPSFormatVersion 		'1'>
	<!ENTITY noteIdPrefix					'n'>
]>

<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"

	xmlns="http://www.wfmc.org/2002/XPDL1.0"
	xmlns:xpdl="http://www.wfmc.org/2002/XPDL1.0"
	xmlns:sw="http://bpm.tibco.com/2004/SWSPDXML2.0" 
	xmlns:extensions="http://www.tibco.com/xpd/XpdlExtensions1.0.0"
	
	xmlns:xpdl2="http://www.wfmc.org/2004/XPDL2.0alpha"
	xmlns:xpdExt="http://www.tibco.com/XPD/xpdExtension1.0.0"
	xmlns:iProcessExt="http://www.tibco.com/XPD/iProcessExt1.0.0"
	xmlns:simulation="http://www.tibco.com/xpd/Simulation1.0.1"
	xmlns:database="http://www.tibco.com/XPD/database1.0.0"
	xmlns:email="http://www.tibco.com/XPD/email1.0.0"
    xmlns:java="http://xml.apache.org/xslt/java"
    xmlns:eaijava="http://www.staffware.com/2003/EAIJavaPlugin"
    xmlns:eaibw="http://www.staffware.com/2002/BWEAIPlugin"
    xmlns:eaiws="http://www.staffware.com/2002/WebServicesEAIPlugin"
	
	exclude-result-prefixes="java"	
	>

<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>

<xsl:param name="project"/>

<!-- 
===================================================================================================================
	Keep these in line with those variables in iPS_2iPM_xpdl.xslt 
===================================================================================================================
-->
<xsl:variable name="zmsg_Participant">Participant</xsl:variable>
<xsl:variable name="zmsg_Parameters">Parameters</xsl:variable>
<xsl:variable name="zmsg_ActMarkers">Activity Markers</xsl:variable>

<!-- 
===================================================================================================================
	Scaling factor for import (objects in iPS take up more room than those in iPM 

	YOU MUST KEEP THIS IN SYNCH WITH IPS_2_IPM_XPDL.XSLT !!
===================================================================================================================
-->
<xsl:variable name="scaleX" select="'1.5'"/>
<xsl:variable name="scaleY" select="'1.2'"/> <!-- Just in case we want to scale different vertically. -->

<xsl:variable name="jmsTargetSet" select="java:java.util.HashSet.new()"/>

<!--
===================================================================================================================
	XSLT processing start point (process whole document)
===================================================================================================================
-->
<xsl:template match="/">

    <!-- We need to save and index embedded WSDL files first. -->
    <xsl:apply-templates mode="wsdl"/>

	<xsl:apply-templates/>
	
</xsl:template>

<!--
===================================================================================================================
	xpdl:Package
===================================================================================================================
-->
<xsl:template match="xpdl:Package">

	<Package>

		<!-- Recurse through all child attributes and elements -->
		<xsl:apply-templates select="@* | node()" />
		<!--Add extension elements after extended attributes -->
		<xsl:call-template name="OutputProcessInterfaces"/>

		<!-- Leave a tag behind for iProcessAfterMigrateV8.xslt so it can tell that migration is done because of import from iProcess. -->
		<iProcessExt:MigratedFromiProcess/>
		
	</Package>
	
</xsl:template>

<!--
===================================================================================================================
	Output Process Interfaces for ProcType =IOTemplate
	Context: Can be called from anywhere

	SIA-2 Import Proc Ifc
===================================================================================================================
-->
<xsl:template name="OutputProcessInterfaces">

	<xpdExt:ProcessInterfaces>
	
		<!-- For each process, check whether it is IOTEMPLATE and create ProcessInteface -->
		<xsl:for-each select="/xpdl:Package/xpdl:WorkflowProcesses/xpdl:WorkflowProcess">
			
			<!-- Get ProcType -->
			<xsl:variable name="procProperties" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name ='ExtAttr']/sw:ExtAttr[@Name = 'ProcProperties']/sw:ProcProperties" />
			<!-- If the Workflow Process is an IOTEMPLATE create a ProcessInterface for it -->
			<xsl:if test="$procProperties/sw:ProcType = 'IOTEMPLATE' ">

				<!-- SIA-46: Sometimes it appears that iProcess Modeler outputs that same process more than once to its XPDL - we should ignore all but the first one with same id. -->
				<xsl:variable name="procId" select="@Id"/>
				<xsl:variable name="firstInterfaceWithId" select="../xpdl:WorkflowProcess[@Id = $procId][1]"/>
				
				<xsl:if test="generate-id($firstInterfaceWithId) = generate-id(.)">

					<xpdExt:ProcessInterface Name="{@Name}">
						<xsl:variable name="procIfcId" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.generateUUId()"/>
						<xsl:attribute name="Id"><xsl:value-of select="$procIfcId"/></xsl:attribute>
	
						<!-- 
							Have to assign an iProcessExt:SequenceNum attribute to each param UNFORTUNATELY this CANNOT be the same as the original
								IPT / OPT param number from iProcess BECAUSE iProcess allows IP1 and OP1 (sequence num only unique with IN or OUT)
								but studio demands that ALL are unique.
	
							This means that on re-export, the parameter "$OPT1 Customer Name" will be exported as a parameter "$OPTxx Customer Name" that is assigned from the field OPT__1 
								(i.e. the field name won't match the $OPT1 name it is assigned from - this might look a bit confusing AND also means that sub-processes, interfaces and main processes
									CANNOT be re-exported piece-meal (because the $IPT/OPT etc param ids WILL CHANGE (will be self consistent in studio thought so re-deploy of imported main-proc 
									AND sub-proc/ifc it calls WILL work).
						-->
						<xsl:attribute name="iProcessExt:NextParamSequenceNum"><xsl:value-of select="1 + count($procProperties/sw:TemplateParams/sw:TemplateInParams/sw:TemplateInParam) + count($procProperties/sw:TemplateParams/sw:TemplateOutParams/sw:TemplateOutParam)"/></xsl:attribute>
						
						<xpdExt:StartMethods>
							<xpdExt:StartMethod xpdExt:DisplayName="Start" Name="Start" Visibility="Public">
								<xsl:attribute name="Id"><xsl:value-of select="concat($procIfcId,'_StartEvent')"/></xsl:attribute>
							</xpdExt:StartMethod>
						</xpdExt:StartMethods>
						
						<FormalParameters>
							<xsl:for-each select="$procProperties/sw:TemplateParams/sw:TemplateInParams/sw:TemplateInParam">
								<xsl:variable name="paramName"><xsl:call-template name="GetParameterName"><xsl:with-param name="param" select="."/></xsl:call-template></xsl:variable>
								<!-- Output FormalParameter of Mode IN -->
								<FormalParameter Id="{generate-id()}" Name="{$paramName}" xpdExt:DisplayName="{@Description}" Mode="IN" Required="{@Required}" iProcessExt:SequenceNum="{position()}">
									
									<xsl:call-template name="OutputDataTypeForTemplateParam">
										<xsl:with-param name="templateParam" select="."/> 	
									</xsl:call-template>
									<xsl:if test="count(sw:TemplateParamAllowedValue) > 0">
										<xpdExt:InitialValues>
											<xsl:for-each select="sw:TemplateParamAllowedValue">
												<xsl:variable name="translatedValue" select="translate(@value, '&quot;', '')"/>
												<xpdExt:Value><xsl:value-of select="$translatedValue"/></xpdExt:Value>
											</xsl:for-each>
										</xpdExt:InitialValues>
									</xsl:if>
								</FormalParameter>
							
							</xsl:for-each> <!-- Next TemplateInParam -->
	
							<xsl:for-each select="$procProperties/sw:TemplateParams/sw:TemplateOutParams/sw:TemplateOutParam">
								<xsl:variable name="paramName"><xsl:call-template name="GetParameterName"><xsl:with-param name="param" select="."/></xsl:call-template></xsl:variable>
								<!-- Output FormalParameter of Mode OUT -->
								<FormalParameter Id="{generate-id()}" Name="{$paramName}" xpdExt:DisplayName="{@Description}" Mode="OUT" Required="false" iProcessExt:SequenceNum="{count($procProperties/sw:TemplateParams/sw:TemplateInParams/sw:TemplateInParam) + position()}">
								
									<xsl:call-template name="OutputDataTypeForTemplateParam">
										<xsl:with-param name="templateParam" select="."/>
									</xsl:call-template>
								</FormalParameter>
								 
							</xsl:for-each> <!-- Next TemplateOutParam -->
							
						</FormalParameters>			
	
						<!-- Set Destination environment to iPM (seeing as we've come from iPM) -->
						<ExtendedAttributes>
							<ExtendedAttribute Name="DestinationEnvironments">
								<extensions:DestinationEnvironments>
									<extensions:DestinationEnvironment Id="ipm10.x" Version="v10.x" Description="TIBCO iProcess Modeler"/>
								</extensions:DestinationEnvironments>
							</ExtendedAttribute>
						</ExtendedAttributes>
					</xpdExt:ProcessInterface>
					
				</xsl:if>
			</xsl:if>
				
		</xsl:for-each>
	</xpdExt:ProcessInterfaces>
</xsl:template>

<!--
===================================================================================================================
	Output data type for template parameter

	SIA-2 Import Proc Ifc
===================================================================================================================
-->
<xsl:template name="OutputDataTypeForTemplateParam">
	<xsl:param name="templateParam"/> <!-- Either sw:TemplateInParam or sw:TemplateOutParam -->
	
	<xsl:variable name="fieldType" select="$templateParam/sw:FieldType"/>
	
	<DataType>
		<BasicType>
			<xsl:choose>
				<xsl:when test="$fieldType = 'DATE'">
					<xsl:attribute name="Type">DATE</xsl:attribute>
				</xsl:when>
	
				<xsl:when test="$fieldType = 'TIME'">
					<xsl:attribute name="Type">TIME</xsl:attribute>
				</xsl:when>
				
				<xsl:when test="$fieldType = 'NUM'">
					<xsl:choose>

						<xsl:when test="$templateParam/@Decimals != '0'">
							<xsl:attribute name="Type">FLOAT</xsl:attribute>
							<Precision><xsl:value-of select="$templateParam/@Length"/></Precision>
							<Scale><xsl:value-of select="$templateParam/@Decimals"/></Scale>
						</xsl:when>
						
						<xsl:otherwise>
							<xsl:attribute name="Type">INTEGER</xsl:attribute>
							<Precision><xsl:value-of select="$templateParam/@Length"/></Precision>
						</xsl:otherwise>
						
					</xsl:choose>
				</xsl:when>
				
				<xsl:when test="$fieldType = 'TEXT'">
					<xsl:attribute name="Type">STRING</xsl:attribute>
					<Length><xsl:value-of select="$templateParam/@Length"/></Length>
				</xsl:when>

				<xsl:when test="$fieldType = 'MEMO'">
					<xsl:attribute name="Type">STRING</xsl:attribute>
				</xsl:when>

				<!-- SIA-77 Import COMPOSITE field types as reference so that the user will see a problem marker and have to deal with it! -->
				<xsl:when test="$fieldType = 'COMPOSITE'">
					<xsl:attribute name="Type">REFERENCE</xsl:attribute>
				</xsl:when>
				
				<xsl:otherwise>
					<xsl:text>STRING</xsl:text>
				</xsl:otherwise>
				
			</xsl:choose>
		</BasicType>
	</DataType>
	
</xsl:template>

<!--
===================================================================================================================
	xpdl:PackageHeader/xpdl:Vendor = Overwrite the vendor
===================================================================================================================
-->
	<xsl:template match="xpdl:PackageHeader/xpdl:Vendor">
		<Vendor>
			<xsl:text>&vendor;</xsl:text>
		</Vendor>
	</xsl:template>

	<xsl:template match="xpdl:Package/xpdl:ExtendedAttributes">
		<xsl:copy>
		  <ExtendedAttribute Name="FormatVersion" Value="&iPSFormatVersion;"/>
		  <ExtendedAttribute Name="ImportFrom_IPM" Value="true"/>
		</xsl:copy>
	</xsl:template>

<!--
===================================================================================================================
	xpdl:WorkflowProcess
===================================================================================================================
-->
<xsl:template match="xpdl:WorkflowProcess">
	<!-- Get ProcType -->
	<xsl:variable name="procProperties" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'ProcProperties']/sw:ProcProperties" />
	
	<!-- For Workflow Processes which are not IOTemplates ; IOTemplates are dealt with separately-->
    <xsl:if test="$procProperties/sw:ProcType != 'IOTEMPLATE'">
    
		<!-- SIA-46: Sometimes it appears that iProcess Modeler outputs that same process more than once to its XPDL - we should ignore all but the first one with same id. -->
		<xsl:variable name="procId" select="@Id"/>
		<xsl:variable name="firstProcWithId" select="../xpdl:WorkflowProcess[@Id = $procId][1]"/>
		
		<xsl:if test="generate-id($firstProcWithId) = generate-id(.)">
    
			<WorkflowProcess>
			
				<!-- Get swim lanes -->
				<xsl:variable name="swimLanes" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'SwimLanes']/sw:SwimLanes" />
				
				<!-- Get lane orientation.  If no lanes then default to horizontal -->
				<xsl:variable name="laneOrientation">
					<xsl:choose>
						<xsl:when test="$swimLanes/sw:SwimLane">
							<xsl:value-of select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'SwimLanes']/sw:SwimLanes/sw:LanesProperties/sw:LanesType" />
						</xsl:when>
						<xsl:otherwise>
							<xsl:text>&laneHorizontal;</xsl:text>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
	
				<!-- Add all nodes and attributes -->
				<xsl:apply-templates select="@*">
					<xsl:with-param name="swimLanes" select="$swimLanes" />
					<xsl:with-param name="laneOrientation" select="$laneOrientation" />
				</xsl:apply-templates>	
				
				<xsl:apply-templates select="xpdl:ProcessHeader">
					<xsl:with-param name="swimLanes" select="$swimLanes" />
					<xsl:with-param name="laneOrientation" select="$laneOrientation" />
				</xsl:apply-templates>  
				
				<xsl:if test="count(xpdl:Participants) = 0">
					<Participants>
						<xsl:call-template name="BwParticipants"/>
					</Participants>
				</xsl:if>
				
				<xsl:apply-templates select="node()[local-name() != 'ProcessHeader']">
					<xsl:with-param name="swimLanes" select="$swimLanes" />
					<xsl:with-param name="laneOrientation" select="$laneOrientation" />
				</xsl:apply-templates>  
					
				<!-- Get all non flow objects -->
				<xsl:variable name="nonFlowObjects" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'NonFlowObject']/sw:NonFlowObject" />
				<xsl:variable name="props" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'ProcProperties']/sw:ProcProperties"/>
				
				<xsl:element name="ExtendedAttributes">
					<!-- Set Destination environment to iPM (seeing as we've come from iPM) -->
					<xsl:element name="ExtendedAttribute">
						<xsl:attribute name="Name">DestinationEnvironments</xsl:attribute>
						<xsl:element name="extensions:DestinationEnvironments">
							<xsl:element name="extensions:DestinationEnvironment">
								<xsl:attribute name="Id">ipm10.x</xsl:attribute>
								<xsl:attribute name="Version">v10.x</xsl:attribute>
								<xsl:attribute name="Description">TIBCO iProcess Modeler</xsl:attribute>
							</xsl:element>
						</xsl:element>
					</xsl:element>
					
					<!-- SIA-2 Add extended attribute for carrying across process interface id -->
					<xsl:variable name="templateName" select="$procProperties/sw:SubProcParams/@TemplateName" />
		
					<xsl:if test="$templateName != ''">
						<ExtendedAttribute Name="v2XPD_ProcessInterface">
								<xpdExt:ImplementedInterface xpdExt:ProcessInterfaceId="{$templateName}"/>
						</ExtendedAttribute>
					</xsl:if>
					<!-- SIA-2 End -->
					<xsl:element name="ExtendedAttribute">
						<xsl:attribute name="Name">XPD</xsl:attribute>
						<!-- Add the default pool and all lanes (if any) to that pool -->
						<xsl:element name="extensions:Diagram">
							<xsl:element name="extensions:ZoomLevel">1.0</xsl:element>
							<xsl:element name="extensions:Pool">
								<xsl:attribute name="Id">0</xsl:attribute>
								<xsl:element name="extensions:Name">&defaultPool;</xsl:element>
								<xsl:element name="extensions:IsVisible">true</xsl:element>
								<xsl:element name="extensions:IsDefault">true</xsl:element>
								
								<!-- Add the lanes if any, otherwise add a default lane -->
								<xsl:choose>
									<!-- Add lanes -->
									<xsl:when test="$swimLanes/sw:SwimLane">
										<xsl:apply-templates select="$swimLanes/sw:SwimLane">
											<xsl:with-param name="nonFlowObjects" select="$nonFlowObjects" />
											<xsl:with-param name="laneOrientation" select="$laneOrientation" />
										</xsl:apply-templates>
										
										<!-- If there are objects outside the lanes then add an extra lane to contain these -->
										<xsl:if test="xpdl:Activities/xpdl:Activity/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'SwimLaneID']/@Value = '0' or $nonFlowObjects[@SwimLaneID = '0']/sw:Annotation">
										
											<!-- Get the Max  offset -->
											<xsl:variable name="maxOffset">
												<xsl:choose>
													<!-- Horizontal lanes so get the Max Y offset -->
													<xsl:when test="$laneOrientation = '&laneHorizontal;'">
														<xsl:call-template name="maxYOffset" />
													</xsl:when>
													<!-- Vertical lanes so get the Max X offset -->
													<xsl:otherwise>
														<xsl:call-template name="maxXOffset" />
													</xsl:otherwise>
												</xsl:choose>
												
											</xsl:variable>
											
											<!-- Get the sum of the size of all lanes -->
											<xsl:variable name="sumSizeOfLanes">
												<xsl:call-template name="GetLaneAbsOffset">
													<xsl:with-param name="swimLanes" select="$swimLanes" />
													<xsl:with-param name="laneID">0</xsl:with-param>
												</xsl:call-template>
											</xsl:variable>
											
											<xsl:element name="extensions:Lane">
												<xsl:attribute name="Id">
													<xsl:text>0</xsl:text>
												</xsl:attribute>
												
												<xsl:element name="extensions:Name">&extraLaneName;</xsl:element>
												
												<!-- Insert any notes that are in this lane -->
												<xsl:if test="$nonFlowObjects">
													<xsl:apply-templates select="$nonFlowObjects[@SwimLaneID = '0']">
														<xsl:with-param name="laneOrientation" select="$laneOrientation" />
													</xsl:apply-templates>
												</xsl:if>
												
												<!-- The size will be the max offset - the sum of all existing lanes -->
												<xsl:element name="extensions:Size">
													<xsl:call-template name="scaleY"><xsl:with-param name="value" select="$maxOffset - $sumSizeOfLanes"/></xsl:call-template>
												</xsl:element>
												
											</xsl:element>
											
										</xsl:if>
										
									</xsl:when>
									
									<!-- No lanes so add a single default lane -->
									<xsl:otherwise>
										<xsl:element name="extensions:Lane">
											<xsl:attribute name="Id">0</xsl:attribute>
											<xsl:element name="extensions:Name">&defaultLane;</xsl:element>
		
											<!-- Insert any notes -->
											<xsl:apply-templates select="$nonFlowObjects">
												<xsl:with-param name="laneOrientation" select="$laneOrientation" />
												<xsl:with-param name="laneID">0</xsl:with-param>
											</xsl:apply-templates>
											
											<xsl:element name="extensions:IsDefault">true</xsl:element>
											<!-- 
												Calculate the maximum Y offset from all objects in the process 
												No lanes, so set orientation to horizontal
											-->
											<xsl:element name="extensions:Size">
												<xsl:variable name="maxYOffset"><xsl:call-template name="maxYOffset" /></xsl:variable>
													<xsl:call-template name="scaleY"><xsl:with-param name="value" select="$maxYOffset"/></xsl:call-template>
											</xsl:element>
										</xsl:element>								
									</xsl:otherwise>
									
								</xsl:choose>
							</xsl:element>
						</xsl:element>
					</xsl:element>
					
					<!-- Add extended attributes to set proper xpdl2 length / decimals for formal params type -->
					<xsl:variable name="inParams" select="$props/sw:SubProcParams/sw:SubPInParams/sw:SubPInParam"/>
					<xsl:variable name="outParams" select="$props/sw:SubProcParams/sw:SubPOutParams/sw:SubPOutParam"/>
					<xsl:variable name="ipmParams" select="$inParams | $outParams"/>
					<xsl:variable name="dataFields" select="xpdl:DataFields/xpdl:DataField"/>
			
					<xsl:for-each select="xpdl:FormalParameters/xpdl:FormalParameter">
						<!-- This parameter should match a given data field in sub-proc. (which will have the proper type info in -->
						<xsl:variable name="pos" select="position()"/>
						
						<!-- Get the data field that this formal parmeter is mapped to. -->
						<xsl:variable name="ipmParam" select="$ipmParams[$pos]"/>
						
						<xsl:variable name="dataId" select="$ipmParam/sw:FieldRef/@fieldname | $ipmParam/sw:Expression/sw:ExprStr/text()"/>
						<xsl:variable name="dataField" select="$dataFields[@Id = $dataId]"/>
						
						<xsl:if test="$dataField">
							<ExtendedAttribute Name="v2XPD_FormalParamExtra" Value="{$dataId}">
								<xsl:call-template name="OutputFieldDimensions">
									<xsl:with-param name="dataField" select="$dataField"/>
								</xsl:call-template>
							</ExtendedAttribute>
						</xsl:if>
						
					</xsl:for-each>
					

					<!-- Copy public step definitions... -->
					<xsl:if test="$procProperties/sw:ProcPublicSteps/sw:PublicSteps/sw:PublicStep">
						<ExtendedAttribute Name="iProcessPublicStartSteps">
							<xsl:for-each select="$procProperties/sw:ProcPublicSteps/sw:PublicSteps/sw:PublicStep">
								<xsl:text>#ObjectName=</xsl:text><xsl:value-of select="@ObjectName"/><xsl:text>&#10;</xsl:text>
								
								<xsl:for-each select="sw:PublicField">
									<xsl:text>@PublicField=</xsl:text><xsl:value-of select="sw:FieldRef/@fieldname"/><xsl:text>@Required=</xsl:text><xsl:value-of select="@Required"/><xsl:text>&#10;</xsl:text>
								</xsl:for-each>
							
							</xsl:for-each>
						</ExtendedAttribute> 
					</xsl:if>

					<xsl:if test="$procProperties/sw:ProcPublicSteps/sw:PublicEvents/sw:PublicStep">
						<ExtendedAttribute Name="iProcessPublicEventSteps">
							<xsl:for-each select="$procProperties/sw:ProcPublicSteps/sw:PublicEvents/sw:PublicStep">
								<xsl:text>#ObjectName=</xsl:text><xsl:value-of select="@ObjectName"/><xsl:text>&#10;</xsl:text>
								
								<xsl:for-each select="sw:PublicField">
									<xsl:text>@PublicField=</xsl:text><xsl:value-of select="sw:FieldRef/@fieldname"/><xsl:text>@Required=</xsl:text><xsl:value-of select="@Required"/><xsl:text>&#10;</xsl:text>
								</xsl:for-each>
							
							</xsl:for-each>
						</ExtendedAttribute> 
					</xsl:if>

				</xsl:element>
				
				<!-- SIA-1 Output any standalone script step objects and other process properties -->
				<iProcessExt:ProcessProperties>
					<xsl:call-template name="AddProcessProperties"/>
				</iProcessExt:ProcessProperties>
				
				<!-- SIA-50 Output the predict duration as required. -->
				<xsl:variable name="predict" select="$props/sw:PredictDuration"/>
				<xsl:if test="$predict">
					<xsl:choose>
						<xsl:when test="$predict/sw:Duration">
							<xpdExt:DurationCalculation>
								<xsl:call-template name="AddElementIfDefined"><xsl:with-param name="name" select="'xpdExt:Years'"/><xsl:with-param name="value" select="$predict/sw:Duration/sw:Years"/></xsl:call-template>
								<xsl:call-template name="AddElementIfDefined"><xsl:with-param name="name" select="'xpdExt:Months'"/><xsl:with-param name="value" select="$predict/sw:Duration/sw:Months"/></xsl:call-template>
								<xsl:call-template name="AddElementIfDefined"><xsl:with-param name="name" select="'xpdExt:Weeks'"/><xsl:with-param name="value" select="$predict/sw:Duration/sw:Weeks"/></xsl:call-template>
								<xsl:call-template name="AddElementIfDefined"><xsl:with-param name="name" select="'xpdExt:Days'"/><xsl:with-param name="value" select="$predict/sw:Duration/sw:Days"/></xsl:call-template>
								<xsl:call-template name="AddElementIfDefined"><xsl:with-param name="name" select="'xpdExt:Hours'"/><xsl:with-param name="value" select="$predict/sw:Duration/sw:Hours"/></xsl:call-template>
								<xsl:call-template name="AddElementIfDefined"><xsl:with-param name="name" select="'xpdExt:Minutes'"/><xsl:with-param name="value" select="$predict/sw:Duration/sw:Minutes"/></xsl:call-template>
								<xsl:call-template name="AddElementIfDefined"><xsl:with-param name="name" select="'xpdExt:Seconds'"/><xsl:with-param name="value" select="$predict/sw:Duration/sw:Seconds"/></xsl:call-template>
								<xsl:call-template name="AddElementIfDefined"><xsl:with-param name="name" select="'xpdExt:Microseconds'"/><xsl:with-param name="value" select="$predict/sw:Duration/sw:MicroSeconds"/></xsl:call-template>
			
							</xpdExt:DurationCalculation>
						</xsl:when>
						<xsl:when test="$predict/sw:PredictExpression">
							<xpdExt:DurationCalculation>
								<xsl:call-template name="AddElementIfDefined"><xsl:with-param name="name" select="'xpdExt:Days'"/><xsl:with-param name="value" select="$predict/sw:PredictExpression/sw:DaysExpression/sw:Expression/sw:ExprStr"/></xsl:call-template>
								<xsl:call-template name="AddElementIfDefined"><xsl:with-param name="name" select="'xpdExt:Hours'"/><xsl:with-param name="value" select="$predict/sw:PredictExpression/sw:HoursExpression/sw:Expression/sw:ExprStr"/></xsl:call-template>
								<xsl:call-template name="AddElementIfDefined"><xsl:with-param name="name" select="'xpdExt:Minutes'"/><xsl:with-param name="value" select="$predict/sw:PredictExpression/sw:MinutesExpression/sw:Expression/sw:ExprStr"/></xsl:call-template>
								<xsl:call-template name="AddElementIfDefined"><xsl:with-param name="name" select="'xpdExt:Seconds'"/><xsl:with-param name="value" select="$predict/sw:PredictExpression/sw:SecondsExpression/sw:Expression/sw:ExprStr"/></xsl:call-template>
								<xsl:call-template name="AddElementIfDefined"><xsl:with-param name="name" select="'xpdExt:Microseconds'"/><xsl:with-param name="value" select="$predict/sw:PredictExpression/sw:MicroSecondsExpression/sw:Expression/sw:ExprStr"/></xsl:call-template>
			
							</xpdExt:DurationCalculation>
						</xsl:when>
					</xsl:choose>
					
				</xsl:if>
				
			</WorkflowProcess>
	
		</xsl:if>
	</xsl:if>
</xsl:template>

<!--
===================================================================================================================
SIA-1 Output any process proeprties including standalone script step objects. 

Context: xpdl:WorkflowProcess
===================================================================================================================
-->
<xsl:template name="AddProcessProperties">
	<!-- Output a few of the process flags. -->
	<xsl:variable name="processProperties" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'ProcProperties']/sw:ProcProperties"/>

	<xsl:attribute name="CaseDescOnStart">
		<xsl:choose>
			<xsl:when test="$processProperties/sw:CaseDescOnStart = 'optional'"><xsl:text>Optional</xsl:text></xsl:when>
			<xsl:when test="$processProperties/sw:CaseDescOnStart = 'hidden'"><xsl:text>Hidden</xsl:text></xsl:when>
			<xsl:otherwise><xsl:text>Required</xsl:text></xsl:otherwise>
		</xsl:choose>
	</xsl:attribute>
	
	<xsl:call-template name="AddAttributeIfDefined"><xsl:with-param name="name" select="'UseWorkingDays'"/><xsl:with-param name="value" select="$processProperties/@UseWorkingDays"/><xsl:with-param name="default" select="'false'"/></xsl:call-template>
	<xsl:call-template name="AddAttributeIfDefined"><xsl:with-param name="name" select="'IgnoreBlankAddressees'"/><xsl:with-param name="value" select="$processProperties/@IgnoreBlankAddressees"/><xsl:with-param name="default" select="'false'"/></xsl:call-template>
	<xsl:call-template name="AddAttributeIfDefined"><xsl:with-param name="name" select="'EnableCasePrediction'"/><xsl:with-param name="value" select="$processProperties/@PredictInBackground"/><xsl:with-param name="default" select="'false'"/></xsl:call-template>
	<xsl:call-template name="AddAttributeIfDefined"><xsl:with-param name="name" select="'NormaliseCaseData'"/><xsl:with-param name="value" select="$processProperties/@NormalizeCaseData"/><xsl:with-param name="default" select="'false'"/></xsl:call-template>
	<xsl:call-template name="AddAttributeIfDefined"><xsl:with-param name="name" select="'AutoPurge'"/><xsl:with-param name="value" select="$processProperties/@AutoPurgeCases"/><xsl:with-param name="default" select="'false'"/></xsl:call-template>

	<!-- Case start permissions expressions. -->
	<xsl:if test="$processProperties/sw:CaseAccess/sw:CaseStartAccess/sw:Expression/sw:ExprStr != ''">
		<iProcessExt:CaseStartPermissions>
			<xsl:for-each select="$processProperties/sw:CaseAccess/sw:CaseStartAccess/sw:Expression/sw:ExprStr">
				<xsl:if test="text() != ''">
					<iProcessExt:CaseStartPermission ScriptGrammar="iProcessScript"><xsl:value-of select="text()"/></iProcessExt:CaseStartPermission>
				</xsl:if>
			</xsl:for-each>
		</iProcessExt:CaseStartPermissions>
	</xsl:if>

	<!-- Case admin permissions expressions. -->
	<xsl:if test="$processProperties/sw:CaseAccess/sw:CaseAdminAccess/sw:Expression/sw:ExprStr != ''">
		<iProcessExt:CaseAdminPermissions>
			<xsl:for-each select="$processProperties/sw:CaseAccess/sw:CaseAdminAccess/sw:Expression/sw:ExprStr">
				<xsl:if test="text() != ''">
					<iProcessExt:CaseAdminPermission ScriptGrammar="iProcessScript"><xsl:value-of select="text()"/></iProcessExt:CaseAdminPermission>
				</xsl:if>
			</xsl:for-each>
		</iProcessExt:CaseAdminPermissions>
	</xsl:if>

	<!-- SIA-1 Output any standalone script step objects -->
	<xsl:variable name="scripts" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'NonFlowObject']/sw:NonFlowObject/sw:ScriptObject"/>
	<xsl:if test="$scripts">
		
		<iProcessExt:StandaloneScripts>
			<xsl:for-each select="$scripts">
				<iProcessExt:StandaloneScript Name="{@ObjectName}" Description="{sw:Description}">
					<xsl:value-of select="sw:Script/sw:ScriptText/text()"/>
				</iProcessExt:StandaloneScript>
			</xsl:for-each>
		</iProcessExt:StandaloneScripts>
		
	</xsl:if>

	

</xsl:template>

<!--
===================================================================================================================
	xpdl:FormalParameter - 
		iPM does not treat formal parameters as data fields, studio does.
		On export to ipm studio xreates data fields for each param.
		On the way in from ipm we will swap formal parameters to the fields they are mapped to.
===================================================================================================================
-->
<xsl:template match="xpdl:FormalParameters">
	<xsl:variable name="props" select="../xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'ProcProperties']/sw:ProcProperties"/>
	<xsl:variable name="inParams" select="$props/sw:SubProcParams/sw:SubPInParams/sw:SubPInParam"/>
	<xsl:variable name="outParams" select="$props/sw:SubProcParams/sw:SubPOutParams/sw:SubPOutParam"/>
	<xsl:variable name="ipmParams" select="$inParams | $outParams"/>
	<xsl:variable name="dataFields" select="../xpdl:DataFields/xpdl:DataField"/>
	
	<FormalParameters>
	
		<!-- For each SubPInParam and SubPOutParam -->
		<xsl:for-each select="$ipmParams">
			
			<xsl:variable name="ipmParam" select="."/>
			
			<!-- Ignore SubPIn/OutParams for inherited template params (these will be created as formalParameters in the interface not the sub-proc, -->
			<xsl:if test="$ipmParam/@ParamID &lt; 1000000000">
				
				<!-- subParamName would the ParamID prefixed with IP__ for local params -->
				<xsl:variable name="subParamName">
					<xsl:call-template name="GetParameterName"><xsl:with-param name="param" select="$ipmParam"/></xsl:call-template>
				</xsl:variable>

				<xsl:variable name="mode">
					<xsl:choose>
						<xsl:when test="local-name($ipmParam) = 'SubPOutParam'"><xsl:text>OUT</xsl:text></xsl:when>
						<xsl:otherwise><xsl:text>IN</xsl:text></xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				
				<FormalParameter Id="{generate-id()}" Name="{$subParamName}" xpdExt:DisplayName="{@Description}" Mode="{$mode}">

					<xsl:variable name="dataId" select="$ipmParam/sw:FieldRef/@fieldname | $ipmParam/sw:Expression/sw:ExprStr/text()"/>
					<xsl:variable name="dataField" select="$dataFields[@Id = $dataId]"/>
					
					<xsl:choose>
						<xsl:when test="$dataField">
							<!-- SIA-65: If the field that is linked to i/O param is an array then define the parameter itself as an array. -->
							<xsl:attribute name="IsArray">
								<xsl:choose>
									<xsl:when test="$dataField/@IsArray = 'TRUE'">TRUE</xsl:when>
									<xsl:otherwise>FALSE</xsl:otherwise>
								</xsl:choose>
							</xsl:attribute>
							
							<!-- If  parameter is assigned to/from a data field then set formalPara type.length from that datafield. -->
							<xsl:element name="DataType">
								<xsl:element name="BasicType">
								
									<xsl:variable name="dataType">
										<xsl:call-template name="GetDataType">
											<xsl:with-param name="dataField" select="$dataField"/>
										</xsl:call-template>
									</xsl:variable>
				
									<xsl:attribute name="Type"><xsl:value-of select="$dataType"/></xsl:attribute>
									
									<xsl:call-template name="OutputParameterDimensions">
										<xsl:with-param name="dataField" select="$dataField"/>
									</xsl:call-template>

								</xsl:element>
							</xsl:element>
				
							<xsl:copy-of select="xpdl:Description"/>
							
						</xsl:when>
						
						<xsl:otherwise>
							<!-- Otherwise parameter is assigned from an expression so can only use the ParamType hint and default values for length etc -->
							<xsl:call-template name="OutputDataTypeForParamType">
								<xsl:with-param name="paramType" select="$ipmParam/sw:ParamType"/>
							</xsl:call-template>
							
						</xsl:otherwise>
					</xsl:choose>
					<xsl:if test="count(sw:InParamAllowedValue) > 0">
						<xpdExt:InitialValues>
							<xsl:for-each select="sw:InParamAllowedValue">
								<xsl:variable name="translatedValue" select="translate(@value, '&quot;', '')"/>
								<xpdExt:Value><xsl:value-of select="$translatedValue"/></xpdExt:Value>
							</xsl:for-each>
						</xpdExt:InitialValues>
					</xsl:if>
				</FormalParameter>

			</xsl:if>
		</xsl:for-each><!-- Next SubPInParam/SubPOutParam -->
	
	</FormalParameters>

</xsl:template> 

<!--
===================================================================================================================
	Output data type for ParamType

	SIA-2 Import Proc Ifc
===================================================================================================================
-->
<xsl:template name="OutputDataTypeForParamType">
	<xsl:param name="paramType"/> <!-- sw:ParamType -->
	<DataType>
		<BasicType>
			<xsl:choose>
				<xsl:when test="$paramType = 'DATE'">
					<xsl:attribute name="Type">DATE</xsl:attribute>
				</xsl:when>
	
				<xsl:when test="$paramType = 'TIME'">
					<xsl:attribute name="Type">TIME</xsl:attribute>
				</xsl:when>
				
				<xsl:when test="$paramType = 'NUM'">
					<xsl:attribute name="Type">FLOAT</xsl:attribute>
					<Precision>10</Precision>
					<Scale>2</Scale>
				</xsl:when>
				
				<xsl:when test="$paramType = 'TEXT'">
					<xsl:attribute name="Type">STRING</xsl:attribute>
				</xsl:when>

				<xsl:when test="$paramType = 'MEMO'">
					<xsl:attribute name="Type">STRING</xsl:attribute>
				</xsl:when>
				
				<xsl:otherwise>
					<xsl:text>STRING</xsl:text>
				</xsl:otherwise>
				
			</xsl:choose>
		</BasicType>
	</DataType>
	
</xsl:template>



<!--
===================================================================================================================
	xpdl:DataFIelds - 
		iPM does not treat formal parameters as data fields, studio does.
		On export to ipm studio xreates data fields for each param.
		On the way in from ipm we will swap formal parameters to the fields they are mapped to.
	So we must ignore datafields that are going to be swapped to formal parameters.
===================================================================================================================
-->
	<xsl:template match="xpdl:WorkflowProcess/xpdl:DataFields">
	
		<xsl:variable name="props" select="../xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'ProcProperties']/sw:ProcProperties"/>
		<xsl:variable name="ipmParams" select="$props/sw:SubProcParams/sw:SubPInParams/sw:SubPInParam | $props/sw:SubProcParams/sw:SubPOutParams/sw:SubPOutParam"/>

		<xsl:variable name="dataFields" select="xpdl:DataField"/>
		
		<DataFields>
			<xsl:for-each select="$dataFields">
	
				<!-- SIA-2 : requires that all datafields be created since the subpparams are created as separate formal parameters -->
				
				<!-- Only copy data field if not used as a mapping for formal param (in which case IT WILL BECOME a formal param -->
				<xsl:apply-templates select="."/>
				<!-- SIA-2 End -->
			
			</xsl:for-each>
		
		</DataFields>
	
	
	</xsl:template>



<!--
===================================================================================================================
	xpdl:DataField = If this is a data field of type FLOAT then:
		If decimal places set then this should remain a float, 
		otherwise this should be an integer.
===================================================================================================================
-->
	<xsl:template match="xpdl:DataField">
		<xsl:copy>
			<xsl:apply-templates select="@*" />

			<xsl:element name="DataType">
				<xsl:element name="BasicType">
				
					<xsl:variable name="dataType">
						<xsl:call-template name="GetDataType">
							<xsl:with-param name="dataField" select="."/>
						</xsl:call-template>
					</xsl:variable>

					<xsl:attribute name="Type"><xsl:value-of select="$dataType"/></xsl:attribute>

				</xsl:element>
			</xsl:element>

			<!-- Add extended attributes to set proper xpdl2 length / decimals for field type -->
			<ExtendedAttributes>
				<ExtendedAttribute Name="v2XPD_DataFieldExtra">

					<xsl:call-template name="OutputFieldDimensions">
						<xsl:with-param name="dataField" select="."/>
					</xsl:call-template>
				
				</ExtendedAttribute>
			</ExtendedAttributes>
		</xsl:copy>
			
	</xsl:template>

<!--
===================================================================================================================
	Output the extra field dimension details node.
===================================================================================================================
-->
<xsl:template name="OutputFieldDimensions">
	<xsl:param name="dataField"/>

	<xsl:variable name="dataType">
		<xsl:call-template name="GetDataType">
			<xsl:with-param name="dataField" select="$dataField"/>
		</xsl:call-template>
	</xsl:variable>

	<xsl:variable name="fldDetails" select="$dataField/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'FieldDetails']/sw:FieldDetails"/>

	<xsl:choose>
		<xsl:when test="$dataType = 'STRING' and $fldDetails/@fsize != ''">
			<details>
				<length><xsl:value-of select="$fldDetails/@fsize"/></length>
			</details>
		</xsl:when>
		<xsl:when test="$dataType = 'INTEGER' and $fldDetails/@fsize != ''">
			<details>
				<precision><xsl:value-of select="$fldDetails/@fsize"/></precision>
			</details>
		</xsl:when>
		<xsl:when test="$dataType = 'FLOAT' and $fldDetails/@fsize != '' and $fldDetails/@decimal != ''">
			<details>
				<precision><xsl:value-of select="$fldDetails/@fsize - 1"/></precision>
				<scale><xsl:value-of select="$fldDetails/@decimal"/></scale>
			</details>
		</xsl:when>
	</xsl:choose>
	
</xsl:template>

<!--
===================================================================================================================
	Output the parameter dimension details form formal parameter
===================================================================================================================
-->
<xsl:template name="OutputParameterDimensions">
	<xsl:param name="dataField"/> <!-- data field that SubPInParam/SubPOutParam -->

	<xsl:variable name="dataType">
		<xsl:call-template name="GetDataType">
			<xsl:with-param name="dataField" select="$dataField"/>
		</xsl:call-template>
	</xsl:variable>

	<xsl:variable name="fldDetails" select="$dataField/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'FieldDetails']/sw:FieldDetails"/>

	<xsl:choose>
		<xsl:when test="$dataType = 'STRING' and $fldDetails/@fsize != ''">
			<Length><xsl:value-of select="$fldDetails/@fsize"/></Length>
		</xsl:when>
		<xsl:when test="$dataType = 'INTEGER' and $fldDetails/@fsize != ''">
			<Precision><xsl:value-of select="$fldDetails/@fsize"/></Precision>
		</xsl:when>
		<xsl:when test="$dataType = 'FLOAT' and $fldDetails/@fsize != '' and $fldDetails/@decimal != ''">
			<Precision><xsl:value-of select="$fldDetails/@fsize - 1"/></Precision>
			<Scale><xsl:value-of select="$fldDetails/@decimal"/></Scale>
		</xsl:when>
	</xsl:choose>
	
</xsl:template>

<!--
===================================================================================================================
	If this is a data type is FLOAT then:
		If decimal places set then this should remain a float, 
		otherwise this should be an integer.
===================================================================================================================
-->
<xsl:template name="GetDataType">
	<xsl:param name="dataField"/>
	
	<xsl:choose>
		<xsl:when test="$dataField/xpdl:DataType/xpdl:BasicType/@Type = 'DATETIME'">
			<!--  Xpdl v1 didn't have separate date/time fields but iProcess does so now xpdl2 can handle separate date / time
				  we can set imported type properly -->
			<xsl:choose>
				<xsl:when test="$dataField/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name='FieldDetails']/sw:FieldDetails/sw:FieldType = 'DATE'">
					<xsl:text>DATE</xsl:text>
				</xsl:when>
				<xsl:when test="$dataField/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name='FieldDetails']/sw:FieldDetails/sw:FieldType = 'TIME'">
					<xsl:text>TIME</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$dataField/xpdl:DataType/xpdl:BasicType/@Type"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:when>
		<xsl:when test="$dataField/xpdl:DataType/xpdl:BasicType/@Type = 'FLOAT'">
			<xsl:choose>
				<!-- If decimal value set to 0 then this is an integer, otherwise a float. -->
				<xsl:when test="$dataField/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name='FieldDetails']/sw:FieldDetails/@decimal = '0'">
					<xsl:text>INTEGER</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>FLOAT</xsl:text>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:when>

		<xsl:when test="$dataField/xpdl:DataType/xpdl:BasicType/@Type = 'STRING'">
			<!-- A string used as a participant is a performer. -->
			<xsl:variable name="fieldPartic" select="$dataField/../../xpdl:Participants/xpdl:Participant[@Name = $dataField/@Name and xpdl:ParticipantType/@Type = 'RESOURCE']"/>
			<xsl:choose>
				<xsl:when test="$fieldPartic">
					<xsl:text>PERFORMER</xsl:text>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$dataField/xpdl:DataType/xpdl:BasicType/@Type"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:when>
		
		<xsl:when test="$dataField/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name='FieldDetails']/sw:FieldDetails/sw:FieldType = 'DATE'">
			<xsl:text>DATE</xsl:text>
		</xsl:when>

		<xsl:when test="$dataField/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name='FieldDetails']/sw:FieldDetails/sw:FieldType = 'TIME'">
			<xsl:text>TIME</xsl:text>
		</xsl:when>
		
		<!-- SIA-77 Import COMPOSITE field types as reference so that the user will see a problem marker and have to deal with it! -->
		<xsl:when test="$dataField/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name='FieldDetails']/sw:FieldDetails/sw:FieldType = 'COMPOSITE'">
			<xsl:text>REFERENCE</xsl:text>
		</xsl:when>


		<xsl:otherwise>
			<xsl:value-of select="$dataField/xpdl:DataType/xpdl:BasicType/@Type"/>
		</xsl:otherwise>
	</xsl:choose>

</xsl:template>


<!--
===================================================================================================================
	extensions:Lane = Add a swim lane
===================================================================================================================
-->
<xsl:template match="sw:SwimLane">
	<xsl:param name="nonFlowObjects" />
	<xsl:param name="laneOrientation" />

	<!-- If this lane has child lanes then recurse into them as we want to only output lanes with no child lanes -->
	<xsl:choose>
		<xsl:when test="sw:SwimLane">
			<xsl:apply-templates select="sw:SwimLane">
				<xsl:with-param name="nonFlowObjects" select="$nonFlowObjects" />
				<xsl:with-param name="laneOrientation" select="$laneOrientation" />
			</xsl:apply-templates>
		</xsl:when>
		<xsl:otherwise>
			<!-- Get the lane id -->
			<xsl:variable name="laneID" select="@SwimLaneID" />
			
			<xsl:element name="extensions:Lane">
				<xsl:attribute name="Id"><xsl:value-of select="$laneID" /></xsl:attribute>
				<xsl:element name="extensions:Name"><xsl:value-of select="@Label" /></xsl:element>
				
				<!-- Insert any notes that are in this lane -->
				<xsl:if test="$nonFlowObjects">
					<xsl:apply-templates select="$nonFlowObjects[@SwimLaneID = $laneID]">
						<xsl:with-param name="laneOrientation" select="$laneOrientation" />
					</xsl:apply-templates>
				</xsl:if>
				
				<xsl:element name="extensions:Size">
					<xsl:call-template name="scaleY"><xsl:with-param name="value" select="@Size"/></xsl:call-template>
				</xsl:element>
			</xsl:element>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!--
===================================================================================================================
	xpdl:Activities
===================================================================================================================
-->
<xsl:template match="xpdl:Activities">
	<xsl:param name="swimLanes" />
	<xsl:param name="laneOrientation" />
	
	<xsl:copy>
		
		<xsl:apply-templates select="@* | node()">
			<xsl:with-param name="swimLanes" select="$swimLanes" />
			<xsl:with-param name="laneOrientation" select="$laneOrientation" />
		</xsl:apply-templates>
		
		<!-- After outputting the activities that are there, output extra activities for the error events for support of withdraw links. -->
		<xsl:call-template name="OutputWithdrawErrorEvents">
			<xsl:with-param name="laneOrientation" select="$laneOrientation" />
		</xsl:call-template>
		
		<!-- SIA-2 Begin-->
		<!-- After ouputting the activities, output extra script task activities for sub-processes -->
		<xsl:variable name="procProperties" select="../xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'ProcProperties']/sw:ProcProperties" />	

		<xsl:if test="$procProperties/sw:SubProcParams/sw:SubPInParams/sw:SubPInParam">
			<xsl:call-template name="OutputInputScriptTaskForSubProc">
				<!-- passing the workflow process that contains these activities -->
				<xsl:with-param name="workflowProcess" select=".."/>
				<xsl:with-param name="swimlanes" select="$swimLanes"/>
				<xsl:with-param name="laneOrientation" select="$laneOrientation"/>
			</xsl:call-template>
		</xsl:if>

		<!-- SIA-2 Check if SubPOutparams exist before outputting output script task -->
		<xsl:if test="$procProperties/sw:SubProcParams/sw:SubPOutParams/sw:SubPOutParam">
			<xsl:call-template name="OutputOutputScriptTaskForSubProc">
				<xsl:with-param name="workflowProcess" select=".."/>
				<xsl:with-param name="swimlanes" select="$swimLanes"/>
				<xsl:with-param name="laneOrientation" select="$laneOrientation"/>
			</xsl:call-template>
		</xsl:if>
		<!-- SIA-2 End-->

	</xsl:copy>
	
</xsl:template>

<!--
===================================================================================================================
	SIA-2
	Output a script task for a given sub process if it implements an IO Template
===================================================================================================================
-->
<xsl:template name="OutputInputScriptTaskForSubProc">
	<xsl:param name="workflowProcess"/>
	<xsl:param name="swimlanes"/>
	<xsl:param name="laneOrientation"/>
	
	
	<!-- Get ProcType -->
	<xsl:variable name="procProperties" select="$workflowProcess/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name ='ExtAttr']/sw:ExtAttr[@Name = 'ProcProperties']/sw:ProcProperties" />
	
	<xsl:if test="$procProperties/sw:SubProcParams/sw:SubPInParams/sw:SubPInParam">

		<xsl:variable name="startActivity" select="$workflowProcess/xpdl:Activities/xpdl:Activity[xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='ExtAttr']/sw:ExtAttr[@Name='ObjType' and @Value='StartObject']]" />
		<xsl:if test="$startActivity">
			<xsl:variable name="assignInputId"><xsl:call-template name="GetAssignInputParamsTaskName"><xsl:with-param name="startActivity" select="$startActivity"/></xsl:call-template></xsl:variable>
	
			<Activity Id="{$assignInputId}" Name="{$assignInputId}">
				<Description>Assign Data From Input Parameters</Description> 
				<Implementation>
					<Tool Type="APPLICATION" Id="IPASSIGN">
						<ActualParameters/>
					</Tool>
				</Implementation>
				<StartMode>
					<Automatic/>
				</StartMode>
				<FinishMode>
					<Automatic/>
				</FinishMode>
				<TransitionRestrictions/>
				<ExtendedAttributes>
					<ExtendedAttribute Name="XPD">
	
						<extensions:Activity>
							<!-- 
									Get lane ID of where this activity belongs.
									If there is no lane ID specified then set to 0 
								-->
							<xsl:variable name="laneId">
								<xsl:choose>
									<xsl:when test="$startActivity/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'SwimLaneID']/@Value != ''">
										<xsl:value-of select="$startActivity/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'SwimLaneID']/@Value" />
									</xsl:when>
									<xsl:otherwise>
										<xsl:text>0</xsl:text>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:variable>
							<xsl:attribute name="LaneId">
								<xsl:value-of select="$laneId"/>
							</xsl:attribute>
	
							<xsl:call-template name="OutputXPDExtensionsLocation">
								<xsl:with-param name="swimLanes"  select="$swimlanes"/>
								<xsl:with-param name="laneOrientation"  select="$laneOrientation"/>
								<xsl:with-param name="activity" select="$startActivity"/>
								<xsl:with-param name="laneID" select="$laneId"/>
								<xsl:with-param name="additionalXOffset" select="'30'"/>
								<xsl:with-param name="additionalYOffset" select="'-28'"/>
								<xsl:with-param name="width" select="70"/>
								<xsl:with-param name="height" select="60"/>								
							</xsl:call-template>
							
						</extensions:Activity>
					</ExtendedAttribute>
					<ExtendedAttribute Name="Task_xpdl2_TaskScript">
						<xpdl2_TaskScript>
							<!-- Use iProcessScript grammar for consistency with assign output parameters script (which has to use iPRocessScript). -->
							<xpdl2_Script ScriptGrammar="iProcessScript">
								<xsl:call-template name="OutputInputScriptDataForWorkflowProcess">
									<xsl:with-param name="workflowProcess" select="$workflowProcess"/>
								</xsl:call-template>
							</xpdl2_Script>
						</xpdl2_TaskScript>
					</ExtendedAttribute>
					<xpdl:ExtendedAttribute Name="v2XPD_Performers"/>
				</ExtendedAttributes>
			</Activity>
			<!-- Create a script task that will contain the input parameter assignments to the IO Template -->
		</xsl:if>
	</xsl:if>
	
</xsl:template>

<!--
===================================================================================================================
	SIA-2
	Template to output the output script script task for a given workflow process
===================================================================================================================
-->
<xsl:template name="OutputOutputScriptTaskForSubProc">
	<xsl:param name="workflowProcess"/>
	<xsl:param name="swimlanes"/>
	<xsl:param name="laneOrientation"/>
	
	<!-- for every stop activity or activity that has no outgoing transitions create an output script task -->
	<xsl:for-each select="$workflowProcess/xpdl:Activities/xpdl:Activity">
		<xsl:variable name="activity" select="."/>
		<xsl:variable name="isStopObject" select="$activity/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='ExtAttr']/sw:ExtAttr[@Name='ObjType']/@Value = 'StopObject'"/>
		<xsl:choose>
			<xsl:when test="$isStopObject"><!-- If the activity is a Stop Object -->
				<xsl:call-template name="OutputOutputScriptTaskActivity">
					<xsl:with-param name="workflowProcess" select="$workflowProcess"/>
					<xsl:with-param name="swimlanes" select="$swimlanes"/>
					<xsl:with-param name="laneOrientation" select="$laneOrientation"/>
					<xsl:with-param name="refActivity" select="$activity"/>
					<xsl:with-param name="isStopObject" select="$isStopObject"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="not($workflowProcess/xpdl:Transitions/xpdl:Transition[@From=$activity/@Id])"><!-- If the activity is one that has no outgoing transitions.-->
				<xsl:call-template name="OutputOutputScriptTaskActivity">
					<xsl:with-param name="workflowProcess" select="$workflowProcess"/>
					<xsl:with-param name="swimlanes" select="$swimlanes"/>
					<xsl:with-param name="laneOrientation" select="$laneOrientation"/>
					<xsl:with-param name="refActivity" select="$activity"/>
					<xsl:with-param name="isStopObject" select="$isStopObject"/>
				</xsl:call-template>
			</xsl:when>
		
		</xsl:choose>
	
	</xsl:for-each>
	
	
</xsl:template>

<!--
===================================================================================================================
	SIA-2
	Template to output the Activity element for the output script task
===================================================================================================================
-->
<xsl:template name="OutputOutputScriptTaskActivity">
	<xsl:param name="workflowProcess"/>
	<xsl:param name="swimlanes"/>
	<xsl:param name="laneOrientation"/>
	<xsl:param name="refActivity"/>
	<xsl:param name="isStopObject"/>
	
	<xsl:variable name="actId"><xsl:call-template name="GetAssignOutputParamsTaskName"><xsl:with-param name="endOfFlowActivity" select="$refActivity"/></xsl:call-template></xsl:variable>
	
	<Activity Id="{$actId}" Name="{$actId}">
		<Description>Assign Data To Output Parameters</Description> 
		<Implementation>
			<Tool Type="APPLICATION" Id="{actId}">
				<ActualParameters/>
			</Tool>
		</Implementation>
		<StartMode>
			<Automatic/>
		</StartMode>
		<FinishMode>
			<Automatic/>
		</FinishMode>
		<TransitionRestrictions/>
		<ExtendedAttributes>
			<ExtendedAttribute Name="XPD">
				<extensions:Activity>
					<!-- 
							Get lane ID of where this activity belongs.
							If there is no lane ID specified then set to 0 
						-->
					<xsl:variable name="laneId">
						<xsl:choose>
							<xsl:when test="$refActivity/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'SwimLaneID']/@Value != ''">
								<xsl:value-of select="$refActivity/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'SwimLaneID']/@Value" />
							</xsl:when>
							<xsl:otherwise>
								<xsl:text>0</xsl:text>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:variable>
					<xsl:attribute name="LaneId">
						<xsl:value-of select="$laneId"/>
					</xsl:attribute>

					<xsl:call-template name="OutputXPDExtensionsLocation">
						<xsl:with-param name="swimLanes"  select="$swimlanes"/>
						<xsl:with-param name="laneOrientation"  select="$laneOrientation"/>
						<xsl:with-param name="activity" select="$refActivity"/>
						<xsl:with-param name="laneID" select="$laneId"/>
						<xsl:with-param name="additionalXOffset">
							<xsl:choose>
								<xsl:when test="$isStopObject">-70</xsl:when>
								<xsl:otherwise>80</xsl:otherwise>
							</xsl:choose>
						</xsl:with-param>
						<xsl:with-param name="additionalYOffset">
							<xsl:choose>
								<xsl:when test="$isStopObject">-28</xsl:when>
								<xsl:otherwise>0</xsl:otherwise>
							</xsl:choose>
						</xsl:with-param>
						<xsl:with-param name="width" select="70"/>
						<xsl:with-param name="height" select="60"/>						
						<!--<xsl:with-param name="additionalXOffset" select="'-50'"/>-->
					</xsl:call-template>
					
				</extensions:Activity>
			</ExtendedAttribute>
			<ExtendedAttribute Name="Task_xpdl2_TaskScript">
				<xpdl2_TaskScript>
					<!-- define as iProcesScript grammar because assignment of sub-process fields to it's out parameters can be expressions which of course will be iProces script. -->
					<xpdl2_Script ScriptGrammar="iProcessScript">
						<xsl:call-template name="OutputOutputScriptDataForWorkflowProcess">
							<xsl:with-param name="workflowProcess" select="$workflowProcess"/>
						</xsl:call-template>
					</xpdl2_Script>
				</xpdl2_TaskScript>
			</ExtendedAttribute>
			<xpdl:ExtendedAttribute Name="v2XPD_Performers"/>
		</ExtendedAttributes>
	</Activity>
</xsl:template>

<!--
===================================================================================================================
	SIA-2
	Template to get the name of the assign output params script task to be iunserted for sub-processes.
===================================================================================================================
-->
<xsl:template name="GetAssignOutputParamsTaskName">
	<xsl:param name="endOfFlowActivity"/>
	
	<!-- Base the name of the output param assign script on the "nth position in list of activities" of the end of flow object it is being created for -->
	<xsl:variable name="activityIndex">
		<xsl:for-each select="$endOfFlowActivity/../xpdl:Activity">
			<xsl:if test="$endOfFlowActivity/@Id = @Id"><xsl:value-of select="position()"/></xsl:if>
		</xsl:for-each>
	</xsl:variable>

	<xsl:text>OP</xsl:text><xsl:value-of select="$activityIndex"/><xsl:text>ASGN</xsl:text>
	
</xsl:template>

<!--
===================================================================================================================
	SIA-2
	Template to get the name of the assign output params script task to be iunserted for sub-processes.
===================================================================================================================
-->
<xsl:template name="GetAssignInputParamsTaskName">
	<xsl:param name="startActivity"/>

	<!-- Base the name of the output param assign script on the "nth position in list of activities" of the end of flow object it is being created for -->
	<xsl:variable name="activityIndex">
		<xsl:for-each select="$startActivity/../xpdl:Activity">
			<xsl:if test="$startActivity/@Id = @Id"><xsl:value-of select="position()"/></xsl:if>
		</xsl:for-each>
	</xsl:variable>

	<xsl:text>IP</xsl:text><xsl:value-of select="$activityIndex"/><xsl:text>ASGN</xsl:text>
	
</xsl:template>
<!--
===================================================================================================================
	SIA-2
	Template to get the input script data for a given workflow process
===================================================================================================================
-->
<xsl:template name="OutputOutputScriptDataForWorkflowProcess">
	<xsl:param name="workflowProcess"/> <!-- xpdl:WorkflowProcess -->
	
	<xsl:variable name="procProperties" select="$workflowProcess/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'ProcProperties']/sw:ProcProperties" />	
	<xsl:variable name="dataFields" select="$workflowProcess/xpdl:DataFields/xpdl:DataField"/>
	
	<xsl:text>// Assign template formal parameter data to data fields &#10;</xsl:text>

	<xsl:for-each select="$procProperties/sw:SubProcParams/sw:SubPOutParams/sw:SubPOutParam">
		<!-- Input can only be mapped from field and not from expression, so expecting to be able to find datafield -->
		<xsl:variable name="paramName"><xsl:call-template name="GetParameterName"><xsl:with-param name="param" select="."/></xsl:call-template></xsl:variable>
		<xsl:variable name="dataId" select="sw:Expression/sw:ExprStr/text()"/>

		<xsl:variable name="dataField" select="$dataFields[@Id = $dataId]"/>
		<xsl:choose>
			<xsl:when test="count($dataField) != 0">
				<xsl:value-of select="$paramName"/><xsl:text> := </xsl:text><xsl:value-of select="$dataField/@Id"/><xsl:text>; // </xsl:text><xsl:value-of select="@Description"/>				
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$paramName"/><xsl:text> := </xsl:text><xsl:value-of select="$dataId"/><xsl:text>; // </xsl:text><xsl:value-of select="@Description"/>				
			</xsl:otherwise>
		</xsl:choose>

		<xsl:text>&#10;</xsl:text>
	</xsl:for-each>
</xsl:template>

<!--
===================================================================================================================
	SIA-2
	Get parameter name for a given TemplateInParam, TemplateOutParam, SubPInParam, or SubPOutParam
===================================================================================================================
-->
<xsl:template name="GetParameterName">
	<xsl:param name="param"/>
	<xsl:variable name="localParamName" select="local-name($param)"/>	

	<xsl:choose>
		<xsl:when test="$localParamName = 'TemplateInParam'"><!--If template in param -->
			<xsl:text>TPARI__</xsl:text><xsl:value-of select="$param/@ParamID - 1000000000"/>
		</xsl:when>
		
		<xsl:when test="$localParamName = 'TemplateOutParam'"> <!-- If template out param -->
			<xsl:text>TPARO__</xsl:text><xsl:value-of select="$param/@ParamID - 1000000000"/>
		</xsl:when>
		
		<xsl:when test="$localParamName = 'SubPInParam'"><!-- for local params of sub param IN-->
			<xsl:choose>
				<xsl:when test="$param/@ParamID &lt; 1000000000">
					<xsl:text>PARI__</xsl:text><xsl:value-of select="$param/@ParamID"/>
				</xsl:when>
				<xsl:when test="$param/@ParamID &gt; 1000000000">
					<xsl:text>TPARI__</xsl:text><xsl:value-of select="$param/@ParamID - 1000000000"/>
				</xsl:when>
			</xsl:choose> 
		</xsl:when>
		
		<xsl:when test="$localParamName = 'SubPOutParam'"><!-- for local params of sub param OUT-->
			<xsl:choose>
				<xsl:when test="$param/@ParamID &lt; 1000000000">
					<xsl:text>PARO__</xsl:text><xsl:value-of select="$param/@ParamID"/>
				</xsl:when>
				<xsl:when test="$param/@ParamID &gt; 1000000000">
					<xsl:text>TPARO__</xsl:text><xsl:value-of select="$param/@ParamID - 1000000000"/>
				</xsl:when>
			</xsl:choose>
		</xsl:when>
		
	</xsl:choose>
	
</xsl:template>

<!--
===================================================================================================================
	SIA-2
	Output extra error event activities to support withdraw links.
===================================================================================================================
-->
<xsl:template name="OutputWithdrawErrorEvents">
	<xsl:param name="laneOrientation"/>
	
	<xsl:variable name="withdrawLinks" select="../xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'Link']/sw:Link"/>
	<xsl:variable name="swimLanes" select="../xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'SwimLanes']/sw:SwimLanes" />
	
	<!-- 
		Look for activities that are the source of withdraw links.

		We will create an extra Error Event object for the primary output withdraws and the secondary output withdraws as newcessary. 
		SIA-46:  Not true... ComplexRouters can be XOR joins and events cannot have multi-in. (Except for ComplexRouters - which can translated straight to error events themselves).
	 -->
	<xsl:for-each select="xpdl:Activity">
		<xsl:variable name="objType" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'ObjType']/@Value"/>
		<xsl:variable name="srcId" select="@Id"/>
		
		<xsl:if test="$withdrawLinks[@srcObjID = $srcId]">
	
			<xsl:variable name="laneID">
				<xsl:choose>
					<xsl:when test="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'SwimLaneID']/@Value != ''">
						<xsl:value-of select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'SwimLaneID']/@Value" />
					</xsl:when>
					<xsl:otherwise>
						<xsl:text>0</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>

			<xsl:variable name="offsetX">
				<xsl:call-template name="scaleX">
					<xsl:with-param name="value">
						<xsl:call-template name="GetXOffset">
							<xsl:with-param name="objType" select="'$objType'"/>
							<xsl:with-param name="laneOrientation" select="$laneOrientation"/>
							<xsl:with-param name="activity" select="."/>
						</xsl:call-template>
					</xsl:with-param>
				</xsl:call-template>
			</xsl:variable>
		
			<xsl:variable name="offsetY">
				<xsl:call-template name="GetYOffset">
					<xsl:with-param name="objType" select="'$objType'"/>
					<xsl:with-param name="laneOrientation" select="$laneOrientation"/>
					<xsl:with-param name="activity" select="."/>
				</xsl:call-template>
			</xsl:variable>

			<xsl:variable name="realY">
				<!-- Get the absolute Y offset of the lane's top boundary -->
				<xsl:variable name="laneOffset">
					<xsl:call-template name="GetLaneAbsOffset">
						<xsl:with-param name="swimLanes" select="$swimLanes" />
						<xsl:with-param name="laneID" select="$laneID" />
					</xsl:call-template>
				</xsl:variable> 
				
				<!-- Get the lane size -->
				<xsl:variable name="laneSize">
					<xsl:value-of select="$swimLanes//sw:SwimLane[@SwimLaneID = $laneID]/@Size" />
				</xsl:variable>

				<xsl:variable name="relativeOffset">
					<xsl:value-of select="$offsetY - $laneOffset" />
				</xsl:variable>

				<!-- If there are swim lanes then calculate the correct Y offset, otherwise use the steps Y offset -->
				<xsl:variable name="preScaleY">
					<xsl:choose>
						<xsl:when test="$swimLanes/sw:SwimLane">
							<xsl:choose>
								<!-- 
									If the offset value plus the height of the activity is greater than the lane boundary then readjust so that the activity
									is within the lane (this does not apply if lane id of object is 0)
								-->
								<xsl:when test="$laneID != '0' and ($objType = 'StepObject' or $objType = 'EAIObject' or $objType = 'SubprocObject' or $objType = 'ComplexRouterObject') and ($relativeOffset + &defaultActivityHeight; &gt; $laneSize)">
									<xsl:value-of select="$relativeOffset - ($relativeOffset + &defaultActivityHeight; - $laneSize) - 5" />
								</xsl:when>
								<xsl:when test="$relativeOffset > 0">
									<xsl:value-of select="$relativeOffset" />
								</xsl:when>
								<!-- If the offset is becomes negative then the object was very close to top of lane so set so is inside lane. -->
								<xsl:otherwise>
									<xsl:text>1</xsl:text>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:when>
						<!-- No swimlanes so just set the Y offset -->
						<xsl:otherwise>
							<xsl:value-of select="$offsetY"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>

				<xsl:call-template name="scaleY"><xsl:with-param name="value" select="$preScaleY"/></xsl:call-template>
			</xsl:variable>


			<!-- If this object is the source of any withdraw links from it's primary output then create an extra error event activity to the right. -->
			<xsl:if test="$withdrawLinks[@srcObjID = $srcId]/sw:LinkFlags[@FromPrimary = 'true']">
				<!-- output an extra error event activity. -->
				<xpdl:Activity Id="PrimaryWithdrawEvent_{$srcId}">
					<xpdl:Implementation>
						<xpdl:No/>
					</xpdl:Implementation>
					<xpdl:ExtendedAttributes>
						<xpdl:ExtendedAttribute Name="XPD">
							<extensions:Activity LaneId="{$laneID}">
								<extensions:Location>
									<extensions:XOffset>
										<xsl:choose>
											<xsl:when test="$objType = 'StepObject' or $objType = 'EAIObject' or $objType = 'SubprocObject'"><xsl:value-of select="$offsetX + (&defaultActivityWidth; div 2) + 10 + (&eventDiameter; div 2)"/></xsl:when>
											<xsl:otherwise><xsl:value-of select="$offsetX + (&complexRouterWidth; div 2) + 10 + (&eventDiameter; div 2)"/></xsl:otherwise>
										</xsl:choose>
									</extensions:XOffset>
									<extensions:YOffset><xsl:value-of select="$realY"/></extensions:YOffset>
									<extensions:Width>&eventDiameter;</extensions:Width>
									<extensions:Height>&eventDiameter;</extensions:Height>
								</extensions:Location>
								<extensions:ActivityType>EVENT</extensions:ActivityType>
								<extensions:EventFlowType>ERROR</extensions:EventFlowType>
								<xsl:element name="extensions:EventErrorCode">
									<xsl:call-template name="GetErrorEventCode">
										<xsl:with-param name="srcObjID" select="$srcId"/>
										<xsl:with-param name="FromPrimary" select="'true'"/>
									</xsl:call-template>
								</xsl:element>
								
							</extensions:Activity>
						</xpdl:ExtendedAttribute>
					</xpdl:ExtendedAttributes>
				</xpdl:Activity>
			</xsl:if>

			<!-- and the same again if there are any secondary withdraws (from condition false / activity deadline) -->
			<xsl:if test="$withdrawLinks[@srcObjID = $srcId]/sw:LinkFlags[@FromPrimary = 'false']">
				<!-- output an extra error event activity. -->
				<xpdl:Activity Id="SecondaryWithdrawEvent_{$srcId}">
					<Implementation>
						<No/>
					</Implementation>
					<ExtendedAttributes>
						<ExtendedAttribute Name="XPD">
							<extensions:Activity LaneId="{$laneID}">
								<extensions:Location>
									<extensions:XOffset><xsl:value-of select="$offsetX"/></extensions:XOffset>
									<extensions:YOffset>
										<xsl:choose>
											<xsl:when test="$objType = 'StepObject' or $objType = 'EAIObject' or $objType = 'SubprocObject'"><xsl:value-of select="$realY + (&defaultActivityHeight; div 2) + 15 + (&eventDiameter;)"/></xsl:when>
											<xsl:otherwise><xsl:value-of select="$realY + (&complexRouterHeight; div 2) + 15 + (&eventDiameter;)"/></xsl:otherwise>
										</xsl:choose>
									</extensions:YOffset>
									<extensions:Width>&eventDiameter;</extensions:Width>
									<extensions:Height>&eventDiameter;</extensions:Height>
								</extensions:Location>
								<extensions:ActivityType>EVENT</extensions:ActivityType>
								<extensions:EventFlowType>ERROR</extensions:EventFlowType>
								<xsl:element name="extensions:EventErrorCode">
									<xsl:call-template name="GetErrorEventCode">
										<xsl:with-param name="srcObjID" select="$srcId"/>
										<xsl:with-param name="FromPrimary" select="'false'"/>
									</xsl:call-template>
								</xsl:element>
								
							</extensions:Activity>
						</ExtendedAttribute>
					</ExtendedAttributes>
				</xpdl:Activity>
			</xsl:if>
		
		</xsl:if>

	</xsl:for-each>
	
</xsl:template>

<!--
===================================================================================================================
	xpdl:Activity
===================================================================================================================
-->
<xsl:template match="xpdl:Activity">
	<xsl:param name="swimLanes" />
	<xsl:param name="laneOrientation" />
	<xsl:param name="withdrawLinks"/>
	
	<xsl:copy>
		<!-- Handle attributes first -->
		<xsl:apply-templates select="@*"/>

		<!-- Insert desctription from iPM Annotation -->
		<xsl:call-template name="addActivityDescription"/>

		<!-- Handle every child node except Description -->
		<xsl:apply-templates select="*[not(self::xpdl:Description)]">
			<xsl:with-param name="swimLanes" select="$swimLanes" />
			<xsl:with-param name="laneOrientation" select="$laneOrientation" />
		</xsl:apply-templates>
		
		<!-- SIA-1 -->
		<xsl:call-template name="OutputActivityExtensionElements"/>
			
	</xsl:copy>
	
	<!-- 
		SIA-46: If this is a complex router with multiple in AND multiple out then we need to create an additional parallel split gateway to match BPMN 
			semantic with the original iProcess semantic (XOR join lets everything thru but you can't have an XOR split without it being conditional).
	-->
	
</xsl:template>

<!-- 
===================================================================================================================
	Output activity extensions elements, these should be carried thru to final xpdl2 "as-is" by Migrate_2.

	Context: activity
===================================================================================================================
-->
<xsl:template name="OutputActivityExtensionElements">
	<xsl:variable name="objType" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'ObjType']/@Value"/>

	<xsl:if test="$objType = 'StepObject' or $objType = 'EAIObject' or $objType = 'SubprocObject' or $objType = 'DynamicSubprocObject' or $objType = 'GraftStepObject'">

		<!-- flags dependent on object type (but some flags objects have same named attributes so useful to have one variable fpor any of them. -->
		<xsl:variable name="flags" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'StepFlags' or 
					@Name = 'EAIStepFlags' or @Name = 'SubPStepFlags' or @Name = 'DynStepFlags' or @Name = 'GraftStepFlags']/node()"/>

		<!-- Prediction and duration properties. -->
		<xsl:variable name="predict" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'PredictDuration']/sw:PredictDuration"/>

		<iProcessExt:TaskProperties>

			<xsl:call-template name="AddAttributeIfDefined"><xsl:with-param name="name" select="'NoDeleteOnWithdraw'"/><xsl:with-param name="value" select="$flags/@NoDelOnWithdraw"/><xsl:with-param name="default" select="'false'"/></xsl:call-template>

			<xsl:call-template name="AddAttributeIfDefined"><xsl:with-param name="name" select="'IgnoreCaseSuspend'"/><xsl:with-param name="value" select="$flags/@IgnoreCaseSuspend"/><xsl:with-param name="default" select="'false'"/></xsl:call-template>

			<!-- Prediction properties.  ============================== -->
			<iProcessExt:Prediction>
				<xsl:call-template name="AddAttributeIfDefined"><xsl:with-param name="name" select="'UseDeadlineForDuration'"/><xsl:with-param name="value" select="$predict/@usedeadline"/><xsl:with-param name="default" select="'false'"/></xsl:call-template>
				<xsl:call-template name="AddAttributeIfDefined"><xsl:with-param name="name" select="'DontIncludeAsFutureWorkItem'"/><xsl:with-param name="value" select="$predict/@notfuture"/><xsl:with-param name="default" select="'false'"/></xsl:call-template>

				<xsl:if test="$objType = 'SubprocObject'">
					<xsl:call-template name="AddAttributeIfDefined"><xsl:with-param name="name" select="'IncludeSubProcessFutureWorkItems'"/><xsl:with-param name="value" select="$flags/@PredictIncludeSubSteps"/><xsl:with-param name="default" select="'false'"/></xsl:call-template>
					<xsl:call-template name="AddAttributeIfDefined"><xsl:with-param name="name" select="'UseSubProcessTaskDuration'"/><xsl:with-param name="value" select="$flags/@PredictUseCallStepDuration"/><xsl:with-param name="default" select="'false'"/></xsl:call-template>
				</xsl:if>
			</iProcessExt:Prediction>

			<!-- Dynamic sub-process & graft step task properties  ============================== -->
			<xsl:if test="$objType = 'DynamicSubprocObject' or $objType = 'GraftStepObject'">
			
				<xsl:variable name="dynGraftError" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'DynGraftError']/sw:DynGraftError"/>
			
				<iProcessExt:DynamicSubProcessTask IsGraftStep="false">
					<xsl:attribute name="IsGraftStep">
						<xsl:choose>
							<xsl:when test="$objType = 'GraftStepObject'">true</xsl:when>
							<xsl:otherwise>false</xsl:otherwise>
						</xsl:choose>
					</xsl:attribute>
					
					<xsl:call-template name="AddAttributeIfDefined"><xsl:with-param name="name" select="'ReturnStatusField'"/><xsl:with-param name="value" select="$dynGraftError/sw:FieldRef/@fieldname"/></xsl:call-template>
	
					<xsl:call-template name="AddAttributeIfDefined"><xsl:with-param name="name" select="'HaltOnBadSubProcess'"/><xsl:with-param name="value" select="$dynGraftError/@HaltOnInvalidSubProcs"/><xsl:with-param name="default" select="'false'"/></xsl:call-template>
					<xsl:call-template name="AddAttributeIfDefined"><xsl:with-param name="name" select="'HaltOnBadIOTemplate'"/><xsl:with-param name="value" select="$dynGraftError/@HaltOnDiffTemplate"/><xsl:with-param name="default" select="'false'"/></xsl:call-template>
					<xsl:call-template name="AddAttributeIfDefined"><xsl:with-param name="name" select="'HaltOnBadIOTemplateVersion'"/><xsl:with-param name="value" select="$dynGraftError/@HaltOnDiffTemplateVersion"/><xsl:with-param name="default" select="'false'"/></xsl:call-template>
	
					<xsl:call-template name="AddAttributeIfDefined"><xsl:with-param name="name" select="'StartStepArrayField'"/><xsl:with-param name="value" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'StartStepArray']/sw:StartStepArray/sw:FieldRef/@fieldname"/></xsl:call-template>
					
				</iProcessExt:DynamicSubProcessTask>
			</xsl:if>
	
			<!-- User Task properties.  ============================== -->
			<xsl:if test="$objType = 'StepObject'">
				<iProcessExt:UserTask>
					<xsl:call-template name="AddAttributeIfDefined"><xsl:with-param name="name" select="'Forwardable'"/><xsl:with-param name="value" select="$flags/@WorkItemForwardable"/><xsl:with-param name="default" select="'false'"/></xsl:call-template>
					<xsl:call-template name="AddAttributeIfDefined"><xsl:with-param name="name" select="'CopyEnabled'"/><xsl:with-param name="value" select="$flags/@FormEnableClipboard"/><xsl:with-param name="default" select="'false'"/></xsl:call-template>

					<!-- Priority expressions. -->
					<xsl:variable name="priority" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'StepPriority']/sw:StepPriority"/>
					
					<xsl:if test="$priority/sw:BasePriority/sw:Expression/sw:ExprStr != ''">
						<iProcessExt:PriorityValueExpression ScriptGrammar="iProcessScript"><xsl:value-of select="$priority/sw:BasePriority/sw:Expression/sw:ExprStr"/></iProcessExt:PriorityValueExpression>
					</xsl:if>
					<xsl:if test="$priority/sw:PriorityInc/sw:Expression/sw:ExprStr != ''">
						<iProcessExt:PriorityIncrementExpression ScriptGrammar="iProcessScript"><xsl:value-of select="$priority/sw:PriorityInc/sw:Expression/sw:ExprStr"/></iProcessExt:PriorityIncrementExpression>
					</xsl:if>
					<xsl:if test="$priority/sw:PriorityNumIncs/sw:Expression/sw:ExprStr != ''">
						<iProcessExt:PriorityNumIncrementsExpression ScriptGrammar="iProcessScript"><xsl:value-of select="$priority/sw:PriorityNumIncs/sw:Expression/sw:ExprStr"/></iProcessExt:PriorityNumIncrementsExpression>
					</xsl:if>
					<xsl:if test="$priority/sw:PriorityIncPeriod/sw:Expression/sw:ExprStr != ''">
						<iProcessExt:PriorityIncrementPeriodExpression ScriptGrammar="iProcessScript"><xsl:value-of select="$priority/sw:PriorityIncPeriod/sw:Expression/sw:ExprStr"/></iProcessExt:PriorityIncrementPeriodExpression>
					</xsl:if>
					<xsl:if test="$priority/sw:PriorityPeriodType/sw:Expression/sw:ExprStr != ''">
						<iProcessExt:PriorityPeriodTypeExpression ScriptGrammar="iProcessScript"><xsl:value-of select="$priority/sw:PriorityPeriodType/sw:Expression/sw:ExprStr"/></iProcessExt:PriorityPeriodTypeExpression>
					</xsl:if>

				</iProcessExt:UserTask>
			</xsl:if>

			<!-- EAITask  properties.  ============================== -->
			<xsl:if test="$objType = 'EAIObject'">
			
				<xsl:variable name="appId" select="xpdl:Implementation/xpdl:Tool/@Id"/>
				<xsl:variable name="app" select="ancestor::xpdl:WorkflowProcess/xpdl:Applications/xpdl:Application[@Id = $appId]"/>
				<xsl:variable name="eaiType" select="$app/@Name"/>
				<xsl:variable name="eaiData" select="$app/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'EAIData']/sw:EAIData"/>
				
				<xsl:choose>
					<!-- Database Task Properties ===========================
							Latest form of database task is EAIDB but the old ones have (more or less) same format so may as well include them just in case -->
					<xsl:when test="$eaiType = 'EAIDB' or $eaiType = 'EAIDB2' or $eaiType = 'EAIORA' or $eaiType = 'EAISQL'">
						<xsl:variable name="line1">
							<xsl:call-template name="FieldFromTokenLine">
								<xsl:with-param name="token" select="'&#10;'"/>
								<xsl:with-param name="line" select="$eaiData/sw:EAIRunData/sw:EAITypeData/sw:EAIText"/>
								<xsl:with-param name="idx" select="0"/>
							</xsl:call-template>									
						</xsl:variable>
	
						<iProcessExt:DatabaseTask>
							<xsl:attribute name="Server">
								<xsl:call-template name="FieldFromTokenLine">
									<xsl:with-param name="token" select="'^'"/>
									<xsl:with-param name="line" select="$line1"/>
									<xsl:with-param name="idx" select="0"/>
								</xsl:call-template>						
							</xsl:attribute>
	
							<xsl:attribute name="Schema">
								<xsl:call-template name="FieldFromTokenLine">
									<xsl:with-param name="token" select="'^'"/>
									<xsl:with-param name="line" select="$line1"/>
									<xsl:with-param name="idx" select="1"/>
								</xsl:call-template>						
							</xsl:attribute>
	
							<xsl:call-template name="OutputEAIDelayedReleaseAttributes">
								<xsl:with-param name="activity" select="."/>
							</xsl:call-template>
							
						</iProcessExt:DatabaseTask>
						
					</xsl:when>

					<!-- Web Service Task Properties =========================== -->
					<xsl:when test="$eaiType = 'EAI_WEBSERVICES'">
					    <xsl:variable name="eaidoc" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.unescape($eaiData/sw:EAIRunData/sw:EAITypeData/sw:EAIText)"/>

						<iProcessExt:WebServiceTask>
							<xsl:attribute name="InvocationStyle">
								<xsl:choose>
									<xsl:when test="$eaidoc/eaiws:Web_Services_EAI_Plugin/eaiws:Advanced_Settings/eaiws:Invocation_Style = 'DELAYED_RELEASE_MANUAL'">ManualDelayedRelease</xsl:when>
									<xsl:when test="$eaidoc/eaiws:Web_Services_EAI_Plugin/eaiws:Advanced_Settings/eaiws:Invocation_Style = 'DELAYED_RELEASE'">AutoDelayedRelease</xsl:when>
									<xsl:otherwise>AsynchWithReply</xsl:otherwise>
								</xsl:choose>
							</xsl:attribute>
						</iProcessExt:WebServiceTask>
					</xsl:when>
					
					<!-- BW Service Task Properties =========================== -->
					<xsl:when test="$eaiType = 'EAI_BW'">
						<xsl:variable name="eaidoc" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.unescape($eaiData/sw:EAIRunData/sw:EAITypeData/sw:EAIText)"/>
						
						<iProcessExt:BWTask>
							 <xsl:attribute name="ReleaseType">
								 <xsl:choose>
										<xsl:when test="$eaidoc/eaijava:EAI_Java_Plugin/eaijava:General/eaijava:Call_Type = 'Delayed'">
											<xsl:text>DelayedRelease</xsl:text>
										</xsl:when>
										<xsl:otherwise>
											<xsl:text>ImmediateRelease</xsl:text>
										</xsl:otherwise>
									</xsl:choose>
							 </xsl:attribute>

							<xsl:variable name="timeoutPayload" select="$eaidoc/eaijava:EAI_Java_Plugin/eaijava:Services/eaijava:Service_Data[eaijava:Name = 'com.staffware.integration.eaidesigntime.gui.integrationwizard.SWBWJMSTimeoutsPanel']/eaijava:Payload"/>

							<xsl:if test="$timeoutPayload != ''">
								<xsl:variable name="payloadDoc" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.unescape($timeoutPayload)"/>
								
								<iProcessExt:TimeoutReturns>
									<xsl:if test="$payloadDoc/eaibw:BW_Plugin/eaibw:JMS_Timeouts/eaibw:Response_Field/@Name != ''">
										<xsl:attribute name="ResponseTimeoutField"><xsl:value-of select="$payloadDoc/eaibw:BW_Plugin/eaibw:JMS_Timeouts/eaibw:Response_Field/@Name"/></xsl:attribute>
									</xsl:if>
									<xsl:if test="$payloadDoc/eaibw:BW_Plugin/eaibw:JMS_Timeouts/eaibw:Response_Value != ''">
										<xsl:attribute name="ResponseTimeoutValue"><xsl:value-of select="$payloadDoc/eaibw:BW_Plugin/eaibw:JMS_Timeouts/eaibw:Response_Value"/></xsl:attribute>
									</xsl:if>
									<xsl:if test="$payloadDoc/eaibw:BW_Plugin/eaibw:JMS_Timeouts/eaibw:Deadlock_Field/@Name != ''">
										<xsl:attribute name="DeadlockTimeoutField"><xsl:value-of select="$payloadDoc/eaibw:BW_Plugin/eaibw:JMS_Timeouts/eaibw:Deadlock_Field/@Name"/></xsl:attribute>
									</xsl:if>
									<xsl:if test="$payloadDoc/eaibw:BW_Plugin/eaibw:JMS_Timeouts/eaibw:Deadlock_Value != ''">
										<xsl:attribute name="DeadlockTimeoutValue"><xsl:value-of select="$payloadDoc/eaibw:BW_Plugin/eaibw:JMS_Timeouts/eaibw:Deadlock_Value"/></xsl:attribute>
									</xsl:if>
								</iProcessExt:TimeoutReturns>
								
								<iProcessExt:TimeoutConfiguration>
									<xsl:if test="$payloadDoc/eaibw:BW_Plugin/eaibw:JMS_Timeouts/eaibw:Response_Timeout != ''">
										<xsl:attribute name="ResponseTimeout"><xsl:value-of select="$payloadDoc/eaibw:BW_Plugin/eaibw:JMS_Timeouts/eaibw:Response_Timeout"/></xsl:attribute>
									</xsl:if>
									<xsl:if test="$payloadDoc/eaibw:BW_Plugin/eaibw:JMS_Timeouts/eaibw:Consume_Timeout != ''">
										<xsl:attribute name="ConsumeTimeout"><xsl:value-of select="$payloadDoc/eaibw:BW_Plugin/eaibw:JMS_Timeouts/eaibw:Consume_Timeout"/></xsl:attribute>
									</xsl:if>
									<xsl:if test="$payloadDoc/eaibw:BW_Plugin/eaibw:JMS_Timeouts/eaibw:Deadlock_Timeout != ''">
										<xsl:attribute name="DeadlockTimeout"><xsl:value-of select="$payloadDoc/eaibw:BW_Plugin/eaibw:JMS_Timeouts/eaibw:Deadlock_Timeout"/></xsl:attribute>
									</xsl:if>
									<xsl:if test="$payloadDoc/eaibw:BW_Plugin/eaibw:JMS_Timeouts/eaibw:Expiration_Timeout != ''">
										<xsl:attribute name="ExpirationTimeout"><xsl:value-of select="$payloadDoc/eaibw:BW_Plugin/eaibw:JMS_Timeouts/eaibw:Expiration_Timeout"/></xsl:attribute>
									</xsl:if>
								</iProcessExt:TimeoutConfiguration> 

							</xsl:if>

						</iProcessExt:BWTask>					
						
					</xsl:when>

					<!-- Custom (and not natively suppotred by Studio) EAI Types =========================== -->
					<xsl:otherwise>
						<iProcessExt:EAIStepTask>

							<xsl:call-template name="OutputEAIDelayedReleaseAttributes">
								<xsl:with-param name="activity" select="."/>
							</xsl:call-template>
							
						</iProcessExt:EAIStepTask>
					</xsl:otherwise>
				</xsl:choose>
				
			</xsl:if>

		</iProcessExt:TaskProperties>

		<!-- EAI Task audit script properties.  ============================== -->
		<xsl:if test="$objType = 'EAIObject'">
			<xsl:variable name="auditInitiated" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'AuditInitiated']/sw:AuditInitiated/sw:Expression/sw:ExprStr"/>
			<xsl:variable name="auditCompleted" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'AuditComplete']/sw:AuditComplete/sw:Expression/sw:ExprStr"/>
			
			<xsl:if test="$auditInitiated != '' or $auditCompleted != ''">
				<xpdExt:Audit>
					<xsl:if test="$auditInitiated != ''">
						<xpdExt:AuditEvent Type="Initiated">
							<xpdExt:Information ScriptGrammar="iProcessScript"><xsl:value-of select="$auditInitiated"/></xpdExt:Information>
						</xpdExt:AuditEvent>
					</xsl:if>
					<xsl:if test="$auditCompleted != ''">
						<xpdExt:AuditEvent Type="Completed">
							<xpdExt:Information ScriptGrammar="iProcessScript"><xsl:value-of select="$auditCompleted"/></xpdExt:Information>
						</xpdExt:AuditEvent>
					</xsl:if>
				</xpdExt:Audit>

			</xsl:if>
		</xsl:if>

		<!-- Duration properties.  ============================== -->
		<xsl:choose>
			<xsl:when test="$predict/sw:Duration">
				<xpdExt:DurationCalculation>
					<xsl:call-template name="AddElementIfDefined"><xsl:with-param name="name" select="'xpdExt:Years'"/><xsl:with-param name="value" select="$predict/sw:Duration/sw:Years"/></xsl:call-template>
					<xsl:call-template name="AddElementIfDefined"><xsl:with-param name="name" select="'xpdExt:Months'"/><xsl:with-param name="value" select="$predict/sw:Duration/sw:Months"/></xsl:call-template>
					<xsl:call-template name="AddElementIfDefined"><xsl:with-param name="name" select="'xpdExt:Weeks'"/><xsl:with-param name="value" select="$predict/sw:Duration/sw:Weeks"/></xsl:call-template>
					<xsl:call-template name="AddElementIfDefined"><xsl:with-param name="name" select="'xpdExt:Days'"/><xsl:with-param name="value" select="$predict/sw:Duration/sw:Days"/></xsl:call-template>
					<xsl:call-template name="AddElementIfDefined"><xsl:with-param name="name" select="'xpdExt:Hours'"/><xsl:with-param name="value" select="$predict/sw:Duration/sw:Hours"/></xsl:call-template>
					<xsl:call-template name="AddElementIfDefined"><xsl:with-param name="name" select="'xpdExt:Minutes'"/><xsl:with-param name="value" select="$predict/sw:Duration/sw:Minutes"/></xsl:call-template>
					<xsl:call-template name="AddElementIfDefined"><xsl:with-param name="name" select="'xpdExt:Seconds'"/><xsl:with-param name="value" select="$predict/sw:Duration/sw:Seconds"/></xsl:call-template>
					<xsl:call-template name="AddElementIfDefined"><xsl:with-param name="name" select="'xpdExt:Microseconds'"/><xsl:with-param name="value" select="$predict/sw:Duration/sw:MicroSeconds"/></xsl:call-template>

				</xpdExt:DurationCalculation>
			</xsl:when>
			<xsl:when test="$predict/sw:PredictExpression">
				<xpdExt:DurationCalculation>
					<xsl:call-template name="AddElementIfDefined"><xsl:with-param name="name" select="'xpdExt:Days'"/><xsl:with-param name="value" select="$predict/sw:PredictExpression/sw:DaysExpression/sw:Expression/sw:ExprStr"/></xsl:call-template>
					<xsl:call-template name="AddElementIfDefined"><xsl:with-param name="name" select="'xpdExt:Hours'"/><xsl:with-param name="value" select="$predict/sw:PredictExpression/sw:HoursExpression/sw:Expression/sw:ExprStr"/></xsl:call-template>
					<xsl:call-template name="AddElementIfDefined"><xsl:with-param name="name" select="'xpdExt:Minutes'"/><xsl:with-param name="value" select="$predict/sw:PredictExpression/sw:MinutesExpression/sw:Expression/sw:ExprStr"/></xsl:call-template>
					<xsl:call-template name="AddElementIfDefined"><xsl:with-param name="name" select="'xpdExt:Seconds'"/><xsl:with-param name="value" select="$predict/sw:PredictExpression/sw:SecondsExpression/sw:Expression/sw:ExprStr"/></xsl:call-template>
					<xsl:call-template name="AddElementIfDefined"><xsl:with-param name="name" select="'xpdExt:Microseconds'"/><xsl:with-param name="value" select="$predict/sw:PredictExpression/sw:MicroSecondsExpression/sw:Expression/sw:ExprStr"/></xsl:call-template>

				</xpdExt:DurationCalculation>
			</xsl:when>
		</xsl:choose>

		<!-- Sub-process output mapping script properties.  ============================== -->
		<xsl:if test="$objType = 'SubprocObject' or $objType = 'DynamicSubprocObject' or $objType = 'GraftStepObject'">
			<xsl:variable name="outputMappingScript" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']//sw:OutputMappingScript"/>
			
			<xsl:if test="$outputMappingScript/sw:Script/sw:ScriptText/text() != ''">
				<xpdExt:ScriptInformation Id="{generate-id()}_OutputMappingScript" Name="Output Mapping Script" Direction="OUT">
					<xpdExt:Expression ScriptGrammar="iProcessScript"><xsl:value-of select="$outputMappingScript/sw:Script/sw:ScriptText/text()"/></xpdExt:Expression>
				</xpdExt:ScriptInformation>
			</xsl:if>
		</xsl:if>

		<!-- Web service mappings (output as single XSLT script mapping) ==============================  -->
		<xsl:if test="$objType = 'EAIObject'">
			<xsl:variable name="appId" select="xpdl:Implementation/xpdl:Tool/@Id"/>
			<xsl:variable name="app" select="ancestor::xpdl:WorkflowProcess/xpdl:Applications/xpdl:Application[@Id = $appId]"/>
			<xsl:variable name="eaiType" select="$app/@Name"/>
			
			<xsl:if test="$eaiType = 'EAI_WEBSERVICES'">
				<xsl:variable name="eaiData" select="$app/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'EAIData']/sw:EAIData"/>
				<xsl:variable name="eaiText" select="$eaiData/sw:EAIRunData/sw:EAITypeData/sw:EAIText"/>
				<xsl:variable name="eaidoc" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.unescape($eaiText)"/>
				
				<xsl:if test="$eaidoc/eaiws:Web_Services_EAI_Plugin/eaiws:Translations/eaiws:Translation_Input/eaiws:Translation_File/text() != ''">
					<xpdExt:ScriptInformation Id="{generate-id()}_MappingIn" Direction="IN">
						<xpdExt:Expression ScriptGrammar="XSLT"><xsl:value-of select="$eaidoc/eaiws:Web_Services_EAI_Plugin/eaiws:Translations/eaiws:Translation_Input/eaiws:Translation_File" disable-output-escaping="no"/></xpdExt:Expression>
					</xpdExt:ScriptInformation>
				</xsl:if>

				<xsl:if test="$eaidoc/eaiws:Web_Services_EAI_Plugin/eaiws:Translations/eaiws:Translation_Output/eaiws:Translation_File/text() != ''">
					<xpdExt:ScriptInformation Id="{generate-id()}_MappingOut" Direction="OUT">
						<xpdExt:Expression ScriptGrammar="XSLT"><xsl:value-of select="$eaidoc/eaiws:Web_Services_EAI_Plugin/eaiws:Translations/eaiws:Translation_Output/eaiws:Translation_File" disable-output-escaping="no"/></xpdExt:Expression>
					</xpdExt:ScriptInformation>
				</xsl:if>
			</xsl:if>
		</xsl:if>
	
		<!-- TODO - more properties.  ============================== -->

	</xsl:if>
	
	<xsl:if test="$objType = 'ConditionObject'">
		<xsl:variable name="predict" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'CondPredict']/sw:CondPredict"/>
		
		<iProcessExt:ConditionProperties>
			<xsl:choose>
				<xsl:when test="$predict = 'EVALUATE'">
					<xsl:attribute name="ConditionPrediction">EvaluateExpression</xsl:attribute>
				</xsl:when>
				<xsl:when test="$predict = 'TRUE'">
					<xsl:attribute name="ConditionPrediction">AlwaysTrue</xsl:attribute>
				</xsl:when>
				<xsl:otherwise>
					<xsl:attribute name="ConditionPrediction">AlwaysFalse</xsl:attribute>
				</xsl:otherwise>
			</xsl:choose>
		</iProcessExt:ConditionProperties>
	</xsl:if>
	
</xsl:template>

<!-- 
===================================================================================================================
	OutputEAIDelayedReleaseAttributes: Output the DelayedReleaseType/Condition/Grammar as required.
===================================================================================================================
-->
<xsl:template name="OutputEAIDelayedReleaseAttributes">
	<xsl:param name="activity"/>
	
	<xsl:variable name="delayedRelease" select="$activity/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'DelayedRelease']/sw:DelayedRelease"/>

	<xsl:attribute name="DelayedReleaseType">
		<xsl:choose>
			<xsl:when test="$delayedRelease/sw:DelayedReleaseType != ''">
				<xsl:value-of select="$delayedRelease/sw:DelayedReleaseType"/>
			</xsl:when>
		<xsl:otherwise>
			<xsl:text>Never</xsl:text>
		</xsl:otherwise>
		</xsl:choose>
	</xsl:attribute>
	
	<xsl:if test="$delayedRelease/sw:DelayedReleaseType = 'Conditional'">
		<xsl:attribute name="DelayedReleaseCondition"><xsl:value-of select="$delayedRelease/sw:Expression/sw:ExprStr/text()"/></xsl:attribute>
		<xsl:attribute name="DelayedReleaseConditionScriptGrammar">iProcessScript</xsl:attribute>
	</xsl:if>
</xsl:template>


<!-- 
===================================================================================================================
	xpdl:TransitionRestrictions - Don't carry these thru for complex router objects (as we will not output them as gateways anymore.
===================================================================================================================
-->
<xsl:template match="xpdl:TransitionRestrictions">
	<xsl:variable name="objType" select="../xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'ObjType']/@Value"/>
	
	<xsl:if test="$objType != 'ComplexRouterObject'">
		<xsl:copy>
			<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:if>

</xsl:template>

<!-- 
===================================================================================================================
	xpdl:TransitionRestriction/xpdl:Split = Template to swap iPM condition object split types to XOR.
===================================================================================================================
-->
<xsl:template match="xpdl:TransitionRestriction/xpdl:Split">
	<xsl:variable name="objType" select="../../../xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'ObjType']/@Value"/>

	<xsl:choose>
		<xsl:when test="@Type = 'AND' and $objType = 'ConditionObject'">
			<xsl:copy>
				<xsl:attribute name="Type">XOR</xsl:attribute>
				<xsl:apply-templates select="node()"/>
			</xsl:copy>
		</xsl:when>
		<!-- iPS explicitly considers Activities to be XOR behaviour by default (and doesn't currently 
				write xpdl:Join Type=XOR, just leaves it empty, so we should do the same on export,
				 -->
		<xsl:when test="@Type = 'AND' and ($objType = 'StepObject' or $objType = 'EAIObject' or $objType = 'SubprocObject')">
		</xsl:when>

		<xsl:otherwise>
			<xsl:copy-of select="."/>
		</xsl:otherwise>
	</xsl:choose>
	
</xsl:template>

<!-- 
===================================================================================================================
	xpdl:TransitionRestriction/xpdl:Join = Complex router with both join and split will use an XOR Join and an AND Split.  
	As this is not supported in Studio this should be converted to a parallel gateway, which will require the Join to be converted to an AND.
===================================================================================================================
-->
<xsl:template match="xpdl:TransitionRestriction/xpdl:Join">
	<xsl:variable name="objType" select="../../../xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'ObjType']/@Value"/>
	
	<xsl:choose>
		<!-- If this is a complex router with both a Join and Split -->
		<xsl:when test="$objType = 'ComplexRouterObject' and ../../xpdl:TransitionRestriction/xpdl:Split">
			<xsl:copy>
				<xsl:attribute name="Type">AND</xsl:attribute>
				<xsl:apply-templates select="node()"/>
			</xsl:copy>
		</xsl:when>
		
		<xsl:otherwise>
			<xsl:copy-of select="."/>
		</xsl:otherwise>
		
	</xsl:choose>

</xsl:template>

<!-- 
===================================================================================================================
	xpdl:Activity/@Name		- iPS Name is either the iPM Description + iPM Extended Description or if this is empty, the iPM Name 
===================================================================================================================
-->
<!-- Sid v3.2 Alpha 13 - HAVE to output iProcess step name as Studio activity name otherwise if description contains jap characters then this causes problems. -->
<xsl:template match="xpdl:Activity/@Name">
	<!-- iPM puts "Condition_Router" in condition gateway name if object had no description label - don't carry this thru on import. -->
	<xsl:variable name="objType" select="../xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'ObjType']/@Value"/>
	
	<xsl:choose>
		<xsl:when test="$objType = 'ConditionObject' and ../xpdl:Description != ''">
			<xsl:attribute name="Name"><xsl:value-of select="../xpdl:Description"/></xsl:attribute>
		</xsl:when>
		<xsl:when test="$objType = 'ConditionObject' or $objType = 'WaitObject' or $objType = 'StopObject'">
			<!-- do nothing for condition objects without specific description -->
		</xsl:when>
		<xsl:otherwise>
			<xsl:copy/>
		</xsl:otherwise>
	</xsl:choose>

</xsl:template>

<!-- 
===================================================================================================================
	iPS Description is iPM annotation upto line of hyphens delimiter.
===================================================================================================================
-->
<xsl:template name="addActivityDescription">
	<xsl:variable name="annot" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'Annotation']/sw:Annotation/sw:AnnotText"/>
	<xsl:if test="$annot != ''">
		<!-- description is everything up to NewlineHyphenHyphenHyphenHyphenHyphen or whole thing if no delimiter -->
		<xsl:variable name="desc">
			<xsl:choose>
				<xsl:when test="contains($annot, '&#10;-----')"><xsl:value-of select="substring-before($annot, '&#10;-----')"/></xsl:when>
				<xsl:when test="contains($annot, $zmsg_Participant) or contains($annot, $zmsg_Parameters) or contains($annot, $zmsg_ActMarkers)">
					<!-- This annotation is from export to iPM from iPS but without a description part, don't carry the made up annotations thru -->
				</xsl:when>
				<xsl:otherwise><xsl:value-of select="$annot"/></xsl:otherwise>
			</xsl:choose>
		</xsl:variable>

		<xsl:if test="$desc != ''">
			<Description><xsl:value-of select="$desc"/></Description>
		</xsl:if>
	</xsl:if>
</xsl:template>


<!-- 
===================================================================================================================
	xpdl:Activity/xpdl:Implementation/xpdlTool : Add actual parameters for formflow user steps.
===================================================================================================================
-->
<xsl:template match="xpdl:Activity/xpdl:Implementation/xpdl:Tool">

	<xsl:variable name="objType" select="../../xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'ObjType']/@Value"/>

	<!-- If this is a formflow form step object then save formflow url. -->
	<xsl:variable name="formflowDef">
		<xsl:if test="$objType = 'StepObject'">
			<xsl:variable name="appId" select="../../xpdl:Implementation/xpdl:Tool/@Id"/>
			
			<xsl:variable name="app" select="../../../../xpdl:Applications/xpdl:Application[@Id = $appId]"/>
			
			<xsl:variable name="formDef" select="$app/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'FormDef']/sw:FormDef"/>
			
			<xsl:if test="$formDef/sw:AltFormType/@RunTimeFormType = 'FORMFLOW'">
				
				<xsl:value-of select="$formDef/sw:AltFormType/sw:FormTypeData/sw:FormTypeText/text()"/>
				
			</xsl:if>
		</xsl:if>
	</xsl:variable>

	<xsl:choose>
		<xsl:when test="$formflowDef != ''">
			<xsl:copy>
				<xsl:apply-templates select="@*"/>
			
				<!-- Find the field defs. -->
				<xsl:variable name="tmp1" select="substring-after($formflowDef, '&lt;fields&gt;')"/>
				<xsl:variable name="fields" select="substring-before($tmp1, '&lt;/fields&gt;')"/>
				
				<xsl:if test="$fields != ''">
					<ActualParameters>
						<xsl:call-template name="formflowFieldsToParams">
							<xsl:with-param name="fields" select="translate($fields, '&#10;', '')"/>
						</xsl:call-template>
					
					</ActualParameters>
				
				</xsl:if>
			
			</xsl:copy>
		
		</xsl:when>
		
		<xsl:otherwise>
			<xsl:copy>
				<xsl:apply-templates select="@* | node()"/>
			</xsl:copy>
		</xsl:otherwise>
	
	</xsl:choose>

</xsl:template>

<!-- 
===================================================================================================================
	formflowFieldsToParams = Output the ActualParameters for the given set of ipm formflow fields (recursive).
===================================================================================================================
-->
<xsl:template name="formflowFieldsToParams">
	<xsl:param name="fields"/>
	
	<xsl:if test="$fields != ''">
		<!-- Strip next field off the start of list. -->
		<xsl:variable name="t1" select="substring-after($fields, '&lt;name&gt;')"/>
		
		<xsl:variable name="remainder" select="substring-after($t1, '&lt;/field&gt;')"/>

		<xsl:variable name="fldName" select="substring-before($t1, '&lt;/name&gt;')"/>
		
		<xsl:if test="$fldName != ''">
			<ActualParameter><xsl:value-of select="$fldName"/></ActualParameter>
		</xsl:if>

		<xsl:call-template name="formflowFieldsToParams">
			<xsl:with-param name="fields" select="$remainder"/>
		</xsl:call-template>
		
	</xsl:if>

</xsl:template>


<!-- 
===================================================================================================================
	xpdl:Activity/xpdl:Route = If this is a start or stop event then replace xpdl:Route with xpdl:Implementation/xpdl:No
===================================================================================================================
-->
<xsl:template match="xpdl:Activity/xpdl:Route">

	<!-- Get object type -->
	<xsl:variable name="objType" select="../xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'ObjType']/@Value"/>
	
	<!-- If this is a Start or Stop object then replace xpdl:Route with xpdl:Implementation/xpdl:No -->
	<xsl:choose>
		<xsl:when test="$objType = 'StartObject' or $objType = 'StopObject'">
			<Implementation>
				<No />
			</Implementation>
		</xsl:when>
		<!-- SIA-46: Complex routers now imported as task type none -->
		<xsl:when test="$objType = 'ComplexRouterObject'">
			<Implementation>
				<No />
			</Implementation>
		</xsl:when>
		<xsl:otherwise>
			<xsl:copy-of select="." />
		</xsl:otherwise>
	</xsl:choose>

</xsl:template>


<!--
===================================================================================================================
	xpdl:Activity/xpdl:ExtendedAttributes - This will set the position and type information for each activity
===================================================================================================================
-->
<xsl:template match="xpdl:Activity/xpdl:ExtendedAttributes">
	<xsl:param name="swimLanes" />
	<xsl:param name="laneOrientation" />
	
	<xsl:variable name="objType" select="xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'ObjType']/@Value" />
	
	<xsl:variable name="act" select=".."/>

	<!-- 
		Get lane ID of where this activity belongs.
		If there is no lane ID specified then set to 0 
	-->
	<xsl:variable name="laneID">
		<xsl:choose>
			<xsl:when test="$act/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'SwimLaneID']/@Value != ''">
				<xsl:value-of select="$act/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'SwimLaneID']/@Value" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>0</xsl:text>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	
	<xsl:element name="ExtendedAttributes">
		<!-- SIA-2 Extract output of extensions:Location to another template for re-use -->
		<xsl:element name="ExtendedAttribute">
			<xsl:attribute name="Name">XPD</xsl:attribute>
			<xsl:element name="extensions:Activity">
				<xsl:attribute name="LaneId">
					<xsl:value-of select="$laneID" />
				</xsl:attribute>	
				
				<xsl:call-template name="OutputXPDExtensionsLocation">
					<xsl:with-param name="swimLanes"  select="$swimLanes"/>
					<xsl:with-param name="laneOrientation"  select="$laneOrientation"/>
					<xsl:with-param name="activity" select="$act"/>
					<xsl:with-param name="laneID" select="$laneID"/>
				</xsl:call-template>
	
				<!-- Convert the steps into appropriate activity types -->
				<xsl:call-template name="addExtActivityType"><xsl:with-param name="activity" select="$act"/></xsl:call-template>
	
			</xsl:element>
		</xsl:element>
		<!-- SIA-2 Extract output of extensions:Location to another template for re-use -->
				
				
		<xsl:if test="$objType = 'StepObject'">
			<!-- If this is a formflow form step object then save formflow url. -->
			<xsl:variable name="appId" select="$act/xpdl:Implementation/xpdl:Tool/@Id"/>
			
			<xsl:variable name="app" select="$act/../../xpdl:Applications/xpdl:Application[@Id = $appId]"/>
			
			<xsl:variable name="formDef" select="$app/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'FormDef']/sw:FormDef"/>
			
			<!-- SID USER TASK FORMS -->
			<xsl:choose>
				<xsl:when test="$formDef/sw:AltFormType/@RunTimeFormType = 'FORMFLOW'">
					
					<xsl:variable name="def" select="$formDef/sw:AltFormType/sw:FormTypeData/sw:FormTypeText/text()"/>
					
					<xsl:variable name="ref1" select="substring-after($def, '&lt;reference&gt;')"/>
					
					<xsl:if test="$ref1 != ''">
	
						<xsl:variable name="ref2" select="substring-before($ref1, '&lt;/reference&gt;')"/>
	
						<ExtendedAttribute Name="bpmJspTask" Value="{$ref2}"/>
					
					</xsl:if>
				</xsl:when>
				<!-- 
					Standard forms get lost in import to Studio, for iPM to BPM it would be useful to leave some hints behind in extended attributes 
				-->
				<xsl:when test="$formDef">
					<xsl:call-template name="addFormInfoExtendedAttributes">
						<xsl:with-param name="act" select="$act"/>
						<xsl:with-param name="formDef" select="$formDef"/>
					</xsl:call-template>
				</xsl:when>
			</xsl:choose>			
			
			<!-- User Task Script properties.  ============================== -->
			<xsl:variable name="stepCommands" select="$act/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'StepCommands']/sw:StepCommands"/>
			<xsl:if test="$stepCommands">
				<xsl:if test="$stepCommands/sw:InitialCmd/sw:Expression/sw:ExprStr != ''">
					<xpdl:ExtendedAttribute Name="iProcessV8_UserTaskOpenScript" Value="{$stepCommands/sw:InitialCmd/sw:Expression/sw:ExprStr/text()}"/>
				</xsl:if>
				<xsl:if test="$stepCommands/sw:KeepCmd/sw:Expression/sw:ExprStr != ''">
					<xpdl:ExtendedAttribute Name="iProcessV8_UserTaskCloseScript" Value="{$stepCommands/sw:KeepCmd/sw:Expression/sw:ExprStr/text()}"/>
				</xsl:if>
				<xsl:if test="$stepCommands/sw:ReleaseCmd/sw:Expression/sw:ExprStr != ''">
					<xpdl:ExtendedAttribute Name="iProcessV8_UserTaskSubmitScript" Value="{$stepCommands/sw:ReleaseCmd/sw:Expression/sw:ExprStr/text()}"/>
				</xsl:if>
			</xsl:if>

		</xsl:if>

		<!-- 
			If this activity has a deadline expression or then add a commented out version of the script to extended attributes 
			Otherwise, if it has a deadline period, these can be handled as ConstandPeriod script in Studio.
	
			Ignore the deadline for Transaction Control steps because that re-uses sw:Deadline for it's defer delay period.
		-->
		<xsl:if test="$objType != 'TCStepObject'">
			<xsl:variable name="deadline" select="$act/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'Deadline']/sw:Deadline"/>
	
			<xsl:choose>
				<xsl:when test="$deadline/sw:DurationExpression">
					<xsl:variable name="dateExpr" select="$deadline/sw:DurationExpression/sw:DateExpression/sw:Expression/sw:ExprStr"/>
					<xsl:variable name="timeExpr" select="$deadline/sw:DurationExpression/sw:TimeExpression/sw:Expression/sw:ExprStr"/>
					
					<xsl:if test="$dateExpr != '' or $timeExpr != ''">
		
						<xpdl:ExtendedAttribute Name="v2XPD_Deadline">
							<v2XPD_Deadline ScriptGrammar="iProcessScript">
								<xsl:if test="$dateExpr != ''">
									<xsl:value-of select="$dateExpr"/><xsl:text>;;DateExpression</xsl:text><xsl:text>&#10;</xsl:text>
								</xsl:if>
								<xsl:if test="$timeExpr != ''">
									<xsl:value-of select="$timeExpr"/><xsl:text>;;TimeExpression</xsl:text><xsl:text>&#10;</xsl:text>
								</xsl:if>
							</v2XPD_Deadline>
							
						</xpdl:ExtendedAttribute>
						
					</xsl:if>
				</xsl:when>
				<xsl:when test="$deadline/sw:Duration">
					<xpdl:ExtendedAttribute Name="v2XPD_DeadlinePeriod">
						<xpdExt:ConstantPeriod>
							<xsl:if test="$deadline/sw:Duration/sw:Years != ''">
								<xsl:attribute name="Years"><xsl:value-of select="$deadline/sw:Duration/sw:Years"/></xsl:attribute>
							</xsl:if>
							<xsl:if test="$deadline/sw:Duration/sw:Months != ''">
								<xsl:attribute name="Months"><xsl:value-of select="$deadline/sw:Duration/sw:Months"/></xsl:attribute>
							</xsl:if>
							<xsl:if test="$deadline/sw:Duration/sw:Weeks != ''">
								<xsl:attribute name="Weeks"><xsl:value-of select="$deadline/sw:Duration/sw:Weeks"/></xsl:attribute>
							</xsl:if>
							<xsl:if test="$deadline/sw:Duration/sw:Days != ''">
								<xsl:attribute name="Days"><xsl:value-of select="$deadline/sw:Duration/sw:Days"/></xsl:attribute>
							</xsl:if>
							<xsl:if test="$deadline/sw:Duration/sw:Hours != ''">
								<xsl:attribute name="Hours"><xsl:value-of select="$deadline/sw:Duration/sw:Hours"/></xsl:attribute>
							</xsl:if>
							<xsl:if test="$deadline/sw:Duration/sw:Minutes != ''">
								<xsl:attribute name="Minutes"><xsl:value-of select="$deadline/sw:Duration/sw:Minutes"/></xsl:attribute>
							</xsl:if>
						</xpdExt:ConstantPeriod>
					</xpdl:ExtendedAttribute>
				</xsl:when>
			</xsl:choose>
		
			<!-- Save deadline conditions for pick up by iProcessAfterMigrateToV8.xslt later.  ============================== -->
			<xsl:if test="$deadline/sw:DeadCond[1]/sw:Expression/sw:ExprStr != ''">
				<xpdl:ExtendedAttribute Name="iProcessV8_DeadCond" Value="{$deadline/sw:DeadCond[1]/sw:Expression/sw:ExprStr/text()}"/>
			</xsl:if>
			<xsl:if test="$deadline/sw:DeadCond[2]/sw:Expression/sw:ExprStr != ''">
				<xpdl:ExtendedAttribute Name="iProcessV8_DeadCond" Value="{$deadline/sw:DeadCond[2]/sw:Expression/sw:ExprStr/text()}"/>
			</xsl:if>
			
			<!-- Conditional deadline not supported in AMX BPM -->
			
			<xsl:variable name="staticText" select="'FIXME: Original iProcess activity used the conditional deadline feature which has no direct equivalent in BPM. Conditions...'"/>
            <xsl:variable name='conditions'>
            	<xsl:choose>
            		<xsl:when test="$deadline/sw:DeadCond[1]/sw:Expression/sw:ExprStr != '' and $deadline/sw:DeadCond[2]/sw:Expression/sw:ExprStr != ''">
            			<xsl:value-of select='concat($staticText, "&#xa;", $deadline/sw:DeadCond[1]/sw:Expression/sw:ExprStr/text(), "&#xa;", $deadline/sw:DeadCond[2]/sw:Expression/sw:ExprStr/text())' />
            		</xsl:when>
               		<xsl:when test="$deadline/sw:DeadCond[1]/sw:Expression/sw:ExprStr != ''">
                		<xsl:value-of select='concat($staticText, "&#xa;", $deadline/sw:DeadCond[1]/sw:Expression/sw:ExprStr/text())' />
                	</xsl:when>
                	<xsl:when test="$deadline/sw:DeadCond[2]/sw:Expression/sw:ExprStr != ''">
                 	   <xsl:value-of select='concat($staticText, "&#xa;", $deadline/sw:DeadCond[2]/sw:Expression/sw:ExprStr/text())' />
                	</xsl:when>
           		</xsl:choose>
            </xsl:variable>

            <xsl:if test="$conditions!=''">
            	<xpdl:ExtendedAttribute Name="iProcessV8_FixMe" >
            		<xsl:value-of select="$conditions"></xsl:value-of>
          		</xpdl:ExtendedAttribute>
            </xsl:if>
			

		</xsl:if>
		
		<!-- Add any special service task definitions -->
		<xsl:if test="$objType = 'EAIObject'">

			<xsl:variable name="appId" select="$act/xpdl:Implementation/xpdl:Tool/@Id"/>
			<xsl:variable name="app" select="$act/../../xpdl:Applications/xpdl:Application[@Id = $appId]"/>
		
			<xsl:variable name="eaiType" select="$app/@Name"/>
			<xsl:variable name="eaiRunType" select="$app/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'EAIRunType']/sw:EAIRunType"/>
			<xsl:variable name="eaiData" select="$app/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'EAIData']/sw:EAIData"/>
			
			<xsl:choose>
				<!-- latest form of database task is EAIDB but the old ones have (more or less) same format so may as well include them just in case -->
				<xsl:when test="$eaiType = 'EAIDB' or $eaiType = 'EAIDB2' or $eaiType = 'EAIORA' or $eaiType = 'EAISQL'">

					<xsl:call-template name="EAIDBase2TaskService">
						<xsl:with-param name="act" select="$act"/>
						<xsl:with-param name="eaiData" select="$eaiData"/>
					</xsl:call-template>
					
				</xsl:when>
				
				<xsl:when test="$eaiType = 'EAI_MAIL'">
				
					<xsl:call-template name="EAIMail2TaskService">
						<xsl:with-param name="act" select="$act"/>
						<xsl:with-param name="eaiData" select="$eaiData"/>
					</xsl:call-template>
					
				</xsl:when>
				
				<xsl:when test="$eaiType = 'EAISCRIPT'">
					<xsl:call-template name="EAIScript2TaskScript">
						<xsl:with-param name="act" select="$act"/>
						<xsl:with-param name="eaiData" select="$eaiData"/>
					</xsl:call-template>
				</xsl:when>
				
                <xsl:when test="$eaiType = 'EAI_POJO'">
					<xsl:call-template name="EAIJavaTaskService">
						<xsl:with-param name="act" select="$act"/>
						<xsl:with-param name="eaiData" select="$eaiData"/>
					</xsl:call-template>
	                </xsl:when>

				<xsl:when test="$eaiType = 'EAI_BW'">
					<xsl:call-template name="EAIBwTaskService">
						<xsl:with-param name="act" select="$act"/>
						<xsl:with-param name="eaiData" select="$eaiData"/>
					</xsl:call-template>
				</xsl:when>
                
                <xsl:when test="$eaiType = 'EAI_WEBSERVICES'">
                    <xsl:call-template name="EAIWebServiceTaskService">
                        <xsl:with-param name="act" select="$act"/>
                        <xsl:with-param name="eaiData" select="$eaiData"/>
                    </xsl:call-template>
                </xsl:when>

				<!-- TODO - other EAI types natively handled in Studio -->

				<xsl:otherwise>
					<!-- ======== Handle anything not handled natively (such as Custom EAI Step types) ====== -->
                    <xsl:call-template name="EAICustomTaskService">
                        <xsl:with-param name="act" select="$act"/>
                        <xsl:with-param name="eaiType" select="$eaiType"/>
                        <xsl:with-param name="eaiRunType">
							<xsl:choose>
								<xsl:when test="$eaiRunType != ''"><xsl:value-of select="$eaiRunType"/></xsl:when>
								<xsl:otherwise><xsl:value-of select="$eaiType"/></xsl:otherwise>
							</xsl:choose>
                        </xsl:with-param>
                        <xsl:with-param name="eaiData" select="$eaiData"/>
                    </xsl:call-template>
                </xsl:otherwise>
				
			</xsl:choose>
			 
			<!-- Conditional delayed release not supported in AMX BPM -->
			<xsl:variable name="delayedRelease" select="$act/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'DelayedRelease']/sw:DelayedRelease"/>
		
			<xsl:if test="$delayedRelease/sw:DelayedReleaseType = 'Conditional'">
				<xsl:variable name="staticTxt" select="'FIXME: Original iProcess activity used the conditional delayed release feature which has no direct equivalent in BPM. Condition...'"/>

        		<xsl:variable name="fixMeTxt" select='concat($staticTxt, "&#xa;", $delayedRelease/sw:Expression/sw:ExprStr/text())'/>
            
        		<xpdl:ExtendedAttribute Name="iProcessV8_FixMe">
        			<xsl:value-of select="$fixMeTxt"></xsl:value-of>
      			</xpdl:ExtendedAttribute>
			</xsl:if>
		 		
		</xsl:if>
		
		<!-- SIA-2 For subflows include input and output mappings from SubProcCallParams -->
		<xsl:if test="$objType = 'SubprocObject'">
			<xsl:variable name="subProcParams" select="$act/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name ='ExtAttr']/sw:ExtAttr[@Name = 'SubProcCallParams']/sw:SubProcCallParams/sw:NewSubProcParams" />
			<!-- Get input and output mappings for the sub proc -->
			<xsl:variable name="inMappings" select="$subProcParams/sw:InputMappings/sw:InputParam"/>
			<xsl:variable name="outMappings" select="$subProcParams/sw:OutputMappings/sw:OutputParam"/>
			<!-- Consolidate mappings into one list -->
			<xsl:variable name="allMappings" select="$inMappings | $outMappings"/>
			<xsl:call-template name="OutputSubProcMappings">
				<xsl:with-param name="act" select="$act" />
				<xsl:with-param name="allMappings" select="$allMappings"/>
			</xsl:call-template>
			
			<xsl:variable name="subProcStartStep" select="translate($act/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name ='ExtAttr']/sw:ExtAttr[@Name = 'SubProcedureDetails']/sw:SubProcedureDetails/@StartStep, '&quot;', '')"/>
			<xsl:if test="$subProcStartStep != ''">
				<xpdl:ExtendedAttribute Name="iProcessV8_SubProcessStartStep" Value="{$subProcStartStep}"/>
			</xsl:if>
			
		</xsl:if>
		<!-- SIA-2 End -->
		
		<!-- SIA-2 Begin -->
		<xsl:if test="$objType = 'DynamicSubprocObject' or $objType = 'GraftStepObject'">

			<ExtendedAttribute Name="v2XPD_TemplateName"><!-- Output the template name into an extended attribute -->
				<xsl:value-of select="$act/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name ='ExtAttr']/sw:ExtAttr[@Name = 'Template']/sw:Template/@Name"/>
			</ExtendedAttribute>
			
			<xsl:variable name="templateInputMappings" select="$act/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name ='ExtAttr']/sw:ExtAttr[@Name = 'InputMappings']" />
			<xsl:variable name="templateOutputMappings" select="$act/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name ='ExtAttr']/sw:ExtAttr[@Name = 'OutputMappings']" />

			<!-- Get input and output mappings for the sub proc -->
			<xsl:variable name="inMappings" select="$templateInputMappings/sw:InputMappings/sw:InputParam"/>
			<xsl:variable name="outMappings" select="$templateOutputMappings/sw:OutputMappings/sw:OutputParam"/>

			<!-- Consolidate mappings into one list -->
			<xsl:variable name="allMappings" select="$inMappings | $outMappings"/>

			<xsl:call-template name="OutputSubProcMappings">
				<xsl:with-param name="act" select="$act" />
				<xsl:with-param name="allMappings" select="$allMappings"/>
			</xsl:call-template>

			<!-- Save SubProcessNameArray for pickup by iProcessAfterMigrateV8.xslt later. -->
			<xsl:choose>
				<xsl:when test="$objType = 'GraftStepObject'">
					<xsl:variable name="subProcNameArray" select="$act/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name ='ExtAttr']/sw:ExtAttr[@Name = 'ReturnSubProcName']/sw:ReturnSubProcName/sw:FieldRef/@fieldname"/>
					<xsl:if test="$subProcNameArray != ''">
						<xpdl:ExtendedAttribute Name="iProcessV8_SubProcNameArray" Value="{$subProcNameArray}"/>
					</xsl:if>
				</xsl:when>
				<xsl:otherwise>
					<xsl:variable name="subProcNameArray" select="$act/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name ='ExtAttr']/sw:ExtAttr[@Name = 'SubProcNameArray']/sw:SubProcNameArray/sw:FieldRef/@fieldname"/>
					<xsl:if test="$subProcNameArray != ''">
						<xpdl:ExtendedAttribute Name="iProcessV8_SubProcNameArray" Value="{$subProcNameArray}"/>
					</xsl:if>
				</xsl:otherwise>
			</xsl:choose>

		</xsl:if>
		
		<!-- If this object is a Start event in a sub process that implements a process interface, the start object is set an extended attribute which in MigrateV8 is used to set the implements
			flag on the Start event, and change the colors of the event -->
		<xsl:if test="$objType = 'StartObject'">
			<xsl:variable name="workflowProcess" select="$act/../.."/>
			<!-- If SubProcParams exists then this is a sub-process that implements a process interface-->
			<xsl:if test="$workflowProcess/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='ExtAttr']/sw:ExtAttr[@Name='ProcProperties']/sw:ProcProperties/sw:SubProcParams/@TemplateName != ''">
				<ExtendedAttribute Name="iProcessV8_ImplementsStart" Value="{$workflowProcess/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='ExtAttr']/sw:ExtAttr[@Name='ProcProperties']/sw:ProcProperties/sw:SubProcParams/@TemplateName != ''}"/>
			</xsl:if>
		</xsl:if>
		<!-- SIA-2 End -->

		<xsl:call-template name="OutputExtPerformers">
			<xsl:with-param name="activity" select="$act"/>
		</xsl:call-template>


		<!-- Output extended attributes testifying who we're withdrawn by. -->
		<xsl:variable name="withdrawLinks" select="../../../xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'Link']/sw:Link[@tgtObjID = $act/@Id]"/>

		<xsl:for-each select="$withdrawLinks">
		
			<xpdl:ExtendedAttribute Name="CatchErrorEventCode">
				<xsl:attribute name="Value">
					<xsl:call-template name="GetErrorEventCode">
						<xsl:with-param name="srcObjID" select="@srcObjID"/>
						<xsl:with-param name="FromPrimary" select="sw:LinkFlags/@FromPrimary"/>
					</xsl:call-template>
				</xsl:attribute>
			</xpdl:ExtendedAttribute>
		
		</xsl:for-each>

		<!-- Add special extended attribute from iprocess original step description so that the Studio DisplayName label can be created from it. -->
		<xsl:variable name="desc">
			<xsl:if test="$act/xpdl:Description != ''">
				<xsl:value-of select="$act/xpdl:Description"/>
			</xsl:if>
			<xsl:if test="$act/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'ExtendedDescription']/sw:ExtendedDescription != ''">
				<xsl:if test="$act/xpdl:Description != ''">
					<xsl:text> </xsl:text>
				</xsl:if>
				<xsl:value-of select="$act/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'ExtendedDescription']/sw:ExtendedDescription"/>
			</xsl:if>
		</xsl:variable>
		
		<xsl:if test="$desc != ''">
			<xpdl:ExtendedAttribute Name="DisplayName" Value="{$desc}"/>
		</xsl:if>
		
		<!-- SIA-1 Preserve StepNum (Migrate_3 will create iProcessExt:StepIndex from it. -->
		<xsl:variable name="stepNum" select="$act/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'StepNum']/@Value"/>
		<xsl:if test="$stepNum != ''">
			<xpdl:ExtendedAttribute Name="iProcessStepNum" Value="{$stepNum}"/>
		</xsl:if>
		
		<!-- Add iProcessV8_FixMe extended attributes for anything that needs it. -->
		<xsl:call-template name="AddFixMeExtendedAttributes">
			<xsl:with-param name="act" select="$act"/>
			<xsl:with-param name="objType" select="$objType"/>
		</xsl:call-template>
		
	</xsl:element>

</xsl:template>


<!--
===================================================================================================================
Standard forms get lost in import to Studio, for iPM to BPM it would be useful to leave some hints behind in extended attributes 
This can be used by customer extensions
===================================================================================================================
-->
<xsl:template name="addFormInfoExtendedAttributes">
	<xsl:param name="act"/>
	<xsl:param name="formDef"/>
	
	<!-- Output Application field marking info -->
	<xsl:for-each select="$formDef//sw:FieldMark">
		
		<xsl:choose>
			<!-- Application fields. -->
			<xsl:when test="sw:MarkType = 'ProgramNoEdit'">
				<xpdl:ExtendedAttribute>
					<xsl:attribute name="Name">IPM2BPM_FormApplicationFieldMarking</xsl:attribute>

					<xsl:choose>
						<xsl:when test="starts-with(sw:FieldCmd/sw:Expression/sw:ExprStr, 'APP,')">
							<xsl:text>FieldCommandProgram=</xsl:text><xsl:value-of select="substring(sw:FieldCmd/sw:Expression/sw:ExprStr, 5)"/><xsl:text>&#10;</xsl:text>
						</xsl:when>
						<xsl:when test="starts-with(sw:FieldCmd/sw:Expression/sw:ExprStr, 'EXP,')">
							<xsl:text>FieldCommandExpression=</xsl:text><xsl:value-of select="substring(sw:FieldCmd/sw:Expression/sw:ExprStr, 5)"/><xsl:text>&#10;</xsl:text>
						</xsl:when>
					</xsl:choose>
					
					<xsl:text>Label=</xsl:text><xsl:value-of select="sw:FieldHelp"/><xsl:text>&#10;</xsl:text>
				</xpdl:ExtendedAttribute>
			</xsl:when>
			
			<!-- Other field markings -->
			<xsl:otherwise>
				<xpdl:ExtendedAttribute>
					<xsl:attribute name="Name">IPM2BPM_FormFieldMarking_<xsl:value-of select="@fieldname"/></xsl:attribute>

					<xsl:text>MarkType=</xsl:text><xsl:value-of select="sw:MarkType"/><xsl:text>&#10;</xsl:text>
					
					<xsl:for-each select="sw:FldValidations/sw:FldValidation">
						<xsl:text>ValidValue_</xsl:text><xsl:value-of select="position()"/><xsl:text>=</xsl:text><xsl:value-of select="sw:Expression/sw:ExprStr"/><xsl:text>&#10;</xsl:text>
					</xsl:for-each>
					
					<xsl:for-each select="sw:FldCalculations/sw:FldCalculation">
						<xsl:text>FieldCalculationAndCondition_</xsl:text><xsl:value-of select="position()"/><xsl:text>=</xsl:text><xsl:value-of select="sw:FldCalculationCmd/sw:Expression/sw:ExprStr"/><xsl:text> ; </xsl:text><xsl:value-of select="sw:FldCalculationCondition/sw:Expression/sw:ExprStr"/><xsl:text>&#10;</xsl:text>
					</xsl:for-each>
					
					<xsl:if test="sw:FieldCmd/sw:Expression/sw:ExprStr != ''">
						<xsl:text>FieldCommandExpression=</xsl:text><xsl:value-of select="sw:FieldCmd/sw:Expression/sw:ExprStr"/><xsl:text>&#10;</xsl:text>
					</xsl:if>
					
				</xpdl:ExtendedAttribute>
			</xsl:otherwise>
		</xsl:choose>
	
	</xsl:for-each>
	
</xsl:template>

<!--
===================================================================================================================
Add iProcessV8_FixMe extended attributes for any activity that needs it.

These will be picked up by iProcessAfterMigrateToV8.xslt and the activity will be coloured red and have an "FIXME: xxx" annotation added 
(FIXME annotations cause a BPMN error).
===================================================================================================================
-->
<xsl:template name="AddFixMeExtendedAttributes">
	<xsl:param name="act"/>
	<xsl:param name="objType"/>
		
	<!-- Transacrtion Control steps not supported. -->
	<xsl:if test="$objType = 'TCStepObject'">
			<xsl:variable name="type" select="$act/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'ControlType']/sw:ControlType"/>
			<xsl:choose>
				<xsl:when test="$type = 'ABORT'">
					<xpdl:ExtendedAttribute Name="iProcessV8_FixMe">

						<xsl:text>FIXME: There is no direct equivalent of the iProcess Abort step 
	 (to explicitly abort the database transaction containing a sequence of tasks)
     because the BPM Process Manager commits after every task and event.</xsl:text>
					</xpdl:ExtendedAttribute>
				</xsl:when>

				<xsl:otherwise>
					<xpdl:ExtendedAttribute Name="iProcessV8_ConversionInfo">
						<xsl:text>Conversion Info:
     There is no direct equivalent of this iProcess Commit step. 
     The BPM Process Manager commits after every task and event.</xsl:text>
					</xpdl:ExtendedAttribute>
				</xsl:otherwise>
			</xsl:choose>
	</xsl:if>
	
	<!-- Graft steps not supported. -->
	<xsl:if test="$objType = 'GraftStepObject'">
		<xpdl:ExtendedAttribute Name="iProcessV8_FixMe">
			<xsl:text>FIXME: There is no direct equivalent of the iProcess Graft step in BPM.</xsl:text>
		</xpdl:ExtendedAttribute>
	</xsl:if>
	
	<xsl:if test="$objType = 'EAIObject'">
        <xsl:variable name="appId" select="$act/xpdl:Implementation/xpdl:Tool/@Id"/>
        <xsl:variable name="app" select="$act/../../xpdl:Applications/xpdl:Application[@Id = $appId]"/>
    
        <xsl:variable name="eaiType" select="$app/@Name"/>
        
	</xsl:if>
	
</xsl:template>

<!--
===================================================================================================================
Output the extensions:LaneId and extensions:Location elements for the XPD exrtendedAttribute
===================================================================================================================
-->
<xsl:template name="OutputSubProcMappings">
	<xsl:param name="act"/>
	<xsl:param name="allMappings"/>
	
	<!-- Get SubProcParams-->
	<!--
	<xsl:variable name="subProcParams" select="$act/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name ='ExtAttr']/sw:ExtAttr[@Name = 'SubProcCallParams']/sw:SubProcCallParams/sw:NewSubProcParams" />
	<xsl:variable name="inMappings" select="$subProcParams/sw:InputMappings/sw:InputParam"/>
	<xsl:variable name="outMappings" select="$subProcParams/sw:OutputMappings/sw:OutputParam"/>
	<xsl:variable name="allMappings" select="$inMappings | $outMappings"/>
	-->
	<ExtendedAttribute Name="v2XPD_DataMappings">
		<DataMappings>	
		
			<xsl:variable name="dataFields" select="$act/../../xpdl:DataFields/xpdl:DataField"/>
			
			<!-- For each InputMapping or OutputMapping -->
			<xsl:for-each select="$allMappings">
				<xsl:variable name="dataId" select="sw:Expression/sw:ExprStr/text() | sw:FieldRef/@fieldname | sw:Script/sw:ScriptText/text()"/>
				
				<xsl:if test="$dataId != ''">
					<xsl:variable name="dataField" select="$dataFields[@Id = $dataId]"/>
	
					<xsl:variable name="mode">
						<xsl:choose>
							<xsl:when test="local-name(.) = 'InputParam'">
								<xsl:text>IN</xsl:text>
							</xsl:when>
							<xsl:when test="local-name(.) = 'OutputParam'">
								<xsl:text>OUT</xsl:text>
							</xsl:when>
						</xsl:choose>
	
					</xsl:variable>
					
					<xsl:variable name="paramFormalName"><!-- Extract the paramFormalName -->
						<xsl:variable name="ipOrIptVal" select="translate(@ParamName, '$', '')"/>
						
						<xsl:choose>
	
							<xsl:when test="local-name(.) = 'InputParam'"> <!-- for input params -->
								<xsl:choose>
									<xsl:when test="starts-with($ipOrIptVal, 'IPT')"> <!-- IPT__ -->
										<xsl:text>TPARI__</xsl:text><xsl:value-of select="substring-after($ipOrIptVal,'IPT')"/>
									</xsl:when>
				
									<xsl:when test="starts-with($ipOrIptVal,'IP')"> <!-- IP__ -->
										<xsl:text>PARI__</xsl:text><xsl:value-of select="substring-after($ipOrIptVal,'IP')"/>
									</xsl:when>
								</xsl:choose>
							</xsl:when>
	
							<xsl:when  test="local-name(.) = 'OutputParam'"><!-- For outputparams -->
								<xsl:choose>
									<xsl:when test="starts-with($ipOrIptVal, 'OPT')"> <!-- OPT__ -->
										<xsl:text>TPARO__</xsl:text><xsl:value-of select="substring-after($ipOrIptVal,'OPT')"/>
									</xsl:when>
				
									<xsl:when test="starts-with($ipOrIptVal,'OP')"> <!-- OP__ -->
										<xsl:text>PARO__</xsl:text><xsl:value-of select="substring-after($ipOrIptVal,'OP')"/>
									</xsl:when>
								</xsl:choose>
							</xsl:when>
						
						</xsl:choose>
					</xsl:variable>
					
					<DataMapping Direction="{$mode}" Formal="{$paramFormalName}"><!-- Add the datamappings for the subproc -->
						<xsl:choose>
							<xsl:when test="$dataField"><!-- When datafield can be resolved -->
								<Actual ScriptGrammar="iProcessScript"><xsl:value-of select="$dataId"/></Actual>
							</xsl:when>
							<xsl:otherwise>
								<xpdExt:ScriptInformation Id="{generate-id()}" Name="Script_{generate-id()}">
									<xpdExt:Expression ScriptGrammar="iProcessScript"></xpdExt:Expression>
								</xpdExt:ScriptInformation>
								
								<Actual ScriptGrammar="iProcessScript">
									<xsl:choose>
									<xsl:when test="not(contains($dataId, ';'))">
										<xsl:value-of select="concat($dataId,';')"/>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="$dataId"/>
									</xsl:otherwise>
	
									</xsl:choose>
								</Actual>
							</xsl:otherwise>
						</xsl:choose>
					
					</DataMapping>
				</xsl:if>
				
			</xsl:for-each> <!-- Next mapping -->
			
		</DataMappings>
	</ExtendedAttribute>
	
</xsl:template>

<!--
===================================================================================================================
Output the extensions:LaneId and extensions:Location elements for the XPD exrtendedAttribute
===================================================================================================================
-->
<xsl:template name="OutputXPDExtensionsLocation">
	<xsl:param name="swimLanes" />
	<xsl:param name="laneOrientation" />
	<xsl:param name="activity"/>
	<xsl:param name="laneID"/>
	<xsl:param name="additionalXOffset" select="'0'"/>
	<xsl:param name="additionalYOffset" select="'0'"/>
	<xsl:param name="width"/>
	<xsl:param name="height"/>

	<xsl:variable name="objType" select="$activity/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'ObjType']/@Value" />

	<!-- SIA-46: Comnplex routers now created as task type none (but we should reduce size) -->
	<xsl:variable name="realWidth">
		<xsl:choose>
			<xsl:when test="$width != ''">
				<xsl:value-of select="$width"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:choose>
					<xsl:when test="$objType = 'ComplexRouterObject'">&complexRouterWidth;</xsl:when>
					<xsl:otherwise>&defaultActivityWidth;</xsl:otherwise>
				</xsl:choose>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>

	<xsl:variable name="realHeight">
		<xsl:choose>
			<xsl:when test="$height != ''">
				<xsl:value-of select="$height"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:choose>
					<xsl:when test="$objType = 'ComplexRouterObject'">&complexRouterHeight;</xsl:when>
					<xsl:otherwise>&defaultActivityHeight;</xsl:otherwise>
				</xsl:choose>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>

	<!-- Get the X and Y offset of the activity - if the lane is vertical then swap the X Y values -->
	<xsl:variable name="offsetX">
		<xsl:call-template name="GetXOffset">
			<xsl:with-param name="objType" select="$objType"/>
			<xsl:with-param name="laneOrientation" select="$laneOrientation"/>
			<xsl:with-param name="activity" select="$activity"/>
		</xsl:call-template>
	</xsl:variable>

	<xsl:variable name="offsetY">
		<xsl:call-template name="GetYOffset">
			<xsl:with-param name="objType" select="$objType"/>
			<xsl:with-param name="laneOrientation" select="$laneOrientation"/>
			<xsl:with-param name="activity" select="$activity"/>
		</xsl:call-template>
	</xsl:variable>
	
	
	
	<!-- Get the absolute Y offset of the lane's top boundary -->
	<xsl:variable name="laneOffset">
		<xsl:call-template name="GetLaneAbsOffset">
			<xsl:with-param name="swimLanes" select="$swimLanes" />
			<xsl:with-param name="laneID" select="$laneID" />
		</xsl:call-template>
	</xsl:variable> 
	
	<!-- Get the lane size -->
	<xsl:variable name="laneSize">
		<xsl:value-of select="$swimLanes//sw:SwimLane[@SwimLaneID = $laneID]/@Size" />
	</xsl:variable>
	

	<xsl:element name="extensions:Location">
	
		<!-- add the X offsets  -->
		<xsl:element name="extensions:XOffset">
			<xsl:call-template name="scaleX"><xsl:with-param name="value" select="$offsetX+$additionalXOffset"/></xsl:call-template>
		</xsl:element>
		<!-- Add the Y offset -->
		<xsl:element name="extensions:YOffset">
			<!-- The Y offset will have to be relative to lane -->
			<xsl:variable name="relativeOffset">
				<xsl:value-of select="($offsetY + $additionalYOffset) - $laneOffset" />
			</xsl:variable>
			
			<!-- If there are swim lanes then calculate the correct Y offset, otherwise use the steps Y offset -->
			<xsl:variable name="preScaleY">
				<xsl:choose>
					<xsl:when test="$swimLanes/sw:SwimLane">
						<xsl:choose>
							<!-- 
								If the offset value plus the height of the activity is greater than the lane boundary then readjust so that the activity
								is within the lane (this does not apply if lane id of object is 0)
							-->
							<xsl:when test="$laneID != '0' and ($objType = 'StepObject' or $objType = 'EAIObject' or $objType = 'SubprocObject') and ($relativeOffset + $height &gt; $laneSize)">
								<xsl:value-of select="$relativeOffset - ($relativeOffset + $height - $laneSize) - 5" />
							</xsl:when>
							<xsl:when test="$relativeOffset > 0">
								<xsl:value-of select="$relativeOffset"/>
							</xsl:when>
							<!-- If the offset is becomes negative then the object was very close to top of lane so set so is inside lane. -->
							<xsl:otherwise>
								<xsl:text>1</xsl:text>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
					<!-- No swimlanes so just set the Y offset -->
					<xsl:otherwise>
						<xsl:value-of select="$offsetY +  $additionalYOffset" />
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>

			<xsl:call-template name="scaleY"><xsl:with-param name="value" select="$preScaleY"/></xsl:call-template>

		</xsl:element>
		
		<!-- add the width and height - this will be fixed to the default sizes -->
		<xsl:element name="extensions:Width"><xsl:value-of select="$realWidth"/></xsl:element>
		<xsl:element name="extensions:Height"><xsl:value-of select="$realHeight"/></xsl:element>
		
	</xsl:element>

	<!-- SIA-46: Colour the complex routers so that they are easily identifiable. -->
	<xsl:if test="$objType = 'ComplexRouterObject'">
		<xsl:element name="extensions:Color">181,254,150</xsl:element>
	</xsl:if>

</xsl:template>

<!--
===================================================================================================================
	Return the Xoffset adjusted for lane orientation.
===================================================================================================================
-->
<xsl:template name="GetXOffset">
	<xsl:param name="objType"/>
	<xsl:param name="laneOrientation"/>
	<xsl:param name="activity"/>

	<!--
		The x,y position of the Activity object is calculated at the top-left of the object, whereas the gateway and event
		objects have the position calculated at the center of the objects.  Therefore, step and EAI object positions need
		to be adjusted accordingly as all objects in iPM have the position calculated from the center of the object.
	-->
			
	<xsl:variable name="valX">
		<xsl:choose>
			<xsl:when test="$objType = 'StepObject' or $objType = 'EAIObject' or $objType = 'SubprocObject' or $objType = 'DynamicSubprocObject' or $objType = 'GraftStepObject' or $objType = 'TCStepObject'">
				<!-- Have to subtract the scaled size here because we want scale the X/Y offset but NOT the width/height -->
				<xsl:variable name="scaled"><xsl:call-template name="antiScaleX"><xsl:with-param name="value" select="round (&defaultActivityWidth; div 2)"/></xsl:call-template></xsl:variable>
				<xsl:value-of select="$activity/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'XOffset']/@Value - $scaled" />
			</xsl:when>
			<xsl:when test="$objType = 'ComplexRouterObject'">
				<!-- Have to subtract the scaled size here because we want scale the X/Y offset but NOT the width/height -->
				<xsl:variable name="scaled"><xsl:call-template name="antiScaleX"><xsl:with-param name="value" select="round (&complexRouterWidth; div 2)"/></xsl:call-template></xsl:variable>
				<xsl:value-of select="$activity/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'XOffset']/@Value - $scaled" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$activity/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'XOffset']/@Value" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>

	<xsl:variable name="valY">
		<xsl:choose>
			<xsl:when test="$objType = 'StepObject' or $objType = 'EAIObject' or $objType = 'SubprocObject' or $objType = 'DynamicSubprocObject' or $objType = 'GraftStepObject' or $objType = 'TCStepObject'">
				<!-- Have to subtract the scaled size here because we want scale the X/Y offset but NOT the width/height -->
				<xsl:variable name="scaled"><xsl:call-template name="antiScaleY"><xsl:with-param name="value" select="round (&defaultActivityHeight; div 2)"/></xsl:call-template></xsl:variable>
				<xsl:value-of select="$activity/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'YOffset']/@Value - $scaled" />
			</xsl:when>
			<xsl:when test="$objType = 'ComplexRouterObject'">
				<!-- Have to subtract the scaled size here because we want scale the X/Y offset but NOT the width/height -->
				<xsl:variable name="scaled"><xsl:call-template name="antiScaleY"><xsl:with-param name="value" select="round (&complexRouterHeight; div 2)"/></xsl:call-template></xsl:variable>
				<xsl:value-of select="$activity/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'YOffset']/@Value - $scaled" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$activity/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'YOffset']/@Value" />
			</xsl:otherwise>
		</xsl:choose>	
	</xsl:variable>

	<xsl:choose>
		<xsl:when test="$laneOrientation = '&laneHorizontal;'"><xsl:value-of select="$valX" /></xsl:when>
		<xsl:otherwise><xsl:value-of select="$valY" /></xsl:otherwise>
	</xsl:choose>

</xsl:template>

<!--
===================================================================================================================
	Return the Yoffset adjusted for lane orientation.
===================================================================================================================
-->
<xsl:template name="GetYOffset">
	<xsl:param name="objType"/>
	<xsl:param name="laneOrientation"/>
	<xsl:param name="activity"/>

	<!--
		The x,y position of the Activity object is calculated at the top-left of the object, whereas the gateway and event
		objects have the position calculated at the center of the objects.  Therefore, step and EAI object positions need
		to be adjusted accordingly as all objects in iPM have the position calculated from the center of the object.
	-->
			
	<xsl:variable name="valX">
		<xsl:choose>
			<xsl:when test="$objType = 'StepObject' or $objType = 'EAIObject' or $objType = 'SubprocObject' or $objType = 'DynamicSubprocObject' or $objType = 'GraftStepObject' or $objType = 'TCStepObject'">
				<!-- Have to subtract the scaled size here because we want scale the X/Y offset but NOT the width/height -->
				<xsl:variable name="scaled"><xsl:call-template name="antiScaleX"><xsl:with-param name="value" select="round (&defaultActivityWidth; div 2)"/></xsl:call-template></xsl:variable>
				<xsl:value-of select="$activity/xpdl:ExtendedAttributes//xpdl:ExtendedAttribute[@Name = 'XOffset']/@Value - $scaled" />
			</xsl:when>
			<xsl:when test="$objType = 'ComplexRouterObject'">
				<!-- Have to subtract the scaled size here because we want scale the X/Y offset but NOT the width/height -->
				<xsl:variable name="scaled"><xsl:call-template name="antiScaleX"><xsl:with-param name="value" select="round (&complexRouterWidth; div 2)"/></xsl:call-template></xsl:variable>
				<xsl:value-of select="$activity/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'XOffset']/@Value - $scaled" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$activity/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'XOffset']/@Value" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>

	<xsl:variable name="valY">
		<xsl:choose>
			<xsl:when test="$objType = 'StepObject' or $objType = 'EAIObject' or $objType = 'SubprocObject' or $objType = 'DynamicSubprocObject' or $objType = 'GraftStepObject' or $objType = 'TCStepObject'">
				<!-- Have to subtract the scaled size here because we want scale the X/Y offset but NOT the width/height -->
				<xsl:variable name="scaled"><xsl:call-template name="antiScaleY"><xsl:with-param name="value" select="round (&defaultActivityHeight; div 2)"/></xsl:call-template></xsl:variable>
				<xsl:value-of select="$activity/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'YOffset']/@Value - $scaled" />
			</xsl:when>
			<xsl:when test="$objType = 'ComplexRouterObject'">
				<!-- Have to subtract the scaled size here because we want scale the X/Y offset but NOT the width/height -->
				<xsl:variable name="scaled"><xsl:call-template name="antiScaleY"><xsl:with-param name="value" select="round (&complexRouterHeight; div 2)"/></xsl:call-template></xsl:variable>
				<xsl:value-of select="$activity/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'YOffset']/@Value - $scaled" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$activity/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'YOffset']/@Value" />
			</xsl:otherwise>
		</xsl:choose>	
	</xsl:variable>

	<xsl:choose>
		<xsl:when test="$laneOrientation = '&laneHorizontal;'"><xsl:value-of select="$valY" /></xsl:when>
		<xsl:otherwise><xsl:value-of select="$valX" /></xsl:otherwise>
	</xsl:choose>

</xsl:template>

<!--
===================================================================================================================
	Return the Error Event Code that should be used for the given withdraw link.
===================================================================================================================
-->
<xsl:template name="GetErrorEventCode">
	<xsl:param name="srcObjID"/>
	<xsl:param name="FromPrimary"/>
	
	<xsl:choose>
		<xsl:when test="$FromPrimary = 'true'"><xsl:text>ErrorCode_</xsl:text></xsl:when>
		<xsl:otherwise><xsl:text>SecondaryWithdraw_</xsl:text></xsl:otherwise>
	</xsl:choose>

	<!-- Can just use the src object id because we will only create one error event object for primary and one for secondary. -->
	<xsl:value-of select="$srcObjID"/>
	
</xsl:template>

<!--
===================================================================================================================
	EAIMail2TaskService - output intermediate extended attributes for TaskService from EAI e-mail application
								these extended attrs will be picked up and put in final xpdl2 by the migration v1 -> v2 transformation.
===================================================================================================================
-->
<xsl:template name="EAIMail2TaskService">
	<xsl:param name="act"/>
	<xsl:param name="eaiData"/>

	<ExtendedAttribute Name="Task_xpdl2_TaskService">
	
		<!-- 
			Output the xpdl2 TaskService definition for the email service task.
			Are destination format is still xpdl v1 so we won't confuse matters by using the xpdl2
			  namespace directly. We'll use 'namespace'_ instead and let migration v1 - v2 sort the rest out.
		-->
		<xpdl2_TaskService xpdExt_ImplementationType="E-Mail" Implementation="Other">
			<xpdl2_MessageIn Id="_MsgIn_{generate-id($act)}"/>
			<xpdl2_MessageOut Id="_MsgOut_{generate-id($act)}"/>
		
			<xsl:variable name="eaiText" select="$eaiData/sw:EAIRunData/sw:EAITypeData/sw:EAIText"/>

			<email_Email>
				<email_Definition>
					<!-- From (either server defined or custom) -->
					<xsl:variable name="serverFrom">
						<xsl:call-template name="GetHeaderTaggedLine"><xsl:with-param name="tag" select="'SERVER_FROM'"/><xsl:with-param name="token" select="'^'"/><xsl:with-param name="string" select="$eaiText"/></xsl:call-template>
					</xsl:variable>
					
					<xsl:variable name="from">
						<xsl:if test="$serverFrom != '1'">
							<xsl:call-template name="GetHeaderTaggedLine"><xsl:with-param name="tag" select="'FROM'"/><xsl:with-param name="token" select="'^'"/><xsl:with-param name="string" select="$eaiText"/></xsl:call-template>
						</xsl:if>
					</xsl:variable>
					
					<xsl:choose>
						<xsl:when test="$from != ''">
							<email_From email_Configuration="Custom"><xsl:value-of select="$from"/></email_From>
						</xsl:when>
						<xsl:otherwise>
							<email_From email_Configuration="Server"/>
						</xsl:otherwise>
					</xsl:choose>
					
					<!-- T0 -->
					<xsl:variable name="to">
						<xsl:call-template name="GetHeaderTaggedLine"><xsl:with-param name="tag" select="'TO'"/><xsl:with-param name="token" select="'^'"/><xsl:with-param name="string" select="$eaiText"/></xsl:call-template>
					</xsl:variable>
					<xsl:if test="$to != ''"><email_To><xsl:value-of select="$to"/></email_To></xsl:if>

					<!-- CC -->
					<xsl:variable name="cc">
						<xsl:call-template name="GetHeaderTaggedLine"><xsl:with-param name="tag" select="'CC'"/><xsl:with-param name="token" select="'^'"/><xsl:with-param name="string" select="$eaiText"/></xsl:call-template>
					</xsl:variable>
					<xsl:if test="$cc != ''"><email_Cc><xsl:value-of select="$cc"/></email_Cc></xsl:if>

					<!-- BCC -->
					<xsl:variable name="bcc">
						<xsl:call-template name="GetHeaderTaggedLine"><xsl:with-param name="tag" select="'BCC'"/><xsl:with-param name="token" select="'^'"/><xsl:with-param name="string" select="$eaiText"/></xsl:call-template>
					</xsl:variable>
					<xsl:if test="$bcc != ''"><email_Bcc><xsl:value-of select="$bcc"/></email_Bcc></xsl:if>

					<!-- ReplyTo -->
					<xsl:variable name="replyto">
						<xsl:call-template name="GetHeaderTaggedLine"><xsl:with-param name="tag" select="'REPLYTO'"/><xsl:with-param name="token" select="'^'"/><xsl:with-param name="string" select="$eaiText"/></xsl:call-template>
					</xsl:variable>
					<xsl:if test="$replyto != ''"><email_ReplyTo><xsl:value-of select="$replyto"/></email_ReplyTo></xsl:if>

					<!-- Headers -->
					<xsl:variable name="headers">
						<xsl:call-template name="GetHeaderTaggedLine"><xsl:with-param name="tag" select="'HEADER'"/><xsl:with-param name="token" select="'^'"/><xsl:with-param name="string" select="$eaiText"/></xsl:call-template>
					</xsl:variable>
					<xsl:if test="$headers != ''"><email_Headers><xsl:value-of select="$headers"/></email_Headers></xsl:if>

					<!-- Priority -->
					<xsl:variable name="priority">
						<xsl:call-template name="GetHeaderTaggedLine"><xsl:with-param name="tag" select="'PRIORITY'"/><xsl:with-param name="token" select="'^'"/><xsl:with-param name="string" select="$eaiText"/></xsl:call-template>
					</xsl:variable>
					
					<xsl:choose>
						<xsl:when test="$priority >= 5"><email_Priority>Low</email_Priority></xsl:when>
						<xsl:when test="$priority = 1"><email_Priority>High</email_Priority></xsl:when>
						<xsl:otherwise><email_Priority>Normal</email_Priority></xsl:otherwise>
					</xsl:choose>

					<!-- Subject -->
					<xsl:variable name="subject">
						<xsl:call-template name="GetHeaderTaggedLine"><xsl:with-param name="tag" select="'SUBJECT'"/><xsl:with-param name="token" select="'^'"/><xsl:with-param name="string" select="$eaiText"/></xsl:call-template>
					</xsl:variable>
					<xsl:if test="$subject != ''"><email_Subject><xsl:value-of select="$subject"/></email_Subject></xsl:if>

				</email_Definition>
				
				<!-- Body - bit of a special case. it is everything from BODY^ to end... -->
				<xsl:variable name="body" select="substring-after($eaiText, '&#10;BODY^&#10;')"/>
				
				<xsl:if test="$body != ''">
					<email_Body><xsl:value-of select="$body"/></email_Body>
				</xsl:if>

				<!-- Attachments -->
				<xsl:variable name="attach">
					<xsl:call-template name="GetHeaderTaggedLine"><xsl:with-param name="tag" select="'ATTACH'"/><xsl:with-param name="token" select="'^'"/><xsl:with-param name="string" select="$eaiText"/></xsl:call-template>
				</xsl:variable>
				<xsl:variable name="fileAttach">
					<xsl:call-template name="GetHeaderTaggedLine"><xsl:with-param name="tag" select="'FILEATTACH'"/><xsl:with-param name="token" select="'^'"/><xsl:with-param name="string" select="$eaiText"/></xsl:call-template>
				</xsl:variable>
				
				<xsl:if test="$attach != '' or $fileAttach != ''">
					<email_Attachment>
						<xsl:if test="$attach != ''">
							<email_Value><xsl:value-of select="$attach"/></email_Value>
						</xsl:if>
						
						<xsl:if test="$fileAttach != ''">
							<email_Files>
								<xsl:call-template name="OutputEMailAttachFiles">
									<xsl:with-param name="fileAttach" select="$fileAttach"/>
								</xsl:call-template>
							</email_Files>
						</xsl:if>
					</email_Attachment>
				</xsl:if>

				<!-- SMTP (either server defined or custom) -->
				<xsl:variable name="serverSMTP">
					<xsl:call-template name="GetHeaderTaggedLine"><xsl:with-param name="tag" select="'SERVER_SMTP'"/><xsl:with-param name="token" select="'^'"/><xsl:with-param name="string" select="$eaiText"/></xsl:call-template>
				</xsl:variable>
				
				<xsl:choose>
					<xsl:when test="$serverSMTP != '1'">
						<xsl:variable name="host">
							<xsl:call-template name="GetHeaderTaggedLine"><xsl:with-param name="tag" select="'HOST'"/><xsl:with-param name="token" select="'^'"/><xsl:with-param name="string" select="$eaiText"/></xsl:call-template>
						</xsl:variable>
						<xsl:variable name="port">
							<xsl:call-template name="GetHeaderTaggedLine"><xsl:with-param name="tag" select="'PORT'"/><xsl:with-param name="token" select="'^'"/><xsl:with-param name="string" select="$eaiText"/></xsl:call-template>
						</xsl:variable>
						
						<email_SMTP email_Configuration="Custom">
							<xsl:if test="$host != ''">
								<xsl:attribute name="Host"><xsl:value-of select="$host"/></xsl:attribute>
							</xsl:if>
							<xsl:if test="$port != ''">
								<xsl:attribute name="Port"><xsl:value-of select="$port"/></xsl:attribute>
							</xsl:if>
						</email_SMTP>
					</xsl:when>
					
					<xsl:otherwise>
						<email_SMTP email_Configuration="Server"/>
					</xsl:otherwise>
				</xsl:choose>


				<!-- Error handling -->
				<xsl:variable name="retcode">
					<xsl:call-template name="GetHeaderTaggedLine"><xsl:with-param name="tag" select="'RETURNCODE'"/><xsl:with-param name="token" select="'^'"/><xsl:with-param name="string" select="$eaiText"/></xsl:call-template>
				</xsl:variable>
				<xsl:variable name="retmsg">
					<xsl:call-template name="GetHeaderTaggedLine"><xsl:with-param name="tag" select="'RETURNMSG'"/><xsl:with-param name="token" select="'^'"/><xsl:with-param name="string" select="$eaiText"/></xsl:call-template>
				</xsl:variable>

				<xsl:if test="$retcode != '' or $retmsg != ''">
					<email_Error>
						<xsl:if test="$retcode != ''">
							<email_ReturnCode><xsl:value-of select="$retcode"/></email_ReturnCode>
						</xsl:if>
						<xsl:if test="$retmsg != ''">
							<email_ReturnMessage><xsl:value-of select="$retmsg"/></email_ReturnMessage>
						</xsl:if>
					</email_Error>

				</xsl:if>

			</email_Email>
		
		</xpdl2_TaskService>
	
	</ExtendedAttribute>
	
	
</xsl:template>

<!--
===================================================================================================================
	OutputEMailAttachFiles - Output the eai mail file attach elements from given "file;file;" string.
===================================================================================================================
-->
<xsl:template name="OutputEMailAttachFiles">
	<xsl:param name="fileAttach"/>
	
	<xsl:if test="$fileAttach != ''">
	
		<xsl:variable name="file">
			<xsl:choose>
				<xsl:when test="contains($fileAttach, ';')"><xsl:value-of select="substring-before($fileAttach, ';')"/></xsl:when>
				<xsl:otherwise><xsl:value-of select="$fileAttach"/></xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		

		<xsl:if test="$file">
			
			<email_File><xsl:value-of select="$file"/></email_File>
			
			<!-- Recurs to process next -->
			<xsl:call-template name="OutputEMailAttachFiles">
				<xsl:with-param name="fileAttach" select="substring-after($fileAttach, ';')"/>
			</xsl:call-template>
		</xsl:if>
	
	</xsl:if>
	
</xsl:template>

<!--
===================================================================================================================
	EAIScript2TaskScript - output intermediate extended attributes for TaskScript from EAI script
===================================================================================================================
-->
<xsl:template name="EAIScript2TaskScript">
	<xsl:param name="act"/>
	<xsl:param name="eaiData"/>

	<ExtendedAttribute Name="Task_xpdl2_TaskScript">
		<xpdl2_TaskScript>
			<xsl:variable name="text" select="$eaiData/sw:EAIRunData/sw:EAITypeData/sw:EAIText"/>
			<xpdl2_Script ScriptGrammar="iProcessScript">
				<xsl:value-of select="$text"/>
			</xpdl2_Script>
		</xpdl2_TaskScript>
	</ExtendedAttribute>

</xsl:template>

<!--
===================================================================================================================
    EAIJavaTaskService - output intermediate extended attributes for Java TaskService from EAI
===================================================================================================================
-->
<xsl:template name="EAIJavaTaskService">
    <xsl:param name="act"/>
    <xsl:param name="eaiData"/>

    <ExtendedAttribute Name="Task_xpdl2_TaskService">
        <xpdl2_TaskService xpdExt_ImplementationType="Java" Implementation="Other">
            <xsl:variable name="text" select="$eaiData/sw:EAIRunData/sw:EAITypeData/sw:EAIText"/>
        </xpdl2_TaskService>
    </ExtendedAttribute>

</xsl:template>

<!--
===================================================================================================================
    EAIBwTaskService - output intermediate extended attributes for BW TaskService from EAI
===================================================================================================================
-->
<xsl:template name="EAIBwTaskService">
    <xsl:param name="act"/>
    <xsl:param name="eaiData"/>

    <xsl:variable name="text" select="$eaiData/sw:EAIRunData/sw:EAITypeData/sw:EAIText"/>
    <xsl:variable name="eaidoc" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.unescape($text)"/>
    <xsl:variable name="swBusinessWorksPanel" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.unescape($eaidoc/eaijava:EAI_Java_Plugin/eaijava:Services/eaijava:Service_Data[eaijava:Name = 'com.staffware.integration.eaidesigntime.gui.integrationwizard.SWBusinessWorksPanel']/eaijava:Payload)"/>
    <xsl:variable name="wsdlText" select="$swBusinessWorksPanel/eaibw:BW_Plugin/eaibw:BusinessWorks/eaibw:File_Contents"/>
    <xsl:variable name="selectedProcess" select="$swBusinessWorksPanel/eaibw:BW_Plugin/eaibw:BusinessWorks/eaibw:Selected_Process"/>
    <xsl:variable name="operationName" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.getOperationName($project, $selectedProcess)"/>
    <xsl:variable name="portName" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.getPortName($project, $selectedProcess)"/>
    <xsl:variable name="serviceName" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.getServiceName($project, $selectedProcess)"/>
    <xsl:variable name="portTypeName" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.getPortTypeName($project, $selectedProcess)"/>
    <xsl:variable name="portOperationName" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.getPortOperationName($selectedProcess)"/>
    <xsl:variable name="jmsTarget" select="$swBusinessWorksPanel/eaibw:BW_Plugin/eaibw:BusinessWorks/eaibw:JMS_Target"/>
    <xsl:variable name="location" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.getWsdlLocation($project, $selectedProcess)"/>
    <xsl:variable name="swOutputXSLTLocatorPanel" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.unescape($eaidoc/eaijava:EAI_Java_Plugin/eaijava:Services/eaijava:Service_Data[eaijava:Name = 'com.staffware.integration.eaidesigntime.gui.integrationwizard.SWOutputXSLTLocatorPanel']/eaijava:Payload)"/>
    <xsl:variable name="swInputXSLTLocatorPanel" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.unescape($eaidoc/eaijava:EAI_Java_Plugin/eaijava:Services/eaijava:Service_Data[eaijava:Name = 'com.staffware.integration.eaidesigntime.gui.integrationwizard.SWInputXSLTLocatorPanel']/eaijava:Payload)"/>
    <xsl:variable name="outputXSLT" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.unescape($swOutputXSLTLocatorPanel/eaibw:BW_Plugin/eaibw:Output_Mappings/eaibw:XSLT)"/>
    <xsl:variable name="inputXSLT" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.unescape($swInputXSLTLocatorPanel/eaibw:BW_Plugin/eaibw:Input_Mappings/eaibw:XSLT)"/>

    <xsl:variable name="requireXsltIn"><xsl:call-template name="CheckXsltIn"><xsl:with-param name="xslt" select="$outputXSLT/xsl:stylesheet/xsl:template"/></xsl:call-template></xsl:variable>
    <xsl:variable name="requireXsltOut"><xsl:call-template name="CheckXsltOut"><xsl:with-param name="xslt" select="$inputXSLT/xsl:stylesheet/xsl:template"/></xsl:call-template></xsl:variable>

    <ExtendedAttribute Name="Task_xpdl2_TaskService">
        <xpdl2_TaskService xpdExt_ImplementationType="BW Service" Implementation="WebService">
            
            <xpdl2_MessageIn Id="_MsgIn_{generate-id($act)}">
                <xpdl2_DataMappings>
                    <xsl:if test="not(contains($requireXsltIn, '1'))">
                        <xsl:apply-templates select="$outputXSLT/xsl:stylesheet/xsl:template/*" mode="DataMappingIn"><xsl:with-param name="selectedProcess" select="$selectedProcess"/><xsl:with-param name="path" select="''"/></xsl:apply-templates>
                    </xsl:if>
                </xpdl2_DataMappings>
            </xpdl2_MessageIn>
            <xpdl2_MessageOut  Id="_MsgOut_{generate-id($act)}t">
                <xpdl2_DataMappings>
                    <xsl:if test="not(contains($requireXsltOut, '1'))">
                        <xsl:apply-templates select="$inputXSLT/xsl:stylesheet/xsl:template/*" mode="DataMappingOut"><xsl:with-param name="selectedProcess" select="$selectedProcess"/><xsl:with-param name="path" select="''"/></xsl:apply-templates>
                    </xsl:if>
                </xpdl2_DataMappings>
            </xpdl2_MessageOut>
            <xpdl2_WebServiceOperation xpdExt_SecurityProfile="" xpdExt_Transport="http://www.tibco.com/namespaces/ws/2004/soap/binding/JMS">
              <!--  Removed the if condition that checked whether the Length of the wsdl content as we need "xpdExt_Alias" either ways. -->	
              <xsl:attribute name="xpdExt_Alias"><xsl:value-of select="$jmsTarget"/></xsl:attribute>
              <xsl:attribute name="OperationName"><xsl:value-of select="$operationName"/></xsl:attribute>
              <xsl:element name="xpdl2_Service">
              <xsl:attribute name="xpdExt:IsRemote"><xsl:choose><xsl:when test="string-length($wsdlText) > 0">false</xsl:when><xsl:otherwise>true</xsl:otherwise></xsl:choose></xsl:attribute>
              <xsl:attribute name="PortName"><xsl:value-of select="$portName"/></xsl:attribute>
              <xsl:attribute name="ServiceName"><xsl:value-of select="$serviceName"/></xsl:attribute>
                <xpdl2_EndPoint EndPointType="WSDL">
                  <xpdl2_ExternalReference>
                      <xsl:attribute name="location"><xsl:value-of select="$location"/></xsl:attribute>
                  </xpdl2_ExternalReference>
                </xpdl2_EndPoint>
              </xsl:element>
            </xpdl2_WebServiceOperation>
            <xpdExt_PortTypeOperation>
              <xsl:attribute name="PortTypeName"><xsl:value-of select="$portTypeName"/></xsl:attribute>
              <xsl:attribute name="OperationName"><xsl:value-of select="$portOperationName"/></xsl:attribute>
              <xpdExt_ExternalReference>
                  <xsl:attribute name="location"><xsl:value-of select="$location"/></xsl:attribute>
              </xpdExt_ExternalReference>
            </xpdExt_PortTypeOperation>
        </xpdl2_TaskService>
    </ExtendedAttribute>

    <xsl:if test="contains($requireXsltIn, '1')">
        <ExtendedAttribute Name="Task_xpdl2_TaskService_ScriptIn"><xsl:value-of select="$swOutputXSLTLocatorPanel/eaibw:BW_Plugin/eaibw:Output_Mappings/eaibw:XSLT"/></ExtendedAttribute>
    </xsl:if>
    <xsl:if test="contains($requireXsltOut, '1')">
        <ExtendedAttribute Name="Task_xpdl2_TaskService_ScriptOut"><xsl:value-of select="$swInputXSLTLocatorPanel/eaibw:BW_Plugin/eaibw:Input_Mappings/eaibw:XSLT"/></ExtendedAttribute>
    </xsl:if>
</xsl:template>

<xsl:template name="CheckXsltIn">
    <xsl:param name="xslt"/>
    <xsl:for-each select="$xslt/*">
        <xsl:if test="name() = 'xsl:choose' or name() = 'xsl:for-each' or name() = 'xsl:variable'"><xsl:text>1</xsl:text></xsl:if>
        <xsl:if test="name() = 'xsl:value-of'">
            <xsl:choose>
                <xsl:when test="contains(./@select, '(')"><xsl:text>1</xsl:text></xsl:when>
                <xsl:otherwise><xsl:text>0</xsl:text></xsl:otherwise>
            </xsl:choose>
        </xsl:if>
        <xsl:call-template name="CheckXsltIn"><xsl:with-param name="xslt" select="."/></xsl:call-template>
    </xsl:for-each>
</xsl:template>

<xsl:template name="CheckXsltOut">
    <xsl:param name="xslt"/>
    <xsl:for-each select="$xslt/*">
        <xsl:if test="name() = 'xsl:choose' or name() = 'xsl:for-each' or name() = 'xsl:variable'"><xsl:text>1</xsl:text></xsl:if>
        <xsl:if test="name() = 'xsl:value-of'">
            <xsl:choose>
                <xsl:when test="contains(./@select, '(')"><xsl:text>1</xsl:text></xsl:when>
                <xsl:otherwise><xsl:text>0</xsl:text></xsl:otherwise>
            </xsl:choose>
        </xsl:if>
        <xsl:call-template name="CheckXsltOut"><xsl:with-param name="xslt" select="."/></xsl:call-template>
    </xsl:for-each>
</xsl:template>

<xsl:template match="xsl:choose | xsl:for-each | xsl:variable" mode="DataMappingIn">
    <xsl:param name="selectedProcess"/>
    <xsl:param name="path"/>
</xsl:template>

<xsl:template match="xsl:value-of" mode="DataMappingIn">
    <xsl:param name="selectedProcess"/>
    <xsl:param name="path"/>
    
    <xpdl2_DataMapping Direction="IN">
       <xsl:variable name="wsdlPath" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.xPathToWsdlPath($project, $selectedProcess, $path, 'IN')"/>
       <xsl:variable name="field"><xsl:call-template name="getLastSegment"><xsl:with-param name="path" select="@select"/></xsl:call-template></xsl:variable>
       <xsl:attribute name="Formal"><xsl:value-of select="$wsdlPath"/></xsl:attribute>
       <xpdl2_Actual ScriptGrammar="JavaScript"><xsl:value-of select="$field"/>= <xsl:value-of select="$field"/>; //<xsl:value-of select="$wsdlPath"/><xsl:value-of select="'&#10;'"/><xsl:value-of select="$field"/></xpdl2_Actual>
    </xpdl2_DataMapping>
</xsl:template>

<xsl:template match="@* | node()" mode="DataMappingIn">
    <xsl:param name="selectedProcess"/>
    <xsl:param name="path"/>
    <xsl:apply-templates select="*" mode="DataMappingIn"><xsl:with-param name="selectedProcess" select="$selectedProcess"/><xsl:with-param name="path" select="concat($path,'/',name())"/></xsl:apply-templates>
</xsl:template>

<xsl:template match="xsl:choose | xsl:for-each | xsl:variable" mode="DataMappingOut">
    <xsl:param name="selectedProcess"/>
    <xsl:param name="path"/>
</xsl:template>

<xsl:template match="xsl:value-of" mode="DataMappingOut">
    <xsl:param name="selectedProcess"/>
    <xsl:param name="path"/>
    
    <xpdl2_DataMapping Direction="OUT">
       <xsl:variable name="wsdlPath" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.xPathToWsdlPath($project, $selectedProcess, @select, 'OUT')"/>
       <xsl:variable name="field"><xsl:call-template name="getLastSegment"><xsl:with-param name="path" select="$path"/></xsl:call-template></xsl:variable>
       <xsl:attribute name="Formal"><xsl:value-of select="$wsdlPath"/></xsl:attribute>
       <xpdl2_Actual ScriptGrammar="JavaScript"><xsl:value-of select="$field"/></xpdl2_Actual>
    </xpdl2_DataMapping>
</xsl:template>

<xsl:template match="@* | node()" mode="DataMappingOut">
    <xsl:param name="selectedProcess"/>
    <xsl:param name="path"/>
    <xsl:apply-templates select="*" mode="DataMappingOut"><xsl:with-param name="selectedProcess" select="$selectedProcess"/><xsl:with-param name="path" select="concat($path,'/',name())"/></xsl:apply-templates>
</xsl:template>

<xsl:template name="getLastSegment">
    <xsl:param name="path"/>
    
    <xsl:choose>
        <xsl:when test="contains($path, '/')">
            <xsl:call-template name="getLastSegment"><xsl:with-param name="path" select="substring-after($path, '/')"/></xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
            <xsl:value-of select="$path"/>
        </xsl:otherwise>
    </xsl:choose>
</xsl:template>

<!--
===================================================================================================================
    EAICustomTaskService - output intermediate extended attributes for Web Service TaskService from EAI
===================================================================================================================
-->
<xsl:template name="EAICustomTaskService">
    <xsl:param name="act"/>
    <xsl:param name="eaiType"/>
    <xsl:param name="eaiRunType"/>
    <xsl:param name="eaiData"/>

    <ExtendedAttribute Name="Task_xpdl2_TaskService">
		<xsl:variable name="eaiText" select="$eaiData/sw:EAIRunData/sw:EAITypeData/sw:EAIText"/>

		<xsl:call-template name="OutputEAIStepTaskService">
			<xsl:with-param name="actId" select="generate-id($act)"/>
			<xsl:with-param name="eaiType" select="$eaiType"/>
			<xsl:with-param name="eaiRunType" select="$eaiRunType"/>
			<xsl:with-param name="eaiText" select="$eaiText"/>
		</xsl:call-template>
		
    </ExtendedAttribute>

	<!-- Handle the withdraw action for manual delayed release. -->
	<xsl:variable name="withdrawEaiText" select="$eaiData/sw:EAIWithdrawData/sw:EAITypeData/sw:EAIText"/>

	<xsl:if test="$withdrawEaiText != ''">

		<xpdl:ExtendedAttribute Name="iProcessV8_EAIWithdrawData">
			<WebServiceWithdraw>
				<!-- Note that migration up from v1 will change these <xpdl2_ServiceTask> elements etc into <xpdl2:xpdl2_SeriveTask - iProcessAfterMigrateToV8.xslt will just have to cope! -->
				<xsl:call-template name="OutputEAIStepTaskService">
					<xsl:with-param name="actId" select="concat(generate-id($act), '_EAIWithdrawTask')"/>
					<xsl:with-param name="eaiType" select="$eaiType"/>
					<xsl:with-param name="eaiRunType" select="$eaiRunType"/>
					<xsl:with-param name="eaiText" select="$withdrawEaiText"/>
				</xsl:call-template>
				
			</WebServiceWithdraw>
		</xpdl:ExtendedAttribute>
	</xsl:if>
	
</xsl:template>

<!--
===================================================================================================================
    EAIWebServiceTaskService - output intermediate extended attributes for Web Service TaskService 
===================================================================================================================
-->
<xsl:template name="EAIWebServiceTaskService">
    <xsl:param name="act"/>
    <xsl:param name="eaiData"/>

    <ExtendedAttribute Name="Task_xpdl2_TaskService">
		<xsl:variable name="eaiText" select="$eaiData/sw:EAIRunData/sw:EAITypeData/sw:EAIText"/>

		<xsl:call-template name="OutputWSTaskService">
			<xsl:with-param name="actId" select="generate-id($act)"/>
			<xsl:with-param name="eaiText" select="$eaiText"/>
		</xsl:call-template>
		
    </ExtendedAttribute>

	<!-- Handle the withdraw action for manual delayed release. -->
	<xsl:variable name="withdrawEaiText" select="$eaiData/sw:EAIWithdrawData/sw:EAITypeData/sw:EAIText"/>

	<xsl:if test="$withdrawEaiText != ''">

		<xpdl:ExtendedAttribute Name="iProcessV8_EAIWithdrawData">
			<WebServiceWithdraw>
				<!-- Note that migration up from v1 will change these <xpdl2_ServiceTask> elements etc into <xpdl2:xpdl2_SeriveTask - iProcessAfterMigrateToV8.xslt will just have to cope! -->
				<xsl:call-template name="OutputWSTaskService">
					<xsl:with-param name="actId" select="concat(generate-id($act), '_EAIWithdrawTask')"/>
					<xsl:with-param name="eaiText" select="$withdrawEaiText"/>
				</xsl:call-template>
				
			</WebServiceWithdraw>
		</xpdl:ExtendedAttribute>
	</xsl:if>
	
</xsl:template>


<!--
===================================================================================================================
	Output xpd2_TaskService definition for iProcess custom EAI service task.
===================================================================================================================
-->
<xsl:template name="OutputEAIStepTaskService">
	<xsl:param name="actId"/>
	<xsl:param name="eaiType"/>
	<xsl:param name="eaiRunType"/>
	<xsl:param name="eaiText"/>
	
	<xpdl2_TaskService xpdExt_ImplementationType="iProcessEAI" Implementation="Other">

		<iProcessExt_EAIStepDefinition Type="{$eaiType}" RunType="{$eaiRunType}"><xsl:value-of select="$eaiText"/></iProcessExt_EAIStepDefinition>
	
	</xpdl2_TaskService>
	
</xsl:template>

<!--
===================================================================================================================
	Output xpd2_TaskService definition for web service task.
===================================================================================================================
-->
<xsl:template name="OutputWSTaskService">
	<xsl:param name="actId"/>
	<xsl:param name="eaiText"/>
	
	<xpdl2_TaskService xpdExt_ImplementationType="WebService" Implementation="WebService">
	
		<xsl:variable name="eaidoc" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.unescape($eaiText)"/>
		
		<xsl:variable name="wsdlText" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.unescape($eaidoc/eaiws:Web_Services_EAI_Plugin/eaiws:WSDL_Definition/eaiws:WSDL_Content)"/>
		<xsl:variable name="serviceName" select="$eaidoc/eaiws:Web_Services_EAI_Plugin/eaiws:Selected_Operation/eaiws:Service_Name"/>
		<xsl:variable name="portName" select="$eaidoc/eaiws:Web_Services_EAI_Plugin/eaiws:Selected_Operation/eaiws:Port_Name"/>
		<xsl:variable name="portTypeName" select="$eaidoc/eaiws:Web_Services_EAI_Plugin/eaiws:Selected_Operation/eaiws:PortType_Name"/>
		<xsl:variable name="operationName" select="$eaidoc/eaiws:Web_Services_EAI_Plugin/eaiws:Selected_Operation/eaiws:Operation_Name"/>
		<xsl:variable name="portOperationName" select="$eaidoc/eaiws:Web_Services_EAI_Plugin/eaiws:Selected_Operation/eaiws:Operation_Name"/>
		
		<xsl:variable name="nameSpace" select="$wsdlText/*/@targetNamespace"/>
		
		<xsl:variable name="location" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.getWSWsdlLocation($project, $operationName, $nameSpace)"/>
		
		<!-- Add Message elements (else mappings can't be made by user after import)! -->
		<xpdl2_MessageIn Id="_MsgIn_{$actId}"/>
		<xpdl2_MessageOut Id="_MsgOut_{$actId}"/>
		
		<xpdl2_WebServiceOperation xpdExt_SecurityProfile="" xpdExt_Transport="http://schemas.xmlsoap.org/soap/http" OperationName="{$operationName}">

			<xpdl2_Service PortName="{$portName}" ServiceName="{$serviceName}">
				<xsl:attribute name="xpdExt_IsRemote"><xsl:choose><xsl:when test="string-length($wsdlText) > 0">false</xsl:when><xsl:otherwise>true</xsl:otherwise></xsl:choose></xsl:attribute>
				
				<xpdl2_EndPoint EndPointType="WSDL">
					<xpdl2_ExternalReference location="{$location}"/>
				</xpdl2_EndPoint>
			</xpdl2_Service>
		</xpdl2_WebServiceOperation>

		<xpdExt_PortTypeOperation PortTypeName="{$portTypeName}" OperationName="{$portOperationName}">
			<xpdExt_ExternalReference location="{$location}"/>
		</xpdExt_PortTypeOperation>
		
	</xpdl2_TaskService>
	
</xsl:template>

<!--
===================================================================================================================
	EAIDBase2TaskService - output intermediate extended attributes for TaskService from EAI data base application
								these extended attrs will be picked up and put in final xpdl2 by the migration v1 -> v2 transformation.
===================================================================================================================
-->
<xsl:template name="EAIDBase2TaskService">
	<xsl:param name="act"/>
	<xsl:param name="eaiData"/>

	<xsl:variable name="text" select="$eaiData/sw:EAIRunData/sw:EAITypeData/sw:EAIText"/>
	
	<xsl:variable name="line1" select="substring-before($text, '&#10;')"/>
	<xsl:variable name="params" select="substring-after( substring-after($text, 'DelayedReleaseID^'), '&#10;')"/>


	<ExtendedAttribute Name="Task_xpdl2_TaskService">
		<!-- 
			Output the xpdl2 TaskService definition for the database service task.
			Are destination format is still xpdl v1 so we won't confuse matters by using the xpdl2
			  namespace directly. We'll use 'namespace'_ instead and let migration v1 - v2 sort the rest out.
		-->
		<xpdl2_TaskService xpdExt_ImplementationType="Database" Implementation="Other">

			<xpdl2_MessageIn Id="_MsgIn_{generate-id($act)}">
				<xpdl2_DataMappings>

					<xsl:call-template name="OutputDBaseDataMappings">
						<xsl:with-param name="remainingParams" select="$params"/>
						<xsl:with-param name="inParams" select="'true'"/>
					</xsl:call-template>

				</xpdl2_DataMappings>
			</xpdl2_MessageIn>
			
			<xpdl2_MessageOut Id="_MsgOut_{generate-id($act)}">
				<xpdl2_DataMappings>

					<xsl:call-template name="OutputDBaseDataMappings">
						<xsl:with-param name="remainingParams" select="$params"/>
						<xsl:with-param name="inParams" select="'false'"/>
					</xsl:call-template>

				</xpdl2_DataMappings>
			</xpdl2_MessageOut>

			<database_Database>
				<database_Server><xsl:value-of select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.getEAIDBDefinitionItem($line1, 'Server')"/></database_Server>
				
				<database_Schema><xsl:value-of select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.getEAIDBDefinitionItem($line1, 'Database')"/></database_Schema>

				<database_Operation>
					<xsl:variable name="owner"><xsl:value-of select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.getEAIDBDefinitionItem($line1, 'Owner')"/></xsl:variable>
	
					<xsl:variable name="procedure"><xsl:value-of select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.getEAIDBDefinitionItem($line1, 'StoredProc')"/></xsl:variable>
				
					<xsl:choose>
						<xsl:when test="$owner != ''">
							<database_Sql><xsl:value-of select="$owner"/>.<xsl:value-of select="$procedure"/></database_Sql>
						</xsl:when>
						<xsl:otherwise>
							<database_Sql><xsl:value-of select="$procedure"/></database_Sql>
						</xsl:otherwise>
					</xsl:choose>

					<database_Parameters>

						<xsl:call-template name="OutputDBaseParams">
							<xsl:with-param name="remainingParams" select="$params"/>
						</xsl:call-template>

					</database_Parameters>

				</database_Operation>
			</database_Database>
		</xpdl2_TaskService>
	</ExtendedAttribute>

	<!-- Save the information about the withdraw stored process for pickup later by iProcessAfterMigrateV8.xslt for creation of compensation task. -->
	<xsl:variable name="withdraw">
		<xsl:call-template name="FieldFromTokenLine">
			<xsl:with-param name="token" select="'&#10;'"/>
			<xsl:with-param name="line" select="$eaiData/sw:EAIWithdrawData/sw:EAITypeData/sw:EAIText"/>
			<xsl:with-param name="idx" select="0"/>
		</xsl:call-template>
	</xsl:variable>
	
	<xsl:if test="$withdraw != ''">
		<!-- 
			SIA-78 Don't import wirthdraw info if the withdraw information is all blank. 
		-->
		<xsl:variable name="wdrawServer"><xsl:value-of select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.getEAIDBDefinitionItem($withdraw, 'Server')"/></xsl:variable>
		<xsl:variable name="wdrawDatabase"><xsl:value-of select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.getEAIDBDefinitionItem($withdraw, 'Database')"/></xsl:variable>
		<xsl:variable name="wdrawOwner"><xsl:value-of select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.getEAIDBDefinitionItem($withdraw, 'Owner')"/></xsl:variable>
		<xsl:variable name="wdrawStoredproc"><xsl:value-of select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.getEAIDBDefinitionItem($withdraw, 'StoredProc')"/></xsl:variable>
		
		<xsl:if test="$wdrawServer != '' or $wdrawDatabase != '' or $wdrawOwner != '' or $wdrawStoredproc != ''">
			<xpdl:ExtendedAttribute Name="iProcessV8_EAIWithdrawData">
				<DatabaseWithdraw>
					<Server><xsl:value-of select="$wdrawServer"/></Server>
					<Database><xsl:value-of select="$wdrawDatabase"/></Database>
					<xsl:choose>
						<xsl:when test="$wdrawOwner != ''">
							<StoredProcedure><xsl:value-of select="$wdrawOwner"/>.<xsl:value-of select="$wdrawStoredproc"/></StoredProcedure>
						</xsl:when>
						<xsl:otherwise>
							<StoredProcedure><xsl:value-of select="$wdrawStoredproc"/></StoredProcedure>
						</xsl:otherwise>
					</xsl:choose>
				</DatabaseWithdraw>
			</xpdl:ExtendedAttribute>
		</xsl:if>
	</xsl:if>

</xsl:template>

<!--
===================================================================================================================
	OutputDBaseDataMappings - output what will be the xpdl2 data mappings 
===================================================================================================================
-->
<xsl:template name="OutputDBaseDataMappings">
	<xsl:param name="remainingParams"/>
	<xsl:param name="inParams"/>

	<!-- pop the next param from start of string. -->
	<xsl:if test="$remainingParams != ''">
		
			<xsl:variable name="param">
				<xsl:choose>
					<xsl:when test="contains($remainingParams, '&#10;')">
						<xsl:value-of select="substring-before($remainingParams, '&#10;')"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="$remainingParams"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			
			<xsl:if test="$param != ''">

				<xsl:variable name="mode"> 
					<xsl:call-template name="FieldFromTokenLine">
						<xsl:with-param name="token" select="'^'"/>
						<xsl:with-param name="line" select="$param"/>
						<xsl:with-param name="idx" select="1"/>
					</xsl:call-template>
				</xsl:variable>

				<xsl:variable name="doIt">
					<xsl:choose>
						<xsl:when test="$inParams = 'true' and ($mode = 'IN' or $mode = 'INOUT')">true</xsl:when>
						<xsl:when test="$inParams != 'true' and ($mode = 'OUT')">true</xsl:when>
						<xsl:otherwise>false</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>

				<xsl:if test="$doIt = 'true'">
					<xpdl2_DataMapping>

						<xsl:attribute name="Direction"><xsl:value-of select="$mode"/></xsl:attribute>
						<xsl:attribute name="Formal">
							<xsl:call-template name="FieldFromTokenLine">
								<xsl:with-param name="token" select="'^'"/>
								<xsl:with-param name="line" select="$param"/>
								<xsl:with-param name="idx" select="0"/>
							</xsl:call-template>
						</xsl:attribute>

						<xsl:variable name="actual">
							<xsl:call-template name="FieldFromTokenLine">
								<xsl:with-param name="token" select="'^'"/>
								<xsl:with-param name="line" select="$param"/>
								<xsl:with-param name="idx" select="3"/>
							</xsl:call-template>
						</xsl:variable>
						
						<xpdl2_Actual><xsl:value-of select="$actual"/></xpdl2_Actual>
					
					</xpdl2_DataMapping>
				</xsl:if>
			</xsl:if>
			
			<!-- Recurs with remainder of string to process next param. -->
			<xsl:call-template name="OutputDBaseDataMappings">
				<xsl:with-param name="remainingParams" select="substring-after($remainingParams, '&#10;')"/>
				<xsl:with-param name="inParams" select="$inParams"/>
			</xsl:call-template>
		
	</xsl:if>
	
	
</xsl:template>


<!--
===================================================================================================================
	OutputDBaseParams - output (recursively) the extended data for data base service task parameters
===================================================================================================================
-->
<xsl:template name="OutputDBaseParams">
	<xsl:param name="remainingParams"/>

	<!-- pop the next param from start of string. -->
	<xsl:if test="$remainingParams != ''">
		
			<xsl:variable name="param">
				<xsl:choose>
					<xsl:when test="contains($remainingParams, '&#10;')">
						<xsl:value-of select="substring-before($remainingParams, '&#10;')"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="$remainingParams"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			
			<xsl:if test="$param != ''">
				<database_Parameter>
					<xsl:attribute name="Name">
						<xsl:call-template name="FieldFromTokenLine">
							<xsl:with-param name="token" select="'^'"/>
							<xsl:with-param name="line" select="$param"/>
							<xsl:with-param name="idx" select="0"/>
						</xsl:call-template>
					</xsl:attribute>
				</database_Parameter>
			</xsl:if>
			
			<!-- Recurs with remainder of string to process next param. -->
			<xsl:call-template name="OutputDBaseParams">
				<xsl:with-param name="remainingParams" select="substring-after($remainingParams, '&#10;')"/>
			</xsl:call-template>
		
	</xsl:if>
	
</xsl:template>



<!--
===================================================================================================================
	GetLaneAbsOffset = Calculate the absolute offset of the top boudary of the lane with the given ID
===================================================================================================================
-->
<xsl:template name="GetLaneAbsOffset">
	<xsl:param name="swimLanes" />
	<xsl:param name="laneID" />
	<xsl:param name="currentSize" />
	
	<!-- If we have swim lanes then calculate the offset, otherwise return 0 -->
	<xsl:choose>

		<xsl:when test="$swimLanes/sw:SwimLane">
		
			<!-- If there is a lane with ID 0 it means that there are objects outside the lane so will need to add a new lane under the last one -->
			<xsl:choose>

				<xsl:when test="$laneID = '0'">
					<!-- Return the sum of all lane sizes -->
					<xsl:value-of select="sum ($swimLanes/sw:SwimLane/@Size)" />					
				</xsl:when>
				
				<xsl:otherwise>

					<!-- Get the lane with the given ID -->
					<xsl:variable name="lane" select="$swimLanes//sw:SwimLane[@SwimLaneID = $laneID]" />
					
					<!-- Calculate the sum of all previous lane siblings -->
					<xsl:variable name="siblingSize" select="sum ($lane/preceding-sibling::sw:SwimLane/@Size)" />		
					
					<!-- Calculate the sum of all the parent lane's (if any) siblings -->
					<xsl:variable name="parentSize">
						<xsl:choose>
							<!-- If there is a parent lane then calculate it's siblings sum -->
							<xsl:when test="local-name ($lane/..) != 'SwimLanes'">
								<xsl:call-template name="GetLaneAbsOffset">
									<xsl:with-param name="swimLanes" select="$swimLanes" />
									<xsl:with-param name="laneID" select="$lane/../@SwimLaneID" />
									<xsl:with-param name="currentSize" select="$siblingSize" />
								</xsl:call-template>
							</xsl:when>
		
							<!-- No parent lane so return the sum of the preceding siblings size -->
							<xsl:otherwise>
								<xsl:value-of select="$siblingSize" />
							</xsl:otherwise>
							
						</xsl:choose>
					</xsl:variable>
					
					<!-- Return the absolute offset of this lane -->
					<xsl:choose>
						<xsl:when test="$currentSize"><xsl:value-of select="($currentSize + $parentSize)" /></xsl:when>
						<xsl:otherwise><xsl:value-of select="$parentSize" /></xsl:otherwise>
					</xsl:choose>
					
				</xsl:otherwise>
			</xsl:choose>
			
		</xsl:when>

		<!-- No swim lanes so return 0 -->
		<xsl:otherwise>
			<xsl:text>0</xsl:text>
		</xsl:otherwise>
		
	</xsl:choose>
</xsl:template> 

<!--
===================================================================================================================
	xpdl:Participants - Process the participants to create a list of unique participants.
	The iPM export may contain more than one participant with the same name.

	ALSO iPM doesn't output participants for subsequent addressees on multi-addressed steps.
	So have to recreate the participants from the actual addressees of steps.
===================================================================================================================
-->
<xsl:template match="xpdl:Participants">
	
	<Participants>
		<xsl:call-template name="GetResolvedParticipants">
			<xsl:with-param name="process" select=".."/>
		</xsl:call-template>
		<xsl:call-template name="BwParticipants"/>
	</Participants>
	
</xsl:template>

<!--
===================================================================================================================
    Adds BW JMS targets as participants.
===================================================================================================================
-->
<xsl:template name="BwParticipants">
    <xsl:for-each select="//xpdl:Applications/xpdl:Application[@Name='EAI_BW']">
        <xsl:variable name="eaiData" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'EAIData']/sw:EAIData"/>
        <xsl:variable name="text" select="$eaiData/sw:EAIRunData/sw:EAITypeData/sw:EAIText"/>
        <xsl:variable name="eaidoc" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.unescape($text)"/>
        <xsl:variable name="swBusinessWorksPanel" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.unescape($eaidoc/eaijava:EAI_Java_Plugin/eaijava:Services/eaijava:Service_Data[eaijava:Name = 'com.staffware.integration.eaidesigntime.gui.integrationwizard.SWBusinessWorksPanel']/eaijava:Payload)"/>
        <xsl:variable name="wsdlText" select="$swBusinessWorksPanel/eaibw:BW_Plugin/eaibw:BusinessWorks/eaibw:File_Contents"/>
        <xsl:variable name="jmsTarget" select="string($swBusinessWorksPanel/eaibw:BW_Plugin/eaibw:BusinessWorks/eaibw:JMS_Target)"/>
        <xsl:if test="string-length($jmsTarget) > 0 and not(string-length(wsdlText) > 0)">
            <xsl:if test="java:contains($jmsTargetSet, $jmsTarget) = false">
                <xsl:if test="java:add($jmsTargetSet, $jmsTarget)">
		            <Participant>
		                <xsl:attribute name="Id"><xsl:value-of select="$jmsTarget"/></xsl:attribute>
		                <xsl:attribute name="Name"><xsl:value-of select="$jmsTarget"/></xsl:attribute>
		                <ParticipantType Type="SYSTEM"/>
		                <!-- iProcessExt_ParticipantProperties UseRoleSemantics="false"/-->
		            </Participant>
	            </xsl:if>
            </xsl:if>
        </xsl:if>
    </xsl:for-each>
</xsl:template>

<!--
===================================================================================================================
	ALSO iPM doesn't output participants for subsequent addressees on multi-addressed steps.
	So have to recreate the participants from the actual addressees of steps.
===================================================================================================================
-->
<xsl:template name="GetResolvedParticipants">
	<xsl:param name="process"/>

	<xsl:variable name="addrs" select="$process/xpdl:Activities/xpdl:Activity/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='ExtAttr']/sw:ExtAttr[@Name='Addressees']/sw:Addressees/*/*"/>

	<xsl:for-each select="$addrs">
		
		<xsl:variable name="name">
			<xsl:choose>
				<xsl:when test="@fieldname != ''"></xsl:when> <!-- Don't carry thru field participants (we'll create performer field for them any how. -->
				<xsl:otherwise><xsl:value-of select="text()"/></xsl:otherwise>
			</xsl:choose>
		</xsl:variable>

		<xsl:if test="$name != ''">
			<xsl:variable name="sameNameParticipants" select="$addrs[@fieldname = $name] | $addrs[text() = $name]"/>
		
			<xsl:choose>
				<!-- If there is only one occurrence of this participant then add it -->
				<xsl:when test="count ($sameNameParticipants) = 1">
					<xsl:element name="Participant">
						<xsl:attribute name="Id"><xsl:value-of select="generate-id()" /></xsl:attribute>
						<xsl:attribute name="Name"><xsl:value-of select="$name" /></xsl:attribute>
							
						<xsl:element name="ParticipantType">
							<xsl:attribute name="Type">
								<xsl:choose>
									<xsl:when test="local-name(..) = 'RoleAddr'">ROLE</xsl:when>
									<xsl:when test="local-name(..) = 'FieldAddr'">RESOURCE</xsl:when>
									<xsl:otherwise>HUMAN</xsl:otherwise>
								</xsl:choose>
							</xsl:attribute>
						</xsl:element>
						
						<xsl:element name="iProcessExt:ParticipantProperties">
							<xsl:choose>
								<xsl:when test="local-name(..) = 'RoleAddr'">
									<xsl:attribute name="UseRoleSemantics"><xsl:text>true</xsl:text></xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:attribute name="UseRoleSemantics"><xsl:text>false</xsl:text></xsl:attribute>
								</xsl:otherwise>
							</xsl:choose>
						</xsl:element>
					</xsl:element>
					
				</xsl:when>
				<!-- Multiple occurrences of this participant, so add the first occurrence -->
				<xsl:otherwise>
					<xsl:if test="generate-id (.) = generate-id ($sameNameParticipants[1])">
						<xsl:element name="Participant">
							<xsl:attribute name="Id"><xsl:value-of select="generate-id()" /></xsl:attribute>
							<xsl:attribute name="Name"><xsl:value-of select="$name"/></xsl:attribute>
	
							<xsl:element name="ParticipantType">
								<xsl:attribute name="Type">
									<xsl:choose>
										<xsl:when test="local-name(..) = 'RoleAddr'">ROLE</xsl:when>
										<xsl:when test="local-name(..) = 'FieldAddr'">RESOURCE</xsl:when>
										<xsl:otherwise>HUMAN</xsl:otherwise>
									</xsl:choose>
								</xsl:attribute>
							</xsl:element>

							<xsl:element name="iProcessExt:ParticipantProperties">
								<xsl:choose>
									<xsl:when test="local-name(..) = 'RoleAddr'">
										<xsl:attribute name="UseRoleSemantics"><xsl:text>true</xsl:text></xsl:attribute>
									</xsl:when>
									<xsl:otherwise>
										<xsl:attribute name="UseRoleSemantics"><xsl:text>false</xsl:text></xsl:attribute>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:element>
						</xsl:element>
					</xsl:if>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
	</xsl:for-each>
	
</xsl:template>


<xsl:template name="AddrToParticID">
	<xsl:param name="process"/>
	<xsl:param name="addrName"/>
	
	<xsl:variable name="addrs" select="$process/xpdl:Activities/xpdl:Activity/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='ExtAttr']/sw:ExtAttr[@Name='Addressees']/sw:Addressees/*/*"/>

	<xsl:variable name="sameNameParticipants" select="$addrs[@fieldname = $addrName] | $addrs[text() = $addrName]"/>

	<xsl:choose>
		<xsl:when test="count($sameNameParticipants) > 0">
			<xsl:value-of select="generate-id($sameNameParticipants[1])"/>
		</xsl:when>
		<xsl:otherwise>
			<xsl:text>Unknown</xsl:text><xsl:value-of select="$addrName"/>
		</xsl:otherwise>
	</xsl:choose>

</xsl:template>

<!--
===================================================================================================================
	xpdl:Activity/@Name - The wait and condition objects will have the same name in the iPM xpdl so these need to be changed to make the names unique.
	This is done by adding the activity's ID to the name
===================================================================================================================
-->
<!--
<xsl:template match="xpdl:Activity[xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'ObjType']/@Value = 'WaitObject']/@Name | xpdl:Activity[xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'ObjType']/@Value = 'ConditionObject']/@Name">

	<xsl:attribute name="Name">
		<xsl:value-of select="." /><xsl:value-of select="parent::node()/@Id" />
	</xsl:attribute>

</xsl:template>
-->
<!--
===================================================================================================================
	Ignore performer because we will always output extended multiple performers attribute.
===================================================================================================================
-->
<xsl:template match="xpdl:Performer">
</xsl:template>

<!--
===================================================================================================================
	Preserve multiple performers in extra ext attribute so that migration from xpdl1 -> 2 can pick them up.
===================================================================================================================
-->
<xsl:template name="OutputExtPerformers">
	<xsl:param name="activity"/>

	<xsl:variable name="participants" select="$activity/../../xpdl:Participants/xpdl:Participant"/>
	
	<xsl:variable name="addressees" select="$activity/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'Addressees']/sw:Addressees"/>
	
		<xpdl:ExtendedAttribute Name="v2XPD_Performers">
		
			<xsl:for-each select="$addressees/sw:UserAddr/sw:UserName">
				<xsl:variable name="addrName" select="text()" />
				
				<xsl:variable name="particID">
					<xsl:call-template name="AddrToParticID">
						<xsl:with-param name="process" select="$activity/../.."/>
						<xsl:with-param name="addrName" select="$addrName"/>
					</xsl:call-template>
				</xsl:variable>
				
				<v2Performer><xsl:value-of select="$particID"/></v2Performer>
			</xsl:for-each>

			<xsl:for-each select="$addressees/sw:RoleAddr/sw:UserName">
				<xsl:variable name="addrName" select="text()" />
		
				<xsl:variable name="particID">
					<xsl:call-template name="AddrToParticID">
						<xsl:with-param name="process" select="$activity/../.."/>
						<xsl:with-param name="addrName" select="$addrName"/>
					</xsl:call-template>
				</xsl:variable>
				
				<v2Performer><xsl:value-of select="$particID"/></v2Performer>
			</xsl:for-each>
				
			<xsl:for-each select="$addressees/sw:FieldAddr/sw:FieldRef">
				<xsl:variable name="addrName" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.toUpper(@fieldname)" />
				
				<v2FieldPerformer><xsl:value-of select="$addrName"/></v2FieldPerformer>
			</xsl:for-each>
			
            <xsl:variable name="appId" select="$activity/xpdl:Implementation/xpdl:Tool/@Id"/>
			<xsl:variable name="app" select="$activity/../../xpdl:Applications/xpdl:Application[@Id = $appId]"/>
			<xsl:if test="$app/@Name='EAI_BW'">
	            <xsl:variable name="eaiData" select="$app/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'EAIData']/sw:EAIData"/>
		        <xsl:variable name="text" select="$eaiData/sw:EAIRunData/sw:EAITypeData/sw:EAIText"/>
		        <xsl:variable name="eaidoc" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.unescape($text)"/>
		        <xsl:variable name="swBusinessWorksPanel" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.unescape($eaidoc/eaijava:EAI_Java_Plugin/eaijava:Services/eaijava:Service_Data[eaijava:Name = 'com.staffware.integration.eaidesigntime.gui.integrationwizard.SWBusinessWorksPanel']/eaijava:Payload)"/>
                <xsl:variable name="wsdlText" select="$swBusinessWorksPanel/eaibw:BW_Plugin/eaibw:BusinessWorks/eaibw:File_Contents"/>
		        <xsl:variable name="jmsTarget" select="$swBusinessWorksPanel/eaibw:BW_Plugin/eaibw:BusinessWorks/eaibw:JMS_Target"/>
		        <!--  Removed the if condition that checked whether the Length of the wsdl content. -->
		        <xsl:if test="string-length($jmsTarget) > 0">
	                <v2Performer><xsl:value-of select="$jmsTarget"/></v2Performer>
		        </xsl:if>
            </xsl:if>
			
		</xpdl:ExtendedAttribute>
		

</xsl:template>

<!--
===================================================================================================================
	Get the max Y coordinate to set the width of the default pool (when no lanes are used in the source xpdl)
===================================================================================================================
-->
<xsl:template name="maxYOffset">
	
	<!-- Get Y offsets of all activities -->
	<xsl:variable name="activityYOffsets" select="xpdl:Activities/xpdl:Activity/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'YOffset']"/> 
	<!-- Get Y offsets of all annotations -->
	<xsl:variable name="annotationYOffsets" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr [@Name = 'NonFlowObject']/sw:NonFlowObject[sw:Annotation]/@Y" />
	
	<!-- Get the max Y offset of the activities -->
	<xsl:variable name="maxActivityYOffset">
		
		<xsl:choose>
			<xsl:when test="count ($activityYOffsets) &gt; 0">
				<xsl:for-each select="$activityYOffsets">
					<xsl:sort data-type="number" order="descending" select="@Value" />
					<!-- output the value of the first item in the sorted list - this should be the max value -->
					<xsl:if test="position() = 1">
						<xsl:value-of select="@Value" />
					</xsl:if>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<!-- No activities so set variable to default value.  Set to 210 as 90 will be added later to take it up to 300 -->
				<xsl:text>210</xsl:text>
			</xsl:otherwise>
		</xsl:choose>
		
	</xsl:variable>
	
	<xsl:choose>
		<!-- If there are annotations then check their max Y offset too -->
		<xsl:when test="$annotationYOffsets">
			<!-- Get the max Y offset of the annotations -->
			<xsl:variable name="maxAnnotationYOffset">
				<xsl:for-each select="$annotationYOffsets">
					<xsl:sort data-type="number" order="descending" select="."/>
					<!-- output the value of the first item in the sorted list - this should be the max value -->
					<xsl:if test="position() = 1">
						<xsl:value-of select="." />
					</xsl:if>
				</xsl:for-each>
			</xsl:variable>
			
			<!-- return the max value -->
			<xsl:choose>
				<!-- if annotations Y offset exceeds all the activities then use that value for the max Y offset -->
				<xsl:when test="$maxAnnotationYOffset &gt; $maxActivityYOffset">
					<xsl:value-of select="$maxAnnotationYOffset + 90"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$maxActivityYOffset + 90"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:when>
		<xsl:otherwise>
			<!-- No annotations so return the max Y offset of the activities -->
			<xsl:value-of select="$maxActivityYOffset + 90"/>
		</xsl:otherwise>	
	</xsl:choose>
			
</xsl:template>

<!--
===================================================================================================================
	Get the max X coordinate to set the width of the default pool (when no lanes are used in the source xpdl)
===================================================================================================================
-->
<xsl:template name="maxXOffset">
	
	<!-- Get X offsets of all activities -->
	<xsl:variable name="activityXOffsets" select="xpdl:Activities/xpdl:Activity/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'XOffset']"/> 
	<!-- Get X offsets of all annotations -->
	<xsl:variable name="annotationXOffsets" select="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr [@Name = 'NonFlowObject']/sw:NonFlowObject[sw:Annotation]/@X" />
	
	<!-- Get the max X offset of the activities -->
	<xsl:variable name="maxActivityXOffset">
		
		<xsl:choose>
			<xsl:when test="count ($activityXOffsets) &gt; 0">
				<xsl:for-each select="$activityXOffsets">
					<xsl:sort data-type="number" order="descending" select="@Value" />
					<!-- output the value of the first item in the sorted list - this should be the max value -->
					<xsl:if test="position() = 1">
						<xsl:value-of select="@Value" />
					</xsl:if>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
				<!-- No activities so set variable to default value.  Set to 210 as 90 will be added later to take it up to 300 -->
				<xsl:text>210</xsl:text>
			</xsl:otherwise>
		</xsl:choose>
		
	</xsl:variable>
	
	<xsl:choose>
		<!-- If there are annotations then check their max X offset too -->
		<xsl:when test="$annotationXOffsets">
			<!-- Get the max X offset of the annotations -->
			<xsl:variable name="maxAnnotationXOffset">
				<xsl:for-each select="$annotationXOffsets">
					<xsl:sort data-type="number" order="descending" select="."/>
					<!-- output the value of the first item in the sorted list - this should be the max value -->
					<xsl:if test="position() = 1">
						<xsl:value-of select="." />
					</xsl:if>
				</xsl:for-each>
			</xsl:variable>
			
			<!-- return the max value -->
			<xsl:choose>
				<!-- if annotations X offset exceeds all the activities then use that value for the max X offset -->
				<xsl:when test="$maxAnnotationXOffset &gt; $maxActivityXOffset">
					<xsl:value-of select="$maxAnnotationXOffset + 90"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$maxActivityXOffset + 90"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:when>
		<xsl:otherwise>
			<!-- No annotations so return the max X offset of the activities -->
			<xsl:value-of select="$maxActivityXOffset + 90"/>
		</xsl:otherwise>	
	</xsl:choose>
			
</xsl:template>

<!--
===================================================================================================================
	sw:NonFlowObjects (Annotations) - This would be annotations and elbow joints (elbow joints are ignored)
	This template outputs the note
===================================================================================================================
-->
<xsl:template match="sw:NonFlowObject">
	<xsl:param name="laneOrientation" />
	
	<!-- Only output annotations -->
	<xsl:if test="sw:Annotation">
	
		<!-- Get the X and Y offset of the annotation.  If vertical lanes are used then swap these values over -->
		<xsl:variable name="offsetX">
			<xsl:choose>
				<xsl:when test="$laneOrientation = '&laneHorizontal;'"><xsl:value-of select="@X"/></xsl:when>
				<xsl:otherwise><xsl:value-of select="@Y"/></xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		
		<xsl:variable name="offsetY">
			<xsl:choose>
				<xsl:when test="$laneOrientation = '&laneHorizontal;'"><xsl:value-of select="@Y"/></xsl:when>
				<xsl:otherwise><xsl:value-of select="@X"/></xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		
		<!-- Get this lane's top offset value as the Y offset of the note will have to be relative to this -->
		<xsl:variable name="swimLanes" select="../../../xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'SwimLanes']/sw:SwimLanes" />

		<xsl:variable name="laneOffset">
			<xsl:call-template name="GetLaneAbsOffset">
				<xsl:with-param name="swimLanes" select="$swimLanes" />
				<xsl:with-param name="laneID" select="@SwimLaneID" />
			</xsl:call-template>
		</xsl:variable>		

		<xsl:element name="extensions:Note">
			<!-- Generate note ID -->
			<xsl:attribute name="Id">
				<xsl:text>&noteIdPrefix;</xsl:text>
				<xsl:value-of select="count (../../preceding-sibling::xpdl:ExtendedAttribute[sw:ExtAttr/sw:NonFlowObject/sw:Annotation]) + 1" />
			</xsl:attribute>
			<xsl:element name="extensions:Location">
				<xsl:element name="extensions:XOffset">
					<xsl:call-template name="scaleX"><xsl:with-param name="value" select="$offsetX"/></xsl:call-template>
				</xsl:element>

				<xsl:element name="extensions:YOffset">
					<!--
						Y offset should be relative to top bound of the lane it's in.  Adjust the position of the note if the bottom of the note
						overlaps the lane boundary
					-->
					<xsl:call-template name="scaleY"><xsl:with-param name="value" select="$offsetY - $laneOffset"/></xsl:call-template>
				</xsl:element>
			</xsl:element>

			<xsl:element name="extensions:Text">
				<xsl:value-of select="sw:Annotation/sw:AnnotText"/>
			</xsl:element>
		</xsl:element>

	</xsl:if>
	
</xsl:template>

<!--
===================================================================================================================
	xpdl:Transitions
===================================================================================================================
-->
<xsl:template match="xpdl:Transitions">

	<!-- Get a list of steps with deadlines, all steps with deadlines will have their emerging transitions set to timer condition -->
	<xsl:variable name="deadlineSteps" select="parent::node()/xpdl:Activities/xpdl:Activity[xpdl:Deadline]" />
	
	<xsl:copy>
		<xsl:apply-templates select="@*" />

		<!-- Get ProcType -->
		<xsl:variable name="procProperties" select="../xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name ='ExtAttr']/sw:ExtAttr[@Name = 'ProcProperties']/sw:ProcProperties" />

		<xsl:variable name="startActivity" select="../xpdl:Activities/xpdl:Activity[xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='ExtAttr']/sw:ExtAttr[@Name='ObjType' and @Value='StartObject']]" />
		
		<xsl:if test="$startActivity and $procProperties/sw:SubProcParams/sw:SubPInParams/sw:SubPInParam">
			<!-- If this is a sub-process with input parameters then create a transition between the start object and the inpit param assignment script we will have created. -->
			<xsl:variable name="assignInputId"><xsl:call-template name="GetAssignInputParamsTaskName"><xsl:with-param name="startActivity" select="$startActivity"/></xsl:call-template></xsl:variable>

			<xpdl:Transition From="{$startActivity/@Id}" To="{$assignInputId}"/>
		</xsl:if>

		<xsl:apply-templates select="xpdl:Transition">
			<xsl:with-param name="startActivity" select="$startActivity"/>
			<xsl:with-param name="deadlineSteps"  select="$deadlineSteps"/>
			<xsl:with-param name="activities" select="../xpdl:Activities/xpdl:Activity"/>
			<xsl:with-param name="withdrawLinks" select="../xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'Link']/sw:Link"/>
		</xsl:apply-templates> 
		
		<xsl:variable name="subProcOutParams" select="$procProperties/sw:SubProcParams/sw:SubPOutParams/sw:SubPOutParam"/>
		
		<xsl:if test="count($subProcOutParams) &gt; 0"> <!-- If the workflow process has SubPOutParams-->
			<!-- For sub-processes with output parameters need to create transitions from ythe end of flow activities to the assign output params script tasks we created. -->
			<xsl:for-each select="../xpdl:Activities/xpdl:Activity">
				<xsl:variable name="actId" select="@Id"/>
				<xsl:choose>
					<xsl:when test="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name='ExtAttr']/sw:ExtAttr[@Name='ObjType']/@Value = 'StopObject'"><!-- if the activity is a Stop Object -->
						<!-- For stop objects we are inserting script before them so create a transition from the assign out params script to the stop object end event -->
						<xsl:variable name="assignOutputId"><xsl:call-template name="GetAssignOutputParamsTaskName"><xsl:with-param name="endOfFlowActivity" select="."/></xsl:call-template></xsl:variable>
						
						<xpdl:Transition From="{$assignOutputId}" To="{$actId}"/> 

					</xsl:when>
					<xsl:when test="not(../../xpdl:Transitions/xpdl:Transition[@From=$actId])"><!-- If the activity is one that has no outgoing transitions -->
						<!-- For non-stop-object activities with no out transitions then add a transition from them to their assign output params script. -->
						<xsl:variable name="assignOutputId"><xsl:call-template name="GetAssignOutputParamsTaskName"><xsl:with-param name="endOfFlowActivity" select="."/></xsl:call-template></xsl:variable>
						
						<xpdl:Transition From="{$actId}" To="{$assignOutputId}"/>
						
					</xsl:when>
				</xsl:choose>
			</xsl:for-each>
		
		</xsl:if>
		
		<xsl:call-template name="OutputErrorEventLinks">
			<xsl:with-param name="activities" select="../xpdl:Activities/xpdl:Activity"/>
			<xsl:with-param name="withdrawLinks" select="../xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'Link']/sw:Link"/>
			<xsl:with-param name="transitions" select="xpdl:Transition"/>
			<xsl:with-param name="deadlineSteps"  select="$deadlineSteps"/>
		</xsl:call-template>
		
	</xsl:copy>
</xsl:template>



<!--
===================================================================================================================
	Output the extra transitions required between objects with withdraw links and the error events we created for them
===================================================================================================================
-->
<xsl:template name="OutputErrorEventLinks">
	<xsl:param name="activities"/>
	<xsl:param name="withdrawLinks"/>
	<xsl:param name="transitions"/>
	<xsl:param name="deadlineSteps"/>
	
	<!-- Go thru each activity looking for ones that have withdraw links outgoing. -->
	<xsl:for-each select="$activities">
		<!-- We do this for all except where original object was a complex router - this will be converted directly to error event itself. -->
		<xsl:variable name="srcId" select="@Id"/>
		<xsl:variable name="objType" select="$activities[@Id = $srcId]/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'ObjType']/@Value"/>

		<!-- SIA-46: We now output task type none for complex router and NEVER re-use the complex router as the event itself - so it is no longer a special case. -->
		<!-- If this object is the source of any withdraw links from it's primary output then create transition to the extra error event activity to the right. -->
		<xsl:if test="$withdrawLinks[@srcObjID = $srcId]/sw:LinkFlags[@FromPrimary = 'true']">
			
			<xsl:variable name="tgtId">PrimaryWithdrawEvent_<xsl:value-of select="$srcId"/></xsl:variable>

			<xpdl:Transition From="{$srcId}" To="{$tgtId}" Id="Trans_{$srcId}_to_{$tgtId}">
			
				<!-- Re-create the condition of the first real transition that was originally from the step. For iPM it's ok to pick first because condition will be same on all.-->
				<xsl:choose>
					<xsl:when test="$objType = 'ConditionObject'">
						<xpdl:Condition Type="CONDITION" ScriptGrammar="iProcessScript">
							<xsl:variable name="expr" select="$activities[@Id = $srcId]/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'Expression']/sw:Expression/sw:ExprStr"/>
							<xsl:value-of select="$expr"/>
						</xpdl:Condition>
					</xsl:when>
					<xsl:otherwise>
					</xsl:otherwise>
				</xsl:choose>

			</xpdl:Transition>
			
		</xsl:if>
		
		<!-- If this object is the source of any withdraw links from it's secondary output then create transition to the extra error event activity to the below. -->
		<xsl:if test="$withdrawLinks[@srcObjID = $srcId]/sw:LinkFlags[@FromPrimary = 'false']">
			
			<xsl:variable name="tgtId">SecondaryWithdrawEvent_<xsl:value-of select="$srcId"/></xsl:variable>

			<xpdl:Transition From="{$srcId}" To="{$tgtId}" Id="Trans_{$srcId}_to_{$tgtId}">
			
				<!-- Re-create the condition of the first real transition that was originally from the step. For iPM it's ok to pick first because condition will be same on all.-->
				<xsl:choose>
					<xsl:when test="$objType = 'ConditionObject'">
						<xpdl:Condition Type="OTHERWISE"/>
					</xsl:when>
					<xsl:otherwise>
						<!-- secondary link from anything else is a deadline. -->
						<Condition Type="EXCEPTION">DontCare</Condition>

						<xsl:element name="ExtendedAttributes">
							<xsl:element name="ExtendedAttribute">
								<xsl:attribute name="Name">XPD</xsl:attribute>
								<xsl:element name="extensions:Transition">
									<xsl:element name="extensions:ExceptionType">TIMER</xsl:element>
								</xsl:element>
							</xsl:element>
						</xsl:element>
						
					</xsl:otherwise>
				</xsl:choose>
				
			</xpdl:Transition>
			
		</xsl:if>
	
	</xsl:for-each>

</xsl:template>


<!--
===================================================================================================================
	xpdl:Transition  - Any transitions that emerge from a step with a deadline will be set to a Timer Condition type.
===================================================================================================================
-->
<xsl:template match="xpdl:Transition">
	<xsl:param name="startActivity"/>
	<xsl:param name="deadlineSteps"/>
	<xsl:param name="activities"/>
	<xsl:param name="withdrawLinks"/>
	
	<xsl:variable name="procProperties" select="../../xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name ='ExtAttr']/sw:ExtAttr[@Name = 'ProcProperties']/sw:ProcProperties" />
	<xsl:copy>
		
		<xsl:variable name="srcId" select="@From"/>
		<xsl:variable name="srcObjType" select="$activities[@Id = $srcId]/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'ObjType']/@Value"/>

		<xsl:variable name="tgtId" select="@To"/>
		<xsl:variable name="tgtObjType" select="$activities[@Id = $tgtId]/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'ObjType']/@Value"/>

		<!-- We may need to redirect transitions that are from objects that have outgoing withdraw links to the appropriate error event that was inserted after the object. 
			We do this for all except where original object was a complex router - this will be converted directly to error event itself. -->
		<xsl:variable name="actualFromID">
			<xsl:choose>
				<!-- 
						If this transition is from start event.
						AND If we output an input parameter assignment script then create transition from start object to script task 
				-->
				<xsl:when test="$srcId = $startActivity/@Id and $procProperties/sw:SubProcParams/sw:SubPInParams/sw:SubPInParam">
					<!-- replace the source of transition from start with the input param assignment script. -->
					<xsl:call-template name="GetAssignInputParamsTaskName"><xsl:with-param name="startActivity" select="$startActivity"/></xsl:call-template>
				</xsl:when>

				<!-- SIA-46: We now output task type none for complex router and NEVER re-use the complex router as the event itself - so it is no longer a special case. -->
				<xsl:when test="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'LinkFlags']/sw:LinkFlags/@FromPrimary = 'true' and
										$withdrawLinks[@srcObjID = $srcId]/sw:LinkFlags/@FromPrimary = 'true'">
						<!-- This link is from the primary and there are withdraw links from the primary, so we need to link to the created error event -->
						<xsl:text>PrimaryWithdrawEvent_</xsl:text><xsl:value-of select="$srcId"/>
				</xsl:when>
				<!-- SIA-46: We now output task type none for complex router and NEVER re-use the complex router as the event itself - so it is no longer a special case. -->
				<xsl:when test="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'LinkFlags']/sw:LinkFlags/@FromPrimary = 'false' and
										$withdrawLinks[@srcObjID = $srcId]/sw:LinkFlags/@FromPrimary = 'false'">
						<!-- This link is from the secondary out and there are withdraw links from the secondary out, so we need to link to the created error event -->
						<xsl:text>SecondaryWithdrawEvent_</xsl:text><xsl:value-of select="$srcId"/>
				</xsl:when>
				
				<xsl:otherwise>
					<xsl:value-of select="$srcId"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		
		<xsl:variable name="actualToID">
			<xsl:choose>
				<xsl:when test="$tgtObjType ='StopObject' and $procProperties/sw:SubProcParams/sw:SubPOutParams/sw:SubPOutParam"><!--SIA-2 Redirect transition to output script instead-->
					<!-- 
						If this is a transition to a StopObject (EndEvent) AND it's a sub-process with output parameters 
							then we will have created an assign output parameters script for it so we must redirect any 
							inputs into the stop object to the assign output script object.
					-->
					<xsl:variable name="stopActivity" select="$activities[@Id = $tgtId]"/>
					<xsl:call-template name="GetAssignOutputParamsTaskName"><xsl:with-param name="endOfFlowActivity" select="$stopActivity"/></xsl:call-template>
						
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$tgtId"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		
		
		<xsl:attribute name="From"><xsl:value-of select="$actualFromID"/></xsl:attribute>
		<xsl:attribute name="To"><xsl:value-of select="$actualToID"/></xsl:attribute>
		<xsl:attribute name="Id"><xsl:value-of select="@Id"/></xsl:attribute>
		
		<!-- Set the link description -->
		<xsl:if test="xpdl:Description">
			<xsl:attribute name="Name">
				<xsl:value-of select="xpdl:Description"/>
			</xsl:attribute>
		</xsl:if>

		
		<!-- Never output condition if we have redirected to an error event (make all outputs from error event uncontrolled) -->
		<xsl:if test="$srcId = $actualFromID">
			<xsl:choose>
				<!-- If exception transition on deadline step, then add timer exception ext attr -->
				<xsl:when test="xpdl:Condition">
					<!-- Copy any conditional information -->
					<xsl:choose>
						<xsl:when test="xpdl:Condition/@Type = 'CONDITION'">
							<xpdl:Condition Type = 'CONDITION' ScriptGrammar="iProcessScript">
								<xsl:value-of select="xpdl:Condition/text()"/>
							</xpdl:Condition>
						</xsl:when>
						<xsl:otherwise>
							<xsl:copy-of select="xpdl:Condition"/>
						</xsl:otherwise>
					</xsl:choose>
					
					<xsl:if test="xpdl:Condition/@Type = 'EXCEPTION' and count($deadlineSteps[@Id = $srcId]) > 0">
						<xsl:element name="ExtendedAttributes">
							<xsl:element name="ExtendedAttribute">
								<xsl:attribute name="Name">XPD</xsl:attribute>
								<xsl:element name="extensions:Transition">
									<xsl:element name="extensions:ExceptionType">TIMER</xsl:element>
								</xsl:element>
							</xsl:element>
						</xsl:element>
	
					</xsl:if>
				</xsl:when>
			</xsl:choose>
		</xsl:if>
	</xsl:copy>
</xsl:template>

<!--
===================================================================================================================
	Template to get the input script data for a given workflow process
===================================================================================================================
-->
<xsl:template name="OutputInputScriptDataForWorkflowProcess">
	<xsl:param name="workflowProcess"/> <!-- xpdl:WorkflowProcess -->
	
	<xsl:variable name="procProperties" select="$workflowProcess/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'ProcProperties']/sw:ProcProperties" />	
	<xsl:variable name="dataFields" select="$workflowProcess/xpdl:DataFields/xpdl:DataField"/>
	
	<xsl:text>// Assign template formal parameter data to data fields &#10;</xsl:text>

	<xsl:for-each select="$procProperties/sw:SubProcParams/sw:SubPInParams/sw:SubPInParam">
		<!-- Input can only be mapped from field and not from expression, so expecting to be able to find datafield -->
		<xsl:variable name="paramName"><xsl:call-template name="GetParameterName"><xsl:with-param name="param" select="."/></xsl:call-template></xsl:variable>
			
			<xsl:variable name="dataId" select="sw:FieldRef/@fieldname | sw:Expression/sw:ExprStr/text()"/>
			<xsl:variable name="dataField" select="$dataFields[@Id = $dataId]"/>
			<xsl:value-of select="$dataField/@Id"/><xsl:text> := </xsl:text><xsl:value-of select="$paramName"/><xsl:text>; // </xsl:text><xsl:value-of select="@Description"/>
			<xsl:text>&#10;</xsl:text>
	</xsl:for-each>
</xsl:template>
<!--
===================================================================================================================
	xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'ObjType'] - Set the appropriate activity type
===================================================================================================================
-->
<xsl:template name="addExtActivityType">
	<xsl:param name="activity"/>
	
	<xsl:variable name="objType" select="$activity/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'ObjType']/@Value"/>

<!-- Check for start/stop steps and add the appropriate event flow types -->
	<xsl:choose>
		<xsl:when test="$objType = 'StartObject'">
			<xsl:element name="extensions:ActivityType">EVENT</xsl:element>
			<xsl:element name="extensions:EventFlowType">START</xsl:element>
		</xsl:when>
		<xsl:when test="$objType = 'StopObject'">
			<xsl:element name="extensions:ActivityType">EVENT</xsl:element>
			<xsl:element name="extensions:EventFlowType">END</xsl:element>		
		</xsl:when>
		<xsl:when test="$objType = 'ConditionObject'">
			<xsl:element name="extensions:ActivityType">GATEWAY</xsl:element>
			<xsl:element name="extensions:GatewayType">XOR_DATA</xsl:element>
		</xsl:when>
		<xsl:when test="$objType = 'WaitObject'">
			<xsl:element name="extensions:ActivityType">GATEWAY</xsl:element>
			<xsl:element name="extensions:GatewayType">PARALLEL</xsl:element>
		</xsl:when>
		<xsl:when test="$objType = 'EventObject'">
			<!-- Intermediate events can become...
				In-Flow Timer event 		- If it has a deadline and ONLY timer outgoing exception transitions and has incoming transitions.
				Receive task					- For all other situations
			-->
			<xsl:element name="extensions:ActivityType">EVENT</xsl:element>
			<xsl:choose>
				<xsl:when test="$activity/xpdl:Deadline"> <!-- this is the quick check -->
					<!-- now check outgoing transitions, if there are any other than timer exceptions then has to be a receive task. -->
					<!-- The TIMER / RECEIVE event flow types will not be recognised by studio v1, but will be recognised by v2's migration from v1 transformation -->
					<xsl:variable name="outTrans" select="$activity/../../xpdl:Transitions/xpdl:Transition[@From = $activity/@Id]"/>
					<xsl:choose>
						<xsl:when test="count($outTrans[not(starts-with(xpdl:Condition, 'DeadlineExpired_Trans_'))]) = 0">
							<xsl:element name="extensions:EventFlowType">TIMER</xsl:element>
						</xsl:when>
						<xsl:otherwise>
							<xsl:element name="extensions:EventFlowType">RECEIVE</xsl:element>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:when>
				<xsl:otherwise>
					<!-- All events that don't have deadline transitions can be interpreted as Receive tasks -->
					<xsl:element name="extensions:EventFlowType">RECEIVE</xsl:element>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:when>

		<!--
			Complex Routers have XOR Joins and AND splits.  As this is not supported in studio 
			the following mapping will be used:

			1. With no split = XOR_DATA,
			2. With split only = PARALLEL,
			3. With join only = XOR_DATA,
			4. With both join and split = PARALLEL.

			SIA-46: No longer convert ComplexRouter directly to error event.
					Also, we no longer treat Complex Router as a gateway because this sometime fit semantics of BPMN. 
					For instance a router with multi in is 'XOR join and parallel split'. 
					Task type None fits the semantics exactly (and is supported for export as complex router and semantics are the same in AMX BPM).
					Also, this makes withdraw action handling easier (as there were problems doing withdraw of a split/join router because withdraw link is
						translated to a throw/catch error event pair and you can't attach an error event to a gateway!
		-->
		<xsl:when test="$objType = 'ComplexRouterObject'">
		</xsl:when>
		
		<xsl:when test="(not($objType) or $objType = '') and $activity/xpdl:Route">
			<!-- 
				If there is no object type set this may be exported xpdl that hasn't been edited and reexported in iPM
				iPS doesn't like having route type activities with no extended attribute to say event or gateway so make sure we define
				one or the other.
			-->
			<xsl:element name="extensions:ActivityType">GATEWAY</xsl:element>
			<xsl:element name="extensions:GatewayType">XOR_DATA</xsl:element>
			
		</xsl:when>
	</xsl:choose>
	
</xsl:template>

<!-- 
===================================================================================================================
	node() - This template causes an input element and all attributes and child elements to be copied 'as is' to output document
		(unless the element has a specific template).
===================================================================================================================
-->
<xsl:template match="node()">
	<!-- Copy input element/attribute to output. -->
	<xsl:copy>
		<xsl:apply-templates select="@* | node()"/>
	</xsl:copy>
</xsl:template>

<!-- 
===================================================================================================================
	xpdl:Script - Ignore all elements of this type
===================================================================================================================
-->
<xsl:template match="xpdl:Script">
	<!-- Ignore xpdl:Script -->
</xsl:template>

<!--
===================================================================================================================
	xpdl:StartMode - Set the StartMode and FinishMode for each activity
===================================================================================================================
-->
<xsl:template match="xpdl:StartMode">
	<xsl:variable name="objType" select="parent::node()/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'ObjType']/@Value"/>
	
	<xsl:choose>
		<xsl:when test="$objType = 'StepObject'">
			<xsl:element name="StartMode">
				<xsl:element name="Manual"/>
			</xsl:element>
			<xsl:element name="FinishMode">
				<xsl:element name="Manual"/>
			</xsl:element>
		</xsl:when>
		<xsl:otherwise>
			<xsl:element name="StartMode">
				<xsl:element name="Automatic"/>
			</xsl:element>
			<xsl:element name="FinishMode">
				<xsl:element name="Automatic"/>
			</xsl:element>
		</xsl:otherwise>
	</xsl:choose>
	
	<!-- iPM does not output xpdl:Deadline when there are no deadline links EVEN when there is a deadline. defined -->
	<xsl:if test="not(../xpdl:Deadline) and ../xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'Deadline']/sw:Deadline">
		<!-- Don't output deadline for transaction control steps (they use sw:Deadline to define the defer commit transaction period. -->
		<xsl:if test="$objType != 'TCStepObject'">
			<Deadline>
				<xsl:attribute name="Execution">
					<xsl:choose>
						<xsl:when test="../xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'Deadline']/sw:Deadline/@withdraw = 'true'">
							<xsl:text>SYNCHR</xsl:text>
						</xsl:when>
						<xsl:otherwise>
							<xsl:text>ASYNCHR</xsl:text>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:attribute>
				
				<DeadlineCondition/>
				<ExceptionName>DeadlineExpired_Trans_None_<xsl:value-of select="generate-id(..)"/></ExceptionName>
			</Deadline>
		</xsl:if>
	
	</xsl:if>
	
</xsl:template>

<!--
===================================================================================================================
	xpdl:FinishMode - Ignore this element type as it is handled by xpdl:StartMode template above
===================================================================================================================
-->
<xsl:template match="xpdl:FinishMode">
	<!-- Do nothing as this is handled by the startmode above -->
</xsl:template>

<!--
===================================================================================================================
	xpdl:ExtendedAttributes - Any unhandled elements of this type should be ignored
===================================================================================================================
-->
<xsl:template match="xpdl:ExtendedAttributes">
	<!-- Do nothing -->
</xsl:template>

<!--
===================================================================================================================
	xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr'] - Any unhandled elements of this type should be ignored
===================================================================================================================
-->
<xsl:template match="xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']">
	<!-- Do nothing -->
</xsl:template>


<!--
===================================================================================================================
	GetHeaderTaggedLine = Given a string (of text lines) and a field separator (token) return the line that has the given header tag.
		This template is recursive - to process each line in turn it recurses with the remainder of the string.
		Outputs the remainder of line (after header tag and token) or null if not found.
===================================================================================================================
-->
<xsl:template name="GetHeaderTaggedLine">
	<xsl:param name="token"/>
	<xsl:param name="string"/>
	<xsl:param name="tag"/>
	
	<!-- Check there's something left in string. -->
	<xsl:if test="$string != ''">
	
		<xsl:variable name="hdrTag"><xsl:value-of select="$tag"/><xsl:value-of select="$token"/></xsl:variable>
		
		<xsl:choose>
			<xsl:when test="starts-with($string, $hdrTag)">
				<!-- Found the line we are after right at the start- truncate at end of line and output it -->
				<xsl:value-of select="substring-before(substring-after($string, $token), '&#10;')"/>
			</xsl:when>
			
			<xsl:otherwise>
				<!-- Check for subsequent line starting with tag -->
				<xsl:variable name="newlineTag"><xsl:text>&#10;</xsl:text><xsl:value-of select="$hdrTag"/></xsl:variable>
				
				<xsl:variable name="after" select="substring-after($string, $newlineTag)"/>
				<xsl:if test="$after">
					<xsl:value-of select="substring-before($after, '&#10;')"/>
				</xsl:if>
			
			</xsl:otherwise>
			
		</xsl:choose>
	
	</xsl:if>
	
</xsl:template>


<!--
===================================================================================================================
	FieldFromTokenLine = Given a string (line) and a field separator (token) return the nth (0 based idx) field separated byt the given token on given line
===================================================================================================================
-->
<xsl:template name="FieldFromTokenLine">
	<xsl:param name="token"/>
	<xsl:param name="line"/>
	<xsl:param name="idx"/>
	
	<xsl:choose>
		<xsl:when test="$idx = 0">
			<xsl:choose>
				<xsl:when test="contains($line, $token)">
					<xsl:value-of select="substring-before($line, $token)"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$line"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:when>
		<xsl:when test="string-length($line) > 0">
			<xsl:call-template name="FieldFromTokenLine">
				<xsl:with-param name="token" select="$token"/>
				<xsl:with-param name="line" select="substring-after($line, $token)"/>
				<xsl:with-param name="idx" select="$idx - 1"/>
			</xsl:call-template>
		</xsl:when>
	</xsl:choose>
	
</xsl:template>

<!-- 
===================================================================================================================
	Apply scaling factor to X/Y / Dimensions.
	Things in iPS are bigger than iPM so move / size  everything by 1.2
===================================================================================================================
-->
<xsl:template name="scaleX">
	<xsl:param name="value"/>
	<xsl:value-of select="round($value * $scaleX)"/>
</xsl:template>

<xsl:template name="antiScaleX">
	<xsl:param name="value"/>
	<xsl:value-of select="round($value div $scaleX)"/>
</xsl:template>

<xsl:template name="scaleY">
	<xsl:param name="value"/>
	<xsl:value-of select="round($value * $scaleY)"/>
</xsl:template>

<xsl:template name="antiScaleY">
	<xsl:param name="value"/>
	<xsl:value-of select="round($value div $scaleY)"/>
</xsl:template>

<!--
===================================================================================================================
    WSDL file creation.
===================================================================================================================
-->
<xsl:template match="xpdl:Activity/xpdl:ExtendedAttributes" mode="wsdl">
    <xsl:variable name="objType" select="xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'ObjType']/@Value" />
    <xsl:if test="$objType = 'EAIObject'">

        <xsl:variable name="act" select=".."/>

        <xsl:variable name="appId" select="$act/xpdl:Implementation/xpdl:Tool/@Id"/>
        <xsl:variable name="app" select="$act/../../xpdl:Applications/xpdl:Application[@Id = $appId]"/>
    
        <xsl:variable name="eaiType" select="$app/@Name"/>
        <xsl:variable name="eaiData" select="$app/xpdl:ExtendedAttributes/xpdl:ExtendedAttribute[@Name = 'ExtAttr']/sw:ExtAttr[@Name = 'EAIData']/sw:EAIData"/>
        
        <xsl:choose>
            <xsl:when test="$eaiType = 'EAI_BW'">
				<xsl:call-template name="EAIBwWsdl">
					<xsl:with-param name="eaiData" select="$eaiData"/>
				</xsl:call-template>
            </xsl:when>
            
            <xsl:when test="$eaiType = 'EAI_WEBSERVICES'">
                <xsl:call-template name="EAIWebServiceWsdl">
                    <xsl:with-param name="eaiData" select="$eaiData"/>
                </xsl:call-template>
            </xsl:when>
        
        </xsl:choose>
    </xsl:if>
</xsl:template>

<!--
===================================================================================================================
    Create a WSDL file from embedded BW WSDL content.
===================================================================================================================
-->
<xsl:template name="EAIBwWsdl">
    <xsl:param name="eaiData"/>
    
    <xsl:variable name="text" select="$eaiData/sw:EAIRunData/sw:EAITypeData/sw:EAIText"/>
    <xsl:variable name="eaidoc" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.unescape($text)"/>
    <xsl:variable name="swBusinessWorksPanel" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.unescape($eaidoc/eaijava:EAI_Java_Plugin/eaijava:Services/eaijava:Service_Data[eaijava:Name = 'com.staffware.integration.eaidesigntime.gui.integrationwizard.SWBusinessWorksPanel']/eaijava:Payload)"/>
    <xsl:variable name="fileName" select="$swBusinessWorksPanel/eaibw:BW_Plugin/eaibw:BusinessWorks/eaibw:File_Name"/>
    <xsl:variable name="wsdlText" select="$swBusinessWorksPanel/eaibw:BW_Plugin/eaibw:BusinessWorks/eaibw:File_Contents"/>
    
    <xsl:value-of select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.createWsdlFile($project, $fileName, $wsdlText)"/>
</xsl:template>

<xsl:template name="EAIWebServiceWsdl">
    <xsl:param name="eaiData"/>
    <xsl:variable name="text" select="$eaiData/sw:EAIRunData/sw:EAITypeData/sw:EAIText"/>
    <xsl:variable name="eaidoc" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.unescape($text)"/>
    <xsl:variable name="wsdlText" select="$eaidoc/eaiws:Web_Services_EAI_Plugin/eaiws:WSDL_Definition/eaiws:WSDL_Content"/>
    <xsl:variable name="serviceName" select="$eaidoc/eaiws:Web_Services_EAI_Plugin/eaiws:Selected_Operation/eaiws:Service_Name"/>
    <xsl:variable name="fileName" select="concat($serviceName, '.wsdl')"/>

    <xsl:variable name="nameSpace" select="$wsdlText/*/@targetNamespace"/>

    <xsl:value-of select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.createWsdlFile($project, $fileName, $wsdlText)"/>

	<!-- Do withdraw action WSDL too. -->
    <xsl:variable name="wtext" select="$eaiData/sw:EAIWithdrawData/sw:EAITypeData/sw:EAIText"/>
    <xsl:if test="$wtext != ''">
		<xsl:variable name="weaidoc" select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.unescape($wtext)"/>
		<xsl:variable name="wwsdlText" select="$weaidoc/eaiws:Web_Services_EAI_Plugin/eaiws:WSDL_Definition/eaiws:WSDL_Content"/>
		<xsl:variable name="wserviceName" select="$weaidoc/eaiws:Web_Services_EAI_Plugin/eaiws:Selected_Operation/eaiws:Service_Name"/>
		<xsl:variable name="wnameSpace" select="$wwsdlText/*/@targetNamespace"/>
		
		<xsl:variable name="wfileName">
			<xsl:choose>
				<xsl:when test="$wnameSpace = $nameSpace">
					<!-- if the run and wirthdraw oeprations are in same name space they jolly well ought to be in same wsdl. -->
					<xsl:value-of select="$fileName"/>
				</xsl:when>
				<xsl:when test="$wserviceName = $serviceName">
					<!-- Diff namespace same service name so don't want to clash file names. -->
					<xsl:value-of select="concat($wserviceName, '2.wsdl')"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="concat($wserviceName, '.wsdl')"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		
		<xsl:value-of select="java:com.tibco.xpd.iprocess.amxbpm.imports.validations.IpmImportUtil.createWsdlFile($project, $wfileName, $wwsdlText)"/>
	</xsl:if>
	
</xsl:template>

<!--
===================================================================================================================
Output attribute but only if value exists in source.
===================================================================================================================
-->
<xsl:template name="AddAttributeIfDefined">
	<xsl:param name="name"/>
	<xsl:param name="value"/>
	<xsl:param name="default" select="''"/>
	
	<xsl:choose>
		<xsl:when test="$value and $value != ''">
			<xsl:attribute name="{$name}"><xsl:value-of select="$value"/></xsl:attribute>
		</xsl:when>
		<xsl:when test="$default != ''">
			<xsl:attribute name="{$name}"><xsl:value-of select="$default"/></xsl:attribute>
		</xsl:when>
	</xsl:choose>

</xsl:template>

<!--
===================================================================================================================
Output attribute but only if value exists in source.
===================================================================================================================
-->
<xsl:template name="AddElementIfDefined">
	<xsl:param name="name"/>
	<xsl:param name="value"/>
	<xsl:param name="default" select="''"/>
	
	<xsl:choose>
		<xsl:when test="$value and $value != ''">
			<xsl:element name="{$name}"><xsl:value-of select="$value"/></xsl:element>
		</xsl:when>
		<xsl:when test="$default != ''">
			<xsl:element name="{$name}"><xsl:value-of select="$default"/></xsl:element>
		</xsl:when>
	</xsl:choose>

</xsl:template>

<!--
===================================================================================================================
	General template to copy all attributes and text of an element
===================================================================================================================
-->
<xsl:template match="@* | node()">
	<!-- Copy input attribute to output. -->
	<xsl:copy>
		<xsl:apply-templates select="@* | node()" />
	</xsl:copy>
</xsl:template>

<xsl:template match="@* | node()" mode="wsdl">
    <xsl:apply-templates select="@* | node()" mode="wsdl"/>
</xsl:template>

</xsl:stylesheet>
