package locations;

import java.util.ArrayList;
import java.util.List;

public class LocationOperators {

    public List<Location> filterOnNorth(List<Location> locations) {
        List<Location> locationList = new ArrayList<>();
        for (Location location: locations) {
            if (location.getLat() > 0) {
                locationList.add(location);
            }
        }
        return locationList;
    }
}
