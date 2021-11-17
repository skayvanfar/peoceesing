package ir.sk.processing.search;

import java.util.Comparator;
import java.util.List;

public class BinarySearchRange<T> implements SearchRange<T> {

    public List<T> searchRange(List<T> list, Comparator<T> comparator, T target) {
        int leftBound = leftBoundBinarySearch(list, comparator, target);
        int rightBound = rightBoundBinarySearch(list, comparator, target, leftBound);
        return list.subList(leftBound, rightBound + 1);
    }

    /**
     * Return the index of the first target number in the lists if it exists, otherwise return -1.
     */
    private int leftBoundBinarySearch(List<T> list, Comparator<T> comparator, T target) {
        int left = 0, right = list.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;

            int result = comparator.compare(target, list.get(mid));

            if (result < 0) {
                left = mid + 1;
            } else if (result > 0) {
                right = mid - 1;
            } else {
                // shrink right border
                right = mid - 1;
            }
        }
        return left;
    }

    /**
     * Return the index of the last target number in the array if it exists, otherwise return -1.
     */
    private int rightBoundBinarySearch(List<T> list, Comparator<T> comparator, T target, int start) {
        int left = start, right = list.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;

            int result = comparator.compare(target, list.get(mid));

            if (result < 0) {
                left = mid + 1;
            } else if (result > 0) {
                right = mid - 1;
            } else {
                // shrink left bounds
                left = mid + 1;
            }
        }
        return right;
    }
}
