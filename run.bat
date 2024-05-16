@echo off
mvn clean compile exec:java -Dexec.mainClass="elixir.restful.App"
