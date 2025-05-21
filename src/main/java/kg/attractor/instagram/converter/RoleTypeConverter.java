package kg.attractor.instagram.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kg.attractor.instagram.entity.RoleType;


@Converter(autoApply = true)
public class RoleTypeConverter implements AttributeConverter<RoleType, Long> {

    @Override
    public Long convertToDatabaseColumn(RoleType accountType) {
        if (accountType == null) {
            return null;
        }
        return accountType.getId();
    }

    @Override
    public RoleType convertToEntityAttribute(Long id) {
        if (id == null) {
            return null;
        }
        return RoleType.getById(id);
    }
}
