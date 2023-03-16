import requests
import os

def google_search(query, api_key, cx):
    url = "https://www.googleapis.com/customsearch/v1"
    params = {
        "key": api_key,
        "cx": cx,
        "q": query
    }
    response = requests.get(url, params=params)
    return response.json()


api_key = os.sys.argv[1]
cx = os.sys.argv[2]
query = os.sys.argv[3]
outID = os.sys.argv[4] if len(os.sys.argv) > 4 else "google_output_";

results = google_search(query, api_key, cx)

links: list = []

for idx, item in enumerate(results['items'], start=1):
    if idx > 3:
        break
    print(item['link'] + ",")

# with open(f"Google_{outID}.txt", "w") as file:
    # file.write("\n".join(links))
# crawl = ",".join(links)
# print(crawl)

os.sys.exit(0)
