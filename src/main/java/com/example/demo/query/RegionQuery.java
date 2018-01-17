package com.example.demo.query;

import com.example.demo.entity.QRegion;
import com.example.demo.entity.Region;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static com.example.demo.service.RegionRawBuilder.*;

@Slf4j
@Repository
public class RegionQuery {

    private static final QRegion qRegion = QRegion.region;

    @PersistenceContext
    private EntityManager entityManager;

    public Integer loadLastId() {
        return new JPAQueryFactory(entityManager)
                .select(qRegion.id)
                .from(qRegion)
                .orderBy(qRegion.id.desc())
                .limit(1)
                .fetchFirst();
    }

    public Integer loadRootRegionId() {
        return new JPAQueryFactory(entityManager)
                .select(qRegion.id)
                .from(qRegion)
                .where(qRegion.depth.eq(ROOT_DEPTH))
                .fetchOne();
    }

    public Integer loadEtcRegionId() {
        return new JPAQueryFactory(entityManager)
                .select(qRegion.id)
                .from(qRegion)
                .where(qRegion.depth.eq(BEST_DEPTH)
                        .and(qRegion.name.eq(ETC_NAME)))
                .limit(1)
                .fetchFirst();
    }

    public List<Region> loadRegion() {
        List<Region> resultList = new JPAQueryFactory(entityManager)
                .selectFrom(qRegion)
                .orderBy(qRegion.id.desc())
                .fetch();
        return resultList;
    }

}