package de.nomisge.bluejextensions.cleanproject;

import bluej.extensions2.*;
import java.net.URL;
//import java.net.URL;

/*
 * A BlueJ extension to remove un-needed files from a BlueJ project
 * 
 * @author Ian Utting (updated for bluej 5 by: nomisge @ live . de)
 * @version 2.0.0
 */

public class CleanProjectExtension extends Extension {
    /*
     * When this method is called, the extension may start its work.
     */
    @Override
    public void startup (BlueJ bluej) {
        // Register a generator for menu items
		MenuBuilder myMenu = new MenuBuilder();
		myMenu.setBlueJ(bluej);
		bluej.setMenuGenerator(myMenu);
    }
    
    /**
     * Compatible with all versions of the extensions API
     * @return true
     */
    @Override
    public boolean isCompatible () { 
        return true; 
    }

    /**
     * @return the version number of this extension
     */
    @Override
    public String  getVersion () { 
        return ("2.0.0");  
    }

    /**
     * @return the user-visible name of this extension
     */
    @Override
    public String  getName () { 
        return ("Clean a BlueJ Project");  
    }
    
    /**
     * @return a user-visible description of this extension
     */
    @Override
    public String getDescription () {
        return ("Removes .class and other files from a BlueJ project to help in exporting to (e.g.) NetBeans");
    }

    /**
     * @return a URL where you can find info on this extension.
     */
    @Override
    public URL getURL () {
        try {
            return new URL("http://bluej.org/extensions/extensions.html");
        } catch ( Exception eee ) {
            // The link is either dead or otherwise unreachable
            System.out.println ("Simple extension: getURL: Exception="+eee.getMessage());
            return null;
        }
    }
}
