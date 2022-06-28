package facades;

import dtos.CarDTO;

import entities.Car;

import entities.Driver;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class Facade {
    private static Facade instance;
    private static EntityManagerFactory emf;

    private Facade() {
    }

    public static Facade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new Facade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public CarDTO create(Car newCar){
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Car c = new Car(newCar.getName());
            c.setRaces(newCar.getRaces());
            Driver d = new Driver(newCar.getDriver().getName());

            c.setDriver(d);
            em.persist(c);

            em.getTransaction().commit();
            return new CarDTO(c);
        }finally {
            em.close();
        }
    }

    public List<CarDTO> read(){
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Car> query = em.createQuery("SELECT c FROM Car c", Car.class);
            List<Car> cars = query.getResultList();
            List<CarDTO> cdtos = CarDTO.getDtos(cars);
            return cdtos;
        }finally {
            em.close();
        }
    }

    public List<CarDTO> readWhere(String nameOfDriver){
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Car> query = em.createQuery("SELECT c FROM Car c WHERE c.driver.name = :driverName", Car.class);
            query.setParameter("driverName", nameOfDriver);
            List<Car> cars = query.getResultList();
            List<CarDTO> cdtos = CarDTO.getDtos(cars);
            return cdtos;
        }finally {
            em.close();
        }
    }

    public CarDTO update(Car newCar){
        EntityManager em = emf.createEntityManager();
        try{
            Car c = em.find(Car.class, newCar.getId());
            c.setName(newCar.getName());
            c.setRaces(newCar.getRaces());
            c.setDriver(newCar.getDriver());
            em.persist(c);
            return new CarDTO(c);
        }finally {
            em.close();
        }
    }

    public CarDTO delete(int id){
        EntityManager em = emf.createEntityManager();
        try{
            Car c = em.find(Car.class, id);
            em.getTransaction().begin();
            em.remove(c);
            em.getTransaction().commit();
            return new CarDTO(c);
        }finally {
            em.close();
        }
    }

    public CarDTO getById(int id){
        EntityManager em = emf.createEntityManager();
        try{
            Car c = em.find(Car.class, id);
            return new CarDTO(c);
        }finally {
            em.close();
        }
    }
}
