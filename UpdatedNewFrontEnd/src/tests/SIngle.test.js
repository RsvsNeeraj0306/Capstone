import { Builder, By, until } from "selenium-webdriver";
import chrome from "selenium-webdriver/chrome";
import { afterAll, beforeAll, describe, expect, it } from "vitest";

const url = "http://localhost:3000";
 
describe("Selenium", () => {
  let driver;
 
  beforeAll(async () => {
    const chromeOptions = new chrome.Options();
    chromeOptions.addArguments(
      "--disable-gpu",
      "--no-sandbox",
      "--disable-dev-shm-usage"
    );
    driver = new Builder()
      .forBrowser("chrome")
      .setChromeOptions(chromeOptions)
      .build();
 
    await driver.manage().window().maximize();
    await driver.manage().setTimeouts({ implicit: 100000 });
    await driver.get(url);
  });
  
  it("should render the page", async () => {
    const currectUrl = await driver.getCurrentUrl();
    expect(currectUrl).toBe(url + "/");

  });

  

  afterAll(async () => {
    await driver.quit();
  });
});

