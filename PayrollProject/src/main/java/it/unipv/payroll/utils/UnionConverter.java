package it.unipv.payroll.utils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import it.unipv.payroll.controller.UnionsController;
import it.unipv.payroll.model.IUnion;

@FacesConverter("unionTranslator")
public class UnionConverter implements Converter{

	@Inject UnionsController controller;
	
	@Override
	public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            try {
            	IUnion union= controller.find(value);
                return union;
            } catch(ClassCastException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Union."));
            } catch (Exception e) {
				FacesContext context =FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error!", e.getMessage()));
				return null;
			}
        } else {
            return null;
        }
    }
	@Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
        	String name=(((IUnion) object).getUnionName());
            return name;
        }else {
            return null;
        }
    }   

}
