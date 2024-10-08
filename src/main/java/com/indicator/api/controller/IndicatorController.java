package com.indicator.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.indicator.api.domain.IndicatorData;
import com.indicator.api.service.IndicatorDataService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(path = "/indicator", produces = MediaType.APPLICATION_JSON_VALUE)
public class IndicatorController {
	private final IndicatorDataService dataService;
	
	@GetMapping
	public List<IndicatorData> getIndicatorData() {
		log.info("get indicator data");
		return dataService.getAllData();
	}
	
	@GetMapping(path = "/latest")
	public Optional<IndicatorData> getLatest() {
		List<IndicatorData> allData = dataService.getAllData();
		return allData.isEmpty() ? Optional.empty() : Optional.of(allData.get(allData.size() - 1));
	}
	
	@PostMapping
	public void saveIndicatorData(@RequestBody IndicatorData data) {
		log.info("save indicator data: {}", data);
		dataService.save(data);
	}
}
