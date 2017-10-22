package org.zerhusen.security.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zerhusen.model.security.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {

    Page<Report> findByTesteeContaining(String testee, Pageable pageable);
}
