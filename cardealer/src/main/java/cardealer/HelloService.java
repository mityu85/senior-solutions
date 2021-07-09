package cardealer;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class HelloService {

    public String sayHello() {
        return "Welcome to our CarDealer page " + LocalDateTime.now();
    }
}
