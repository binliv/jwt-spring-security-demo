package org.zerhusen.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerhusen.model.security.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
