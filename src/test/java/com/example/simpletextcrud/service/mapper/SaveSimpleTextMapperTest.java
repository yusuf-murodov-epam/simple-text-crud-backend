package com.example.simpletextcrud.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.simpletextcrud.config.TestsMappersConfig;
import com.example.simpletextcrud.domain.dto.SaveSimpleTextDTO;
import com.example.simpletextcrud.domain.entity.SimpleText;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestsMappersConfig.class)
class SaveSimpleTextMapperTest {
  @Autowired
  private SaveSimpleTextMapper saveSimpleTextMapper;
  private final SaveSimpleTextDTO simpleTextDTO = new SaveSimpleTextDTO("Magic text in mapper test.");
  private final SimpleText simpleText = new SimpleText(0, "Magic text in mapper test");

  @Test
  void testToDtoMapping() {
    SaveSimpleTextDTO dto = saveSimpleTextMapper.toDto(simpleText);
    assertEquals(simpleText.getMagicText(), dto.getMagicText());
  }

  @Test
  void testToEntityMapping() {
    SimpleText entity = saveSimpleTextMapper.toEntity(simpleTextDTO);
    assertEquals(simpleTextDTO.getMagicText(), entity.getMagicText());
  }
}
