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

        Location location = new Location("Budapest", 15.789,78.789);

        when(locationRepository.findByName(any()))
                .thenReturn(Optional.of(location));

        distanceService.calculateDistance("Budapest", "Szeged");
        verify(locationRepository, atLeast(1)).findByName(any());
    }
}
