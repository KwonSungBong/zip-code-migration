package com.example.demo.dto;

import lombok.Data;

@Data
public class RangeAreaRaw extends AreaRaw {

    public RangeAreaRaw() {}

    public RangeAreaRaw(String line){
        super(line);
    }

    // 0        1    2      3     4        5     6        7   8     9     10       11       12     13
    // 새우편번호 |시도 |시도영문 |시군구|시군구영문 |읍면동 |읍면동영문 |리명|산여부|행정동  |시작주번지|시작부번지|끝주번지|끝부번지
    // zipCode  area0       area1          area2          area3
    @Override
    public void parseLine(String line) {
        String[] values = line.split("\\|");
        setZipCode(values[0]);
        setArea0(values[1]);
        setArea1(values[3]);
        setArea2(values[5]);
        setArea3(values[7]);
    }

}
