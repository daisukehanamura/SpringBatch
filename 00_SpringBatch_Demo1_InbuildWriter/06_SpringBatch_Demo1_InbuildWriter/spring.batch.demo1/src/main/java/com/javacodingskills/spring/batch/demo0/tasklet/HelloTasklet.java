package com.javacodingskills.spring.batch.demo0.tasklet;

import com.javacodingskills.spring.batch.demo0.callable.MyCallable01;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
@StepScope
@Slf4j
public class HelloTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
            throws Exception {

        // ExecutorServiceを生成
        ExecutorService ex = Executors.newFixedThreadPool(3); // スレッドプールを用意
        List<Future<String>> futureResults = new ArrayList<>(); // Futureオブジェクトを格納するリスト

        // スレッドとの並行処理がわかりやすいように、
        // メイン側でもfor文で「メイン側: ＊回目の実行です」
        // を出力
        for (int i = 0; i < 3; i++) {
            // Executorにスレッド ThreadRun5() の実行を依頼
            Future<String> futureResult = ex.submit(new MyCallable01(i)); // Callableタスクをスレッドに渡す
            futureResults.add(futureResult); // リストに追加
            System.out.println("メイン側: " + i + "回目の実行です");           // スレッドから戻ってきた値を受け取る。
        }

        // スレッドの処理結果を取得
        for (Future<String> futureResult : futureResults) {
            try {
                String result = futureResult.get(); // スレッド処理の結果を取得
            } catch (Exception e) {
                System.out.println("エラーが発生しました: " + e.getMessage());
            }
        }

        return RepeatStatus.FINISHED;
    }
}
