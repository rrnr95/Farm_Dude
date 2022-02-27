package pt.iul.ista.poo.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;



public final class Saver {
	
	PrintWriter fileWriter;
	String fileName = null;
	
	public Saver(String fileName){
		this.fileName = fileName;
	}
	
	public final void save(){
		try {
			this.fileWriter = new PrintWriter(new File(fileName));
			fileWriter.println((int)ImageMatrixGUI.getInstance().getGridDimension().getWidth() + ";" + (int)ImageMatrixGUI.getInstance().getGridDimension().getHeight() + ";");
			fileWriter.println(Farm.getInstance().getPoints() +";");
			
			for(ImageTile i: Farm.getInstance().images)
				fileWriter.println(i.toString() + ";");		
			fileWriter.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}
	
	
}
