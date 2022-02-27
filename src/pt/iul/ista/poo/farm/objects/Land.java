package pt.iul.ista.poo.farm.objects;

import java.util.Random;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.interfaces.Interactable;
import pt.iul.ista.poo.utils.Point2D;

public class Land extends FarmObject implements Interactable{

	private boolean plowed;
	private boolean hasVegie = false;
	private ImageTile Vegie = null;
	
	public Land(Point2D p, boolean plowed) {
		super(p);
		this.plowed = plowed;
	}
	
	public Land(Point2D p, boolean plowed, boolean hasVegie){
		super(p);
		this.plowed = plowed;
		this.hasVegie = hasVegie;
	}
	
	public boolean isPlowed(){
		return this.plowed;
	}
	
	public void plowState(boolean state){
		this.plowed = state;
	}
	
	public final static Land unplowedLand(Point2D position){
		return new Land(position, false, false);
	}
	
	@Override
	public void interact() {
		Point2D position = this.getPosition();
		for(ImageTile i: Farm.getInstance().images)
			if(position.equals(i.getPosition()) && i.getLayer() > 0)
				return;
		if(plowed == false){
			Farm.getInstance().imagesTEMP.add(new Land(position, true));
			Farm.getInstance().imagesTEMP.remove(this);
		}
		else{
			if(hasVegie)
				return;
			else{
				hasVegie = true;
				Random luckyVegie = new Random();
				int luckyVegieValue = luckyVegie.nextInt(2);
				if (luckyVegieValue == 0){
					Vegie = new Cabbage(position,0); 
					Farm.getInstance().imagesTEMP.add(Vegie);
				}
				else {
					Vegie = new Tomato(position,0); 
					Farm.getInstance().imagesTEMP.add(Vegie);
				}
			}
		}
	}
	
	@Override
	public String getName() {
		if(!plowed){
			return super.getName();
		}
		else{
			return "plowed";
		}
	}
	
	@Override
	public String toString() {
		return "land" + ";" + getPosition().getX() + ";" + getPosition().getY() + ";" + isPlowed() + ";" +  hasVegie;	
	}
}
