package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Sheep extends Animal{

	private int cycleCount = 20;
	private int state = 0;		/*	0 - fed
							  		1 - hungry (roaming for food)
							  		2 - famished
								*/

	public Sheep(Point2D p) {
		super(p);
		// TODO Auto-generated constructor stub
	}
	public Sheep() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public Sheep(Point2D p, int state){
		super(p);
		this.state = state;
		if(state == 2)
			this.cycleCount = 0;
	}
	
	public Sheep(Point2D p, int cycleCount, int state){
		super(p);
		this.cycleCount = cycleCount;
		this.state = state;
		if(state == 2 && cycleCount != 0)
			throw new IllegalArgumentException("Error in Sheep loading arguments!");
	}
	
	public int getState() {
		return this.state;
	}
	
	private void moveOrEatVegie(){
		Direction dir = Direction.random();
		Point2D destination = new Point2D(dir.asVector().getX() + getPosition().getX(), dir.asVector().getY() + getPosition().getY());			
		boolean canMove = true;
	
		if (ImageMatrixGUI.getInstance().isWithinBounds(destination)) {
			for(ImageTile i : Farm.getInstance().images)
				if(i.getPosition().equals(destination) && i.getLayer() != 0) {
					canMove = false;
					if(i instanceof Vegetable){						
						feed();
						Farm.getInstance().imagesTEMP.add(Land.unplowedLand(destination));
						Farm.getInstance().imagesTEMP.remove(i);
					}
					break;
				}
			if(canMove)
				this.setPosition(destination.getX(), destination.getY());
		}
	}
	
	private void feed(){
		state = 0;
		cycleCount = 20;
	}
	
	@Override
	public void interact(){
		feed();
	}
	
	@Override
	public void update(){
		if(cycleCount > 0){
			cycleCount --;
			if(cycleCount <= 10){
				state = 1;
				moveOrEatVegie();
			}			
		}
		else if(cycleCount == 0){
			Farm.getInstance().imagesTEMP.add(new Sheep(this.getPosition(), 2));
			Farm.getInstance().imagesTEMP.remove(this);
		}
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		if(state == 2)
			return "famished_" + super.getName();
		else
			return super.getName();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName() + ";" + getPosition().getX() + ";" + getPosition().getY() + ";" + cycleCount + ";" + state;
	}
	
}
