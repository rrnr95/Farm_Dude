package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.ista.poo.farm.objects.Land;


public class Cabbage extends Vegetable {
	
	private int cycleCount = 0;
	private boolean isCared = false;

	public Cabbage(Point2D p, int state) {
		super(p);
		setState(state);
		// TODO Auto-generated constructor stub
	}
	
	public Cabbage(Point2D p, int state, int cycleCount){
		super(p);
		setState(state);
		this.cycleCount = cycleCount;
	}
	
	public Cabbage(Point2D p, int state, int cycleCount, boolean isCared){
		super(p, state);
		this.cycleCount = cycleCount;
		this.isCared = isCared;
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
			Farm.getInstance().imagesTEMP.add(new Cabbage(this.getPosition(),1,cycleCount));
			Farm.getInstance().imagesTEMP.remove(this);
		}
		else if(cycleCount >= 30 && getState() != 2){
			Farm.getInstance().imagesTEMP.add(new Cabbage(this.getPosition(),2,cycleCount));
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
				Farm.getInstance().addPoints(2);
			Farm.getInstance().imagesTEMP.add(Land.unplowedLand(this.getPosition()));
			Farm.getInstance().imagesTEMP.remove(this);		
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + ";" + cycleCount + ";" + isCared;
	}
}
