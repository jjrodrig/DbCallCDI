DB Call CDI Extension (LGPL)
=============

A CDI Extension to call stored procedures or database functions declaratively using hibernate.

Feel free to use this library as you wish, make sure to quote the LGPL in all used sources.

```java
  @StoredProcedure("CALL echoProc(?,?)")
	@StoredProcedureResult({ @FieldResult(name = "value", position = 2) })
	@DatabaseCall
	public ProcedureResult<String> callEchoAsProcedure(@StoredProcedureParameter(1) String name) throws Exception;
```
