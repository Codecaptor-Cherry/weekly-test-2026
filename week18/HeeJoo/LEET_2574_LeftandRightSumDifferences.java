/*
 * n 사이즈 배열
 * leftSum[i] : i번 왼쪽 원소의 합
 * rightSum[i] : i번 오른쪽 원소의 합
 * answer[i] = |leftSum[i] - rightSum[i]|인 배열 answer 구하기
 */

import java.util.*;

class Solution {
    public int[] leftRightDifference(int[] nums) {
        int n = nums.length;
        int[] leftSum = new int[n];
        int[] rightSum = new int[n];

        for(int i = 1; i < n; i++) {
            leftSum[i] = leftSum[i - 1] + nums[i - 1];
            rightSum[n - i - 1] = rightSum[n - i] + nums[n - i];
        }

        int[] answer = new int[n];
        for (int i = 0; i < n; i++) {
            answer[i] = Math.abs(leftSum[i] - rightSum[i]);
        }

        // System.out.println(Arrays.toString(leftSum));
        // System.out.println(Arrays.toString(rightSum));

        return  answer;
    }
}
