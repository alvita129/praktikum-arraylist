package com.praktikum.alvita.demo;

import java.util.Arrays;

// operasi dasar pada Array (int[]): traversal, pencarian, penyisipan, penghapusan
// ukuran array di Java bersifat tetap, jadi penyisipan/penghapusan
// selalu menghasilkan array BARU (elemen disalin dengan System.arraycopy)
public class ArrayOperations {

    // traversal: mengembalikan isi array dalam bentuk String
    public String traverse(int[] arr) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(arr[i]);
        }
        return sb.append("]").toString();
    }

    // traversal tanpa print (dipakai untuk benchmark supaya waktu I/O tidak ikut terukur)
    // mengembalikan jumlah seluruh elemen agar loop tidak dibuang oleh JIT compiler
    public long traverseSum(int[] arr) {
        long sum = 0;
        for (int value : arr) {
            sum += value;
        }
        return sum;
    }

    // linear search - O(n). Mengembalikan indeks pertama yang cocok, atau -1 jika tidak ada
    public int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) return i;
        }
        return -1;
    }

    // binary search - O(log n) dengan syarat array sudah terurut naik. mengembalikan indeks atau -1
    public int binarySearch(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2; // hindari overflow dibanding (low+high)/2
            if (arr[mid] == target) return mid;
            if (arr[mid] < target) low = mid + 1;
            else high = mid - 1;
        }
        return -1;
    }

    // penyisipan nilai pada indeks tertentu - O(n) karena elemen sesudahnya digeser.
    // return array baru berukuran arr.length + 1
    public int[] insert(int[] arr, int index, int value) {
        if (index < 0 || index > arr.length) {
            throw new IndexOutOfBoundsException("Indeks " + index + " di luar batas 0.." + arr.length);
        }
        int[] result = new int[arr.length + 1];
        System.arraycopy(arr, 0, result, 0, index); // bagian sebelum index
        result[index] = value; // nilai baru
        System.arraycopy(arr, index, result, index + 1, arr.length - index); // bagian sesudah (digeser)
        return result;
    }

    // penghapusan elemen pada indeks tertentu - O(n)
    // return array baru berukuran arr.length - 1
    public int[] deleteAt(int[] arr, int index) {
        if (index < 0 || index >= arr.length) {
            throw new IndexOutOfBoundsException("Indeks " + index + " di luar batas 0.." + (arr.length - 1));
        }
        int[] result = new int[arr.length - 1];
        System.arraycopy(arr, 0, result, 0, index);
        System.arraycopy(arr, index + 1, result, index, arr.length - index - 1);
        return result;
    }

    // oenghapusan berdasarkan nilai (menghapus kemunculan pertama). Jika tidak ada, array dikembalikan apa adanya
    public int[] deleteValue(int[] arr, int value) {
        int idx = linearSearch(arr, value);
        return idx == -1 ? arr : deleteAt(arr, idx);
    }

    // pengurutan array (dipakai untuk pembanding dengan Collections.sort pada ArrayList)
    public void sort(int[] arr) {
        Arrays.sort(arr);
    }
}
