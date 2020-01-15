package com.newbie.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class HelloPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        // Add the 'greeting' extension object
        def extension = project.extensions.create('greeting', GreetingExtension)

        // Add a task that uses configuration from the extension object
        def task = project.tasks.create('测试插件')
        task.group = 'toolkit'
        task.doLast {
            println "hello: + ${extension.name} , ${extension.message}"
            println project.greeting
        }
    }
}

