package cardealer;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class CarService {

    private ModelMapper modelMapper;
    private AtomicLong idGenerator = new AtomicLong();

    private List<Car> cars = Collections.synchronizedList(new ArrayList<>(List.of(
            new Car("Toyota", "Corolla", 1999, CarShape.NORMAL, List.of(
                    new KmState(LocalDate.of(2002,05,02), 120000),
                    new KmState(LocalDate.of(2005,10,12), 180000),
                    new KmState(LocalDate.of(2008,9,01), 230000)
            )),
            new Car("Renault", "Megane", 2005, CarShape.BAD, List.of(
                    new KmState(LocalDate.of(2010,05,02), 320000),
                    new KmState(LocalDate.of(2015,10,12), 400000),
                    new KmState(LocalDate.of(2020,9,01), 500000)
            )),
            new Car("Mercedes", "Benz", 2013, CarShape.EXCELLENT, List.of(
                    new KmState(LocalDate.of(2016,05,02), 80000),
                    new KmState(LocalDate.of(2019,10,12), 130000),
                    new KmState(LocalDate.of(2020,9,01), 160000)
            ))
    )));

    public CarService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<CarDTO> listCars(Optional<String> brand) {
        return cars.stream()
                .filter(e -> brand.isEmpty() ||  e.getBrand().equalsIgnoreCase(brand.get()))
                .map(e -> modelMapper.map(e, CarDTO.class))
                .collect(Collectors.toList());
    }
}
