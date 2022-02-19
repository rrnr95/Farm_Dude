package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.utils.Point2D;

public class Tomato extends Vegetable {
	
	private int cycleCount = 0;
	private boolean isCared = true;
	int caredCounter = 10;

	public Tomato(Point2D p, int state) {
		super(p);
		setState(state);
		// TODO Auto-generated constructor stub
	}
	
	public Tomato(Point2D p, int state, int cycleCount, int caredCounter){
		super(p, state);
		this.cycleCount = cycleCount;
		this.caredCounter = caredCounter;
	}
	
	public Tomato(Point2D p, int state, int cycleCount, boolean isCared, int caredCounter){
		super(p, state);
		this.cycleCount = cycleCount;
		this.isCared = isCared;
		this.caredCounter = caredCounter;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub	
		if(getState() == 0){
			if(isCared == true){
				cycleCount++;
				caredCounter--;
				if (caredCounter == 0)
					isCared = false;
				else
					isCared = true;
			}	
		}
		else
			cycleCount++;
		if(cycleCount >= 15 && cycleCount < 25 && getState() != 1){
			Farm.getInstance().imagesTEMP.add(new Tomato(this.getPosition(), 1, cycleCount, caredCounter));
			Farm.getInstance().imagesTEMP.remove(this);
		}
		else if(cycleCount >= 25 && getState() != 2){
			Farm.getInstance().imagesTEMP.add(new Tomato(this.getPosition(), 2, cycleCount, caredCounter));
			Farm.getInstance().imagesTEMP.remove(this);
		}
	}
	
	@Override
	public void interact() {
		// TODO Auto-generated method stub
		if(getState() == 0){
			isCared = true;
			caredCounter = 10;
		}
		else{
			if(getState() == 1)
				Farm.getInstance().addPoints(3);
			Farm.getInstance().imagesTEMP.add(Land.unplowedLand(this.getPosition()));
			Farm.getInstance().imagesTEMP.remove(this);		
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + ";" + cycleCount + ";" + isCared + ";" + caredCounter;
	}

}
