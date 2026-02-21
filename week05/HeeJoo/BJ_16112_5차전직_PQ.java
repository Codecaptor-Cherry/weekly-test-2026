package saturday.year26.sat260131;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
 * 1. 우선순위 큐 : 경험치 오름차순
 * 2. 총 경험치 합 = 활성화된 스톤 개수 * 이번 경험치
 */
public class BJ_16112_5차전직_PQ {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = stoi(st.nextToken());
        int k = stoi(st.nextToken());

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        st = new StringTokenizer(br.readLine());
        while (st.hasMoreTokens()) {
            pq.offer(stoi(st.nextToken()));
        }

        pq.poll(); // 첫 번째 퀘스트의 경험치 포함 xxx
        long activate = 1; // 첫 번째 퀘스트의 스톤 활성화
        long ans = 0;
        for (int i = 1; i < n; i++) { // 첫 번째 퀘스트는 완료했으니 1부터 시작
            // 중요 !!! activate와 pq의 범위는 int 내이나 (1 ≤ k ≤ n ≤ 3 * 10^5, 0 <= pq <= 10^8)
            // 곱셈 결과가 int 범위를 벗어날 수 있으므로 오버플로우 방지를 위해 곱셈 연산 중 하나는 long으로 둬야 함!!!
            ans += activate * pq.poll();

            if (activate < k) {
                activate++;
            }
        }

        System.out.println(ans);
    }

    public static int stoi(String str) {
        return Integer.parseInt(str);
    }
}
