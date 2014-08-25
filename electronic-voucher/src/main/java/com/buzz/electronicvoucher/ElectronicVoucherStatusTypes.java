package com.buzz.electronicvoucher;

public enum ElectronicVoucherStatusTypes {
	
	AUTHORIZED("AUTORIZADO");
	
	private String status;
	
	private ElectronicVoucherStatusTypes() {
		
	}
	
	private ElectronicVoucherStatusTypes(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}

}
