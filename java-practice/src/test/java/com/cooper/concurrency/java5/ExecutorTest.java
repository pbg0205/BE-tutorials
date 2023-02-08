package com.cooper.concurrency.java5;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executor;

/**
 * @reference https://mangkyu.tistory.com/259
 *
 * @Executor 쓰레드 풀의 구현을 위한 인터페이스이다. 쓰레드 풀을 사용하는 이유는 동시에 여러 요청을 처리하는 경우를 대비에
 * 쓰레드를 미리 만들어두고 재사용하기 위함이다. 등록한 작업(Runnable) 을 실행하기 위한 인터페이스이며,
 * Executor 인터페이스를 선언함으로써 쓰레드의 등록 + 실행의 책임 중에 실행의 책임만 분리하였다.(ISP)
 *
 */
class ExecutorTest {

    @Test
    void executorRun() {
        final Runnable runnable = () -> System.out.println("Thread: " + Thread.currentThread().getName());

        Executor executor = new RunExecutor();
        executor.execute(runnable);
    }

    static class RunExecutor implements Executor {

        @Override
        public void execute(final Runnable command) {
            command.run();
        }
    }

    @Test
    void executorStart() {
        final Runnable runnable = () -> System.out.println("Thread: " + Thread.currentThread().getName());

        Executor executor = new StartExecutor();
        executor.execute(runnable);
    }

    static class StartExecutor implements Executor {

        @Override
        public void execute(final Runnable command) {
            new Thread(command).start();
        }
    }

}
