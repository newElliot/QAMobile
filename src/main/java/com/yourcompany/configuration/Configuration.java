package com.yourcompany.configuration;

public class Configuration {
	private String app;
	private String emulatorDevice;
	private String realDevice;
	
	public String getApp() {
		return this.app;
	}
	
	public String getEmulatorDevice() {
		return this.emulatorDevice;
	}
	
	public String getRealDevice() {
		return this.realDevice;
	}
	
	public void setApp(String app) {
		this.app = app;
	}
	
	public void setEmulatorDevice(String device) {
		this.emulatorDevice = device;
	}
	
	public void setRealDevice(String device) {
		this.realDevice = device;
	}
	
	public Configuration() {
	}
	
	@Override
	public String toString() {
		return "app: " + getApp()
		+ ",emulator device: " + getEmulatorDevice()
		+ ", real device: " + getRealDevice();
	}
}
