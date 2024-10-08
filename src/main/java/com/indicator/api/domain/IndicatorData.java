package com.indicator.api.domain;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.ToString;

@Document
@Data
@ToString
public class IndicatorData implements Comparable<IndicatorData> {
	@Id
	@JsonIgnore
	private String id;
	private String value;
	private String timestamp;
	
	public IndicatorData() {
		ZoneOffset zoneOffSet= ZoneOffset.of("+03:00");
		var dateTime = OffsetDateTime.now(zoneOffSet);
		DateTimeFormatter dateTimeFormatter = 
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		timestamp = dateTimeFormatter.format(dateTime);
	}

	@Override
	public int compareTo(IndicatorData other) {
		return timestamp.compareTo(other.timestamp);
	}
}
