package com.hulkaach.hr_skill_ninja.repository;

import com.hulkaach.hr_skill_ninja.model.Candidate;
import com.hulkaach.hr_skill_ninja.model.CandidateStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
@Primary
@Slf4j
@RequiredArgsConstructor
public class CandidateHibernateRepositoryImpl implements CandidateRepository {
    private final EntityManager entityManager;

    @Override
    @Transactional
    public Candidate save(Candidate candidate) {
        log.info("Save candidate via JPA");
        entityManager.persist(candidate);
        return candidate;
    }

    @Override
    @Transactional
    public Candidate update(Candidate candidate) {
        log.info("Update candidate via JPA");
        return entityManager.merge(candidate);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Candidate> findById(UUID id) {
        log.info("Find candidate by Id via JPA");
        Candidate candidate = entityManager.find(Candidate.class, id);
        return Optional.ofNullable(candidate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Candidate> findAll() {
        log.info("Find all candidates via JPA");
        TypedQuery<Candidate> query = entityManager.createQuery("FROM Candidate", Candidate.class);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Candidate> search(String fio, Set<CandidateStatus> statuses, String position) {
        log.info("Search candidates via JPA");
        String hql = buildSearchHQL(fio, statuses, position);

        TypedQuery<Candidate> query = entityManager.createQuery(hql, Candidate.class);

        if (fio != null && !fio.trim().isEmpty()) {
            query.setParameter("fio", "%" + fio.trim() + "%");
        }

        if (statuses != null && !statuses.isEmpty()) {
            query.setParameter("statuses", "%" + statuses + "%");
        }

        if (position != null && !position.trim().isEmpty()) {
            query.setParameter("position", "%" + position.trim() + "%");
        }

        return query.getResultList();
    }

    @Override
    public void deleteById(UUID id) {
        log.info("Delete candidate by Id via JPA");
        Candidate candidate = entityManager.find(Candidate.class, id);
        if (candidate != null) {
            entityManager.remove(candidate);
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