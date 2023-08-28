package com.example.simpletextcrud.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <code>SaveSimpleTextDTO<code/> simple text data transfer object to communicate via API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveSimpleTextDTO implements Serializable {
  private static final long serialVersionUID = 2023_08_27_12_36L;

  @NotNull
  @Size(min = 5, max = 200)
  private String magicText;
}
