package com.example.simpletextcrud.web;

import com.example.simpletextcrud.domain.dto.GetSimpleTextDTO;
import com.example.simpletextcrud.domain.dto.SaveSimpleTextDTO;
import com.example.simpletextcrud.service.SimpleTextService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * <code>TextController<code/> Simple text API holder.
 */
@RestController
@RequestMapping("/api/v1/texts")
public class SimpleTextController {
  private static Logger log = LoggerFactory.getLogger(SimpleTextController.class.getName());
  private final SimpleTextService service;

  @Autowired
  public SimpleTextController(SimpleTextService service) {
    this.service = service;
  }

  /**
   * Save and process a simple text.
   * @param saveTextDTO simple text dto.
   * @return the {@link GetSimpleTextDTO} with saved values.
   */
  @PostMapping
  @ResponseStatus(value = HttpStatus.CREATED)
  public GetSimpleTextDTO save(@RequestBody @Validated SaveSimpleTextDTO saveTextDTO) {
    log.info("Request to save simple text: {}", saveTextDTO);
    return service.save(saveTextDTO);
  }
}
