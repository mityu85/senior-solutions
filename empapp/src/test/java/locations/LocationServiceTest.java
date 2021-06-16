package locations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LocationServiceTest {

    LocationService locationService;

    @BeforeEach
    void init() {
        locationService = new LocationService();
    }

    @Test
    void testRead() throws IOException {
        List<Location> locations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(LocationServiceTest.class.getResourceAsStream("favoriteplaces.csv")))) {
            locations = locationService.readFavoritePlaces(br);
        }
        assertThat(locations.get(0).getName().startsWith("New"));
        assertThat(locations.get(1).getName().equals("Washington"));
        assertThat(locations.stream().map(Location::getLat).anyMatch(l -> l == 0));
    }
}
