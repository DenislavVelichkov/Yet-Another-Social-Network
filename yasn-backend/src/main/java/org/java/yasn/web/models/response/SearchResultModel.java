package org.java.yasn.web.models.response;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchResultModel<T> {

  private Collection<T> searchResult;

}
