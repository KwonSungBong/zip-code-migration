//package com.example.demo.dto;
//
//import lombok.Data;
//import org.apache.commons.lang3.StringUtils;
//
///**
// * Created by ksb on 2018. 1. 14..
// */
//@Data
//public class RegionRaw_back {
//
//    private Integer id;
//    private String name;
//    private String zipCode;
//    private String fullName;
//    private Integer depth;
//    private boolean enabled = true;
//    private String description;
//    private Integer parent;
//
//    private String area0; // 강원도
//    private String area1; // 강릉시
//    private String area2; // 강동면
//    private String area3; // 모전리
//    private String area4; // area3까지도 같을 경우 zipCode로 area4의 값으로 한다.
//    private String uniqueRoadName;
//
//    public RegionRaw_back() {}
//
//    public RegionRaw_back(AreaRaw areaRaw) {
//        this.zipCode = areaRaw.getZipCode();
//        this.area0 = areaRaw.getArea0();
//        this.area1 = areaRaw.getArea1();
//        this.area2 = areaRaw.getArea2();
//        this.area3 = areaRaw.getArea3();
//        this.uniqueRoadName = areaRaw.getUniqueRoadName();
//
//        compactAreas();
//        this.fullName = buildFullName();
//        setNameAndLevel();
//    }
//
//    public RegionRaw_back(int id, String name, String fullName, int depth, Integer parent) {
//        this(id, name, fullName, null, depth, parent);
//    }
//
//    public RegionRaw_back(int id, String name, String fullName, String zipCode, int depth, Integer parent) {
//        this.id = id;
//        this.name = name;
//        this.fullName = fullName;
//        this.zipCode = zipCode;
//        this.depth = depth;
//        this.parent = parent;
//    }
//
//    private void compactAreas() {
//        // 세종시 같은 경우 "세종특별자치시", "", "가람동" 요렇게 area1이 빈경우가 있다. 요걸 "세종특별자치시", "가람동", ""로 바꾸어 준다.
//        if(StringUtils.isEmpty(area1)) {
//            area1 = area2;
//            area2 = area3;
//            area3 = "";
//        }
//        if(StringUtils.isEmpty(area2)) {
//            area2 = area3;
//            area3 = "";
//        }
//    }
//
//    private String buildFullName() {
//        return this.area0
//                +appendingValue(this.area1)
//                +appendingValue(this.area2)
//                +appendingValue(this.area3)
//                +appendingValue(this.area4);
//    }
//
//    private String appendingValue(String value) {
//        if(StringUtils.isEmpty(value)) { return ""; }
//        return " "+value;
//    }
//
//    public void addZipCodeAsArea() {
//        if(depth==1) {
//            area2 = zipCode;
//        }
//        else if(depth==2) {
//            area3 = zipCode;
//        }
//        else if(depth==3) {
//            area4 = zipCode;
//        }
//        else {
//            throw new RuntimeException("unable to add zipCode to area or already added."+this);
//        }
//        name = zipCode;
//        depth++;
//        fullName = buildFullName();
//    }
//
//    private void setNameAndLevel() {
//        name = area3;
//        depth = 3;
//        if(StringUtils.isEmpty(name)) { name = area2; depth = 2; }
//        if(StringUtils.isEmpty(name)) { name = area1; depth = 1; }
//    }
//
//}
