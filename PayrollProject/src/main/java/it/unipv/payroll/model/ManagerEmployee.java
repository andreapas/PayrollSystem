package it.unipv.payroll.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "Manager")
public class ManagerEmployee extends FlatEmployee{

	}

