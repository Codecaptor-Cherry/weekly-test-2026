package saturday.year26.sat260117;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * 1. String.split() -> StringTokenizer
 * 2. 불필요한 좌표 재계산 제거
 * 3. 메모리 레이아웃 최적화 : 이전 상태(j) <-> 현재 상태(k) why? dp[i][k]를 갱신하므로 k가 안쪽 루프에서 변하는 것이 캐시 효율에 더 유리
 *
 * 메모리 : 54636KB -> 45700KB
 * 시간 : 464ms -> 368ms
 */

public class BJ_2159_케익배달_속도개선 {
    static final long INF = Long.MAX_VALUE;
    static final int RNG = 100_000;
    static int[] dirX = {0, -1, 1, 0, 0}; // 중상하좌우
    static int[] dirY = {0, 0, 0, -1, 1}; // 중상하좌우
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = stoi(br.readLine());

        // 시작 위치
        // 1. String.split() -> StringTokenizer
        StringTokenizer st = new StringTokenizer(br.readLine());
        int a = stoi(st.nextToken());
        int b = stoi(st.nextToken());

        // 고객 위치
        int[][] targets = new int[N][2];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            targets[i][0] = stoi(st.nextToken());
            targets[i][1] = stoi(st.nextToken());
        }

        // dp[순서][5방향]
        long[] prevDp = new long[5];
        long[] curDp = new long[5];

        // 초기 위치 -> 첫 번째 좌표
        for (int i = 0; i < 5; i++) {
            int da = targets[0][0] + dirX[i];
            int db = targets[0][1] + dirY[i];

            if (!checkRange(da, db)) continue;

            prevDp[i] = manhattan(a, b, da, db);
        }

        // DP 진행
        // N이 크므로 2차원 배열 -> 1차원 배열 2개로 공간 최적화
        for (int i = 1; i < N; i++) {
            Arrays.fill(curDp, INF);

            // 2, 3 : 반복문 순서 바꿔서 캐시 이용 ~ N = 100_000일 때, 약 200만번의 덧셈 연산 감소
            for (int j = 0; j < 5; j++) { // 목표 위치
                int da = targets[i][0] + dirX[j];
                int db = targets[i][1] + dirY[j];

                if (!checkRange(da, db)) continue;

                for (int k = 0; k < 5; k++) { // 이전 위치
                    a = targets[i - 1][0] + dirX[k];
                    b = targets[i - 1][1] + dirY[k];

                    if (!checkRange(a, b)) continue;

                    long dist = manhattan(a, b, da, db);

                    curDp[j] = Math.min(curDp[j], prevDp[k] + dist);
                }
            }

            prevDp = curDp.clone();

        }

        long ans = INF;
        for (long d : prevDp) {
            ans = Math.min(ans, d);
        }

        System.out.println(ans);
    }

    public static long manhattan(int a, int b, int x, int y) {
        return Math.abs(a - x) + Math.abs(b - y);
    }

    public static boolean checkRange(int x, int y) {
        if (x < 0 || x > RNG || y < 0 || y > RNG) return false;

        return true;
    }

    public static int stoi(String str) {
        return Integer.parseInt(str);
    }
}
