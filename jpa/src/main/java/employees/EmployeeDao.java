package employees;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class EmployeeDao {

    private EntityManagerFactory entityManagerFactory;

    public EmployeeDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void save(Employee employee) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();
        em.close();
    }

    public Employee findById(String depName, Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Employee employee = em.find(Employee.class, new EmployeeId(depName, id));
        em.close();
        return employee;

    }

    public List<Employee> listAll() {
        EntityManager em = entityManagerFactory.createEntityManager();
        List<Employee> employees = em.createQuery("select e from Employee e order by e.name", Employee.class)
                .getResultList();
        em.close();
        return employees;
    }

    public void updateEmployeeNames() {
        EntityManager em = entityManagerFactory.createEntityManager();

        List<Employee> employees = em.createQuery("select e from Employee e order by e.name", Employee.class)
                .getResultList();

        em.getTransaction().begin();
        for (Employee employee: employees) {
            employee.setName(employee.getName() + " ***");
            System.out.println("Updated");
            em.flush();
        }
        em.getTransaction().commit();

        em.close();
    }

    public void changeName(String depName, Long id, String name) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Employee employee = em.find(Employee.class, new EmployeeId(depName, id));
        employee.setName(name);
        em.getTransaction().commit();
        em.close();
    }

    public void updateEmployee(Employee employee) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Employee merged = em.merge(employee);

        merged.setName("***" + employee.getName());

        em.getTransaction().commit();
        em.close();
    }

    public void delete(String depName, Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Employee employee = em.getReference(Employee.class, new EmployeeId(depName, id));
        em.remove(employee);
        em.getTransaction().commit();
        em.close();
    }

    public Employee findEmployeeByIdWithVacations(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Employee employee= em.createQuery("select e from Employee e join fetch e.vacationBookings where e.id = :id", Employee.class)
                .setParameter("id", id)
                .getSingleResult();
        em.close();
        return employee;
    }

    public Employee findEmployeeByIdWithPhoneNumbers(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Employee employee = em.createQuery("select e from Employee e join fetch e.phoneNumbers where e.id = :id", Employee.class)
                .setParameter("id", id)
                .getSingleResult();
        return employee;
    }

    public void addPhoneNumber(String depName, long id, PhoneNumber phoneNumber) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        //Employee employee = em.find(Employee.class, new EmployeeId(depName, id));
        Employee employee = em.getReference(Employee.class, new EmployeeId(depName, id));
        phoneNumber.setEmployee(employee);
        em.persist(phoneNumber);

        em.getTransaction().commit();
        em.close();

    }

    public Employee finEmployeeByIdWithPhoneNumbers(long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Employee employee = em.createQuery("select e from Employee e join fetch e.phoneNumbers where e.id = :id", Employee.class)
                .setParameter("id", id)
                .getSingleResult();
        em.close();
        return employee;
    }

    public Employee findEmployeeByName(String name) {
        EntityManager em = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Employee> c = cb.createQuery(Employee.class);
        Root<Employee> emp = c.from(Employee.class);
        c.select(emp).where(cb.equal(emp.get("name"), name));
        Employee employee = em.createQuery(c).getSingleResult();
        em.close();
        return employee;
    }

    public List<Employee> listEmployees(int start, int maxResult) {
        EntityManager em = entityManagerFactory.createEntityManager();
        List<Employee> employees = em.createNamedQuery("listEmployees", Employee.class)
                .setFirstResult(start)
                .setMaxResults(maxResult)
                .getResultList();
        em.close();
        return employees;
    }

    public int findParkingPlaceNumberByEmployeeName(String name) {
        EntityManager em = entityManagerFactory.createEntityManager();
        int i = em.createQuery("select p.number from Employee e join e.parkingPlace p where e.name = :name", Integer.class)
                .setParameter("name", name)
                .getSingleResult();
        em.close();
        return i;
    }

    public List<Object[]> listEntityBaseData() {
        EntityManager em = entityManagerFactory.createEntityManager();
        List<Object[]> baseData = em.createQuery("select e.id, e.name from Employee e")
                .getResultList();
        em.close();
        return baseData;
    }

    public List<EmpBaseDataDto> listEmployeeDto() {
        EntityManager em = entityManagerFactory.createEntityManager();
        List<EmpBaseDataDto> data = em.createQuery("select new employees.EmpBaseDataDto(e.id, e.name) from Employee e order by e.name")
                .getResultList();

        em.close();
        return data;
    }
}