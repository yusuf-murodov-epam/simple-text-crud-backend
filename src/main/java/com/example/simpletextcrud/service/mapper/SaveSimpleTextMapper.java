package com.example.simpletextcrud.service.mapper;

import com.example.simpletextcrud.domain.dto.SaveSimpleTextDTO;
import com.example.simpletextcrud.domain.entity.SimpleText;
import org.mapstruct.Mapper;

/**
 * <code>SaveSimpleTextMapper<code/> is an extended interface of the {@link BaseMapper} to map between {@link SimpleText} and
 * {@link SaveSimpleTextDTO}.
 */
@Mapper(config = BaseMapper.class)
public interface SaveSimpleTextMapper extends BaseMapper<SimpleText, SaveSimpleTextDTO> {
}
