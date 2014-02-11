
set -e
cd "$(dirname $0)/.."

mvn compile exec:java -Dexec.mainClass="it.xpug.hangman.main.Main"
