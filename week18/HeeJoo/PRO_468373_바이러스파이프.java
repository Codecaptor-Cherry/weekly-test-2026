/*
 * 1 ~ n번 배양체 n개
 * n-1개의 파이프로 이어진 하나의 트리
 * 파이프 종류는 A, B, C 중 하나
 * 초기에는 모든 파이프 닫혀있음
 * 배양체 중 하나가 바이러스 감염 상태
 * 열린 파이프를 연결된 다른 인접한 배양체 감염
 * 종류가 같은 파이프를 한꺼번에 모두 열닫 가능
 * 한 종류의 파이프를 연 후 다시 닫기 전에 다른 종류의 파이프를 열 수 없음
 * 최대 k번 파이프 열닫을 통해 최대한 많은 배양체 감염 시키기
 
 * 1. 파이프 열닫 순서 정하기 - 순열
 * 2. union-find로 이어진 파이프 탐색
 */

import java.util.*;

class Solution {
    static int[][] parent;
    static int N, ans;
    public int solution(int n, int infection, int[][] edges, int k) {
        N = n;
        ans = 0 ;
        
        parent = new int[n + 1][3 + 1];
        for (int i = 1; i <= n ; i++) {
            Arrays.fill(parent[i], i);
        }
        
        for (int[] edge : edges) {
            int x = edge[0];
            int y = edge[1];
            int type = edge[2];

            union(x, y, type);
        }

        Set<Integer> infected = new HashSet<>();
        infected.add(infection);
        perm(0, k, infected);
        
        return ans;
    }
    
    Set<Integer> spread(int type, Set<Integer> infected) {        
        Set<Integer> set = new HashSet<>(infected);
        
        for (int infect : infected) {
            int pi = find(infect, type);

            for (int x = 1; x <= N; x++) {
                int px = find(x, type);
                
                if (pi == px) set.add(x);
            }
        }
        
        return set;
    }
    
    void perm(int idx, int k, Set<Integer> infected) {
        ans = Math.max(ans, infected.size());

        if (idx == k) {
            return;
        }
        
        for (int i = 1; i <= 3; i++) {
            Set<Integer> next = spread(i, infected);
            
            if (next.equals(infected)) continue;
            
            perm(idx + 1, k, next);
        }
        
    }
    
    int find(int x, int type) {
        if (x == parent[x][type]) return parent[x][type];
        
        return parent[x][type] = find(parent[x][type], type);
    }
    
    void union(int x, int y, int type) {
        x = find(x, type);
        y = find(y, type);
        
        if (x <= y) {
            parent[y][type] = x;
        } else {
            parent[x][type] = y;
        }
    }
}
