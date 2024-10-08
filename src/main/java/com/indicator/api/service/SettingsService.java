package com.indicator.api.service;

import static com.indicator.api.constants.IndicatorConstants.OBJECTS_TO_RETAIN;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SettingsService {
	
	private final Environment environment;
	
	public Map<String, String> getRetainProperty() {
		String objectsToRetain = environment.getProperty(OBJECTS_TO_RETAIN);
		Map<String, String> result = new HashMap<>();
		result.put(OBJECTS_TO_RETAIN, objectsToRetain);
		return result;
	}

	public void setRetainProperty(String propertyValue) {
		try {
			Integer.parseInt(propertyValue);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("value is not an integer.", e);
		}
		System.setProperty(OBJECTS_TO_RETAIN, propertyValue);
	}
}
