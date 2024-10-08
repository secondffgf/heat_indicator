package com.indicator.api.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.indicator.api.service.SettingsService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/indicator/settings", produces = MediaType.APPLICATION_JSON_VALUE)
public class SettingsController {
	private final SettingsService settingsService;
	
	@GetMapping
	public Map<String, String> getRetainProperty() {
		return settingsService.getRetainProperty();
	}
	
	@PostMapping
	public void setRetainProperty(@RequestParam String retainValue) {
		settingsService.setRetainProperty(retainValue);
	}
}
