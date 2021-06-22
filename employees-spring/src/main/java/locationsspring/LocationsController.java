package locationsspring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LocationsController {

    private List<Location> locations = List.of(
            new Location(1, "Budapest", 15.785, 18.999),
            new Location(2, "Szeged", 25.785, 58.999)
    );

    @GetMapping("/")
    @ResponseBody
    public String getLocations() {
        return locations.toString();
    }
}
