/*
 * 여러 bucket 중 하나만 독
 * 돼지한테 먹여서 독 양동이 확인
 * 제한시간 minutesToTest
 * 1. 먹이를 줄 돼지들 고르기
 * 2. 각각의 돼지들에게 먹일 bucket 고르기 ~ 급여 (bucket mix 가능)
 * 3. minutesToDie 대기. 이 때 다른 돼지에게 먹이를 줄 수 없음
 * 위 과정을 반복하며, 독이 든 bucket을 찾을 때까지 필요한 최소 돼지 수 구하기

 * turn = minutesToTest / minutesToDie
 * 한 마리의 돼지는 turn + 1가지 케이스 해결 (총 턴 수 + 생존하는 경우)
 */

class Solution {
    public int poorPigs(int buckets, int minutesToDie, int minutesToTest) {
        int pig = 0;
        int maxBuckets = 1;
        int states = minutesToTest / minutesToDie + 1;
        while (maxBuckets < buckets) {
            maxBuckets *= states;
            pig++;        
        }

        return pig;
    }

}
