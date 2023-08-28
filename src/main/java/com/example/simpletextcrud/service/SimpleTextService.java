package com.example.simpletextcrud.service;

import com.example.simpletextcrud.domain.dto.GetSimpleTextDTO;
import com.example.simpletextcrud.domain.dto.SaveSimpleTextDTO;

/**
 * <code>TextService<code/> is business logic specification.
 */
public interface SimpleTextService {
  GetSimpleTextDTO save(SaveSimpleTextDTO saveTextDTO);
}
