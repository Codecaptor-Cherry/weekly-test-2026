package saturday.year26.sat260103;

/*
 * N층 빌딩
 * F층 : 금고를 떨어뜨렸을 때 부서지는 최소 층
 * K개의 금고를 이용하여 F층 구하기
 * 금고가 부서진 경우 재활용 불가능 ~ 부서지지 않았다면 재활용 가능
 * E(N, K) : K개의 금고를 가지고 F층을 알아낼 수 있는 최소한의 금고 낙하 횟수
 * E(N, K) 구하기
 *
 * DP
 * E(N, 1) = N
 * E(4, 2) = 3
 * E(8, 3) = 4
 * E(10, 2) = 4
 * 시작 지점 알 수 없음 ... 전부 확인해서 최악의 경우를 계산해야 함
 * X층에서 낙하했을 때, 깨진 경우 vs 안 깨진 경우
 *
 * 방법 1 : dp[a][b] : b층에서 a개의 금고가 남은 경우
 * dp[층][금고] vs dp[금고][층] : 행을 고정하고 열을 순서대로 읽는 것이 캐시 효율에 좋음 + 보통 층 수가 금고 수보다 큰 경우가 많으므로 dp[금고][층]이 좋음
 * 방법 2 : dp[a] : a개의 금고로 확인할 수 있는 최대 층수
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BJ_2266_금고테스트_2차원DP {
    static final int INF = 1_000;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] inputs = br.readLine().split(" ");

        int n = stoi(inputs[0]);
        int k = stoi(inputs[1]);

        int[][] dp = new int[k + 1][n + 1];

        // 초기값 설정
        // 금고가 1개인 경우, 1층부터 순서대로 던져야 함
        for (int i = 0; i <= n; i++) {
            dp[1][i] = i;
        }

        // 금고가 i개일 때 1층만 확인하는 경우
        for (int i = 0; i <= k; i++) {
            dp[i][1] = 1;
        }


        // 점화식
        for (int i = 2; i <= k; i++) {
            for (int j = 2; j <= n; j++) {
                dp[i][j] = INF;

                // x층에서 금고를 던졌을 때 최악의 최솟값 찾기
                for (int x = 1; x <= j; x++) {
                    // x층에서 금고 깨짐 vs 금고 안 깨짐
                    // dp[i - 1][x - 1] : x층에서 금고가 깨진 경우, i - 1개의 금고로 아래쪽 x - 1개의 층 확인
                    // dp[i][j - x] : x층에서 금고가 깨지지 않은 경우, i개의 금고로 위쪽 j - x개의 층 확인
                    // 위 두 경우 중 최악의 경우 선택한 후(max), 최선의 최악으로 갱신(min)
                    int res = Math.max(dp[i - 1][x - 1], dp[i][j - x]) + 1;
                    dp[i][j] = Math.min(dp[i][j], res);
                }
            }
        }

        System.out.println(dp[k][n]);
    }

    public static int stoi(String str) {
        return Integer.parseInt(str);
    }
}
