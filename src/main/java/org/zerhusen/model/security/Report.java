package org.zerhusen.model.security;


import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "REPORTS")
public class Report {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_seq")
    @SequenceGenerator(name = "report_seq", sequenceName = "report_seq", allocationSize = 1)
    private Long id;

    @Column(name = "TESTER", length = 50)
    @NotNull
    private String tester;

    @Column(name = "TESTEE", length = 100)
    @NotNull
    private String testee;

    @Column(name = "TIME")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date time;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String cases;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String info;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTester() {
        return tester;
    }

    public void setTester(String tester) {
        this.tester = tester;
    }

    public String getTestee() {
        return testee;
    }

    public void setTestee(String testee) {
        this.testee = testee;
    }

    public Date getTime() {
        return time;
    }

    public long getMiliTime(){
        return time.getTime();
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getCases() { return cases; } public void setCases(String cases) { this.cases = cases; }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
