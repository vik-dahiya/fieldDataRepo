package com.field.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.geojson.GeoJsonObject;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.field.model.Field;
import com.field.model.SoilHistory;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class FieldControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	int randomServerPort;

	@Autowired
	FieldController controller;

	private String fieldId;

	// Date String
	private String dateString = "2020-07-25T10:03:56.782Z";

	// Formatter
	private static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

	private LocalDateTime dtCreated = LocalDateTime.parse(dateString, formatter);

	private String country = Locale.GERMANY.getISO3Country();

	private Field field = new Field("Potato Field", dtCreated, null, country, getObject());

	@Test
	public void testAddPolygon() throws Exception {
		final String baseUrl = "http://localhost:" + randomServerPort + "/fields";
		URI uri = new URI(baseUrl);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Field> request = new HttpEntity<>(field, headers);

		ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

		// Verify request succeed
		assertEquals(201, result.getStatusCodeValue());

	}

	@Test
	public void testGetPloygon() throws URISyntaxException {

		Field[] fields = controller.getAllPolygons().getBody();

		for (Field field : fields) {
			fieldId = field.getId();
		}

		final String baseUrl = "http://localhost:" + randomServerPort + "/fields" + "/" + fieldId;
		URI uri = new URI(baseUrl);

		ResponseEntity<Field> result = this.restTemplate.getForEntity(uri, Field.class);

		// Verify request succeed
		assertEquals(200, result.getStatusCodeValue());

	}

	@Test
	public void testGetAllPolygons() throws URISyntaxException {

		final String baseUrl = "http://localhost:" + randomServerPort + "/fields";
		URI uri = new URI(baseUrl);

		ResponseEntity<Field[]> result = this.restTemplate.getForEntity(uri, Field[].class);

		// Verify request succeed
		assertEquals(200, result.getStatusCodeValue());
	}

	@Test
	public void testUpdatePolygon() throws URISyntaxException {

		Field[] fields = controller.getAllPolygons().getBody();

		for (Field field : fields) {
			fieldId = field.getId();
		}

		final String baseUrl = "http://localhost:" + randomServerPort + "/fields" + "/" + fieldId;
		URI uri = new URI(baseUrl);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Field> request = new HttpEntity<>(field, headers);

		ResponseEntity<String> result = this.restTemplate.exchange(uri, HttpMethod.PUT, request, String.class);

		// Verify request succeed
		assertEquals(200, result.getStatusCodeValue());
		assertEquals("Field has been updated.", result.getBody());
	}

	@Test
	public void testDeletePolygon() throws Exception {

		Field[] fields = controller.getAllPolygons().getBody();

		for (Field field : fields) {
			fieldId = field.getId();
		}

		final String baseUrl = "http://localhost:" + randomServerPort + "/fields" + "/" + fieldId;
		URI uri = new URI(baseUrl);

		HttpEntity<Field> request = new HttpEntity<>(field);

		ResponseEntity<String> result = this.restTemplate.exchange(uri, HttpMethod.DELETE, request, String.class);

		// Verify request succeed
		if (fields.length != 0) {
			assertEquals(200, result.getStatusCodeValue());
			assertEquals("Field has been deleted.", result.getBody());
		} else {
			assertEquals(422, result.getStatusCodeValue());
		}

	}

	@Test
	public void testGetSoilHistory() throws URISyntaxException {
		Field[] fields = controller.getAllPolygons().getBody();

		for (Field field : fields) {
			fieldId = field.getId();
		}
		final String baseUrl = "http://localhost:" + randomServerPort + "/fields" + "/" + fieldId + "/soil-history";
		URI uri = new URI(baseUrl);

		ResponseEntity<SoilHistory[]> result = this.restTemplate.getForEntity(uri, SoilHistory[].class);

		// Verify request succeed
		assertEquals(200, result.getStatusCodeValue());

	}

	@SuppressWarnings("unchecked")
	private GeoJsonObject getObject() {

		JSONObject properties = new JSONObject();
		JSONObject geoJson = new JSONObject();
		geoJson.put("type", "Feature");
		geoJson.put("properties", properties);
		JSONObject geometry = new JSONObject();
		geometry.put("type", "Polygon");
		JSONArray arrayCoord = new JSONArray();
		double arr[][] = { { -5.553604888914691, 33.88229680420605 }, { -5.5516736984239685, 33.88229680420605 },
				{ -5.5516736984239685, 33.88372189858022 }, { -5.555965232847882, 33.88390003370375 },
				{ -5.555965232847882, 33.88229680420605 }, { -5.553604888914691, 33.88229680420605 } };
		arrayCoord.add(arr);

		geometry.put("coordinates", arrayCoord);

		geoJson.put("geometry", geometry);

		GeoJsonObject object = null;
		try {
			object = new ObjectMapper().readValue(geoJson.toString(), GeoJsonObject.class);

		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return object;
	}

}
