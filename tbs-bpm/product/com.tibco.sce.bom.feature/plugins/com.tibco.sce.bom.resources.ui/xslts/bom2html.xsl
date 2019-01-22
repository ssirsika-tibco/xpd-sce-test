<?xml version="1.0" encoding="UTF-8"?>

<!-- ======================================================================= -->
<!--                                                                         -->	
<!--           XSLT: bom2html.xsl                                            -->
<!--                                                                         -->	
<!--           DESCRIPTION:                                                  -->
<!-- 	            Creates Documentation in HTML format from the selected   -->
<!--                business object model (.bom file).                       -->
<!--                                                                         -->	
<!--           TODO:                                                         -->
<!-- 	            use fully qualified names for packages and concepts      -->
<!--                                                                         -->	
<!-- ======================================================================= -->


<!-- ======================================================================= -->
<!--                                                                         -->	
<!-- ENTITY DEFINITIONS                                                      -->
<!--                                                                         -->	
<!-- ======================================================================= -->

  <!DOCTYPE xsl:stylesheet [
  <!ENTITY nbsp '<xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>'>
  <!ENTITY imageDir 'images/'>
  <!ENTITY qm '?'>
  <!ENTITY imageExtension '.jpg'>
  ]>

  <xsl:stylesheet
    exclude-result-prefixes="#default xsl fo java uml notation" 
    version="1.0" 
    xmlns="http://www.w3.org/1999/xhtml" 
    xmlns:fo="http://www.w3.org/1999/XSL/Format" 
    xmlns:html="http://www.w3.org/1999/xhtml" 
    xmlns:uml="http://www.eclipse.org/uml2/5.0.0/UML" 
    xmlns:xmi="http://www.omg.org/XMI" 
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:notation="http://www.eclipse.org/gmf/runtime/1.0.2/notation"
    xmlns:java="http://xml.apache.org/xslt/java"
  >

  <xsl:param name="messagePropertiesId"/>


  <xsl:output encoding="UTF-8" indent="yes" method="xml" version="1.0"/>

