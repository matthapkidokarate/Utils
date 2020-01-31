package com.saunderstheaterproperties.utils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SerialExecutor implements Executor {

    private Executor executor;
    private Queue<Runnable> taskQueue = new LinkedList<Runnable>();
    private Runnable active = null;

    public SerialExecutor() {
        this.executor = Executors.newFixedThreadPool(1);
    }

    public void execute(final Runnable command) {
        taskQueue.offer(new Runnable() {
            public void run() {
                try {
                    command.run();
                } finally {
                    scheduleNext();
                }
            }
        });

        if (active == null) {
            scheduleNext();
        }
    }

    private void scheduleNext() {
        if ((active = taskQueue.poll()) != null) {
            executor.execute(active);
        }
    }
}