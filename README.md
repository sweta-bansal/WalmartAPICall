# WalmartAPICall
Java program to rank-order Walmart product recommendations based upon customer review.

Assumption:

1. Only one product search string is passed by the user as argument in the command line prompt.
2. If any product doesnt has any review then its review score will be 0.

Instructions and Overview:

1. There are two files attached to execute the solution namely product.java(contains all the 

business logic) and json-simple.jar(external jar file).

2. Instruction for executing the solution via command line:-

	javac -cp json-simple.jar Product.java

	java -cp json-simple.jar; Product iPod

3. I successfully implemented the requirements and got the below output which displays the first 

10 recommended products in sorted order of review sentiments:-

C:\Users\Sweta\Desktop>javac -cp json-simple.jar Product.java

C:\Users\Sweta\Desktop>java -cp json-simple.jar; Product iPod
Rank:1 OtterBox Defender Series Case for Apple iPod touch 5th Generation
Rank:2 LeapFrog Explorer Learning Game: Adventure Sketchers! Draw, Play, Create
Rank:3 Apple iPad mini 2 16GB WiFi
Rank:4 Beats by Dr. Dre  Drenched Solo On-Ear Headphones, Assorted Colors
Rank:5 Apple iPod touch 32GB, Assorted Colors
Rank:6 Apple iPod nano 16GB, Assorted Colors
Rank:7 Griffin Survivor Extreme-Duty Case for Apple iPod touch 5G, Blue
Rank:8 RCA 7" Tablet 16GB Quad Core
Rank:9 LifeProof fre Case for Apple iPod touch 5G, Black/Clear
Rank:10 OtterBox Vibrant Screen Protector for Apple iPod Touch 5G
