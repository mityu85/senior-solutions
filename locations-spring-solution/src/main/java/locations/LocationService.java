package locations;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private List<Location> locations = List.of(
            new Location(1, "Budapest", 48.789, 63.987),
            new Location(1, "Szeged", 84.789, 36.987),
            new Location(1, "Debrecen", -89.789, 148.987)
    );

    public List<Location> getLocations() {
        return locations;
    }
}
