CC=g++
CFLAGS=-c -std=c++11 -Wall -fPIC -I "${JAVA_HOME}/include" -I "${JAVA_HOME}/include/
CHOMEFLAGS=
LDFLAGS=-fPIC -shared
EXTFLAGS=
ifeq ($(OS), Windows_NT)
	SOURCES_DIR=src/main/cpp/Windows
	OBJECTS_DIR=target/cpp
	EXECUTABLE=target/classes/JudgerCore.dll
	EXTFLAGS=-luserenv -lpsapi
	CHOMEFLAGS=linux"
else
    UNAME_S := $(shell uname -s)
    ifeq ($(UNAME_S), Darwin)
        CC = g++-7
        SOURCES_DIR=src/main/cpp/MacOS
        OBJECTS_DIR=target/cpp
        EXECUTABLE=target/classes/libJudgerCore.dylib
        EXTFLAGS=-lpthread
        CHOMEFLAGS=darwin"
    endif
	ifeq ($(UNAME_S), Linux)
		SOURCES_DIR=src/main/cpp/Linux
		OBJECTS_DIR=target/cpp
		EXECUTABLE=target/classes/libJudgerCore.so
		EXTFLAGS=-lpthread -lrt
		CHOMEFLAGS=linux"
	endif
endif

SOURCES=$(wildcard $(SOURCES_DIR)/*.cpp)
OBJECTS=$(SOURCES:$(SOURCES_DIR)/%.cpp=$(OBJECTS_DIR)/%.o)

all: $(EXECUTABLE)

$(EXECUTABLE): $(OBJECTS)
	$(CC) $(LDFLAGS) $(OBJECTS) $(EXTFLAGS) -o $@

$(OBJECTS): $(SOURCES)
	mkdir -p $(OBJECTS_DIR)
	$(CC) $(CFLAGS)$(CHOMEFLAGS) $< -o $@

clean:
	rm -rf $(OBJECTS_DIR) $(EXECUTABLE)
