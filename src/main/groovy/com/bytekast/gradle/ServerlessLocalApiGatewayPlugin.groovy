package com.bytekast.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import ratpack.gradle.RatpackGroovyPlugin

class ServerlessLocalApiGatewayPlugin implements Plugin<Project> {

  @Override
  void apply(Project project) {
    applyBuildScripts project
    applyRepositories project
    applyPlugins project
    applyDependencies project

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
    }
  }

  private static void applyPlugins(Project p) {
    p.plugins.apply 'groovy'
    p.plugins.apply RatpackGroovyPlugin.class
  }

  private static void applyDependencies(Project p) {
    p.dependencies {
      compileOnly 'io.ratpack:ratpack-gradle:1.5.4'
      compileOnly 'io.ratpack:ratpack-groovy:1.5.4'
      runtimeOnly 'org.yaml:snakeyaml:1.20'
    }
  }

  private static void createTasks(Project p) {
    p.tasks.create('setupRatpack') {
      doLast {
        def dir = File.newInstance("${p.projectDir}/src/ratpack")
        dir.mkdirs()
        def file = File.newInstance(dir, 'ratpack1.groovy')
        file.text = '//TODO'
      }
    }
  }

  private static void createTasksDependencies(Project p) {
    p.run.dependsOn p.setupRatpack
  }
}
