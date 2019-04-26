/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.pgupta25.web;

import edu.iit.sat.itmd4515.pgupta25.domain.Department;
import edu.iit.sat.itmd4515.pgupta25.service.DepartmentService;
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
@FacesConverter(value = "departmentConverter", managed = true)
public class DepartmentConverter implements Converter{
    public static List<Department> departments;
    @EJB
    public DepartmentService departmentSvc;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        departments = departmentSvc.findAll();
        if (s.trim().equals("")) {
            return null;
        } else {
            try {
                int number = Integer.parseInt(s);

                for (Department lt : departments) {
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
            return String.valueOf(((Department) o).getId());
        }
    }
} 
