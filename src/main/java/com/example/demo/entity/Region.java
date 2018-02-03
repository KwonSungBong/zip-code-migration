package com.example.demo.entity;

import com.example.demo.dto.AreaRaw;
import com.example.demo.dto.RegionRaw;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Arrays;

@Entity
@Data
public class Region {

    public static final String SEPARATOR = " ";

    @Id
    private Integer id;
    private Integer parent;
    private String name;
    private String zipCode;
    private String description;
    private boolean enabled = true;

    private Integer depth;
    private Integer depth1; // 강원도
    private Integer depth2; // 강릉시
    private Integer depth3; // 강동면
    private Integer depth4; // 모전리
    private Integer depth5; // area3까지도 같을 경우 zipCode로 area4의 값으로 한다.
    private String depth1Name;
    private String depth2Name;
    private String depth3Name;
    private String depth4Name;
    private String depth5Name;

    public Region() {}

    public Region(AreaRaw areaRaw) {
        this.zipCode = areaRaw.getZipCode();
        this.depth1Name = areaRaw.getArea0();
        this.depth2Name = areaRaw.getArea1();
        this.depth3Name = areaRaw.getArea2();
        this.depth4Name = areaRaw.getArea3();
    }

    public Region(String line) {
        String[] values = line.split("\\|");

        id = values[0].equals("null") ? null : Integer.valueOf(values[0]);
        parent = values[1].equals("null") ? null : Integer.valueOf(values[1]);
        name = values[2].equals("null") ? null : values[2];
        zipCode = values[3].equals("null") ? null : values[3];
        description = values[4].equals("null") ? null : values[4];
        enabled  = values[5].equals("null") ? null : values[5].equals("true");
        depth = values[6].equals("null") ? null : Integer.valueOf(values[6]);
        depth1 = values[7].equals("null") ? null : Integer.valueOf(values[7]);
        depth2 = values[8].equals("null") ? null : Integer.valueOf(values[8]);
        depth3 = values[9].equals("null") ? null : Integer.valueOf(values[9]);
        depth4 = values[10].equals("null") ? null : Integer.valueOf(values[10]);
        depth5 = values[11].equals("null") ? null : Integer.valueOf(values[11]);
        depth1Name = values[12].equals("null") ? null : values[12];
        depth2Name = values[13].equals("null") ? null : values[13];
        depth3Name = values[14].equals("null") ? null : values[14];
        depth4Name = values[15].equals("null") ? null : values[15];
        depth5Name = values[16].equals("null") ? null : values[16];
    }

    public String getFullName() {
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

}
