package com.example.demo.dto;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public abstract class AreaRaw {

    private String zipCode;
    private String area0; // 시도
    private String area1; // 시군구
    private String area2; // 읍면
    private String area3;
    private String uniqueRoadName;
    private String buildingName;

    public AreaRaw() {}

    public AreaRaw(String line) {
        parseLine(line);
    }

    abstract protected void parseLine(String line);

    protected final boolean hasValue(String name) {
        return !StringUtils.isEmpty(name);
    }

    public final String mappingKey() {
        return this.area0 + this.area1 + this.area2 + this.area3 + this.uniqueRoadName;
    }

//    public void setArea0(String area0) {
//        this.area0 = area0.replaceAll("\\p{Z}","");
//    }
//
//    public void setArea1(String area1) {
//        this.area1 = area1.replaceAll("\\p{Z}","");
//    }
//
//    public void setArea2(String area2) {
//        this.area2 = area2.replaceAll("\\p{Z}","");
//    }
//
//    public void setArea3(String area3) {
//        this.area3 = area3.replaceAll("\\p{Z}","");
//    }

}
