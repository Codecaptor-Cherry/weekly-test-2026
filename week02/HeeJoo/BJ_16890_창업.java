package saturday.year26.sat260110;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
 * N개의 글자로 이루어진 회사 이름
 * 두 사람은 각자 사용할 N개의 문자를 정함(중복 문자 가능)
 * 초기 회사 이름 : N개의 물음표(?)
 * 각 턴이 되었을 때, 각 사람은 미리 정해놓은 문자 중 하나를 고르고, 물음표 하나를 고른 문자로 변경
 * 고른 물음표는 더 이상 사용할 수 없고, 모든 물음표가 문자로 바뀌면 끝
 * A는 회사 이름을 사전 순으로 가장 앞서게, B는 사전 순으로 가장 뒤에 오게 만들고 싶어할 때, 최종 회사 이름은?
 * 게임은 A부터 시작
 *
 * 가장 앞서게 : 가장 빠른 알파벳을 앞쪽에
 * 가장 뒤에 : 가장 늦은 알파벳을 앞쪽에
 * 예외 : A(bb), B(aa) -> 정답 ab
 * 포인트 1. 사용할 글자를 미리 계산
 * 포인트 2. 상대방과 비교해서 앞 or 뒤 결정
 */
public class BJ_16890_창업 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        char[] a = br.readLine().toCharArray();
        char[] b = br.readLine().toCharArray();
        int N = a.length;

        Arrays.sort(a);
        Arrays.sort(b);

        // 각자 사용할 문자의 개수
        int appleLen = (N + 1) / 2;
        int cubeLen = N / 2;

        Deque<Character> apple = new ArrayDeque<>();
        Deque<Character> cube = new ArrayDeque<>();
        for (int i = 0; i < appleLen; i++) { // 오름차순
            apple.addLast(a[i]);
        }
        for (int i = N - 1; i >= N - cubeLen; i--) { // 내림차순
            cube.addLast(b[i]);
        }

        int start = 0;
        int end = N - 1;
        char[] ans = new char[N];

        for (int i = 0; i < N; i++) {
            if (i % 2 == 0) {
                // 1. A : 가장 앞서게
                // A의 최소 글자가 B의 최대 글자보다 작을 때 앞부분 차지
                // B의 글자 수가 부족할 경우, 남은 A의 글자를 순서대로 나열(오름차순 정렬 상태)
                if (cube.isEmpty() || apple.peekFirst() < cube.peekFirst()) {
                    ans[start++] = apple.pollFirst();
                } else {
                    // A의 최소 글자가 B의 최대 글자 이상이면 다음에 사용할 수 있도록 A의 최대 글자(최악의 경우)를 뒤로 보내서 트롤링 방지
                    // 어차피 현재 계산한 appleLen개의 글자를 모두 사용해야 함
                    ans[end--] = apple.pollLast();
                }
            } else {
                // 2. B : 가장 늦게
                // B의 최대 글자가 A의 최소 글자보다 클 때 앞부분 차지
                // A의 글자 수가 부족할 경우, 남은 B의 글자를 순서대로 나열(내림차순 정렬 상태)
                if (apple.isEmpty()|| cube.peekFirst() > apple.peekFirst()) {
                    ans[start++] = cube.pollFirst();
                } else {
                    // B의 최대 글자가 A의 최소 글자 이하이면 다음에 사용할 수 있도록 B의 최소 글자(최악의 경우)를 뒤로 보내서 트롤링 방지
                    // 어차피 현재 계산한 cubeLen개의 글자를 모두 사용해야 함
                    ans[end--] = cube.pollLast();
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (char ch : ans) sb.append(ch);

        System.out.println(sb);

    }
}
