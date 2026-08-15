plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "MCRSpeedrun"

val tavallToolsVersion = "1.0.0"
val tavallToolRepositories = listOf(
    "tavall-di",
    "tavall-cache",
    "tavall-concurrency",
    "tavall-database",
    "tavall-eventbus",
    "tavall-logging",
    "tavall-reflection",
    "tavall-registry",
    "tavall-scheduler",
)

gradle.beforeProject { project ->
    val githubToken = providers.environmentVariable("GITHUB_TOKEN").orNull
    if (!githubToken.isNullOrBlank()) {
        tavallToolRepositories.forEach { repository ->
            project.repositories.maven("https://maven.pkg.github.com/TavallStudios/$repository") {
                name = "github${repository.replace("-", "")}"
                credentials {
                    username = providers.environmentVariable("GITHUB_ACTOR").orElse("github").get()
                    password = githubToken
                }
            }
        }
    }

    project.pluginManager.withPlugin("java") {
        // DI is universal for Tavall-owned Java consumers, not just the shared API module.
        project.dependencies.add("implementation", "org.tavall:tavall-di:$tavallToolsVersion")

        when (project.path) {
            ":store-persistence" -> project.dependencies.add(
                "api",
                "org.tavall:tavall-database-postgres:$tavallToolsVersion"
            )
            ":velocitycore" -> project.dependencies.add(
                "implementation",
                "org.tavall:tavall-database-redis:$tavallToolsVersion"
            )
        }
    }
}

include(
    "api",
    "core",
    "minecraft-integration",
    "store-domain",
    "store-persistence",
    "velocitycore",
    "kingdomfactions",
    "speedrun",
    "website",
)

project(":api").projectDir = file("API")
project(":core").projectDir = file("Core")
project(":minecraft-integration").projectDir = file("MinecraftIntegration")
project(":store-domain").projectDir = file("StoreDomain")
project(":store-persistence").projectDir = file("StorePersistence")
project(":velocitycore").projectDir = file("VelocityCore")
project(":kingdomfactions").projectDir = file("KingdomFactions")
project(":speedrun").projectDir = file("SpeedRun")
project(":website").projectDir = file("Website")
