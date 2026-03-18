package com.ecommerce.order.models.enums;

public enum Estado {

	ACTIVO(1), 
    INACTIVO(0);
	
	private final Integer codigo;

    Estado(Integer codigo) {
        this.codigo = codigo;
    }
    
    public Integer getCodigo() {
        return codigo;
    }
	
}