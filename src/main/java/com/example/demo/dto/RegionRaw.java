package com.example.demo.dto;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by ksb on 2018. 1. 14..
 */
@Data
public class RegionRaw {

    public static final String SEPARATOR = " ";

    private String zipCode;

    private String depth1Name;
    private String depth2Name;
    private String depth3Name;
    private String depth4Name;
    private String depth5Name;

    public RegionRaw() {
    }

    public RegionRaw(String line) {
        String[] values = line.split("\\|");
        zipCode = values[0];
        depth5Name = values[0];
        depth1Name = values[1];
        depth2Name = values[3];

        // 1      3      5      17     18   19
        // 강원도, 강릉시, 강동면, "", 모전리, 강동명  -> "강원도 강릉시 강동면 모전리"
        // 경기도, 평택시, "",    통북동, "",  원평동 -> "경기도 평택시 통북동"
        // 세종시, "",   금남면,  "", 용포리, ""     -> "세종시 금남면 용포"
        // 강원도, 강릉시, "",   교동,   "",  교2동  -> "강원도 강릉시 교동 교2동"

        // 읍면
        if (hasValue(values[5])) { // 읍면이 있다면
            depth3Name = values[5];
            if (hasValue(values[17])) {
                depth4Name = values[17]; // 읍면 + 법정동
            } else {
                depth4Name = values[18]; // 읍면 + 리
            }
        } else {
            if (hasValue(values[17])) { // 동이 있다면
                depth3Name = values[17]; // 동
            } else {
                depth3Name = values[18]; // 리
            }
        }
    }

    public String getName(int depth) {
        if(depth == 1) {
            return depth1Name;
        } else if(depth == 2) {
            return depth2Name;
        } else if(depth == 3) {
            return depth3Name;
        } else if(depth == 4) {
            return depth4Name;
        } else if(depth == 5) {
            return zipCode;
        } else {
            return "";
        }
    }

    public String getParentFullName(int depth) {
        int parentDepth = depth - 1;
        return getFullName(parentDepth);
    }

    public String getFullName(int depth) {
        StringBuilder fullName = new StringBuilder();
        if(depth == 1) {
            fullName.append(depth1Name);
        }
        if(depth == 2) {
            fullName.append(depth1Name);
            fullName.append(SEPARATOR);
            fullName.append(depth2Name);
        }
        if(depth == 3) {
            fullName.append(depth1Name);
            fullName.append(SEPARATOR);
            fullName.append(depth2Name);
            fullName.append(SEPARATOR);
            fullName.append(depth3Name);
        }
        if(depth == 4) {
            fullName.append(depth1Name);
            fullName.append(SEPARATOR);
            fullName.append(depth2Name);
            fullName.append(SEPARATOR);
            fullName.append(depth3Name);
            fullName.append(SEPARATOR);
            fullName.append(depth4Name);
        }
        if(depth == 5) {
            fullName.append(depth1Name);
            fullName.append(SEPARATOR);
            fullName.append(depth2Name);
            fullName.append(SEPARATOR);
            fullName.append(depth3Name);
            fullName.append(SEPARATOR);
            fullName.append(depth4Name);
            fullName.append(SEPARATOR);
            fullName.append(depth5Name);
        }

        return fullName.toString();
    }

    private final boolean hasValue(String name) {
        return !StringUtils.isEmpty(name);
    }

}
