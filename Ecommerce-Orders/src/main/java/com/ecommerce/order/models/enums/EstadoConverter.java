package com.ecommerce.order.models.enums;



import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EstadoConverter implements AttributeConverter<Estado, Integer>{

	@Override
	public Integer convertToDatabaseColumn(Estado status) {
		if (status == null) {
            return null;
        }
        return status.getCodigo();
	}

	@Override
	public Estado convertToEntityAttribute(Integer statusDb) {
		if (statusDb == null) {
            return null;
        }

        
        for (Estado estado : Estado.values()) {
            if (estado.getCodigo().equals(statusDb)) {
                return estado;
            }
        }
        
        throw new IllegalArgumentException("Código desconocido: " + statusDb);
	}

}
