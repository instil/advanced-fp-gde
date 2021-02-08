package optics.part5

import arrow.optics.Lens
import arrow.optics.extensions.list.cons.cons
import optics.part5.dsl.*
import optics.part5.model.*
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

typealias LensToList<T, U> = Lens<U, List<T>>

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
    fun <T, U> insert(container: U, item: T, lens: LensToList<T, U>): U {
        return lens.modify(container) { item.cons(it) }
    }

    var instance = dsl.toInstance()

    dsl.projects.content.forEach { dslProject ->
        var project = dslProject.toProject()

        dslProject.content.forEach {
            project = insert(project, it.toRepo(), Project.repos)
        }

        instance = insert(instance, project, Instance.projects)
    }

    dsl.profiles.content.forEach {
        instance = insert(instance, it.toProfile(), Instance.profiles)
    }

    dsl.blogs.content.forEach {
        var blog = it.toBlog()
        it.content.forEach { item ->
            blog = insert(blog, item, Blog.content)
        }

        instance = insert(instance,blog,Instance.blogs)
    }

    return instance
}
