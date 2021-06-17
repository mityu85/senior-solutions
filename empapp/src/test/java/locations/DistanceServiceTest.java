package locations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DistanceServiceTest {

    @BeforeEach
    void init() {
        service = new DistanceService(List.of(
                new Location("Budapest", 14.789, 23.487),
                new Location("Szeged", 14.789, 23.487)
        ));
    }

    @Mock
    LocationRepository repository;

    @InjectMocks
    DistanceService service;

    @Test
    void testCalculateDistance() {
        //Optional<Double> result = service.calculateDistance("Budapest", "Szeged");
        //when(repository.findByName(anyString()))
        //        .thenReturn(Optional.of(new Location("Budapest", 14.789, 23.487)));
        Optional<Location> result = service.findByName("Budapest");
        assertEquals("Location{name='Budapest', lat=14.789, lon=23.487}", result.get().toString());
    }
}
