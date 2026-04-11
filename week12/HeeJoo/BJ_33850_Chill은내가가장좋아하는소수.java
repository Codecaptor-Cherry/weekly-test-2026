package saturday.year26.sat260411;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * 2 x n 격자의 각 칸에 양의 정수가 하나씩
 * 2 x 1 or 1 x 2 타일로 격자를 빈틈없이 채워 점수 얻기
 * 한 타일이 덮고 있는 수의 합이 소수라면 a점, 아니라면 b점
 * 최고 점수 구하기
 *
 * 1. 소수 판별
 * 2. 타일 덮기
 *
 * dp[i] = Math.max(dp[i - 2] + 가로 2개, dp[i - 2] + 세로 2개, dp[i - 1] + 세로 1개)
 */
public class BJ_33850_Chill은내가가장좋아하는소수 {
    static final int size = 200_000;
    static boolean[] prime;
    static int a, b;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = stoi(st.nextToken());
        a = stoi(st.nextToken());
        b = stoi(st.nextToken());

        int[][] map = new int[2][n];
        String[] line1 = br.readLine().split(" ");
        String[] line2 = br.readLine().split(" ");
        for (int i = 0; i < n; i++) {
            map[0][i] = stoi(line1[i]);
            map[1][i] = stoi(line2[i]);
        }

        // 1. 소수 판별 테이블
        prime = new boolean[size + 1];
        makePrimeArray();

        if (n == 1) { // 예외(n = 1) : dp array index out of bounds
            System.out.println(getScore(map[0][0] + map[1][0]));
            return;
        }

        // 2. 타일 덮기
        int[] dp = new int[n + 1];
        dp[1] = getScore(map[0][0] + map[1][0]);

        int row = map[0][1] + map[1][1];
        int up = map[0][0] + map[0][1];
        int down = map[1][0] + map[1][1];
        dp[2] = Math.max(dp[1] + getScore(row), dp[0] + getScore(up) + getScore(down));

        for (int i = 3; i <= n; i++) {
            row = map[0][i - 1] + map[1][i - 1];
            up = map[0][i - 2] + map[0][i - 1];
            down = map[1][i - 2] + map[1][i - 1];
            dp[i] = Math.max(dp[i - 1] + getScore(row), dp[i - 2] + getScore(up) + getScore(down));
            System.out.printf("row = %d -> %d, up = %d, down = %d -> %d\n",row,getScore(row), up,down, getScore(up) + getScore(down));
        }

        System.out.println(Arrays.toString(dp));
        System.out.println(dp[n]);
    }

    public static int getScore(int x) {
        boolean flag = isPrime(x);

        if (flag) return a;
        else return b;
    }

    public static void makePrimeArray() {
        for (int i = 2; i <= Math.sqrt(size); i++) {
            if (prime[i]) continue;

            for (int j = i * i; j <= size; j += i) {
                prime[j] = true;
            }
        }
    }

    public static boolean isPrime(int x) {
        if (!prime[x]) return true;
        else return false;
    }

    public static int stoi(String str) {
        return Integer.parseInt(str);
    }
}
