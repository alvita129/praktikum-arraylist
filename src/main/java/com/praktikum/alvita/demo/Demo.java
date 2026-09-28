package com.praktikum.alvita.demo;

import java.util.List;

// demo fungsional: mencetak hasil operasi sesuai format "Contoh Output" pada soal.
public class Demo {

    public static void main(String[] args) {
        run();
        System.out.println();
        new Comparison().run(new int[]{10, 1_000, 100_000});
    }

    public static void run() {
        ArrayOperations arrayOps = new ArrayOperations();
        ArrayListOperations listOps = new ArrayListOperations();

        int[] array = {10, 20, 30, 40, 50};
        List<Integer> list = listOps.fromArray(array);

        // traversal
        System.out.println("Array Traversal: " + arrayOps.traverse(array));
        System.out.println("ArrayList Traversal: " + listOps.traverse(list));
        System.out.println();

        // pencarian
        int idxA = arrayOps.linearSearch(array, 30);
        int idxL = listOps.search(list, 30);
        System.out.println("Pencarian 30 dalam Array: " + (idxA >= 0 ? "Ditemukan di indeks " + idxA : "Tidak ditemukan"));
        System.out.println("Pencarian 30 dalam ArrayList: " + (idxL >= 0 ? "Ditemukan di indeks " + idxL : "Tidak ditemukan"));
        System.out.println("Binary search 40 dalam Array: indeks " + arrayOps.binarySearch(array, 40));
        System.out.println();

        // penyisipan 25 (di antara 20 dan 30 -> indeks 2)
        array = arrayOps.insert(array, 2, 25);
        listOps.addAt(list, 2, 25);
        System.out.println("Array setelah penyisipan elemen 25: " + arrayOps.traverse(array));
        System.out.println("ArrayList setelah penyisipan elemen 25: " + listOps.traverse(list));
        System.out.println();

        // penghapusan 25
        array = arrayOps.deleteValue(array, 25);
        listOps.removeValue(list, 25);
        System.out.println("Array setelah penghapusan elemen 25: " + arrayOps.traverse(array));
        System.out.println("ArrayList setelah penghapusan elemen 25: " + listOps.traverse(list));
        System.out.println();

        // pengurutan ArrayList
        List<Integer> unsorted = new java.util.ArrayList<>(List.of(50, 10, 40, 20, 30));
        System.out.println("ArrayList sebelum sort: " + listOps.traverse(unsorted));
        listOps.sort(unsorted);
        System.out.println("ArrayList setelah sort: " + listOps.traverse(unsorted));
    }
}
