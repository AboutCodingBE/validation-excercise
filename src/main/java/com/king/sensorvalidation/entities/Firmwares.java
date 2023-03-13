package com.king.sensorvalidation.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Firmwares {
    private String currentConfiguration;
    private int configurationCount;
    private String fileId;

}
