package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.interfaces.Interactable;
import pt.iul.ista.poo.interfaces.Updatable;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Egg extends FarmObject implements Interactable, Updatable{
	
	private int cycleCount = 0;
	
	public Egg(Point2D p) {
		// TODO Auto-generated constructor stub
		super(p);
	}	
	
	public Egg(Point2D p, int cycleCount) {
		// TODO Auto-generated constructor stub
		super(p);
		this.cycleCount = cycleCount;
	}
	
	@Override
	public int getLayer() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		cycleCount++;
	
		if(timeTo_BirthOrDie()){
			chickenBirth();
			Farm.getInstance().imagesTEMP.remove(this);
		}
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub
			Farm.getInstance().addPoints(1);
			Farm.getInstance().imagesTEMP.remove(this);
	}
	
	private void chickenBirth(){
		Point2D spawnPoint;		
		
		if(isNotOcupied(Direction.UP)){
			spawnPoint = this.getPosition().plus(Direction.UP.asVector());
			Farm.getInstance().imagesTEMP.add(new Chicken(spawnPoint));
		}
		
		else if(isNotOcupied(Direction.RIGHT)){
			spawnPoint = this.getPosition().plus(Direction.RIGHT.asVector());
			Farm.getInstance().imagesTEMP.add(new Chicken(spawnPoint));
		}

		else if(isNotOcupied(Direction.DOWN)){
			spawnPoint = this.getPosition().plus(Direction.DOWN.asVector());
			Farm.getInstance().imagesTEMP.add(new Chicken(spawnPoint));
		}

		else if(isNotOcupied(Direction.LEFT)){
			spawnPoint = this.getPosition().plus(Direction.LEFT.asVector());
			Farm.getInstance().imagesTEMP.add(new Chicken(spawnPoint));
		}
		else
			return;
	}
	
	private boolean isNotOcupied(Direction d){
		Point2D position = this.getPosition().plus(d.asVector());
		for(ImageTile i: Farm.getInstance().images)
			if(i.getPosition().equals(position) && i.getLayer() > 0 && ImageMatrixGUI.getInstance().isWithinBounds(position))
				return false;
		
		return true;
				
	}
	
	private final boolean timeTo_BirthOrDie(){
		return this.cycleCount == 20;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + ";" + cycleCount;
	}
}
