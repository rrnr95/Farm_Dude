package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.ista.poo.farm.objects.Land;


public class Cabage extends Vegetable {
	
	private int cycleCount = 0;
	private boolean isCared = false;

	public Cabage(Point2D p, int state) {
		super(p);
		setState(state);
		// TODO Auto-generated constructor stub
	}
	
	public Cabage(Point2D p, int state, int cycleCount){
		super(p);
		setState(state);
		this.cycleCount = cycleCount;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(isCared == true){
			isCared = false;
		}
		else
			cycleCount++;
		if(cycleCount >= 10 && cycleCount < 30 && getState() != 1){
			Farm.getInstance().imagesTEMP.add(new Cabage(this.getPosition(),1,cycleCount));
			Farm.getInstance().imagesTEMP.remove(this);
		}
		else if(cycleCount >= 30 && getState() != 2){
			Farm.getInstance().imagesTEMP.add(new Cabage(this.getPosition(),2,cycleCount));
			Farm.getInstance().imagesTEMP.remove(this);
		}
	}
	
	public void Care(){
		cycleCount+= 2;
		isCared = true;
	}
	
	@Override
	public void interact() {
		// TODO Auto-generated method stub
		if(getState() == 0)
			Care();
		else{
			if(getState() == 1)
				Farm.getInstance().setPoints(Farm.getInstance().getPoints() + 2);
			Farm.getInstance().imagesTEMP.add(Land.landAfterHarvest(this.getPosition()));
			Farm.getInstance().imagesTEMP.remove(this);		
		}
	}
}