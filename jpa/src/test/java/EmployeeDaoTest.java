import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import employees.Employee;
import employees.EmployeeDao;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeDaoTest {

    private EmployeeDao employeeDao;

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
}