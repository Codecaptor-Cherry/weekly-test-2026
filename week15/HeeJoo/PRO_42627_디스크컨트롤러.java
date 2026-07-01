/*
 * 대기 큐 : 작업의 번호, 요청 시각, 소요 시간 저장
 * 하드디스크가 쉬고 있을 때, 대기 큐가 비어있지 않다면 가장 우선순위가 높은 작업을 꺼내서 수행
 * 우선순위 : 짧은 소요 시간 > 빠른 요청 시각 > 작은 작업 번호
 * 작업을 한 번 시작하면 해당 작업을 마칠 때까지 다른 작업 불가
 * 하드디스크가 작업을 마치는 시점과 다른 요청이 들어오는 시점이 겹치는 경우, 요청을 대기 큐에 저장한 후 우선순위에 따라 작업 수행
 * 반환 시간 : 작업 요청부터 종료까지 걸린 시간 ~ 작업 시작 시간 - 요청 시간 + 소요 시간
 * 모든 요청 작업의 평균 반환 시간 정수 부분 구하기
 */

import java.util.*;

class Solution {
    public int solution(int[][] jobs) {
        int len = jobs.length;
        
        Arrays.sort(jobs, (o1, o2) -> o1[0] - o2[0]); // 빠른 요청 시각 순으로 정렬
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> {            
            return o1[1] - o2[1]; // 짧은 소요 시간
        });
        
        int idx = 0;
        int time = 0;
        int sum = 0;
        
        while (idx < len) {
            while (idx < len && jobs[idx][0] <= time) { // 현재 시간까지 입력된 요청을 대기 큐에 저장
                pq.offer(jobs[idx]);
                idx++;
            }
            
            if (!pq.isEmpty()) { // 대기 큐에 작업이 있는 경우 수행
                int[] job = pq.poll();
                sum += time - job[0] + job[1];
                time += job[1];
            } else { // 작업이 없는 경우 시간만 경과
                time++;
            }
        }
        
        while (!pq.isEmpty()) { // 이후 남은 대기 큐 작업 수행
            int[] job = pq.poll();
            sum += time - job[0] + job[1];
            time += job[1];
        }
        
        return sum / len;
    }
}
