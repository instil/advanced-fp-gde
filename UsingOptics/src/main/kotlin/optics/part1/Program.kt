package optics.part1

import optics.part1.dsl.*
import optics.part1.model.*
import java.net.URI

val dsl = spaceInstance("Kotlin Programming for MegaCorp") {
    profiles {
        profile {
            forename = "Jane"
            surname = "Jones"
            email = "Jane.Jones@megacorp.com"
        }
        profile {
            forename = "Pete"
            surname = "Smith"
            email = "Pete.Smith@megacorp.com"
        }
    }
    projects {
        project("Course Examples", "PROJ101") {
            repo(URI("http://somewhere.com")) {}
        }
        project("Course Exercises", "PROJ202") {
            repo(URI("http://elsewhere.com")) {}
        }
    }
    blogs {
        blog("Welcome to the Course", "welcome.md") {
            +"Some additional client-specific content"
        }
        blog("Setting Up", "setup.md") {
            +"More client-specific content"
        }
    }
}

fun main() {
    val instance = createInstance()
    println(instance)
}

fun DslRepo.toRepo() = Repo(location)
fun DslBlog.toBlog() = Blog(title, location)
fun DslInstance.toInstance() = Instance(title)
fun DslProfile.toProfile() = Profile(forename, surname, email)
fun DslProject.toProject() = Project(name, key)

fun createInstance(): Instance {
    var instance = dsl.toInstance()

    with(dsl.projects) {
        content.forEach { dslProject ->
            var project = dslProject.toProject()

            dslProject.content.forEach { dslRepo ->
                val repo = dslRepo.toRepo()
                project = project.copy(repos = project.repos.plus(repo))
            }

            instance = instance.copy(projects = instance.projects.plus(project))
        }
    }

    with(dsl.profiles) {
        content.forEach { dslProfile ->
            val profile = dslProfile.toProfile()

            instance = instance.copy(profiles = instance.profiles.plus(profile))
        }
    }

    with(dsl.blogs) {
        content.forEach { dslBlog ->
            var blog = dslBlog.toBlog()
            dslBlog.content.forEach { item ->
                blog = blog.copy(content = blog.content.plus(item))
            }

            instance = instance.copy(blogs = instance.blogs.plus(blog))
        }
    }

    return instance
}
