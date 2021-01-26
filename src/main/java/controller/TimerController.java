package controller;

import java.util.Objects;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TimerController {
	private final Logger log = Logger.getLogger(TimerController.class.getName());

	private final int minDelayBetweenIterations;
	private final int maxDelayBetweenIterations;

	public TimerController(Integer minDelay, Integer maxDelay) {
		this.minDelayBetweenIterations = Objects.requireNonNull(minDelay, "Min delay is required");
		this.maxDelayBetweenIterations = Objects.requireNonNull(maxDelay, "Max delay is required");
	}

	private int getRandomNumber(int minNum, int maxNum) {
		return new Random().nextInt(maxNum-minNum)+minNum;
	}

	public void sleepBetweenIterations() {
		try {
			int sleepMilis = getRandomNumber(this.getMinDelayBetweenIterations(), this.getMaxDelayBetweenIterations());
			log.log(Level.INFO, String.format("Espera forzada %d ms", sleepMilis));
			Thread.sleep(sleepMilis);
		} catch (InterruptedException e) {
			log.log(Level.SEVERE, "Excepción en la espera forzada", e);
		}
	}

	private int getMinDelayBetweenIterations() {
		return minDelayBetweenIterations;
	}

	private int getMaxDelayBetweenIterations() {
		return maxDelayBetweenIterations;
	}
}
