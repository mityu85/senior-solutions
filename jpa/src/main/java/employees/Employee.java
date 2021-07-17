package employees;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
@IdClass(EmployeeId.class)
public class Employee {

    public enum EmployeeType {FULL_TIME, HALF_TIME}

//    @EmbeddedId
//    private EmployeeId employeeId;

    @Id
    private String depName;

    @Id
//    @GeneratedValue(strategy = GenerationType.TABLE)
//    @GeneratedValue(generator = "Emp_Gen")
//    @TableGenerator(name = "Emp_Gen", table = "emp_id_gen", pkColumnName = "gen_name", valueColumnName = "gen_val")
    private Long id;
    @Column(name = "emp_name", length = 200, nullable = false)
    private String name;
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private EmployeeType employeeType = EmployeeType.FULL_TIME;

    public Employee() {
    }

    public Employee(String name) {
        this.name = name;
    }

    public Employee(String depName, Long id, String name, EmployeeType employeeType, LocalDate dateOfBirth) {
        this.depName = depName;
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.employeeType = employeeType;
    }

    public Employee(String depName, Long id, String name) {
        this.depName = depName;
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
