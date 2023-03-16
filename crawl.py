from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
from bs4 import BeautifulSoup
import requests
import os
import time


chrome_driver_path = os.sys.argv[1]  # Get the path to the Chrome driver from the command line

# Set up the Chrome driver
chrome_options = Options()
# chrome_options.add_argument('--headless')  # Run Chrome in headless mode to avoid opening a new window
service = Service(chrome_driver_path)  # Replace with the path to your Chromedriver executable
driver = webdriver.Chrome(service=service, options=chrome_options)

# Load the webpage in Chrome
urls = []
for i in range(2, len(os.sys.argv)):
    urls.append(os.sys.argv[i])
    
for url in urls:
    driver.execute_script(f"window.open('{url}', '_blank');")
    time.sleep(1)

# Retrieve the HTML content of the webpage after it has finished loading
for i in range(len(urls)):
    driver.switch_to.window(driver.window_handles[i])
    html = driver.page_source
    soup = BeautifulSoup(html, 'html.parser')
    text = soup.get_text().replace("\n", " ")
    while text.find("  ") != -1:
        text = text.replace("  ", " ")
    print(text)
    print("<<<<END_OF_DATA>>>>")

# Clean up and quit the driver
driver.quit()
os.sys.exit(0)
