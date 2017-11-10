package org.zerhusen.security.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zerhusen.model.security.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {

    Page<Report> findByTesteeContaining(String testee, Pageable pageable);

    Page<Report> findByTesterContaining(String tester, Pageable pageable);

    Page<Report> findByTesteeContainingAndTester(String testee, String tester, Pageable pageable);

    Page<Report> findByTesteeContainingAndTesterContaining(String testee, String tester, Pageable pageable);

    Page<Report> findByTester(String tester, Pageable pageable);
}
