package ir.sk.processing.search;

import java.util.Comparator;
import java.util.List;

/**
 * Created by sad.kayvanfar on 11/17/2021.
 */
public interface SearchRange<T> {
    List<T> searchRange(List<T> list, Comparator<T> comparator, T target);
}
