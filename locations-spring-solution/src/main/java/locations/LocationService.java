package locations;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private ModelMapper modelMapper;
    private AtomicLong idGenerator = new AtomicLong();

    private List<Location> locations = Collections.synchronizedList(new ArrayList<>(List.of(
            new Location(idGenerator.incrementAndGet(), "Budapest", 48.789, 63.987),
            new Location(idGenerator.incrementAndGet(), "Szeged", 84.789, 36.987),
            new Location(idGenerator.incrementAndGet(), "Debrecen", -89.789, 148.987)))
    );

    public LocationService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<LocationDto> getLocations(Optional<String> name) {
        return locations.stream()
                .filter(e -> name.isEmpty() || e.getName().equalsIgnoreCase(name.get()))
                .map(e -> modelMapper.map(e, LocationDto.class))
                .collect(Collectors.toList());
    }

    public List<LocationDto> getLocationsByLatitude(Optional<Double> minLat, Optional<Double> minLon) {
        return locations.stream()
                .filter(e -> (minLat.isEmpty() || minLon.isEmpty()) ||
                        (e.getLat() >= minLat.get() && e.getLon() >= minLon.get()))
                .map(e -> modelMapper.map(e, LocationDto.class))
                .collect(Collectors.toList());
    }
}
