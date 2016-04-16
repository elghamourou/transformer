/*********************************************************************\
*                                                                     *
*         Jamper - Java XML mapper (visual XSLT generator)            *
*                Copyright (C) 2005 Filip Pavlovic                    *
*                    sqba@users.sourceforge.net                       *
*                                                                     *
*  This program is free software; you can redistribute it and/or      *
*  modify it under the terms of the GNU General Public License        *
*  as published by the Free Software Foundation; either version 2     *
*  of the License, or (at your option) any later version.             *
*                                                                     *
*  This program is distributed in the hope that it will be useful,    *
*  but WITHOUT ANY WARRANTY; without even the implied warranty of     *
*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the      *
*  GNU General Public License for more details.                       *
*                                                                     *
*  You should have received a copy of the GNU General Public License  *
*  along with this program; if not, write to the Free Software        *
*  Foundation, Inc., 59 Temple Place - Suite 330, Boston,             *
*  MA 02111-1307, USA.                                                *
*                                                                     *
\*********************************************************************/


package utils;


import java.io.IOException;
import java.io.DataOutputStream;


/**
 * Utility class.
 * Contains utility functions for manipulating XMLs.
 *
 * @author	Filip Pavlovic
 * @version	1.0
 */
public class XMLUtils
{
	/**
	 * Replaces special XML characters.
	 *
	 * @param str String to be escaped
	 *
	 * @return Escaped string.
	 */
	public static String escape(String str)
	{
    	String result;
    	result =    str.replace("&",  "&amp;");
    	result = result.replace("<",  "&lt;");
    	result = result.replace(">",  "&gt;");
    	result = result.replace("\"", "&quot;");
    	result = result.replace("'",  "&apos;");
    	return result;
	}

	public static String unEscape(String str)
	{
    	String result;
    	result =    str.replace("&amp;",  "&");
    	result = result.replace("&lt;",   "<");
    	result = result.replace("&gt;",   ">");
    	result = result.replace("&quot;", "\"");
    	result = result.replace("&apos;", "'");
    	return result;
	}

	// Mora ovako zbog UNICODE-a
    public static void writeStr(String str, DataOutputStream outStream)
    {
    	try {
	    	for(int i=0; i<str.length(); i++) {
	    		char c = str.charAt(i);
	    		outStream.writeByte(c);
	    	}
		} catch(IOException e) {
			System.out.println(e);
			return;
		}
    }
}
