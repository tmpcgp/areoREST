@echo off
mvn clean compile && mvn exec:java -Dexec.mainClass="elixir.restful.App"
