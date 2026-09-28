package com.praktikum.alvita.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// operasi dasar pada ArrayList<Integer>: tambah, hapus, cari, urutkan
// berbeda dengan array, ArrayList otomatis membesar dan mengelola pergeseran elemen sendiri
public class ArrayListOperations {

    // traversal: mengembalikan isi list sebagai String
    public String traverse(List<Integer> list) {
        return list.toString();
    }

    // traversal tanpa print untuk benchmark
    public long traverseSum(List<Integer> list) {
        long sum = 0;
        for (int value : list) {
            sum += value;
        }
        return sum;
    }

    // mnambahkan elemen di akhir list - O(1) amortized
    public void add(List<Integer> list, int value) {
        list.add(value);
    }

    // menambahkan elemen pada indeks tertentu - O(n) karena elemen sesudahnya digeser
    public void addAt(List<Integer> list, int index, int value) {
        list.add(index, value);
    }

    // menghapus elemen pada indeks tertentu - O(n)
    public int removeAt(List<Integer> list, int index) {
        return list.remove(index); // remove(int) -> berdasarkan INDEKS
    }

    // menghapus kemunculan pertama sebuah nilai - O(n)
    // tanpa Integer.valueOf(), remove(int) akan menganggap argumen sebagai indeks
    public boolean removeValue(List<Integer> list, int value) {
        return list.remove(Integer.valueOf(value)); // remove(Object) -> berdasarkan NILAI
    }

    // pencarian elemen - O(n). Mengembalikan indeks atau -1
    public int search(List<Integer> list, int target) {
        return list.indexOf(target);
    }

    // binary search pada list yang sudah terurut - O(log n). mengembalikan indeks atau -1
    public int binarySearch(List<Integer> list, int target) {
        int idx = Collections.binarySearch(list, target);
        return idx >= 0 ? idx : -1;
    }

    // mengurutkan list secara naik
    public void sort(List<Integer> list) {
        Collections.sort(list);
    }

    // helper: membuat ArrayList dari array int
    public List<Integer> fromArray(int[] arr) {
        List<Integer> list = new ArrayList<>(arr.length);
        for (int v : arr) list.add(v);
        return list;
    }
}
