package saturday.year26.sat260110;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*
 * n x m 체스 판과 상대 팀의 Q, K, P의 위치가 주어졌을 때, 안전한 칸이 몇 칸인지 세는 프로그램
 * Queen :  가로, 세로, 대각선으로 갈 수 있는 만큼 최대한 많이 이동 가능 but 장애물에 막히면 끝
 * Knight : 2 x 3 직사각형의 반대쪽 꼭짓점으로 이동. 장애물 있어도 이동 가능
 * Pawn : 장애물 역할
 */
public class BJ_1986_체스 {
    static int[] kx = new int[]{-2, -1, 1, 2, 2, 1, -1, -2};
    static int[] ky = new int[]{1, 2, 2, 1, -1, -2, -2, -1};
    static int[] qx = new int[]{-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] qy = new int[]{0, 1, 1, 1, 0, -1, -1, -1};
    static int n, m;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        n = stoi(st.nextToken());
        m = stoi(st.nextToken());
        int[][] map = new int[n][m];

        // 1. 퀸
        st = new StringTokenizer(br.readLine());
        int q = stoi(st.nextToken());
        List<int[]> qList = new ArrayList<>();
        for (int i = 0; i < q; i++) {
            int x = stoi(st.nextToken()) - 1;
            int y = stoi(st.nextToken()) - 1;
            qList.add(new int[]{x , y});

            map[x][y] = 1;
        }

        // 2. 나이트
        st = new StringTokenizer(br.readLine());
        int k = stoi(st.nextToken());
        List<int[]> kList = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            int x = stoi(st.nextToken()) - 1;
            int y = stoi(st.nextToken()) - 1;
            kList.add(new int[]{x , y});

            map[x][y] = 2;
        }


        // 3. 폰
        st = new StringTokenizer(br.readLine());
        int p = stoi(st.nextToken());
        for (int i = 0; i < p; i++) {
            int x = stoi(st.nextToken()) - 1;
            int y = stoi(st.nextToken()) - 1;

            map[x][y] = 3;
        }

        // 구역 계산 : 나이트
        for (int[] point : kList) {
            knight(point[0], point[1], map);
        }

        // 구역 계산 : 퀸
        for (int[] point : qList) {
            queen(point[0], point[1], map);
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (map[i][j] == 0) ans++;
            }
        }

        System.out.println(ans);

    }

    public static void queen(int x, int y, int[][] map) {
        // 8방향
        for (int i = 0; i < 8; i++) {
            int dx = x + qx[i];
            int dy = y + qy[i];

            while (check(dx, dy) && map[dx][dy] <= 0) {
                map[dx][dy] = -1;
                dx += qx[i];
                dy += qy[i];
            }
        }
    }

    public static void knight(int x, int y, int[][] map) {
        for (int i = 0; i < 8; i++) {
            int dx = x + kx[i];
            int dy = y + ky[i];

            if (!check(dx, dy) || map[dx][dy] != 0) continue;

            map[dx][dy] = -1;
        }
    }

    public static boolean check(int x, int y) {
        if (x < 0 || x >= n || y < 0 || y >= m) return false;

        return true;
    }

    public static void print(int[][] map) {
        for (int[] arr : map) {
            for (int v : arr) {
                System.out.printf("%d ", v);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static int stoi(String str) {
        return Integer.parseInt(str);
    }
}
