package com.hulkaach.hr_skill_ninja.repository;

import com.hulkaach.hr_skill_ninja.model.Candidate;
import com.hulkaach.hr_skill_ninja.model.Position;
import com.hulkaach.hr_skill_ninja.model.PositionStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PositionHibernateRepositoryImpl implements PositionRepository {
    private final SessionFactory sessionFactory;

    @Override
    public void save(Position position) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            log.info("Save position via Hibernate");

            transaction = session.beginTransaction();

            session.merge(position);

            transaction.commit();
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            log.error("Error", e);
            throw e;
        }
    }

    @Override
    public Optional<Position> findById(UUID id) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            log.info("Find position by Id via Hibernate");

            transaction = session.beginTransaction();

            Position position = session.get(Position.class, id);
            transaction.commit();
            return Optional.ofNullable(position);
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            log.error("Error", e);
            throw e;
        }
    }

    @Override
    public List<Position> findAll(UUID id) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            log.info("Find all position via Hibernate");

            transaction = session.beginTransaction();

            Query<Position> positionQuery = session.createQuery("FROM Position", Position.class);
            List<Position> positionList = positionQuery.list();
            transaction.commit();
            return positionList;
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            log.error("Error", e);
            throw e;
        }
    }

    @Override
    public void addCandidate(UUID positionId, UUID candidateId) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            log.info("Add candidate to position via Hibernate");

            transaction = session.beginTransaction();
            Position position = session.get(Position.class, positionId);
            Candidate candidate = session.get(Candidate.class, candidateId);

            if (position != null && candidate != null) {
                position.getCandidates().add(candidate);
            }

            transaction.commit();
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            log.error("Error", e);
            throw e;
        }
    }

    @Override
    public void archive(UUID positionId) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            log.info("Archive position via Hibernate");

            transaction = session.beginTransaction();
            Position position = session.get(Position.class, positionId);

            if (position != null) {
                position.setStatus(PositionStatus.ARCHIVE);
            }

            transaction.commit();
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            log.error("Error", e);
            throw e;
        }
    }
}