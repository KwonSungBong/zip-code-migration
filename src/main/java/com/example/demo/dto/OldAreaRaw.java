package com.example.demo.dto;

import lombok.Data;

@Data
public class OldAreaRaw extends AreaRaw {

    public OldAreaRaw() {}

    public OldAreaRaw(String line) {
        super(line);
    }

    // 우편번호|우편일련번호|시도  |시도영문    |시군구|시군구영문    |읍면 |읍면영문        |도로명코드    |도로명|도로명영문    |지하여부|건물번호본번|건물번호부번|건물관리번호               |다량배달처명|시군구용건물명|법정동코드  |법정동명 |리    |산여부  |지번본번 |읍면동일련번호|지번부번
    // 0      1          2      3           4     5            6    7              8            9     10          11      12         13         14                        15        16           17         18      19     20     21      22            23
    // 210821|001       |강원도 |Gangwon-do|강릉시|Gangneung-si|강동면|Gangdong-myeon|421503220011|단경로|Dangyeong-ro|0      |33        |0         |4215034022003470007044650|         |            |4215034022|       |모전리 |0     |347     |01          |7
    @Override
    public void parseLine(String line) {
        String[] values = line.split("\\|");
        setZipCode(values[0]);
        setArea0(values[2]);
        setArea1(values[4]);

        // 2      4      6      18     19   20
        // 강원도, 강릉시, 강동면, "", 모전리, 강동명  -> "강원도 강릉시 강동면 모전리"
        // 경기도, 평택시, "",    통북동, "",  원평동 -> "경기도 평택시 통북동"
        // 세종시, "",   금남면,  "", 용포리, ""     -> "세종시 금남면 용포"
        // 강원도, 강릉시, "",   교동,   "",  교2동  -> "강원도 강릉시 교동 교2동"

        // 읍면
        if (hasValue(values[6])) { // 읍면이 있다면
            setArea2(values[6]);
            if(hasValue(values[18])) {
                setArea3(values[18]); // 읍면 + 법정동
            } else{
                setArea3(values[19]); // 읍면 + 리
            }
        } else {
            if(hasValue(values[18])) { // 동이 있다면
                setArea2(values[18]); // 동
            } else{
                setArea2(values[19]); // 리
            }
        }

        setUniqueRoadName(values[9]); // 도로명

        // 건물번호본번 + 건물번호부번
        if (values[12] != null) {
            String buildingName = values[12];
            if (values[13] != null) {
                buildingName += "-" + values[13];
            }
            setBuildingName(buildingName);
        }

    }

}
