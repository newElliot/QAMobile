package com.yourcompany.configuration;

public class Configuration {
	private String app;
	private String device;
	
	public String getApp() {
		return this.app;
	}
	
	public String getDevice() {
		return this.device;
	}
	
	public void setApp(String app) {
		this.app = app;
	}
	
	public void setDevice(String device) {
		this.device = device;
	}
	
	public Configuration() {
	}
	
	@Override
	public String toString() {
		return "app: " + getApp()
		+ "device: " + getDevice();
	}
}
