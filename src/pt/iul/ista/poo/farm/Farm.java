package pt.iul.ista.poo.farm;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;




import pt.iul.ista.poo.farm.objects.Animal;
import pt.iul.ista.poo.farm.objects.Chicken;

import pt.iul.ista.poo.farm.objects.Farmer;
import pt.iul.ista.poo.farm.objects.Land;
import pt.iul.ista.poo.farm.objects.Rock;
import pt.iul.ista.poo.farm.objects.Sheep;
import pt.iul.ista.poo.farm.objects.Vegetable;
import pt.iul.ista.poo.farm.utils.Loader;
import pt.iul.ista.poo.farm.utils.Saver;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.interfaces.Interactable;
import pt.iul.ista.poo.interfaces.Updatable;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Farm implements Observer {

	public List<ImageTile> images;
	public List<ImageTile> imagesTEMP;
	private boolean spaceOn = false;

	private static final String SAVE_FNAME = "config/savedGame";

	private static final int MIN_X = 5;
	private static final int MIN_Y = 5;

	private static Farm INSTANCE;
	
	private static Saver instanceSaver = null;
	private static Loader instanceLoader = null;

	private int max_x;
	private int max_y;
	private int points;
	private Farmer farmer;
	private Animal sheep, sheep_1, chicken, chicken_1;
	

	private Farm(int max_x, int max_y) {	
		if (max_x < MIN_X || max_y < MIN_Y)
			throw new IllegalArgumentException();

		this.max_x = max_x;
		this.max_y = max_y;

		INSTANCE = this;

		ImageMatrixGUI.setSize(max_x, max_y);

		// N�o usar ImageMatrixGUI antes de inicializar o tamanho		
		// TODO

		loadScenario();

	}

	private void registerAll() {
		// TODO
		images = new ArrayList<ImageTile>();
		// images.addAll(...);
		farmer = new Farmer(new Point2D(0,0));
		images.add(farmer);
		sheep = new Sheep();
		sheep_1 = new Sheep();
		chicken = new Chicken();
		chicken_1 = new Chicken();
		while(sheep_1.getPosition().equals(sheep.getPosition()))
			sheep_1 = new Sheep();
		while(chicken.getPosition().equals(sheep.getPosition()) || chicken.getPosition().equals(sheep_1.getPosition()))
			chicken = new Chicken();
		while(chicken_1.getPosition().equals(sheep.getPosition()) || chicken_1.getPosition().equals(sheep_1.getPosition()) || chicken_1.getPosition().equals(chicken.getPosition()))
			chicken_1 = new Chicken();
		images.add(sheep);
		images.add(sheep_1);
		images.add(chicken);
		images.add(chicken_1);
		
		for(int x = 0; x < max_x; x++)
			for(int y = 0; y < max_y; y++){
				if(isRockFloor() && x != 0 && y != 0)
					images.add(new Rock(new Point2D(x,y)));			
				else		
					images.add(new Land(new Point2D(x, y), false, false));
			}
		imagesTEMP = new ArrayList<ImageTile>(images);
		ImageMatrixGUI.getInstance().addImages(images);
		ImageMatrixGUI.getInstance().update();
	}



	private void loadScenario() {
		// TODO
		registerAll();
	}

	@Override
	public void update(Observable gui, Object a) {
		ImageMatrixGUI.getInstance().clearImages();
		ImageMatrixGUI.getInstance().addImages(images);
		System.out.println("Update sent " + a);
		// TODO 
		
		if (a instanceof Integer) {
			int key = (Integer) a;
			if (Direction.isDirection(key) && spaceOn == false) {
				System.out.println("Update is a Direction " + Direction.directionFor(key));
				farmer.moveIn(Direction.directionFor(key));
				if(areAllSheepFed())
					points++;
				
				for(ImageTile tile: images){
					if(tile instanceof Updatable){
						((Updatable) tile).update();
					}
				}
			}
			else if(key == KeyEvent.VK_SPACE){
				if(spaceOn)
					spaceOn = false;
				else 
					spaceOn = true;
				System.out.println("Update is Space Key");				
			}
			else if (Direction.isDirection(key) && spaceOn == true) {
				for(ImageTile tile: images){
					if(tile instanceof Interactable && tile.getPosition().equals(new Point2D(farmer.getPosition().getX() + Direction.directionFor(key).asVector().getX(), farmer.getPosition().getY() + Direction.directionFor(key).asVector().getY()))){
						((Interactable) tile).interact();
					}
					if(tile instanceof Vegetable)
						((Updatable) tile).update();
				}
				spaceOn = false;
			}
			else if (key == KeyEvent.VK_S){
				instanceSaver = new Saver(SAVE_FNAME);
				instanceSaver.save();
			}
			else if (key == KeyEvent.VK_L){
				instanceLoader = new Loader(SAVE_FNAME);
				instanceLoader.load();
			}
				
		}
		
		if(!spaceOn)
			ImageMatrixGUI.getInstance().setStatusMessage("Points: " + points);
		else
			ImageMatrixGUI.getInstance().setStatusMessage("Points: " + points + "                                                  Choose direction to interact");
		
		images = new ArrayList<>(imagesTEMP);
		ImageMatrixGUI.getInstance().clearImages();
		ImageMatrixGUI.getInstance().addImages(images);
		ImageMatrixGUI.getInstance().update();
	}
	
	public boolean isSpaceOn(){
		return this.spaceOn;
	}
	
	public int getPoints(){
		return this.points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public void addPoints(int addedPoints){
		this.points += addedPoints;
	}
	
	public void loadFarmer(Farmer farmer){
		this.farmer = farmer;
	}
	
	private final static boolean areAllSheepFed(){
		for(ImageTile i: Farm.getInstance().images)
			if(i instanceof Sheep && ((Sheep) i).getState() != 0)
				return false;
		return true;
	}
	
	private final static boolean isRockFloor(){
		Random chooser = new Random();
		int value = chooser.nextInt(10);
		if(value == 0)
			return true;
		else
			return false;
	}
	
	public int getWidth(){
		return max_x;
	}
	
	public int getHeight(){
		return max_y;
	}

	// N�o precisa de alterar nada a partir deste ponto
	private void play() {
		ImageMatrixGUI.getInstance().addObserver(this);
		ImageMatrixGUI.getInstance().go();
	}

	public static Farm getInstance() {
		assert (INSTANCE != null);
		return INSTANCE;
	}
	public static void main(String[] args) {
		Farm f = new Farm(7, 9);
		f.play();
	}

	public void remove(ImageTile image) {
		int index = 0;
		for(int i = 0; i < imagesTEMP.size(); i++){
			if(imagesTEMP.get(i).equals(image)){
				index = i;
				break;
			}
		}
		imagesTEMP.remove(index);
		System.out.println(images.size());
		System.out.println(imagesTEMP.size());
	}
}
