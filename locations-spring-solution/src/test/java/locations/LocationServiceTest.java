package locations;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationServiceTest {

    @Test
    void getLocation() {
        LocationService locationService = new LocationService();
        assertThat(locationService.getLocations().size() == 3);
        assertThat(locationService.getLocations().get(0).getName().startsWith("B"));
    }
}
