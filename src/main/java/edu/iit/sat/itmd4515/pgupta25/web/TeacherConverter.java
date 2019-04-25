/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.pgupta25.web;

import edu.iit.sat.itmd4515.pgupta25.domain.Teacher;
import edu.iit.sat.itmd4515.pgupta25.service.TeacherService;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author pooja gupta
 */
@FacesConverter(value = "teacherConverter", managed = true)
public class TeacherConverter implements Converter{
    public static List<Teacher> teachers;
    @EJB
    public TeacherService teacherSvc;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        teachers = teacherSvc.findAll();
        if (s.trim().equals("")) {
            return null;
        } else {
            try {
                int number = Integer.parseInt(s);

                for (Teacher lt : teachers) {
                    if (lt.getId() == number) {
                        return lt;
                    }
                }
            } catch(NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid converter"));
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        if (o == null || o.equals("")) {
            return "";
        } else {
            return String.valueOf(((Teacher) o).getId());
        }
    }
} 
