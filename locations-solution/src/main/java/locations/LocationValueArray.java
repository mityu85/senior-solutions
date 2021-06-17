package locations;

public class LocationValueArray {

    private Location location;
    private boolean value;

    public LocationValueArray(Location location, boolean value) {
        this.location = location;
        this.value = value;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isValue() {
        return value;
    }
}
