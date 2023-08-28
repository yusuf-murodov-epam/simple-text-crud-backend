package com.example.simpletextcrud.service.mapper;

import org.mapstruct.Builder;
import org.mapstruct.MapperConfig;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * <code>BaseMapper<code/> base interface containing mapping between entity and dto.
 * @param <E> entity.
 * @param <D> data transfer object.
 */
@MapperConfig(componentModel = "spring",
    builder = @Builder(disableBuilder = true),
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BaseMapper<E, D> {
  /**
   * Make an entity map to a data transfer object
   * @param e entity.
   * @return data transfer object.
   */
  D toDto(E e);

  /**
   * Make a dto (data transfer object) map to an entity.
   * @param d data transfer object.
   * @return entity.
   */
  E toEntity(D d);
}
