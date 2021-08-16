package com.field.model;

import java.time.LocalDateTime;

import org.geojson.GeoJsonObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Field {

	private String id;
	private String name;
	private LocalDateTime created;
	private LocalDateTime updated;
	private String countryCode;
	private GeoJsonObject geo_json;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getUpdated() {
		return updated;
	}

	public void setUpdated(LocalDateTime updated) {
		this.updated = updated;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Field() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GeoJsonObject getGeo_json() {
		return geo_json;
	}

	public void setGeo_json(GeoJsonObject geo_json) {
		this.geo_json = geo_json;
	}

	public Field(String name, LocalDateTime created, LocalDateTime updated, String countryCode,
			GeoJsonObject geo_json) {
		this.name = name;
		this.created = created;
		this.updated = updated;
		this.countryCode = countryCode;
		this.geo_json = geo_json;
	}

}
