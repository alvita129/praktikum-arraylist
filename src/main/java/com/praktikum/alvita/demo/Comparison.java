package com.praktikum.alvita.demo;

import java.util.List;
import java.util.Random;

// membandingkan waktu eksekusi Array vs ArrayList untuk operasi dasar
// pada beberapa ukuran data, lalu menampilkannya sebagai tabel.
public class Comparison {

    private static final int WARMUP = 200;
    private static final int REPEAT = 500;

    private final ArrayOperations arrayOps = new ArrayOperations();
    private final ArrayListOperations listOps = new ArrayListOperations();

    private long sink;

    // menjalankan perbandingan untuk semua ukuran data yang diberikan.
    public void run(int[] sizes) {
        for (int n : sizes) {
            runForSize(n);
        }
        if (sink == 42) System.out.print(""); // sink dipakai agar tidak dibuang
    }

    private void runForSize(int n) {
        int[] base = buildSortedData(n);
        int target = base[n * 3 / 4]; // nilai yang ada di list, di 3/4 posisi
        int mid = n / 2;
        List<Integer> baseList = listOps.fromArray(base);

        System.out.println();
        System.out.println("=== Ukuran data: " + n + " elemen ===");
        System.out.printf("%-22s | %14s | %14s | %s%n", "Operasi", "Array (ms)", "ArrayList (ms)", "Lebih cepat");
        System.out.println("-".repeat(72));

        // 1. traversal
        row("Traversal",
                measure(() -> sink += arrayOps.traverseSum(base)),
                measure(() -> sink += listOps.traverseSum(baseList)));

        // 2. linear search
        row("Linear search",
                measure(() -> sink += arrayOps.linearSearch(base, target)),
                measure(() -> sink += listOps.search(baseList, target)));

        // 3. binary search
        row("Binary search",
                measure(() -> sink += arrayOps.binarySearch(base, target)),
                measure(() -> sink += listOps.binarySearch(baseList, target)));

        // 4. penyisipan di tengah (array: buat array baru; list: add(index, value))
        row("Insert di tengah",
                measure(() -> sink += arrayOps.insert(base, mid, 25).length),
                measureListMutation(base, l -> listOps.addAt(l, mid, 25)));

        // 5. penghapusan di tengah
        row("Delete di tengah",
                measure(() -> sink += arrayOps.deleteAt(base, mid).length),
                measureListMutation(base, l -> listOps.removeAt(l, mid)));

        // 6. sorting data acak
        int[] random = buildRandomData(n);
        row("Sort (data acak)",
                measureSort(random, true),
                measureSort(random, false));
    }

    // ---------- helper pengukuran ----------

    // mengukur rata-rata waktu (ms) sebuah operasi
    private double measure(Runnable op) {
        for (int i = 0; i < WARMUP; i++) op.run();
        long start = System.nanoTime();
        for (int i = 0; i < REPEAT; i++) op.run();
        long elapsed = System.nanoTime() - start;
        return elapsed / (double) REPEAT / 1_000_000.0;
    }

     // operasi yang mengubah list: tiap iterasi memakai salinan baru (dibuat di luar pengukuran),
     // sehingga hanya waktu operasinya saja yang dihitung. salinan dibuat per iterasi
     // supaya memori tidak habis untuk data besar
    private double measureListMutation(int[] base, java.util.function.Consumer<List<Integer>> op) {
        long elapsed = 0;
        for (int i = 0; i < WARMUP + REPEAT; i++) {
            List<Integer> copy = listOps.fromArray(base);
            long start = System.nanoTime();
            op.accept(copy);
            long t = System.nanoTime() - start;
            if (i >= WARMUP) elapsed += t;
            sink += copy.size();
        }
        return elapsed / (double) REPEAT / 1_000_000.0;
    }

    // sorting: tiap iterasi memakai salinan data acak yang belum terurut
    private double measureSort(int[] random, boolean useArray) {
        int reps = Math.max(20, REPEAT / 10);
        int warm = Math.max(5, WARMUP / 10);
        int total = warm + reps;
        long elapsed = 0;
        for (int i = 0; i < total; i++) {
            int[] arrCopy = random.clone();
            List<Integer> listCopy = useArray ? null : listOps.fromArray(random);
            long start = System.nanoTime();
            if (useArray) arrayOps.sort(arrCopy);
            else listOps.sort(listCopy);
            long t = System.nanoTime() - start;
            if (i >= warm) elapsed += t;
            sink += useArray ? arrCopy[0] : listCopy.get(0);
        }
        return elapsed / (double) reps / 1_000_000.0;
    }

    // ---------- data & output ----------

    private int[] buildSortedData(int n) {
        int[] data = new int[n];
        for (int i = 0; i < n; i++) data[i] = (i + 1) * 10; // 10, 20, 30, ...
        return data;
    }

    private int[] buildRandomData(int n) {
        Random rnd = new Random(12345); // seed tetap -> data uji konsisten antar run
        int[] data = new int[n];
        for (int i = 0; i < n; i++) data[i] = rnd.nextInt(1_000_000);
        return data;
    }

    private void row(String name, double arrayMs, double listMs) {
        String winner = Math.abs(arrayMs - listMs) < arrayMs * 0.05 ? "seimbang"
                : (arrayMs < listMs ? "Array" : "ArrayList");
        System.out.printf("%-22s | %14.6f | %14.6f | %s%n", name, arrayMs, listMs, winner);
    }
}
