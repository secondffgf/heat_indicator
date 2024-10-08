package com.indicator.api.service;

import static com.indicator.api.constants.IndicatorConstants.OBJECTS_TO_RETAIN;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.indicator.api.domain.IndicatorData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndicatorDataService {
	private static final int RETAIN_DEFAULT_VALUE = 5;
	private final MongoTemplate mongoTemplate;
	private final SettingsService settingsService;
	
	public void save(IndicatorData data) {
		List<IndicatorData> allData = mongoTemplate.findAll(IndicatorData.class);
		cleanData(allData);
		mongoTemplate.save(data);
	}

	private void cleanData(List<IndicatorData> allData) {
		int dataSize = allData.size();
		int toRemove = dataSize - getRetainProperty() + 1;
		if (toRemove <= 0) {
			return;
		}
		List<String> itemsToRemove = allData.stream()
			.limit(toRemove)
			.map(d -> d.getId())
			.collect(Collectors.toList());
		mongoTemplate.remove(new Query(Criteria.where("id").in(itemsToRemove)), IndicatorData.class);
	}

	private int getRetainProperty() {
		Map<String, String> retainProperty = settingsService.getRetainProperty();
		String value = retainProperty.get(OBJECTS_TO_RETAIN);
		return value == null ? RETAIN_DEFAULT_VALUE : Integer.parseInt(value);
	}

	public List<IndicatorData> getAllData() {
		return mongoTemplate.findAll(IndicatorData.class);
	}

}
