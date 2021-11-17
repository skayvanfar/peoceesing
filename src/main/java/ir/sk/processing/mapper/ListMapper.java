package ir.sk.processing.mapper;

import java.util.List;

/**
 * Created by sad.kayvanfar on 11/17/2021.
 */
@FunctionalInterface
public interface ListMapper<T> {
    List<T> apply(List<T> list);
}
