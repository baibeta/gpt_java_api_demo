# gpt_java_api_demo

https://platform.openai.com/docs/guides/chat

change API key:

```diff
- String apiKey = "YOUR_API_KEY";
+ String apiKey = "sk-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
```

compile:

```shell
## windows:
> javac -cp .;jars/* ChatGPTClient.java

## linux:
> javac -cp .:jars/* ChatGPTClient.java
```

run:

```shell
> java -cp .;jars/* ChatGPTClient

1. Please input your question:
1+1等于几
1+1等于2。
2. Please input your question:
2+2呢
2+2等于4。
3. Please input your question:
最开始我问你的什么？
你问我："1+1等于几"。
Finish demo! 
```

