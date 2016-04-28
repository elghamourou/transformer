<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:axsl="http://www.w3.org/1999/XSL/TransformAlias" xmlns:map="mapping"
	version="2.0" exclude-result-prefixes="map">
	<xsl:output indent="yes" method="xml" encoding="utf-8"
		omit-xml-declaration="no" />
	<xsl:namespace-alias stylesheet-prefix="axsl"
		result-prefix="xsl" />


	<xsl:template match="/">
	<xsl:variable name="outermostElementName" select="name(/*)" />
		<axsl:stylesheet version="2.0">
			<axsl:output indent="yes" method="xml" encoding="utf-8"
				omit-xml-declaration="yes" />
				
				
<!-- 				<axsl:template match="/{$outermostElementName}"> -->
<!-- 					<axsl:apply-templates /> -->
<!-- 				</axsl:template> -->
				
				<axsl:template match="/">
					<xsl:apply-templates />
				</axsl:template>
				
			
		</axsl:stylesheet>
	</xsl:template>


	<!-- template to copy elements -->
	<xsl:template match="*">
		<xsl:element name="{local-name()}">
			<xsl:apply-templates select="@* | node()" />
		</xsl:element>
	</xsl:template>

	<!-- template to copy attributes -->
	<xsl:template match="@*">
		<xsl:attribute name="{local-name()}">
			<xsl:value-of select="." />
		</xsl:attribute>
	</xsl:template>

	<!-- template to copy the rest of the nodes -->
	<xsl:template match="comment() | text() | processing-instruction()">
		<xsl:copy />
	</xsl:template>


	<xsl:template match="map:value">
		<axsl:value-of select="{@path}" >
			<xsl:apply-templates />
		</axsl:value-of>
	</xsl:template>

	<xsl:template match="map:list">
		<axsl:for-each select="{@path}">
					<xsl:apply-templates />
		</axsl:for-each>
	</xsl:template>
	
	
	<xsl:template match="map:list2">
		<axsl:for-each select="{@path}">
					<axsl:apply-templates select = "."/>
		</axsl:for-each>
	</xsl:template>
	
	
	
	

</xsl:stylesheet>