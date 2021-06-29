package locations;

public class LocationNotFoundException extends IllegalArgumentException {
    public LocationNotFoundException(String s) {
        super(s);
    }
}
