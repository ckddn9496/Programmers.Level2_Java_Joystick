import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
//		String name = "JEROEN";
//		String name = "JAZ";
//		String name = "JAN";
//		String name = "BABBBAAAABBA"; // len : 12
//		String name = "AAABAAA"; // return 4
//		String name = "AZAAAZ"; // return 5
//		String name = "ABAAABBBBBBB"; // 
		String name = "ABABAAAAAAABA"; // 10

		System.out.println(new Solution().solution(name));
	}
}

class Solution {
	static int MAX = 50;
	public int solution(String name) {
		int answer = 0;
		int len = name.length();
		char[] cArr = new char[len];
		Arrays.fill(cArr, 'A');
		String raw = new String(cArr);

		// 문자당 조이스틱의 위아래 조작 횟수를 담은 배열 comp 계산
		int[] comp = new int[len];
		for (int i = 0; i < len; i++) {
			int diff = name.charAt(i) - raw.charAt(i);
			comp[i] = diff < 26 - diff ? diff : 26 - diff;
		}
//		System.out.println("comp: " + Arrays.toString(comp));
		answer += Arrays.stream(comp).sum();
		// index마다 움직여야 하는 위 아래 값들을 모두 더해준다.
				
		int[] arr = new int[len * 2 - 1];
		for (int i = 0; i < len-1; i++) {
			arr[i] = comp[i+1];
		}
		for (int i = 0; i < len; i++) {
			arr[len + i - 1] = comp[i];
		}
//		System.out.println("arr: " + Arrays.toString(arr));

		int startIdx = len-1;
		arr[startIdx] = 0; // 첫 번째 위치에서 조이스틱은 좌우 이동없이 원하는 문자를 맞추고 시작
		int moveCount = 0;
//		System.out.println("arr: " + Arrays.toString(arr));

		
		while(!finished(arr, len)) {
			// left
			int leftJump=0, rightJump=0;
			leftJump = findLeftJmp(startIdx, arr);
			rightJump = findRightJmp(startIdx, arr);
//			System.out.println("left jump : " + leftJump);
//			System.out.println("right jump : " + rightJump);
			if (leftJump < rightJump) {
//				System.out.println("left!");
				for (int i = 1; i <= leftJump; i++) {
					arr[startIdx-i] = 0;
				}
				moveCount += leftJump;
				startIdx -= leftJump;
			} else {
//				System.out.println("right!");
				for (int i = 1; i <= rightJump; i++) {
					arr[startIdx+i] = 0;
				}
				moveCount += rightJump;
				startIdx += rightJump;
			}
//			System.out.println(Arrays.toString(arr));
		}
		if (moveCount > len-1)
			return answer+len-1;
		else
			return answer+moveCount;
	}
	
	int findLeftJmp(int startIdx, int[] arr) {
		int leftJump;
		int leftIdx = startIdx-1;
		if (leftIdx < 0) leftJump = MAX;
		else
			while (arr[leftIdx] == 0) {
			leftIdx--;
			if (leftIdx < 0)
				break;
		}
		// 0이 아닐때
		if (leftIdx < 0) leftJump = MAX;
		else {
			while (arr[leftIdx] != 0) {
				leftIdx--;
				if (leftIdx < 0)
					break;
			}
			leftJump = startIdx - leftIdx - 1;
		}
		return leftJump;
	}

	int findRightJmp(int startIdx, int[] arr) {
		int rightJump;
		int rightIdx = startIdx+1;
		if (rightIdx > arr.length-1) rightJump = MAX;
		else
			while (arr[rightIdx] == 0) {
			rightIdx++;
			if (rightIdx > arr.length-1)
				break;
		}
		// 범위 이상
		if (rightIdx > arr.length-1) rightJump = MAX;
		else {
			while (arr[rightIdx] != 0) {
				rightIdx++;
				if (rightIdx > arr.length-1)
					break;
			}
			rightJump = rightIdx - startIdx - 1;
		}
		return rightJump;
	}

	
	boolean finished(int[] arr, int len) { // arr에서 len개 만큼 이어진 0이 있다면 생성 완료
		int count = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == 0) {
				count++;
				if (count == len)
					return true;
			} else {
				count = 0;
			}
		}
		return false;
	}
}