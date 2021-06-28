package locations;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationServiceTest {

    private ModelMapper modelMapper;

    @Test
    void getLocation() {
        LocationService locationService = new LocationService(modelMapper);
        assertThat(locationService.getLocations(null).size() == 3);
        assertThat(locationService.getLocations(null).get(0).getName().startsWith("B"));
    }
}
