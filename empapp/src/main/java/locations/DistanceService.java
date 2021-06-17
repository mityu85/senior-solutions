package locations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DistanceService implements LocationRepository {

    private LocationParser locationParser = new LocationParser();
    private List<Location> locations;

    public DistanceService(List<Location> locations) {
        this.locations = locations;
    }

    public Optional<Double> calculateDistance(String name1, String name2) {
        if (findByName(name1).isPresent() && findByName(name2).isPresent()) {
            System.out.println("OK");
            return Optional.of(locationParser.distanceFrom(findByName(name1).get(), findByName(name2).get()));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Location> findByName(String name) {
        Optional<Location> location = locations.stream().filter(l -> l.getName().equals(name)).findAny();
        return location;
    }
}
