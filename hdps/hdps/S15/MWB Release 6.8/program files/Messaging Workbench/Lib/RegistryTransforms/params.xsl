<xsl:stylesheet
     xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
     xmlns:fo="http://www.w3.org/1999/XSL/Format"
     xmlns:java="http://xml.apache.org/xslt/java">

  <!--
    Place the name of the document here.
    It will appear in the header of the document
  -->
  <xsl:variable name="DocName">HL7 Conformance Message Profile</xsl:variable>


  <!--
    Place the relative path location of the graphic image to appear
    in the upper left of the document.  If left blank, no image will be used.
  -->
<!--
  <xsl:variable name="HeaderGraphic"></xsl:variable>
-->
  <xsl:variable name="HeaderGraphic">C:\Hbocdata\HL7\Conformance\Balt_Prep\JP_ mckesson.gif</xsl:variable>

  <!--
    Place the full path location of the graphic image to appear
    as a banner on the document.  If left blank, no image will be used.
  <xsl:variable name="Banner">C:\Hbocdata\HL7\Conformance\Balt_Prep\DSbanner.jpg</xsl:variable>
  <xsl:variable name="AltBanner">Data Standards Center</xsl:variable>

  <xsl:variable name="Banner"></xsl:variable>
  <xsl:variable name="AltBanner"></xsl:variable>  
  -->
  <xsl:variable name="Banner"></xsl:variable>
  <xsl:variable name="AltBanner"></xsl:variable>  
  <!--
    This should be 'true' if rows with an optionality of 'X' should be
    displayed, and 'false' otherwise.
  -->
  <xsl:variable name="IncludeNotSupported">false</xsl:variable>

  <!--
    This should be 'true' if tables should be
    displayed, and 'false' otherwise.
  -->
  <xsl:variable name="IncludeTables">true</xsl:variable>

  <!--
    This should be 'true' if Usage Extensionsshould be
    displayed, and 'false' otherwi \se.
  -->
  <xsl:variable name="UsageExtensions">true</xsl:variable>

  <!--
    This should be 'true' if only fields should be
    displayed, and 'false' otherwise.
  -->
  <xsl:variable name="FieldsOnly">true</xsl:variable>

  <!--
    This should be 'true' if only the Reference should be
    displayed, and 'false' otherwise.
  -->
  <xsl:variable name="DisplayReference">false</xsl:variable>

  <!--
    This should be 'true' if only the Example and Fixed Values should be
    displayed, and 'false' otherwise.
  -->
  <xsl:variable name="DisplayValues">true</xsl:variable>

  <!--
    This should be 'true' if only the Predicates should be
    displayed, and 'false' otherwise.
  -->
  <xsl:variable name="DisplayPredicate">true</xsl:variable>

</xsl:stylesheet>