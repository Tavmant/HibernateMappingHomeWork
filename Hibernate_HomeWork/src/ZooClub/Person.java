package ZooClub;

import java.util.List;

public class Person {
	private int id;
	private String name;
	private int age;
	private List<Animal> animals;
	
	Person() {
		
	}
	
	public Person(String name, int age, List<Animal> animals) {
		this.id = 0;
		this.name = name;
		this.age = age;
		this.animals = animals;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public List<Animal> getAnimals() {
		return animals;
	}

	public void setAnimals(List<Animal> animals) {
		this.animals = animals;
	}

	@Override
	public String toString() {
		return "Member of club: Name= " + name + ", Age= " + age + ". ";
	}
}