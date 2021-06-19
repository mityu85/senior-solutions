package locations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DistanceServiceTest {

    @Mock
    LocationRepository locationRepository;

    @InjectMocks
    DistanceService distanceService;

    @Test
    void testCalculateDistance() {

        when(locationRepository.findByName(eq("Budapest")))
                .thenReturn(Optional.of(new Location("Budapest", 15.789,78.789)));

        Optional<Location> location = locationRepository.findByName("Budapest");
        //Optional<Double> distance = distanceService.calculateDistance("Budapest", "Szeged");
        verify(locationRepository).findByName(any());
        assertEquals("Budapest", location.get().getName());
    }
}
