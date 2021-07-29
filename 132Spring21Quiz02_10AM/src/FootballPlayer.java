import java.util.ArrayList;

public class FootballPlayer extends Athlete {

	private Position position;
	private int superbowlRings;
	
	
	/* If you can't this constructor to compile, leave it empty, but DO NOT REMOVE 
	 * IT or you will get 0 points on the entire coding part of the quiz. */
	public FootballPlayer(String name, int number, Position position, int rings) {
		super(name,number);
		this.position=position;
		this.superbowlRings=rings;

		
	
		
	
		
	}
	
	/* If you can't get this method to compile, remove it. */
	public String toString() {
		return super.toString()+position+superbowlRings;   // remove this statement and implement it correctly
	}
	
	/* If you can't get this method to compile, remove it. */
	public static ArrayList<Position> method1(ArrayList<FootballPlayer> team) {
		ArrayList<Position>missing=new ArrayList<>();
		
		
		
		for(Position players:Position.values()) {
				missing.add(players);
		}
		for(Position players:Position.values()) {
			for(FootballPlayer count:team) {
				if(count.position==players) {
					missing.remove(count.position);
				}
				
			}
	}
		
		
		return missing;   // remove this statement and implement it correctly
	}
	
	
	
	
	
	
	
	
	/* Don't modify this method -- we need it for testing purposes */
	public void setPosition(Position p) {
		this.position = p;
	}
	
}
