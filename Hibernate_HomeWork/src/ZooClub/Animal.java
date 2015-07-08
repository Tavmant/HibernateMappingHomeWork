package ZooClub;

public class Animal {
	private int id;
	private String type;
	private String name;
	private Person owner;
	
	Animal() {
		
	}
	
	public Animal(String type, String name) {
		this.id = 0;
		this.type = type;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "            " + type + " with name " + name;
	}
}