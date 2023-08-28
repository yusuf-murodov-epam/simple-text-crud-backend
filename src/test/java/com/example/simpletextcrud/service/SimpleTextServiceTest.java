package com.example.simpletextcrud.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.simpletextcrud.domain.dto.GetSimpleTextDTO;
import com.example.simpletextcrud.domain.dto.SaveSimpleTextDTO;
import com.example.simpletextcrud.domain.entity.SimpleText;
import com.example.simpletextcrud.persistence.SimpleTextRepository;
import com.example.simpletextcrud.service.implemenation.SimpleTextServiceImpl;
import com.example.simpletextcrud.service.mapper.GetSimpleTextMapper;
import com.example.simpletextcrud.service.mapper.SaveSimpleTextMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

@ExtendWith(MockitoExtension.class)
class SimpleTextServiceTest {
  private final GetSimpleTextMapper getSimpleTextMapper = Mappers.getMapper(GetSimpleTextMapper.class);
  private final SaveSimpleTextMapper saveSimpleTextMapper = Mappers.getMapper(SaveSimpleTextMapper.class);
  private final SimpleTextRepository repository = mock(SimpleTextRepository.class);
  private final SimpleTextService service = new SimpleTextServiceImpl(repository, saveSimpleTextMapper, getSimpleTextMapper);

  @Test
  void shouldSaveSimpleText() {
    final String magicText = "Simple magic text in service test";
    final SaveSimpleTextDTO saveSimpleTextDTO = getSaveSimpleTextDTO(magicText);

    final SimpleText savedSimpleText = new SimpleText();
    savedSimpleText.setMagicText(magicText);
    savedSimpleText.setId(123);

    when(repository.saveAndFlush(any(SimpleText.class))).thenReturn(savedSimpleText);

    GetSimpleTextDTO result = service.save(saveSimpleTextDTO);

    assertNotNull(result);
    assertEquals(saveSimpleTextDTO.getMagicText(), result.getMagicText());
    assertNotNull(result.getId());

    verify(repository, only()).saveAndFlush(any(SimpleText.class));
  }

  @Test
  void shouldThrowExceptionWhenSaveSimpleTextWithTwoIdenticalMagicText() {
    final SaveSimpleTextDTO saveSimpleTextDTO = getSaveSimpleTextDTO("Simple magic text in service test");
    when(repository.saveAndFlush(any(SimpleText.class))).thenThrow(DataIntegrityViolationException.class);
    assertThrows(DataIntegrityViolationException.class, () -> service.save(saveSimpleTextDTO));

    verify(repository, only()).saveAndFlush(any(SimpleText.class));
  }

  @Test
  void shouldThrowExceptionWhenSaveSimpleTextWithNull() {
    final SaveSimpleTextDTO saveSimpleTextDTO = new SaveSimpleTextDTO();
    saveSimpleTextDTO.setMagicText(null);
    when(repository.saveAndFlush(any(SimpleText.class))).thenThrow(DataIntegrityViolationException.class);
    assertThrows(DataIntegrityViolationException.class, () -> service.save(saveSimpleTextDTO));

    verify(repository, only()).saveAndFlush(any(SimpleText.class));
  }

  @Test
  void shouldThrowExceptionWhenSaveSimpleTextWithMoreThan200Chars() {
    final SaveSimpleTextDTO saveSimpleTextDTO = getSaveSimpleTextDTO("In today's dynamic world, technological advancements are " +
        "reshaping industries and societies. The fusion of AI, biotech, and clean energy propels us into uncharted territories. " +
        "It's vital to stay curious, learn continuously, and collaborate for a future that's not just innovative, but also harmonious " +
        "and equitable.");
    when(repository.saveAndFlush(any(SimpleText.class))).thenThrow(DataIntegrityViolationException.class);
    assertThrows(DataIntegrityViolationException.class, () -> service.save(saveSimpleTextDTO));

    verify(repository, only()).saveAndFlush(any(SimpleText.class));
  }

  private SaveSimpleTextDTO getSaveSimpleTextDTO(final String magicText) {
    final SaveSimpleTextDTO saveSimpleTextDTO = new SaveSimpleTextDTO();
    saveSimpleTextDTO.setMagicText(magicText);
    return saveSimpleTextDTO;
  }
}
