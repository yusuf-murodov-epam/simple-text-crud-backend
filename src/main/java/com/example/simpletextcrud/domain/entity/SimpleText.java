package com.example.simpletextcrud.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * <code>SimpleText<code/> simple text entity used to persist into data storage.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "simple_texts")
public class SimpleText extends Auditable implements Serializable {
  private static final long serialVersionUID = 2023_08_27_12_17L;

  @Id
  @GeneratedValue(generator = "simple_text_sequence", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "simple_text_sequence", sequenceName = "simple_text_sequence", allocationSize = 5)
  private int id;

  @Column(nullable = false, unique = true, length = 200)
  private String magicText;
}
