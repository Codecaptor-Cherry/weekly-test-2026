package saturday.year26.sat260411;

/*
 * 1 ~ N번 도시, 도시들 사이에는 길 존재
 * 어느 한 도시에서 출발해 N개의 도시를 모두 거쳐 다시 원래의 도시로 돌아오는 순회 여행 경로 계획
 * 한 번 갔던 도시로는 다시 갈 수 없음(출발 = 도착 제외)
 * 최소 비용 여행 계획 구하기
 * 
 * 최소 비용 여행 계획은 순환 경로라 어디서 시작해도 상관 xxx ~ 입력도 순환인 경우만 주어짐
 * -1과 INF 같은 특이값을 써서 방문 여부 체크
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BJ_2098_외판원순회 {
    static int N, range;
    static int[][] W;
    static final int INF = 1_000_000_000;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = stoi(br.readLine());

        W = new int[N][N];
        StringTokenizer st;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                W[i][j] = stoi(st.nextToken());
            }
        }

        // dp[i][j] : 현재 도시 i, 이미 방문한 도시 정보 j ~ i번 도시에서 도시 집합 j를 모두 방문한 최소 비용
        range = (1 << N) - 1;
        int[][] dp = new int[N][range];
        for (int i = 0; i < N; i++) {
            Arrays.fill(dp[i], -1);
        }

        System.out.println(tsp(0, 1, dp));
    }

    public static int tsp(int x, int bit, int[][] dp) {
        // 모든 도시 방문 완료
        if (bit == range) {
            if (W[x][0] == 0) { // 현재 위치(x)에서 출발 지점(0)으로 돌아가는 경로 없음
                return INF;
            } else {
                return W[x][0];
            }
        }

        // 이미 방문한 경로
        if (dp[x][bit] != -1) return dp[x][bit];

        // 방문 표시
        dp[x][bit] = INF;

        // 도시 탐색
        for (int i = 0; i < N; i++) {
            int next = 1 << i; // 방문할 도시

            // 이번 도시를 이미 방문한 상태 or 경로 없음
            if ((bit & next) != 0 || W[x][i] == 0) continue;

            dp[x][bit] = Math.min(dp[x][bit], tsp(i, bit | next, dp) + W[x][i]);
        }

        return dp[x][bit];
    }

    public static int stoi(String str) {
        return Integer.parseInt(str);
    }
}
