package com.rafaelmj.streaming.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedResponseUtil<T> {

  private List<T> content;
  private int currentPage;
  private int totalPages;
  private long totalElements;

}
