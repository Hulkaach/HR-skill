package com.hulkaach.hr_skill_ninja.repository;

import com.hulkaach.hr_skill_ninja.model.Candidate;
import com.hulkaach.hr_skill_ninja.model.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Primary
@RequiredArgsConstructor
@Slf4j
public class CommentHibernateRepositoryImpl implements CommentRepository {
    private final SessionFactory sessionFactory;

    @Override
    public Comment save(Comment comment) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            log.info("Save comment via Hibernate");

            transaction = session.beginTransaction();

            session.merge(comment);

            transaction.commit();

            return comment;
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            log.error("Error", e);
            throw e;
        }
    }

    @Override
    public Optional<Comment> findById(UUID id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            log.info("Find comment by Id via Hibernate");

            transaction = session.beginTransaction();

            Comment comment = session.get(Comment.class, id);

            transaction.commit();

            return Optional.ofNullable(comment);
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            log.error("Error", e);
            throw e;
        }
    }

    @Override
    public List<Candidate> findAll() {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            log.info("Fina all candidates via Hibernate");

            transaction = session.beginTransaction();

            Query<Candidate> query = session.createQuery("FROM Candidate", Candidate.class);
            List<Candidate> candidates = query.list();
            transaction.commit();

            return candidates;
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            log.error("Error", e);
            throw e;
        }
    }

    @Override
    public List<Comment> findAllByCandidate(Candidate candidate) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            log.info("Fina all candidates via Hibernate");

            transaction = session.beginTransaction();

            String hql = "FROM Comment c WHERE c.candidate = :candidate:";

            List<Comment> commentQueries = session.createQuery(hql, Comment.class)
                    .setParameter("candidate", candidate)
                    .getResultList();
            transaction.commit();

            return commentQueries;
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            log.error("Error", e);
            throw e;
        }
    }
}
