import sys

stopWords = {"xref", "path", "from", "newsgroups", "subject", "keywords",
"message", "date", "article", "expires", "follow-", "distribution", "organization",
"lines", "approved", "nntp", "supersedes", "references", "sender", "followup-", 
"x-", "reply", "article", "news-", "in-reply", "archive-", "last-","\n"}

def clean(input):
	for word in stopWords:
		if input.lower().startswith(word):
			return 0
	return 1

	
#Script
fileParh = sys.argv[1]
output = ""

with open(fileParh, "r") as f:
	for i, line in enumerate(f):
		if clean(line):
			output += line
			
with open(fileParh, "w") as f:
	f.write(output)
