package saturday.year26.sat260404;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 1 ~ N번의 N개의 지역과 그 지역 사이를 잇는 몇 개의 횡단보도
 * 모든 지역은 횡단보도를 통해 직접/간접적으로 연결
 * 횡단보도의 주기는 총 M분이며 1분마다 신호가 바뀐다.
 * 각 주기의 1 + i번째 신호는 i, M + i, 2M + i, 3M + i, ... 분에 시작해서
 * 1분동안 A_i번 지역과 B_i번 지역을 잇는 횡단보도에 파란불, 나머지 횡단보도는 빨간불
 * 한 주기 동안 같은 횡단보도에 파란불이 여러 번 들어올 수 있음
 * 횡단보도 이동 시간은 1분, 중간에 빨간불이 안들어오도록 주기 변경 이전에 출발할 것
 * 시간 0분에서 시작해서 1번 지역에서 N번 지역까지 가는 최소 시간 구하기
 */

import java.util.*;

class Node {
    int next;
    long period;

    Node(int next, long period) {
        this.next = next;
        this.period = period;
    }
}

public class BJ_24042_횡단보도 {
    static final Long INF = Long.MAX_VALUE;
    static int N, M;
    static List<List<Node>> graph;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = stoi(st.nextToken()); // 지역의 수
        M = stoi(st.nextToken()); // 횡단보도 주기

        graph = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = stoi(st.nextToken());
            int b = stoi(st.nextToken());

            graph.get(a).add(new Node(b, i));
            graph.get(b).add(new Node(a, i));
        }

        System.out.println(dijkstra(1, N));
    }

    public static long dijkstra(int start, int end) {
        long[] dp = new long[N + 1];
        Arrays.fill(dp, INF);
        PriorityQueue<Node> pq = new PriorityQueue<>((o1, o2) -> Long.compare(o1.period, o2.period));
        dp[start] = 0;
        pq.offer(new Node(start, 0));

        while (!pq.isEmpty()) {
            Node now = pq.poll();
            start = now.next;

            if (now.period > dp[now.next]) continue;

//            System.out.printf("start = %d, min = %d\n", start, now.period);
            for (Node node : graph.get(start)) {
                int next = node.next;
                long period = node.period;

//                System.out.printf("%d -> %d ~ %d vs %d\n",start,next,now.period, period);
                if (now.period <= period) period = period + 1;
                // now.period - period : 현재 시간과 목표 신호 시간 사이의 차이
                // M - 1 : 주기 M으로 나눴을 때 나머지가 있으면 몫 +1을 하기 위한 장치
                else period = ((now.period - period + M - 1) / M ) * M + period + 1;
//                else if (now.period / M == 0) period = M + period + 1;
//                else if (now.period % M <= period) period = (now.period / M) * M + period + 1;
//                else period = (now.period / M + 1) * M + period + 1;

                if (dp[next] > period) {
                    dp[next] = period;
                    pq.offer(new Node(next, period));
                }
            }

//            System.out.println(Arrays.toString(dp));
        }

        return dp[end];
    }

    public static int stoi(String str) {
        return Integer.parseInt(str);
    }
}
