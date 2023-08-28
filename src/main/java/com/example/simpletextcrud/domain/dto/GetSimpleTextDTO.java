package com.example.simpletextcrud.domain.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <code>GetSimpleTextDTO<code/> simple text data transfer object to communicate via API.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"id", "magicText", "createdDate", "lastModifiedDate"})
public class GetSimpleTextDTO extends AuditableDTO implements Serializable {
  private static final long serialVersionUID = 2023_08_27_12_26L;
  private String id;
  private String magicText;
}
