/*
 * 0과 1로 이루어진 2^n x 2^n 배열
 * 1. S : 압축하고자 하는 특정 영역
 * 2. S 내부에 있는 모든 수가 같은 값이라면, S를 해당 수 하나로 압축
 * 3. 그렇지 않다면, S를 정확히 4개의 균일한 정사각형 영역으로 쪼갠 후 압축 과정 반복
 * 최종적으로 남는 0의 개수가 1의 개수 구하기
 
 * 1. 정사각형 4등분
 * 2. 각 정사각형마다 압축 여부 확인 -> 영역의 합 / 넓이 = 1
 * 3. 더 이상 쪼갤 수 없는 경우 결과 카운팅
 */

class Solution {
    static int zero, one;
    public int[] solution(int[][] arr) {
        int[] answer = new int[2];
        
        zero = 0;
        one = 0;
        
        int n = arr.length;
        func(0, 0, n, arr);
        
        answer[0] = zero;
        answer[1] = one;
        
        return answer;
    }
    
    void func(int x, int y, int n, int[][] arr) { // 시작점(x, y), 한 변의 길이 n
        if (n == 1) { // 더 이상 쪼갤 수 없음
            if (arr[x][y] == 0) zero++;
            else if (arr[x][y] == 1) one++;
            
            return;
        }
        
        int sum = getSum(x, y, n, arr);
        
        if (sum % (n * n) == 0) { // 압축 가능
            if (arr[x][y] == 0) zero++;
            else if (arr[x][y] == 1) one++;
            
            return;            
        }
        else { // 압축 불가능 ~ 4분할
            int nextLen = n / 2;
            func(x, y, nextLen, arr);
            func(x, y + nextLen, nextLen, arr);
            func(x + nextLen, y, nextLen, arr);
            func(x + nextLen, y + nextLen, nextLen, arr);
        }        
    }
    
    int getSum(int x, int y, int n, int[][] arr) {
        int sum = 0;
        
        for (int i = x; i < x + n; i++) {
            for (int j = y; j < y + n; j++) {
                sum += arr[i][j];
            }
        }
        
        return sum;
    }
}
