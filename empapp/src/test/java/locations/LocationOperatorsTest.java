package locations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationOperatorsTest {

    LocationOperators locationOperators;

    @BeforeEach
    void init() {
        locationOperators = new LocationOperators();
    }

    @Test
    void testOperators() {
        List<Location> locations = List.of(
                new Location("Budapest",47.497912,19.040235),
                new Location("Debrecen",37.497912,-19.040235),
                new Location("Szeged",-37.497912,-19.040235)
        );
        assertEquals(List.of("Budapest", "Debrecen"), locationOperators.filterOnNorth(locations).stream().map(Location::getName).collect(Collectors.toList()));
    }
}
