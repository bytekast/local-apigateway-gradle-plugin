# local-apigateway-gradle-plugin
Gradle Plugin to Expose API Gateway Endpoints in [Serverless Framework](https://serverless.com/) Locally

To use the plugin, simply add the following to the `plugins` section of `build.gradle`

```groovy
plugins {
  id "com.bytekast.serverless-local-apigateway" version "0.5"
}
```

**WARNING!!!** This project is in its very early stages and comes with many limitations, including:

- The handler function must be defined in the following format: `{fullyQualifiedClass}::{methodName}`
- The `http` events `path` and `method` must be explicitly defined

Example:
```yaml
functions:
  echo:
    handler: com.bytekast.Functions::echo
    events:
      - http:
          path: echo/{message}
          method: get
```

The function's method signature must include the following typed parameters:
- `java.util.Map`
- `com.amazonaws.services.lambda.runtime.Context`

Example:
```groovy
Object echo(Map input, Context context) {
  // Add function logic here
}
``` 

**WORK IN PROGRESS!!!**
 