package com.icommerce.backend.service;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public class QueryBuilder {
  private final List<Optional<BooleanExpression>> expressions = new ArrayList<>();
  private QueryBuilder() {
  }
  public static QueryBuilder builder() {
    return new QueryBuilder();
  }
  public QueryBuilder ifNotBlank(String text, Function<String, BooleanExpression> mapper) {
    expressions.add(
        Optional.ofNullable(text)
            .map(String::trim)
            .filter(str -> str.length() > 0)
            .map(mapper)
    );
    return this;
  }
  public <T> QueryBuilder ifNotNull(T param, Function<T, BooleanExpression> mapper) {
    expressions.add(
        Optional.ofNullable(param)
            .map(mapper)
    );
    return this;
  }
  public QueryBuilder add(BooleanExpression predicate) {
    expressions.add(
        Optional.of(predicate)
    );
    return this;
  }
  public <T> QueryBuilder ifNotEmptyCollection(Collection<T> list,
      Function<Collection<T>, BooleanExpression> mapper) {
    expressions.add(
        Optional.ofNullable(list)
            .filter(item -> !item.isEmpty())
            .map(mapper)
    );
    return this;
  }
  public Optional<BooleanExpression> toMatchAll() {
    return buildWith(BooleanExpression::and);
  }
  public Optional<BooleanExpression> toMatchAny() {
    return buildWith(BooleanExpression::or);
  }
  private Optional<BooleanExpression> buildWith(BinaryOperator<BooleanExpression> reduce) {
    return expressions.stream()
        .filter(Optional::isPresent)
        .map(Optional::get)
        .reduce(reduce);
  }

}
