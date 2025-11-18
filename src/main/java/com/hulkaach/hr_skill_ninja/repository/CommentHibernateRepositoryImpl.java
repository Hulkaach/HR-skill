package com.hulkaach.hr_skill_ninja.repository;

import com.hulkaach.hr_skill_ninja.model.Candidate;
import com.hulkaach.hr_skill_ninja.model.Comment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Primary
@RequiredArgsConstructor
@Slf4j
public class CommentHibernateRepositoryImpl implements CommentRepository {
    private final EntityManager entityManager;

    @Override
    @Transactional
    public Comment save(Comment comment) {
        log.info("Save comment via JPA");
        entityManager.merge(comment);
        return comment;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Comment> findById(UUID id) {
        log.info("Find comment by Id via JPA");
        Comment comment = entityManager.find(Comment.class, id);
        return Optional.ofNullable(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Candidate> findAll() {
        log.info("Fina all candidates via JPA");
        TypedQuery<Candidate> query = entityManager.createQuery("FROM Candidate", Candidate.class);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findAllByCandidate(Candidate candidate) {
        log.info("Find all candidates via JPA");
        String hql = "FROM Comment c WHERE c.candidate = :candidate:";
        return entityManager.createQuery(hql, Comment.class)
                .setParameter("candidate", candidate)
                .getResultList();
    }
}