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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        Employee employee = new Employee("John Doe");
        employeeDao.save(employee);

        long id = employee.getId();

        Employee another = employeeDao.findById(id);
        assertEquals("John Doe", another.getName());
    }

    @Test
    public void testSaveAndListAll() {
        employeeDao.save(new Employee("John Doe"));
        employeeDao.save(new Employee("Jane Doe"));

        List<Employee> employees = employeeDao.listAll();
        assertEquals(List.of("Jane Doe", "John Doe"),
                employees.stream().map(Employee::getName).collect(Collectors.toList()));
    }

    @Test
    public void testChangeName() {
        Employee employee = new Employee("John Doe");
        employeeDao.save(employee);

        long id = employee.getId();

        employeeDao.changeName(id, "Jack Doe");

        Employee another = employeeDao.findById(id);
        assertEquals("Jack Doe", another.getName());
    }

    @Test
    public void testDelete() {
        Employee employee = new Employee("John Doe");
        employeeDao.save(employee);
        long id = employee.getId();
        employeeDao.delete(id);

        List<Employee> employees = employeeDao.listAll();
        assertTrue(employees.isEmpty());
    }

    @Test
    public void testIllegalId() {
        Employee employee = employeeDao.findById(12L);
        assertEquals(null, employee);
    }

    @Test
    public void testEmployeeWithAttributes() {
        employeeDao.save(new Employee("John Doe", Employee.EmployeeType.HALF_TIME,
                LocalDate.of(2000, 1,1)));
        Employee employee = employeeDao.listAll().get(0);
        assertEquals(LocalDate.of(2000,1,1), employee.getDateOfBirth());
    }
}
