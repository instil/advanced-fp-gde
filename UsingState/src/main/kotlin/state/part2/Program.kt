package state.part2

import arrow.core.*
import arrow.core.extensions.id.monad.monad
import arrow.mtl.State
import arrow.mtl.extensions.fx
import arrow.optics.Lens
import arrow.optics.extensions.list.cons.cons
import state.part2.dsl.*
import state.part2.model.*
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

typealias LensToList<T, U> = Lens<U, ListK<T>>

fun main() {
    val (instance, log) = createInstance(dsl)
    println(log)
    displayInstance(instance)
}

fun tab(num: Int) = "".padStart(num, '\t')

fun displayInstance(instance: Instance) {
    val result = State.fx<Instance, ForId, List<String>>(Id.monad()) {
        val title = !displayTitle
        val profiles = !displayProfiles
        val projects = !displayProjects
        val blogs = !displayBlogs

        listOf(title, profiles, projects, blogs).flatten()
    }.runA(Id.monad(), instance).fix()

    result.value().forEach(::println)
}

val displayTitle = State<Instance, List<String>> { instance ->
    instance toT ListK.just(instance.title)
}

val displayProjects = State<Instance, List<String>> { instance ->
    val output = instance.projects.flatMap { project ->
        val title = "${tab(2)}Project '${project.name}' (${project.key}) with repos:"
        val repos = project.repos.map { repo ->
            "${tab(4)}${repo.location}"
        }
        title.cons(repos).k()
    }
    instance toT "${tab(1)}Projects are:".cons(output)
}

val displayProfiles = State<Instance, List<String>> { instance ->
    val output = instance.profiles.map {
        "${tab(2)}${it.forename} ${it.surname} at ${it.email}"
    }
    instance toT "${tab(1)}Profiles are:".cons(output)
}

val displayBlogs = State<Instance, List<String>> { instance ->
    val output: List<String> = instance.blogs.flatMap {
        listOf("${tab(2)}${it.title}", "${tab(3)}${it.location}")
            .plus(it.content.map { str -> "${tab(3)}$str" })
            .k()
    }
    instance toT "${tab(1)}Blogs are:".cons(output)
}

fun DslRepo.toRepo() = Repo(location)
fun DslBlog.toBlog() = Blog(title, location)
fun DslInstance.toInstance() = Instance(title)
fun DslProfile.toProfile() = Profile(forename, surname, email)
fun DslProject.toProject() = Project(name, key)

fun <T, U> insert(container: U, item: T, lens: LensToList<T, U>): U {
    return lens.modify(container) { item.cons(it).k() }
}

fun copyProjects(dsl: DslInstance) = State<Instance, String> { instance ->
    val newInstance = dsl.projects.content.fold(instance) { result, dslProject ->
        val project = dslProject.content.fold(dslProject.toProject()) { result, dslRepo ->
            insert(result, dslRepo.toRepo(), Project.repos)
        }
        insert(result, project, Instance.projects)
    }
    newInstance toT "Created Projects"
}

fun copyProfiles(dsl: DslInstance) = State<Instance, String> { instance ->
    val newInstance = dsl.profiles.content.fold(instance) { result, dslProfile ->
        insert(result, dslProfile.toProfile(), Instance.profiles)
    }
    newInstance toT "Created Profiles"
}

fun copyBlogs(dsl: DslInstance) = State<Instance, String> { instance ->
    val newInstance = dsl.blogs.content.fold(instance) { result, dslBlog ->
        val blog = dslBlog.content.fold(dslBlog.toBlog()) { result, item ->
            insert(result, item, Blog.content)
        }
        insert(result, blog, Instance.blogs)
    }
    newInstance toT "Created Blogs"
}

fun createInstance(dsl: DslInstance) = State.fx<Instance, ForId, String>(Id.monad()) {
    val log1 = !copyProjects(dsl)
    val log2 = !copyProfiles(dsl)
    val log3 = !copyBlogs(dsl)
    "$log1 $log2 $log3"
}.runF(dsl.toInstance()).fix().value()
