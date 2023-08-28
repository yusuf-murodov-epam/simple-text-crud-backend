package com.example.simpletextcrud.domain.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuditableDTO {
  private Date createdDate;
  private Date lastModifiedDate;
}
