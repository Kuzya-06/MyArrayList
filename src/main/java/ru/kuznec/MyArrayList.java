package ru.kuznec;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * Этот класс предоставляет методы для управления размером массива, который используется внутри для хранения списка.
 * Приложение может увеличить емкость экземпляра MyArrayList перед добавлением большого количества элементов с помощью операции обеспечения CAPACITY.
 * Это может уменьшить объем дополнительного перераспределения. Обратите внимание, что эта реализация не синхронизирована.
 * Если несколько потоков одновременно обращаются к экземпляру MyArrayList и хотя бы один из потоков изменяет структуру списка, его необходимо синхронизировать извне.
 * Документация по методам, содержащимся в этом классе, включает краткие описания реализаций.
 * Такие описания следует рассматривать как замечания по реализации, а не как части спецификации.
 * Разработчики могут свободно заменять другие алгоритмы при условии соблюдения самой спецификации.
 * Создание списка:
 * MyArrayList<T> list = new MyArrayList<>();
 * @autor Kuznetsov Mikhail
 * @version 1.0
 */
public class MyArrayList<T> implements Serializable {
    /**
     * Начальная емкость по умолчанию.
     */
    private static final int DEFAULT_CAPACITY = 14;

    /**
     * Буфер массива, в котором хранятся элементы MyArrayList.
     * Емкость MyArrayList — это длина этого буфера массива.
     * Любой пустой MyArrayList с elements == DEFAULT_CAPACITY будет расширен до DEFAULT_CAPACITY при добавлении первого элемента.
      */
    private Object[] elements;
    /**
     * Размер MyArrayList (количество содержащихся в нем элементов).
     */
    private int size;

    /** Общий пустой экземпляр массива, используемый для пустых экземпляров.
   */
     private static final Object[] EMPTY_ELEMENT_DATA = {

    };

    /**
     * Создает пустой список с начальной емкостью DEFAULT_CAPACITY.
     */
    public MyArrayList() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    /**
     * Создает пустой список с указанной начальной емкостью.
     * Параметры: InitialCapacity – начальная емкость списка.
     * Выдает:
     * IllegalArgumentException – если указанная начальная емкость отрицательна.
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
     * Добавляет указанный элемент в конец этого списка.
     * Параметры: element — элемент, который будет добавлен в этот список.
     * Возвраты: true .
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
     * Вставляет указанный элемент в указанную позицию в этом списке.
     * Смещает элемент, находящийся в данный момент в этой позиции (если есть), и любые последующие элементы вправо (добавляет единицу к их индексам).
     * Параметры: index – индекс, в который должен быть вставлен указанный элемент.
     * element – элемент, который должен быть вставлен.
     * Выдает: IndexOutOfBoundsException – если индекс выходит за пределы диапазона (индекс < 0 || индекс > размер())
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
     * Возвращает элемент в указанной позиции в этом списке.
     * Параметры: index – индекс возвращаемого элемента.
     * Возвращает: элемент в указанной позиции в этом списке.
     * Выдает: IndexOutOfBoundsException – если индекс выходит за пределы диапазона (индекс < 0 || индекс >= размер())
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
     * Удаляет элемент в указанной позиции в этом списке.
     * Сдвигает любые последующие элементы влево (вычитает единицу из их индексов).
     * Параметры: index — индекс удаляемого элемента.
     * Возвращает: элемент, который был удален из списка.
     * Выдает: IndexOutOfBoundsException — если индекс выходит за пределы диапазона (индекс < 0 || индекс >= размер())
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
     * Удаляет все элементы из этого списка.
     * Список будет пуст после возврата этого вызова.
     */
    public void clear() {
        Arrays.fill(elements, null);
        size = 0;
    }

    /**
     * Сортирует список с помощью Arrays.sort
     */
    public void sort() {
        Arrays.sort(elements, 0, size);
    }

    /**
     * Быстрая сортировка
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
     * Метод увеличения ёмкости в 2 раза
     */
    private void increaseCapacity() {
        int newCapacity = elements.length * 2;
        elements = Arrays.copyOf(elements, newCapacity);
    }

    /**
     * Возвращает: количество элементов в этом списке.
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