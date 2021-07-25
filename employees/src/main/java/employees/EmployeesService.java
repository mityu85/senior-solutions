package employees;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeesService {

    private ModelMapper modelMapper;
    private EmployeeRepository repository;

    //    private AtomicLong idGenerator = new AtomicLong();
//
//    private List<Employee> employees = Collections.synchronizedList(new ArrayList<>(List.of(
//            new Employee(idGenerator.incrementAndGet(), "John Doe"),
//            new Employee(idGenerator.incrementAndGet(), "Jack Doe")
//    )));

//    public EmployeesService(ModelMapper modelMapper) {
//        this.modelMapper = modelMapper;
//    }
//
//    public List<EmployeeDto> listEmployees(Optional<String> prefix) {
//        List<Employee> filtered = employees.stream()
//                .filter(e -> prefix.isEmpty() || e.getName().toLowerCase().startsWith(prefix.get().toLowerCase()))
//                .collect(Collectors.toList());
//        //Type targetListType = new TypeToken<List<EmployeeDto>>(){}.getType();
//        //return modelMapper.map(filtered,targetListType);
//        return filtered.stream().map(e -> modelMapper.map(e, EmployeeDto.class)).collect(Collectors.toList());
//    }

    public List<EmployeeDto> listEmployees(Optional<String> prefix) {
        return repository.findAll().stream()
                .map(e -> modelMapper.map(e, EmployeeDto.class))
                .collect(Collectors.toList());
    }

//    public EmployeeDto findEmployeeById(long id) {
//        return modelMapper.map(employees.stream()
//                .filter(e -> e.getId() == id).findAny()
//                .orElseThrow(() -> new EmployeeNotFoundException(id)),
//                EmployeeDto.class);
//    }

    public EmployeeDto findEmployeeById(long id) {
        return modelMapper.map(repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found")), EmployeeDto.class);
    }

//    public EmployeeDto createEmployee(CreateEmployeeCommand command) {
//        Employee employee = new Employee(idGenerator.incrementAndGet(), command.getName());
//        employees.add(employee);
//        return  modelMapper.map(employee, EmployeeDto.class);
//    }

    public EmployeeDto createEmployee(CreateEmployeeCommand command) {
        Employee employee = new Employee((command.getName()));
        repository.save(employee);
        return modelMapper.map(employee, EmployeeDto.class);
    }

//    public EmployeeDto updateEmployee(long id, UpdateEmployeeCommand command) {
//        Employee employee = employees.stream()
//                .filter(e -> e.getId() == id)
//                .findFirst()
//                .orElseThrow(() -> new EmployeeNotFoundException(id));
//        employee.setName(command.getName());
//        return modelMapper.map(employee, EmployeeDto.class);
//    }

    @Transactional
    public EmployeeDto updateEmployee(long id, UpdateEmployeeCommand command) {
        Employee employee = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Employee not found"));
        employee.setName(command.getName());
        return modelMapper.map(employee, EmployeeDto.class);
    }

//    public void deleteEmployee(long id) {
//        Employee employee = employees.stream()
//                .filter(e -> e.getId() == id)
//                .findFirst()
//                .orElseThrow(() -> new EmployeeNotFoundException(id));
//        employees.remove(employee);
//    }

    public void deleteEmployee(long id) {
        repository.deleteById(id);
    }

//    public void deleteAllEmployees() {
//        idGenerator = new AtomicLong();
//        employees.clear();
//    }

    public void deleteAllEmployees() {
        repository.deleteAll();
    }
}