<!-- The following assignment of param to temporary variable is required to ensure that evaluation of creation of class is not done UNTIL xslt is run (rather than on compile which will fail) --> 
<xsl:variable name="msgPropsId"><xsl:value-of select="$messagePropertiesId"/></xsl:variable> 
<xsl:variable name="msgProps" select="java:com.tibco.xpd.ui.importexport.utils.ImportExportMessageProperties.new($msgPropsId)"/> 
<xsl:variable name="bomMsgs" select="java:com.tibco.xpd.bom.resources.ui.internal.BomMsgs.new()" />

  <!-- Should ids be displayed? (Useful in debugging perhaps, not for normal usage) -->
	
  <xsl:variable name="showIds">false</xsl:variable> 

   <!-- Get Model xmi:id -->
	
  <xsl:variable name="modelID">
		<xsl:value-of select="//uml:Model/@xmi:id"/>
  </xsl:variable>

  <!-- Get Model name - if no name set then set the name to ID -->
  
  <xsl:variable name="modelName">
    <xsl:choose>
      <xsl:when test="//uml:Model/@name">
        <xsl:value-of select="//uml:Model/@name"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:value-of select="$modelID"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:variable>

  <!-- Get Model ID -->
  
  <xsl:variable name="diagramID">
    <xsl:value-of select="//notation:Diagram/@xmi:id"/>
  </xsl:variable>

  <xsl:variable name="questionMark" select="java:getMessage($bomMsgs, 'QuestionMark')"/>

  <!-- ===================================================================== -->
  <!--                                                                       -->  
  <!-- XSLT PROCESSING START POINT (PROCESS WHOLE DOCUMENT).                 -->
  <!--                                                                       -->  
  <!-- ===================================================================== -->
  
  <xsl:template match="/">
    <xsl:apply-templates/>
  </xsl:template>
  
  <xsl:template match="uml:Model">
    <!-- Output the DOCTYPE required or strict xhtml documents. -->
    
    <xsl:text disable-output-escaping="yes">&lt;</xsl:text><![CDATA[!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"]]>
    <xsl:text disable-output-escaping="yes">&gt;</xsl:text>

    <!-- ========== HTML - TAG ============================================= -->
    
    <xsl:element name="html" namespace="http://www.w3.org/1999/xhtml">
    
      <!-- ========== HTML - HEADER ======================================== -->

      <xsl:variable name="HTML_Header_title" select="java:getMessage($bomMsgs, 'HTML_Header_title')"/>
        
      <xsl:element name="head" namespace="http://www.w3.org/1999/xhtml">
        <meta content="text/xml; charset=UTF-8" http-equiv="Content-Type"/>
        <title><xsl:value-of select="$HTML_Header_title"/> - <xsl:value-of select="@name"/>
        </title>
        <link href="style/bomdoc.css" rel="stylesheet" type="text/css"/>
      </xsl:element>
      
      <!-- ========== HTML - BODY ========================================== -->
        
      <xsl:element name="body" namespace="http://www.w3.org/1999/xhtml">

      <!-- ========== HEADLINE ============================================= -->
        
        <xsl:variable name="imageAlt" select="java:getMessage($bomMsgs, 'ImageLogo_alt_label')"/>
        <xsl:variable name="main_title" select="java:getMessage($bomMsgs, 'Main_title')"/>
        
        <table class="mainTitle">
          <tbody>
            <tr>
              <td class="noBorder"><xsl:value-of select="$main_title"/>      </td>
              <td align="center" class="noBorder">
                <xsl:value-of select="$modelName"/>
              </td>
              <td align="right" class="noBorder">
                <img alt="" src="images/Logo.png"/>
              </td>
            </tr>
          </tbody>
        </table>
        
        <br/>
        
        <!-- ========== DESCRIPTION ======================================== -->
        
        <!--table>
          <tbody>
            <tr>
               <th class="separator" />
            </tr>
            <xsl:if test="$showIds = 'true'">
              <tr>
                <th class="heading"><xsl:value-of select="java:getMessage($bomMsgs, 'ID_label')"/></th>
                <td>
                  <xsl:value-of select="$modelID"/>
                </td>
              </tr>
            </xsl:if>
            <tr>
              <th class="heading"> <xsl:value-of select="java:getMessage($bomMsgs, 'Description_label')"/></th>
            </tr>
          </tbody>
        </table-->

        <!-- ========== MODEL META-DATA ==================================== -->
        
        <table>         
            <tr>
               <th class="separator" />
            </tr>
            <tr>
				<th>
				    <xsl:value-of select="java:getMessage($bomMsgs, 'Notes_label')"/>
				</th>
				<td>
				    <xsl:value-of select="ownedComment/@body"/>
				</td>
			</tr>
        </table>
        
        <div class="image">
          
          <!-- Use diagram ID to identify the image file below -->
          
          <xsl:element name="img">
            <xsl:attribute name="src">
              <xsl:text>&imageDir;</xsl:text>
              <xsl:value-of select="$diagramID"/>
              <xsl:text>&imageExtension;</xsl:text>
            </xsl:attribute>
            <xsl:attribute name="alt">
              <xsl:variable name="business_object_model_text" select="java:getMessage($bomMsgs, 'business_object_model_text')"/>
              <xsl:text><xsl:value-of select="$business_object_model_text"/></xsl:text>
              <xsl:value-of select="@name"/>
            </xsl:attribute>
            <xsl:attribute name="usemap">
            	<xsl:text>#</xsl:text>
            </xsl:attribute>          
          </xsl:element>
        </div>

        <!-- ========== SUMMARY ============================================ -->      
        
        <!-- table>
          <tbody>
            <tr>
               <th class="separator" />
            </tr>
            <tr>
              <th class="heading"><xsl:value-of select="java:getMessage($bomMsgs, 'Header_Summary_title_label')"/></th>
            </tr>
            <tr>
               <th class="separator" />
            </tr>
          </tbody>
        </table-->
        
        <!-- Packages summary -->
        
        <xsl:call-template name="packagedElementSummary">
          <xsl:with-param name="packagedElementContainer" select="."/>
          <xsl:with-param name="xmiType">uml:Package</xsl:with-param>
        </xsl:call-template>

        <!-- Concepts summary -->
        
        <xsl:call-template name="packagedElementSummary">
          <xsl:with-param name="packagedElementContainer" select="."/>
          <xsl:with-param name="xmiType">uml:Class</xsl:with-param>
        </xsl:call-template>
        
        <!-- Primitive Type summary -->
        
        <xsl:call-template name="packagedElementSummary">
          <xsl:with-param name="packagedElementContainer" select="."/>
          <xsl:with-param name="xmiType">uml:PrimitiveType</xsl:with-param>
        </xsl:call-template>
        
        <!-- Enumeration summary -->
        
         <xsl:call-template name="packagedElementSummary">
          <xsl:with-param name="packagedElementContainer" select="."/>
          <xsl:with-param name="xmiType">uml:Enumeration</xsl:with-param>
        </xsl:call-template>

        <!-- Association summary -->
        
        <xsl:call-template name="packagedElementSummary">
          <xsl:with-param name="packagedElementContainer" select="."/>
          <xsl:variable name="uml_association_label" select="java:getMessage($bomMsgs, 'uml_association_label')"/>
          <xsl:with-param name="xmiType">uml:Association</xsl:with-param>
        </xsl:call-template>
        
        <!-- ========== MODEL DIAGRAM (IMAGE) ============================== -->
        
        <!-- table>
          <tbody>
            <tr>
               <th class="separator" />
            </tr>
            <tr>
              <th class="heading"><xsl:value-of select="java:getMessage($bomMsgs, 'Header_title_label')"/></th>
            </tr>
          </tbody>
        </table-->
        
        
        
        <!-- ========== DETAILS ============================================ -->        
        
        <table>
          <tbody>
            <tr>
               <th class="separator" />
            </tr>
            <tr>
              <th class="heading"> <xsl:value-of select="java:getMessage($bomMsgs, 'Header_Details_title_label')"/></th>
            </tr>
          </tbody>
        </table>
        
        <!-- Packages -->
        
        <xsl:apply-templates select="packagedElement[@xmi:type='uml:Package']"/>
        
        <!-- Concepts -->
        
        <xsl:apply-templates select="packagedElement[@xmi:type='uml:Class']"/>
        
        <!-- Primitive Types -->
        
        <xsl:apply-templates select="packagedElement[@xmi:type='uml:PrimitiveType']"/>
        
        <!-- Enumeration -->
        
        <xsl:apply-templates select="packagedElement[@xmi:type='uml:Enumeration']"/>
        
        <!-- Associations -->

          <xsl:apply-templates select="packagedElement[@xmi:type='uml:Association']"/>
        
      </xsl:element>
    </xsl:element>
  </xsl:template>
  
  
  <!-- ===================================================================== -->
  <!--                                                                       -->  
  <!--           T E M P L A T E S                                           -->
  <!--                                                                       -->
  <!-- ===================================================================== -->
  
  <!-- ===================================================================== -->
  <!--                                                                       -->
  <!--           Summary Information for packaged elements                   -->
  <!--                                                                       -->
  <!-- ===================================================================== -->
  
  <xsl:template name="packagedElementSummary">
    <xsl:param name="packagedElementContainer"/>
    <xsl:param name="xmiType"/>
    
    <xsl:if test="$packagedElementContainer/packagedElement[@xmi:type=$xmiType] != '0'">

      <table>
          <thead>
          <tr>
            <th class="sectionTitle" colspan="6">
              <xsl:choose>
                <xsl:when test="$xmiType='uml:Package'">
                  <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'Header_Packages_title_label')"/> </xsl:text>
                </xsl:when>
                <xsl:when test="$xmiType='uml:Class'">
                  <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'Header_Concepts_title_label')"/> </xsl:text>
                </xsl:when>
                <xsl:when test="$xmiType='uml:PrimitiveType'">
                  <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'Header_PrimitiveTypes_title_label')"/> </xsl:text>
                </xsl:when>
                <xsl:when test="$xmiType='uml:Enumeration'">
                  <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'Header_Enumeration_title_label')"/> </xsl:text>
                </xsl:when>                
                <xsl:when test="$xmiType='uml:Association'">
                  <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'Header_Associations_title_label')"/> </xsl:text>
                </xsl:when>
                
                <xsl:otherwise>
                  <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'UnknownElement_label')"/> </xsl:text>
                </xsl:otherwise>
              </xsl:choose>
              
              <xsl:text> (</xsl:text>
              <xsl:value-of
                  select="count ($packagedElementContainer/packagedElement[@xmi:type=$xmiType])"/>)
            </th>
          </tr>
          
          <tr>
            <th class="columnIndent"></th>
            <th class="summaryColumnName"><xsl:value-of select="java:getMessage($bomMsgs, 'Name_label')"/></th>
            <th class="summaryColumnDescription"><xsl:value-of select="java:getMessage($bomMsgs, 'Description_label')"/></th>
          </tr>
          </thead>
        
        <tbody>
          <xsl:for-each
               select="$packagedElementContainer/packagedElement[@xmi:type=$xmiType]">
               
            <xsl:element name="a">
              <xsl:attribute name="href">#packagedElement_<xsl:value-of select="@xmi:id"/></xsl:attribute>
            </xsl:element>               
            <tr>
              <td class="indent"></td>
              <td>
                <xsl:element name="a">
                  <xsl:attribute name="href">#packagedElement_<xsl:value-of select="@xmi:id"/>
                  </xsl:attribute>
                  <xsl:call-template name="fullQualifiedName"/>
                </xsl:element>
              </td>
              <td>
                 <xsl:value-of select="ownedComment/@body"/>
              </td>
            </tr>
          </xsl:for-each>
        </tbody>
        
      </table>
    </xsl:if>
  </xsl:template>
  
  <!-- ===================================================================== -->
  <!--                                                                       -->
  <!--          Details of Packages, Concepts, PrimitiveTypes,               -->
  <!--          Enumerations and Associations                                -->
  <!--                                                                       -->
  <!-- ===================================================================== -->
    
  <xsl:template match="packagedElement">
  
    <xsl:element name="a">
      <xsl:attribute name="name">packagedElement_<xsl:value-of select="@xmi:id"/>
      </xsl:attribute>
    </xsl:element>
  
    <div class="section">
      <table>
        <tbody>
          <tr>
            <th class="noMargin">
              <xsl:element name="a">
                <xsl:attribute name="name">packagedElement_<xsl:value-of select="@xmi:id"/>
                </xsl:attribute>
              </xsl:element>
            </th>    
            <td class="detailTitleLeft">
            <xsl:text>&nbsp; </xsl:text>
            <xsl:choose>
              <xsl:when test="@xmi:type='uml:Package'">
                <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'Package_label')"/> &nbsp; &nbsp; </xsl:text>
              </xsl:when>
              <xsl:when test="@xmi:type='uml:Class'">
                <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'Concept_label')"/> &nbsp; &nbsp; </xsl:text>
              </xsl:when>
              <xsl:when test="@xmi:type='uml:PrimitiveType'">
                <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'PrimitiveType_label')"/> &nbsp; &nbsp; </xsl:text>
              </xsl:when>
              <xsl:when test="@xmi:type='uml:Enumeration'">
                <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'Enumeration_label')"/> &nbsp; &nbsp; </xsl:text>
              </xsl:when>
              <xsl:when test="@xmi:type='uml:Association'">
                <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'Association_label')"/> &nbsp; &nbsp; </xsl:text>
              </xsl:when>
              <xsl:otherwise>
                <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'UnknownElement_label')"/> </xsl:text>
              </xsl:otherwise>
            </xsl:choose>
            </td>
                
               <!-- ========== display the full qualified name ============ -->
                
               <td class="detailTitleRight">
                 <xsl:text>&nbsp; </xsl:text>
                 <xsl:call-template name="fullQualifiedName"/>
                 <xsl:if test="(@xmi:type='uml:Class' or @xmi:type='uml:PrimitiveType' or @xmi:type='uml:Enumeration') and generalization">                   
                    <xsl:for-each select="generalization">                               
                     <xsl:variable name="generalizedXmiId" select="./@general"/>
                       <xsl:variable name="substringAfter" select="substring-after($generalizedXmiId, '&qm;')" />
                       <xsl:variable name="substringBefore" select="substring-before($substringAfter, '&qm;')" />
                       <xsl:variable name="hRef" select="./general/@href"/>  
                       <xsl:choose>
                      <xsl:when test="position()= 1 and $substringBefore">                     
                    <xsl:text> (<xsl:value-of select="java:getMessage($bomMsgs,'SpecializationOf_label')"/></xsl:text>
                    </xsl:when> 
                  <xsl:when test="$substringBefore!=''">                    
                      <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'Komma')"/></xsl:text>                                      
                     </xsl:when>
                     </xsl:choose>                          
                       <xsl:choose>
                         <xsl:when test="$substringBefore">                                      
                              <xsl:call-template name="SubstringReplace">
                                <xsl:with-param name="stringIn" select="$substringBefore"/>
                                <xsl:with-param name="substringIn" select="'%3a%3a'"/>
                                <xsl:with-param name="substringOut" select="'::'"/>
                              </xsl:call-template>
                           <xsl:text>)</xsl:text>
                         </xsl:when>
                           <xsl:otherwise>                        
                           <xsl:value-of
                             select="substring-before(//packagedElement[@xmi:id=$generalizedXmiId]/@name, '&qm;')"/>
                         </xsl:otherwise> 
                       </xsl:choose>
                     </xsl:for-each>
                 </xsl:if>
               </td>
          </tr>
        </tbody>
      </table>

      <!-- Notes -->
      
      <xsl:call-template name="note">
        <xsl:with-param name="noteContainer" select="."/>
      </xsl:call-template>
      
      <xsl:choose>
        <xsl:when test="@xmi:type='uml:Package'">
        
          <!-- Packages summary -->
          
          <xsl:call-template name="packagedElementSummary">
            <xsl:with-param name="packagedElementContainer" select="."/>
            <xsl:with-param name="xmiType">uml:Package</xsl:with-param>
          </xsl:call-template>

          <!-- Concepts summary -->
          
          <xsl:call-template name="packagedElementSummary">
            <xsl:with-param name="packagedElementContainer" select="."/>
            <xsl:with-param name="xmiType">uml:Class</xsl:with-param>
          </xsl:call-template>
          
          <!-- PrimitiveType summary -->
          
          <xsl:call-template name="packagedElementSummary">
            <xsl:with-param name="packagedElementContainer" select="."/>
            <xsl:with-param name="xmiType">uml:PrimitiveType</xsl:with-param>
          </xsl:call-template>
          
          <!-- Enumeration summary -->
          
          <xsl:call-template name="packagedElementSummary">
            <xsl:with-param name="packagedElementContainer" select="."/>
            <xsl:with-param name="xmiType">uml:Enumeration</xsl:with-param>
          </xsl:call-template>
          
          <!-- Packages -->
          
          <xsl:apply-templates select="packagedElement[@xmi:type='uml:Package']"/>
          
          <!-- Concepts -->
          
          <xsl:apply-templates select="packagedElement[@xmi:type='uml:Class']"/>
          
          <!-- Primitive Types -->
          
          <xsl:apply-templates select="packagedElement[@xmi:type='uml:PrimitiveType']"/>
          
          <!-- Enumeration -->
          
          <xsl:apply-templates select="packagedElement[@xmi:type='uml:Enumeration']"/>

          <!-- Association -->

          <xsl:apply-templates select="packagedElement[@xmi:type='uml:Association']"/>
      
        </xsl:when>


        <xsl:when test="@xmi:type='uml:Class' and ownedAttribute">
            <table>
            
              <!-- ========== ATTRIBUTE HEADER ============================= -->
            
              <div class="section">
                <thead>
                  <tr>
                    <th class="sectionTitle" colspan="6">
                      <xsl:element name="a">
                        <xsl:attribute name="id">ownedAttribute_<xsl:value-of select="@xmi:id"/>
                        </xsl:attribute>
                      </xsl:element><xsl:value-of select="java:getMessage($bomMsgs, 'AttributesLabel')"/> (<xsl:value-of select="count (ownedAttribute)"/>)              
                    </th>
                  </tr>
                  <tr>
                    <th class="headingId">
                    </th>
                    <xsl:if test="$showIds = 'true'">
                      <th class="columnWidth"><xsl:value-of select="java:getMessage($bomMsgs, 'ID_label')"/></th>
                    </xsl:if>
                    <th class="detailColumnName"><xsl:value-of select="java:getMessage($bomMsgs, 'Name_label')"/></th>
                    <th class="detailColumnType"><xsl:value-of select="java:getMessage($bomMsgs, 'Type_label')"/></th>
                    <th class="detailColumnMultiplicity"><xsl:value-of select="java:getMessage($bomMsgs, 'Multiplicity_label')"/></th>
                    <th class="detailColumnAssociationName"><xsl:value-of select="java:getMessage($bomMsgs, 'AttributeAssociation_name_label')"/></th>
                    <th class="detailColumnDescription"><xsl:value-of select="java:getMessage($bomMsgs, 'AttributeDescription_label')"/></th>
                  </tr>
                </thead>
              </div>
              
              <tbody class="bodyText">
                <xsl:for-each select="ownedAttribute">
                  <tr>
                  
                    <!-- ========== ATTRIBUTE NUMBER (ID FOR DEBUG) ======== -->
                    
                    <td class="position">
                      <xsl:element name="a">
                        <xsl:attribute name="id">ownedAttribute_<xsl:value-of select="@xmi:id"/>
                        </xsl:attribute>
                        <xsl:value-of select="position()"/>
                      </xsl:element>
                    </td>
                    <xsl:if test="$showIds = 'true'">
                      <td>
                        <xsl:value-of select="@xmi:id"/>
                      </td>
                    </xsl:if>
                    
                    <!-- ========== ATTRIBUTE NAME ========================= -->
                    
                    <td>
                      <xsl:call-template name="fullQualifiedName"/>
                    </td>
                    
                    <!-- ========== ATTRIBUTE TYPE =========================================================================== -->
                    
                    <xsl:choose>
                    
                      <xsl:when test="@type">
                      
                        <!-- ========== CONCEPT ============================================================== -->
                        
                        <xsl:variable name="type" select="@type"/>
                        <td>
                          <xsl:variable name="typeString" select="@type"/>
                          <xsl:variable name="tempType" select="substring-before(substring-after($typeString, '&qm;'), '&qm;')" />
                          <xsl:call-template name="SubstringReplace">
                            <xsl:with-param name="stringIn" select="$tempType"/>
                            <xsl:with-param name="substringIn" select="'%3a%3a'"/>
                            <xsl:with-param name="substringOut" select="'::'"/>
                          </xsl:call-template>
                        </td>

                        <!-- ========== MULTPLICITY ======================== -->
                        
                          <td>   
                          <!--Kapil Previous Code for multiplicity did not work for multiplicity = 0..1 and multiplicity = "", fixed that -->
                                                 
                          <xsl:choose>
  						  <xsl:when test="lowerValue/@value">
							 <xsl:value-of select="lowerValue/@value"/>   
							 <xsl:if test=" upperValue/@value and lowerValue/@value != upperValue/@value">
                                <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'DOT_DOT_label')"/></xsl:text>
                                <xsl:value-of select="upperValue/@value"/>
                                 </xsl:if>
						    </xsl:when>
    						<xsl:when test="upperValue/@value">
							<xsl:text>0</xsl:text>
 							<xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'DOT_DOT_label')"/></xsl:text>
                                <xsl:value-of select="upperValue/@value"/>
    							</xsl:when>
    							<xsl:otherwise>
    							<xsl:text>1</xsl:text>
    							</xsl:otherwise>
							</xsl:choose>
                        </td>
                        
                      

                        <!-- ========== ASSOCIATION ======================== -->
                        
                        <xsl:variable name="attributeXmiId" select="@xmi:id"/>
                        <xsl:variable name="association" select="//packagedElement[@xmi:type='uml:Association' and contains(@memberEnd, $attributeXmiId)]"/>
                        <td>
                          <xsl:element name="a">
                            <xsl:attribute name="href">#packagedElement_<xsl:value-of select="$association/@xmi:id"/>
                            </xsl:attribute>
                            <xsl:value-of select="$association/@name"/>
                          </xsl:element>
                        </td>
                        <td>
                            <xsl:value-of select="ownedComment/@body"/>
                        </td>
                      </xsl:when>
                      
                      <xsl:otherwise>
                      
                        <!-- ========== PRIMITIVE TYPE ======================================================= -->
                        
                        <xsl:variable name="hRef" select="./type/@href"/>
                            <td>
                               <xsl:variable name="temp" select="substring-before(substring-after($hRef, '&qm;'), '&qm;')" />
                               <xsl:call-template name="SubstringReplace">
                                 <xsl:with-param name="stringIn" select="$temp"/>
                                 <xsl:with-param name="substringIn" select="'%3a%3a'"/>
                                 <xsl:with-param name="substringOut" select="'::'"/>
                               </xsl:call-template>
                            </td>
                        
                          <!-- ========== MULTPLICITY ======================== -->
                        
                          <td>                                                 
                          <!--Kapil Previous Code for multiplicity did not work for multiplicity = 0..1 and multiplicity = "", fixed that -->
                          
                          <xsl:choose>
  						  <xsl:when test="lowerValue/@value">
							 <xsl:value-of select="lowerValue/@value"/>   
							 <xsl:if test=" upperValue/@value and lowerValue/@value != upperValue/@value">
                                <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'DOT_DOT_label')"/></xsl:text>
                                <xsl:value-of select="upperValue/@value"/>
                                 </xsl:if>
						    </xsl:when>
    						<xsl:when test="upperValue/@value">
							<xsl:text>0</xsl:text>
 							<xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'DOT_DOT_label')"/></xsl:text>
                                <xsl:value-of select="upperValue/@value"/>
    							</xsl:when>
    							<xsl:otherwise>
    							<xsl:text>1</xsl:text>
    							</xsl:otherwise>
							</xsl:choose>

                          </td>

                          <!-- ========== ASSOCIATION ====================== -->
                                                      
                          <td>
                          </td>
                        <td>
                            <xsl:value-of select="ownedComment/@body"/>
                        </td>
                      </xsl:otherwise>
                    </xsl:choose>
                  </tr>
                </xsl:for-each>
              </tbody>
            </table>
        </xsl:when>
        
        <!-- Displays the Enum Literal Table -->
        
         <xsl:when test="@xmi:type='uml:Enumeration' and ownedLiteral">
            <table>
            
              <!-- ========== Enum Literal HEADER ============================= -->
            
              <div class="section">
                <thead>
                  <tr>
                    <th class="sectionTitle" colspan="6">
                      <xsl:element name="a">
                        <xsl:attribute name="id">ownedLiteral_<xsl:value-of select="@xmi:id"/>
                        </xsl:attribute>
                      </xsl:element><xsl:value-of select="java:getMessage($bomMsgs, 'EnumLiteralLabel')"/> (<xsl:value-of select="count (ownedLiteral)"/>)              
                    </th>
                  </tr>
                  <tr>
                    <th class="headingId">
                    </th>
                    <xsl:if test="$showIds = 'true'">
                      <th class="columnWidth"><xsl:value-of select="java:getMessage($bomMsgs, 'ID_label')"/></th>
                    </xsl:if>
                    
                     <!-- ===============  Kapil: Enum Literal Table will have only Id, Name, Value and Description ================= -->
                     
                    <th class="enumLiteralColumnName"><xsl:value-of select="java:getMessage($bomMsgs, 'Name_label')"/></th>                    
                     <th class="enumLiteralColumnValue"><xsl:value-of select="java:getMessage($bomMsgs, 'EnumLiteralValue_label')"/></th>
                        <th class="enumLiteralColumnDescription"><xsl:value-of select="java:getMessage($bomMsgs, 'AttributeDescription_label')"/></th>
                  </tr>
                </thead>
              </div>
              <tbody class="bodyText">
                <xsl:for-each select="ownedLiteral">
                  <tr>
                  
                    <!-- ========== Enum Literal NUMBER (ID FOR DEBUG) ======== -->
                    
                    <td class="position">
                      <xsl:element name="a">
                        <xsl:attribute name="id">ownedLiteral_<xsl:value-of select="@xmi:id"/>
                        </xsl:attribute>
                        <xsl:value-of select="position()"/>
                      </xsl:element>
                    </td>
                    <xsl:if test="$showIds = 'true'">
                      <td>
                        <xsl:value-of select="@xmi:id"/>
                      </td>
                    </xsl:if>
                    
                    <!-- ========== Enum Literal  NAME ========================= -->                    
                    <td>
                      <xsl:call-template name="fullQualifiedName"/>
                    </td>
                    
                    
                    <!-- ========== Enum Literal  VALUE ========================= -->
                    
                    
                    <xsl:choose>
                    <xsl:when test="eAnnotations/contents/@value">
                    <td>                  
                            <xsl:value-of select="eAnnotations/contents/@value"/>
                        </td>
                    </xsl:when>
                     <xsl:when test="eAnnotations/contents/@lower">
                    <td>
                    <xsl:value-of select="java:getMessage($bomMsgs, 'EnumLiteralValueLower_label')"/>
                            <xsl:value-of select="eAnnotations/contents/@lower"/>
                            <xsl:if test="eAnnotations/contents/@lowerInclusive">
                            <xsl:value-of select="java:getMessage($bomMsgs, 'Value_Inclusive')"/>
                            </xsl:if>
                            <xsl:if test="eAnnotations/contents/@upper">
                            <xsl:value-of select="java:getMessage($bomMsgs, 'Komma')"/>
                            <xsl:value-of select="java:getMessage($bomMsgs, 'EnumLiteralValueUpper_label')"/>
                            <xsl:value-of select="eAnnotations/contents/@upper"/>
                            <xsl:if test="eAnnotations/contents/@upperInclusive">
                            <xsl:value-of select="java:getMessage($bomMsgs, 'Value_Inclusive')"/>
                            </xsl:if>
                            </xsl:if>                                                 
                        </td>
                    </xsl:when>
                    <xsl:when test="eAnnotations/contents/@upper">
                    <td>
                    <xsl:value-of select="java:getMessage($bomMsgs, 'EnumLiteralValueUpper_label')"/>
                            <xsl:value-of select="eAnnotations/contents/@upper"/>
                            <xsl:if test="eAnnotations/contents/@upperInclusive">
                            <xsl:value-of select="java:getMessage($bomMsgs, 'Value_Inclusive')"/>
                            </xsl:if>
                        </td>
                    </xsl:when>
                    <xsl:otherwise>
                    <td>
                    <xsl:text> <xsl:value-of select="java:getMessage($bomMsgs, 'Not_Applicable')"/> </xsl:text>
                     </td>
                    </xsl:otherwise>
                    </xsl:choose>                                                                             
                    
                     <!-- ========== Enum Literal  Description ========================= -->
                    <td>
                            <xsl:value-of select="ownedComment/@body"/>
                        </td>
                    
                  </tr>
                </xsl:for-each>
              </tbody>
            </table>
        </xsl:when>
          
        </xsl:choose>
        
        <xsl:choose>
        
        <!-- ========== OPERATIONS ========================================= -->
        
        <xsl:when test="@xmi:type='uml:Class' and ownedOperation">
            <table>
            
              <!-- ========== OPERATION HEADER ============================= -->
            
              <div class="section">
                <thead>
                  <tr>
                    <th class="sectionTitle" colspan="6">
                      <xsl:element name="a">
                        <xsl:attribute name="id">ownedOperation_<xsl:value-of select="@xmi:id"/>
                        </xsl:attribute>
                      </xsl:element>Operations (<xsl:value-of select="count (ownedOperation)"/>)
                    </th>
                  </tr>
                  <tr>
                    <th class="headingId">
                    </th>
                    <xsl:if test="$showIds = 'true'">
                      <th class="columnWidth"><xsl:value-of select="java:getMessage($bomMsgs, 'ID_label')"/></th>
                    </xsl:if>
                    <th class="operationName"><xsl:value-of select="java:getMessage($bomMsgs, 'OperationName_label')"/></th>
                    <th class="operationReturnType"><xsl:value-of select="java:getMessage($bomMsgs, 'OperationReturnType_label')"/></th>   
                    <!-- Kapil: Added Multiplicity to Operations Table(Previously there was no multiplicity.) -->                 
                    <th class="detailColumnMultiplicity"><xsl:value-of select="java:getMessage($bomMsgs, 'Multiplicity_label')"/></th>                    
                    <th class="operationDescription"><xsl:value-of select="java:getMessage($bomMsgs, 'OperationDescription_label')"/></th>
                  </tr>
                </thead>
              </div>
              
              <tbody class="bodyText">
                <xsl:for-each select="ownedOperation">
                  <tr>
                  
                    <!-- ========== ATTRIBUTE NUMBER (ID FOR DEBUG) ======== -->
                    
                    <td class="position">
                      <xsl:element name="a">
                        <xsl:attribute name="id">ownedOperation_<xsl:value-of select="@xmi:id"/>
                        </xsl:attribute>
                        <xsl:value-of select="position()"/>
                      </xsl:element>
                    </td>
                    <xsl:if test="$showIds = 'true'">
                      <td>
                        <xsl:value-of select="@xmi:id"/>
                      </td>
                    </xsl:if>
                    
                    <!-- ========== OPERATION NAME ========================= -->
                    
                    <td>
                      <xsl:call-template name="fullQualifiedName"/>
                    </td>
                   <!--  <xsl:choose>
                    <xsl:when test="ownedParameter"></xsl:when>
                    <xsl:otherwise>
                    <td>
                           <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'OperationParameterTypeVoid_text')"/></xsl:text>   
                           </td> 
                           <td>
                           
                           <xsl:text> <xsl:value-of select="java:getMessage($bomMsgs, 'Not_Applicable')"/> </xsl:text>   
                            </td>
                    </xsl:otherwise>
                    </xsl:choose>-->

                    <!-- ========== OPERATION RETURN TYPE ================== -->
                    <!-- If return type =0, then set return type = Void and Multiplicity = NA -->
                    <xsl:variable name="CountName"><xsl:value-of select="count(ownedParameter/@direction)"/></xsl:variable>
                    <xsl:if test="$CountName='0'">
                    <td>
                           <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'OperationParameterTypeVoid_text')"/></xsl:text>   
                           </td>
                           <td>
                           <xsl:text> <xsl:value-of select="java:getMessage($bomMsgs, 'Not_Applicable')"/> </xsl:text>   
                           </td>
                    </xsl:if>
                    
					<xsl:for-each select="ownedParameter">                    
                       <xsl:choose>
                       
                       <xsl:when test="@xmi:type = 'uml:Parameter' and @direction = 'return'and @type"> 
                       <td>
                         <xsl:variable name="type" select="@type"/>
                       
                          <xsl:variable name="typeString" select="@type"/>
                          <xsl:variable name="tempType" select="substring-before(substring-after($typeString, '&qm;'), '&qm;')" />
                          <xsl:call-template name="SubstringReplace">
                            <xsl:with-param name="stringIn" select="$tempType"/>
                            <xsl:with-param name="substringIn" select="'%3a%3a'"/>
                            <xsl:with-param name="substringOut" select="'::'"/>
                          </xsl:call-template> 
                          </td>
                          
                          </xsl:when>
                         <xsl:when test="@xmi:type = 'uml:Parameter' and @direction = 'return' and type/@href ">
                         <td>
                           <xsl:variable name="hRef" select="type/@href"/>
                           <xsl:variable name="temp" select="substring-before(substring-after($hRef, '&qm;'), '&qm;')" />
                           <xsl:call-template name="SubstringReplace">
                             <xsl:with-param name="stringIn" select="$temp"/>
                             <xsl:with-param name="substringIn" select="'%3a%3a'"/>
                             <xsl:with-param name="substringOut" select="'::'"/>
                           </xsl:call-template>
                           </td>
                         </xsl:when>    
                       </xsl:choose>
                    
                    
                    <!-- ========== OPERATION Multiplicity ================== -->
                                                         
                         <xsl:choose>
                          <xsl:when test="@xmi:type = 'uml:Parameter' and @direction">
                         
                           <td> 
                            <xsl:choose>
  						  <xsl:when test="lowerValue/@value">
							 <xsl:value-of select="lowerValue/@value"/>   
							 <xsl:if test=" upperValue/@value and lowerValue/@value != upperValue/@value">
                                <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'DOT_DOT_label')"/></xsl:text>
                                <xsl:value-of select="upperValue/@value"/>
                                 </xsl:if>
						    </xsl:when>
    						<xsl:when test="upperValue/@value">
							<xsl:text>0</xsl:text>
 							<xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'DOT_DOT_label')"/></xsl:text>
                                <xsl:value-of select="upperValue/@value"/>
    							</xsl:when>
    							<xsl:otherwise>
    							<xsl:text>1</xsl:text>
    							</xsl:otherwise>
							</xsl:choose>   
							</td>                       
                          </xsl:when>
                          </xsl:choose>
                          </xsl:for-each>

						

                    <!-- ========== OPERATION DESCRIPTION ================== -->
                    
                    <td>
                      <xsl:value-of select="ownedComment/@body"/>
                    </td>
                  </tr>
                  
                  <tr>
                    <th class="opererationParametersIndent"></th>
                    <th class="operationParameters"><xsl:value-of select="java:getMessage($bomMsgs, 'OperationParameters_label')"/></th>
                    <th class="operationParameterTypes"><xsl:value-of select="java:getMessage($bomMsgs, 'OperationParameterTypes_label')"/></th>
                    <th class="detailColumnMultiplicity"><xsl:value-of select="java:getMessage($bomMsgs, 'Multiplicity_label')"/></th>  
                    <th class="opererationParametersIndent"></th>
                  </tr>


                    <xsl:for-each select="ownedParameter">
                    
                      <xsl:if test="@name">

                        <tr>
                          <td>
                          </td>
                      
                          <!-- ========== PARAMETER NAME ======================= -->

                          <td>                    
                          <xsl:text><xsl:value-of select="@name" /></xsl:text>                                                             
                          </td>

                          <!-- ========== PARAMETER TYPE ======================= -->

                          <td>
                             <xsl:choose>
                              <xsl:when test="@type">
                                <xsl:variable name="temp" select="substring-before(substring-after(@type, '&qm;'), '&qm;')" />
                                <xsl:call-template name="SubstringReplace">
                                  <xsl:with-param name="stringIn" select="$temp"/>
                                  <xsl:with-param name="substringIn" select="'%3a%3a'"/>
                                  <xsl:with-param name="substringOut" select="'::'"/>
                                </xsl:call-template>
                              </xsl:when>
                              <xsl:otherwise>
                                <xsl:variable name="hRef" select="type/@href"/>
                                <xsl:variable name="temp" select="substring-before(substring-after($hRef, '&qm;'), '&qm;')" />
                                <xsl:call-template name="SubstringReplace">
                                  <xsl:with-param name="stringIn" select="$temp"/>
                                  <xsl:with-param name="substringIn" select="'%3a%3a'"/>
                                  <xsl:with-param name="substringOut" select="'::'"/>
                                </xsl:call-template>
                              </xsl:otherwise>
                            </xsl:choose>                                                     
                         </td>
                         <td>   
                                                                       
                          <!-- =============PARAMETER MULTIPLICITY ============== -->
                          
                          <xsl:choose>
  						  <xsl:when test="lowerValue/@value">
							 <xsl:value-of select="lowerValue/@value"/>   
							 <xsl:if test=" upperValue/@value and lowerValue/@value != upperValue/@value">
                                <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'DOT_DOT_label')"/></xsl:text>
                                <xsl:value-of select="upperValue/@value"/>
                                 </xsl:if>
						    </xsl:when>
    						<xsl:when test="upperValue/@value">
							<xsl:text>0</xsl:text>
 							<xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'DOT_DOT_label')"/></xsl:text>
                                <xsl:value-of select="upperValue/@value"/>
    							</xsl:when>
    							<xsl:otherwise>
    							<xsl:text>1</xsl:text>
    							</xsl:otherwise>
							</xsl:choose>

                          </td>
                        
                        </tr>                        
                        
                      </xsl:if>
                      
                    </xsl:for-each>
                                        
                </xsl:for-each>
              </tbody>
            </table>
        </xsl:when>
      </xsl:choose>
      <xsl:call-template name="association">
          <xsl:with-param name="associationEnd" select="."/>
      </xsl:call-template>
    </div>

  </xsl:template>

  <!-- =================================================================== -->
  <!-- display notes attached to the parameter 'noteContainer'             -->
  <!-- =================================================================== -->
  
  <xsl:template name="note">
    <xsl:param name="noteContainer"/>
    <xsl:if test="$noteContainer/ownedComment">
        <table>
        <thead class="section">
          <tr>
            <th class="sectionTitle" colspan="6">
              <xsl:element name="a">
                <xsl:attribute name="name">notes_<xsl:value-of select="$noteContainer/@xmi:id"/>
                </xsl:attribute><xsl:value-of select="java:getMessage($bomMsgs, 'Notes_label')"/></xsl:element>
            </th>
          </tr>
        </thead>
        <tbody>
        
          <xsl:for-each select="$noteContainer/ownedComment">
            <xsl:variable name="noteText" select="@body"/>
            <xsl:if test="$noteText">
              <tr>
                <td class="indent">
                  <xsl:value-of select="position()"/>
                </td>
                <td>
                  <xsl:value-of select="@body"/>
                </td>
              </tr>
            </xsl:if>
          </xsl:for-each>
        </tbody>
      </table>
    </xsl:if>
  </xsl:template>

  <!-- =================================================================== -->
  <!-- displays any associations where this Concept is an end              --> 
  <!-- =================================================================== -->
  
  <xsl:template name="association">
    <xsl:param name="associationEnd"/>
    <xsl:variable name="associationXmiId" select="substring-before(@xmi:id, '&qm;')"/>
    <xsl:variable name="ownedEndXmiId" select="$associationEnd/@xmi:id"/>
    <xsl:variable name="ownedEndXmiName" select="$associationEnd/@xmi:name"/>
    <xsl:variable name="associationName" select="@name"/>
    
    <xsl:if test="@xmi:type='uml:Association'">
    
     <xsl:element name="a">
       <xsl:attribute name="name">association_<xsl:value-of select="@xmi:id"/></xsl:attribute>
     </xsl:element>
    
      <table>
        <tbody>
            <xsl:variable name="memberEndType" select="$associationEnd/@type"/>
            <xsl:variable name="memberEndXmiId" select="substring-before(substring-after(@memberEnd, ' '), '&qm;')"/>
            <xsl:variable name="sourceTypeName" select="substring-before(substring-after(substring-after(@memberEnd, ' '), '&qm;'), '&qm;')"/>
            <xsl:variable name="destinationTypeName" select="substring-before(substring-after(substring-before(@memberEnd, ' '), '&qm;'), '&qm;')"/>
            <xsl:variable name="memberEndAttribute" select="//ownedAttribute[@xmi:id=$memberEndXmiId]"/>
            <xsl:variable name="ownedEndType" select="ownedEnd/@type"/>
            <xsl:variable name="ownedEndXmiId" select="substring-before(substring-before(@memberEnd, ' '), '&qm;')"/>
            <xsl:variable name="ownedEndAttribute" select="//ownedAttribute[@xmi:id=$ownedEndXmiId]"/>
          <xsl:if test="$associationName=@name">
          <thead>
            <tr>
              <th class="indent" />
              <th class="column_associationName"><xsl:value-of select="java:getMessage($bomMsgs, 'Association_name_label')"/></th>
              <th class="column_associationType"><xsl:value-of select="java:getMessage($bomMsgs, 'Association_type_label')"/></th>
              <th class="column_associationSourceDestination"><xsl:value-of select="java:getMessage($bomMsgs, 'Source_label')"/></th>
              <th class="column_associationMultiplicity"><xsl:value-of select="java:getMessage($bomMsgs, 'Multiplicity_label')"/></th>
              <th class="column_associationSourceDestination"><xsl:value-of select="java:getMessage($bomMsgs, 'Destination_label')"/></th>
              <th class="column_associationMultiplicity"><xsl:value-of select="java:getMessage($bomMsgs, 'Multiplicity_label')"/></th>
            </tr>
          </thead>
          <tr>
            <td class="indent" />
            
              <!-- ========== ASSOCIATION NAME ============================= -->

              <td class="column_associationName">
                <xsl:value-of select="@name"/>
              </td>

             <!-- ========== ASSOCIATION TYPE ============================== -->

              <td class="column_associationName">
                <xsl:choose>
                  <xsl:when test="$memberEndAttribute/@aggregation = 'composite'">
                     <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'AssociationType_Composition_text')"/></xsl:text>
                  </xsl:when>
                  <xsl:when test="$memberEndAttribute/@aggregation = 'shared'">
                     <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'AssociationType_Aggregation_text')"/></xsl:text>
                  </xsl:when>
                  <xsl:otherwise>
                     <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'AssociationType_Simple_text')"/></xsl:text>
                  </xsl:otherwise>
                  
                </xsl:choose>
   
              </td>

              <!-- ========== SOURCE NAME ================================== -->

              <td>
                 <xsl:call-template name="SubstringReplace">
                   <xsl:with-param name="stringIn" select="$sourceTypeName"/>
                   <xsl:with-param name="substringIn" select="'%3a%3a'"/>
                   <xsl:with-param name="substringOut" select="'::'"/>
                 </xsl:call-template>
              </td>

              <!-- ========== SOURCE MULTIPLICITY ========================== -->

              <td>
                <xsl:choose>
                  <xsl:when test="$memberEndAttribute/lowerValue/@value">
                    <xsl:value-of select="$memberEndAttribute/lowerValue/@value"/>
                  </xsl:when>
                  <xsl:otherwise>
                    <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'DIGIT_ZERO')"/></xsl:text>
                  </xsl:otherwise>
                </xsl:choose>
                
                <xsl:choose>
                  <xsl:when test="$memberEndAttribute/lowerValue/@value = $memberEndAttribute/upperValue/@value">
                  </xsl:when>
                  <xsl:otherwise>
                    <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'DOT_DOT_label')"/></xsl:text>
                    <xsl:value-of select="$memberEndAttribute/upperValue/@value"/>
                  </xsl:otherwise>
                  
                </xsl:choose>
                
                
              </td>
              
              <!-- ========== DESTINATION NAME ================================== -->

              <td class="column_associationSourceDestination">
                <xsl:choose>
                  <xsl:when test="ownedEnd">
                    <xsl:text></xsl:text>
                  </xsl:when>
                  <xsl:otherwise>
                    <xsl:call-template name="SubstringReplace">
                      <xsl:with-param name="stringIn" select="$destinationTypeName"/>
                      <xsl:with-param name="substringIn" select="'%3a%3a'"/>
                      <xsl:with-param name="substringOut" select="'::'"/>
                    </xsl:call-template>
                  </xsl:otherwise>
                </xsl:choose>
              </td>
                            
              <!-- ========== DESTINATION MULTIPLICITY ========================== -->
              
              <td class="column_associationSourceDestination">
              
              
                <xsl:choose>
              
                  <xsl:when test="ownedEnd">
                    <xsl:value-of select="ownedEnd/lowerValue/@value"/>
                    <xsl:choose>
                      <xsl:when test="ownedEnd/lowerValue/@value = ownedEnd/upperValue/@value">
                      </xsl:when>
                      <xsl:otherwise>
                        <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'DOT_DOT_label')"/></xsl:text>
                        <xsl:value-of select="ownedEnd/upperValue/@value"/>
                      </xsl:otherwise>
                    </xsl:choose>
                  </xsl:when>
                  
                  <xsl:otherwise>
                    <xsl:choose>
                      <xsl:when test="$ownedEndAttribute/lowerValue/@value">
                        <xsl:value-of select="$ownedEndAttribute/lowerValue/@value"/>
                      </xsl:when>
                      <xsl:otherwise>
                        <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'DIGIT_ZERO')"/></xsl:text>
                      </xsl:otherwise>
                    </xsl:choose>
                
                    <xsl:choose>
                      <xsl:when test="$ownedEndAttribute/lowerValue/@value = $ownedEndAttribute/upperValue/@value">
                      </xsl:when>
                      <xsl:otherwise>
                        <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'DOT_DOT_label')"/></xsl:text>
                        <xsl:value-of select="$ownedEndAttribute/upperValue/@value"/>
                      </xsl:otherwise>
                    </xsl:choose>
                  </xsl:otherwise>
                </xsl:choose>
                  <xsl:text> </xsl:text>
                  <xsl:value-of select="//packagedElement[@xmi:id=$ownedEndType]/@name"/>
                </td>
              </tr>
            </xsl:if>
        </tbody>
      </table>
    </xsl:if>
  </xsl:template>

  <!-- =================================================================== -->
  <!-- Catch-all template                                                  -->
  <!-- =================================================================== -->
  
  <!-- <xsl:template match="@* | node() | text()">
    <!- - Supress default copy of all nodes and text - ->
  </xsl:template> -->
   
  <!-- =================================================================== -->
  <!--                                                                     -->
  <!-- LIBRARY FUNCTIONS                                                   -->
  <!--                                                                     -->
  <!-- =================================================================== -->
  
   <xsl:template name="fullQualifiedName">
                <xsl:variable name="rootName" select="//@name"/>
                <xsl:variable name="thisName" select="@name"/>
                <xsl:variable name="beforeName1" select="../@name"/>
                <xsl:variable name="beforeName2" select="../../@name"/>
                <xsl:variable name="beforeName3" select="../../../@name"/>
                <xsl:variable name="beforeName4" select="../../../../@name"/>
                <xsl:variable name="beforeName5" select="../../../../../@name"/>
                <xsl:if test="$beforeName5!=$rootName">
                  <xsl:value-of select="$beforeName5"/>
	              <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'COLON_COLON')"/></xsl:text>
                </xsl:if>
                <xsl:if test="$beforeName4!=$rootName">
                  <xsl:value-of select="$beforeName4"/>
	              <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'COLON_COLON')"/></xsl:text>
                </xsl:if>
                <xsl:if test="$beforeName3!=$rootName">
                  <xsl:value-of select="$beforeName3"/>
	              <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'COLON_COLON')"/></xsl:text>
                </xsl:if>
                <xsl:if test="$beforeName2!=$rootName">
                  <xsl:value-of select="$beforeName2"/>
	              <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'COLON_COLON')"/></xsl:text>
                </xsl:if>
                <xsl:if test="$beforeName1!=$rootName">
                  <xsl:value-of select="$beforeName1"/>
	              <xsl:text><xsl:value-of select="java:getMessage($bomMsgs, 'COLON_COLON')"/></xsl:text>
                </xsl:if>
                <xsl:value-of select="@name"/>
  </xsl:template>
  
  
  <xsl:template name="SubstringReplace">
    <xsl:param name="stringIn"/>
    <xsl:param name="substringIn"/>
    <xsl:param name="substringOut"/>
	<xsl:choose>
      <xsl:when test="contains($stringIn,$substringIn)">
        <xsl:value-of select="concat(substring-before($stringIn,$substringIn),$substringOut)"/>
        <xsl:call-template name="SubstringReplace">
          <xsl:with-param name="stringIn" select="substring-after($stringIn,$substringIn)"/>
          <xsl:with-param name="substringIn" select="$substringIn"/>
          <xsl:with-param name="substringOut" select="$substringOut"/>
        </xsl:call-template>
      </xsl:when>
	  <xsl:otherwise>
        <xsl:value-of select="$stringIn"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>
  
  
</xsl:stylesheet>
