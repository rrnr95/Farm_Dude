package pt.iul.ista.poo.farm.objects;


import pt.iul.ista.poo.interfaces.Interactable;
import pt.iul.ista.poo.interfaces.Updatable;
import pt.iul.ista.poo.utils.Point2D;

public class Animal extends FarmObject implements Interactable, Updatable {

	public Animal(Point2D p) {
		super(p);
		// TODO Auto-generated constructor stub
	}
	
	public Animal() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public int getLayer(){
		return 2;
	}

	@Override
	public void interact() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
