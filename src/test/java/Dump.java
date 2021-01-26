import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

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

		BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
		launchOptions.headless = false;

		Browser.NewContextOptions options = new Browser.NewContextOptions();
		options.withViewport(800, 600);
		options.javaScriptEnabled = true;

		for (BrowserType browserType : browserTypes) {
			Browser browser = browserType.launch(launchOptions);
			BrowserContext context = browser.newContext(options);

			Page page = context.newPage();
			page.waitForLoadState();

			ElementHandle a = page.waitForSelector("");
			String text = a.textContent();
			System.out.println(text);

			page.waitForTimeout(10000);
			browser.close();
		}

		playwright.close();
	}

}
