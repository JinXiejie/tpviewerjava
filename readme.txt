1)打包
D:\develop\eclipse-jee-ganymede-SR2-win32\workspace_xdt\tpviewerjava\bin>jar -cvf tpviewerjava.jar *.*
2)maven入库
mvn install:install-file -DgroupId=com.bea.xml -DartifactId=jsr173-ri -Dversion=1.0 -Dpackaging=jar -Dfile=[path to file]
mvn install:install-file -DgroupId=com.mip -DartifactId=tpviewerjava -Dversion=1.0 -Dpackaging=jar -Dfile=D:\develop\eclipse-jee-ganymede-SR2-win32\workspace_xdt\tpviewerjava\输出jar\tpviewerjava.jar