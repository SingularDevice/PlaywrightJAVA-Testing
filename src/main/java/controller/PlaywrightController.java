package controller;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

public class PlaywrightController {

	private static PlaywrightController _instance;
	private final Playwright playwright;

	private PlaywrightController(Playwright playwright) {
		this.playwright = Objects.requireNonNull(playwright, "Playwright object is required");
	}

	public static PlaywrightController getInstance(Playwright playwright) {
		if ( _instance == null ) {
			_instance = new PlaywrightController(playwright);
		}
		return _instance;
	}

	public static PlaywrightController getInstance() {
		return _instance;
	}

	public static void openBrowser() {
		openBrowser(Optional.empty());
	}

	public static void openBrowser(Optional<Path> chromePath) {
		BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
		launchOptions.headless = false;

		if ( Optional.empty().isPresent() ) {
			launchOptions.executablePath = chromePath.get();
		}

		Browser.NewContextOptions options = new Browser.NewContextOptions();
		options.withViewport(800, 600);
		options.javaScriptEnabled = true;
	}

	public static void closeBrowser() {

	}

	public void setMaxParalelPages(int maxParalelPages) {

	}
}
