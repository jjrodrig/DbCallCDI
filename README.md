DB Call CDI Extension (LGPL)
=============

A CDI Extension to call stored procedures or database functions declaratively using hibernate.

Feel free to use this library as you wish, make sure to quote the LGPL in all used sources.

## Using this project

Define your interface:

```java
@ApplicationScoped
public interface DBTester {

  @StoredProcedure("CALL echoProc(?,?)")
  @StoredProcedureResult({ @FieldResult(name = "value", position = 2) })
  @DatabaseCall
  public ProcedureResult<String> callEchoAsProcedure(@StoredProcedureParameter(1) String name) throws Exception;

}
```

Executing db call:

```java
@Inject
private DBTester dbTester;

...

ProcedureResult<String> result = dbTester.callEchoAsProcedure(testVal);
```

Using EntityManager Qualifier

```java
@ApplicationScoped
public interface DBTester {

  @StoredProcedure("CALL echoProc(?,?)")
  @StoredProcedureResult({ @FieldResult(name = "value", position = 2) })
  @DatabaseCall(qualifier=MyDatabase.class)
  public ProcedureResult<String> callEchoAsProcedure(@StoredProcedureParameter(1) String name) throws Exception;

}
```
Check tests for detailed use.
