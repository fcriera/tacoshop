package com.lm2a.model;

import org.hibernate.validator.constraints.CreditCardNumber;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
public class Order {
	
	private Long id;
	private Date placedAt;
	
	@NotBlank(message="El nombre es obligatorio")
	private String name;
	@NotBlank(message="La calle es obligatoria")
	private String street;
	@NotBlank(message="La ciudad es obligatoria")
	private String city;
	@NotBlank(message="El estado es obligatorio")
	private String state;
	@NotBlank(message="El código postal es obligatorio")
	private String zip;
	@CreditCardNumber(message="No es una tarjeta válida")
	private String ccNumber;
	@Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$", message="Debe estar formateada como MM/YY")
	private String ccExpiration;
	@Digits(integer=3, fraction=0, message="CVV invalido")
	private String ccCVV;
	
	private List<Taco> tacos = new ArrayList<>();
	
	public void addDesign(Taco design) {
		tacos.add(design);
	}
}
