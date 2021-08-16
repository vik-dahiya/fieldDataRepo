package com.field.resource;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.field.model.Field;
import com.field.model.SoilHistory;
import com.field.service.FieldService;

@RestController
@RequestMapping("/fields")
public class FieldController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private FieldService service;

	@Value("${apikey}")
	private String appid;

	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<Field> addPolygon(@RequestBody Field field) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Field> entity = new HttpEntity<Field>(field, headers);
		String url = "http://api.agromonitoring.com/agro/1.0/polygons?appid=" + appid;

		return restTemplate.exchange(url, HttpMethod.POST, entity, Field.class);

	}

	@GetMapping("/{fieldId}")
	public ResponseEntity<Field> getPloygon(@PathVariable String fieldId) {
		String uri = "http://api.agromonitoring.com/agro/1.0/polygons/" + fieldId + "?appid=" + appid;
		return restTemplate.getForEntity(uri, Field.class);
	}
	
	@GetMapping
	public ResponseEntity<Field[]> getAllPolygons(){
		String url = "http://api.agromonitoring.com/agro/1.0/polygons?appid="+appid;
		return restTemplate.getForEntity(url, Field[].class);
	}
	
	@PutMapping(value = "/{fieldId}",  consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> updatePolygon(@PathVariable String fieldId, @RequestBody Field field )
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Field> entity = new HttpEntity<Field>(field, headers);
		String url = "http://api.agromonitoring.com/agro/1.0/polygons/" + fieldId + "?appid=" + appid;
		
		restTemplate.exchange(url, HttpMethod.PUT, entity, Field.class);
		return ResponseEntity.ok("Field has been updated.");
	}
	
	@DeleteMapping("/{fieldId}")
	public ResponseEntity<String> deletePolygon(@PathVariable String fieldId) {
		String url = "http://api.agromonitoring.com/agro/1.0/polygons/" + fieldId + "?appid=" + appid;
		restTemplate.delete(url);
		return ResponseEntity.ok("Field has been deleted.");
	}

	@GetMapping("/{fieldId}/soil-history")
	public ResponseEntity<SoilHistory[]> getSoilHistory(@PathVariable String fieldId) {
		LocalDateTime end = LocalDateTime.now();
		LocalDateTime start = end.minusDays(7);

		String url = "https://samples.openweathermap.org/agro/1.0/soil/history?polyid=" + fieldId + "&start=" + start
				+ "&end=" + end + "&appid=" + appid;

		return restTemplate.getForEntity(url, SoilHistory[].class);
	}

	@GetMapping("/hard-coded")
	public List<Field> getAllFields() {
		return service.getFieldsList();
	}

	@GetMapping("/hard-coded/{fieldId}")
	public Field getField(@PathVariable String fieldId) {
		return service.getField(fieldId);
	}

	@PostMapping("/hard-coded/create")
	public void createField(@RequestBody Field newField) {
		service.addField(newField);
	}

	@PutMapping("/hard-coded/{fieldId}")
	public void updateField(@PathVariable String fieldId, @RequestBody Field newField) {
		service.updateField(fieldId, newField);
	}

	@DeleteMapping("/hard-coded/{fieldId}")
	public void deleteField(@PathVariable String fieldId) {
		service.deleteField(fieldId);
	}
}
