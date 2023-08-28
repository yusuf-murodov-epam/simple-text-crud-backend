package com.example.simpletextcrud.persistence;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.simpletextcrud.common.PostgresExtension;
import com.example.simpletextcrud.domain.entity.SimpleText;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@ExtendWith(PostgresExtension.class)
@DirtiesContext
@TestPropertySource(locations = "classpath:application.properties")
class SimpleTextRepositoryTest {
  @Autowired
  private SimpleTextRepository repository;

  @Test
  void shouldSaveSimpleText() {
    final SimpleText simpleText = getSimpleText("Simple text test.");
    final SimpleText result = repository.saveAndFlush(simpleText);
    assertSimpleText(simpleText, result);
  }

  @Test
  void shouldThrowExceptionWhenSaveSimpleTextWithSameText() {
    final String magicalText = "Simple text test.";
    final SimpleText simpleText1 = getSimpleText(magicalText);
    final SimpleText result = repository.saveAndFlush(simpleText1);
    assertSimpleText(simpleText1, result);

    final SimpleText simpleText2 = new SimpleText();
    simpleText2.setMagicText(magicalText);
    assertThrows(DataIntegrityViolationException.class, () -> repository.saveAndFlush(simpleText2));
  }

  @Test
  void shouldThrowExceptionWhenSaveSimpleTextWithNullText() {
    final SimpleText simpleText = getSimpleText(null);
    assertThrows(DataIntegrityViolationException.class, () -> repository.saveAndFlush(simpleText));
  }

  @Test
  void shouldThrowExceptionWhenSaveSimpleTextWithMoreThan200Chars() {
    final SimpleText simpleText = getSimpleText("In today's dynamic world, technological advancements are reshaping industries " +
        "and societies. The fusion of AI, biotech, and clean energy propels us into uncharted territories. It's vital to stay curious, " +
        "learn continuously, and collaborate for a future that's not just innovative, but also harmonious and equitable.");
    assertThrows(DataIntegrityViolationException.class, () -> repository.saveAndFlush(simpleText));
  }

  private SimpleText getSimpleText(final String magicalText) {
    final SimpleText simpleText = new SimpleText();
    simpleText.setMagicText(magicalText);
    return simpleText;
  }

  private void assertSimpleText(final SimpleText expectedSimpleText, final SimpleText actualSimpleText) {
    assertNotNull(actualSimpleText);
    assertTrue(actualSimpleText.getId() > 0);
    assertNotNull(actualSimpleText.getCreatedDate());
    assertNotNull(actualSimpleText.getLastModifiedDate());
    assertEquals(expectedSimpleText.getMagicText(), actualSimpleText.getMagicText());
  }
}
