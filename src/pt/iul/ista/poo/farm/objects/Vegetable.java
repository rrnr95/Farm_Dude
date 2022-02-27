package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.interfaces.Interactable;
import pt.iul.ista.poo.interfaces.Updatable;
import pt.iul.ista.poo.utils.Point2D;

public class Vegetable extends FarmObject implements Updatable, Interactable{
	
	private int state = 0; /* 0 - growing
							  1 - good
							  2 - bad	
							*/

	public Vegetable(Point2D p) {
		super(p);
	}
	
	public Vegetable(Point2D p, int state){
		super(p);
		this.state = state;
	}
	
	public int getLayer(){
		return 1;
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	public int getState(){
		return state;
	}

	@Override
	public void interact() {
	}

	@Override
	public void update() {
	}	
	
	@Override
	public String getName() {
		if(this.state == 0)
			return "small_" + super.getName();
		else if(this.state == 1)
			return super.getName();
		else
			return "bad_" + super.getName();
	}
	
	@Override
	public String toString() {
		return super.toString() + ";" + getState();
	}
	
}
