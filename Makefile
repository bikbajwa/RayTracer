JCC = javac

JFLAGS = -g

JVM = java

Main = Main

default: HW5.class

HW5.class:
	$(JCC) $(JFLAGS) -classpath "./lib/*" ./src/*.java
	
clean: 
	$(RM) ./src/*.class
