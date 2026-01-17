package saturday.year26.sat260117;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
 * N개의 문제... 각각의 문제마다 주어지는 컵라면 개수가 다름
 * 각각의 문제마다 데드라인 존재
 * 데드라인 내에 받을 수 잇는 최대 컵라면 수 게산
 * 문제를 푸는데는 단위 시간 1이 소요됨
 *
 * 데드라인 내에 풀 수 있는 모든 문제 풀기
 */

class Problem implements Comparable<Problem> {
    int deadLine;
    int cnt;

    Problem(int deadLine, int cnt) {
        this.deadLine = deadLine;
        this.cnt = cnt;
    }

    @Override
    public int compareTo(Problem o) { // 데드라인 오름차순, 컵라면 내림차순
        if (this.deadLine == o.deadLine) {
            return o.cnt - this.cnt;
        }

        return this.deadLine - o.deadLine;
    }
}
public class BJ_1781_컵라면 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = stoi(br.readLine());

        // 데드라인, 컵라면 개수
        Problem[] problems = new Problem[N];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int deadLine = stoi(st.nextToken());
            int cnt = stoi(st.nextToken());

            problems[i] = new Problem(deadLine, cnt);
        }

        // 1. 데드라인 오름차순, 컵라면 내림차순 정렬
        Arrays.sort(problems);

        // 2. 풀 수 있는 문제의 컵라면 개수를 담을 최소 힙
        // 큐의 크기 = 데드라인
        // 이후 더 많은 컵라면을 주는 문제를 풀 수 있도록 컵라면 개수만 저장
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (Problem pro : problems) {
            pq.offer(pro.cnt);

            if (pq.size() > pro.deadLine) { // 큐의 최솟값과 비교해서 교체
                pq.poll();
            }
        }

        int ans = 0;
        while (!pq.isEmpty()) ans += pq.poll();

        System.out.println(ans);
    }

    private static int stoi(String str) {
        return Integer.parseInt(str);
    }
}
