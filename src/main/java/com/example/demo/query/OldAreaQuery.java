package com.example.demo.query;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Component
public class OldAreaQuery {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public int drop() {
        final String sql = "DROP TABLE IF EXISTS `old_area`";
        int result = entityManager
                .createNativeQuery(sql)
                .executeUpdate();
        return result;
    }

    @Transactional
    public int create() {

        StringBuilder sql = new StringBuilder();
        sql.append(" CREATE TABLE IF NOT EXISTS `old_area` (  ");
        sql.append("     `id` int(11) NOT NULL AUTO_INCREMENT,  ");
        sql.append("    `area0` varchar(255) COLLATE utf8_unicode_ci NOT NULL, ");
        sql.append("    `area1` varchar(255) COLLATE utf8_unicode_ci NOT NULL, ");
        sql.append("    `area2` varchar(255) COLLATE utf8_unicode_ci NOT NULL, ");
        sql.append("    `area3` varchar(255) COLLATE utf8_unicode_ci NOT NULL, ");
        sql.append("     `zip_code` char(6) COLLATE utf8_unicode_ci DEFAULT NULL, ");
        sql.append("     `unique_road_name` varchar(255) DEFAULT NULL, ");
        sql.append("     PRIMARY KEY (`id`)");
        sql.append("   ) ENGINE=InnoDB DEFAULT CHARSET=utf8  ");

        log.debug("create sql={}", sql);
        return entityManager
                .createNativeQuery(sql.toString())
                .executeUpdate();
    }

    @Transactional
    public int insertWriteFile(String absoluteFileName) {
        log.debug("absoluteFileName={}", absoluteFileName);

        final String loadDataFileSql
                = "LOAD DATA LOCAL INFILE '" + absoluteFileName
                + "' INTO TABLE old_area CHARACTER SET UTF8 FIELDS TERMINATED BY '|' \n LINES TERMINATED BY '\\n'";
        int result = entityManager.createNativeQuery(loadDataFileSql).executeUpdate();
        return result;
    }

}