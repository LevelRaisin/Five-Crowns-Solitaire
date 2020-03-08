package gameComponents;

import java.util.ArrayList;
import java.util.Arrays;

public class ComboChecker {
	private ComboChecker() {
		
	}
	
	public static boolean checkCombo (ArrayList<Card> c, int joker) {
		if (c.size() < 3) {
			return false;
		}
		ArrayList<Integer> vals = new ArrayList<Integer>();
		boolean sameNum = true;
		int numJokers = 0;
		int suit = -1;
		for (Card cd : c) {
			int temp = cd.getValue();
			if (temp == joker || temp == 15) {
				numJokers++;
				continue;
			}
			suit = cd.getSuitNum();
			vals.add(new Integer(temp));
			if (temp != vals.get(0).intValue()) {
				sameNum = false;
			}
		}
		if (sameNum) {
			return true;
		}
		
		
		boolean sameSuit = true;
		for (Card cd : c) {
			int temp = cd.getSuitNum();
			if (temp != suit && cd.getValue() != joker && temp != 10) {
				sameSuit = false;
				break;
			}
		}
		if (!sameSuit) {
			return false;
		}
		
		Integer[] temp =  new Integer[vals.size()];
		for (int i = 0; i<temp.length; i++) {
			temp[i] = vals.get(i);
		}
		Arrays.sort(temp);
		for (int i = 0; i<temp.length-1; i++) {
			int[] compare = {temp[i].intValue(), temp[i+1].intValue()};
			int dif = compare[1]-compare[0];
			if (dif!=0 && dif-1<=numJokers) {
				numJokers -= dif-1;
			} else {
				return false;
			}
		}
		return true;
	}
}
