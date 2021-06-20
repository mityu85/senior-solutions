package locations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocationControllerTest {

    @Mock
    LocationService locationService;

    @InjectMocks
    LocationsController locationsController;

    @Test
    void getLocation() {
        when(locationService.getLocations())
                .thenReturn(List.of(
                        new Location(1, "Budapest", 48.789, 63.987),
                        new Location(1, "Szeged", 84.789, 36.987),
                        new Location(1, "Debrecen", -89.789, 148.987)
                ));
        assertEquals("[Location{id=1, name='Budapest', lat=48.789, lon=63.987}," +
                " Location{id=1, name='Szeged', lat=84.789, lon=36.987}," +
                " Location{id=1, name='Debrecen', lat=-89.789, lon=148.987}]", locationsController.getLocations());
    }
}
