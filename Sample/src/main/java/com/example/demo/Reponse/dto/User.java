package com.example.demo.Reponse.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class User {

	@Getter
	@Setter
	private String name ;
	
	@Getter
	@Setter
	private String phoneNumber;
	
	@Getter
	@Setter
	private String address;
	
	@Override
	public String toString()
	{
		return String.format("User { 'name' : '%s', 'phonenumber' : '%s', 'address' : '%s' }", 
				this.name, 
				this.phoneNumber, 
				this.address) ;
	}
}
