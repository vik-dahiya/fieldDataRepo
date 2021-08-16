package com.field.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SoilHistory {

	private long dt;
	private double t10;
	private double moisture;
	private double t0;

	public long getDt() {
		return dt;
	}

	public void setDt(long dt) {
		this.dt = dt;
	}

	public double getT10() {
		return t10;
	}

	public void setT10(double t10) {
		this.t10 = t10;
	}

	public double getMoisture() {
		return moisture;
	}

	public void setMoisture(double moisture) {
		this.moisture = moisture;
	}

	public double getT0() {
		return t0;
	}

	public void setT0(double t0) {
		this.t0 = t0;
	}

	public SoilHistory() {
	}

	public SoilHistory(long dt, double t10, double moisture, double t0) {
		super();
		this.dt = dt;
		this.t10 = t10;
		this.moisture = moisture;
		this.t0 = t0;
	}

	@Override
	public String toString() {
		return "SoilHistory [dt=" + dt + ", t10=" + t10 + ", moisture=" + moisture + ", t0=" + t0 + "]";
	}

}
