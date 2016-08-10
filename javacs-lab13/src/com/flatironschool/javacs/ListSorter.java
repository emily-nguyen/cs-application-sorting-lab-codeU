/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	private List<T> rankList(List<T> firstList, List<T> secondList, Comparator<T> comparator) {
		if (firstList.size() == 0) {
			return secondList; 
		}

		if (secondList.size() == 0) {
			return firstList;
		}

		T item1 = firstList.get(0);
		T item2 = secondList.get(0); 
		int result = comparator.compare(item1, item2);

		if (result <= 0) {
			return firstList;
		} else {
			return secondList;
		}
	}

	private List<T> merge(List<T> firstList, List<T> secondList, Comparator<T> comparator) {
		List<T> result = new LinkedList<T>(); 
		int length = firstList.size() + secondList.size(); 

		for (int i=0; i < length; i++) {
			List<T> list = rankList(firstList, secondList, comparator);
			T element = list.remove(0);

			result.add(element); 
		}

		return result;
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
		int length = list.size(); 
		int mid = length / 2; 

		if (length <= 1) {
			return list; 
		}

		List<T> left = list.subList(0, mid);
		List<T> right = list.subList(mid, length); 

		List<T> firstList = mergeSort(new LinkedList<T>(left), comparator);
		List<T> secondList = mergeSort(new LinkedList<T>(right), comparator);

		return merge(firstList, secondList, comparator);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
		int length = list.size(); 
		PriorityQueue<T> heap = new PriorityQueue<T>(length, comparator);

		heap.addAll(list); 
		list.clear(); 

		while (!heap.isEmpty()) {
			T element = heap.poll(); 

			list.add(element);
		}
	}

	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return 
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
		int length = list.size(); 
		PriorityQueue<T> heap = new PriorityQueue<T>(length, comparator); 

		for (T element: list) {
			if (heap.size() < k) {
				heap.offer(element); 
				continue; 
			}

			T top = heap.peek();
			int cmp = comparator.compare(element, top); 

			if (cmp > 0) {
				heap.poll(); 
				heap.offer(element);
			}
		}

		List<T> result = new ArrayList<T>();

		while (!heap.isEmpty()) {
			T element = heap.poll(); 

			result.add(element);
		}

		return result;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
