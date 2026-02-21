package saturday.year26.sat260131;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
/*
 * 총 n개의 퀘스트를 진행해서 n개의 스톤을 받고, 각각의 스톤에 5억 이상의 경험치를 모아야 전직 가능
 * i번째 퀘스트를 진행하면 a_i의 경험치와 i번째 스톤이 주어짐
 * 스톤의 최대 경험치 제한을 없애 버리고, 최대 k개의 스톤을 동시에 활성화
 * 즉, 한 퀘스트의 보상 경험치가 여러 개의 스톤에 추가될 수 있음
 * ex) 1번, 3번 스톤이 활성화된 상태에서 2번째 퀘스트를 진행해 1,000의 경험치와 2번째 스톤을 획득하면
 * 1번, 3번 스톤에 각각 1,000의 경험치가 저장되고, 2번째 스톤은 경험치가 0인 상태로 받게 됨
 * 원하는 순서대로 퀘스트를 진행할 수 있지만, 같은 퀘스트를 두 번 이상 진행할 수 없음 ~ 한 번만 가능
 * 스톤을 적절히 활성화/비활성화 시켜서 스톤에 모인 경험치의 합을 최대화
 *
 * 누적합
 * 경험치가 작은 순서대로 k개의 퀘스트 수행
 * 활성화 전의 퀘스트 경험치는 포함이 되지 않도록 하기
 */

public class BJ_16112_5차전직 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = stoi(st.nextToken());
        int k = stoi(st.nextToken());

        int[] exps = Arrays.stream(br.readLine().split(" "))
               .mapToInt(Integer::parseInt)
               .toArray();

        Arrays.sort(exps);
        long[] sum = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            sum[i] = sum[i - 1] + exps[i - 1];
        }

        long ans = 0;
        for (int i = 0; i < k; i++) {
            ans += sum[n] - sum[i + 1];
        }

        System.out.println(ans);
    }

    public static int stoi(String str) {
        return Integer.parseInt(str);
    }
}
