package saturday.year26.sat260103;

/*
 * 가로 N, 세로 M 격자 형태
 * 최단거리로 집에서 학교까지 가는 경우의 수 구하기
 * 도로가 공사 중일 때는 해당 도로를 이용할 수 없음
 * (0, 0)에서 (N, M)까지 가는 서로 다른 경로의 경우의 수 구하기
 *
 * (상 -> 하) + (좌 -> 우)
 * 공사 중인 도로는 제외해야 하는데 ... 어떻게 ? 비트마스킹으로 방향 체크 ?
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_1577_도로의개수 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = stoi(st.nextToken()); // 가로
        int M = stoi(st.nextToken()); // 세로

        int K = stoi(br.readLine()); // 공사 중인 도로의 개수
        int[][] map = new int[N + 1][M + 1];
        for (int i = 0; i < K; i++) {
            st = new StringTokenizer(br.readLine());

            int a = stoi(st.nextToken());
            int b = stoi(st.nextToken());
            int c = stoi(st.nextToken());
            int d = stoi(st.nextToken());

            // flag : 동서남북
            if (a < c) { // a남, c북
                map[a][b] |= 1 << 2;
                map[c][d] |= 1 << 1;
            }
            else if (a > c) { // a북, c남
                map[a][b] |= 1 << 1;
                map[c][d] |= 1 << 2;
            }
            else if (b < d) { // b서, d동
                map[a][b] |= 1 << 3;
                map[c][d] |= 1 << 4;
            }
            else if (b > d) { // b동, d서
                map[a][b] |= 1 << 4;
                map[c][d] |= 1 << 3;
            }
        }

        long[][] dp = new long[N + 1][M + 1];
        dp[0][0] = 1;

        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= M; j++) {
                // 상 -> 하 ~ 남 -> 북
                if (i > 0 && ((map[i - 1][j] & 1 << 2) == 0  || (map[i][j] & 1 << 1) == 0)) {
                    dp[i][j] += dp[i - 1][j];
                }

                // 좌 -> 우 ~ 서 -> 동
                if (j > 0 && ((map[i][j - 1] & 1 << 3) == 0  || (map[i][j] & 1 << 1) == 4)) {
                    dp[i][j] += dp[i][j - 1];
                }
            }
        }

//        print(map);
//        for (int i = 0; i <= N; i++) {
//            for (int j = 0; j <= M; j++) {
//                System.out.printf("%d ", dp[i][j]);
//            }
//            System.out.println();
//        }
//        System.out.println();

        System.out.println(dp[N][M]);
    }

    public static void print(int[][] map) {
        for (int[] arr : map) {
            for (int k : arr) {
                System.out.printf("%d ", k);
            }
            System.out.println();
        }
        System.out.println();
    }
    public static int stoi(String str) {
        return Integer.parseInt(str);
    }
}
