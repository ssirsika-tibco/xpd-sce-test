<?xml version="1.0" encoding="UTF-8"?>
<!--
	===================================================================================================================
	XSLT:       MigrateStudioXPDL_6.xslt
	
	DESCRIPTION:

  	    This xslt will updgrade a xpdl of format version 6 to format version 7 (See XpdlMigrate.xpdl for format version <-> Studio version equivalence.

	===================================================================================================================
-->

<xsl:stylesheet version="1.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xpdExt="http://www.tibco.com/XPD/xpdExtension1.0.0"
	xmlns:eaijava="http://www.tibco.com/XPD/EAIJava1.0.0" 
	xmlns:simulation="http://www.tibco.com/xpd/Simulation1.0.1"
	xmlns:database="http://www.tibco.com/XPD/database1.0.0"
	xmlns:email="http://www.tibco.com/XPD/email1.0.0"
	xmlns:iProcessExt="http://www.tibco.com/XPD/iProcessExt1.0.0"	
	xmlns:java="http://xml.apache.org/xslt/java"

	xmlns:xpdl2="http://www.wfmc.org/2008/XPDL2.1"
	
	exclude-result-prefixes="java"
	>


	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" />

	<!--
		===============================================================================
		XSLT processing start point (process whole document)
		===============================================================================
	-->
	<xsl:template match="/">
		<xsl:apply-templates />
	</xsl:template>

	<xsl:template match="xpdl2:ExtendedAttribute[@Name = 'FormatVersion']">
		<xsl:element name="xpdl2:ExtendedAttribute">
			<xsl:attribute name="Name">FormatVersion</xsl:attribute>
			<xsl:attribute name="Value">7</xsl:attribute>
		</xsl:element>
	</xsl:template>

	<!--
	===============================================================================
	Convert old offer/allocate flag from boolean into new value.	   
    ===============================================================================
    -->
	<xsl:template match="xpdExt:AllocationStrategy/@xpdExt:Offer">
		<xsl:attribute name="xpdExt:Offer">
			<xsl:choose>
				<xsl:when test=". = 'false'">AllocateOne</xsl:when>
				<xsl:otherwise>OfferAll</xsl:otherwise>
			</xsl:choose>
		</xsl:attribute>
	
	</xsl:template>

    <!--
    ===============================================================================
    Convert old database server/schema to new advanced properties.       
    ===============================================================================
    -->
    <xsl:template match="xpdl2:Activity[not(iProcessExt:TaskProperties)]">
        <xpdl2:Activity>
            <xsl:apply-templates select="@* | node() | text()"/>
	        <xsl:if test="xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService/database:Database/database:Server or xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService/database:Database/database:Schema">
		        <iProcessExt:TaskProperties>
		            <iProcessExt:DatabaseTask>
		                <xsl:if test="xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService/database:Database/database:Server">
		                    <xsl:attribute name="Server"><xsl:value-of select="xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService/database:Database/database:Server"/></xsl:attribute>
		                </xsl:if>
	                    <xsl:if test="xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService/database:Database/database:Schema">
	                        <xsl:attribute name="Schema"><xsl:value-of select="xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService/database:Database/database:Schema"/></xsl:attribute>
	                    </xsl:if>
		            </iProcessExt:DatabaseTask>
		        </iProcessExt:TaskProperties>
	        </xsl:if>
        </xpdl2:Activity>
    </xsl:template>
    
    <xsl:template match="xpdl2:Activity/iProcessExt:TaskProperties[not(iProcessExt:DatabaseTask)]">
		<xsl:choose>
			<xsl:when test="../xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService/database:Database/database:Server or ../xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService/database:Database/database:Schema">
				<iProcessExt:TaskProperties>
					<xsl:apply-templates select="@* | node() | text()"/>
					<iProcessExt:DatabaseTask>
						<xsl:if test="../xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService/database:Database/database:Server">
							<xsl:attribute name="Server"><xsl:value-of select="../xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService/database:Database/database:Server"/></xsl:attribute>
						</xsl:if>
						<xsl:if test="../xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService/database:Database/database:Schema">
							<xsl:attribute name="Schema"><xsl:value-of select="../xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService/database:Database/database:Schema"/></xsl:attribute>
						</xsl:if>
					</iProcessExt:DatabaseTask>
				</iProcessExt:TaskProperties>
			</xsl:when>
			<xsl:otherwise>
				<!-- Copy TaskProperties for tasks that aren't database service tasks. -->
				<xsl:copy-of select="."/>
			</xsl:otherwise>
		</xsl:choose>
    </xsl:template>
    
    <xsl:template match="xpdl2:Activity/iProcessExt:TaskProperties/iProcessExt:DatabaseTask">
        <xsl:if test="../../xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService/database:Database/database:Server or ../../xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService/database:Database/database:Schema">
             <iProcessExt:DatabaseTask>
                 <xsl:apply-templates select="@* | node() | text()"/>
                 <xsl:if test="../../xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService/database:Database/database:Server">
                     <xsl:attribute name="Server"><xsl:value-of select="../../xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService/database:Database/database:Server"/></xsl:attribute>
                 </xsl:if>
                 <xsl:if test="../../xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService/database:Database/database:Schema">
                     <xsl:attribute name="Schema"><xsl:value-of select="../../xpdl2:Implementation/xpdl2:Task/xpdl2:TaskService/database:Database/database:Schema"/></xsl:attribute>
                 </xsl:if>
             </iProcessExt:DatabaseTask>
        </xsl:if>
    </xsl:template>
    
    <xsl:template match="database:Server">
    </xsl:template>
    
    <xsl:template match="database:Schema">
    </xsl:template>

	<!--
    ===============================================================================
    User Task Activity now has extension element for deprecated bpmJspTask extended Attribute - add a xpdExt:FormImplementation element.
    ===============================================================================
    -->
    <xsl:template match="xpdl2:TaskUser">
		<xpdl2:TaskUser>
			<xsl:apply-templates select="@* | node()"/>
			
			<xsl:variable name="bpmJspTask" select="ancestor::xpdl2:Activity/xpdl2:ExtendedAttributes/xpdl2:ExtendedAttribute[@Name = 'bpmJspTask']"/>
			<xsl:if test="$bpmJspTask">
				<xpdExt:FormImplementation>
					<xsl:attribute name="FormType">
						<xsl:choose>
							<xsl:when test="starts-with($bpmJspTask, 'form://')">
								<xsl:text>Form</xsl:text>
							</xsl:when>
							<xsl:otherwise>
								<xsl:text>UserDefined</xsl:text>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:attribute>
					
					<xsl:attribute name="FormURI"><xsl:value-of select="$bpmJspTask/@Value"/></xsl:attribute>
				</xpdExt:FormImplementation>
			
			</xsl:if>
		</xpdl2:TaskUser>
    </xsl:template>

    <!--
    ===============================================================================
	Add EXPANDED / COLLAPSED STATE to embedded sub-processes.
	(in 3.1 all collapsed embedded sub-processes should be exactly 192x128 - there's a slight possibility that an expanded one happens 
		to be exactly that in which case that will also be set to collapsed, but we have nothing else to go on).
    ===============================================================================
    -->
	<xsl:template match="xpdl2:BlockActivity">
		<xsl:copy>
			<xsl:if test="count(@ViewType) = 0">
				<xsl:variable name="ngi" select="../xpdl2:NodeGraphicsInfos/xpdl2:NodeGraphicsInfo[count(@ToolId) = 0]"/>
				
				<xsl:choose>
					<xsl:when test="$ngi/@Width = 192 and $ngi/@Height = 128">
						<xsl:attribute name="View">COLLAPSED</xsl:attribute>
					</xsl:when>
					<xsl:otherwise>
						<xsl:attribute name="View">EXPANDED</xsl:attribute>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
			
			<xsl:apply-templates select="@* | node()"/>
			
		</xsl:copy>
	
	</xsl:template>
    

    <!--
    ===============================================================================
	In v3.1 Implementing event does not have copy of name om implementd method, in 3.2 it does so if we can find name, copy it.
    ===============================================================================
    -->
	<xsl:template match="xpdl2:Activity/@xpdExt:Implements">
		<xsl:copy/>
			
		<xsl:variable name="methodId" select="."/>
		
		<xsl:variable name="method" select="/xpdl2:Package/xpdExt:ProcessInterfaces/xpdExt:ProcessInterface/xpdExt:StartMethods/xpdExt:StartMethod[@Id = $methodId] | 
				/xpdl2:Package/xpdExt:ProcessInterfaces/xpdExt:ProcessInterface/xpdExt:IntermediateMethods/xpdExt:IntermediateMethod[@Id = $methodId]"/>
		
		<xsl:if test="$method">
			<xsl:attribute name="Name"><xsl:value-of select="$method/@Name"/></xsl:attribute>
			<xsl:attribute name="xpdExt:DisplayName"><xsl:value-of select="$method/@xpdExt:DisplayName"/></xsl:attribute>
		</xsl:if>
		
		
	</xsl:template>

    <!--
    ===============================================================================
    Set v3.1 Implementing message event IsRemote = true.
    ===============================================================================
    -->
	<xsl:template match="xpdl2:TriggerResultMessage/xpdl2:WebServiceOperation/xpdl2:Service">
		<xsl:choose>
			<xsl:when test="(count(ancestor::xpdl2:StartEvent) > 0 or count(ancestor::xpdl2:IntermediateEvent) > 0) and (count(ancestor::xpdl2:Activity/@xpdExt:Implements) > 0)">
				<xpdl2:Service xpdExt:IsRemote="true">
					<xsl:comment>Touched</xsl:comment>
					<xsl:apply-templates select="@*"/>
					<xsl:apply-templates select="node()"/>
				</xpdl2:Service>
			</xsl:when>
			<xsl:otherwise>
				<xpdl2:Service>
					<xsl:comment>UnTouched</xsl:comment>
					<xsl:apply-templates select="@* | node()"/>
				</xpdl2:Service>
			</xsl:otherwise>
		</xsl:choose>
	
	</xsl:template>

    <!--
    ===============================================================================
    Convert Response End message events to use new flag and specify Start event id.       
    ===============================================================================
    -->
    <xsl:template match="xpdl2:EndEvent/xpdl2:TriggerResultMessage">
	    <xpdl2:TriggerResultMessage>
	        <xsl:choose>
	            <xsl:when test="../../../@xpdExt:Implements">
	                <xsl:variable name="startMethodId"><xsl:value-of select="../../../@xpdExt:Implements"/></xsl:variable>
                    <xsl:variable name="startCount"><xsl:value-of select="count(//xpdl2:Activity[@xpdExt:Implements = $startMethodId and xpdl2:Event/xpdl2:StartEvent])"/></xsl:variable>
                    <xsl:choose>
                        <xsl:when test="$startCount = 1">
                            <xsl:variable name="replyToActivityId"><xsl:value-of select="//xpdl2:Activity[@xpdExt:Implements = $startMethodId and xpdl2:Event/xpdl2:StartEvent]/@Id"/></xsl:variable>
                            <xsl:if test="string-length($replyToActivityId) > 0">
                                <xsl:attribute name="xpdExt:ReplyToActivityId"><xsl:value-of select="$replyToActivityId"/></xsl:attribute>
                            </xsl:if>
                        </xsl:when>
                        <xsl:when test="$startCount > 1">
                            <xsl:attribute name="xpdExt:ReplyToActivityId"/>
                        </xsl:when>
                    </xsl:choose>
	            </xsl:when>
	            <xsl:when test="xpdExt:PortTypeOperation">
	                <xsl:variable name="operationName"><xsl:value-of select="xpdExt:PortTypeOperation/@OperationName"/></xsl:variable>
                    <xsl:variable name="startCount"><xsl:value-of select="count(//xpdl2:Activity[xpdl2:Event/xpdl2:StartEvent/xpdl2:TriggerResultMessage/xpdExt:PortTypeOperation/@OperationName = $operationName])"/></xsl:variable>
	                <xsl:choose>
		                <xsl:when test="$startCount = 1">
			                <xsl:variable name="replyToActivityId"><xsl:value-of select="//xpdl2:Activity[xpdl2:Event/xpdl2:StartEvent/xpdl2:TriggerResultMessage/xpdExt:PortTypeOperation/@OperationName = $operationName]/@Id"/></xsl:variable>
			                <xsl:if test="string-length($replyToActivityId) > 0">
			                    <xsl:attribute name="xpdExt:ReplyToActivityId"><xsl:value-of select="$replyToActivityId"/></xsl:attribute>
			                </xsl:if>
		                </xsl:when>
                        <xsl:when test="$startCount > 1">
                            <xsl:attribute name="xpdExt:ReplyToActivityId"/>
                        </xsl:when>
	                </xsl:choose>
	            </xsl:when>
	            <xsl:otherwise>
	                <xsl:attribute name="xpdExt:ReplyToActivityId"/>
	            </xsl:otherwise>
            </xsl:choose>
	        <xsl:apply-templates select="@* | node() | text()"/>
        </xpdl2:TriggerResultMessage>
    </xsl:template>

	<!--
        ===============================================================================
       Default copy all template.
        ===============================================================================
    -->
	<xsl:template match="node()" priority="-9">
		<xsl:if test="name() != ''">
			<xsl:copy>
				<xsl:apply-templates select="@* | node() | text()"/>
			</xsl:copy>
		</xsl:if>
	</xsl:template>
		
	<xsl:template match="@* | text()" priority="-9">
		<!-- Copy input attribute to output. -->
		<xsl:copy>
			<xsl:apply-templates select="@* | text()"/>
		</xsl:copy>
	</xsl:template>


</xsl:stylesheet>
