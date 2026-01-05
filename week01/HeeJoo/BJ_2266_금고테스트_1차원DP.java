package saturday.year26.sat260103;

/*
 * 방법2 : T번 던져서 확인할 수 있는 최대 층 수 계산
 *
 * 예시) K = 2
 * 0회 시도 : dp[1] = 0, dp[2] = 0
 * 1회 시도 : dp[2] = dp[1] + dp[2] + 1 = 1, dp[1] = dp[0] + dp[1] + 1 = 1 ~ 기회가 1번이면 금고가 몇 개능 1층만 확인 가능
 * 2회 시도 : dp[2] = dp[1] + dp[2] + 1 = 3, dp[1] = dp[0] + dp[1] + 1 = 2 ~ 기회 2번, 금고 2개면 최대 3층까지 확인 가능
 * 3회 시도 : dp[2] = dp[1] + dp[2] + 1 = 6, dp[1] = dp[0] + dp[1] + 1 = 3 ~ 기회 3번, 금고 2개면 최대 6층까지 확인 가능
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BJ_2266_금고테스트_1차원DP {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] inputs = br.readLine().split(" ");

        int n = stoi(inputs[0]);
        int k = stoi(inputs[1]);

        int[] dp = new int[k + 1]; // dp[x] : x개의 금고를 가지고 현재까지 시도한 횟수 내에서 가능한 최대 층 수
        int cnt = 0; // 시도 횟수

        while (dp[k] < n) {
            cnt++;

            // dp[x] = 금고가 깨진 경우의 층 수 + 금고가 깨지지 않은 경우의 층 수 + 현재 던진 층
            // 금고가 깨진 경우의 층 수 << 이전 층의 정보가 필요하므로 뒤에서부터 계산
            for (int i = k; i > 0; i--) {
                dp[i] = dp[i - 1] + dp[i] + 1;
            }
        }

        System.out.println(cnt); // N층 이상을 커버할 수 있게 된 시점의 시도 횟수
    }

    public static int stoi(String str) {
        return Integer.parseInt(str);
    }
}
