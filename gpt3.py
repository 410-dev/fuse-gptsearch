import openai
import os
import json
import time

# Get first parameter from the command line
token: str = os.sys.argv[1]

# Get the prompt from the command line
message: str = os.sys.argv[2]

# Set model
modelName: str = os.sys.argv[3] # "gpt-3.5-turbo"

# Set randomID
randomID: str = os.sys.argv[4] if len(os.sys.argv) > 4 else "gpt3_output_";

# Set API key
openai.api_key = token

def request(message: str) -> dict:
	completion = openai.ChatCompletion.create(
		model=modelName, 
		messages=[{"role": "user", "content": message}]
	)
 
	finishReason: str = completion.choices[0].finish_reason
	message: str = completion.choices[0].message.content
 
	return {"finishReason": finishReason, "message": message}

# Get current time in milliseconds
msec: int = int(round(time.time() * 1000))

# Write file to the current directory
# with open(f"GPT3_{msec}.txt", "w") as file:
    # d: dict = request(message)
    # file.write(message)

d: dict = request(message)
print(json.dumps(d))

# Exit the program
os.sys.exit(0)
