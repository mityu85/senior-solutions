package locations;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/locations")
public class LocationsController {

    private LocationService locationService;

    public LocationsController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public List<LocationDto> getLocations(@RequestParam Optional<String> name) {
        return locationService.getLocations(name);
    }

    @GetMapping("/lat")
    public List<LocationDto> getLocations(@RequestParam Optional<Double> minLat, @RequestParam Optional<Double> maxLat,
                                          @RequestParam Optional<Double> minLon, @RequestParam Optional<Double> maxLon) {
        return locationService.getLocationsByLatitude(minLat, maxLat, minLon, maxLon);
    }

    @GetMapping("/{id}")
    public LocationDto getLocationById(@PathVariable("id") long id) {
        return locationService.getLocationById(id);
    }

    @PostMapping
    public LocationDto createLocation(@RequestBody CreateLocationCommand command) {
        return locationService.createLocation(command);
    }
}
