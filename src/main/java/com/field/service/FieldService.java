package com.field.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.geojson.GeoJsonObject;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.field.model.Field;

@Service
public class FieldService {

	// Date String
	private String dateString = "2020-07-25T10:03:56.782Z";

	// Formatter
	private static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

	private LocalDateTime dtCreated = LocalDateTime.parse(dateString, formatter);
	
	private String country = Locale.GERMANY.getISO3Country();
	
	private List<Field> fieldsList = new ArrayList<Field>(Arrays.asList(new Field(
			"Potato Field", dtCreated, null, country , getObject())));

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

	public List<Field> getFieldsList() {
		return fieldsList;
	}

	public void setFieldsList(List<Field> fieldsList) {
		this.fieldsList = fieldsList;
	}

	public Field getField(String id) {
		return fieldsList.stream().filter(field -> field.getId().equals(id)).findFirst().get();
	}

	public void addField(Field field) {
		fieldsList.add(field);
	}

	public void updateField(String id, Field field) {
		int counter = 0;
		for (Field f : fieldsList) {
			if (f.getId().equals(id)) {
				fieldsList.set(counter, field);
			}
			counter++;
		}
	}

	public void deleteField(String id) {
		fieldsList.removeIf(field -> field.getId().equals(id));
	}

}
