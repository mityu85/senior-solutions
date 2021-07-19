import employees.Employee;
import employees.EmployeeDao;
import employees.ParkingPlace;
import employees.ParkingPlaceDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParkingPlaceDaoTest {

    private ParkingPlaceDao parkingPlaceDao;
    private EmployeeDao employeeDao;

    @BeforeEach
    public void init() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu");
        parkingPlaceDao = new ParkingPlaceDao(factory);
        employeeDao = new EmployeeDao(factory);
    }

    @Test
    public void testSave() {
        parkingPlaceDao.saveParkingPlace(new ParkingPlace(100));

        ParkingPlace parkingPlace = parkingPlaceDao.findParkingPlaceByNumber(100);
        assertEquals(100, parkingPlace.getNumber());
    }

    @Test
    public void testSaveEmployeeWithParkingPlace() {
        ParkingPlace parkingPlace = new ParkingPlace(100);
        parkingPlaceDao.saveParkingPlace(parkingPlace);

        Employee employee = new Employee("x", 1L, "John Doe");
        employee.setParkingPlace(parkingPlace);
        employeeDao.save(employee);

        Employee anotherEmployee = employeeDao.findById(employee.getDepName(), employee.getId());
        assertEquals(100, anotherEmployee.getParkingPlace().getNumber());

    }

}
