package employees;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

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

    @ElementCollection
    private Set<VacationEntry> vacationBookings;

//    @ElementCollection
//    @CollectionTable(name = "phone_numbers")
//    @MapKeyColumn(name = "phone_type")
//    @Column(name = "phone_number")
//    private Map<String, String> phoneNumbers;

    @OneToOne
    private ParkingPlace parkingPlace;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, mappedBy = "employee")
    @OrderColumn(name = "pos")
    private List<PhoneNumber> phoneNumbers;

    @PostPersist
    public void debugPersist() {
        System.out.println(name + " " + id);
    }

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

    public void addPhoneNumber(PhoneNumber phoneNumber) {
        if (phoneNumbers == null) {
            phoneNumbers = new ArrayList<>();
        }
        phoneNumbers.add(phoneNumber);
        phoneNumber.setEmployee(this);
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

    public Set<VacationEntry> getVacationBookings() {
        return vacationBookings;
    }

    public void setVacationBookings(Set<VacationEntry> vacationBookings) {
        this.vacationBookings = vacationBookings;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    //    public Map<String, String> getPhoneNumbers() {
//        return phoneNumbers;
//    }
//
//    public void setPhoneNumbers(Map<String, String> phoneNumbers) {
//        this.phoneNumbers = phoneNumbers;
//    }

    public ParkingPlace getParkingPlace() {
        return parkingPlace;
    }

    public void setParkingPlace(ParkingPlace parkingPlace) {
        this.parkingPlace = parkingPlace;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}