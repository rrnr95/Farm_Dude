package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.interfaces.Interactable;
import pt.iul.ista.poo.interfaces.Updatable;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Chicken extends Animal implements Interactable, Updatable {
	
	private boolean isCycleEven = true;
	private int cycleCount = 0;
	boolean canLayEgg, canMove;
	
	public Chicken(Point2D p) {
		// TODO Auto-generated constructor stub
		super(p);
	}
	
	public Chicken() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public Chicken(Point2D p, int cycleCount, boolean isCycleEven){
		super(p);
		this.cycleCount = cycleCount;
		this.isCycleEven = isCycleEven;
	}
	
	@Override
	public void interact() {
		// TODO Auto-generated method stub
		for(ImageTile i: Farm.getInstance().images)
			if(i.getPosition().equals(this.getPosition()) && i instanceof Egg)
				return;
		Farm.getInstance().addPoints(2);
		Farm.getInstance().imagesTEMP.remove(this);
		 
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		cycleCount++;
		
		if(isMultipleOfTen(cycleCount) && canMove){
			canLayEgg = true;
			
			for(ImageTile i: Farm.getInstance().images)
				if(i.getPosition().equals(this.getPosition()) && i instanceof Egg){
					canLayEgg = false;
					break; }
			if(canLayEgg)
				Farm.getInstance().imagesTEMP.add(new Egg(this.getPosition()));
		}
		else 
			canLayEgg = false;
			
		moveIfPossible();
	}
	
	private void moveOrEatTomato(){
		Direction dir = Direction.random();
		Point2D destination = new Point2D(dir.asVector().getX() + getPosition().getX(), dir.asVector().getY() + getPosition().getY());			
		canMove = true;
	
		if (ImageMatrixGUI.getInstance().isWithinBounds(destination) && isCycleEven == true) {
			for(ImageTile i : Farm.getInstance().images)
				if(i.getPosition().equals(destination) && i.getLayer() != 0) {
					
					canMove = false;
					
					if(i instanceof Tomato){						
						Farm.getInstance().imagesTEMP.add(Land.unplowedLand(destination));
						Farm.getInstance().imagesTEMP.remove(i);
					}
					
					break;
				}
			if(canMove)
				this.setPosition(destination.getX(), destination.getY());
		}
	}
	
	private final static boolean isMultipleOfTen(int cycleCount){
		if(cycleCount % 10 == 0) return true;
		else return false;
	}
	
	private final void moveIfPossible(){
		if(isCycleEven){
			isCycleEven = false;
		}
		else 
			isCycleEven = true;
		moveOrEatTomato();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + ";" + cycleCount + ";" + isCycleEven;
	}
	
	
}
