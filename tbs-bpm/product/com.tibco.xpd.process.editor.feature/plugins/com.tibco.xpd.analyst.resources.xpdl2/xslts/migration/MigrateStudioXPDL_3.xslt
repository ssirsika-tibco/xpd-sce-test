<?xml version="1.0" encoding="UTF-8"?>
<!--
	===================================================================================================================
	XSLT:       MigrateStudioXPDL_3.xslt
	
	DESCRIPTION:
	This xslt will updgrade a xpdl of format version 3 to format version 4.  This includes (summary):
	1. Split INOUT DataMapping elements into separate IN and OUT.
	2. Add parameter sequencing info for iProcess
    3. Add step index from iProcess step num.
	
	===================================================================================================================
-->

<!-- 
	DON'T USE <xsl:copy> for source doc's extensions:elements !!!!!  THIS FORCES ANY MISSING namespace declarations from old document onto attributes of the copied element.
	
	When we created the <Package> root element we forced swap of extensions: to be namespace 1.0.0
	
	If we create an xsl:element then xslt engine will just assume it's for the output doc's idea of "extensions" namespace.
	
	If we use xsl:copy the xsl engine assumes the element is from the source document' s extensions namespace.
	
	Any extensions: elements output from this xslt will have to have the new namespace provided, otherwise it will carry the old namespace to the output file
