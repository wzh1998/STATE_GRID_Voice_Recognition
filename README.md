# STATE GRID Voice Control System

**SGVR is a Voice Recognition System Designed for State Grid Corporation of China, the program can execute correspond RPA (Robotic Process Automation) by users' voice commands.**


## RPA
Robotic process automation is an emerging form of business process automation technology based on the notion of metaphorical software robots or artificial intelligence (AI) workers. RPA tools differ from such systems including features that allow data to be handled in and between multiple applications, for instance, receiving email containing an invoice, extracting the data, and then typing that into a bookkeeping system.

## Why Offline System is Necessary?
Companies usually choose to publish RPA to orchestrator, orchestrator can control the computer through key with Internet access. However, State Grid Corporation of China is a secrecy department, so an offline voice control system is necessary. 


## Requirements

 - The developing and executing environments can be either Windows 7+ or Linux with Java JDK higher than JDK 8. 

 - UIPath Studio, UIRobot are required for your system, Orchestrator is optional.

## Update History

 - **2019.08.06:** 
 	- Implemented offline asr system using SDK from Iflytek CO.,LTD., held a meeting with subsidiary corporation of Iflytek this week, considered transform asr into an offline system.

 - **2019.08.12:** 
 	- Implemented turning result parsing system, including a JSON message "piecing together" method in Java and a Json parse method to deframe the result. 
 	- Combined Turning, ASR, TTS modules. Read the audio voice from microphone, translate it into text by ASR, analysis the meaning by turning module, finally displayed and played the text and audio result from turning API.

