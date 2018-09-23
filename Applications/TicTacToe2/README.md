How to Compile and Run the game on Linux

mkdir build
mkdir class
find src/tictactoe2/ -name *.java > manifest.txt
javac -d classes/ @manifest.txt
cd classes/
jar cvfe TicTacToe2.jar tictactoe2/main/Tictactoe2 .
mv TicTacToe2.jar ../build
cd ..
cp gameSettings.xml build
cd build
java -jar TicTacToe2.jar


	
