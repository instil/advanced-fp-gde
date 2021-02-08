package optics.part4

import arrow.optics.extensions.list.cons.cons
import optics.part4.dsl.*
import optics.part4.model.*
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
                project = Project.repos.modify(project) { repo.cons(it) }
            }

            instance = Instance.projects.modify(instance) { project.cons(it) }
        }
    }

    with(dsl.profiles) {
        content.forEach { dslProfile ->
            val profile = dslProfile.toProfile()

            instance = Instance.profiles.modify(instance) { profile.cons(it) }
        }
    }

    with(dsl.blogs) {
        content.forEach { dslBlog ->
            var blog = dslBlog.toBlog()
            dslBlog.content.forEach { item ->
                blog = Blog.content.modify(blog) { item.cons(it) }
            }

            instance = Instance.blogs.modify(instance) { blog.cons(it) }
        }
    }

    return instance
}
