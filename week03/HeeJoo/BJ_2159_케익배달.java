package saturday.year26.sat260117;

/*
 * 주문이 들어온 순서대로 케이크 배달
 * N명의 고객의 위치는 순서대로 100_000 x 100_000 격자의 정수 좌표로 주어짐
 * 상하좌우 이동 가능
 * 배달 완료 : 고객의 위치 or 고객의 상하좌우 인접 격자점
 * 배달을 끝낼 수 있는 최단 거리 계산
 * 순서에 어긋난 사람에게 배달할 수 있는 위치에 있더라도 케이크를 주지 않고 순서대로 배달해야 함 !!!
 *
 * 1. 고객 좌표 저장
 * 2. 현재 위치에서 목표 좌표를 순서대로 방문 ~ 중 + 상하좌우
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BJ_2159_케익배달 {
    static final long INF = Long.MAX_VALUE;
    static final int RNG = 100_000;
    static int[] dirX = {0, -1, 1, 0, 0}; // 중상하좌우
    static int[] dirY = {0, 0, 0, -1, 1}; // 중상하좌우
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = stoi(br.readLine());

        // 시작 위치
        String[] inputs = br.readLine().split(" ");
        int a = stoi(inputs[0]);
        int b = stoi(inputs[1]);

        // 고객 위치
        int[][] targets = new int[N][2];
        for (int i = 0; i < N; i++) {
            inputs = br.readLine().split(" ");
            targets[i][0] = stoi(inputs[0]);
            targets[i][1] = stoi(inputs[1]);
        }

        // dp[i][j] : (a, b) -> i번째 좌표(x, y)의 중상하좌우의 최단거리
        long[][] dp = new long[N + 1][5];
        for (int i = 1; i <= N; i++) {
            Arrays.fill(dp[i], INF);
        }

        // 초기 위치 -> 첫 번째 좌표
        int x = 0, y = 0;
        for (int i = 0; i < 5; i++) {
            x = targets[0][0] + dirX[i];
            y = targets[0][1] + dirY[i];

            dp[1][i] = manhattan(a, b, x, y);
        }

        for (int i = 2; i <= N; i++) {
            for (int j = 0; j < 5; j++) {
                // 시작 위치
                a = targets[i - 2][0] + dirX[j];
                b = targets[i - 2][1] + dirY[j];

                if (!checkRange(a, b)) continue;

                for (int k = 0; k < 5; k++) {
                    // 고객 위치
                    x = targets[i - 1][0] + dirX[k];
                    y = targets[i - 1][1] + dirY[k];

                    if (!checkRange(x, y)) continue;

                    dp[i][k] = Math.min(dp[i][k], dp[i - 1][j] + manhattan(a, b, x, y));
                }
            }
        }

        long ans = INF;
        for (int i = 0; i < 5; i++) {
            ans = Math.min(ans, dp[N][i]);
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
