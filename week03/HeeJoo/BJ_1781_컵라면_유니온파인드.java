package saturday.year26.sat260117;

/*
 * PQ : 모든 데이터를 조회하며 작은 것을 버리는 방식
 * UF : 큰 데이터부터 빈 자리에 넣는 방식
 * PQ는 1, 2, 3 ... 데드라인 순서대로 확인해야 하지만, UF는 find로 빈 날짜 즉시 점프
 * 시간 : 1044ms -> 584ms
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BJ_1781_컵라면_유니온파인드 {
    static int[] parent;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = stoi(br.readLine());

        // 데드라인, 컵라면 개수
        // 컵라면 개수 내림차순
        PriorityQueue<int[]> pq = new PriorityQueue<>(((o1, o2) -> o2[1] - o1[1]));
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int deadLine = stoi(st.nextToken());
            int cnt = stoi(st.nextToken());

            pq.offer(new int[]{deadLine, cnt});
        }

        // parent 초기화
        parent = new int[N + 1];
        for (int i = 0; i <= N; i++) {
            parent[i] = i;
        }

        int ans = 0;
        for (int i = 0; i < N; i++) {
            int[] problem = pq.poll();
            int deadLine = problem[0];
            int cnt = problem[1];

            // 현재 데드라인 이내에 빈 자리가 있는지 확인
            int availableDay = find(deadLine);

            if (availableDay > 0) { // parent[a] = b : 비어있는 날짜는 b일
                // 자리가 있다면 해당 날짜에 문제 풀기
                // 이제 해당 날짜는 못 쓰니까 바로 전날과 통합
                ans += cnt;
                union(availableDay, availableDay - 1);
            }
        }

        System.out.println(ans);
    }

    public static int find(int x) {
        if (x == parent[x]) return x;

        return parent[x] = find(parent[x]);
    }

    public static void union(int x, int y) {
        x = find(x);
        y = find(y);

//        if (x < y) parent[y] = x;
//        else parent[x] = y;
        // day, day - 1의 통합이므로 둘 다 이전 날인 day - 1을 가지도록 함
        if (x != y) parent[x] = y;
    }

    public static int stoi(String str) {
        return Integer.parseInt(str);
    }

}
