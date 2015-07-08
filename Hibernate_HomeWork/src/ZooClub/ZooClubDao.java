package ZooClub;

import java.util.*;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class ZooClubDao {
	public void resetTablesZooClub() {
		List<Animal> list1 = new ArrayList();
		List<Animal> list2 = new ArrayList();
		List<Animal> list3 = new ArrayList();
		List<Animal> list4 = new ArrayList();
		list1.add(new Animal("Dog", "Sharik"));
		list1.add(new Animal("Cat", "Murzik"));
		list2.add(new Animal("Cat", "Milka"));
		list2.add(new Animal("Fish", "Star"));
		list4.add(new Animal("Cat", "Baron"));
		
		Session session = null;
		try {
			session = HibernateUtils.getSessionFactory().openSession();
			session.beginTransaction();
			
			session.createQuery("DELETE FROM Animal").executeUpdate();
			session.createQuery("DELETE FROM Person").executeUpdate();
			session.save(new Person("Vasja", 23, list1));
			session.save(new Person("Ivan", 45, list2));
			session.save(new Person("Petro", 12, list3));
			session.save(new Person("Anton", 50, list4));
			
			session.getTransaction().commit();
		} finally {
			closeSession(session);
		}
	}

	public void addPerson() {
		System.out.println();
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter member name: ");
		String name = myUpperCase(sc.nextLine());
		if (namePersonIsAlreadyHere(name)) {
			System.out.println("The names of members can't be repeated.");
			return;
		}
		System.out.print("Age: ");
		int age = sc.nextInt();
		
		Session session = null;
		try {
			session = HibernateUtils.getSessionFactory().openSession();
			session.beginTransaction();
			
			session.save(new Person(name, age, new ArrayList<Animal>()));
			
			session.getTransaction().commit();
			System.out.println("You add new person to ZooClub.");
		} catch (Exception e) {
			System.out.println("Sorry - operation is failure.");
		} finally {
			closeSession(session);
		}
	}
	
	public void addPet() {
		System.out.println();
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter member name: ");
		String name = myUpperCase(sc.nextLine());
		if (!namePersonIsAlreadyHere(name)) {
			System.out.println("This member doesn't exist.");
			return;
		}
	
		Scanner sc1 = new Scanner(System.in);
		System.out.print("Enter pet type: ");
		String type = myUpperCase(sc.nextLine());
		Scanner sc2 = new Scanner(System.in);
		System.out.print("Enter pet name: ");
		String petName = myUpperCase(sc2.nextLine());

		Session session = null;
		try {
			session = HibernateUtils.getSessionFactory().openSession();
			session.beginTransaction();
			
			Criteria cr = session.createCriteria(Person.class);
			cr.add(Restrictions.eq("name", name));
			List<Person> list = cr.list();
			Person p = list.get(0);
			p.getAnimals().add(new Animal(type, petName));
			session.update(p);;
			
			session.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Sorry - operation is failure.");
		} finally {
			closeSession(session);
		}
			
	}
	
	public void removePet() {
		System.out.println();
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter member name: ");
		String name = myUpperCase(sc.nextLine());
		if (!namePersonIsAlreadyHere(name)) {
			System.out.println("This member doesn't exist.");
			return;
		}

		Session session = null;
		try {
			session = HibernateUtils.getSessionFactory().openSession();
			session.beginTransaction();
		
			Criteria cr = session.createCriteria(Person.class);
			cr.add(Restrictions.eq("name", name));
			List<Person> list = cr.list();
			Person p = list.get(0);
		
			Scanner sc1 = new Scanner(System.in);
			System.out.println("Enter pet name: ");
			String petName = sc1.nextLine();
			boolean myTrue = false;
			
			Iterator<Animal> iter = p.getAnimals().iterator();
			while (iter.hasNext()) {
				Animal animal = iter.next();
				if (petName.equalsIgnoreCase(animal.getName())) {
					iter.remove();
					myTrue = true;
				}
			}
			if (!myTrue) {
				System.out.println("Pet with this name doesn't exist in this person.");
				closeSession(session);
				return;
			}
		
			Criteria cr1 = session.createCriteria(Animal.class);
			cr1.add(Restrictions.eq("name", petName));
			List<Animal> list1 = cr1.list();
			if (!list1.isEmpty()) {
				for (Animal animal : list1) {
					session.delete(animal);
				}
			}
			
			session.update(p);
			session.getTransaction().commit();
		} finally {
			closeSession(session);
		}
	}
	
	public void removePerson() {
		System.out.println();
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter member name: ");
		String name = sc.nextLine();
		
		Session session = null;
		try {
			session = HibernateUtils.getSessionFactory().openSession();
			session.beginTransaction();
			
			Criteria cr = session.createCriteria(Person.class);
			cr.add(Restrictions.eq("name", name));
			List<Person> list = cr.list();
			if (list.isEmpty()) {
				System.out.println("This member doesn't exist.");
				closeSession(session);
				return;
			}
			session.delete(list.get(0));			
			session.getTransaction().commit();
		} finally {
			closeSession(session);
		}
		
	}
	
	public void removePetFromAllPersons() {
		System.out.println();
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter pet type: ");
		String petType = sc.nextLine();
		boolean myTrue = false;
		
		Session session = null;
		try {
			session = HibernateUtils.getSessionFactory().openSession();
			session.beginTransaction();
			
			Criteria cr = session.createCriteria(Animal.class);
			cr.add(Restrictions.eq("type", petType));
			List<Animal> list = cr.list();
			if (!list.isEmpty()) {
				for (Animal animal : list) {
					session.delete(animal);
				}
			}
			
			
			List<Person> persons = session.createCriteria(Person.class).list();
			
			Iterator<Person> personIter = persons.iterator();
			while (personIter.hasNext()) {
				Person p = personIter.next();
				Iterator<Animal> animalIter = p.getAnimals().iterator();
				while (animalIter.hasNext()) {
					Animal a = animalIter.next();
					if (petType.equalsIgnoreCase(a.getType())) {
						animalIter.remove();
						myTrue = true;
					}
				}
				session.update(p);
			}
			session.getTransaction().commit();
		} finally {
			closeSession(session);
		}

		if (myTrue) {
			System.out.println("From ZooClub expelled all " + myUpperCase(petType)
				+ "'s");
		} else {
			System.out.println("No member of the club has such a pet with this name.");
		}
	}
	
	public void printZooClub() {
		System.out.println();
		Session session = null;
		try {
			session = HibernateUtils.getSessionFactory().openSession();
			List<Person> persons = session.createCriteria(Person.class).list();
			
			Iterator<Person> personIter = persons.iterator();
			while (personIter.hasNext()) {
				Person p = personIter.next();
				System.out.print(p);
				
				if (p.getAnimals().isEmpty()) {
					System.out.println("Doesn't have any pets.");
				} else {
					System.out.println("Has such pets:");
					Iterator<Animal> animalIter = p.getAnimals().iterator();
					while (animalIter.hasNext()) {
						System.out.println(animalIter.next());
					}
				}
			}
		} finally {
			closeSession(session);
		}
	}
	
	public void exit() {
		HibernateUtils.shutdown();
		System.exit(1);
	}
	
	private boolean namePersonIsAlreadyHere(String name) {
		Session session = null;
		try {
			session = HibernateUtils.getSessionFactory().openSession();

			Criteria cr = session.createCriteria(Person.class);
			cr.add(Restrictions.eq("name", name));
			List<Person> list = cr.list();
			return !list.isEmpty();
		} finally {
			closeSession(session);
		}
	}
	
	private void closeSession(Session session) {
		if ((session != null) && (session.isOpen())) {
			session.close();
		}
	}

	private String myUpperCase(String s) {
		String s2 = s.substring(0, 1).toUpperCase();
		s = s.substring(1);
		return s2.concat(s);
	}
}