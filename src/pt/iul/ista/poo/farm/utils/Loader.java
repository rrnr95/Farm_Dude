package pt.iul.ista.poo.farm.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.farm.objects.Cabbage;
import pt.iul.ista.poo.farm.objects.Chicken;
import pt.iul.ista.poo.farm.objects.Egg;
import pt.iul.ista.poo.farm.objects.Farmer;
import pt.iul.ista.poo.farm.objects.Land;
import pt.iul.ista.poo.farm.objects.Rock;
import pt.iul.ista.poo.farm.objects.Sheep;
import pt.iul.ista.poo.farm.objects.Tomato;
import pt.iul.ista.poo.utils.Point2D;


public final class Loader {

	private Scanner fileReader;
	private String fileName = "";
	private int x = -1, y = -1, points = -1, class_State = -1, class_CycleCount = -1 , tomato_CaredCounter = -1;
	private String class_Name = null;
	private boolean land_HasVegie, land_IsPlowed, vegetable_IsCared, chicken_IsCycleEven;
	private Point2D spawn = null;
	private Farmer farmer = null;
	

	public Loader(String fileName){
		this.fileName = fileName;
	}

	@SuppressWarnings("resource")
	public final void load(){
		try {
			
			fileReader = new Scanner(new File(fileName)).useDelimiter(";");

			x = fileReader.nextInt();
			y = fileReader.nextInt();
			
			if( (Farm.getInstance().getWidth() != x) || (Farm.getInstance().getHeight() != y) ){
				return;
			}
			else{

				Farm.getInstance().imagesTEMP.clear();

				fileReader.nextLine();

				points = fileReader.nextInt();
				Farm.getInstance().setPoints(points);
				fileReader.nextLine();

				while(fileReader.hasNextLine()){
					class_Name = fileReader.next();

					x = fileReader.nextInt();
					y = fileReader.nextInt();
					spawn = new Point2D(x, y);

					switch (class_Name) {

					case ("farmer"):{
						farmer = new Farmer(spawn);
						Farm.getInstance().imagesTEMP.add(farmer);
						Farm.getInstance().loadFarmer(farmer); break;
					}
					case ("rock"):{
						Farm.getInstance().imagesTEMP.add(new Rock(spawn)); break;
					}
					case ("egg"):{
						class_CycleCount = fileReader.nextInt();
						Farm.getInstance().imagesTEMP.add(new Egg(spawn, class_CycleCount)); break;
					}
					case ("chicken"):{
						class_CycleCount = fileReader.nextInt();
						chicken_IsCycleEven = fileReader.nextBoolean();
						Farm.getInstance().imagesTEMP.add(new Chicken(spawn, class_CycleCount, chicken_IsCycleEven)); break;				
					}
					case ("sheep"):
					case ("famished_sheep"):{
						class_CycleCount = fileReader.nextInt();
						class_State = fileReader.nextInt();
						Farm.getInstance().imagesTEMP.add(new Sheep(spawn, class_CycleCount, class_State)); break;
					}
					case ("cabbage"):
					case ("small_cabbage"):
					case ("bad_cabbage"): {
						class_State = fileReader.nextInt();
						class_CycleCount = fileReader.nextInt();
						vegetable_IsCared = fileReader.nextBoolean();
						Farm.getInstance().imagesTEMP.add(new Cabbage(spawn, class_State, class_CycleCount, vegetable_IsCared)); break;
					}
					case ("tomato"):
					case ("small_tomato"):
					case ("bad_tomato"):{
						class_State = fileReader.nextInt();
						class_CycleCount = fileReader.nextInt();
						vegetable_IsCared = fileReader.nextBoolean();
						tomato_CaredCounter = fileReader.nextInt();
						Farm.getInstance().imagesTEMP.add(new Tomato(spawn, class_State, class_CycleCount, vegetable_IsCared, tomato_CaredCounter)); break;
					}
					case ("land"):{
						land_IsPlowed = fileReader.nextBoolean();
						land_HasVegie = fileReader.nextBoolean();
						Farm.getInstance().imagesTEMP.add(new Land(spawn, land_IsPlowed, land_HasVegie)); break;
					}
					default:
						break;
					}
					fileReader.nextLine();
				}
			}

			fileReader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}
}
