package com.hulkaach.hr_skill_ninja.repository;

import com.hulkaach.hr_skill_ninja.model.Candidate;
import com.hulkaach.hr_skill_ninja.model.CandidateStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CandidateRepositoryJDBCTemplateImpl implements CandidateRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final JdbcTemplate jdbcTemplate;
    private static final RowMapper<Candidate> ROW_MAPPER = ((rs, rowNum) -> {
        Candidate candidate = new Candidate();
        candidate.setId(rs.getObject("id", UUID.class));
        candidate.setFio(rs.getString("fio"));
        candidate.setAge(rs.getShort("age"));
        candidate.setPosition(rs.getString("position"));
        candidate.setCvInfo(rs.getString("cv_info"));
        candidate.setComment(rs.getString("comment"));
        candidate.setStatus(CandidateStatus.valueOf(rs.getString("status")));
        return candidate;
    });

    @Override
    public Candidate save(Candidate candidate) {
        String sql = """
                INSERT INTO candidates (id, fio, age, position, cv_info, status)
                VALUES (?,?,?,?,?,?)
                RETURNING id, fio, age, position, cv_info, comment, status
                """;
        log.info("Save candidate by jdbc template");
        return jdbcTemplate.queryForObject(sql, ROW_MAPPER,
                candidate.getId(),
                candidate.getFio(),
                candidate.getAge(),
                candidate.getPosition(),
                candidate.getCvInfo(),
                candidate.getStatus().name()
        );
    }

    @Override
    public void saveAll(List<Candidate> candidates) {
        String sql = """
                INSERT INTO candidates (id, fio, age, position, cv_info, status)
                VALUES (:id, :fio, :age, :position, :cvInfo, :status)
                """;
        log.info("Save all candidates by Named JDBC template");
        SqlParameterSource[] batch = candidates.stream()
                .map(this::toParamSource)
                .toArray(SqlParameterSource[]::new);
        namedParameterJdbcTemplate.batchUpdate(sql, batch);

//        jdbcTemplate.batchUpdate(
//                sql,
//                candidates,
//                5,
//                (ps, candidate) -> {
//                    ps.setObject(1, candidate.getId());
//                    ps.setString(2, candidate.getFio());
//                    ps.setShort(3, candidate.getAge());
//                    ps.setString(4, candidate.getPosition());
//                    ps.setString(5, candidate.getCvInfo());
//                    ps.setString(6, candidate.getStatus().name());
//                }
//        );
    }

    private MapSqlParameterSource toParamSource(Candidate candidate) {
        return new MapSqlParameterSource()
                .addValue("id", candidate.getId())
                .addValue("fio", candidate.getFio())
                .addValue("age", candidate.getAge())
                .addValue("position", candidate.getPosition())
                .addValue("cvInfo", candidate.getCvInfo())
                .addValue("status", candidate.getStatus().name(), Types.VARCHAR);
    }

    @Override
    public Candidate update(Candidate candidate) {
        String sql = """
                UPDATE candidates
                SET fio = ?, age = ?, position = ?, cv_info = ?,
                    status = ?, comment = ?
                WHERE id = ?
                RETURNING id,fio,age, position, cv_info, comment, status    
                """;
        log.info("Update candidate by jdbc template");
        return jdbcTemplate.queryForObject(sql, ROW_MAPPER,
                candidate.getFio(),
                candidate.getAge(),
                candidate.getPosition(),
                candidate.getCvInfo(),
                candidate.getStatus().name(),
                candidate.getComment(),
                candidate.getId()
        );
    }

    @Override
    public Optional<Candidate> findById(UUID id) {
        String sql = """
                SELECT *
                FROM candidates
                WHERE id = ?
                """;
        log.info("Find by ID candidate by jdbc template");
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            sql, ROW_MAPPER, id
                    )
            );
        } catch (Exception e) {
            log.error("Error!", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Candidate> findAll() {
        String sql = """
                SELECT *
                FROM candidates
                ORDER BY fio ASC
                """;

        log.info("Find all candidates by jdbc template");
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    @Override
    public List<Candidate> search(String fio, Set<CandidateStatus> statuses, String position) {
        StringBuilder sql = new StringBuilder("""
                SELECT id, fio, age, position, cv_info, comment, status
                FROM candidates
                WHERE 1=1
                """);
        List<Object> params = new ArrayList<>();

        if (fio != null && !fio.isBlank()) {
            sql.append(" AND fio LIKE ?");
            params.add("%" + fio + "%");
        }

        if (statuses != null && !statuses.isEmpty()) {
            sql.append(" AND status = ANY(?)");
            params.add(statuses.stream().map(Enum::name).toArray(String[]::new));
        }

        if (position != null && !position.isBlank()) {
            sql.append(" AND position LIKE ?");
            params.add("%" + position + "%");
        }

        sql.append(" ORDER BY fio ASC");

        log.info("Search candidates by jdbc template");
        return jdbcTemplate.query(sql.toString(),
                ROW_MAPPER,
                params.toArray());
    }
}