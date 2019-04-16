package com.desafiopitang.apirestful.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.desafiopitang.apirestful.util.Constantes;

@Embeddable
public class Phones  {
	
	@NotBlank(message = Constantes.MISSING_FIELDS)
	@Column(nullable = false)
    @Size(max = 10)
	private Number number;
    
	@NotBlank(message = Constantes.MISSING_FIELDS)
    @Column(nullable = false)
    @Size(max = 2)
	private Number area_code;
    
	@NotBlank(message = Constantes.MISSING_FIELDS)
    @Column(nullable = false)
    @Size(max = 3)
	private String country_code;

    public Phones() {
		
	}
    
    public Number getNumber() {
		return number;
	}

	public void setNumber(Number number) {
		this.number = number;
	}

	public Number getArea_code() {
		return area_code;
	}

	public void setArea_code(Number area_code) {
		this.area_code = area_code;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public Phones(Number number, Number area_code, String country_code) {
    	this.number=number;
    	this.area_code=area_code;
		this.country_code=country_code;
		
   	}
    
    
}