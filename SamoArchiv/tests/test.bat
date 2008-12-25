@echo off
java -jar "C:\eclipse\workspace\SamoArchiv\tests\SamoArchiv.jar" -z "C:\eclipse\workspace\SamoArchiv\tests\sandbox\SamoArchivPlny.jar" "C:\eclipse\workspace\SamoArchiv\tests\data"


java -jar "C:\eclipse\workspace\SamoArchiv\tests\sandbox\SamoArchivPlny.jar" -r "C:\eclipse\workspace\SamoArchiv\tests\sandbox\cilDir"
pause

rd "C:\eclipse\workspace\SamoArchiv\tests\sandbox" /S /Q

mkdir sandbox