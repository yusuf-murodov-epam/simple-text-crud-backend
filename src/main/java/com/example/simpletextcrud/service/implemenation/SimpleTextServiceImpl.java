package com.example.simpletextcrud.service.implemenation;

import com.example.simpletextcrud.domain.dto.GetSimpleTextDTO;
import com.example.simpletextcrud.domain.dto.SaveSimpleTextDTO;
import com.example.simpletextcrud.domain.entity.SimpleText;
import com.example.simpletextcrud.persistence.SimpleTextRepository;
import com.example.simpletextcrud.service.SimpleTextService;
import com.example.simpletextcrud.service.mapper.GetSimpleTextMapper;
import com.example.simpletextcrud.service.mapper.SaveSimpleTextMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <code>TextServiceImpl<code/> is an implementation of the {@link SimpleTextService}.
 */
@Service
@Transactional(readOnly = true)
public class SimpleTextServiceImpl implements SimpleTextService {
  private final SimpleTextRepository repository;
  private final SaveSimpleTextMapper saveSimpleTextMapper;
  private final GetSimpleTextMapper getSimpleTextMapper;

  @Autowired
  public SimpleTextServiceImpl(SimpleTextRepository repository, SaveSimpleTextMapper saveSimpleTextMapper,
      GetSimpleTextMapper getSimpleTextMapper) {
    this.repository = repository;
    this.saveSimpleTextMapper = saveSimpleTextMapper;
    this.getSimpleTextMapper = getSimpleTextMapper;
  }

  /**
   * Process and save a simple text input.
   * @param saveSimpleTextDTO simple text input.
   * @return the {@link GetSimpleTextDTO} with saved values.
   */
  @Transactional
  @Override
  public GetSimpleTextDTO save(SaveSimpleTextDTO saveSimpleTextDTO) {
    final SimpleText simpleText = saveSimpleTextMapper.toEntity(saveSimpleTextDTO);
    final SimpleText savedSimpleText = repository.saveAndFlush(simpleText);
    return getSimpleTextMapper.toDto(savedSimpleText);
  }
}
