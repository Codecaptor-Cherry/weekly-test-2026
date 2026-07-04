/*
 * m x n 격자
 * 출발 : 좌상단
 * 도착 : 우하단
 * 에너지가 0 이하가 될 시 사망
 * 몬스터(음수), 회복(양수)
 * 오른쪽 또는 아래쪽으로만 이동 가능
 * 목적지에 도착하기 위해 필요한 최소 초기 체력 구하기

 * 역방향 DP
 * dp[i][j] : (i, j)에서 도착지까지 가는데 필요한 최소 체력
 */

class Solution {
    static final int INF = 1_000_000_000;
    public int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length;
        int n = dungeon[0].length;
        int[][] dp = new int[m + 1][n + 1];

        for (int[] arr : dp) {
            Arrays.fill(arr, INF);
        }

        // 도착지(dp[m - 1][n - 1]를 기준으로 초기화
        dp[m - 1][n] = 1;
        dp[m][n - 1] = 1;

        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                int need = Math.min(dp[i + 1][j], dp[i][j + 1]) - dungeon[i][j]; // 오른쪽과 아래쪽 칸 중 필요한 체력이 더 적은 칸 선택

                if (need <= 0) {
                    dp[i][j] = 1;
                } else {
                    dp[i][j] = need;
                }
            }
        }

        return dp[0][0];
    }

    public void print(int[][] map) {
        for (int[] arr : map) {
            for (int k : arr) {
                if (k == INF) System.out.printf("F ", k);
                else System.out.printf("%d ", k);
            }
            System.out.println();
        }
        
        System.out.println();
    }
}
