package ru.list.surkovr.gymservice.repositories;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.list.surkovr.gymservice.domain.QWorkout;
import ru.list.surkovr.gymservice.domain.Workout;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Repository
@Transactional
public class WorkoutRepositoryImpl implements WorkoutRepository {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public WorkoutRepositoryImpl(@Qualifier("entityManagerFactory") EntityManager entityManager) {
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Workout> findAllByDateBetweenAndUserId(LocalDate dateFrom, LocalDate dateTo, Long userId) {
        QWorkout qWorkout = QWorkout.workout;

        List<Predicate> predicates = new ArrayList<>();
        if (nonNull(dateFrom)) {
            predicates.add(qWorkout.date.goe(dateFrom));
        }
        if (nonNull(dateTo)) {
            predicates.add(qWorkout.date.loe(dateTo));
        }
        if (nonNull(userId)) {
            predicates.add(qWorkout.user.id.eq(userId));
        }
        return queryFactory.selectFrom(qWorkout).where(ExpressionUtils.allOf(predicates)).fetch();
    }
}
