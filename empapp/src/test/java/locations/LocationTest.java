package locations;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {

    LocationParser locationParser;

    @BeforeEach
    void init() {
        locationParser = new LocationParser();
    }

    @Test
    @DisplayName("Testing the location parser")
    void testParse() {
        Location location = locationParser.parse("Budapest,47.497912,19.040235");
        assertAll(
                () -> assertEquals("Budapest", location.getName()),
                () -> assertEquals(47.497912, location.getLat()),
                () -> assertEquals(19.040235, location.getLon())
        );
    }

    @Test
    @DisplayName("Testing the equator")
    void testIsOnEquatorWithStream() {
        locationParser.addLocation(locationParser.parse("Budapest,0,19.040235"));
        assertTrue(locationParser.isOnEquatorWithStream());
    }

    @RepeatedTest(3)
    void testIsOnEquator(RepetitionInfo repetitionInfo) {
        LocationValueArray[] locationValueArrays = {
               new LocationValueArray(new Location("Budapest", 0, 12.879), true),
               new LocationValueArray(new Location("Szeged", 2.478, 12.879), false),
               new LocationValueArray(new Location("Debrecen", 0, 12.879), true)
        };
        assertEquals(locationValueArrays[repetitionInfo.getCurrentRepetition()-1].isValue(),
                    locationParser.isOnEquator(locationValueArrays[repetitionInfo.getCurrentRepetition()-1].getLocation()));
    }

    @Test
    @DisplayName("Testing the meridian")
    void testIsOnPrimeMeridian() {
        locationParser.addLocation(locationParser.parse("Budapest,47.497912,0"));
        assertTrue(locationParser.isOnPrimeMeridian());
    }

    @Test
    void testSame() {
        assertNotSame(locationParser.parse("Budapest,47.497912,0"), locationParser.parse("Budapest,47.497912,0"));
    }

    @Test
    void testDistanceFrom() {
        assertEquals(0, locationParser.distanceFrom(locationParser.parse("Budapest,47.497912,0"), locationParser.parse("Budapest,47.497912,0")));
    }

    @Test
    void testIllegalArgument() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class,
                () -> new Location("Budapest", -91.0, 183));
        assertEquals("latitude must be between: -90 and 90", iae.getMessage());

        IllegalArgumentException iae2 = assertThrows(IllegalArgumentException.class,
                () -> new Location("Szeged", 81, 183));
        assertEquals("longitude must be between: -180 and 180", iae2.getMessage());
    }
}
