package de.nomisge.bluejextensions.cleanproject;

import java.io.File;
import java.io.IOException;

import bluej.extensions2.*;
import javafx.event.*;
import javafx.scene.control.*;

class MenuBuilder extends MenuGenerator {
	private BPackage curPackage;
	private BClass curClass;
	private BObject curObject;
	private EventHandler<ActionEvent> menAction = menuAction();
	private BlueJ bluej;
	
	public void setBlueJ(BlueJ bluej)
	{
		this.bluej = bluej;
	}
	
	
	@Override
	public MenuItem getToolsMenuItem(BPackage aPackage) {
		if(this.bluej == null) return null;	
		MenuItem mi = new MenuItem();
		mi.setText( bluej.getLabel("menu.tools.cleanproject"));
		mi.setId("Tools menu:");
		mi.setOnAction(menAction);
		return mi;
	}

	// These methods will be called when
	// each of the different menus are about to be invoked.
	@Override
	public void notifyPostToolsMenu(BPackage bp, MenuItem mi) {
		System.out.println("Post on Tools menu");
		curPackage = bp;
		curClass = null;
		curObject = null;
	}

	/**
	 *  An action to delete all the .class and .ctxt files,
	 *  and the doc directory, from a BlueJ project
	 */
	public EventHandler<ActionEvent> menuAction() {
		return actionEvent -> {
			try {
				if (curObject != null)
					curClass = curObject.getBClass();
				if (curClass != null)
					curPackage = curClass.getPackage();

				// Run the clean-up itself on a separate thread
				(new Thread() {
					@Override
					public void run() {
						try {
							BPackage[] bpl = curPackage.getProject().getPackages();
							for (BPackage bp : bpl) {
								clean(bp.getDir());
							}
						} catch (IOException ioe) {
						} catch (ExtensionException ee) {
						}
					}
				}).start();
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		};
	}

	/**
	 * Remove the .class and .ctxt files, and (recursively) the doc directory from
	 * dir
	 * 
	 * @param dir the folder to be cleaned
	 * @throws IOException
	 */
	private void clean(File dir) throws IOException {

		// Shouldn't happen
		if (!dir.isDirectory())
			return;

		for (File c : dir.listFiles()) {
			// Delete all .class and .ctxt files
			if (c.getName().endsWith(".class"))
				c.delete();
			if (c.getName().endsWith(".ctxt"))
				c.delete();

			// and (recursively) the doc folder
			if (c.isDirectory() && c.getName().equals("doc")) {
				totallyRemove(c);
			}
		}
	}

	/**
	 * Recursively remove a directory
	 * 
	 * @param f the directory to be removed
	 * @throws IOException
	 */
	private void totallyRemove(File f) throws IOException {
		if (f.isDirectory()) {
			for (File c : f.listFiles())
				totallyRemove(c);
		}
		f.delete();
	}

}