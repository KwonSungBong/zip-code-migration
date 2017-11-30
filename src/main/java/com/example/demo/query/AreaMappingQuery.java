package com.example.demo.query;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Slf4j
@Component
public class AreaMappingQuery {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public int drop() {
        final String sql = "DROP TABLE IF EXISTS `area_zip_code_mapping_temp`";
        int result = entityManager
                .createNativeQuery(sql)
                .executeUpdate();
        return result;
    }

    @Transactional
    public int create() {
        StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE IF NOT EXISTS `area_zip_code_mapping_temp` (  ");
        sql.append("     `id` int(11) NOT NULL AUTO_INCREMENT,  ");
        sql.append("    `new_zip_code` char(5) COLLATE utf8_unicode_ci NOT NULL, ");
        sql.append("    `old_zip_code` char(6) COLLATE utf8_unicode_ci NOT NULL, ");
        sql.append("     PRIMARY KEY (`id`)");
        sql.append("   ) ENGINE=InnoDB DEFAULT CHARSET=utf8  ");

        log.debug("create sql={}", sql);
        return entityManager
                .createNativeQuery(sql.toString())
                .executeUpdate();
    }

    public List<Object[]> getArea01() {
        Query q = entityManager.createNativeQuery("SELECT DISTINCT area0, area1 FROM old_area");
        return q.getResultList();
    }

    public List<Object[]> getNotInNewArea(String area0, String area1) {
        final String query = "SELECT * FROM new_area " +
                "WHERE area0='"+area0+"' AND area1='"+area1+"' " +
                "AND zip_code NOT IN (SELECT new_zip_code FROM area_zip_code_mapping)";
        Query q = entityManager.createNativeQuery(query);
        return q.getResultList();
    }

    public List<Object[]> getNotInOldArea(String area0, String area1) {
        final String query = "SELECT * FROM old_area " +
                "WHERE area0='"+area0+"' AND area1='"+area1+"' " +
                "AND zip_code NOT IN (SELECT old_zip_code FROM area_zip_code_mapping)";
        Query q = entityManager.createNativeQuery(query);
        return q.getResultList();
    }

    @Transactional
    public int insert(String area0, String area1) {
        log.debug("area0={}, area1={}", area0, area1);

        final String query = "INSERT INTO area_zip_code_mapping_temp (new_zip_code, old_zip_code) " +
                "SELECT DISTINCT a.zip_code new_zip_code, b.zip_code old_zip_code " +
                "FROM (SELECT * FROM new_area WHERE area0='"+area0+"' AND area1='"+area1+"') a " +
                "JOIN (SELECT * FROM old_area WHERE area0='"+area0+"' AND area1='"+area1+"') b " +
                "ON a.area0=b.area0 AND a.area1=b.area1 AND a.area2=b.area2 AND a.area3=b.area3 " +
                "AND a.unique_road_name=b.unique_road_name";
        int result = entityManager.createNativeQuery(query).executeUpdate();
        return result;
    }

    @Transactional
    public int insert() {
        final String query = "INSERT INTO area_zip_code_mapping (new_zip_code, old_zip_code) " +
                "SELECT DISTINCT a.zip_code new_value, b.zip_code old_value " +
                "FROM (SELECT * FROM new_area) a " +
                "JOIN (SELECT * FROM old_area) b " +
                "ON a.area0=b.area0 AND a.area1=b.area1 AND a.area2=b.area2 AND a.area3=b.area3 " +
                "AND a.unique_road_name=b.unique_road_name";
        int result = entityManager.createNativeQuery(query).executeUpdate();
        return result;
    }

}