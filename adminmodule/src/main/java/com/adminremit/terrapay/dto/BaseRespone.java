package com.adminremit.terrapay.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseRespone {

	@NotNull
	@JsonSerialize(using = StringSerializer.class)
	@JsonDeserialize(using = StringDeserializer.class)
	protected String status;
	
	
	@JsonSerialize(using = StringSerializer.class)
	@JsonDeserialize(using = StringDeserializer.class)
	protected String statusCode;
	
	@NotNull
	@JsonSerialize(using = StringSerializer.class)
	@JsonDeserialize(using = StringDeserializer.class)
	protected String message;
	

}
