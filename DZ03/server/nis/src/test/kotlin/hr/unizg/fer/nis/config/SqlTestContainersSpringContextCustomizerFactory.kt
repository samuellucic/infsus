package hr.unizg.fer.nis.config

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.annotation.AnnotatedElementUtils
import org.springframework.test.context.ContextConfigurationAttributes
import org.springframework.test.context.ContextCustomizer
import org.springframework.test.context.ContextCustomizerFactory
import org.springframework.test.context.MergedContextConfiguration

class SqlTestContainersSpringContextCustomizerFactory: ContextCustomizerFactory {

    companion object {
        private var prodTestContainer: SqlTestContainer? = null
    }

    override fun createContextCustomizer(
        testClass: Class<*>,
        configAttributes: MutableList<ContextConfigurationAttributes>
    ): ContextCustomizer {
        return object : ContextCustomizer {
            override fun customizeContext(context: ConfigurableApplicationContext, mergedConfig: MergedContextConfiguration) {
                val beanFactory: ConfigurableListableBeanFactory = context.beanFactory
                var testValues: TestPropertyValues = TestPropertyValues.empty()
                val sqlAnnotation: EmbeddedSQL? = AnnotatedElementUtils.findMergedAnnotation(testClass, EmbeddedSQL::class.java)
                if (sqlAnnotation != null) {
                    if (prodTestContainer == null) {
                        try {
                            val containerClass: Class<out SqlTestContainer> = Class.forName("${this::class.java.packageName}.SqlServerTestContainer") as (Class<out SqlTestContainer>)
                            prodTestContainer = beanFactory.createBean(containerClass)
                            beanFactory.registerSingleton(containerClass.name, prodTestContainer!!)
                        } catch (e: ClassNotFoundException) {
                            throw RuntimeException(e)
                        }
                    }
                    testValues = testValues.and("spring.datasource.url=${prodTestContainer!!.getTestContainer().jdbcUrl}")
                    testValues = testValues.and("spring.datasource.username=${prodTestContainer!!.getTestContainer().username}")
                    testValues = testValues.and("spring.datasource.password=${prodTestContainer!!.getTestContainer().password}")
                }
                testValues.applyTo(context)
            }
        }
    }

    override fun hashCode(): Int {
        return SqlTestContainer::class.java.name.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return this.hashCode() == other.hashCode()
    }
}
