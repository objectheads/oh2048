# oh2048
Java library for creating 2048 game with sample UI (JavaFX).

Run application:
```
mvn clean install exec:java -pl oh2048-ui -Dexec.mainClass="com.objectheads.oh2048.Main"
```

Sample usage:
```java
GridBuilder gb = GridBuilder.create().disableUndo().setTargetScore(8);
Grid grid = gb.build();
System.out.println(grid);

grid.add(0, 0, 8);
System.out.println(grid);

grid.getGridMovement().moveRight();
System.out.println(grid);

grid.add(0, 2,8);
System.out.println(grid);

grid.getGridMovement().moveRight();
System.out.println(grid);
```

Result:
```
 - - - -
 - - - -
 - - - -
 - - - -

 8 - - -
 - - - -
 - - - -
 - - - -

 4 - - 8
 - - - -
 - - - -
 - - - -

 4 - 8 8
 - - - -
 - - - -
 - - - -

  -  -  4 16
  -  -  -  -
  -  -  -  -
  -  -  -  2
```
