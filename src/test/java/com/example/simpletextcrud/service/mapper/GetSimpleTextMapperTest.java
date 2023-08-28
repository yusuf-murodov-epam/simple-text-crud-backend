package com.example.simpletextcrud.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.simpletextcrud.common.CryptUtils;
import com.example.simpletextcrud.config.TestsMappersConfig;
import com.example.simpletextcrud.domain.dto.GetSimpleTextDTO;
import com.example.simpletextcrud.domain.entity.SimpleText;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestsMappersConfig.class)
class GetSimpleTextMapperTest {
  @Autowired
  private GetSimpleTextMapper getSimpleTextMapper;
  private final GetSimpleTextDTO simpleTextDTO = new GetSimpleTextDTO("VGYEyAIOsIzTAjvdYfzvEA==", "Magic text in mapper test.");
  private final SimpleText simpleText = new SimpleText(123, "Magic text in mapper test.");

  @Test
  void testToDtoMapping() {
    final GetSimpleTextDTO getSimpleTextDTO = getSimpleTextMapper.toDto(simpleText);
    assertEquals(simpleText.getMagicText(), getSimpleTextDTO.getMagicText());
    assertEquals(simpleText.getId(), CryptUtils.decrypt(getSimpleTextDTO.getId(), Integer.class));
  }

  @Test
  void testToEntityMapping() {
    final SimpleText entity = getSimpleTextMapper.toEntity(simpleTextDTO);
    assertEquals(CryptUtils.decrypt(simpleTextDTO.getId(), Integer.class), entity.getId());
    assertEquals(simpleTextDTO.getMagicText(), entity.getMagicText());
  }
}
