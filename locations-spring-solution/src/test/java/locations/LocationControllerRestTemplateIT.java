package locations;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocationControllerRestTemplateIT {

    @Autowired
    TestRestTemplate template;

    @Autowired
    LocationService locationService;

    @Test
    void testListLocations() {
        locationService.deleteAllLocation();

        LocationDto locationDto = template.postForObject("/api/locations", new CreateLocationCommand("Temesvár", 14.789, 98.456), LocationDto.class);
        assertEquals("Temesvár", locationDto.getName());

        template.postForObject("/api/locations", new CreateLocationCommand("Alcsútdoboz", 79.654, 123.78), LocationDto.class);

        List<LocationDto> locations = template.exchange(
                "/api/locations",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LocationDto>>() {})
                .getBody();

        assertThat(locations)
                .extracting(LocationDto::getName)
                .containsExactly("Temesvár", "Alcsútdoboz");


//        List<LocationDto> locations = template.exchange("/api/locations",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<LocationDto>>() {})
//                .getBody();
    }
}
