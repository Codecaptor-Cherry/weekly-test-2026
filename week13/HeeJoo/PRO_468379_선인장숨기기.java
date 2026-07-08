/*
 * m x n 격자
 * (0, 0) ~ (m - 1, n - 1)
 * 어딘가에 w x h 크기의 선인장 구역 조성
 * 비구름은 미리 정해진 순서대로 격자의 여러 칸에 비를 뿌림
 * 빗방울이 처음으로 선인장 구역에 포함된 칸에 떨어졌을 때, 그 시점을 선인장이 처음으로 비를 맞는 순간으로 기록
 * 선인장이 최대한 비를 늦게 맞도록 구역 정하기
 * 후보가 여러 개라면 가장 위쪽 행, 왼쪽 열에 위치한 구역 선택
 
 * deque, 슬라이딩 윈도우
 * 가로 최소값, 세로 최소값으로 w x h 구역의 최소값 찾기
 */

import java.util.*;

class Solution {
    static final int INF = 1_000_000;
    static int M, N;
    static int A, B;
    public int[] solution(int m, int n, int h, int w, int[][] drops) {            
        int[][] map = new int[m][n];
        for (int[] arr : map) {
            Arrays.fill(arr, INF); // 비 안옴
        }

        int cnt = 1;
        for (int[] drop : drops) {
            int x = drop[0];
            int y = drop[1];
            
            map[x][y] = cnt++; // 비가 온 순서
        }

        int[][] rowMin = new int[m][n - w + 1]; // 가로 최소값
        for (int i = 0; i < m; i++) {
            Deque<Integer> dq = new ArrayDeque<>(); // 가로 슬라이딩 윈도우
            for (int j = 0; j < n; j++) {
                // 1. 슬라이딩 윈도우 범위 밖인 왼쪽 인덱스 제거
                while (!dq.isEmpty() && dq.peekFirst() <= j - w) {
                    dq.pollFirst();
                }
                
                // 2. 새로 추가된 값보다 크거나 같은 값 제거
                while (!dq.isEmpty() && map[i][dq.peekLast()] >= map[i][j]) {
                    dq.pollLast();
                }
                
                // 3. 현재 인덱스 추가
                dq.offerLast(j);
                
                // 4. 결과 기록
                if (j >= w - 1) {
                    rowMin[i][j - w + 1] = map[i][dq.peekFirst()];                
                }
            }
        }
        
        int[][] winMin = new int[m - h + 1][n - w + 1]; // 선인장 영역(가로 + 세로) 최소값
        for (int i = 0; i < n - w + 1; i++) {
            Deque<Integer> dq = new ArrayDeque<>();
            for (int j = 0; j < m; j++) { // 세로 슬라이딩 윈도우
                // 1. 슬라이딩 윈도우 범위 밖인 윗쪽 인덱스 제거
                while (!dq.isEmpty() && dq.peekFirst() <= j - h) {
                    dq.pollFirst();
                }
               
                // 2. 새로 추가된 값보다 크거나 같은 값 제거
                while (!dq.isEmpty() && rowMin[dq.peekLast()][i] >= rowMin[j][i]) {
                    dq.pollLast();
                }
                
                
                // 3. 현재 인덱스 추가
                dq.offerLast(j);
                
                // 4. 결과 기록
                if (j >= h - 1) {
                    winMin[j - h + 1][i] = rowMin[dq.peekFirst()][i];
                }                
            }
        }
        
        return getMaxPoint(winMin);
    }
    
    int[] getMaxPoint(int[][] map) {
        int x = 0;
        int y = 0;
        int max = 0;
        
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > max) {
                    x = i;
                    y = j;
                    max = map[i][j];
                }
            }
        }
        
        return new int[]{x, y};
    }
    
    void print(int[][] map) {
        for (int[] arr : map) {
            for (int k : arr) {
                if (k == INF) {
                    System.out.print("INF ");
                } else {
                    System.out.printf("%d ", k);
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
