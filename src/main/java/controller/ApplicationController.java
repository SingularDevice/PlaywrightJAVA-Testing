package controller;

import com.microsoft.playwright.Playwright;
import core.AppConfig;
import core.Constants;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;
import java.util.logging.Level;

public class ApplicationController implements Runnable {
	private final Logger log = Logger.getLogger(ApplicationController.class.getName());

	private final AppConfig config;

	private final TimerController tc;
	private int iterations;

	private final AtomicBoolean running;

	public ApplicationController() {
		this.config = AppConfig.getInstance();
		this.tc = new TimerController(
				config.getIntegerProperty(Constants.MIN_DELAY_BETWEEN_ITERATIONS),
				config.getIntegerProperty(Constants.MAX_DELAY_BETWEEN_ITERATIONS));
		this.running = new AtomicBoolean(config.getBooleanProperty(Constants.DEFAULT_RUNNING_VALUE));
		this.iterations = 0;
	}

	@Override
	public void run() {
		log.log(Level.INFO, "Aplicación iniciada");

		Playwright playwright = Playwright.create();
		try {
			PlaywrightController pc = PlaywrightController.getInstance(playwright);
			pc.setMaxParalelPages(config.getIntegerProperty(Constants.MAX_PARALEL_PAGES));

			while ( this.getRunning() ) {
				log.log(Level.INFO, String.format("Iteración %d iniciada", iterations));

				// Todo iniciar gestión de navegador y pestañas
				// Cola de procesos a ejecutar
				// Ejecución de cada uno de los procesos en diferentes pestañas marcadas por un límite

				tc.sleepBetweenIterations();
				incIterations();
			}
		} catch ( Exception e ) {
			log.log(Level.SEVERE, "Excepción general no controlada", e);
		} finally {
			try {
				playwright.close();
			} catch (Exception e) {
				log.log(Level.SEVERE, "Excepción no controlada al cerrar Playwright", e);
			}
		}

		log.log(Level.INFO, "Aplicación finalizada");
	}

	private void incIterations() {
		this.iterations++;
	}

	// TODO: Implementar forma de parar
	public synchronized void setRunning(boolean running) {
		this.running.set(running);
	}

	private synchronized boolean getRunning() {
		return running.get();
	}
}
