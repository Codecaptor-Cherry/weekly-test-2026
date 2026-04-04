package saturday.year26.sat260328;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * N x M 직사각형. 각 부위마다 강도가 다름
 * 항상 3칸을 차지하는 'ㄱ'모양 부메랑
 * 중심칸(꺾이는 부분)은 강도 2배
 * 꼭 모든 부분을 사용해야 되는 것은 아님
 * 만들 수 있는 부메랑들의 강도 합 최댓값 구하기
 *
 * 1 <= N, M <= 5 -> DFS
 * N or M <= 1 -> 0
 * (x, y)를 중심칸으로 설정했을 때 만들 수 있는 부메랑 경우 따지기
 */
public class BJ_18430_무기공학 {
    static int N, M;
    static int ans = 0;
    static int[] dirX = new int[]{-1, 0, 1, 0}; // 상우하좌
    static int[] dirY = new int[]{0, 1, 0, -1};
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = stoi(st.nextToken()); // 행
        M = stoi(st.nextToken()); // 열

        int[][] map = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = stoi(st.nextToken());
            }
        }

        if (N <= 1 || M <= 1) {
            System.out.println(0);
            return;
        }

        DFS(0, 0, 0, map, new boolean[N][M]);

        System.out.println(ans);
    }

    public static void DFS(int x, int y, int sum, int[][] map, boolean[][] visited) {
        if (y == M) { // 행바꿈
            x++;
            y = 0;
        }

        if (x == N) { // 탐색 종료
            ans = Math.max(ans, sum);
            return;
        }

        if (!visited[x][y]) { // (x, y)를 중심칸으로 설정
            visited[x][y] = true;
            for (int i = 0; i < 4; i++) {
                int left = i;
                int right = (i + 1) % 4;

                int lx = x + dirX[left];
                int ly = y + dirY[left];
                int rx = x + dirX[right];
                int ry = y + dirY[right];

                if (!checkRange(lx, ly) || !checkRange(rx, ry)) continue; // 범위 벗어남
                if (visited[lx][ly] || visited[rx][ry]) continue; // 이미 사용된 칸

                visited[lx][ly] = true;
                visited[rx][ry] = true;
                DFS(x, y + 1, sum + map[x][y] * 2 + map[lx][ly] + map[rx][ry], map, visited);
                visited[lx][ly] = false;
                visited[rx][ry] = false;
            }
            visited[x][y] = false;
        }

        DFS(x, y + 1, sum, map, visited); // (x, y)를 중심칸으로 설정 안함
    }

    public static boolean checkRange(int x, int y) {
        if (x < 0 || x >= N || y < 0 || y >= M) return false;

        return true;
    }

    public static int stoi(String str) {
        return Integer.parseInt(str);
    }
}
