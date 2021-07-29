
public class Athlete {

	
	
	/*****************************************************************
	 * STUDENTS: DO NOT MODIFY THIS CLASS IN ANY WAY!!               *
	 * In particular, you may not add a getter for the "name" field, *
	 * and you may not add any setters.                              * 
	 *                                                               *
	 * MODIFYING THIS CLASS WILL BE CONSIDERED CHEATING.             *
	 *****************************************************************/
	
	
	
	private String name;
	private int number;
	
	public Athlete(String name, int number) {
		this.name = name;
		this.number = number;
	}
	
	public Athlete() {
		name = "Unknown";
		number = 0;
	}
	
	public int getNumber() {
		return number;
	}
	
	/* Returns a String formatted like:
	 * "#12 Brady"
	 */
	public String toString() {
		return "#" + number + " " + name;
	}
	
}
