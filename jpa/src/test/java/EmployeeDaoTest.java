import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import employees.*;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeDaoTest {

    private EmployeeDao employeeDao;
    private ParkingPlaceDao parkingPlaceDao;

    @BeforeEach
    public void init() {
//        MysqlDataSource dataSource = new MysqlDataSource();
//        dataSource.setURL("jdbc:mysql://localhost:3306/employees");
//        dataSource.setUser("employees");
//        dataSource.setPassword("employees");
//
//        Flyway flyway = new Flyway();
//        flyway.setDataSource(dataSource);
//        flyway.clean();
//        flyway.migrate();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("pu");
        employeeDao = new EmployeeDao(entityManagerFactory);
        parkingPlaceDao = new ParkingPlaceDao(entityManagerFactory);
    }

    @Test
    public void testSaveAndFindById() {
        Employee employee = new Employee("x", 1L, "John Doe");
        employeeDao.save(employee);

        long id = employee.getId();
        String depName = employee.getDepName();

        Employee another = employeeDao.findById(depName, id);
        assertEquals("John Doe", another.getName());
    }

    @Test
    public void testSaveAndListAll() {
        employeeDao.save(new Employee("x", 1L, "John Doe"));
        employeeDao.save(new Employee("x", 2L,"Jane Doe"));

        List<Employee> employees = employeeDao.listAll();
        assertEquals(List.of("Jane Doe", "John Doe"),
                employees.stream().map(Employee::getName).collect(Collectors.toList()));
    }

    @Test
    public void testChangeName() {
        Employee employee = new Employee("x", 1L,"John Doe");
        employeeDao.save(employee);

        long id = employee.getId();
        String depName = employee.getDepName();

        employeeDao.changeName(depName, id, "Jack Doe");

        Employee another = employeeDao.findById(depName, id);
        assertEquals("Jack Doe", another.getName());
    }

    @Test
    public void testDelete() {
        Employee employee = new Employee("x", 1L,"John Doe");
        employeeDao.save(employee);
        long id = employee.getId();
        String depName = employee.getDepName();
        employeeDao.delete(depName, id);

        List<Employee> employees = employeeDao.listAll();
        assertTrue(employees.isEmpty());
    }

    @Test
    public void testIllegalId() {
        Employee employee = employeeDao.findById("x", 12L);
        assertEquals(null, employee);
    }

    @Test
    public void testEmployeeWithAttributes() {
        for (long i = 0; i < 10; i++) {
            employeeDao.save(new Employee("x", i, "John Doe", Employee.EmployeeType.HALF_TIME,
                    LocalDate.of(2000, 1,1)));
        }
        Employee employee = employeeDao.listAll().get(0);
        assertEquals(LocalDate.of(2000,1,1), employee.getDateOfBirth());
    }

    @Test
    public void testSaveEmployeeThenChangeState() {
        Employee employee = new Employee("x", 1L, "John Doe");
        employeeDao.save(employee);

        employee.setName("Jack Doe");
        Employee modifiedEmployee = employeeDao.findById(employee.getDepName(), employee.getId());

        assertEquals("John Doe", modifiedEmployee.getName());
        assertFalse(employee == modifiedEmployee);
    }

    @Test
    public void testMerge() {
        Employee employee = new Employee("x", 1L, "John Doe");
        employeeDao.save(employee);

        employee.setName("Jack Doe");
        employeeDao.updateEmployee(employee);

        Employee modifiedEmployee = employeeDao.findById(employee.getDepName(), employee.getId());
        assertEquals("***Jack Doe", modifiedEmployee.getName());
    }

    @Test
    public void testFlush() {
        for (long i = 0; i < 10; i++) {
            employeeDao.save(new Employee("x", i, "John Doe" + i, Employee.EmployeeType.HALF_TIME,
                    LocalDate.of(2000, 1,1)));
        }
        employeeDao.updateEmployeeNames();
    }

    @Test
    public void testVacation() {
        Employee employee = new Employee("x", 1L, "John Doe");
        employee.setVacationBookings(Set.of(new VacationEntry(LocalDate.of(2021, 8, 16), 4),
                new VacationEntry(LocalDate.of(2021, 11, 2), 2)));
        employeeDao.save(employee);

        Employee anotherEmployee = employeeDao.findEmployeeByIdWithVacations(employee.getId());
        System.out.println(anotherEmployee.getVacationBookings());
        assertEquals(2, anotherEmployee.getVacationBookings().size());
    }

//    @Test
//    public void testPhoneNumber() {
//        Employee employee = new Employee("x", 1L, "John Doe");
//        employee.setPhoneNumbers(Map.of("home", "1234", "work", "4321"));
//
//        employeeDao.save(employee);
//        Employee anotherEmployee = employeeDao.findEmployeeByIdWithPhoneNumbers(employee.getId());
//        assertEquals("1234", anotherEmployee.getPhoneNumbers().get("home"));
//        assertEquals("4321", anotherEmployee.getPhoneNumbers().get("work"));
//    }

    @Test
    public void testPhoneNumber() {
        PhoneNumber phoneNumberHome = new PhoneNumber("home", "1234");
        PhoneNumber phoneNumberWork = new PhoneNumber("work", "4321");

        Employee employee = new Employee("x", 1L, "John Doe");
        //employee.setPhoneNumbers(Set.of(phoneNumberHome, phoneNumberWork));
        employee.addPhoneNumber(phoneNumberWork);
        employee.addPhoneNumber(phoneNumberHome);
        employeeDao.save(employee);

        Employee anotherEmployee = employeeDao.finEmployeeByIdWithPhoneNumbers(employee.getId());
        //System.out.println(anotherEmployee.getPhoneNumbers());
        assertEquals(2, anotherEmployee.getPhoneNumbers().size());
        assertEquals("work", anotherEmployee.getPhoneNumbers().get(0).getType());
    }

    @Test
    public void testAddPhoneNumber() {
        Employee employee = new Employee("x", 1L, "John Doe");
        employeeDao.save(employee);

        employeeDao.addPhoneNumber(employee.getDepName(), employee.getId(), new PhoneNumber("home", "1111"));

        Employee anotherEmployee = employeeDao.finEmployeeByIdWithPhoneNumbers(employee.getId());
        assertEquals(1, anotherEmployee.getPhoneNumbers().size());
    }

    @Test
    public void testRemove() {
        Employee employee = new Employee("x", 1L, "John Doe");

        employee.addPhoneNumber(new PhoneNumber("home", "1111"));
        employee.addPhoneNumber(new PhoneNumber("work", "2222"));

        employeeDao.save(employee);

        employeeDao.delete(employee.getDepName(), employee.getId());
    }

    @Test
    public void testFindEmployeeByName() {
        employeeDao.save(new Employee("x", 1L, "John Doe"));

        Employee employee = employeeDao.findEmployeeByName("John Doe");
        assertEquals("John Doe", employee.getName());
    }

    @Test
    public void testPaging() {
        for (long i = 100; i < 300; i++) {
            Employee employee = new Employee("x", i, "John Doe" + i);
            employeeDao.save(employee);
        }
        List<Employee> employees = employeeDao.listEmployees(50, 20);
        assertEquals("John Doe150", employees.get(0).getName());
        assertEquals(20, employees.size());
    }

    @Test
    public void testFindNumber() {
        Employee employee = new Employee("x", 1L, "John Doe");
        ParkingPlace parkingPlace = new ParkingPlace(101);
        parkingPlaceDao.saveParkingPlace(parkingPlace);

        employee.setParkingPlace(parkingPlace);
        employeeDao.save(employee);
        int number = employeeDao.findParkingPlaceNumberByEmployeeName("John Doe");
        assertEquals(101, number);
    }

    @Test
    public void testBaseData() {
        Employee employee = new Employee("x", 1L, "John Doe");
        employeeDao.save(employee);

        List<Object[]> data = employeeDao.listEntityBaseData();
        assertEquals(1, data.size());
        assertEquals("John Doe", data.get(0)[1]);
    }

    @Test
    public void testDto() {
        employeeDao.save(new Employee("x", 1L, "John Doe"));
        employeeDao.save(new Employee("x", 2L, "Jane Doe"));

        List<EmpBaseDataDto> data = employeeDao.listEmployeeDto();
        //System.out.println(data);

        assertEquals(2, data.size());
        assertEquals("Jane Doe", data.get(0).getName());
    }
}