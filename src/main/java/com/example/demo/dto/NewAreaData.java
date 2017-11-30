package com.example.demo.dto;

import lombok.Data;
import lombok.ToString;
import org.springframework.util.StringUtils;

@ToString
@Data
public class NewAreaData {

    String zipCode;
    String area0; // 시도
    String area1; // 시군구
    String area2; // 읍면
    String area3;
    String buildingName; // 시군구용건물명
    String uniqueRoadName;

    public NewAreaData() {
    }

    // 새우편번호|시도|시도영문|시군구|시군구영문|읍면|읍면영문|도로명코드|도로명|도로명영문|지하여부|건물번호본번|건물번호부번|건물관리번호|다량배달처명|시군구용건물명|법정동코드|법정동명|리명|행정동명|산여부|지번본번|읍면동일련번호|지번부번|구우편번호|우편번호일련번호
    // 0        1            3               5                   8                         11        12                           15                   17      18   19
    // 25627   |강원도|Gangwon-do|강릉시|Gangneung-si|강동면|Gangdong-myeon|421504460343|돌평길|Dolpyeong-gil|0|58|16|4215034022007350000044742|||4215034022||모전리|강동면|0|735|01|0||
    public NewAreaData(String lineValue) {
        String[] values = lineValue.split("\\|");
        this.zipCode = values[0];
        this.area0 = values[1];
        this.area1 = values[3];

        // 1      3      5      17     18   19
        // 강원도, 강릉시, 강동면, "", 모전리, 강동명  -> "강원도 강릉시 강동면 모전리"
        // 경기도, 평택시, "",    통북동, "",  원평동 -> "경기도 평택시 통북동"
        // 세종시, "",   금남면,  "", 용포리, ""     -> "세종시 금남면 용포"
        // 강원도, 강릉시, "",   교동,   "",  교2동  -> "강원도 강릉시 교동 교2동"

        // 읍면
        if (!StringUtils.isEmpty(values[5])) { // 읍면이 있다면
            this.area2 = values[5];
            if(!StringUtils.isEmpty(values[17])) {
                this.area3 = values[17]; // 읍면 + 법정동
            } else{
                this.area3 = values[18]; // 읍면 + 리
            }
        } else {
            if(!StringUtils.isEmpty(values[17])) { // 동이 있다면
                this.area2 = values[17]; // 동
            } else{
                this.area2 = values[18]; // 리
            }
        }

//        this.buildingName = values[15];

//        this.uniqueRoadName = values[8]; // 도로명

        this.uniqueRoadName = values[8]; // 도로명 + 건물번호본번 + 건물번호부번
        if (values[11] != null) {
            this.uniqueRoadName += " " + values[11];
            if (values[12] != null) {
                this.uniqueRoadName += "-" + values[12];
            }
        }
    }

    public String condition() {
        return this.area0 + this.area1 + this.area2 + this.area3 + this.uniqueRoadName;
    }

}
