package com.example.demo.dto;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class AreaRawData {
    String zipCode;
    String area0;
    String area1;
    String area2;
    String area3;
    String publicArea;

    public AreaRawData(String lineValue) {
        String[] values = lineValue.split("\\|");
        // 0        1    2      3     4        5     6        7   8     9     10       11       12     13
        // 새우편번호|시도|시도영문|시군구|시군구영문|읍면동|읍면동영문|리명|산여부|행정동|시작주번지|시작부번지|끝주번지|끝부번지
        // zipCode  area0       area1          area2          area3
        this.zipCode = values[0];
        this.area0 = values[1];
        this.area1 = values[3];
        this.area2 = values[5];
        this.area3 = values[7];
    }

}
