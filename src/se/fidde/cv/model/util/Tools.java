package se.fidde.cv.model.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tools {

	public static int getRandomIntInRange(int min, int max) {
		Random rand = new Random();
		List<Integer> numbers = new ArrayList<>();

		for (int i = min; i <= max; i++)
			numbers.add(i);

		try {
			return numbers.get(rand.nextInt(numbers.size()));

		} catch (ArrayIndexOutOfBoundsException e) {
			return -1;
		}

	}
}
