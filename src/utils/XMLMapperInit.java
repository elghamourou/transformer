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


import java.io.File;

//import java.net.URL;

import javax.swing.JMenu;
//import javax.swing.JFrame;
import javax.swing.ImageIcon;
//import javax.swing.JMenuItem;
//import javax.swing.KeyStroke;
import javax.swing.UIManager;
//import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.JRadioButtonMenuItem;

import java.awt.Component;

//import java.awt.event.KeyEvent;
import java.awt.event.ItemEvent;
//import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
//import java.awt.event.ActionListener;


/**
 * Constants and utility functions.
 *
 * @author	Filip Pavlovic
 * @version	1.0
 */
public class XMLMapperInit
{
	public static final String ICON_APP		= "cube.gif";
	public static final String ICON_LEAF	= "attribute.gif";
	public static final String ICON_OPEN	= "open.gif";
	public static final String ICON_CLOSED	= "closed.gif";

	private static Component _c;
    private static File _directory = new File(".");

	/** File types to be handled in open/close dialog */
    private static final String[][] _extensions = {
						{"xdr",		"XML Schema files (Microsoft)"},
						{"xsd",		"XML Schema files (W3C)"},
						{"htm",		"HTML files"},
						{"html",	"HTML files"},
						{"xml",		"XML files (instance)"} };

	/**
	 * Loads and icon from the .jar file.
	 *
	 * @param name String
	 *
	 * @return ImageIcon
	 */
	public static ImageIcon loadIcon(String name)
	{
		Class c = XMLMapperInit.class;
		java.net.URL url = c.getResource("/sqba/images/" + name);
		ImageIcon icon = new ImageIcon(url);
		return icon;
	}

	/**
	 * Opens the file browsing dialog.
	 *
     * @parem load Load/Save
     *
	 * @return File path
	 */
	public static String browse(boolean load)
	{
		String result = "";
		JFileChooser chooser = new JFileChooser();
		for(int i=0; i<_extensions.length; i++) {
			//if(!load && (i!=_extensions.length-1))
			chooser.setFileFilter(
				new ExampleFileFilter(_extensions[i][0], _extensions[i][1]));
		}
		chooser.setCurrentDirectory(_directory);
		int returnVal = JFileChooser.CANCEL_OPTION;
		if(load)
			returnVal = chooser.showOpenDialog(null); //this
		else
			returnVal = chooser.showSaveDialog(null); //this
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				result = chooser.getSelectedFile().getAbsolutePath();
				_directory = chooser.getCurrentDirectory();
			} catch(Exception err) {
				err.printStackTrace();
			}
		}
		return result;
	}

/*
	public static JMenu createMenu(String name, String[] items, ActionListener al)
   	{
    	JMenu menu = new JMenu(name);
        for(int i=0; i<items.length; i++) {
        	if(items[i] != null) {
				//JMenuItem item = new JMenuItem(items[i], i);
				JMenuItem item = new JMenuItem(items[i]);
				//item.setMnemonic(KeyEvent.VK_C);
				//item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.ALT_MASK));
				item.setMnemonic(KeyEvent.VK_A + i);
				item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A + i, ActionEvent.ALT_MASK));
				item.addActionListener(al);
				menu.add(item);
	    	} else
	    		menu.addSeparator();
    	}
    	return menu;
	}
*/
	public static void initLookAndFeelMenu(JMenu optionsMenu, Component c)
	{
		_c = c;
		UIManager.LookAndFeelInfo[] lafs = UIManager.getInstalledLookAndFeels();
		ButtonGroup lafGroup = new ButtonGroup();
		for(int i = 0; i < lafs.length; i++) {
			JRadioButtonMenuItem rb = new JRadioButtonMenuItem(lafs[i].getName());
			optionsMenu.add(rb);
			rb.setSelected(UIManager.getLookAndFeel().getName().equals(lafs[i].getName()));
			rb.putClientProperty("UIKey", lafs[i]);
			rb.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent ae) {
					JRadioButtonMenuItem rb2 = (JRadioButtonMenuItem)ae.getSource();
					if(rb2.isSelected()) {
						UIManager.LookAndFeelInfo info = (UIManager.LookAndFeelInfo)rb2.getClientProperty("UIKey");
						try {
							UIManager.setLookAndFeel(info.getClassName());
							SwingUtilities.updateComponentTreeUI(_c);
						} catch (Exception e) {
							System.err.println("unable to set UI " + e.getMessage());
						}
					}
				}
			});
			lafGroup.add(rb);
		}
	}

    /**
     * Returns the resource bundle associated with this demo. Used
     * to get accessable and internationalized strings.
     */
    /*public ResourceBundle getResourceBundle()
    {
		if(bundle == null) {
		    bundle = ResourceBundle.getBundle("resources.swingset");
		}
		return bundle;
    }*/

    /**
     * This method returns a string from the demo's resource bundle.
     */
    /*public String getString(String key)
    {
		String value = null;
		try {
			value = getResourceBundle().getString(key);
		} catch (MissingResourceException e) {
			System.out.println("java.util.MissingResourceException: Couldn't find value for: " + key);
		}
		if(value == null) {
			value = "Could not find resource: " + key + "  ";
		}
		return value;
    }*/

    /**
     * Returns a mnemonic from the resource bundle. Typically used as
     * keyboard shortcuts in menu items.
     */
    /*public char getMnemonic(String key)
    {
		return (getString(key)).charAt(0);
    }*/

    /**
     * Creates an icon from an image contained in the "images" directory.
     */
    /*public ImageIcon createImageIcon(String filename, String description)
    {
		String path = "/resources/images/" + filename;
		return new ImageIcon(getClass().getResource(path));
    }*/

    /**
     * If DEBUG is defined, prints debug information out to std ouput.
     */
	/*public void debug(String s)
	{
		if(DEBUG) {
			System.out.println((debugCounter++) + ": " + s);
		}
	}*/
/*
	public static void showAboutDlg()
	{
		//JOptionPane.showMessageDialog(XMLMapper.this,
		JOptionPane.showMessageDialog(null,
			"Filip Pavlovic\nsqba@users.sourceforge.net\nhttp://jamper.sourceforge.net",
			"Jamper - Java XML Mapper",
			JOptionPane.PLAIN_MESSAGE,
			loadIcon(XMLMapperInit.ICON_APP));
	}
*/
	/**
	 * Tries to load system look and feel, and, if fails,
	 * loads cross platform look and feel.
	 */
	public static void initLookAndFeel()
    {
		String className;
		try {
			className = UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel(className);
		} catch (Exception exc) {
			try {
				className = UIManager.getCrossPlatformLookAndFeelClassName();
				UIManager.setLookAndFeel(className);
			} catch (Exception exc2) {
				System.err.println("Error loading L&F: " + exc2);
			}
		}
    }

	public static String getPathOnly(String path)
	{
		String result = "";
		String[] s = path.split((File.separator.equals("\\") ? "\\\\" : File.separator));
		if(s.length > 0) {
			for(int i=0; i<s.length-1; i++) {
				result += (result.length() > 0 ? File.separator : "") + s[i];
			}
			result += File.separator;
		}
		return result;
	}
}
