package ru.kuznec;

import org.junit.jupiter.api.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MyArrayListTest {
    MyArrayList<Integer> arrayList;


    @BeforeEach
    void setUp() {
        arrayList = new MyArrayList<>();
        for (int i = 0; i < 10; i++) {
            arrayList.add(i * 10);
        }
    }

    @Test
    @Order(1)
    void add() {
        arrayList.add(123);
        Assertions.assertEquals(123, arrayList.get(arrayList.size() - 1));
    }

    @Test
    @Order(10)
    void testAddById() {
        arrayList.add(1, 300);
        Assertions.assertEquals(300, arrayList.get(1));
    }

    @Test
    @Order(20)
    void get() {
        Integer integer = arrayList.get(1);
        Assertions.assertEquals(10, integer);
    }

    @Test
    @Order(400)
    void remove() {
        int size = arrayList.size();
        arrayList.remove(1);
        int size2 = arrayList.size();
        Assertions.assertEquals(size, size2 + 1);
    }

    @Test
    @Order(404)
    void clear() {
        arrayList.clear();
        Assertions.assertEquals(0, arrayList.size());
    }

    @Test
    @Order(5)
    void sort() {
        arrayList.add(1, 1000);
        arrayList.sort();
        Assertions.assertEquals(1000, arrayList.get(arrayList.size() - 1));
    }

    @Test
    @Order(6)
    void quickSort() {
        arrayList.add((arrayList.size() - 1), -1000);
        arrayList.sort();
        Assertions.assertEquals(-1000, arrayList.get(0));
    }

    @Test
    @Order(15)
    void size() {
        Assertions.assertNotNull(arrayList);
    }
}