package ru.list.surkovr.gymservice.repositories;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.list.surkovr.gymservice.domain.QWorkout;
import ru.list.surkovr.gymservice.domain.Workout;
import ru.list.surkovr.gymservice.domain.WorkoutSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

@Repository
@Transactional
public class WorkoutRepositoryImpl implements WorkoutRepository {

    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    @Autowired
    public WorkoutRepositoryImpl(/*@Qualifier("entityManagerFactory")*/
            EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        this.entityManager = entityManager;
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

    @Override
    public Workout save(Workout workout) {
        if (nonNull(workout.getId())) {
            entityManager.merge(workout);
        } else {
            entityManager.persist(workout);
        }
        return workout;
    }

    @Override
    public WorkoutSet save(WorkoutSet workoutSet) {
        if (nonNull(workoutSet.getId())) {
            entityManager.merge(workoutSet);
        } else {
            entityManager.persist(workoutSet);
        }
        return workoutSet;
    }

    @Override
    public Workout findOne(Long id) {
        QWorkout qWorkout = QWorkout.workout;
        return queryFactory.selectFrom(qWorkout).where(qWorkout.id.eq(id)).fetchFirst();
    }

    @Override
    public void deleteById(Long id) {
        QWorkout qWorkout = QWorkout.workout;
        queryFactory.delete(qWorkout).where(qWorkout.id.eq(id)).execute();
    }
}
