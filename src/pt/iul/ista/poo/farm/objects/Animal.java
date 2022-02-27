package pt.iul.ista.poo.farm.objects;


import pt.iul.ista.poo.interfaces.Interactable;
import pt.iul.ista.poo.interfaces.Updatable;
import pt.iul.ista.poo.utils.Point2D;

public class Animal extends FarmObject implements Interactable, Updatable {

	public Animal(Point2D p) {super(p);}
	
	public Animal() {super();}
	
	public int getLayer(){
		return 2;
	}

	@Override
	public void interact() {}

	@Override
	public void update() {}

}
