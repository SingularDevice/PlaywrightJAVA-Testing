import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dump {

	public static void main(String[] args) throws Exception {
		Playwright playwright = Playwright.create();

		List<BrowserType> browserTypes = Arrays.asList(
				playwright.chromium()/*,
				playwright.webkit(),
				playwright.firefox()*/
		);

		Path chromePath = Paths.get("/opt/google/chrome/google-chrome");

		BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
		launchOptions.executablePath = chromePath;
		launchOptions.headless = false;

		Browser.NewContextOptions options = new Browser.NewContextOptions();
		options.withViewport(800, 600);
		options.javaScriptEnabled = true;

		for (BrowserType browserType : browserTypes) {
			Browser browser = browserType.launch(launchOptions);
			BrowserContext context = browser.newContext(options);

			Page page = context.newPage();
			//page.navigate("https://www.pccomponentes.com/lista-de-deseos/carrito/1y0RVs0xLe0wlZ");
			page.navigate("https://www.pccomponentes.com/lista-de-deseos/carrito/dcVXeUtBHBqrnY");
			page.waitForLoadState();

			ElementHandle a = page.waitForSelector("//*[contains(@class, 'white-card-movil')]/h1/strong[1]");
			String text = a.textContent();
			System.out.println(text);

			page.waitForTimeout(10000);
			browser.close();
		}

		playwright.close();
	}

}
