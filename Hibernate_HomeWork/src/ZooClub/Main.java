package ZooClub;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class Main {
	public static void menu() {
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		System.out.println("--------------------------------\n"
				+ "Enter number of menu:");
		System.out.println("1 - Add member to ZooClub"
				+ "\n2 - Add pet to some member of ZooClub"
				+ "\n3 - Expel pet from some member of ZooClub"
				+ "\n4 - Expel some member of ZooClub"
				+ "\n5 - Expel some pet from all members of ZooClub"
				+ "\n6 - View ZooClub" 
				+ "\n7 - Reset ZooClub to default list of members"
				+ "\n8 - Exit");
	}

	public static void main(String[] args) {
		ZooClubDao zoo = new ZooClubDao();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			while (true) {
				menu();
				switch (Integer.parseInt(reader.readLine())) {
				case 1: {
					zoo.addPerson();
					break;
				}
				case 2: {
					zoo.addPet();
					break;
				}
				case 3: {
					zoo.removePet();
					break;
				}
				case 4: {
					zoo.removePerson();
					break;
				}
				case 5: {
					zoo.removePetFromAllPersons();
					break;
				}
				case 6: {
					zoo.printZooClub();
					break;
				}
				case 7: {
					zoo.resetTablesZooClub();
					break;
				}
				case 8: {
					zoo.exit();
					break;
				}
				default:
					System.out.println("Sorry, you enter wrong number of menu.");
				}
				System.out.println();
			}
		} catch (Exception e) {
			System.err.println("Fatal error, please restart program!");
			System.exit(1);
		}
	}
}