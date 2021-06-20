package locations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LocationIT {

    @Autowired
    LocationsController locationsController;

    @Test
    void getLocation() {
        assertEquals("[Location{id=1, name='Budapest', lat=48.789, lon=63.987}," +
                " Location{id=1, name='Szeged', lat=84.789, lon=36.987}," +
                " Location{id=1, name='Debrecen', lat=-89.789, lon=148.987}]", locationsController.getLocations());
    }
}
