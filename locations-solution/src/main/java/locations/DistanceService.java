package locations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DistanceService {

    LocationRepository locationRepository;
    LocationParser locationParser = new LocationParser();

    public DistanceService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Optional<Double> calculateDistance(String name1, String name2) {
        Optional<Double> result = Optional.of(locationParser.distanceFrom(locationRepository.findByName(name1).get(),
                locationRepository.findByName(name2).get()));
        return result;
    }
}
