package saturday.year26.sat260404;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * 알파벳 소문자 여러 개와 별표(*) 하나로 이루어진 문자열
 * 별표를 알파벳 소문자로 이루어진 임의의 문자열로 변환해 파일 이름과 같게 만들 수 있는지 판단하기
 * ex) 패턴 : a*d
 * ex) 일치 : abcd, ad, anestonestod | 불일치 : bcd
 *
 * 길이가 0 ~ n인 문자열 가능
 */
public class BJ_9996_한국이그리울땐서버에접속하지 {
    static final String YES = "DA";
    static final String NO = "NE";
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        String pattern = br.readLine();
        int star = pattern.indexOf('*');

        StringBuilder sb = new StringBuilder();
        for (int t = 0; t < N; t++) {
            String input = br.readLine();
            sb.append(func(star, pattern, input)).append("\n");
        }

        System.out.println(sb);

    }

    public static String func(int star, String pattern, String input) {
        int left = 0;
        for (int i = 0; i < star; i++) {
            if (input.charAt(i) != pattern.charAt(i)) return NO;
            left = i;
        }
        for (int i = 0; i < pattern.length() - star - 1; i++) {
            if (input.length() - 1 - i == left) return NO;
            if (input.charAt(input.length() - 1 - i) != pattern.charAt(pattern.length() - 1 - i)) return NO;
        }

        return YES;
    }
}
