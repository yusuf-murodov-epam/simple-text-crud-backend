package com.example.simpletextcrud.service.mapper;

import com.example.simpletextcrud.common.CryptUtils;
import com.example.simpletextcrud.domain.dto.GetSimpleTextDTO;
import com.example.simpletextcrud.domain.entity.SimpleText;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * <code>GetSimpleTextMapper<code/> extension of the {@link BaseMapper} to map between {@link SimpleText} and {@link GetSimpleTextDTO}.
 */
@Mapper(config = BaseMapper.class)
public interface GetSimpleTextMapper extends BaseMapper<SimpleText, GetSimpleTextDTO> {

  /**
   * Anonymize the identity by encrypting after mapping.
   * @param simpleText the {@link SimpleText} entity.
   * @param getSimpleTextDTO the {@link GetSimpleTextDTO} dto.
   */
  @AfterMapping
  default void cryptIdentity(SimpleText simpleText, @MappingTarget GetSimpleTextDTO getSimpleTextDTO) {
    getSimpleTextDTO.setId(CryptUtils.encrypt(simpleText.getId()));
  }

  /**
   * Deanonymize the identity by encrypting after mapping.
   * @param simpleText the {@link SimpleText} entity.
   * @param getSimpleTextDTO the {@link GetSimpleTextDTO} dto.
   */
  @AfterMapping
  default void decryptIdentity(GetSimpleTextDTO getSimpleTextDTO, @MappingTarget SimpleText simpleText) {
    simpleText.setId(CryptUtils.decrypt(getSimpleTextDTO.getId(), Integer.class));
  }

  @Mapping(target = "id", ignore = true)
  SimpleText toEntity(GetSimpleTextDTO dto);

  @Mapping(target = "id", ignore = true)
  GetSimpleTextDTO toDto(SimpleText dto);
}
