package com.icommerce.backend.domain.converter;

import com.icommerce.backend.domain.type.PersistableEnum;
import java.util.stream.Stream;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Base class for converting enum to persistable entity.
 * <p>
 *   Subclasses should extends this class, and provide suitable T,E classes.
 * </p>
 */
@Converter
public class AbstractEnumConverter<T extends Enum<T> & PersistableEnum<E>, E>
    implements AttributeConverter<T, E> {

  private final Class<T> clazz;

  public AbstractEnumConverter(Class<T> clazz) {
    this.clazz = clazz;
  }

  @Override
  public E convertToDatabaseColumn(T attribute) {
    return attribute != null ? attribute.getCode() : null;
  }

  @Override
  public T convertToEntityAttribute(E dbData) {
    if (dbData == null) {
      return null;
    }

    return Stream.of(clazz.getEnumConstants())
        .filter(status -> status.getCode().equals(dbData))
        .findFirst()
        .orElseThrow(UnsupportedOperationException::new);
  }
}
