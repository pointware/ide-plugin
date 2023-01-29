package com.example.gen;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class EngineGenerator {

    private final Random random = new Random();
    private final ProgressIndicator indicator;
    private final ConcurrentLinkedQueue<Integer> queue;
    private final AtomicInteger atomicInteger;

    public EngineGenerator() {
        this.indicator = ProgressManager.getInstance().getProgressIndicator();
        this.indicator.setFraction(0.0);
        this.queue = new ConcurrentLinkedQueue<>(
                List.of(1,2,3,4,5,6,7,8,9,10)
        );
        this.atomicInteger = new AtomicInteger(0);
    }


    public void generate() {
        CompletableFuture<Void>[] list = new CompletableFuture[10];
        int size = queue.size();
        for(int i=0; i<size; i++) {
            CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
                final Integer poll = queue.poll();
                String name = Thread.currentThread().getName();
                System.out.println(name);
                int n = random.nextInt() % 5+1;
                try {
                    TimeUnit.SECONDS.sleep(n);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                int i1 = atomicInteger.addAndGet(1);
                System.out.println(name+"/end"+poll+"size:"+queue.size());
                this.indicator.setText(name+"/end"+poll+"size:"+queue.size());
                double fraction = (double)i1 / 10;
                System.out.println("progress"+fraction);
                this.indicator.setFraction(fraction);
                this.indicator.setText2("22222"+name+"/end"+poll+"size:"+queue.size());
            });
            list[i] = voidCompletableFuture;
        }

        try {
            CompletableFuture.allOf(list).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
