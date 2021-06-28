package locations;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/locations/api")
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
    public List<LocationDto> getLocations(@RequestParam Optional<Double> lat, @RequestParam Optional<Double> lon) {
        return locationService.getLocationsByLatitude(lat, lon);
    }
}