-->
<xsl:stylesheet version="1.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xpdl2="http://www.wfmc.org/2004/XPDL2.0alpha"
	xmlns:xpdExt="http://www.tibco.com/XPD/xpdExtension1.0.0"
	xmlns:ej="http://www.tibco.com/XPD/EAIJava1.0.0"
	xmlns:simulation="http://www.tibco.com/xpd/Simulation1.0.1"
	xmlns:database="http://www.tibco.com/XPD/database1.0.0"
	xmlns:email="http://www.tibco.com/XPD/email1.0.0"
	xmlns:iProcessExt="http://www.tibco.com/XPD/iProcessExt1.0.0"	
	xmlns:cim="http://www.tibco.com/xpd/cimService1.0.0"	
	xmlns:java="http://xml.apache.org/xslt/java"
	>


	<xsl:output method="xml" version="1.0" encoding="UTF-8"
		indent="yes" />

	<!--
		===============================================================================
		XSLT processing start point (process whole document)
		===============================================================================
	-->
	<xsl:template match="/">
		<xsl:apply-templates />
	</xsl:template>

	<!--
		===============================================================================
		Handle package - do default on everything EXCEPT extended attributes 
		===============================================================================
	-->
	<xsl:template match="xpdl2:Package">
		<xpdl2:Package>
			<!-- Process everything in package... -->
			<!-- SID CIM - Must apply-templates individually so that we can deal with migrating OR creating PackageDataFields if they're not there  
						AND STILL be able to maintain element ordering -->

			<!-- process all the package attributes. -->
			<xsl:apply-templates select="@*" />
			
			<!-- process everything in schema before /Xpdl2:Package/xpdl2:DataFields -->
			<xsl:apply-templates select="xpdl2:PackageHeader | xpdl2:RedefinableHeader | xpdl2:ConformanceClass | xpdl2:Script | xpdl2:ExternalPackages | xpdl2:TypeDeclarations | xpdl2:Participants | xpdl2:Applications"/>
			
			<!-- process package datafields (has some special stuff for CIM processes -->
			<xsl:call-template name="outputPackageDataFields"/>
			
			<!-- process everything after datafields. -->
			<xsl:apply-templates select="xpdl2:PartnerLinkTypes | xpdl2:Pools | xpdl2:MessageFlows | xpdl2:Associations | xpdl2:Artifacts | xpdl2:WorkflowProcesses"/>
			
			<!-- Make sure output contains extended attribute for format version... -->
			<xsl:element name="xpdl2:ExtendedAttributes">
				<xsl:element name="xpdl2:ExtendedAttribute">
					<xsl:attribute name="Name">FormatVersion</xsl:attribute>
					<xsl:attribute name="Value">4</xsl:attribute>
				</xsl:element>

				<xsl:for-each
					select="xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute">
					<xsl:if test="@Name != 'FormatVersion'">
						<xsl:apply-templates select="." />
					</xsl:if>
				</xsl:for-each>

			</xsl:element>

			<!-- Then copy all the extension elements... -->
			<xsl:for-each
				select="/xpdl2:Package/*[not(starts-with(name(), 'xpdl2:'))]">
				<xsl:copy>
					<xsl:apply-templates select="@* | node()" />
				</xsl:copy>
			</xsl:for-each>
			
			<!-- SID CIM - removed CIM "copy process non=ActivityName extended attribute fields to package" 
						This is now handled in the xpdl2:Package/xpdl2:DataFields template below.
			-->
			
		</xpdl2:Package>
	</xsl:template>


	<!-- 
		===============================================================================
		Add parameter sequencing information to process.
		===============================================================================
	-->
	<xsl:template match="xpdl2:WorkflowProcess">

		<!-- Get a list of all activities that do not have an iProcess step number. -->
		<xsl:variable name="actsWithoutStepNum" select="xpdl2:Activities/xpdl2:Activity[not(xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'iProcessStepNum'])]"/>

		<!-- Get the highest step number allocated to activities with step numbers (get all stepNum extended attributes, sort by high->low  and output just the first). -->
		<xsl:variable name="highestStepNum">
			<xsl:for-each select="xpdl2:Activities/xpdl2:Activity/xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'iProcessStepNum']">
				<xsl:sort data-type="number" order="descending" select="@Value"/>
				<xsl:if test="position() = 1">
					<xsl:value-of select="@Value"/>
				</xsl:if>
			</xsl:for-each>
		</xsl:variable>

		<xpdl2:WorkflowProcess>
			<xsl:apply-templates select="@*"/>

			<xsl:variable name="isIPM"><xsl:call-template name="isIProcess"><xsl:with-param name="process" select="."/></xsl:call-template></xsl:variable>
			<xsl:if test="$isIPM = 'true'">
				<xsl:attribute name="iProcessExt:MaxStepIndex"><xsl:value-of select="$highestStepNum + count($actsWithoutStepNum) "/></xsl:attribute>
				<xsl:attribute name="iProcessExt:NextParamSequenceNum"><xsl:value-of select="count(xpdl2:FormalParameters/xpdl2:FormalParameter) + 1"/></xsl:attribute>
			</xsl:if>

			<xsl:apply-templates select="*">
				<xsl:with-param name="actsWithoutStepNum" select="$actsWithoutStepNum"/>
				<xsl:with-param name="highestStepNum" select="$highestStepNum"/>
			</xsl:apply-templates>
		
		</xpdl2:WorkflowProcess>
		
	</xsl:template>

	<!-- 
		===============================================================================
		PackageHeader/xpdl2:Created(convert old date format to new one).
		===============================================================================
	-->
	<xsl:template match="xpdl2:PackageHeader/xpdl2:Created">
		<xpdl2:Created><xsl:call-template name="convertDate"><xsl:with-param name="date" select="./text()"/></xsl:call-template></xpdl2:Created>
	</xsl:template>

	<!-- 
		===============================================================================
		xpdl2:DataField/xpdl2:InitialValue
		===============================================================================
	-->
	<xsl:template match="xpdl2:DataField/xpdl2:InitialValue">
		<xsl:variable name="value">
			<xsl:choose>
				<xsl:when test="../xpdl2:DataType/xpdl2:BasicType/@Type = 'DATETIME'">
					<xsl:choose>
						<xsl:when test="../@IsArray = 'TRUE'">
							<!-- For arrays convert possible multiple dates separate by comma -->
							<xsl:call-template name="convertMultiDates"><xsl:with-param name="dates" select="./text()"/></xsl:call-template>
						</xsl:when>
						<xsl:otherwise>
							<xsl:call-template name="convertDate"><xsl:with-param name="date" select="./text()"/></xsl:call-template>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:when>

				<xsl:otherwise>
					<xsl:value-of select="."/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		
		<xpdl2:InitialValue><xsl:value-of select="$value"/></xpdl2:InitialValue>
	</xsl:template>


	<!--
		===============================================================================
		Don't do anything by default with original package ext attrs 
		(We want to ensure that FormatVersion gets output regardless of the initial state so we handle extended attrs separately 
		in the package template at the top of this xslt.
		===============================================================================
	-->
	<xsl:template match="/xpdl2:Package/xpdl2:ExtendedAttributes">
	</xsl:template>

	<!-- And the same with xpdl2:Package/xpdExt (as these appear after extended attributes. -->
	<xsl:template
		match="/xpdl2:Package/*[not(starts-with(name(), 'xpdl2:'))]">
	</xsl:template>

	<!-- 
		===============================================================================
		Ignore old IProcessWebServiceInvocationStyle which has now moved to iProcessExt:TaskProperties/WebServiceTask
		===============================================================================
	-->
	<xsl:template match="xpdl2:ExtendedAttribute">
		<xsl:if test="@Name != 'IProcessWebServiceInvocationStyle'">
			<xsl:copy-of select="." />
		</xsl:if>
	</xsl:template>

	
	<!--
		===============================================================================
		Fix existing user task parameters
		===============================================================================
	-->
	<xsl:template match="xpdl2:Activity">
		<xsl:param name="actsWithoutStepNum"/>
		<xsl:param name="highestStepNum"/>
		
		<xsl:variable name="activityId" select="@Id"/>

		<xsl:variable name="isIPM"><xsl:call-template name="isIProcess"><xsl:with-param name="process" select="ancestor::xpdl2:WorkflowProcess"/></xsl:call-template></xsl:variable>
		
		<xsl:element name="xpdl2:Activity">

			<xsl:if test="$isIPM = 'true'">
				<xsl:attribute name="iProcessExt:StepIndex">
					<xsl:call-template name="GetiProcessStepIndex">
						<xsl:with-param name="act" select="."/>
						<xsl:with-param name="actsWithoutStepNum" select="$actsWithoutStepNum"/>
						<xsl:with-param name="highestStepNum" select="$highestStepNum"/>
					</xsl:call-template>
				</xsl:attribute>
			</xsl:if>
					
			<xsl:apply-templates select="@* | node()" />
						
			<xsl:if
				test="(count(xpdl2:Implementation/xpdl2:Task/xpdl2:TaskUser/xpdl2:MessageIn/xpdl2:ActualParameters/xpdl2:ActualParameter) + count(xpdl2:Implementation/xpdl2:Task/xpdl2:TaskUser/xpdl2:MessageOut/xpdl2:ActualParameters/xpdl2:ActualParameter)) > 0">
				<xsl:element name="xpdExt:AssociatedParameters">					
					<xsl:for-each
						select="xpdl2:Implementation/xpdl2:Task/xpdl2:TaskUser/xpdl2:MessageIn/xpdl2:ActualParameters/xpdl2:ActualParameter">
						<xsl:variable name="formal" select="." />
						<xsl:element
							name="xpdExt:AssociatedParameter">
							<xsl:attribute name="FormalParam"><xsl:value-of
									select="$formal" />
						</xsl:attribute>
							<xsl:choose>																					
								<xsl:when test="function-available('java:com.tibco.xpd.xpdl2.edit.util.XsltUtils.addActualParameter')">
								    <xsl:value-of select="java:com.tibco.xpd.xpdl2.edit.util.XsltUtils.addActualParameter($activityId, $formal)" />									
								</xsl:when>								
							</xsl:choose>
							<xsl:choose>
								<xsl:when
									test="count(../../../xpdl2:MessageOut/xpdl2:ActualParameters/xpdl2:ActualParameter[text() = $formal]) > 0">
									<xsl:attribute name="Mode">INOUT</xsl:attribute>
								</xsl:when>
								<xsl:otherwise>
									<xsl:attribute name="Mode">IN</xsl:attribute>
								</xsl:otherwise>
							</xsl:choose>
							<xsl:attribute name="Mandatory">false</xsl:attribute>
							<xsl:element name="xpdl2:Description" />
						</xsl:element>
					</xsl:for-each>
					<xsl:for-each
						select="xpdl2:Implementation/xpdl2:Task/xpdl2:TaskUser/xpdl2:MessageOut/xpdl2:ActualParameters/xpdl2:ActualParameter">
						<xsl:variable name="formal" select="." />
						<xsl:if
							test="count(../../../xpdl2:MessageIn/xpdl2:ActualParameters/xpdl2:ActualParameter[text() = $formal]) = 0">
							<xsl:element
								name="xpdExt:AssociatedParameter">
								<xsl:attribute name="FormalParam"><xsl:value-of
										select="$formal" />
                                </xsl:attribute>
                                <xsl:choose>
								<xsl:when test="function-available('java:com.tibco.xpd.xpdl2.edit.util.XsltUtils.addActualParameter')">
									<xsl:value-of select="java:com.tibco.xpd.xpdl2.edit.util.XsltUtils.addActualParameter($activityId, $formal)" />									 
								</xsl:when>								
								</xsl:choose>
								<xsl:attribute name="Mode">OUT</xsl:attribute>
								<xsl:attribute name="Mandatory">false</xsl:attribute>
								<xsl:element name="xpdl2:Description" />
							</xsl:element>
						</xsl:if>
					</xsl:for-each>
					<!-- Add scripts. -->
					<xsl:for-each
						select="xpdl2:Implementation/xpdl2:Task/xpdl2:TaskUser/xpdExt:UserTaskScripts/xpdExt:OpenScript | xpdl2:Implementation/xpdl2:Task/xpdl2:TaskUser/xpdExt:UserTaskScripts/xpdExt:CloseScript | xpdl2:Implementation/xpdl2:Task/xpdl2:TaskUser/xpdExt:UserTaskScripts/xpdExt:SubmitScript">
						<xsl:variable name="script" select="." />
						<xsl:choose>
						<xsl:when test="function-available('java:com.tibco.xpd.xpdl2.edit.util.XsltUtils.addScript')">
							<xsl:value-of select="java:com.tibco.xpd.xpdl2.edit.util.XsltUtils.addScript($activityId, $script)" />							 
						</xsl:when>						
						</xsl:choose>
					</xsl:for-each>
					<!-- Add destinations. -->
					<xsl:for-each
						select="ancestor::xpdl2:WorkflowProcess/xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'Destination']">
						<xsl:variable name="processDestination" select="@Value" />
						<xsl:choose>
						<xsl:when test="function-available('java:com.tibco.xpd.xpdl2.edit.util.XsltUtils.addProcessDestination')">
							<xsl:value-of select="java:com.tibco.xpd.xpdl2.edit.util.XsltUtils.addProcessDestination(ancestor::xpdl2:WorkflowProcess/@Id, $processDestination)" />							 
						</xsl:when>						
						</xsl:choose>
					</xsl:for-each>
					<!-- Add activity data fields and parameters. -->
					<xsl:for-each
						select="ancestor::xpdl2:WorkflowProcess/xpdl2:FormalParameters/xpdl2:FormalParameter | ancestor::xpdl2:WorkflowProcess/xpdl2:DataFields/xpdl2:DataField | //xpdl2:Package/xpdl2:DataFields/xpdl2:DataField">
						<xsl:variable name="data" select="." />
						<xsl:choose>
						<xsl:when test="function-available('java:com.tibco.xpd.xpdl2.edit.util.XsltUtils.addProcessData')">
							<xsl:value-of select="java:com.tibco.xpd.xpdl2.edit.util.XsltUtils.addProcessData(ancestor::xpdl2:WorkflowProcess/@Id, $data/@Name, $data/@Mode)" />							 
						</xsl:when>						
						</xsl:choose>
					</xsl:for-each>
					<!-- Transform scripts variables to xpdExt:AssociatedParameter. -->
					<xsl:choose>
						<xsl:when test="function-available('java:com.tibco.xpd.xpdl2.edit.util.XsltUtils.transformScriptVariables')">
							<xsl:variable name="scriptParameters" select="java:com.tibco.xpd.xpdl2.edit.util.XsltUtils.transformScriptVariables(ancestor::xpdl2:WorkflowProcess/@Id, @Id)" /> 
							<xsl:if test="$scriptParameters/xpdExt:AssociatedParameters">
							    <xsl:copy-of select="$scriptParameters/xpdExt:AssociatedParameters/*"/>
							</xsl:if>
						</xsl:when>						
					</xsl:choose>
				</xsl:element>
			</xsl:if>
			<!-- For WebService service tasks, convert old "IProcessWebServiceInvocationStyle" ExtendedAttribute setting to new iProcessExt:TaskProperties/WebServiceTask element for it. -->
			<xsl:if
				test="xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService[@xpdExt:ImplementationType='WebService' or @xpdExt:ImplementationType='Web Service']">
				<!-- Get the v2 equivalent extended attribute for invocation style (i.e. from activity or process if that's not there or package if that's not there) -->
				<xsl:variable name="actInvocStyle"
					select="xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'IProcessWebServiceInvocationStyle']/@Value" />

				<xsl:variable name="procInvocStyle"
					select="ancestor::xpdl2:WorkflowProcess/xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'IProcessWebServiceInvocationStyle']/@Value" />

				<xsl:variable name="pkgInvocStyle"
					select="/xpdl2:Package/xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'IProcessWebServiceInvocationStyle']/@Value" />

				<xsl:variable name="invocStyle">
					<xsl:choose>
						<xsl:when test="$actInvocStyle != ''">
							<xsl:value-of select="$actInvocStyle" />
						</xsl:when>
						<xsl:when test="$procInvocStyle != ''">
							<xsl:value-of select="$procInvocStyle" />
						</xsl:when>
						<xsl:when test="$pkgInvocStyle != ''">
							<xsl:value-of select="$pkgInvocStyle" />
						</xsl:when>
					</xsl:choose>
				</xsl:variable>

				<xsl:if test="$invocStyle != ''">
					<iProcessExt:TaskProperties>
						<iProcessExt:WebServiceTask>
							<xsl:attribute name="InvocationStyle">
								<xsl:choose>
									<xsl:when
										test="$invocStyle = 'ASYNC_WITH_REPLY'">AsynchWithReply</xsl:when>
									<xsl:otherwise>AutoDelayedRelease</xsl:otherwise>
								</xsl:choose>
							</xsl:attribute>
						</iProcessExt:WebServiceTask>
					</iProcessExt:TaskProperties>
				</xsl:if>

			</xsl:if>
			<xsl:apply-templates
				select="xpdl2:Implementation/xpdl2:Task/*"
				mode="AllocationStrategy" />
			<xsl:apply-templates select="xpdl2:Event/*"
				mode="AllocationStrategy" />
		</xsl:element>
	</xsl:template>

<!-- 
====================================================================================================
Return the iProcess step number for the given activity
====================================================================================================
-->
	<xsl:template name="GetiProcessStepIndex">
		<xsl:param name="act"/>
		<xsl:param name="actsWithoutStepNum"/>
		<xsl:param name="highestStepNum"/>
	
		<xsl:variable name="stepIdx">
			<xsl:choose>
				<xsl:when test="$act/xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'iProcessStepNum']/@Value != ''">
					<xsl:value-of select="$act/xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'iProcessStepNum']/@Value"/>
				</xsl:when>
				<xsl:otherwise>
					<!-- Will have to calculate the step num of this activity from it's position in list of activities without step numbers. -->
					<xsl:for-each select="$actsWithoutStepNum">
						<xsl:if test="@Id = $act/@Id">
							<xsl:value-of select="position() + $highestStepNum"/>
						</xsl:if>
					</xsl:for-each>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		
		<xsl:value-of select="$stepIdx"/>
	</xsl:template>


	<xsl:template match="xpdl2:TaskUser" mode="AllocationStrategy"
		priority="3">
		<xsl:element name="xpdExt:ActivityResourcePatterns">
			<xsl:element name="xpdExt:AllocationStrategy">
				<xsl:attribute name="xpdExt:Offer">false</xsl:attribute>
				<xsl:attribute name="xpdExt:Strategy">SYSTEM_DETERMINED</xsl:attribute>
			</xsl:element>
		</xsl:element>
	</xsl:template>

	<xsl:template
		match="xpdl2:TaskService | xpdl2:TaskScript | xpdl2:TaskSend | xpdl2:TaskReceive | xpdl2:StartEvent[@Trigger='Message'] | xpdl2:IntermediateEvent[@Trigger='Message'] | xpdl2:EndEvent[@Result='Message']"
		mode="AllocationStrategy" priority="3">
		<xsl:element name="xpdExt:ActivityResourcePatterns">
			<xsl:element name="xpdExt:AllocationStrategy">
				<xsl:attribute name="xpdExt:Strategy">SYSTEM_DETERMINED</xsl:attribute>
			</xsl:element>
		</xsl:element>
	</xsl:template>

	<xsl:template match="*" mode="AllocationStrategy" priority="0">
	</xsl:template>


	<!--
		===============================================================================
		Ignore task user actual parameters
		===============================================================================
	-->
	<xsl:template match="xpdl2:TaskUser/*/xpdl2:ActualParameters">
	</xsl:template>

	<!--
		===============================================================================
		Fix existing DataMapping nodes (web service)
		===============================================================================
	-->
	<xsl:template
		match="xpdl2:TaskService[@xpdExt:ImplementationType='WebService' or @xpdExt:ImplementationType='Web Service' or @xpdExt:ImplementationType='BW Service']/xpdl2:MessageIn/xpdl2:DataMappings/xpdl2:DataMapping">
		<xsl:variable name="script" select="xpdl2:Actual" />
		<xsl:call-template name="parseWebServiceInput">
			<xsl:with-param name="script" select="$script" />
		</xsl:call-template>
	</xsl:template>

	<!--
		===============================================================================
		Fix existing DataMapping nodes (web service)
		===============================================================================
	-->
	<xsl:template
		match="xpdl2:TaskService[@xpdExt:ImplementationType='WebService' or @xpdExt:ImplementationType='Web Service' or @xpdExt:ImplementationType='BW Service']/xpdl2:MessageOut">
		<xsl:element name="xpdl2:MessageOut">
			<xsl:attribute name="Id"><xsl:value-of select="@Id" />
			</xsl:attribute>
			<xsl:element name="xpdl2:DataMappings">
				<xsl:for-each
					select="../../../../xpdl2:Assignments/xpdl2:Assignment">
					<xsl:variable name="target" select="xpdl2:Target" />
					<xsl:variable name="expression"
						select="xpdl2:Expression" />
					<xsl:variable name="paths"
						select="substring-before($expression, '&#10;var ')" />
					<xsl:variable name="script"
						select="substring-after(substring-after($expression, $paths), '&#10;')" />
					<xsl:call-template name="parseWebServiceOutput">
						<xsl:with-param name="paths" select="$paths" />
						<xsl:with-param name="target" select="$target" />
						<xsl:with-param name="script" select="$script" />
					</xsl:call-template>
				</xsl:for-each>
                <xsl:copy-of select="xpdl2:DataMappings/*"/>
			</xsl:element>
		</xsl:element>
	</xsl:template>

	<!--
		===============================================================================
		Fix existing DataMapping nodes (TaskSend)
		===============================================================================
	-->
	<xsl:template
		match="xpdl2:TaskSend[@xpdExt:ImplementationType='WebService' or @xpdExt:ImplementationType='Web Service']/xpdl2:Message">
		<xsl:element name="xpdl2:Message">
			<xsl:attribute name="Id"><xsl:value-of select="@Id" />
            </xsl:attribute>
			<xsl:element name="xpdl2:DataMappings">
				<xsl:apply-templates
					select="xpdl2:DataMappings/xpdl2:DataMapping" mode="TaskSend" />
				<xsl:for-each
					select="../../../../xpdl2:Assignments/xpdl2:Assignment">
					<xsl:variable name="target" select="xpdl2:Target" />
					<xsl:variable name="expression"
						select="xpdl2:Expression" />
					<xsl:variable name="paths"
						select="substring-before($expression, '&#10;var ')" />
					<xsl:variable name="script"
						select="substring-after(substring-after($expression, $paths), '&#10;')" />
					<xsl:call-template name="parseWebServiceOutput">
						<xsl:with-param name="paths" select="$paths" />
						<xsl:with-param name="target" select="$target" />
						<xsl:with-param name="script" select="$script" />
					</xsl:call-template>
				</xsl:for-each>
			</xsl:element>
		</xsl:element>
	</xsl:template>

	<!--
		===============================================================================
		Fix existing DataMapping nodes (TaskSend)
		===============================================================================
	-->
	<xsl:template match="xpdl2:DataMapping" mode="TaskSend">
		<xsl:variable name="script" select="xpdl2:Actual" />
		<xsl:call-template name="parseWebServiceInput">
			<xsl:with-param name="script" select="$script" />
		</xsl:call-template>
	</xsl:template>

	<!--
		===============================================================================
		Fix existing DataMapping nodes (TaskReceive)
		===============================================================================
	-->
	<xsl:template
		match="xpdl2:TaskReceive[@xpdExt:ImplementationType='WebService' or @xpdExt:ImplementationType='Web Service']/xpdl2:Message">
		<xsl:element name="xpdl2:Message">
			<xsl:attribute name="Id"><xsl:value-of select="@Id" />
            </xsl:attribute>
			<xsl:element name="xpdl2:DataMappings">
				<xsl:apply-templates
					select="xpdl2:DataMappings/xpdl2:DataMapping" mode="TaskReceive" />
				<xsl:for-each
					select="../../../../xpdl2:Assignments/xpdl2:Assignment">
					<xsl:variable name="target" select="xpdl2:Target" />
					<xsl:variable name="expression"
						select="xpdl2:Expression" />
					<xsl:variable name="paths"
						select="substring-before($expression, '&#10;var ')" />
					<xsl:variable name="script"
						select="substring-after(substring-after($expression, $paths), '&#10;')" />
					<xsl:call-template name="parseWebServiceOutput">
						<xsl:with-param name="paths" select="$paths" />
						<xsl:with-param name="target" select="$target" />
						<xsl:with-param name="script" select="$script" />
					</xsl:call-template>
				</xsl:for-each>
			</xsl:element>
		</xsl:element>
	</xsl:template>

	<!--
		===============================================================================
		Fix existing DataMapping nodes (TaskReceive)
		===============================================================================
	-->
	<xsl:template match="xpdl2:DataMapping" mode="TaskReceive">
		<xsl:variable name="script" select="xpdl2:Actual" />
		<xsl:call-template name="parseWebServiceInput">
			<xsl:with-param name="script" select="$script" />
		</xsl:call-template>
	</xsl:template>

	<!--
		===============================================================================
		Fix existing DataMapping nodes (Java)
		===============================================================================
	-->
	<xsl:template
		match="xpdl2:TaskService[@xpdExt:ImplementationType='Java']/xpdl2:MessageIn/xpdl2:DataMappings/xpdl2:DataMapping">
		<xsl:variable name="actual" select="xpdl2:Actual" />
		<xsl:variable name="assignment"
			select="substring-before($actual, ';')" />
		<xsl:variable name="script"
			select="substring-after($assignment, '=')" />
		<xsl:element name="xpdl2:DataMapping">
			<xsl:attribute name="Direction">IN</xsl:attribute>
			<xsl:attribute name="Formal"><xsl:value-of select="@Formal" />
                        </xsl:attribute>
			<xsl:choose>
				<xsl:when
					test="contains($script, '.substring(') or contains($actual, '&quot;')">
					<xsl:element name="xpdExt:ScriptInformation">
						<xsl:attribute name="Id"><xsl:value-of
								select="generate-id()" />
                        </xsl:attribute>
						<xsl:attribute name="Name"><xsl:value-of
								select="@Formal" />
                        </xsl:attribute>
					</xsl:element>
					<xsl:element name="xpdl2:Actual">
						<xsl:attribute name="ScriptGrammar">JavaScript</xsl:attribute>
						<xsl:value-of select="$script" />
						<xsl:if test="not(contains($script, ';'))">
							<xsl:value-of select="';'" />
						</xsl:if>
					</xsl:element>
				</xsl:when>
				<xsl:otherwise>
					<xsl:element name="xpdl2:Actual">
						<xsl:attribute name="ScriptGrammar">JavaScript</xsl:attribute>
						<xsl:value-of select="$script" />
					</xsl:element>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:element>
	</xsl:template>

	<!--
		===============================================================================
		Fix existing DataMapping nodes (Java)
		===============================================================================
	-->
	<xsl:template
		match="xpdl2:TaskService[@xpdExt:ImplementationType='Java']/xpdl2:MessageOut">
		<xsl:element name="xpdl2:MessageOut">
			<xsl:attribute name="Id"><xsl:value-of select="@Id" />
            </xsl:attribute>
			<xsl:for-each
				select="../../../../xpdl2:Assignments/xpdl2:Assignment">
				<xsl:variable name="target" select="xpdl2:Target" />
				<xsl:variable name="expression"
					select="xpdl2:Expression" />
				<xsl:variable name="assignment"
					select="substring-before($expression, ';')" />
				<xsl:variable name="script"
					select="substring-after($assignment, '=')" />
				<xsl:element name="xpdl2:DataMapping">
					<xsl:attribute name="Direction">OUT</xsl:attribute>
					<xsl:choose>
						<xsl:when
							test="contains($script, '.substring(') or contains($script, '&quot;')">
							<xsl:attribute name="Formal">_SCRIPT_</xsl:attribute>
							<xsl:element
								name="xpdExt:ScriptInformation">
								<xsl:attribute name="Id"><xsl:value-of
										select="generate-id()" />
                                </xsl:attribute>
								<xsl:attribute name="Name"><xsl:value-of
										select="$target" />
                                </xsl:attribute>
								<xsl:element name="xpdExt:Expression">
									<xsl:attribute
										name="ScriptGrammar">JavaScript</xsl:attribute>
									<xsl:value-of select="$script" />
									<xsl:if
										test="not(contains($script, ';'))">
										<xsl:value-of select="';'" />
									</xsl:if>
								</xsl:element>
							</xsl:element>
						</xsl:when>
						<xsl:when test="contains($script, '+')">
							<xsl:attribute name="Formal">_SCRIPT_</xsl:attribute>
							<xsl:element name="xpdExt:Expression">
								<xsl:attribute name="ScriptGrammar">JavaScript</xsl:attribute>
								<xsl:value-of select="$script" />
							</xsl:element>
						</xsl:when>
						<xsl:otherwise>
							<xsl:attribute name="Formal">
                            <xsl:value-of select="$script" />
                        </xsl:attribute>
						</xsl:otherwise>
					</xsl:choose>
					<xsl:element name="xpdl2:Actual">
						<xsl:attribute name="ScriptGrammar">JavaScript</xsl:attribute>
						<xsl:value-of select="$target" />
					</xsl:element>
				</xsl:element>
			</xsl:for-each>
		</xsl:element>
	</xsl:template>

	<!--
		===============================================================================
		Fix existing DataMapping nodes (SubFlow)
		===============================================================================
	-->
	<xsl:template
		match="xpdl2:SubFlow/xpdl2:DataMappings/xpdl2:DataMapping[@Direction='INOUT']">
		<xsl:element name="xpdl2:DataMapping">
			<xsl:attribute name="Direction">IN</xsl:attribute>
			<xsl:attribute name="Formal"><xsl:value-of select="@Formal" />
			</xsl:attribute>
			<xsl:element name="xpdl2:Actual">
				<xsl:if test="not(@ScriptGrammar)">
					<xsl:attribute name="ScriptGrammar">JavaScript</xsl:attribute>
				</xsl:if>
				<xsl:value-of select="xpdl2:Actual" />
			</xsl:element>
		</xsl:element>
		<xsl:element name="xpdl2:DataMapping">
			<xsl:attribute name="Direction">OUT</xsl:attribute>
			<xsl:attribute name="Formal"><xsl:value-of select="@Formal" />
			</xsl:attribute>
			<xsl:element name="xpdl2:Actual">
				<xsl:if test="not(@ScriptGrammar)">
					<xsl:attribute name="ScriptGrammar">JavaScript</xsl:attribute>
				</xsl:if>
				<xsl:value-of select="xpdl2:Actual" />
			</xsl:element>
		</xsl:element>
	</xsl:template>

	<!--
		===============================================================================
		Fix existing DataMapping nodes (SubFlow)
		===============================================================================
	-->
	<xsl:template
		match="xpdl2:SubFlow/xpdl2:DataMappings/xpdl2:DataMapping[@Direction='IN' or @Direction='OUT']/xpdl2:Actual">
		<xsl:element name="xpdl2:Actual">
			<xsl:choose>
				<xsl:when test="not(@ScriptGrammar)">
					<xsl:attribute name="ScriptGrammar">JavaScript</xsl:attribute>
				</xsl:when>
				<xsl:when test="@ScriptGrammar">
					<xsl:attribute name="ScriptGrammar"><xsl:value-of select="@ScriptGrammar"/></xsl:attribute>
				</xsl:when>
			</xsl:choose>
			<xsl:value-of select="text()" />
		</xsl:element>
	</xsl:template>

	<!--
		===============================================================================
		Fix existing DataMapping nodes (Events)
		===============================================================================
	-->
	<xsl:template match="xpdl2:TriggerResultMessage/xpdl2:Message">
		<xsl:element name="xpdl2:Message">
			<xsl:attribute name="Id"><xsl:value-of select="@Id" />
            </xsl:attribute>
			<xsl:element name="xpdl2:DataMappings">
				<xsl:apply-templates
					select="xpdl2:DataMappings/xpdl2:DataMapping" mode="events" />
				<xsl:for-each
					select="../../../../xpdl2:Assignments/xpdl2:Assignment">
					<xsl:variable name="target" select="xpdl2:Target" />
					<xsl:variable name="expression"
						select="xpdl2:Expression" />
					<xsl:variable name="paths"
						select="substring-before($expression, '&#10;var ')" />
					<xsl:variable name="script"
						select="substring-after(substring-after($expression, $paths), '&#10;')" />
					<xsl:call-template name="parseWebServiceOutput">
						<xsl:with-param name="paths" select="$paths" />
						<xsl:with-param name="target" select="$target" />
						<xsl:with-param name="script" select="$script" />
					</xsl:call-template>
				</xsl:for-each>
			</xsl:element>
		</xsl:element>
	</xsl:template>

	<!--
		===============================================================================
		Fix existing DataMapping nodes (Events)
		===============================================================================
	-->
	<xsl:template match="xpdl2:DataMapping" mode="events">
		<xsl:variable name="script" select="xpdl2:Actual" />
		<xsl:call-template name="parseWebServiceInput">
			<xsl:with-param name="script" select="$script" />
		</xsl:call-template>
	</xsl:template>

	<!--
		===============================================================================
		Add xpdExt:Mandatory attribute to FormalParameter and the iProcess sequencing info if required.
		===============================================================================
	-->
	<xsl:template match="xpdl2:FormalParameters">
		<xpdl2:FormalParameters>
			<xsl:for-each select="xpdl2:FormalParameter">
				<xsl:element name="xpdl2:FormalParameter">
					<xsl:attribute name="xpdExt:Mandatory">false</xsl:attribute>
		
					<xsl:variable name="isIPM"><xsl:call-template name="isIProcess"><xsl:with-param name="process" select="ancestor::xpdl2:WorkflowProcess"/></xsl:call-template></xsl:variable>
					<xsl:if test="$isIPM = 'true'">
						<xsl:attribute name="iProcessExt:SequenceNum"><xsl:value-of select="position()"/></xsl:attribute>
					</xsl:if>
		
					<xsl:apply-templates select="@* | node()" />
				</xsl:element>
			</xsl:for-each>
		</xpdl2:FormalParameters>
	</xsl:template>
	

	<!--
		===============================================================================
		Parse the web service input mapping script, create new DataMappings
		===============================================================================
	-->
	<xsl:template name="parseWebServiceInput">
		<xsl:param name="script" />
		<xsl:variable name="before"
			select="substring-before($script, '&#10;')" />
		<xsl:variable name="after"
			select="substring-after($script, '&#10;')" />
		<xsl:variable name="formal"
			select="substring-after($before, '//')" />
		<xsl:variable name="assignment"
			select="substring-before($before, ';')" />
		<xsl:variable name="actual"
			select="substring-after($assignment, '= ')" />
		<xsl:if test="string-length($formal) > 0">
			<xsl:element name="xpdl2:DataMapping">
				<xsl:attribute name="Direction">IN</xsl:attribute>
				<xsl:attribute name="Formal"><xsl:value-of
						select="$formal" />
						</xsl:attribute>
				<xsl:if
					test="contains($actual, '.substring(') or contains($actual, '&quot;')">
					<xsl:variable name="name"
						select="substring-before($assignment, ' =')" />
					<xsl:element name="xpdExt:ScriptInformation">
						<xsl:attribute name="Id"><xsl:value-of
								select="generate-id()" />
						</xsl:attribute>
						<xsl:attribute name="Name"><xsl:value-of
								select="$name" />
						</xsl:attribute>
					</xsl:element>
				</xsl:if>
				<xsl:element name="xpdl2:Actual">
					<xsl:attribute name="ScriptGrammar">XPath</xsl:attribute>
					<xsl:call-template name="convertSubstring">
						<xsl:with-param name="script" select="$actual" />
					</xsl:call-template>
				</xsl:element>
			</xsl:element>
		</xsl:if>
		<xsl:if test="string-length($after) > 0">
			<xsl:call-template name="parseWebServiceInput">
				<xsl:with-param name="script" select="$after" />
			</xsl:call-template>
		</xsl:if>
	</xsl:template>

	<!--
		===============================================================================
		Parse the web service output mapping script, create new DataMappings
		===============================================================================
	-->
	<xsl:template name="parseWebServiceOutput">
		<xsl:param name="paths" />
		<xsl:param name="target" />
		<xsl:param name="script" />
		<xsl:variable name="before"
			select="substring-before($script, '&#10;')" />
		<xsl:variable name="after"
			select="substring-after($script, '&#10;')" />
		<xsl:variable name="assignment"
			select="substring-before($before, ';')" />
		<xsl:if test="not(starts-with($assignment, 'var '))">
			<xsl:variable name="formal"
				select="substring-after($assignment, '=')" />

			<xsl:if test="string-length($formal) > 0">
				<xsl:element name="xpdl2:DataMapping">
					<xsl:attribute name="Direction">OUT</xsl:attribute>
					<xsl:choose>
						<xsl:when
							test="contains($formal, '.substring(') or contains($formal, '&quot;')">
							<xsl:attribute name="Formal">_SCRIPT_</xsl:attribute>
							<xsl:element
								name="xpdExt:ScriptInformation">
								<xsl:attribute name="Id"><xsl:value-of
										select="generate-id()" />
                        </xsl:attribute>
								<xsl:attribute name="Name"><xsl:value-of
										select="$target" />
                        </xsl:attribute>
								<xsl:element name="xpdExt:Expression">
									<xsl:attribute
										name="ScriptGrammar">XPath</xsl:attribute>
									<xsl:call-template
										name="convertSubstring">
										<xsl:with-param name="script"
											select="$formal" />
									</xsl:call-template>
								</xsl:element>
							</xsl:element>
						</xsl:when>
						<xsl:when test="contains($formal, '+')">
							<xsl:attribute name="Formal">_SCRIPT_</xsl:attribute>
							<xsl:element name="xpdExt:Expression">
								<xsl:attribute name="ScriptGrammar">XPath</xsl:attribute>
								<xsl:call-template name="writePath">
									<xsl:with-param name="paths"
										select="$paths" />
									<xsl:with-param name="formal"
										select="$formal" />
								</xsl:call-template>
							</xsl:element>
						</xsl:when>
						<xsl:otherwise>
							<xsl:attribute name="Formal">
                    <xsl:call-template name="writePath">
                <xsl:with-param name="paths" select="$paths" />
                <xsl:with-param name="formal" select="$formal" />
                            </xsl:call-template>
                        </xsl:attribute>
						</xsl:otherwise>
					</xsl:choose>
					<xsl:element name="xpdl2:Actual">
						<xsl:attribute name="ScriptGrammar">XPath</xsl:attribute>
						<xsl:value-of select="$target" />
					</xsl:element>
				</xsl:element>
			</xsl:if>
		</xsl:if>
		<xsl:if test="string-length($after) > 0">
			<xsl:call-template name="parseWebServiceOutput">
				<xsl:with-param name="paths" select="$paths" />
				<xsl:with-param name="target" select="$target" />
				<xsl:with-param name="script" select="$after" />
			</xsl:call-template>
		</xsl:if>
	</xsl:template>

	<!--
		===============================================================================
		Write out the path corresponding to the formal parameter.
		===============================================================================
	-->
	<xsl:template name="convertSubstring">
		<xsl:param name="script" />
		<xsl:choose>
			<xsl:when test="contains($script, '.substring(')">
				<xsl:variable name="before"
					select="substring-before($script, '.substring(')" />
				<xsl:variable name="after"
					select="substring-after($script, '.substring(')" />
				<xsl:variable name="parameter">
					<xsl:call-template name="last-parameter">
						<xsl:with-param name="script" select="$before" />
					</xsl:call-template>
				</xsl:variable>
				<xsl:variable name="beforeParameter"
					select="substring-before($script, $parameter)" />
				<xsl:variable name="converted"
					select="concat($beforeParameter, 'substring(', $parameter, ',', $after)" />
				<xsl:call-template name="convertSubstring">
					<xsl:with-param name="script" select="$converted" />
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="translate($script, '.', '/')" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!--
		===============================================================================
		Write out the path corresponding to the formal parameter.
		===============================================================================
	-->
	<xsl:template name="last-parameter">
		<xsl:param name="script" />
		<xsl:choose>
			<xsl:when test="contains($script, ' ')">
				<xsl:variable name="before"
					select="substring-before($script, ' ')" />
				<xsl:call-template name="last-parameter">
					<xsl:with-param name="script" select="$before" />
				</xsl:call-template>
			</xsl:when>
			<xsl:when test="contains($script, '+')">
				<xsl:variable name="before"
					select="substring-before($script, '+')" />
				<xsl:call-template name="last-parameter">
					<xsl:with-param name="script" select="$before" />
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$script" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!--
		===============================================================================
		Write out the path corresponding to the formal parameter.
		===============================================================================
	-->
	<xsl:template name="writePath">
		<xsl:param name="paths" />
		<xsl:param name="formal" />
		<xsl:variable name="before"
			select="substring-before($paths, '&#10;')" />
		<xsl:variable name="after"
			select="substring-after($paths, '&#10;')" />
		<xsl:variable name="path">
			<xsl:choose>
				<xsl:when test="contains($paths, '&#10;')">
					<xsl:value-of
						select="substring-after($before, '//')" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of
						select="substring-after($paths, '//')" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:variable name="converted">
			<xsl:call-template name="wsdlPathToXPath">
				<xsl:with-param name="path" select="$paths" />
			</xsl:call-template>
		</xsl:variable>
		<xsl:variable name="replaced">
			<xsl:call-template name="replaceAll">
				<xsl:with-param name="script" select="$formal" />
				<xsl:with-param name="find" select="$converted" />
				<xsl:with-param name="replace" select="$path" />
			</xsl:call-template>
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="string-length($after) > 0">
				<xsl:call-template name="writePath">
					<xsl:with-param name="paths" select="$after" />
					<xsl:with-param name="formal" select="$replaced" />
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$replaced" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!--
		===============================================================================
		Replace all instances of $find with $replace
		===============================================================================
	-->
	<xsl:template name="replaceAll">
		<xsl:param name="script" />
		<xsl:param name="find" />
		<xsl:param name="replace" />
		<xsl:choose>
			<xsl:when test="contains($script, $find)">
				<xsl:variable name="before"
					select="substring-before($script, $find)" />
				<xsl:variable name="after"
					select="substring-after($script, $find)" />
				<xsl:variable name="prefix"
					select="concat($before, $replace)" />
				<xsl:value-of select="$before" />
				<xsl:value-of select="$replace" />
				<xsl:call-template name="replaceAll">
					<xsl:with-param name="script" select="$after" />
					<xsl:with-param name="find" select="$find" />
					<xsl:with-param name="replace" select="$replace" />
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$script" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!--
		===============================================================================
		Convert a WsdlPath to an XPath.
		===============================================================================
	-->
	<xsl:template name="wsdlPathToXPath">
		<xsl:param name="path" />
		<xsl:choose>
			<xsl:when test="contains($path, '/')">
				<xsl:variable name="before"
					select="substring-before($path, '/')" />
				<xsl:variable name="after"
					select="substring-after($path, '/')" />
				<xsl:variable name="nextsection"
					select="substring-before($after, '/')" />
				<xsl:variable name="section">
					<xsl:call-template name="sectionToXPath">
						<xsl:with-param name="section" select="$before" />
						<xsl:with-param name="nextsection"
							select="$nextsection" />
					</xsl:call-template>
				</xsl:variable>
				<xsl:if test="string-length($section) > 0">
					<xsl:value-of select="$section" />
					<xsl:value-of select="'.'" />
				</xsl:if>
				<xsl:call-template name="wsdlPathToXPath">
					<xsl:with-param name="path" select="$after" />
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:call-template name="sectionToXPath">
					<xsl:with-param name="section" select="$path" />
					<xsl:with-param name="nextsection" select="''" />
				</xsl:call-template>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!--
		===============================================================================
		Convert a WsdlPath to an XPath.
		===============================================================================
	-->
	<xsl:template name="sectionToXPath">
		<xsl:param name="section" />
		<xsl:param name="nextsection" />
		<xsl:variable name="stripped">
			<xsl:choose>
				<xsl:when test="contains($section, '[')">
					<xsl:value-of
						select="substring-before($section, '[')" />
					<xsl:value-of
						select="substring-after($section, ']')" />
					<xsl:if
						test="string-length($nextsection) != 0 and contains($nextsection, '{')">
						<xsl:if
							test="substring-before($section, '[') = substring-before($nextsection, '{')">
							<xsl:variable name="index">
								<xsl:value-of
									select="substring-before(substring-after($nextsection, '{'), '}')" />
							</xsl:variable>
							<xsl:value-of
								select="concat('[', $index, ']')" />
						</xsl:if>
					</xsl:if>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$section" />
				</xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="contains($stripped, '{')" />
			<xsl:when test="contains($stripped, ':')">
				<xsl:choose>
					<xsl:when test="starts-with($stripped, 'part:')">
						<xsl:value-of
							select="substring-after($stripped, 'part:')" />
					</xsl:when>
					<xsl:when test="starts-with($stripped, 'wso:')">
					</xsl:when>
					<xsl:when test="starts-with($stripped, 'group:')">
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of
							select="substring-before($stripped, ':')" />
						<xsl:value-of select="'::'" />
						<xsl:value-of
							select="substring-after($stripped, ':')" />
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$stripped" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!--
		===============================================================================
		Fixed old v2 studio broken database operation type "Stored Procedure" -> StoredProc
		===============================================================================
	-->
	<xsl:template match="database:Operation[starts-with(@database:Type, 'Stor') or count(@database:Type) = 0]">
		
		<database:Operation database:Type="StoredProc">
			<xsl:apply-templates select="./*"/>
		</database:Operation>
				
	</xsl:template>
	 
	<!--
		===============================================================================
		Remove all Assignment nodes
		===============================================================================
	-->
	<xsl:template match="xpdl2:Assignment"></xsl:template>

	<!--
		===============================================================================
		Convert multiple comman separated v2 studio date to v3 format ( ~ separated )
		===============================================================================
	-->
	<xsl:template name="convertMultiDates">
		<xsl:param name="dates"/>
		<xsl:param name="first" select="'true'"/>

		<!-- extract next date from start of string. -->
		<xsl:variable name="nextDate">
			<xsl:choose>
				<xsl:when test="contains($dates, ',')"><xsl:value-of select="substring-before($dates, ',')"/></xsl:when>
				<xsl:otherwise><xsl:value-of select="$dates"/></xsl:otherwise>
			</xsl:choose>
		</xsl:variable>
		
		<xsl:if test="$nextDate != ''">
			<xsl:if test="$first != 'true'">
				<xsl:text>~</xsl:text>
			</xsl:if>
			
			<xsl:call-template name="convertDate"><xsl:with-param name="date" select="$nextDate"/></xsl:call-template>
			
			<!-- if there are any more dates to output then recurs -->
			<xsl:if test="contains($dates, ',')">
				<xsl:call-template name="convertMultiDates">
					<xsl:with-param name="dates" select="substring-after($dates, ',')"/>
					<xsl:with-param name="first" select="'false'"/>
				</xsl:call-template>
			</xsl:if>
			
		</xsl:if>
	</xsl:template>


	<!--
		===============================================================================
		Convert v2 studio date to v3 format.
		===============================================================================
	-->
	<xsl:template name="convertDate">
		<xsl:param name="date"/>

		<xsl:choose>
			<xsl:when test="$date != '' and function-available('java:com.tibco.xpd.xpdl2.edit.util.XsltUtils.v2DateTov3Date')">
				<xsl:value-of select="java:com.tibco.xpd.xpdl2.edit.util.XsltUtils.v2DateTov3Date($date)"/>
			</xsl:when>
			<xsl:otherwise><xsl:value-of select="$date"/></xsl:otherwise>
		</xsl:choose>
		
	</xsl:template>


	<!--
		===============================================================================
		Default template to copy all attributes nodes and text of an element
		===============================================================================
	-->
	<xsl:template
		match="@xpdExt:ImplementationType[text()='Web Service']">
		<!-- Copy input attribute to output. -->
		<xsl:attribute name="xpdExt:ImplementationType">
			<xsl:value-of select="WebService" />
		</xsl:attribute>
	</xsl:template>

	<!--
        ===============================================================================
		Return string "true" if the process/interface has an iprocess destination environment set.
        ===============================================================================
    -->
	<xsl:template name="isIProcess">
		<xsl:param name="process"/>
		
		<xsl:choose>
			<xsl:when test="count($process/xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'Destination' and (starts-with(@Value, 'ipm') or starts-with(@Value, 'ipe'))]) > 0">
				<xsl:text>true</xsl:text>
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>false</xsl:text>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>


	<!--
		===============================================================================
		Template to migrate from cim activity to webservice activity
		===============================================================================
	-->
	<xsl:template match="xpdl2:Activity[xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService/@xpdExt:ImplementationType = 'CIM Service']">
		<xsl:element name="xpdl2:Activity">
			<xsl:attribute name="Id"><xsl:value-of select="@Id"/></xsl:attribute>
			<xsl:attribute name="Name"><xsl:value-of select="@Name"/></xsl:attribute>
			<xsl:attribute name="xpdExt:DisplayName"><xsl:value-of select="@Name"/></xsl:attribute>
			<xsl:element name="xpdl2:Description">
				<xsl:value-of select="xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService/cim:CimService/cim:CimActivityDescription/text()"/>
			</xsl:element>
			<xsl:element name="xpdl2:Implementation">
				<xsl:element name="xpdl2:Task">
					<xsl:element name="xpdl2:TaskService">
						<xsl:attribute name="xpdExt:ImplementationType">WebService</xsl:attribute>
						<xsl:attribute name="Implementation">WebService</xsl:attribute>		
						<xsl:variable name="activityName" select="@Name"/>	
						<xsl:variable name="activityAction" select="xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService/cim:CimService/cim:CimActionType"/>
						<!-- migrating activity names -->						
						<xsl:variable name="action">
							<xsl:choose>
								<xsl:when test="$activityAction='CheckRecordState'">CheckRecordInWorkflow</xsl:when>
								<xsl:when test="$activityAction='Exec'">ExecuteExternalCommand</xsl:when>
								<xsl:when test="$activityAction='HandleProtocolMessaging'">HandleMessaging</xsl:when>
								<xsl:when test="$activityAction='Reclassify'">ReclassifyRecord</xsl:when>
								<xsl:otherwise><xsl:value-of select="$activityAction"/></xsl:otherwise>
							</xsl:choose>										
						</xsl:variable>
						
						<xsl:variable name="executionMode" select="xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService/cim:CimService/cim:CimExecutionMode"/>
						<xsl:variable name="formalInputPrefix" select="concat('wso:null/part:',$action,'Input/')"/>	
						<xsl:variable name="dataFields" select="ancestor::xpdl2:WorkflowProcess/xpdl2:DataFields/xpdl2:DataField"/>
						<xsl:element name="xpdl2:MessageIn">			
							<xsl:attribute name="Id"><xsl:value-of select="concat('_',$activityName,'In')"/></xsl:attribute>
							<!-- Map execution mode of the activity -->
							<xsl:element name="xpdl2:DataMappings">	
								<xsl:element name="xpdl2:DataMapping">					
									<xsl:attribute name="Direction">IN</xsl:attribute>
									<xsl:attribute name="Formal"><xsl:value-of select="concat($formalInputPrefix,'@ExecutionMode/')"/></xsl:attribute>
									<xsl:element name="xpdExt:ScriptInformation">						
										<xsl:attribute name="Id"><xsl:value-of select="concat('_',$activityName,'ExecutionMode')"/></xsl:attribute>										
										<xsl:attribute name="Name">ExecutionMode</xsl:attribute>																														
									</xsl:element>
									<xsl:element name="xpdl2:Actual">					
										<xsl:attribute name="ScriptGrammar">XPath</xsl:attribute>
										<xsl:choose>
											<xsl:when test="count(xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService/cim:CimService/cim:CimExecutionMode) > 0">
												<xsl:choose>
													<xsl:when test="xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService/cim:CimService/cim:CimExecutionMode/text()='Synchronous'">
														<xsl:text>'SYNCHR'</xsl:text>
													</xsl:when>
													<xsl:otherwise>
														<xsl:text>'ASYNCHR'</xsl:text>
													</xsl:otherwise>
												</xsl:choose>																								
											</xsl:when>
											<xsl:otherwise>
												<xsl:text>'SYNCHR'</xsl:text>
											</xsl:otherwise>
										</xsl:choose>										
									</xsl:element>	
								</xsl:element>					
							
								<xsl:for-each select="xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService/cim:CimService/cim:CimParameter[@cim:direction='IN']">
									<xsl:variable name="name" select="@cim:name"></xsl:variable>
									<!-- @name -->															
									<!-- for xpath no need to write value mapping,value is part of the xpath expression -->
									<xsl:if test="count(preceding-sibling::cim:CimParameter[@cim:name = $name]) = 0">
										<xsl:if test="not(@cim:eval='xpath' and starts-with(@cim:source,'/Message'))">
										<xsl:element name="xpdl2:DataMapping">
											<xsl:attribute name="Direction">IN</xsl:attribute>																																
											<xsl:attribute name="Formal"><xsl:value-of select="concat($formalInputPrefix,'group:sequence[1]/',@cim:name)"/></xsl:attribute>																																									
											<xsl:choose>
												<xsl:when test="@cim:eval='variable'">									
													<!-- do nothing as this parameter is going to be mapped to a data field for variable-->
												</xsl:when>												
												<xsl:otherwise>
													<!-- create a script to define value -->
													<xsl:element name="xpdExt:ScriptInformation">
															<xsl:attribute name="Id"><xsl:value-of select="concat('_',$activityName,@cim:name,'_',@cim:type)"/></xsl:attribute>
														<xsl:attribute name="Name"><xsl:value-of select="concat(@cim:name,'_Value')"/></xsl:attribute>
													</xsl:element>
												</xsl:otherwise>
											</xsl:choose>											
											<xsl:element name="xpdl2:Actual">
												<xsl:attribute name="ScriptGrammar">XPath</xsl:attribute>
												<xsl:choose>
													<xsl:when test="@cim:eval ='variable'">
														<xsl:value-of select="."/>
													</xsl:when>
														<xsl:when test="(@cim:eval='rule' and @cim:type='long') or (@cim:eval='catalog' and @cim:type='long') or (@cim:eval='lookup' and @cim:type='long') or (@cim:eval='compute' and @cim:type='long')">
														<xsl:text>number('</xsl:text><xsl:value-of select="."/><xsl:text>')</xsl:text>																	
													</xsl:when>
													<xsl:otherwise>
														<xsl:text>'</xsl:text><xsl:value-of select="."/><xsl:text>'</xsl:text>
													</xsl:otherwise>
												</xsl:choose>
											</xsl:element>
										</xsl:element>
									</xsl:if>
									<!-- @eval -->
									<xsl:choose>							
										<xsl:when test="@cim:eval='variable' or @cim:eval ='constant' or (@cim:eval ='xpath' and starts-with(@cim:source,'/Message'))">
											<!-- do nothing -->
										</xsl:when>
										<xsl:otherwise>
											<xsl:element name="xpdl2:DataMapping">
												<xsl:attribute name="Direction">IN</xsl:attribute>
												<xsl:attribute name="Formal"><xsl:value-of select="concat($formalInputPrefix,'group:sequence[1]/',@cim:name,'/@eval')"/></xsl:attribute>	
												<xsl:element name="xpdExt:ScriptInformation">
													<xsl:attribute name="Id"><xsl:value-of select="concat('_',$activityName,@cim:name,'Eval_',@cim:type)"/></xsl:attribute>
													<xsl:attribute name="Name"><xsl:value-of select="concat(@cim:name,'_Eval')"/></xsl:attribute>
												</xsl:element>								
												<xsl:element name="xpdl2:Actual">
													<xsl:attribute name="ScriptGrammar">XPath</xsl:attribute>
													<xsl:text>'</xsl:text><xsl:value-of select="@cim:eval"/><xsl:text>'</xsl:text>
												</xsl:element>																																																																						
											</xsl:element>
										</xsl:otherwise>
									</xsl:choose>
	
									<xsl:variable name="source" select="@cim:source"/>
									<!-- writing source attribute-->
									<xsl:choose>
										<xsl:when test="$source!='' and @cim:eval='xpath' and starts-with(@cim:source,'/Message')">
											<xsl:element name="xpdl2:DataMapping">
												<xsl:attribute name="Direction">IN</xsl:attribute>
												<xsl:attribute name="Formal"><xsl:value-of select="concat($formalInputPrefix,'group:sequence[1]/',@cim:name,'/@source')"/></xsl:attribute>	
												<xsl:element name="xpdExt:ScriptInformation">
														<xsl:attribute name="Id"><xsl:value-of select="concat('_',$activityName,@cim:name,'Source_',@cim:type)"/></xsl:attribute>
													<xsl:attribute name="Name"><xsl:value-of select="concat(@cim:name,'_Source')"/></xsl:attribute>
												</xsl:element>								
												<xsl:element name="xpdl2:Actual">
													<xsl:attribute name="ScriptGrammar">XPath</xsl:attribute>										
													<xsl:variable name="value"><xsl:value-of select="."/></xsl:variable>
													<xsl:variable name="sourceSubString"><xsl:value-of select="substring-after(@cim:source,'/Message')"/></xsl:variable>
													<xsl:value-of select="concat('$',$value,$sourceSubString)"/>
												</xsl:element>																																																
											</xsl:element>
										</xsl:when>
											<xsl:when test="$source!='' and not(@cim:eval='xpath' and starts-with(@cim:source,'/Message'))">
											<xsl:element name="xpdl2:DataMapping">
												<xsl:attribute name="Direction">IN</xsl:attribute>
												<xsl:attribute name="Formal"><xsl:value-of select="concat($formalInputPrefix,'group:sequence[1]/',@cim:name,'/@source')"/></xsl:attribute>	
												<xsl:element name="xpdExt:ScriptInformation">
														<xsl:attribute name="Id"><xsl:value-of select="concat('_',$activityName,@cim:name,'Source_',@cim:type)"/></xsl:attribute>
													<xsl:attribute name="Name"><xsl:value-of select="concat(@cim:name,'_Source')"/></xsl:attribute>
												</xsl:element>	
												<xsl:element name="xpdl2:Actual">
													<xsl:attribute name="ScriptGrammar">XPath</xsl:attribute>
													<xsl:choose>
															<xsl:when test="@cim:eval='lookup' or @cim:eval='xpath'">
																<xsl:text>"</xsl:text><xsl:value-of select="@cim:source"/><xsl:text>"</xsl:text>
														</xsl:when>
														<xsl:otherwise>
															<xsl:text>'</xsl:text><xsl:value-of select="@cim:source"/><xsl:text>'</xsl:text>
														</xsl:otherwise>
													</xsl:choose>																																																			
												</xsl:element>																																																										
											</xsl:element>
										</xsl:when>							
									</xsl:choose>									
									</xsl:if>
								</xsl:for-each>					
							</xsl:element>
						</xsl:element>
						
						<!-- Map out CIM parameters -->
						<xsl:element name="xpdl2:MessageOut">			
							<xsl:attribute name="Id"><xsl:value-of select="concat('_',$activityName,'Out')"/></xsl:attribute>
							<xsl:element name="xpdl2:DataMappings">			
								<xsl:for-each select="xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService/cim:CimService/cim:CimParameter[@cim:direction='OUT']">
									<xsl:element name="xpdl2:DataMapping">
										<xsl:attribute name="Direction">OUT</xsl:attribute>
										<xsl:attribute name="Formal"><xsl:value-of select="concat('wso:null/part:parameters/group:sequence/',@cim:name)"/></xsl:attribute>	
										<xsl:element name="xpdl2:Actual">
											<xsl:attribute name="ScriptGrammar">XPath</xsl:attribute>
											<xsl:value-of select="."/>
										</xsl:element>
									</xsl:element>
								</xsl:for-each>
							</xsl:element>	
						</xsl:element>	
						<xsl:element name="xpdl2:WebServiceOperation">
							<xsl:attribute name="xpdExt:SecurityProfile"></xsl:attribute>
							<xsl:attribute name="xpdExt:Alias"></xsl:attribute>
							<xsl:attribute name="OperationName"><xsl:value-of select="$action"/></xsl:attribute>			
							<xpdl2:Service xpdExt:IsRemote="false" PortName="" ServiceName="">
								<xpdl2:EndPoint EndPointType="WSDL">
									<xpdl2:ExternalReference location="CIMServices.wsdl"/>
								</xpdl2:EndPoint>
							</xpdl2:Service>			
						</xsl:element>
						<xpdExt:PortTypeOperation PortTypeName="CimActivities">
							<xsl:attribute name="OperationName"><xsl:value-of select="$action"/></xsl:attribute>
							<xpdExt:ExternalReference location="CIMServices.wsdl"/>
						</xpdExt:PortTypeOperation>					
					</xsl:element>
				</xsl:element>				
			</xsl:element>	
			<xsl:if	test="xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService/cim:CimService/cim:CimScript">
				<xpdExt:Audit>
					<xpdExt:AuditEvent Type="Initiated">
						<xpdExt:Information ScriptGrammar="Text">
							<xsl:text>&lt;![CDATA[</xsl:text><xsl:value-of select="xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService/cim:CimService/cim:CimScript/text()"/><xsl:text>]]&gt;</xsl:text>	
						</xpdExt:Information>
					</xpdExt:AuditEvent>
				</xpdExt:Audit>
			</xsl:if>
			<xsl:element name="xpdl2:NodeGraphicsInfos">
				<xsl:apply-templates select="xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo" />
			</xsl:element>
			
		</xsl:element>		
	</xsl:template>
	
	<!--
		===============================================================================
		Template to create data fields of cim activity local parameters.
		===============================================================================
	-->	
	<xsl:template match="xpdl2:WorkflowProcess/xpdl2:DataFields[ancestor::xpdl2:WorkflowProcess/xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'Destination' and (starts-with(@Value, 'cim'))]]">
		<xsl:element name="xpdl2:DataFields">
			<xsl:variable name="cimOutParameterNode"
				select="ancestor::xpdl2:WorkflowProcess/xpdl2:Activities/xpdl2:Activity/xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService/cim:CimService/cim:CimParameter[not(.=following-sibling::cim:CimParameter)]"/>
			<xsl:variable name="dataFields" select="ancestor::xpdl2:WorkflowProcess/xpdl2:DataFields/xpdl2:DataField"/>	
			<xsl:for-each select="$cimOutParameterNode[not(.=following::text())]">	
				<xsl:variable name="parameterValue" select="."/>	
				<xsl:choose>
					<xsl:when test="count($dataFields[@Name = $parameterValue])= 0 and @cim:direction='OUT'">
						<xsl:element name="xpdl2:DataField">
							<xsl:attribute name="Id"><xsl:value-of select="."/></xsl:attribute>
							<xsl:attribute name="xpdExt:DisplayName"><xsl:value-of select="."/></xsl:attribute>
							<xsl:attribute name="Name"><xsl:value-of select="."/></xsl:attribute>
							<xsl:attribute name="IsArray">false</xsl:attribute>												
								<xsl:choose>
									<xsl:when test="@cim:type='document'">
										<xsl:element name="xpdl2:DataType">
											<xsl:element name="xpdl2:DeclaredType">
												<xsl:attribute name="Id"><xsl:text>Out_Document</xsl:text></xsl:attribute>
											</xsl:element>	
										</xsl:element>
									</xsl:when>
									<xsl:when test="@cim:type='string'">
										<xsl:element name="xpdl2:DataType">
											<xsl:element name="xpdl2:DeclaredType">
												<xsl:attribute name="Id"><xsl:text>Out_String</xsl:text></xsl:attribute>
											</xsl:element>	
										</xsl:element>
									</xsl:when>
									<xsl:when test="@cim:type='recordlist'">
										<xsl:element name="xpdl2:DataType">
											<xsl:element name="xpdl2:DeclaredType">
												<xsl:attribute name="Id"><xsl:text>Out_RecordList</xsl:text></xsl:attribute>
											</xsl:element>	
										</xsl:element>
									</xsl:when>
									<xsl:when test="@cim:type='long'">
										<xsl:element name="xpdl2:DataType">
											<xsl:element name="xpdl2:DeclaredType">
												<xsl:attribute name="Id"><xsl:text>Out_Long</xsl:text></xsl:attribute>
											</xsl:element>	
										</xsl:element>
									</xsl:when>
									<xsl:when test="@cim:type='arraylist'">
										<xsl:element name="xpdl2:DataType">
											<xsl:element name="xpdl2:DeclaredType">
												<xsl:attribute name="Id"><xsl:text>Out_ArrayList</xsl:text></xsl:attribute>
											</xsl:element>	
										</xsl:element>
									</xsl:when>
									<xsl:when test="@cim:type='boolean'">
										<xsl:element name="xpdl2:DataType">
											<xsl:element name="xpdl2:DeclaredType">
												<xsl:attribute name="Id"><xsl:text>Out_Boolean</xsl:text></xsl:attribute>
											</xsl:element>		
										</xsl:element>
									</xsl:when>
									<xsl:otherwise/>															
								</xsl:choose>
							</xsl:element>																																				
					</xsl:when>
					<xsl:when test="count($dataFields[@Name = $parameterValue])= 0 and @cim:direction='IN' and (@cim:eval='variable' or @cim:eval='xpath' or @cim:eval='rule'or @cim:eval='catalog' or @cim:eval='lookup')">
						<xsl:element name="xpdl2:DataField">
							<xsl:attribute name="Id"><xsl:value-of select="."/></xsl:attribute>
							<xsl:attribute name="xpdExt:DisplayName"><xsl:value-of select="."/></xsl:attribute>
							<xsl:attribute name="Name"><xsl:value-of select="."/></xsl:attribute>
							<xsl:attribute name="IsArray">false</xsl:attribute>		
							<xsl:choose>
								<!-- if eval is xpath or rule or catalog or lookup this means parameter value is definitly of document type -->
								<xsl:when test="@cim:eval='xpath' or @cim:eval='rule' or @cim:eval='catalog' or @cim:eval='lookup'">	
									<xsl:element name="xpdl2:DataType">
										<xsl:element name="xpdl2:DeclaredType">
											<xsl:attribute name="Id"><xsl:text>In_Document</xsl:text></xsl:attribute>
										</xsl:element>	
									</xsl:element>
								</xsl:when>
								<xsl:otherwise>
									<xsl:choose>
										<xsl:when test="@cim:type='document'">
											<xsl:element name="xpdl2:DataType">
												<xsl:element name="xpdl2:DeclaredType">
													<xsl:attribute name="Id"><xsl:text>In_Document</xsl:text></xsl:attribute>
												</xsl:element>	
											</xsl:element>										
										</xsl:when>
										<xsl:when test="@cim:type='string'">
											<xsl:element name="xpdl2:DataType">
												<xsl:element name="xpdl2:DeclaredType">
													<xsl:attribute name="Id"><xsl:text>In_String</xsl:text></xsl:attribute>
												</xsl:element>	
											</xsl:element>												
										</xsl:when>
										<xsl:when test="@cim:type='recordlist'">
											<xsl:element name="xpdl2:DataType">
												<xsl:element name="xpdl2:DeclaredType">
													<xsl:attribute name="Id"><xsl:text>In_RecordList</xsl:text></xsl:attribute>
												</xsl:element>	
											</xsl:element>	
										</xsl:when>
										<xsl:when test="@cim:type='long'">
											<xsl:element name="xpdl2:DataType">
												<xsl:element name="xpdl2:DeclaredType">
													<xsl:attribute name="Id"><xsl:text>In_Long</xsl:text></xsl:attribute>
												</xsl:element>	
											</xsl:element>	
										</xsl:when>
										<xsl:when test="@cim:type='arraylist'">
											<xsl:element name="xpdl2:DataType">
												<xsl:element name="xpdl2:DeclaredType">
													<xsl:attribute name="Id"><xsl:text>In_ArrayList</xsl:text></xsl:attribute>
												</xsl:element>	
											</xsl:element>	
										</xsl:when>
										<xsl:when test="@cim:type='boolean'">
											<xsl:element name="xpdl2:DataType">
												<xsl:element name="xpdl2:DeclaredType">
													<xsl:attribute name="Id"><xsl:text>In_Boolean</xsl:text></xsl:attribute>
												</xsl:element>	
											</xsl:element>	
										</xsl:when>
										<xsl:otherwise/>															
									</xsl:choose>
								</xsl:otherwise>
							</xsl:choose>																		
							</xsl:element>																																			
					</xsl:when>
				</xsl:choose>				
			</xsl:for-each>		
			<xsl:for-each select="$dataFields">
				<xsl:if test="xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute/@Name = 'ActivityName'">
					<xsl:element name="xpdl2:DataField">
						<xsl:attribute name="Id"><xsl:value-of select="@Id"/></xsl:attribute>
						<xsl:attribute name="xpdExt:DisplayName"><xsl:value-of select="@Name"/></xsl:attribute>
						<xsl:attribute name="Name"><xsl:value-of select="@Name"/></xsl:attribute>
						<xsl:attribute name="IsArray">false</xsl:attribute>						
						<xsl:element name="xpdl2:DataType">
							<xsl:element name="xpdl2:DeclaredType">
								<xsl:attribute name="Id"><xsl:value-of select="xpdl2:DataType/xpdl2:DeclaredType/@Id"/></xsl:attribute>
							</xsl:element>							
						</xsl:element>								
						<xsl:copy-of select="xpdl2:InitialValue"/>								
						<xsl:if test="count(xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute) > 0">
							<xsl:element name="xpdl2:ExtendedAttributes">
								<xsl:for-each select="xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute">							
									<xsl:element name="xpdl2:ExtendedAttribute">
										<xsl:attribute name="Name"><xsl:value-of select="@Name"/></xsl:attribute>
										<xsl:attribute name="Value"><xsl:value-of select="@Value"/></xsl:attribute>
									</xsl:element>									
								</xsl:for-each>
							</xsl:element>	
						</xsl:if>
					</xsl:element>
				</xsl:if>
			</xsl:for-each>
		</xsl:element>
	</xsl:template>
	<!--
		===============================================================================
		Template to check whether process has CIM destination.
		===============================================================================
	-->			
	<xsl:template name="isCIMProcess">
		<xsl:param name="process"/>		
		<xsl:choose>
			<xsl:when test="count($process/xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'Destination' and (starts-with(@Value, 'cim'))]) > 0">
				<xsl:text>true</xsl:text>
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>false</xsl:text>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<!--
		===============================================================================
		Template to migrate type declarations of CIM template.
		===============================================================================
	-->
	<xsl:template match="xpdl2:TypeDeclaration[count(/xpdl2:Package/xpdl2:WorkflowProcesses[1]/xpdl2:WorkflowProcess[1]/xpdl2:ExtendedAttributes[1]/xpdl2:ExtendedAttribute[@Name = 'Destination'
		and (starts-with(@Value, 'cim'))]) > 0]">	
		<xsl:element name="xpdl2:TypeDeclaration">
			<xsl:attribute name="Id"><xsl:value-of select="@Id"/></xsl:attribute>
			<xsl:attribute name="Name"><xsl:value-of select="translate(@Id,'_','')"/></xsl:attribute>
			<xsl:attribute name="xpdExt:DisplayName"><xsl:value-of select="translate(@Id,'_',' ')"/></xsl:attribute>
			<xsl:choose>
				<xsl:when test="@Id='In_Document' or @Id='Out_Document'">
					<xsl:element name="xpdl2:ExternalReference">
						<xsl:attribute name="location">mlXMLCore.bom</xsl:attribute>
						<xsl:attribute name="namespace">http://www.eclipse.org/uml2/2.1.0/UML</xsl:attribute>
						<xsl:attribute name="xref">_BTos0O48Ed2CwI5y0_cmJA</xsl:attribute>
					</xsl:element>
					<xsl:element name="xpdl2:ExtendedAttributes">
						<xsl:for-each select="xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute">
						<xsl:element name="xpdl2:ExtendedAttribute">
							<xsl:attribute name="Name"><xsl:value-of select="@Name"/></xsl:attribute>
							<xsl:attribute name="Value"><xsl:value-of select="@Value"/></xsl:attribute>								
						</xsl:element>
						</xsl:for-each>
					</xsl:element>					
				</xsl:when>
				<xsl:otherwise>					
					<xsl:element name="xpdl2:BasicType">
						<xsl:choose>
							<xsl:when test="@Id ='In_ArrayList' or @Id ='Out_ArrayList' or @Id ='In_RecordList' or @Id ='Out_RecordList'">
								<xsl:attribute name="Type">STRING</xsl:attribute>
							</xsl:when>
							<xsl:otherwise>
								<xsl:attribute name="Type"><xsl:value-of select="xpdl2:BasicType/@Type"/></xsl:attribute>	
							</xsl:otherwise>
						</xsl:choose>	
					</xsl:element>
					<xsl:element name="xpdl2:ExtendedAttributes">
						<xsl:for-each select="xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute">
							<xsl:element name="xpdl2:ExtendedAttribute">
								<xsl:attribute name="Name"><xsl:value-of select="@Name"/></xsl:attribute>
								<xsl:attribute name="Value"><xsl:value-of select="@Value"/></xsl:attribute>								
							</xsl:element>
						</xsl:for-each>
					</xsl:element>					
				</xsl:otherwise>
			</xsl:choose>			
		</xsl:element>			
	</xsl:template>	
	
	
	<!-- 
		===============================================================================
		SID CIM - 
		For CIM process package, move all process data fields that do not have ActivityName ExtendedAttribute up to Package level - 
		whilst processing the Package DataFields (the template for WorkflowProcess/xpdl2:DataFields[ for CIM process ] will ignore the 
		DataFields that are moved up here.
		===============================================================================
	-->
	<xsl:template name="outputPackageDataFields">
		<xpdl2:DataFields>
			<!-- Do all the normal stuff for existing package level data fields -->
			<xsl:apply-templates select="/xpdl2:Package/xpdl2:DataFields/xpdl2:DataField"/>

			<!-- If this is a CIM package then copy all the process data fields that DO NOT have an ActivityName extended attribute  -->
			<xsl:variable name="isCIMProcessPackage">
				<!-- it's a CIM process package if the first process is a CIM process. -->
				<xsl:call-template name="isCIMProcess">
					<xsl:with-param name="process" select="/xpdl2:Package/xpdl2:WorkflowProcesses[1]/xpdl2:WorkflowProcess[1]"></xsl:with-param>
				</xsl:call-template>
			</xsl:variable>
			
			<xsl:if test="$isCIMProcessPackage='true'">
				<!-- Move the CIM non-ActivityName ExtendedAttribute fields up to package by simply processing them here instead
						of during processing of workflowprocess datafields. -->
				<xsl:for-each
					select="/xpdl2:Package/xpdl2:WorkflowProcesses/xpdl2:WorkflowProcess/xpdl2:DataFields/xpdl2:DataField">
					<xsl:if test="not(xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute/@Name = 'ActivityName')">
						<!-- SID CIM - don't output DataFields element for every field any more. -->
						<xsl:element name="xpdl2:DataField">
							<xsl:attribute name="Id"><xsl:value-of select="@Id"/></xsl:attribute>
							<xsl:attribute name="xpdExt:DisplayName"><xsl:value-of select="@Name"/></xsl:attribute>
							<xsl:attribute name="Name"><xsl:value-of select="@Name"/></xsl:attribute>
							<xsl:attribute name="IsArray">false</xsl:attribute>																								
							<xsl:element name="xpdl2:DataType">
								<xsl:element name="xpdl2:DeclaredType">
									<xsl:attribute name="Id"><xsl:value-of select="xpdl2:DataType/xpdl2:DeclaredType/@Id"/></xsl:attribute>
								</xsl:element>
							</xsl:element>																																								
							<xsl:copy-of select="xpdl2:InitialValue"/>								
						</xsl:element>
					</xsl:if>
				</xsl:for-each>																								
			</xsl:if>	

		</xpdl2:DataFields>
	</xsl:template>

<!-- End of CIM template -->

	<!--
		===============================================================================
		Default template to copy all attributes nodes and text of an element
		===============================================================================
	-->
	<!--
===============================================================================
	General template to copy all nodes
===============================================================================
-->

	<xsl:template match="node()" priority="-9">
		<xsl:param name="actsWithoutStepNum"/>
		<xsl:param name="highestStepNum"/>

		<xsl:if test="name() != ''">
			<xsl:element name="{name()}">
				<xsl:apply-templates select="@* | node() | text()">
					<xsl:with-param name="actsWithoutStepNum" select="$actsWithoutStepNum"/>
					<xsl:with-param name="highestStepNum" select="$highestStepNum"/>
				</xsl:apply-templates>
			</xsl:element>
		</xsl:if>
	</xsl:template>
		
	<xsl:template match="@* | text()" priority="-9">
		<!-- Copy input attribute to output. -->
		<xsl:copy>
			<xsl:apply-templates select="@* | text()"/>
		</xsl:copy>
	</xsl:template>

</xsl:stylesheet>
