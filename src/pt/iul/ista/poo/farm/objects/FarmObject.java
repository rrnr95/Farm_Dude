package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public abstract class FarmObject implements ImageTile {

	private Point2D position;

	public FarmObject(Point2D p) {
		this.position = p;
	}
	
	public FarmObject(){
		this.position = randomSpawnPoint();
	}

	@Override
	public String getName() {
		return getClass().getSimpleName().toLowerCase();
	}

	@Override
	public Point2D getPosition() {
		return position;
	}
	

	@Override
	public int getLayer() {
		return 0;
	}
	
	public void setPosition(int x, int y){
		this.position = new Point2D(x, y);
	}	
	
	private Point2D randomSpawnPoint(){
		int x, y;
		x = (int) (Math.random() * ImageMatrixGUI.getInstance().getGridDimension().width);
		if(x == 0)
			y = (int) (Math.random() * (ImageMatrixGUI.getInstance().getGridDimension().height - 1)) + 1;
		else
			y = (int) (Math.random() * ImageMatrixGUI.getInstance().getGridDimension().height);
		
		return new Point2D(x, y);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName() + ";" + getPosition().getX() + ";" + getPosition().getY();
	}
	
}
