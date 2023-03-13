package com.king.sensorvalidation.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Builder
public record SensorRequestDto(
        @Length(max = 17, min = 17, message = "Invalid sensor id provided") List<String> sensorsId
) {}