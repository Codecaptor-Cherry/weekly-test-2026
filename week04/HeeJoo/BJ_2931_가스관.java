package saturday.year26.sat260124;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

/*
 * R x C
 * 7가지 기본 블록 |, -, +, 1, 2, 3, 4
 * 가스는 모스크바에서 자그레브로 흐르며, 블록을 통해 양방향으로 흐를 수 있음
 * 완성된 도면에서 특정 한 칸이 지워졌는데, 지워진 블록의 위치와 기존 블록의 종류 구하기
 * 항상 답이 있고 가스의 흐름이 유일한 경우만 존재
 * 모스크바와 자그레브는 하나의 블록과 인접해 있음
 * 불필요한 블록이 존재하지 않음
 *
 * 1. 끊어진 부분 찾기
 * 2. 이어져야 하는 방향 찾기
 * | : 상하
 * - : 좌우
 * + : 4방향
 * 1 : 하우
 * 2 : 상우
 * 3 : 상좌
 * 4 : 하좌
 */
public class BJ_2931_가스관 {
    static final char[] blocks = new char[]{'|', '-', '+', '1', '2','3', '4'};
    static int[] dirX = new int[]{-1, 1, 0, 0};
    static int[] dirY = new int[]{0, 0, -1, 1};
    static int R, C;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] inputs = br.readLine().split(" ");
        R = Integer.parseInt(inputs[0]);
        C = Integer.parseInt(inputs[1]);

        char[][] map = new char[R][C];
        int x = 0, y = 0;
        for (int i = 0; i < R; i++) {
            String input = br.readLine();
            for (int j = 0; j < C; j++) {
                map[i][j] = input.charAt(j);

                if (map[i][j] == 'M') { // 출발 모스크바
                    x = i;
                    y = j;
                }
            }
        }

        // 1. 사라진 타일 찾기
        int[] point = bfs(x, y, map);

        // 2. 이어진 방향 찾기
        char ans = solve(point[0], point[1], map);

        System.out.printf("%d %d %c\n", point[0] + 1, point[1] + 1, ans);
    }

    public static char solve(int x, int y, char[][] map) {
        boolean[] arr = new boolean[4];

        int sum = 0;
        for (int i = 0; i < 4; i++) {
            int dx = x + dirX[i];
            int dy = y + dirY[i];

            if (!checkRange(dx, dy) || map[dx][dy] == '.') continue; // 가스의 흐름이 유일하므로 여기는 도착 타일을 제외할 필요 없음

            int dist = getDirect(map[dx][dy], i);

            if (dist != -1) {
                arr[i] = true;
                sum++;
            }
        }

        if (sum == 4) return '+'; // 4방향

        if (arr[0] && arr[1]) return '|';// 상하
        else if (arr[2] && arr[3]) return '-'; // 좌우
        else if (arr[1] && arr[3]) return '1'; // 하우
        else if (arr[0] && arr[3]) return '2'; // 상우
        else if (arr[0] && arr[2]) return '3'; // 상좌
//        else if (arr[1] && arr[2]) return '4'; // 하좌
        else return '4'; // 하좌
    }

    public static int[] bfs(int x, int y, char[][] map) {
        Queue<int[]> queue = new ArrayDeque<>(); // 현재 타일의 x, y 좌표 + 이전 타일에서의 방향
        boolean[][] visited = new boolean[R][C];

        // 모스크바 시작
        visited[x][y] = true;
        for (int i = 0; i < 4; i++) {
            int dx = x + dirX[i];
            int dy = y + dirY[i];

            if (!checkRange(dx, dy) || map[dx][dy] == '.' || map[dx][dy] == 'Z') continue; // 범위를 벗어남 + 빈 타일 + 도착 타일 제외

            queue.offer(new int[]{dx, dy, i}); // 이어진 타일 정보 저장
            visited[dx][dy] = true;
        }

        while (!queue.isEmpty()) {
            int[] now = queue.poll();
            x = now[0];
            y = now[1];
            int pre = now[2];

            int dist = getDirect(map[x][y], pre); // 이동 방향 구하기 ~ 타일의 종류와 이전 방향에 따라 달라짐

            // 다음 좌표 계산
            if (dist == 4) { // 4방향
                for (int i = 0; i < 4; i++) {
                    int dx = x + dirX[i];
                    int dy = y + dirY[i];

                    if (!checkRange(dx, dy) || visited[dx][dy] || map[dx][dy] == 'Z') continue;
                    if (map[dx][dy] == '.') { // 경로가 비어있는 경우 ~ 채워넣어야 되는 타일
                        return new int[]{dx, dy, i};
                    }

                    queue.offer(new int[]{dx, dy, i}); // 다음 좌표와 이동 방향 저장
                    visited[dx][dy] = true;
                }
            } else {
                int dx = x + dirX[dist];
                int dy = y + dirY[dist];

                if (!checkRange(dx, dy) || visited[dx][dy] || map[dx][dy] == 'Z') continue;
                if (map[dx][dy] == '.') {
                    return new int[]{dx, dy};
                }

                queue.offer(new int[]{dx, dy, dist});
                visited[dx][dy] = true;
            }
        }

        return null;
    }

    public static int getDirect(char ch, int pre) {
        if (ch == '|') { // | : 상하01
            if (pre == 0) return 0;
            else if (pre == 1) return 1;
        }
        else if (ch == '-') { // - : 좌우23
            if (pre == 2) return 2;
            else if (pre == 3) return 3;
        }
        else if (ch == '+') { // + : 4방향
            return 4;
        }
        else if (ch == '1') { // 1 : 하우13
            if (pre == 0) return 3;
            else if (pre == 2) return 1;
        }
        else if (ch == '2') { // 2 : 상우03
            if (pre == 1) return 3;
            else if (pre == 2) return 0;
        }
        else if (ch == '3') { // 3 : 상좌02
            if (pre == 1) return 2;
            else if (pre == 3) return 0;
        }
        else if (ch == '4') { // 4 : 하좌12
            if (pre == 0) return 2;
            else if (pre == 3) return 1;
        }

        return -1;
    }

    public static boolean checkRange(int x, int y) {
        if (x < 0 || x >= R || y < 0 || y >= C) return false;

        return true;
    }
}
