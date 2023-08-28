package com.example.simpletextcrud.persistence;

import com.example.simpletextcrud.domain.entity.SimpleText;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <code>SimpleTextRepository<code/> repository interface to manage/operate entities on data storage.
 */
@Repository
public interface SimpleTextRepository extends JpaRepository<SimpleText, Integer> {
}
