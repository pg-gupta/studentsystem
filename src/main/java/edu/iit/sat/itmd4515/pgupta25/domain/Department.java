/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.pgupta25.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author miteshpatekar
 */
@Entity
@Table(name = "department")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Department.findAll", query = "SELECT d FROM Department d")
    , @NamedQuery(name = "Department.findById", query = "SELECT d FROM Department d WHERE d.id = :id")
    , @NamedQuery(name = "Department.findByName", query = "SELECT d FROM Department d WHERE d.name = :name")})
public class Department implements Serializable {

    @Basic(optional = false)
    @NotBlank
    @Size(min = 1, max = 45)
    @Column(name = "name",nullable = false)
    private String name;
    @Basic(optional = false)
    @NotBlank
    @Size(min = 1, max = 45)
    @Column(name = "location",nullable = false)
    private String location;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "departmentId")
    private Collection<Teacher> teacherCollection = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "departmentId")
    private Collection<Course> courseCollection=new ArrayList<>();

    public Department() {
    }
  
    public Department(String name, String location) {
        this.name = name;
        this.location = location;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @XmlTransient
    public Collection<Teacher> getTeacherCollection() {
        return teacherCollection;
    }

    public void setTeacherCollection(Collection<Teacher> teacherCollection) {
        this.teacherCollection = teacherCollection;
    }

    @XmlTransient
    public Collection<Course> getCourseCollection() {
        return courseCollection;
    }

    public void setCourseCollection(Collection<Course> courseCollection) {
        this.courseCollection = courseCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Department)) {
            return false;
        }
        Department other = (Department) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.iit.sat.itmd4515.pgupta25.domain.Department[ id=" + id + " ]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    /**
     * addCourse helper method to add course to the department
     * @param c 
     */
    public void addCourse(Course c){
        if (!this.courseCollection.contains(c)) {
            this.courseCollection.add(c);
        }
        if (c.getDepartmentId()==null) {
            c.setDepartmentId(this);
        }
    }
    
    /**
     * addTeacher helper method to add teacher to the department
     * @param t
     */
    public void addTeacher(Teacher t){
        if (!this.teacherCollection.contains(t)) {
            this.teacherCollection.add(t);
        }
        if (t.getDepartmentId()==null) {
            t.setDepartmentId(this);
        }
    }
    
}
