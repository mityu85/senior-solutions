package activitytracker;

public class CoordinateDto {

    private double lat;
    private double lon;

    public CoordinateDto(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "CoordinateDto{" +
                "lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
