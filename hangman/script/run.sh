
set -e
cd "$(dirname $0)/../hangman-server"

mvn compile exec:java -Dexec.mainClass="it.xpug.hangman.main.Main"
