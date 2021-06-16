package locations;

import java.util.ArrayList;
import java.util.List;

public class LocationParser {

    List<Location> locations = new ArrayList<>();

    public List<Location> getLocations() {
        return locations;
    }

    public Location parse(String text) {
        String[] temp = text.split(",");
        return new Location(temp[0], Double.parseDouble(temp[1]), Double.parseDouble(temp[2]));
    }

    public void addLocation(Location location) {
        locations.add(location);
    }

    public boolean isOnEquatorWithStream() {
        if (locations.stream().map(Location::getLat).anyMatch(l -> l == 0)) {
            return true;
        }
        return false;
    }

    public boolean isOnEquator(Location location) {
        if (location.getLat() == 0.0) {
            return true;
        }
        return false;
    }

    public boolean isOnPrimeMeridian() {
        if (locations.stream().map(Location::getLon).anyMatch(l -> l == 0)) {
            return true;
        }
        return false;
    }

    public double distanceFrom(Location location, Location otherLocation) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(otherLocation.getLat() - location.getLat());
        double lonDistance = Math.toRadians(otherLocation.getLon() - location.getLon());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(location.getLat())) * Math.cos(Math.toRadians(otherLocation.getLat()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        //double height = el1 - el2;

        distance = Math.pow(distance, 2); //+ Math.pow(height, 2);

        return Math.sqrt(distance);
    }
}
