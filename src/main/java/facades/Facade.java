package facades;

import dtos.CarDTO;

import dtos.DriverDTO;
import entities.Car;

import entities.Driver;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
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
            Car car = new Car(newCar.getName(), newCar.getBrand(), newCar.getColor(), newCar.getSponsor(), newCar.getYear());
            car.setDriver(newCar.getDriver());
            car.setRaces(newCar.getRaces());
            em.persist(car);

            em.getTransaction().commit();
            return new CarDTO(car);
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

    public List<DriverDTO> readWhere(){
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Driver> query = em.createQuery("SELECT d FROM Driver d",  Driver.class);
            List<Driver> drivers = query.getResultList();
            List<Driver> drivers1 = new ArrayList<>();
//            for (Driver driver : drivers) {
//                if (driver.getId() == id) {
//                    drivers1.add(driver);
//                    System.out.println(driver);
//                }

            //}
            List<DriverDTO> ddtos = DriverDTO.getDtos(drivers);
            return ddtos;
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

    public DriverDTO getByDriverId(int id){
        EntityManager em = emf.createEntityManager();
        try{
            Driver d = em.find(Driver.class, id);
            return new DriverDTO(d);
        }finally {
            em.close();
        }
    }

    public List<DriverDTO> getDrivers() {
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Driver> query = em.createQuery("SELECT d FROM Driver d", Driver.class);
            List<Driver> drivers = query.getResultList();
            List<DriverDTO> ddtos = DriverDTO.getDtos(drivers);
            return ddtos;
        }finally {
            em.close();
        }
    }
}
