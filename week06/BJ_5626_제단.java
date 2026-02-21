package saturday.year26.sat260207;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
/*
 * N열 제단
 * 각 열의 높이는 모두 정수이며, 가장 처음에 모든 열의 높이는 0
 * 제단은 다음과 같은 규칙으로 만들어짐
 * 1. 같은 높이를 가지는 연속하는 열 선택
 * 2. 선택한 첫 열과 마지막 열을 제외한 모든 열의 높이를 1만큼 증가
 * 남은 제단의 높이를 가지고, 가능한 제단의 경우의 수 구하기
 *
 * 제단의 높이가 0이여도 훔칠 수 있음... 존재는 하니까~
 * 인접 높이 차이는 1 이하
 * dp[i][j] = i번째 위치까지 왔을 때 높이가 j인 경우의 수
 * 제단의 최대 높이 = N / 2
 */
public class BJ_5626_제단 {
    static final int MOD = 1_000_000_007;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = stoi(br.readLine());

        int[] arr = Arrays.stream(br.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        int maxHeight = N / 2; // 최대 높이
        int[][] dp = new int[N][maxHeight + 1];

        // 시작과 끝은 반드시 0 or -1
        if (arr[0] > 0 || arr[N - 1] > 0) {
            System.out.println(0);
            return;
        } else {
            dp[0][0] = 1;
        }

        for (int i = 1; i < N; i++) { // 현재 제단의 위치(열)
            for (int j = 0; j <= maxHeight; j++) { // 현재 제단의 높이
                // -1이 아닌 경우, 고정된 높이만 가능
                if (arr[i] != -1 && arr[i] != j) continue;

                int val = 0;

                // 높이 유지
                val = (val + dp[i - 1][j]) % MOD;

                // 높이 증가
                if (j - 1 >= 0) {
                    val = (val + dp[i - 1][j - 1]) % MOD;
                }

                // 높이 감소
                if (j + 1 <= maxHeight) {
                    val = (val + dp[i - 1][j + 1]) % MOD;
                }

                dp[i][j] = val;
            }
        }

        System.out.println(dp[N - 1][0] % MOD); // 마지막 제단은 반드시 0
    }

    public static int stoi(String str) {
        return Integer.parseInt(str);
    }
}
