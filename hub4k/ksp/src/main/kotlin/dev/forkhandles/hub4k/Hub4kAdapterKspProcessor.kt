package dev.forkhandles.hub4k

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.squareup.kotlinpoet.ksp.writeTo

class Hub4kAdapterKspProcessor(
    private val logger: KSPLogger,
    private val codeGenerator: CodeGenerator
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val allActions = resolver
            .getSymbolsWithAnnotation(Hub4kAction::class.qualifiedName!!)
            .toList()

        resolver
            .getSymbolsWithAnnotation(Hub4kAdapter::class.qualifiedName!!)
            .forEach {
                it.accept(Hub4kAdapterVisitor { logger.info(it.toString()) }, allActions)
                    .forEach { it.writeTo(codeGenerator = codeGenerator, aggregating = false) }
            }
        return emptyList()
    }
}
