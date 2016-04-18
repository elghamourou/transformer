<?xml version="1.0" encoding="ISO-8859-1" standalone="no"?>
<!--Generated using the Java XML Mapper - Jamper--><!--jamper@sourceforge.net--><xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns:user="urn:user" xmlns:var="urn:var" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" exclude-result-prefixes="user xs fn  user" version="2.0">
<xsl:output encoding="UTF-8" indent="yes" method="xml" omit-xml-declaration="yes"/>
<xsl:template match="/src">
<dst>
<dataDst>
<dstname>
<xsl:value-of select="dataSrc/name"/>
</dstname>
</dataDst>
</dst>
</xsl:template>
</xsl:stylesheet>