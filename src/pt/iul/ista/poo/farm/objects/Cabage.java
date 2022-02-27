package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.utils.Point2D;


public class Cabage extends Vegetable {
	
	private int cycleCount = 0;
	private boolean isCared = false;

	public Cabage(Point2D p, int state) {
		super(p);
		setState(state);
	}
	
	public Cabage(Point2D p, int state, int cycleCount){
		super(p);
		setState(state);
		this.cycleCount = cycleCount;
	}
	
	@Override
	public void update() {
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
	
	public void care(){
		cycleCount+= 2;
		isCared = true;
	}
	
	@Override
	public void interact() {
		// TODO Auto-generated method stub
		if(getState() == 0)
			care();
		else{
			if(getState() == 1)
				Farm.getInstance().setPoints(Farm.getInstance().getPoints() + 2);
			Farm.getInstance().imagesTEMP.add(Land.unplowedLand(this.getPosition()));
			Farm.getInstance().imagesTEMP.remove(this);		
		}
	}
}
