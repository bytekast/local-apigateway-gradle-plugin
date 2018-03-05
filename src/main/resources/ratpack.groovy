import org.yaml.snakeyaml.Yaml
import ratpack.handling.Context

import static ratpack.groovy.Groovy.ratpack

final serverlessYml = new File("${System.getProperty('project.rootDir')}/serverless.yml").text
final yaml = new Yaml().load(serverlessYml)
final functions = yaml.functions?.keySet()?.collect { name -> yaml.functions.get(name) }

def functionHandlers = []
functions?.each { f ->
  def clazz = f.handler?.tokenize('::')?.first()
  def instance = Class.forName(clazz).newInstance()

  def method = f.handler?.tokenize('::')?.last()
  def handler = instance.&"${method}"

  def httpEvents = f?.events?.http

  httpEvents?.each {
    def path = it.path?.toString()?.replaceAll('\\{', ":").replaceAll("}", "")
    functionHandlers << [
        method : it.method,
        path   : path,
        handler: { Context context ->
          def input = context.request.path
          println context.pathTokens instanceof Map
          println context.request.queryParams
          context.render handler(input, null)
        }
    ]
  }
}

ratpack {
  handlers {
    functionHandlers.each {
      "${it.method}"("${it.path}".toString(), it.handler)
    }
  }
}





