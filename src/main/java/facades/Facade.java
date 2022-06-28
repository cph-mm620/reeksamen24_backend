package facades;

import dtos.ManySideDTO;
import entities.ManySide;
import entities.OneSide;
import entities.OtherOneSide;

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

    public ManySideDTO create(ManySide newManySide){
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            ManySide ms = new ManySide(newManySide.getName());
            ms.setOtherManySides(newManySide.getOtherManySides());
            OneSide os = new OneSide(newManySide.getOneSide().getName());
            OtherOneSide oos = new OtherOneSide(newManySide.getOneSide().getOtherOneSide().getName());

            ms.setOneSide(os);
            os.setOtherOneSide(oos);
            em.persist(ms);

            em.getTransaction().commit();
            return new ManySideDTO(ms);
        }finally {
            em.close();
        }
    }

    public List<ManySideDTO> read(){
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<ManySide> query = em.createQuery("SELECT m FROM ManySide m", ManySide.class);
            List<ManySide> manySides = query.getResultList();
            List<ManySideDTO> msdtos = ManySideDTO.getDtos(manySides);
            return msdtos;
        }finally {
            em.close();
        }
    }

    public List<ManySideDTO> readWhere(String nameOfOneSide){
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<ManySide> query = em.createQuery("SELECT m FROM ManySide m WHERE m.oneSide.name = :oneSideName", ManySide.class);
            query.setParameter("oneSideName", nameOfOneSide);
            List<ManySide> manySides = query.getResultList();
            List<ManySideDTO> msdtos = ManySideDTO.getDtos(manySides);
            return msdtos;
        }finally {
            em.close();
        }
    }

    public ManySideDTO update(ManySide newManySide){
        EntityManager em = emf.createEntityManager();
        try{
            ManySide ms = em.find(ManySide.class, newManySide.getId());
            ms.setName(newManySide.getName());
            ms.setOtherManySides(newManySide.getOtherManySides());
            ms.setOneSide(newManySide.getOneSide());
            em.persist(ms);
            return new ManySideDTO(ms);
        }finally {
            em.close();
        }
    }

    public ManySideDTO delete(int id){
        EntityManager em = emf.createEntityManager();
        try{
            ManySide ms = em.find(ManySide.class, id);
            em.getTransaction().begin();
            em.remove(ms);
            em.getTransaction().commit();
            return new ManySideDTO(ms);
        }finally {
            em.close();
        }
    }

    public ManySideDTO getById(int id){
        EntityManager em = emf.createEntityManager();
        try{
            ManySide c = em.find(ManySide.class, id);
            return new ManySideDTO(c);
        }finally {
            em.close();
        }
    }
}
