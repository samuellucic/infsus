package hr.unizg.fer.nis.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.testcontainers.containers.JdbcDatabaseContainer
import org.testcontainers.containers.MSSQLServerContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import java.util.*

class SqlServerTestContainer(
    private var sqlServerContainer: MSSQLServerContainer<*>? = null
): SqlTestContainer {

    companion object {
        val log: Logger = LoggerFactory.getLogger(SqlServerTestContainer::class.java)
    }

    override fun getTestContainer(): JdbcDatabaseContainer<*> {
        return sqlServerContainer!!;
    }

    override fun afterPropertiesSet() {
        if (sqlServerContainer == null) {
            sqlServerContainer = MSSQLServerContainer("mcr.microsoft.com/mssql/server:2022-latest").acceptLicense()
                .withTmpFs(Collections.singletonMap("/testtmpfs", "rw"))
                .withLogConsumer(Slf4jLogConsumer(log))
                .withReuse(true);
        }
        if (!sqlServerContainer!!.isRunning) {
            sqlServerContainer!!.start();
        }
    }

    override fun destroy() {
        if (sqlServerContainer != null && sqlServerContainer!!.isRunning) {
            sqlServerContainer!!.stop()
        }
    }
}
