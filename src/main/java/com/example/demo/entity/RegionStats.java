package com.example.demo.entity;

import com.example.demo.dto.RegionStatsDto;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.Date;

@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "RegionStatsDto",
                classes = {
                        @ConstructorResult(
                                targetClass = RegionStatsDto.class,
                                columns = {
                                        @ColumnResult(name = "region1", type = Integer.class),
                                        @ColumnResult(name = "region2", type = Integer.class),
                                        @ColumnResult(name = "region3", type = Integer.class),
                                        @ColumnResult(name = "region1Name", type = String.class),
                                        @ColumnResult(name = "region2Name", type = String.class),
                                        @ColumnResult(name = "region3Name", type = String.class),
                                        @ColumnResult(name = "count", type = Integer.class)
                                }
                        )
                }
        )
})
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "selectRegionStatsDtoList",
                query = "SELECT depth1 AS 'region1', depth2 AS 'region2', depth3 AS 'region3', " +
                        "depth1name AS 'region1Name', depth2name AS 'region2Name', depth3name AS 'region3Name', " +
                        "COUNT(*) AS 'count' " +
                        "FROM region " +
                        "WHERE depth1 IS NOT NULL AND depth2 IS NOT NULL AND depth3 IS NOT NULL " +
                        "GROUP BY depth1, depth2, depth3, depth1name, depth2name, depth3name",
                resultSetMapping = "RegionStatsDto"
        ),
        @NamedNativeQuery(
                name    = "migration",
                query   = "INSERT INTO region_stats(region1, region2, region3, count, modified_at) " +
                        "SELECT depth1, depth2, depth3, count(*), now() " +
                        "FROM region " +
                        "WHERE depth1 IS NOT NULL AND depth2 IS NOT NULL AND depth3 IS NOT NULL " +
                        "GROUP BY depth1, depth2, depth3 " +
                        "ON DUPLICATE KEY UPDATE count=VALUES(count), modified_at=now()"
        )
})
@Entity
@Data
@IdClass(RegionStatsId.class)
public class RegionStats {

    @Id
    @OneToOne
    @JoinColumn(name = "region1")
    private Region region1;

    @Id
    @OneToOne
    @JoinColumn(name = "region2")
    private Region region2;

    @Id
    @OneToOne
    @JoinColumn(name = "region3")
    private Region region3;

    @Null
    private Integer count;

    @Null
    private Date modifiedAt;

}
