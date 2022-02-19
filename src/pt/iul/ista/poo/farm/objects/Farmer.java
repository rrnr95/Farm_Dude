package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Farmer extends FarmObject {

	public Farmer(Point2D p) {
		super(p);
	}
	
	@Override
	public int getLayer(){
		return 2;
	}
	
	public void moveIn(Direction dir){
		
		Point2D destination = new Point2D(this.getPosition().getX() + dir.asVector().getX(), this.getPosition().getY() + dir.asVector().getY() );			
		boolean canMove = true;
		
		if (ImageMatrixGUI.getInstance().isWithinBounds(destination)) {
			for(ImageTile i : Farm.getInstance().images) 
				if(i.getPosition().equals(destination) && i.getLayer() >= 2) {
					canMove = false;
					break;
				}
			if(canMove) 
				this.setPosition(destination.getX(), destination.getY());
		}
	}
}

