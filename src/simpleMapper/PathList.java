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


package simpleMapper;


import java.util.Iterator;
import java.util.ArrayList;


/**
 * Utility class.
 * Converts ArrayList of objects into a String containing the full XPath.
 *
 * @author Filip Pavlovic
 * @version 1.0
 */
public class PathList extends ArrayList
{
	/**
	 * Constructor
	 */
	public PathList()
	{
		super();
	}

	/**
	 * Constructor
	 *
	 * @param path String containing the XPath
	 */
	public PathList(String path)
	{
		this();
		this.set(path);
	}

	/**
	 * Renders the XPath string.
	 *
	 * @param fromRoot boolean, if true then the path begins with '/'
	 *
	 * @return XPath string.
	 */
	private String render(boolean fromRoot)
	{
        String result = new String();
		Iterator iter = iterator();
		while ( iter.hasNext() ) {
        	String elem = (String)iter.next();
        	if(fromRoot || result.length()>0) {
	        	result += "/";
         	}
	     	result += elem;
        }
        return result;
	}

	/**
	 * Renders the absolute XPath string.
	 *
	 * @return absolute XPath string.
	 */
	public String toString()
	{
		return render(true);
	}

	/**
	 * Returns name of the last object in the path.
	 *
	 * @return name of the last object in the path.
	 */
	public String getLast()
	{
		return (String)this.get(size()-1);
	}

	/**
	 * Removes last object ftom the path.
	 */
	public void removeLast()
	{
		this.remove(size()-1);
	}

	/**
	 * Sets new path.
	 *
	 * @param path String containing the new XPath
	 */
	public void set(String path)
	{
		this.clear();
		String[] s = path.split("/");
		for(int i=0; i<s.length; i++) {
			if(s[i].length() > 0)
				this.add(s[i]);
		}
	}

	/**
	 * Returns the number of common path elements.
	 *
	 * @param path1 PathList
	 * @param path2 PathList
	 *
	 * @return number of common path elements.
	 */
	private static int getCommonLevel(PathList path1, PathList path2)
	{
		int n = (path1.size() < path2.size() ? path1.size() : path2.size());
		int result = 0;
		for(int i=0; i<n; i++) {
			String a = (String)path1.get(i);
			String b = (String)path2.get(i);
			if(a.equals(b))
				result++;
			else
				break;
		}
		return result;
	}
/*
	private PathList getCommonPath(PathList path1, PathList path2)
	{
		int n = (path1.size() < path2.size() ? path1.size() : path2.size());
		PathList result = new PathList();
		for(int i=0; i<n; i++) {
			String a = (String)path1.get(i);
			String b = (String)path2.get(i);
			if(a.equals(b))
				result.add(a);
			else
				break;
		}
		return result;
	}
*/
	/**
	 * Returns the path relative to this.
	 *
	 * @param path String containing the XPath
	 *
	 * @return relative path.
	 */
	public String getRelativePath(String path)
	{
		PathList newPath = new PathList();
		newPath.set(path);
		//PathList outPath = new PathList();


		/*int n = (this.size() < newPath.size() ? this.size() : newPath.size());
		int c = 0;
		for(int i=0; i<n; i++) {
			String a = (String)this.get(i);
			String b = (String)newPath.get(i);
			if(a.equals(b)) {
				c++;
			}
		}*/

		//System.out.println(this + " " + newPath + " " + getCommonPath(this, newPath));
		int c = getCommonLevel(this, newPath);

		if(c>0) {
			PathList pl = new PathList();
			if(this.size() > c) {
				pl.add("ancestor::*[" + (this.size() - c) + "]");
			}
			for(int x=c; x<newPath.size(); x++) {
				pl.add((String)newPath.get(x));
			}
			return pl.render(false);
		} else {
			//System.out.println(path + " " + this.toString());
			return path;
		}
	}
}
