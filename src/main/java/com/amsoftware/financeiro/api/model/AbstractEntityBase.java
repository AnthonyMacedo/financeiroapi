package com.amsoftware.financeiro.api.model;

import java.io.Serializable;


public abstract class AbstractEntityBase<ID> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public abstract ID getId();
	
	
}
