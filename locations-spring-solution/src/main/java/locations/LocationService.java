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

    public List<LocationDto> getLocationsByLatitude(Optional<Double> minLat, Optional<Double> maxLat, Optional<Double> minLon, Optional<Double> maxLon) {
        return locations.stream()
                .filter(e -> ((minLat.isEmpty() || maxLat.isEmpty() || minLon.isEmpty()) || maxLon.isEmpty()) ||
                        (e.getLat() >= minLat.get() && e.getLat() <= maxLat.get() &&
                                e.getLon() >= minLon.get() && e.getLon() <= maxLon.get()))
                .map(e -> modelMapper.map(e, LocationDto.class))
                .collect(Collectors.toList());
    }

    public LocationDto getLocationById(long id) {
        return modelMapper.map(locations.stream()
                .filter(e -> e.getId() == id).findAny()
                .orElseThrow(() -> new IllegalArgumentException("Location is not found: " + id)),
                LocationDto.class);
    }

    public LocationDto createLocation(CreateLocationCommand command) {
        return modelMapper.map(locations.add(new Location(
                idGenerator.incrementAndGet(),
                        command.getName(),
                        command.getLat(),
                        command.getLon())), LocationDto.class);
    }

    public LocationDto updateLocation(long id, UpdateLocationCommand command) {
        Location location = locations.stream().filter(e -> e.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Location is not found: " + id));
        location.setName(command.getName());
        location.setLat(command.getLat());
        location.setLon(command.getLon());
        return modelMapper.map(location, LocationDto.class);
    }
}
