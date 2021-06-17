package locations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class LocationService {

    public List<Location> readFavoritePlaces(Reader reader) {
        List<Location> locations = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] temp = line.split(",");
                locations.add(new Location(temp[0], Double.parseDouble(temp[1]), Double.parseDouble(temp[2])));
            }
        } catch (IOException e) {
            throw new IllegalStateException("File cannot read");
        }
        return locations;
    }
}
