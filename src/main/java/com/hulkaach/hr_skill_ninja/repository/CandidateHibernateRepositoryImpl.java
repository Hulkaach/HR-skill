package com.hulkaach.hr_skill_ninja.repository;

import com.hulkaach.hr_skill_ninja.model.Candidate;
import com.hulkaach.hr_skill_ninja.model.CandidateStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Primary
@Slf4j
@RequiredArgsConstructor
public class CandidateHibernateRepositoryImpl implements CandidateRepository {
    private final SessionFactory sessionFactory;

    @Override
    public Candidate save(Candidate candidate) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            log.info("Save candidate via Hibernate");

            transaction = session.beginTransaction();

            session.persist(candidate);

            transaction.commit();

            return candidate;
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            log.error("Error transaction", e);
            throw e;
        }
    }

    @Override
    public Candidate update(Candidate candidate) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            log.info("Update candidate via Hibernate");

            transaction = session.beginTransaction();

            session.merge(candidate);

            transaction.commit();

            return candidate;
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            log.error("Error transaction", e);
            throw e;
        }
    }

    @Override
    public void saveAll(List<Candidate> candidates) {

    }

    @Override
    public Optional<Candidate> findById(UUID id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            log.info("Find candidate by ID via Hibernate");

            transaction = session.beginTransaction();

            Candidate candidate = session.get(Candidate.class, id);

            transaction.commit();

            return Optional.ofNullable(candidate);
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            log.error("Error transaction", e);
            throw e;
        }
    }

    @Override
    public List<Candidate> findAll() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            log.info("Find all candidates via Hibernate");

            transaction = session.beginTransaction();

            Query<Candidate> query = session.createQuery("FROM Candidate", Candidate.class);
            List<Candidate> candidates = query.list();
            transaction.commit();

            return candidates;
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            log.error("Error transaction", e);
            throw e;
        }
    }

    @Override
    public List<Candidate> search(String fio, Set<CandidateStatus> statuses, String position) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            log.info("Search candidates via Hibernate");

            transaction = session.beginTransaction();

            String hql = buildSearchHQL(fio, statuses, position);

            Query<Candidate> query = session.createQuery(hql, Candidate.class);

            if (fio != null && !fio.trim().isEmpty()) {
                query.setParameter("fio", "%" + fio.trim() + "%");
            }

            if (statuses != null && !statuses.isEmpty()) {
                query.setParameter("statuses", "%" + statuses + "%");
            }

            if (position != null && !position.trim().isEmpty()) {
                query.setParameter("position", "%" + position.trim() + "%");
            }

            List<Candidate> candidates = query.list();

            transaction.commit();

            return candidates;
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            log.error("Error transaction", e);
            throw e;
        }
    }

    private String buildSearchHQL(String fio, Set<CandidateStatus> statuses, String position) {
        StringBuilder hql = new StringBuilder("FROM Candidate WHERE 1=1");

        if (fio != null && !fio.trim().isEmpty()) {
            hql.append(" AND LOWER(fio) LIKE LOWER(:fio)");
        }

        if (statuses != null && !statuses.isEmpty()) {
            hql.append(" AND status IN (:statuses)");
        }

        if (position != null && !position.trim().isEmpty()) {
            hql.append(" AND LOWER(position) LIKE LOWER(:position)");
        }

        return hql.toString();
    }
}