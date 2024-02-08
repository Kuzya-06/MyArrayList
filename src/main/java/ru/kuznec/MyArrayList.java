package ru.kuznec;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * ���� ����� ������������� ������ ��� ���������� �������� �������, ������� ������������ ������ ��� �������� ������.
 * ���������� ����� ��������� ������� ���������� MyArrayList ����� ����������� �������� ���������� ��������� � ������� �������� ����������� CAPACITY.
 * ��� ����� ��������� ����� ��������������� �����������������. �������� ��������, ��� ��� ���������� �� ����������������.
 * ���� ��������� ������� ������������ ���������� � ���������� MyArrayList � ���� �� ���� �� ������� �������� ��������� ������, ��� ���������� ���������������� �����.
 * ������������ �� �������, ������������ � ���� ������, �������� ������� �������� ����������.
 * ����� �������� ������� ������������� ��� ��������� �� ����������, � �� ��� ����� ������������.
 * ������������ ����� �������� �������� ������ ��������� ��� ������� ���������� ����� ������������.
 * �������� ������:
 * MyArrayList<T> list = new MyArrayList<>();
 * @autor Kuznetsov Mikhail
 * @version 1.0
 */
public class MyArrayList<T> implements Serializable {
    /**
     * ��������� ������� �� ���������.
     */
    private static final int DEFAULT_CAPACITY = 14;

    /**
     * ����� �������, � ������� �������� �������� MyArrayList.
     * ������� MyArrayList � ��� ����� ����� ������ �������.
     * ����� ������ MyArrayList � elements == DEFAULT_CAPACITY ����� �������� �� DEFAULT_CAPACITY ��� ���������� ������� ��������.
      */
    private Object[] elements;
    /**
     * ������ MyArrayList (���������� ������������ � ��� ���������).
     */
    private int size;

    /** ����� ������ ��������� �������, ������������ ��� ������ �����������.
   */
     private static final Object[] EMPTY_ELEMENT_DATA = {

    };

    /**
     * ������� ������ ������ � ��������� �������� DEFAULT_CAPACITY.
     */
    public MyArrayList() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    /**
     * ������� ������ ������ � ��������� ��������� ��������.
     * ���������: InitialCapacity � ��������� ������� ������.
     * ������:
     * IllegalArgumentException � ���� ��������� ��������� ������� ������������.
     */
    public MyArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elements = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elements = EMPTY_ELEMENT_DATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+
                    initialCapacity);
        }
    }

    /**
     * ��������� ��������� ������� � ����� ����� ������.
     * ���������: element � �������, ������� ����� �������� � ���� ������.
     * ��������: true .
     * @param element
     */
    public void add(T element) {
        if (size == elements.length-4) {
            increaseCapacity();
        }
        elements[size] = element;
        size++;
    }

    /**
     * ��������� ��������� ������� � ��������� ������� � ���� ������.
     * ������� �������, ����������� � ������ ������ � ���� ������� (���� ����), � ����� ����������� �������� ������ (��������� ������� � �� ��������).
     * ���������: index � ������, � ������� ������ ���� �������� ��������� �������.
     * element � �������, ������� ������ ���� ��������.
     * ������: IndexOutOfBoundsException � ���� ������ ������� �� ������� ��������� (������ < 0 || ������ > ������())
     * @param index
     * @param element
     */
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        if (size == elements.length) {
            increaseCapacity();
        }
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    /**
     * ���������� ������� � ��������� ������� � ���� ������.
     * ���������: index � ������ ������������� ��������.
     * ����������: ������� � ��������� ������� � ���� ������.
     * ������: IndexOutOfBoundsException � ���� ������ ������� �� ������� ��������� (������ < 0 || ������ >= ������())
     * @param index
     * @return
     */
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return (T) elements[index];
    }

    /**
     * ������� ������� � ��������� ������� � ���� ������.
     * �������� ����� ����������� �������� ����� (�������� ������� �� �� ��������).
     * ���������: index � ������ ���������� ��������.
     * ����������: �������, ������� ��� ������ �� ������.
     * ������: IndexOutOfBoundsException � ���� ������ ������� �� ������� ��������� (������ < 0 || ������ >= ������())
     * @param index
     */
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        size--;
    }

    /**
     * ������� ��� �������� �� ����� ������.
     * ������ ����� ���� ����� �������� ����� ������.
     */
    public void clear() {
        Arrays.fill(elements, null);
        size = 0;
    }

    /**
     * ��������� ������ � ������� Arrays.sort
     */
    public void sort() {
        Arrays.sort(elements, 0, size);
    }

    /**
     * ������� ����������
     * @param comparator
     */
    public void quickSort(Comparator<? super T> comparator) {
        quickSort(0, size - 1, comparator);
    }

    private void quickSort(int low, int high, Comparator<? super T> comparator) {
        if (low < high) {
            int pivotIndex = partition(low, high, comparator);
            quickSort(low, pivotIndex - 1, comparator);
            quickSort(pivotIndex + 1, high, comparator);
        }
    }

    private int partition(int low, int high, Comparator<? super T> comparator) {
        T pivot = get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparator.compare(get(j), pivot) <= 0) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return i + 1;
    }

    private void swap(int i, int j) {
        T temp = get(i);
        elements[i] = elements[j];
        elements[j] = temp;
    }

    /**
     * ����� ���������� ������� � 2 ����
     */
    private void increaseCapacity() {
        int newCapacity = elements.length * 2;
        elements = Arrays.copyOf(elements, newCapacity);
    }

    /**
     * ����������: ���������� ��������� � ���� ������.
     */
    public int size() {
        return size;
    }

    @Override
    public String toString() {
       if(size()!=0){
        String str ="[";
        for (int i = 0; i < size(); i++) {
            if (i== size-1){
                str = str + this.get(i) + "]";
            } else {
                str = str + this.get(i) + ", ";
            }
        }
        return str ;}
       else return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyArrayList<?> that = (MyArrayList<?>) o;
        return Arrays.equals(elements, that.elements);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(elements);
        return result;
    }
}