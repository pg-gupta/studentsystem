/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.pgupta25.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author miteshpatekar
 */
@Entity
@Table(name = "grade")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grade.findAll", query = "SELECT g FROM Grade g")
    , @NamedQuery(name = "Grade.findByStudentId", query = "SELECT g FROM Grade g WHERE g.gradePK.studentId = :studentId")
    , @NamedQuery(name = "Grade.findByCourseId", query = "SELECT g FROM Grade g WHERE g.gradePK.courseId = :courseId")
    , @NamedQuery(name = "Grade.findByStudentIdAndCourse", query = "SELECT g FROM Grade g WHERE g.gradePK.studentId = :studentId AND g.gradePK.courseId = :courseId")
    , @NamedQuery(name = "Grade.findByGrade", query = "SELECT g FROM Grade g WHERE g.grade = :grade")})
public class Grade implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "grade")
    private String grade;

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GradePK gradePK;
    @JoinColumn(name = "course_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Course course;
    @JoinColumn(name = "student_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Student student;

    public Grade() {
    }

    public Grade(GradePK gradePK) {
        this.gradePK = gradePK;
    }

    public Grade(GradePK gradePK, String grade) {
        this.gradePK = gradePK;
        this.grade = grade;
    }

    public Grade(int studentId, int courseId) {
        this.gradePK = new GradePK(studentId, courseId);
    }

    public GradePK getGradePK() {
        return gradePK;
    }

    public void setGradePK(GradePK gradePK) {
        this.gradePK = gradePK;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gradePK != null ? gradePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grade)) {
            return false;
        }
        Grade other = (Grade) object;
        if ((this.gradePK == null && other.gradePK != null) || (this.gradePK != null && !this.gradePK.equals(other.gradePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.iit.sat.itmd4515.pgupta25.domain.Grade[ gradePK=" + gradePK + " ]";
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

}
