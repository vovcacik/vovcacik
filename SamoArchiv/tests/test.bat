@echo off
java -jar "D:\eclipse\workspace\SamoArchiv\tests\SamoArchiv.jar" -z "D:\eclipse\workspace\SamoArchiv\tests\sandbox\SamoArchivPlny.jar" "D:\eclipse\workspace\SamoArchiv\tests\data"


java -jar "D:\eclipse\workspace\SamoArchiv\tests\sandbox\SamoArchivPlny.jar" -r "D:\eclipse\workspace\SamoArchiv\tests\sandbox\cilDir"
pause

rd "D:\eclipse\workspace\SamoArchiv\tests\sandbox" /S /Q

mkdir sandbox