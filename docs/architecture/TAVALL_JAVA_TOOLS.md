# MCRSpeedrun Tavall Java Tools Contract

MCRSpeedrun is a Tavall-owned Java consumer. Tavall DI is the universal first-party composition/lifecycle baseline for every Java subproject, not only `:api`.

The repository already consumes Tavall EventBus, Logging, Concurrency, Reflection, and Scheduler through the shared API. This migration makes the platform rule repository-wide and adds the missing persistence ownership direction:

- `tavall-di`: every Java module.
- `tavall-eventbus`: generic typed in-process events.
- `tavall-logging`: application/runtime logging.
- `tavall-concurrency`: asynchronous work and coordination.
- `tavall-reflection`: reusable reflection/class metadata behavior.
- `tavall-scheduler`: recurring/timed Java work.
- `tavall-database-postgres`: store persistence infrastructure.
- `tavall-database-redis`: Redis infrastructure at the Velocity/runtime edge.
- `tavall-registry`: typed keyed runtime/domain catalogs when required.
- `tavall-cache`: bounded/expiring projections when required.

## Persistence migration

`StorePersistence`, Website, and VelocityCore still contain direct Spring Data/Flyway/PostgreSQL/MySQL/Jedis infrastructure from the older architecture. The platform-adoption branch introduces Tavall Database to the relevant current modules; source migration should move persistence/cache infrastructure behind Tavall Database while preserving store/player/domain semantics.

Do not introduce new first-party service locators, ServiceLoader composition, executors, logging wrappers, registry/cache/event frameworks, reflection scanners, scheduled executors, or direct database infrastructure where the corresponding Tavall tool owns the concern.

Paper, Velocity, and Spring remain external/platform adapters where thread affinity, lifecycle, HTTP, or framework contracts require them.

Exact Java 25 repository verification, dependency-lock refresh, persistence integration tests, Paper/Velocity runtime acceptance, and website acceptance remain required before promotion.

The shared Tavall modules listed in `.tavallci` are exact-source build inputs composed by Tavall CI. Maven Local and GitHub Packages are not internal dependency authorities; third-party Paper, Velocity, and CodeMC repositories remain external inputs.
