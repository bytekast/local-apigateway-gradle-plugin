package com.bytekast.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.JavaExec

class ServerlessLocalApiGatewayPlugin implements Plugin<Project> {

  @Override
  void apply(Project project) {
    applyBuildScripts project
    applyRepositories project
    applyPlugins project
    applyDependencies project
    applyMiscellaneous project

    createTasks project
    createTasksDependencies project
  }

  private static void applyBuildScripts(Project p) {
    p.buildscript {
      //TODO
    }
  }

  private static void applyRepositories(Project p) {
    p.repositories {
      mavenCentral()
      maven {
        url "https://plugins.gradle.org/m2/"
      }
    }
  }

  private static void applyPlugins(Project p) {
    p.plugins.apply 'groovy'
    p.plugins.apply ratpack.gradle.RatpackGroovyPlugin.class
  }

  private static void applyDependencies(Project p) {
    p.dependencies {
      compile 'org.yaml:snakeyaml:1.20'
    }
  }

  private static void applyMiscellaneous(Project p) {

    if (p.hasProperty('shadowJar')) {
      p.shadowJar {
        dependencies {
          exclude(dependency('gradle.plugin.io.ratpack::'))
          exclude(dependency('io.ratpack::'))
          exclude(dependency('io.netty::'))
        }
      }
    }

    p.tasks.withType(JavaExec) {
      systemProperty "project.rootDir", "${p.rootDir.absolutePath}"
    }
  }

  private static void createTasks(Project p) {

    p.tasks.create('setupRatpack') {
      doLast {
        def dir = File.newInstance("${p.projectDir}/src/ratpack")
        dir.mkdirs()
        def file = File.newInstance(dir, 'ratpack.groovy')
        file.text = ServerlessLocalApiGatewayPlugin.class.getResourceAsStream('/ratpack.groovy').text
      }
    }
  }

  private static void createTasksDependencies(Project p) {
    p.run.dependsOn p.setupRatpack
  }
}
