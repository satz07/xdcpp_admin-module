package com.adminremit.terrapay.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrxStatusRequest extends BaseRequest {
	
	@NotNull
	//Reference number returned by TerraPay during initiate transaction
	protected String terraPayReferenceNumber;
	
	

}
