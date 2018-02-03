package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegionStatsDto {

    private Integer region1;
    private Integer region2;
    private Integer region3;
    private String region1Name;
    private String region2Name;
    private String region3Name;
    private Integer count;

}
