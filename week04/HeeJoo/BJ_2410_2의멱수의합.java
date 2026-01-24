package saturday.year26.sat260124;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * 어떤 자연수 N을 2의 멱수의 합으로 나타내는 경우의 수 구하기
 * 2의 멱수 : 2^k로 표현되는 자연수
 * ex) 7 = 1111111 = 111112 = 11122 = 1114 = 1222 = 124 ~ 6가지
 * 답이 커질 수 있으므로 1_000_000_000으로 나눈 나머지를 출력
 *
 * n이 홀수인 경우, 무조건 1이 하나 이상 포함 ~ 무조건 포함되는 1을 빼고 생각하면 n - 1을 표현하는 경우의 수와 동일
 * n이 짝수인 경우, 1이 포함되는 경우와 포함되지 않는 경우로 나뉨
 * 1이 포함되는 경우는 n - 1을 표현하는 경우의 수와 동일 ~ dp[n - 1]
 * 1이 포함되지 않는 경우는 표현에 등장하는 수가 모두 2의 거듭제곱 ~ 이를 전부 2로 나눠주면 n / 2를 표현하는 경우의 수와 동일함 ~ dp[n / 2]
 * n이 홀수인 경우 dp[n] = dp[n - 1]
 * n이 짝수인 경우 dp[n] = dp[n - 1] + dp[n / 2]
 * ex) 8 = 11111111 = 1111112 = 111122 = 11114 = 11222 = 1124 = 2222 = 224 = 44 = 8 ~ 10
 * 1이 포함되지 않은 (2222 = 224 = 44 = 8)를 모두 2로 나누면 (1111 = 112 = 22 = 4)로 4를 나타내는 경우와 같음
 */
public class BJ_2410_2의멱수의합 {
    static final int MOD = 1_000_000_000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        int[] dp = new int[N + 1];
        dp[1] = 1;

        for (int i = 2; i <= N; i++) {
            if (i % 2 == 0) {
                dp[i] = (dp[i - 1] + dp[i / 2]) % MOD;
            } else { // 홀수의 경우 i - 1에 1만 붙이므로 dp[i - 1]과 같음
                dp[i] = dp[i - 1];
            }
        }

        System.out.println(dp[N] % MOD);
    }
}
