package employees;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class ParkingPlaceDao {

    private EntityManagerFactory entityManagerFactory;

    public ParkingPlaceDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void saveParkingPlace(ParkingPlace parkingPlace) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(parkingPlace);
        em.getTransaction().commit();
        em.close();
    }

    public ParkingPlace findParkingPlaceByNumber(int number) {
        EntityManager em = entityManagerFactory.createEntityManager();
        ParkingPlace parkingPlace = em.createQuery("select p from ParkingPlace p where p.number = :number", ParkingPlace.class)
                .setParameter("number", number)
                .getSingleResult();
        em.close();
        return parkingPlace;
    }
}
